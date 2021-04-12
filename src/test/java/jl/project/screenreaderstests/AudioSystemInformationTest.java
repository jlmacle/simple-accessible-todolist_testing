package jl.project.screenreaderstests;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import jl.project.exceptions.UnrecognizedOSException;

public class AudioSystemInformationTest {	
	Logger logger = LoggerFactory.getLogger(AudioSystemInformationTest.class);
	
  @Test
  public void mainTest() throws UnrecognizedOSException {
	
	ProcessBuilder processBuilder = new ProcessBuilder();
	String osName = System.getProperty("os.name");
	String packageFolder = System.getProperty("user.dir")+"/src/test/java/jl/project/screenreaderstests";
	String scriptFolder = packageFolder +"/scripts/";
	String logFolder =  packageFolder +"/logs/";
	String pathToScript;
	File output_log = new File(logFolder+"output.txt");
	File error_log = new File(logFolder+"error.txt");
	String mixerInformation =  "Mixer information";
	String commonFileNameExtension = "Common file name extension for this type";
	boolean isMixerInformationFound = false;
	boolean isCommonFileNameExtensionFound = false;
	
	if (logger.isDebugEnabled()) logger.debug(String.format("Operating system: %s", osName));
	
	if (osName.contains("Windows"))
	{
		pathToScript = scriptFolder+"AudioSystemInformation_windows.bat";
	}
	else if (osName.contains("Mac"))
	{
		pathToScript = scriptFolder+"AudioSystemInformation_macOS.zsh";
	}
	else if (osName.contains("Linux"))	
	{
		pathToScript = scriptFolder+"AudioSystemInformation_linux.sh";
	}
	else 
	{
		
		throw new UnrecognizedOSException(String.format("Unrecognized operating system: %s", osName));
	}
	
	processBuilder.command(pathToScript);
	processBuilder.redirectError(error_log);
	try {
		Process process = processBuilder.start();		
		InputStream commandOutput = process.getInputStream();
		Files.copy(commandOutput, output_log.toPath(),StandardCopyOption.REPLACE_EXISTING);		
		
		//Reading the output file
		List<String> lines = Files.readAllLines(output_log.toPath());
		for(String line:lines)
		{
			logger.debug(line);
			if (line.contains(mixerInformation)) isMixerInformationFound = true;
			if (line.contains(commonFileNameExtension)) isCommonFileNameExtensionFound = true;
		}
		logger.debug("All lines read.");
		
	} 
	catch (IOException e) 
	{
		e.printStackTrace();
	}
	
	
	// assertTrue(isMixerInformationFound && isCommonFileNameExtensionFound) ;
  }
}
