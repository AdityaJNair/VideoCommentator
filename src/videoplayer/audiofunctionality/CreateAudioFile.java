package videoplayer.audiofunctionality;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import videoplayer.videofunctionality.SaveAs;
import videoplayer.videoscreen.MainFrame;

/**
 * CreateAudioFile creates a wav audio file from text provided by the user.
 * Also creates a text file from the text which is used to create the audio file by festival.
 * @author Adi Nair, Priyankit Singh
 *
 */
public class CreateAudioFile extends SwingWorker<Void, Void> {

	public String comment;
	public boolean cancel = false;
	private ProcessBuilder builder;
	private String cmd;
	private Process process;
	private String fileName;
	private String textFileName;
	private JDialog dialog;
	private File file;

	/**
	 * Creates a new CreateAudioFile object
	 * @param comment - String to be converted to speech comments.
	 * @param fileName - name of the .wav file created by the process.
	 */
	CreateAudioFile(String comment, String fileName) {
		this.comment = comment;
		this.fileName = fileName;
		this.textFileName = fileName.substring(0,fileName.length() - 4) + ".txt";
		createFile(textFileName);
	}

	/**
	 * Creates a text file from the comment. The text file is used to create the audio file by festival.
	 * @param fileName.txt
	 */
	private void createFile(String fileName){
		file = new File(fileName);
		try {
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(this.comment);
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method uses festival to create an audio file from the text provided by the user.
	 * Overrides the doInBACKGROUNG function of SwingWorker to execute the process in background. 
	 */
	@Override
	protected Void doInBackground() throws Exception {
		createDialog();
		try {
			dialog.setVisible(true);
			cmd = "text2wave \"" + textFileName +"\" -o \"" + fileName + "\"";
			builder = new ProcessBuilder("/bin/bash", "-c", cmd);
			process = builder.start();
			process.waitFor();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * This method use CombineAudioVideo class to combine the audio file created by this class to the video selected by the user.
	 * It creates a save as dialog to get the location of merged file. Overrides the done method in the event dispatch thread.
	 */
	@Override
	protected void done(){
		dialog.setVisible(false);
		file.delete();
		String videoName = MainFrame.videoName;
		JOptionPane.showMessageDialog(null,
			    "Select the location where you want to save the merged file",
			    "Merged File Saving Location",
			    JOptionPane.OK_OPTION);
		SaveAs sa = new SaveAs("mp4", "Select output File Video Location");
		String outputPath = sa.getSelectionPath();
		CombineAudioVideo combine = new CombineAudioVideo(fileName,videoName,outputPath);
		combine.execute();
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
