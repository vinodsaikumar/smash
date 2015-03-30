package uk.co.smash.business;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import uk.co.smash.model.LeagueDataModel;
import uk.co.smash.model.LeagueDataModel.Match;
import uk.co.smash.model.LeagueDataModel.Team;
import uk.co.smash.model.MatchStatus;
import uk.co.smash.model.TeamPerformance;

public class LeaguePerformanceCalculator {

	private static final Logger LOG = Logger.getLogger(LeaguePerformanceCalculator.class.getName());

	private List<TeamPerformance> teamPerformances = new ArrayList<TeamPerformance>();

	public List<TeamPerformance> calculate(LeagueDataModel leagueDataModel) throws Exception {

		LOG.debug("The league match scores are " + leagueDataModel.toString());
		List<Team> teams = leagueDataModel.getTeams();
		this.updateAllTeams(teams);
		List<Match> matches = leagueDataModel.getMatches();
		for (Match match : matches) {
			MatchStatus matchStatus = this.getMatchStatus(match);
			this.updateTeamPerformance(matchStatus);
			this.updateTotalSetPoints(match, matchStatus);
			this.updateTotalMatchesPlayed(match);
		}
		this.updateAverageSetPoints();

		LOG.debug("The team performance list " + teamPerformances);
		return this.teamPerformances;
	}

	private void updateAllTeams(List<Team> teams) {

		for (Team team : teams) {
			TeamPerformance teamPerformance = new TeamPerformance();
			teamPerformance.setTeamId(team.getId());
			teamPerformance.setTeamName(team.getName());
			teamPerformance.setPlayersName(team.getPlayers().toString());
			this.teamPerformances.add(teamPerformance);
		}
	}

	private void updateTeamPerformance(MatchStatus matchStatus) {

		for (TeamPerformance teamPerformance : this.teamPerformances) {
			if (teamPerformance.getTeamId() == matchStatus.getTeamIdWon()) {
				teamPerformance.setMatchesWon(teamPerformance.getMatchesWon() + 1);
				teamPerformance.setTotalSetsPlayedToWin(teamPerformance.getTotalSetsPlayedToWin() + matchStatus.getNoOfSetsPlayedToWin());
				teamPerformance.setWonByPoints(teamPerformance.getWonByPoints() + matchStatus.getWonByPoints());
			}
		}
	}

	private void updateAverageSetPoints() {

		for (TeamPerformance teamPerformance : this.teamPerformances) {
			if (teamPerformance.getTotalSetsPlayed() != 0) {
				double avgSetPoints = (double) teamPerformance.getTotalSetsPoints() / teamPerformance.getTotalSetsPlayed();
				double roundOffAvgSetPoints = Math.round(avgSetPoints * 100.0) / 100.0;
				teamPerformance.setAvgSetPoints(roundOffAvgSetPoints);
			}
		}
	}

	private void updateTotalMatchesPlayed(Match match) {

		for (TeamPerformance teamPerformance : this.teamPerformances) {
			if (teamPerformance.getTeamId() == match.getTeamOneId() || teamPerformance.getTeamId() == match.getTeamTwoId()) {
				int totalMatchScores = match.getTeamOneSetOneScore() + match.getTeamOneSetTwoScore() + match.getTeamOneSetThreeScore() + match.getTeamTwoSetOneScore() + match.getTeamTwoSetTwoScore() + match.getTeamTwoSetThreeScore();
				if (totalMatchScores > 0) {
					int matchesPlayed = teamPerformance.getMatchesPlayed() + 1;
					teamPerformance.setMatchesPlayed(matchesPlayed);
				}
			}
		}
	}

