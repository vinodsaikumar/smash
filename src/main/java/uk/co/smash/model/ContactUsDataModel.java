package uk.co.smash.model;

public class ContactUsDataModel {

	private String fName;
	private String lName;
	private String eMail;
	private String message;
	private String captchaClientResponse;

	public String getfName() {

		return fName;
	}

	public void setfName(String fName) {

		this.fName = fName;
	}

	public String getlName() {

		return lName;
	}

	public void setlName(String lName) {

		this.lName = lName;
	}

	public String geteMail() {

		return eMail;
	}

	public void seteMail(String eMail) {

		this.eMail = eMail;
	}

	public String getMessage() {

		return message;
	}

	public void setMessage(String message) {

		this.message = message;
	}

	public String getCaptchaClientResponse() {

		return captchaClientResponse;
	}

	public void setCaptchaClientResponse(String captchaClientResponse) {

		this.captchaClientResponse = captchaClientResponse;
	}

	@Override
	public String toString() {

		return "ContactUsDataModel [fName=" + fName + ", lName=" + lName + ", eMail=" + eMail + ", message=" + message + ", captchaClientResponse=" + captchaClientResponse + "]";
	}
	
	

}
