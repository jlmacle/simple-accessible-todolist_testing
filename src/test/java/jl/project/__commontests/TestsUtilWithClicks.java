package jl.project.__commontests;

import static org.testng.Assert.fail;

import java.awt.Robot;
import java.util.List;


import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;

import jl.project.StringExternalization;


// Code with likely immaturities.
// To do: code rewrite to allow running tests in parallel.


public class TestsUtilWithClicks 
{	
	public static boolean createAndDeleteCategory_UsingClicks(Logger logger, WebDriver driver, Robot robot)
	{
		boolean isTestSuccessful = false;

		logger.debug("Entering "+new Object(){}.getClass().getEnclosingMethod().getName());
		logger.info(String.format("%s %s", 
			StringExternalization.COMMENT_ENTERING_TEST_FOR,
			StringExternalization.TEST_CATEGORY_CREATION_AND_DELETION,
			StringExternalization.TEST_WITH_CLICKS
		));

		// Category creation
		boolean isCategoryCreated = false;
		logger.info(String.format("%s %s %s","1.", 
				StringExternalization.TEST_CATEGORY_CREATION, 
				StringExternalization.TEST_WITH_CLICKS));
		
		isCategoryCreated = TestsUtilCommon.createCategory_UsingClicks(logger, driver, robot);
		if(!isCategoryCreated) {fail(StringExternalization.TEST_FAILURE_CATEGORY_CREATION);}
		
		// Can the category created be deleted ?
		boolean isCategoryDeleted = false;
		

		//TODO: code to finish
	
		return isTestSuccessful;
	}

 	
 	
	/**
	 *  Tests the DELETION of a CATEGORY using CLICKS
	 * @param logger - the logger instance of User Requirement Test class 
	 * @param driver - the webdriver instance used for the test
	 * @param robot - the instance of the Robot class used to add delays
	 * @return true if the test is successful, false otherwise
	 */
 	public static boolean deleteCategory_UsingClicks(Logger logger, WebDriver driver, Robot robot)
 	{
	 	logger.info(StringExternalization.COMMENT_ENTERING_TEST_FOR+StringExternalization.TEST_CATEGORY_DELETION);
		int testCategoryPositionIntheList = 0;
		int currentCategoryPosition = 0;
		boolean isCategoryFound = false;
		
		//1. Confirmation that the category was created; registration of its position in the list of elements named aCategory    	
		logger.info(String.format("1. %s",StringExternalization.TEST_CATEGORY_CREATION_CONFIRMATION));
		driver.get(StringExternalization.ANGULAR_SERVER_URL);
		robot.delay(2000);

		isCategoryFound = TestsUtilCommon.isTextFindableWithinElements_withName(StringExternalization.TEST_STRING_FOR_CREATED_CATEGORY, StringExternalization.ELEMENT_NAME_A_CATEGORY, driver, logger);
		if(!isCategoryFound) {fail(StringExternalization.TEST_FAILURE_CATEGORY_FOUND);}		

		//2. Deletion of the category created
		// finding the elements with the name StringExternalization.ELEMENT_NAME_AN_ICON_TO_DELETE_A_CATEGORY
		logger.info(String.format("2. %s", StringExternalization.TEST_CATEGORY_DELETION));
		driver.get(StringExternalization.ANGULAR_SERVER_URL);
		robot.delay(2000);
		
		List<WebElement> trashIconElementsInFrontOfCategories = driver.findElements(By.name(StringExternalization.ELEMENT_NAME_AN_ICON_TO_DELETE_A_CATEGORY));
		logger.debug(StringExternalization.DEBUG_FOUND+trashIconElementsInFrontOfCategories.size()+" elements with name anIconToDeleteACategory. There should be no more than 2.");    		
		try 
		{
			currentCategoryPosition=0;    			
			for(WebElement trashCanIconElementInFrontOfCategory : trashIconElementsInFrontOfCategories) 
			{
				// Goal : to delete the category in second position
				currentCategoryPosition++;    				    				
				if (currentCategoryPosition == testCategoryPositionIntheList) 
				{
					logger.debug("Clicking the trash can icon in position: "+currentCategoryPosition);
					trashCanIconElementInFrontOfCategory.click();
					//Issue with undeleted category
					robot.delay(2000);
					break;
				}
				
			}
			
		}
		catch(StaleElementReferenceException e) 
		{
			logger.error(StringExternalization.EXCEPTION_STALE_ELEMENT_REFERENCE
					+ "while going through the elements related to a trash can icon in front of a category.");
			e.getMessage();
			e.printStackTrace();    			
		}    	
		
		
		//3. confirmation of deletion
		logger.info(String.format("%s %s","3. ", StringExternalization.TEST_CATEGORY_DELETION_CONFIRMATION));
		driver.get(StringExternalization.ANGULAR_SERVER_URL);
		robot.delay(2000);			
		
		isCategoryFound = TestsUtilCommon.isTextFindableWithinElements_withName(StringExternalization.TEST_STRING_FOR_CREATED_CATEGORY, StringExternalization.ELEMENT_NAME_A_CATEGORY, driver, logger);
		if(isCategoryFound) {fail(StringExternalization.TEST_FAILURE_CATEGORY_FOUND);}
		
		
		return true;
 	}

