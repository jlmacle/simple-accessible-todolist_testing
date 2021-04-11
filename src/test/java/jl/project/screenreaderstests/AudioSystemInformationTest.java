package jl.project.screenreaderstests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

public class AudioSystemInformationTest {	
	
  @Test
  public void mainTest() {
	Logger logger = LoggerFactory.getLogger(AudioSystemInformationTest.class);
	ProcessBuilder processBuilder = new ProcessBuilder();
	String osName = System.getProperty("os.name");
	String scriptFolder = "/scripts";
	String pathToScript;
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
	
	
	  
    throw new RuntimeException("Test not implemented");
  }
}
