package com.despegar.moku.web.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateRequestKeyFieldResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("mock_service_id")
	private Long mockServiceId;

	@JsonProperty("request_key_field_id")
	private Long requestKeyFieldId;

	public CreateRequestKeyFieldResponse(Long mockServiceId, Long requestKeyFieldId) {
		this.mockServiceId = mockServiceId;
		this.requestKeyFieldId = requestKeyFieldId;
	}

	public Long getMockServiceId() {
		return mockServiceId;
	}
	
	public void setMockServiceId(Long mockServiceId) {
		this.mockServiceId = mockServiceId;
	}

	public Long getRequestKeyFieldId() {
		return requestKeyFieldId;
	}

	public void setRequestKeyFieldId(Long requestKeyFieldId) {
		this.requestKeyFieldId = requestKeyFieldId;
	}
}
