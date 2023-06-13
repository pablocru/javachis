package version_1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class StatisticWindowGUI extends JFrame {

	private JPanel contentPane;
	private JTable table;

	String[] columnNames = {"username", "wonGames", "playedGames"};
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StatisticWindowGUI frame = new StatisticWindowGUI();
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
	public StatisticWindowGUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400, 200, 1000, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane);
		scrollPane.setBounds(10, 10, 970, 650);
		
		table = new JTable(Database.savingDatabasePlayerInto2DArray(), columnNames);
		table.setBounds(0, 0, 1, 1);
		
		scrollPane.setViewportView(table);

	}

}
