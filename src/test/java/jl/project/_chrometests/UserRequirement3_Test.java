package jl.project._chrometests;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.Robot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import org.testng.annotations.Test;

import jl.project.StringExternalization;
import jl.project.__commontests.RobotFactory;
import jl.project.__commontests.tests.TestsUtilCommon;
import jl.project.__commontests.tests.TestsUtilWithClicks;


/**
 * @author 
 * Class testing the display/hiding of items
 */
public class UserRequirement3_Test {
	Logger logger = LoggerFactory.getLogger(jl.project._chrometests.UserRequirement3_Test.class);
	WebDriver driver; 	
	Robot robot = RobotFactory.getRobotInstance();
	
	@BeforeClass
	public void setup()
	{		
		robot = RobotFactory.getRobotInstance();	
		
		driver = TestsUtilCommon.setup(logger,robot, StringExternalization.BROWSER_NAME_CHROME, driver, StringExternalization.WEBDRIVER_KEY_CHROME,StringExternalization.WEBDRIVER_VALUE_CHROME);
	
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
		TestsUtilCommon.releaseResources(driver);
	}
	
}
