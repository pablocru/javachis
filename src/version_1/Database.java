package version_1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Database {
	public static void main (String [] args) throws ClassNotFoundException {
		//here is the code for database connection

		//starting connection
		Connection connection=null;
		//url of the database: javachis is the DB name
		String url="jdbc:mysql://localhost:3306/javachis"+ ""; 
		//user of the database: root is selected
		String user="root";
		//password is alumnoalumno
		String password="alumnoalumno";
		String query="SELECT * from  resultsTable";


		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			//Trying the connection with the url, user and password given
			connection=DriverManager.getConnection(url,user,password);
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		} 

		showingData(connection, url, user, password);

	}
	public static void showingData (Connection connection, String url, String user, String password) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection=DriverManager.getConnection(url,user,password); 
			System.out.println("connected");
			Statement sentence=connection.createStatement();
			ResultSet rs=sentence.executeQuery("SELECT title, author FROM books");
			while (rs.next()) {
				System.out.println(rs.getString("title")+", "+rs.getString("author"));
				//tambien se puede hacer con numeros dependiendo del orden que tenga en la tabla la columna del atributo que se quiere seleccionar
			}
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}


}
