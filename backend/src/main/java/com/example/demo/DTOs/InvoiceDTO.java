package com.example.demo.DTOs;

import java.time.LocalDate;
import java.util.List;

public class InvoiceDTO {

	private Long invoice_id;
	private Long client_id;
	private String clientName;
	private String projectDetails;
	private String client_email;
	private String address;
	private String mobile_number;
	private double totalAmount;
	private Double discount;
	private Double fare;
	private double paidAmount;
	private double remainingAmount;
	private LocalDate invoiceDate;
	private String invoice_number;
	private List<InstallmentDTO> installments;

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getFare() {
		return fare;
	}

	public void setFare(Double fare) {
		this.fare = fare;
	}

	public String getMobile_number() {
		return mobile_number;
	}

	public void setMobile_number(String mobile_number) {
		this.mobile_number = mobile_number;
	}

	public String getClient_email() {
		return client_email;
	}

	public void setClient_email(String client_email) {
		this.client_email = client_email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getInvoice_id() {
		return invoice_id;
	}

	public void setInvoice_id(Long invoice_id) {
		this.invoice_id = invoice_id;
	}

	public Long getClient_id() {
		return client_id;
	}

	public void setClient_id(Long client_id) {
		this.client_id = client_id;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getProjectDetails() {
		return projectDetails;
	}

	public void setProjectDetails(String projectDetails) {
		this.projectDetails = projectDetails;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public double getRemainingAmount() {
		return remainingAmount;
	}

	public void setRemainingAmount(double remainingAmount) {
		this.remainingAmount = remainingAmount;
	}

	public LocalDate getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(LocalDate invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getInvoice_number() {
		return invoice_number;
	}

	public void setInvoice_number(String invoice_number) {
		this.invoice_number = invoice_number;
	}

	public List<InstallmentDTO> getInstallments() {
		return installments;
	}

	public void setInstallments(List<InstallmentDTO> installments) {
		this.installments = installments;
	}

	public InvoiceDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InvoiceDTO(Long invoice_id, Long client_id, String clientName, String projectDetails, String client_email,
			String address, String mobile_number, double totalAmount, Double discount, Double fare, double paidAmount,
			double remainingAmount, LocalDate invoiceDate, String invoice_number, List<InstallmentDTO> installments) {
		super();
		this.invoice_id = invoice_id;
		this.client_id = client_id;
		this.clientName = clientName;
		this.projectDetails = projectDetails;
		this.client_email = client_email;
		this.address = address;
		this.mobile_number = mobile_number;
		this.totalAmount = totalAmount;
		this.discount = discount;
		this.fare = fare;
		this.paidAmount = paidAmount;
		this.remainingAmount = remainingAmount;
		this.invoiceDate = invoiceDate;
		this.invoice_number = invoice_number;
		this.installments = installments;
	}

}