	private void updateTotalSetPoints(Match match, MatchStatus matchStatus) {

		for (TeamPerformance teamPerformance : this.teamPerformances) {
			if (teamPerformance.getTeamId() == match.getTeamOneId()) {
				teamPerformance.setTotalSetsPlayed(teamPerformance.getTotalSetsPlayed() + matchStatus.getNoOfSetsPlayedToWin());
				int totalSetsPointInThisMatch = match.getTeamOneSetOneScore() + match.getTeamOneSetTwoScore() + match.getTeamOneSetThreeScore();
				teamPerformance.setTotalSetsPoints(teamPerformance.getTotalSetsPoints() + totalSetsPointInThisMatch);
			}
			if (teamPerformance.getTeamId() == match.getTeamTwoId()) {
				teamPerformance.setTotalSetsPlayed(teamPerformance.getTotalSetsPlayed() + matchStatus.getNoOfSetsPlayedToWin());
				int totalSetsPointInThisMatch = match.getTeamTwoSetOneScore() + match.getTeamTwoSetTwoScore() + match.getTeamTwoSetThreeScore();
				teamPerformance.setTotalSetsPoints(teamPerformance.getTotalSetsPoints() + totalSetsPointInThisMatch);
			}
		}
	}

	private MatchStatus getMatchStatus(Match match) {

		MatchStatus matchStatus = new MatchStatus();
		int setsWonTeamOne = 0;
		int setsWonTeamTwo = 0;
		int teamOneWonByPoints = 0;
		int teamTwoWonByPoints = 0;
		int noOfSetsPlayedToWin = 3;
		if (match.getTeamOneSetOneScore() > match.getTeamTwoSetOneScore()) {
			setsWonTeamOne++;
			teamOneWonByPoints = teamOneWonByPoints + (match.getTeamOneSetOneScore() - match.getTeamTwoSetOneScore());
		} else if (match.getTeamTwoSetOneScore() > match.getTeamOneSetOneScore()) {
			setsWonTeamTwo++;
			teamTwoWonByPoints = teamTwoWonByPoints + (match.getTeamTwoSetOneScore() - match.getTeamOneSetOneScore());
		} else {
			noOfSetsPlayedToWin--;
		}
		if (match.getTeamOneSetTwoScore() > match.getTeamTwoSetTwoScore()) {
			setsWonTeamOne++;
			teamOneWonByPoints = teamOneWonByPoints + (match.getTeamOneSetTwoScore() - match.getTeamTwoSetTwoScore());
		} else if (match.getTeamTwoSetTwoScore() > match.getTeamOneSetTwoScore()) {
			setsWonTeamTwo++;
			teamTwoWonByPoints = teamTwoWonByPoints + (match.getTeamTwoSetTwoScore() - match.getTeamOneSetTwoScore());
		} else {
			noOfSetsPlayedToWin--;
		}
		if (match.getTeamOneSetThreeScore() > match.getTeamTwoSetThreeScore()) {
			setsWonTeamOne++;
			teamOneWonByPoints = teamOneWonByPoints + (match.getTeamOneSetThreeScore() - match.getTeamTwoSetThreeScore());
		} else if (match.getTeamTwoSetThreeScore() > match.getTeamOneSetThreeScore()) {
			setsWonTeamTwo++;
			teamTwoWonByPoints = teamTwoWonByPoints + (match.getTeamTwoSetThreeScore() - match.getTeamOneSetThreeScore());
		} else {
			noOfSetsPlayedToWin--;
		}
		if (setsWonTeamOne > setsWonTeamTwo) {
			matchStatus.setTeamIdWon(match.getTeamOneId());
			matchStatus.setWonByPoints(teamOneWonByPoints);
			matchStatus.setNoOfSetsPlayedToWin(noOfSetsPlayedToWin);
		} else if (setsWonTeamTwo > setsWonTeamOne) {
			matchStatus.setTeamIdWon(match.getTeamTwoId());
			matchStatus.setWonByPoints(teamTwoWonByPoints);
			matchStatus.setNoOfSetsPlayedToWin(noOfSetsPlayedToWin);
		} else {
			matchStatus.setTeamIdWon(0);
			matchStatus.setWonByPoints(0);
			matchStatus.setNoOfSetsPlayedToWin(0);
		}

		return matchStatus;
	}

}
