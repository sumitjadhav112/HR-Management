package com.example.demo.model;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "employee_leave")
public class EmployeeLeave {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "employee_id", nullable = false)
	private OurEmployees employee;

	private LocalDate leaveDate;
	private String leaveType; // "Full Day" or "Half Day"

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OurEmployees getEmployee() {
		return employee;
	}

	public void setEmployee(OurEmployees employee) {
		this.employee = employee;
	}

	public LocalDate getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(LocalDate leaveDate) {
		this.leaveDate = leaveDate;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public EmployeeLeave(Long id, OurEmployees employee, LocalDate leaveDate, String leaveType) {
		super();
		this.id = id;
		this.employee = employee;
		this.leaveDate = leaveDate;
		this.leaveType = leaveType;
	}

	public EmployeeLeave() {
		super();
		// TODO Auto-generated constructor stub
	}

}
