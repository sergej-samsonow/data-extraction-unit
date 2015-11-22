package com.github.sergejsamsonow.dataextractionunit;


import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationDaoTest {
	
	@Mock
	private EntityManager manager;
	
	@Mock
	private EntityTransaction transaction;
	
	@Mock
	private Company company;
	
	@Mock
	private Address address;
	
	@Mock
	private Address parsed;

	@Mock
	private TypedQuery<Company> resultQuery;
	
	@Mock
	private List<Company> result;
	
	private ApplicationDao object;

	@Before
	public void prepare() {
		when(manager.getTransaction()).thenReturn(transaction);
		when(resultQuery.getResultList()).thenReturn(result);
		object = new ApplicationDao();
		object.setEntityManager(manager);
	}

	@Test
	public void getCompanies() {
		when(manager.createQuery("SELECT c FROM Company c", Company.class)).thenReturn(resultQuery);
		assertThat(object.getCompanies(), equalTo(result));
	}

	@Test
	public void processParsedAddressIsTheSameCalled() {
		when(company.getLastParsedAddress()).thenReturn(address);
		object.processParsedAddress(company, parsed);
		verify(address).isTheSame(parsed);
	}

	@Test
	public void processParsedAddressCompanyNull() {
		object.processParsedAddress(null, parsed);
	}
	
	@Test
	public void processParsedAddressParsedNull() {
		object.processParsedAddress(company, null);
	}

	@Test
	public void processParsedLastParsedIsNull() {
		when(company.getLastParsedAddress()).thenReturn(null);
		object.processParsedAddress(company, parsed);
	}

	@Test
	public void processParsedAddressIsNotTheSame() {
		when(company.getLastParsedAddress()).thenReturn(address);
		object.processParsedAddress(company, parsed);
		verify(address).isTheSame(parsed);
	}

	@Test
	public void processParsedLastIsNotTheSame() {
		when(company.getLastParsedAddress()).thenReturn(address);
		when(address.isTheSame(parsed)).thenReturn(false);
		object.processParsedAddress(company, parsed);
		verify(parsed).setCompany(company);
		verify(company).setLastParsedAddress(parsed);
	}

	@Test
	public void processParsedLastIsTheSame() {
		when(company.getLastParsedAddress()).thenReturn(address);
		when(address.isTheSame(parsed)).thenReturn(true);
		object.processParsedAddress(company, parsed);
		verify(parsed, never()).setCompany(company);;
		verify(company, never()).setLastParsedAddress(parsed);;
	}

	@Test
	public void processParsedLastStoreData() {
		when(company.getLastParsedAddress()).thenReturn(address);
		when(address.isTheSame(parsed)).thenReturn(false);
		object.processParsedAddress(company, parsed);
		InOrder order = inOrder(transaction, manager, company, parsed);
		order.verify(transaction).begin();
		order.verify(parsed).setCompany(company);
		order.verify(manager).persist(parsed);
		order.verify(transaction).commit();

		order.verify(transaction).begin();
		order.verify(company).setLastParsedAddress(parsed);
		order.verify(manager).persist(company);
		order.verify(transaction).commit();
	}
}
