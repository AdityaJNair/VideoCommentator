package videoplayer.audiofunctionality;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import videoplayer.videofunctionality.FileChooser;
import videoplayer.videofunctionality.SaveAs;
import videoplayer.videoscreen.MainFrame;

/**
 * A frame that lets users choose between adding existing audio file or create new file 
 * to the video.
 * @author Adi Nair, Priyankit Singh
 *
 */
public class SwitchDialogBox implements ActionListener{
	private JFrame frame;
	private JButton addExistingAudioButton;
	private JButton audioTextButton; 
	
	/**
	 * Creates a SwitchDialogBox object.
	 */
	public SwitchDialogBox(){
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
		frame.setSize(459, 220);
		frame.setLocationRelativeTo(null);
		//frame.setBounds(500, 500, 459, 220);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel switchLabelPanel = new JPanel();
		switchLabelPanel.setBounds(12, 13, 417, 85);
		frame.getContentPane().add(switchLabelPanel);
		switchLabelPanel.setLayout(null);
		
		JLabel switchLabel = new JLabel("Enter Existing audio file or add audio from text.");
		switchLabel.setHorizontalAlignment(SwingConstants.CENTER);
		switchLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		switchLabel.setBounds(0, 0, 417, 87);
		switchLabelPanel.add(switchLabel);
		
		JPanel switchButtonPanel = new JPanel();
		switchButtonPanel.setBounds(12, 111, 417, 54);
		frame.getContentPane().add(switchButtonPanel);
		switchButtonPanel.setLayout(null);
		
		audioTextButton = new JButton("Audio from Text");
		audioTextButton.setBounds(0, 0, 186, 54);
		switchButtonPanel.add(audioTextButton);
		audioTextButton.addActionListener(this);
		
		addExistingAudioButton = new JButton("Existing audio file");
		addExistingAudioButton.setBounds(231, 0, 186, 54);
		switchButtonPanel.add(addExistingAudioButton);
		addExistingAudioButton.addActionListener(this);
		frame.setVisible(true);
		
	}
	//Action for the buttons, in the switch dialog box
	@Override
	public void actionPerformed(ActionEvent e) {
		//This is for the existing audio, in which user adds audio from an existing audio file
		if(e.getSource() == addExistingAudioButton){
			//Check video existing or not
			if(MainFrame.videoName == null){
				JOptionPane.showMessageDialog(null,
					    "Please select a valid video file before continuing",
					    "Video File Selection",
					    JOptionPane.OK_OPTION);
				FileChooser fc = new FileChooser("Video files", "mp4","avi");
				File videoFile = fc.chooseFile();
				MainFrame.setVideo(videoFile);
			}
			//Add audio file from filesystem
			if(MainFrame.videoName != null){
				JOptionPane.showMessageDialog(null,
					    "Please select a audio file to add on the video you selected",
					    "Audio File Selection",
					    JOptionPane.OK_OPTION);
				FileChooser fc = new FileChooser("Audio Files", "mp3","wav");
				try{
				File audioFile = fc.chooseFile();
				String path = audioFile.getAbsolutePath();
				JOptionPane.showMessageDialog(null,
					    "Select the location where you want to save the merged file",
					    "Merged File Saving Location",
					    JOptionPane.OK_OPTION);
				//Save to a path by a user (the merged file)
				SaveAs sa = new SaveAs("mp4", "Select output Video File Location");
				String outputPath = sa.getSelectionPath();
				CombineAudioVideo combine = new CombineAudioVideo(path, MainFrame.videoName,outputPath);
				combine.execute();
				} catch (NullPointerException e3){
					
				}
				frame.dispose();
			}
		}else if( e.getSource() == audioTextButton){
			//Opens a new frame that allows the user to add audio commentary from a text file.
			TextToAudioFrame t = new TextToAudioFrame();
			frame.dispose();
		}
	}
}
