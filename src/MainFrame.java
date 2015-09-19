import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

public class MainFrame {
	private JFrame frame;
	private static EmbeddedMediaPlayerComponent mediaPlayerComponent;
    private static EmbeddedMediaPlayer video;
    private VideoButtonPanel buttonPanel;
    private MenuPanel menuBar;
    private JPanel panel;
    public static String videoName = null;

	public static void main(String[] args) {
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
				ex.printStackTrace();
			}
		}
		try {
			NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(),
					"/Applications/vlc-2.0.0/VLC.app/Contents/MacOS/lib");
			Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
		} catch (Exception e) {
			System.out.println("Not found");
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainFrame() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setSize(900, 700);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.setVisible(true);
		mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		video = mediaPlayerComponent.getMediaPlayer();
		
	    panel = new JPanel(new BorderLayout());
	    panel.add(mediaPlayerComponent, BorderLayout.CENTER);
	    frame.getContentPane().add(panel, BorderLayout.CENTER);
		
		buttonPanel = new VideoButtonPanel(video);
		frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		menuBar = new MenuPanel();
		frame.setJMenuBar(menuBar);
	}
	
	public static void setVideo(File file){
		try{
		String fileName = file.getAbsolutePath();
		videoName = fileName;
		System.out.println(videoName);
		video.playMedia(fileName);
		} catch (NullPointerException pointe){
			
		}
	}

}
