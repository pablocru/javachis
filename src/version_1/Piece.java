package version_1;

public class Piece {
//	Attributes
	private int id;
	private int position;
	private boolean isInFinalBox=false;
	
//	Constructors
	public Piece(int id, int position) {
		this.id=id;
		this.position = position;
	}

//	Setters
	public void setPosition(int position) {
		this.position = position;
	}
	
//	Getters
	public int getId() {
		return id;
	}

	public int getPosition() {
		return position;
	}

//	Methods
	public boolean isInFinalBox() {
		return isInFinalBox;
	}
	
}
