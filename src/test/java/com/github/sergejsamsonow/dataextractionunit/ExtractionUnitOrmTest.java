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
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;

@Ignore
public class ExtractionUnitOrmTest {
	private static final String dataset = ""
	+ "<!DOCTYPE dataset SYSTEM \"dataset.dtd\">"
	+ "<dataset>"
		+ "<table name=\"extraction_units\">"
			+ "<column>id</column>"
			+ "<column>extraction_class</column>"
			+ "<row>"
				+ "<value>42</value>"
				+ "<value>XE</value>"
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
		ExtractionUnit unit = entityManager.find(ExtractionUnit.class, 42);
		assertThat(unit.getExtractionClass(), equalTo("XE"));
	}

	@Test
	public void storeEntry() {
		ExtractionUnit unit = new ExtractionUnit();
		unit.setExtractionClass("X");
		int oldId = unit.getId();
		entityManager.getTransaction().begin();
		entityManager.persist(unit);
		entityManager.getTransaction().commit();
		int stored = unit.getId();
		assertThat(stored, not(equalTo(oldId)));
	}

	@Test
	public void readStored() {
		String storedValue = "XB";
		ExtractionUnit unit = new ExtractionUnit();
		unit.setExtractionClass(storedValue);
		entityManager.getTransaction().begin();
		entityManager.persist(unit);
		entityManager.getTransaction().commit();

		unit = entityManager.find(ExtractionUnit.class, unit.getId());
		assertThat(unit.getExtractionClass(), equalTo(storedValue));
	}

}
