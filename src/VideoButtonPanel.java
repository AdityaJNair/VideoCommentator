import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;


public class VideoButtonPanel extends JPanel implements ActionListener, ChangeListener{
	JButton rewind = new JButton("<<");
	JButton play= new JButton("");
	JButton fastForward  = new JButton(">>");
	JButton mute = new JButton("");
	JButton addAudioButton  = new JButton("Add Audio");
	JSlider volumeSlider = new JSlider(0,100,50);
	EmbeddedMediaPlayer video;
	public VideoButtonPanel(EmbeddedMediaPlayer em){
		video = em;
		rewind.addActionListener(this);
	
		play.setIcon(new ImageIcon(MainFrame.class.getResource("/com/sun/javafx/webkit/prism/resources/mediaPlayDisabled.png")));
		play.addActionListener(this);
		
		fastForward.addActionListener(this);
		
		addAudioButton.addActionListener(this);
		
		JLabel minLABEL = new JLabel("MIN");
		minLABEL.setEnabled(false);
		
		mute.setIcon(new ImageIcon(MainFrame.class.getResource("/com/sun/javafx/webkit/prism/resources/mediaMuteDisabled.png")));
		mute.addActionListener(this);
		
		volumeSlider.addChangeListener(this);
		
		
		JLabel maxLABEL = new JLabel("MAX");
		maxLABEL.setEnabled(false);
		
		GroupLayout gl_buttonPanel = new GroupLayout(this);
		gl_buttonPanel.setHorizontalGroup(
			gl_buttonPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_buttonPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(rewind, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(play, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(fastForward, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(mute, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_buttonPanel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(volumeSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_buttonPanel.createSequentialGroup()
							.addGap(10)
							.addComponent(minLABEL)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(maxLABEL)))
					.addGap(58)
					.addComponent(addAudioButton, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_buttonPanel.setVerticalGroup(
			gl_buttonPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_buttonPanel.createSequentialGroup()
					.addGroup(gl_buttonPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(mute, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
						.addComponent(fastForward, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(rewind, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
						.addComponent(play, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, gl_buttonPanel.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(addAudioButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(Alignment.LEADING, gl_buttonPanel.createSequentialGroup()
								.addGap(24)
								.addComponent(volumeSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_buttonPanel.createParallelGroup(Alignment.LEADING)
									.addComponent(minLABEL)
									.addComponent(maxLABEL))
								.addGap(14))))
					.addContainerGap())
		);
		this.setLayout(gl_buttonPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == rewind){
			video.skip(-5000);
		} else if (e.getSource() == play){
			video.play();
		}else if (e.getSource() == fastForward){
			video.skip(5000);
		}else if (e.getSource() == addAudioButton){
			
		}else if (e.getSource() == mute){
			volumeSlider.setValue(0);
			video.mute();
		}
		
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		Object source = arg0.getSource();
		if(source instanceof JSlider){
			JSlider vol = (JSlider) source;
			if(!vol.getValueIsAdjusting()){
				System.out.println(vol.getValue());
				video.setVolume(vol.getValue());
			}
		}
	}
}
