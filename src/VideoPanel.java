import java.awt.BorderLayout;

import javax.swing.JPanel;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;


public class VideoPanel extends JPanel {
	private final static EmbeddedMediaPlayerComponent MEDIAPLAYERCOMPONENT = new EmbeddedMediaPlayerComponent();
	private final static EmbeddedMediaPlayer VIDEO = MEDIAPLAYERCOMPONENT.getMediaPlayer();

	public VideoPanel(){
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(MEDIAPLAYERCOMPONENT, BorderLayout.CENTER);
	}
	
	public static void addVideo(String videoName){
		VIDEO.playMedia("test.mp4");
	}

	public static EmbeddedMediaPlayer getMediaPlayer() {
		return VIDEO;
	}
}
