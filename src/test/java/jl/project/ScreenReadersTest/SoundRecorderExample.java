// https://docs.oracle.com/javase/tutorial/sound/sampled-overview.html
// https://docs.oracle.com/javase/tutorial/sound/capturing.html

package jl.project.ScreenReadersTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalTime;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.AudioInputStream;
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
			// AudioFormat values from https://www.codejava.net/coding/capture-and-record-sound-into-wav-file-with-java-sound-api
			boolean isSigned = true;
			boolean isBigEndian = true;			
			AudioFormat wavFormat = new AudioFormat(16000, 8, 2,isSigned, isBigEndian);
			DataLine.Info data_line_info = new DataLine.Info(TargetDataLine.class, wavFormat);
			if(AudioSystem.isLineSupported(data_line_info)) { line = (TargetDataLine)AudioSystem.getLine(data_line_info);}
			else {logger.error("Line configured not supported"); System.exit(0);}
						
			line.open(wavFormat);			
			logger.debug("Starting the audio capture");
			line.start();
			logger.debug("Recording started.");
			// I sure got helped.
			// https://www.codejava.net/coding/capture-and-record-sound-into-wav-file-with-java-sound-api
			AudioInputStream ais = new AudioInputStream(line);
			boolean keepRecording = true;
			LocalTime startTime = LocalTime.now();			
			int startMinutes= startTime.getMinute();	
			logger.debug(String.format("Minute the record got started: %d",LocalTime.now().getMinute()));		
			
			
			
					
			while (keepRecording) 
			{ 										
				if(Math.abs(startMinutes-LocalTime.now().getMinute()) >1)  keepRecording=false;
				logger.debug("*");
				
			}
			logger.debug(String.format("Minute the record got stopped, %d",LocalTime.now().getMinute()));
			logger.debug("Recording stopped.");
			ais.close();
			line.stop();
			line.close();
			
		} 
		catch (LineUnavailableException e) 
		{
			logger.debug("Caught a LineUnavailableException");
			e.printStackTrace();
		} catch (IOException e) {
			logger.debug("Caught a IOException ");
			e.printStackTrace();
		}	
	}

}

class RecordingThread extends Thread
{
	AudioInputStream ais;
	AudioFileFormat fileFormat;
	String filePath;
	
	RecordingThread(AudioInputStream ais, AudioFileFormat fileFormat, String filePath)
	{
		this.ais = ais;
		this.fileFormat=fileFormat;
		this.filePath = filePath;
	}
}


