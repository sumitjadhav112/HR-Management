package com.example.demo.Service;

import org.springframework.stereotype.Service;

import com.example.demo.DTOs.InternsDTO;
import com.example.demo.response.SuccessResponse;

@Service
public interface InternsService {

	SuccessResponse addOrUpdate(InternsDTO internsDTO);
	SuccessResponse getAllInterns();
	SuccessResponse getInternById(Long id);
    SuccessResponse deleteInternById(Long id);

}
