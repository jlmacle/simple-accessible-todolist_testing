package jl.project.__commontests.tests;

import java.awt.Robot;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jl.project.__commontests.RobotFactory;
import jl.project.__commontests.TestsUtilCommon;

public class UserRequirement_Common {
    static Logger logger = LoggerFactory.getLogger(jl.project.__commontests.tests.UserRequirement_Common.class);
    static WebDriver driver;	
    static Robot robot;

    public static WebDriver setup(String browserName, String webDriverKey, String webDriverValue) 
    {	
        
        robot = RobotFactory.getRobotInstance();		
        driver = TestsUtilCommon.setup(logger,robot, browserName, driver, webDriverKey,webDriverValue);
        return driver;
    }	
    
    public static void navigate(String URL) 
    {
        driver.get(URL);		
    }    
    
    public static void releaseResources(WebDriver driver) 
    {
        TestsUtilCommon.release(driver);
    }

}