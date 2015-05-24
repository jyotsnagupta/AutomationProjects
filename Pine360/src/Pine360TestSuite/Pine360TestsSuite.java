package Pine360TestSuite;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import FrameworkLibrary.FrameworkLibrary;
import ProjectCommonLibraries.Pine360Common;

public class Pine360TestsSuite 
{
	FrameworkLibrary obj_Fl=new FrameworkLibrary();
	Pine360Common obj_Pine=new Pine360Common();
	private WebDriver wb_driver;
	String str_TestDataExcelPath=System.getProperty("user.dir") + "\\InputData\\TestData.xls";
	String str_ConfigFilePath=System.getProperty("user.dir") + "\\InputData\\config.xml";;
	String str_browserdriverPath=System.getProperty("user.dir") + "\\BrowserDriverServer";
	ArrayList<String> arrL_Failures=new ArrayList<String>();
	ArrayList<String> arrL_LoginData = new ArrayList<String>();
	
	@Parameters("browser")
	
	@BeforeMethod
	public void beforeMethod(String str_browser)
	{
		if (str_browser.equalsIgnoreCase("firefox"))
        {
			wb_driver = new FirefoxDriver();
        }
        else if (str_browser.equalsIgnoreCase("chrome"))
        {
        	// Set Path for the executable file
        	System.setProperty("webdriver.chrome.driver",str_browserdriverPath + "\\chromedriver.exe");
        	wb_driver = new ChromeDriver();
        } 
        else if (str_browser.equalsIgnoreCase("ie")) 
        {
        	// Set Path for the executable file
        	System.setProperty("webdriver.ie.driver", str_browserdriverPath + "\\IEDriverServer.exe");
        	wb_driver = new InternetExplorerDriver();
        }
        else
        {
        	throw new IllegalArgumentException("The Browser Type is Undefined");
        }
		wb_driver.manage().window().maximize();
		wb_driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        arrL_LoginData=obj_Fl.ComReadConfigFile(str_ConfigFilePath);
		wb_driver.get(arrL_LoginData.get(0));
		//wb_driver=obj_Fl.ComOpenURL(arrL_LoginData.get(0));
		obj_Pine.Pine360Login(wb_driver, arrL_LoginData.get(1),arrL_LoginData.get(2),arrL_LoginData.get(3));
		obj_Pine.Pine360SelectMerchant(wb_driver, arrL_LoginData.get(4));
	}

	@AfterMethod
	public void afterMethod()
	{
		obj_Pine.Pine360ClickMenu(wb_driver, "Logout");
		wb_driver.quit();
		arrL_Failures.clear();
	}
	
	@Test
	public void CreateMembership()
	{
		ArrayList<String> arrL_MembershipList = new ArrayList<String>();
		arrL_MembershipList=obj_Fl.ComReadExcel(str_TestDataExcelPath, "createMembership");
		
		String[] arrS_membershipDetails=new String[]{};
		for(int i_i_count=0;i_i_count < arrL_MembershipList.size();i_i_count++)
		{
			arrS_membershipDetails=arrL_MembershipList.get(i_i_count).split("\\;",-1);
			if(!obj_Pine.Pine360CreateMembership(wb_driver, arrS_membershipDetails))
			{
				arrL_Failures.add("Create Membership Failed for:" + arrS_membershipDetails[0] + ". ");
				continue;
			}
		}
		
		//List Failed
		if(arrL_MembershipList.size() < 1)
		{
			Assert.fail("****** START OF LOG ******" + "No Test Data Present" + "****** END OF LOG ******");
		}
		else if(obj_Fl.ComStringConcatenate(arrL_Failures).length() > 0)
		{
			Assert.fail("****** START OF LOG ******" + obj_Fl.ComStringConcatenate(arrL_Failures) + "****** END OF LOG ******");
		}
		
	}
	
