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
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MockService")
public class MockService implements Model {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false, unique = true)
	private String name;

	@OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "defaultResponseId", nullable = false)
	private MockServiceResponse defaultResponse;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "serviceId", referencedColumnName = "id", nullable = true)
	private List<MockServiceResponse> otherResponses;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "serviceId", referencedColumnName = "id", nullable = true)
	private List<RequestKeyField> requestKeyFields;

	public MockService() {
		super();
	}

	public MockService(String name, MockServiceResponse defaultResponse) {
		this(name, defaultResponse, null, null);
	}

	public MockService(String name, MockServiceResponse defaultResponse, List<RequestKeyField> requestKeyFields) {
		this(name, defaultResponse, null, requestKeyFields);
	}

	public MockService(String name, MockServiceResponse defaultResponse, List<MockServiceResponse> otherResponses,
			List<RequestKeyField> requestKeyFields) {
		super();
		this.name = name;
		this.defaultResponse = defaultResponse;
		this.otherResponses = otherResponses;
		this.requestKeyFields = requestKeyFields;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public MockServiceResponse getDefaultResponse() {
		return defaultResponse;
	}

	public void setDefaultResponse(MockServiceResponse defaultResponse) {
		this.defaultResponse = defaultResponse;
	}

	public List<MockServiceResponse> getOtherResponses() {
		return otherResponses;
	}

	public void setOtherResponses(List<MockServiceResponse> otherResponses) {
		this.otherResponses = otherResponses;
	}

	public List<RequestKeyField> getRequestKeyFields() {
		return requestKeyFields;
	}

	public void setRequestKeyFields(List<RequestKeyField> requestKeyFields) {
		this.requestKeyFields = requestKeyFields;
	}
}
