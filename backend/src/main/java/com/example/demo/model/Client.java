package com.example.demo.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long client_id;
	private String client_name;
	private String mobile_number;
	private String projectDetails;
	private LocalDateTime timeline;
	private LocalDateTime startDate;
	private Double totalAmount; // Total amount of the project
	private Double total_discount = 0.0;
	private String address;
	private String client_email;
	private boolean status = true;
	@CreationTimestamp
	private LocalDateTime creation_time;

	@UpdateTimestamp
	private LocalDateTime updated_time;

	public Client(Long client_id, String client_name, String mobile_number, String projectDetails,
			LocalDateTime timeline, LocalDateTime startDate, Double totalAmount, Double total_discount, String address,
			String client_email, boolean status, LocalDateTime creation_time, LocalDateTime updated_time) {
		super();
		this.client_id = client_id;
		this.client_name = client_name;
		this.mobile_number = mobile_number;
		this.projectDetails = projectDetails;
		this.timeline = timeline;
		this.startDate = startDate;
		this.totalAmount = totalAmount;
		this.total_discount = total_discount;
		this.address = address;
		this.client_email = client_email;
		this.status = status;
		this.creation_time = creation_time;
		this.updated_time = updated_time;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getClient_id() {
		return client_id;
	}

	public void setClient_id(Long client_id) {
		this.client_id = client_id;
	}

	public String getClient_name() {
		return client_name;
	}

	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}

	public String getMobile_number() {
		return mobile_number;
	}

	public void setMobile_number(String mobile_number) {
		this.mobile_number = mobile_number;
	}

	public String getProjectDetails() {
		return projectDetails;
	}

	public void setProjectDetails(String projectDetails) {
		this.projectDetails = projectDetails;
	}

	public LocalDateTime getTimeline() {
		return timeline;
	}

	public void setTimeline(LocalDateTime timeline) {
		this.timeline = timeline;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Double getTotal_discount() {
		return total_discount;
	}

	public void setTotal_discount(Double total_discount) {
		this.total_discount = total_discount;
	}

	public String getClient_email() {
		return client_email;
	}

	public void setClient_email(String client_email) {
		this.client_email = client_email;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public LocalDateTime getCreation_time() {
		return creation_time;
	}

	public void setCreation_time(LocalDateTime creation_time) {
		this.creation_time = creation_time;
	}

	public LocalDateTime getUpdated_time() {
		return updated_time;
	}

	public void setUpdated_time(LocalDateTime updated_time) {
		this.updated_time = updated_time;
	}

	public Client() {
		super();
	}

}
