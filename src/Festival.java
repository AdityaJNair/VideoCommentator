
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

public class Festival extends SwingWorker {

	public String string;
	public boolean cancel = false;
	private ProcessBuilder builder;
	private String cmd;
	private Process process;
	
	Festival(String string) {
		this.string = string;
	}

	public void cancelThread() {

		this.cancel = true;
		//cmd = "pstree -lp | grep bash";
		cmd = "pgrep play";
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
			int num = line.lastIndexOf("play");
			String pidString = line.substring((num + 6), (num + 14));
			String[] Array = pidString.split(")");
			
			try {
				int pid = Integer.parseInt(pidString);
				cmd = "kill " + Array[0]; 
				builder = new ProcessBuilder("/bin/bash", "-c", cmd);
				process = builder.start();
			} catch (Exception e) {
				return;
			}
			System.out.println(pidString);

		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			process = builder.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("cancelled");
	}

	@Override
	protected Object doInBackground() throws Exception {
		try {
			String cmd = "echo " + string + " | festival --tts";
			builder = new ProcessBuilder("/bin/bash", "-c", cmd);
			process = builder.start();
			if (cancel) {
				cmd = "pstree -p | grep festival";
				builder = new ProcessBuilder("/bin/bash", "-c", cmd);
				process = builder.start();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	class cancelFestival extends SwingWorker<Void, Void>{

		@Override
		protected Void doInBackground() throws Exception {
			cancel = true;
			//cmd = "pstree -lp | grep bash";
			cmd = "pgrep play";
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
				//int num = line.lastIndexOf("play");
				//String pidString = line.substring((num + 6), (num + 14));
				//String[] Array = pidString.split(")");
				
				try {
					int pid = Integer.parseInt(line/*pidString*/);
					//cmd = "kill " + Array[0];
					cmd = "kill " + pid;
					builder = new ProcessBuilder("/bin/bash", "-c", cmd);
					process = builder.start();
				} catch (Exception e) {
					return null;
				}
				System.out.println(line);

			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				process = builder.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("cancelled");
			return null;
		}
		
	}
	
}


