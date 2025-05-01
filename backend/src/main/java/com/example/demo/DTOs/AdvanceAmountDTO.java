package com.example.demo.DTOs;

import java.time.LocalDate;

public class AdvanceAmountDTO {
	private Double advanceAmount = 0.0;
	private LocalDate month;

	public LocalDate getMonth() {
		return month;
	}

	public void setMonth(LocalDate month) {
		this.month = month;
	}

	public Double getAdvanceAmount() {
		return advanceAmount;
	}

	public void setAdvanceAmount(Double advanceAmount) {
		this.advanceAmount = advanceAmount;
	}

}
