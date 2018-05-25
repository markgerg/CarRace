package automenu;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cars.e8CarColour;
import track.e8TrackID;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CarAndRouteChoose extends JFrame {

	private JPanel contentPane;
	int colornumber;
	int trackID;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CarAndRouteChoose frame = new CarAndRouteChoose();
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
	public CarAndRouteChoose() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 851, 735);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(51, 51, 102));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCar = new JLabel("Car");
		lblCar.setFont(new Font("Tahoma", Font.BOLD, 48));
		lblCar.setForeground(new Color(153, 255, 255));
		lblCar.setBounds(137, 42, 109, 58);
		contentPane.add(lblCar);
		
		JLabel lblRoute = new JLabel("Route");
		lblRoute.setForeground(new Color(153, 255, 255));
		lblRoute.setFont(new Font("Tahoma", Font.BOLD, 48));
		lblRoute.setBounds(556, 42, 166, 58);
		contentPane.add(lblRoute);
		
		JButton btnCarType1 = new JButton("RED");
		btnCarType1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				colornumber = 0;
			}
		});
		btnCarType1.setBounds(42, 126, 290, 196);
		contentPane.add(btnCarType1);
		
		JButton btnCarType2 = new JButton("BLUE");
		btnCarType2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				colornumber = 1;
			}
		});
		btnCarType2.setBounds(42, 359, 290, 196);
		contentPane.add(btnCarType2);
		
		JButton btnRouteType1 = new JButton("track1");
		btnRouteType1.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			trackID = 1;
		}
		});
		btnRouteType1.setBounds(493, 126, 290, 196);
		contentPane.add(btnRouteType1);
		
		JButton btnRouteType2 = new JButton("track2");
		btnRouteType2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				trackID = 2;
			}
			});
		btnRouteType2.setBounds(493, 359, 290, 196);
		contentPane.add(btnRouteType2);
		
		JButton btnNewButton = new JButton("Select");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//kiv�lasztott adatok elment�se
				setVisible(false);
			}
		});
		btnNewButton.setForeground(new Color(51, 51, 102));
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 42));
		btnNewButton.setBounds(325, 594, 177, 68);
		contentPane.add(btnNewButton);
	}
	public e8CarColour getColour() {
		e8CarColour colour;
		if (colornumber == 0) {
		 colour = e8CarColour.RED;
		}
		else {
		 colour = e8CarColour.BLUE;
		}
		
		return colour;
	}
	
	public e8TrackID getTrackID() {
		e8TrackID TrackID;
		if (trackID == 1) {
		TrackID = e8TrackID.TRACK1;
		}
		else {
		TrackID = e8TrackID.TRACK1;
		}
		
		return TrackID;
	}
}
