import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Creates a window that lets users to select a file from their system.
 * @author Adi Nair, Priyankit Singh
 *
 */
public class FileChooser {
	JFileChooser chooser;
	FileNameExtensionFilter filter;

	/**
	 * Creates a FileChooser object that selects files with one extention.
	 * @param description
	 * @param extention1
	 */
	FileChooser(String description, String extention1) {
		chooser = new JFileChooser();
		filter = new FileNameExtensionFilter(description, extention1);
		chooser.setFileFilter(filter);
	}
	/**
	 * Creates a FileChooser object that selects files with two extensions.
	 * @param description
	 * @param extention1
	 * @param extension2
	 */
	FileChooser(String description, String extention1, String extension2) {
		chooser = new JFileChooser();
		filter = new FileNameExtensionFilter(description, extention1, extension2);
		chooser.setFileFilter(filter);
	}
	
	/**
	 * Creates a FileChooser object that selects files with three extensions.
	 * @param description
	 * @param extention1
	 * @param extention2
	 * @param extension3
	 */
	FileChooser(String description, String extention1, String extention2, String extension3) {
		chooser = new JFileChooser();
		filter = new FileNameExtensionFilter(description, extention1, extention2, extension3);
		chooser.setFileFilter(filter);
	}
	
	
	/**
	 * Creates a FileChooser object that selects files with four extensions.
	 * @param description
	 * @param extention1
	 * @param extention2
	 * @param extension3
	 * @param extension4
	 */
	FileChooser(String description, String extention1, String extention2, String extension3, String extension4) {
		chooser = new JFileChooser();
		filter = new FileNameExtensionFilter(description, extention1, extention2, extension3, extension4);
		chooser.setFileFilter(filter);
	}

	/**
	 * Displays a file chooser window and gets the user Choice.
	 * Returns null if file not found.
	 */
	public File chooseFile() {
		int returnVal = chooser.showOpenDialog(new JPanel());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return (chooser.getSelectedFile());
		} else {
			return null;
		}
	}
}
