package com.despegar.moku.service;

import java.util.List;

import com.despegar.moku.model.MockService;
import com.despegar.moku.service.exception.ServiceException;
import com.despegar.moku.web.dto.CreateMockServiceDTO;
import com.despegar.moku.web.dto.MockServiceResponseDTO;
import com.despegar.moku.web.dto.RequestKeyFieldDTO;

public interface MockServiceService {

	Long createMockService(CreateMockServiceDTO createMockServiceDTO) throws ServiceException;

	List<MockService> getAllMockServices();

	Long addRequestKeyField(Long mockServiceId, RequestKeyFieldDTO requestKeyFieldDTO) throws ServiceException;

	Long addMockServiceResponse(Long mockServiceId, MockServiceResponseDTO mockServiceResponseDTO) throws ServiceException;
	
}
