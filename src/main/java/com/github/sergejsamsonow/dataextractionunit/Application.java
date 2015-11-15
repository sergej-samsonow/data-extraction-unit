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

	private static String moebus1 = ""
			+ ".*id=\"c175\""
			+ "\\s*.*?strong\\s*>\\s*(?<name>.*?)"
			+ "\\s*<\\s*/\\s*strong\\s*>.*?/\\s*strong.*?<\\s*br\\s*/?\\s*>"
			+ "\\s*(?<street>.*?)\\s*,"
			+ "\\s*(?<zip>\\d{5})"
			+ "\\s*(?<city>.*?)<.*?";	

	private static String moebus2 = ""
			+ ".*id=\"c176\""
			+ "\\s*.*?strong\\s*>\\s*(?<name>.*?)"
			+ "\\s*<\\s*/\\s*strong\\s*>.*?/\\s*strong.*?<\\s*br\\s*/?\\s*>"
			+ "\\s*(?<street>.*?)\\s*,"
			+ "\\s*(?<zip>\\d{5})"
			+ "\\s*(?<city>.*?)<.*?";	
	
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
		// extract("http://www.regis24.de/impressum.php", regisRegex);
		// FIXME work not System.out.println(content("http://www.savage-wear.com/impressum/index.html"));
		// FIXME work not System.out.println(content("http://www.idealo.de/preisvergleich/AGB.html"));
		extract("http://www.regis24.de/impressum.php", regisRegex);
		extract("http://www.moebus-gruppe.de/impressum.html", moebus1);
		extract("http://www.moebus-gruppe.de/impressum.html", moebus2);
	}
}
