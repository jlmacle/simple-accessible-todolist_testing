package jl.project.__commontests;

import java.awt.AWTException;
import java.awt.Robot;
import java.net.MalformedURLException;
import java.net.URL;

import io.appium.java_client.android.AndroidDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.log4testng.Logger;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import jl.project.StringExternalization;


public class TestsUtilCommon 
{
		
 	public static WebDriver setup(Logger logger, Robot robot, String browserName, WebDriver driver, String webDriverKey, String webDriverValue)
	{
			
		logger.info(StringExternalization.TEST_START+webDriverValue);
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
				capabilities.setCapability("automationName", "UiAutomator2");
				capabilities.setCapability("chromedriverExecutableDir","../webdrivers/");
				driver =  new AndroidDriver<WebElement>(capabilities);
				
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
		//driver.manage().window().maximize();
		
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
}
