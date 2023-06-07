package version_1;

import java.io.Serializable;
import java.util.Arrays;

public class Player implements Serializable {
//	Attributes
	protected static final int RED_STARTING_BOX = 1;
	protected static final int YELLOW_STARTING_BOX = 21;
	protected static final int GREEN_STARTING_BOX = 41;
	protected static final int BLUE_STARTING_BOX = 61;
	private String color;
	private String name;
	private Piece [] pieces = new Piece[1];
	private int startingBox;
	private int whoAmI;
	
//	Constructors
	public Player(String name, int whoAmI) {
		this.name = name;
		this.setPieces();
		this.whoAmI = whoAmI;
		
		switch(whoAmI) {
		case 0:
			this.color = "red";
			this.startingBox = RED_STARTING_BOX;
			break;
		case 1: 
			this.color = "yellow";
			this.startingBox = YELLOW_STARTING_BOX;
			break;
		case 2:
			this.color = "green";
			this.startingBox = GREEN_STARTING_BOX;
			break;
		case 3:
			this.color = "blue";
			this.startingBox = BLUE_STARTING_BOX;
			break;
		}
		
	}

//	Setters
	public void setPieces() {
		this.pieces[0] = new Piece(1, 0);
	}
	
//	Getters
	public String getColor() {return color;}

	public String getName() {return name;}
	
	public int getStartingBox() {return this.startingBox;}

	public Piece [] getPieces() {return pieces;}
	
	public int getWhoAmI() {return this.whoAmI;}
	
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
		return "color: " + color + ", name: " + name + ", pieces: " + Arrays.toString(pieces) + ", startingBox: "
				+ startingBox;
	}
}
