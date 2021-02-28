package jl.project.EdgeTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.util.List;

import org.testng.log4testng.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import jl.project.StringExternalization;

/**
 * @author 
 * Class testing the creation and deletion of an item.
 * */


public class UserRequirement2 {
	Logger logger = Logger.getLogger(jl.project.EdgeTests.UserRequirement2.class);
	EdgeDriver driver;
		
	@BeforeClass
	public void setup() {		
		System.setProperty(StringExternalization.WEBDRIVER_EDGE_KEY, 
				StringExternalization.WEBDRIVERS_FOLDER+StringExternalization.WEBDRIVER_EDGE_VALUE);
		driver = new EdgeDriver();	
		driver.manage().window().maximize();
	}
	
	@BeforeMethod
	public void navigate() {
		driver.get(StringExternalization.FRONT_END_URL);
		
	}
	
	@Test
	public void createItem() throws Exception{		
		
		logger.info(StringExternalization.TEST_START+StringExternalization.TEST_ITEM_CREATION);	
		logger.info("1. "+StringExternalization.TEST_ITEM_CREATION);
		boolean isItemCreated=false;
		//Adding an item to the Uncategorized category created at startup
		driver.findElement(By.id(StringExternalization.ELEMENT_ID_CATEGORY_TO_SELECT_FIELD)).sendKeys("Uncategorized");
		driver.findElement(By.id(StringExternalization.ELEMENT_ID_ITEM_INPUT_NAME)).sendKeys(StringExternalization.LABEL_TEST_ITEM);
		driver.findElement(By.id(StringExternalization.ELEMENT_ID_ADD_ITEM_BUTTON)).click();
		//To avoid a StaleElementReferenceException
		driver.get(StringExternalization.FRONT_END_URL);
		
				
		//Checking that the new item creation was successful		
		logger.info("2. Confirmation of item creation ");
		List<WebElement> anItemElements = driver.findElements(By.name(StringExternalization.ELEMENT_NAME_AN_ITEM));
		try {
			logger.debug("Found "+anItemElements.size()+" element named 'anItem'");
			for(WebElement anItemElement: anItemElements) {
				String text = anItemElement.getText();				
				if (text.contains(StringExternalization.LABEL_TEST_ITEM))
				{
					logger.debug("Found "+text+" as text.");
					isItemCreated = true;
				}
			}
			
		}
		catch(StaleElementReferenceException e) {
			System.err.println(StringExternalization.EXCEPTION_STALE_ELEMENT_REFERENCE
					+ "the elements named 'anItem' after creation of the element.");
			e.getMessage();
			e.printStackTrace();
		}	
		assertThat(isItemCreated).isEqualTo(true);
	}
	
	
	@Test
	public void deleteItem() throws Exception {
		
		logger.info(StringExternalization.TEST_START+StringExternalization.TEST_ITEM_DELETION);
		//Deleting the item
		logger.info("1. "+StringExternalization.TEST_ITEM_DELETION);
		List<WebElement> anIconToDeleteAnItemElements = driver.findElements(By.name(StringExternalization.ELEMENT_NAME_AN_ICON_TO_DELETE_AN_ITEM));
		
		try {			
			logger.debug("Found "+anIconToDeleteAnItemElements.size()+" element named 'anIconToDeleteAnItem'");
			//There should be only one item
			if(anIconToDeleteAnItemElements.size() != 1) 
				{fail(StringExternalization.EXCEPTION_ITEM_NOT_EXISTING_OR_NOT_UNIQUE+anIconToDeleteAnItemElements.size());}
			for(WebElement anIconToDeleteAnItemElement: anIconToDeleteAnItemElements) {				
				anIconToDeleteAnItemElement.click();
				logger.debug("Trash can icon clicked.");
			}
			driver.get(StringExternalization.FRONT_END_URL);
			
			
		}
		catch(StaleElementReferenceException e) {
			System.err.println(StringExternalization.EXCEPTION_STALE_ELEMENT_REFERENCE
					+ "the elements named 'anIconToDeleteAnItem' ");
			e.getMessage();
			e.printStackTrace();
		}
		//Checking the absence of the items
		logger.info("2. Confirmation of deletion");
		anIconToDeleteAnItemElements = driver.findElements(By.name(StringExternalization.ELEMENT_NAME_AN_ITEM));
		try {
			
			logger.debug("Found "+anIconToDeleteAnItemElements.size()+" element named 'anItem'");
			for(WebElement anItemElement: anIconToDeleteAnItemElements) {
				String text = anItemElement.getText();
				logger.debug("Found *"+text+"* as text.");
				if (text.equals(StringExternalization.LABEL_TEST_ITEM)) {
					fail("Error: the test label has been found.");}
			}
			
		}
		catch(StaleElementReferenceException e) {
			System.err.println(StringExternalization.EXCEPTION_STALE_ELEMENT_REFERENCE
					+ "the elements named 'anItem' ");
			e.getMessage();
			e.printStackTrace();
		}
	}
	

	@AfterClass
	public void releaseResources() {
		driver.quit();
	}
	
	
	
	
}
