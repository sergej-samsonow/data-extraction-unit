package com.github.sergejsamsonow.dataextractionunit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "companies")
public class Company {

	@Id
	@GeneratedValue
	private int id;
	private String name;
	private String url;
	@Column(name = "address_extraction_unit_id")
	private int addressExtractionUnitId;
	@Column(name = "address_extraction_rule")
	private String addressExtractionRule;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getAddressExtractionUnitId() {
		return addressExtractionUnitId;
	}

	public void setAddressExtractionUnitId(int addressExtractionUnitId) {
		this.addressExtractionUnitId = addressExtractionUnitId;
	}

	public String getAddressExtractionRule() {
		return addressExtractionRule;
	}

	public void setAddressExtractionRule(String addressExtractionRule) {
		this.addressExtractionRule = addressExtractionRule;
	}

}
