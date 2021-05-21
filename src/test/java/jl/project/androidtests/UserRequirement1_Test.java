package jl.project.androidtests;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.Robot;



import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import jl.project.StringExternalization;
import jl.project.__commontests.RobotFactory;
import jl.project.__commontests.TestsUtilCommon;
import jl.project.__commontests.TestsUtilWithClicks;

public class UserRequirement1_Test 
{
	Logger logger = Logger.getLogger(UserRequirement1_Test.class);
	WebDriver driver;
	Robot robot;
	
	@BeforeClass
	public void setup()
	{
		robot = RobotFactory.getRobotInstance();
		driver = TestsUtilCommon.setup(logger, robot, StringExternalization.BROWSER_NAME_CHROME, driver, StringExternalization.WEBDRIVER_CHROME_KEY, StringExternalization.WEBDRIVER_CHROME_ON_ANDROID_VALUE);
	}
	
	@BeforeMethod
	public void navigate()
	{
		driver.get(StringExternalization.ANGULAR_SERVER_URL);
		robot.delay(20000);
	}
	
	@Test
	public void createCategory()
	{
		boolean isCategoryFound = false;
		isCategoryFound = TestsUtilWithClicks.createCategory_UsingClicks(logger, driver, robot);
		assertThat(isCategoryFound).isTrue();
	}
	
	
	@AfterClass
	public void releaseResources()
	{
		TestsUtilCommon.release(driver);
	}

}
