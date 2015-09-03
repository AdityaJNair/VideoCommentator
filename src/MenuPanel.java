import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;


public class MenuPanel extends JMenuBar{
	//the menu and its listeners
	public MenuPanel(){
		JMenu mediaMenu = new JMenu("Media");
		this.add(mediaMenu);
		
		JMenuItem openFileMenuItem = new JMenuItem("Open File");
		openFileMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				System.out.println("Open file");
			}
		});
		mediaMenu.add(openFileMenuItem);
		
		JMenuItem addAudioMenuItem = new JMenuItem("Add Audio");
		addAudioMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				System.out.println("add audio");
			}
		});
		mediaMenu.add(addAudioMenuItem);
		
		JMenuItem quitMenuItem = new JMenuItem("Quit");
		quitMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				System.out.println("quit");
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
				System.out.println("add existing audio");
			}
		});
		featuresMenu.add(addExistingAudioMenuItem);
		
		JMenu subtitlesMenu = new JMenu("Subtitles");
		this.add(subtitlesMenu);
		
		JMenuItem addSubMenuItem = new JMenuItem("Add Subtitles");
		addSubMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				System.out.println("add subs");
			}
		});
		subtitlesMenu.add(addSubMenuItem);
	}
}
