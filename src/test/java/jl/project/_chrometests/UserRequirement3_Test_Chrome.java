package jl.project._chrometests;

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
import jl.project._edgetests.UserRequirement3_Test;


/**
 * @author 
 * Class testing the display/hiding of items
 */
public class UserRequirement3_Test_Chrome {
	Logger logger = LoggerFactory.getLogger(jl.project._chrometests.UserRequirement3_Test_Chrome.class);
	WebDriver driver; 	
	Robot robot = RobotFactory.getRobotInstance();
	
	@BeforeClass
	public void setup()
	{		
		driver = TestsUtilCommon.setup_and_navigate(StringExternalization.BROWSER_NAME_CHROME);
	}	
	
	@Test
	public void hideAndDisplayItem_UsingClicks()
	{
		UserRequirement3_Test.hideAndDisplayItem_UsingClicks(logger, driver, robot);		
	}	
	
	@AfterClass	
	public void releaseResources() 
	{
		TestsUtilCommon.releaseResources(driver);
	}
	
}
