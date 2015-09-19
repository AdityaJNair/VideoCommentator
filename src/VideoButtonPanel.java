import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

public class VideoButtonPanel extends JPanel{
	private JButton rewind;
	private JButton play;
	private JButton fastForward;
	private JButton mute;
	private JButton addAudioButton;
	private JSlider volumeSlider;
	private EmbeddedMediaPlayer video;
	private JProgressBar progressBar;
	private final JLabel lblNewLabel;
	private boolean fwd = false;
	private boolean bak = false;

	public VideoButtonPanel(EmbeddedMediaPlayer em) {
		video = em;
		rewind = new JButton("<<");
		play = new JButton("►");
		fastForward = new JButton(">>");
		addAudioButton = new JButton("\"TXT\" --> ♫");
		volumeSlider = new JSlider(0, 100, 50);
		mute = new JButton("🔊");
		
		JLabel minLABEL = new JLabel("♩");
		minLABEL.setEnabled(false);

		JLabel maxLABEL = new JLabel("♬");
		maxLABEL.setEnabled(false);

		progressBar = new JProgressBar(0,1000);

		lblNewLabel = new JLabel("0m 0s|0m 0s");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);		
		

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
					play.setText("►");
				} else {
					video.play();
					play.setText("||");
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
			}

		});
		
		//VOLUME SLIDER
		volumeSlider.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
					if (!volumeSlider.getValueIsAdjusting()) {
						System.out.println(volumeSlider.getValue());
						video.mute(false);
						video.setVolume(volumeSlider.getValue());
					}
				}
		});
		
		
		Timer t = new Timer(200, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				TimerWorker time = new TimerWorker();
				time.execute();
			}
		});
		t.start();
			

		//auto window builder generated code
		GroupLayout gl_buttonPanel = new GroupLayout(this);
		gl_buttonPanel.setHorizontalGroup(gl_buttonPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_buttonPanel.createSequentialGroup()
			.addContainerGap().addGroup(gl_buttonPanel.createParallelGroup(Alignment.TRAILING,false).addGroup(
				gl_buttonPanel.createSequentialGroup().addComponent(lblNewLabel,GroupLayout.DEFAULT_SIZE,
					GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE).addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(progressBar,GroupLayout.PREFERRED_SIZE,726,GroupLayout.PREFERRED_SIZE)).addGroup(
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
				progressBar,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE).addComponent(
					lblNewLabel)).addPreferredGap(ComponentPlacement.RELATED, 16,Short.MAX_VALUE).addGroup(gl_buttonPanel
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


	class TimerWorker extends SwingWorker<Void,Void>{

		@Override
		protected Void doInBackground() throws Exception {
			try{
				long timeCurrent = video.getTime()/1000;
				long videoLength = video.getLength()/1000;
				double timeHolder = (double)timeCurrent/videoLength*1000;
				if(videoLength/60 == -1){
					lblNewLabel.setText("0m 0s|0m 0s");
					progressBar.setValue(0);
				} else {
					lblNewLabel.setText(timeCurrent/60+"m "+timeCurrent%60+"s|"+videoLength/60+"m "+videoLength%60+"s");
					progressBar.setValue((int) timeHolder);
				}
			} catch (Exception time){
				System.out.println("Video hasnt loaded yet");
			}
			return null;
		}
		
	}
	
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
			
		}
		
		
	}
}
