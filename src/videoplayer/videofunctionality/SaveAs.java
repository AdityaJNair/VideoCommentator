package videoplayer.videofunctionality;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

/** 
 * SaveAs dialog box lets users choose the filename of output file.
 * Can be used to save any type of file.
 * @author Adi Nair, Priyankit Singh
 *
 */
public class SaveAs extends JFrame {

	JFileChooser fileChooser;
	String extention;
	FileNameExtensionFilter filter;

	/**
	 * Creates a new Save As dialog box.
	 * @param extention - file extension for the output file.
	 * @param title - Title of save as dialog
	 */
	public SaveAs(String extention, String title) {
		fileChooser = new JFileChooser();
		fileChooser.setDialogTitle(title);
		this.extention = "." + extention;
		filter = new FileNameExtensionFilter("Choose " + extention + " files", extention);
		fileChooser.setFileFilter(filter);
	}

	/**
	 * Gets the selection path for output files from users.
	 * @return Returns the path to the location selected by the user. Null if no file selected
	 */
	public String getSelectionPath() {
		int userSelection = fileChooser.showSaveDialog(this);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToSave = fileChooser.getSelectedFile();
			String path = fileToSave.getAbsolutePath();
			File f = new File(fileToSave.getAbsolutePath());
			if(path.lastIndexOf(extention) == -1){
				//case when extention does not exist so it looks like Desktop/asda or /asda.mp3
				f = new File(path + extention);
				if (f.exists()) {
					int count = 0;
					while (f.exists()) {
						count++;
						f = new File(path + "(" + count
								+ ")" + extention);
					}
					return path + "(" + count + ")"
							+ extention;
				} else {
					return path + extention;
				}
			} else {
				//case when filename ends with .mp4
				if (f.exists()) {
					
					int count = 0;
					while (f.exists()) {
						count++;
						path = path.substring(0,path.length()-4);
						System.out.println(path + "(" + count + ")" + extention);
						f = new File(path + "(" + count
								+ ")" + extention);
					}	
					return path + "(" + count + ")"
					+ extention;
				} else {
					return path + extention;
				}			
			}
		}
		return null;
	}

}
