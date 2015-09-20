
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
	CombineAudioVideo(String audioFileName, String videoFileName) {
		this.audioFileName = audioFileName;
		this.videoFileName = videoFileName;
	}

	@Override
	protected Void doInBackground() throws Exception {
		SaveAs sa = new SaveAs();
		String path = sa.getSelectionPath();
		createDialog();
		cmd = "ffmpeg -i " + videoFileName + " -i " + audioFileName
				+ " -filter_complex amix=inputs=2 " + path;
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
		dialog.setVisible(true);
	}
}
