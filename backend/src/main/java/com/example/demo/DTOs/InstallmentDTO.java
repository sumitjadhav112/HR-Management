package com.example.demo.DTOs;

import java.time.LocalDate;

import com.example.demo.enums.InstallmentStatus;

public class InstallmentDTO {

	private Long installment_id;
	private double amount;
	private LocalDate dueDate;
	private Long client_id;
	private InstallmentStatus status = InstallmentStatus.PENDING;

	public Long getInstallment_id() {
		return installment_id;
	}

	public void setInstallment_id(Long installment_id) {
		this.installment_id = installment_id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public Long getClient_id() {
		return client_id;
	}

	public void setClient_id(Long client_id) {
		this.client_id = client_id;
	}

	public InstallmentStatus getStatus() {
		return status;
	}

	public void setStatus(InstallmentStatus status) {
		this.status = status;
	}

	public InstallmentDTO(Long installment_id, double amount, LocalDate dueDate, Long client_id,
			InstallmentStatus status) {
		super();
		this.installment_id = installment_id;
		this.amount = amount;
		this.dueDate = dueDate;
		this.client_id = client_id;
		this.status = status;
	}

	public InstallmentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
