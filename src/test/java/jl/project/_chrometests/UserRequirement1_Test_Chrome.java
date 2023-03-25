	
package jl.project._chrometests;

import java.awt.Robot;


import org.openqa.selenium.WebDriver;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jl.project.StringExternalization;
import jl.project.__commontests.RobotFactory;
import jl.project.__commontests.TestsUtilCommon;

import jl.project.__commontests.tests.UserRequirement1_Test;


/**
 * @author 
 *	Class testing the user requirement 1 of creating and deleting a category
 */
public class UserRequirement1_Test_Chrome 
{
	Logger logger = LoggerFactory.getLogger(jl.project._chrometests.UserRequirement1_Test_Chrome.class);
	WebDriver driver;	
	Robot robot;

	
	/**
	 * "The annotated method will be run before the first test method in the current class is invoked."  
	 * https://testng.org/doc/documentation-main.html
	 */
	@BeforeClass	
	public void setup() 
	{	
		driver = UserRequirement1_Test.setup(StringExternalization.BROWSER_NAME_CHROME, StringExternalization.WEBDRIVER_CHROME_KEY,StringExternalization.WEBDRIVER_CHROME_VALUE);
		robot = RobotFactory.getRobotInstance();		
	}
	
	/**
	 * "The annotated method will be run before each test method"
	 * https://testng.org/doc/documentation-main.html
	 */
	@BeforeMethod	
	public void navigate() 
	{
		UserRequirement1_Test.navigate(StringExternalization.ANGULAR_SERVER_URL);			
	}
	
	/**
	 * Tests a successful creation and deletion of category
	 */
	@Test	
    public void create_and_delete_category_UsingClicks() 
	{
		UserRequirement1_Test.create_and_delete_category_UsingClicks(logger, driver, robot);
    	
    }		
	
	/**
	 * The annotated method will be run after all the test methods in the current class have been run.
	 * https://testng.org/doc/documentation-main.html 
	 */
	@AfterClass	
	public void releaseResources() 
	{
		TestsUtilCommon.release(driver);
	}

}
