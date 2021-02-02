package jl.project;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author
 * Class testing the user requirement of using the functionalities using the keyboard only
 */
public class UserRequirement4 {
	ChromeDriver driver;
	
	
	@BeforeClass
	public void setup() {
	//https://chromedriver.chromium.org/downloads
	System.setProperty("webdriver.chrome.driver", "C:\\Users\\jeanl\\Documents\\_SynchronizedFolder_Code\\JavaFullStackCode\\z_webdriver_win32\\chromedriver.exe");
	driver = new ChromeDriver();		
	}
	
	@BeforeMethod
	public void navigate() {
		driver.get("http://localhost:4200");
	}
	
	@Test
	public void createACategoryWithKeyboard() {
		String testCategoryLabel= "Protractor test category"; 
		boolean isCategoryCreated = false;		
		
		System.out.println("1. Creation of a category with the keyboard");		
		//Tabbing until finding the input field to add the new category label
		Robot robot;
		Actions  action = new Actions(driver);
		try {
			robot = new Robot();
			robot.keyPress(KeyEvent.VK_TAB);
			robot.delay(1000);
			action.sendKeys(testCategoryLabel).build().perform();
			robot.delay(1000);
			robot.keyPress(KeyEvent.VK_TAB);
			robot.delay(1000);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.delay(1000);
			
		} catch (AWTException e) {
			System.err.println("AWTException when using the robot class");
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
		//Verifying that the category has been created		
		List<WebElement> aCategoryElements = driver.findElements(By.name("aCategory"));
		for(WebElement aCategoryElement: aCategoryElements ) {
			String text = aCategoryElement.getText();
			if(text.contains(testCategoryLabel)) 
			{
				System.out.println("The text *"+text+"* was found. The category was successfully "
						+ "created using the keyboard only. ");
				isCategoryCreated=true;
			}
		};		
		assertThat(isCategoryCreated).isEqualTo(true);		
	}
	
	
	

	@AfterClass
	public void releseResources() {
		driver.close();
		driver.quit();
	}
	
}
