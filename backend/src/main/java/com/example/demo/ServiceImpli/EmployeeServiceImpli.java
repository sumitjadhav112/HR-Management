
package com.example.demo.ServiceImpli;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTOs.AdvanceAmountDTO;
import com.example.demo.DTOs.EmployeeDTO;
import com.example.demo.DTOs.EmployeeSummaryDTO;
import com.example.demo.Repository.EmployeeRepository;
import com.example.demo.Repository.LeaveRepository;
import com.example.demo.Repository.SalaryRepository;
import com.example.demo.Service.EmployeeService;
import com.example.demo.enums.SalaryStatus;
import com.example.demo.model.OurEmployees;
import com.example.demo.model.EmployeeLeave;
import com.example.demo.model.Salary;
import com.example.demo.response.SuccessResponse;

@Service
public class EmployeeServiceImpli implements EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	private SalaryRepository salaryRepository;

	@Autowired
	LeaveRepository leaveRepository;

	@Autowired
	SalaryServiceImple salaryServiceImple;

	@Autowired
	ModelMapper modelMapper;

	private String generateUniqueEmployeeCode(OurEmployees employee) {
		String uuid = UUID.randomUUID().toString().substring(0, 8);
		String code = employee.getName().substring(0, 3).toUpperCase() + uuid;
		System.out.println("Unique Code for employee : " + code);
		return code;
	}

	private Integer calculateTotalWorkingDays(LocalDate month) {
		// Add logic to calculate working days in the month (excluding Sundays,
		// holidays, etc.)
		return 20; // Example return value
	}

	private String generatePaymentSlipNumber() {
		// Logic to generate a unique payment slip number
		return "PS123456";
	}

	@Override
	public SuccessResponse addOrUpdate(EmployeeDTO employeeDTO) {
		System.out.println("Employee designation : " + employeeDTO.getDesignation());
		SuccessResponse response = new SuccessResponse();
		if (employeeDTO.getEmail() == null || employeeDTO.getName() == null || employeeDTO.getPhone() == null) {
			response.nullData();
			return response;
		}

		OurEmployees employee;
		if (employeeDTO.getEmployee_id() != null) {
			Optional<OurEmployees> findById = employeeRepository.findById(employeeDTO.getEmployee_id());
			if (!findById.isPresent()) {
				response.employeeNotFound();
				return response;
			}
			employee = findById.get();
			System.out.println("Designation 2 : " + employee.getDesignation());
			modelMapper.map(employeeDTO, employee);
			employeeRepository.save(employee);
			response.employeeUpdated(employeeDTO);
		} else {
			employee = new OurEmployees();
			modelMapper.map(employeeDTO, employee);
			OurEmployees savedEmployee = employeeRepository.save(employee);
			employeeDTO.setEmployee_id(savedEmployee.getEmployee_id());
			response.employeeAdded(employeeDTO);
		}
		return response;
	}

	@Override
	public SuccessResponse getAllEmployees() {
		SuccessResponse response = new SuccessResponse();
		List<OurEmployees> employees = employeeRepository.findAllActiveEmployees();
		if (employees.isEmpty()) {
			response.employeeNotFound();
			return response;
		}
		List<EmployeeDTO> collect = employees.stream().map(employee -> modelMapper.map(employee, EmployeeDTO.class))
				.collect(Collectors.toList());
		response.retriveEmployees(collect);
		return response;
	}

	@Override
	public SuccessResponse getEmployeeById(Long id) {
		SuccessResponse response = new SuccessResponse();
		Optional<OurEmployees> employeeOptional = employeeRepository.findById(id);
		if (!employeeOptional.isPresent()) {
			response.employeeNotFound();
			return response;
		}
		EmployeeDTO employeeDTO = modelMapper.map(employeeOptional.get(), EmployeeDTO.class);
		response.setMessage("Employee found successfully");
		response.setStatus(true);
		response.setResponse(employeeDTO);
		return response;
	}

	@Override
	public SuccessResponse deleteEmployee(Long id) {
		SuccessResponse response = new SuccessResponse();
		Optional<OurEmployees> employeeOptional = employeeRepository.findById(id);
		if (!employeeOptional.isPresent()) {
			response.employeeNotFound();
			return response;
		}
		OurEmployees employee = employeeOptional.get();
		employee.setStatus(false);
		employeeRepository.save(employee);
		response.setMessage("Employee status set to inactive successfully");
		response.setStatus(true);
		return response;
	}

	@Override
	public SuccessResponse payAdvance(Long employeeId, AdvanceAmountDTO advanceAmountDTO) {
		SuccessResponse response = new SuccessResponse();

		Optional<OurEmployees> employeeOptional = employeeRepository.findById(employeeId);
		if (!employeeOptional.isPresent()) {
			response.employeeNotFound();
			return response;
		}
		OurEmployees employee = employeeOptional.get();
		LocalDate specifiedMonth = advanceAmountDTO.getMonth().withDayOfMonth(1);

		Optional<Salary> salaryOptional = salaryRepository.findByEmployeeIdAndMonth(employeeId, specifiedMonth);
		Salary salary;
		if (salaryOptional.isPresent()) {
			salary = salaryOptional.get();
			if (salary.getAdvance_amount() == null) {
				salary.setAdvance_amount(0.0);
				salary.setSalaryStatus(SalaryStatus.ADVANCED);
			}
			salary.setAdvance_amount(salary.getAdvance_amount() + advanceAmountDTO.getAdvanceAmount());
		} else {
			salary = new Salary();
			salary.setEmployee(employee);
			salary.setMonth(specifiedMonth);
			salary.setAdvance_amount(advanceAmountDTO.getAdvanceAmount());
			salary.setBaseSalary(employee.getBaseSalary());
			salary.setDeductions(0.0);
			salary.setFinalSalary(employee.getBaseSalary() - advanceAmountDTO.getAdvanceAmount());
			salary.setPaidAmount(0.0);
			salary.setSalaryStatus(SalaryStatus.ADVANCED);
		}
		salaryRepository.save(salary);

		response.setMessage("Advance amount paid successfully");
		response.setStatus(true);
		response.setResponse(salary);
		return response;
	}

