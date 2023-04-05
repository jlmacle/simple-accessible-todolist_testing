package jl.project._edgetests;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.Robot;



import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jl.project.__commontests.RobotFactory;
import jl.project.__commontests.tests.TestsUtilWithClicks;


/**
 * @author 
 * Class testing the display/hiding of items
 */
public class UserRequirement3_Test {
	Logger logger = LoggerFactory.getLogger(jl.project._edgetests.UserRequirement3_Test.class);
	WebDriver driver; 
	Robot robot = RobotFactory.getRobotInstance();
		
	public static void hideAndDisplayItem_UsingClicks(Logger logger, WebDriver driver, Robot robot) 
	{
		boolean isTestSuccessful = false;
		isTestSuccessful = TestsUtilWithClicks.hideAndDisplayItem_UsingClicks(logger, driver, robot);	
		assertThat(isTestSuccessful).isTrue();			
	}	


}
