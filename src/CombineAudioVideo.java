
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingWorker;

/**
 * CombineAudioVideo adds audio to video files and saves it as a separate mp4 file.
 * @author Adi Nair, Priyankit Singh
 *
 */
public class CombineAudioVideo extends SwingWorker<Void, Void> {

	private ProcessBuilder builder;
	private String cmd;
	private Process process;
	private String audioFileName;
	private String videoFileName;
	private JDialog dialog;
	private String outputPath;

	/**
	 * Creates a new CombineAudioVideo object
	 * 
	 * @param audioFileName - Name of the audio file to be added to the video.
	 * @param videoFileName - Name of the video file.
	 *            
	 */
	CombineAudioVideo(String audioFileName, String videoFileName, String outputP) {
		this.audioFileName = audioFileName;
		this.videoFileName = videoFileName;
		this.outputPath = outputP;
	}

	@Override
	protected Void doInBackground() throws Exception {
		createDialog();
		System.out.println("HERE");
		if(outputPath != null ){
			dialog.setVisible(true);
			/*
			 * user this one 
			cmd = "ffmpeg -i \"" + videoFileName + "\" -i \"" + audioFileName
			+ "\" -filter_complex amix=inputs=2 " + outputPath;*/
			cmd = "ffmpeg -i \"" + videoFileName + "\" -i \"" + audioFileName
					+ "\" -strict -2 -filter_complex amix=inputs=2 " + "\""+outputPath+ "\"";
			System.out.println(cmd);
			builder = new ProcessBuilder("/bin/bash", "-c", cmd);
			try {
				process = builder.start();
				process.waitFor();
				System.out.println(process.exitValue());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	@Override
	protected void done() {
		dialog.setVisible(false);
		// setVideo(path of output file)
	}

	/**
	 * Creates a dialog box to ask the users to wait for the process to finish.
	 */
	protected void createDialog() {
		dialog = new JDialog();
		dialog.setBounds(500, 500, 150,100);
		dialog.setResizable(false);
		JLabel label = new JLabel("     Please wait...");
		dialog.setLocationRelativeTo(null);
		dialog.setTitle("Please Wait...");
		dialog.add(label);
		dialog.setVisible(false);
	}
}
