package version_1;

<<<<<<< HEAD
import java.util.Random;

public class TestingDatabase {

	public static void main(String[] args) {

//		Database testDatabase = new Database();
//		System.out.println(Database.howManyPlayersInDatabase());
//		String [][] array = new String [2][3];
//		array = Database.savingDatabasePlayerInto2DArray();
//		
//		for (int i =0; i<array.length; i++) {
//			for (int j=0; j<array[i].length;j++) {
//				System.out.println(array[i][j]);
//			}
//		}
		
		System.out.println(rollDice());
		

		
		
	}
	
	public static int rollDice() {
		Random r = new Random ();
		int dice;
		int a = r.nextInt(1,4);
		int b = r.nextInt(1,5);
		int c = r.nextInt(5,7);
		
		if (a==1) {
			dice = b;
		}else dice = c;
				
		return dice;
=======
public class TestingDatabase {

	public static void main(String[] args) {
		Player Dani = new Player ("Dani", 0);
		boolean didIWin = true;
		Database.executeDatabase(Dani, didIWin);

>>>>>>> branch 'main' of https://github.com/pabcrudel/javachis.git
	}

}
