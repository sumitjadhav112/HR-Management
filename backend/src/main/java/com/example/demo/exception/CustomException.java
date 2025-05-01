package com.example.demo.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.response.SuccessResponse;

@RestControllerAdvice
public class CustomException {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<SuccessResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage)
				.collect(Collectors.toList());

		Map<String, List<String>> errorsMap = getErrorsMap(errors);

		SuccessResponse response = new SuccessResponse();

		response.setMessage("Bad Request");
		response.setStatus(false);
		response.setResponse(errorsMap);

		return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	private Map<String, List<String>> getErrorsMap(List<String> errors) {
		Map<String, List<String>> errorResponse = new HashMap<>();
		errorResponse.put("errors", errors);
		return errorResponse;
	}

	@ExceptionHandler(UnexpectedRollbackException.class) // Datta
	@ResponseBody
	public String handleUnexpectedRollbackException(UnexpectedRollbackException ex) {
		// Log the exception or handle it appropriately
		return "Transaction rolled back unexpectedly: " + ex.getMessage();
	}

}
