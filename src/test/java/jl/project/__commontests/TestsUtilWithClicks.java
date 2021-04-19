package jl.project.__commontests;

import static org.testng.Assert.fail;

import java.awt.Robot;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.testng.log4testng.Logger;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import jl.project.StringExternalization;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;


public class TestsUtilWithClicks 
{	
 	
 	public static boolean createCategory_UsingClicks(Logger logger, WebDriver driver, Robot robot) 
	{
		logger.info(StringExternalization.TEST_START+StringExternalization.TEST_CATEGORY_CREATION);
    	boolean isCategoryFound = false;    	
    	
    	logger.info("1. Category creation using clicks");
    	driver.findElement(By.id(StringExternalization.ELEMENT_ID_NEW_CATEGORY_INPUT_FIELD)).sendKeys(StringExternalization.LABEL_TEST_CATEGORY);
    	robot.delay(1000);
    	driver.findElement(By.id(StringExternalization.ELEMENT_ID_ADD_CATEGORY_BUTTON)).click();
    	robot.delay(1000);
    	//The category has been added. The display of the existing categories is being refreshed.
    	logger.debug("At this point, the test category should have been created.");
    	   	
    	logger.info("2. Confirmation of category creation ");		
    	driver.get(StringExternalization.ANGULAR_SERVER_URL);
		robot.delay(2000);
    	List<WebElement> aCategoryElements = driver.findElements(By.name(StringExternalization.ELEMENT_NAME_A_CATEGORY));	  
    	
    	try 
    	{
    		logger.debug(StringExternalization.DEBUG_FOUND+aCategoryElements.size()+" aCategory elements");
    		if( aCategoryElements.isEmpty() ){fail(StringExternalization.EXCEPTION_APP_NOT_STARTED);}//for the case where the app wasn't started 
    		for (WebElement aCategoryElement : aCategoryElements) 
    		{
	    		String text = aCategoryElement.getText().trim();//A space is in front of all strings
				logger.debug("Found text: *"+text+"*");				
				if (text.contains(StringExternalization.LABEL_TEST_CATEGORY)) {isCategoryFound=true;break;}
				
			}
    	}
    	catch(StaleElementReferenceException e) 
    	{
    		logger.error(StringExternalization.EXCEPTION_STALE_ELEMENT_REFERENCE
    				+ "while going through the elements with the name aCategory.");
    		e.printStackTrace();
    	}	    	

    	// Giving time for the item to be displayed
    	// Recurrent failed deletion issues that did not occur with the slowest computer I have.
    	
		robot.delay(3000);    
		
		return isCategoryFound;
	}
 	
