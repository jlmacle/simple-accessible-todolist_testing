package jl.project;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigurationUtility {
	static Logger logger = LoggerFactory.getLogger(ConfigurationUtility.class);

	public static void main(String[] args) 
	{
		String path_to_folder = System.getProperty("user.dir")+"/src/test/java/jl/project";
		Path stringExternlizationFile_path = FileSystems.getDefault().getPath(path_to_folder,"StringExternalization.java");
		String stringExternlizationFile_swp ="StringExternalization_swap.txt";
		List<String> lines = null; 		
		String webdrivers_opening_tag = "//<webdrivers>";
		String webdrivers_closing_tag = "//</webdrivers>";
		boolean isAWebDriverTag = false;
		boolean betweenTags = false;
		String osName = System.getProperty("os.name");
		logger.debug(String.format("Operating system: %s", osName));
		
		
		try 
		{
			lines = Files.readAllLines(stringExternlizationFile_path);
			logger.debug(String.format("The file has %d lines.",lines.size()));
			
			for(String line:lines)
			{	
				
				if(line.contains(webdrivers_opening_tag))
				{
					betweenTags = true;
					isAWebDriverTag = true;
					
					
				}
				else if(line.contains(webdrivers_closing_tag))
				{
					isAWebDriverTag = false;
					isAWebDriverTag = true;
					String webdrivers="";
					
					if(osName.contains("Windows"))
					{
						webdrivers ="public static final String WEBDRIVER_CHROME_VALUE = \"chromedriver\"; \r\n"
								+ "public static final String WEBDRIVER_FIREFOX_VALUE = \"geckodriver\"; \r\n"
								+ "public static final String WEBDRIVER_EDGE_VALUE = \"msedgedriver\"; \r\n";
					}
					else if (osName.contains("MacOS"))
					{
						webdrivers ="public static final String WEBDRIVER_CHROME_VALUE = \"chromedriver\";  \r\n "
								+ "public static final String WEBDRIVER_FIREFOX_VALUE = \"geckodriver\"; \r\n"
								+ "public static final String WEBDRIVER_EDGE_VALUE = \"msedgedriver\"; \r\n"
								+ "public static final String WEBDRIVER_SAFARI_VALUE = \"\" \r\n; // /usr/bin/safaridriver to be used instead \r\n";
					}
					else if (osName.contains("Linux"))
					{
						webdrivers="public static final String WEBDRIVER_CHROME_VALUE = \"chromedriver\"; \r\n"
								+ "public static final String WEBDRIVER_FIREFOX_VALUE = \"geckodriver\"; \r\n"
								+ "public static final String WEBDRIVER_EDGE_VALUE = \"msedgedriver\"; \r\n";
					}
					else {
						logger.error("Unrecognized operating system: %s",osName);
					}
					//Adding the webdrivers information
					line = webdrivers +"\r\n"+ line; 
					
				}
				logger.debug(line);
				if(!betweenTags ||  isAWebDriverTag)Files.write(Paths.get(path_to_folder,stringExternlizationFile_swp), line.getBytes(),StandardOpenOption.APPEND);
			}
			logger.debug("---> All lines read.");
		} 
		catch (IOException e) 
		{
			logger.debug(StringExternalization.EXCEPTION_IO);
			e.printStackTrace();
		}		
		
	}	

}
