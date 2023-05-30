package version_1;

public class HomeBox extends Box{
//	Attributes
	private Piece [] pieces;

//	Constructors
	public HomeBox(Piece [] howManyPieces) {
		this.pieces = howManyPieces;
	}
	
//	Methods
	public boolean isAnyoneHome() {
		return pieces != null && pieces.length > 0;
	}
}