	@Test
	public void CreateCardTier()
	{
		ArrayList<String> arrL_TierList = new ArrayList<String>();
		arrL_TierList=obj_Fl.ComReadExcel(str_TestDataExcelPath, "createCardTier");
		
		String[] arrS_Tier=new String[]{};
		for(int i_count=0;i_count < arrL_TierList.size();i_count++)
		{
			arrS_Tier=arrL_TierList.get(i_count).split("\\;",-1);
			if(!obj_Pine.Pine360CreateCardTier(wb_driver, arrS_Tier))
			{
				arrL_Failures.add("Create Card Tier Failed for:" + arrS_Tier[1] + ". ");
				continue;
			}
		}
		
		//List Failed 
		if(arrL_TierList.size() < 1)
		{
			Assert.fail("****** START OF LOG ******" + "No Test Data Present" + "****** END OF LOG ******");
		}
		else if(obj_Fl.ComStringConcatenate(arrL_Failures).length() > 0)
		{
			Assert.fail("****** START OF LOG ******" + obj_Fl.ComStringConcatenate(arrL_Failures) + "****** END OF LOG ******");
		}
		arrL_Failures.clear();
	}
	
	@Test
	public void CreateBinRangeProduct()
	{
		ArrayList<String> arrL_ProductList = new ArrayList<String>();
		arrL_ProductList=obj_Fl.ComReadExcel(str_TestDataExcelPath, "createBinProduct");
		obj_Pine.Pine360ClickMenu(wb_driver, "Change Merchant");
		obj_Pine.Pine360SelectMerchant(wb_driver, "Admin");
		
		String[] arrS_Product=new String[]{};
		for(int i_count=0;i_count < arrL_ProductList.size();i_count++)
		{
			arrS_Product=arrL_ProductList.get(i_count).split("\\;",-1);
			if(!obj_Pine.Pine360CreateBinRangeProduct(wb_driver, arrS_Product))
			{
				arrL_Failures.add("Create BinRangeProduct Failed for:" + arrS_Product[2] + ".");
				continue;
			}
		}
		
		//List Failed
		if(arrL_ProductList.size() < 1)
		{
			Assert.fail("****** START OF LOG ******" + "No Test Data Present" + "****** END OF LOG ******");
		}
		else if(obj_Fl.ComStringConcatenate(arrL_Failures).length() > 0)
		{
			Assert.fail("****** START OF LOG ******" + obj_Fl.ComStringConcatenate(arrL_Failures) + "****** END OF LOG ******");
		}
		arrL_Failures.clear();
	}
	
	@Test
	public void CreateBinRangeCorporate()
	{
		ArrayList<String> arrL_CorporateList = new ArrayList<String>();
		arrL_CorporateList=obj_Fl.ComReadExcel(str_TestDataExcelPath, "createBinCorporate");
		obj_Pine.Pine360ClickMenu(wb_driver, "Change Merchant");
		obj_Pine.Pine360SelectMerchant(wb_driver, "Admin");
		
		String[] arrS_Corporate=new String[]{};
		for(int i_count=0;i_count < arrL_CorporateList.size();i_count++)
		{
			arrS_Corporate=arrL_CorporateList.get(i_count).split("\\;",-1);
			if(!obj_Pine.Pine360CreateBinRangeCorporate(wb_driver, arrS_Corporate))
			{
				arrL_Failures.add("Create BinRangeCorporate Failed for:" + arrS_Corporate[2] + ".");
				continue;
			}
		}
		
		//List Failed
		if(arrL_CorporateList.size() < 1)
		{
			Assert.fail("****** START OF LOG ******" + "No Test Data Present" + "****** END OF LOG ******");
		}
		else if(obj_Fl.ComStringConcatenate(arrL_Failures).length() > 0)
		{
			Assert.fail("****** START OF LOG ******" + obj_Fl.ComStringConcatenate(arrL_Failures) + "****** END OF LOG ******");
		}
		arrL_Failures.clear();
	}

	@Test
	public void CreateBinRange()
	{
		ArrayList<String> arrL_binrangeList = new ArrayList<String>();
		arrL_binrangeList=obj_Fl.ComReadExcel(str_TestDataExcelPath, "createBinRange");
		obj_Pine.Pine360ClickMenu(wb_driver, "Change Merchant");
		obj_Pine.Pine360SelectMerchant(wb_driver, "Admin");
		
		String[] arrS_binRange=new String[]{};
		for(int i_count=0;i_count < arrL_binrangeList.size();i_count++)
		{
			arrS_binRange=arrL_binrangeList.get(i_count).split("\\;",-1);
			if(!obj_Pine.Pine360CreateBinRange(wb_driver, arrS_binRange))
			{
				arrL_Failures.add("Create BinRange Failed for:" + arrS_binRange[0] + ".");
				continue;
			}
		}
		
		//List Failed
		if(arrL_binrangeList.size() < 1)
		{
			Assert.fail("****** START OF LOG ******" + "No Test Data Present" + "****** END OF LOG ******");
		}
		else if(obj_Fl.ComStringConcatenate(arrL_Failures).length() > 0)
		{
			Assert.fail("****** START OF LOG ******" + obj_Fl.ComStringConcatenate(arrL_Failures) + "****** END OF LOG ******");
		}
		arrL_Failures.clear();
	}
	
