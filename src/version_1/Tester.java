package version_1;

public class Tester {
	private static Game game;
	
	public static void displayTurnOwner() {
		System.out.println("Turn owner: " + game.getPlayers()[game.getTurnOwner()].getColor());
	}
	
	public static void play() {
		while(!game.isFinish()) {
			displayTurnOwner();
			game.initiateTurn();
			System.out.println("--------------------------------\n");
		}
		System.out.println("Winner: " + game.getWinner());
	}
	
	public static void main(String[] args) {
		play();
	}
}
