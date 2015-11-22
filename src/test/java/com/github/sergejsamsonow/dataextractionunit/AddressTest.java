package com.github.sergejsamsonow.dataextractionunit;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;

public class AddressTest {

	private Address a;
	private Address b;

	@Before
	public void prepare() {
		a = new Address();
		b = new Address();
	}

	@Test
	public void isTheSame() throws Exception {
		a.setCompanyName("Company");
		a.setStreet("street");
		a.setZip("zip");
		a.setCity("city");

		b.setCompanyName("Company");
		b.setStreet("street");
		b.setZip("zip");
		b.setCity("city");

		assertTrue(a.isTheSame(b));
	}

	@Test
	public void isNotTheSame() throws Exception {
		a.setCompanyName("Company a");
		a.setStreet("street a");
		a.setZip("zip a ");
		a.setCity("city a");

		b.setCompanyName("Company");
		b.setStreet("street");
		b.setZip("zip");
		b.setCity("city");

		assertFalse(a.isTheSame(b));
	}

	@Test
	public void isNotTheSameBecauseNull() throws Exception {
		a.setCompanyName("Company a");
		a.setStreet("street a");
		a.setZip("zip a ");
		a.setCity("city a");

		assertFalse(a.isTheSame(null));
	}

	@Test
	public void equals() throws Exception {
		a.setId(1);
		a.setCompanyName("Company");
		a.setStreet("street");
		a.setZip("zip");
		a.setCity("city");

		b.setId(1);
		b.setCompanyName("Company");
		b.setStreet("street");
		b.setZip("zip");
		b.setCity("city");

		assertThat(a, equalTo(b));
	}

	@Test
	public void equalsWithId() throws Exception {
		a.setId(1);
		a.setCompanyName("Company");
		a.setStreet("street");
		a.setZip("zip");
		a.setCity("city");

		b.setId(2);
		b.setCompanyName("Company");
		b.setStreet("street");
		b.setZip("zip");
		b.setCity("city");

		assertThat(a, not(equalTo(b)));
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
	public void hashCodeWithId() throws Exception {
		a.setId(1);
		a.setCompanyName("Company a");
		a.setStreet("street a");
		a.setZip("zip a ");
		a.setCity("city a");
		int hashOne = a.hashCode();
		a.setId(2);
		int hashTwo = a.hashCode();
		assertThat(hashOne, not(equalTo(hashTwo)));
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

	@Test
	public void isEmptyNullString() throws Exception {
		a.setCompanyName(null);
		a.setStreet(null);
		a.setZip(null);
		a.setCity(null);
		assertThat(a.isEmpty(), equalTo(true));
	}

	@Test
	public void isEmptyEmptyString() throws Exception {
		a.setCompanyName("");
		a.setStreet("");
		a.setZip("");
		a.setCity("");
		assertThat(a.isEmpty(), equalTo(true));
	}

	@Test
	public void isNotEmpty() throws Exception {
		a.setCompanyName("");
		a.setStreet("some value");
		a.setZip("");
		a.setCity("");
		assertThat(a.isEmpty(), equalTo(false));
	}

}
