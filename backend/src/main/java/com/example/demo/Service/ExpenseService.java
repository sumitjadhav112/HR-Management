package com.example.demo.Service;

import org.springframework.stereotype.Service;

import com.example.demo.DTOs.ExpenseDTO;
import com.example.demo.response.SuccessResponse;

@Service
public interface ExpenseService {
	
	 public SuccessResponse addExpense(ExpenseDTO expenseDTO);

	 public SuccessResponse getAllExpenses();

	public SuccessResponse editExpe(ExpenseDTO expenseDTO);

	public SuccessResponse deleteExp(Long id);

}
