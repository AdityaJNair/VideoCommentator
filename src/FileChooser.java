import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooser {
	JFileChooser chooser;
	FileNameExtensionFilter filter;
	
	FileChooser(String description, String extention) {
		chooser = new JFileChooser();
		filter = new FileNameExtensionFilter(
				description, extention);
	}
	/**
	 * Displays a file chooser window and gets the user Choice.
	 * >>>>> Add return statement to return the file <<<<<<
	 */
	public void chooseFile(){
		int returnVal = chooser.showOpenDialog(new JPanel());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			System.out.println("You chose to open this file: "
					+ chooser.getSelectedFile().getName());
		}
	}
}
