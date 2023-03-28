package jl.project.androidtests;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.Robot;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import jl.project.StringExternalization;
import jl.project.__commontests.RobotFactory;
import jl.project.__commontests.TestsUtilCommon;

public class UserRequirement1_Test 
{
	Logger logger = LoggerFactory.getLogger(UserRequirement1_Test.class);
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
		robot.delay(2000);
	}
	
	/**
	 * Tests a successful creation of category
	 */
	@Test	
    public void createCategory_UsingClicks() 
	{
		boolean isCategoryFound = false;
		isCategoryFound = TestsUtilCommon.createCategory_UsingClicks(logger, driver, robot);
    	assertThat(isCategoryFound).isTrue();
    	
    }
	
	/**
	 * Tests a successful deletion of category	 
	 */
	@Test	
	public void deleteCategory_UsingClicks() 
	{
		boolean isCategoryFound = false;
		isCategoryFound = TestsUtilCommon.deleteCategory_UsingClicks(logger, driver, robot);
		assertThat(isCategoryFound).isFalse();		
	}	
	
	
	@AfterClass
	public void releaseResources()
	{
		TestsUtilCommon.release(driver);
	}

}
