package jl.com;

import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author 
 * Class testing the ability to create/delete an item
 *
 */
public class UserRequirement2 {
	
	WebDriver driver;
	@BeforeClass
	public void setup() {
		System.setProperty("webdriver.chrome.driver", "C:\\\\Users\\\\jeanl\\\\Documents\\\\_SynchronizedFolder_Code\\\\Programming a good gift to Adelaide Ellie and Liam\\\\z_webdriver_win32\\\\chromedriver.exe");
		driver = new ChromeDriver();
	}
	
	@Before
	public void navigate() {
		driver.get("http://localhost:4200");
	}
	
	@Test
	public void createItem() throws Exception {
		
		//Item creation
			//Selecting the "Misc." category for the test
		//driver.findElement(By.id("category_to_select_field")).sendKeys("Misc");			
		driver.findElement(By.id("item_input_name")).sendKeys("End-to-end test item");
		driver.wait(5000);
		driver.findElement(By.id("add_item_button")).click();
		
		//TODO
				
	}
	
	@AfterClass
	public void releaseResources() {
		driver.quit();
	}
	

}
