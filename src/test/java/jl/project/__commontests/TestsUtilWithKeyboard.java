package jl.project.__commontests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.fail;

import java.awt.Robot;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;

import jl.project.StringExternalization;


// Code with likely immaturities.

public class TestsUtilWithKeyboard 
{
	/**
	 * Tests the CREATION and DELETION of a CATEGORY using KEYBOARD KEYS ONLY
	 * @param logger - the logger instance of User Requirement Test class
	 * @param driver - the webdriver instance used for the test
	 * @param robot - the instance of the Robot class used to add delays
	 * @param keyUsedToSubmitData - the key used to submit data ( ENTER or SPACE )
	 * @param commentAddOn - a field used to add a comment concerning the use of the Enter or Space key to enter data
	 * @return true if the test is successful, false otherwise
	 */
	public static boolean createAndDeleteCategory_UsingTheKeyboard(Logger logger, WebDriver driver, Robot robot, Keys keyUsedToSubmitData, String commentAddOn)
		{
			boolean isTestSuccessful = false;
			
			logger.info(String.format("%s %s %s %s",
				StringExternalization.COMMENT_ENTERING_TEST_FOR,
				StringExternalization.TEST_CATEGORY_CREATION_AND_DELETION,
				StringExternalization.TEST_WITH_KEYBOARD,
				commentAddOn));		
			
			// Category creation
			boolean isCategoryCreated = false;					
			logger.info(String.format("%s %s %s","1.", 
				StringExternalization.TEST_CATEGORY_CREATION, 
				StringExternalization.TEST_WITH_KEYBOARD,
				commentAddOn));		
			//Tabbing until finding the input field to add the new category label	

			Actions  action = new Actions(driver);			
			TestsUtilCommon.tabTo_NameOfTheCategoryToAdd(action, robot);
			robot.delay(1000);
			action.sendKeys(StringExternalization.TEST_STRING_FOR_CREATED_CATEGORY).build().perform(); // adding the category label
			robot.delay(1000);
			action.sendKeys(Keys.TAB).build().perform();// tabbing to the button to add the category
			robot.delay(1000);
			action.sendKeys(Keys.ENTER).build().perform();// pressing the enter key to add the category			
			robot.delay(1000);				
		
			//Verifying that the category has been created		
			logger.info(String.format("2. %s", StringExternalization.TEST_CATEGORY_CREATION_CONFIRMATION));
			driver.get(StringExternalization.ANGULAR_SERVER_URL);
			robot.delay(2000);

			isCategoryCreated = TestsUtilCommon.isTextFindableWithinElements_withName( StringExternalization.TEST_STRING_FOR_CREATED_CATEGORY, StringExternalization.ELEMENT_NAME_A_CATEGORY,driver, logger);
			if(!isCategoryCreated) {fail(StringExternalization.TEST_FAILURE_CATEGORY_NOT_FOUND);	}

			// List<WebElement> aCategoryElements = driver.findElements(By.name(StringExternalization.ELEMENT_NAME_A_CATEGORY));
			// logger.debug(StringExternalization.DEBUG_FOUND+aCategoryElements.size()+" elements named aCategory");	
			// for(WebElement aCategoryElement: aCategoryElements ) {
			// 	String text = aCategoryElement.getText();
			// 	if(text.contains(StringExternalization.TEST_STRING_FOR_CREATED_CATEGORY)) 
			// 	{
			// 		logger.debug("The text *"+text+"* was found. The category was successfully "
			// 				+ "created using the keyboard only. ");
			// 		isCategoryCreated=true;
			// 	}
				
			// 	assertThat(isCategoryCreated).isTrue();
			// }
					
			
			logger.info("3. Deletion of a category with the keyboard only.");
			driver.get(StringExternalization.ANGULAR_SERVER_URL);
			robot.delay(2000);
			//Assuming the category location
			boolean isCategoryFound;
			
			TestsUtilCommon.tabTo_AddTheCategoryButton(action, robot);
			robot.delay(1000);
			action.sendKeys(Keys.TAB).build().perform();//category selection
			robot.delay(1000);
			action.sendKeys(Keys.TAB).build().perform();//new item text
			robot.delay(1000);
			action.sendKeys(Keys.TAB).build().perform();//submit item button
			robot.delay(1000);	
			action.sendKeys(Keys.TAB).build().perform();//hyperlink
			robot.delay(1000);
			action.sendKeys(Keys.TAB).build().perform();//trash can icon: category "Selenium test category"
			robot.delay(1000);
			action.sendKeys(Keys.ENTER).build().perform();//Click to delete the test category
			robot.delay(2000);
			
		
			//Verifying that the category has been deleted
			logger.info("4. Confirming that the category has been deleted.");
			driver.get(StringExternalization.ANGULAR_SERVER_URL);
			robot.delay(2000);
			
			List<WebElement> aCategoryElements = driver.findElements(By.name(StringExternalization.ELEMENT_NAME_A_CATEGORY));
			logger.debug(StringExternalization.DEBUG_FOUND+aCategoryElements.size()+" elements in aCategoryElements after deletion.");
			try {
				for(WebElement aCategoryElement : aCategoryElements) 
				{
					String text = aCategoryElement.getText();
					logger.debug(text);
					if (text.contains(StringExternalization.TEST_STRING_FOR_CREATED_CATEGORY)) 
					{
						//if the created category can be found the test is failed    					
						fail(StringExternalization.DEBUG_FOUND+StringExternalization.TEST_STRING_FOR_CREATED_CATEGORY+" when the test category should have been deleted."
								+ "The test is failed.");
					}
					
					isTestSuccessful  = true;
					   				
				}
				//otherwise the test is successful
				isCategoryFound = false;
				
				assertThat(isCategoryFound).isFalse();
			}
			catch(StaleElementReferenceException e) 
			{
				logger.error(StringExternalization.EXCEPTION_STALE_ELEMENT_REFERENCE
						+ "while going through the elements related to a trash can icon before a category.");
				logger.error(e.getMessage());
				e.printStackTrace();    			
			}  
			
			return isTestSuccessful;
		}
	
