package version_1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

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
				Game game = (Game) input.readObject();
				
				if (game == null) lock = false;
				else parchis.setGame(game);			
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
