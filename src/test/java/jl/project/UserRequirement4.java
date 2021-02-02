package jl.project;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.fail;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

/**
 * @author
 * Class testing the user requirement of using the functionalities using the keyboard only
 */
public class UserRequirement4 {
	ChromeDriver driver;
	String testCategoryLabel= "Protractor test category"; 
	
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
	@Ignore
	public void createACategoryWithKeyboard() {
		
		boolean isCategoryCreated = false;		
		
		System.out.println("1. Creation of a category with the keyboard.");		
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
	
	@Test
	public void deleteCategoryWithKeyboard() {
		boolean isCategoryFound;
		System.out.println("Deletion of a category with a keyboard.");
		//Assuming the category in 2nd position
		Robot robot;
		try {
			robot = new Robot();
			robot.keyPress(KeyEvent.VK_TAB);//new category text
			robot.keyPress(KeyEvent.VK_TAB);//submit category button
			robot.keyPress(KeyEvent.VK_TAB);//category selection
			robot.keyPress(KeyEvent.VK_TAB);//new item text
			robot.keyPress(KeyEvent.VK_TAB);//submit item button
			robot.keyPress(KeyEvent.VK_TAB);//trash can icon: category "Misc."
			robot.keyPress(KeyEvent.VK_TAB);//plus sign icon: category "Misc."
			robot.keyPress(KeyEvent.VK_TAB);//trash can icon: category "Protractor test category"
			robot.keyPress(KeyEvent.VK_ENTER);//Click to delete the test category
			
		} catch (AWTException e) {
			System.err.println("AWTException while using the instance of the class ");
			e.printStackTrace();
		}
		
		//Verifying that the category has been deleted
		driver.get("http://localhost:4200");
		List<WebElement >aCategoryElements = driver.findElements(By.name("aCategory"));
		System.out.println("Found "+aCategoryElements.size()+" elements in aCategoryElements after deletion.");
		try {
			for(WebElement aCategoryElement : aCategoryElements) {
				String text = aCategoryElement.getText();
				System.out.println(text);
				if (text.equals(testCategoryLabel)) {
					//if the created category can be found the test is failed    					
					fail("Found "+testCategoryLabel+" when the test category should have been deleted."
							+ "The test is failed.");
				}
				   				
			}
			//otherwise the test is successful
			isCategoryFound = false;
			assertThat(isCategoryFound).isEqualTo(false);
		}
		catch(StaleElementReferenceException e) {
			System.err.println("Caught a StaleElementReferenceException"
					+ "while going through the elements related to a trash can icon before a category.");
			e.getMessage();
			e.printStackTrace();    			
		}  
		
		
		
		
	}
	
	

	@AfterClass
	public void releseResources() {
		driver.close();
		driver.quit();
	}
	
}
