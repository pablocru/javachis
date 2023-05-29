package version_1;

public class Turn {
	private String turnOrder[];
	public String[] getTurnOrder() {
		return turnOrder;
	}

	public int getTurnOwner() {
		return turnOwner;
	}

	private int turnOwner;
	private Player owner;
	
	public Turn(Player[] players) {
		
	}
	
}