	/**
	 * Tests the CREATION and DELETION of an ITEM using KEYBOARD KEYS ONLY
	 * @param logger - the logger instance of User Requirement Test class
	 * @param driver - the webdriver instance used for the test
	 * @param robot - the instance of the Robot class used to add delays
	 * @param keyUsedToSubmitData - the key used to submit data ( ENTER or SPACE )
	 * @param commentAddOn - a field used to add a comment concerning the use of the Enter or Space key to enter data
	 * @return true if the test is successful, false otherwise
	 */
	public static boolean createAndDeleteItem_UsingTheKeyboard(Logger logger, WebDriver driver, Robot robot, Keys keyUsedToSubmitData, String commentAddOn)
	{
		boolean isTheTestSuccessful = false;
		
		logger.info(StringExternalization.COMMENT_ENTERING_TEST_FOR
				+StringExternalization.TEST_ITEM_CREATION_DELETION_WITH_KEYBOARD
				+StringExternalization.TEST_KEYBOARD_ENTER_KEY);
		logger.info("1. "+StringExternalization.TEST_STEP_ITEM_CREATION);
		
		Actions action;
				
		action = new Actions(driver);		
		TestsUtilCommon.tabTo_AddTheCategoryButton(action, robot);
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//category selection
		action.sendKeys("Uncategorized").build().perform();
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//new item text
		robot.delay(1000);
		action.sendKeys(StringExternalization.TEST_STRING_FOR_TEST_ITEM).build().perform();
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//submit item button
		robot.delay(2000);
		action.sendKeys(Keys.ENTER).build().perform();
		robot.delay(2000);
			
		
		
		logger.info("2. Confirmation of creation.");
		//Checking that the new item creation was successful		
		driver.get(StringExternalization.ANGULAR_SERVER_URL);
		robot.delay(2000);
		List<WebElement> anItemElements = driver.findElements(By.name(StringExternalization.ELEMENT_AN_ITEM_NAME));
		
		boolean isItemCreated=false;
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
			logger.error(e.getMessage());
			e.printStackTrace();
		}		
		assertThat(isItemCreated).isTrue();
		
		logger.info("3. Deletion of the test item using the keyboard only.");
		
		
		action = new Actions(driver);		
		TestsUtilCommon.tabTo_AddTheCategoryButton(action, robot);
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//category selection
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//new item text
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//submit item button
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//trash can icon: category "Uncategorized"
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//plus sign icon: category "Uncategorized"
		robot.delay(1000);		
		action.sendKeys(Keys.TAB).build().perform();//Category "Uncategorized"
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//Extra tab 
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//trash can icon: category "Selenium test category"
		robot.delay(1000);
		action.sendKeys(Keys.ENTER).build().perform(); //Click to delete the test category
			
		
		
		logger.info("4. Confirmation of deletion");
		driver.get(StringExternalization.ANGULAR_SERVER_URL);
		robot.delay(2000);
		
