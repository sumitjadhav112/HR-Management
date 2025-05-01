package com.example.demo.Service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.example.demo.DTOs.AdvanceAmountDTO;
import com.example.demo.DTOs.EmployeeDTO;
import com.example.demo.model.EmployeeLeave;
import com.example.demo.response.SuccessResponse;

@Service
public interface EmployeeService {

	SuccessResponse addOrUpdate(EmployeeDTO employeeDTO);

	SuccessResponse getAllEmployees();

	SuccessResponse getEmployeeById(Long id);

	SuccessResponse deleteEmployee(Long id);

//	SuccessResponse calculateAndSaveSalary(Long employeeId, LocalDate month);

	SuccessResponse addLeave(EmployeeLeave leave, Long emp_id);

	SuccessResponse getLeaves(Long emp_id);

	SuccessResponse calculateEmployeeSummary(Long employeeId, LocalDate month);

	public SuccessResponse payAdvance(Long employeeId, AdvanceAmountDTO advanceAmountDTO);
	
	public SuccessResponse deleteLeave(Long leaveId, Long empId);

}
