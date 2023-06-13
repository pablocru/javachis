package version_1;

public class TestingDatabase {

	public static void main(String[] args) {
		Player Dani = new Player ("Dani", 0);
		boolean didIWin = true;
		Database.executeDatabase(Dani, didIWin);

	}

}
