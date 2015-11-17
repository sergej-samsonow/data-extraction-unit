package com.github.sergejsamsonow.dataextractionunit;

import static org.dbunit.operation.DatabaseOperation.CLEAN_INSERT;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.dbunit.AbstractDatabaseTester;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class CompanyOrmTest {
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
		+ "<table name=\"extraction_units\">"
			+ "<column>id</column>"
			+ "<column>extraction_class</column>"
			+ "<row>"
				+ "<value>1</value>"
				+ "<value>BasicExtractor</value>"
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
				+ "<value>1</value>"
				+ "<value>1</value>"
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
	public void readEntry() {
		Company company = entityManager.find(Company.class, 1);
		assertThat(company.getName(), equalTo("company 1"));
		assertThat(company.getAddressExtractionRule(), equalTo("rule"));
		assertThat(company.getUrl(), equalTo("company url"));
		assertThat(company.getAddressExtractionUnit().getExtractionClass(),
				equalTo("BasicExtractor"));
		assertThat(company.getAddress().getCity(), equalTo("some city"));
		assertThat(company.getLastParsedAddress().getCompanyName(), 
				equalTo("some company"));
	}
	
	@Test
	public void storeEnty() {
		Address address = entityManager.find(Address.class, 1);
		ExtractionUnit unit = entityManager.find(ExtractionUnit.class, 1);
		String name = "some company name";
		String url = "some company url";
		String rule = "extraction rule";
		Company company = new Company();
		company.setAddress(address);
		company.setLastParsedAddress(address);
		company.setAddressExtractionUnit(unit);
		company.setName(name);
		company.setUrl(url);
		company.setAddressExtractionRule(rule);
		entityManager.getTransaction().begin();
		entityManager.persist(company);
		entityManager.getTransaction().commit();
		company = entityManager.find(Company.class, company.getId());
		assertThat(company.getName(), equalTo(name));
		assertThat(company.getUrl(), equalTo(url));
		assertThat(company.getAddressExtractionRule(), equalTo(rule));
	}
}
