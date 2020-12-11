package jl.com;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SeleniumTest {

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
	    	driver.findElement(By.id("add_category_button")).submit();
	    	try {
				driver.wait(5000);
			} catch (InterruptedException e) {
				System.err.println();
				e.printStackTrace();
			}
	    		    	
	    	List<WebElement> spanElements = driver.findElements(By.tagName("span"));
	    	for (WebElement spanElement : spanElements) {
				String text = spanElement.getText();
				System.out.println("Found text: "+text);
				if (text.equals(textToFind)) isCategoryFound=true;
				System.out.println("isCategoryFound: "+isCategoryFound);
			}
	    	
	    	assertThat(isCategoryFound).isEqualTo(true);
	    	
	    }

	    @AfterClass
	    public void releaseResources() {
	    	driver.quit();
	    }
	    
	    
	    /*
	    @Test
	    public void byIdLocatorExample() {

	        WebElement searchBox = driver.findElement(By.id("search"));

	        searchBox.sendKeys("Bags");
	        searchBox.submit();

	        assertThat(driver.getTitle())
	                .isEqualTo("Search results for: 'Bags'");
	    }

	    @Test
	    public void byClassNameLocatorExample() {

	        WebElement searchBox = driver.findElement(By.id("search"));
	        searchBox.sendKeys("Electronics");

	        WebElement searchButton =
	                driver.findElement(By.className("search-button"));
	        searchButton.click();

	        assertThat(driver.getTitle())
	                .isEqualTo("Search results for: 'Electronics'");
	    }

	    @Test
	    public void byLinkTextLocatorExample() {

	        WebElement myAccountLink =
	                driver.findElement(By.linkText("MY ACCOUNT"));

	        myAccountLink.click();

	        assertThat(driver.getTitle())
	                .isEqualTo("Customer Login");
	    }

	    @Test
	    public void byPartialLinkTextLocatorExample() {

	        WebElement orderAndReturns =
	                driver.findElement(By.partialLinkText("PRIVACY"));

	        orderAndReturns.click();

	        assertThat(driver.getTitle())
	                .isEqualTo("Privacy Policy");
	    }

	    @Test
	    public void byTagNameLocatorExample() {

	        // get all links from the Home page
	        List<WebElement> links = driver.findElements(By.tagName("a"));

	        System.out.println("Found links:" + links.size());

	        // print links which have text using Java 8 Streams API
	        links.stream()
	                .filter(elem -> elem.getText().length() > 0)
	                .forEach(elem -> System.out.println(elem.getText()));
	    }

	    @Test
	    public void byXPathLocatorExample() {

	        WebElement searchBox =
	                driver.findElement(By.xpath("//*[@id='search']"));

	        searchBox.sendKeys("Bags");
	        searchBox.submit();

	        assertThat(driver.getTitle())
	                .isEqualTo("Search results for: 'Bags'");
	    }

	    @Test
	    public void byCssSelectorLocatorExample() {

	        WebElement searchBox =
	                driver.findElement(By.cssSelector("#search"));

	        searchBox.sendKeys("Bags");
	        searchBox.submit();

	        assertThat(driver.getTitle())
	                .isEqualTo("Search results for: 'Bags'");
	    }

	    @AfterClass
	    public void tearDown() {
	        driver.quit();
	    }
	    
	    */

}
