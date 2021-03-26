// https://docs.oracle.com/javase/tutorial/sound/sampled-overview.html
// https://docs.oracle.com/javase/tutorial/sound/capturing.html

package jl.project.ScreenReadersTest;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

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

import com.itextpdf.io.source.ByteArrayOutputStream;



//https://docs.oracle.com/javase/tutorial/sound/capturing.html
public class SoundRecorderExample 
{
	static Logger logger = LoggerFactory.getLogger(SoundRecorderExample.class);
	
	
	
	public static void main(String[] args) {
		// from https://stackoverflow.com/questions/3705581/java-sound-api-capturing-microphone
		//Retrieves audio information from the operating system
		TargetDataLine line_microphone = null;
		logger.debug("Getting the available MixerInfo from the AudioSystem class");
		Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();
		for (Mixer.Info mixerInfo: mixerInfos)
		{
			Mixer mixer = AudioSystem.getMixer(mixerInfo); // Using the instance of Mixer.Info as the key to get a mixer
			Info[] lineInfos = mixer.getSourceLineInfo();
			for (Line.Info lineInfo:lineInfos)
			{
				logger.debug(String.format("**** Name of the mixer: *%s*",mixerInfo.getName()));
				logger.debug("Line info: "+lineInfo);
				TargetDataLine line;
				try 
				{
					line = (TargetDataLine)mixer.getLine(lineInfo); // Using the instance of Line.info as the key to get a line
					logger.debug("Line: "+line);		
					
					//To be adapted for your microphone or an other audio recording line.					
					if(mixerInfo.getName() == "Port Microphone Array (Realtek(R) Au" )
					{
						logger.debug("Port Microphone Array mixer found.");
						logger.debug("Defining the target line, from which an application can receive the data.");
						line_microphone = line;
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
						AudioFormat flacFormat = new AudioFormat(100, 24, 1, isSigned, false);
						line.open(flacFormat);
						logger.debug("Starting the audio capture");
						line.start();
						FileOutputStream fileOutputStrem = new FileOutputStream("recording.flac");
						FileChannel fileChannel = fileOutputStrem.getChannel(); 
						ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
						WritableByteChannel writableByteChannel = Channels.newChannel((OutputStream)outputStream);
						//fileChannel.trans
						
					}
				} 
				catch (LineUnavailableException e) 
				{
					logger.debug("Caught a LineUnavailableException");
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					logger.debug("Caught a FileNotFoundException");
					e.printStackTrace();
				}
			}
		}
		
		
		
		
		
		
		//logger.debug("1. Defining the input port.");
		// Interface Port
		// https://docs.oracle.com/javase/7/docs/api/javax/sound/sampled/Port.html
		// https://docs.oracle.com/javase/7/docs/api/javax/sound/sampled/Port.Info.html
		// Port.Info(Class<?> lineClass, String name, boolean isSource)
		
		
		
		
		
		
		
	}

}
