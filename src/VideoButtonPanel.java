import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

/**
 * Panel containing video controls like Play, Pause, Volume controls and a Video Progress bar.
 * @author pb tech
 *
 */
public class VideoButtonPanel extends JPanel{
	private JButton rewind;
	private JButton play;
	private JButton fastForward;
	private JButton mute;
	private JButton addAudioButton;
	private JSlider volumeSlider;
	private EmbeddedMediaPlayer video;
	private JSlider videoScroll;
	private final JLabel timeLabel;
	private boolean fwd = false;
	private boolean bak = false;
	private boolean mouseClickedToScroller = false;
	private final Icon playIcon;

	/**
	 * Creates a VideoButtonPanel.
	 * @param em - The video to be controlled by the panel.
	 */
	public VideoButtonPanel(EmbeddedMediaPlayer emplayer) {
		//icons from  - http://www.iconarchive.com/show/audio-video-outline-icons-by-danieledesantis.html
		final Icon audioIcon = new ImageIcon("audio-icon.png");
		final Icon muteIcon = new ImageIcon("audio-off-icon.png");
		final Icon forwardIcon = new ImageIcon("forward-icon.png");
		final Icon pauseIcon = new ImageIcon("pause-icon.png");
		playIcon = new ImageIcon("play-icon.png");
		final Icon rewindIcon = new ImageIcon("rewind-icon.png");
		final Icon textIcon = new ImageIcon("text2speech-icon.png");
		video = emplayer;
		rewind = new JButton(rewindIcon);
		play = new JButton(playIcon);
		fastForward = new JButton(forwardIcon);
		addAudioButton = new JButton(textIcon);
		volumeSlider = new JSlider(0, 100, 50);
		mute = new JButton(audioIcon);
		videoScroll = new JSlider(0,10000);
		JLabel minLABEL = new JLabel("MIN");
		JLabel maxLABEL = new JLabel("MAX");
		timeLabel = new JLabel("HH:MM:SS");
		
		videoScroll.putClientProperty( "Slider.paintThumbArrowShape", Boolean.TRUE );
		minLABEL.setEnabled(false);
		maxLABEL.setEnabled(false);
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);	
		videoScroll.setValue(0);
		videoScroll.setEnabled(false);
		videoScroll.addMouseListener(new MouseAdapter(){
			@Override
            public void mousePressed(MouseEvent e) {
              if(!video.isPlayable()){
            	  return;
              }
              if(video.isPlaying()){
            	  mouseClickedToScroller = true;
            	  video.pause();
              } else {
            	  mouseClickedToScroller = false;
              }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            	videoScroll.setValue(videoScroll.getValue());
            	float videoTimeRatio =  (((float)videoScroll.getValue())/((float)videoScroll.getMaximum()));
				long currentTimeNew = (long) (video.getLength() * videoTimeRatio);
				video.setTime(currentTimeNew);
				video.play();
				mouseClickedToScroller = false;
            }
			
		});
		
		
		//REWIND
		rewind.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				fwd = false;
				bak = true;
				fastForward.setEnabled(true);
				if(video.isPlayable()){
					rewind.setEnabled(false);
					ProgressBarWorker p = new ProgressBarWorker();
					p.execute();
					}
				}

		});

		//PLAY
		play.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				fwd = false;
				bak = false;
				fastForward.setEnabled(true);
				rewind.setEnabled(true);
				if (video.isPlaying()) {
					video.pause();
					play.setIcon(playIcon);
				} else {
					video.play();
					play.setIcon(pauseIcon);
				}
			}

		});
		
		//FAST FORWARD
		fastForward.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				fwd = true;
				bak = false;
				rewind.setEnabled(true);
				if(video.isPlayable()){
					fastForward.setEnabled(false);
					ProgressBarWorker p = new ProgressBarWorker();
					p.execute();
				}
			}

		});
		
		//ADD AUDIO
		addAudioButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				SwitchDialogBox p =new SwitchDialogBox();
			}

		});
		
		//MUTE
		mute.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				volumeSlider.setValue(0);
				video.mute();
				if(volumeSlider.getValue() == 0){
					mute.setIcon(muteIcon);
				} else {
					mute.setIcon(audioIcon);
				}
			}

		});
		
		//VOLUME SLIDER
		volumeSlider.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
					if (!volumeSlider.getValueIsAdjusting()) {
						video.mute(false);
						video.setVolume(volumeSlider.getValue());
						if(volumeSlider.getValue() == 0){
							mute.setIcon(muteIcon);
						} else {
							mute.setIcon(audioIcon);
						}
					}
				}
		});
		
		
		Timer t = new Timer(200, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(video.isPlaying()){
					videoScroll.setEnabled(true);
					play.setIcon(pauseIcon);
					TimerWorker time = new TimerWorker();
					time.execute();
				} else {
					play.setIcon(playIcon);
				}
			}
		});
		t.start();
			

		//auto window builder generated code
		GroupLayout gl_buttonPanel = new GroupLayout(this);
		gl_buttonPanel.setHorizontalGroup(gl_buttonPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_buttonPanel.createSequentialGroup()
			.addContainerGap().addGroup(gl_buttonPanel.createParallelGroup(Alignment.TRAILING,false).addGroup(
				gl_buttonPanel.createSequentialGroup().addComponent(timeLabel,GroupLayout.DEFAULT_SIZE,
					GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE).addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(videoScroll,GroupLayout.PREFERRED_SIZE,726,GroupLayout.PREFERRED_SIZE)).addGroup(
							gl_buttonPanel.createSequentialGroup().addComponent(rewind,GroupLayout.PREFERRED_SIZE,
								93,GroupLayout.PREFERRED_SIZE).addGap(18).addComponent(play,GroupLayout.PREFERRED_SIZE,
									99,GroupLayout.PREFERRED_SIZE).addGap(18).addComponent(fastForward,GroupLayout.PREFERRED_SIZE,
										91,GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(mute,GroupLayout.PREFERRED_SIZE,89,GroupLayout.PREFERRED_SIZE)
												.addGap(30).addGroup(gl_buttonPanel.createParallelGroup(Alignment.LEADING,false).addGroup(
													gl_buttonPanel.createSequentialGroup().addComponent(volumeSlider,GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE).addGap(35)).addGroup(gl_buttonPanel
															.createSequentialGroup().addGap(9).addComponent(minLABEL).addPreferredGap(ComponentPlacement.RELATED,
																GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE).addComponent(maxLABEL,GroupLayout.PREFERRED_SIZE,35,
																	GroupLayout.PREFERRED_SIZE).addGap(39))).addGap(1).addComponent(addAudioButton,
																		GroupLayout.PREFERRED_SIZE,169,GroupLayout.PREFERRED_SIZE))).addContainerGap(1047, Short.MAX_VALUE)));
		
		gl_buttonPanel.setVerticalGroup(gl_buttonPanel.createParallelGroup(Alignment.TRAILING).addGroup(gl_buttonPanel
			.createSequentialGroup().addGap(14).addGroup(gl_buttonPanel.createParallelGroup(Alignment.LEADING).addComponent(
				videoScroll,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE).addComponent(
					timeLabel)).addPreferredGap(ComponentPlacement.RELATED, 16,Short.MAX_VALUE).addGroup(gl_buttonPanel
						.createParallelGroup(Alignment.TRAILING,false).addComponent(addAudioButton,GroupLayout.DEFAULT_SIZE,
							GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE).addComponent(mute,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE).addGroup(gl_buttonPanel.createSequentialGroup().addComponent(volumeSlider,GroupLayout.PREFERRED_SIZE,
									GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE).addGap(18).addGroup(gl_buttonPanel.createParallelGroup(
										Alignment.BASELINE).addComponent(minLABEL).addComponent(maxLABEL)).addGap(6)).addComponent(fastForward,
											GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE).addComponent(
												play,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE).addComponent(rewind,
													GroupLayout.DEFAULT_SIZE,66,Short.MAX_VALUE)).addContainerGap()));
		this.setLayout(gl_buttonPanel);
	}

	/**
	 * Controls time elements like progress bar and video length label.
	 *
	 */
	class TimeScroller{
		public int time;
		public String str;
		TimeScroller(int t, String s){
			this.time = t;
			this.str = s;
		}
	}
	
	class TimerWorker extends SwingWorker<Void,TimeScroller>{

		@Override
		protected Void doInBackground() throws Exception {
			try{
				
				
				long timeCurrent = video.getTime()/1000;
				long videoLength = video.getLength()/1000;
				double timeHolder = ((double)timeCurrent)/((double)videoLength)*10000;
				if(videoLength/60 == -1){
					publish(new TimeScroller(0,"00:00:00"));
				} else {
					publish(new TimeScroller((int) timeHolder,String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(video.getTime()), TimeUnit.MILLISECONDS.toMinutes(video.getTime()) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(video.getTime())), TimeUnit.MILLISECONDS.toSeconds(video.getTime()) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(video.getTime())))));
				}
			} catch (Exception time){
				System.out.println("Video hasnt loaded yet");
			}
			return null;
		}
		
		@Override
		protected void process(List<TimeScroller> chunks){
			for(TimeScroller s: chunks){
				if(!mouseClickedToScroller){
					videoScroll.setValue(s.time);
					timeLabel.setText(s.str);
				}
			}
		}
	}
	
	/**
	 * Controls the video progress controls like fast forward, rewind and pause
	 *
	 */
	class ProgressBarWorker extends SwingWorker<Void, Void>{
		
		@Override
		protected Void doInBackground() throws Exception {
			System.out.println(video.isPlayable());
			if(video.isPlayable()){
				if(fwd & !bak){
					while(fwd){
						if(video.getTime() >= video.getLength()){
							fastForward.setEnabled(true);
							break;
						}
						if(video.isPlaying()){
							video.skip(6);
						}else {
							video.play();
							video.mute(true);
							video.skip(6);
						}
					}
				} else if(bak & !fwd){
					while(bak){
						if(video.getTime() <= 0){
							rewind.setEnabled(true);
							break;
						}
						if(video.isPlaying()){
							video.skip(-6);
						}else {
							video.play();
							video.mute(true);
							video.skip(-6);
						}
					}
				}
			}
			return null;
		}
		
		@Override
		public void done(){
			System.out.println("DONE");
			video.mute(false);
			video.pause();
			video.play();
			if(video.isPlaying()){
				play.setIcon(playIcon);
			}
		}
		
	}
}
