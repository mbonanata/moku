package com.despegar.moku.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import com.despegar.moku.model.MockService;
import com.despegar.moku.model.MockServiceResponse;
import com.despegar.moku.service.MockServiceService;
import com.despegar.moku.util.JsonUtils;
import com.despegar.moku.util.MockServiceUtils;
import com.despegar.moku.web.exception.ValidationException;

@RestController
@RequestMapping("/mock-services")
public class MockServiceController {

	private static final Logger logger = LoggerFactory.getLogger(MockServiceController.class);

	@Autowired
	private MockServiceService mockServiceService;

	@RequestMapping(value = { "/json/{service-name}", "/json/{service-name}/**" }, method = { RequestMethod.GET, RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> jsonService(@PathVariable("service-name") String serviceName, @RequestBody(required = false) Object body,
			HttpServletRequest request) throws ValidationException, IOException {

		if (StringUtils.isBlank(serviceName)) {
			throw new ValidationException("service-name is required");
		}

		MockService mockService = mockServiceService.findByName(serviceName);

		if (mockService == null) {
			throw new ValidationException(String.format("service %s not found", serviceName));
		}

		if (body != null) {
			logger.debug("Se recibió como body el siguiente json: {}", JsonUtils.writer().writeValueAsString(body));
		}

		String pathInfo = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);

		MockServiceResponse returnResponse = MockServiceUtils.getOtherResponse(body, getPathVariables(pathInfo, serviceName),
				request.getParameterMap(), mockService).orElse(mockService.getDefaultResponse());

		logger.info("Se aplica la respuesta con nombre: {}", returnResponse.getName());
		
		if (returnResponse.getElapsedTime() != null) {
			applyDelay(returnResponse.getElapsedTime());
		}

		logger.debug("Se devuelve como body de la respuesta: {}", returnResponse.getBody());
		logger.debug("Se devuelve como http status code: {}", returnResponse.getHttpCode());

		return new ResponseEntity<String>(returnResponse.getBody(), HttpStatus.valueOf(returnResponse.getHttpCode()));
	}

	private void applyDelay(Long elapsedTime) {
		try {
			logger.debug("Se aplica un retraso a la respuesta de {} miliseg.", elapsedTime);
			Thread.sleep(elapsedTime);
		} catch (InterruptedException e) {
		}
	}

	private String[] getPathVariables(String pathInfo, String serviceName) {
		/*
		 * Si path =>
		 * /moku/mock-services/json/{service-name}/example1/example2/...
		 * Devuelvo => ["", "example1", "example2", ...]
		 */
		
		String realPath = pathInfo.substring(pathInfo.indexOf(serviceName) + serviceName.length());
		
		logger.info("El path a procesar es {}", realPath);
		
		return realPath.split("/");
	}

}