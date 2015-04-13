package com.despegar.moku.web.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MockServiceDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	@JsonProperty("request_key_fields")
	private List<RequestKeyFieldDTO> requestKeyFields;

	@JsonProperty("default_response")
	private MockServiceResponseDTO defaultResponse;

	@JsonProperty("other_responses")
	private List<MockServiceResponseDTO> otherResponses;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<RequestKeyFieldDTO> getRequestKeyFields() {
		return requestKeyFields;
	}

	public void setRequestKeyFields(List<RequestKeyFieldDTO> requestKeyFields) {
		this.requestKeyFields = requestKeyFields;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MockServiceResponseDTO getDefaultResponse() {
		return defaultResponse;
	}

	public void setDefaultResponse(MockServiceResponseDTO defaultResponse) {
		this.defaultResponse = defaultResponse;
	}

	public List<MockServiceResponseDTO> getOtherResponses() {
		return otherResponses;
	}

	public void setOtherResponses(List<MockServiceResponseDTO> otherResponses) {
		this.otherResponses = otherResponses;
	}
}
