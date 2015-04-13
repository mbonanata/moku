package com.despegar.moku.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.despegar.moku.model.enums.FieldType;

@Entity
@Table(name = "RequestKeyField")
public class RequestKeyField implements Model {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private FieldType type;

	@Column(nullable = false)
	private String code;

	@Column(nullable = false)
	private String pathInJson;

	public RequestKeyField() {

	}

	public RequestKeyField(FieldType type, String code, String pathInJson) {
		super();
		this.type = type;
		this.code = code;
		this.pathInJson = pathInJson;
	}

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

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

}
