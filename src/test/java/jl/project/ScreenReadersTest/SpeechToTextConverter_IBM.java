// https://www.ibm.com/cloud/watson-speech-to-text
// https://cloud.ibm.com/services/speech-to-text/crn%3Av1%3Abluemix%3Apublic%3Aspeech-to-text%3Aeu-gb%3Aa%2F2e4a017664644b6c865cf4e532dd9056%3A08dcb5ed-d57f-410f-98ef-11ae645b5a04%3A%3A?paneId=manage&new=true
// https://cloud.ibm.com/docs/speech-to-text?topic=speech-to-text-gettingStarted#getting-started-tutorial

package jl.project.ScreenReadersTest;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpeechToTextConverter_IBM 
{
	Logger logger = LoggerFactory.getLogger(SpeechToTextConverter_IBM.class);
	ProcessBuilder processBuilder = new ProcessBuilder();	
	String apikey="\"apikey:"+System.getenv("IBM_SpeechToText")+"\"";	
	
	
	public String convertAudioToText(String pathToAudioFile) throws Exception
	{
		//Needed to fix an new line issue in the environment variable value in Windows.
		apikey = apikey.replace("\n", "");
		
		String textRecognized  = null;	
		ArrayList<String> command;
		String audioFileParameter = "@"+System.getProperty("user.dir")+"/src/test/java/jl/project/ScreenReadersTest/"+pathToAudioFile;		
		//Works on Windows, not on macOS
		//ArrayList<String> command_common_arguments = new ArrayList<String>(List.of("curl","-X","POST","-u", this.apikey ,"--header","\"Content-Type:audio/flac\"","--data-binary",audioFileParameter,"\"https://api.eu-gb.speech-to-text.watson.cloud.ibm.com/instances/08dcb5ed-d57f-410f-98ef-11ae645b5a04/v1/recognize\""));
		//Works on macOS
		ArrayList<String> command_common_arguments = new ArrayList<String>(List.of("curl","-X","POST","-u", this.apikey ,"--header","\"Content-Type:audio/flac\"","--data-binary",audioFileParameter,"https://api.eu-gb.speech-to-text.watson.cloud.ibm.com/instances/08dcb5ed-d57f-410f-98ef-11ae645b5a04/v1/recognize"));
		String os_name = System.getProperty("os.name");
		ProcessBuilder processBuilder = new ProcessBuilder();
		Process process = null;
		
		logger.debug(String.format("OS: %s",os_name));
		if(os_name.contains("Windows"))
		{
			//command = new ArrayList<String>(List.of("cmd.exe","/c"));		
			command = new ArrayList<String>();	
		}
		else if (os_name.contains("Mac"))
		{
			//command = new ArrayList<String>(List.of("#!/bin/zsh","-c"));
			command = new ArrayList<String>();	
		}
		else if (os_name.contains("Linux"))
		{
			command = new ArrayList<String>(List.of("#!/bin/bash","-c"));		
		}
		else {throw new Exception(String.format("Unrecognized OS : %s",os_name));}
		
		logger.debug(String.format("audioFileParameter: %s",audioFileParameter));
		logger.debug(String.format("Command root: %s", command));
		logger.debug(String.format("Adding command_common_arguments: %s", command_common_arguments));		
		command.addAll(command_common_arguments);		
		logger.debug(String.format("Using command: %s", command));
		//command = new ArrayList<String>(List.of("cmd.exe","/c","pa11y","http://localhost:4200"));
		processBuilder.command(command);
		//processBuilder.redirectOutput(new File("curl_output.txt"));
		//processBuilder.redirectInput(new File("curl_input.txt"));
		//processBuilder.redirectError(new File("curl_output_error.txt"));
		process = processBuilder.start();
		logger.debug("Process started");
		
		logger.debug("Waiting for results.");
		//process.waitFor();
		Thread.sleep(5000);
		//textRecognized = new String(process.getInputStream().readAllBytes());
		//logger.debug(String.format("Error stream %s",new String(process.getErrorStream().readAllBytes())));
		
		logger.debug(String.format("Input stream %s",new String(process.getInputStream().readAllBytes())));
		
			
		
		return textRecognized;
	}
}
