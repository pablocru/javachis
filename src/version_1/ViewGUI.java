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
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

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
	private JPanel pane_parchis = new JPanelConFondoParchis("../img/Parchis_version_2.png");
	private JPanel pane_player = new JPanel();
	private JButton btn_createPlay = new JButton("Create play");
	private JButton btn_joinPlay = new JButton("Join play");
	private JButton btn_exit = new JButton("Exit");
	private JButton btn_move = new JButton("Move");
	private JButton btn_rollDice = new JButton("Roll dice");
	private JLabel lbl_wellcome = new JLabel("Wellcome to Javachis");
	private JLabel lbl_menu = new JLabel("What do you want to do?");
	private JLabel lbl_imageDice = new JLabel("");
	private JScrollPane scrollPane = new JScrollPane();
	private JTextArea textArea_status = new JTextArea();
	private int casillaRojo;
	private int casillaVerde;
	private int mueveFichas;

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


	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the frame.
	 */
	public ViewGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 1000);
		setContentPane(contentPane);

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.add(pane_initial);

		pane_initial.setBounds(15, 15, 1381, 900);
		pane_initial.setLayout(null);
		pane_initial.add(lbl_wellcome);
		pane_initial.add(lbl_menu);
		pane_initial.add(btn_createPlay);
		pane_initial.add(btn_joinPlay);
		pane_initial.add(btn_exit);

		lbl_wellcome.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_wellcome.setBounds(564, 178, 252, 14);

		lbl_menu.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_menu.setBounds(559, 203, 263, 14);

		contentPane.add(pane_parchis);
		contentPane.add(pane_player);

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

		textArea_status.setEditable(false);

		scrollPane.setViewportView(textArea_status);
		scrollPane.setBounds(34, 28, 413, 248);

		lbl_imageDice.setBounds(81, 480, 319, 313);
		lbl_imageDice.setIcon(new ImageIcon(ViewGUI.class.getResource("../img/dice1.png")));

		btn_createPlay.setBounds(636, 236, 108, 23);
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

		btn_joinPlay.setBounds(636, 270, 108, 23);
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

		btn_exit.setBounds(636, 304, 108, 23);
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
		this.game.joinPlayer(this.whoAmI);
		this.setServer();
		this.output.writeObject(this.game);
		this.setGame((Game) this.input.readObject());
	}

	private void joinPlay() throws UnknownHostException, IOException, ClassNotFoundException {
		this.whoAmI = 1;
		this.setClient();
		this.setGame((Game) this.input.readObject());
		this.game.joinPlayer(this.whoAmI);
		this.output.writeObject(this.game);
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

	private void setGame(Game game) {this.game = game;}

	private void messageDialog(String cta, String windowTitle) {
		JOptionPane.showMessageDialog(contentPane, cta, windowTitle, JOptionPane.INFORMATION_MESSAGE);
	}

	private boolean isMyTurn() {return game.getTurnOwnerInt() == whoAmI;}
	private boolean itsMe() {return game.getWinner().getWhoAmI() == whoAmI;}

	private void initiateTurn() throws ClassNotFoundException, IOException {
		turnOwner = this.game.getTurnOwnerPlayer();
		color = turnOwner.getColor();

		this.updateStatus("Turn owner: " + color);

		if (this.isMyTurn()) {
			this.updateStatus("It's your turn");
			this.enableDice();
		}
		else {
			this.updateStatus("Waiting for " + color + "...");
			this.setGame((Game) this.input.readObject());
			this.updateStatus(this.game.getStatus());

			newPosition = this.game.getCurrentMove();
			if (newPosition != 0) {
				if (color.equals("red")) move(pane_redPiece);
				else move(pane_greenPiece);
			}
			initiateTurn();
		}
	}

	private void rollDice() {
		dice = game.rollDice();
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

			if (color.equals("red")) move(pane_redPiece);
			else move(pane_greenPiece);

			break;
		}

		disableButtons();

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
		}
	}

	private void updateStatus(String status) {
		textArea_status.setText(textArea_status.getText() + status + "\n");
	}

	private void move(JPanel pane_piece) {
		if (newPosition<21) {
			pane_piece.setLocation((int)referenciaCasillas1A20.getX(), (int)referenciaCasillas1A20.getY() + 30 * (newPosition - 1));			
		}
		else if (newPosition<41) {
			pane_piece.setLocation((int)referenciaCasillas21A40.getX() + 29 * (newPosition - 21), (int)referenciaCasillas21A40.getY());
		}
		else if (newPosition<61) {
			pane_piece.setLocation((int)referenciaCasillas41A60.getX(), (int)referenciaCasillas41A60.getY() - 29 * (newPosition - 41));
		}
		else if (newPosition<81) {
			pane_piece.setLocation((int)referenciaCasillas61A80.getX() - 29 * (newPosition - 61), (int)referenciaCasillas61A80.getY());
		}
	}
}
