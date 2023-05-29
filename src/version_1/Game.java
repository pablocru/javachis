package version_1;

import version_1.Piece;
import version_1.Player;

public class Game {
	private Player [] players;
	private Box [] boxes;


	public Game(int numberOfPlayers) {
		
		this.createPlayers(numberOfPlayers);


		
	}

	private void createPlayers(int numberOfPlayers) {
		this.players[0] = new Player ("red", "Player 1", new Piece [1]);
		this.players[1] = new Player ("yellow", "Player 2", new Piece [1]);

		if (numberOfPlayers > 2) {
			this.players[2] = this.players[2] = new Player ("green", "Player 3", new Piece [1]);
		}
		if (numberOfPlayers > 3) {
			this.players[3] = this.players[3] = new Player ("blue", "Player 4", new Piece [1]);
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

}