package version_1;

public class HomeBox extends Box{
	private Player owner;
	
	public Player getOwner() {
		return owner;
	}

	public HomeBox(Player owner) {
		super(0);
		this.owner = owner;
	}
}