		List<WebElement> anIconToDeleteAnItemElements = driver.findElements(By.name(StringExternalization.ELEMENT_AN_ITEM_NAME));
		try 
		{
			
			logger.debug(StringExternalization.DEBUG_FOUND+anIconToDeleteAnItemElements.size()+StringExternalization.DEBUG_ELEMENT_NAMED_AN_ITEM);
			for(WebElement anItemElement: anIconToDeleteAnItemElements) 
			{
				String text = anItemElement.getText();
				logger.debug("Found *"+text+"* as text.");
				if (text.equals(StringExternalization.TEST_STRING_FOR_TEST_ITEM)) 
				{
					fail("Error: the test item label has been found. The test is failed.");
				}				
			}
			isTheTestSuccessful = true;
			
		}
		catch(StaleElementReferenceException e) 
		{
			logger.error(StringExternalization.EXCEPTION_STALE_ELEMENT_REFERENCE
					+ "the elements named 'anItem' ");
			e.getMessage();
			e.printStackTrace();
		}
		return isTheTestSuccessful;
	}
	
	/**
	 * Tests the ability to HIDE and DISPLAY an ITEM using CLICKS
	 * @param logger - the logger instance of User Requirement Test class
	 * @param driver - the webdriver instance used for the test
	 * @param robot - the instance of the Robot class used to add delays
	 * @param keyUsedToSubmitData - the key used to submit data ( ENTER or SPACE )
	 * @param commentAddOn - a field used to add a comment concerning the use of the Enter or Space key to enter data
	 * @return true if the test is successful, false otherwise
	 */
	public static boolean HideAndDisplayItems_UsingTheKeyboard(Logger logger, WebDriver driver, Robot robot, Keys keyUsedToSubmitData, String commentAddOn)
	{
		boolean isTheTestSuccessful = false;
		logger.info(StringExternalization.COMMENT_ENTERING_TEST_FOR
				+StringExternalization.TEST_ITEM_HIDING_DISPLAY
				+StringExternalization.TEST_KEYBOARD_ENTER_KEY);
		driver.get(StringExternalization.ANGULAR_SERVER_URL);
		
		
		Actions action;
	
		action = new Actions(driver);		
		TestsUtilCommon.tabTo_AddTheCategoryButton(action, robot);
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//category selection
		action.sendKeys("Uncategorized").build().perform();
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//new item text
		robot.delay(1000);
		action.sendKeys(StringExternalization.TEST_STRING_FOR_TEST_ITEM).build().perform();
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//submit item button
		robot.delay(1000);			
		action.sendKeys(Keys.ENTER).build().perform();
		robot.delay(5000);
						
		
		logger.info("2. Confirmation of creation.");
		//Checking that the new item creation was successful	
		driver.get(StringExternalization.ANGULAR_SERVER_URL);
		robot.delay(2000);
		List<WebElement> anItemElements = driver.findElements(By.name(StringExternalization.ELEMENT_AN_ITEM_NAME));
		boolean isItemCreated=false;
		try 
		{
			logger.debug(StringExternalization.DEBUG_FOUND+anItemElements.size()+StringExternalization.DEBUG_ELEMENT_NAMED_AN_ITEM);
			for(WebElement anItemElement: anItemElements) 
			{
				String text = anItemElement.getText();				
				if (text.contains(StringExternalization.TEST_STRING_FOR_TEST_ITEM)) 
				{
					logger.debug("Success. Found "+text+" as text.");
					isItemCreated = true;
				}
			}
			assertThat(isItemCreated).isTrue();
			
		}
		catch(StaleElementReferenceException e) 
		{
			logger.error(StringExternalization.EXCEPTION_STALE_ELEMENT_REFERENCE
					+ "the elements named 'anItem' after creation of the element.");
			logger.error(e.getMessage());
			e.printStackTrace();
		}		
	
		
		logger.info("3. Verification that the item is displayed");
		
		//clicking to hide the item		
		logger.info("4. Verification that the item can be hidden.");
		//Using the keyboard to hide the item. Only one category (Uncategorized) means only one element named foldUnfoldArea.
		driver.get(StringExternalization.ANGULAR_SERVER_URL);
		
		
		action = new Actions(driver);		
		TestsUtilCommon.tabTo_AddTheCategoryButton(action, robot);
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//category selection
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//new item text
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//submit item button
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//Extra tab 
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//trash can icon: category "Uncategorized"
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//plus sign icon: category "Uncategorized"
		robot.delay(1000);
		//Click to hide the item
		action.sendKeys(Keys.ENTER).build().perform();//Click to hide the item
		robot.delay(5000);
		
		// TODO : To use .isDisplayed() method

		
		//Verification that the item can be displayed by clicking a second time.
		logger.info("5. Verification that the item can be displayed");
		
		robot.delay(1000);
		action = new Actions(driver);
		action.sendKeys(Keys.ENTER).build().perform();//Click to hide the item
		
		// TODO : To use .isDisplayed() method
		
		//Cleaning up for a potential next test. Using a click for the task
		logger.info("6. Suppression of the item.");
		driver.get(StringExternalization.ANGULAR_SERVER_URL);
		robot.delay(2000);
		List<WebElement> anIconToDeleteAnItemElements = driver.findElements(By.name(StringExternalization.ELEMENT_AN_ICON_TO_DELETE_AN_ITEM_NAME));
		for(WebElement anIconToDeleteAnItemElement: anIconToDeleteAnItemElements) 
		{//only one item in the test
			anIconToDeleteAnItemElement.click();
			robot.delay(2000);
		}
		
		logger.debug("7. Testing the deletion of the test item");
		driver.get(StringExternalization.ANGULAR_SERVER_URL);
		robot.delay(2000);
		
		anIconToDeleteAnItemElements = driver.findElements(By.name(StringExternalization.ELEMENT_AN_ICON_TO_DELETE_AN_ITEM_NAME));
		if(!anIconToDeleteAnItemElements.isEmpty()) { fail("The test item was not deleted. "+anIconToDeleteAnItemElements.size()+" element has been found with the name anIconToDeleteAnItem");}
		else {logger.debug("Page cleaned from test item.");}
		
		isTheTestSuccessful = true;
		return isTheTestSuccessful;
	}

}
