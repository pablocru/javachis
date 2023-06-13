package version_1;

import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ViewGUI extends JFrame {
	//	Attributes
	//	Frame
	private Point referenciaCasillas1A20 = new Point(100,300);
	private Point referenciaCasillas21A40 = new Point(300, 750);
	private Point referenciaCasillas41A60 = new Point(700,567);
	private Point referenciaCasillas61A80 = new Point(570, 146);
	private JPanel contentPane = new JPanel();
	private JPanel pane_redPiece = new JPanel();
	private JPanel pane_greenPiece = new JPanel();
	private JPanel pane_initial = new JPanel();
	private JPanel pane_parchis = new ParchisBoardGUI("../img/Parchis_version_2.png");
	private JPanel pane_player = new JPanel();
	private JButton btn_createPlay = new JButton("Create play");
	private JButton btn_joinPlay = new JButton("Join play");
	private JButton btn_exit = new JButton("Exit");
	private JButton btn_move = new JButton("Move");
	private JButton btn_rollDice = new JButton("Roll dice");
	private JButton btn_data = new JButton("Statistics");
	private JLabel lbl_wellcome = new JLabel("Wellcome to Javachis");
	private JLabel lbl_menu = new JLabel("What do you want to do?");
	private JLabel lbl_imageDice = new JLabel("");
	private JScrollPane scrollPane = new JScrollPane();
	private JTextArea textArea_status = new JTextArea();

	//	Connection
	private Socket socket;
	private ServerSocket server;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private final int PORT = 5005;
	private final String IP = "127.0.0.1";

	//	Parchis
	private Game game;
	private int whoAmI;

	//	Used to show turn status to all players
	private String color;
	private int dice;
	private Player turnOwner;
	private int newPosition;
	private String status;

	// Setters
	public void setGame(Game game) {this.game = game;}

	// Getters
	public ObjectInputStream getInput() {return this.input;}

	// Main
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewGUI frame = new ViewGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Constructors
	public ViewGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 1000);
		setContentPane(contentPane);

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.add(pane_initial);
		contentPane.add(pane_parchis);
		contentPane.add(pane_player);

		pane_initial.setBounds(15, 15, 1381, 900);
		pane_initial.setLayout(null);
		pane_initial.add(lbl_wellcome);
		pane_initial.add(lbl_menu);
		pane_initial.add(btn_createPlay);
		pane_initial.add(btn_joinPlay);
		pane_initial.add(btn_exit);
		pane_initial.add(btn_data);

		pane_parchis.setBounds(15, 15, 900, 900);
		pane_parchis.setLayout(null);
		pane_parchis.add(pane_redPiece);
		pane_parchis.add(pane_greenPiece);

		pane_player.setBounds(915, 15, 481, 900);
		pane_player.setLayout(null);
		pane_player.add(lbl_imageDice);
		pane_player.add(btn_rollDice);
		pane_player.add(btn_move);
		pane_player.add(scrollPane);

		pane_redPiece.setBounds(100, 300, 28, 28);
		pane_redPiece.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		pane_redPiece.setBackground(Color.RED);

		pane_greenPiece.setBounds(700, 567, 28, 28);
		pane_greenPiece.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		pane_greenPiece.setBackground(new Color(124, 252, 0));

		lbl_wellcome.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_wellcome.setBounds(564, 178, 252, 14);

		lbl_menu.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_menu.setBounds(559, 203, 263, 14);

		lbl_imageDice.setBounds(81, 480, 319, 313);
		lbl_imageDice.setIcon(new ImageIcon(ViewGUI.class.getResource("../img/dice1.png")));

		textArea_status.setEditable(false);

		scrollPane.setViewportView(textArea_status);
		scrollPane.setBounds(34, 28, 413, 248);

		btn_createPlay.setBounds(636, 236, 110, 25);
		btn_createPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					btn_joinPlay.setEnabled(true);
					createPlay();
					pane_initial.setVisible(false);
					initiateTurn();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btn_joinPlay.setBounds(636, 270, 110, 25);
		btn_joinPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					btn_createPlay.setEnabled(true);
					joinPlay();
					pane_initial.setVisible(false);
					initiateTurn();
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btn_data.setBounds(636, 304, 110, 25);
		btn_data.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {StatisticWindowGUI.main(null);}
		});
		btn_exit.setBounds(636, 339, 110, 25);
		btn_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {dispose();}
		});
		btn_rollDice.setBounds(188, 399, 105, 27);
		btn_rollDice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {rollDice();}
		});
		btn_move.setBounds(188, 335, 105, 27);
		btn_move.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					executeTurn();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		disableButtons();
	}

	private void disableButtons() {
		btn_move.setEnabled(false);
		btn_rollDice.setEnabled(false);
	}	
	private void enableDice() {
		btn_move.setEnabled(false);
		btn_rollDice.setEnabled(true);
	}	
	private void enableMove() {
		btn_move.setEnabled(true);
		btn_rollDice.setEnabled(false);
	}

	private void createPlay() throws IOException, ClassNotFoundException {
		this.whoAmI = 0;
		this.game = new Game(2);
		this.game.joinPlayer(inputUsername(), this.whoAmI);
		this.setServer();
		this.output.writeObject(this.game);
		this.setGame((Game) this.input.readObject());

		ReadingOtherPlayer t = new ReadingOtherPlayer(this);
		t.start();
	}

	private void joinPlay() throws UnknownHostException, IOException, ClassNotFoundException {
		this.whoAmI = 1;
		this.setClient();
		this.setGame((Game) this.input.readObject());
		this.game.joinPlayer(inputUsername(), this.whoAmI);
		this.output.writeObject(this.game);

		ReadingOtherPlayer t = new ReadingOtherPlayer(this);
		t.start();
	}

	private void setClient() throws UnknownHostException, IOException {
		socket = new Socket(IP, PORT);
		this.messageDialog("Connecting to server" + socket.getInetAddress(), "Connection Status");

		ObjectOutputStream();
		ObjectInputStream();
	}

	private void setServer() throws IOException {
		server = new ServerSocket(PORT);

		this.messageDialog("Server waiting for a client...", "Connection Status");
		socket = server.accept();
		this.messageDialog("Connected to client" + socket.getInetAddress(), "Connection Status");

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

	private String inputUsername() {
		return JOptionPane.showInputDialog(contentPane, "What's your name?", "Set username", JOptionPane.PLAIN_MESSAGE);
	}

	private void messageDialog(String cta, String windowTitle) {
		JOptionPane.showMessageDialog(contentPane, cta, windowTitle, JOptionPane.INFORMATION_MESSAGE);
	}

	private boolean isMyTurn() {return game.getTurnOwnerInt() == whoAmI;}
	private boolean iWon() {return game.getWinner().getWhoAmI() == whoAmI;}

	private void initiateTurn() throws ClassNotFoundException, IOException {
		turnOwner = this.game.getTurnOwnerPlayer();
		color = turnOwner.getColor();

		this.updateStatus("Turn owner: " + color);
		contentPane.repaint();

		if (this.isMyTurn()) {
			this.updateStatus("It's your turn");
			this.enableDice();
		}
		else this.updateStatus("Waiting for " + this.color + "...");
	}

	public void updateTurn() throws ClassNotFoundException, IOException {
		this.updateStatus(this.game.getStatus());

		this.newPosition = this.game.getCurrentMove();

		if (this.newPosition != 0) this.movePiece();

		if (!this.game.isFinish()) this.initiateTurn();
		else saveGame();
	}

	private void rollDice() {
		dice = game.rollCheatDice();
		lbl_imageDice.setIcon(new ImageIcon(ViewGUI.class.getResource("../img/dice" + dice + ".png")));
		updateStatus("Rolling dice... " + dice + "!!");
		enableMove();
	}

	private void executeTurn() throws IOException, ClassNotFoundException {
		if (turnOwner.isAnyoneHome()) {
			if (dice == 5) {
				updateStatus("You can take piece from home");
				newPosition = game.startPiece();
			}
			else newPosition = 0;
		}
		else newPosition = game.movePiece();

		switch(newPosition) {
		case -1:
			status = "End of game";
			updateStatus(status);
			break;
		case 0: 
			status = "can't move";
			updateStatus("You " + status);
			break;
		default: 
			status = "moves to " + newPosition;
			updateStatus("Your piece " + status);
			break;
		}

		disableButtons();

		if (newPosition != 0) this.movePiece();

		if (newPosition != -1) {
			game.setStatus(color + " has taken " + dice + ": " + status);
			game.switchOwner();
			game.setCurrentMove(newPosition);
			output.writeObject(game);
			initiateTurn();
		}
		else {
			game.setStatus(color + " has won the game");
			game.setCurrentMove(newPosition);
			output.writeObject(game);
			saveGame();
		}
	}

	public void updateStatus(String status) {
		textArea_status.setText(textArea_status.getText() + status + "\n");
	}

	public void movePiece() {
		if (color.equals("red")) move(pane_redPiece);
		else move(pane_greenPiece);
	}

	private void move(JPanel pane_piece) {
		int position;

		if (!game.isFinish()) position = newPosition;
		else position = game.getWinner().getStartingBox();

		if (position<21) {
			pane_piece.setLocation((int)referenciaCasillas1A20.getX(), (int)referenciaCasillas1A20.getY() + 30 * (position - 1));			
		}
		else if (position<41) {
			pane_piece.setLocation((int)referenciaCasillas21A40.getX() + 29 * (position - 21), (int)referenciaCasillas21A40.getY());
		}
		else if (position<61) {
			pane_piece.setLocation((int)referenciaCasillas41A60.getX(), (int)referenciaCasillas41A60.getY() - 29 * (position - 41));
		}
		else if (position<81) {
			pane_piece.setLocation((int)referenciaCasillas61A80.getX() - 29 * (position - 61), (int)referenciaCasillas61A80.getY());
		}
	}

	private void saveGame() {
		if (whoAmI == 0) {
			for (Player player : game.getPlayers()) {
				Database.executeDatabase(player, game.getWinner().equals(player));
			}
		}
		pane_initial.setVisible(true);
		StatisticWindowGUI.main(null);
	}
}