	/**
	 * Tests the CREATION of an ITEM using CLICKS
	 * @param logger - the logger instance of User Requirement Test class
	 * @param driver - the webdriver instance used for the test
	 * @param robot - the instance of the Robot class used to add delays
	 * @return true if the test is successful, false otherwise
	 */
 	public static boolean createItem_UsingClicks(Logger logger, WebDriver driver, Robot robot)
 	{ 	 		
 		logger.info(StringExternalization.COMMENT_ENTERING_TEST_FOR+StringExternalization.TEST_STEP_ITEM_CREATION);	
		logger.info("1. "+StringExternalization.TEST_STEP_ITEM_CREATION);
		boolean isItemCreated=false;
		//Adding an item to the Uncategorized category created at startup
		driver.findElement(By.id(StringExternalization.ELEMENT_ID_FOR_CATEGORY_TO_SELECT_FIELD)).sendKeys("Uncategorized");
		driver.findElement(By.id(StringExternalization.ELEMENT_ID_FOR_NEW_ITEM_FIELD)).sendKeys(StringExternalization.TEST_STRING_FOR_TEST_ITEM);
		driver.findElement(By.id(StringExternalization.ELEMENT_ID_FOR_ADD_ITEM_BUTTON)).click();
		//To avoid a StaleElementReferenceException
		driver.get(StringExternalization.ANGULAR_SERVER_URL);

		// Giving time for the item to be displayed
    	// Issue with undetected created item.
		
		robot.delay(3000);		
    					
		//Checking that the new item creation was successful		
		logger.info("2. Confirmation of item creation ");
		driver.get(StringExternalization.ANGULAR_SERVER_URL);
		robot.delay(2000);
		List<WebElement> anItemElements = driver.findElements(By.name(StringExternalization.ELEMENT_AN_ITEM_NAME));
		try 
		{
			logger.debug(StringExternalization.DEBUG_FOUND+anItemElements.size()+StringExternalization.DEBUG_ELEMENT_NAMED_AN_ITEM);
			for(WebElement anItemElement: anItemElements) 
			{
				String text = anItemElement.getText();				
				if (text.contains(StringExternalization.TEST_STRING_FOR_TEST_ITEM))
				{
					logger.debug(StringExternalization.DEBUG_FOUND+text+" as text.");
					isItemCreated = true;
				}
			}
			
		}
		catch(StaleElementReferenceException e) 
		{
			logger.error(StringExternalization.EXCEPTION_STALE_ELEMENT_REFERENCE
					+ "the elements named 'anItem' after creation of the element.");
			e.getMessage();
			e.printStackTrace();
		}			
		
		robot.delay(3000);    	   	

		return isItemCreated;
 		
 	}
 	
	/**
	 * Tests the DELETION of an ITEM using CLICKS
	 * @param logger - the logger instance of User Requirement Test class
	 * @param driver - the webdriver instance used for the test
	 * @param robot - the instance of the Robot class used to add delays
	 * @return true if the test is successful, false otherwise
	 */
 	public static boolean deleteItem_UsingClicks(Logger logger, WebDriver driver, Robot robot)
 	{
 		boolean isItemDeleted=false;
 		logger.debug(StringExternalization.COMMENT_ENTERING_TEST_FOR+StringExternalization.TEST_ITEM_DELETION);
		logger.debug("1. "+StringExternalization.TEST_ITEM_DELETION);
		
		driver.get(StringExternalization.ANGULAR_SERVER_URL);
		robot.delay(2000);
		
		List<WebElement> anIconToDeleteAnItemElements = driver.findElements(By.name(StringExternalization.ELEMENT_AN_ICON_TO_DELETE_AN_ITEM_NAME));
		robot.delay(2000);
		try 
		{			
			logger.debug(StringExternalization.DEBUG_FOUND+anIconToDeleteAnItemElements.size()+" element named 'anIconToDeleteAnItem'");
			//There should be only one item
			if(anIconToDeleteAnItemElements.size() != 1) 
				{fail(StringExternalization.EXCEPTION_ITEM_NOT_EXISTING_OR_NOT_UNIQUE+anIconToDeleteAnItemElements.size());}
			for(WebElement anIconToDeleteAnItemElement: anIconToDeleteAnItemElements) 
			{				
				anIconToDeleteAnItemElement.click();
				logger.debug("Trash can icon clicked.");
				robot.delay(2000);
			}
			
		}
		catch(StaleElementReferenceException e) 
		{
			logger.error(StringExternalization.EXCEPTION_STALE_ELEMENT_REFERENCE
					+ "the elements named 'anIconToDeleteAnItem' ");
			e.getMessage();
			e.printStackTrace();
		}
		
		//Checking the absence of the items
		logger.debug("2. Confirmation of deletion");
		driver.get(StringExternalization.ANGULAR_SERVER_URL);
		robot.delay(2000);
		anIconToDeleteAnItemElements = driver.findElements(By.name(StringExternalization.ELEMENT_AN_ITEM_NAME));
		try 
		{
			
			logger.debug(StringExternalization.DEBUG_FOUND+anIconToDeleteAnItemElements.size()+StringExternalization.DEBUG_ELEMENT_NAMED_AN_ITEM);
			for(WebElement anItemElement: anIconToDeleteAnItemElements) 
			{
				String text = anItemElement.getText();
				logger.debug("Found *"+text+"* as text.");
				if (text.equals(StringExternalization.TEST_STRING_FOR_TEST_ITEM)) 
				{fail("Error: the test label has been found.");}
			}
			isItemDeleted = true;
			
		}
		catch(StaleElementReferenceException e) 
		{
			logger.error(StringExternalization.EXCEPTION_STALE_ELEMENT_REFERENCE
					+ "the elements named 'anItem' ");
			e.getMessage();
			e.printStackTrace();
		}
		
		//Issue with an undeleted test item
		robot.delay(3000);
		
		return isItemDeleted;
 	}

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
		logger.debug(String.format("%s %s","1.",StringExternalization.TEST_STEP_ITEM_CREATION));
		//Adding an item to the Uncategorized category created at startup
		TestsUtilCommon.sendKeys_withId(StringExternalization.ELEMENT_ID_FOR_CATEGORY_TO_SELECT_FIELD, StringExternalization.TEST_NAME_OF_DEFAULT_CATEGORY, driver);
		TestsUtilCommon.sendKeys_withId(StringExternalization.ELEMENT_ID_FOR_NEW_ITEM_FIELD, StringExternalization.TEST_STRING_FOR_TEST_ITEM, driver);
		TestsUtilCommon.click_withId(StringExternalization.ELEMENT_ID_FOR_ADD_ITEM_BUTTON, driver);
				
