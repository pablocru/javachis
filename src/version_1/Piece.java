package version_1;

public class Piece {
	private int id;
	private Box position;
	private boolean isInFinalBox=false;
	
	public Piece(int id, Box position) {
		this.id=id;
		this.position = position;
	}
	
}
