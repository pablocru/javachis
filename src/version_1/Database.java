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

	

	public Database() {
	}

	public ArrayList<String> getUsernameArray() {
		return usernameArray;
	}

	public void setUsernameArray(ArrayList<String> usernameArray) {
		this.usernameArray = usernameArray;
	}

	public static void main (String [] args) throws ClassNotFoundException {
		//here is the code for database connection
		Database myDatabase = new Database();
		//ejemplo de añadido al string
//		myDatabase.usernameArray[0]="hola";
//		System.out.println(myDatabase.usernameArray[0]);
		//starting connection
		Connection connection=null;
		//url of the database: javachis is the DB name
		String url="jdbc:mysql://localhost:33306/javachis"+ ""; 
		//user of the database: root is selected
		String user="root";
		//password is alumnoalumno
		String password="alumnoalumno";
		String query="SELECT * from  resultsTable";
		
//		//necesito traer dos cosas del juego: el nombre del jugador local y si ha ganado la partida
//		//este ejemplo hay que cambiarlo al ejemplo del jugador local
//		Player myPlayer = new Player("example1", 0);
//		boolean haveIWon=true;
//		
//		//primero se realiza un select username de la tabla para que saque todos los username
//		
		
		
		

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			//Trying the connection with the url, user and password given
			connection=DriverManager.getConnection(url,user,password);
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		} 

//		showingData(connection, url, user, password);
		savingUsernamesIntoAnArray(connection, url, user, password, myDatabase);
	}
	public static void showingData (Connection connection, String url, String user, String password) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection=DriverManager.getConnection(url,user,password); 
			System.out.println("connected");
			Statement sentence=connection.createStatement();
			ResultSet rs=sentence.executeQuery("SELECT username, wonGames FROM resultsTable");
			while (rs.next()) {
				System.out.println(rs.getString("username")+", "+rs.getString("wonGames"));
				//tambien se puede hacer con numeros dependiendo del orden que tenga en la tabla la columna del atributo que se quiere seleccionar
			}
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
	
	//method for saving usernames into an array
	public static void savingUsernamesIntoAnArray (Connection connection, String url, String user, String password, Database exampleDatabase) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection=DriverManager.getConnection(url,user,password); 
			System.out.println("connected");
			Statement sentence=connection.createStatement();
			ResultSet rs=sentence.executeQuery("SELECT username, wonGames FROM resultsTable");
			while (rs.next()) {
				System.out.println(rs.getString("username"));
				String usernameToAdd = rs.getString("username");
				exampleDatabase.usernameArray.add(usernameToAdd);
			}
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}

}
