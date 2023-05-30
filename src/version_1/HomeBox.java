package version_1;

public class HomeBox extends Box{
	private Piece [] howManyPieces;

	public HomeBox(Piece [] howManyPieces) {
		this.howManyPieces = howManyPieces;
	}
	
	public boolean anyoneHome() {
		return howManyPieces != null && howManyPieces.length > 0;
	}
}
