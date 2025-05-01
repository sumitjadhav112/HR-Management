package com.example.demo.Service;

import org.springframework.stereotype.Service;

import com.example.demo.response.SuccessResponse;

@Service
public interface InvoiceService {
	
	  public SuccessResponse generateInvoice(Long clientId);

	public SuccessResponse getInvoice(Long id);

}
