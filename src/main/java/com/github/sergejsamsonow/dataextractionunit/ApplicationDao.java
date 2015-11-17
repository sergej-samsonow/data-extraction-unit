package com.github.sergejsamsonow.dataextractionunit;

import java.util.List;

import javax.persistence.EntityManager;

public class ApplicationDao {
	
	private EntityManager entityManager;
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public List<Company> getCompanies() {
		return entityManager.createQuery("SELECT c FROM Company c", Company.class).getResultList();
	}
	
	public void processParsedAddress(Company company, Address parsed) {
		if (company == null || parsed == null) return;
		Address last = company.getLastParsedAddress();
		if (last != null && last.isTheSame(parsed)) return;
		parsed.setCompany(company);
		company.setLastParsedAddress(parsed);
		entityManager.getTransaction().begin();
		entityManager.persist(parsed);
		entityManager.persist(company);
		entityManager.getTransaction().commit();
	}
	
}
