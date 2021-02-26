	
package jl.project.ChromeTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.util.List;

import org.apache.pdfbox.jbig2.util.log.Logger;
import org.apache.pdfbox.jbig2.util.log.LoggerFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import jl.project.StringExternalization;



/**
 * @author 
 *	Class testing the user requirement 1 of creating and deleting a category
 */
public class UserRequirement1 {
	Logger logger = LoggerFactory.getLogger(jl.project.ChromeTests.UserRequirement1.class);
	WebDriver driver;	
	
	/**
	 * "The annotated method will be run before the first test method in the current class is invoked."  
	 * https://testng.org/doc/documentation-main.html
	 */
	@BeforeClass	
	public void setup() {	
		logger.info(StringExternalization.TEST_START+StringExternalization.WEBDRIVER_CHROME_VALUE);
		//https://chromedriver.chromium.org/downloads
		System.setProperty(StringExternalization.WEBDRIVER_CHROME_KEY, 
				StringExternalization.WEBDRIVERS_FOLDER+StringExternalization.WEBDRIVER_CHROME_VALUE);
		driver = new ChromeDriver();		
		driver.manage().window().maximize();
	}
	
	/**
	 * "The annotated method will be run before each test method"
	 * https://testng.org/doc/documentation-main.html
	 */
	@BeforeMethod	
	public void navigate() {
		driver.get(StringExternalization.FRONT_END_URL);
		
	}
	
	/**
	 * Tests a successful creation of category
	 */
	@Test	
    public void createCategory() {
		logger.info(StringExternalization.TEST_START+StringExternalization.TEST_CATEGORY_CREATION);
    	boolean isCategoryFound = false;    	
    	
    	driver.findElement(By.id("new-category-input-field")).sendKeys(StringExternalization.LABEL_TEST_CATEGORY);
    	driver.findElement(By.id("add-category-button")).click();
    	//The category has been added. The display of the existing categories is being refreshed.
    	logger.debug("At this point, the test category should have been created.");
    	driver.get(StringExternalization.FRONT_END_URL);
    	
    		    	
    	List<WebElement> aCategoryElements = driver.findElements(By.name("aCategory"));	    	
    	try {
    		logger.debug("Found "+aCategoryElements.size()+" aCategory elements");
    		if(aCategoryElements.size() == 0 ){fail(StringExternalization.EXCEPTION_APP_NOT_STARTED);}//for the case where the app wasn't started 
    		for (WebElement aCategoryElement : aCategoryElements) {
	    		String text = aCategoryElement.getText().trim();//A space is in front of all strings
				logger.debug("Found text: *"+text+"*");				
				if (text.contains(StringExternalization.LABEL_TEST_CATEGORY)) {isCategoryFound=true;break;}
				
			}
    	}
    	catch(StaleElementReferenceException e) {
    		System.err.println("Caught a StaleElementReferenceException "
    				+ "while going through the elements with the name aCategory.");
    		e.printStackTrace();
    	}	    	
    	
    	assertThat(isCategoryFound).isEqualTo(true);
    	
    }
	
	/**
	 * Tests a successful deletion of category	 
	 */
	@Test	
	public void deleteCategory() {
		logger.debug(StringExternalization.TEST_START+StringExternalization.TEST_CATEGORY_DELETION);
		int testCategoryPositionIntheList = 0;
		int currentCategoryPosition = 0;
		boolean isCategoryFound = false;
		
		//1. Confirmation that the category was created; registration of its position in the list of elements named aCategory    	
    	List<WebElement> aCategoryElements = driver.findElements(By.name("aCategory"));	
		logger.debug("Found "+aCategoryElements.size()+" elements named aCategory");
		if(aCategoryElements.size() == 0 ){fail(StringExternalization.EXCEPTION_APP_NOT_STARTED);}//for the case where the app wasn't started 
    	try {    		
    		for (WebElement aCategoryElement : aCategoryElements) {
    			currentCategoryPosition++;
	    		String text = aCategoryElement.getText().trim();
	    		logger.debug("Found text: *"+text+"*");
				if (text.equals(StringExternalization.LABEL_TEST_CATEGORY)) {
					testCategoryPositionIntheList = currentCategoryPosition;
					logger.debug("Found the text:"+text+" in position: "+testCategoryPositionIntheList);					
					isCategoryFound=true;
					break;
				}
			}
    	}
    	catch(StaleElementReferenceException e) {
    		System.err.println("Caught a StaleElementReferenceException "
    				+ "while going through the anItem elements.");
    		e.getMessage();
    		e.printStackTrace();
    	}	 
		    	
    	if(isCategoryFound == true) {
    		logger.debug("The new category has been successfuly created.");
    		//2. Deletion of the category created
    			// finding the elements with the name "anIconToDeleteACategory"
    		List<WebElement> trashIconElementsInFrontOfCategories = driver.findElements(By.name("anIconToDeleteACategory"));
    		logger.debug("Found "+trashIconElementsInFrontOfCategories.size()+" elements with name anIconToDeleteACategory.");    		
    		try {
    			currentCategoryPosition=0;    			
    			for(WebElement trashCanIconElementInFrontOfCategory : trashIconElementsInFrontOfCategories) {
    				currentCategoryPosition++;    				    				
    				if (currentCategoryPosition == testCategoryPositionIntheList) {
    					logger.debug("Clicking the trash can icon in position: "+currentCategoryPosition);
    					trashCanIconElementInFrontOfCategory.click();
    					break;
    				}
    				else {logger.debug("Skipping this trash can icon.");}
    			}
    			
    		}
    		catch(StaleElementReferenceException e) {
    			System.err.println("Caught a StaleElementReferenceException"
    					+ "while going through the elements related to a trash can icon in front of a category.");
    			e.getMessage();
    			e.printStackTrace();    			
    		}    		
    		
    		//3. confirmation of deletion
    		driver.get(StringExternalization.FRONT_END_URL);
    		
    		aCategoryElements = driver.findElements(By.name("aCategory"));
    		logger.debug("Found "+aCategoryElements.size()+" elements in aCategoryElements after deletion.");
    		try {
    			for(WebElement aCategoryElement : aCategoryElements) {
    				String text = aCategoryElement.getText();
    				logger.debug(text);
    				if (text.equals(StringExternalization.LABEL_TEST_CATEGORY)) {
    					//if the created category can be found the test is failed    					
    					fail("Found "+StringExternalization.LABEL_TEST_CATEGORY+" when the test category should have been deleted."
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
    	
    	else {
    		System.err.println("The protractor test category was not found.");
    		fail("Test of category creation failed.");
    	}		
		
	}	
	
	
	/**
	 * The annotated method will be run after all the test methods in the current class have been run.
	 * https://testng.org/doc/documentation-main.html 
	 */
	@AfterClass	
	public void releaseResources() {
		driver.quit();
	}

}
