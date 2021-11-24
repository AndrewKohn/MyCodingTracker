/*
* My Coding Tracker
* 	Used to track the amount of time spent on coding actively (not spent watching videos/reading).
* Andrew Kohn
* 	Started on 07/03/2021
* 	Redone on 11/24/2021
* 	Last Edit: 11/24/2021
* */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyCodingTracker extends JFrame implements ActionListener
{
	private final TimeTracker tTracker = new TimeTracker();
	private final File timeLog = tTracker.file;
	private final JTextField notesTextField = new JTextField();
	private String dateString;
	private boolean loginBoolean = true;


	public MyCodingTracker()
	{
		JPanel panel = new JPanel();
		JButton loginButton = new JButton("Login");
		JButton logoutButton = new JButton("Logout");
		JButton openFileButton = new JButton("Open Log");
		JLabel notesLabel = new JLabel("Notes:");
		JLabel loginLabel = new JLabel();
		JLabel logoutLabel = new JLabel();
		JLabel elapsedTimeLabel = new JLabel();
		SimpleDateFormat timeFormat = new SimpleDateFormat("MMM dd EE: KK:mm a");

		//Will start timer, print out on JLabel current date/time, and write the START time onto file.
		ActionListener loginButtonPressed = e ->
		{
			tTracker.startTimer();
			Date date = new Date();
			dateString = timeFormat.format(date);

			loginLabel.setFont(new Font("Courier", Font.BOLD, 14));
			loginLabel.setForeground(new Color(205, 111, 49));
			loginLabel.setText("Start: " + dateString);
			loginLabel.setBounds(110, 20, 275, 50);
			loginLabel.setHorizontalTextPosition(JLabel.CENTER);
			loginLabel.setVisible(true);
			loginButton.setVisible(false);

			tTracker.logIn();

			loginBoolean = false;
		};

		//Will print out on JLabel current date/time and write the END time, elapsed session time, & notes onto file.
		ActionListener logoutButtonPressed = e ->
		{
			if(!loginBoolean)
			{
				tTracker.stopTimer();
				Date date = new Date();
				dateString = timeFormat.format(date);

				logoutLabel.setFont(new Font("Courier", Font.BOLD, 14));
				logoutLabel.setForeground(new Color(205, 111, 49));
				logoutLabel.setText("End: " + dateString);
				logoutLabel.setBounds(113, 80, 275, 50);
				logoutLabel.setVisible(true);
				logoutButton.setVisible(false);

				tTracker.logOut();
				setNotesToFile();

				//Elapsed time label shown under logout time.  TODO: replace this with a dynamic/updating label while running.
				elapsedTimeLabel.setBounds(140,135,175,35);
				elapsedTimeLabel.setFont(new Font("Courier", Font.BOLD, 14));
				elapsedTimeLabel.setForeground(new Color(205, 111, 49));
				elapsedTimeLabel.setText("Time spent: " + tTracker.getTimePassed());
				elapsedTimeLabel.setVisible(true);
			}
		};

		//Opens the "Coding Timer.txt" file
		ActionListener openFileButtonPressed = e ->
		{
			try
			{
				Desktop.getDesktop().open(timeLog);
			}
			catch(IOException err)
			{
				System.out.println("****************");
				System.out.println("ERROR encountered while opening text file...");
				System.out.println("****************");
				err.printStackTrace();
			}
		};

		//Set buttons/actions
		loginButton.setBounds(170,20,75,50);
		loginButton.addActionListener(loginButtonPressed);
		logoutButton.setBounds(170,80,75,50);
		logoutButton.addActionListener(logoutButtonPressed);
		openFileButton.setBounds(290,145,90,25);
		openFileButton.addActionListener(openFileButtonPressed);
		notesLabel.setBounds(17,150,50,35);
		notesLabel.setForeground(Color.WHITE);
		notesLabel.setVisible(true);

		notesTextField.setBounds(17,180,350,35);
		notesTextField.setToolTipText("Notes");
		notesTextField.setVisible(true);
		notesTextField.addActionListener(logoutButtonPressed);

		//JFrame / JPanel setup
		ImageIcon image = new ImageIcon("src/clock.png");		//TODO: Get a better visible app icon
		this.setIconImage(image.getImage());

		panel.setLayout(null);
		panel.add(openFileButton);
		panel.add(loginButton);
		panel.add(logoutButton);
		panel.add(loginLabel);
		panel.add(logoutLabel);
		panel.add(elapsedTimeLabel);
		panel.add(notesTextField);
		panel.add(notesLabel);
		panel.setBackground(new Color(0xFF3A3B44, true));

		this.add(panel);
		this.setTitle("Coding Time Tracker");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setSize(400,265);
		this.setVisible(true);

		//Default JFrame location to center of screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((screenSize.getWidth() - this.getWidth()) / 2);
		int y = (int) ((screenSize.getHeight() - this.getHeight()) / 2);
		this.setLocation(x, y);
	}

	public void setNotesToFile()
	{
		try
		{
			FileWriter fw = new FileWriter(timeLog, true);
			fw.append("         ");
			fw.append(notesTextField.getText());
			fw.flush();
			fw.close();
		}
		catch(IOException e)
		{
			System.out.println("****************");
			System.out.println("ERROR encountered while writing NOTES onto text file...");
			System.out.println("****************");
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		new MyCodingTracker();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{

	}
}
