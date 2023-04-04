public class   UserRequirement3_Test
{
    static Logger logger = LoggerFactory.getLogger(jl.project.__commontests.tests.UserRequirement3_Test.class);
    static WebDriver driver;
    static Robot robot;

    @Test
	public void hideAndDisplayItem_UsingClicks()
	{
		boolean isTestSuccessful = false;
		isTestSuccessful = TestsUtilWithClicks.hideAndDisplayItem_UsingClicks(logger, driver, robot);	
		assertThat(isTestSuccessful).isTrue();
		
	}	
}