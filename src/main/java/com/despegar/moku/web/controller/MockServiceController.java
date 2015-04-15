package com.despegar.moku.web.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.despegar.moku.model.MockService;
import com.despegar.moku.model.MockServiceResponse;
import com.despegar.moku.model.RequestKeyField;
import com.despegar.moku.service.MockServiceService;
import com.despegar.moku.util.JsonUtils;
import com.despegar.moku.web.exception.ValidationException;

@RestController
@RequestMapping("/mock-services")
public class MockServiceController {

	private static final Logger logger = LoggerFactory.getLogger(MockServiceController.class);

	@Autowired
	private MockServiceService mockServiceService;

	@RequestMapping(value = "/json/{service-name}", method = { RequestMethod.GET, RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> jsonService(@PathVariable("service-name") String serviceName, @RequestBody Object body)
			throws ValidationException, IOException {
		if (StringUtils.isBlank(serviceName)) {
			throw new ValidationException("service-name is required");
		}

		MockService mockService = mockServiceService.findByName(serviceName);

		if (mockService == null) {
			throw new ValidationException(String.format("service %s not found", serviceName));
		}

		MockServiceResponse returnResponse = null;

		if (!CollectionUtils.isEmpty(mockService.getRequestKeyFields())) {
			String jsonBody = JsonUtils.writer().writeValueAsString(body);
			logger.debug("Se recibió como body el siguiente json: {}", jsonBody);

			Map<String, Optional<Object>> fielKeysWithValues = mockService.getRequestKeyFields().stream()
					.collect(Collectors.toMap(RequestKeyField::getCode, (field) -> JsonUtils.getValue(jsonBody, field.getPathInJson())));

			fielKeysWithValues.keySet().removeIf(e -> !fielKeysWithValues.get(e).isPresent());

			List<MockServiceResponse> responses = mockService
					.getOtherResponses()
					.stream()
					.filter((response) -> response
							.getRequestKeyFieldValues()
							.stream()
							.allMatch(
									(responseField) -> fielKeysWithValues.containsKey(responseField.getRequestKeyFieldCode())
											&& responseField.getValue().equals(
													fielKeysWithValues.get(responseField.getRequestKeyFieldCode()).get().toString())))
					.collect(Collectors.toList());

			if (!responses.isEmpty()) {
				returnResponse = responses.get(0);
			}
		}

		if (returnResponse == null) {
			returnResponse = mockService.getDefaultResponse();
		}

		if (returnResponse.getElapsedTime() != null) {
			try {
				logger.debug("Se aplica un retraso a la respuesta de {} miliseg.", returnResponse.getElapsedTime());
				Thread.sleep(returnResponse.getElapsedTime());
			} catch (InterruptedException e) {
			}
		}

		logger.debug("Se devuelve como body de la respuesta: {}", returnResponse.getBody());
		logger.debug("Se devuelve como http status code: {}", returnResponse.getHttpCode());

		return new ResponseEntity<String>(returnResponse.getBody(), HttpStatus.valueOf(returnResponse.getHttpCode()));
	}

}