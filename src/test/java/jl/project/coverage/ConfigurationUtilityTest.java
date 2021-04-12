package jl.project.coverage;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.google.common.io.Files;

import jl.project.ConfigurationUtility;
import jl.project.StringExternalization;

public class ConfigurationUtilityTest {
	Logger logger = LoggerFactory.getLogger(ConfigurationUtilityTest.class);
	String stringTestFileFolder = "/src/test/java/jl/project/coverage/files/";
	String fileWindows ="replaceDataTest_windows.txt";
	String tag = "testTag";
	String testValueForWindows = "valueWindows";
	String testValueForMacOS = "valueMacOS";
	String testValueForLinux = "valueLinux";
	
	
  @Test
  public void replaceTagByDataTest() {
	//File creation with an ending and closing tag and no other data.
	String initialText= "//<"+tag+">"+System.lineSeparator()+"//</"+tag+">";
	byte[] initialText_bytes = initialText.getBytes();
	File testFile = new File(System.getProperty("user.dir")+stringTestFileFolder+fileWindows);
	try {
		Files.write(initialText_bytes, testFile);
	} catch (IOException e) {
		logger.debug(StringExternalization.EXCEPTION_IO);
		e.printStackTrace();
	}
	
	ConfigurationUtility.replaceTagByData(tag, stringTestFileFolder, fileWindows ,testValueForWindows ,testValueForMacOS , testValueForLinux);
	boolean isTestTextFound = false;
    //Testing that the value can be found
	try {
		
		List<String> lines = Files.readLines(testFile, Charset.defaultCharset());
		for(String line:lines)
		{
			logger.debug(line);
			if (line.contains(testValueForWindows)) 
			{
				logger.debug("Found text.");
				isTestTextFound = line.contains(testValueForWindows);
			}
		}
		
		assertTrue(isTestTextFound);
		
	} catch (IOException e) {
		logger.debug(StringExternalization.EXCEPTION_IO);
		e.printStackTrace();
	}
	
  }
}
