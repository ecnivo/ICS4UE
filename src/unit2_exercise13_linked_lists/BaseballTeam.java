package unit2_exercise13_linked_lists;

public class BaseballTeam implements Comparable<BaseballTeam> {

	private String name;
	private int wins, losses, ties;
	private String[] players = new String[25];

	public BaseballTeam(String name) {
		this.name = name;
		wins = 0;
		losses = 0;
		ties = 0;
	}

	public BaseballTeam(String name, int wins, int losses, int ties) {
		super();
		this.name = name;
		this.wins = wins;
		this.losses = losses;
		this.ties = ties;
	}

	public BaseballTeam(String name, int wins, int losses, int ties,
			String[] players) {
		super();
		this.name = name;
		this.wins = wins;
		this.losses = losses;
		this.ties = ties;
		this.players = players;
	}

	public void winGame() {
		wins++;
	}

	public void loseGame() {
		losses++;
	}

	public void tieGame() {
		ties++;
	}

	/**
	 * @return the wins
	 */
	public int getWins() {
		return wins;
	}

	/**
	 * @return the losses
	 */
	public int getLosses() {
		return losses;
	}

	/**
	 * @return the ties
	 */
	public int getTies() {
		return ties;
	}

	/**
	 * @return the players
	 */
	public String[] getPlayers() {
		return players;
	}

	@Override
	public int compareTo(BaseballTeam otherTeam) {
		return this.wins - otherTeam.getWins();
	}

	public String toString() {
		return name + " and " + wins + " wins";
	}

	public String getName() {
		return name;
	}
}