//	@Override
//	public SuccessResponse calculateEmployeeSummary(Long employeeId, LocalDate month) {
//		SuccessResponse response = new SuccessResponse();
//
//		Optional<GeniusEmployees> employeeOptional = employeeRepository.findById(employeeId);
//		if (!employeeOptional.isPresent()) {
//			response.employeeNotFound();
//			return response;
//		}
//
//		GeniusEmployees employee = employeeOptional.get();
//		double totalAmount = employee.getBaseSalary();
//		String employeeCode = generateUniqueEmployeeCode(employee);
//
//		List<EmployeeLeave> empLeaves = leaveRepository.findByEmpId(employeeId).stream()
//				.filter(leave -> leave.getLeaveDate().getMonth().equals(month.getMonth())
//						&& leave.getLeaveDate().getYear() == month.getYear())
//				.filter(leave -> !isSunday(leave.getLeaveDate())) // Exclude Sundays
//				.collect(Collectors.toList());
//
//		// Calculate total leave deductions for the specified month
//		double fullDayLeaveDeduction = empLeaves.stream()
//				.filter(leave -> leave.getLeaveType().equalsIgnoreCase("Full Day"))
//				.mapToDouble(leave -> totalAmount * 0.033).sum();
//		double halfDayLeaveDeduction = empLeaves.stream()
//				.filter(leave -> leave.getLeaveType().equalsIgnoreCase("Half Day"))
//				.mapToDouble(leave -> totalAmount * 0.0165).sum();
//
//		System.out.println("full day deduction" + fullDayLeaveDeduction);
//		System.out.println("half day deduction" + halfDayLeaveDeduction);
//
//		double leaveDeduction = fullDayLeaveDeduction + halfDayLeaveDeduction;
//
//		System.out.println("total leave deduction : " + leaveDeduction);
//
//		// Calculate leave count in points
//		double leaveCount = empLeaves.stream()
//				.mapToDouble(leave -> leave.getLeaveType().equalsIgnoreCase("Full Day") ? 1.0 : 0.5).sum();
//
//		Optional<Salary> salaryOptional = salaryRepository.findByEmployeeIdAndMonth(employeeId,
//				month.withDayOfMonth(1));
//		double advanceAmount = 0.0;
//		if (salaryOptional.isPresent()) {
//			Salary existingSalary = salaryOptional.get();
//			advanceAmount = existingSalary.getAdvance_amount() != null ? existingSalary.getAdvance_amount() : 0.0;
//		}
//
//		double otherDeductions = salaryServiceImple.calculateDeductions(employee, month);
//		Double final_amount = otherDeductions + advanceAmount;
//		double totalFare = totalAmount - final_amount;
//
//		Salary salary;
//		if (salaryOptional.isPresent()) {
//			salary = salaryOptional.get();
//			salary.setDeductions(leaveDeduction);
//			salary.setFinalSalary(totalFare);
//		} else {
//			salary = new Salary();
//			salary.setEmployee(employee);
//			salary.setMonth(month);
//			salary.setBaseSalary(totalAmount);
//			salary.setDeductions(leaveDeduction);
//			salary.setFinalSalary(totalFare);
//			salary.setAdvance_amount(advanceAmount);
//			salary.setPaidAmount(0.0);
//			salary.setSalaryStatus(SalaryStatus.ADVANCED);
//		}
//		salaryRepository.save(salary);
//
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH);
//		String formattedMonthYear = month.format(formatter);
//
//		EmployeeSummaryDTO summaryDTO = new EmployeeSummaryDTO(employee.getEmployee_id(), employee.getName(),
//				employee.getEmail(), employee.getPhone(), totalAmount, leaveDeduction, leaveCount, advanceAmount,
//				formattedMonthYear, totalFare,employeeCode);
//
//		response.setStatus(true);
//		response.setResponse(summaryDTO);
//		return response;
//	}

	@Override
	public SuccessResponse calculateEmployeeSummary(Long employeeId, LocalDate month) {
		SuccessResponse response = new SuccessResponse();

		// Fetch employee information
		Optional<OurEmployees> employeeOptional = employeeRepository.findById(employeeId);
		if (!employeeOptional.isPresent()) {
			response.employeeNotFound();
			return response;
		}

		OurEmployees employee = employeeOptional.get();
		double totalAmount = employee.getBaseSalary();
		String employeeCode = generateUniqueEmployeeCode(employee);

		// Find employee leaves for the specified month, excluding Sundays
		List<EmployeeLeave> empLeaves = leaveRepository.findByEmpId(employeeId).stream()
				.filter(leave -> leave.getLeaveDate().getMonth().equals(month.getMonth())
						&& leave.getLeaveDate().getYear() == month.getYear())
				.filter(leave -> !isSunday(leave.getLeaveDate())) // Exclude Sundays
				.collect(Collectors.toList());

		// Calculate full day and half day leave deductions
		double fullDayLeaveDeduction = empLeaves.stream()
				.filter(leave -> leave.getLeaveType().equalsIgnoreCase("Full Day"))
				.mapToDouble(leave -> totalAmount * 0.033).sum();
		double halfDayLeaveDeduction = empLeaves.stream()
				.filter(leave -> leave.getLeaveType().equalsIgnoreCase("Half Day"))
				.mapToDouble(leave -> totalAmount * 0.0165).sum();

		double leaveDeduction = fullDayLeaveDeduction + halfDayLeaveDeduction;

		double leaveCount = empLeaves.stream()
				.mapToDouble(leave -> leave.getLeaveType().equalsIgnoreCase("Full Day") ? 1.0 : 0.5).sum();

		Optional<Salary> salaryOptional = salaryRepository.findByEmployeeIdAndMonth(employeeId,
				month.withDayOfMonth(1));
		double advanceAmount = salaryOptional.isPresent() ? salaryOptional.get().getAdvance_amount() : 0.0;

		double otherDeductions = salaryServiceImple.calculateDeductions(employee, month);
		double finalAmount = otherDeductions + advanceAmount;
		double totalFare = totalAmount - finalAmount;

		Salary salary;
		if (salaryOptional.isPresent()) {
			salary = salaryOptional.get();
			salary.setDeductions(leaveDeduction);
			salary.setFinalSalary(totalFare);
		} else {
			salary = new Salary();
			salary.setEmployee(employee);
			salary.setMonth(month);
			salary.setBaseSalary(totalAmount);
			salary.setDeductions(leaveDeduction);
			salary.setFinalSalary(totalFare);
			salary.setAdvance_amount(advanceAmount);
			salary.setPaidAmount(0.0);
			salary.setSalaryStatus(SalaryStatus.ADVANCED);
		}
		salaryRepository.save(salary);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH);
		String formattedMonthYear = month.format(formatter);

		Integer totalWorkingDays = calculateTotalWorkingDays(month); // Assuming this method is implemented

		String paymentSlipNumber = generatePaymentSlipNumber(); // Assuming this method is implemented

		EmployeeSummaryDTO summaryDTO = new EmployeeSummaryDTO(employee.getEmployee_id(), employee.getName(),
				employee.getEmail(), employee.getPhone(), totalAmount, leaveDeduction, leaveCount,
				employee.getDesignation(), // Assuming `getDesignation()` is available
				paymentSlipNumber, employeeCode, totalWorkingDays, advanceAmount, formattedMonthYear, totalFare);

		response.setStatus(true);
		response.setResponse(summaryDTO);
		return response;
	}

	private boolean isSunday(LocalDate date) {
		return date.getDayOfWeek() == DayOfWeek.SUNDAY;
	}

	@Override
	public SuccessResponse getLeaves(Long emp_id) {
		SuccessResponse response = new SuccessResponse();
		if (emp_id == null) {
			response.nullData();
			return response;
		}
		List<EmployeeLeave> empLeave = leaveRepository.findByEmpId(emp_id);
		if (empLeave.isEmpty()) {
			response.noLeavesPresent();
			return response;
		}
		response.leavesRetrived(empLeave);
		return response;
	}

	@Override
	public SuccessResponse addLeave(EmployeeLeave leave, Long emp_id) {
		SuccessResponse response = new SuccessResponse();

		Optional<OurEmployees> employeeOptional = employeeRepository.findById(emp_id);
		if (!employeeOptional.isPresent()) {
			response.employeeNotFound();
			return response;
		}

		leave.setEmployee(employeeOptional.get());
		leaveRepository.save(leave);

		response.setMessage("Leave added successfully");
		response.setStatus(true);
		response.setResponse(leave);
		return response;
	}

