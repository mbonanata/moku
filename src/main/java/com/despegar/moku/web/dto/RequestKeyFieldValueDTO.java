package com.despegar.moku.web.dto;

import java.io.Serializable;

public class RequestKeyFieldValueDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String code;

	private String value;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
