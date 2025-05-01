package com.example.demo.model;

import java.time.LocalDate;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Interns {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String time_period;
	private Double total_Amount;
	private LocalDate startDate;
	private Double paidAmount;

	@CreationTimestamp
	private Date created_at;

	private boolean status = true;

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime_period() {
		return time_period;
	}

	public void setTime_period(String time_period) {
		this.time_period = time_period;
	}

	public Double getTotal_Amount() {
		return total_Amount;
	}

	public void setTotal_Amount(Double total_Amount) {
		this.total_Amount = total_Amount;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public Double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Interns(Long id, String name, String time_period, Double total_Amount, LocalDate startDate,
			Double paidAmount, Date created_at, boolean status) {
		super();
		this.id = id;
		this.name = name;
		this.time_period = time_period;
		this.total_Amount = total_Amount;
		this.startDate = startDate;
		this.paidAmount = paidAmount;
		this.created_at = created_at;
		this.status = status;
	}

	public Interns() {
		super();
		// TODO Auto-generated constructor stub
	}

}
