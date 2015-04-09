package com.despegar.moku.web.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MockServiceDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	@JsonProperty("request_key_fields")
	private List<RequestKeyFieldDTO> requestKeyFields;

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
}
