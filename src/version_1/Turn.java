package version_1;

public class Turn {
	private Player turnOrder[];
	private int turnOwner;
	private Player owner;
	
	public Turn(Player[] players) {
		for (int i = 0; i < players.length; i++) {
			Player player = players[i];
			if (player.getName() != null) this.turnOrder[i] = player;
		}
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
