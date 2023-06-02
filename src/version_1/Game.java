package version_1;

import java.util.Arrays;
import java.util.Random;

public class Game {
//	Attributes
	private static final int TOTAL_BOXES = 80;
	private Player [] players;
	private int turnOwner = 0;
	private Box [] boxes;
	private boolean isFinish = false;
	private Player winner;

//	Constructors
	public Game(int numberOfPlayers) {
		this.players = new Player[numberOfPlayers];
		this.createBoxes();
	}

//	Getters
	public Player[] getPlayers() {
		return players;
	}
	
	public int getTurnOwner() {
		return turnOwner;
	}
	
	public Player getWinner() {return this.winner;}
	
//	Methods
	public void joinPlayer(int whoami) {
		for (int i = 0; i < players.length; i++) {
			players[i] = new Player("Player", i == whoami);
		}
	}
	
	private void createBoxes() {
		this.boxes = new Box[TOTAL_BOXES];
		
		for (int i=0;i<TOTAL_BOXES; i++) {
			if (i==Player.RED_STARTING_BOX||i==Player.YELLOW_STARTING_BOX||i==Player.GREEN_STARTING_BOX||i==Player.BLUE_STARTING_BOX) {
				boxes[i]=new StartingBox(i);
			}
			else {
				boxes[i]=new Box(i);
			}
		}
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