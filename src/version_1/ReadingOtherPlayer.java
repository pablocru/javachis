package version_1;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ReadingOtherPlayer extends Thread {
	//	Attributes
	private ViewGUI parchis;

	//	Constructor
	public ReadingOtherPlayer(ViewGUI parchis) {
		this.parchis = parchis;
	}

	//	Methods
	@Override
	public void run() {
		try {
			boolean lock = true;

			ObjectInputStream oI = parchis.getInput();
			while(lock) {
				parchis.updateStatus("Waiting for " + parchis.getColor() + "...");
				Object ob = oI.readObject();

				if (ob instanceof Game) {
					Game game = (Game) ob;
					parchis.setGame(game);
					
					parchis.updateStatus(game.getStatus());

					int newPosition = game.getCurrentMove();
					if (newPosition != 0) {
						parchis.movePiece();
					}
					parchis.initiateTurn();
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
