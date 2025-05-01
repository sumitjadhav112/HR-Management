//package com.example.demo.ServiceImpli;
//
//import java.time.YearMonth;

package com.example.demo.ServiceImpli;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTOs.MonthlyDetailsDTO;
import com.example.demo.DTOs.MonthlySummaryDTO;
import com.example.demo.Repository.EmployeeRepository;
import com.example.demo.Repository.ExpenseRepository;
import com.example.demo.Repository.InternsRepository;
import com.example.demo.Repository.InvoiceRepository;
import com.example.demo.Repository.LeaveRepository;
import com.example.demo.Repository.RevenueRepository;
import com.example.demo.Repository.SalaryRepository;
import com.example.demo.model.OurEmployees;
import com.example.demo.model.Expense;
import com.example.demo.model.Interns;
import com.example.demo.model.Invoice;
import com.example.demo.model.EmployeeLeave;
import com.example.demo.model.Revenue;
import com.example.demo.model.Salary;
import com.example.demo.response.SuccessResponse;

@Service
public class FinancialService {

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private ExpenseRepository expenseRepository;

	@Autowired
	private RevenueRepository revenueRepository;

	@Autowired
	private EmployeeRepository employeeRepository; 

	@Autowired
	private InternsRepository internRepository; 

	@Autowired
	private LeaveRepository leaveRepository; 
	@Autowired
	private SalaryRepository salaryRepository;

	public SuccessResponse getMonthlySummary(int year) {
		List<MonthlySummaryDTO> monthlySummaries = new ArrayList<>();

		for (int month = 1; month <= 12; month++) {
			YearMonth yearMonth = YearMonth.of(year, month);
			Date startDate = Date.from(yearMonth.atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
			Date endDate = Date.from(yearMonth.atEndOfMonth().atStartOfDay(ZoneId.systemDefault()).toInstant());

			List<Invoice> invoices = invoiceRepository.findByDateBetween(startDate, endDate);
			List<Expense> expenses = expenseRepository.findByDateBetween(startDate, endDate);
			List<Revenue> revenues = revenueRepository.findByDateBetween(startDate, endDate);
			List<OurEmployees> employees = employeeRepository.findAll(); // Fetch all employees
			List<EmployeeLeave> leaves = leaveRepository.findByDateBetween(startDate, endDate); // Fetch all leaves

			double totalIncome = invoices.stream().mapToDouble(Invoice::getPaidAmount).sum();
			double totalExpenses = expenses.stream().mapToDouble(Expense::getAmount).sum();
			double totalRevenue = revenues.stream().mapToDouble(revenue -> revenue.getAmount().doubleValue()).sum();
//			double netProfit = totalIncome + totalRevenue - totalExpenses;
			double totalPaidAmount = invoices.stream().mapToDouble(Invoice::getPaidAmount).sum();

			// Calculate Employee and Intern data
			double totalEmployeeSalaries = employees.stream()
					.mapToDouble(employee -> calculateMonthlySalary(employee, yearMonth.atDay(1))).sum();
			int totalLeaveDays = (int) leaves.stream()
					.mapToDouble(leave -> "Full Day".equals(leave.getLeaveType()) ? 1.0 : 0.5).sum();

			// Calculate net profit after reducing employee salaries
			double netProfit = (totalIncome + totalRevenue - totalExpenses) - totalEmployeeSalaries;

			MonthlySummaryDTO summary = new MonthlySummaryDTO(yearMonth, totalIncome, totalExpenses, totalRevenue,
					netProfit, totalPaidAmount, totalEmployeeSalaries, totalLeaveDays);

			monthlySummaries.add(summary);
		}

		SuccessResponse response = new SuccessResponse();
		response.setMonthlySummaries(monthlySummaries);
		return response;
	}

	public double calculateMonthlySalary(OurEmployees employee, LocalDate month) {
		List<Salary> salaries = salaryRepository.findByEmployeeAndMonth(employee.getEmployee_id(),
				month.getMonthValue(), month.getYear());
		if (salaries.isEmpty()) {
			return 0.0;
		}
		Salary salary = salaries.get(0);
		double finalSalary = salary.getBaseSalary() - salary.getDeductions() - salary.getAdvance_amount();
		return finalSalary;
	}

	public SuccessResponse getMonthlyDetails(int year, int month) {
		YearMonth yearMonth = YearMonth.of(year, month);
		Date startDate = Date.from(yearMonth.atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
		Date endDate = Date.from(yearMonth.atEndOfMonth().atStartOfDay(ZoneId.systemDefault()).toInstant());

		List<Invoice> invoices = invoiceRepository.findByDateBetween(startDate, endDate);
		List<Expense> expenses = expenseRepository.findByDateBetween(startDate, endDate);
		List<Revenue> revenues = revenueRepository.findByDateBetween(startDate, endDate);
		List<OurEmployees> employees = employeeRepository.findAll(); // Fetch all employees
		List<Interns> interns = internRepository.findAll(); // Fetch all interns
		List<EmployeeLeave> leaves = leaveRepository.findByDateBetween(startDate, endDate); // Fetch all leaves

		MonthlyDetailsDTO monthlyDetails = new MonthlyDetailsDTO(yearMonth, invoices, expenses, revenues, employees,
				interns, leaves);

		SuccessResponse response = new SuccessResponse();
		response.MonthlyData(monthlyDetails);
		return response;
	}

}
