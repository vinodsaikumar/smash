package uk.co.smash.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;

public class CaptchaVerifier {

	private static final Logger LOG = Logger.getLogger(CaptchaVerifier.class.getName());

	private static final String CAPTCHA_URL = "captchaUrl";

	public boolean verify(String privateKey, String clientResponseKey) throws Exception {

		boolean status = false;
		Properties applicationProperties = CustomPropertyLoader.getInstance().getApplicationProperties();
		String captchaUrl = applicationProperties.getProperty(CaptchaVerifier.CAPTCHA_URL);
		String getUrl = MessageFormat.format(captchaUrl, privateKey, clientResponseKey);
		String captchaResponse = this.sendGet(getUrl);
		LOG.debug("The Json response from captcha google server is " + captchaResponse);
		JsonNode jsonObject = AppUtils.convertJsonStringToJsonObject(captchaResponse);
		status = jsonObject.get("success").asBoolean();
		return status;

	}

	private String sendGet(String url) throws Exception {

		StringBuffer response = new StringBuffer();
		BufferedReader bufferedReader = null;
		try {
			URL getUrl = new URL(url);
			HttpsURLConnection httpsURLConnection = (HttpsURLConnection) getUrl.openConnection();
			httpsURLConnection.setRequestMethod("GET");
			int responseCode = httpsURLConnection.getResponseCode();
			LOG.debug("Sending 'GET' request to URL : " + url);
			LOG.debug("Response Code : " + responseCode);
			bufferedReader = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
			String inputLine;
			while ((inputLine = bufferedReader.readLine()) != null) {
				response.append(inputLine);
			}
		} catch (MalformedURLException e) {
			LOG.error("Unable to format the captcha url ", e);
			throw e;
		} catch (ProtocolException e) {
			LOG.error("Unable to open a connection to captcha google server ", e);
			throw e;
		} catch (IOException e) {
			LOG.error("Unable to read the response from captcha server ", e);
			throw e;
		} finally {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				LOG.error("Unable to read the response from captcha server ", e);
				throw e;
			}
		}
		return response.toString();
	}

}
