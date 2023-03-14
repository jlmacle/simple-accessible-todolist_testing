package jl.project._chrometests;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.Robot;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import jl.project.StringExternalization;
import jl.project.__commontests.RobotFactory;
import jl.project.__commontests.TestsUtilCommon;
import jl.project.__commontests.TestsUtilWithClicks;

/**
 * @author 
 * Class testing the creation and deletion of an item.
 * */


public class UserRequirement2_Test {
	Logger logger = LoggerFactory.getLogger(jl.project._chrometests.UserRequirement2_Test.class);	
	WebDriver driver;	
	Robot robot;
	
	@BeforeClass
	public void setup() 
	{		
		robot = RobotFactory.getRobotInstance();
		
		driver = TestsUtilCommon.setup(logger,robot, StringExternalization.BROWSER_NAME_CHROME, driver, StringExternalization.WEBDRIVER_CHROME_KEY,StringExternalization.WEBDRIVER_CHROME_VALUE);
	
	}
	
	@BeforeMethod
	public void navigate() 
	{
		driver.get(StringExternalization.ANGULAR_SERVER_URL);
		
	}
		
	@Test
	public void createItem() throws Exception
	{			
		boolean isItemCreated =  false;
		isItemCreated = TestsUtilWithClicks.createItem_UsingClicks(logger, driver, robot);
		assertThat(isItemCreated).isTrue();
	}
	
	@Test
	public void deleteItem() 
	{
		boolean isItemDeleted=false;
		isItemDeleted = TestsUtilWithClicks.deleteItem_UsingClicks(logger, driver, robot);
		assertThat(isItemDeleted).isTrue();	
	}
	

	@AfterClass
	public void releaseResources() 
	{
		TestsUtilCommon.release(driver);
	}
	

}
