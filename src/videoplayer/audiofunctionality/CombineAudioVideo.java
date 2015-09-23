package videoplayer.audiofunctionality;

import java.io.File;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import videoplayer.videoscreen.MainFrame;

/**
 * CombineAudioVideo adds audio to video files and saves it as a separate mp4 file.
 * This class keeps the original video audio and adds the audio file on top of the original audio.
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
	public CombineAudioVideo(String audioFileName, String videoFileName, String outputP) {
		this.audioFileName = audioFileName;
		this.videoFileName = videoFileName;
		this.outputPath = outputP;
	}

	/**
	 * This method used ffmpeg to merge the audio and video files to create a merged video that contains the audio 
	 * file audio while keeping the original audio.
	 */
	@Override
	protected Void doInBackground() throws Exception {
		createDialog();
		if(outputPath != null ){
			dialog.setVisible(true);			
			cmd = "ffmpeg -i \"" + videoFileName + "\" -i \"" + audioFileName
			+ "\" -filter_complex amix=inputs=2 \"" + outputPath+ "\"";
			builder = new ProcessBuilder("/bin/bash", "-c", cmd);
			try {
				process = builder.start();
				process.waitFor();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * This method plays the merged  video on the video player if it was successfully merged. 
	 * Its displays an error dialog of the merge function was unsuccessful.
	 * Overrides the done method in the event dispatch thread.
	 */
	@Override
	protected void done() {
		dialog.setVisible(false);
		try{
			if(process.exitValue() != 0){
				JOptionPane.showMessageDialog(null,
					    "Please try again and ensure your video and audio files are of the correct format",
					    "Operation failed",
					    JOptionPane.ERROR_MESSAGE);
				return;
			}
		} catch (Exception e){
			
		}
		//shows file if merge worked correctly
		try{
			File merged = new File(outputPath);
			MainFrame.setVideo(merged);
		} catch (Exception e){
		}
	}

	/**
	 * Creates a dialog box to ask the users to wait for the process to finish.
	 * NOTE : This does not make the dialog box visible and setVisible method has to be invoked to make it visible.
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
