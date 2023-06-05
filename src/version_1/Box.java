package version_1;

import java.io.Serializable;

public class Box implements Serializable {
//	Attributes
	private int boxNumber;
	private Piece firstPiece = null;
//	private Piece secondPiece = null;
//	private boolean isThereBridge = false;
	
//	Constructors
	public Box() {}
	
	public Box(int boxNumber) {
		this.boxNumber = ++boxNumber;
	}

//	Setters
	public int getBoxNumber() {
		return boxNumber;
	}
	
//	Methods
	@Override
	public String toString() {
		return "boxNumber: " + boxNumber + ", firstPiece: " + firstPiece;
	}
}
