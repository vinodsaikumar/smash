package uk.co.smash.model;

public class TeamPerformance implements Comparable<TeamPerformance> {

	private int teamId;
	private String teamName;
	private String playersName;
	private int matchesPlayed=0;
	private int matchesWon = 0;
	private int totalSetsPlayedToWin = 0;
	private int totalSetsPoints = 0;
	private int totalSetsPlayed = 0;
	private double avgSetPoints = 0;
	private int wonByPoints = 0;
	
	public int getMatchesPlayed() {
	
		return matchesPlayed;
	}

	
	public void setMatchesPlayed(int matchesPlayed) {
	
		this.matchesPlayed = matchesPlayed;
	}

	public double getAvgSetPoints() {
	
		return avgSetPoints;
	}

	public void setAvgSetPoints(double avgSetPoints) {
	
		this.avgSetPoints = avgSetPoints;
	}

	public int getTotalSetsPlayed() {

		return totalSetsPlayed;
	}

	public void setTotalSetsPlayed(int totalSetsPlayed) {

		this.totalSetsPlayed = totalSetsPlayed;
	}

	public String getTeamName() {

		return teamName;
	}

	public String getPlayersName() {

		return playersName;
	}

	public void setPlayersName(String playersName) {

		this.playersName = playersName;
	}

	public void setTeamName(String teamName) {

		this.teamName = teamName;
	}

	public int getTeamId() {

		return teamId;
	}

	public void setTeamId(int teamId) {

		this.teamId = teamId;
	}

	public int getMatchesWon() {

		return matchesWon;
	}

	public void setMatchesWon(int matchesWon) {

		this.matchesWon = matchesWon;
	}

	public int getTotalSetsPlayedToWin() {

		return totalSetsPlayedToWin;
	}

	public void setTotalSetsPlayedToWin(int totalSetsPlayedToWin) {

		this.totalSetsPlayedToWin = totalSetsPlayedToWin;
	}

	public int getTotalSetsPoints() {

		return totalSetsPoints;
	}

	public void setTotalSetsPoints(int totalSetsPoints) {

		this.totalSetsPoints = totalSetsPoints;
	}

	public int getWonByPoints() {

		return wonByPoints;
	}

	public void setWonByPoints(int wonByPoints) {

		this.wonByPoints = wonByPoints;
	}

	@Override
	public int compareTo(TeamPerformance o) {

		int performanceScale = 0;

		if (this.matchesWon > o.matchesWon) {
			performanceScale = -1;
		} else if (this.matchesWon < o.matchesWon) {
			performanceScale = 1;
		} else {
			if (this.totalSetsPlayedToWin < o.totalSetsPlayedToWin) {
				performanceScale = -1;
			} else if (this.totalSetsPlayedToWin > o.totalSetsPlayedToWin) {
				performanceScale = 1;
			} else {
				if (this.wonByPoints > o.wonByPoints) {
					performanceScale = -1;
				} else if (this.wonByPoints < o.wonByPoints) {
					performanceScale = 1;
				} else {
					if (this.avgSetPoints > o.avgSetPoints) {
						performanceScale = -1;
					} else if (this.avgSetPoints < o.avgSetPoints) {
						performanceScale = 1;
					}
				}
			}
		}

		return performanceScale;
	}

	@Override
	public String toString() {

		return "TeamPerformance [teamId=" + teamId + ", teamName=" + teamName + ", playersName=" + playersName + ", matchesWon=" + matchesWon + ", totalSetsPlayedToWin=" + totalSetsPlayedToWin + ", totalSetsPoints=" + totalSetsPoints + ", totalSetsPlayed=" + totalSetsPlayed + ", avgSetPoints=" + avgSetPoints + ", wonByPoints=" + wonByPoints + "]";
	}

}
