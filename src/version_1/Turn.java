package version_1;

public class Turn {
	private Player turnOrder[];
	private int turnOwner;
	private Player owner;
	
	public Turn(Player[] players) {
		this.turnOrder = players;
		this.turnOwner = 0;
		this.owner = this.turnOrder[this.turnOwner];
	}
	
	public Player[] getTurnOrder() {
		return turnOrder;
	}
	
	public int getTurnOwner() {
		return turnOwner;
	}
	
}
