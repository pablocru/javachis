package version_1;

public class TestingDatabase {

	public static void main(String[] args) {

		Database testDatabase = new Database();
		System.out.println(Database.howManyPlayersInDatabase());
		String [][] array = new String [2][3];
		array = Database.savingDatabasePlayerInto2DArray();
		
		for (int i =0; i<array.length; i++) {
			for (int j=0; j<array[i].length;j++) {
				System.out.println(array[i][j]);
			}
		}
		
	}

}
