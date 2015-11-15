package com.github.sergejsamsonow.dataextractionunit;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Application {

	private static String regisRegex = "" 
			+ ".*?contentText\"\\s*>\\s*"
			+ "<\\s*p\\s*>\\s*(?<name>.*)\\s*"
			+ "<\\s*br\\s*/\\s*>\\s*(?<street>.*)\\s*"
			+ "<\\s*br\\s*/\\s*>\\s*(?<zip>\\d{5})\\s*"
			+ "(?<city>.*?)</\\s*p>.*";

	public static void main(String ... args) throws Exception {
		String source = "http://www.regis24.de/impressum.php";
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
		
		// TODO Extract data from url.
		// 1 company name.
		// 2 address
		// 3 address (additional)
		// 4 city
		// 5 zip
		
		String loaded = builder.toString();
		Pattern pattern = Pattern.compile(regisRegex);
		Matcher matcher = pattern.matcher(loaded);
		if (matcher.find()) {
			System.out.println("Source " + source);
			System.out.println("    name   : " + matcher.group("name"));
			System.out.println("    street : " + matcher.group("street"));
			System.out.println("    zip    : " + matcher.group("zip"));
			System.out.println("    city   : " + matcher.group("city"));
		}


	}
}
