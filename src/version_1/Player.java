package version_1;

import java.util.Arrays;

public class Player {
//	Attributes
	private static int playerCount = 0;
	private static final int RED_STARTING_BOX = 1;
	private static final int YELLOW_STARTING_BOX = 21;
	private static final int GREEN_STARTING_BOX = 41;
	private static final int BLUE_STARTING_BOX = 61;
	private String color;
	private String name;
	private Piece [] pieces = new Piece[1];
	private int startingBox;
	private int me;
	
//	Constructors
	public Player(String color, String name, int startingBox, int me) {
		this.color = color;
		this.name = name;
		this.startingBox = startingBox;
		this.setPieces();
		this.me = me;
	}

//	Setters
	public void setPieces() {
		this.pieces[0] = new Piece(1, 0);
	}
	
//	Getters
	public String getColor() {
		return color;
	}

	public String getName() {
		return name;
	}
	
	public int getStartingBox() {
		return this.startingBox;
	}

	public Piece [] getPieces() {
		return pieces;
	}
	
	public Piece getPieceFromHome() {
		boolean lock = true;
		Piece piece = null;
		
		for (int i = 0; lock && i < pieces.length; i++) {			
			if (pieces[i].getPosition() == 0) {
				piece = pieces[i];
				lock = false;
			}
		}
		
		return piece;
	}
	
//	Methods
	public boolean isAnyoneHome() {
		boolean isInHome = false;
		
		for (int i = 0; !isInHome && i < this.pieces.length; i++)
			if (pieces[i].getPosition() == 0) isInHome = true;
		
		return isInHome;		
	}

	@Override
	public String toString() {
		return "Player [color: " + color + ", name: " + name + ", pieces: " + Arrays.toString(pieces) + ", startingBox: "
				+ startingBox + "]";
	}
}
