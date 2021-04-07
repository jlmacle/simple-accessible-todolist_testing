package jl.project;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigurationUtility {
	static Logger logger = LoggerFactory.getLogger(ConfigurationUtility.class);
	
	public static void main(String[] args) 
	{
		configure_StringExternalization();
		
		
	}

	public static void configure_StringExternalization() 
	{
		String path_to_baseFolder = System.getProperty("user.dir")+"/src/test/java/jl/project/";
		String path_to_tmp_folder = path_to_baseFolder+"/tmp";
		Path stringExternlizationFile_path = FileSystems.getDefault().getPath(path_to_baseFolder,"StringExternalization.java");
		Path stringExternlizationFile_swp_path = FileSystems.getDefault().getPath(path_to_tmp_folder, "StringExternalization_swap.txt");
		String tab = "\t";
		List<String> lines = null; 		
		String webdrivers_opening_tag = "//<webdrivers>";
		String webdrivers_closing_tag = "//</webdrivers>";
		boolean isAWebDriverTag = false;
		boolean betweenTags = false;
		String osName = System.getProperty("os.name");
		logger.debug(String.format("Operating system: %s", osName));
		
		
		try 
		{
			Files.deleteIfExists(stringExternlizationFile_swp_path);
			Files.createFile(stringExternlizationFile_swp_path);
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
					isAWebDriverTag = true;
					betweenTags = false;
					
					String webdrivers="";
					
					if(osName.contains("Windows"))
					{
						webdrivers = tab +"public static final String WEBDRIVER_CHROME_VALUE = \"chromedriver.exe\";"+ System.lineSeparator()
								+ tab + "public static final String WEBDRIVER_FIREFOX_VALUE = \"geckodriver.exe\"; "+ System.lineSeparator()
								+ tab + "public static final String WEBDRIVER_EDGE_VALUE = \"msedgedriver.exe\"; ";
					}
					else if (osName.contains("MacOS"))
					{
						webdrivers =tab+"public static final String WEBDRIVER_CHROME_VALUE = \"chromedriver\"; "+ System.lineSeparator()
								+ tab +  "public static final String WEBDRIVER_FIREFOX_VALUE = \"geckodriver\"; "+ System.lineSeparator()
								+ tab +  "public static final String WEBDRIVER_EDGE_VALUE = \"msedgedriver\"; "+ System.lineSeparator()
								+ tab +  "public static final String WEBDRIVER_SAFARI_VALUE = \"\" \r\n; // /usr/bin/safaridriver to be used instead";
					}
					else if (osName.contains("Linux"))
					{
						webdrivers=tab+"public static final String WEBDRIVER_CHROME_VALUE = \"chromedriver\"; "+ System.lineSeparator()
								+ tab +  "public static final String WEBDRIVER_FIREFOX_VALUE = \"geckodriver\"; "+ System.lineSeparator()
								+ tab +  "public static final String WEBDRIVER_EDGE_VALUE = \"msedgedriver\"; ";
					}
					else {
						logger.error("Unrecognized operating system: %s",osName);
					}
					//Adding the webdrivers information
					line = webdrivers + System.lineSeparator()+ line; 
					
				}
				else
				{
					isAWebDriverTag = false;
				}
				
				if(!betweenTags ||  isAWebDriverTag) 
				{
					line = line + System.lineSeparator();
					Files.write(stringExternlizationFile_swp_path, line.getBytes(),StandardOpenOption.APPEND);
					logger.debug(line);
				}
			}
			logger.debug("---> All lines read.");
			
			//Replacing the previous version of StringExternalization
			Files.copy(stringExternlizationFile_swp_path, stringExternlizationFile_path, StandardCopyOption.REPLACE_EXISTING);
		} 
		catch (IOException e) 
		{
			logger.debug(StringExternalization.EXCEPTION_IO);
			e.printStackTrace();
		}		
		
	}	
	
}
