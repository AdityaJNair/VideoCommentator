import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooser {
	JFileChooser chooser;
	FileNameExtensionFilter filter;

	FileChooser(String description, String extention1) {
		chooser = new JFileChooser();
		filter = new FileNameExtensionFilter(description, extention1);
		chooser.setFileFilter(filter);
	}
	
	FileChooser(String description, String extention1, String extension2) {
		chooser = new JFileChooser();
		filter = new FileNameExtensionFilter(description, extention1, extension2);
		chooser.setFileFilter(filter);
	}
	
	FileChooser(String description, String extention1, String extention2, String extension3) {
		chooser = new JFileChooser();
		filter = new FileNameExtensionFilter(description, extention1, extention2, extension3);
		chooser.setFileFilter(filter);
	}
	
	FileChooser(String description, String extention1, String extention2, String extension3, String extension4) {
		chooser = new JFileChooser();
		filter = new FileNameExtensionFilter(description, extention1, extention2, extension3, extension4);
		chooser.setFileFilter(filter);
	}

	/**
	 * Displays a file chooser window and gets the user Choice.
	 */
	public File chooseFile() {
		int returnVal = chooser.showOpenDialog(new JPanel());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			System.out.println("You chose to open this file: "
					+ chooser.getSelectedFile().getName());
			return (chooser.getSelectedFile());
		} else {
			return null;
		}
	}
}
