package com.despegar.moku.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.despegar.moku.service.exception.ServiceException;
import com.despegar.moku.web.exception.ValidationException;
import com.despegar.moku.web.response.RestErrorResponse;

@ControllerAdvice(annotations = RestController.class)
public class RestExceptionHandlerAdvice {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandlerAdvice.class);

	@ExceptionHandler(value = ValidationException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public RestErrorResponse handleValidationError(ValidationException validationException) {
		LOGGER.info("ValidationError --> {}", validationException.getValidationMessage());
		RestErrorResponse response = new RestErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
		response.addCause(validationException.getValidationMessage());
		return response;
	}

	@ExceptionHandler(value = ServiceException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public RestErrorResponse handleServiceError(ServiceException serviceException) {
		LOGGER.info("ServiceError --> {}", serviceException.getMessage());
		RestErrorResponse response = new RestErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
		response.addCause(serviceException.getMessage());
		return response;
	}

	@ExceptionHandler(value = Throwable.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public RestErrorResponse handleServerError(Throwable throwable) {
		LOGGER.error("InternalServerError", throwable);
		RestErrorResponse response = new RestErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		response.addCause("A generic error has occurred on the server");
		return response;
	}

	@ExceptionHandler(value = HttpMessageConversionException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public RestErrorResponse httpMessageConversionException(HttpMessageConversionException throwable) {
		LOGGER.error("InternalServerError", throwable);
		RestErrorResponse response = new RestErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
		response.addCause(throwable.getMessage());
		return response;
	}
}