	@Test
	public void CreateDenomination()
	{
		ArrayList<String> arrL_denominationList = new ArrayList<String>();
		arrL_denominationList=obj_Fl.ComReadExcel(str_TestDataExcelPath, "createDenomination");
		
		String[] arrS_denomination=new String[]{};
		for(int i_count=0;i_count < arrL_denominationList.size();i_count++)
		{
			arrS_denomination=arrL_denominationList.get(i_count).split("\\;",-1);
			if(!obj_Pine.Pine360CreateDenomination(wb_driver, arrS_denomination))
			{
				arrL_Failures.add("Create Denomination Failed for:" + arrS_denomination[0] + ".");
				continue;
			}
		}
		
		//List Failed
		if(arrL_denominationList.size() < 1)
		{
			Assert.fail("****** START OF LOG ******" + "No Test Data Present" + "****** END OF LOG ******");
		}
		else if(obj_Fl.ComStringConcatenate(arrL_Failures).length() > 0)
		{
			Assert.fail("****** START OF LOG ******" + obj_Fl.ComStringConcatenate(arrL_Failures) + "****** END OF LOG ******");
		}
		arrL_Failures.clear();
	}
	
	@Test
	public void CreateStockOrder() 
	{
		ArrayList<String> arrL_stockorderList = new ArrayList<String>();
		arrL_stockorderList=obj_Fl.ComReadExcel(str_TestDataExcelPath, "createOrder");
		
		String[] arrS_orderDetails=new String[]{};
		for(int i_i_count=0;i_i_count < arrL_stockorderList.size();i_i_count++)
		{
			arrS_orderDetails=arrL_stockorderList.get(i_i_count).split("\\;",-1);
			if(!obj_Pine.Pine360ClickMenu(wb_driver, "Change Merchant"))
			{
				arrL_Failures.add("Not Able to Click Change Merchant Option.");
				continue;
			}
			if(!obj_Pine.Pine360SelectMerchant(wb_driver, arrL_LoginData.get(4)))
			{
				arrL_Failures.add("Not Able to Select Merchant:" + arrL_LoginData.get(4) +  " from Drpdown.");
				continue;
			}
			if(!obj_Pine.Pine360CreateOrder(wb_driver, arrS_orderDetails))
			{
				arrL_Failures.add("Create Order Failed for:" + arrS_orderDetails[0] + ". ");
				continue;
			}
			if(!obj_Pine.Pine360ClickMenu(wb_driver, "Change Merchant"))
			{
				arrL_Failures.add("Not Able to Click Change Merchant Option.");
				continue;
			}
			if(!obj_Pine.Pine360SelectMerchant(wb_driver, "Admin"))
			{
				arrL_Failures.add("Not Able to Select Merchant: Admin from Drpdown.");
				continue;
			}
			
			String[] str_approveAction=arrS_orderDetails[5].split("/");
			for(int i_count=0;i_count < str_approveAction.length; i_count++)
			{
				if(!obj_Pine.Pine360ClickStockOrder(wb_driver, arrS_orderDetails[0], str_approveAction[i_count])) //Approve Action
				{
					arrL_Failures.add("Not Able to update Stock Order with Action: " + str_approveAction[i_count]);
					continue;
				}
			}
			
			if(str_approveAction[str_approveAction.length -1].equalsIgnoreCase("cancel") || str_approveAction[str_approveAction.length -1].equalsIgnoreCase("view"))
			{
				continue;
			}
			
			//Allocate
			if(!obj_Pine.Pine360AllocateOrCancelorReceiveStockOrder(wb_driver, arrS_orderDetails[6],arrS_orderDetails[12]))
			{
				arrL_Failures.add("Not Able to update Stock Order with Action: " + arrS_orderDetails[6]);
				continue;
			}
			if(arrS_orderDetails[6].equalsIgnoreCase("cancel"))
			{
				continue;
			}
			
			
			//Dispatch
			if(!obj_Pine.Pine360ClickStockOrder(wb_driver, arrS_orderDetails[0],"Edit" )) //Edit Order
			{
				arrL_Failures.add("Not Able to Edit Stock Order");
				continue;
			}
			if(!obj_Pine.Pine360DispatchStockOrder(wb_driver,arrS_orderDetails[8],arrS_orderDetails[9],arrS_orderDetails[13]))
			{
				arrL_Failures.add("Not Able to Dispatch Stock Order");
				continue;
			}
			
			
			if(!obj_Pine.Pine360ClickMenu(wb_driver, "Change Merchant"))
			{
				arrL_Failures.add("Not Able to Click Change Merchant Option.");
				continue;
			}
			if(!obj_Pine.Pine360SelectMerchant(wb_driver, arrL_LoginData.get(4)))
			{
				arrL_Failures.add("Not Able to Select Merchant:" + arrL_LoginData.get(4) +  " from Drpdown.");
				continue;
			}
			
			//Receive Order
			if(!obj_Pine.Pine360ClickStockOrder(wb_driver, arrS_orderDetails[0],"Edit" )) //Edit Order
			{
				arrL_Failures.add("Not Able to Edit Stock Order");
				continue;
			}
			if(!obj_Pine.Pine360AllocateOrCancelorReceiveStockOrder(wb_driver, arrS_orderDetails[10],arrS_orderDetails[14]))
			{
				arrL_Failures.add("Not Able to update Stock Order with Action: " + arrS_orderDetails[6]);
				continue;
			}
		}
		
		//List Failed
		if(arrL_stockorderList.size() < 1)
		{
			Assert.fail("****** START OF LOG ******" + "No Test Data Present" + "****** END OF LOG ******");
		}
		else if(obj_Fl.ComStringConcatenate(arrL_Failures).length() > 0)
		{
			Assert.fail("****** START OF LOG ******" + obj_Fl.ComStringConcatenate(arrL_Failures) + "****** END OF LOG ******");
		}
		arrL_Failures.clear();
		
	}

