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
		Player playerExample = new Player ("Ejemplo",1);
		

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
		String player="porRellenar";
		boolean iWon = true; //variable de ejemplo a conectar con el controlador
		if (isPlayerInArrayList(myDatabase, playerExample)) {
			updatingDatabase (myDatabase,playerExample,iWon);
		}else  insertingPlayerInDatabase (myDatabase,player,iWon);
		
		showingData(myDatabase);
	}

	public static void showingData (Database exampleDatabase) {
		//este metodo servirá para mostrar los datos en la interfaz
		try {
			System.out.println("connected. Showing usernames from database");
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
	
	public static boolean isPlayerInArrayList (Database exampleDatabase, Player player1) {
		//este metodo revisa si el jugador está en el arraylist de la base de datos y devuelve un booleano
		boolean back=false; //se inicializa a falso
		for (int i=1;i<exampleDatabase.usernameArray.size();i++) {
			if (player1.getName()==exampleDatabase.usernameArray.get(i)) {
				back= true;
			}else back= false;
		}
		return back;
	}
	
	public static void updatingDatabase (Database exampleDatabase, Player player, boolean iWon) {
		//aqui se hace el update del jugador. Se accede SOLO SI EL JUGADOR EXISTE
		//si ha ganado, se hace +1 a wonGames y a playedGames
		//si ha perdido, se hace +1 solo a playedGames
		String username=player.getName();
		int wonGames=0;
		int playedGames=0;
		
		//aqui hago un select de las partidas ganadas y lo guardo en una variable
		try {
			Statement sentence=exampleDatabase.connection.createStatement();
			ResultSet rs=sentence.executeQuery("SELECT wonGames FROM resultsTable WHERE username ='"+username+"'");
			wonGames = rs.getInt("wonGames");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//aqui hago un select de las partidas jugadas y lo guardo en otra variable
		try {
			Statement sentence=exampleDatabase.connection.createStatement();
			ResultSet rs=sentence.executeQuery("SELECT playedGames FROM resultsTable WHERE username ='"+username+"'");
			playedGames = rs.getInt("playedGames");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//aqui creo dos variables de los valores de partidas ganadas y jugadas actualizada, porque las necesito para el UPDATE
		int wonGamesUpdated;
		int playedGamesUpdated;
		
		//aqui, si se gana, se iteran ambas y si se pierde, solo la de partidas jugadas
		if (iWon) {
			wonGamesUpdated=wonGames+1;
			playedGamesUpdated=playedGames+1;
		} else { 
			playedGamesUpdated=playedGames+1;
		}
		try {
			Statement sentence=exampleDatabase.connection.createStatement();
			ResultSet rs=sentence.executeQuery("UPDATE resultsTable SET playedGames = "+playedGamesUpdated+"WHERE username ='"+username+"'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static void insertingPlayerInDatabase (Database exampleDatabase, String player, boolean winOrLose) {
		//aqui se hace el insert del jugador en la database. Se accede SOLO CUANDO EL JUGADOR NO EXISTE
		//si ha ganado, se pone 1 en wonGames y a playedGames
		//si ha perdido, se pone 1 solo en playedGames
	}
}
