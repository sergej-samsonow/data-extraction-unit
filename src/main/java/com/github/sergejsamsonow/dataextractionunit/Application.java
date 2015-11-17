package com.github.sergejsamsonow.dataextractionunit;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
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

	private static Address extract(String source, String pattern) throws Exception {
		String loaded = content(source);
		Pattern compiled = Pattern.compile(pattern);
		Matcher matcher = compiled.matcher(loaded);
		Address address = new Address();
		if (matcher.find()) {
			address.setExtractionTime(new Date());
			address.setCompanyName(matcher.group("name"));
			address.setStreet(matcher.group("street"));
			address.setZip(matcher.group("zip"));
			address.setCity(matcher.group("city"));
		}
		return address;
	}

	public static void main(String ... args) throws Exception {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("default-persistence-unit");	
		EntityManager entityManager =  factory.createEntityManager();
		extractAndStoreAddresses(entityManager);
		List<Address> addresses = entityManager.createQuery("SELECT a FROM Address a", Address.class).getResultList();
		for (Address address : addresses) {
			System.out.println(address.getCompany().getUrl());
			System.out.println(address.getCompany().getAddressExtractionUnit().getExtractionClass());
			System.out.println(address.getCity());
			System.out.println(address.getExtractionTime());
			System.out.println(address.getZip());
			System.out.println(address.getStreet());
			System.out.println(address.getCompanyName());
			System.out.println(address.getId());
		}
		entityManager.close();
		factory.close();
	}

	private static void extractAndStoreAddresses(EntityManager entityManager) throws Exception {
		ApplicationDao dao = new ApplicationDao();
		dao.setEntityManager(entityManager);
		for (Company company : dao.getCompanies()) {
			dao.processParsedAddress(company, extract(company.getUrl(), company.getAddressExtractionRule()));
		}
	}
}
