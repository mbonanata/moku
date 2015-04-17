package com.despegar.moku.web.dto;

import java.io.Serializable;

import com.despegar.moku.model.enums.FieldType;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestKeyFieldDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private FieldType type;

	private String code;

	/*
	 * Required if type = BODY
	 */
	@JsonProperty("path_in_json")
	private String pathInJson;


	/*
	 * Required if type = QUERY
	 */
	@JsonProperty("param_name")
	private String paramName;

	/*
	 * Required if type = PATH
	 */
	@JsonProperty("path_variable_index")
	private Integer pathVariableIndex;

	
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public Integer getPathVariableIndex() {
		return pathVariableIndex;
	}

	public void setPathVariableIndex(Integer pathVariableIndex) {
		this.pathVariableIndex = pathVariableIndex;
	}
}
