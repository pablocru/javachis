package version_1;

public class HomeBox extends Box{
//	Attributes
	private Piece [] howManyPieces;

//	Constructors
	public HomeBox(Piece [] howManyPieces) {
		this.howManyPieces = howManyPieces;
	}
	
//	Methods
	public boolean isAnyoneHome() {
		return howManyPieces != null && howManyPieces.length > 0;
	}
}
