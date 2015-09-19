import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingWorker;

public class CombineAudioVideo extends SwingWorker {

	private ProcessBuilder builder;
	private String cmd;
	private Process process;
	private String audioFileName;
	private String videoFileName;
	private JDialog dialog;

	/**
	 * Creates a new CombineAudioVideo object
	 * 
	 * @param fileName
	 *            - name of the .wav file created by the process.
	 */
	CombineAudioVideo(String audioFileName, String videoFileName) {
		this.audioFileName = audioFileName;
		this.videoFileName = videoFileName;
	}

	@Override
	protected Object doInBackground() throws Exception {
		createDialog();
		String cmd = "ffmpeg -i " + videoFileName + " -i " + audioFileName
				+ " -filter_complex amix=inputs=2 out.mp4";
		System.out.println(cmd);
		builder = new ProcessBuilder("/bin/bash", "-c", cmd);
		try {
			process = builder.start();
			process.waitFor();
			System.out.println(process.exitValue());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	protected void done() {
		dialog.setVisible(false);
		System.out.println("Done something");
		// setVideo(path of output file)
	}

	protected void createDialog() {
		dialog = new JDialog();
		dialog.setBounds(500, 500, 150,100);
		dialog.setResizable(false);
		JLabel label = new JLabel("     Please wait...");
		dialog.setLocationRelativeTo(null);
		dialog.setTitle("Please Wait...");
		dialog.add(label);
		dialog.setVisible(true);
	}
}
