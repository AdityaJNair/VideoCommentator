import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;


public class SaveAs extends JFrame{

	SaveAs(){
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Select output File Location");

		int userSelection = fileChooser.showSaveDialog(this);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToSave = fileChooser.getSelectedFile();
			System.out.println("Save as file: " + fileToSave.getAbsolutePath());
		}
	}
}
