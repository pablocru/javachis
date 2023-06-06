package version_1;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

public class Game implements Serializable {
//	Attributes
	private static final int TOTAL_BOXES = 80;
	private Player [] players;
	private int turnOwner = 0;
	private Box [] boxes;
	private int dice;
	private boolean isFinish = false;
	private Player winner;

//	Constructors
	public Game(int numberOfPlayers) {
		this.players = new Player[numberOfPlayers];
		this.createBoxes();
	}

//	Getters
	public Player[] getPlayers() {return players;}
	
	public Player getPlayerByIndex(int index) {return this.players[index];}
	
	public Player getTurnOwner() {return players[turnOwner];}
	
	public Player getWinner() {return this.winner;}
	
	public int getDice() {return this.dice;}
	
//	Methods
	public void joinPlayer(int whoAmI) {
		players[whoAmI] = new Player("Player", whoAmI);
	}
	
	private void createBoxes() {
		this.boxes = new Box[TOTAL_BOXES];
		
		for (int i=0;i<TOTAL_BOXES; i++)
			boxes[i]=new Box(i);
	}
	
	public void rollDice() {
		this.dice = new Random().nextInt(1, 7);
	}
	
	public void switchOwner() {
		this.turnOwner = (this.turnOwner + 1) % players.length;
	}
	
	public void startPiece(Player owner) {
		owner.getPieceFromHome().setPosition(owner.getStartingBox());
	}
	
	public void movePiece(Player owner, int position) {
		Piece piece = owner.getPieces()[0];
		
		
		for (int i = 0; !this.isFinish && i < position; i++) {
			int box = piece.getPosition();
			
			piece.setPosition(box == TOTAL_BOXES ? 1 : box + 1);
			
			if (piece.getPosition() == owner.getStartingBox()) {
				this.isFinish = true;
				this.winner = owner;
			}
		}
	}
	
	public boolean isFinish() {return this.isFinish;}

	@Override
	public String toString() {
		return "players: " + Arrays.toString(players) + ", turnOwner: " + turnOwner + ", boxes: "
				+ Arrays.toString(boxes);
	}
}