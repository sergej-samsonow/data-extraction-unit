package com.github.sergejsamsonow.dataextractionunit;

import java.util.Objects;

public class Address {

	private String companyName;
	private String street;
	private String zip;
	private String city;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Override
	public boolean equals(Object obj) {
		if ( !(obj instanceof Address)) {
			return false;
		}
		Address b = (Address) obj;
		return Objects.equals(getCompanyName(), b.getCompanyName())
			&& Objects.equals(getStreet(), b.getStreet())
			&& Objects.equals(getZip(), b.getZip())
			&& Objects.equals(getCity(), b.getCity());
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			getCompanyName(), getStreet(), getZip(), getCity()
		);
	}

}
