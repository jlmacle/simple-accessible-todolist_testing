package jl.project.pa11yTest;

import static org.junit.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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
	String script_folder;
	String backend_script = "";
	String angular_script = "";
	String pa11y_script = "";
	String url_log = "./log_URL.txt";
	String pa11y_log = "./log_Pa11y.txt";
	
	boolean UncategorizedFound = false;
	boolean pa11yTestPassed = false; 
		
	
	@BeforeClass
	public void setup()
	{
		logger.debug(String.format("OS: %s",osName));
		logger.debug(String.format("User dir: %s", System.getProperty("user.dir")));
		
		
		if (this.osName.contains("Windows"))
		{			
			script_folder = "src/test/java/jl/project/pa11yTest/";
			backend_script = script_folder+"run_Backend_server-Windows.bat";
			angular_script = script_folder+"run_Angular_server-Windows.bat";
			pa11y_script = script_folder+"run_Pa11y_test-Windows.bat";
			
		}
		else if (this.osName.contains("Mac"))
		{
			//Todo
		}		
		
		else if (this.osName.contains("Linux"))
		{
			script_folder = "src/test/java/jl/project/pa11yTest/";
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
			print_stream(process_backend.getInputStream(), "Backend", "");
			logger.debug("Waiting for the back-end server to start.");
			Thread.sleep(35000);			
						
			logger.debug("Testing that the 'Uncategorized' category can be found."); 	
			URL page_url = new URL("http://localhost:8080/categories");	 
			//https://www.baeldung.com/java-download-file
			ReadableByteChannel readableByteChannel = Channels.newChannel(page_url.openStream());
			FileOutputStream url_file_output = new FileOutputStream(url_log);
			FileChannel fileChannel = url_file_output.getChannel();
			fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
			List<String> lines = FileUtils.readLines(new File(url_log), "UTF-8");
			for(String line:lines)
			{
				logger.debug(line);
				if(line.contains("Uncategorized")) {UncategorizedFound=true;break;}				
			}
			readableByteChannel.close();
			url_file_output.close();
			fileChannel.close();
			
			//if(!UncategorizedFound)fail("Uncategorized was not found. There may be an issue with the back-end server.");
									
			logger.debug("Starting Angular server");	
			processBuilder.command(angular_script);
			process_angular = processBuilder.start();
						
			try {
				// Note: starting the angular server may take a while
				logger.debug("Waiting for the Angular server to start.");
				Thread.sleep(35000);	
				
				logger.debug("Building and starting the pa11y command."); 
				processBuilder.command(pa11y_script);
				process_Pa11y = processBuilder.start();
				logger.debug("Waiting for the pa11y test to be finished"); 
				Thread.sleep(20000);
				
				//if no output, replace getInputStream() by getErrorStream()
				readableByteChannel = Channels.newChannel(process_Pa11y.getInputStream());
				FileOutputStream pa11y_output = new FileOutputStream(pa11y_log);
				fileChannel = pa11y_output.getChannel();
				fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
				lines = FileUtils.readLines(new File(pa11y_log), "UTF-8");
				for(String line:lines)
				{
					logger.debug(line);
					if(line.contains("No issues found!")) {pa11yTestPassed=true;break;}				
				}
				readableByteChannel.close();
				pa11y_output.close();
				fileChannel.close();
			} 
			catch (InterruptedException e1) 
			{
				logger.debug("Caught an InterruptedException while pausing the execution.");
				e1.printStackTrace();
			}			
			
		} 
		catch (IOException e) 
		{
			logger.debug("IOException caught.");
			logger.debug(e.getMessage());
			e.printStackTrace();
		}	
		
		catch (InterruptedException e1) 
		{
		  logger.debug("Caught an InterruptedException while pausing the execution.");
		  e1.printStackTrace(); 
		}
		 
	}
	
	
	@Test
	public void pa11yTest()
	{		
		assertTrue(pa11yTestPassed);		
	}
	
	
	/**
	 * A method that takes an input stream (for example, an output stream or an error stream) and logs its content.
	 * The method can also test if a String is in the stream.
	 * @param stream: the InputStream
	 * @param streamType: the stream type: (regular)output or error
	 * @param stringToFind: potentially a String to find in the output
	 * @return returns true if stringToFind
	 */
	private boolean print_stream(InputStream stream, String streamType, String stringToFind)	
	{
		boolean stringFound =  false;
		try {
			logger.debug(String.format("%s stream available %d",streamType,stream.available()));
			File stream_File = new File("log_"+streamType+".txt");		
			logger.debug("Before copyInputStreamToFile.");
			FileUtils.copyToFile(stream, stream_File);//The source stream is not closed with copyToFile.
			
			List<String> log_lines = FileUtils.readLines(stream_File,"UTF-8");
			//https://en.wikipedia.org/wiki/UTF-8 
			for(String line:log_lines) 
			{
			  logger.debug(line); if(line.contains(stringToFind)) {stringFound = true; }
			  
			} 
			logger.debug("All lines read."); 
			 
		} 
		catch (IOException e) 
		{
			logger.debug("");
			e.printStackTrace();
		}		
		
		return stringFound;
	}
	

	@AfterClass
	void release()
	{
		process_angular.destroy();
		process_backend.destroy();
		process_Pa11y.destroy();
	}

}
