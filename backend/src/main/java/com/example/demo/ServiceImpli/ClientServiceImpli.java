package com.example.demo.ServiceImpli;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTOs.ClientDTO;
import com.example.demo.Repository.ClientRepository;
import com.example.demo.Repository.InstallmentRepository;
import com.example.demo.Service.ClientService;
import com.example.demo.enums.InstallmentStatus;
import com.example.demo.model.Client;
import com.example.demo.model.Installment;
import com.example.demo.response.SuccessResponse;

@Service
public class ClientServiceImpli implements ClientService {

	SuccessResponse response = new SuccessResponse();

	@Autowired
	ClientRepository clientRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	InstallmentRepository installmentRepository;

//
//	@Override
//	public SuccessResponse addOrUpdateClient(ClientDTO clientDTO) {
//		if (clientDTO.getClient_name() == null || clientDTO.getProjectDetails() == null) {
//			response.nullData();
//			return response;
//		}
//
//		Client client;
//		if (clientDTO.getClient_id() != null) {
//			Optional<Client> existingClient = clientRepository.findById(clientDTO.getClient_id());
//			if (existingClient.isPresent()) {
//				client = existingClient.get();
//				modelMapper.map(clientDTO, client);
//				clientRepository.save(client);
//				response.clientUpdated(clientDTO);
//				return response;
//			} else {
//				response.clientNotFound();
//				return response;
//			}
//		} else {
//			client = modelMapper.map(clientDTO, Client.class);
//			clientRepository.save(client);
//			ClientDTO savedClientDTO = modelMapper.map(client, ClientDTO.class);
//			response.clientAdded(savedClientDTO);
//			return response;
//		}
//	}

	@Override
	public SuccessResponse addOrUpdateClient(ClientDTO clientDTO) {
		if (clientDTO.getClient_name() == null || clientDTO.getProjectDetails() == null) {
			response.nullData();
			return response;
		}

		Client client;
		if (clientDTO.getClient_id() != null) {
			Optional<Client> existingClient = clientRepository.findById(clientDTO.getClient_id());
			if (existingClient.isPresent()) {
				client = existingClient.get();

				// Get the old total amount before update
				double oldTotalAmount = client.getTotalAmount();
				double oldDiscount = client.getTotal_discount();

				// Map the changes from the DTO
				modelMapper.map(clientDTO, client);
				clientRepository.save(client);

				// Handle the adjustment in installments
				adjustInstallmentsAfterClientUpdate(client, oldTotalAmount, oldDiscount);

				response.clientUpdated(clientDTO);
				return response;
			} else {
				response.clientNotFound();
				return response;
			}
		} else {
			client = modelMapper.map(clientDTO, Client.class);
			clientRepository.save(client);
			ClientDTO savedClientDTO = modelMapper.map(client, ClientDTO.class);
			response.clientAdded(savedClientDTO);
			return response;
		}
	}

