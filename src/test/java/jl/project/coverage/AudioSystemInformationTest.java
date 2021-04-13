package jl.project.coverage;

import static org.testng.Assert.assertTrue;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import jl.project.StringExternalization;
import jl.project.exceptions.UnrecognizedOSException;

public class AudioSystemInformationTest {	
	Logger logger = LoggerFactory.getLogger(AudioSystemInformationTest.class);
	
  @Test
  public void mainTest() throws UnrecognizedOSException {
	
	String infoFile_URI = System.getProperty("user.dir")+"/src/test/java/jl/project/screenreaderstests/logs/"
				+"AudioSystemInfo.txt";	
	Path infoFilePath =  Paths.get(infoFile_URI);
	String mixerInformation =  "Name: Primary Sound Driver";
	String commonFileNameExtension = "Common file name extension for this type: wav";
	boolean isMixerInformationFound = false;
	boolean isCommonFileNameExtensionFound = false;
	
	//Reading the output file
	List<String> lines;
	try {
		lines = Files.readAllLines(infoFilePath);
		for(String line:lines)
		{
			logger.debug(line);
			if (line.contains(mixerInformation)) isMixerInformationFound = true;
			if (line.contains(commonFileNameExtension)) isCommonFileNameExtensionFound = true;
		}
		
		
	} catch (IOException e) {
		logger.debug(StringExternalization.EXCEPTION_IO);
		e.printStackTrace();
	}
	
	assertTrue(isMixerInformationFound && isCommonFileNameExtensionFound) ;
  }
}
