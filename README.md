# WataBuffalo
videos of buffalo
This should be formatted
ffmpeg -i (input mp4 video) -i (name of audio file want to overlay) -filter_complex amix=inputs=2 (output file .mp4)
ffmpeg -i VfE_html5.mp4 -i festivalWave.wav -filter_complex amix=inputs=2 out2.mp4

Then to do the festival
Add string to a text file
text2wave speakFile.txt -o festivalWave.wav
text2wave (textfile) -o (output file.wav)

