package com.example.demo.DTOs;

import java.time.YearMonth;

public class MonthlySummaryDTO {

	private YearMonth month;
	private double totalIncome;
	private double totalExpenses;
	private double totalRevenue;
	private double netProfit;
	private double totalPaidAmount; // Add this field if it was missing
	private double totalEmployeeSalaries;
	private int totalLeaveDays;

	public MonthlySummaryDTO() {
		super();
	}

	public MonthlySummaryDTO(YearMonth month, double totalIncome, double totalExpenses, double totalRevenue,
			double netProfit, double totalPaidAmount, double totalEmployeeSalaries, int totalLeaveDays) {
		super();
		this.month = month;
		this.totalIncome = totalIncome;
		this.totalExpenses = totalExpenses;
		this.totalRevenue = totalRevenue;
		this.netProfit = netProfit;
		this.totalPaidAmount = totalPaidAmount;
		this.totalEmployeeSalaries = totalEmployeeSalaries;
		this.totalLeaveDays = totalLeaveDays;
	}

	// Getters and setters
	public YearMonth getMonth() {
		return month;
	}

	public void setMonth(YearMonth month) {
		this.month = month;
	}

	public double getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(double totalIncome) {
		this.totalIncome = totalIncome;
	}

	public double getTotalExpenses() {
		return totalExpenses;
	}

	public void setTotalExpenses(double totalExpenses) {
		this.totalExpenses = totalExpenses;
	}

	public double getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(double totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	public double getNetProfit() {
		return netProfit;
	}

	public void setNetProfit(double netProfit) {
		this.netProfit = netProfit;
	}

	public double getTotalPaidAmount() {
		return totalPaidAmount;
	}

	public void setTotalPaidAmount(double totalPaidAmount) {
		this.totalPaidAmount = totalPaidAmount;
	}

	public double getTotalEmployeeSalaries() {
		return totalEmployeeSalaries;
	}

	public void setTotalEmployeeSalaries(double totalEmployeeSalaries) {
		this.totalEmployeeSalaries = totalEmployeeSalaries;
	}

	public int getTotalLeaveDays() {
		return totalLeaveDays;
	}

	public void setTotalLeaveDays(int totalLeaveDays) {
		this.totalLeaveDays = totalLeaveDays;
	}
}
