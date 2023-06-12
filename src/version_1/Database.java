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

	public static void executeDatabase (Player player, boolean didIWin) {
		//It creates a database
		Database myDatabase = new Database();

		//url of the database: javachis is the DB name
		String url="jdbc:mysql://localhost:4306/javachis"+ ""; 
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

		//if the player is in the array list, there is an update. If not, an insertion.
		if (isPlayerInArrayList(myDatabase, player)) {
			updatingDatabase (myDatabase,player,didIWin);
		}else  insertingPlayerInDatabase (myDatabase,player,didIWin);

		//if wanting to show the usernames stored in our database:
		//showingDatabaseUsernames(myDatabase);
	}

	public static void showingDatabaseUsernames (Database exampleDatabase) {
		//este metodo servirá para mostrar los datos de la base de datos en la interfaz
		try {
			System.out.println("connected. Showing usernames from database");

			String query="SELECT username FROM resultsTable";
			PreparedStatement statement = exampleDatabase.connection.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
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

			String query="SELECT username FROM resultsTable";
			PreparedStatement statement = exampleDatabase.connection.prepareStatement(query);
			ResultSet rs = statement.executeQuery();

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
		wonGames=queryWonGamesOfUsername(exampleDatabase,  player);

		//aqui hago un select de las partidas jugadas y lo guardo en la otra variable previamente inicializada
		playedGames=queryPlayedGamesOfUsername(exampleDatabase,  player);

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
			String query = "UPDATE resultsTable SET playedGames = ? WHERE username = ?";
			PreparedStatement statement = exampleDatabase.connection.prepareStatement(query);
			statement.setInt(1, playedGamesUpdated);
			statement.setString(2, username);

			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		//update de los wonGames
		try {
			String query = "UPDATE resultsTable SET wonGames = ? WHERE username = ?";
			PreparedStatement statement = exampleDatabase.connection.prepareStatement(query);
			statement.setInt(1, wonGamesUpdated);
			statement.setString(2, username);

			statement.executeUpdate();
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
			String query = "INSERT INTO resultsTable VALUES (?, ?, ?)";
			PreparedStatement statement = exampleDatabase.connection.prepareStatement(query);
			statement.setString(1, player.getName());
			statement.setInt(2, wonGames);
			statement.setInt(3, playedGames);

			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static int queryWonGamesOfUsername(Database exampleDatabase, Player player) {
		String username=player.getName();
		int wonGames=0;

		//aqui hago un select de las partidas ganadas y lo guardo en la variable previamente inicializada
		try {
			String query = "SELECT wonGames FROM resultsTable WHERE username = ?";
			PreparedStatement statement = exampleDatabase.connection.prepareStatement(query);
			statement.setString(1, username);

			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				wonGames = rs.getInt("wonGames");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return wonGames;
	}
	public static int queryPlayedGamesOfUsername(Database exampleDatabase, Player player) {
		String username=player.getName();
		int playedGames=0;

		//aqui hago un select de las partidas ganadas y lo guardo en la variable previamente inicializada
		try {
			String query = "SELECT playedGames FROM resultsTable WHERE username = ?";
			PreparedStatement statement = exampleDatabase.connection.prepareStatement(query);
			statement.setString(1, username);

			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				playedGames = rs.getInt("playedGames");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return playedGames;
	}
}
