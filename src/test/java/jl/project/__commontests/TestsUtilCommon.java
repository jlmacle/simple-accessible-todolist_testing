package jl.project.__commontests;

import java.awt.AWTException;
import java.awt.Robot;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

import static org.testng.Assert.fail;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import jl.project.StringExternalization;


// A credit to GitHub Copilot for the help with this project

public class TestsUtilCommon 
{
	/**
	 * Returns the WebDriver instance used for a specific browser
	 * @param logger - the logger to be used
	 * @param robot - the instance of the Robot class to be used
	 * @param browserName - the name of the browser being used for the test
	 * @param driver - the webdriver used
	 * @param webDriverKey - a value used when instancing WebDriver 
	 * @param webDriverValue
	 * @return
	 */
 	public static WebDriver setup(Logger logger, Robot robot, String browserName, WebDriver driver, String webDriverKey, String webDriverValue)
	{
		boolean isNotATestOnAndroid = true;
		boolean isNotATestOnIOS = true;
		logger.debug(String.format("logger.toString(): %s", logger.toString()));
		logger.debug(String.format("logger.isDebugEnabled(): %b", logger.isDebugEnabled()));
		logger.debug(StringExternalization.COMMENT_ENTERING_TEST_FOR+webDriverValue);
		//https://chromedriver.chromium.org/downloads
		System.setProperty(webDriverKey, 
				StringExternalization.WEBDRIVERS_FOLDER+webDriverValue);
		
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setBrowserName(browserName);
		
		if(StringExternalization.GRID_NOT_USED) 
		{
			if (webDriverValue.equals(StringExternalization.WEBDRIVER_CHROME_VALUE)) driver = new ChromeDriver();
			else if (webDriverValue.equals(StringExternalization.WEBDRIVER_EDGE_VALUE)) driver = new EdgeDriver();
			else if (webDriverValue.equals(StringExternalization.WEBDRIVER_CHROME_ON_ANDROID_VALUE))
			{
				isNotATestOnAndroid = false; 
				capabilities.setCapability("automationName", "UiAutomator2");
				capabilities.setCapability("chromedriverExecutableDir","../webdrivers/");
				driver =  new AndroidDriver<WebElement>(capabilities);
				
			}
			else if (webDriverValue.equals(StringExternalization.WEBDRIVER_SAFARI_ON_IOS_VALUE))
			{
				isNotATestOnIOS = false;
				capabilities.setCapability("platformName","ios");
				capabilities.setCapability("platformVersion","13.7");
				capabilities.setCapability("appium:deviceName","iPhone 8");
				capabilities.setCapability("browserName","safari");
				capabilities.setCapability("appium:automationName","xcuitest");
				capabilities.setCapability("appium:udid","4A462E70-381D-46E6-BF1B-EB31BA8A1E30");
				// Appium's primary support for automating iOS apps is via the XCUITest driver.
				// http://appium.io/docs/en/drivers/ios-xcuitest/index.html
				driver =  new IOSDriver<WebElement>(capabilities);	
				
			}
			
			else 
			{
				if (logger.isDebugEnabled()) logger.debug(String.format("Unexpected webdriver value: %s",webDriverValue));
			}
		}
		
		else 
		{	
			try 
			{
				driver = new RemoteWebDriver(new URL(StringExternalization.SELENIUM_HUB), capabilities);
			} 
			catch (MalformedURLException e) 
			{
				logger.error(StringExternalization.EXCEPTION_MALFORMEDURL);
				logger.error(e.getMessage());
				e.printStackTrace();
			}			
		}
		if (isNotATestOnAndroid && isNotATestOnIOS) driver.manage().window().maximize();
		
		try 
		{
			robot = new Robot();
		} 
		catch (AWTException e) 
		{
			logger.debug(StringExternalization.EXCEPTION_AWT);
			e.printStackTrace();
		}
		
		return driver;
		
	}
 
	/**
	 * Releases the WebDriver instance used by the test
	 * @param driver - the webdriver used
	 */
 	public static void release(WebDriver driver)
 	{ 		
 		driver.quit();
 	}

