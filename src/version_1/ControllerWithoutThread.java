package version_1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ControllerWithoutThread {
//	Attributes
	//	Non static	
	private Socket socket;
	private ServerSocket server;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private final int PORT = 5005;
	private final String IP = "127.0.0.1";
	private Game game;
	private int whoAmI;
	
	//	Static: used to show turn status to all players
	private static String color;
	private static int dice;
	private static Player turnOwner;
	private static int control;
	private static String status;

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
	}
	private void ObjectOutputStream() throws IOException {
		output = new ObjectOutputStream(socket.getOutputStream());
	}
	
	private boolean isMyTurn() {return game.getTurnOwnerInt() == whoAmI;}
	private boolean itsMe() {return game.getWinner().getWhoAmI() == whoAmI;}

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
		ControllerWithoutThread parchis = new ControllerWithoutThread();

		try {
			switch(startMenu()) {
			case 1:
				parchis.whoAmI = 0;
				parchis.game = new Game(2);
				parchis.game.joinPlayer(parchis.whoAmI);
				parchis.setServer();
				parchis.output.writeObject(parchis.game);
				parchis.setGame((Game) parchis.input.readObject());
				break;
			case 2:
				parchis.whoAmI = 1;
				parchis.setClient();
				parchis.setGame((Game) parchis.input.readObject());
				parchis.game.joinPlayer(parchis.whoAmI);
				parchis.output.writeObject(parchis.game);
				break;
			case 0: 
				System.out.println("Exit");
				break;
			};
			
			System.out.println();
			parchis.displayPlayers();
			
			while(!parchis.game.isFinish()) {
				turnOwner = parchis.game.getTurnOwnerPlayer();
				color = turnOwner.getColor();
				
				System.out.println("Turn owner: " + color);
				if (parchis.isMyTurn()) {
					System.out.println("It's your turn");
					
					dice = parchis.game.rollCheatDice();
					System.out.println("Rolling dice... " + dice + "!!");
					
					if (turnOwner.isAnyoneHome()) {
						if (dice == 5) {
							System.out.println("You can take piece from home");
							control = parchis.game.startPiece();
						}
						else control = 0;
					}
					else control = parchis.game.movePiece();
					
					switch(control) {
					case 0: 
						status = "can't move";
						System.out.println("You " + status);
					break;
					default: 
						status = "moves to " + control;
						System.out.println("Your piece " + status);
					break;
					}
					
					parchis.output.writeObject(parchis.game);
				}
				else {
					System.out.println("Waiting for " + color + "...");
					parchis.setGame((Game) parchis.input.readObject());
					System.out.println(color + " has taken " + dice + ": " + status);
				}
				
				parchis.game.switchOwner();
				parchis.displayPlayers();
			}
			System.out.println("Winner: " + parchis.game.getWinner().getColor());
			System.out.println((parchis.itsMe() ? "Congratulations" : "Loser") + "!!");
		}
		catch (IOException e) {e.printStackTrace();} 
		catch (ClassNotFoundException e) {e.printStackTrace();}
	}
}
