package uk.co.smash.model;

public class ContactUsResponse {

	private boolean isRobot = false;
	private boolean isCaptchaValid = false;
	private String message = "";
	private boolean submited = false;
	
	
	public boolean isSubmited() {
	
		return submited;
	}

	
	public void setSubmited(boolean submited) {
	
		this.submited = submited;
	}

	public boolean isRobot() {
	
		return isRobot;
	}
	
	public void setRobot(boolean isRobot) {
	
		this.isRobot = isRobot;
	}
	
	public boolean isCaptchaValid() {
	
		return isCaptchaValid;
	}
	
	public void setCaptchaValid(boolean isCaptchaValid) {
	
		this.isCaptchaValid = isCaptchaValid;
	}
	
	public String getMessage() {
	
		return message;
	}
	
	public void setMessage(String message) {
	
		this.message = message;
	}


	@Override
	public String toString() {

		return "ContactUsResponse [isRobot=" + isRobot + ", isCaptchaValid=" + isCaptchaValid + ", message=" + message + ", submited=" + submited + "]";
	}
	
	
}
