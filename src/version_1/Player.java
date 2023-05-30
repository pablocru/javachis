package version_1;

public class Player {
	private String color;
	private String name;
	private Piece [] pieces;
	private HomeBox home;
	
	public Player(String color, String name) {
		this.color = color;
		this.name = name;
		this.home = new HomeBox(this.pieces);
		this.setPieces();
	}

	public void setPieces() {
		this.pieces[0] = new Piece(1, this.home);
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

	public Piece [] getPieces() {
		return pieces;
	}
	
	
}
