import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

/** 
 * SaveAs dialog box lets users choose the filename of output file.
 * Used to save mp4 files.
 * @author Adi Nair, Priyankit Singh
 *
 */
public class SaveAs extends JFrame {

	JFileChooser fileChooser;

	/**
	 * Creates a new Save As dialog box.
	 */
	SaveAs() {
		fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Select output File Location");
	}

	/**
	 * Gets the selection path for output files from users.
	 * @return Returns the path to the location selected by the user. Null if no file selected
	 */
	public String getSelectionPath() {
		int userSelection = fileChooser.showSaveDialog(this);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToSave = fileChooser.getSelectedFile();
			File f = new File(fileToSave.getAbsolutePath() + ".mp4");
			if (f.exists()) {
				int count = 0;
				while (f.exists()) {
					count++;
					f = new File(fileToSave.getAbsolutePath() + "(" + count
							+ ")" + ".mp4");
				}
				return fileToSave.getAbsolutePath() + "(" + count + ")"
						+ ".mp4";
			} else {
				return fileToSave.getAbsolutePath() + ".mp4";
			}

		}
		return null;
	}

}
