package com.github.sergejsamsonow.dataextractionunit;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {
	public static void send(String message) throws Exception {

		Properties properties = new Properties();
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");

		Session session = Session.getDefaultInstance(properties);
		MimeMessage mime = new MimeMessage(session);
		mime.addRecipient(Message.RecipientType.TO, new InternetAddress("xxx"));
		mime.setSubject("Address updates");
		mime.setContent(message, "text/plain");
		
		Transport transport = session.getTransport("smtp");
		transport.connect("xxx", "user", "password");
		transport.sendMessage(mime, mime.getAllRecipients());
		transport.close();
	}
}
