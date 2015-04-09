package com.despegar.moku.web.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateMockServiceResponseResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("mock_service_id")
	private Long mockServiceId;

	@JsonProperty("mock_service_response_id")
	private Long mockServiceResponseId;

	public CreateMockServiceResponseResponse(Long mockServiceId, Long mockServiceResponseId) {
		this.mockServiceId = mockServiceId;
		this.mockServiceResponseId = mockServiceResponseId;
	}

	public Long getMockServiceId() {
		return mockServiceId;
	}

	public void setMockServiceId(Long mockServiceId) {
		this.mockServiceId = mockServiceId;
	}

	public Long getMockServiceResponseId() {
		return mockServiceResponseId;
	}

	public void setMockServiceResponseId(Long mockServiceResponseId) {
		this.mockServiceResponseId = mockServiceResponseId;
	}
}
