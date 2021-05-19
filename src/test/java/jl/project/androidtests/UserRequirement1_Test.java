package jl.project.androidtests;

import java.awt.Robot;

import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.log4testng.Logger;

import jl.project.StringExternalization;
import jl.project.__commontests.RobotFactory;
import jl.project.__commontests.TestsUtilCommon;

public class UserRequirement1_Test 
{
	Logger logger = Logger.getLogger(UserRequirement1_Test.class);
	WebDriver driver;
	Robot robot;
	
	@BeforeClass
	public void setup()
	{
		robot = RobotFactory.getRobotInstance();
		driver = TestsUtilCommon.setup(logger, robot, StringExternalization.BROWSER_NAME_CHROME, driver, StringExternalization.WEBDRIVER_CHROME_KEY, StringExternalization.WEBDRIVER_CHROME_VALUE);
	}
	
	@BeforeMethod
	public void navigate()
	{
		driver.get(StringExternalization.ANGULAR_SERVER_URL);
	}
	
	

}
