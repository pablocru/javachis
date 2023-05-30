package version_1;

public class Box {
//	Attributes
	private int boxNumber;
	private Piece firstPiece = null;
//	private Piece secondPiece = null;
//	private boolean isThereBridge = false;
	
//	Constructors
	public Box() {}
	
	public Box(int boxNumber) {
		this.boxNumber = boxNumber;
	}

//	Setters
	public int getBoxNumber() {
		return boxNumber;
	}
	
//	Methods
	public void movePiece(Box destination) {
		destination.firstPiece = this.firstPiece;
		this.firstPiece = null;
	}
}
