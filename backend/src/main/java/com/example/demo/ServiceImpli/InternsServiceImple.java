package com.example.demo.ServiceImpli;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTOs.InternsDTO;
import com.example.demo.Repository.InternsRepository;
import com.example.demo.Service.InternsService;
import com.example.demo.model.Interns;
import com.example.demo.response.SuccessResponse;

@Service
public class InternsServiceImple implements InternsService {

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	InternsRepository internsRepository;

	@Override
	public SuccessResponse addOrUpdate(InternsDTO internsDTO) {
		SuccessResponse response = new SuccessResponse();

		if (internsDTO.getName() == null || internsDTO.getTime_period() == null) {
			response.nullData();
			return response;
		}

		Interns interns;
		if (internsDTO.getId() != null) {
			Optional<Interns> findById = internsRepository.findById(internsDTO.getId());
			if (!findById.isPresent()) {
				response.InternNotFound();
				return response;
			}
			interns = findById.get();
			modelMapper.map(internsDTO, interns); // Update the existing entity with new values
		} else {
			interns = modelMapper.map(internsDTO, Interns.class); // Create a new entity
		}

		Interns savedIntern = internsRepository.save(interns);
		InternsDTO savedInternDTO = modelMapper.map(savedIntern, InternsDTO.class);

		if (internsDTO.getId() != null) {
			response.interUpdated(savedInternDTO);
		} else {
			response.internSave(savedInternDTO);
		}

		return response;
	}

	@Override
	public SuccessResponse getAllInterns() {
		SuccessResponse response = new SuccessResponse();
		List<Interns> internsList = internsRepository.findAll();
		List<InternsDTO> collect = internsList.stream().map(intern -> modelMapper.map(intern, InternsDTO.class))
				.collect(Collectors.toList());
		response.intersFound(collect);
		return response;
	}

	@Override
	public SuccessResponse getInternById(Long id) {
		SuccessResponse response = new SuccessResponse();
		Optional<Interns> optionalIntern = internsRepository.findById(id);
		InternsDTO data = optionalIntern.map(intern -> modelMapper.map(intern, InternsDTO.class)).orElse(null);
		response.intersFound(data);
		return response;

	}

	@Override
	public SuccessResponse deleteInternById(Long id) {
		SuccessResponse response = new SuccessResponse();
		Optional<Interns> optionalIntern = internsRepository.findById(id);
		if (optionalIntern.isPresent()) {
			Interns interns = optionalIntern.get();
			interns.setStatus(false);
			internsRepository.save(interns);
			response.setMessage("Intern deleted successfully");
		} else {
			response.InternNotFound();
		}
		return response;
	}
}
