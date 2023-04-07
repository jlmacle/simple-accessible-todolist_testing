	
package jl.project._chrometests;

import java.awt.Robot;


import org.openqa.selenium.WebDriver;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jl.project.StringExternalization;
import jl.project.__commontests.RobotFactory;
import jl.project.__commontests.tests.TestsUtilCommon;
import jl.project.__commontests.tests.UserRequirement1_Test;


/**
 * @author 
 *	Class testing the user requirement 1 of creating and deleting a category
 */
public class UserRequirement1_Test_Chrome extends UserRequirement1_Test
{
	Logger logger = LoggerFactory.getLogger(jl.project._chrometests.UserRequirement1_Test_Chrome.class);
	WebDriver driver;	
	Robot robot = RobotFactory.getRobotInstance();

	
	/**
	 * "The annotated method will be run before the first test method in the current class is invoked."  
	 * https://testng.org/doc/documentation-main.html
	 */
	@BeforeClass	
	public void setup() 
	{			
		driver = TestsUtilCommon.setup_and_navigate(StringExternalization.BROWSER_NAME_CHROME);
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
		TestsUtilCommon.releaseResources(driver);
	}

}
