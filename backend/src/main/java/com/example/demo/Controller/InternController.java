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

import com.example.demo.DTOs.InternsDTO;
import com.example.demo.Service.InternsService;
import com.example.demo.response.SuccessResponse;

@RestController
@RequestMapping("/management/v1/interns")
@CrossOrigin(origins = "*")
public class InternController {

	@Autowired
	InternsService internsService;

	@PostMapping("/saveOrUpdateIntern")
	public SuccessResponse addOrUpdateIntern(@RequestBody InternsDTO internsDTO) {
		return internsService.addOrUpdate(internsDTO);
	}

	@GetMapping("/getAllInterns")
	public SuccessResponse getAllInterns() {
		return internsService.getAllInterns();
	}

	@GetMapping("/getById/{id}")
	public SuccessResponse getInternById(@PathVariable Long id) {
		return internsService.getInternById(id);
	}

	@DeleteMapping("/deleteById/{id}")
	public SuccessResponse deleteInternById(@PathVariable Long id) {
		return internsService.deleteInternById(id);

	}

}
