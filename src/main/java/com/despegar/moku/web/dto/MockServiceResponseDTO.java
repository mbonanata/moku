package com.despegar.moku.web.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MockServiceResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	@JsonProperty("http_code")
	private Integer httpCode;

	private Object body;

	@JsonProperty("request_key_field_values")
	private List<RequestKeyFieldValueDTO> requestKeyFieldValues;

	@JsonProperty("elapsed_time")
	private Long elapsedTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getHttpCode() {
		return httpCode;
	}

	public void setHttpCode(Integer httpCode) {
		this.httpCode = httpCode;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	public List<RequestKeyFieldValueDTO> getRequestKeyFieldValues() {
		return requestKeyFieldValues;
	}

	public void setRequestKeyFieldValues(List<RequestKeyFieldValueDTO> requestKeyFieldValues) {
		this.requestKeyFieldValues = requestKeyFieldValues;
	}

	public Long getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(Long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
