package version_1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class extends Thread, and it has 3 attributes: an input, a socket and a server
 * @author migguavil
 *
 */
public class ReadingOtherPlayer extends Thread {
	//	Attributes
	private ObjectInputStream input;
	private Socket socket;
	private ServerSocket server;

	//	Constructor
	public ReadingOtherPlayer(ObjectInputStream input, Socket socket, ServerSocket server) {
		this.input = input;
		this.socket = socket;
		this.server = server;
	}

	//	Methods
	@Override
	public void run() {
		try {
			boolean lock = true;

			while(lock) {
				System.out.println("voy a leer el obj");
				Game game = (Game) input.readObject();
				
				if (game == null) {
					socket.close();
					if (server != null) server.close();
				}
				else {
					this.displayPlayers(game);
				}				
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void displayPlayers(Game game) {
		for (Player player : game.getPlayers())
			System.out.println(player);
	}

}
