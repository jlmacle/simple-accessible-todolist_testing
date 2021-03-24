package jl.project.ScreenReadersTest;

import static org.junit.Assert.*;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jl.project.StringExternalization;

public class SpeechToTextConverter_IBMTest {
	Logger logger = LoggerFactory.getLogger(SpeechToTextConverter_IBMTest.class);
	SpeechToTextConverter_IBM converter = new SpeechToTextConverter_IBM();	
	
	@Test
	public void convertAudioToTextTest() {
		String textRecognized;
		try {
			textRecognized = converter.convertAudioToText("audioFiles/audio-file.flac");
			logger.debug(String.format("Text recognized: *%s*",textRecognized));
			
		} catch (Exception e) {
			logger.debug(String.format("%s %s",StringExternalization.EXCEPTION," in convertAudioToText"));
			logger.debug(String.format("e.getMessage(): %s", e.getMessage()));
			e.printStackTrace();
		}
		
		fail("Not yet implemented");
	}

}
