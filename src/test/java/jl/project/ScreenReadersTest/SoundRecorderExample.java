// https://docs.oracle.com/javase/tutorial/sound/sampled-overview.html
// https://docs.oracle.com/javase/tutorial/sound/capturing.html

package jl.project.ScreenReadersTest;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.time.LocalTime;

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
			javax.sound.sampled.Line.Info[] lineInfos = mixer.getSourceLineInfo();
			for (Info info:lineInfos)
			{
				logger.debug(String.format("**** Name of the mixer: *%s*",mixerInfo.getName()));
				logger.debug("Line info: "+info);
				TargetDataLine line;
				try 
				{
					
					//	https://docs.oracle.com/javase/7/docs/api/javax/sound/sampled/AudioFormat.Encoding.html
					//	" Besides PCM, other encodings include mu-law and a-law, 
					//	which are nonlinear mappings of the sound amplitude 
					//	that are often used for recording speech."
					//	According to this source: https://docs.oracle.com/cd/A87860_01/doc/inter.817/a85336/mm_audfm.htm
					//	aiff, and wav have a Linear PCM encoding, and au can have a mu-law encoding.					
					//	https://en.wikipedia.org/wiki/Au_file_format
					//  Originally it was headerless, being simply 8-bit μ-law-encoded data at an 8000 Hz sample rate
					//  All fields are stored in big-endian format, including the sample data.
					//  https://en.wikipedia.org/wiki/Endianness#Current_architectures
					
					// AudioFormat auFormat = new AudioFormat(8000, 8, 1, isSigned, isBigEndian);
					// console output: Line unsupported: interface TargetDataLine supporting format PCM_UNSIGNED 8000.0 Hz, 8 bit, mono, 1 bytes/frame
					
					//AudioFormat auFormat = new AudioFormat(8000, 32, 1, isSigned, isBigEndian);
					// console output
					
					//	https://en.wikipedia.org/wiki/WAV
					// "Though a WAV file can contain compressed audio, 
					//	the most common WAV audio format is uncompressed audio in the linear pulse-code modulation (LPCM) format. 
					//	LPCM is also the standard audio coding format for audio CDs, 
					//	which store two-channel LPCM audio sampled at 44,100 Hz with 16 bits per sample."
					//	"There are some inconsistencies in the WAV format: for example, 8-bit data is unsigned while 16-bit data is signed"
					boolean isSigned = true;
					boolean isBigEndian = true;
					//https://www.codejava.net/coding/capture-and-record-sound-into-wav-file-with-java-sound-api
					AudioFormat wavFormat = new AudioFormat(16000, 8, 2,isSigned, isBigEndian);
					
					line = (TargetDataLine)AudioSystem.getTargetDataLine(wavFormat, mixerInfo);
					logger.debug("Line: "+line);		
					
					//To be adapted for your microphone or an other audio recording line.					
					if(mixerInfo.getName().equals("Port Microphone Array (Realtek(R) Au"))
					//if(false)
					{
						logger.debug("Port Microphone Array mixer found.");
						logger.debug("Defining the target line, from which an application can receive the data.");
						line_microphone = (TargetDataLine)line;
						
						
						
						line_microphone.open(wavFormat);
						//https://docs.oracle.com/javase/tutorial/sound/capturing.html
						logger.debug("Starting the audio capture");
						line_microphone.start();
						logger.debug("Recording started.");
						int numBytesRead;
						byte[] data = new byte[line_microphone.getBufferSize()/5];
						ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
									
						// https://docs.oracle.com/javase/7/docs/api/javax/sound/sampled/AudioFormat.Encoding.html
						// https://en.wikipedia.org/wiki/Bit_rate
						// https://www.wildlifeacoustics.com/resources/faqs/what-is-an-audio-channel
						// "A channel is a representation of sound coming from or going to a single point. 
						// A single microphone can produce one channel of audio, and a single speaker can accept one channel of audio, for example."
						FileOutputStream fileOutputStream = new FileOutputStream("recording.wav");
						outputStream.writeTo(fileOutputStream);
						
						//getLine(Line.Info) - Static method in class javax.sound.sampled.AudioSystem
						//Obtains a line that matches the description in the specified Line.Info object.
						
						boolean keepRecording = true;
						LocalTime startTime = LocalTime.now();
						int startMinutes= startTime.getMinute();
						
						while (!keepRecording) { 
							numBytesRead = line_microphone.read(data, 0,  data.length); 
							outputStream.write(data, 0, numBytesRead);
							LocalTime nowTime = LocalTime.now();
							int nowMinutes = nowTime.getMinute();
							if(Math.abs(startMinutes-nowMinutes) == 1) logger.debug("Math.abs(startMinutes-nowMinutes) = 1 ");
							if(Math.abs(startMinutes-nowMinutes) > 1)keepRecording=false;
							
							
						}
  
						/*
						 * //Opening of a FileChannel to write the output in a file
						 * 
						 * FileChannel fileChannel = fileOutputStream.getChannel(); //need to transfer
						 * the data from the line to a readableByteChannel ReadableByteChannel
						 * readableByteChannel = Channels.newChannel(inputStream);
						 * fileChannel.transferFrom(readableByteChannel, 0, 1024);
						 */
						 
						
					}
				} 
				catch (LineUnavailableException e) 
				{
					logger.debug("Caught a LineUnavailableException");
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					logger.debug("Caught a FileNotFoundException");
					e.printStackTrace();
				} catch (IOException e) {
					logger.debug("Caught a IOException");
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
