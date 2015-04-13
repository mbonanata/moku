package com.despegar.moku.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "MockServiceResponse")
public class MockServiceResponse implements Model {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private Integer httpCode;

	@Column(length = 2000)
	private String body;

	private Long elapsedTime;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name = "serviceResponseId", referencedColumnName = "id", nullable = true)
	private List<RequestKeyFieldValue> requestKeyFieldValues;

	public MockServiceResponse() {
		super();
	}

	public MockServiceResponse(Integer httpCode) {
		this(httpCode, null, null, null);
	}

	public MockServiceResponse(Integer httpCode, String body, Long elapsedTime, List<RequestKeyFieldValue> requestKeyFieldValues) {
		super();
		this.httpCode = httpCode;
		this.body = body;
		this.elapsedTime = elapsedTime;
		this.requestKeyFieldValues = requestKeyFieldValues;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Integer getHttpCode() {
		return httpCode;
	}

	public void setHttpCode(Integer httpCode) {
		this.httpCode = httpCode;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Long getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(Long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public List<RequestKeyFieldValue> getRequestKeyFieldValues() {
		return requestKeyFieldValues;
	}

	public void setRequestKeyFieldValues(List<RequestKeyFieldValue> requestKeyFieldValues) {
		this.requestKeyFieldValues = requestKeyFieldValues;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
