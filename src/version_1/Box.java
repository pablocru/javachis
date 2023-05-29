package version_1;

import version_1.Piece;

public class Box {
	private static int boxCounter = 1;
	private int boxNumber;
	private Piece firstPiece = null;
	private Piece secondPiece = null;
	private boolean isThereBridge = false;
	
	
	public Box(int boxNumber, Piece firstPiece, Piece secondPiece, boolean isThereBridge) {
		this.boxNumber = boxCounter++;
	}
	
	
	
}
