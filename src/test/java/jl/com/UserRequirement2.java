package jl.com;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author 
 * Class testing the creation and deletion of an item.
 * */


public class UserRequirement2 {
	
	WebDriver driver;
	String newItemLabel="Protractor test item";
	
	@BeforeClass
	public void setup() {
		//https://chromedriver.chromium.org/downloads
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\jeanl\\Documents\\_SynchronizedFolder_Code\\JavaFullStackCode\\z_webdriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();		
	}
	
	@Test
	public void createItem() throws Exception{
		String testItemLabel = "newItemLabel";
		boolean isItemFound = false;
		
		
		//Adding an item to the Misc. category created at startup
		driver.findElement(By.id("category_to_select_field")).sendKeys("Misc.");
		driver.findElement(By.id("item_input_name")).sendKeys("newItemLabel");
		driver.findElement(By.id("add_item_button")).click();
		//To avoid a StaleElementReferenceException 
		driver.get("http://localhost:4200");
		
		//Checking that the new item creation was successful		
		List<WebElement> anItemElements = driver.findElements(By.name("anItem"));
		try {
			System.out.println("Found "+anItemElements.size()+" elements named 'anItem'");
			for(WebElement anItemElement: anItemElements) {
				String text = anItemElement.getText();
				System.out.println("Found "+text+" as text.");
				if (text.equals(testItemLabel)) {isItemFound=true;break;}
			}
			
		}
		catch(StaleElementReferenceException e) {
			System.err.println("A StaleElementReferenceException has been caught while searching"
					+ "the elements named 'anItem' ");
			e.getMessage();
			e.printStackTrace();
		}
		assertThat(isItemFound).isEqualTo(true);
		//nb: worked in progress
	}
	
	/*
	@Test
	public void deleteItem() throws Exception {
		List<WebElement> 
	}
	*/
	
	@BeforeMethod
	public void navigate() {
		driver.get("http://localhost:4200");
	}
	
	@AfterClass
	public void releaseResources() {
		driver.quit();
	}
	
	
	
	
}
