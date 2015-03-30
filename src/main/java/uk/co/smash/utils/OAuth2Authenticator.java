/* Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.co.smash.utils;

import java.security.Provider;
import java.security.Security;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.URLName;

import org.apache.log4j.Logger;

import com.sun.mail.smtp.SMTPTransport;

public class OAuth2Authenticator {
	
	private static final Logger LOG = Logger.getLogger(OAuth2Authenticator.class.getName());

	public static final class OAuth2Provider extends Provider {

		private static final long serialVersionUID = 1L;

		public static final String GOOGLE_PROVIDER_NAME = "Google OAuth2 Provider";

		public OAuth2Provider() {

			super(OAuth2Provider.GOOGLE_PROVIDER_NAME, 1.0, "Provides the XOAUTH2 SASL Mechanism");
			put("SaslClientFactory.XOAUTH2", "uk.co.smash.utils.OAuth2SaslClientFactory");
		}
	}

	public static void initialize() {

		Provider googleProvider = Security.getProvider(OAuth2Provider.GOOGLE_PROVIDER_NAME);
		if (googleProvider == null) {
			LOG.debug("Added the OAuth provider provider");
			Security.addProvider(new OAuth2Provider());
		}
	}

	public static SMTPTransport connectToSmtp(String host, int port, String userEmail, String oauthToken) throws Exception {

		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.starttls.required", "true");
		props.put("mail.smtp.sasl.enable", "true");
		props.put("mail.smtp.sasl.mechanisms", "XOAUTH2");
		props.put(OAuth2SaslClientFactory.OAUTH_TOKEN_PROP, oauthToken);
		Session session = Session.getInstance(props);
		final URLName unusedUrlName = null;
		SMTPTransport transport = new SMTPTransport(session, unusedUrlName);
		final String emptyPassword = "";
		transport.connect(host, port, userEmail, emptyPassword);
		return transport;
	}

	
}