	@Test
	public void UnblockandResetPassword()
	{
		ArrayList<String> arrL_userList = new ArrayList<String>();
		arrL_userList=obj_Fl.ComReadExcel(str_TestDataExcelPath, "UnblockResetPassword");
		
		String[] arrS_userDetails=new String[]{};
		for(int i_i_count=0;i_i_count < arrL_userList.size();i_i_count++)
		{
			arrS_userDetails=arrL_userList.get(i_i_count).split("\\;",-1);
			if(!obj_Pine.Pine360UnblockAndResetPassword(wb_driver, arrS_userDetails))
			{
				arrL_Failures.add("Unblock and Reset Password Password failed for:" + arrS_userDetails[0] + ". ");
				continue;
			}
		}
		
		//List Failed
		if(arrL_userList.size() < 1)
		{
			Assert.fail("****** START OF LOG ******" + "No Test Data Present" + "****** END OF LOG ******");
		}
		else if(obj_Fl.ComStringConcatenate(arrL_Failures).length() > 0)
		{
			Assert.fail("****** START OF LOG ******" + obj_Fl.ComStringConcatenate(arrL_Failures) + "****** END OF LOG ******");
		}
		arrL_Failures.clear();
	}
	
	@Test
	public void CreateUser()
	{
		ArrayList<String> arrL_userList = new ArrayList<String>();
		arrL_userList=obj_Fl.ComReadExcel(str_TestDataExcelPath, "createUser");
		
		String[] arrS_userDetails=new String[]{};
		for(int i_i_count=0;i_i_count < arrL_userList.size();i_i_count++)
		{
			arrS_userDetails=arrL_userList.get(i_i_count).split("\\;",-1);
			if(!obj_Pine.Pine360CreateUser(wb_driver, arrS_userDetails))
			{
				arrL_Failures.add("Unable to create user: " + arrS_userDetails[2] + ".");
				continue;
			}
		}
		
		//List Failed
		if(arrL_userList.size() < 1)
		{
			Assert.fail("****** START OF LOG ******" + "No Test Data Present" + "****** END OF LOG ******");
		}
		else if(obj_Fl.ComStringConcatenate(arrL_Failures).length() > 0)
		{
			Assert.fail("****** START OF LOG ******" + obj_Fl.ComStringConcatenate(arrL_Failures) + "****** END OF LOG ******");
		}
		arrL_Failures.clear();
	}
	
