package version_1;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Color;

public class VisualGUI extends JFrame {

	private JPanel contentPane;

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
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.RED);
		panel_2.setBounds(12, 146, 28, 27);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(986, 81, 400, 42);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		//Escrito por MA
		Point abajoIzquierda = panel.getLocation(); //para coger las coordenadas del panel
		Point casilla80 = panel.getLocation(); 	//primero cogemos las coordenadas del pane
		casilla80.setLocation(casilla80.getX()+1, casilla80.getY()+4);		//luego las editamos
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(986, 135, 400, 30);
		contentPane.add(lblNewLabel);
		
		lblNewLabel.setText(casilla80.toString()); //para cambiar el texto del label de arriba y que me diga las coordenadas
		Point ficha = panel_2.getLocation();
		ficha.setLocation(40,146);
		panel_2.setLocation(ficha.getLocation());
		
	}
}
