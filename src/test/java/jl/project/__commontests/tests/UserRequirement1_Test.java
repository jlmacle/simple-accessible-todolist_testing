	
package jl.project.__commontests.tests;

import static org.assertj.core.api.Assertions.assertThat;


import java.awt.Robot;


import org.openqa.selenium.WebDriver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jl.project.__commontests.RobotFactory;
import jl.project.__commontests.TestsUtilCommon;


/**
 * @author 
 *	Class testing the user requirement 1 of creating and deleting a category
 */
public class UserRequirement1_Test extends UserRequirement_Common
{
	static Logger logger = LoggerFactory.getLogger(jl.project.__commontests.tests.UserRequirement1_Test.class);
	static WebDriver driver;	
	static Robot robot;
	
	public static void create_and_delete_category_UsingClicks(Logger logger, WebDriver driver, Robot robot) 
	{
		logger.debug("Entering "+new Object(){}.getClass().getEnclosingMethod().getName());
		boolean isCategoryFound = false;
		isCategoryFound = TestsUtilCommon.createCategory_UsingClicks(logger, driver, robot);
    	boolean isCategoryDeleted = false;
		isCategoryDeleted = TestsUtilCommon.deleteCategory_UsingClicks(logger, driver, robot);
		assertThat(isCategoryFound && isCategoryDeleted).isTrue();
    	
    }
}
