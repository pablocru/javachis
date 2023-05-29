package version_1;

import version_1.Piece;

public class Player {
	private String color;
	private String name;
	private Piece piece;
	private HomeBox home;
	
	public Player(String color, String name) {
		this.color = color;
		this.name = name;
		this.piece = new Piece(1);
		this.home=new HomeBox(this);
	}
	
	
}
