package uk.co.smash.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import uk.co.smash.business.LeagueManager;
import uk.co.smash.model.LeagueUiModel;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class LeagueHandler
 */
@WebServlet(name = "LeagueHandler", displayName = "LeagueHandler", urlPatterns = { "/league" }, loadOnStartup = 1)
public class LeagueHandler extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(LeagueHandler.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LeagueHandler() {

		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		LeagueManager leagueManager = new LeagueManager();
		LeagueUiModel league = leagueManager.getLeagueData();
		LOG.debug("The UI data model : " + league);
		ObjectMapper objectToJsonMapper = new ObjectMapper();
		objectToJsonMapper.writeValue(response.getOutputStream(), league);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// TODO Auto-generated method stub
	}
	
	public static void main(String[] args) {
		LeagueManager leagueManager = new LeagueManager();
		LeagueUiModel league = leagueManager.getLeagueData();
		LOG.debug("The UI data model : " + league);
		
	}
}
