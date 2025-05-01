package com.example.demo.ServiceImpli;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.itextpdf.layout.element.Table;

import com.example.demo.DTOs.InstallmentDTO;
import com.example.demo.DTOs.InvoiceDTO;
import com.example.demo.Repository.ClientRepository;
import com.example.demo.enums.InstallmentStatus;
import com.example.demo.model.Client;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

@Service
public class PdfService {

	@Autowired
	private ClientRepository clientRepository;

//	public byte[] generateInvoicePdf(InvoiceDTO invoiceDTO) {
//		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//
//		try (PdfWriter writer = new PdfWriter(byteArrayOutputStream)) {
//			PdfDocument pdfDocument = new PdfDocument(writer);
//			Document document = new Document(pdfDocument);
//
//			Optional<Client> clientOptional = clientRepository.findById(invoiceDTO.getClient_id());
//			if (clientOptional.isPresent()) {
//				Client client = clientOptional.get();
//				document.add(new Paragraph("Client Name: " + client.getClient_name()));
//			} else {
//				document.add(new Paragraph("Client Name: Not Found")); // Handle case where client is not found
//			}
//			document.add(new Paragraph("Invoice Number: " + invoiceDTO.getInvoice_number()));
//			document.add(new Paragraph("Total Amount: " + invoiceDTO.getTotalAmount()));
//			document.add(new Paragraph("Paid Amount: " + invoiceDTO.getPaidAmount()));
//			document.add(new Paragraph("Remaining Amount: " + invoiceDTO.getRemainingAmount()));
//			document.add(new Paragraph("Invoice Date: " + invoiceDTO.getInvoiceDate()));
//
//			document.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return byteArrayOutputStream.toByteArray();
//	}

	public byte[] generateInvoicePdf(InvoiceDTO invoiceDTO) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		try (PdfWriter writer = new PdfWriter(byteArrayOutputStream)) {
			PdfDocument pdfDocument = new PdfDocument(writer);
			Document document = new Document(pdfDocument);

			Optional<Client> clientOptional = clientRepository.findById(invoiceDTO.getClient_id());
			if (clientOptional.isPresent()) {
				Client client = clientOptional.get();
				document.add(new Paragraph("Client Name: " + client.getClient_name()));
			} else {
				document.add(new Paragraph("Client Name: Not Found")); // Handle case where client is not found
			}
			document.add(new Paragraph("Invoice Number: " + invoiceDTO.getInvoice_number()));
			document.add(new Paragraph("Total Amount: " + invoiceDTO.getTotalAmount()));
			document.add(new Paragraph("Paid Amount: " + invoiceDTO.getPaidAmount()));
			document.add(new Paragraph("Remaining Amount: " + invoiceDTO.getRemainingAmount()));
			document.add(new Paragraph("Invoice Date: " + invoiceDTO.getInvoiceDate()));

			document.add(new Paragraph("Installments:"));
			Table table = new Table(3);
			table.addCell("Amount");
			table.addCell("Paid");
			table.addCell("Due Date");

			for (InstallmentDTO installment : invoiceDTO.getInstallments()) {
				table.addCell(String.valueOf(installment.getAmount()));
				table.addCell(installment.getStatus() == InstallmentStatus.PAID ? "Yes" : "No");
				table.addCell(installment.getDueDate().toString());
			}

			document.add(table);
			document.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return byteArrayOutputStream.toByteArray();
	}
}
