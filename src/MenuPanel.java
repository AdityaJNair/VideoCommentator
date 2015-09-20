import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * A menu bar that lets users navigate the program.
 * @author Adi Nair, Priyankit Singh
 *
 */
public class MenuPanel extends JMenuBar{
	
	/**
	 * Creates a MenuPanel object.
	 */
	public MenuPanel(){
		JMenu mediaMenu = new JMenu("Media");
		this.add(mediaMenu);
		
		JMenuItem openFileMenuItem = new JMenuItem("Open File");
		openFileMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				FileChooser fc = new FileChooser("Video files", "mp4", "avi");
				File videoFile = fc.chooseFile();

				MainFrame.setVideo(videoFile);
			}
		});
		mediaMenu.add(openFileMenuItem);
		
		JMenuItem addAudioMenuItem = new JMenuItem("Add Audio");
		addAudioMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				SwitchDialogBox s = new SwitchDialogBox();
			}
		});
		mediaMenu.add(addAudioMenuItem);
		
		JMenuItem quitMenuItem = new JMenuItem("Quit");
		quitMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		mediaMenu.add(quitMenuItem);
		
		JMenu featuresMenu = new JMenu("Features");
		this.add(featuresMenu);
		
		JMenuItem createFestivalMenuItem = new JMenuItem("Create Festival Speech");
		createFestivalMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				System.out.println("festival");
			}
		});
		featuresMenu.add(createFestivalMenuItem);
		
		JMenuItem addExistingAudioMenuItem = new JMenuItem("Add Existing Audio");
		addExistingAudioMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(MainFrame.videoName == null){
					JOptionPane.showMessageDialog(null,
						    "Please select a valid video file before continuing",
						    "OK",
						    JOptionPane.OK_OPTION);
					FileChooser fc = new FileChooser("Video files", "mp4","avi");
					File videoFile = fc.chooseFile();
					MainFrame.setVideo(videoFile);
				}
				if(MainFrame.videoName != null){
					JOptionPane.showMessageDialog(null,
						    "Please select a audio file to add on the video you selected",
						    "OK",
						    JOptionPane.OK_OPTION);
					FileChooser fc = new FileChooser("Audio Files", "mp3","wav");
					try{
					File audioFile = fc.chooseFile();
					String path = audioFile.getAbsolutePath();
					System.out.println(path);
					SaveAs sa = new SaveAs();
					String outputPath = sa.getSelectionPath();
					CombineAudioVideo combine = new CombineAudioVideo(path, MainFrame.videoName,outputPath);
					combine.execute();
					} catch (NullPointerException e3){
						
					}
				}
			}
		});
		featuresMenu.add(addExistingAudioMenuItem);
		
		JMenu subtitlesMenu = new JMenu("Help");
		this.add(subtitlesMenu);
		
		JMenuItem addSubMenuItem = new JMenuItem("READ ME");
		addSubMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				ProcessBuilder pb = new ProcessBuilder("gedit", "README");
				try {
					pb.start();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		subtitlesMenu.add(addSubMenuItem);
	}
}