//	@Override
//	public SuccessResponse deleteLeave(Long leaveId, Long empId) {
//		SuccessResponse response = new SuccessResponse();
//
//		// Fetch the leave details
//		Optional<EmployeeLeave> leaveOptional = leaveRepository.findById(leaveId);
//		if (!leaveOptional.isPresent()) {
//			response.noLeavesPresent();
//			return response;
//		}
//
//		EmployeeLeave leave = leaveOptional.get();
//
//		// Ensure the leave belongs to the specified employee
//		if (!leave.getEmployee().getEmployee_id().equals(empId)) {
//			response.setMessage("Leave does not belong to the specified employee.");
//			response.setStatus(false);
//			return response;
//		}
//
//		// Calculate the amount to be re-added based on the leave type
//		double leaveAmountToBeAddedBack;
//		double baseSalary = leave.getEmployee().getBaseSalary();
//		if (leave.getLeaveType().equalsIgnoreCase("Full Day")) {
//			leaveAmountToBeAddedBack = baseSalary * 0.033;
//		} else {
//			leaveAmountToBeAddedBack = baseSalary * 0.0165;
//		}
//
//		// Fetch the employee's salary for the specific month
//		LocalDate leaveMonth = leave.getLeaveDate().withDayOfMonth(1);
//		Optional<Salary> salaryOptional = salaryRepository.findByEmployeeIdAndMonth(empId, leaveMonth);
//
//		if (!salaryOptional.isPresent()) {
//			response.setMessage("Salary record not found for the leave month.");
//			response.setStatus(false);
//			return response;
//		}
//
//		Salary salary = salaryOptional.get();
//		// Adjust the salary deduction and final salary
//		salary.setDeductions(salary.getDeductions() - leaveAmountToBeAddedBack);
//		salary.setFinalSalary(salary.getFinalSalary() + leaveAmountToBeAddedBack);
//		salaryRepository.save(salary);
//
//		// Delete the leave record
//		leaveRepository.delete(leave);
//
//		response.setMessage("Leave deleted and salary adjusted successfully.");
//		response.setStatus(true);
//		response.setResponse(salary);
//		return response;
//	}

	@Override
	public SuccessResponse deleteLeave(Long leaveId, Long empId) {
		SuccessResponse response = new SuccessResponse();

		// Fetch the leave details
		Optional<EmployeeLeave> leaveOptional = leaveRepository.findById(leaveId);
		if (!leaveOptional.isPresent()) {
			response.noLeavesPresent();
			return response;
		}

		EmployeeLeave leave = leaveOptional.get();

		// Ensure the leave belongs to the specified employee
		if (!leave.getEmployee().getEmployee_id().equals(empId)) {
			response.setMessage("Leave does not belong to the specified employee.");
			response.setStatus(false);
			return response;
		}

		// Calculate the amount to be re-added based on the leave type
		double leaveAmountToBeAddedBack = 0;
		double baseSalary = leave.getEmployee().getBaseSalary();
		if (leave.getLeaveType().equalsIgnoreCase("Full Day")) {
			leaveAmountToBeAddedBack = baseSalary * 0.033;
		} else if (leave.getLeaveType().equalsIgnoreCase("Half Day")) {
			leaveAmountToBeAddedBack = baseSalary * 0.0165;
		}

		// Fetch the employee's salary for the specific month
		LocalDate leaveMonth = leave.getLeaveDate().withDayOfMonth(1);
		Optional<Salary> salaryOptional = salaryRepository.findByEmployeeIdAndMonth(empId, leaveMonth);
		if (salaryOptional.isPresent()) {
			Salary salary = salaryOptional.get();
			// Adjust the salary deduction and final salary
			salary.setDeductions(salary.getDeductions() - leaveAmountToBeAddedBack);
			salary.setFinalSalary(salary.getFinalSalary() + leaveAmountToBeAddedBack);
			salaryRepository.save(salary);
		} else {
			// If salary record not found, simply log or handle it as needed
			// No need to adjust the salary if the record does not exist
		}
		// Delete the leave record
		leaveRepository.delete(leave);
		response.setMessage("Leave deleted and salary adjusted successfully.");
		response.setStatus(true);
		response.setResponse(salaryOptional.orElse(null));
		return response;
	}

}