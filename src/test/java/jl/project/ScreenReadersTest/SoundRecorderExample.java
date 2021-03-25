// https://docs.oracle.com/javase/tutorial/sound/sampled-overview.html
// https://docs.oracle.com/javase/tutorial/sound/capturing.html

package jl.project.ScreenReadersTest;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.Line.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.AudioSystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



//https://docs.oracle.com/javase/tutorial/sound/capturing.html
public class SoundRecorderExample 
{
	static Logger logger = LoggerFactory.getLogger(SoundRecorderExample.class);
	
	
	
	public static void main(String[] args) {
		// from https://stackoverflow.com/questions/3705581/java-sound-api-capturing-microphone
		//Retrieves audio information from the operating system
		Line line_microphone = null;
		Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();
		for (Mixer.Info mixerInfo: mixerInfos)
		{
			Mixer mixer = AudioSystem.getMixer(mixerInfo); // Using the instance of Mixer.Info as the key to get a mixer
			Info[] lineInfos = mixer.getSourceLineInfo();
			for (Line.Info lineInfo:lineInfos)
			{
				logger.debug(String.format("**** Name of the mixer: *%s*",mixerInfo.getName()));
				logger.debug("Line info: "+lineInfo);
				Line line;
				try 
				{
					line = mixer.getLine(lineInfo); // Using the instance of Line.info as the key to get a line
					logger.debug("Line: "+line);
					
					//To be adapted for your microphone or an other audio recording line.
					if(mixerInfo.getName() == "Port Microphone Array (Realtek(R) Au" )
					{
						logger.debug("Port Microphone Array mixer found.");
						line_microphone = line;
					}
				} 
				catch (LineUnavailableException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		
		logger.debug("Starting the audio recording");
		// Planning to record the streamed audio input from the microphone in flac format
		// https://en.wikipedia.org/wiki/FLAC
		// The FLAC format supports only integer samples, not floating-point. 
		// It can handle any PCM bit resolution from 4 to 32 bits per sample, 
		// any sampling rate from 1 Hz to 65,535 Hz in 1 Hz increments or from 10 Hz to 655,350 Hz in 10 Hz increments, 
		// and any number of channels from 1 to 8.[10] 
		// To date (version 1.3.3 of the reference encoder), 
		// FLAC encoding is limited to 24 bits per sample since no encoder for 32 bits per sample exists.
		// "any number of channels from 1 to 8"
		// https://docs.oracle.com/javase/7/docs/api/javax/sound/sampled/AudioFormat.Encoding.html
		//"When I export my song as FLAC, I get asked to choose a Subformat, signed 8, 16 or 24 Bit PCM."
		// https://en.wikipedia.org/wiki/Bit_rate
		// https://www.wildlifeacoustics.com/resources/faqs/what-is-an-audio-channel
		// "A channel is a representation of sound coming from or going to a single point. 
		// A single microphone can produce one channel of audio, and a single speaker can accept one channel of audio, for example."
		boolean isSigned = true;
		//AudioFormat flacFormat = new AudioFormat(100, 24, 1, isSigned, false);
		
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
		//TargetDataLine line;
		
		//DataLine.Info dataLineInfo = (AudioSystem.getMixerInfo()).
		//Opening of the line
		/*
		 * try { line = (TargetDataLine)AudioSystem.getLine(dataLineInfo);
		 * line.open(flacFormat); } catch (LineUnavailableException e) { logger.
		 * debug("Caught a LineUnavailableException while getting the target data line."
		 * );
		 * 
		 * e.printStackTrace(); }
		 */
	}

}
