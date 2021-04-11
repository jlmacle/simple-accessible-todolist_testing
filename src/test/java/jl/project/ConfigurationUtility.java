package jl.project;

import jl.project.StringExternalization;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * 
 * @author 
 * Class that sets Selenium webdriver, Angular, and grid default configurations. 
 * The args that can be expected by the method main are:
 * - the path to the tmp folder
 * - the path the the StringExternalization.java file
 *
 */
public class ConfigurationUtility {
	static Logger logger = LoggerFactory.getLogger(ConfigurationUtility.class);
	static String path_to_tmp_folder_by_arg = null;
	static String stringExternalization_folder_by_arg = null;
	static String stringExternalization_folder = "/src/test/java/jl/project/";
	
	/***
	 * The arguments are used for scripting. 
	 * The first expected argument is the path to the tmp folder	 * 
	 */
	public static void main(String[] args) 
	{
		if (args.length!=0)
		{
			logger.debug(String.format("Value for arg[0]: %s", args[0]));
			path_to_tmp_folder_by_arg = args[0];
			logger.debug(String.format("Value for arg[1]: %s", args[1]));
			stringExternalization_folder_by_arg = args[1];
		}
		
		
		String tab = "\t";
		//configure_StringExternalization();
		String webdrivers_value_Windows = tab +"public static final String WEBDRIVER_CHROME_VALUE = \"chromedriver.exe\";"+ System.lineSeparator()
		+ tab + "public static final String WEBDRIVER_FIREFOX_VALUE = \"geckodriver.exe\"; "+ System.lineSeparator()
		+ tab + "public static final String WEBDRIVER_EDGE_VALUE = \"msedgedriver.exe\"; ";
		String webdrivers_value_MacOS = tab+"public static final String WEBDRIVER_CHROME_VALUE = \"chromedriver\"; "+ System.lineSeparator()
		+ tab +  "public static final String WEBDRIVER_FIREFOX_VALUE = \"geckodriver\"; "+ System.lineSeparator()
		+ tab +  "public static final String WEBDRIVER_EDGE_VALUE = \"msedgedriver\"; "+ System.lineSeparator()
		+ tab +  "public static final String WEBDRIVER_SAFARI_VALUE = \"\" ; // /usr/bin/safaridriver to be used instead";		
		String webdrivers_value_Linux = tab+"public static final String WEBDRIVER_CHROME_VALUE = \"chromedriver\"; "+ System.lineSeparator()
		+ tab +  "public static final String WEBDRIVER_FIREFOX_VALUE = \"geckodriver\"; "+ System.lineSeparator()
		+ tab +  "public static final String WEBDRIVER_EDGE_VALUE = \"msedgedriver\"; ";
		
		String selenium_Grid_not_used = tab +  "public static final boolean GRID_NOT_USED = true;";
		
		String angular_server = tab +  "public static final String ANGULAR_SERVER_URL = \"http://localhost:4200\";";
		
		replace_tag_by_data("webdrivers",stringExternalization_folder, "StringExternalization.java",webdrivers_value_Windows,webdrivers_value_MacOS,webdrivers_value_Linux);
		
		replace_tag_by_data("seleniumGrid4",stringExternalization_folder, "StringExternalization.java",selenium_Grid_not_used,selenium_Grid_not_used,selenium_Grid_not_used);
		
		replace_tag_by_data("angularServer",stringExternalization_folder, "StringExternalization.java",angular_server,angular_server,angular_server);
		
	}
	
	/***
	 * A method used to insert configuration data in a file. 
	 * An opening and closing tag in the file locates the position where the information should be inserted.
	 * 
	 * @param tagValue:			the tag used to locate the information.
	 * @param file_folder: 		the folder containing the file with the configuration information. 
	 * 							The path to the folder is relative to the root folder of the application.
	 * @param file_name: 		the name of the file with the configuration information. 
	 * @param value_Windows: 	the configuration value to insert if the operating system is Windows 
	 * @param value_MacOS: 		the configuration value to insert if the operating system is macOS.
	 * @param value_Linux: 		the configuration value to insert if the operating system is Linux.
	 */
	public static void replace_tag_by_data(String tagValue, String file_folder, String file_name, String value_Windows, String value_MacOS, String value_Linux)
	{
		//temporary folder
		String path_to_tmp_folder = null;
		if (path_to_tmp_folder_by_arg !=null) path_to_tmp_folder = path_to_tmp_folder_by_arg;		
		else path_to_tmp_folder = "tmp";
		
		if (stringExternalization_folder_by_arg !=null) stringExternalization_folder = stringExternalization_folder_by_arg;
		else stringExternalization_folder=file_folder;
		
		Path file_swp_path = FileSystems.getDefault().getPath(path_to_tmp_folder, file_name+".txt");
		
		Path file_path = FileSystems.getDefault().getPath( System.getProperty("user.dir")+stringExternalization_folder, file_name);
		
		
		List<String> lines = null; 		
		String opening_tag = "//<"+tagValue+">";
		String closing_tag = "//</"+tagValue+">";
		boolean isATag = false;
		boolean betweenTags = false;
		String osName = System.getProperty("os.name");
		logger.debug(String.format("Operating system: %s", osName));
		
		
		try 
		{
			Files.deleteIfExists(file_swp_path);
			Files.createFile(file_swp_path);
			lines = Files.readAllLines(file_path);
			logger.debug(String.format("The file has %d lines.",lines.size()));
			
			for(String line:lines)
			{	
				
				if(line.contains(opening_tag))
				{
					betweenTags = true;
					isATag = true;
					
					
				}
				else if(line.contains(closing_tag))
				{
					isATag = true;
					betweenTags = false;
					
					String conf_data="";
					
					if(osName.contains("Windows"))
					{
						conf_data =value_Windows;
					}
					else if (osName.contains("Mac OS"))
					{
						conf_data = value_MacOS;
					}
					else if (osName.contains("Linux"))
					{
						conf_data= value_Linux;
					}
					else {
						logger.error("Unrecognized operating system: %s",osName);
					}
					//Adding the configuration information
					line = conf_data + System.lineSeparator()+ line; 
					
				}
				else
				{
					isATag = false;
				}
				
				if(!betweenTags ||  isATag) 
				{
					line = line + System.lineSeparator();
					Files.write(file_swp_path, line.getBytes(),StandardOpenOption.APPEND);
					logger.debug(line);
				}
			}
			logger.debug("---> All lines read.");
			
			//Replacing the previous version of the file
			Files.copy(file_swp_path, file_path, StandardCopyOption.REPLACE_EXISTING);
		} 
		catch (IOException e) 
		{
			logger.debug(StringExternalization.EXCEPTION_IO);
			e.printStackTrace();
		}		
	}
	
	
}
