package com.despegar.moku.web.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.despegar.moku.model.enums.FieldType;
import com.despegar.moku.service.MockServiceService;
import com.despegar.moku.service.exception.ServiceException;
import com.despegar.moku.web.dto.CreateMockServiceDTO;
import com.despegar.moku.web.dto.MockServiceResponseDTO;
import com.despegar.moku.web.dto.RequestKeyFieldDTO;
import com.despegar.moku.web.exception.ValidationException;
import com.despegar.moku.web.response.CreateMockServiceResponse;
import com.despegar.moku.web.response.CreateMockServiceResponseResponse;
import com.despegar.moku.web.response.CreateRequestKeyFieldResponse;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Api(value = "/admin", description = "Operaciones administrativas")
@RestController()
@RequestMapping("/admin")
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private MockServiceService mockServiceService;

	@RequestMapping(value = "/mock-services", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> mockServices() {
		// TODO implementar
		return null;
	}

	@ApiOperation(value = "Crear un servicio mock", produces = "json")
	@RequestMapping(value = "/mock-services", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public CreateMockServiceResponse createMockService(
			@RequestBody @ApiParam(value = "Datos requeridos para crear un servicio mock") CreateMockServiceDTO mockServiceDTO)
			throws ValidationException, ServiceException {
		if (StringUtils.isBlank(mockServiceDTO.getMockService().getName())) {
			throw new ValidationException("mock_service.name required");
		}

		if (mockServiceDTO.getDefaultResponse() == null) {
			throw new ValidationException("default_response required");
		}

		if (mockServiceDTO.getDefaultResponse().getHttpCode() == null) {
			throw new ValidationException("default_response.http_code required");
		}

		Long mockServiceId = this.mockServiceService.createMockService(mockServiceDTO);

		logger.info("Servicio {} creado con id: {}", mockServiceDTO.getMockService().getName(), mockServiceId);

		return new CreateMockServiceResponse(mockServiceId, mockServiceDTO.getMockService().getName());
	}

	@RequestMapping(value = "/mock-services/{mock-service-id}/request-key-field", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public CreateRequestKeyFieldResponse addRequestKeyField(@PathVariable("mock-service-id") Long mockServiceId,
			@RequestBody RequestKeyFieldDTO requestKeyFieldDTO) throws ValidationException, ServiceException {

		if (StringUtils.isBlank(requestKeyFieldDTO.getCode())) {
			throw new ValidationException("code is required");
		}

		if (requestKeyFieldDTO.getType() == null) {
			throw new ValidationException("type is required");
		}

		if (requestKeyFieldDTO.getType().equals(FieldType.BODY) && StringUtils.isBlank(requestKeyFieldDTO.getPathInJson())) {
			throw new ValidationException(String.format("path_in_json is required when type = '%s'", FieldType.BODY.name()));
		}

		Long requestKeyFieldId = this.mockServiceService.addRequestKeyField(mockServiceId, requestKeyFieldDTO);

		logger.info("RequestKeyField {} agregado al servicio con id: {}", requestKeyFieldDTO.getCode(), mockServiceId);

		return new CreateRequestKeyFieldResponse(mockServiceId, requestKeyFieldId);
	}

	@RequestMapping(value = "/mock-services/{mock-service-id}/mock-service-responses", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public CreateMockServiceResponseResponse addMockServiceResponse(@PathVariable("mock-service-id") Long mockServiceId,
			@RequestBody MockServiceResponseDTO mockServiceResponseDTO) throws ValidationException, ServiceException {

		if (StringUtils.isBlank(mockServiceResponseDTO.getName())) {
			throw new ValidationException("name is required");
		}

		if (mockServiceResponseDTO.getHttpCode() == null) {
			throw new ValidationException("http_code is required");
		}

		if (!CollectionUtils.isEmpty(mockServiceResponseDTO.getRequestKeyFieldValues())) {
			if (mockServiceResponseDTO.getRequestKeyFieldValues().stream()
					.filter(value -> StringUtils.isEmpty(value.getRequestKeyFieldCode())).count() > 0) {
				throw new ValidationException("Al least one request_key_field_value does not have any code");
			}
		}

		Long mockServiceResponseId = this.mockServiceService.addMockServiceResponse(mockServiceId, mockServiceResponseDTO);

		logger.info("MockServiceResponse {} agregado al servicio con id: {}", mockServiceResponseId, mockServiceId);

		return new CreateMockServiceResponseResponse(mockServiceId, mockServiceResponseId);
	}
}
