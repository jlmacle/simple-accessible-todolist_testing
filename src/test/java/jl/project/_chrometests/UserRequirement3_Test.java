			package jl.project._chrometests;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.Robot;

import org.testng.log4testng.Logger;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import org.testng.annotations.Test;

import jl.project.StringExternalization;
import jl.project.__commontests.RobotFactory;
import jl.project.__commontests.TestsUtilCommon;
import jl.project.__commontests.TestsUtilWithClicks;


/**
 * @author 
 * Class testing the display/hiding of items
 */
public class UserRequirement3_Test {
	Logger logger = Logger.getLogger(jl.project._chrometests.UserRequirement3_Test.class);
	WebDriver driver; 	
	Robot robot;
	
	@BeforeClass
	public void setup()
	{		
		robot = RobotFactory.getRobotInstance();
		
		driver = TestsUtilCommon.setup(logger,robot, StringExternalization.BROWSER_NAME_CHROME, driver, StringExternalization.WEBDRIVER_CHROME_KEY,StringExternalization.WEBDRIVER_CHROME_VALUE);
	
	}	
	
	@Test
	public void hideAndDisplayItem_UsingClicks()
	{
		boolean isTestSuccessful = false;
		isTestSuccessful = TestsUtilWithClicks.hideAndDisplayItem_UsingClicks(logger, driver, robot);	
		assertThat(isTestSuccessful).isTrue();
		
	}	
	
	@AfterClass	
	public void releaseResources() 
	{
		TestsUtilCommon.release(driver);
	}
	
}
