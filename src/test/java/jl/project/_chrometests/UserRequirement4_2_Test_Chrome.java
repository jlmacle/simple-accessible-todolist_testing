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
import org.testng.annotations.Test;

import jl.project.StringExternalization;
import jl.project.__commontests.RobotFactory;
import jl.project.__commontests.tests.TestsUtilCommon;
import jl.project.__commontests.tests.TestsUtilWithKeyboard;

/**
 * @author
 * Class testing the user requirement of physical impairment web accessibility 
 * using the keyboard only - Space key used.
 */
public class UserRequirement4_2_Test_Chrome {
	Logger logger = LoggerFactory.getLogger(jl.project._chrometests.UserRequirement4_2_Test_Chrome.class);
	/* Note: delaying or not the sending of the keys impact the success of the tests */
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

	@Test(groups = {"creation_deletion_Chrome_2"})		
	public void createAndDeleteACategoryWithKeyboardOnly_SpaceKey() 
	{
		boolean isTestSuccessful = false;
		isTestSuccessful = TestsUtilWithKeyboard.createAndDeleteCategory_UsingTheKeyboard(logger, driver, robot, Keys.SPACE, StringExternalization.TEST_KEYBOARD_SPACE_KEY);
		assertThat(isTestSuccessful).isTrue();			
	}
		
	@Test(groups = {"creation_deletion_Chrome_2"})	
	
	public void createAndDeleteItemWithKeyboardOnly_SpaceKey() 
	{
		boolean isTestSuccessful = false;
		isTestSuccessful = TestsUtilWithKeyboard.createAndDeleteItem_UsingTheKeyboard(logger, driver, robot,  Keys.SPACE, StringExternalization.TEST_KEYBOARD_SPACE_KEY);
		assertThat(isTestSuccessful).isTrue();	
	}
	
	@Test(dependsOnGroups = {"creation_deletion_Chrome_2"})		
	public void hideAndDisplayItemsWithKeyboardOnly_SpaceKey() 
	{
		boolean isTestSuccessful = false;
		isTestSuccessful = TestsUtilWithKeyboard.hideAndDisplayItems_UsingTheKeyboard(logger, driver, robot, Keys.SPACE, StringExternalization.TEST_KEYBOARD_SPACE_KEY);
		assertThat(isTestSuccessful).isTrue();	
			
	}
	
	
	@AfterClass	
	public void releaseResources() 
	{
		TestsUtilCommon.releaseResources(driver);
	}
	
}
