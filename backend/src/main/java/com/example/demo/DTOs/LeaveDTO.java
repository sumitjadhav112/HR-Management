package com.example.demo.DTOs;

import java.time.LocalDate;

public class LeaveDTO {

	private Long leave_id;
	private Long employee_id;
	private LocalDate date;
	private String type;

	public LeaveDTO() {
		super();
	}

	public LeaveDTO(Long leave_id, Long employee_id, LocalDate date, String type) {
		super();
		this.leave_id = leave_id;
		this.employee_id = employee_id;
		this.date = date;
		this.type = type;
	}

	public Long getLeave_id() {
		return leave_id;
	}

	public void setLeave_id(Long leave_id) {
		this.leave_id = leave_id;
	}

	public Long getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(Long employee_id) {
		this.employee_id = employee_id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
