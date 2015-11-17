package com.github.sergejsamsonow.dataextractionunit;

import java.io.ByteArrayInputStream;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.dbunit.AbstractDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.operation.DatabaseOperation;

public class TestDataSource {

	public static EntityManagerFactory entityManagerFactory() {
		return Persistence.createEntityManagerFactory("default-persistence-unit");
	}

	public static AbstractDatabaseTester dataTester() throws Exception {
		return new JdbcDatabaseTester(
			"org.hsqldb.jdbcDriver",
			"jdbc:hsqldb:hsql://localhost:9001/data-extraction-unit",
			"sa", "");
	}

	public static AbstractDatabaseTester dataTester(String dataset) throws Exception {
		AbstractDatabaseTester tester = dataTester();
		tester.setDataSet(new XmlDataSet(new ByteArrayInputStream(dataset.getBytes())));
		return tester;
	}
	
	public static AbstractDatabaseTester dataTester(DatabaseOperation ops, String dataset) throws Exception {
		AbstractDatabaseTester tester = dataTester(dataset);
		tester.setSetUpOperation(ops);
		return tester;
	}
}
