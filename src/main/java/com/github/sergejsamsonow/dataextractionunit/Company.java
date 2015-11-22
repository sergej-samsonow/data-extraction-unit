package com.github.sergejsamsonow.dataextractionunit;

import static java.lang.String.format;

import java.util.Objects;

public class Company {

	private int id;
	private String name;
	private String url;
	private ExtractionUnit addressExtractionUnit;
	private String addressExtractionRule;
	private Address lastParsedAddress;
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
	
	@Override
	public String toString() {
		return Objects.toString(format(
			"Company (\n"
		  + "id: %s\n"
		  + "name: %s\n"
		  + "url: %s\n"
		  + "addressExtractionRule: %s)\n"
				, Objects.toString(getId())
				, Objects.toString(getName())
				, Objects.toString(getUrl())
				, Objects.toString(getAddressExtractionRule())));
	}

}
