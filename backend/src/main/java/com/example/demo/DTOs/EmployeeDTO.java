package com.example.demo.DTOs;

import java.time.LocalDate;

public class EmployeeDTO {

	private Long employee_id;
	private String name;
	private String email;
	private String phone;
	private boolean status = true;
	private double baseSalary = 0.0;
	private LocalDate joiningDate;
	private String designation;

	public LocalDate getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(LocalDate joiningDate) {
		this.joiningDate = joiningDate;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public EmployeeDTO() {
		super();
	}

	public EmployeeDTO(Long employee_id, String name, String email, String phone, boolean status, double baseSalary,
			LocalDate joiningDate, String designation) {
		super();
		this.employee_id = employee_id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.status = status;
		this.baseSalary = baseSalary;
		this.joiningDate = joiningDate;
		this.designation = designation;
	}

	public Long getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(Long employee_id) {
		this.employee_id = employee_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public double getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(double baseSalary) {
		this.baseSalary = baseSalary;
	}

}
