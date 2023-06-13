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

			ObjectInputStream oI =parchis.getInput();
			while(lock) {
				Object ob = oI.readObject();

				if (ob instanceof Game) {
					parchis.setGame((Game) ob);
					
					parchis.updateTurn();
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
