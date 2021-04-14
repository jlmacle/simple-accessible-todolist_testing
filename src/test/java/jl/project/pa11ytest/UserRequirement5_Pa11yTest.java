package jl.project.pa11ytest;

import static org.junit.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.io.FileNotFoundException;
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

import jl.project.StringExternalization;

/**
 * @author 
 * 
 */
public class UserRequirement5_Pa11yTest {
	
	Logger logger = Logger.getLogger(UserRequirement5_Pa11yTest.class);
	String osName = System.getProperty("os.name");		
	Robot robot;
		
	ProcessBuilder processBuilder = new ProcessBuilder();	
	Process process_backend = null;
	Process process_angular = null;
	Process process_Pa11y = null;
	Process process_stopServer = null;
	String root_folder = "src/test/java/jl/project/pa11ytest";
	String script_folder = root_folder+"/scripts/";
	String log_folder = root_folder+"/logs/";
	String backend_script = "";
	String angular_script = "";
	String pa11y_script = "";		
	String stop_servers_script = "";
	File backend_server_error_log = new File(log_folder+"log_SpringBoot_error.txt");
	File backend_server_input_log = new File(log_folder+"log_SpringBoot_input.txt");
	File angular_server_error_log = new File(log_folder+"log_Angular_error.txt");
	File angular_server_input_log = new File(log_folder+"log_Angular_input.txt");
	String url_log = log_folder+"log_URL.txt";
	String pa11y_log = log_folder+"log_Pa11y.txt";
	
	boolean isUncategorizedFound = false;
	boolean isPa11yTestPassed = false; 
		
	
	@BeforeClass
	public void setup()
	{
		logger.debug(String.format("OS: %s",osName));
		logger.debug(String.format("User dir: %s", System.getProperty("user.dir")));
		
		//Used to avoid the deprecated Thread.stop();
		
		  try { robot = new Robot(); } catch (AWTException e1) {
		  logger.debug(StringExternalization.EXCEPTION_AWT); e1.printStackTrace(); }
		 
		
		
		if (this.osName.contains("Windows"))		{			
			
			backend_script = script_folder+"run_Backend_server-windows.bat";
			angular_script = script_folder+"run_Angular_server-windows.bat";
			pa11y_script = script_folder+"run_Pa11y_test-windows.bat";
			stop_servers_script = script_folder+"stop_potentially_existing_Angular_Spring_server_processes-windows.bat";
			
		}
		else if (this.osName.contains("Mac"))
		{
			backend_script = script_folder+"run_Backend_server-macOS.zsh";
			angular_script = script_folder+"run_Angular_server-macOS.zsh";
			pa11y_script = script_folder+"run_Pa11y_test-macOS.zsh";
			stop_servers_script = script_folder + "stop_potentially_existing_Angular_Spring_server_processes-macOS.zsh";
		}		
		
		else if (this.osName.contains("Linux"))		{
			
			backend_script = script_folder+"run_Backend_server-linux.sh";
			angular_script = script_folder+"run_Angular_server-linux.sh";
			pa11y_script = script_folder+"run_Pa11y_test-linux.sh";
			stop_servers_script  = script_folder + "stop_potentially_existing_Angular_Spring_server_processes-linux.sh";
		}	
		else {fail("OS name not recognized");}
		
		try 
		{		
			//https://docs.oracle.com/javase/7/docs/api/java/lang/Runtime.html
			logger.debug("Starting back-end server");			
			processBuilder.command(backend_script);		
			processBuilder.redirectError(backend_server_error_log);
			processBuilder.redirectInput(backend_server_input_log);
			process_backend = processBuilder.start();				
			Channels.newChannel(process_backend.getInputStream());//Added to get the code to run on Windows.
			logger.debug("Waiting for the back-end server to start.");
			robot.delay(25000);			
						
			logger.debug("Testing that the 'Uncategorized' category can be found."); 	
			URL page_url = new URL("http://localhost:8080/categories");	 
			String categoriesPageURLOutput = new String(page_url.openStream().readAllBytes());
			isUncategorizedFound = categoriesPageURLOutput.contains("Uncategorized");			

			if(!isUncategorizedFound)fail("Uncategorized was not found. There may be an issue with the back-end server.");
									
			logger.debug("Starting Angular server");	
			processBuilder.command(angular_script);		
			processBuilder.redirectError(angular_server_error_log);
			processBuilder.redirectInput(angular_server_input_log);
			process_angular = processBuilder.start();
			logger.debug("Waiting for the Angular server to start.");
			robot.delay(35000);	
			 
			
			logger.debug("Building and starting the pa11y command."); 
			processBuilder.command(pa11y_script);
			process_Pa11y = processBuilder.start();
			logger.debug("Waiting for the pa11y test to be finished"); 
			robot.delay(46000);//not too much time for a slow computer.
			
			//if no output, getInputStream() replaced by getErrorStream()
			String pa11y_output = new String(process_Pa11y.getInputStream().readAllBytes());
			logger.debug(pa11y_output);
			isPa11yTestPassed = pa11y_output.contains("No issues found!");
			
		} 
		catch (IOException e) 
		{
			logger.debug("IOException caught.");
			logger.debug(e.getMessage());
			e.printStackTrace();
		}	
		
	}	
	
		
	
	@Test
	public void pa11yTest()
	{		
		assertTrue(isPa11yTestPassed);		
	}	
	
	@AfterClass
	void release()
	{			
		processBuilder.command(stop_servers_script);
		try {
			process_stopServer = processBuilder.start();
			robot.delay(2000);
		} catch (IOException e) {
			logger.debug(StringExternalization.EXCEPTION_IO);
			e.printStackTrace();
		}
		
		if (process_Pa11y.supportsNormalTermination()) process_Pa11y.destroy(); else process_Pa11y.destroyForcibly();
		if (process_stopServer.supportsNormalTermination()) process_stopServer.destroy(); else process_stopServer.destroyForcibly();
		
		logger.debug(String.format("process_angular \t is alive : %b", process_angular.isAlive()));
		logger.debug(String.format("process_backend \t is alive : %b", process_backend.isAlive()));
		logger.debug(String.format("process_Pa11y \t is alive : %b", process_Pa11y.isAlive()));
		logger.debug(String.format("process_stopServer  is alive : %b", process_stopServer.isAlive()));
	}

}