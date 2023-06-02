package version_1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Tester {
	private static Socket socket;
	private static ServerSocket server;
	private final static int PORT = 5005;
	private final static String IP = "127.0.0.1";
	private static Game game;

	public static void displayPlayers() {
		for (Player player : game.getPlayers())
			System.out.println(player);
	}

	private static void setClient() throws UnknownHostException, IOException {
		socket = new Socket(IP, PORT);
		System.out.println("Connecting to server" + socket.getInetAddress());
	}

	private static void setServer() throws IOException {
		server = new ServerSocket(PORT);

		System.out.println("Server waiting for a client...");
		socket = server.accept();
		System.out.println("Connected to client" + socket.getInetAddress());

		socket.setSoLinger(true, 10);
	}

	public static int startMenu() {
		Scanner k = new Scanner(System.in);

		System.out.println("What do you want to do?");
		System.out.println("1) Start game");
		System.out.println("2) Join game");
		System.out.println("0) Exit");

		int res = k.nextInt(); k.nextLine();

		return (res == 1 || res == 2) ? res : 0;
	}

	public static void main(String[] args) {
		try {
			game = new Game(2);

			switch(startMenu()) {
			case 1:
				game.joinPlayer(0);
				setServer();
				break;
			case 2:
				game.joinPlayer(1);
				setClient();
				break;
			case 0: 
				System.out.println("Exit"); 
				break;
			};

			System.out.println();
			while(!game.isFinish()) {
				Player owner = game.getTurnOwner();
				String color = owner.getColor();
				System.out.println("Turn owner: " + color);

				game.rollDice();
				int dice = game.getDice();
				System.out.println("Rolling dice... Takes: " + dice);
				if (owner.isAnyoneHome()) {
					if (dice == 5) {
						System.out.println(color + " take piece from home");
						game.startPiece(owner);
						System.out.println(color + " is in " + owner.getPieces()[0].getPosition());
					}
					else System.out.println(color + " can't move");
				}
				else {
					game.movePiece(owner, dice);
					System.out.println(color + " moves to " + owner.getPieces()[0].getPosition());			
				}
				game.switchOwner();
				System.out.println();
			}

			System.out.println("Winner: " + game.getWinner().getColor());
		}
		catch (IOException e) {e.printStackTrace();}
	}
}
