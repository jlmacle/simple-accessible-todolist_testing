// https://www.ibm.com/cloud/watson-speech-to-text
// https://cloud.ibm.com/services/speech-to-text/crn%3Av1%3Abluemix%3Apublic%3Aspeech-to-text%3Aeu-gb%3Aa%2F2e4a017664644b6c865cf4e532dd9056%3A08dcb5ed-d57f-410f-98ef-11ae645b5a04%3A%3A?paneId=manage&new=true
// https://cloud.ibm.com/docs/speech-to-text?topic=speech-to-text-gettingStarted#getting-started-tutorial

package jl.project.ScreenReadersTest;

import java.io.File;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpeechToTextConverter_IBM 
{
	Logger logger = LoggerFactory.getLogger(SpeechToTextConverter_IBM.class);
	String apikey="apikey:"+System.getenv("IBM_SpeechToText");	
	ProcessBuilder processBuilder = new ProcessBuilder();	
	
	public String convertAudioToText(String pathToAudioFile) throws Exception
	{
		String textRecognized  = null;	
		ArrayList<String> command;
		String audioFileParameter = "@"+pathToAudioFile;
		ArrayList<String> command_common_arguments = new ArrayList<String>(List.of("curl","-X","POST","-u", this.apikey ,"--header","Content-Type:audio/flac","--data-binary",audioFileParameter,"https://api.eu-gb.speech-to-text.watson.cloud.ibm.com/instances/08dcb5ed-d57f-410f-98ef-11ae645b5a04/v1/recognize"));
		String os_name = System.getProperty("os.name");
		ProcessBuilder processBuilder = new ProcessBuilder();
		Process process = null;
		
		logger.debug(String.format("OS: %s",os_name));
		if(os_name.contains("Windows"))
		{
			command = new ArrayList<String>(List.of("cmd.exe","\\c"));			
		}
		else if (os_name.contains("Mac"))
		{
			command = new ArrayList<String>(List.of("#!/bin/zsh","-c")); 			
		}
		else if (os_name.contains("Linux"))
		{
			command = new ArrayList<String>(List.of("#!/bin/bash","-c"));		
		}
		else {throw new Exception(String.format("Unrecognized OS : %s",os_name));}
		logger.debug(String.format("Command root: %s", command));
		logger.debug(String.format("Adding command_common_arguments: %s", command_common_arguments));		
		command.addAll(command_common_arguments);		
		logger.debug(String.format("Using command: %s", command));
		//command = new ArrayList<String>(List.of("cmd.exe","/c","pa11y","http://localhost:4200"));
		//Need to understand why the command fails.
		processBuilder.command(command);
		processBuilder.redirectOutput(new File("curl_output.txt"));
		processBuilder.redirectInput(new File("curl_input.txt"));
		processBuilder.redirectError(new File("curl_output_error.txt"));
		process = processBuilder.start();
		logger.debug("Process started");
		
		logger.debug("Waiting for results.");
			
		textRecognized = new String(process.getInputStream().readAllBytes());
		process.waitFor();	
		
		return textRecognized;
	}
}
