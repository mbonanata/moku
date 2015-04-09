package com.despegar.moku.web.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RestErrorResponse implements Serializable {

	private static final long serialVersionUID = 3354585540850467382L;

	private int code;
	private String message;
	private List<String> causes = new ArrayList<String>();
	
	public RestErrorResponse(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getCauses() {
		return causes;
	}

	public void setCauses(List<String> causes) {
		this.causes = causes;
	}

	public void addCause(String cause) {
		if(causes == null) {
			causes = new ArrayList<String>();
		}
		if(cause != null) {
			causes.add(cause);
		}
	}
}
