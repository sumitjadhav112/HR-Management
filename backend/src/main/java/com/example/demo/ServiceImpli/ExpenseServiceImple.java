package com.example.demo.ServiceImpli;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTOs.ExpenseDTO;
import com.example.demo.Repository.ExpenseRepository;
import com.example.demo.Service.ExpenseService;
import com.example.demo.model.Expense;
import com.example.demo.response.SuccessResponse;

@Service
public class ExpenseServiceImple implements ExpenseService {

	@Autowired
	ExpenseRepository expenseRepository;

	@Autowired
	ModelMapper modelMapper;

	private SuccessResponse response = new SuccessResponse();

	@Override
	public SuccessResponse addExpense(ExpenseDTO expenseDTO) {
		if (expenseDTO.getType() == null) {
			response.nullData();
			return response;
		}
		Expense expense = modelMapper.map(expenseDTO, Expense.class);
		expenseRepository.save(expense);
		ExpenseDTO map = modelMapper.map(expense, expenseDTO.getClass());
		response.expenseAdded(map);
		return response;
	}

	@Override
	public SuccessResponse getAllExpenses() {
		List<Expense> expenses = expenseRepository.findAll();
		if (expenses.isEmpty()) {
			response.expenseNotFound();
			return response;
		}
		List<ExpenseDTO> expenseDTOs = expenses.stream().map(expense -> modelMapper.map(expense, ExpenseDTO.class))
				.collect(Collectors.toList());
		response.expenseFound(expenseDTOs);
		return response;
	}

	public SuccessResponse editExpe(ExpenseDTO expenseDTO) {
		if (expenseDTO.getType() == null || expenseDTO.getAmount() <= 0 || expenseDTO.getDate() == null) {
			response.nullData();
			return response;
		}

		Optional<Expense> findById = expenseRepository.findById(expenseDTO.getId());
		if (findById.isPresent()) {
			Expense expense = findById.get();
			expense.setType(expenseDTO.getType());
			expense.setAmount(expenseDTO.getAmount());
			expense.setDate(expenseDTO.getDate());

			expenseRepository.save(expense);

			ExpenseDTO updatedExpenseDTO = modelMapper.map(expense, ExpenseDTO.class);
			response.expenseUpdated(updatedExpenseDTO);
			return response;
		} else {
			response.expenseNotFound();
			return response;
		}
	}

	@Override
	public SuccessResponse deleteExp(Long id) {
		SuccessResponse response = new SuccessResponse();
		if (id == null) {
			response.nullData();
			return response;
		}
		expenseRepository.deleteById(id);
		response.expenseDeleted();
		return response;
	}

}
