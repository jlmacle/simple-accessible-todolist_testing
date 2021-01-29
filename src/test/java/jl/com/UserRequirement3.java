package jl.com;

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
import org.testng.annotations.Ignore;
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
	boolean isTestItemLabelFound = false;
	
	@BeforeClass
	public void setup(){
		//https://chromedriver.chromium.org/downloads
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\jeanl\\Documents\\_SynchronizedFolder_Code\\JavaFullStackCode\\z_webdriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
	}
	
	@BeforeMethod
	public void navigate() {
		driver.get("http://localhost:4200");
	}
	
	@Test
	public void hideItems() {
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
		if(result.contains(testItemLabel)) {System.out.println("Success. The test label has been found on the screen.");};
				
		//3. Hiding of the item
		
		
		//4. Verification that the item is hidden
		
		
		//fail("The hideItems test failed.");
	}
	
	
	@Test
	@Ignore
	public void displayItems() {
		fail();
	}
	
	@AfterClass
	public void releaseResources() {
		driver.quit();
	}

}
