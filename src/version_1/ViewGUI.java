package version_1;

import java.awt.EventQueue;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;

public class ViewGUI extends JFrame {
	//	Attributes
	//	Frame
	private JPanel contentPane;
	Point referenciaCasillas1A20;
	Point referenciaCasillas21A40;
	Point referenciaCasillas41A60;
	Point referenciaCasillas61A80;
	JPanel fichaRoja = new JPanel();
	JPanel fichaVerde = new JPanel();
	JPanel parchis_pane = new JPanelConFondoParchis("../img/Parchis_version_2.png");
	JPanel initial_pane = new JPanel();
	JPanel player_pane = new JPanel();
	int casillaRojo;
	int casillaVerde;
	int mueveFichas;

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
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(initial_pane);
		contentPane.add(parchis_pane);
		contentPane.add(player_pane);

		initial_pane.setBounds(15, 15, 1381, 900);
		initial_pane.setLayout(null);
		initial_pane.setVisible(false);

		parchis_pane.setBounds(15, 15, 900, 900);
		parchis_pane.setLayout(null);
		parchis_pane.add(fichaVerde);

		fichaRoja.setBounds(100, 300, 28, 28);
		fichaRoja.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		fichaRoja.setBackground(Color.RED);

		fichaVerde.setBounds(700, 567, 28, 28);
		fichaVerde.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		fichaVerde.setBackground(new Color(124, 252, 0));

		parchis_pane.add(fichaRoja);

		JButton botonOriginal = new JButton("Mover Rojo");
		botonOriginal.setBounds(100, 315, 105, 27);

		JButton btnMoverVerde = new JButton("Mover Verde");
		btnMoverVerde.setBounds(100, 125, 109, 27);
		
		JLabel muestraIteracionVerde = new JLabel("41");
		muestraIteracionVerde.setBounds(100, 103, 14, 17);
		
		JLabel coordenadasRoja = new JLabel("Coordenadas roja");
		coordenadasRoja.setBounds(100, 37, 104, 17);
		
		JLabel coordenadasVerde = new JLabel("Coordenadas Verde");
		coordenadasVerde.setBounds(100, 59, 116, 17);

		JLabel muestraIteracionRojo = new JLabel("1");
		muestraIteracionRojo.setBounds(100, 81, 14, 17);

		JLabel lblCasillarojo = new JLabel("CasillaRojo");
		lblCasillarojo.setBounds(100, 157, 66, 17);

		JLabel lblCasillaVerde = new JLabel("Casilla Verde");
		lblCasillaVerde.setBounds(100, 179, 78, 17);

		JLabel resultDice = new JLabel("Result: ");
		resultDice.setBounds(100, 201, 60, 17);

		JLabel imageDice = new JLabel("");
		imageDice.setBounds(100, 223, 319, 313);
		imageDice.setIcon(new ImageIcon(ViewGUI.class.getResource("../img/dice1.png")));

		JButton tirarDado = new JButton("Tirar Dado");
		tirarDado.setBounds(100, 541, 96, 27);
		tirarDado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				//It creates random number r
				Random r = new Random();
				//It sets int mueveFichas with a number from 0 to 5, but it adds 1, so it's from 1 to 6
				mueveFichas = r.nextInt(1, 7);
				//It sets resultDice text
				resultDice.setText("Result: "+mueveFichas);	
				//it sets Icon 
				imageDice.setIcon(new ImageIcon(ViewGUI.class.getResource("../img/dice" + mueveFichas + ".png")));
			}
		});
		
		player_pane.setBounds(915, 15, 481, 900);
		player_pane.setLayout(null);
		player_pane.add(botonOriginal);
		player_pane.add(coordenadasRoja);
		player_pane.add(coordenadasVerde);
		player_pane.add(muestraIteracionRojo);
		player_pane.add(muestraIteracionVerde);
		player_pane.add(btnMoverVerde);
		player_pane.add(lblCasillarojo);
		player_pane.add(lblCasillaVerde);
		player_pane.add(resultDice);
		player_pane.add(imageDice);
		player_pane.add(tirarDado);
		
		btnMoverVerde.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				movement(muestraIteracionVerde, casillaVerde, fichaVerde, coordenadasVerde);
			}
		});
		botonOriginal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				movement(muestraIteracionRojo, casillaRojo, fichaRoja, coordenadasRoja);
			}
		});

		//Escrito por MA

		//estas dos de abajo se meten en un bucle y cada vez que se da al boton se iteran
		referenciaCasillas1A20 = fichaRoja.getLocation();
		referenciaCasillas21A40 = fichaRoja.getLocation();
		referenciaCasillas41A60 = fichaRoja.getLocation();
		referenciaCasillas61A80 = fichaRoja.getLocation();

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
}
