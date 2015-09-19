
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingWorker;

/**
 * CreateAudioFile creates a wav audio file from text provided by the user.
 * Also creates a text file from the text. 
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
	private JDialog dialog;

	/**
	 * Creates a new CreateAudioFile object
	 * @param comment - String to be converted to speech comments.
	 * @param fileName - name of the .wav file created by the process.
	 */
	CreateAudioFile(String comment, String fileName) {
		this.comment = comment;
		this.fileName = fileName;
		createFile(fileName + ".txt");
	}
	
	/**
	 * Creates a text file from the comment.
	 * @param fileName.txt
	 */
	private void createFile(String fileName){
		File file = new File(fileName);
		try {
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(this.comment);
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		try {
			createDialog();
			cmd = "text2wave " + fileName + ".txt" +" -o " + fileName + ".wav";
			System.out.println(cmd);
			builder = new ProcessBuilder("/bin/bash", "-c", cmd);
			process = builder.start();
			process.waitFor();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void done(){
		dialog.setVisible(false);
		String videoName = MainFrame.videoName;
		CombineAudioVideo combine = new CombineAudioVideo(fileName + ".wav", videoName);
		combine.execute();
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
