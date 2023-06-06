package version_1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

/**
 * This class extends Thread, and it has 3 attributes: an input, a socket and a server
 * @author migguavil
 *
 */
public class ReadingOtherPlayer extends Thread {
	//	Attributes
	private ObjectInputStream input;
	private Controller parchis;

	//	Constructor
	public ReadingOtherPlayer(ObjectInputStream input, Controller parchis) {
		this.input = input;
		this.parchis = parchis;
	}

	//	Methods
	@Override
	public void run() {
		try {
			boolean lock = true;

			while(lock) {
				Object ob = input.readObject();
				
				if (ob instanceof Game) {
					Game game = (Game) ob;
					parchis.setGame(game);
				}
				else lock = false;	
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
