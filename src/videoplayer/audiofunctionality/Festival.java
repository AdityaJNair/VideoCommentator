package videoplayer.audiofunctionality;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.SwingWorker;

/**
 * Festival creates speech from Strings.
 * 
 * @author Adi Nair, Priyankit Singh
 * 
 */
public class Festival extends SwingWorker<Void, Void> {

	public String string;
	public boolean cancel = false;
	private ProcessBuilder builder;
	private String cmd;
	private Process process;
	private JButton demonstrate;

	/**
	 * Creates a Festival object.
	 * 
	 * @param string
	 *            - The string to be converted to speech.
	 * @param demo
	 *            - The demonstrate button from TextToAudioFrame.
	 */
	Festival(String string, JButton demo) {
		this.string = string;
		this.demonstrate = demo;
	}

	/**
	 * Stops the speech using CancelFestival class.
	 */
	public void cancelThread() {
		CancelFestival c = new CancelFestival();
		c.execute();
	}

	/**
	 * Uses festival to play audio from the text provided by the users.
	 */
	@Override
	protected Void doInBackground() throws Exception {
		try {
			String cmd = "echo \"" + string + "\" | festival --tts";
			builder = new ProcessBuilder("/bin/bash", "-c", cmd);
			process = builder.start();
			Thread.sleep(500);
			if (cancel) {
				cmd = "pstree -p | grep festival";
				builder = new ProcessBuilder("/bin/bash", "-c", cmd);
				process = builder.start();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		process.waitFor();
		return null;
	}

	/**
	 * Changes the text of demonstrate button when the festival speech is done.
	 */
	@Override
	protected void done() {
		demonstrate.setText("DEMONSTRATE");
	}

	/**
	 * CancelFestival is used to stop the festival speech. Extends the
	 * SwingWorker class.
	 */
	class CancelFestival extends SwingWorker<Void, Void> {

		/**
		 * This method gets the process id of play task and kills it to stop the
		 * festival speech. Implements the doInBackground method in SwingWorker.
		 */
		@Override
		protected Void doInBackground() throws Exception {
			cancel = true;

			// Gets a list of all current processes
			cmd = "pstree -lp | grep bash";
			builder = new ProcessBuilder("/bin/bash", "-c", cmd);
			try {
				process = builder.start();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			// Reads to input stream and finds the "Play" process.
			InputStream out = process.getInputStream();
			BufferedReader stdout = new BufferedReader(new InputStreamReader(
					out));
			String line = null;
			try {
				line = stdout.readLine();
				Matcher match = Pattern.compile("\\((.*?)\\)").matcher(
						line.substring(line.indexOf("play")));
				match.find();
				int pid = Integer.parseInt(match.group(1));

				// Kills the play process using its process id
				try {
					cmd = "kill " + pid;
					builder = new ProcessBuilder("/bin/bash", "-c", cmd);
					process = builder.start();
				} catch (Exception e) {
					return null;
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				process = builder.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

}
