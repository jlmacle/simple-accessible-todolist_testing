package jl.project._edgetests;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.Robot;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import jl.project.StringExternalization;
import jl.project.__commontests.RobotFactory;
import jl.project.__commontests.tests.TestsUtilCommon;

/**
 * @author 
 * Class testing the creation and deletion of an item.
 * */


public class UserRequirement2_Test_Edge {
	Logger logger = LoggerFactory.getLogger(jl.project._edgetests.UserRequirement2_Test_Edge.class);
	WebDriver driver;
	Robot robot = RobotFactory.getRobotInstance();
		
	/**
	 * "The annotated method will be run before the first test method in the current class is invoked."  
	 * https://testng.org/doc/documentation-main.html
	 */
	@BeforeClass	
	public void setup() 
	{			
		driver = TestsUtilCommon.setup_and_navigate(StringExternalization.BROWSER_NAME_EDGE);
	}	
	
	@Test
	public void create_and_delete_item_UsingClicks() 
	{
		logger.debug("Entering "+new Object(){}.getClass().getEnclosingMethod().getName());
		boolean isItemCreated = false;
		isItemCreated = TestsUtilCommon.createItem_UsingClicks(logger, driver, robot);
		boolean isItemDeleted = false;
		isItemDeleted = TestsUtilCommon.deleteItem_UsingClicks(logger, driver, robot);
		assertThat(isItemCreated && isItemDeleted).isTrue();		
	}
	

	@AfterClass
	public void releaseResources() 
	{
		TestsUtilCommon.releaseResources(driver);
	}
	
	
	
	
}
