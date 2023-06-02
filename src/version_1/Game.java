package version_1;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Random;

public class Game {
//	Attributes
	private static final int RED_STARTING_BOX = 1;
	private static final int YELLOW_STARTING_BOX = 21;
	private static final int GREEN_STARTING_BOX = 41;
	private static final int BLUE_STARTING_BOX = 61;
	private static final int TOTAL_BOXES = 80;
	private Player [] players;
	private int turnOwner;
	private Box [] boxes;
	private boolean isFinish = false;
	private Player winner;

//	Constructors
	public Game(int numberOfPlayers) {
		
		this.createPlayers(numberOfPlayers);

		this.createBoxes();
		
		this.createTurn();
	}

//	Getters
	public Player[] getPlayers() {
		return players;
	}

	public Box[] getBoxes() {
		return boxes;
	}
	
	public int getTurnOwner() {
		return turnOwner;
	}
	
	public Player getWinner() {return this.winner;}
	
//	Methods
	private void createPlayers(int numberOfPlayers) {
		this.players = new Player[numberOfPlayers];
		
		this.players[0] = new Player ("red", "Player 1", RED_STARTING_BOX);
		
		if (numberOfPlayers > 1) {
			this.players[1] = new Player ("yellow", "Player 2", YELLOW_STARTING_BOX);
		}

		if (numberOfPlayers > 2) {
			this.players[2] = new Player("green", "Player 3", GREEN_STARTING_BOX);
		}
		
		if (numberOfPlayers > 3) {
			this.players[3] = new Player("blue", "Player 4", BLUE_STARTING_BOX);
		}
	}
	
	private void createBoxes() {
		this.boxes = new Box[TOTAL_BOXES];
		
		for (int i=0;i<TOTAL_BOXES; i++) {
			if (i==RED_STARTING_BOX||i==YELLOW_STARTING_BOX||i==GREEN_STARTING_BOX||i==BLUE_STARTING_BOX) {
				boxes[i]=new StartingBox(i);
			}
			else {
				boxes[i]=new Box(i);
			}
		}
	}
	
	private void createTurn() {
		this.turnOwner = 0;
	}
	
	private int rollDice() {
		return new Random().nextInt(1, 7);
	}
	
	private void switchOwner() {
		this.turnOwner = (this.turnOwner + 1) % players.length;
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
	
	public void initiateTurn() {
		int dice = rollDice();
		System.out.println("dice: " + dice);
		
		Player owner = this.players[this.turnOwner];
		
		if (owner.isAnyoneHome()) {
			if (dice == 5) {
				owner.getPieceFromHome().setPosition(owner.getStartingBox());	
				System.out.println(owner.getColor() + " is in " + owner.getPieces()[0].getPosition());
			} 
			else System.out.println("can't move");
		}
		else {
			this.movePiece(owner, dice);
			System.out.println(owner.getColor() + " is in " + owner.getPieces()[0].getPosition());			
		}
		
		this.switchOwner();
	}
	
	public boolean isFinish() {return this.isFinish;}

	@Override
	public String toString() {
		return "Game [players: " + Arrays.toString(players) + ", turnOwner: " + turnOwner + ", boxes: "
				+ Arrays.toString(boxes) + "]";
	}
}