package com.example.demo.DTOs;

import java.time.YearMonth;
import java.util.List;

import com.example.demo.model.OurEmployees;
import com.example.demo.model.Expense;
import com.example.demo.model.Interns;
import com.example.demo.model.Invoice;
import com.example.demo.model.EmployeeLeave;
import com.example.demo.model.Revenue;

public class MonthlyDetailsDTO {

	private YearMonth month;
	private List<Invoice> invoices;
	private List<Expense> expenses;
	private List<Revenue> revenues;
	private List<OurEmployees> employees;
	private List<Interns> interns;
	private List<EmployeeLeave> leaves;

	public MonthlyDetailsDTO() {
	}

	public MonthlyDetailsDTO(YearMonth month, List<Invoice> invoices, List<Expense> expenses, List<Revenue> revenues,
			List<OurEmployees> employees, List<Interns> interns, List<EmployeeLeave> leaves) {
		super();
		this.month = month;
		this.invoices = invoices;
		this.expenses = expenses;
		this.revenues = revenues;
		this.employees = employees;
		this.interns = interns;
		this.leaves = leaves;
	}

	public List<OurEmployees> getEmployees() {
		return employees;
	}

	public void setEmployees(List<OurEmployees> employees) {
		this.employees = employees;
	}

	public List<Interns> getInterns() {
		return interns;
	}

	public void setInterns(List<Interns> interns) {
		this.interns = interns;
	}

	public List<EmployeeLeave> getLeaves() {
		return leaves;
	}

	public void setLeaves(List<EmployeeLeave> leaves) {
		this.leaves = leaves;
	}

	public YearMonth getMonth() {
		return month;
	}

	public void setMonth(YearMonth month) {
		this.month = month;
	}

	public List<Invoice> getInvoices() {
		return invoices;
	}

	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}

	public List<Expense> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<Expense> expenses) {
		this.expenses = expenses;
	}

	public List<Revenue> getRevenues() {
		return revenues;
	}

	public void setRevenues(List<Revenue> revenues) {
		this.revenues = revenues;
	}
}
