package jl.project;

public class StringExternalization 
{
	
	private StringExternalization() 
	{
	    throw new IllegalStateException("Utility class");
	}
	
	//Used by the ConfigurationUtility class to add the proper webdrivers to this class	
	//<webdrivers>
	public static final String WEBDRIVER_CHROME_VALUE = "chromedriver.exe";
	public static final String WEBDRIVER_FIREFOX_VALUE = "geckodriver.exe"; 
	public static final String WEBDRIVER_EDGE_VALUE = "msedgedriver.exe"; 
	// At the time of starting this project,
	// the value for WEBDRIVER_CHROME_ON_ANDROID_VALUE didn't matter
	// as long as the webdriver was in the webdriver folder.
	// Automatixc research of webdrrivers with 
	// capabilities.setCapability["chromedriverExecutableDir","../webdrivers/"];
	// Potential TODO: to find if this remains true.
	public static final String WEBDRIVER_CHROME_ON_ANDROID_VALUE = "Chrome on Android";
	public static final String WEBDRIVER_SAFARI_ON_IOS_VALUE = "N.A.";
	//</webdrivers>
		
	//Used by the ConfigurationUtility class to make sure that the default configuration doesn't use Selenium Grid 4.
	//<seleniumGrid4>
	public static final boolean GRID_NOT_USED = true;
	//</seleniumGrid4>
	
	//<angularServer>
	public static final String ANGULAR_SERVER_URL = "http://localhost:4200";
	//</angularServer>
	
	/* Selenium Grid 4 configuration : begin */
	public static final String SELENIUM_HUB = "http://<static IP>:4444";
	/* Selenium Grid 4 configuration : end */
	
	public static final String BROWSER_NAME_CHROME = "chrome";	
	public static final String BROWSER_NAME_EDGE = "MicrosoftEdge";	
	public static final String BROWSER_NAME_FIREFOX = "firefox";
	public static final String BROWSER_NAME_SAFARI = "safari";	
	
	public static final String ELEMENT_ID_NEW_CATEGORY_INPUT_FIELD = "new-category-input-field";
	public static final String ELEMENT_ID_ADD_CATEGORY_BUTTON = "add-category-button";
	public static final String ELEMENT_ID_FOR_CATEGORY_TO_SELECT_FIELD = "category-to-select-field";
	public static final String ELEMENT_ID_FOR_NEW_ITEM_FIELD = "item-input-name";
	public static final String ELEMENT_ID_FOR_ADD_ITEM_BUTTON = "add-item-button";
	
	public static final String ELEMENT_NAME_A_CATEGORY = "aCategory";
	public static final String ELEMENT_NAME_AN_ICON_TO_DELETE_A_CATEGORY = "anIconToDeleteACategory";
	public static final String ELEMENT_AN_ITEM_NAME = "anItem";
	public static final String ELEMENT_AN_ITEM_CLASS = ".itemName";
	public static final String ELEMENT_AN_ICON_TO_DELETE_AN_ITEM_NAME = "anIconToDeleteAnItem";
	public static final String ELEMENT_A_FOLDABLE_AREA = ".foldUnfoldClickArea"; 

	public static final String EXCEPTION = "Caught an exception";
	public static final String EXCEPTION_APP_NOT_STARTED = "The app wasn't started.";
	public static final String EXCEPTION_ITEM_NOT_EXISTING_OR_NOT_UNIQUE = 
			"There should be one item. Number of item found: ";
	public static final String EXCEPTION_STALE_ELEMENT_REFERENCE = "Caught a StaleElementReferenceException ";
	public static final String EXCEPTION_IO = "Caught an IOException ";
	public static final String EXCEPTION_AWT = "Caught an AWTException while using the instance of the Robot class";
	public static final String EXCEPTION_MALFORMEDURL = "Caught a MalformedURLException while instantiating the RemoteWebDriver instance";
	
	public static final String TEST_FAILURE_CATEGORY_CREATION = "Test of category creation failed.";
	public static final String TEST_FAILURE_CATEGORY_NOT_FOUND = "The test category was not found.";
	public static final String TEST_FAILURE_CATEGORY_FOUND = "The test category was found.";

	public static final String TEST_STRING_FOR_CREATED_CATEGORY = "Selenium test category";
	public static final String TEST_NAME_OF_DEFAULT_CATEGORY = "Uncategorized";
	public static final String TEST_STRING_FOR_TEST_ITEM = "test"; 

	public static final String COMMENT_ENTERING_TEST_FOR = "Entering test for: ";
	public static final String TEST_CATEGORY_CREATION = "Category creation";
	public static final String TEST_CATEGORY_DELETION = "Category deletion";
	public static final String TEST_CATEGORY_CREATION_AND_DELETION = 
			"Category creation/deletion";
	public static final String TEST_CATEGORY_CREATION_CONFIRMATION = "Confirming creation of the category";
	public static final String TEST_CATEGORY_DELETION_CONFIRMATION = "Confirming deletion of the category";

	public static final String TEST_STEP_ITEM_CREATION = "Item creation";
	public static final String TEST_ITEM_DELETION = "Item deletion";	
	public static final String TEST_STEP_VERIFICATION_OF_DISPLAYED_ITEM = "Verification that the item is displayed";
	public static final String TEST_STEP_VERIFICATION_OF_HIDEABLE_ITEM = "Verification that the item can be hidden.";
	
	public static final String TEST_ITEM_CREATION_DELETION_WITH_KEYBOARD = 
			"item creation/deletion with the keyboard";	
	public static final String TEST_ITEM_HIDING_DISPLAY = "item hiding/display";
	public static final String TEST_ITEM_HIDING_DISPLAY_ITEM_NOT_FOUND = "The test item wasn't found.";
	public static final String TEST_ITEM_HIDING_DISPLAY_ITEM_NOT_HIDDEN = "The test item isn't hidden.";
	public static final String TEST_WITH_CLICKS = " with clicks";
	public static final String TEST_WITH_KEYBOARD = " with the keyboard only";
	public static final String TEST_KEYBOARD_ENTER_KEY = " ( with ENTER key )";
	public static final String TEST_KEYBOARD_SPACE_KEY = " ( with SPACE key )";
			
	public static final String WEBDRIVERS_FOLDER = "webdrivers/";	
	public static final String WEBDRIVER_CHROME_KEY = "webdriver.chrome.driver";	
	public static final String WEBDRIVER_FIREFOX_KEY = "webdriver.gecko.driver";	
	public static final String WEBDRIVER_EDGE_KEY = "webdriver.edge.driver";

	public static final String DEBUG_FOUND = "Found ";
	public static final String DEBUG_ELEMENT_NAMED_AN_ITEM = " element named 'anItem'";

}
