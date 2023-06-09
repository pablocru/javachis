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

public class ViewGUI extends JFrame {
	//	Attributes
	//	Frame
	private Point referenciaCasillas1A20;
	private Point referenciaCasillas21A40;
	private Point referenciaCasillas41A60;
	private Point referenciaCasillas61A80;
	private JPanel contentPane = new JPanel();
	private JPanel pane_redPiece = new JPanel();
	private JPanel pane_greenPiece = new JPanel();
	private JPanel pane_initial = new JPanel();
	private JPanel pane_parchis = new JPanelConFondoParchis("../img/Parchis_version_2.png");
	private JPanel pane_player = new JPanel();
	private JButton btn_createPlay = new JButton("Create play");
	private JButton btn_joinPlay = new JButton("Join play");
	private JButton btn_exit = new JButton("Exit");
	private JButton btn_moveRed = new JButton("Mover Rojo");
	private JButton btn_moveGreen = new JButton("Mover Verde");
	private JButton btn_move = new JButton("Move");
	private JButton btn_rollDice = new JButton("Roll dice");
	private JLabel lbl_wellcome = new JLabel("Wellcome to Javachis");
	private JLabel lbl_menu = new JLabel("What do you want to do?");
	private JLabel lbl_muestraIteracionRojo = new JLabel("1");
	private JLabel lbl_muestraIteracionVerde = new JLabel("41");
	private JLabel lbl_coordenadasRoja = new JLabel("Coordenadas roja");
	private JLabel lbl_coordenadasVerde = new JLabel("Coordenadas Verde");
	private JLabel lbl_redBox = new JLabel("CasillaRojo");
	private JLabel lbl_greenBox = new JLabel("Casilla Verde");
	private JLabel lbl_resultDice = new JLabel("Result: ");
	private JLabel lbl_imageDice = new JLabel("");
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

	//	Static: used to show turn status to all players
	private static String color;
	private static int dice;
	private static Player turnOwner;
	private static int control;
	private static String status;


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
		contentPane.add(pane_parchis);
		contentPane.add(pane_player);

		pane_initial.setBounds(15, 15, 1381, 900);
		pane_initial.setLayout(null);
		pane_initial.add(lbl_wellcome);
		pane_initial.add(lbl_menu);
		pane_initial.add(btn_createPlay);
		pane_initial.add(btn_joinPlay);
		pane_initial.add(btn_exit);
		
		pane_parchis.setBounds(15, 15, 900, 900);
		pane_parchis.setLayout(null);
		pane_parchis.add(pane_redPiece);
		pane_parchis.add(pane_greenPiece);
		
		pane_player.setBounds(915, 15, 481, 900);
		pane_player.setLayout(null);
		pane_player.add(btn_moveRed);
		pane_player.add(lbl_coordenadasRoja);
		pane_player.add(lbl_coordenadasVerde);
		pane_player.add(lbl_muestraIteracionRojo);
		pane_player.add(lbl_muestraIteracionVerde);
		pane_player.add(btn_moveGreen);
		pane_player.add(lbl_redBox);
		pane_player.add(lbl_greenBox);
		pane_player.add(lbl_resultDice);
		pane_player.add(lbl_imageDice);
		pane_player.add(btn_rollDice);
		pane_player.add(btn_move);
		
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
		
		lbl_muestraIteracionRojo.setBounds(100, 81, 14, 17);
		lbl_muestraIteracionVerde.setBounds(100, 103, 14, 17);

		lbl_coordenadasRoja.setBounds(100, 37, 104, 17);
		lbl_coordenadasVerde.setBounds(100, 59, 116, 17);

		lbl_redBox.setBounds(100, 157, 66, 17);
		lbl_greenBox.setBounds(100, 179, 78, 17);

		lbl_resultDice.setBounds(100, 201, 60, 17);

		lbl_imageDice.setBounds(81, 543, 319, 313);
		lbl_imageDice.setIcon(new ImageIcon(ViewGUI.class.getResource("../img/dice1.png")));

		btn_createPlay.setBounds(636, 236, 108, 23);
		btn_createPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					btn_joinPlay.setEnabled(true);
					createPlay();
					pane_initial.setVisible(false);
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

		btn_rollDice.setBounds(188, 489, 105, 27);
		btn_rollDice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dice = game.rollDice();
				
