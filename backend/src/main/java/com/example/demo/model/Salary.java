package com.example.demo.model;

import java.time.LocalDate;

import com.example.demo.enums.SalaryStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Salary {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long salary_id;

	@ManyToOne
	@JoinColumn(name = "employee_id", referencedColumnName = "employee_id", nullable = false)
	private OurEmployees employee;

	private LocalDate month;
	private double baseSalary = 0.0;
	private double deductions = 0.0;
	private double finalSalary = 0.0;
	private Double paidAmount = 0.0; // Initialize paidAmount
	private SalaryStatus salaryStatus = SalaryStatus.PENDING; // default pending status.. PENDING,ADVANCED,PAID
	private Double advance_amount = 0.0;

	public Double getAdvance_amount() {
		return advance_amount;
	}

	public void setAdvance_amount(Double advance_amount) {
		this.advance_amount = advance_amount;
	}

	public Salary() {
		super();
	}

	public Salary(Long salary_id, OurEmployees employee, LocalDate month, double baseSalary, double deductions,
			double finalSalary, Double paidAmount, SalaryStatus salaryStatus, Double advance_amount) {
		super();
		this.salary_id = salary_id;
		this.employee = employee;
		this.month = month;
		this.baseSalary = baseSalary;
		this.deductions = deductions;
		this.finalSalary = finalSalary;
		this.paidAmount = paidAmount;
		this.salaryStatus = salaryStatus;
		this.advance_amount = advance_amount;
	}

	public Double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public Long getSalary_id() {
		return salary_id;
	}

	public void setSalary_id(Long salary_id) {
		this.salary_id = salary_id;
	}

	public OurEmployees getEmployee() {
		return employee;
	}

	public void setEmployee(OurEmployees employee) {
		this.employee = employee;
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

	public SalaryStatus getSalaryStatus() {
		return salaryStatus;
	}

	public void setSalaryStatus(SalaryStatus salaryStatus) {
		this.salaryStatus = salaryStatus;
	}

}
