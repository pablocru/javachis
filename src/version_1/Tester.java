package version_1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Tester {
	private static Socket socket;
	private static ServerSocket server;
	private static ObjectOutputStream output;
	private static ObjectInputStream input;
	private static ReadingOtherPlayer reader;
	private final static int PORT = 5005;
	private final static String IP = "127.0.0.1";
	private static Game game = null;

	
	/**
	 * This method displays all the players the game has, with a for:each structure
	 */
	public static void displayPlayers() {
		for (Player player : game.getPlayers())
			System.out.println(player);
	}

	
	/**
	 * setClient creates a socket, prints Connecting to Server and returns the adress to which the socket is connected.
	 * First, it connects the Clients' Output with the Server's Input, and then the reverse.
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	private static void setClient() throws UnknownHostException, IOException {
		socket = new Socket(IP, PORT);
		System.out.println("Connecting to server" + socket.getInetAddress());
		
		ObjectOutputStream();
		ObjectInputStream();
	}

	
	/**
	 * setServer creates a server, it prints it's waiting for a client, until the server accepts a cliente, and then, it prints
	 * that it is connected, and it returns the socket adress. Then, a waiting time is of 10 seconds before the thread closes.
	 * 
	 * @throws IOException
	 */
	private static void setServer() throws IOException {
		server = new ServerSocket(PORT);

		System.out.println("Server waiting for a client...");
		socket = server.accept();
		System.out.println("Connected to client" + socket.getInetAddress());

		socket.setSoLinger(true, 10);
		
		ObjectInputStream();
		ObjectOutputStream();
	}
	
	/**
	 * ObjectInputStream reads data from the stream and wirtes it in input object . Then it creates a reader, and starts the thread
	 * in background
	 * @throws IOException
	 */
	private static void ObjectInputStream() throws IOException {
		input = new ObjectInputStream(socket.getInputStream());

//		reader = new ReadingOtherPlayer(input, game);
//		reader.start();
	}
	private static void ObjectOutputStream() throws IOException {
		output = new ObjectOutputStream(socket.getOutputStream());
	}

	
	/**
	 * startMenu asks user if he wants to start a game, or join other
	 * @return
	 */
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
			switch(startMenu()) {
			case 1:
				game = new Game(2);
				game.joinPlayer(0);
				setServer();
				System.out.println("voy a escribir el obj");
				output.writeObject(game);
				break;
			case 2:
				setClient();
				Thread.sleep(500);
				System.out.println("main: " + game);
				game.joinPlayer(1);
				break;
			case 0: 
				System.out.println("Exit"); 
				break;
			};
			
			displayPlayers();
			
//			output.println(game);
//
//			System.out.println();
			
			
//			while(!game.isFinish()) {
//				Player owner = game.getTurnOwner();
//				String color = owner.getColor();
//				System.out.println("Turn owner: " + color);
//
//				game.rollDice();
//				int dice = game.getDice();
//				System.out.println("Rolling dice... Takes: " + dice);
//				if (owner.isAnyoneHome()) {
//					if (dice == 5) {
//						System.out.println(color + " take piece from home");
//						game.startPiece(owner);
//						System.out.println(color + " is in " + owner.getPieces()[0].getPosition());
//					}
//					else System.out.println(color + " can't move");
//				}
//				else {
//					game.movePiece(owner, dice);
//					System.out.println(color + " moves to " + owner.getPieces()[0].getPosition());			
//				}
//				
//				game.switchOwner();
//				System.out.println();
//			}
//
//			System.out.println("Winner: " + game.getWinner().getColor());
		}
		catch (IOException e) {e.printStackTrace();} 
		catch (InterruptedException e) {e.printStackTrace();}
	}
}
