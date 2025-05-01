package com.example.demo.ServiceImpli;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTOs.InstallmentDTO;
import com.example.demo.DTOs.InvoiceDTO;
import com.example.demo.Repository.ClientRepository;
import com.example.demo.Repository.InstallmentRepository;
import com.example.demo.Repository.InvoiceRepository;
import com.example.demo.Service.InvoiceService;
import com.example.demo.enums.InstallmentStatus;
import com.example.demo.model.Client;
import com.example.demo.model.Installment;
import com.example.demo.model.Invoice;
import com.example.demo.response.SuccessResponse;

@Service
public class InvoiceServiceImple implements InvoiceService {

	@Autowired
	private InstallmentRepository installmentRepository;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private ModelMapper modelMapper;

	private String generateInvoiceNumber() {
		LocalDate currentDate = LocalDate.now();
		int year = currentDate.getYear() % 100;
		int month = currentDate.getMonthValue();

		Optional<Invoice> lastInvoice = invoiceRepository.findTopByOrderByInvoiceNumberDesc();
		String lastInvoiceNumber;
		if (lastInvoice.isPresent()) {
			lastInvoiceNumber = lastInvoice.get().getInvoice_number();
		} else {
			lastInvoiceNumber = "INV" + String.format("%02d%02d", year, month) + "000";
		}
		String numericPart = lastInvoiceNumber.substring(7);
		int numericValue = Integer.parseInt(numericPart) + 1;
		String newNumericPart = String.format("%03d", numericValue);

		return "INV" + String.format("%02d%02d", year, month) + newNumericPart;
	}

//	@Override
//	public SuccessResponse generateInvoice(Long clientId) {
//		SuccessResponse response = new SuccessResponse();
//		if (clientId == null) {
//			response.nullData();
//			return response;
//		}
//		Optional<Client> clientOptional = clientRepository.findById(clientId);
//		if (!clientOptional.isPresent()) {
//			response.clientNotFound();
//			return response;
//		}
//		Client client = clientOptional.get();
//		List<Installment> installments = installmentRepository.findByClientId(clientId);
//		double totalAmount = client.getTotalAmount();
//		double discount = client.getTotal_discount();
//		double fare = totalAmount - discount;
//		double paidAmount = installments.stream()
//				.filter(installment -> installment.getStatus() == InstallmentStatus.PAID)
//				.mapToDouble(Installment::getAmount).sum();
//		double remainingAmount = fare - paidAmount;
//
//		Invoice invoice = new Invoice();
//		invoice.setClient(client);
//		invoice.setTotalAmount(totalAmount);
//		invoice.setPaidAmount(paidAmount);
//		invoice.setRemainingAmount(remainingAmount);
//		invoice.setInvoice_number(generateInvoiceNumber());
//		invoice.setInvoiceDate(LocalDate.now());
//
//		Invoice savedInvoice = invoiceRepository.save(invoice);
//		InvoiceDTO invoiceDTO = modelMapper.map(savedInvoice, InvoiceDTO.class);
//		invoiceDTO.setClientName(client.getClient_name());
//		invoiceDTO.setProjectDetails(client.getProjectDetails());
//		invoiceDTO.setClient_email(client.getClient_email());
//		invoiceDTO.setDiscount(discount);
//		invoiceDTO.setFare(fare);
//
//		List<InstallmentDTO> installmentDTOs = installments.stream()
//				.map(installment -> modelMapper.map(installment, InstallmentDTO.class)).collect(Collectors.toList());
//		invoiceDTO.setInstallments(installmentDTOs);
//
//		response.invoiceAdded(invoiceDTO);
//		return response;
//	}

	@Override
	public SuccessResponse generateInvoice(Long clientId) {
		SuccessResponse response = new SuccessResponse();
		if (clientId == null) {
			response.nullData();
			return response;
		}
		Optional<Client> clientOptional = clientRepository.findById(clientId);
		if (!clientOptional.isPresent()) {
			response.clientNotFound();
			return response;
		}
		Client client = clientOptional.get();
		List<Installment> installments = installmentRepository.findByClientId(clientId);
		double totalAmount = client.getTotalAmount();
		double discount = client.getTotal_discount();
		double fare = totalAmount - discount;
		double paidAmount = installments.stream()
				.filter(installment -> installment.getStatus() == InstallmentStatus.PAID)
				.mapToDouble(Installment::getAmount).sum();
		double remainingAmount = fare - paidAmount;

		Optional<Invoice> existingInvoiceOptional = invoiceRepository.findByClientId(clientId).stream().findFirst();
		Invoice invoice;
		if (existingInvoiceOptional.isPresent()) {
			invoice = existingInvoiceOptional.get();
			invoice.setTotalAmount(totalAmount);
			invoice.setPaidAmount(paidAmount);
			invoice.setRemainingAmount(remainingAmount);
			invoice.setInvoiceDate(LocalDate.now());
		} else {
			invoice = new Invoice();
			invoice.setClient(client);
			invoice.setTotalAmount(totalAmount);
			invoice.setPaidAmount(paidAmount);
			invoice.setRemainingAmount(remainingAmount);
			invoice.setInvoice_number(generateInvoiceNumber());
			invoice.setInvoiceDate(LocalDate.now());
		}

		Invoice savedInvoice = invoiceRepository.save(invoice);
		InvoiceDTO invoiceDTO = modelMapper.map(savedInvoice, InvoiceDTO.class);
		invoiceDTO.setClientName(client.getClient_name());
		invoiceDTO.setAddress(client.getAddress());
		invoiceDTO.setProjectDetails(client.getProjectDetails());
		invoiceDTO.setClient_email(client.getClient_email());
		invoiceDTO.setDiscount(discount);
		invoiceDTO.setFare(fare);

		List<InstallmentDTO> installmentDTOs = installments.stream()
				.map(installment -> modelMapper.map(installment, InstallmentDTO.class)).collect(Collectors.toList());
		invoiceDTO.setInstallments(installmentDTOs);

		response.invoiceAdded(invoiceDTO);
		return response;
	}

	@Override
	public SuccessResponse getInvoice(Long clientId) {
		SuccessResponse response = new SuccessResponse();
		List<Invoice> invoices = invoiceRepository.findByClientId(clientId);

		if (invoices.isEmpty()) {
			response.clientNotFound();
			return response;
		}
		List<InvoiceDTO> invoiceDTOs = invoices.stream().map(invoice -> {
			InvoiceDTO invoiceDTO = modelMapper.map(invoice, InvoiceDTO.class);
			Client client = invoice.getClient();
			invoiceDTO.setClientName(client.getClient_name());
			invoiceDTO.setProjectDetails(client.getProjectDetails());
			invoiceDTO.setClient_email(client.getClient_email());
			System.out.println("Email of the client" + client.getClient_email());
			invoiceDTO.setMobile_number(client.getMobile_number());
			invoiceDTO.setDiscount(client.getTotal_discount());
			invoiceDTO.setFare(client.getTotalAmount() - client.getTotal_discount());
			invoiceDTO.setAddress(client.getAddress());

			List<Installment> installments = installmentRepository.findByClientId(clientId);
			List<InstallmentDTO> installmentDTOs = installments.stream()
					.map(installment -> modelMapper.map(installment, InstallmentDTO.class))
					.collect(Collectors.toList());
			invoiceDTO.setInstallments(installmentDTOs);

			return invoiceDTO;
		}).collect(Collectors.toList());

		response.invoiceFound(invoiceDTOs);
		return response;
	}
}
