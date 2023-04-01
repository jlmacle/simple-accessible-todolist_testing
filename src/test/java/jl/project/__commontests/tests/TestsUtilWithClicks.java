package jl.project.__commontests.tests;

import static org.testng.Assert.fail;

import java.awt.Robot;
import java.util.List;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;

import jl.project.StringExternalization;


// Code with likely immaturities.
// To do: code rewrite to allow running tests in parallel.


public class TestsUtilWithClicks 
{		
	/**
	 * Tests the ability to HIDE and DISPLAY an ITEM using CLICKS
	 * @param logger - the logger instance of User Requirement Test class
	 * @param driver - the webdriver instance used for the test
	 * @param robot - the instance of the Robot class used to add delays
	 * @return true if the test is successful, false otherwise
	 */
 	public static boolean hideAndDisplayItem_UsingClicks(Logger logger, WebDriver driver, Robot robot)
 	{ 	
		
 		boolean isTestSuccessful = false;	
		
		logger.debug(String.format("%s %s","Entering ",new Object(){}.getClass().getEnclosingMethod().getName()));
		
		driver.get(StringExternalization.ANGULAR_SERVER_URL);
		driver.navigate().refresh();		
		
		//1. Creation of an item. By default the item is displayed
		logger.debug(String.format("%s %s","1.",StringExternalization.TEST_ITEM_CREATION));
		//Adding an item to the Uncategorized category created at startup
		TestsUtilCommon.sendKeys_withId(StringExternalization.ELEMENT_ID_FOR_CATEGORY_TO_SELECT_FIELD, StringExternalization.TEST_NAME_OF_DEFAULT_CATEGORY, driver);
		TestsUtilCommon.sendKeys_withId(StringExternalization.ELEMENT_ID_FOR_NEW_ITEM_FIELD, StringExternalization.TEST_STRING_FOR_TEST_ITEM, driver);
		TestsUtilCommon.click_withId(StringExternalization.ELEMENT_ID_FOR_ADD_ITEM_BUTTON, driver);
				
		//To avoid a StaleElementReferenceException 
		driver.get(StringExternalization.ANGULAR_SERVER_URL);
		robot.delay(2000);

		//2. Verification that the item is created and displayed	
		// TODO: test to re-work at a later time
		logger.debug(String.format("%s %s","2.",StringExternalization.TEST_ITEM_VERIFICATION_OF_DISPLAYED_ITEM));
		boolean itemIsCreated = TestsUtilCommon.isTextFindableWithinElements_withName(StringExternalization.TEST_STRING_FOR_TEST_ITEM,StringExternalization.ELEMENT_AN_ITEM_NAME, driver, logger);
		boolean itemCreatedIsDisplayed = TestsUtilCommon.isTextFindableWithinElements_withCSSSelector( StringExternalization.TEST_STRING_FOR_TEST_ITEM, StringExternalization.ELEMENT_AN_ITEM_CLASS, driver, logger);
		if (!(itemIsCreated && itemCreatedIsDisplayed)) {fail(StringExternalization.TEST_ITEM_HIDING_DISPLAY_ITEM_NOT_FOUND);};

		//3. Verification that the item can be hidden.	
		logger.debug(String.format("%s %s","3.",StringExternalization.TEST_ITEM_VERIFICATION_OF_HIDEABLE_ITEM));
		//Click on the category to hide the item. Only one category (Uncategorized) means only one element named foldUnfoldArea.
		driver.findElement(By.cssSelector(StringExternalization.ELEMENT_A_FOLDABLE_AREA)).click();
		
		//4. Verification that the item is hidden		
		itemCreatedIsDisplayed = TestsUtilCommon.isTextFindableWithinElements_withCSSSelector( StringExternalization.TEST_STRING_FOR_TEST_ITEM, StringExternalization.ELEMENT_AN_ITEM_CLASS, driver, logger);
		if (itemCreatedIsDisplayed) {fail(StringExternalization.TEST_ITEM_HIDING_DISPLAY_ITEM_NOT_FOUND);};
		if(itemCreatedIsDisplayed) 
		{fail(StringExternalization.TEST_ITEM_HIDING_DISPLAY_ITEM_NOT_HIDDEN);};
	
		//4. Verification that the item can be displayed 
		logger.debug(String.format("%s %s","4.", StringExternalization.TEST_ITEM_VERIFICATION_OF_DISPLAYED_ITEM));
		driver.findElement(By.cssSelector(StringExternalization.ELEMENT_A_FOLDABLE_AREA)).click();				
		itemCreatedIsDisplayed = TestsUtilCommon.isTextFindableWithinElements_withCSSSelector( StringExternalization.TEST_STRING_FOR_TEST_ITEM, StringExternalization.ELEMENT_AN_ITEM_CLASS, driver, logger);
		if(!itemCreatedIsDisplayed) 
		{fail(StringExternalization.TEST_ITEM_HIDING_DISPLAY_ITEM_NOT_FOUND);}
		
		// The test succeeded. The next steps are for cleaning the test environment.
		else {isTestSuccessful=true;}

		
		//5. Suppressing the item to go on with the test suite
		logger.debug(String.format("%s %s", "5.", StringExternalization.TEST_ITEM_DELETION));
		driver.get(StringExternalization.ANGULAR_SERVER_URL);		
		robot.delay(2000);

		
		
		// List<WebElement> anIconToDeleteAnItemElements = driver.findElements(By.name(StringExternalization.ELEMENT_AN_ICON_TO_DELETE_AN_ITEM_NAME));
		// 	for(WebElement anIconToDeleteAnItemElement: anIconToDeleteAnItemElements) {//only one item in the test
		// 		anIconToDeleteAnItemElement.click();
		// 		robot.delay(2000);
		// 	}
			
		logger.debug("6. Testing the deletion of the test item");
		driver.get(StringExternalization.ANGULAR_SERVER_URL);
		robot.delay(2000);
		
		TestsUtilCommon.click_onElement_withName(StringExternalization.ELEMENT_AN_ICON_TO_DELETE_AN_ITEM_NAME, 1, driver, logger);
		List<WebElement> anIconToDeleteAnItemElements = driver.findElements(By.name(StringExternalization.ELEMENT_AN_ICON_TO_DELETE_AN_ITEM_NAME));
		if( !anIconToDeleteAnItemElements.isEmpty() ) fail("The test item was not deleted. "+anIconToDeleteAnItemElements.size()+" element has been found with the name anIconToDeleteAnItem");
		else {logger.debug(String.format("The test item has been deleted, the number is items found with the name %s is: %d ",StringExternalization.ELEMENT_AN_ICON_TO_DELETE_AN_ITEM_NAME,anIconToDeleteAnItemElements.size() ));}
		
		return isTestSuccessful;
 	}
 	
}
