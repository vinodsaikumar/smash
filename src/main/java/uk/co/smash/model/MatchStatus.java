package uk.co.smash.model;

public class MatchStatus {

	private int teamIdWon;
	private int noOfSetsPlayedToWin=0;
	private int wonByPoints=0;
	
	public int getTeamIdWon() {
	
		return teamIdWon;
	}
	
	public void setTeamIdWon(int teamIdWon) {
	
		this.teamIdWon = teamIdWon;
	}
	
	public int getNoOfSetsPlayedToWin() {
	
		return noOfSetsPlayedToWin;
	}
	
	public void setNoOfSetsPlayedToWin(int noOfSetsPlayedToWin) {
	
		this.noOfSetsPlayedToWin = noOfSetsPlayedToWin;
	}
	
	public int getWonByPoints() {
	
		return wonByPoints;
	}
	
	public void setWonByPoints(int wonByPoints) {
	
		this.wonByPoints = wonByPoints;
	}

	@Override
	public String toString() {

		return "MatchStatus [teamIdWon=" + teamIdWon + ", noOfSetsPlayedToWin=" + noOfSetsPlayedToWin + ", wonByPoints=" + wonByPoints + "]";
	}
	
	
}
