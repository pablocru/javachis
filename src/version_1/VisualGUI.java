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
	Point referenciaCasillas1A20;
	Point referenciaCasillas21A40;
	Point referenciaCasillas41A60;
	Point referenciaCasillas61A80;
	JPanel ficha = new JPanel();
	
	
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
		ficha.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		
		
		ficha.setBackground(Color.RED);
		ficha.setBounds(570, 146, 28, 28);
		panel.add(ficha);
		ficha.setLayout(null);
		
		//Escrito por MA
		
		
		//estas dos de abajo se meten en un bucle y cada vez que se da al boton se iteran
		referenciaCasillas61A80=ficha.getLocation();
//		ficha.setLocation((int)referenciaCasillas61A80.getX(), (int)referenciaCasillas61A80.getY());
		
		referenciaCasillas61A80.setLocation(570, 146);//Empezar aqui el Jueves
		
		JLabel coordenadas = new JLabel("New label");
		coordenadas.setBounds(986, 135, 400, 30);
		contentPane.add(coordenadas);
		coordenadas.setText(referenciaCasillas61A80.toString()); //para cambiar el texto del label de arriba y que me diga las coordenadas
		
		JLabel muestraIteracion = new JLabel("61");
		muestraIteracion.setBounds(1129, 201, 100, 17);
		contentPane.add(muestraIteracion);
		
		JButton botonIterar = new JButton("New button");
		botonIterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				muestraIteracion.setText(String.valueOf(Integer.parseInt(muestraIteracion.getText())+1));
				referenciaCasillas61A80=ficha.getLocation();
				ficha.setLocation((int)referenciaCasillas61A80.getX()-29, (int)referenciaCasillas61A80.getY());
				coordenadas.setText(referenciaCasillas61A80.toString());
			}
		});
		botonIterar.setBounds(986, 196, 105, 27);
		contentPane.add(botonIterar);
		
		
		
		
		
		
		
	}
}
