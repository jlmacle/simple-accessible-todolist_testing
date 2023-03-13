package jl.project.__commontests;

import java.awt.AWTException;
import java.awt.Robot;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import jl.project.StringExternalization;


// A credit to GitHub Copilot for the help with this project

public class TestsUtilCommon 
{
	/**
	 * 
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
 

 	public static void release(WebDriver driver)
 	{ 		
 		driver.quit();
 	}


	public static void sendKeys_withId(String id, String text, WebDriver driver)
	{
		driver.findElement(By.id(id)).sendKeys(text);
	}

	public static void click_withId(String id, WebDriver driver)
	{
		driver.findElement(By.id(id)).click();
	}

	public static void click_onElements_withName(String name, WebDriver driver, Logger logger)
	{
		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			logger.error(e.getMessage());
		}
		List<WebElement> anIconToDeleteAnItemElements = driver.findElements(By.name(name));
		for(WebElement anIconToDeleteAnItemElement: anIconToDeleteAnItemElements) {//only one item in the test
			anIconToDeleteAnItemElement.click();
			if (robot != null) {robot.delay(2000);}
		}
		
	}

	public static boolean isTextFindableWithinElements_withName(String text, String elementName, WebDriver driver, Logger logger)
	{
		boolean isTextFindableWithinElements_withName = false;
		for (WebElement element : driver.findElements(By.name(elementName)))
		{
			logger.debug(String.format("String *%s* present", text)); 
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
	

}
