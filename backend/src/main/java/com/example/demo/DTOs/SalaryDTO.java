package com.example.demo.DTOs;

import java.time.LocalDate;

import com.example.demo.enums.SalaryStatus;

public class SalaryDTO {

	private Long salary_id;
	private Long employee_id;
	private LocalDate month;
	private double baseSalary = 0.0;
	private double deductions = 0.0;
	private double finalSalary = 0.0;
	private SalaryStatus salaryStatus = SalaryStatus.PENDING; // default pending status..
	private Double advance_amount = 0.0;

	public Double getAdvance_amount() {
		return advance_amount;
	}

	public void setAdvance_amount(Double advance_amount) {
		this.advance_amount = advance_amount;
	}

	public SalaryDTO() {
		super();
	}

	public SalaryDTO(Long salary_id, Long employee_id, LocalDate month, double baseSalary, double deductions,
			double finalSalary, SalaryStatus salaryStatus, Double advance_amount) {
		super();
		this.salary_id = salary_id;
		this.employee_id = employee_id;
		this.month = month;
		this.baseSalary = baseSalary;
		this.deductions = deductions;
		this.finalSalary = finalSalary;
		this.salaryStatus = salaryStatus;
		this.advance_amount = advance_amount;
	}

	public SalaryStatus getSalaryStatus() {
		return salaryStatus;
	}

	public void setSalaryStatus(SalaryStatus salaryStatus) {
		this.salaryStatus = salaryStatus;
	}

	public Long getSalary_id() {
		return salary_id;
	}

	public void setSalary_id(Long salary_id) {
		this.salary_id = salary_id;
	}

	public Long getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(Long employee_id) {
		this.employee_id = employee_id;
	}

	public LocalDate getMonth() {
		return month;
	}

	public void setMonth(LocalDate month) {
		this.month = month;
	}

	public double getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(double baseSalary) {
		this.baseSalary = baseSalary;
	}

	public double getDeductions() {
		return deductions;
	}

	public void setDeductions(double deductions) {
		this.deductions = deductions;
	}

	public double getFinalSalary() {
		return finalSalary;
	}

	public void setFinalSalary(double finalSalary) {
		this.finalSalary = finalSalary;
	}

}
