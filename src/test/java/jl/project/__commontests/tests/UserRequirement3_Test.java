package jl.project.__commontests.tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.Robot;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserRequirement3_Test
{
    static Logger logger = LoggerFactory.getLogger(jl.project.__commontests.tests.UserRequirement3_Test.class);
    static WebDriver driver;
    static Robot robot;
    
	public static void hideAndDisplayItem_UsingClicks(Logger logger, WebDriver driver, Robot robot)
	{
		boolean isTestSuccessful = false;
		isTestSuccessful = TestsUtilWithClicks.hideAndDisplayItem_UsingClicks(logger, driver, robot);	
		assertThat(isTestSuccessful).isTrue();
		
	}	
}