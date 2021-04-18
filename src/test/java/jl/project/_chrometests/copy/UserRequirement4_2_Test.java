package jl.project._chrometests.copy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.fail;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.testng.log4testng.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import org.testng.annotations.Test;

import jl.project.StringExternalization;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/**
 * @author
 * Class testing the user requirement of physical impairment web accessibility 
 * using the keyboard only - Space key used.
 */
public class UserRequirement4_2_Test {
	Logger logger = Logger.getLogger(jl.project._chrometests.UserRequirement4_2_Test.class);
	/* Note: delaying or not the sending of the keys impact the success of the tests */
	WebDriver driver;	
	Robot robot;
	
	@BeforeClass
	public void setup() {
		System.setProperty(StringExternalization.WEBDRIVER_CHROME_KEY, 
				StringExternalization.WEBDRIVERS_FOLDER+StringExternalization.WEBDRIVER_CHROME_VALUE);

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setBrowserName(StringExternalization.BROWSER_NAME_CHROME);
		
		if(StringExternalization.GRID_NOT_USED) {driver = new ChromeDriver();}
		else 
		{	
			try {
				driver = new RemoteWebDriver(new URL(StringExternalization.SELENIUM_HUB), capabilities);
			} catch (MalformedURLException e) {
				logger.error(StringExternalization.EXCEPTION_MALFORMEDURL);
				logger.error(e.getMessage());
				e.printStackTrace();
			}			
		}
			
		driver.manage().window().maximize();
		
		try 
		{
			robot = new Robot();
		} 
		catch (AWTException e) 
		{
			logger.debug(StringExternalization.EXCEPTION_AWT);
			e.printStackTrace();
		}
	
	}
	
	@BeforeMethod
	public void navigate() {
		driver.get(StringExternalization.ANGULAR_SERVER_URL);
		
	}
	
	
	
