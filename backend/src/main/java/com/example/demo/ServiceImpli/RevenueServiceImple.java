package com.example.demo.ServiceImpli;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTOs.RevenueDTO;
import com.example.demo.Repository.RevenueRepository;
import com.example.demo.Service.RevenueService;
import com.example.demo.model.Revenue;
import com.example.demo.response.SuccessResponse;

@Service
public class RevenueServiceImple implements RevenueService {

	SuccessResponse response = new SuccessResponse();

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	RevenueRepository revenueRepository;

	@Override
	public SuccessResponse saveOrUpdateRevenue(RevenueDTO revenueDTO) {
		if (revenueDTO.getDescription() == null) {
			response.nullData();
			return response;
		}

		Revenue revenue = modelMapper.map(revenueDTO, Revenue.class);

		if (revenueDTO.getId() != null) {
			Optional<Revenue> existingRevenue = revenueRepository.findById(revenueDTO.getId());
			if (existingRevenue.isPresent()) {
				revenue.setId(existingRevenue.get().getId());
				revenueRepository.save(revenue);
				RevenueDTO updatedDTO = modelMapper.map(revenue, RevenueDTO.class);
				response.revenueUpdated(updatedDTO);
				return response;
			} else {
				response.revenueNotFound();
				return response;
			}
		} else {
			Revenue savedRevenue = revenueRepository.save(revenue);
			RevenueDTO responseDTO = modelMapper.map(savedRevenue, RevenueDTO.class);
			response.revenueAdded(responseDTO);
			return response;
		}
	}

	@Override
	public SuccessResponse getRevenueById(Long id) {
		Optional<Revenue> revenue = revenueRepository.findById(id);
		if (revenue.isPresent()) {
			RevenueDTO revenueDTO = modelMapper.map(revenue.get(), RevenueDTO.class);
			response.revenueRetrived(revenueDTO);
			return response;
		}
		response.nullData();
		return response;
	}
	@Override
	public SuccessResponse deleteRevenue(Long id) {
		if (id == null) {
			response.nullData();
			return response;
		}
		Optional<Revenue> findById = revenueRepository.findById(id);
		if (!findById.isPresent()) {
			response.revenueNotFound();
			return response;
		}
		revenueRepository.deleteById(id);
		response.revenueDeleted(findById);
		return response;
	}

	@Override
	public SuccessResponse getAllRevenue() {
		List<Revenue> findAll = revenueRepository.findAll();
		if (findAll.isEmpty()) {
			response.nullData();
			return response;
		}
		response.revenueRetrived(findAll);
		return response;
	}

}
