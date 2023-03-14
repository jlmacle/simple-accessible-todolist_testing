package jl.project._edgetests;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.Robot;



import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import org.testng.annotations.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jl.project.StringExternalization;
import jl.project.__commontests.RobotFactory;
import jl.project.__commontests.TestsUtilCommon;
import jl.project.__commontests.TestsUtilWithClicks;


/**
 * @author 
 * Class testing the display/hiding of items
 */
public class UserRequirement3_Test {
	Logger logger = LoggerFactory.getLogger(jl.project._edgetests.UserRequirement3_Test.class);
	WebDriver driver; 
	Robot robot;
		
	@BeforeClass
	public void setup()
	{
		robot = RobotFactory.getRobotInstance();
		
		driver = TestsUtilCommon.setup(logger,robot, StringExternalization.BROWSER_NAME_EDGE, driver, StringExternalization.WEBDRIVER_EDGE_KEY,StringExternalization.WEBDRIVER_EDGE_VALUE);
		
	}
	
	@Test
	public void hideAndDisplayItem() 
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
