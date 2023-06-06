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

//	Setters
	protected void setGame(Game game) {this.game = game;}

	//	Getters
	private Game getGame() {return this.game;}

	//	Methods
	private void displayPlayers() {
		for (Player player : this.game.getPlayers())
			System.out.println(player);
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

		reader = new ReadingOtherPlayer(input, this);
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
		int timeSleeping = 100;

		try {
			switch(startMenu()) {
			case 1:
				parchis.game = new Game(2);
				parchis.game.joinPlayer(0);
				parchis.setServer();
				parchis.output.writeObject(parchis.game);
				Thread.sleep(timeSleeping*2);
				break;
			case 2:
				parchis.setClient();
				Thread.sleep(timeSleeping);
				parchis.game.joinPlayer(1);
				parchis.output.writeObject(parchis.game);
				break;
			case 0: 
				System.out.println("Exit");
				break;
			};

			parchis.displayPlayers();
		}
		catch (IOException e) {e.printStackTrace();} 
		catch (InterruptedException e) {e.printStackTrace();}
	}
}
