package com.despegar.moku.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.despegar.moku.dao.MockServiceDao;
import com.despegar.moku.model.MockService;
import com.despegar.moku.model.MockServiceResponse;
import com.despegar.moku.model.RequestKeyField;
import com.despegar.moku.model.RequestKeyFieldValue;
import com.despegar.moku.service.exception.ServiceException;
import com.despegar.moku.web.dto.CreateMockServiceDTO;
import com.despegar.moku.web.dto.MockServiceResponseDTO;
import com.despegar.moku.web.dto.RequestKeyFieldDTO;
import com.despegar.moku.web.dto.RequestKeyFieldValueDTO;
import com.google.common.collect.Lists;

@Service
public class MockServiceServiceImpl implements MockServiceService {

	@Autowired
	private MockServiceDao mockServiceDao;

	@Override
	@Transactional
	public Long createMockService(CreateMockServiceDTO createMockServiceDTO) throws ServiceException {
		MockServiceResponse defaultResponse = new MockServiceResponse();

		if (this.mockServiceDao.findByName(createMockServiceDTO.getMockService().getName()) != null) {
			throw new ServiceException(
					String.format("Service with name %s already exists", createMockServiceDTO.getMockService().getName()));
		}

		defaultResponse.setName("defaultResponse");
		defaultResponse.setBody(createMockServiceDTO.getDefaultResponse().getBody());
		defaultResponse.setHttpCode(createMockServiceDTO.getDefaultResponse().getHttpCode());
		defaultResponse.setElapsedTime(createMockServiceDTO.getDefaultResponse().getElapsedTime());

		List<RequestKeyField> requestKeyFields = Lists.newArrayList();
		for (RequestKeyFieldDTO rf : createMockServiceDTO.getMockService().getRequestKeyFields()) {
			requestKeyFields.add(new RequestKeyField(rf.getType(), rf.getCode(), rf.getPathInJson()));
		}

		MockService mockService = new MockService(createMockServiceDTO.getMockService().getName(), defaultResponse, requestKeyFields);

		this.mockServiceDao.save(mockService);

		return mockService.getId();
	}

	@Override
	public List<MockService> getAllMockServices() {
		return this.mockServiceDao.findAll();
	}

	@Override
	@Transactional
	public Long addRequestKeyField(Long mockServiceId, RequestKeyFieldDTO requestKeyFieldDTO) throws ServiceException {
		MockService mockService = this.mockServiceDao.findById(mockServiceId);

		if (mockService == null) {
			throw new ServiceException(String.format("Service with id %d doesnt exists", mockServiceId));
		}

		if (mockService.getRequestKeyFields().stream()
				.anyMatch((RequestKeyField field) -> field.getCode().equalsIgnoreCase(requestKeyFieldDTO.getCode()))) {
			throw new ServiceException(String.format("Key field with code: %s already exists for service: %s",
					requestKeyFieldDTO.getCode(), mockService.getName()));
		}

		RequestKeyField field = new RequestKeyField();

		field.setCode(requestKeyFieldDTO.getCode());
		field.setType(requestKeyFieldDTO.getType());
		field.setPathInJson(requestKeyFieldDTO.getPathInJson());

		mockService.getRequestKeyFields().add(field);

		this.mockServiceDao.save(mockService);

		return mockService.getRequestKeyFields().stream().filter(keyField -> keyField.getCode().equals(field.getCode())).findFirst()
				.orElseThrow(RuntimeException::new).getId();
	}

	@Override
	@Transactional
	public Long addMockServiceResponse(Long mockServiceId, MockServiceResponseDTO mockServiceResponseDTO) throws ServiceException {
		MockService mockService = this.mockServiceDao.findById(mockServiceId);

		if (mockService == null) {
			throw new ServiceException(String.format("Service with id %d doesnt exists", mockServiceId));
		}

		/*
		 * Valido que los keyFieldValues correspondan a un KeyField asociado al
		 * servicio
		 */
		if (!CollectionUtils.isEmpty(mockServiceResponseDTO.getRequestKeyFieldValues())) {
			if (!mockServiceResponseDTO
					.getRequestKeyFieldValues()
					.stream()
					.allMatch(
							(RequestKeyFieldValueDTO value) -> mockService.getRequestKeyFields().stream()
									.anyMatch((RequestKeyField field) -> field.getCode().equalsIgnoreCase(value.getCode())))) {
				throw new ServiceException(String.format("Some key field value doesnt exists for service: %s", mockService.getName()));
			}
		}

		/*
		 * Valido que no exista otra respuesta con el mismo nombre asociado al
		 * servicio
		 */
		if (mockService.getOtherResponses().stream()
				.anyMatch(otherResponse -> otherResponse.getName().equalsIgnoreCase(mockServiceResponseDTO.getName()))) {
			throw new ServiceException(String.format("MockServiceResponse with name %s already exists for service: %s",
					mockServiceResponseDTO.getName(), mockService.getName()));
		}

		/*
		 * Valido que no haya otra respuesta con los mismos keyFieldValues
		 */
		if (mockService
				.getOtherResponses()
				.stream()
				.anyMatch(
						otherResponse -> otherResponse
								.getRequestKeyFieldValues()
								.stream()
								.allMatch(
										value -> mockServiceResponseDTO.getRequestKeyFieldValues().stream()
												.anyMatch(valueDTO -> value.getRequestKeyFieldCode().equalsIgnoreCase(valueDTO.getCode())))
								&& otherResponse.getRequestKeyFieldValues().size() == mockServiceResponseDTO.getRequestKeyFieldValues()
										.size())) {
			throw new ServiceException(String.format("Already exists a MockServiceResponse with this params for service: %s",
					mockService.getName()));
		}

		MockServiceResponse mockServiceResponse = new MockServiceResponse();

		mockServiceResponse.setName(mockServiceResponseDTO.getName());
		mockServiceResponse.setHttpCode(mockServiceResponseDTO.getHttpCode());
		mockServiceResponse.setElapsedTime(mockServiceResponseDTO.getElapsedTime());
		mockServiceResponse.setBody(mockServiceResponseDTO.getBody());

		List<RequestKeyFieldValue> requestKeyFieldValues = Lists.newArrayList();

		if (!CollectionUtils.isEmpty(mockServiceResponseDTO.getRequestKeyFieldValues())) {
			requestKeyFieldValues = mockServiceResponseDTO.getRequestKeyFieldValues().stream()
					.map(value -> new RequestKeyFieldValue(value.getCode(), value.getValue())).collect(Collectors.toList());
		}

		mockServiceResponse.setRequestKeyFieldValues(requestKeyFieldValues);

		mockService.getOtherResponses().add(mockServiceResponse);

		this.mockServiceDao.save(mockService);

		return mockService.getOtherResponses().stream().filter(response -> response.getName().equals(mockServiceResponse.getName()))
				.findFirst().orElseThrow(RuntimeException::new).getId();
	}
}
