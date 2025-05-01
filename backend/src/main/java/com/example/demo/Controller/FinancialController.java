package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.ServiceImpli.FinancialService;
import com.example.demo.response.SuccessResponse;

@RestController
@RequestMapping("management/v1/financial")
@CrossOrigin(origins = "*")
public class FinancialController {

	@Autowired
	private FinancialService financialService;

	@GetMapping("/monthly-summary")
	public SuccessResponse getMonthlySummary(@RequestParam int year) {
		return financialService.getMonthlySummary(year);
	}

	@GetMapping("/monthly-details")
	public SuccessResponse getMonthlyDetails(@RequestParam int year, @RequestParam int month) {
		return financialService.getMonthlyDetails(year, month);
	}
}