	/**
	 * Submits text using an element id
	 * id - the id of the element to use to push text
	 * text - the text to be submitted
	 * driver - the webdriver used
	 */
	public static void sendKeys_withId(String id, String text, WebDriver driver)
	{
		driver.findElement(By.id(id)).sendKeys(text);
	}

	public static void click_withId(String id, WebDriver driver)
	{
		driver.findElement(By.id(id)).click();
	}
	
	/**
	 * 
	 * @param name - the name of the element to be clicked
	 * @param elementPosition - the position of the element to be clicked (starting from 0)
	 * @param driver - the webdriver used
	 * @param logger - the logger to be used
	 */
	public static void click_onElement_withName(String name, int elementPosition, WebDriver driver, Logger logger)
	{
		Robot robot = RobotFactory.getRobotInstance();		
		List<WebElement> anIconToDeleteAnItemElements = driver.findElements(By.name(name));
		int count = 0;
		for(WebElement anIconToDeleteAnItemElement: anIconToDeleteAnItemElements) {//only one item in the test
			
			if (count == elementPosition) 
			{ 
				anIconToDeleteAnItemElement.click();
				if (robot != null) {robot.delay(2000);}
			}
			count++;
			
		}
		
	}

	public static boolean isTextFindableWithinElements_withName(String text, String elementName, WebDriver driver, Logger logger)
	{
		boolean isTextFindableWithinElements_withName = false;
		for (WebElement element : driver.findElements(By.name(elementName)))
		{
			
			if ((element.getText().trim()).equals(text))
			{
				logger.debug(String.format("%s %s as text.",StringExternalization.DEBUG_FOUND, text)); 
				isTextFindableWithinElements_withName = true;
				break;
			}
		}
		logger.debug(String.format("isLabelFindableWithinElements_withName: %b",isTextFindableWithinElements_withName));
		return isTextFindableWithinElements_withName;
	}
	
	public static boolean isTextFindableWithinElements_withCSSSelector(String text, String selectorName, WebDriver driver, Logger logger)
	{
		boolean isTextindableWithinElements_withCSSSelector = false;
		for (WebElement element : driver.findElements(By.cssSelector(selectorName)))
		{
			if (element.getText().trim().equals(text))
			{
				logger.debug(String.format("%s %s as text.",StringExternalization.DEBUG_FOUND, text)); 
				isTextindableWithinElements_withCSSSelector = true;
				break;
			}
		}
		logger.debug(String.format("isLabelFindableWithinElements_withCSSSelector: %b",isTextindableWithinElements_withCSSSelector));
		return isTextindableWithinElements_withCSSSelector;
	}
	
	
	public static void tabTo_AddTheCategoryButton(Actions action, Robot robot)
	{
		action.sendKeys(Keys.TAB).build().perform();// nav bar
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();// nav bar
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();// nav bar
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();// Name of the category to add
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();// Add the category button
	}

	public static void tabTo_NameOfTheCategoryToAdd(Actions action, Robot robot)
	{
		action.sendKeys(Keys.TAB).build().perform();// nav bar
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();// nav bar
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();// nav bar
		robot.delay(1000);
		action.sendKeys(Keys.TAB).build().perform();// Name of the category to add
	}

