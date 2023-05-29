package version_1;

import version_1.Piece;
import version_1.Player;

public class Game {
	private Player [] players;


	public Game(int numberOfPlayers) {
		Piece [] player1pieces= new Piece [1];
		Piece [] player2pieces= new Piece [1];
		this.players[0] = new Player ("red", "Player 1", player1pieces);
		this.players[1] = new Player ("yellow", "Player 2", player2pieces);
		
		if (numberOfPlayers > 2) {
			Piece [] player3pieces= new Piece [1];
			this.players[2] = this.players[2] = new Player ("green", "Player 3", player3pieces);
		}
		if (numberOfPlayers > 3) {
			Piece [] player4pieces= new Piece [1];
			this.players[3] = this.players[3] = new Player ("blue", "Player 4", player4pieces);
		}
		
	}

}