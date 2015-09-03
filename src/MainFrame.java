import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;


public class MainFrame {
	private JFrame frame;
    private EmbeddedMediaPlayerComponent mediaPlayerComponent;
	public static void main(String[] args) {
        NativeLibrary.addSearchPath(
                RuntimeUtil.getLibVlcLibraryName(), "/Applications/vlc-2.0.0/VLC.app/Contents/MacOS/lib"
            );
            Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
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
		frame.setBounds(100, 100, 909, 707);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
	      
		 mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		 final EmbeddedMediaPlayer video = mediaPlayerComponent.getMediaPlayer();
	     JPanel panel = new JPanel(new BorderLayout());
	     panel.add(mediaPlayerComponent, BorderLayout.CENTER);
	     frame.setContentPane(panel);

		
		//VideoPanel videPlayerPanel = new VideoPanel();
		//frame.getContentPane().add(videPlayerPanel, BorderLayout.CENTER);
		
		VideoButtonPanel buttonPanel = new VideoButtonPanel(video);
		frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		MenuPanel menuBar = new MenuPanel();
		frame.setJMenuBar(menuBar);
		frame.setVisible(true);
		video.playMedia("test.mp4");
		
		//
	}
}