	public static boolean createCategory_UsingClicks(Logger logger, WebDriver driver, Robot robot) 
	{
		logger.debug("Entering "+new Object(){}.getClass().getEnclosingMethod().getName());
		logger.info(StringExternalization.COMMENT_ENTERING_TEST_FOR+StringExternalization.TEST_CATEGORY_CREATION);
    	boolean isCategoryFound = false;    	
    	
    	logger.debug(String.format("1. %s %s", StringExternalization.TEST_CATEGORY_CREATION , StringExternalization.TEST_WITH_CLICKS));
    	driver.findElement(By.id(StringExternalization.ELEMENT_ID_NEW_CATEGORY_INPUT_FIELD)).sendKeys(StringExternalization.TEST_STRING_FOR_CREATED_CATEGORY);
    	robot.delay(1000);
    	driver.findElement(By.id(StringExternalization.ELEMENT_ID_ADD_CATEGORY_BUTTON)).click();
    	robot.delay(1000);
    	    	   	
    	logger.debug(String.format("2. %s", StringExternalization.TEST_CATEGORY_CREATION_CONFIRMATION ));		
    	driver.get(StringExternalization.ANGULAR_SERVER_URL);
		robot.delay(2000);

		isCategoryFound = TestsUtilCommon.isTextFindableWithinElements_withName(StringExternalization.TEST_STRING_FOR_CREATED_CATEGORY, StringExternalization.ELEMENT_NAME_A_CATEGORY, driver, logger);	    	

    	// Giving time for the item to be displayed
    	// Recurrent failed deletion issues that did not occur with the slowest computer I have.
    	
		robot.delay(3000);    
		
		return isCategoryFound;
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
		boolean isCategoryFound = false;	   

		//1. Deletion of the category created
		// finding the elements with the name StringExternalization.ELEMENT_NAME_AN_ICON_TO_DELETE_A_CATEGORY
		logger.info(String.format("1. %s", StringExternalization.TEST_CATEGORY_DELETION));
		driver.get(StringExternalization.ANGULAR_SERVER_URL);
		robot.delay(2000);

		click_onElement_withName(StringExternalization.ELEMENT_NAME_AN_ICON_TO_DELETE_A_CATEGORY, 0, driver, logger);
				
		//2. confirmation of deletion
		logger.info(String.format("%s %s","2. ", StringExternalization.TEST_CATEGORY_DELETION_CONFIRMATION));
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
		logger.debug("Entering "+new Object(){}.getClass().getEnclosingMethod().getName()); 		
		logger.info(StringExternalization.COMMENT_ENTERING_TEST_FOR+StringExternalization.TEST_ITEM_CREATION);	
		logger.info("1. "+StringExternalization.TEST_ITEM_CREATION);
		boolean isItemCreated=false;
		//Adding an item to the Uncategorized category created at startup
		driver.findElement(By.id(StringExternalization.ELEMENT_ID_FOR_CATEGORY_TO_SELECT_FIELD)).sendKeys(StringExternalization.TEST_NAME_OF_DEFAULT_CATEGORY);
		driver.findElement(By.id(StringExternalization.ELEMENT_ID_FOR_NEW_ITEM_FIELD)).sendKeys(StringExternalization.TEST_STRING_FOR_TEST_ITEM);
		driver.findElement(By.id(StringExternalization.ELEMENT_ID_FOR_ADD_ITEM_BUTTON)).click();
		//To avoid a StaleElementReferenceException
		driver.get(StringExternalization.ANGULAR_SERVER_URL);

		// Giving time for the item to be displayed
		// Issue with undetected created item.	   
		robot.delay(3000);		
						
		//Checking that the new item creation was successful		
		logger.info(String.format("%s %s","2. ", StringExternalization.TEST_ITEM_CREATION_CONFIRMATION));
		robot.delay(2000);

		isItemCreated = TestsUtilCommon.isTextFindableWithinElements_withName(StringExternalization.TEST_STRING_FOR_TEST_ITEM, StringExternalization.ELEMENT_AN_ITEM_NAME, driver, logger);
			
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
		logger.debug("Entering "+new Object(){}.getClass().getEnclosingMethod().getName());
		boolean isItemDeleted=false;
		logger.debug(StringExternalization.COMMENT_ENTERING_TEST_FOR+StringExternalization.TEST_ITEM_DELETION);
		logger.debug("1. "+StringExternalization.TEST_ITEM_DELETION);
		
		driver.get(StringExternalization.ANGULAR_SERVER_URL);
		robot.delay(2000);
		
		TestsUtilCommon.click_onElement_withName(StringExternalization.ELEMENT_AN_ICON_TO_DELETE_AN_ITEM_NAME, 0, driver, logger);
		
		
		//Checking the absence of the items
		logger.debug(String.format("%s %s","2. ", StringExternalization.TEST_ITEM_DELETION_CONFIRMATION));
		driver.get(StringExternalization.ANGULAR_SERVER_URL);
		robot.delay(2000);

		isItemDeleted = !TestsUtilCommon.isTextFindableWithinElements_withName(StringExternalization.TEST_STRING_FOR_TEST_ITEM, StringExternalization.ELEMENT_AN_ITEM_NAME, driver, logger);
				
		//Issue with an undeleted test item
		robot.delay(3000);
		
		return isItemDeleted;
	}

}