 	public static boolean deleteCategory_UsingClicks(Logger logger, WebDriver driver, Robot robot)
 	{
	 	logger.info(StringExternalization.TEST_START+StringExternalization.TEST_CATEGORY_DELETION);
		int testCategoryPositionIntheList = 0;
		int currentCategoryPosition = 0;
		boolean isCategoryFound = false;
		
		//1. Confirmation that the category was created; registration of its position in the list of elements named aCategory    	
		logger.info("1. Category existence confirmation");
		driver.get(StringExternalization.ANGULAR_SERVER_URL);
		robot.delay(2000);
		List<WebElement> aCategoryElements = driver.findElements(By.name(StringExternalization.ELEMENT_NAME_A_CATEGORY));	
		
		logger.debug(StringExternalization.DEBUG_FOUND+aCategoryElements.size()+" elements named aCategory");
		if( aCategoryElements.isEmpty() ){fail(StringExternalization.EXCEPTION_APP_NOT_STARTED);}//for the case where the app wasn't started 
		try 
		{    		
			for (WebElement aCategoryElement : aCategoryElements) 
			{
				currentCategoryPosition++;
	    		String text = aCategoryElement.getText().trim();
	    		logger.debug("Found text: *"+text+"*");
				if (text.equals(StringExternalization.LABEL_TEST_CATEGORY)) 
				{
					testCategoryPositionIntheList = currentCategoryPosition;
					logger.debug("Found the text:"+text+" in position: "+testCategoryPositionIntheList);					
					isCategoryFound=true;
					break;
				}
			}
		}
		catch(StaleElementReferenceException e) 
		{
			logger.error(StringExternalization.EXCEPTION_STALE_ELEMENT_REFERENCE
					+ "while going through the anItem elements.");
			e.getMessage();
			e.printStackTrace();
		}	 
		    	
		if(isCategoryFound ) 
		{
			logger.debug("The new category has been successfuly created.");    		
			//2. Deletion of the category created
				// finding the elements with the name StringExternalization.ELEMENT_NAME_AN_ICON_TO_DELETE_A_CATEGORY
			logger.info("2. Category deletion");
			driver.get(StringExternalization.ANGULAR_SERVER_URL);
			robot.delay(2000);
			List<WebElement> trashIconElementsInFrontOfCategories = driver.findElements(By.name(StringExternalization.ELEMENT_NAME_AN_ICON_TO_DELETE_A_CATEGORY));
			logger.debug(StringExternalization.DEBUG_FOUND+trashIconElementsInFrontOfCategories.size()+" elements with name anIconToDeleteACategory.");    		
			try 
			{
				currentCategoryPosition=0;    			
				for(WebElement trashCanIconElementInFrontOfCategory : trashIconElementsInFrontOfCategories) 
				{
					currentCategoryPosition++;    				    				
					if (currentCategoryPosition == testCategoryPositionIntheList) 
					{
						logger.debug("Clicking the trash can icon in position: "+currentCategoryPosition);
						trashCanIconElementInFrontOfCategory.click();
						//Issue with undeleted category
			    		robot.delay(2000);
						break;
					}
					else {logger.debug("Skipping this trash can icon.");}
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
			logger.info("3. Confirmation of category deletion");
			driver.get(StringExternalization.ANGULAR_SERVER_URL);
			robot.delay(2000);
			
			aCategoryElements = driver.findElements(By.name(StringExternalization.ELEMENT_NAME_A_CATEGORY));
			logger.debug(StringExternalization.DEBUG_FOUND+aCategoryElements.size()+" elements in aCategoryElements after deletion.");
			try 
			{
				for(WebElement aCategoryElement : aCategoryElements) 
				{
					String text = aCategoryElement.getText();
					logger.debug(text);
					if (text.equals(StringExternalization.LABEL_TEST_CATEGORY)) 
					{
						//if the created category can be found the test is failed    					
						fail(StringExternalization.DEBUG_FOUND+StringExternalization.LABEL_TEST_CATEGORY+" when the test category should have been deleted."
								+ "The test is failed.");
					}
					   				
				}
				//otherwise the test is successful
				isCategoryFound = false;
				
			}
			catch(StaleElementReferenceException e) 
			{
				logger.error(StringExternalization.EXCEPTION_STALE_ELEMENT_REFERENCE
						+ "while going through the elements related to a trash can icon before a category.");
				e.getMessage();
				e.printStackTrace();    			
			}    
			
		}
		
		else 
		{
			logger.error("The test category was not found.");
			fail("Test of category creation failed.");
		}		
		return isCategoryFound;
 	}

 	public static boolean createItem_UsingClicks(Logger logger, WebDriver driver, Robot robot)
 	{ 	 		
 		logger.info(StringExternalization.TEST_START+StringExternalization.TEST_ITEM_CREATION);	
		logger.info("1. "+StringExternalization.TEST_ITEM_CREATION);
		boolean isItemCreated=false;
		//Adding an item to the Uncategorized category created at startup
		driver.findElement(By.id(StringExternalization.ELEMENT_ID_CATEGORY_TO_SELECT_FIELD)).sendKeys("Uncategorized");
		driver.findElement(By.id(StringExternalization.ELEMENT_ID_ITEM_INPUT_NAME)).sendKeys(StringExternalization.LABEL_TEST_ITEM);
		driver.findElement(By.id(StringExternalization.ELEMENT_ID_ADD_ITEM_BUTTON)).click();
		//To avoid a StaleElementReferenceException
		driver.get(StringExternalization.ANGULAR_SERVER_URL);

		// Giving time for the item to be displayed
    	// Issue with undetected created item.
		
		robot.delay(3000);		
    					
		//Checking that the new item creation was successful		
		logger.info("2. Confirmation of item creation ");
		driver.get(StringExternalization.ANGULAR_SERVER_URL);
		robot.delay(2000);
		List<WebElement> anItemElements = driver.findElements(By.name(StringExternalization.ELEMENT_NAME_AN_ITEM));
		try 
		{
			logger.debug(StringExternalization.DEBUG_FOUND+anItemElements.size()+StringExternalization.DEBUG_ELEMENT_NAMED_AN_ITEM);
			for(WebElement anItemElement: anItemElements) 
			{
				String text = anItemElement.getText();				
				if (text.contains(StringExternalization.LABEL_TEST_ITEM))
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
 	
 	public static boolean deleteItem_UsingClicks(Logger logger, WebDriver driver, Robot robot)
 	{
 		boolean isItemDeleted=false;
 		logger.info(StringExternalization.TEST_START+StringExternalization.TEST_ITEM_DELETION);
		logger.info("1. "+StringExternalization.TEST_ITEM_DELETION);
		
		driver.get(StringExternalization.ANGULAR_SERVER_URL);
		robot.delay(2000);
		
		List<WebElement> anIconToDeleteAnItemElements = driver.findElements(By.name(StringExternalization.ELEMENT_NAME_AN_ICON_TO_DELETE_AN_ITEM));
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
		logger.info("2. Confirmation of deletion");
		driver.get(StringExternalization.ANGULAR_SERVER_URL);
		robot.delay(2000);
		anIconToDeleteAnItemElements = driver.findElements(By.name(StringExternalization.ELEMENT_NAME_AN_ITEM));
		try 
		{
			
			logger.debug(StringExternalization.DEBUG_FOUND+anIconToDeleteAnItemElements.size()+StringExternalization.DEBUG_ELEMENT_NAMED_AN_ITEM);
			for(WebElement anItemElement: anIconToDeleteAnItemElements) 
			{
				String text = anItemElement.getText();
				logger.debug("Found *"+text+"* as text.");
				if (text.equals(StringExternalization.LABEL_TEST_ITEM)) 
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

 	public static boolean hideAndDisplayItem_UsingClicks(Logger logger, WebDriver driver, Robot robot)
 	{ 		
 		boolean isTestSuccessful = false;
 		
 		logger.info(StringExternalization.TEST_START+StringExternalization.TEST_ITEM_HIDING_DISPLAY);
		boolean isTestItemLabelFound = false;		
		driver.get(StringExternalization.ANGULAR_SERVER_URL);
		driver.navigate().refresh();		
		
		//1. Creation of an item. By default the item is displayed
		logger.info("1. "+StringExternalization.TEST_ITEM_CREATION);
		//Adding an item to the Uncategorized category created at startup
		driver.findElement(By.id(StringExternalization.ELEMENT_ID_CATEGORY_TO_SELECT_FIELD)).sendKeys("Uncategorized");
		driver.findElement(By.id(StringExternalization.ELEMENT_ID_ITEM_INPUT_NAME)).sendKeys(StringExternalization.LABEL_TEST_ITEM);
		driver.findElement(By.id(StringExternalization.ELEMENT_ID_ADD_ITEM_BUTTON)).click();
		
				
		//Checking that the new item creation was successful	
		//To avoid a StaleElementReferenceException 
		driver.get(StringExternalization.ANGULAR_SERVER_URL);
		robot.delay(2000);
		List<WebElement> anItemElements = driver.findElements(By.name(StringExternalization.ELEMENT_NAME_AN_ITEM));
		try 
		{
			for(WebElement anItemElement: anItemElements) 
			{
				String text = anItemElement.getText();				
				if (text.contains(StringExternalization.LABEL_TEST_ITEM)) {logger.debug(StringExternalization.DEBUG_FOUND+text+" as text."); isTestItemLabelFound=true;}
				if(isTestItemLabelFound == false) {fail("The test label was not found. The test of item creation failed.");}
			}
			
			isTestSuccessful = true;
			
		}
		catch(StaleElementReferenceException e) 
		{
			logger.error(StringExternalization.EXCEPTION_STALE_ELEMENT_REFERENCE
					+ "the elements named 'anItem' after creation of the element.");
			e.getMessage();
			e.printStackTrace();
		}		
		
		
		//2. Verification that the item is displayed
		//a. code to get a screenshot from the browser
		logger.info("2. Verification that the item is displayed");
		File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);	
		File screenshotFile_copy = new File(StringExternalization.TESSERACT_SCREENSHOT_PATH_NEW_ITEM);
		try {
			FileUtils.copyFile(screenshotFile, screenshotFile_copy);
		} catch (IOException e) {
			logger.error(StringExternalization.EXCEPTION_IO+"while copy and saving the screenshot");
			e.printStackTrace();
		}
		//b. code to extract the text from the picture
		Tesseract ocr = new Tesseract();
		String result = null;
		//https://github.com/tesseract-ocr/tessdata
		ocr.setDatapath(StringExternalization.TESSERACT_TESSDATA);
		ocr.setLanguage(StringExternalization.TESSERACT_LANGUAGE);
		ocr.setTessVariable(StringExternalization.TESSERACT_DPI_KEY,StringExternalization.TESSERACT_DPI_VALUE);
		try {
			result = ocr.doOCR(screenshotFile_copy);
		} catch (TesseractException e) {
			logger.error(StringExternalization.EXCEPTION_TESSERACT);
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		if(result != null && result.contains(StringExternalization.LABEL_TEST_ITEM)) 
		{
			 
			logger.debug(StringExternalization.TESSERACT_FOUND_TEST_ITEM);
		}
		else{fail(StringExternalization.TESSERACT_NOT_FOUND_TEST_ITEM+result);};
	
		//3. Hiding of the item		
		logger.info("3. Verification that the item can be hidden.");
		//Click on the category to hide the item. Only one category (Uncategorized) means only one element named foldUnfoldArea.
		driver.findElement(By.cssSelector(".foldUnfoldClickArea")).click();
		//4. Verification that the item is hidden
		screenshotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		File screenshot_AfterClickToHide_copy = new File("./screenshots/AfterClickToHideScreenshot.png");
		try {
			FileUtils.copyFile(screenshotFile, screenshot_AfterClickToHide_copy);
			result = ocr.doOCR(screenshot_AfterClickToHide_copy);
			if(!result.contains(StringExternalization.LABEL_TEST_ITEM)) 
			{ 
				
				logger.debug("Success: the label couldn't be found in the screenshot: "+result);
			}
			else 
			{fail("The label was found on the screenshot when the item should have been hidden: "+result);
			}
			
		} catch (IOException e) {
			logger.error("An IOExeption occured while copying the screenshot taken after the click"				
					+ "(Hiding of the item).");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		 catch (TesseractException e) {
			logger.error(StringExternalization.EXCEPTION_TESSERACT
					+ "(Hiding of the item)");			
			logger.error(e.getMessage());
			e.printStackTrace();
		}		
		
		//4. Verification that the item can be displayed 
		logger.info("4. Verification that the item can be displayed");
		driver.findElement(By.cssSelector(".foldUnfoldClickArea")).click();
		
		screenshotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		File screenshot_AfterClickToDisplay_copy = new File("./screenshots/AfterClickToDisplayScreenshot.png");
		
		try {			
			FileUtils.copyFile(screenshotFile, screenshot_AfterClickToDisplay_copy);
			ocr.setDatapath(StringExternalization.TESSERACT_TESSDATA);
			ocr.setLanguage(StringExternalization.TESSERACT_LANGUAGE);
			ocr.setTessVariable(StringExternalization.TESSERACT_DPI_KEY,StringExternalization.TESSERACT_DPI_VALUE);
			result = ocr.doOCR(screenshot_AfterClickToDisplay_copy);
			
			if(result.contains(StringExternalization.LABEL_TEST_ITEM)) 
			{
				logger.debug("Sucess: the label was found after clicking to display the item: "+result);
			}
			else {fail("The label: "+StringExternalization.LABEL_TEST_ITEM+" could not be in the ocr result: "+result
					+" when the item should have been displayed.");}
		} catch (IOException e) {
			logger.error(StringExternalization.EXCEPTION_IO+"while copying the screenshot taken after the click"
					+ "(Display of the item)");
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (TesseractException e) {
			logger.error(StringExternalization.EXCEPTION_TESSERACT
					+ "(Display of the item)");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
		
		
		//5. Suppressing the item to go on with the test suite
		logger.info("5. Deletion of the test item");
		driver.get(StringExternalization.ANGULAR_SERVER_URL);		
		robot.delay(2000);
		
		List<WebElement> anIconToDeleteAnItemElements = driver.findElements(By.name(StringExternalization.ELEMENT_NAME_AN_ICON_TO_DELETE_AN_ITEM));
			for(WebElement anIconToDeleteAnItemElement: anIconToDeleteAnItemElements) {//only one item in the test
				anIconToDeleteAnItemElement.click();
				robot.delay(2000);
			}
			
		logger.info("6. Testing the deletion of the test item");
		driver.get(StringExternalization.ANGULAR_SERVER_URL);
		robot.delay(2000);
		
		anIconToDeleteAnItemElements = driver.findElements(By.name(StringExternalization.ELEMENT_NAME_AN_ICON_TO_DELETE_AN_ITEM));
		if( !anIconToDeleteAnItemElements.isEmpty() ) fail("The test item was not deleted. "+anIconToDeleteAnItemElements.size()+" element has been found with the name anIconToDeleteAnItem");
		else {logger.debug(String.format("The test item has been deleted, the number is items found with thwe name %s is: %d ",StringExternalization.ELEMENT_NAME_AN_ICON_TO_DELETE_AN_ITEM,anIconToDeleteAnItemElements.size() ));}
		
		return isTestSuccessful;
 	}
 	
}
