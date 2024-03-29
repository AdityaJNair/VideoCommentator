package videoplayer.videoscreen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import videoplayer.audiofunctionality.CombineAudioVideo;
import videoplayer.audiofunctionality.SwitchDialogBox;
import videoplayer.audiofunctionality.TextToAudioFrame;
import videoplayer.videofunctionality.FileChooser;
import videoplayer.videofunctionality.SaveAs;

/**
 * A menu bar that lets users navigate the program from one place.
 * Contains important functions like open file and exit.
 * @author Adi Nair, Priyankit Singh
 *
 */
public class MenuPanel extends JMenuBar{
	
	/**
	 * Creates a MenuPanel object. These are the menubars for the program that holds functionality and allows access that is otherwise done
	 * by the Buttons.
	 */
	public MenuPanel(){
		
		//The media panel which contains Open File, Add Audio and Quit
		JMenu mediaMenu = new JMenu("Media");
		this.add(mediaMenu);
		
		//Opens a video file to be played on the video player
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
		
		//Adds audio to the file either form existing or text by user.
		JMenuItem addAudioMenuItem = new JMenuItem("Add Audio");
		addAudioMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				SwitchDialogBox s = new SwitchDialogBox();
			}
		});
		mediaMenu.add(addAudioMenuItem);
		
		//Quits the program
		JMenuItem quitMenuItem = new JMenuItem("Quit");
		quitMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		mediaMenu.add(quitMenuItem);
		
		//Feature menubar that has - Create Festival Speech, Add Existing audio
		JMenu featuresMenu = new JMenu("Features");
		this.add(featuresMenu);
		
		//Add text by the user that will be used to overlay on the video
		JMenuItem createFestivalMenuItem = new JMenuItem("Create Festival Speech");
		createFestivalMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				TextToAudioFrame t = new TextToAudioFrame();
			}
		});
		featuresMenu.add(createFestivalMenuItem);
		
		//Add existing audio to overlap current video
		JMenuItem addExistingAudioMenuItem = new JMenuItem("Add Existing Audio");
		addExistingAudioMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(MainFrame.videoName == null){
					JOptionPane.showMessageDialog(null,
						    "Please select a valid video file before continuing",
						    "Video File Selection",
						    JOptionPane.OK_OPTION);
					FileChooser fc = new FileChooser("Video files", "mp4","avi");
					File videoFile = fc.chooseFile();
					MainFrame.setVideo(videoFile);
				}
				if(MainFrame.videoName != null){
					JOptionPane.showMessageDialog(null,
						    "Please select a audio file to add on the video you selected",
						    "Audio File Selection",
						    JOptionPane.OK_OPTION);
					FileChooser fc = new FileChooser("Audio Files", "mp3","wav");
					try{
					File audioFile = fc.chooseFile();
					String path = audioFile.getAbsolutePath();
					System.out.println(path);
					JOptionPane.showMessageDialog(null,
						    "Select the location where you want to save the merged file",
						    "Merged File Saving Location",
						    JOptionPane.OK_OPTION);
					SaveAs sa = new SaveAs("mp4", "Select output Video File Location");
					String outputPath = sa.getSelectionPath();
					CombineAudioVideo combine = new CombineAudioVideo(path, MainFrame.videoName,outputPath);
					combine.execute();
					} catch (NullPointerException e3){
						
					}
				}
			}
		});
		featuresMenu.add(addExistingAudioMenuItem);
		
		//The Help to access README
		JMenu subtitlesMenu = new JMenu("Help");
		this.add(subtitlesMenu);
		
		JMenuItem addSubMenuItem = new JMenuItem("READ ME");
		addSubMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				BufferedReader txtReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("README")));
				String path = System.getProperty("user.dir")+System.getProperty("file.separator")+"README.txt";
				File subReadMe = new File(path);
				subReadMe.canWrite();
				String line = null;
				try {
					if(!subReadMe.exists()){
						FileWriter fileWriter = new FileWriter(path,true);
						while ((line = txtReader.readLine()) != null){
							if(subReadMe.exists()){
								fileWriter.write(line+System.getProperty("line.separator"));
							}
						}
						fileWriter.close();	
					}
				} catch (Exception e3) {
				}
		
				subReadMe.setReadOnly();
				ProcessBuilder pb = new ProcessBuilder("gedit",path);
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
