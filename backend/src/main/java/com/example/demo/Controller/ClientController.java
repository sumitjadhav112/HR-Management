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

import com.example.demo.DTOs.ClientDTO;
import com.example.demo.Service.ClientService;
import com.example.demo.response.SuccessResponse;

@RestController
@RequestMapping("/management/v1/client")
@CrossOrigin(origins = "*")
public class ClientController {

	@Autowired
	ClientService clientService;

	@GetMapping("/test")
	public String test() {
		return "Invoice App Run Successfully";
	}

	@PostMapping("/addOrUpdateClient")
	public SuccessResponse addOrUpdateClient(@RequestBody ClientDTO clientDTO) {
		return clientService.addOrUpdateClient(clientDTO);
	}

	@GetMapping("/getAllClients")
	public SuccessResponse getAll() {
		return clientService.getAllClients();
	}

	@GetMapping("/getClientById/{client_id}")
	public SuccessResponse getClientByid(@PathVariable Long client_id) {
		return clientService.getById(client_id);
	}

	@DeleteMapping("/deleteClient/{client_id}")
	public SuccessResponse deleteClient(@PathVariable Long client_id) {
		return clientService.deleteById(client_id);
	}
	
	
}
