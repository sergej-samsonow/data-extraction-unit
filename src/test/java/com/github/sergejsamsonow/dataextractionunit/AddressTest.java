package com.github.sergejsamsonow.dataextractionunit;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertFalse;;

public class AddressTest {

	private Address a;
	private Address b;

	@Before
	public void prepare() {
		a = new Address();
		b = new Address();
	}

	@Test
	public void equals() throws Exception {
		a.setCompanyName("Company");
		a.setStreet("street");
		a.setZip("zip");
		a.setCity("city");

		b.setCompanyName("Company");
		b.setStreet("street");
		b.setZip("zip");
		b.setCity("city");

		assertThat(a, equalTo(b));
	}

	@Test
	public void notEquals() throws Exception {
		a.setCompanyName("Company a");
		a.setStreet("street a");
		a.setZip("zip a ");
		a.setCity("city a");

		b.setCompanyName("Company");
		b.setStreet("street");
		b.setZip("zip");
		b.setCity("city");

		assertThat(a, not(equalTo(b)));
	}

	@Test
	public void notEqualsDifferntType() throws Exception {
		a.setCompanyName("Company a");
		a.setStreet("street a");
		a.setZip("zip a ");
		a.setCity("city a");

		String other = "String type";
		assertThat(a, not(equalTo(other)));
	}

	@Test
	public void notEqualsNullValue() throws Exception {
		a.setCompanyName("Company a");
		a.setStreet("street a");
		a.setZip("zip a ");
		a.setCity("city a");

		assertFalse(a.equals(null));
	}

	@Test
	public void hashCodeNotNull() throws Exception {
		a.setCompanyName("Company a");
		a.setStreet("street a");
		a.setZip("zip a ");
		a.setCity("city a");
		assertThat(a.hashCode(), not(equalTo(0)));
	}


	@Test
	public void differentHashCode() throws Exception {
		a.setCompanyName("Company a");
		a.setStreet("street a");
		a.setZip("zip a ");
		a.setCity("city a");

		b.setCompanyName("Company");
		b.setStreet("street");
		b.setZip("zip");
		b.setCity("city");
		assertThat(a.hashCode(), not(equalTo(b.hashCode())));
	}
}
