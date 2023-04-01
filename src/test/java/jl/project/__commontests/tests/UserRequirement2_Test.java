package jl.project.__commontests.tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.Robot;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.WebDriver;

/**
 * @author 
 * Class testing the creation and deletion of an item.
 * */


public class UserRequirement2_Test {
	static Logger logger = LoggerFactory.getLogger(jl.project._chrometests.UserRequirement2_Test_Chrome.class);	
	static WebDriver driver;	
	static Robot robot;


	public static void create_and_delete_item_UsingClicks(Logger logger, WebDriver driver, Robot robot) 
	{
		logger.debug("Entering "+new Object(){}.getClass().getEnclosingMethod().getName());
		boolean isItemCreated = false;
		isItemCreated = TestsUtilCommon.createItem_UsingClicks(logger, driver, robot);
		boolean isItemDeleted = false;
		isItemDeleted = TestsUtilCommon.deleteItem_UsingClicks(logger, driver, robot);
		assertThat(isItemCreated && isItemDeleted).isTrue();
		
	}

	
}
