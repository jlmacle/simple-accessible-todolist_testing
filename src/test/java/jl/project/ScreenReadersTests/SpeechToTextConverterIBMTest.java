package jl.project.ScreenReadersTests;

import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
import java.awt.Robot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import jl.project.StringExternalization;



public class SpeechToTextConverterIBMTest {
	Logger logger = LoggerFactory.getLogger(SpeechToTextConverterIBMTest.class);
	Robot robot;
	SpeechToTextConverterIBM converter = new SpeechToTextConverterIBM();	
	String informationReturned;
	
	@BeforeClass
	public void setup()
	{
		//Used to avoid the deprecated Thread.stop();
		try {
			robot = new Robot();
		} catch (AWTException e1) {
			logger.debug(StringExternalization.EXCEPTION_AWT);
			e1.printStackTrace();
		}
	}
	
	
	@Test
	public void convertAudioToTextTest() {
		
		try {
			informationReturned = converter.convertAudioToText("audioFiles/audio-file.flac");
			logger.debug(String.format("Information returned: %s",informationReturned));
			
		} catch (Exception e) {
			logger.debug(String.format("%s %s",StringExternalization.EXCEPTION," in convertAudioToText"));
			logger.debug(String.format("e.getMessage(): %s", e.getMessage()));
			e.printStackTrace();
		}
		
		assertTrue(informationReturned.contains("several tornadoes touched down as a line of severe thunderstorms swept through Colorado on Sunday "));
	}

}
