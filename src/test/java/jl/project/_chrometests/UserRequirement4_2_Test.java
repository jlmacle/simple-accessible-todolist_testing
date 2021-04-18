package jl.project._chrometests;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.Robot;

import org.testng.log4testng.Logger;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import jl.project.StringExternalization;
import jl.project.__commontests.RobotFactory;
import jl.project.__commontests.TestsUtilCommon;
import jl.project.__commontests.TestsUtilWithKeyboard;

/**
 * @author
 * Class testing the user requirement of physical impairment web accessibility 
 * using the keyboard only - Space key used.
 */
public class UserRequirement4_2_Test {
	Logger logger = Logger.getLogger(jl.project._chrometests.UserRequirement4_2_Test.class);
	/* Note: delaying or not the sending of the keys impact the success of the tests */
	WebDriver driver;	
	Robot robot;
	
	@BeforeClass
	public void setup() 
	{
		robot = RobotFactory.getRobotInstance();
		
		driver = TestsUtilCommon.setup(logger,robot, StringExternalization.BROWSER_NAME_CHROME, driver, StringExternalization.WEBDRIVER_CHROME_KEY,StringExternalization.WEBDRIVER_CHROME_VALUE);
	
	}
	
	@BeforeMethod
	public void navigate() {
		driver.get(StringExternalization.ANGULAR_SERVER_URL);
		
	}

	
	@Ignore
	@Test(groups = {"creation_deletion_Chrome_2"})		
	public void createAndDeleteACategoryWithKeyboardOnly_SpaceKey() 
	{
		boolean isTestSuccessful = false;
		isTestSuccessful = TestsUtilWithKeyboard.createAndDeleteCategory_UsingTheKeyboard(logger, driver, robot, Keys.SPACE, StringExternalization.TEST_KEYBOARD_SPACE_KEY);
		assertThat(isTestSuccessful).isTrue();			
	}
	
		
	@Test(groups = {"creation_deletion_Chrome_2"})	
	@Ignore
	public void createAndDeleteItemWithKeyboardOnly_SpaceKey() 
	{
		boolean isTestSuccessful = false;
		isTestSuccessful = TestsUtilWithKeyboard.createAndDeleteItem_UsingTheKeyboard(logger, driver, robot,  Keys.SPACE, StringExternalization.TEST_KEYBOARD_SPACE_KEY);
		assertThat(isTestSuccessful).isTrue();	
	}
	
	
	@Ignore
	@Test(dependsOnGroups = {"creation_deletion_Chrome_2"})		
	public void HideAndDisplayItemsWithKeyboardOnly_SpaceKey() 
	{
		boolean isTestSuccessful = false;
		isTestSuccessful = TestsUtilWithKeyboard.HideAndDisplayItems_UsingTheKeyboard(logger, driver, robot, Keys.SPACE, StringExternalization.TEST_KEYBOARD_SPACE_KEY);
		assertThat(isTestSuccessful).isTrue();	
			
	}
	
	
	@AfterClass
	public void releseResources() 
	{
		driver.close();
		driver.quit();
	}
	
}
