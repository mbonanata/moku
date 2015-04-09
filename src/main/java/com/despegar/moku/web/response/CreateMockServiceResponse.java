package com.despegar.moku.web.response;

import java.io.Serializable;

public class CreateMockServiceResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	public CreateMockServiceResponse(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
