package com.github.sergejsamsonow.dataextractionunit;

import java.io.IOException;
import org.apache.http.client.fluent.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.sergejsamsonow.testable.regex.Matcher;
import com.github.sergejsamsonow.testable.regex.Pattern;
import com.github.sergejsamsonow.testable.regex.PatternInstance;

public class AddressReader {

	public static final Logger logger = LogManager.getLogger(AddressReader.class);

	public Request request(String url) {
		return Request.Get(url);
	}

	public Pattern pattern(String pattern) {
		return new PatternInstance(pattern, java.util.regex.Pattern.DOTALL);
	}
	
	public Address address() {
		return new Address();
	}

	public Address read(String url, String pattern) throws IOException {
		logger.trace("READ URL: {} PATTERN: {} ", url, pattern);
		
		String content = request(url).execute().returnContent().asString();
		Matcher matcher = pattern(pattern).matcher(content);
		Address address = address();
		if (matcher.find()) {
			logger.trace("PATTERN PASS");
			address.setCompanyName(matcher.group("name"));
			address.setStreet(matcher.group("street"));
			address.setZip(matcher.group("zip"));
			address.setCity(matcher.group("city"));
		}
		else {
			logger.trace("PATTERN OUTDATED FOR CONTENT");
			logger.trace(content);
			logger.trace("CONTENT END");
		}
		return address;
	}

}
