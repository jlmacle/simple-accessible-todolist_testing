package jl.project._chrometests;

import static org.assertj.core.api.Assertions.assertThat;

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
import jl.project.__commontests.tests.TestsUtilWithKeyboard;
import jl.project.__commontests.tests.UserRequirement4_1_Test;

/**
 * @author
 * Class testing the user requirement of physical impairment web accessibility 
 * using the keyboard only - Enter key used.
 */
public class UserRequirement4_1_Test_Chrome {
	/* Note: delaying or not the sending of the keys impact the success of the tests */
	Logger logger = LoggerFactory.getLogger(jl.project._chrometests.UserRequirement4_1_Test_Chrome.class);
	WebDriver driver;
	Robot robot = RobotFactory.getRobotInstance();
			
	@BeforeClass
	public void setup() 
	{
		driver = TestsUtilCommon.setup_and_navigate(StringExternalization.BROWSER_NAME_CHROME);
	}
	
	@Ignore
	@Test(groups = {"creation_deletion_Chrome_1"})		
	public void createAndDeleteACategoryWithKeyboardOnly_EnterKey() 
	{
		UserRequirement4_1_Test.createAndDeleteACategoryWithKeyboardOnly_EnterKey(logger, driver, robot);
	}
	
	
	@Test(groups = {"creation_deletion_Chrome_1"})		
	public void createAndDeleteItemWithKeyboardOnly_EnterKey() 
	{
		UserRequirement4_1_Test.createAndDeleteItemWithKeyboardOnly_EnterKey(logger, driver, robot);		
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
