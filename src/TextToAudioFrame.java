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

public class TextToAudioFrame {

	private JFrame frame;
	public Festival test;

	public TextToAudioFrame() {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// If Nimbus is not available, fall back to cross-platform
			try {
				UIManager.setLookAndFeel(UIManager
						.getCrossPlatformLookAndFeelClassName());
			} catch (Exception ex) {
				// not worth my time
			}
		}
		frame = new JFrame();
		frame.setResizable(false);
		frame.setSize(909, 402);
		frame.setLocationRelativeTo(null);
		// frame.setBounds(500, 500, 909, 402);
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
				"Enter Commentary: must be 160 characters (roughly 20 - 30 words)");
		lblNewLabel.setBounds(12, 0, 749, 27);
		frame.getContentPane().add(lblNewLabel);

		JButton btnSaveButton = new JButton("SAVE");
		btnSaveButton.setBounds(761, 0, 130, 177);
		frame.getContentPane().add(btnSaveButton);
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
					} else if ( textFromUser.length() <= 160 && stringList.length < 25) {
						CreateAudioFile audio = new CreateAudioFile(textFromUser, "FestivalAudio");
						audio.execute();
					} else{
						JOptionPane.showMessageDialog(frame,
							    "Too much text !",
							    "Warning",
							    JOptionPane.WARNING_MESSAGE);
					}
				}
				
			}
		});

		final JButton buttonDemonstrate = new JButton("DEMONSTRATE");
		buttonDemonstrate.setBounds(761, 178, 130, 177);
		frame.getContentPane().add(buttonDemonstrate);
		buttonDemonstrate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// the new line is a character
				String textFromUser = textPane.getText();
				String[] stringList = textPane.getText().split(" ");
				if ( textFromUser.length() <= 160 && stringList.length < 25 ) {
					if (buttonDemonstrate.getText().equals("DEMONSTRATE")) {
						buttonDemonstrate.setText("Cancel");
						test = new Festival(textFromUser);
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
