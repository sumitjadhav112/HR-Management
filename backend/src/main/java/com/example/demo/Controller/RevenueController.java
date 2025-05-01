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

import com.example.demo.DTOs.RevenueDTO;
import com.example.demo.Service.RevenueService;
import com.example.demo.response.SuccessResponse;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/management/v1/revenue")
public class RevenueController {

	@Autowired
	private RevenueService revenueService;

	@PostMapping("/saveOrUpdateRevenue")
	public SuccessResponse saveOrUpdateRevenue(@RequestBody RevenueDTO revenueDTO) {
		return revenueService.saveOrUpdateRevenue(revenueDTO);
	}

	@GetMapping("/getAllRevenue")
	public SuccessResponse getAllRevenue() {
		return revenueService.getAllRevenue();

	}

	@GetMapping("/getRevenueById/{id}")
	public SuccessResponse getRevenueById(@PathVariable Long id) {
		return revenueService.getRevenueById(id);
	}

	@DeleteMapping("/deleteRevenue/{id}")
	public SuccessResponse deleteRevenue(@PathVariable Long id) {
		return revenueService.deleteRevenue(id);
	}
}