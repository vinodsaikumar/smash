package uk.co.smash.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

public class LeagueUiModel {

	public static class ScoreBoardItem implements Comparable<ScoreBoardItem> {

		private static final Logger LOG = Logger.getLogger(ScoreBoardItem.class.getName());
		private String matchDate;
		private String teamOnePlayers;
		private String teamTwoPlayers;
		private String scores;

		public String getMatchDate() {

			return matchDate;
		}

		public void setMatchDate(String matchDate) {

			this.matchDate = matchDate;
		}

		public String getTeamOnePlayers() {

			return teamOnePlayers;
		}

		public void setTeamOnePlayers(String teamOnePlayers) {

			this.teamOnePlayers = teamOnePlayers;
		}

		public String getTeamTwoPlayers() {
		
			return teamTwoPlayers;
		}

		
		public void setTeamTwoPlayers(String teamTwoPlayers) {
		
			this.teamTwoPlayers = teamTwoPlayers;
		}

		public String getScores() {

			return scores;
		}

		public void setScores(String scores) {

			this.scores = scores;
		}

		@Override
		public int compareTo(ScoreBoardItem o) {

			int dateOrder = 0;
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy");
			Date thisDate = null;
			Date oDate = null;
			try {
				thisDate = simpleDateFormat.parse(this.matchDate);
				oDate = simpleDateFormat.parse(o.matchDate);
			} catch (ParseException e) {
				LOG.error("Could not convert match date from string to date object " + e);
			}
			if (thisDate.before(oDate)) {
				dateOrder = -1;
			} else if (thisDate.after(oDate)) {
				dateOrder = 1;
			}
			return dateOrder;
		}

		@Override
		public String toString() {

			return "ScoreBoardItem [matchDate=" + matchDate + ", teamOnePlayers=" + teamOnePlayers + ", teamTwoPlayers=" + teamTwoPlayers + ", scores=" + scores + "]";
		}

	}

	private List<String> startPerformers;
	private String startDate;
	private String endDate;
	private String venue;
	private String time;
	private List<TeamPerformance> teamPerformances;
	private List<ScoreBoardItem> scoreBoard;

	public List<ScoreBoardItem> getScoreBoard() {
	
		return scoreBoard;
	}

	public void setScoreBoard(List<ScoreBoardItem> scoreBoard) {
	
		this.scoreBoard = scoreBoard;
	}

	public List<String> getStartPerformers() {

		return startPerformers;
	}

	public void setStartPerformers(List<String> startPerformers) {

		this.startPerformers = startPerformers;
	}

	public String getStartDate() {

		return startDate;
	}

	public void setStartDate(String startDate) {

		this.startDate = startDate;
	}

	public String getEndDate() {

		return endDate;
	}

	public void setEndDate(String endDate) {

		this.endDate = endDate;
	}

	public String getVenue() {

		return venue;
	}

	public void setVenue(String venue) {

		this.venue = venue;
	}

	public String getTime() {

		return time;
	}

	public void setTime(String time) {

		this.time = time;
	}

	public List<TeamPerformance> getTeamPerformances() {

		return teamPerformances;
	}

	public void setTeamPerformances(List<TeamPerformance> teamPerformances) {

		this.teamPerformances = teamPerformances;
	}

	@Override
	public String toString() {

		return "LeagueUiModel [startPerformers=" + startPerformers + ", startDate=" + startDate + ", endDate=" + endDate + ", venue=" + venue + ", time=" + time + ", teamPerformances=" + teamPerformances + ", scoreBoard=" + scoreBoard + "]";
	}

}
