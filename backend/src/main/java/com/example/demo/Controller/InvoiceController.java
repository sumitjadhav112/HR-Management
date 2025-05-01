package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTOs.InvoiceDTO;
import com.example.demo.Service.InvoiceService;
import com.example.demo.ServiceImpli.PdfService;
import com.example.demo.response.SuccessResponse;

@RestController
@RequestMapping("/management/v1/invoice")
@CrossOrigin(origins = "*")
public class InvoiceController {

	@Autowired
	private InvoiceService invoiceService;

	@Autowired
	private PdfService pdfService;

//	@GetMapping("/generate-invoice-pdf/{clientId}")
//	public ResponseEntity<byte[]> generateInvoicePdf(@PathVariable Long clientId) {
//		SuccessResponse response = invoiceService.generateInvoice(clientId);
//		InvoiceDTO invoiceDTO = (InvoiceDTO) response.getResponse();
//		byte[] pdfBytes = pdfService.generateInvoicePdf(invoiceDTO);
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_PDF);
//		headers.setContentDispositionFormData("attachment", "invoice.pdf");
//		return ResponseEntity.ok().headers(headers).body(pdfBytes);
//	}

	@GetMapping("/generate/{clientId}")
	public SuccessResponse generateInvoice(@PathVariable Long clientId) {
		return invoiceService.generateInvoice(clientId);
	}
	
	@GetMapping("/getinvoiceByClient/{id}")
	public SuccessResponse getInvoiceByClientId(@PathVariable Long id) {
		return invoiceService.getInvoice(id);
	}
}
