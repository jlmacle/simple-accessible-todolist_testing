package jl.project.FirefoxTests;

import static org.testng.Assert.fail;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
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
	FirefoxDriver driver; 
	String testItemLabel ="Protractor test";//Note the ocr might sometimes have an issue to detect the text.
	
	
	@BeforeClass
	public void setup(){
		System.setProperty(StringExternalization.webdriver_firefox_key, 
				StringExternalization.webdrivers_folder+StringExternalization.webdriver_firefox_value);
		driver = new FirefoxDriver();	
		driver.manage().window().maximize();
	}
	
	// For reasons of Tesseract library issue this test needs to be ignored on Ubuntu
	@Ignore
	@Test
	public void hideAndDisplayItem() {
		boolean isTestItemLabelFound = false;
		driver.get(StringExternalization.front_end_url);
		
		//1. Creation of an item. By default the item is displayed
		System.out.println("1. Creation of the item");
		//Adding an item to the Uncategorized category created at startup
		driver.findElement(By.id("category-to-select-field")).sendKeys("Uncategorized");
		driver.findElement(By.id("item-input-name")).sendKeys(testItemLabel);
		driver.findElement(By.id("add-item-button")).click();
		//To avoid a StaleElementReferenceException 
		driver.get(StringExternalization.front_end_url);
		
				
		//Checking that the new item creation was successful		
		List<WebElement> anItemElements = driver.findElements(By.name("anItem"));
		try {
			for(WebElement anItemElement: anItemElements) {
				String text = anItemElement.getText();				
				if (text.contains(testItemLabel)) {System.out.println("Found "+text+" as text."); isTestItemLabelFound=true;}
				if(isTestItemLabelFound == false) {fail("The test label was not found. The test of item creation failed.");}
			}
			
		}
		catch(StaleElementReferenceException e) {
			System.err.println("A StaleElementReferenceException has been caught while searching"
					+ "the elements named 'anItem' after creation of the element.");
			e.getMessage();
			e.printStackTrace();
		}		
		
		
		//2. Verification that the item is displayed
		//a. code to get a screenshot from the browser
		System.out.println("2. Verification that the item is displayed");
		File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);	
		File screenshotFile_copy = new File("./screenshots/newItemScreenshot.png");
		try {
			FileUtils.copyFile(screenshotFile, screenshotFile_copy);
		} catch (IOException e) {
			System.err.println("IOException while copy and saving the screenshot");
			e.printStackTrace();
		}
		//b. code to extract the text from the picture
		Tesseract ocr = new Tesseract();
		String result = null;
		//https://github.com/tesseract-ocr/tessdata
		ocr.setDatapath("./tessdata");
		ocr.setLanguage("eng");
		try {
			result = ocr.doOCR(screenshotFile_copy);
		} catch (TesseractException e) {
			System.err.println("Exception while doing the OCR.");
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		if(result.contains(testItemLabel)) 
		{
			 
			System.out.println("Success. The test label has been found on the screen.");
		}
		else{fail("The item label seems to be absent from the screenshot: "+result);};
	
		//3. Hiding of the item		
		System.out.println("3. Verification that the item can be hidden.");
		//Click on the category to hide the item. Only one category (Uncategorized) means only one element named foldUnfoldArea.
		driver.findElementByCssSelector(".foldUnfoldClickArea").click();
		//4. Verification that the item is hidden
		screenshotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		File screenshot_AfterClickToHide_copy = new File("./screenshots/AfterClickToHideScreenshot.png");
		try {
			FileUtils.copyFile(screenshotFile, screenshot_AfterClickToHide_copy);
			result = ocr.doOCR(screenshot_AfterClickToHide_copy);
			if(!result.contains(testItemLabel)) 
			{ 
				
				System.out.println("Success: the label couldn't be found in the screenshot: "+result);
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
			System.err.println("TesseractException while reading the screenshot taken after the click."
					+ "(Hiding of the item)");			
			System.err.println(e.getMessage());
			e.printStackTrace();
		}		
		
		//4. Verification that the item can be displayed 
		System.out.println("4. Verification that the item can be displayed");
		driver.findElementByCssSelector(".foldUnfoldClickArea").click();
		
		screenshotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		File screenshot_AfterClickToDisplay_copy = new File("./screenshots/AfterClickToDisplayScreenshot.png");
		
		try {			
			FileUtils.copyFile(screenshotFile, screenshot_AfterClickToDisplay_copy);
			ocr.setDatapath("./tessdata");
			ocr.setLanguage("eng");
			result = ocr.doOCR(screenshot_AfterClickToDisplay_copy);
			
			if(result.contains(testItemLabel)) 
			{
				System.out.println("Sucess: the label was found after clicking to display the item: "+result);
			}
			else {fail("The label: "+testItemLabel+" could not be in the ocr result: "+result
					+" when the item should have been displayed.");}
		} catch (IOException e) {
			System.err.println("An IOException occured while copying the screenshot taken after the click"
					+ "(Display of the item)");
			System.err.println(e.getMessage());
			e.printStackTrace();
		} catch (TesseractException e) {
			System.err.println("TesseractException while reading the screenshot taken after the click"
					+ "(Display of the item)");
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
		//5. Suppressing the item to go on with the test suite
		System.out.println("5. Deletion of the test item");
		List<WebElement> anIconToDeleteAnItemElements = driver.findElements(By.name("anIconToDeleteAnItem"));
			for(WebElement anIconToDeleteAnItemElement: anIconToDeleteAnItemElements) {//only one item in the test
				anIconToDeleteAnItemElement.click();
			}
			
		System.out.println("6. Testing the deletion of the test item");
		driver.get(StringExternalization.front_end_url);
		
		anIconToDeleteAnItemElements = driver.findElements(By.name("anIconToDeleteAnItem"));
		if(!(anIconToDeleteAnItemElements.size() == 0)) fail("The test item was not deleted. "+anIconToDeleteAnItemElements.size()+" element has been found with the name anIconToDeleteAnItem");
				
	}	
	@AfterClass	
	public void releaseResources()
	{	
		driver.quit();
		
	}
	
}
