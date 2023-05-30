package version_1;

public class Player {
//	Attributes
	private String color;
	private String name;
	private Piece [] pieces;
	private HomeBox home;
	private int startingBox;
	
//	Constructors
	public Player(String color, String name, int startingBox) {
		this.color = color;
		this.name = name;
		this.home = new HomeBox(this.pieces);
		this.startingBox = startingBox;
		this.setPieces();
	}

//	Setters
	public void setPieces() {
		this.pieces[0] = new Piece(1, this.home);
	}
	
//	Getters
	public HomeBox getHome() {
		return home;
	}

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
			if (pieces[i].getPosition() instanceof HomeBox) {
				piece = pieces[i];
				lock = false;
			}
		}
		
		return piece;
	}
}
