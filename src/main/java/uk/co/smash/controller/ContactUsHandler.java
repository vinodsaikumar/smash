package uk.co.smash.controller;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import uk.co.smash.business.ContactUsManager;
import uk.co.smash.model.ContactUsDataModel;
import uk.co.smash.model.ContactUsResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class ContactUsHandler
 */
@WebServlet(name = "ContactUsHandler", displayName = "ContactUsHandler", urlPatterns = { "/contactus" }, loadOnStartup = 1)
public class ContactUsHandler extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(ContactUsHandler.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ContactUsHandler() {

		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			LOG.debug("Entered to solve the contact us functionality");

			String fName = request.getParameter("fname");
			String lName = request.getParameter("lname");
			String email = request.getParameter("email");
			String message = request.getParameter("message");
			String captchaClientResponse = request.getParameter("g-recaptcha-response");

			ContactUsDataModel contactUsDataModel = new ContactUsDataModel();
			contactUsDataModel.setfName(fName);
			contactUsDataModel.setlName(lName);
			contactUsDataModel.seteMail(email);
			contactUsDataModel.setMessage(message);
			contactUsDataModel.setCaptchaClientResponse(captchaClientResponse);
			LOG.debug("Contact Us form  " + contactUsDataModel.toString());

			ContactUsManager contactUsManager = new ContactUsManager();
			ContactUsResponse contactUsResponse = contactUsManager.manage(contactUsDataModel);
			LOG.debug("The contact us form submit status " + contactUsResponse.toString());

			ObjectMapper objectToJsonMapper = new ObjectMapper();
			objectToJsonMapper.writeValue(response.getOutputStream(), contactUsResponse);
		} catch (Exception e) {
			LOG.error("Error while processing the contact us form, redirected the user to default error page.", e);
			throw new ServletException();
		}

	}

}
