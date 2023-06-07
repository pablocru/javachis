package version_1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Controller {
//	Attributes
	private Socket socket;
	private ServerSocket server;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ReadingOtherPlayer reader;
	private final int PORT = 5005;
	private final String IP = "127.0.0.1";
	private Game game;
	private static final int TIME_SLEEPING = 200;
	private int whoAmI;

//	Setters
	protected void setGame(Game game) {this.game = game;}
	
//	Getters
	protected ObjectInputStream getInput() {return this.input;}

//	Methods
	private void displayPlayers() {
		for (Player player : this.game.getPlayers())
			System.out.println(player);
		System.out.println();
	}

	private void setClient() throws UnknownHostException, IOException {
		socket = new Socket(IP, PORT);
		System.out.println("Connecting to server" + socket.getInetAddress());

		ObjectOutputStream();
		ObjectInputStream();
	}

	private void setServer() throws IOException {
		server = new ServerSocket(PORT);

		System.out.println("Server waiting for a client...");
		socket = server.accept();
		System.out.println("Connected to client" + socket.getInetAddress());

		socket.setSoLinger(true, 10);

		ObjectInputStream();
		ObjectOutputStream();
	}

	private void ObjectInputStream() throws IOException {
		input = new ObjectInputStream(socket.getInputStream());

		reader = new ReadingOtherPlayer(this);
		reader.start();
	}
	private void ObjectOutputStream() throws IOException {
		output = new ObjectOutputStream(socket.getOutputStream());
	}

	private static int startMenu() {
		Scanner k = new Scanner(System.in);

		System.out.println("What do you want to do?");
		System.out.println("1) Start game");
		System.out.println("2) Join game");
		System.out.println("0) Exit");

		int res = k.nextInt(); k.nextLine();

		return (res == 1 || res == 2) ? res : 0;
	}

//	Main
	public static void main(String[] args) {
		Controller parchis = new Controller();

		try {
			switch(startMenu()) {
			case 1:
				parchis.whoAmI = 0;
				parchis.game = new Game(2);
				parchis.game.joinPlayer(parchis.whoAmI);
				parchis.setServer();
				parchis.output.writeObject(parchis.game);
				Thread.sleep(TIME_SLEEPING);
				break;
			case 2:
				parchis.whoAmI = 1;
				parchis.setClient();
				Thread.sleep(TIME_SLEEPING/2);
				parchis.game.joinPlayer(parchis.whoAmI);
				parchis.output.writeObject(parchis.game);
				break;
			case 0: 
				System.out.println("Exit");
				break;
			};

			parchis.displayPlayers();
			
			while(!parchis.game.isFinish()) {
				Player owner = parchis.game.getTurnOwnerPlayer();
				String color = owner.getColor();
				
				System.out.println("Turn owner: " + color);
				if (parchis.game.getTurnOwnerInt() == parchis.whoAmI) {
					
					parchis.game.rollDice();
					System.out.println(parchis.game.getDice());
					if (parchis.game.getTurnOwnerPlayer().isAnyoneHome()) {
						if (parchis.game.getDice() == 5) {
							System.out.println(color + " take piece from home");
							parchis.game.startPiece();	
							System.out.println(color + " is in " + owner.getPieces()[0].getPosition());
						}
						else System.out.println(color + " can't move");
					}
					else {
						parchis.game.movePiece();
						System.out.println(color + " moves to " + owner.getPieces()[0].getPosition());
					}
					
					parchis.output.writeObject(parchis.game);
				}
				else Thread.sleep(TIME_SLEEPING);
				
				parchis.game.switchOwner();
				parchis.displayPlayers();
			}
			System.out.println("Winner: " + parchis.game.getWinner().getColor());
		}
		catch (IOException e) {e.printStackTrace();} 
		catch (InterruptedException e) {e.printStackTrace();}
	}
}
