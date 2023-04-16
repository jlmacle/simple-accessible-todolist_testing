package jl.project.__commontests.tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.Robot;
import java.util.HashMap;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jl.project.StringExternalization;

public class UserRequirement4_Test 
{
	static Logger logger = LoggerFactory.getLogger(jl.project.__commontests.tests.UserRequirement4_Test.class);
    static WebDriver driver;
    static Robot robot;
	static HashMap <Keys, String> keyCommentAddOnMap = new HashMap <Keys, String>();

    public static void createAndDeleteACategoryWithKeyboardOnly(Logger logger, WebDriver driver, Robot robot, Keys key) 
	{
		boolean isTestSuccessful = false;
		TestsUtilCommon.load_KeyCommentAddOnMap();
		isTestSuccessful = TestsUtilWithKeyboard.createAndDeleteCategory_UsingTheKeyboard(logger, driver, robot, key, keyCommentAddOnMap.get(key));
		assertThat(isTestSuccessful).isTrue();		
	}
    

	public static void createAndDeleteItemWithKeyboardOnly(Logger logger, WebDriver driver, Robot robot, Keys key) 
	{
		boolean isTestSuccessful = false;
		isTestSuccessful = TestsUtilWithKeyboard.createAndDeleteItem_UsingTheKeyboard(logger, driver, robot,  key, keyCommentAddOnMap.get(key));
		assertThat(isTestSuccessful).isTrue();	
	}

	public static void hideAndDisplayItemsWithKeyboardOnly(Logger logger, WebDriver driver, Robot robot, Keys key) 
	{
		boolean isTestSuccessful = false;
		isTestSuccessful = TestsUtilWithKeyboard.hideAndDisplayItems_UsingTheKeyboard(logger, driver, robot,  key, keyCommentAddOnMap.get(key));
		assertThat(isTestSuccessful).isTrue();				
	}

}
