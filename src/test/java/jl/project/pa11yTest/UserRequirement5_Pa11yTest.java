package jl.project.pa11yTest;

import static org.junit.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channel;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

/**
 * @author 
 *
 */
public class UserRequirement5_Pa11yTest {
	
	Logger logger = Logger.getLogger(UserRequirement5_Pa11yTest.class);
	String osName = System.getProperty("os.name");		
		
	ProcessBuilder processBuilder = new ProcessBuilder();
	Process process_backend = null;
	Process process_angular = null;
	Process process_Pa11y = null;
	String script_folder = "src/test/java/jl/project/pa11yTest/scripts/";
	String backend_script = "";
	String angular_script = "";
	String pa11y_script = ".";
	String backend_log = "/log_Backend.txt";
	String angular_server_log = "./log_Angular.txt";
	String url_log = "./log_URL.txt";
	String pa11y_log = "./log_Pa11y.txt";
	
	boolean isUncategorizedFound = false;
	boolean isPa11yTestPassed = false; 
		
	
	@BeforeClass
	public void setup()
	{
		logger.debug(String.format("OS: %s",osName));
		logger.debug(String.format("User dir: %s", System.getProperty("user.dir")));
		
		
		if (this.osName.contains("Windows"))		{			
			
			backend_script = script_folder+"run_Backend_server-Windows.bat";
			angular_script = script_folder+"run_Angular_server-Windows.bat";
			pa11y_script = script_folder+"run_Pa11y_test-Windows.bat";
			
		}
		else if (this.osName.contains("Mac"))
		{
			backend_script = script_folder+"run_Backend_server-mac.zsh";
			angular_script = script_folder+"run_Angular_server-mac.zsh";
			pa11y_script = script_folder+"run_Pa11y_test-mac.zsh";
		}		
		
		else if (this.osName.contains("Linux"))		{
			
			backend_script = script_folder+"run_Backend_server-Linux.sh";
			angular_script = script_folder+"run_Angular_server-Linux.sh";
			pa11y_script = script_folder+"run_Pa11y_test-Linux.sh";
		}	
		else {fail("OS name not recognized");}
		
		try 
		{		
			//https://docs.oracle.com/javase/7/docs/api/java/lang/Runtime.html
			logger.debug("Starting back-end server");			
			processBuilder.command(backend_script);		
			process_backend = processBuilder.start();		
			// Test blocked by the use of printstream
			//this.print_stream(process_backend.getErrorStream(),backend_log, "");
			logger.debug("Waiting for the back-end server to start.");
			Thread.sleep(35000);			
						
			logger.debug("Testing that the 'Uncategorized' category can be found."); 	
			URL page_url = new URL("http://localhost:8080/categories");	 			
			isUncategorizedFound = this.print_stream(page_url.openStream(), url_log, "Uncategorized");
			// Issue: case where the back-end starts with no Uncategorized category
			// If this happens, finding and suppressing the process still running on port 8080
			// should solve the issue   lsof -nP -iTCP -sTCP:LISTEN | grep 8080
			

			if(!isUncategorizedFound)fail("Uncategorized was not found. There may be an issue with the back-end server.");
									
			logger.debug("Starting Angular server");	
			processBuilder.command(angular_script);
			process_angular = processBuilder.start();
			// Test blocked by the use of printstream
			//this.print_stream(process_angular.getErrorStream(),angular_server_log,"");
			logger.debug("Waiting for the Angular server to start.");
			Thread.sleep(35000);	
			
			logger.debug("Building and starting the pa11y command."); 
			processBuilder.command(pa11y_script);
			process_Pa11y = processBuilder.start();
			logger.debug("Waiting for the pa11y test to be finished"); 
			Thread.sleep(20000);
			
			//if no output, getInputStream() replaced by getErrorStream()
			isPa11yTestPassed = this.print_stream(process_Pa11y.getInputStream(), pa11y_log, "No issues found!");
				
					
			
		} 
		catch (IOException e) 
		{
			logger.debug("IOException caught.");
			logger.debug(e.getMessage());
			e.printStackTrace();
		}	
		
		catch (InterruptedException e) 
		{
		  logger.debug("Caught an InterruptedException while pausing the execution.");
		  logger.debug(e.getMessage());
		  e.printStackTrace(); 
		}
		 
	}
	
	/**
	 * 
	 * @param input: an InputStream
	 * @param log_path: the path to log the output of the stream (from getInputStrem() or getErrorStream())
	 * @param stringToFind: a string to find in the output
	 * @return true if the string has been found in the output, false otherwise.
	 */
	private boolean print_stream(InputStream inputStream, String log_path, String stringToFind)
	{
		boolean isStringFound = false;
		ReadableByteChannel readableByteChannel = Channels.newChannel(inputStream);
		FileOutputStream fileOutputStream;
		try {
			fileOutputStream = new FileOutputStream(log_path);
			FileChannel fileChannel = fileOutputStream.getChannel();
			fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
			List<String> lines = FileUtils.readLines(new File(log_path), "UTF-8");
			
			for(String line:lines)
			{
				logger.debug(line);
				if(line.contains(stringToFind)) {isStringFound=true;break;}				
			}
			readableByteChannel.close();
			fileChannel.close();
			fileOutputStream.close();
			
		} catch (FileNotFoundException e) {
			logger.debug("Caught a FileNotFoundException in print_stream");
			logger.debug(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.debug("Caught an IOExceptionin print_stream");
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		
		return isStringFound;
		
	}
	
	
	@Test
	public void pa11yTest()
	{		
		assertTrue(isPa11yTestPassed);		
	}	
	

	@AfterClass
	void release()
	{
		process_angular.destroy();
		process_backend.destroy();
		process_Pa11y.destroy();
	}

}
