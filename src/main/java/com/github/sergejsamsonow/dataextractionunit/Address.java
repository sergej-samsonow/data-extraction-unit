package com.github.sergejsamsonow.dataextractionunit;

import static java.lang.String.format;

import java.util.Date;
import java.util.Objects;

public class Address {
	private int id;
	private Date extractionTime;
	private Company company;
	private String companyName;
	private String street;
	private String zip;
	private String city;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Date getExtractionTime() {
		return extractionTime;
	}

	public void setExtractionTime(Date extractionTime) {
		this.extractionTime = extractionTime;
	}

	public String getCompanyName() {
		return companyName == null ? "" : companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getStreet() {
		return street == null ? "" : street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZip() {
		return zip == null ? "" : zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCity() {
		return city == null ? "" : city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public boolean isEmpty() {
		return getCompanyName().isEmpty() && getStreet().isEmpty() && getZip().isEmpty() && getCity().isEmpty();
	}

	public boolean isTheSame(Address address) {
		if (address == null)
			return false;
		return Objects.equals(getCompanyName(), address.getCompanyName())
				&& Objects.equals(getStreet(), address.getStreet()) && Objects.equals(getZip(), address.getZip())
				&& Objects.equals(getCity(), address.getCity());
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Address)) {
			return false;
		}
		Address b = (Address) obj;
		return isTheSame(b) && Objects.equals(getId(), b.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getCompanyName(), getStreet(), getZip(), getCity());
	}

	@Override
	public String toString() {
		return Objects.toString(format(
		    "Address (\n" 
		  + "id: %s\n" 
		  + "companyName: %s\n"
		  + "street: %s\n"
		  + "zip: %s\n"
		  + "city: %s)\n",
		  Objects.toString(getId()), 
		  Objects.toString(getCompanyName()), 
		  Objects.toString(getStreet()),
		  Objects.toString(getZip()),
		  Objects.toString(getCity())));
	}
}
