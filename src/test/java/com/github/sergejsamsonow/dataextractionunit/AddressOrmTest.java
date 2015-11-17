package com.github.sergejsamsonow.dataextractionunit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.dbunit.AbstractDatabaseTester;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.dbunit.operation.DatabaseOperation.CLEAN_INSERT;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;

@Ignore
public class AddressOrmTest {
	private static final String dataset = ""
	+ "<!DOCTYPE dataset SYSTEM \"dataset.dtd\">"
	+ "<dataset>"
		+ "<table name=\"addresses\">"
			+ "<column>id</column>"
			+ "<column>company_id</column>"
			+ "<column>extraction_time</column>"
			+ "<column>company_name</column>"
			+ "<column>street</column>"
			+ "<column>zip</column>"
			+ "<column>city</column>"
			+ "<row>"
				+ "<value>1</value>"
				+ "<value>1</value>"
				+ "<value>2015-11-17 11:57:11</value>"
				+ "<value>some company</value>"
				+ "<value>street 1</value>"
				+ "<value>11111</value>"
				+ "<value>some city</value>"
			+ "</row>"
		+ "</table>"
		+ "<table name=\"companies\">"
			+ "<column>id</column>"
			+ "<column>name</column>"
			+ "<column>url</column>"
			+ "<column>address_extraction_rule</column>"
			+ "<column>address_extraction_unit_id</column>"
			+ "<column>last_parsed_address_id</column>"
			+ "<column>current_address_id</column>"
			+ "<row>"
				+ "<value>1</value>"
				+ "<value>company 1</value>"
				+ "<value>company url</value>"
				+ "<value>rule</value>"
				+ "<value>1</value>"
				+ "<null />"
				+ "<null />"
			+ "</row>"
		+ "</table>"
	+ "</dataset>";
	private static EntityManagerFactory factory;
	private static AbstractDatabaseTester dbUnit;
	private EntityManager entityManager;

	@BeforeClass
	public static void configure() throws Exception {
		dbUnit = TestDataSource.dataTester(CLEAN_INSERT, dataset);
		factory = TestDataSource.entityManagerFactory();
	}

	@AfterClass
	public static void close() throws Exception {
		factory.close();
	}

	@Before
	public void prepare() throws Exception {
		dbUnit.onSetup();
		entityManager = factory.createEntityManager();
	}

	@After
	public void cleanup() throws Exception {
		entityManager.close();
		dbUnit.onTearDown();
	}
	
	@Test
	public void storeEntry() {
		Company company = entityManager.find(Company.class, 1);
		String name = "New company";
		String street = "New street";
		String zip = "22222";
		String city = "New city";
		Address address = new Address();
		address.setCompany(company);
		address.setCompanyName(name);
		address.setExtractionTime(new Date());
		address.setStreet(street);
		address.setZip(zip);
		address.setCity(city);
		entityManager.getTransaction().begin();
		entityManager.persist(address);
		entityManager.getTransaction().commit();
		address = entityManager.find(Address.class, address.getId());
		assertThat(address.getCompanyName(), equalTo(name));
		assertThat(address.getStreet(), equalTo(street));
		assertThat(address.getZip(), equalTo(zip));
		assertThat(address.getCity(), equalTo(city));
	}

	@Test
	public void readEntry() {
		Address address = entityManager.find(Address.class, 1);
		assertThat(address.getCompanyName(), equalTo("some company"));
		assertThat(address.getStreet(), equalTo("street 1"));
		assertThat(address.getZip(), equalTo("11111"));
		assertThat(address.getCity(), equalTo("some city"));
		assertThat(address.getCompany().getUrl(), equalTo("company url"));
	}
}
