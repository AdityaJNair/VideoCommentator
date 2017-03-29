# WataBuffalo
**========================================================================================================================================================================================================**
											VIDEO PLAYER A2
**========================================================================================================================================================================================================**
Authors and Creators : Aditya Nair and Priyankit Singh

It is assumed that user is running on linux, has FFMPEG, Festival and VLC installed already
**========================================================================================================================================================================================================**
GUI COMPONENTS

MenuBar - top left of GUI

Media
Open File : Opens a file selected by the user. This should be of a video type file. It is expected that the user knows whether this is a correct format type video like avi, mp4 etc.
Add audio : Adds audio component to the current selected video being played, either by festival speech synthesis (user writes text) or by an existing audio file.
Quit      : Quits the program

Features
Create Festival Speech : Creates audio overlap using text specified by the user. Add Existing Audio : Adds audio overlap using an existing audio file.	
Add existing audio     : Adds existing audio over the selected video file.

Help
README : Access this file for additional help during use of this video player.
**========================================================================================================================================================================================================**
BUTTONS AND SLIDERS

Slider 		- Allows user to move to certain section of the video. Precision movement requires holding down the slider and moving to the section wanted. Or select any distance to move a fixed 			  distance from the screen.
Play   		- Plays/Pauses the video depending on its status
Rewind 		- Rewinds video
Forward 	- Forwards the video
Mute Button 	- mutes audio
Volume Slider 	- Set volume of the video player
Audio/Text to audio Button (the button with the file and speaker icon) - Allows user to select either adding a audio overlay by either an existing audio or by text.

**========================================================================================================================================================================================================**
How to use:

Playing a video

1) On the menu bar on the top left of the GUI, select the "Media" menu option and select the "Open File" option
2) Open a valid video file format from users filesystem.
3) The video will play if it is a valid format that is useable by the video player.
4) Now able to use the functinality with the buttons

Adding audio commentary, done using existing file

1) Select a valid video type file to be playable in the video player and add audio using existing file. 
	This can be done using different ways:
		Add Audio/Add existing audio on the menu bars
		Audio/Text to audio button on the main interface --> then select add existing audio
		(**IMPORTANT THE VIDEO MUST BE SELECTED FIRST BEFORE SELECTING THE AUDIO**)
2) Select these video/audio files from the users filesystem. 
3) Save the merged file to the specified location.
4) The merged video automotically plays to the videoplayer	

Adding audio commentary, done using user entered text

1) Select a valid video type file to be playable in the video player and add audio using existing file. 
	This can be done using different ways:
		Add Audio/Create Festival Speech on the menu bars
		Audio/Text to audio button on the main interface --> then select audio from text
		(**IMPORTANT THE VIDEO MUST BE SELECTED FIRST BEFORE SELECTING THE AUDIO**)
2) Enter text in the text field.
	Ensure that the text field is not empty, and that it meets the required 160 characters - up to 30 word limit.
3) User can demonstrate the text in which they hear the audio being played to them.
4) Once the user is satisfied with the text being played they can save the text audio file and merge the video playing in the background with the text~audio file
5) Save the (TEXT AUDIO FILE of the speech synthesis to a path that can be known by the user)
6) Save the merged file in another path
7) The merged video automotically plays to the videoplayer	

**========================================================================================================================================================================================================**		
FAQ/NOTES

TO USE THE FUNCTIONALITY OF ADDING AUDIO THERE MUST BE A VIDEO ENTERED IN THE VIDEO PLAYER

If a invalid video/audio format is added to the player, the player will not recognise the format and may/maynot
play the video file. 

The reason we are adding the audio files that were generated from the user entered text is so that it can be later used by the user later. The wav file can be used when adding existing audio file so future videos doesnt require the user to re enter the text again.

There is a slight delay on when the audio from text demonstrate will stop when prompted by the button.

If this video/audio file is added to the Audio/Text to audio button and the user wants to merge and error message will be shown. It is assumed that the user knows the files they are giving us are the correct format that is able to be used by this player.

There is a limit for the amount of words you can enter for the text to audio function. The limit is 30 words or a character limit of 160 (similar to a tweet). This is to ensure that festival the speech synthesiser does not start to fall of in pitch. This was found by testing, and also finding that a good amount of commentary that meets the word count can be done well using 160 characters. This can be seen by using a similar style of twitter character limits.

**========================================================================================================================================================================================================**
