package com.example.demo.DTOs;

import java.time.LocalDateTime;

public class NotificationsDTO {

	private Long id;
	private Long client_id;
	private String message;
	private LocalDateTime createdAt;
	private boolean isRead;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getClient_id() {
		return client_id;
	}

	public void setClient_id(Long client_id) {
		this.client_id = client_id;
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

	public NotificationsDTO(Long id, Long client_id, String message, LocalDateTime createdAt, boolean isRead) {
		super();
		this.id = id;
		this.client_id = client_id;
		this.message = message;
		this.createdAt = createdAt;
		this.isRead = isRead;
	}

	public NotificationsDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
