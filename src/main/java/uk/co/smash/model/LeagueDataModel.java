package uk.co.smash.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

public class LeagueDataModel {

	public static class Team {

		private int id;
		private String name;
		private List<String> players;

		public int getId() {

			return id;
		}

		public void setId(int id) {

			this.id = id;
		}

		public String getName() {

			return name;
		}

		public void setName(String name) {

			this.name = name;
		}

		public List<String> getPlayers() {

			return players;
		}

		public void setPlayers(List<String> players) {

			this.players = players;
		}

		@Override
		public String toString() {

			return "Team [id=" + id + ", name=" + name + ", players=" + players + "]";
		}

	}

	public static class Match implements Comparable<Match> {

		private static final Logger LOG = Logger.getLogger(Match.class.getName());

		private String id;
		private String date;
		private int teamOneId;
		private int teamTwoId;
		private int teamOneSetOneScore;
		private int teamOneSetTwoScore;
		private int teamOneSetThreeScore;
		private int teamTwoSetOneScore;
		private int teamTwoSetTwoScore;
		private int teamTwoSetThreeScore;

		public String getId() {

			return id;
		}

		public void setId(String id) {

			this.id = id;
		}

		public String getDate() {

			return date;
		}

		public void setDate(String date) {

			this.date = date;
		}

		public int getTeamOneId() {

			return teamOneId;
		}

		public void setTeamOneId(int teamOneId) {

			this.teamOneId = teamOneId;
		}

		public int getTeamTwoId() {

			return teamTwoId;
		}

		public void setTeamTwoId(int teamTwoId) {

			this.teamTwoId = teamTwoId;
		}

		public int getTeamOneSetOneScore() {

			return teamOneSetOneScore;
		}

		public void setTeamOneSetOneScore(int teamOneSetOneScore) {

			this.teamOneSetOneScore = teamOneSetOneScore;
		}

		public int getTeamOneSetTwoScore() {

			return teamOneSetTwoScore;
		}

		public void setTeamOneSetTwoScore(int teamOneSetTwoScore) {

			this.teamOneSetTwoScore = teamOneSetTwoScore;
		}

		public int getTeamOneSetThreeScore() {

			return teamOneSetThreeScore;
		}

		public void setTeamOneSetThreeScore(int teamOneSetThreeScore) {

			this.teamOneSetThreeScore = teamOneSetThreeScore;
		}

		public int getTeamTwoSetOneScore() {

			return teamTwoSetOneScore;
		}

		public void setTeamTwoSetOneScore(int teamTwoSetOneScore) {

			this.teamTwoSetOneScore = teamTwoSetOneScore;
		}

		public int getTeamTwoSetTwoScore() {

			return teamTwoSetTwoScore;
		}

		public void setTeamTwoSetTwoScore(int teamTwoSetTwoScore) {

			this.teamTwoSetTwoScore = teamTwoSetTwoScore;
		}

		public int getTeamTwoSetThreeScore() {

			return teamTwoSetThreeScore;
		}

		public void setTeamTwoSetThreeScore(int teamTwoSetThreeScore) {

			this.teamTwoSetThreeScore = teamTwoSetThreeScore;
		}

		@Override
		public int compareTo(Match o) {

			int dateOrder = 0;
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy");
			Date thisDate = null;
			Date oDate = null;
			try {
				thisDate = simpleDateFormat.parse(this.date);
				oDate = simpleDateFormat.parse(o.date);
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

			return "Match [id=" + id + ", date=" + date + ", teamOneId=" + teamOneId + ", teamTwoId=" + teamTwoId + ", teamOneSetOneScore=" + teamOneSetOneScore + ", teamOneSetTwoScore=" + teamOneSetTwoScore + ", teamOneSetThreeScore=" + teamOneSetThreeScore + ", teamTwoSetOneScore=" + teamTwoSetOneScore + ", teamTwoSetTwoScore=" + teamTwoSetTwoScore + ", teamTwoSetThreeScore=" + teamTwoSetThreeScore + "]";
		}

	}

	private List<Team> teams;

	private List<Match> matches;

	private String startDate;

	private String endDate;

	private String venue;

	private String time;

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

	public List<Team> getTeams() {

		return teams;
	}

	public void setTeams(List<Team> teams) {

		this.teams = teams;
	}

	public List<Match> getMatches() {

		return matches;
	}

	public void setMatches(List<Match> matches) {

		this.matches = matches;
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

	@Override
	public String toString() {

		return "League [teams=" + teams + ", matches=" + matches + ", startDate=" + startDate + ", endDate=" + endDate + ", venue=" + venue + ", time=" + time + "]";
	}

}
