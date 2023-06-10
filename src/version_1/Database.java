package version_1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Database {
	private ArrayList<String> usernameArray = new ArrayList<>();
	private Connection connection;


	public Database() {
	}

	public ArrayList<String> getUsernameArray() {
		return usernameArray;
	}

	public void setUsernameArray(ArrayList<String> usernameArray) {
		this.usernameArray = usernameArray;
	}

	public static void main (String [] args) {
		//here is the code for database connection
		Database myDatabase = new Database();
		//ejemplo de añadido al string
		//		myDatabase.usernameArray[0]="hola";
		//		System.out.println(myDatabase.usernameArray[0]);

		//url of the database: javachis is the DB name
		String url="jdbc:mysql://localhost:33306/javachis"+ ""; 
		//user of the database: root is selected
		String user="root";
		//password is alumnoalumno
		String password="alumnoalumno";
		//starting connection

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			myDatabase.connection=DriverManager.getConnection(url,user,password); 
		} catch (Exception e) { 
			e.printStackTrace();
		} 

		savingUsernamesIntoAnArray(myDatabase);
		//estas dos variables son ejemplos, despues hay que conectarlas con el Controller
		String player="porRellenar";
		boolean winOrLose = true;
		if (isPlayerInArrayList(myDatabase)) {
			updatingDatabase (myDatabase,player,winOrLose);
		}else  insertingPlayerInDatabase (myDatabase,player,winOrLose);
		
		showingData(myDatabase);
	}

	public static void showingData (Database exampleDatabase) {
		//este metodo servirá para mostrar los datos en la interfaz
		try {
			System.out.println("connected. Showing usernames from ArrayList");
			Statement sentence=exampleDatabase.connection.createStatement();
			ResultSet rs=sentence.executeQuery("SELECT username, wonGames FROM resultsTable");
			while (rs.next()) {
				System.out.println(rs.getString("username"));
			}
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}

	//method for saving usernames into an arraylist
	public static void savingUsernamesIntoAnArray (Database exampleDatabase) {
		try {
			System.out.println("connected. Adding usernames to ArrayList");
			Statement sentence=exampleDatabase.connection.createStatement();

			ResultSet rs=sentence.executeQuery("SELECT username, wonGames FROM resultsTable");
			while (rs.next()) {
				String usernameToAdd = rs.getString("username");
				exampleDatabase.usernameArray.add(usernameToAdd);
			}
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
	
	public static boolean isPlayerInArrayList (Database exampleDatabase) {
		//este metodo revisa si el jugador está en el arraylist de la base de datos y devuelve un booleano
		return true;
	}
	
	public static void updatingDatabase (Database exampleDatabase, String player, boolean winOrLose) {
		//aqui se hace el update del jugador. Se accede SOLO SI EL JUGADOR EXISTE
		//si ha ganado, se hace +1 a wonGames y a playedGames
		//si ha perdido, se hace +1 solo a playedGames
	}
	
	public static void insertingPlayerInDatabase (Database exampleDatabase, String player, boolean winOrLose) {
		//aqui se hace el insert del jugador en la database. Se accede SOLO CUANDO EL JUGADOR NO EXISTE
		//si ha ganado, se pone 1 en wonGames y a playedGames
		//si ha perdido, se pone 1 solo en playedGames
	}
}
