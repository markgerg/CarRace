package automenu;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cars.e8CarColour;
import gameMaster.e8GameType;
import track.e8TrackID;

import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JToggleButton;
import javax.swing.JSpinner;
import javax.swing.AbstractAction;
import javax.swing.Action;



public class Properties extends JFrame {

	int GameTypeButton;
	
	private JPanel contentPane;
	private JTextField textFieldPlayer1;
	private JTextField textFieldPlayer2;
	private JTextField textField;
	private final Action action = new SwingAction();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Properties frame = new Properties();
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
	public Properties() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 974, 653);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(102, 0, 102));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JRadioButton rdbtnOnePlayer = new JRadioButton(" One player");
		
		rdbtnOnePlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GameTypeButton = 1;
			}
		});
		
		rdbtnOnePlayer.setFont(new Font("Tahoma", Font.PLAIN, 24));
		rdbtnOnePlayer.setForeground(new Color(255, 255, 255));
		rdbtnOnePlayer.setBackground(new Color(102, 0, 102));
		rdbtnOnePlayer.setBounds(92, 209, 230, 28);
		contentPane.add(rdbtnOnePlayer);
		
		JRadioButton rdbtnTwoPlayers = new JRadioButton("  Two players");
		
		rdbtnTwoPlayers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GameTypeButton = 2;
			}
		});
		
		rdbtnTwoPlayers.setBackground(new Color(102, 0, 102));
		rdbtnTwoPlayers.setForeground(new Color(255, 255, 255));
		rdbtnTwoPlayers.setFont(new Font("Tahoma", Font.PLAIN, 24));
		rdbtnTwoPlayers.setBounds(92, 245, 217, 34);
		contentPane.add(rdbtnTwoPlayers);
		
	    ButtonGroup group = new ButtonGroup();
	    group.add(rdbtnOnePlayer);
	    group.add(rdbtnTwoPlayers);
		
		JLabel lblPlayer1Name = new JLabel("Player 1 name");
		lblPlayer1Name.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblPlayer1Name.setForeground(new Color(255, 255, 255));
		lblPlayer1Name.setBounds(478, 193, 166, 44);
		contentPane.add(lblPlayer1Name);
		
		JLabel lblPlayer2Name = new JLabel("Player 2 name");
		lblPlayer2Name.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblPlayer2Name.setForeground(new Color(255, 255, 255));
		lblPlayer2Name.setBounds(478, 235, 166, 44);
		contentPane.add(lblPlayer2Name);
		
		textFieldPlayer1 = new JTextField();
		textFieldPlayer1.setColumns(10);
		textFieldPlayer1.setBounds(677, 203, 235, 28);
		contentPane.add(textFieldPlayer1);
		
		textFieldPlayer2 = new JTextField();
		textFieldPlayer2.setColumns(10);
		textFieldPlayer2.setBounds(677, 245, 235, 28);
		contentPane.add(textFieldPlayer2);
		
		JLabel lblNumberOfPlayers = new JLabel("Number of players");
		lblNumberOfPlayers.setForeground(new Color(255, 255, 255));
		lblNumberOfPlayers.setFont(new Font("Tahoma", Font.PLAIN, 36));
		lblNumberOfPlayers.setBackground(new Color(0, 0, 0));
		lblNumberOfPlayers.setBounds(49, 140, 308, 44);
		contentPane.add(lblNumberOfPlayers);
		
		JLabel lblNameOfPlayers = new JLabel("Name of player(s)");
		lblNameOfPlayers.setForeground(Color.WHITE);
		lblNameOfPlayers.setFont(new Font("Tahoma", Font.PLAIN, 36));
		lblNameOfPlayers.setBackground(Color.BLACK);
		lblNameOfPlayers.setBounds(429, 140, 308, 44);
		contentPane.add(lblNameOfPlayers);
		
		JLabel lblProperties = new JLabel("Properties");
		lblProperties.setForeground(new Color(255, 255, 204));
		lblProperties.setFont(new Font("Tahoma", Font.BOLD, 48));
		lblProperties.setBounds(364, 27, 270, 61);
		contentPane.add(lblProperties);
		
		JLabel lblDisplayResolution = new JLabel("Display resolution");
		lblDisplayResolution.setForeground(Color.WHITE);
		lblDisplayResolution.setFont(new Font("Tahoma", Font.PLAIN, 36));
		lblDisplayResolution.setBackground(Color.BLACK);
		lblDisplayResolution.setBounds(49, 333, 308, 44);
		contentPane.add(lblDisplayResolution);
		
		JRadioButton rdbtnLow = new JRadioButton(" 1280x720");
		rdbtnLow.setForeground(Color.WHITE);
		rdbtnLow.setFont(new Font("Tahoma", Font.PLAIN, 24));
		rdbtnLow.setBackground(new Color(102, 0, 102));
		rdbtnLow.setBounds(92, 397, 154, 25);
		contentPane.add(rdbtnLow);
		
		JRadioButton rdbtnHigh = new JRadioButton(" 1920x1080");
		rdbtnHigh.setForeground(Color.WHITE);
		rdbtnHigh.setFont(new Font("Tahoma", Font.PLAIN, 24));
		rdbtnHigh.setBackground(new Color(102, 0, 102));
		rdbtnHigh.setBounds(92, 439, 175, 25);
		contentPane.add(rdbtnHigh);
		
		ButtonGroup group2 = new ButtonGroup();
	    group2.add(rdbtnLow);
	    group2.add(rdbtnHigh);
		
		
		JButton btnSave = new JButton("SAVE");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//adatok elment�se, felhaszn�l�sa
				setVisible(false);
			}
		});
		btnSave.setForeground(new Color(102, 0, 102));
		btnSave.setFont(new Font("Tahoma", Font.BOLD, 42));
		btnSave.setBounds(752, 516, 175, 61);
		contentPane.add(btnSave); 
		
		JLabel lblNetConnection = new JLabel("Net connection");
		lblNetConnection.setForeground(Color.WHITE);
		lblNetConnection.setFont(new Font("Tahoma", Font.PLAIN, 36));
		lblNetConnection.setBackground(Color.BLACK);
		lblNetConnection.setBounds(429, 333, 308, 44);
		contentPane.add(lblNetConnection);
		
		JRadioButton rdbtnServer = new JRadioButton("server");
		rdbtnServer.setForeground(Color.WHITE);
		rdbtnServer.setFont(new Font("Tahoma", Font.PLAIN, 24));
		rdbtnServer.setBackground(new Color(102, 0, 102));
		rdbtnServer.setBounds(479, 439, 175, 25);
		contentPane.add(rdbtnServer);
		
		JRadioButton rdbtnClient = new JRadioButton("client");
		rdbtnClient.setForeground(Color.WHITE);
		rdbtnClient.setFont(new Font("Tahoma", Font.PLAIN, 24));
		rdbtnClient.setBackground(new Color(102, 0, 102));
		rdbtnClient.setBounds(479, 397, 154, 25);
		contentPane.add(rdbtnClient);
		
		ButtonGroup group3 = new ButtonGroup();
	    group3.add(rdbtnServer);
	    group3.add(rdbtnClient);
		
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(677, 442, 235, 28);
		contentPane.add(textField);
		
		JLabel lblIpAddress = new JLabel("IP address");
		lblIpAddress.setForeground(Color.WHITE);
		lblIpAddress.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblIpAddress.setBounds(677, 387, 166, 44);
		contentPane.add(lblIpAddress);
		
		JButton button = new JButton("?");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "You can look for your IP address in the properties of your computer.");
			}
		});
		button.setFont(new Font("Tahoma", Font.BOLD, 16));
		button.setBounds(865, 397, 47, 32);
		contentPane.add(button);
		
		JButton button_1 = new JButton("?");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "The display resolution is the number of distinct pixels in each dimension that can be displayed. \nYou can check your display resolution in the properties of your computer.");
			}
		});
		button_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		button_1.setBounds(275, 414, 47, 32);
		contentPane.add(button_1);
		
	}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
	
	
	
	
	public e8GameType getGameType() {
		e8GameType gametype;
		if (GameTypeButton == 2) {
			gametype = e8GameType.MULTIPLAYER;
		}
		else {
			gametype = e8GameType.SINGLEPLAYER;
		}
		
		return gametype;
	}
	
	
	
}
