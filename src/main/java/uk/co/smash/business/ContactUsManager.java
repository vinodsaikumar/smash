package uk.co.smash.business;

import java.util.Properties;

import org.apache.log4j.Logger;

import uk.co.smash.model.ContactUsDataModel;
import uk.co.smash.model.ContactUsResponse;
import uk.co.smash.model.EmailDataModel;
import uk.co.smash.utils.AppUtils;
import uk.co.smash.utils.CaptchaVerifier;
import uk.co.smash.utils.CustomPropertyLoader;
import uk.co.smash.utils.EmailUtils;

public class ContactUsManager {

	private ContactUsResponse contactUsResponse = new ContactUsResponse();
	private static final String CAPTCHA_CODE_PRIVATE_KEY = "captchaCodePrivateKey";
	private static final String CLUB_EMAIL_ID = "clubEmailId";
	private static final String SMTP_SERVER_USERNAME = "SMTPServerUsername";
	private static final String SMTP_SERVER_ENCRYPTED_PASSWORD = "SMTPServerEncryptedPassword";
	private static final String SMTP_SERVER_HOST = "SMTPServerHost";
	private static final String SMTP_SERVER_OUTGOING_PORT = "SMTPServerOutgoingPort";
	private static final Logger LOG = Logger.getLogger(ContactUsManager.class.getName());

	public ContactUsResponse manage(ContactUsDataModel contactUsDataModel) throws Exception {

		String captchaClientResponse = contactUsDataModel.getCaptchaClientResponse();
		if (captchaClientResponse == null || captchaClientResponse.isEmpty()) {
			this.contactUsResponse.setRobot(true);
			LOG.debug("User has not answered the captcha field ");
		} else {
			validateCaptcha(captchaClientResponse);
		}
		if (this.contactUsResponse.isCaptchaValid()) {
			boolean validationStatus = validateContactUsFormFields(contactUsDataModel);
			if (validationStatus) {
				LOG.debug("The contact us form is valid and about to process the form.");
				boolean submitStatus = processContactUsForm(contactUsDataModel);
				this.contactUsResponse.setSubmited(submitStatus);
			}
		}
		return contactUsResponse;
	}

	private boolean processContactUsForm(ContactUsDataModel contactUsDataModel) throws Exception {

		LOG.debug("Ready to process the contact us form.");
		LOG.info("Sending email to club admin account");
		LOG.info("#### Start ####");
		LOG.info("First Name:" + contactUsDataModel.getfName());
		LOG.info("Last Name:" + contactUsDataModel.getlName());
		LOG.info("Email Id:" + contactUsDataModel.geteMail());
		LOG.info("Message:" + contactUsDataModel.getMessage());
		LOG.info("#### END ####");
		Properties applicationProperties = CustomPropertyLoader.getInstance().getApplicationProperties();
		EmailDataModel emailDataModel = new EmailDataModel();
		emailDataModel.setTo(applicationProperties.getProperty(ContactUsManager.CLUB_EMAIL_ID));
		emailDataModel.setFrom(applicationProperties.getProperty(ContactUsManager.CLUB_EMAIL_ID));
		emailDataModel.setSmtpServerHost(applicationProperties.getProperty(ContactUsManager.SMTP_SERVER_HOST));
		String port = applicationProperties.getProperty(ContactUsManager.SMTP_SERVER_OUTGOING_PORT);
		emailDataModel.setSmtpServerOutgoingPort(Integer.parseInt(port));
		emailDataModel.setSubject("Message from - " + contactUsDataModel.getfName() + " " + contactUsDataModel.getlName());
		StringBuffer emailMessage = new StringBuffer();
		emailMessage.append("First Name: " + contactUsDataModel.getfName() + "\n");
		emailMessage.append("Last Name: " + contactUsDataModel.getlName() + "\n");
		emailMessage.append("Email Id: " + contactUsDataModel.geteMail() + "\n");
		emailMessage.append("Message: " + contactUsDataModel.getMessage() + "\n");
		emailDataModel.setMessage(emailMessage.toString());
		LOG.debug("Email data model is " + emailDataModel.toString());
		EmailUtils.sendTextEmail(emailDataModel);
		this.contactUsResponse.setMessage("Thank you for your message. We will get in touch with you as soon as possible.");
		return true;
	}

	private void validateCaptcha(String captchaClientResponse) throws Exception {

		Properties applicationProperties = CustomPropertyLoader.getInstance().getApplicationProperties();
		String privateKey = applicationProperties.getProperty(ContactUsManager.CAPTCHA_CODE_PRIVATE_KEY);
		CaptchaVerifier captchaVerifier = new CaptchaVerifier();
		boolean status = captchaVerifier.verify(privateKey, captchaClientResponse);
		this.contactUsResponse.setCaptchaValid(status);
	}

	private boolean validateContactUsFormFields(ContactUsDataModel contactUsDataModel) {

		boolean status = true;
		String fName = contactUsDataModel.getfName();
		String lName = contactUsDataModel.getlName();
		String email = contactUsDataModel.geteMail();
		String message = contactUsDataModel.getMessage();
		if (fName == null || fName.isEmpty() || fName.length() > 50) {
			this.contactUsResponse.setMessage("Please enter a valid first name.");
			status = false;
		} else if (lName == null || lName.isEmpty() || lName.length() > 50) {
			this.contactUsResponse.setMessage("Please enter a valid last name.");
			status = false;
		} else if (email == null || email.isEmpty() || email.length() > 50) {
			this.contactUsResponse.setMessage("Please enter a valid email id.");
			status = false;
		} else if (message == null || message.isEmpty() || message.length() > 500) {
			this.contactUsResponse.setMessage("Please enter a valid message.");
			status = false;
		}
		return status;
	}

}
