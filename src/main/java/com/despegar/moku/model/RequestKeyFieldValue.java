package com.despegar.moku.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RequestKeyFieldValue")
public class RequestKeyFieldValue implements Model {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String requestKeyFieldCode;

	@Column(nullable = false)
	private String value;

	public RequestKeyFieldValue() {
		super();
	}

	public RequestKeyFieldValue(String requestKeyFieldCode, String value) {
		super();
		this.requestKeyFieldCode = requestKeyFieldCode;
		this.value = value;
	}

	public String getRequestKeyFieldCode() {
		return requestKeyFieldCode;
	}

	public void setRequestKeyFieldCode(String requestKeyFieldCode) {
		this.requestKeyFieldCode = requestKeyFieldCode;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String name) {
		this.value = name;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

}
