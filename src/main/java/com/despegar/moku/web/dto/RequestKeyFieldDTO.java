package com.despegar.moku.web.dto;

import java.io.Serializable;

import com.despegar.moku.model.enums.FieldType;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestKeyFieldDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private FieldType type;

	private String code;

	@JsonProperty("path_in_json")
	private String pathInJson;

	public FieldType getType() {
		return type;
	}

	public void setType(FieldType type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPathInJson() {
		return pathInJson;
	}

	public void setPathInJson(String pathInJson) {
		this.pathInJson = pathInJson;
	}
}