	@Test(groups = {"creation_deletion_Chrome_2"})		
	public void createAndDeleteACategoryWithKeyboardOnly_SpaceKey() {
		logger.info(StringExternalization.TEST_START
				+StringExternalization.TEST_CATEGORY_CREATION_DELETION_WITH_KEYBOARD
				+StringExternalization.TEST_KEYBOARD_SPACE_KEY);
		boolean isCategoryCreated = false;		
		
		logger.info("1. Creation of a category with the keyboard only.");		
		//Tabbing until finding the input field to add the new category label
		driver.get(StringExternalization.ANGULAR_SERVER_URL);		
		
		Actions  action = new Actions(driver);
	
		action.sendKeys(Keys.TAB).build().perform();//nav bar
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//nav bar
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//nav bar
		robot.delay(1000);	
		action.sendKeys(Keys.TAB).build().perform();//category input
		robot.delay(1000);
		action.sendKeys(StringExternalization.LABEL_TEST_CATEGORY).build().perform();
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();
		robot.delay(1000);
		action.sendKeys(Keys.SPACE).build().perform();			
		robot.delay(1000);
		
	
	
		//Verifying that the category has been created		
		logger.info("2. Confirming creation of the category");
		driver.get(StringExternalization.ANGULAR_SERVER_URL);
		robot.delay(2000);
		List<WebElement> aCategoryElements = driver.findElements(By.name(StringExternalization.ELEMENT_NAME_A_CATEGORY));
		logger.debug(StringExternalization.DEBUG_FOUND+aCategoryElements.size()+" elements named aCategory");	
		for(WebElement aCategoryElement: aCategoryElements ) {
			String text = aCategoryElement.getText();
			if(text.contains(StringExternalization.LABEL_TEST_CATEGORY)) 
			{
				logger.debug("The text *"+text+"* was found. The category was successfully "
						+ "created using the keyboard only. ");
				isCategoryCreated=true;
			}
		};		
		
		assertThat(isCategoryCreated).isTrue();
		
				
		logger.info("3. Deletion of a category with the keyboard only.");
		//Assuming the category location
		boolean isCategoryFound;
	
		action.sendKeys(Keys.TAB).build().perform();//nav bar
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//nav bar
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//nav bar
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//new category text
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//submit category button
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
		action.sendKeys(Keys.SPACE).build().perform();//Click to delete the test category
		robot.delay(2000);
	
		//Verifying that the category has been deleted
		logger.info("4. Confirming that the category has been deleted.");
		driver.get(StringExternalization.ANGULAR_SERVER_URL);
		robot.delay(2000);
		
		aCategoryElements = driver.findElements(By.name(StringExternalization.ELEMENT_NAME_A_CATEGORY));
		logger.debug(StringExternalization.DEBUG_FOUND+aCategoryElements.size()+" elements in aCategoryElements after deletion.");
		try {
			for(WebElement aCategoryElement : aCategoryElements) {
				String text = aCategoryElement.getText();
				logger.debug(text);
				if (text.contains(StringExternalization.LABEL_TEST_CATEGORY)) {
					//if the created category can be found the test is failed    					
					fail(StringExternalization.DEBUG_FOUND+StringExternalization.LABEL_TEST_CATEGORY+" when the test category should have been deleted."
							+ "The test is failed.");
				}
				   				
			}
			//otherwise the test is successful
			isCategoryFound = false;
			
			assertThat(isCategoryFound).isFalse();
		}
		catch(StaleElementReferenceException e) {
			logger.error(StringExternalization.EXCEPTION_STALE_ELEMENT_REFERENCE
					+ "while going through the elements related to a trash can icon before a category.");
			logger.error(e.getMessage());
			e.printStackTrace();    			
		}  
			
		
		
	}
	
		
	@Test(groups = {"creation_deletion_Chrome_2"})					
	public void createAndDeleteItemWithKeyboardOnly_SpaceKey() {
		logger.info(StringExternalization.TEST_START
				+StringExternalization.TEST_ITEM_CREATION_DELETION_WITH_KEYBOARD
				+StringExternalization.TEST_KEYBOARD_SPACE_KEY);
		
		Actions action;
		
		logger.info("1. "+StringExternalization.TEST_ITEM_CREATION);
				
		action = new Actions(driver);
		action.sendKeys(Keys.TAB).build().perform();//nav bar
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//nav bar
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//nav bar
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//new category text
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//submit category button
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//category selection
		action.sendKeys("Uncategorized").build().perform();
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//new item text
		robot.delay(1000);
		action.sendKeys(StringExternalization.LABEL_TEST_ITEM).build().perform();
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//submit item button
		robot.delay(2000);			
		action.sendKeys(Keys.SPACE).build().perform();
		robot.delay(2000);
				
		
		logger.info("2. Confirmation of creation.");
		//Checking that the new item creation was successful	
		driver.get(StringExternalization.ANGULAR_SERVER_URL);
		robot.delay(2000);
		List<WebElement> anItemElements = driver.findElements(By.name(StringExternalization.ELEMENT_NAME_AN_ITEM));
		boolean isItemCreated=false;
		try {
			logger.debug(StringExternalization.DEBUG_FOUND+anItemElements.size()+StringExternalization.DEBUG_ELEMENT_NAMED_AN_ITEM);
			for(WebElement anItemElement: anItemElements) {
				String text = anItemElement.getText();				
				if (text.contains(StringExternalization.LABEL_TEST_ITEM)) {
					logger.debug(StringExternalization.DEBUG_FOUND+text+" as text.");
					isItemCreated = true;
					}
			}
			
		}
		catch(StaleElementReferenceException e) {
			logger.error(StringExternalization.EXCEPTION_STALE_ELEMENT_REFERENCE
					+ "the elements named 'anItem' after creation of the element.");
			logger.error(e.getMessage());
			e.printStackTrace();
		}		
		assertThat(isItemCreated).isTrue();
		
		logger.info("3. Deletion of the test item using the keyboard only.");
		
		
		action = new Actions(driver);
		action.sendKeys(Keys.TAB).build().perform();//nav bar
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//nav bar
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//nav bar
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//new category text
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//submit category button
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
		action.sendKeys(Keys.TAB).build().perform();//hyperlink
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//trash can icon: category "Selenium test category"
		robot.delay(1000);
		action.sendKeys(Keys.SPACE).build().perform(); //Click to delete the test category
		robot.delay(2000);
		
		logger.info("4. Confirmation of deletion");
		driver.get(StringExternalization.ANGULAR_SERVER_URL);
		robot.delay(2000);
		
		List<WebElement> anIconToDeleteAnItemElements = driver.findElements(By.name(StringExternalization.ELEMENT_NAME_AN_ITEM));
		try {
			
			logger.debug(StringExternalization.DEBUG_FOUND+anIconToDeleteAnItemElements.size()+StringExternalization.DEBUG_ELEMENT_NAMED_AN_ITEM);
			for(WebElement anItemElement: anIconToDeleteAnItemElements) {
				String text = anItemElement.getText();
				logger.debug("Found *"+text+"* as text.");
				if (text.equals(StringExternalization.LABEL_TEST_ITEM)) 
				{
					fail("Error: the test item label has been found. The test is failed.");
				}
				
			}
			
		}
		catch(StaleElementReferenceException e) 
		{
			logger.error(StringExternalization.EXCEPTION_STALE_ELEMENT_REFERENCE
					+ "the elements named 'anItem' ");
			e.getMessage();
			e.printStackTrace();
		}
	}
	
	
	
