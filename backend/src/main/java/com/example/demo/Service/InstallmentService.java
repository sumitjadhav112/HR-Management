package com.example.demo.Service;

import org.springframework.stereotype.Service;

import com.example.demo.DTOs.InstallmentDTO;
import com.example.demo.response.SuccessResponse;

@Service
public interface InstallmentService {

	SuccessResponse createInstallment(InstallmentDTO installmentDTO);

	SuccessResponse isPaid( InstallmentDTO installmentDTO);

	SuccessResponse getAllInstallmentByClient(Long id);

	public SuccessResponse editInstallment(InstallmentDTO installmentDTO);
}
