package jl.project.ScreenReadersTests;

import static org.junit.Assert.*;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jl.project.StringExternalization;

public class SpeechToTextConverterIBMTest {
	Logger logger = LoggerFactory.getLogger(SpeechToTextConverterIBMTest.class);
	SpeechToTextConverterIBM converter = new SpeechToTextConverterIBM();	
	String textRecognized;
	
	@Test
	public void convertAudioToTextTest() {
		
		try {
			textRecognized = converter.convertAudioToText("audioFiles/audio-file.flac");
			logger.debug(String.format("Text recognized: *%s*",textRecognized));
			
		} catch (Exception e) {
			logger.debug(String.format("%s %s",StringExternalization.EXCEPTION," in convertAudioToText"));
			logger.debug(String.format("e.getMessage(): %s", e.getMessage()));
			e.printStackTrace();
		}
		
		assertTrue(textRecognized.contains("several tornadoes touched down as a line of severe thunderstorms swept through Colorado on Sunday "));
	}

}
