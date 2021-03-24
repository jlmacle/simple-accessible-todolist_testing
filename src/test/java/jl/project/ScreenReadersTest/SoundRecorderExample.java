// https://docs.oracle.com/javase/tutorial/sound/sampled-overview.html
// https://docs.oracle.com/javase/tutorial/sound/capturing.html

package jl.project.ScreenReadersTest;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.AudioSystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//https://docs.oracle.com/javase/tutorial/sound/capturing.html
public class SoundRecorderExample 
{
	static Logger logger = LoggerFactory.getLogger(SoundRecorderExample.class);
	
	
	
	public static void main(String[] args) {
		logger.debug("Starting the audio recording");
		// Planning to record the streamed audio input from the microphone in flac format
		// https://en.wikipedia.org/wiki/FLAC
		// The FLAC format supports only integer samples, not floating-point. 
		// It can handle any PCM bit resolution from 4 to 32 bits per sample, 
		// any sampling rate from 1 Hz to 65,535 Hz in 1 Hz increments or from 10 Hz to 655,350 Hz in 10 Hz increments, 
		// and any number of channels from 1 to 8.[10] 
		// To date (version 1.3.3 of the reference encoder), 
		// FLAC encoding is limited to 24 bits per sample since no encoder for 32 bits per sample exists.
		// https://docs.oracle.com/javase/7/docs/api/javax/sound/sampled/AudioFormat.Encoding.html
		//"When I export my song as FLAC, I get asked to choose a Subformat, signed 8, 16 or 24 Bit PCM."
		AudioFormat audioFormat = new AudioFormat(Encoding.PCM_SIGNED, 0f, 24,0,0,0f, false);
		
		logger.debug("1. Defining the input port.");
		// Interface Port
		// https://docs.oracle.com/javase/7/docs/api/javax/sound/sampled/Port.html
		// https://docs.oracle.com/javase/7/docs/api/javax/sound/sampled/Port.Info.html
		// Port.Info(Class<?> lineClass, String name, boolean isSource)
		
		
		
		logger.debug("2. Defining the mixer: where the input data is placed.");
		// Interface Mixer
		// https://docs.oracle.com/javase/10/docs/api/javax/sound/sampled/Mixer.html
		// https://docs.oracle.com/javase/10/docs/api/javax/sound/sampled/class-use/Mixer.Info.html
		
		
		logger.debug("3. Defining one or more target lines, from which an application can receive the data.");
		// https://docs.oracle.com/javase/7/docs/api/javax/sound/sampled/AudioFormat.html
		// From the java tutorial
		TargetDataLine line;
		
		DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
		if()
		
		
	}

}
