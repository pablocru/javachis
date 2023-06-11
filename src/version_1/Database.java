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
		
		//datos del usuario de ejemplo que vamos a introducir: nombre, numero y si gana
		Player playerExample = new Player ("Ejemplo",1);
		boolean iWon=true;


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

		//First of all: saving database usernames (primary keys) into an array
		savingUsernamesIntoAnArray(myDatabase);
		
		//para ver los datos dentro del array list
		System.out.println(myDatabase.usernameArray.toString());
		
		//if the player is in the array list, there is an update. If not, an insertion.
		if (isPlayerInArrayList(myDatabase, playerExample)) {
			updatingDatabase (myDatabase,playerExample,iWon);
		}else  insertingPlayerInDatabase (myDatabase,playerExample,iWon);

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

			ResultSet rs=sentence.executeQuery("SELECT username FROM resultsTable");
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
		for (int i=0;!back&&i<exampleDatabase.usernameArray.size();i++) {
			if (player1.getName().equals(exampleDatabase.usernameArray.get(i))) {
				back=true;
			}
		}
		return back;
	}

	public static void updatingDatabase (Database exampleDatabase, Player player, boolean iWon) {
		//aqui se hace el update del jugador. Se accede (desde el main) SOLO SI EL JUGADOR EXISTE
		//se inicializa el nombre del jugador y los wonGames y los playedGames a 0
		String username=player.getName();
		int wonGames=0;
		int playedGames=0;

		//aqui hago un select de las partidas ganadas y lo guardo en la variable previamente inicializada
		try {
			Statement sentence=exampleDatabase.connection.createStatement();
			ResultSet rs=sentence.executeQuery("SELECT wonGames FROM resultsTable WHERE username ='"+username+"'");
			wonGames = rs.getInt("wonGames");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//aqui hago un select de las partidas jugadas y lo guardo en la otra variable previamente inicializada
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
			wonGamesUpdated=wonGames;
			playedGamesUpdated=playedGames+1;
		}

		//update de los playedGames
		try {
			Statement sentence=exampleDatabase.connection.createStatement();
			sentence.executeUpdate("UPDATE resultsTable SET playedGames = "+playedGamesUpdated+"WHERE username ='"+username+"'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//update de los wonGames
		try {
			Statement sentence=exampleDatabase.connection.createStatement();
			sentence.executeUpdate("UPDATE resultsTable SET wonGames = "+wonGamesUpdated+"WHERE username ='"+username+"'");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void insertingPlayerInDatabase (Database exampleDatabase, Player player, boolean iWon) {
		//aqui se hace el insert del jugador en la database. Se accede SOLO CUANDO EL JUGADOR NO EXISTE
		//si ha ganado, se pone 1 en wonGames y a playedGames
		//si ha perdido, se pone 1 solo en playedGames
		
		//se crea variable de playedGames inicializada a 1
		int playedGames=1;
		//se crea variable wonGames, que depende de si el jugador ha tenido la victoria en su primera partida para ser 0 o 1. 
		int wonGames=0;
		if (iWon) {
			wonGames=1;
		}
		
		//insert del jugador
		try {
			Statement sentence=exampleDatabase.connection.createStatement();
			sentence.executeUpdate("INSERT INTO resultsTable VALUES ('"+player.getName()+"',"+wonGames+","+playedGames+")");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
}
