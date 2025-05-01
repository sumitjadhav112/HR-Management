package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azure.core.annotation.PathParam;
import com.example.demo.DTOs.ExpenseDTO;
import com.example.demo.Service.ExpenseService;
import com.example.demo.response.SuccessResponse;

@RestController
@RequestMapping("management/v1/expense")
@CrossOrigin(origins = "*")
public class ExpenseController {

	@Autowired
	private ExpenseService expenseService;

	@PostMapping("/addExpenses")
	public SuccessResponse addExpense(@RequestBody ExpenseDTO expenseDTO) {
		return expenseService.addExpense(expenseDTO);
	}

	@GetMapping("/getAllExpense")
	public SuccessResponse getAllExpenses() {
		return expenseService.getAllExpenses();
	}

	@PostMapping("/updateExpense")
	public SuccessResponse editExpense(@RequestBody ExpenseDTO expenseDTO) {
		return expenseService.editExpe(expenseDTO);
	}

	@DeleteMapping("/deleteExpense/{id}")
	public SuccessResponse deleteExpense(@PathVariable Long id) {
		return expenseService.deleteExp(id);
	}
}
