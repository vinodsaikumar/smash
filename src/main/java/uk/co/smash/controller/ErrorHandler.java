package uk.co.smash.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Servlet implementation class ErrorHandler
 */
@WebServlet(name = "ErrorHandler", displayName = "ErrorHandler", urlPatterns = { "/error" }, loadOnStartup = 1)
public class ErrorHandler extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(ErrorHandler.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ErrorHandler() {

		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		processError(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		processError(request, response);
	}

	private void processError(HttpServletRequest request, HttpServletResponse response) {

		try {
			request.getRequestDispatcher("/error.html").forward(request, response);
		} catch (ServletException e) {
			LOG.error("Error while rendering error page to end customer " + e);
		} catch (IOException e) {
			LOG.error("Error while rendering error page to end customer " + e);
		}
	}

}