		//To avoid a StaleElementReferenceException 
		driver.get(StringExternalization.ANGULAR_SERVER_URL);
		robot.delay(2000);

		//2. Verification that the item is created and displayed	
		// TODO: test to re-work at a later time
		logger.debug(String.format("%s %s","2.",StringExternalization.TEST_STEP_VERIFICATION_OF_DISPLAYED_ITEM));
		boolean itemIsCreated = TestsUtilCommon.isTextFindableWithinElements_withName(StringExternalization.TEST_STRING_FOR_TEST_ITEM,StringExternalization.ELEMENT_AN_ITEM_NAME, driver, logger);
		boolean itemCreatedIsDisplayed = TestsUtilCommon.isTextFindableWithinElements_withCSSSelector( StringExternalization.TEST_STRING_FOR_TEST_ITEM, StringExternalization.ELEMENT_AN_ITEM_CLASS, driver, logger);
		if (!(itemIsCreated && itemCreatedIsDisplayed)) {fail(StringExternalization.TEST_ITEM_HIDING_DISPLAY_ITEM_NOT_FOUND);};

		//3. Verification that the item can be hidden.	
		logger.debug(String.format("%s %s","3.",StringExternalization.TEST_STEP_VERIFICATION_OF_HIDEABLE_ITEM));
		//Click on the category to hide the item. Only one category (Uncategorized) means only one element named foldUnfoldArea.
		driver.findElement(By.cssSelector(StringExternalization.ELEMENT_A_FOLDABLE_AREA)).click();
		
		//4. Verification that the item is hidden		
		itemCreatedIsDisplayed = TestsUtilCommon.isTextFindableWithinElements_withCSSSelector( StringExternalization.TEST_STRING_FOR_TEST_ITEM, StringExternalization.ELEMENT_AN_ITEM_CLASS, driver, logger);
		if (itemCreatedIsDisplayed) {fail(StringExternalization.TEST_ITEM_HIDING_DISPLAY_ITEM_NOT_FOUND);};
		if(itemCreatedIsDisplayed) 
		{fail(StringExternalization.TEST_ITEM_HIDING_DISPLAY_ITEM_NOT_HIDDEN);};
	
		//4. Verification that the item can be displayed 
		logger.debug(String.format("%s %s","4.", StringExternalization.TEST_STEP_VERIFICATION_OF_DISPLAYED_ITEM));
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
		
		TestsUtilCommon.click_onElements_withName(StringExternalization.ELEMENT_AN_ICON_TO_DELETE_AN_ITEM_NAME, driver, logger);
		List<WebElement> anIconToDeleteAnItemElements = driver.findElements(By.name(StringExternalization.ELEMENT_AN_ICON_TO_DELETE_AN_ITEM_NAME));
		if( !anIconToDeleteAnItemElements.isEmpty() ) fail("The test item was not deleted. "+anIconToDeleteAnItemElements.size()+" element has been found with the name anIconToDeleteAnItem");
		else {logger.debug(String.format("The test item has been deleted, the number is items found with the name %s is: %d ",StringExternalization.ELEMENT_AN_ICON_TO_DELETE_AN_ITEM_NAME,anIconToDeleteAnItemElements.size() ));}
		
		return isTestSuccessful;
 	}
 	
}
