package com.example.demo.DTOs;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ClientDTO {

	private Long client_id;
	@NotBlank(message = "Name is mandatory")
	private String client_name;
	@Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits")
	private String mobile_number;
	@NotBlank(message = "Project details required")
	private String projectDetails;
	@NotBlank(message = "Enter the dead-line for project")
	private LocalDateTime timeline;
	private LocalDateTime startDate;
	private double totalAmount; // Total amount of the project
	private Double total_discount = 0.0;
	private String address;
	@Email(message = "Email should be valid")
	private String client_email;
	private boolean status = true;
	@CreationTimestamp
	private LocalDateTime creation_time;

	@UpdateTimestamp
	private LocalDateTime updated_time;

	public Double getTotal_discount() {
		return total_discount;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public Long getClient_id() {
		return client_id;
	}

	public void setClient_id(Long client_id) {
		this.client_id = client_id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
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

	public void setTotal_discount(Double total_discount) {
		this.total_discount = total_discount;
	}

	public ClientDTO(Long client_id, @NotBlank(message = "Name is mandatory") String client_name,
			@Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits") String mobile_number,
			@NotBlank(message = "Project details required") String projectDetails,
			@NotBlank(message = "Enter the dead-line for project") LocalDateTime timeline, LocalDateTime startDate,
			double totalAmount, Double total_discount, String address,
			@Email(message = "Email should be valid") String client_email, boolean status, LocalDateTime creation_time,
			LocalDateTime updated_time) {
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

	public ClientDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
