package jl.com;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/**
 * @author 
 * Class testing the display/hiding of items
 */
public class UserRequirement3 {
	ChromeDriver driver; 
	String testItemLabel ="Protractor test item";
	
	
	@BeforeClass
	public void setup(){
		//https://chromedriver.chromium.org/downloads
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\jeanl\\Documents\\_SynchronizedFolder_Code\\JavaFullStackCode\\z_webdriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
	}
	
		
	@Test
	public void hideAndDisplayItems() {
		boolean isTestItemLabelFound = false;
		boolean isItemDiplayed = false;
		boolean isItemHidden =  false;		
		boolean isItemDisplayedAgain = false;
		driver.get("http://localhost:4200");
		//1. Creation of an item. By default the item is displayed
		System.out.println("1. Creation of the item");
		//Adding an item to the Misc. category created at startup
		driver.findElement(By.id("category_to_select_field")).sendKeys("Misc.");
		driver.findElement(By.id("item_input_name")).sendKeys(testItemLabel);
		driver.findElement(By.id("add_item_button")).click();
		//To avoid a StaleElementReferenceException 
		driver.get("http://localhost:4200");
				
		//Checking that the new item creation was successful		
		List<WebElement> anItemElements = driver.findElements(By.name("anItem"));
		try {
			for(WebElement anItemElement: anItemElements) {
				String text = anItemElement.getText();				
				if (text.equals(testItemLabel)) {System.out.println("Found "+text+" as text."); isTestItemLabelFound=true;}
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
		if(result.contains(testItemLabel)) {isItemDiplayed=true; System.out.println("Success. The test label has been found on the screen.");};
	
		//3. Hiding of the item		
		System.out.println("3. Verification that the item can be hidden.");
		//Click on the category to hide the item. Only one category (Misc.) means only one element named foldUnfoldArea.
		driver.findElement(By.id("foldUnfoldArea")).click();
		//4. Verification that the item is hidden
		screenshotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		File screenshot_AfterClickToHide_copy = new File("./screenshots/AfterClickToHideScreenshot.png");
		try {
			FileUtils.copyFile(screenshotFile, screenshot_AfterClickToHide_copy);
			result = ocr.doOCR(screenshot_AfterClickToHide_copy);
			if(!result.contains(testItemLabel)) { isItemHidden = true; System.out.println("Success: the label couldn't be found in the screenshot: "+result);}
			
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
		driver.findElement(By.id("foldUnfoldArea")).click();	
		
		screenshotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		File screenshot_AfterClickToDisplay_copy = new File("./screenshots/AfterClickToDisplayScreenshot.png");
		
		try {			
			FileUtils.copyFile(screenshotFile, screenshot_AfterClickToDisplay_copy);
			result = ocr.doOCR(screenshot_AfterClickToDisplay_copy);
			
			if(result.contains(testItemLabel)) {isItemDisplayedAgain=true;System.out.println("Sucess: the label was found after clicking to display the item: "+result);}
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
		
		assertThat(isItemDiplayed&&isItemHidden&&isItemDisplayedAgain).isEqualTo(true);
	}
	
	
	@AfterClass
	public void releaseResources() {
		//TODO: to understand why the Google Chrome doesn't close at the end of the test.
		driver.quit();
	}

}