	@Test(dependsOnGroups = {"creation_deletion_Chrome_2"})		
	public void HideAndDisplayItemsWithKeyboardOnly_SpaceKey() 
	{
		logger.info(StringExternalization.TEST_START
				+StringExternalization.TEST_ITEM_HIDING_DISPLAY_WITH_KEYBOARD
				+StringExternalization.TEST_KEYBOARD_SPACE_KEY);
		driver.get(StringExternalization.ANGULAR_SERVER_URL);
		
		Actions action;
		
		logger.info("1. "+StringExternalization.TEST_ITEM_CREATION);
		
		action = new Actions(driver);
		action.sendKeys(Keys.TAB).build().perform();//nav bar
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//nav bar
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//nav bar
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//new category text
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//submit category button
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//category selection
		action.sendKeys("Uncategorized").build().perform();
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//new item text
		robot.delay(1000);
		action.sendKeys(StringExternalization.LABEL_TEST_ITEM).build().perform();
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//submit item button
		robot.delay(1000);			
		action.sendKeys(Keys.SPACE).build().perform();
		robot.delay(5000);			
		
		
		logger.info("2. Confirmation of creation.");
		//Checking that the new item creation was successful	
		driver.get(StringExternalization.ANGULAR_SERVER_URL);
		robot.delay(2000);
		List<WebElement> anItemElements = driver.findElements(By.name(StringExternalization.ELEMENT_NAME_AN_ITEM));
		boolean isItemCreated=false;
		try {
			logger.debug(StringExternalization.DEBUG_FOUND+anItemElements.size()+StringExternalization.DEBUG_ELEMENT_NAMED_AN_ITEM);
			if(anItemElements.size()==0) {fail("Failure of element creation test during the hiding/diplay test.");}
			for(WebElement anItemElement: anItemElements) {
				String text = anItemElement.getText();				
				if (text.contains(StringExternalization.LABEL_TEST_ITEM)) {
					logger.debug("Success. Found "+text+" as text.");
					isItemCreated = true;
					}
			}
			assertThat(isItemCreated).isTrue();
			
		}
		catch(StaleElementReferenceException e) {
			logger.error(StringExternalization.EXCEPTION_STALE_ELEMENT_REFERENCE
					+ "the elements named 'anItem' after creation of the element.");
			logger.error(e.getMessage());
			e.printStackTrace();
		}		

		
		logger.info("3. Verification that the item is displayed");
		File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);	
		File screenshotFile_copy = new File(StringExternalization.TESSERACT_SCREENSHOT_PATH_NEW_ITEM);
		try {
			FileUtils.copyFile(screenshotFile, screenshotFile_copy);
		} catch (IOException e) {
			logger.error(StringExternalization.EXCEPTION_IO+"while copy and saving the screenshot");
			e.printStackTrace();
		}
		// code to extract the text from the picture
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
		
		//clicking to hide the item		
		logger.info("4. Verification that the item can be hidden.");
		//Using the keyboard to hide the item. Only one category (Uncategorized) means only one element named foldUnfoldArea.
		driver.get(StringExternalization.ANGULAR_SERVER_URL);
		
		
		action = new Actions(driver);
		action.sendKeys(Keys.TAB).build().perform();//nav bar
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//nav bar
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//nav bar
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//new category text
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//submit category button
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//category selection
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//new item text
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//submit item button
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//hyperlink
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//trash can icon: category "Uncategorized"
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();//plus sign icon: category "Uncategorized"
		robot.delay(1000);			
		//Click to hide the item
		action.sendKeys(Keys.SPACE).build().perform();//Click to hide the item
		robot.delay(5000);
		
		// Verification that the item is hidden
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
		
		//Verification that the item can be displayed by clicking a second time.
		logger.info("5. Verification that the item can be displayed");
		
		action = new Actions(driver);
		robot.delay(1000);
		action.sendKeys(Keys.SPACE).build().perform();//Click to hide the item
		//Click to hide the item
		
		
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
		//Cleaning up for a potential next test. Using a click for the task
		logger.info("6. Suppression of the item.");
		driver.get(StringExternalization.ANGULAR_SERVER_URL);
		robot.delay(2000);
		List<WebElement> anIconToDeleteAnItemElements = driver.findElements(By.name(StringExternalization.ELEMENT_NAME_AN_ICON_TO_DELETE_AN_ITEM));
		for(WebElement anIconToDeleteAnItemElement: anIconToDeleteAnItemElements) {//only one item in the test
			anIconToDeleteAnItemElement.click();
			robot.delay(2000);
		}
		
		logger.debug("7. Testing the deletion of the test item");
		driver.get(StringExternalization.ANGULAR_SERVER_URL);
		robot.delay(2000);
		
		anIconToDeleteAnItemElements = driver.findElements(By.name(StringExternalization.ELEMENT_NAME_AN_ICON_TO_DELETE_AN_ITEM));
		if(!anIconToDeleteAnItemElements.isEmpty()) { fail("The test item was not deleted. "+anIconToDeleteAnItemElements.size()+" element has been found with the name anIconToDeleteAnItem");}
		else {logger.debug("Page cleaned from test item.");}
		
		//Useful to verify that all test objects have been effectively deleted.
		robot.delay(5000);
		
			
	}
	
	
	@AfterClass
	public void releseResources() 
	{
		driver.close();
		driver.quit();
	}
	
}
