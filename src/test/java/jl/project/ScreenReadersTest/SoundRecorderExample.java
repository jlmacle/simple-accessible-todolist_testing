// https://docs.oracle.com/javase/tutorial/sound/sampled-overview.html
// https://docs.oracle.com/javase/tutorial/sound/capturing.html

package jl.project.ScreenReadersTest;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

/**
 * @author 
 * Unstuck thanks to code written by Written by  Nam Ha Minh
 * https://www.codejava.net/coding/capture-and-record-sound-into-wav-file-with-java-sound-api
 * 
 * 
 */
public class SoundRecorderExample 
{
	static Logger logger = LoggerFactory.getLogger(SoundRecorderExample.class);
	
	public static void main(String[] args) {		
		TargetDataLine line = null;				
		
		try 
		{
			//	https://en.wikipedia.org/wiki/WAV
			// "Though a WAV file can contain compressed audio, 
			//	the most common WAV audio format is uncompressed audio in the linear pulse-code modulation (LPCM) format. 
			//	LPCM is also the standard audio coding format for audio CDs, 
			//	which store two-channel LPCM audio sampled at 44,100 Hz with 16 bits per sample."
			//	"There are some inconsistencies in the WAV format: for example, 8-bit data is unsigned while 16-bit data is signed"
			boolean isSigned = true;
			boolean isBigEndian = true;
			// AudioFormat values from https://www.codejava.net/coding/capture-and-record-sound-into-wav-file-with-java-sound-api
			AudioFormat wavFormat = new AudioFormat(16000, 8, 2,isSigned, isBigEndian);
			DataLine.Info data_line_info = new DataLine.Info(TargetDataLine.class, wavFormat);
			if(AudioSystem.isLineSupported(data_line_info)) { line = (TargetDataLine)AudioSystem.getLine(data_line_info);}
			else {logger.error("Line configured not supported"); System.exit(0);}
			
			//  <--->  <To be adapted for your microphone or an other audio recording line>.					
				
			line.open(wavFormat);			
			logger.debug("Starting the audio capture");
			line.start();
			logger.debug("Recording started.");
			int numBytesRead;
			byte[] data = new byte[line.getBufferSize()/5];
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();			
			FileOutputStream fileOutputStream = new FileOutputStream("recording.wav");
			outputStream.writeTo(fileOutputStream);
						
			boolean keepRecording = true;
			LocalTime startTime = LocalTime.now();
			int startMinutes= startTime.getMinute();
			Thread.sleep(2000);
			
			while (keepRecording) { 
				numBytesRead = line.read(data, 0,  data.length); 
				outputStream.write(data, 0, numBytesRead);
				LocalTime nowTime = LocalTime.now();
				int nowMinutes = nowTime.getMinute();							
				if(Math.abs(startMinutes-nowMinutes) > 1)  keepRecording=false;
			}
			logger.debug("Recording stopped.");
			line.stop();
			line.close();
			outputStream.close();
			fileOutputStream.close();
  
					
		} 
		catch (LineUnavailableException e) 
		{
			logger.debug("Caught a LineUnavailableException");
			e.printStackTrace();
		}		 
		catch (FileNotFoundException e) 
		{
			logger.debug("Caught a FileNotFoundException");
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			logger.debug("Caught a IOException");
			e.printStackTrace();
		} 
		catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
	}

}