	@Test
	public void CreateCustomer()
	{
		ArrayList<String> arrL_customerList = new ArrayList<String>();
		arrL_customerList=obj_Fl.ComReadExcel(str_TestDataExcelPath, "createCustomer");
		
		String[] arrS_customerDetails=new String[]{};
		for(int i_i_count=0;i_i_count < arrL_customerList.size();i_i_count++)
		{
			arrS_customerDetails=arrL_customerList.get(i_i_count).split("\\;",-1);
			if(!obj_Pine.Pine360CreateCustomer(wb_driver, arrS_customerDetails))
			{
				arrL_Failures.add("Unable to create customer: " + arrS_customerDetails[1] + " " + arrS_customerDetails[2] + ".");
				continue;
			}
		}
		
		//List Failed
		if(arrL_customerList.size() < 1)
		{
			Assert.fail("****** START OF LOG ******" + "No Test Data Present" + "****** END OF LOG ******");
		}
		else if(obj_Fl.ComStringConcatenate(arrL_Failures).length() > 0)
		{
			Assert.fail("****** START OF LOG ******" + obj_Fl.ComStringConcatenate(arrL_Failures) + "****** END OF LOG ******");
		}
		arrL_Failures.clear();
	}
	
	@Test
	public void CreateCardScheme()
	{
		ArrayList<String> arrL_schemeList = new ArrayList<String>();
		arrL_schemeList=obj_Fl.ComReadExcel(str_TestDataExcelPath, "createCardScheme");
		
		String[] arrS_schemeDetails=new String[]{};
		for(int i_i_count=0;i_i_count < arrL_schemeList.size();i_i_count++)
		{
			arrS_schemeDetails=arrL_schemeList.get(i_i_count).split("\\;",-1);
			if(!obj_Pine.Pine360CreateCardScheme(wb_driver, arrS_schemeDetails))
			{
				arrL_Failures.add("Create Card Scheme failed for:" + arrS_schemeDetails[0] + ". ");
				continue;
			}
		}
		
		//List Failed
		if(arrL_schemeList.size() < 1)
		{
			Assert.fail("****** START OF LOG ******" + "No Test Data Present" + "****** END OF LOG ******");
		}
		else if(obj_Fl.ComStringConcatenate(arrL_Failures).length() > 0)
		{
			Assert.fail("****** START OF LOG ******" + obj_Fl.ComStringConcatenate(arrL_Failures) + "****** END OF LOG ******");
		}
		arrL_Failures.clear();
	}
	
	@Test
	public void EditUser()
	{
		ArrayList<String> arrL_userList = new ArrayList<String>();
		arrL_userList=obj_Fl.ComReadExcel(str_TestDataExcelPath, "editUser");
		
		String[] arrS_userDetails=new String[]{};
		for(int i_i_count=0;i_i_count < arrL_userList.size();i_i_count++)
		{
			arrS_userDetails=arrL_userList.get(i_i_count).split("\\;",-1);
			if(!obj_Pine.Pine360EditUser(wb_driver, arrS_userDetails))
			{
				arrL_Failures.add("Unable to create user: " + arrS_userDetails[2] + ".");
				continue;
			}
		}
		
		//List Failed
		if(arrL_userList.size() < 1)
		{
			Assert.fail("****** START OF LOG ******" + "No Test Data Present" + "****** END OF LOG ******");
		}
		else if(obj_Fl.ComStringConcatenate(arrL_Failures).length() > 0)
		{
			Assert.fail("****** START OF LOG ******" + obj_Fl.ComStringConcatenate(arrL_Failures) + "****** END OF LOG ******");
		}
		arrL_Failures.clear();
	}
	
