package com.github.sergejsamsonow.dataextractionunit;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Application {
	
	private static String content(String source) throws Exception {
		// TODO Handle redirects to https
		URL url = new URL(source);
		URLConnection openConnection = url.openConnection();
		StringBuilder builder = new StringBuilder();
		try (InputStream inputStream = openConnection.getInputStream()) {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				builder.append(line);
			}
		}
		return builder.toString();
	}

	private static void extract(String source, String pattern) throws Exception {
		String loaded = content(source);
		Pattern compiled = Pattern.compile(pattern);
		Matcher matcher = compiled.matcher(loaded);
		if (matcher.find()) {
			System.out.println("Source " + source);
			System.out.println("    name   : " + matcher.group("name"));
			System.out.println("    street : " + matcher.group("street"));
			System.out.println("    zip    : " + matcher.group("zip"));
			System.out.println("    city   : " + matcher.group("city"));
		}
	}

	public static void main(String ... args) throws Exception {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("default-persistence-unit");	
		EntityManager entityManager =  factory.createEntityManager();
		List<Company> resultList = entityManager.createQuery("SELECT c FROM Company c", Company.class).getResultList();
		for (Company company : resultList) {
			extract(company.getUrl(), company.getAddressExtractionRule());
		}
		entityManager.close();
		factory.close();
	}
}
