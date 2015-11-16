package com.github.sergejsamsonow.dataextractionunit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "companies")
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String url;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "address_extraction_unit_id")
	private ExtractionUnit addressExtractionUnit;

	@Column(name = "address_extraction_rule")
	private String addressExtractionRule;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "last_parsed_address_id", nullable = true)
	private Address lastParsedAddress;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "current_address_id", nullable = true)
	private Address address;

	public Address getLastParsedAddress() {
		return lastParsedAddress;
	}

	public void setLastParsedAddress(Address lastParsedAddress) {
		this.lastParsedAddress = lastParsedAddress;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

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

	public ExtractionUnit getAddressExtractionUnit() {
		return addressExtractionUnit;
	}

	public void setAddressExtractionUnit(ExtractionUnit addressExtractionUnitId) {
		this.addressExtractionUnit = addressExtractionUnitId;
	}

	public String getAddressExtractionRule() {
		return addressExtractionRule;
	}

	public void setAddressExtractionRule(String addressExtractionRule) {
		this.addressExtractionRule = addressExtractionRule;
	}

}
