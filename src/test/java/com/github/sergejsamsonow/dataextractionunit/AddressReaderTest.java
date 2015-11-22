package com.github.sergejsamsonow.dataextractionunit;

import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.github.sergejsamsonow.testable.regex.Matcher;
import com.github.sergejsamsonow.testable.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.doCallRealMethod;

@RunWith(MockitoJUnitRunner.class)
public class AddressReaderTest {

	private static final String GOOGLE_URL = "http://www.google.de";
	private static final String PATTERN = "some_pattern";
	private static final String RESPONSE_CONTENT = "X";

	private static final String NAME = "Name";
	private static final String STREET = "Street";
	private static final String ZIP = "Zip";
	private static final String CITY = "City";

	@Mock
	private Request request;

	@Mock
	private Response response;

	@Mock
	private Matcher matcher;

	@Mock
	private Pattern pattern;

	@Mock
	private Content content;
	
	@Mock
	private Address address;

	private AddressReader object;

	@Before
	public void prepare() throws Exception {
		object = spy(new AddressReader());
		when(pattern.matcher(RESPONSE_CONTENT)).thenReturn(matcher);
		when(request.execute()).thenReturn(response);
		when(response.returnContent()).thenReturn(content);
		when(content.asString()).thenReturn(RESPONSE_CONTENT);
		when(object.request(GOOGLE_URL)).thenReturn(request);
		when(object.pattern(PATTERN)).thenReturn(pattern);
		when(object.address()).thenReturn(address);
		when(matcher.find()).thenReturn(true);

		when(matcher.group("name")).thenReturn(NAME);
		when(matcher.group("street")).thenReturn(STREET);
		when(matcher.group("zip")).thenReturn(ZIP);
		when(matcher.group("city")).thenReturn(CITY);
	}

	@Test
	public void requestCalled() throws Exception {
		object.read(GOOGLE_URL, PATTERN);
		verify(object).request(GOOGLE_URL);
	}

	@Test
	public void requestValue() throws Exception {
		doCallRealMethod().when(object).request(GOOGLE_URL);
		assertThat(object.request(GOOGLE_URL), not(nullValue()));
	}

	@Test
	public void checkFluentApi() throws Exception {
		object.read(GOOGLE_URL, PATTERN);
		InOrder inOrder = inOrder(request, response, content);
		inOrder.verify(request).execute();
		inOrder.verify(response).returnContent();
		inOrder.verify(content).asString();
	}
	
	@Test 
	public void patternCalled() throws Exception {
		object.read(GOOGLE_URL, PATTERN);
		verify(object).pattern(PATTERN);
	}

	@Test
	public void patternValue() throws Exception {
		doCallRealMethod().when(object).pattern(".*");
		assertThat(object.pattern(GOOGLE_URL), not(nullValue()));
	}

	@Test
	public void matcherCheckAndLoadAllGroups() throws Exception {
		object.read(GOOGLE_URL, PATTERN);
		InOrder inOrder = inOrder(matcher);
		inOrder.verify(matcher).find();
		inOrder.verify(matcher).group("name");
		inOrder.verify(matcher).group("street");
		inOrder.verify(matcher).group("zip");
		inOrder.verify(matcher).group("city");
	}
	
	@Test
	public void matcherCheckFoundNothing() throws Exception {
		when(matcher.find()).thenReturn(false);
		object.read(GOOGLE_URL, PATTERN);
		verify(matcher).find();
		verify(matcher, never()).group("name");
		verify(matcher, never()).group("street");
		verify(matcher, never()).group("zip");
		verify(matcher, never()).group("city");
	}

	@Test
	public void address() throws Exception {
		object.read(GOOGLE_URL, PATTERN);
		verify(object).address();
	}

	@Test
	public void addressValue() throws Exception {
		doCallRealMethod().when(object).address();
		assertThat(object.address(), not(nullValue()));
	}

	@Test
	public void addressDataPassed() throws Exception {
		object.read(GOOGLE_URL, PATTERN);
		verify(address).setCompanyName(NAME);
		verify(address).setStreet(STREET);
		verify(address).setCity(CITY);
		verify(address).setZip(ZIP);
	}

	@Test
	public void read() throws Exception {
		doCallRealMethod().when(object).address();
		Address otherAddress = new Address();
		otherAddress.setCompanyName(NAME);
		otherAddress.setStreet(STREET);
		otherAddress.setCity(CITY);
		otherAddress.setZip(ZIP);
		assertThat(otherAddress.isTheSame(object.read(GOOGLE_URL, PATTERN)), equalTo(true));
	}
	
}
