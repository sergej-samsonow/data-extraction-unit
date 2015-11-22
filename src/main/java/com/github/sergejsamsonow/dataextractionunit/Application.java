package com.github.sergejsamsonow.dataextractionunit;


import java.util.Date;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class Application {

	public static void main(String ... args) throws Exception {
		
		AddressReader reader = new AddressReader();
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("default-persistence-unit");	
		ApplicationDao dao = new ApplicationDao();
		dao.setEntityManager(factory.createEntityManager());
		for (Company company : dao.getCompanies()) {
			Address address = reader.read(company.getUrl(), company.getAddressExtractionRule());
			address.setExtractionTime(new Date());
			dao.processParsedAddress(company, address);
		}
		factory.close();
	}
}
