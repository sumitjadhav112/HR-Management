	package com.example.demo.model;
	
	import java.time.LocalDate;
	
	import com.example.demo.enums.InstallmentStatus;
	
	import jakarta.persistence.Entity;
	import jakarta.persistence.GeneratedValue;
	import jakarta.persistence.GenerationType;
	import jakarta.persistence.Id;
	import jakarta.persistence.JoinColumn;
	import jakarta.persistence.ManyToOne;
	
	@Entity
	public class Installment {
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long installment_id;
		@ManyToOne
		@JoinColumn(name = "client_id", nullable = false)
		private Client client;
	
		private double amount;
		private LocalDate dueDate;
		private InstallmentStatus status = InstallmentStatus.PENDING;
	
		public Long getInstallment_id() {
			return installment_id;
		}
	
		public void setInstallment_id(Long installment_id) {
			this.installment_id = installment_id;
		}
	
		public Client getClient() {
			return client;
		}
	
		public void setClient(Client client) {
			this.client = client;
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
	
		public InstallmentStatus getStatus() {
			return status;
		}
	
		public void setStatus(InstallmentStatus status) {
			this.status = status;
		}
	
		public Installment(Long installment_id, Client client, double amount, LocalDate dueDate, InstallmentStatus status) {
			super();
			this.installment_id = installment_id;
			this.client = client;
			this.amount = amount;
			this.dueDate = dueDate;
			this.status = status;
		}
	
		public Installment() {
			super();
			// TODO Auto-generated constructor stub
		}
	
	}
