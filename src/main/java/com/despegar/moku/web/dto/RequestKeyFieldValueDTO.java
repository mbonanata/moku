package com.despegar.moku.web.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestKeyFieldValueDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	@JsonProperty("request_key_field_code")
	private String requestKeyFieldCode;

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRequestKeyFieldCode() {
		return requestKeyFieldCode;
	}

	public void setRequestKeyFieldCode(String requestKeyFieldCode) {
		this.requestKeyFieldCode = requestKeyFieldCode;
	}
}
