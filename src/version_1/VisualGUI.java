package version_1;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;

public class VisualGUI extends JFrame {

	private JPanel contentPane;
	Point rojoReferenciaCasillas1A20;
	Point rojoReferenciaCasillas21A40;
	Point rojoReferenciaCasillas41A60;
	Point rojoReferenciaCasillas61A80;
	JPanel fichaRoja = new JPanel();


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VisualGUI frame = new VisualGUI();
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
	public VisualGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 1000);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanelConFondoParchis("Parchis_version_2.png");
		panel.setBounds(15, 15, 900, 900);
		contentPane.add(panel);
		panel.setLayout(null);
		fichaRoja.setBorder(new LineBorder(new Color(0, 0, 0), 2));


		fichaRoja.setBackground(Color.RED);
		fichaRoja.setBounds(300, 750, 28, 28);
		panel.add(fichaRoja);
		fichaRoja.setLayout(null);
		
		JPanel fichaAmarilla = new JPanel();
		fichaAmarilla.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		fichaAmarilla.setBackground(new Color(124, 252, 0));
		fichaAmarilla.setBounds(0, 0, 28, 28);
		panel.add(fichaAmarilla);

		//Escrito por MA


		//estas dos de abajo se meten en un bucle y cada vez que se da al boton se iteran
		rojoReferenciaCasillas1A20=fichaRoja.getLocation();
		rojoReferenciaCasillas21A40=fichaRoja.getLocation();
		rojoReferenciaCasillas41A60=fichaRoja.getLocation();
		rojoReferenciaCasillas61A80=fichaRoja.getLocation();
		//		ficha.setLocation((int)referenciaCasillas61A80.getX(), (int)referenciaCasillas61A80.getY());


		rojoReferenciaCasillas1A20.setLocation(100,300);
		rojoReferenciaCasillas21A40.setLocation(300, 750);
		rojoReferenciaCasillas41A60.setLocation(700,567);
		rojoReferenciaCasillas61A80.setLocation(570, 146);

		JLabel coordenadasRoja = new JLabel("Coordenadas roja");
		coordenadasRoja.setBounds(986, 135, 400, 30);
		contentPane.add(coordenadasRoja);

		JLabel coordenadasAmarilla = new JLabel("Coordenadas Verde");
		coordenadasAmarilla.setBounds(986, 89, 400, 30);
		contentPane.add(coordenadasAmarilla);
		
		JLabel muestraIteracionRojo = new JLabel("21"); //NO SE BORRA, SI NO SE PIERDE LA ITERACION
		muestraIteracionRojo.setBounds(1102, 213, 100, 17);
		contentPane.add(muestraIteracionRojo);
		
		JLabel muestraIteracionVerde = new JLabel("CasillaVerde");
		muestraIteracionVerde.setBounds(1102, 254, 60, 17);
		contentPane.add(muestraIteracionVerde);
		muestraIteracionVerde.setText(muestraIteracionRojo.getText());

		JButton botonOriginal = new JButton("Mover Rojo");
		botonOriginal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean in=true;
				while (in) {

					muestraIteracionRojo.setText(String.valueOf(Integer.parseInt(muestraIteracionRojo.getText())+1));
					muestraIteracionVerde.setText(muestraIteracionRojo.getText());
					int casilla = Integer.parseInt(muestraIteracionRojo.getText());	
					Point casilla2= new Point(100,302);
					Point casilla22=new Point(300, 750);
					Point casilla42=new Point(700,567);

					if (casilla<21) {
						if (casilla==1) {
							fichaRoja.setLocation((int)rojoReferenciaCasillas1A20.getX(), (int)rojoReferenciaCasillas1A20.getY());
							fichaAmarilla.setLocation((int)rojoReferenciaCasillas1A20.getX(), (int)rojoReferenciaCasillas1A20.getY());

						}
						else {
							fichaRoja.setLocation((int)casilla2.getX(), (int)casilla2.getY()+30*(casilla-1));
							fichaAmarilla.setLocation((int)casilla2.getX(), (int)casilla2.getY()+30*(casilla-1));
						}
						in=false;
					}else if (casilla<41) {
						if (casilla==21) {
							fichaRoja.setLocation((int)rojoReferenciaCasillas21A40.getX(), (int)rojoReferenciaCasillas21A40.getY());
							fichaAmarilla.setLocation((int)rojoReferenciaCasillas21A40.getX(), (int)rojoReferenciaCasillas21A40.getY());

						}
						else {
							fichaRoja.setLocation((int)casilla22.getX()+29*(casilla-21), (int)casilla22.getY());
							fichaAmarilla.setLocation((int)casilla22.getX()+29*(casilla-21), (int)casilla22.getY());
						}
						in=false;
					}else if (casilla<61) {
						if (casilla==41) {
							fichaRoja.setLocation((int)rojoReferenciaCasillas41A60.getX(), (int)rojoReferenciaCasillas41A60.getY());
							fichaAmarilla.setLocation((int)rojoReferenciaCasillas41A60.getX(), (int)rojoReferenciaCasillas41A60.getY());
						}
						else {
							fichaRoja.setLocation((int)casilla42.getX(), (int)casilla42.getY()-29*(casilla-41));
							fichaAmarilla.setLocation((int)casilla42.getX(), (int)casilla42.getY()-29*(casilla-41));
						}
						in=false;
					}else if (casilla<81) {
						fichaRoja.setLocation((int)rojoReferenciaCasillas61A80.getX()-29*(casilla-61), (int)rojoReferenciaCasillas61A80.getY());
						fichaAmarilla.setLocation((int)rojoReferenciaCasillas61A80.getX()-29*(casilla-61), (int)rojoReferenciaCasillas61A80.getY());
						in=false;
					}				
					if (casilla==81) {
						muestraIteracionRojo.setText("1");
						casilla= Integer.parseInt(muestraIteracionRojo.getText());
					}
					if (casilla==1) {
						fichaRoja.setLocation((int)rojoReferenciaCasillas1A20.getX(), (int)rojoReferenciaCasillas1A20.getY());
						fichaAmarilla.setLocation((int)rojoReferenciaCasillas1A20.getX(), (int)rojoReferenciaCasillas1A20.getY());

					}
					in=false;
				}
				coordenadasRoja.setText(fichaRoja.getLocation()+"."); //EL PUNTO NO LO TOQUES, QUE SI NO, NO VA
				coordenadasAmarilla.setText(fichaAmarilla.getLocation()+"."); //EL PUNTO NO LO TOQUES, QUE SI NO, NO VA

			}
		});
		botonOriginal.setBounds(986, 315, 105, 27);
		contentPane.add(botonOriginal);
		
		JButton btnMoverVerde = new JButton("Mover Verde");
		btnMoverVerde.setBounds(986, 393, 105, 27);
		contentPane.add(btnMoverVerde);
		
		JLabel lblCasillarojo = new JLabel("CasillaRojo");
		lblCasillarojo.setBounds(986, 213, 93, 17);
		contentPane.add(lblCasillarojo);
		
		JLabel lblCasillaVerde = new JLabel("Casilla Verde");
		lblCasillaVerde.setBounds(986, 254, 93, 17);
		contentPane.add(lblCasillaVerde);
		
		JButton tirarDado = new JButton("Tirar Dado");
		tirarDado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//aqui poner un numero random del 1 al 6
				//hay una variable en la clase que se llame resultadoDado o algo así. Iniciada a null.
				//Cuando se consigue el resultado, se le asigna, y después, se ejecuta ese movimiento en los otros
				
				
			}
		});
		tirarDado.setBounds(986, 462, 105, 27);
		contentPane.add(tirarDado);
		
		JLabel resultDice = new JLabel("Result: ");
		resultDice.setBounds(1214, 467, 60, 17);
		contentPane.add(resultDice);
		
		

		
		







	}
}
