// https://www.ibm.com/cloud/watson-speech-to-text
// https://cloud.ibm.com/services/speech-to-text/crn%3Av1%3Abluemix%3Apublic%3Aspeech-to-text%3Aeu-gb%3Aa%2F2e4a017664644b6c865cf4e532dd9056%3A08dcb5ed-d57f-410f-98ef-11ae645b5a04%3A%3A?paneId=manage&new=true
// https://cloud.ibm.com/docs/speech-to-text?topic=speech-to-text-gettingStarted#getting-started-tutorial

package jl.project.screenreaderstests;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jl.project.StringExternalization;
import jl.project.exceptions.UnrecognizedOSException;

public class SpeechToTextConverterIBM 
{
	Logger logger = LoggerFactory.getLogger(SpeechToTextConverterIBM.class);
	Robot robot;
	ProcessBuilder processBuilder = new ProcessBuilder();	
	String apikeyParam="\"apikey:"+System.getenv("IBM_SpeechToText")+"\"";	
	
	
	
	public String convertAudioToText(String pathToAudioFile) throws UnrecognizedOSException
	{
		System.setProperty("pathToAudioFile", pathToAudioFile);
		 
		//Needed to fix an new line issue in the environment variable value in Windows.
		apikeyParam = apikeyParam.replace("\n", "");
		
		String informationReturned  = null;			
				
		String osName = System.getProperty("os.name");
		Process process = null;
		
		try {
			robot = new Robot();
		} catch (AWTException e) {
			logger.debug(StringExternalization.EXCEPTION_AWT);
		}
		
		if (logger.isDebugEnabled())logger.debug(String.format("OS: %s",osName));		
		if(osName.contains("Windows"))
		{
			processBuilder.command("src/test/java/jl/project/ScreenReadersTests/scripts/script_IBM_STT_Windows.bat");
		}
		else if (osName.contains("Mac"))
		{
			processBuilder.command("src/test/java/jl/project/ScreenReadersTests/scripts/script_IBM_STT_macOS.zsh");
		}
		else if (osName.contains("Linux"))
		{
			processBuilder.command("src/test/java/jl/project/ScreenReadersTests/scripts/script_IBM_STT_Ubuntu.sh");

		}
		else {throw new UnrecognizedOSException(String.format("Unrecognized OS : %s",osName));}
		
		try 
		{
			process = processBuilder.start();
		
			logger.debug("Request sent.");
			
			logger.debug("Waiting for results.");
			robot.delay(15000);
			
			// readAllBytes() since Java 1.9
			// https://docs.oracle.com/javase/9/docs/api/java/io/InputStream.html#readAllBytes--
			informationReturned = new String(process.getInputStream().readAllBytes());
		} 
		catch (IOException e) 
		{
			logger.debug(StringExternalization.EXCEPTION_IO);
		}
		// supportsNormalTermination() since Java 1.9
		// https://docs.oracle.com/javase/9/docs/api/java/lang/Process.html#supportsNormalTermination--
		if (process != null)
		{
			if ( process.supportsNormalTermination()) process.destroy(); else process.destroyForcibly();
			if (logger.isDebugEnabled()) logger.debug(String.format("Process is alive : %b", process.isAlive()));		
		}
						
		
		return informationReturned;
	}
}
