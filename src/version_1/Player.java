package version_1;

public class Player {
	private String color;
	private String name;
	private Piece piece;
	private HomeBox home;
	
	public Player() {
		this.home = new HomeBox(this);
	}
	
	public Player(String color, String name) {
		this.color = color;
		this.name = name;
		this.home = new HomeBox(this);
		this.setPiece();
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPiece() {
		this.piece = new Piece(1, this.home);
	}

	public HomeBox getHome() {
		return home;
	}

	public String getColor() {
		return color;
	}

	public String getName() {
		return name;
	}

	public Piece getPiece() {
		return piece;
	}
	
	
}