				lbl_resultDice.setText("Result: "+ dice);
				lbl_imageDice.setIcon(new ImageIcon(ViewGUI.class.getResource("../img/dice" + dice + ".png")));
			}
		});
		btn_move.setBounds(188, 429, 105, 27);
		btn_move.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.movePiece();
				
			}
		});
		btn_moveGreen.setBounds(100, 125, 109, 27);
		btn_moveGreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				movement(lbl_muestraIteracionVerde, casillaVerde, pane_greenPiece, lbl_coordenadasVerde);
			}
		});
		btn_moveRed.setBounds(272, 125, 105, 27);
		btn_moveRed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				movement(lbl_muestraIteracionRojo, casillaRojo, pane_redPiece, lbl_coordenadasRoja);
			}
		});

		//Escrito por MA

		//estas dos de abajo se meten en un bucle y cada vez que se da al boton se iteran
		referenciaCasillas1A20 = pane_redPiece.getLocation();
		referenciaCasillas21A40 = pane_redPiece.getLocation();
		referenciaCasillas41A60 = pane_redPiece.getLocation();
		referenciaCasillas61A80 = pane_redPiece.getLocation();

		referenciaCasillas1A20.setLocation(100,300);
		referenciaCasillas21A40.setLocation(300, 750);
		referenciaCasillas41A60.setLocation(700,567);
		referenciaCasillas61A80.setLocation(570, 146);
	}

	private void movement(JLabel muestraIteracion, int casilla, JPanel ficha, JLabel coordenadas) {
		//Se ejecuta un bucle porque es necesario que haga un check del numero de casilla que tiene
		boolean in=true;
		while (in) {
			//el label que muestra la iteracion
			muestraIteracion.setText(String.valueOf(Integer.parseInt(muestraIteracion.getText())+mueveFichas));

			//variable que se necesita para no hacer un gettext de muestraIteracion
			casilla = Integer.parseInt(muestraIteracion.getText()); 	

			//estos puntos no se para que están muy bien, pero los dejo ahi por si acaso 
			Point casilla2= new Point(100, 302);
			Point casilla22=new Point(300, 750);
			Point casilla42=new Point(700, 567);

			if (casilla<21) {
				if (casilla==1) {
					//esta es la primera casilla del bloque, que es diferente a las demás
					ficha.setLocation((int)referenciaCasillas1A20.getX(), (int)referenciaCasillas1A20.getY());
					//cuando es 1, se hace un set de las referencias vacias
				}
				else {
					//si no es la 1, se hace un setter de las referencias mas una suma del numero de casillas de diferencia que tiene con la primera del bloque (que es la 1)
					ficha.setLocation((int)casilla2.getX(), (int)casilla2.getY()+30*(casilla-1));
				}
				//cuando esto se da, el bucle pasa a false
				in=false;
			}else if (casilla<41) {
				if (casilla==21) {
					ficha.setLocation((int)referenciaCasillas21A40.getX(), (int)referenciaCasillas21A40.getY());

				}
				else {
					ficha.setLocation((int)casilla22.getX()+29*(casilla-21), (int)casilla22.getY());
				}
				in=false;
			}else if (casilla<61) {
				if (casilla==41) {
					ficha.setLocation((int)referenciaCasillas41A60.getX(), (int)referenciaCasillas41A60.getY());
				}
				else {
					ficha.setLocation((int)casilla42.getX(), (int)casilla42.getY()-29*(casilla-41));
				}
				in=false;
			}else if (casilla<81) {
				ficha.setLocation((int)referenciaCasillas61A80.getX()-29*(casilla-61), (int)referenciaCasillas61A80.getY());
				in=false;
			}				
			if (casilla>=81) {
				int res = casilla-80;
				muestraIteracion.setText(Integer.toString(res));
				casilla= Integer.parseInt(muestraIteracion.getText());
				ficha.setLocation((int)casilla2.getX(), (int)casilla2.getY()+30*(casilla-1));
			}
			if (casilla==1) {
				ficha.setLocation((int)referenciaCasillas1A20.getX(), (int)referenciaCasillas1A20.getY());

			}
			in=false;
		}
		coordenadas.setText(ficha.getLocation()+"."); //EL PUNTO NO LO TOQUES, QUE SI NO, NO VA
		//Se hace un setText de las coordenadas de la ficha, para ayudarnos con los puntos
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

	private String inputDialog(String cta, String windowTitle) {
		return JOptionPane.showInputDialog(contentPane, cta, windowTitle, JOptionPane.PLAIN_MESSAGE);
	}

	private void messageDialog(String cta, String windowTitle) {
		JOptionPane.showMessageDialog(contentPane, cta, windowTitle, JOptionPane.INFORMATION_MESSAGE);
	}
}
