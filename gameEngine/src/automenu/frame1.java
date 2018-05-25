package automenu;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JTextPane;

import common.e8State;
import gameMaster.GameMain;

public class frame1 {

	private JFrame frame;
	public static boolean startGame;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					frame1 window = new frame1();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
	
	public static void guiMain() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame1 window = new frame1();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public frame1() {
		startGame = false;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(204, 255, 255));
		frame.setBounds(100, 100, 780, 682);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		
		JLabel lblWelcome = new JLabel("Welcome in the CarRace game!");
		lblWelcome.setForeground(new Color(153, 0, 102));
		lblWelcome.setBackground(new Color(255, 255, 255));
		lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 36));
		lblWelcome.setBounds(142, 170, 499, 116);
		frame.getContentPane().add(lblWelcome);
		
		JLabel lblGameName = new JLabel("CarRace");
		lblGameName.setForeground(new Color(102, 51, 204));
		lblGameName.setFont(new Font("Tahoma", Font.BOLD, 72));
		lblGameName.setBounds(239, 28, 326, 144);
		frame.getContentPane().add(lblGameName);
		
		JLabel lblText = new JLabel(" Have a pleasant time here! :)");
		lblText.setForeground(new Color(204, 0, 153));
		lblText.setFont(new Font("Tahoma", Font.PLAIN, 36));
		lblText.setBounds(142, 248, 484, 96);
		frame.getContentPane().add(lblText);
		
		JButton btnCarsAndRoutes = new JButton("Cars and Routes");
		btnCarsAndRoutes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CarAndRouteChoose choose = new CarAndRouteChoose();
				choose.setVisible(true);
			}
		});
		btnCarsAndRoutes.setForeground(new Color(153, 255, 255));
		btnCarsAndRoutes.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnCarsAndRoutes.setBackground(new Color(51, 51, 102));
		btnCarsAndRoutes.setBounds(274, 543, 222, 54);
		frame.getContentPane().add(btnCarsAndRoutes);
		
		JButton btnProperties = new JButton("Properties");
		btnProperties.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//frame.dispose();
				System.out.println("Megnyomták a start gombot");
				Properties propr = new Properties();
				propr.setVisible(true);
			}
		});
		btnProperties.setForeground(new Color(255, 50, 204));
		btnProperties.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnProperties.setBackground(new Color(102, 0, 102));
		btnProperties.setBounds(40, 543, 180, 54);
		frame.getContentPane().add(btnProperties);
		
		JButton btnRecords = new JButton("Records");
		btnRecords.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Records rec = new Records();
				rec.setVisible(true);
			}
		});
		btnRecords.setForeground(new Color(204, 255, 204));
		btnRecords.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnRecords.setBackground(new Color(102, 0, 0));
		btnRecords.setBounds(550, 543, 180, 54);
		frame.getContentPane().add(btnRecords);
		
		JButton btnStart = new JButton("START");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Megnyomták a start gombot");
				startGame = true;
				
			}
		});
		btnStart.setForeground(new Color(0, 102, 102));
		btnStart.setFont(new Font("Tahoma", Font.PLAIN, 72));
		btnStart.setBounds(239, 377, 286, 96);
		frame.getContentPane().add(btnStart);
		
		JButton button = new JButton("?");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "PLease select first the attributes of the car in the PROPERTIES menu.");
			}
		});
		button.setFont(new Font("Tahoma", Font.BOLD, 16));
		button.setBounds(627, 408, 47, 32);
		frame.getContentPane().add(button);
	}
}
