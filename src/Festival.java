
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.SwingWorker;

public class Festival extends SwingWorker {

	public String comment;
	public boolean cancel = false;
	private ProcessBuilder builder;
	private String cmd;
	private Process process;
	private String fileName;
	private File textFile = null;

	/**
	 * Creates a new Festival object
	 * @param comment
	 * @param fileName - name of the .wav file created by the process.
	 */
	Festival(String comment, String fileName) {
		this.comment = comment;
		this.fileName = fileName;
		createFile(fileName + ".txt");
	}
	
	Festival (File textFile){
		this.fileName = textFile.getName();
		this.textFile = textFile;
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

	/**
	 * Stops the festival voice.
	 */
	public void cancelThread() {
		this.cancel = true;
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
			int num = line.lastIndexOf("play");
			String pidString = line.substring((num + 6), (num + 11));
			try {
				int pid = Integer.parseInt(pidString);
				cmd = "kill " + pidString; //try string here to be safe
				builder = new ProcessBuilder("/bin/bash", "-c", cmd);
				process = builder.start();
			} catch (Exception e) {
				return;
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			process = builder.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected Object doInBackground() throws Exception {
		try {
			String cmd = "text2wave " + fileName + ".txt" +" -o " + fileName + ".wav";
			System.out.println(cmd);
			builder = new ProcessBuilder("/bin/bash", "-c", cmd);
			process = builder.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
