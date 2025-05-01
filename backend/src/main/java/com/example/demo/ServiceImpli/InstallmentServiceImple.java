package com.example.demo.ServiceImpli;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTOs.InstallmentDTO;
import com.example.demo.Repository.ClientRepository;
import com.example.demo.Repository.InstallmentRepository;
import com.example.demo.Service.InstallmentService;
import com.example.demo.enums.InstallmentStatus;
import com.example.demo.model.Client;
import com.example.demo.model.Installment;
import com.example.demo.response.SuccessResponse;

@Service
public class InstallmentServiceImple implements InstallmentService {

	@Autowired
	private InstallmentRepository installmentRepository;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	InvoiceServiceImple invoiceServiceImple;

	@Autowired
	private ModelMapper modelMapper;

	SuccessResponse response = new SuccessResponse();

//	@Override
//	public SuccessResponse createInstallment(InstallmentDTO installmentDTO) {
//		SuccessResponse response = new SuccessResponse();
//		if (installmentDTO.getClient_id() == null) {
//			response.nullData();
//			return response;
//		}
//
//		Optional<Client> findById = clientRepository.findById(installmentDTO.getClient_id());
//		if (!findById.isPresent()) {
//			response.clientNotFound();
//			return response;
//		}
//		Client client = findById.get();
//		long installmentCount = installmentRepository.countByClient(client.getClient_id());
//		double totalInstallmentAmount = installmentRepository.sumByClient(client.getClient_id());
//
//		// Calculate the total amount considering the discount
//		double discountedTotalAmount = client.getTotalAmount();
//		if (client.getTotal_discount() != null) {
//			discountedTotalAmount -= client.getTotal_discount();
//		}
//		double remainingAmount = discountedTotalAmount - totalInstallmentAmount;
//
//		if (installmentCount >= 3) {
//			response.setMessage("Cannot add more than 3 installments for a single client");
//			response.setStatus(false);
//			return response;
//		}
//
//		// If it's the last installment, set the amount to the remaining amount
//		if (installmentCount == 2) {
//			installmentDTO.setAmount(remainingAmount);
//		} else if (installmentDTO.getAmount() > remainingAmount) {
//			response.setMessage("Installment amount exceeds remaining total amount");
//			response.setStatus(false);
//			return response;
//		}
//
//		Installment installment = modelMapper.map(installmentDTO, Installment.class);
//		installment.setClient(client);
//		Installment savedInstallment = installmentRepository.save(installment);
//		InstallmentDTO savedInstallmentDTO = modelMapper.map(savedInstallment, InstallmentDTO.class);
//
//		response.instalmentCreated(savedInstallmentDTO);
//		return response;
//	}

	@Override
	public SuccessResponse createInstallment(InstallmentDTO installmentDTO) {
		SuccessResponse response = new SuccessResponse();

		if (installmentDTO.getClient_id() == null) {
			response.nullData();
			return response;
		}

		Optional<Client> findById = clientRepository.findById(installmentDTO.getClient_id());
		if (!findById.isPresent()) {
			response.clientNotFound();
			return response;
		}

		Client client = findById.get();
		double totalInstallmentAmount = installmentRepository.sumByClient(client.getClient_id());

		// Calculate the total amount considering the discount
		double discountedTotalAmount = client.getTotalAmount();
		if (client.getTotal_discount() != null) {
			discountedTotalAmount -= client.getTotal_discount();
		}
		double remainingAmount = discountedTotalAmount - totalInstallmentAmount;

		// Check if the new installment amount exceeds the remaining total amount
		if (installmentDTO.getAmount() > remainingAmount) {
			response.setMessage("Installment amount exceeds remaining amount is " + remainingAmount);
			response.setStatus(false);
			return response;
		}

		Installment installment = modelMapper.map(installmentDTO, Installment.class);
		installment.setClient(client);
		Installment savedInstallment = installmentRepository.save(installment);
		InstallmentDTO savedInstallmentDTO = modelMapper.map(savedInstallment, InstallmentDTO.class);

		response.instalmentCreated(savedInstallmentDTO);
		return response;
	}

	@Override
	public SuccessResponse isPaid(InstallmentDTO installmentDTO) {
		SuccessResponse response = new SuccessResponse();

		if (installmentDTO.getInstallment_id() == null) {
			response.nullData();
			return response;
		}
		Optional<Installment> findById = installmentRepository.findById(installmentDTO.getInstallment_id());
		if (!findById.isPresent()) {
			response.installmentNotFound();
			return response;
		}
		Installment installment = findById.get();
		installment.setStatus(InstallmentStatus.PAID); // Assuming you want to mark it as paid
		Installment updatedInstallment = installmentRepository.save(installment);
		// Call generateInvoice after marking the installment as paid
		SuccessResponse invoiceResponse = this.invoiceServiceImple
				.generateInvoice(installment.getClient().getClient_id());
		if (!invoiceResponse.getStatus()) {
			response.setMessage("Installment marked as paid, but invoice generation failed.");
			response.setStatus(false);
			return response;
		}

		InstallmentDTO updatedInstallmentDTO = modelMapper.map(updatedInstallment, InstallmentDTO.class);
		response.markAsPaid(updatedInstallmentDTO);
		return response;
	}

	@Override
	public SuccessResponse getAllInstallmentByClient(Long id) {
		if (id == null) {
			response.nullData();
			return response;
		}
		List<Installment> findByClientId = installmentRepository.findByClientId(id);
		if (findByClientId.isEmpty()) {
			response.installmentNotFound();
			return response;
		}
		List<InstallmentDTO> installmentDTOs = findByClientId.stream()
				.map(installment -> modelMapper.map(installment, InstallmentDTO.class)).collect(Collectors.toList());
		response.retriveInstallment(installmentDTOs);
		return response;
	}

	@Override
	public SuccessResponse editInstallment(InstallmentDTO installmentDTO) {
		SuccessResponse response = new SuccessResponse();
		if (installmentDTO.getInstallment_id() == null || installmentDTO.getClient_id() == null) {
			response.nullData();
			return response;
		}
		Optional<Installment> optionalInstallment = installmentRepository.findById(installmentDTO.getInstallment_id());
		if (!optionalInstallment.isPresent()) {
			response.installmentNotFound();
			return response;
		}
		Optional<Client> optionalClient = clientRepository.findById(installmentDTO.getClient_id());
		if (!optionalClient.isPresent()) {
			response.clientNotFound();
			return response;
		}
		Client client = optionalClient.get();
		Installment installment = optionalInstallment.get();
		List<Installment> clientInstallments = installmentRepository.findByClientId(installmentDTO.getClient_id());
		double totalPaidAmount = clientInstallments.stream().mapToDouble(Installment::getAmount).sum();
		// Calculate the remaining amount
		double remainingAmount = client.getTotalAmount() - (totalPaidAmount - installment.getAmount());
		// Validate the new installment amount
		if (installmentDTO.getAmount() > remainingAmount) {
			response.setMessage("Installment amount exceeds remaining amout is " + remainingAmount);
			response.setStatus(false);
			return response;
		}
		installment.setAmount(installmentDTO.getAmount());
		installment.setDueDate(installmentDTO.getDueDate());
		Installment updatedInstallment = installmentRepository.save(installment);
		InstallmentDTO updatedInstallmentDTO = modelMapper.map(updatedInstallment, InstallmentDTO.class);
		response.instalmentUpdated(updatedInstallmentDTO);
		return response;
	}
}