	@Test
	public void EditCustomer()
	{
		ArrayList<String> arrL_customerList = new ArrayList<String>();
		arrL_customerList=obj_Fl.ComReadExcel(str_TestDataExcelPath, "editCustomer");
		
		String[] arrS_customerDetails=new String[]{};
		for(int i_i_count=0;i_i_count < arrL_customerList.size();i_i_count++)
		{
			arrS_customerDetails=arrL_customerList.get(i_i_count).split("\\;",-1);
			if(!obj_Pine.Pine360EditCustomer(wb_driver, arrS_customerDetails))
			{
				arrL_Failures.add("Unable to create customer: " + arrS_customerDetails[1] + " " + arrS_customerDetails[2] + ".");
				continue;
			}
		}
		
		//List Failed
		if(arrL_customerList.size() < 1)
		{
			Assert.fail("****** START OF LOG ******" + "No Test Data Present" + "****** END OF LOG ******");
		}
		else if(obj_Fl.ComStringConcatenate(arrL_Failures).length() > 0)
		{
			Assert.fail("****** START OF LOG ******" + obj_Fl.ComStringConcatenate(arrL_Failures) + "****** END OF LOG ******");
		}
		arrL_Failures.clear();
	}
	
	@Test
	public void CreateRole()
	{
		ArrayList<String> arrL_roleList = new ArrayList<String>();
		arrL_roleList=obj_Fl.ComReadExcel(str_TestDataExcelPath, "createRole");
		
		String[] arrS_roleDetails=new String[]{};
		for(int i_i_count=0;i_i_count < arrL_roleList.size();i_i_count++)
		{
			arrS_roleDetails=arrL_roleList.get(i_i_count).split("\\;",-1);
			if(!obj_Pine.Pine360CreateRole(wb_driver, arrS_roleDetails))
			{
				arrL_Failures.add("Unable to create customer: " + arrS_roleDetails[1] + " " + arrS_roleDetails[2] + ".");
				continue;
			}
		}
		
		//List Failed
		if(arrL_roleList.size() < 1)
		{
			Assert.fail("****** START OF LOG ******" + "No Test Data Present" + "****** END OF LOG ******");
		}
		else if(obj_Fl.ComStringConcatenate(arrL_Failures).length() > 0)
		{
			Assert.fail("****** START OF LOG ******" + obj_Fl.ComStringConcatenate(arrL_Failures) + "****** END OF LOG ******");
		}
		arrL_Failures.clear();
	}
	/*
	@Test
	public void createTSBUserWithoutCard()
	{
		ArrayList<String> Users = new ArrayList<String>();
		Users=obj_Fl.ComReadExcel(str_TestDataExcelPath,"createtsbuser");
		
		String[] Userdetails=new String[]{};
		for(int i_count=0;i_count<Users.size();i_count++)
		{
			wb_driver.quit();
			ArrayList<String> arrLoginData = new ArrayList<String>();
			arrLoginData=obj_Fl.ComReadConfigFile(str_ConfigFilePath);
			wb_driver=obj_Fl.ComOpenURL(arrLoginData.get(0));
			
			WebElement wbbtncreateAci_count=wb_driver.findElement(By.xpath("//input[@class='sign_in_button'][@value='Create An Aci_count']"));
			wbbtncreateAci_count.click();
			
			Userdetails=Users.get(i_count).split(";");
			
			WebElement wbfirstname=wb_driver.findElement(By.id("customerName"));
			obj_Fl.ComEnterText(wbfirstname,Userdetails[0]);
			
			WebElement wblastname=wb_driver.findElement(By.id("lastName"));
			obj_Fl.ComEnterText(wblastname,Userdetails[1]);
			
			WebElement wbusername=wb_driver.findElement(By.id("userName"));
			obj_Fl.ComEnterText(wbusername,Userdetails[2]);
			
			WebElement wbpwd=wb_driver.findElement(By.id("custpassword"));
			obj_Fl.ComEnterText(wbpwd,Userdetails[3]);
			
			WebElement wbconfirmpwd=wb_driver.findElement(By.id("custpasswordconfirm"));
			obj_Fl.ComEnterText(wbconfirmpwd,Userdetails[4]);
			
			WebElement wbemail=wb_driver.findElement(By.id("customerEmail"));
			obj_Fl.ComEnterText(wbemail,Userdetails[5]);
			
			WebElement wbmobile=wb_driver.findElement(By.id("mobile"));
			obj_Fl.ComEnterText(wbmobile,Userdetails[6]);
			
			WebElement wbpfmd=wb_driver.findElement(By.id("bothPreference"));
			wbpfmd.click();
			
			WebElement wbbtnSubmit=wb_driver.findElement(By.xpath("//input[@class='sign_in_button'][@value='Create An Aci_count']"));
			wbbtnSubmit.click();
			
		}
	}*/
}
