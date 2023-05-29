package version_1;

public class Game {
	private Player [] players;
	private Box [] boxes;
	private Box [] homeBoxes;


	public Game(int numberOfPlayers) {
		
		this.createPlayers(numberOfPlayers);

		this.createBoxes();
		
		this.createHomeBoxes();
		
	}

	private void createPlayers(int numberOfPlayers) {
		this.players[0] = new Player ("red", "Player 1");
		
		this.players[1] = new Player ("yellow", "Player 2");

		this.players[2] = new Player();
		if (numberOfPlayers > 2) {
			this.players[2].setColor("green");
			this.players[2].setName("Player 3");
			this.players[2].setPiece();
		}
		
		this.players[3] = new Player();
		if (numberOfPlayers > 3) {
			this.players[3].setColor("blue");
			this.players[3].setName("Player 4");
			this.players[3].setPiece();
		}
	}
	
	private void createHomeBoxes() {
		for(int i = 0; i < players.length; i++) {
			homeBoxes[i] = players[i].getHome();
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

	public Player[] getPlayers() {
		return players;
	}

	public Box[] getBoxes() {
		return boxes;
	}

	public Box[] getHomeBoxes() {
		return homeBoxes;
	}

}