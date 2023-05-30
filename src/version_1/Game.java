package version_1;

import java.util.Random;

public class Game {
	private static final int RED_STARTING_BOX = 1;
	private static final int YELLOW_STARTING_BOX = 21;
	private static final int GREEN_STARTING_BOX = 41;
	private static final int BLUE_STARTING_BOX = 61;
	private Player [] players;
	private int turnOwner;
	private Box [] boxes;


	public Game(int numberOfPlayers) {
		
		this.createPlayers(numberOfPlayers);

		this.createBoxes();
		
		this.createTurn();
	}

	private void createPlayers(int numberOfPlayers) {
		this.players[0] = new Player ("red", "Player 1", RED_STARTING_BOX);
		
		this.players[1] = new Player ("yellow", "Player 2", YELLOW_STARTING_BOX);

		if (numberOfPlayers > 2) {
			this.players[2] = new Player("green", "Player 3", GREEN_STARTING_BOX);
		}
		
		if (numberOfPlayers > 3) {
			this.players[3] = new Player("blue", "Player 4", BLUE_STARTING_BOX);
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
		this.turnOwner = 0;
	}
	
	private int rollDice() {
		return new Random().nextInt(7);
	}
	
	private void switchOwner() {
		this.turnOwner = (this.turnOwner + 1) % 4;
	}
	
	private void movePiece(int positionNumber) {
//		Piece piece = owner.getPieceFromHome();
	}
	
	public void initiateTurn() {
		int dice = rollDice();
		
//		if (dice == 5 && owner.getHome().isAnyoneHome()) {
//			movePiece(owner.getStartingBox());
//		}
	}

	public Player[] getPlayers() {
		return players;
	}

	public Box[] getBoxes() {
		return boxes;
	}
	
	public int getTurnOwner() {
		return turnOwner;
	}

}