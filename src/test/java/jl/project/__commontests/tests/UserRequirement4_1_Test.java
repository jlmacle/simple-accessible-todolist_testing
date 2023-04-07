package jl.project.__commontests.tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.Robot;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jl.project.StringExternalization;

public class UserRequirement4_1_Test 
{
	static Logger logger = LoggerFactory.getLogger(jl.project.__commontests.tests.UserRequirement4_1_Test.class);
    static WebDriver driver;
    static Robot robot;

    public static void createAndDeleteACategoryWithKeyboardOnly_EnterKey(Logger logger, WebDriver driver, Robot robot) 
	{
		boolean isTestSuccessful = false;
		isTestSuccessful = TestsUtilWithKeyboard.createAndDeleteCategory_UsingTheKeyboard(logger, driver, robot, Keys.ENTER, StringExternalization.TEST_KEYBOARD_ENTER_KEY);
		assertThat(isTestSuccessful).isTrue();		
	}
    
}
