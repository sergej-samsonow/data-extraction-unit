package com.github.sergejsamsonow.dataextractionunit;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Application {

	public static void main(String ... args) throws Exception {
		// TODO Read data from expected url
		URL url = new URL("http://www.regis24.de/impressum.php");
		URLConnection openConnection = url.openConnection();
		try (InputStream inputStream = openConnection.getInputStream()) {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				System.out.println(line);
				System.out.println("\n");
			}
		}

	}
}
