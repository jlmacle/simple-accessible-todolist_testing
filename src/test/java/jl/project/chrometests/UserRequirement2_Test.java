package jl.project.chrometests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.awt.AWTException;
import java.awt.Robot;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.testng.log4testng.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import jl.project.StringExternalization;

/**
 * @author 
 * Class testing the creation and deletion of an item.
 * */


public class UserRequirement2_Test {
	Logger logger = Logger.getLogger(jl.project.chrometests.UserRequirement2_Test.class);	
	WebDriver driver;	
	Robot robot;
	
	@BeforeClass
	public void setup() {		
		System.setProperty(StringExternalization.WEBDRIVER_CHROME_KEY, 
				StringExternalization.WEBDRIVERS_FOLDER+StringExternalization.WEBDRIVER_CHROME_VALUE);

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setBrowserName(StringExternalization.BROWSER_NAME_CHROME);
		
		if(StringExternalization.GRID_NOT_USED)  {driver = new ChromeDriver();}
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
	
	@Test
	public void createItem() throws Exception{	
		
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
		try {
			logger.debug(StringExternalization.DEBUG_FOUND+anItemElements.size()+StringExternalization.DEBUG_ELEMENT_NAMED_AN_ITEM);
			for(WebElement anItemElement: anItemElements) {
				String text = anItemElement.getText();				
				if (text.contains(StringExternalization.LABEL_TEST_ITEM))
				{
					logger.debug(StringExternalization.DEBUG_FOUND+text+" as text.");
					isItemCreated = true;
				}
			}
			
		}
		catch(StaleElementReferenceException e) {
			System.err.println(StringExternalization.EXCEPTION_STALE_ELEMENT_REFERENCE
					+ "the elements named 'anItem' after creation of the element.");
			e.getMessage();
			e.printStackTrace();
		}	
		
		// Giving time for the item to be displayed
    	// Recurrent failed deletion issues that did not occur with the slowest computer I have.
    	robot.delay(3000);

		assertThat(isItemCreated).isTrue();
	}
	
	
	@Test
	public void deleteItem() throws Exception {
		logger.info(StringExternalization.TEST_START+StringExternalization.TEST_ITEM_DELETION);
		logger.info("1. "+StringExternalization.TEST_ITEM_DELETION);
		//Deleting the item
		driver.get(StringExternalization.ANGULAR_SERVER_URL);
		robot.delay(2000);
		List<WebElement> anIconToDeleteAnItemElements = driver.findElements(By.name(StringExternalization.ELEMENT_NAME_AN_ICON_TO_DELETE_AN_ITEM));
		robot.delay(2000);
		try {			
			logger.debug(StringExternalization.DEBUG_FOUND+anIconToDeleteAnItemElements.size()+" element named 'anIconToDeleteAnItem'");
			//There should be only one item
			if(anIconToDeleteAnItemElements.size() != 1) 
				{fail(StringExternalization.EXCEPTION_ITEM_NOT_EXISTING_OR_NOT_UNIQUE+anIconToDeleteAnItemElements.size());}
			for(WebElement anIconToDeleteAnItemElement: anIconToDeleteAnItemElements) {				
				anIconToDeleteAnItemElement.click();
				logger.debug("Trash can icon clicked.");
				robot.delay(2000);
			}
			
			
			
		}
		catch(StaleElementReferenceException e) {
			System.err.println(StringExternalization.EXCEPTION_STALE_ELEMENT_REFERENCE
					+ "the elements named 'anIconToDeleteAnItem' ");
			e.getMessage();
			e.printStackTrace();
		}
		
		
		//Checking the absence of the items
		logger.info("2. Confirmation of item deletion");
		driver.get(StringExternalization.ANGULAR_SERVER_URL);
		robot.delay(2000);
		anIconToDeleteAnItemElements = driver.findElements(By.name(StringExternalization.ELEMENT_NAME_AN_ITEM));
		try {
			
			logger.debug(StringExternalization.DEBUG_FOUND+anIconToDeleteAnItemElements.size()+StringExternalization.DEBUG_ELEMENT_NAMED_AN_ITEM);
			for(WebElement anItemElement: anIconToDeleteAnItemElements) {
				String text = anItemElement.getText();
				logger.debug("Found *"+text+"* as text.");
				if (text.equals(StringExternalization.LABEL_TEST_ITEM)) {
					fail("Error: the test label has been found.");}
			}
			
		}
		catch(StaleElementReferenceException e) {
			System.err.println(StringExternalization.EXCEPTION_STALE_ELEMENT_REFERENCE
					+ "the elements named 'anItem' ");
			e.getMessage();
			e.printStackTrace();
		}
		
		//Issue with an undeleted test item
		robot.delay(3000);
	}
	

	@AfterClass
	public void releaseResources() {
		driver.quit();
	}
	
	
	
	
}
