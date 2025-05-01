package com.example.demo.Service;

import org.springframework.stereotype.Service;

import com.example.demo.DTOs.RevenueDTO;
import com.example.demo.response.SuccessResponse;

@Service
public interface RevenueService {

    SuccessResponse saveOrUpdateRevenue(RevenueDTO revenueDTO);

	SuccessResponse getRevenueById(Long id);

	SuccessResponse deleteRevenue(Long id);

	SuccessResponse getAllRevenue();

}
