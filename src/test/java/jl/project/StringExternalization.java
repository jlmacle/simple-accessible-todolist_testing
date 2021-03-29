package jl.project;

public class StringExternalization {
	// Windows webdrivers			
	
	  public static final String WEBDRIVER_CHROME_VALUE = "chromedriver.exe";
	  public static final String WEBDRIVER_FIREFOX_VALUE = "geckodriver.exe";
	  public static final String WEBDRIVER_EDGE_VALUE = "msedgedriver.exe"; 
	  public static final String WEBDRIVER_SAFARI_VALUE = "";
	 
	  		
	// Linux webdrivers		
	/*
	 * public static final String WEBDRIVER_CHROME_VALUE = "chromedriver"; public
	 * static final String WEBDRIVER_FIREFOX_VALUE = "geckodriver"; public static
	 * final String WEBDRIVER_EDGE_VALUE = "msedgedriver"; public static final
	 * String WEBDRIVER_SAFARI_VALUE = "";
	 */
	 
	 
	 
	// macOS webdrivers	
	/*
	 * public static final String WEBDRIVER_CHROME_VALUE = "chromedriver"; public
	 * static final String WEBDRIVER_FIREFOX_VALUE = "geckodriver"; public static
	 * final String WEBDRIVER_EDGE_VALUE = "msedgedriver"; public static final
	 * String WEBDRIVER_SAFARI_VALUE = ""; // /usr/bin/safaridriver to be used
	 * instead
	 */
	  
	public static final boolean GRID_NOT_USED = false;
	public static final String ANGULAR_SERVER_URL = "http://localhost:4200";	
	//Grid 4: beginning	
	//public static final String ANGULAR_SERVER_URL = "http://192.168.1.16:4200";
	public static final String SELENIUM_HUB = "http://192.168.1.16:4444";
	public static final String BROWSER_NAME_CHROME = "chrome";	
	public static final String BROWSER_NAME_EDGE = "MicrosoftEdge";	
	public static final String BROWSER_NAME_FIREFOX = "firefox";
	//Grid 4 : end
	
	public static final String LABEL_TEST_CATEGORY = "Selenium test category";
	public static final String LABEL_TEST_ITEM = "test"; 
	//the ocr sometimes has an issue to detect the word 'item'.
	
	public static final String ELEMENT_ID_NEW_CATEGORY_INPUT_FIELD = "new-category-input-field";
	public static final String ELEMENT_ID_ADD_CATEGORY_BUTTON = "add-category-button";
	public static final String ELEMENT_ID_CATEGORY_TO_SELECT_FIELD = "category-to-select-field";
	public static final String ELEMENT_ID_ITEM_INPUT_NAME = "item-input-name";
	public static final String ELEMENT_ID_ADD_ITEM_BUTTON = "add-item-button";
	
	public static final String ELEMENT_NAME_A_CATEGORY = "aCategory";
	public static final String ELEMENT_NAME_AN_ICON_TO_DELETE_A_CATEGORY = "anIconToDeleteACategory";
	public static final String ELEMENT_NAME_AN_ITEM = "anItem";
	public static final String ELEMENT_NAME_AN_ICON_TO_DELETE_AN_ITEM = "anIconToDeleteAnItem";
	
	public static final String EXCEPTION = "Caught an exception";
	public static final String EXCEPTION_APP_NOT_STARTED = "The app wasn't started.";
	public static final String EXCEPTION_ITEM_NOT_EXISTING_OR_NOT_UNIQUE = 
			"There should be one item. Number of item found: ";
	public static final String EXCEPTION_STALE_ELEMENT_REFERENCE = "Caught a StaleElementReferenceException ";
	public static final String EXCEPTION_TESSERACT = "Caught a TesseractException while doing the OCR.";
	public static final String EXCEPTION_IO = "Caught an IOException ";
	public static final String EXCEPTION_AWT = "Caught an AWTException while using the instance of the Robot class";
	public static final String EXCEPTION_MALFORMEDURL = "Caught a MalformedURLException while instantiating the RemoteWebDriver instance";
	
	public static final String TEST_START = "Entering test for: ";
	public static final String TEST_CATEGORY_CREATION = "category creation";
	public static final String TEST_CATEGORY_DELETION = "category deletion";
	public static final String TEST_CATEGORY_CREATION_DELETION_WITH_KEYBOARD = 
			"category creation/deletion with the keyboard";
	public static final String TEST_ITEM_CREATION = "item creation";	
	public static final String TEST_ITEM_DELETION = "item deletion";
	public static final String TEST_ITEM_CREATION_DELETION_WITH_KEYBOARD = 
			"item creation/deletion with the keyboard";	
	public static final String TEST_ITEM_HIDING_DISPLAY = "item hiding/display";
	public static final String TEST_ITEM_HIDING_DISPLAY_WITH_KEYBOARD = "item hiding/display with keyboard";
	public static final String TEST_KEYBOARD = " with the keyboard";
	public static final String TEST_KEYBOARD_ENTER_KEY = " with ENTER key";
	public static final String TEST_KEYBOARD_SPACE_KEY = " with SPACE key";
	
	public static final String TESSERACT_SCREENSHOT_PATH_NEW_ITEM = "./screenshots/newItemScreenshot.png";
	public static final String TESSERACT_TESSDATA = "./tessdata";
	public static final String TESSERACT_LANGUAGE = "eng";
	public static final String TESSERACT_DPI_KEY = "user_defined_dpi";
	public static final String TESSERACT_DPI_VALUE = "300";
	public static final String TESSERACT_FOUND_TEST_ITEM = "Success. The test label has been found on the screen.";
	public static final String TESSERACT_NOT_FOUND_TEST_ITEM = "The item label seems to be absent from the screenshot: ";
	
		
	public static final String WEBDRIVERS_FOLDER = "../webdrivers/";	
	public static final String WEBDRIVER_CHROME_KEY = "webdriver.chrome.driver";	
	public static final String WEBDRIVER_FIREFOX_KEY = "webdriver.gecko.driver";	
	public static final String WEBDRIVER_EDGE_KEY = "webdriver.edge.driver";

	
	
	

	
	
	
	

}
