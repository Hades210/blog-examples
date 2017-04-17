package net.tburne.api.integration.with.oauth;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Base64;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.Gmail.Builder;
import com.google.api.services.gmail.model.Message;

public class ClientRequestAPI {

	public static void main(String[] args) {
		try {
			String apiKey = "654832567742-6udg14sfuif69g84c78ss3s3msdlf33l.apps.googleusercontent.com";
			String apiSecret = "SDFwrgfsdSeQnFtSDFSDFzHd";
			String accessToken = "SDFABHheGgfaewFAWEFewfAEWfefaFEaeFsFAdsfdsfWfEWAenavsdsfdsvs";
			String refreshToken = "DSfSdfaweFawcfEWfaewFAfAfEWFsfawfESfaesfesGegawFEaeFAEfasx";
			HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
			JsonFactory jsonFactory = new JacksonFactory();
			final Credential credential = convertToGoogleCredential(accessToken, refreshToken, apiSecret, apiKey);
			Builder builder = new Gmail.Builder(httpTransport, jsonFactory, credential);
			builder.setApplicationName("OAuth API Sample");
			Gmail gmail = builder.build();
			MimeMessage content = createEmail("inactive@tburne.net", "fromsomeone@tburne.net", "Test Email", "It works");
			Message message = createMessageWithEmail(content);
			gmail.users().messages().send("fromsomeone@tburne.net", message).execute();
		} catch (GeneralSecurityException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static Credential convertToGoogleCredential(String accessToken, String refreshToken, String apiSecret, String apiKey) {
		HttpTransport httpTransport = new NetHttpTransport();
		JsonFactory jsonFactory = new JacksonFactory();
		GoogleCredential credential = new GoogleCredential.Builder().setTransport(httpTransport).setJsonFactory(jsonFactory).setClientSecrets(apiKey, apiSecret).build();
		credential.setAccessToken(accessToken);
		credential.setRefreshToken(refreshToken);
		try {
			credential.refreshToken();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return credential;
	}
	
	private static MimeMessage createEmail(String to, String from, String subject, String bodyText) throws MessagingException {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		MimeMessage email = new MimeMessage(session);
		email.setFrom(new InternetAddress(from));
		email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
		email.setSubject(subject);
		email.setText(bodyText);
		return email;
	}

	private static Message createMessageWithEmail(MimeMessage emailContent) throws IOException, MessagingException  {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		emailContent.writeTo(buffer);
		byte[] bytes = buffer.toByteArray();
		String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
		Message message = new Message();
		message.setRaw(encodedEmail);
		return message;
	}


	
}
