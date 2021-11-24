import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimeTracker
{
	File file;
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd EE: ");
	private final SimpleDateFormat timeFormat = new SimpleDateFormat("KK:mm a");
	private final Timer timer = new Timer();
	private int hoursPassed = 0;
	private int minutesPassed = 0;
	private int secondsPassed = 0;
	private final TimerTask task = new TimerTask()
	{
		@Override
		public void run()
		{
			secondsPassed++;
			if (secondsPassed == 60)
			{
				minutesPassed++;
				if (minutesPassed == 60)
				{
					hoursPassed++;
					minutesPassed = 0;
				}
				secondsPassed = 0;
			}
			System.out.println("Time Passed: " + hoursPassed + ":" + minutesPassed + ":" + secondsPassed);
		}
	};


	public TimeTracker()
	{
		checkExistingFile();
	}

	//Generates the session count & start date/time when the login button is clicked
	public void logIn()
	{
		try
		{
			FileWriter fw = new FileWriter(file, true);
			Date date = new Date();

			fw.append("\n");
			fw.append(getDate());
			fw.append(" ");
			fw.append(timeFormat.format(date));
			fw.flush();
			fw.close();
		} catch (IOException e)
		{
			System.out.println("****************");
			System.out.println("ERROR encountered while performing user Login...");
			System.out.println("****************");
			e.printStackTrace();
		}

		/*
			Sample Output:
				47 DEC 25 SAT: 11:54 AM
		 */
	}

	//Generates logout time & session time total
	public void logOut()
	{
		try
		{
			FileWriter fw = new FileWriter(file, true);
			Date date = new Date();

			fw.append("  ");
			fw.append(timeFormat.format(date));
			fw.append("    ");
			fw.append(getTimePassed());
			fw.flush();
			fw.close();
		} catch (IOException e)
		{
			System.out.println("****************");
			System.out.println("ERROR encountered while performing user Logout...");
			System.out.println("****************");
			e.printStackTrace();
		}

		/*
			Sample Output:
				  07:23 PM    4:18:23
		 */
	}

	public void startTimer()
	{
		timer.scheduleAtFixedRate(task, 1000, 1000);
	}

	public void stopTimer()
	{
		timer.cancel();
	}

	//Scans .txt file for session count.  Returns session count with date.
	public String getDate()
	{
		int sessionCount = 0;
		Date date = new Date();

		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			while (reader.readLine() != null)
			{
				sessionCount++;
			}
			reader.close();
		} catch (IOException e)
		{
			System.out.println("****************");
			System.out.println("ERROR encountered while counting number of sessions in .txt file...");
			System.out.println("****************");
			e.printStackTrace();
		}

		return sessionCount + " " + dateFormat.format(date).toUpperCase();

		/*
			Sample Output:
				47 DEC 25 SAT:
		 */
	}

	public String getTimePassed()
	{
		return hoursPassed + ":" + minutesPassed + ":" + secondsPassed;
	}

	//Checks if Coding Timer.txt exists on user's directory.
	//If not present, a new .txt file will be created.
	public void checkExistingFile()
	{
		String desktopPath = System.getProperty("user.home") + File.separator + "Documents" + File.separator;
		file = new File(desktopPath + "Coding Timer.txt");

		if(file.exists())
		{
			System.out.println("Coding Timer.txt EXISTS in the user's document folder");
		}else{
			System.out.println("Coding Timer.txt DOES NOT exist in the user's document folder");
			System.out.println("Creating file to: " + desktopPath + "Coding Timer.txt");

			try
			{
				FileWriter fw = new FileWriter(file);
				fw.write("# Date         Login     Logout      Session Time    Notes");
				fw.flush();
				fw.close();
			}
			catch(IOException e)
			{
				System.out.println("****************");
				System.out.println("ERROR encountered while creating new .txt file...");
				System.out.println("****************");
				e.printStackTrace();
			}
		}
	}
}