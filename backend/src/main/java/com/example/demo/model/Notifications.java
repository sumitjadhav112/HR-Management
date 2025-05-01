package com.example.demo.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Notifications {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "client_id", nullable = false)
	private Client client;

	@ManyToOne
	@JoinColumn(name = "installment_id", nullable = false)
	private Installment installment;

	private String message;

	@CreationTimestamp
	private LocalDateTime createdAt;
	public String seen_time;
	private boolean isRead;

	public Long getId() {
		return id;
	}

	public Installment getInstallment() {
		return installment;
	}

	public void setInstallment(Installment installment) {
		this.installment = installment;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public String getSeen_time() {
		return seen_time;
	}

	public void setSeen_time(String seen_time) {
		this.seen_time = seen_time;
	}

	public Notifications(Long id, Client client, Installment installment, String message, LocalDateTime createdAt,
			String seen_time, boolean isRead) {
		super();
		this.id = id;
		this.client = client;
		this.installment = installment;
		this.message = message;
		this.createdAt = createdAt;
		this.seen_time = seen_time;
		this.isRead = isRead;
	}

	public Notifications() {
		super();
		// TODO Auto-generated constructor stub
	}

}
