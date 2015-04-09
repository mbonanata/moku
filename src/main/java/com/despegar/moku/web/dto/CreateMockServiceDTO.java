package com.despegar.moku.web.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateMockServiceDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("mock_service")
	private MockServiceDTO mockService;

	@JsonProperty("default_response")
	private MockServiceResponseDTO defaultResponse;

	public MockServiceDTO getMockService() {
		return mockService;
	}

	public void setMockService(MockServiceDTO mockService) {
		this.mockService = mockService;
	}

	public MockServiceResponseDTO getDefaultResponse() {
		return defaultResponse;
	}

	public void setDefaultResponse(MockServiceResponseDTO defaultResponse) {
		this.defaultResponse = defaultResponse;
	}
}
