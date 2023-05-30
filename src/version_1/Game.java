package version_1;

public class Game {
	private Player [] players;
	private Box [] boxes;
	private Turn turn;


	public Game(int numberOfPlayers) {
		
		this.createPlayers(numberOfPlayers);

		this.createBoxes();
		
		this.createTurn();
		
	}

	private void createPlayers(int numberOfPlayers) {
		this.players[0] = new Player ("red", "Player 1");
		
		this.players[1] = new Player ("yellow", "Player 2");

		if (numberOfPlayers > 2) {
			this.players[2] = new Player("green", "Player 3");
		}
		
		if (numberOfPlayers > 3) {
			this.players[3] = new Player("blue", "Player 4");
		}
	}
	
	private void createBoxes() {
		for (int i=1;i<=80; i++) {
			if (i==1||i==21||i==41||i==61) {
				boxes[i]=new StartingBox(i);
			}
			else {
				boxes[i]=new Box(i);
			}
		}
	}
	
	private void createTurn() {
		this.turn = new Turn(this.players);
	}

	public Player[] getPlayers() {
		return players;
	}

	public Box[] getBoxes() {
		return boxes;
	}

}