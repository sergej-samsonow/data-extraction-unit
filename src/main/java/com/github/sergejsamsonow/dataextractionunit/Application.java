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
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		Properties properties = new Properties();
		properties.put("user", "sa");
		properties.put("password", "");
		Connection connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost:9001/data-extraction-unit", properties);
		ResultSet companies = connection.createStatement().executeQuery("select * from companies");
		ResultSetMetaData meta = companies.getMetaData();
		int columnCountMax = meta.getColumnCount() + 1;
		StringBuilder message = new StringBuilder();
		while (companies.next()) {
			int id = 0;
			String company = "";
			String url = "";
			String regex = "";
			for (int i = 1; i < columnCountMax; i++) {
				String name = meta.getColumnName(i);
				if ("URL".equals(name)) {
					url = (String) companies.getObject(i);
				}
				else if ("ADDRESS_EXTRACTION_RULE".equals(name)) {
					regex = (String) companies.getObject(i);
				}
				else if ("ID".equals(name)) {
					id = companies.getInt(i);
				}
				else if ("NAME".equals(name)) {
					company = (String) companies.getObject(i);
				}
			}
			extract(url, regex);
			message.append("New data for Company " + company + " with id " + id + "\n");
		}
		System.out.println("\n");
		System.out.println(message.toString());
		System.out.println("SEND MAIL");
		// Mail.send(message.toString());
	}
}
