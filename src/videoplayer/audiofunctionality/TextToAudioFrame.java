package videoplayer.audiofunctionality;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import videoplayer.videofunctionality.FileChooser;
import videoplayer.videofunctionality.SaveAs;
import videoplayer.videoscreen.MainFrame;

/**
 * Frame that lets users create text comments, demonstrate them and add them to a video.
 * @author Adi Nair, Priyankit Singh
 *
 */
public class TextToAudioFrame {

	private JFrame frame;
	public Festival test;

	/**
	 * Creates a TextToAudioFrame object. Creates the frame for adding audio comments to the video selected by the user.
	 * Contains a text field and two buttons that lets users to add text commentary to a video file.
	 * Buttons : 
	 * Demonstrate - Demonstrates the text by playing it to the users and allows the user to cancel the audio
	 * Save - saves the text in the text box as an audio file and overlays it on the video.
	 */
	public TextToAudioFrame() {
		// Source : http://stackoverflow.com/questions/4617615/how-to-set-nimbus-look-and-feel-in-main
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			try {
				UIManager.setLookAndFeel(UIManager
						.getCrossPlatformLookAndFeelClassName());
			} catch (Exception ex) {
			}
		}
		frame = new JFrame();
		frame.setResizable(false);
		frame.setSize(909, 402);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Create Festival Speech");
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setVisible(true);
		frame.getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 26, 761, 329);
		frame.getContentPane().add(scrollPane);

		final JTextPane textPane = new JTextPane();
		textPane.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textPane.setToolTipText("Enter in commentary and when finished press 'SAVE' to save the audio file or 'DEMONSTRATE' to hear the file.");
		scrollPane.setViewportView(textPane);

		JLabel lblNewLabel = new JLabel(
				"Enter Commentary: must be 160 characters (or up to 30 words).");
		lblNewLabel.setBounds(12, 0, 749, 27);
		frame.getContentPane().add(lblNewLabel);

		JButton btnSaveButton = new JButton("SAVE");
		btnSaveButton.setBounds(761, 0, 130, 177);
		frame.getContentPane().add(btnSaveButton);
		
		// Action listeners for Save button.
		//Saves the text from the user to a wav file that is saved to a place told by user,
		//Then saves the merged file to another location .mp4 (video merged file)
		btnSaveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String textFromUser = textPane.getText();
				String[] stringList = textPane.getText().split(" ");
				if(MainFrame.videoName == null){
					JOptionPane.showMessageDialog(frame,
						    "Please select a valid video file before continuing",
						    "OK",
						    JOptionPane.OK_OPTION);
					FileChooser fc = new FileChooser("Video files", "mp4", "avi");
					File videoFile = fc.chooseFile();
					MainFrame.setVideo(videoFile);					
				}
				if(MainFrame.videoName != null){
					if (textFromUser.trim().length() == 0){
						JOptionPane.showMessageDialog(frame,
							    "You have not entered any text in the text field",
							    "Warning",
							    JOptionPane.WARNING_MESSAGE);
					} else if ( textFromUser.length() <= 160 && stringList.length < 30) {
						JOptionPane.showMessageDialog(null,
							    "Select the location where you want to save the festival audio file",
							    "Festival File Saving Location",
							    JOptionPane.OK_OPTION);
						SaveAs sa = new SaveAs("wav", "Select output Audio File Location");
						String path = sa.getSelectionPath();
						CreateAudioFile audio = new CreateAudioFile(textFromUser, path);
						audio.execute();
						frame.dispose();
					} else{
						JOptionPane.showMessageDialog(frame,
							    "Too much text !",
							    "Warning",
							    JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});

		//LIMITING TO 160 CHARACTERS and up to 30 WORDS
		final JButton buttonDemonstrate = new JButton("DEMONSTRATE");
		buttonDemonstrate.setBounds(761, 178, 130, 177);
		frame.getContentPane().add(buttonDemonstrate);
		buttonDemonstrate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// the new line is a character
				String textFromUser = textPane.getText();
				String[] stringList = textPane.getText().split(" ");
				if ( textFromUser.length() <= 160 && stringList.length < 31 ) {
					if (buttonDemonstrate.getText().equals("DEMONSTRATE")) {
						buttonDemonstrate.setText("CANCEL");
						test = new Festival(textFromUser, buttonDemonstrate);
						test.execute();
					} else {
						buttonDemonstrate.setText("DEMONSTRATE");
						test.cancelThread();
					}
				} else {
					JOptionPane.showMessageDialog(frame,
						    "Too much text !",
						    "Warning",
						    JOptionPane.WARNING_MESSAGE);
				}
			}
		});

	}
}
