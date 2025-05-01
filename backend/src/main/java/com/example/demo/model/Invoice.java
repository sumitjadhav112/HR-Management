package com.example.demo.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Invoice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long invoice_id;

	@ManyToOne
	@JoinColumn(name = "client_id", nullable = false)
	private Client client;

	private double totalAmount;
	private double paidAmount;
	private double remainingAmount;
	private LocalDate invoiceDate;
	private String invoice_number;

	public Long getInvoice_id() {
		return invoice_id;
	}

	public void setInvoice_id(Long invoice_id) {
		this.invoice_id = invoice_id;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
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

	public Invoice(Long invoice_id, Client client, double totalAmount, double paidAmount, double remainingAmount,
			LocalDate invoiceDate, String invoice_number) {
		super();
		this.invoice_id = invoice_id;
		this.client = client;
		this.totalAmount = totalAmount;
		this.paidAmount = paidAmount;
		this.remainingAmount = remainingAmount;
		this.invoiceDate = invoiceDate;
		this.invoice_number = invoice_number;
	}

	public Invoice() {
		super();
		// TODO Auto-generated constructor stub
	}

}