	private void adjustInstallmentsAfterClientUpdate(Client client, double oldTotalAmount, double oldDiscount) {
		// Get the total paid amount through installments
		double totalInstallmentAmount = installmentRepository.sumByClient(client.getClient_id());

		// Calculate the new discounted total amount
		double newDiscountedTotal = client.getTotalAmount() - client.getTotal_discount();
		double remainingAmount = newDiscountedTotal - totalInstallmentAmount;

		// Check if the total amount has been reduced
		if (oldTotalAmount > client.getTotalAmount()) {
			// Calculate the amount that needs to be reduced from installments
			double amountToReduce = oldTotalAmount - client.getTotalAmount();

			// Update the client's total discount with the reduced amount
			client.setTotal_discount(client.getTotal_discount() + amountToReduce);
			clientRepository.save(client); // Save the updated discount to the client

			System.out.println("Updated Discount: " + client.getTotal_discount());

			// Get all installments in descending order of due date
			List<Installment> installments = installmentRepository.findByClientId(client.getClient_id()).stream()
					.sorted((a, b) -> b.getDueDate().compareTo(a.getDueDate())) // Sort by due date descending
					.collect(Collectors.toList());

			// Traverse through the installments and reduce the amounts
			for (Installment installment : installments) {
				if (amountToReduce <= 0) {
					break; // If the amount has been fully reduced, exit the loop
				}

				if (installment.getAmount() >= amountToReduce) {
					// If the installment amount is enough to cover the reduction, deduct the amount
					installment.setAmount(installment.getAmount() - amountToReduce);
					amountToReduce = 0; // Set amount to 0 as we have adjusted fully
				} else {
					// If the installment amount is not enough, reduce the entire installment amount
					amountToReduce -= installment.getAmount();
					installment.setAmount(0); // Set installment to 0 as it's fully reduced
				}

				// Save the updated installment
				installmentRepository.save(installment);
			}

		} else if (client.getTotalAmount() > oldTotalAmount) {
			// If the total amount has been increased, add the extra to the last installment
			double adjustment = client.getTotalAmount() - oldTotalAmount;
			Optional<Installment> lastInstallmentOpt = installmentRepository
					.findTopByClientOrderByDueDateDesc(client.getClient_id());

			if (lastInstallmentOpt.isPresent()) {
				Installment lastInstallment = lastInstallmentOpt.get();
				lastInstallment.setAmount(lastInstallment.getAmount() + adjustment);
				installmentRepository.save(lastInstallment);
			}
		}

		// Check if the discount amount has been updated
		if (client.getTotal_discount() != oldDiscount) {
			double discountDifference = client.getTotal_discount() - oldDiscount;

			if (discountDifference > 0) {
				double totalAdjustment = discountDifference;

				Optional<Installment> lastInstallmentOpt = installmentRepository
						.findTopByClientOrderByDueDateDesc(client.getClient_id());

				if (lastInstallmentOpt.isPresent()) {
					Installment lastInstallment = lastInstallmentOpt.get();
					if (!lastInstallment.getStatus().equals(InstallmentStatus.PAID)) { // Check if last installment is
																						// unpaid
						if (lastInstallment.getAmount() >= totalAdjustment) {
							lastInstallment.setAmount(lastInstallment.getAmount() - totalAdjustment);
							installmentRepository.save(lastInstallment);
						} else {
							// If the last installment can't absorb the full amount, reduce it completely
							totalAdjustment -= lastInstallment.getAmount();
							lastInstallment.setAmount(0);
							installmentRepository.save(lastInstallment);
							Optional<Installment> secondLastInstallmentOpt = installmentRepository
									.findSecondLastByClientId(client.getClient_id());

							if (secondLastInstallmentOpt.isPresent()) {
								Installment secondLastInstallment = secondLastInstallmentOpt.get();
								if (!secondLastInstallment.getStatus().equals(InstallmentStatus.PAID)) { // Check if
																											// second
																											// last
																											// installment
																											// is unpaid
									secondLastInstallment
											.setAmount(secondLastInstallment.getAmount() - totalAdjustment);
									installmentRepository.save(secondLastInstallment);
								} else {
									System.out
											.println("Second last installment is already paid; cannot reduce amount.");
								}
							} else {
								System.out.println(
										"No second last installment found for client: " + client.getClient_id());
							}
						}
					} else {
						System.out.println("Last installment is already paid; cannot reduce amount.");
					}
				}
			}
		}
	}
	
	
//	private void adjustInstallmentsAfterClientUpdate(Client client, double oldTotalAmount, double oldDiscount) {
//	// Get the total paid amount through installments
//	double totalInstallmentAmount = installmentRepository.sumByClient(client.getClient_id());
//
//	// Calculate the new discounted total amount
//	double newDiscountedTotal = client.getTotalAmount() - client.getTotal_discount();
//	double remainingAmount = newDiscountedTotal - totalInstallmentAmount;
//
//	// Check if the total amount has been reduced
//	if (oldTotalAmount > client.getTotalAmount()) {
//		// Calculate the amount that needs to be reduced from installments
//		double amountToReduce = oldTotalAmount - client.getTotalAmount();
//
//		// Add the reduced amount to the client's total discount
//		// Combine old discount with the amount reduced from the original total
//		double updatedDiscount = client.getTotal_discount() + amountToReduce;
//		client.setTotal_discount(updatedDiscount); // Update discount in the client
//		Client savedClient = clientRepository.save(client); // Save the updated client
//
//		System.out.println("Updated Discount: " + savedClient.getTotal_discount());
//
//		// Get all installments in descending order of due date
//		List<Installment> installments = installmentRepository.findByClientId(client.getClient_id()).stream()
//				.sorted((a, b) -> b.getDueDate().compareTo(a.getDueDate())) // Sort by due date descending
//				.collect(Collectors.toList());
//
//		// Traverse through the installments and reduce the amounts
//		for (Installment installment : installments) {
//			if (amountToReduce <= 0) {
//				break; // If the amount has been fully reduced, exit the loop
//			}
//
//			if (installment.getAmount() >= amountToReduce) {
//				// If the installment amount is enough to cover the reduction, deduct the amount
//				installment.setAmount(installment.getAmount() - amountToReduce);
//				amountToReduce = 0; // Set amount to 0 as we have adjusted fully
//			} else {
//				// If the installment amount is not enough, reduce the entire installment amount
//				amountToReduce -= installment.getAmount();
//				installment.setAmount(0); // Set installment to 0 as it's fully reduced
//			}
//
//			// Save the updated installment
//			installmentRepository.save(installment);
//		}
//
//	} else if (client.getTotalAmount() > oldTotalAmount) {
//		// If the total amount has been increased, add the extra to the last installment
//		double adjustment = client.getTotalAmount() - oldTotalAmount;
//		Optional<Installment> lastInstallmentOpt = installmentRepository
//				.findTopByClientOrderByDueDateDesc(client.getClient_id());
//
//		if (lastInstallmentOpt.isPresent()) {
//			Installment lastInstallment = lastInstallmentOpt.get();
//			lastInstallment.setAmount(lastInstallment.getAmount() + adjustment);
//			installmentRepository.save(lastInstallment);
//		}
//	}
//}

	@Override
	public SuccessResponse getAllClients() {
		List<Client> clients = clientRepository.findAllClientByOrder();

		if (clients.isEmpty()) {
			response.clientNotFound();
			return response;
		}

		List<ClientDTO> clientDTOs = clients.stream().map(client -> modelMapper.map(client, ClientDTO.class))
				.collect(Collectors.toList());
		response.clients(clientDTOs);
		return response;
	}

	@Override
	public SuccessResponse getById(Long clientId) {
		if (clientId == null) {
			response.nullData();
			return response;
		}

		Optional<Client> clientOptional = clientRepository.findById(clientId);

		if (clientOptional.isPresent()) {
			ClientDTO clientDTO = modelMapper.map(clientOptional.get(), ClientDTO.class);
			response.clients(clientDTO);
			return response;
		} else {
			response.clientNotFound();
			return response;
		}
	}

	@Override
	public SuccessResponse deleteById(Long clientId) {
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
		client.setStatus(false);
		clientRepository.save(client);
		response.clientDeleted();
		return response;
	}

}
