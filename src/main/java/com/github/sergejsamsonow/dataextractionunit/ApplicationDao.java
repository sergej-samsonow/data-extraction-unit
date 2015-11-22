package com.github.sergejsamsonow.dataextractionunit;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ApplicationDao {
	
	public static final Logger logger = LogManager.getLogger(ApplicationDao.class);
	
	private EntityManager entityManager;
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public List<Company> getCompanies() {
		return entityManager.createQuery("SELECT c FROM Company c", Company.class).getResultList();
	}
	
	public void processParsedAddress(Company company, Address parsed) {

		logger.trace("START PROCESS PARSED ADDRESS");
		if (company == null || parsed == null) return;
		Address last = company.getLastParsedAddress();
		if (last != null && last.isTheSame(parsed)) return;

		logger.trace("STORE NEW ADDRESS");
		entityManager.getTransaction().begin();
		parsed.setCompany(company);
		entityManager.persist(parsed);
		entityManager.getTransaction().commit();

		logger.trace("UPDATE COMPANY LAST PARSED ADDRESS");
		entityManager.getTransaction().begin();
		company.setLastParsedAddress(parsed);
		entityManager.persist(company);
		entityManager.getTransaction().commit();
		logger.trace("PROCESS PARSED ADDRESS DONE");
	}
	
}
