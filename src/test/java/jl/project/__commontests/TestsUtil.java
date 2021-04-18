package jl.project.__commontests;

import java.awt.AWTException;
import java.awt.Robot;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.openqa.selenium.WebDriver;

import jl.project.StringExternalization;

import org.testng.log4testng.Logger;

public class TestsUtil {
	
	public static WebDriver setup(Logger logger, Robot robot, String browserName, WebDriver driver, String webDriverKey, String webDriverValue)
	{
			
		logger.info(StringExternalization.TEST_START+webDriverValue);
		//https://chromedriver.chromium.org/downloads
		System.setProperty(webDriverKey, 
				StringExternalization.WEBDRIVERS_FOLDER+webDriverValue);
		
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setBrowserName(browserName);
		
		//if(StringExternalization.GRID_NOT_USED) {driver = new RemoteWebDriver(capabilities);}
		if(StringExternalization.GRID_NOT_USED) {driver = new ChromeDriver();}
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
		
		return driver;
		
	}
	
	

}
