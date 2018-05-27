package automenu;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.border.CompoundBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Records extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Records frame = new Records();
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
	public Records() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 939, 601);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(102, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JList list = new JList();
		list.setBounds(282, 249, 142, -109);
		contentPane.add(list);
		
		JLabel lblRecords = new JLabel("Records");
		lblRecords.setFont(new Font("Tahoma", Font.BOLD, 48));
		lblRecords.setForeground(new Color(204, 255, 204));
		lblRecords.setBounds(363, 13, 228, 110);
		contentPane.add(lblRecords);
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnNewButton.setForeground(new Color(102, 0, 0));
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 42));
		btnNewButton.setBounds(12, 474, 142, 67);
		contentPane.add(btnNewButton);
	}
}
