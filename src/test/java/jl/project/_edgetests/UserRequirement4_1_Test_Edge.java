package jl.project._edgetests;

import java.awt.Robot;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import jl.project.StringExternalization;
import jl.project.__commontests.RobotFactory;
import jl.project.__commontests.tests.TestsUtilCommon;
import jl.project.__commontests.tests.UserRequirement4_Test;

/**
 * @author
 * Class testing the user requirement of physical impairment web accessibility 
 * using the keyboard only - Enter key used.
 */
public class UserRequirement4_1_Test_Edge {
	/* Note: delaying or not the sending of the keys impact the success of the tests */
	Logger logger = LoggerFactory.getLogger(jl.project._edgetests.UserRequirement4_1_Test_Edge.class);
	WebDriver driver;
	Robot robot = RobotFactory.getRobotInstance();
			
	@BeforeClass
	public void setup() 
	{
		driver = TestsUtilCommon.setup_and_navigate(StringExternalization.BROWSER_NAME_EDGE);
	}
	
	@Test(groups = {"creation_deletion_Chrome_1"})		
	public void createAndDeleteACategoryWithKeyboardOnly_EnterKey() 
	{
		UserRequirement4_Test.createAndDeleteACategoryWithKeyboardOnly(logger, driver, robot, Keys.ENTER);
	}
	
	@Test(groups = {"creation_deletion_Chrome_1"})		
	public void createAndDeleteItemWithKeyboardOnly_EnterKey() 
	{
		UserRequirement4_Test.createAndDeleteItemWithKeyboardOnly(logger, driver, robot, Keys.ENTER);		
	}
	
	@Test(dependsOnGroups = {"creation_deletion_Chrome_1"})		
	public void hideAndDisplayItemsWithKeyboardOnly_EnterKey() 
	{
		UserRequirement4_Test.hideAndDisplayItemsWithKeyboardOnly(logger, driver, robot, Keys.ENTER);			
	}
	
	@AfterClass	
	public void releaseResources() 
	{
		TestsUtilCommon.releaseResources(driver);
	}
}
