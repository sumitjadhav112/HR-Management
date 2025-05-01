package com.example.demo.Controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTOs.AdvanceAmountDTO;
import com.example.demo.DTOs.EmployeeDTO;
import com.example.demo.Service.EmployeeService;
import com.example.demo.model.EmployeeLeave;
import com.example.demo.model.SalaryRequest;
import com.example.demo.response.SuccessResponse;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("management/v1/employees")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@PostMapping("/addOrUpdateEmployee")
	public SuccessResponse addOrUpdateEmployee(@RequestBody EmployeeDTO employeeDTO) {
		return employeeService.addOrUpdate(employeeDTO);
	}

	@GetMapping("/getAllActiveEmployees")
	public SuccessResponse getAllEmployees() {
		return employeeService.getAllEmployees();
	}

	@GetMapping("/getEmployeeById/{id}")
	public SuccessResponse getEmployeeById(@PathVariable Long id) {
		return employeeService.getEmployeeById(id);
	}

	@DeleteMapping("/deleteEmployee/{id}")
	public SuccessResponse deleteEmployee(@PathVariable Long id) {
		return employeeService.deleteEmployee(id);
	}

	@PostMapping("/calculateEmployeeSummary/{employeeId}")
	public SuccessResponse calculateEmployeeSummary(@PathVariable Long employeeId,
			@RequestBody SalaryRequest salaryRequest) {
		LocalDate month = LocalDate.parse(salaryRequest.getDate());
		return employeeService.calculateEmployeeSummary(employeeId, month);
	}

	@PostMapping("/addLeaves/{emp_id}")
	public SuccessResponse addLeavesForEmp(@RequestBody EmployeeLeave leave, @PathVariable Long emp_id) {
		return employeeService.addLeave(leave, emp_id);
	}

	@GetMapping("/getAllLeavesByEmpId/{emp_id}")
	public SuccessResponse getLeavesByEmpId(@PathVariable Long emp_id) {
		return employeeService.getLeaves(emp_id);
	}

	@PostMapping("/payAdvance/{employeeId}")
	public SuccessResponse payAdvance(@PathVariable Long employeeId, @RequestBody AdvanceAmountDTO advanceAmountDTO) {
		return employeeService.payAdvance(employeeId, advanceAmountDTO);
	}

	@DeleteMapping("/deleteLeave/{empId}/{leaveId}")
	public SuccessResponse deleteLeave(@PathVariable Long empId, @PathVariable Long leaveId) {
		return employeeService.deleteLeave(leaveId, empId);
	}

}
