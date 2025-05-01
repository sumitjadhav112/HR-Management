package com.example.demo.DTOs;

public class EmployeeSummaryDTO {

	private Long employee_id;
	private String employee_name;
	private String employee_email;
	private String employee_mobile;
	private double total_salary;
	private double deduction;
	private double total_leave;
	private String designation;
	private String paymentSlipNumber;
	private String empCode;
	private Integer totalWorkingDays;
	private Double advance_amount;
	private String salaryMonth;
	private double total_fare;

	public EmployeeSummaryDTO(Long employee_id, String employee_name, String employee_email, String employee_mobile,
			double total_salary, double deduction, double total_leave, String designation, String paymentSlipNumber,
			String empCode, Integer totalWorkingDays, Double advance_amount, String salaryMonth, double total_fare) {
		super();
		this.employee_id = employee_id;
		this.employee_name = employee_name;
		this.employee_email = employee_email;
		this.employee_mobile = employee_mobile;
		this.total_salary = total_salary;
		this.deduction = deduction;
		this.total_leave = total_leave;
		this.designation = designation;
		this.paymentSlipNumber = paymentSlipNumber;
		this.empCode = empCode;
		this.totalWorkingDays = totalWorkingDays;
		this.advance_amount = advance_amount;
		this.salaryMonth = salaryMonth;
		this.total_fare = total_fare;
	}

	public Long getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(Long employee_id) {
		this.employee_id = employee_id;
	}

	public String getEmployee_name() {
		return employee_name;
	}

	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}

	public String getEmployee_email() {
		return employee_email;
	}

	public void setEmployee_email(String employee_email) {
		this.employee_email = employee_email;
	}

	public String getEmployee_mobile() {
		return employee_mobile;
	}

	public void setEmployee_mobile(String employee_mobile) {
		this.employee_mobile = employee_mobile;
	}

	public double getTotal_salary() {
		return total_salary;
	}

	public void setTotal_salary(double total_salary) {
		this.total_salary = total_salary;
	}

	public double getDeduction() {
		return deduction;
	}

	public void setDeduction(double deduction) {
		this.deduction = deduction;
	}

	public double getTotal_leave() {
		return total_leave;
	}

	public void setTotal_leave(double total_leave) {
		this.total_leave = total_leave;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getPaymentSlipNumber() {
		return paymentSlipNumber;
	}

	public void setPaymentSlipNumber(String paymentSlipNumber) {
		this.paymentSlipNumber = paymentSlipNumber;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public Integer getTotalWorkingDays() {
		return totalWorkingDays;
	}

	public void setTotalWorkingDays(Integer totalWorkingDays) {
		this.totalWorkingDays = totalWorkingDays;
	}

	public Double getAdvance_amount() {
		return advance_amount;
	}

	public void setAdvance_amount(Double advance_amount) {
		this.advance_amount = advance_amount;
	}

	public String getSalaryMonth() {
		return salaryMonth;
	}

	public void setSalaryMonth(String salaryMonth) {
		this.salaryMonth = salaryMonth;
	}

	public double getTotal_fare() {
		return total_fare;
	}

	public void setTotal_fare(double total_fare) {
		this.total_fare = total_fare;
	}

	public EmployeeSummaryDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
