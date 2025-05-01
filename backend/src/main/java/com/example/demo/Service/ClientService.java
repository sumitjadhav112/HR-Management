package com.example.demo.Service;

import org.springframework.stereotype.Service;

import com.example.demo.DTOs.ClientDTO;
import com.example.demo.response.SuccessResponse;

@Service
public interface ClientService {

	public SuccessResponse addOrUpdateClient(ClientDTO clientDTO);
	
	public SuccessResponse getAllClients();
	
	public SuccessResponse getById(Long client_id);

	public SuccessResponse deleteById(Long client_id);
	
	
}
