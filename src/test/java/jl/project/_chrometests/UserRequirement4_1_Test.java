package jl.project._chrometests;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.Robot;


import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import jl.project.StringExternalization;
import jl.project.__commontests.RobotFactory;
import jl.project.__commontests.tests.TestsUtilCommon;
import jl.project.__commontests.tests.TestsUtilWithKeyboard;

/**
 * @author
 * Class testing the user requirement of physical impairment web accessibility 
 * using the keyboard only - Enter key used.
 */
public class UserRequirement4_1_Test {
	/* Note: delaying or not the sending of the keys impact the success of the tests */
	Logger logger = LoggerFactory.getLogger(jl.project._chrometests.UserRequirement4_1_Test.class);
	WebDriver driver;
	Robot robot = RobotFactory.getRobotInstance();
			
	@BeforeClass
	public void setup() 
	{
		robot = RobotFactory.getRobotInstance();
		
		driver = TestsUtilCommon.setup(logger,robot, StringExternalization.BROWSER_NAME_CHROME, driver, StringExternalization.WEBDRIVER_KEY_CHROME,StringExternalization.WEBDRIVER_VALUE_CHROME);
		
	}
	
	@BeforeMethod
	public void navigate() 
	{
		driver.get(StringExternalization.ANGULAR_SERVER_URL);		
	}
	
	@Ignore
	@Test(groups = {"creation_deletion_Chrome_1"})		
	public void createAndDeleteACategoryWithKeyboardOnly_EnterKey() 
	{
		boolean isTestSuccessful = false;
		isTestSuccessful = TestsUtilWithKeyboard.createAndDeleteCategory_UsingTheKeyboard(logger, driver, robot, Keys.ENTER, StringExternalization.TEST_KEYBOARD_ENTER_KEY);
		assertThat(isTestSuccessful).isTrue();		
	}
	
	
	@Test(groups = {"creation_deletion_Chrome_1"})		
	public void createAndDeleteItemWithKeyboardOnly_EnterKey() 
	{
		boolean isTestSuccessful = false;
		isTestSuccessful = TestsUtilWithKeyboard.createAndDeleteItem_UsingTheKeyboard(logger, driver, robot,  Keys.ENTER, StringExternalization.TEST_KEYBOARD_ENTER_KEY);
		assertThat(isTestSuccessful).isTrue();	
	}
	
	@Ignore
	@Test(dependsOnGroups = {"creation_deletion_Chrome_1"})		
	public void HideAndDisplayItemsWithKeyboardOnly_EnterKey() 
	{
		boolean isTestSuccessful = false;
		isTestSuccessful = TestsUtilWithKeyboard.HideAndDisplayItems_UsingTheKeyboard(logger, driver, robot,  Keys.ENTER, StringExternalization.TEST_KEYBOARD_ENTER_KEY);
		assertThat(isTestSuccessful).isTrue();	
			
	}
	
	@AfterClass	
	public void releaseResources() 
	{
		TestsUtilCommon.releaseResources(driver);
	}
}
