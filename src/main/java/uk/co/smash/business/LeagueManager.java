package uk.co.smash.business;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import uk.co.smash.model.LeagueDataModel;
import uk.co.smash.model.LeagueDataModel.Match;
import uk.co.smash.model.LeagueDataModel.Team;
import uk.co.smash.model.LeagueUiModel;
import uk.co.smash.model.LeagueUiModel.ScoreBoardItem;
import uk.co.smash.model.TeamPerformance;
import uk.co.smash.utils.CustomPropertyLoader;

public class LeagueManager {

	private static final Logger LOG = Logger.getLogger(LeagueManager.class.getName());

	public LeagueUiModel getLeagueData() {

		LeagueUiModel leagueUiModel = new LeagueUiModel();
		LeagueDataModel leagueDataModel = this.getLeague();
		leagueUiModel.setStartDate(leagueDataModel.getStartDate());
		leagueUiModel.setEndDate(leagueDataModel.getEndDate());
		leagueUiModel.setTime(leagueDataModel.getTime());
		leagueUiModel.setVenue(leagueDataModel.getVenue());
		List<TeamPerformance> teamPerformances = this.getTeamPerformances();
		Collections.sort(teamPerformances);
		leagueUiModel.setTeamPerformances(teamPerformances);
		List<ScoreBoardItem> scoreBoard = this.getScoreBoard();
		Collections.sort(scoreBoard);
		leagueUiModel.setScoreBoard(scoreBoard);
		LOG.debug("The league UI model " + leagueUiModel.toString());
		return leagueUiModel;
	}

	private List<TeamPerformance> getTeamPerformances() {

		LeaguePerformanceCalculator lpc = new LeaguePerformanceCalculator();
		List<TeamPerformance> teamPerformances = null;
		try {
			teamPerformances = lpc.calculate(this.getLeague());
		} catch (Exception e) {
			LOG.error("Could not calculate the team performance ", e);
		}
		LOG.debug("The team performances are " + teamPerformances);
		return teamPerformances;
	}

	private List<ScoreBoardItem> getScoreBoard() {

		LeagueDataModel leagueDataModel = this.getLeague();
		List<Match> allMatches = leagueDataModel.getMatches();
		List<Team> allTeams = leagueDataModel.getTeams();
		List<ScoreBoardItem> scoreBoard = new ArrayList<LeagueUiModel.ScoreBoardItem>();

		for (Match match : allMatches) {
			if (isMatchPlayed(match)) {
				ScoreBoardItem scoreBoardItem = new ScoreBoardItem();
				scoreBoardItem.setMatchDate(match.getDate());
				for (Team team : allTeams) {
					if (match.getTeamOneId() == team.getId()) {
						scoreBoardItem.setTeamOnePlayers(team.getPlayers().toString());
					}
					if (match.getTeamTwoId() == team.getId()) {
						scoreBoardItem.setTeamTwoPlayers(team.getPlayers().toString());
					}
				}

				StringBuffer scores = new StringBuffer();
				scores.append(match.getTeamOneSetOneScore() + "-" + match.getTeamTwoSetOneScore());
				scores.append(" ");
				scores.append(match.getTeamOneSetTwoScore() + "-" + match.getTeamTwoSetTwoScore());
				scores.append(" ");
				scores.append(match.getTeamOneSetThreeScore() + "-" + match.getTeamTwoSetThreeScore());
				scoreBoardItem.setScores(scores.toString());
				scoreBoard.add(scoreBoardItem);
			}
		}
		return scoreBoard;
	}

	private boolean isMatchPlayed(Match match) {

		boolean isPlayed = false;
		int teamOneScores = match.getTeamOneSetOneScore() + match.getTeamOneSetTwoScore() + match.getTeamOneSetThreeScore();
		int teamTwoScores = match.getTeamTwoSetOneScore() + match.getTeamTwoSetTwoScore() + match.getTeamTwoSetThreeScore();
		if (teamOneScores != 0 || teamTwoScores != 0) {
			isPlayed = true;
		}
		return isPlayed;
	}

	private LeagueDataModel getLeague() {

		LeagueDataModel league = null;
		try {
			league = CustomPropertyLoader.getInstance().getLeague();
		} catch (Exception e) {
			LOG.error("Error while retrieving the league match scores " + e);
		}
		return league;
	}

}
