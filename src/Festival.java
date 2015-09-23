
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
	 * @param string - The string to be converted to speech.
	 */
	Festival(String string, JButton demo) {
		this.string = string;
		this.demonstrate = demo;
	}

	/**
	 * Stops the speech.
	 */
	public void cancelThread() {
		CancelFestival c = new CancelFestival();
		c.execute();
	}

	
	@Override
	protected Void doInBackground() throws Exception {
		try {
			String cmd = "echo " + string + " | festival --tts";
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
	@Override
	protected void done(){
		demonstrate.setText("DEMONSTRATE");
	}
	/**
	 * Cancels the speech.
	 *
	 */
	class CancelFestival extends SwingWorker<Void, Void>{

		@Override
		protected Void doInBackground() throws Exception {
			cancel = true;
			cmd = "pstree -lp | grep bash";
			builder = new ProcessBuilder("/bin/bash", "-c", cmd);
			try {
				process = builder.start();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			InputStream out = process.getInputStream();
			BufferedReader stdout = new BufferedReader(new InputStreamReader(out));
			String line = null;
			try {
				line = stdout.readLine();
				System.out.println(line);
				Matcher match = Pattern.compile("\\((.*?)\\)").matcher(line.substring(line.indexOf("play")));
				match.find();
				System.out.println(match.group(1));
				int pid = Integer.parseInt(match.group(1));
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
		
		@Override
		protected void done(){
		}
	}
	

}

