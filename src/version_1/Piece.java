package version_1;

public class Piece {
//	Attributes
	private int id;
	private Box position;
	private boolean isInFinalBox=false;
	
//	Constructors
	public Piece(int id, Box position) {
		this.id=id;
		this.position = position;
	}

//	Setters
	public void setPosition(Box position) {
		this.position = position;
	}
	
//	Getters
	public int getId() {
		return id;
	}

	public Box getPosition() {
		return position;
	}

//	Methods
	public boolean isInFinalBox() {
		return isInFinalBox;
	}
	
}
