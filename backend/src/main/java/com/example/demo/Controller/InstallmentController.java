package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTOs.InstallmentDTO;
import com.example.demo.Service.InstallmentService;
import com.example.demo.response.SuccessResponse;

@RestController
@RequestMapping("management/v1/installment")
@CrossOrigin(origins = "*")
public class InstallmentController {

	@Autowired
	InstallmentService installmentService;

	@PostMapping("/createInstallment")
	public SuccessResponse createInstallment(@RequestBody InstallmentDTO installmentDTO) {
		return installmentService.createInstallment(installmentDTO);
	}

	@PostMapping("/markAsPaid")
	public SuccessResponse isPaidInstallment(@RequestBody InstallmentDTO installmentDTO) {
		return installmentService.isPaid(installmentDTO);
	}

	@GetMapping("/getAllInstallmentForClients/{id}")
	public SuccessResponse getAllInstallmentByClientId(@PathVariable Long id) {
		return installmentService.getAllInstallmentByClient(id);
	}

	@PostMapping("/editInstallment")
	public SuccessResponse editInstallment(@RequestBody InstallmentDTO installmentDTO) {
		return installmentService.editInstallment(installmentDTO);
	}

}
