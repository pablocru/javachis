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
	private Random r = new Random();

//	Constructors
	public Game(int numberOfPlayers) {
		this.players = new Player[numberOfPlayers];
		this.createBoxes();
		r.setSeed(System.currentTimeMillis());
	}

//	Getters
	public Player[] getPlayers() {return players;}
	
	public Player getPlayerByIndex(int index) {return this.players[index];}
	
	public Player getPlayerByColor(String color) {
		boolean lock = true;
		Player player = null;
		
		for (int i = 0; lock && i < this.players.length; i++) {
			Player focusedPlayer = this.players[i];
			if (focusedPlayer.getColor().equals(color)) {
				player = focusedPlayer;
				lock = false;
			}
		}
		
		return player;
	}
	
	public Player getTurnOwnerPlayer() {return players[turnOwner];}
	
	public int getTurnOwnerInt() {return turnOwner;}
	
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
	
	public int rollDice() {
		int dice = r.nextInt(1, 7);
		
		this.dice = dice;
		
		return dice;
	}
	
	public void switchOwner() {
		this.turnOwner = (this.turnOwner + 1) % players.length;
	}
	
	public int startPiece() {
		Player owner = this.getTurnOwnerPlayer();
		
		owner.getPieceFromHome().setPosition(owner.getStartingBox());
		
		return owner.getStartingBox();
	}
	
	public int movePiece() {
		Player owner = this.getTurnOwnerPlayer();
		
		Piece piece = owner.getPieces()[0];
		
		int newPosition = 0;
		
		for (int i = 0; !this.isFinish && i < this.dice; i++) {
			int box = piece.getPosition();
			
			newPosition = box == TOTAL_BOXES ? 1 : box + 1;
			
			piece.setPosition(newPosition);
			
			if (piece.getPosition() == owner.getStartingBox()) {
				this.isFinish = true;
				this.winner = owner;
			}
		}
		
		return newPosition;
	}
	
	public boolean isFinish() {return this.isFinish;}

	@Override
	public String toString() {
		return "players: " + Arrays.toString(players) + ", turnOwner: " + turnOwner + ", boxes: "
				+ Arrays.toString(boxes);
	}
}