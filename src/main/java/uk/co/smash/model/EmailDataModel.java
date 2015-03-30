package uk.co.smash.model;

public class EmailDataModel {

	private String to;
	private String from;
	private String subject;
	private String message;
	private String smtpServerUsername;
	private String smtpServerPassword;
	private String smtpServerHost;
	private int smtpServerOutgoingPort;

	public String getTo() {

		return to;
	}

	public void setTo(String to) {

		this.to = to;
	}

	public String getFrom() {

		return from;
	}

	public void setFrom(String from) {

		this.from = from;
	}

	public String getSubject() {

		return subject;
	}

	public void setSubject(String subject) {

		this.subject = subject;
	}

	public String getMessage() {

		return message;
	}

	public void setMessage(String message) {

		this.message = message;
	}

	public String getSmtpServerUsername() {

		return smtpServerUsername;
	}

	public void setSmtpServerUsername(String smtpServerUsername) {

		this.smtpServerUsername = smtpServerUsername;
	}

	public String getSmtpServerPassword() {

		return smtpServerPassword;
	}

	public void setSmtpServerPassword(String smtpServerPassword) {

		this.smtpServerPassword = smtpServerPassword;
	}

	public String getSmtpServerHost() {

		return smtpServerHost;
	}

	public void setSmtpServerHost(String smtpServerHost) {

		this.smtpServerHost = smtpServerHost;
	}

	public int getSmtpServerOutgoingPort() {

		return smtpServerOutgoingPort;
	}

	public void setSmtpServerOutgoingPort(int smtpServerOutgoingPort) {

		this.smtpServerOutgoingPort = smtpServerOutgoingPort;
	}

	@Override
	public String toString() {

		return "EmailDataModel [to=" + to + ", from=" + from + ", subject=" + subject + ", message=" + message + ", smtpServerUsername=" + smtpServerUsername + ", smtpServerPassword=XXXXXX, smtpServerHost=" + smtpServerHost + ", smtpServerOutgoingPort=" + smtpServerOutgoingPort + "]";
	}

}
