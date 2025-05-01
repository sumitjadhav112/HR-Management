package com.example.demo.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "genius_employees")
public class OurEmployees {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long employee_id;
	private String name;
	private String email;
	private String phone;
	private boolean status = true;
	private double baseSalary;
	private String designation;
	private LocalDate joiningDate;

//	private Double advanceAmount = 0.0;

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public OurEmployees() {
		super();
	}

	public LocalDate getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(LocalDate joiningDate) {
		this.joiningDate = joiningDate;
	}

	public OurEmployees(Long employee_id, String name, String email, String phone, boolean status, double baseSalary,
			String designation, LocalDate joiningDate) {
		super();
		this.employee_id = employee_id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.status = status;
		this.baseSalary = baseSalary;
		this.designation = designation;
		this.joiningDate = joiningDate;
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
