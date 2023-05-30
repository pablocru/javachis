package version_1;

public class Player {
//	Attributes
	private String color;
	private String name;
	private Piece [] pieces;
	private int startingBox;
	
//	Constructors
	public Player(String color, String name, int startingBox) {
		this.color = color;
		this.name = name;
		this.startingBox = startingBox;
		this.setPieces();
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
}
