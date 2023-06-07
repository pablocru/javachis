package version_1;

import java.io.IOException;

/**
 * This class extends Thread, and it has 3 attributes: an input, a socket and a server
 * @author migguavil
 *
 */
public class ReadingOtherPlayer extends Thread {
	//	Attributes
	private Controller parchis;

	//	Constructor
	public ReadingOtherPlayer(Controller parchis) {
		this.parchis = parchis;
	}

	//	Methods
	@Override
	public void run() {
		try {
			boolean lock = true;

			while(lock) {
				System.out.println("Esperando...");
				Object ob = parchis.getInput().readObject();
				System.out.println("Leido");
				
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
