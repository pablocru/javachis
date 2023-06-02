package version_1;

public class Piece {
//	Attributes
	private int id;
	private int position;
	
//	Constructors
	public Piece(int id, int position) {
		this.id = id;
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
	@Override
	public String toString() {
		return "id: " + id + ", position: " + position;
	}
}
