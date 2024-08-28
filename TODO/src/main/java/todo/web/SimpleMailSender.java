package todo.web;

import java.util.Date;
import java.util.Properties;

import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class SimpleMailSender {

	private static final String SMTP_HOST = "127.0.0.1";
	private static final String SMTP_PORT = "25";
	private static final String AUTH_USER_NAME = "todo_server";
	private static final String AUTH_PASSWORD = "todopass";

	public void sendMessage(String toAddr, String fromAddr, String personName, String subject, String message)
			throws Exception {
		Properties props = new Properties();
		props.put("mail.smtp.host", SMTP_HOST);
		props.put("mail.host", SMTP_HOST);
		props.put("mail.smtp.port", SMTP_PORT);
		props.put("mail.from", fromAddr);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.socketFactory.port", SMTP_PORT);
		props.put("mail.smtp.socketFactory.fallback", String.valueOf(false));
		
		Session session = Session.getInstance(props,
				new jakarta.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(AUTH_USER_NAME, AUTH_PASSWORD);
			}
		});
		
		MimeMessage mimeMsg = new MimeMessage(session);
		mimeMsg.setRecipients(Message.RecipientType.TO, toAddr);
		InternetAddress fromHeader = new InternetAddress(fromAddr, personName);
		
		mimeMsg.setFrom(fromHeader);
		mimeMsg.setSubject(subject, "ISO-2022-JP");;
		mimeMsg.setSentDate(new Date());
		mimeMsg.setText(message, "ISO-2022-JP");
		
		Transport.send(mimeMsg);
		
	}
}
