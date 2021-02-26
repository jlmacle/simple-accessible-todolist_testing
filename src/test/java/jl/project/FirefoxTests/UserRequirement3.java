package jl.project.FirefoxTests;

import static org.testng.Assert.fail;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.testng.log4testng.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import jl.project.StringExternalization;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/**
 * @author 
 * Class testing the display/hiding of items
 */
public class UserRequirement3 {
	Logger logger = Logger.getLogger(jl.project.FirefoxTests.UserRequirement3.class);
	FirefoxDriver driver; 
	
	@BeforeClass
	public void setup(){
		System.setProperty(StringExternalization.WEBDRIVER_FIREFOX_KEY, 
				StringExternalization.WEBDRIVERS_FOLDER+StringExternalization.WEBDRIVER_FIREFOX_VALUE);
		driver = new FirefoxDriver();	
		driver.manage().window().maximize();
	}
	
	// For reasons of Tesseract library issue this test needs to be ignored on Ubuntu
	
	@Test
	public void hideAndDisplayItem() {
		logger.info(StringExternalization.TEST_START+StringExternalization.TEST_ITEM_HIDING_DISPLAY);
		boolean isTestItemLabelFound = false;
		driver.get(StringExternalization.FRONT_END_URL);
		
		//1. Creation of an item. By default the item is displayed
		logger.info("1. "+StringExternalization.TEST_ITEM_CREATION);
		//Adding an item to the Uncategorized category created at startup
		driver.findElement(By.id("category-to-select-field")).sendKeys("Uncategorized");
		driver.findElement(By.id("item-input-name")).sendKeys(StringExternalization.LABEL_TEST_ITEM);
		driver.findElement(By.id("add-item-button")).click();
		//To avoid a StaleElementReferenceException 
		driver.get(StringExternalization.FRONT_END_URL);
		
				
		//Checking that the new item creation was successful		
		List<WebElement> anItemElements = driver.findElements(By.name("anItem"));
		try {
			for(WebElement anItemElement: anItemElements) {
				String text = anItemElement.getText();				
				if (text.contains(StringExternalization.LABEL_TEST_ITEM)) {logger.debug("Found "+text+" as text."); isTestItemLabelFound=true;}
				if(isTestItemLabelFound == false) {fail("The test label was not found. The test of item creation failed.");}
			}
			
		}
		catch(StaleElementReferenceException e) {
			System.err.println(StringExternalization.EXCEPTION_STALE_ELEMENT_REFERENCE
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
			System.err.println(StringExternalization.EXCEPTION_IO+"while copy and saving the screenshot");
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
			System.err.println(StringExternalization.EXCEPTION_TESSERACT);
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		if(result.contains(StringExternalization.LABEL_TEST_ITEM)) 
		{
			 
			logger.debug(StringExternalization.TESSERACT_FOUND_TEST_ITEM);
		}
		else{fail(StringExternalization.TESSERACT_NOT_FOUND_TEST_ITEM+result);};
	
		//3. Hiding of the item		
		logger.info("3. Verification that the item can be hidden.");
		//Click on the category to hide the item. Only one category (Uncategorized) means only one element named foldUnfoldArea.
		driver.findElementByCssSelector(".foldUnfoldClickArea").click();
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
			System.err.println("An IOExeption occured while copying the screenshot taken after the click"				
					+ "(Hiding of the item).");
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		 catch (TesseractException e) {
			System.err.println(StringExternalization.EXCEPTION_TESSERACT
					+ "(Hiding of the item)");			
			System.err.println(e.getMessage());
			e.printStackTrace();
		}		
		
		//4. Verification that the item can be displayed 
		logger.info("4. Verification that the item can be displayed");
		driver.findElementByCssSelector(".foldUnfoldClickArea").click();
		
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
			System.err.println(StringExternalization.EXCEPTION_IO+"while copying the screenshot taken after the click"
					+ "(Display of the item)");
			System.err.println(e.getMessage());
			e.printStackTrace();
		} catch (TesseractException e) {
			System.err.println(StringExternalization.EXCEPTION_TESSERACT
					+ "(Display of the item)");
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
		//5. Suppressing the item to go on with the test suite
		logger.info("5. Deletion of the test item");
		List<WebElement> anIconToDeleteAnItemElements = driver.findElements(By.name("anIconToDeleteAnItem"));
			for(WebElement anIconToDeleteAnItemElement: anIconToDeleteAnItemElements) {//only one item in the test
				anIconToDeleteAnItemElement.click();
			}
			
		logger.info("6. Testing the deletion of the test item");
		driver.get(StringExternalization.FRONT_END_URL);
		
		anIconToDeleteAnItemElements = driver.findElements(By.name("anIconToDeleteAnItem"));
		if(!(anIconToDeleteAnItemElements.size() == 0)) fail("The test item was not deleted. "+anIconToDeleteAnItemElements.size()+" element has been found with the name anIconToDeleteAnItem");
				
	}	
	@AfterClass	
	public void releaseResources()
	{	
		driver.quit();
		
	}
	
}
