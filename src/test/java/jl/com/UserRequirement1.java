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
 * Class testing the user requirements of being able to create a new category.
 */
public class UserRequirement1 {

	 WebDriver driver;

	    @BeforeClass
	    public void setup() {

	        System.setProperty("webdriver.chrome.driver",
	                "C:\\Users\\jeanl\\Documents\\_SynchronizedFolder_Code\\Programming a good gift to Adelaide Ellie and Liam\\z_webdriver_win32\\chromedriver.exe");
	        		// where.exe chromedriver in Powershell can help find the path to chromedriver
	        driver = new ChromeDriver();

	    }

	    @BeforeMethod
	    public void navigate() {
	        driver.get("http://localhost:4200");
	    }
	    
	    @Test
	    public void createCategory() {
	    	boolean isCategoryFound = false;
	    	String textToFind= "Protractor test category";
	    	
	    	driver.findElement(By.id("new_category_input_field")).sendKeys("Protractor test category");
	    	driver.findElement(By.id("add_category_button")).click();
	    	//The category has been added. The display of the existing categories is being refreshed.
	    	driver.get("http://localhost:4200");
	    		    	
	    	List<WebElement> spanElements = driver.findElements(By.tagName("span"));	    	
	    	try {
	    		System.out.println("Found "+spanElements.size()+" SPAN elements");
	    		for (WebElement spanElement : spanElements) {
		    		String text = spanElement.getText();
					System.out.println("Found text: "+text);
					if (text.equals(textToFind)) isCategoryFound=true;
					System.out.println("isCategoryFound: "+isCategoryFound);
				}
	    	}
	    	catch(StaleElementReferenceException e) {
	    		System.err.println("Caught a StaleElementReferenceException "
	    				+ "while going through the SPAN elements.");
	    		e.printStackTrace();
	    	}	    	
	    	
	    	assertThat(isCategoryFound).isEqualTo(true);
	    	
	    }

	    @AfterClass
	    public void releaseResources() {
	    	driver.quit();
	    }	    
	   
}
