package Pine360TestSuite;
import FrameworkLibrary.FrameworkLibrary;
import ProjectCommonLibraries.Pine360Common;

import java.util.ArrayList;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

public class Pine360UsersLoginLoadTest
{
	FrameworkLibrary obj_Fl=new FrameworkLibrary();
	Pine360Common obj_Pine=new Pine360Common();
	private WebDriver wb_driver;
	String str_ConfigFilePath=System.getProperty("user.dir") + "\\InputData\\config.xml";
	String str_TestDataExcelPath=System.getProperty("user.dir") + "\\InputData\\TestData.xls";
	String str_browserdriverPath=System.getProperty("user.dir") + "\\BrowserDriverServer";
	ArrayList<String> arrL_LoginData = new ArrayList<String>();
	
	@Parameters("browser")
	
	@BeforeTest
	public void beforeTest(String browser) 
	{
		if (browser.equalsIgnoreCase("firefox"))
        {
			wb_driver = new FirefoxDriver();
        }
        else if (browser.equalsIgnoreCase("chrome"))
        {
        	// Set Path for the executable file
        	System.setProperty("webdriver.chrome.driver",str_browserdriverPath + "\\chromedriver.exe");
        	wb_driver = new ChromeDriver();
        } 
        else if (browser.equalsIgnoreCase("ie")) 
        {
        	// Set Path for the executable file
        	System.setProperty("webdriver.ie.driver", str_browserdriverPath + "\\IEDriverServer.exe");
        	wb_driver = new InternetExplorerDriver();
        }
        else
        {
        	throw new IllegalArgumentException("The Browser Type is Undefined");
        }
        arrL_LoginData=obj_Fl.ComReadConfigFile(str_ConfigFilePath);
		wb_driver.get(arrL_LoginData.get(0));
	}
	
	@Test
    public void login() throws InterruptedException 
    {
    	obj_Pine.Pine360Login(wb_driver, arrL_LoginData.get(1),arrL_LoginData.get(2),arrL_LoginData.get(3));
    }
	
	@AfterClass
    public void afterClass() 
    {
    	try
    	{
    		wb_driver.quit();
        }
    	catch (Exception e)
    	{
    		wb_driver = null;
        }
    }

}
