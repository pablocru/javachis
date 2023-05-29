package version_1;

public class Player {
	private String color;
	private String name;
	private Piece piece;
	private HomeBox home;
	
	public Player(String color, String name) {
		this.color = color;
		this.name = name;
		this.home = new HomeBox(this);
		this.piece = new Piece(1, this.home);
	}
	
	
}
