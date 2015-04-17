package com.despegar.moku.web.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.servlet.HandlerMapping;

import com.despegar.moku.model.MockService;
import com.despegar.moku.model.MockServiceResponse;
import com.despegar.moku.model.RequestKeyField;
import com.despegar.moku.model.enums.FieldType;
import com.despegar.moku.service.MockServiceService;
import com.despegar.moku.util.JsonUtils;
import com.despegar.moku.web.exception.ValidationException;
import com.fasterxml.jackson.core.JsonProcessingException;

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

		String pathInfo = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);

		MockServiceResponse returnResponse = null;

		Optional<MockServiceResponse> returnOtherResponse = getOtherResponse(body, getPathVariables(pathInfo, serviceName),
				request.getParameterMap(), mockService);

		if (returnOtherResponse.isPresent()) {
			returnResponse = returnOtherResponse.get();
		} else {
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

	private String[] getPathVariables(String pathInfo, String serviceName) {
		/*
		 * Si path =>
		 * /moku/mock-services/json/{service-name}/example1/example2/...
		 * Devuelvo => ["", "example1", "example2", ...]
		 */
		return pathInfo.substring(pathInfo.indexOf(serviceName) + serviceName.length()).split("/");
	}

	private Optional<MockServiceResponse> getOtherResponse(Object body, String[] pathVariables, Map<String, String[]> queryParameters,
			MockService mockService) throws ValidationException, JsonProcessingException {

		Stream<MockServiceResponse> candidateResponses = null;

		logger.debug("pathVariables: {}", StringUtils.join(pathVariables, ", "));

		/*
		 * Si tengo pathVariables busco aquellas respuestas que contengan como
		 * valores estos parametros
		 */
		if (pathVariables.length > 1) {

		}

		if (!CollectionUtils.isEmpty(mockService.getRequestKeyFields().stream().filter((field) -> FieldType.BODY.equals(field.getType()))
				.collect(Collectors.toList()))) {

			if (body == null) {
				throw new ValidationException("body is required");
			}

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
				// returnResponse = responses.get(0);
			}
		}

		return candidateResponses != null ? candidateResponses.findFirst() : null;
	}

}