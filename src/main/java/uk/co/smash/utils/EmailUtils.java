package uk.co.smash.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.net.ssl.HttpsURLConnection;

import org.apache.log4j.Logger;

import uk.co.smash.model.EmailDataModel;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.mail.smtp.SMTPTransport;

public class EmailUtils {

	private static final Logger LOG = Logger.getLogger(EmailUtils.class.getName());
	
	private static final String REFRESH_AUTH_TOKEN_URL = "refreshOauthTokenUrl";
	private static final String REFRESH_AUTH_TOKEN_PARAMETERS = "refreshOauthTokenParameters";
	private static final String CLIENT_SECRET = "client_secret";
	private static final String CLIENT_ID = "client_id";
	private static final String REFRESH_TOKEN = "refreshToken";
	
	public static void sendTextEmail(EmailDataModel emailDataModel) throws Exception {

		Properties properties = new Properties();
		Session session = Session.getDefaultInstance(properties, null);
		OAuth2Authenticator.initialize();
		String oauthToken = EmailUtils.refreshOauthToken();
		SMTPTransport smtpTransport = OAuth2Authenticator.connectToSmtp(emailDataModel.getSmtpServerHost(), emailDataModel.getSmtpServerOutgoingPort(), emailDataModel.getFrom(), oauthToken);
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(emailDataModel.getFrom()));
			message.setSubject(emailDataModel.getSubject());
			message.setText(emailDataModel.getMessage());
			smtpTransport.sendMessage(message, InternetAddress.parse(emailDataModel.getTo()));
			LOG.debug("Email sent to admin successfully");
		} catch (MessagingException mex) {
			LOG.error("Error while sending email " + mex);
			throw mex;
		}
	}
	
	private static String refreshOauthToken() throws Exception{
		
		Properties applicationProperties = CustomPropertyLoader.getInstance().getApplicationProperties();
		String refreshOauthTokenUrl = applicationProperties.getProperty(EmailUtils.REFRESH_AUTH_TOKEN_URL);
		String refreshOauthTokenParameters = applicationProperties.getProperty(EmailUtils.REFRESH_AUTH_TOKEN_PARAMETERS);
		String refreshToken = applicationProperties.getProperty(EmailUtils.REFRESH_TOKEN);
		String clientId = applicationProperties.getProperty(EmailUtils.CLIENT_ID);
		String clientSecret = applicationProperties.getProperty(EmailUtils.CLIENT_SECRET);
		String urlParameters = MessageFormat.format(refreshOauthTokenParameters, refreshToken, clientId, clientSecret);
		String authTokenResponse = sendPost(refreshOauthTokenUrl, urlParameters);
		JsonNode jsonObject = AppUtils.convertJsonStringToJsonObject(authTokenResponse);
		String authToken = jsonObject.get("access_token").asText();
		return authToken;
	}
	
	
	private static String sendPost(String url, String urlParameters) throws Exception {

		BufferedReader bufferedReader = null;
		StringBuffer response = new StringBuffer();
		URL postUrl;
		try {
			postUrl = new URL(url);
			HttpsURLConnection httpsURLConnection = (HttpsURLConnection) postUrl.openConnection();
			httpsURLConnection.setRequestMethod("POST");
			httpsURLConnection.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(httpsURLConnection.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
			int responseCode = httpsURLConnection.getResponseCode();
			LOG.info("Response code of auth token refresh " + responseCode);
			bufferedReader = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
			String inputLine;
			while ((inputLine = bufferedReader.readLine()) != null) {
				response.append(inputLine);
			}
		} catch (MalformedURLException e) {
			LOG.error("Error while refreshing the auth token", e);
			throw e;
		} catch (IOException e) {
			LOG.error("Error while refreshing the auth token", e);
			throw e;
		}
		LOG.debug("The response from google after token refresh " + response.toString());
		return response.toString();

	}
}
