package version_1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;

public class TestingDataInter extends JFrame {

	private JPanel pane_data;
	String[][] data = {
			{"1", "John", "Doe"},
			{"2", "Jane", "Smith"},
			{"3", "Bob", "Johnson"},
			{"1", "John", "Doe"},
			{"2", "Jane", "Smith"},
			{"3", "Bob", "Johnson"},
			{"1", "John", "Doe"},
			{"2", "Jane", "Smith"},
			{"3", "Bob", "Johnson"},
			{"1", "John", "Doe"},
			{"2", "Jane", "Smith"},
			{"3", "Bob", "Johnson"},
			{"1", "John", "Doe"},
			{"2", "Jane", "Smith"},
			{"3", "Bob", "Johnson"},
			{"1", "John", "Doe"},
			{"2", "Jane", "Smith"},
			{"3", "Bob", "Johnson"}
			
	};

	String[] columnNames = {"ID", "First Name", "Last Name"};
	private JTable table;
	private JTable table_data;
	private JTable table_1;



	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestingDataInter frame = new TestingDataInter();
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
	public TestingDataInter() {
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(100, 100, 1000, 1000);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 442, 267);
		panel.add(scrollPane);
		
		table_1 = new JTable(data, columnNames);
		
		scrollPane.setViewportView(table_1);

		
		

	}
}
