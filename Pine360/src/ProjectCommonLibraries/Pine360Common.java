package ProjectCommonLibraries;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import FrameworkLibrary.FrameworkLibrary;

public class Pine360Common 
{
	//*********************************************************************************************************************
	//* Function Name    : Pine360Login
	//*
	//* Description      : Login to Pine 360 web page
	//*
	//* Input            : wb_driver      	[in] Required Object
	//*					 : str_userName    	[in] Required Object
	//*					 : str_passWord    	[in] Required Object
	//*					 : str_merchantName [in] Required Object
	//*                   
	//* Output           : ArrayList [out] (Return the Node values for URL, UserName, passWord)
	//*
	//* Example          : Pine360Login(wb_driver,"system","system");
	//*                    
	//*********************************************************************************************************************
	//* Creation By      : Saurabh Agrawal 
	//* Date of Creation : 4 April, 2013 
	//*********************************************************************************************************************
	public boolean Pine360Login(WebDriver wb_driver,String str_userName,String str_passWord,String str_merchantName )
	{
		FrameworkLibrary obj_Fl=new FrameworkLibrary();
		try
		{
			WebElement wbE_UserName=wb_driver.findElement(By.name("uname"));
			obj_Fl.ComEnterText(wbE_UserName,str_userName);
			
			WebElement wbE_Password=wb_driver.findElement(By.name("j_password"));
			obj_Fl.ComEnterText(wbE_Password,str_passWord);
			
			WebElement wbE_btnLogin;
			if(str_merchantName.equalsIgnoreCase("tatastarbucks"))
			{
				wbE_btnLogin=wb_driver.findElement(By.xpath("//input[@class='sign_in_button'][@value='Sign in']"));
			}
			else if (str_merchantName.equalsIgnoreCase("religare"))
			{
				wbE_btnLogin=wb_driver.findElement(By.xpath("//input[@value='Submit']"));
			}
			else
			{
				wbE_btnLogin=wb_driver.findElement(By.xpath("//img[@alt='Login']"));
			}
			wbE_btnLogin.click();
			
			return true;
		}
		catch(Exception e)
		{
			obj_Fl.ComPrintException("Unable to Login to portal", e.toString());
			return false;
		}
	}
	
	//*********************************************************************************************************************
	//* Function Name    : Pine360ClickTab
	//*
	//* Description      : Click the Header Tab
	//*
	//* Input            : wb_driver      [in] Required Object
	//*					 : arrS_TabList     [in] Required Object
	//*                   
	//* Output           : None
	//*
	//* Example          : Pine360ClickTab(wb_driver,{"Reports"});,Pine360ClickTab(wb_driver,{"More","Create User"});
	//*                    
	//*********************************************************************************************************************
	//* Creation By      : Saurabh Agrawal 
	//* Date of Creation : 4 April, 2013 
	//*********************************************************************************************************************
	public boolean Pine360ClickTab(WebDriver wb_driver, String[] arrS_TabList)
	{
		FrameworkLibrary obj_Fl=new FrameworkLibrary();
		try
		{
			if(arrS_TabList.length < 2)
			{
				WebElement wbE_Menu=wb_driver.findElement(By.linkText(arrS_TabList[0]));
				wbE_Menu.click();
			}
			else
			{
				WebElement wbE_TabList=wb_driver.findElement(By.className("navigationBlueTabs"));
				WebElement wbE_DropDown=wbE_TabList.findElement(By.className("dropdown"));
				WebElement wbE_MenuList=wbE_DropDown.findElement(By.id("one-ddheader"));
				Actions builder=new Actions(wb_driver);
		        builder.moveToElement(wbE_MenuList).build().perform();
		        Thread.sleep(2000);
				WebElement wbE_SubMenu=wb_driver.findElement(By.ByLinkText.linkText(arrS_TabList[1]));
				wbE_SubMenu.click();
			}
			return true;
		}
		catch(Exception e)
		{
			String str_Failmsg="Unable to click: "+ arrS_TabList[0]+"->"+arrS_TabList[1];
			obj_Fl.ComPrintException(str_Failmsg, e.toString());
			return false;
		}
	}
	
	//*********************************************************************************************************************
	//* Function Name    : Pine360Search
	//*
	//* Description      : Search details like users etc
	//*
	//* Input            : wb_driver      [in] Required Object
	//*					 : searchField [in] Required Object
	//*					 : searchText  [in] Required Object
	//*                   
	//* Output           : None
	//*
	//* Example          : Pine360Search(wb_driver,{"userNameSearch","firstNameSearch"},{"system","system"});
	//*                    
	//*********************************************************************************************************************
	//* Creation By      : Saurabh Agrawal 
	//* Date of Creation : 4 April, 2013 
	//*********************************************************************************************************************
	public boolean Pine360Search(WebDriver wb_driver,String[] arrS_searchField,String[] arrS_searchText)
	{
		FrameworkLibrary obj_Fl=new FrameworkLibrary();
		try
		{
			WebElement wbE_Search,wbE_SchButton;
			int i_lensearchField=arrS_searchField.length;
			int i_lensearchText=arrS_searchText.length;
			if(i_lensearchField==i_lensearchText)
			{
				for(int i_count=0;i_count<i_lensearchField;i_count++)
				{
					wbE_Search=wb_driver.findElement(By.id(arrS_searchField[i_count]));
					obj_Fl.ComEnterText(wbE_Search, arrS_searchText[i_count]);
				}
			}
			wbE_SchButton=wb_driver.findElement(By.xpath("//img[@alt='Search']"));
			wbE_SchButton.click();
			return true;
		}
		catch(Exception e)
		{
			obj_Fl.ComPrintException("Unable to Search", e.toString());
			return false;
		}
	}
	
	//*********************************************************************************************************************
	//* Function Name    : Pine360CreateUser
	//*
	//* Description      : Create a New User [More -> Create User]
	//*
	//* Input            : wb_driver			[in] Required Object
	//*					 : arrS_userDetails 	[in] Required Object
	//*                   
	//* Output           : None
	//*
	//* Example          : Pine360CreateUser(wb_driver,"{User details in array}");
	//*                    
	//*********************************************************************************************************************
	//* Creation By      : Saurabh Agrawal
	//* Date of Creation : 14 April, 2014 
	//*********************************************************************************************************************
	public boolean Pine360CreateUser(WebDriver wb_driver, String[] arrS_userDetails)
	{
		FrameworkLibrary obj_Fl=new FrameworkLibrary();
		Pine360Common obj_Pine=new Pine360Common();
		try
		{
			String[] arrS_TabList=new String[]{"More","Create User"};
			if(!obj_Pine.Pine360ClickTab(wb_driver,arrS_TabList))
			{
				return false;
			}
			
			WebElement wbE_firstName = wb_driver.findElement(By.id("customerName"));
			obj_Fl.ComEnterText(wbE_firstName,arrS_userDetails[0]);
			
        	WebElement wbE_lastName = wb_driver.findElement(By.id("lastName"));
        	obj_Fl.ComEnterText(wbE_lastName,arrS_userDetails[1]);
        	
        	WebElement wbE_userName = wb_driver.findElement(By.id("userName"));
        	obj_Fl.ComEnterText(wbE_userName,arrS_userDetails[2]);
        	
        	WebElement wbE_password = wb_driver.findElement(By.id("custpassword"));
        	obj_Fl.ComEnterText(wbE_password,arrS_userDetails[3]);
        	
        	WebElement wbE_userStatus = wb_driver.findElement(By.id("status"));
        	wbE_userStatus.sendKeys(arrS_userDetails[4]);
        	
        	WebElement wbE_dob=wb_driver.findElement(By.xpath("//img[@alt='Birth Date']"));
        	wbE_dob.click();
        	obj_Fl.ComSetDate(wb_driver,arrS_userDetails[5],"MM/dd/yyyy");
        	
        	WebElement wbE_state = wb_driver.findElement(By.id("state"));
        	obj_Fl.ComSelectDropDownValue(wb_driver, wbE_state, arrS_userDetails[6]);
        	Thread.sleep(2000);
        	
        	WebElement wbE_city = wb_driver.findElement(By.id("city"));
        	wbE_city.sendKeys(arrS_userDetails[7]);
        	
        	WebElement wbE_email = wb_driver.findElement(By.id("customerEmail"));
        	obj_Fl.ComEnterText(wbE_email,arrS_userDetails[8]);
        	
        	WebElement wbE_phone=wb_driver.findElement(By.id("profilePhone"));
        	obj_Fl.ComEnterText(wbE_phone,arrS_userDetails[9]);
        	
        	WebElement wbE_role = wb_driver.findElement(By.id("role"));
        	wbE_role.sendKeys(arrS_userDetails[10]);
        	
        	WebElement wbE_btnSave=wb_driver.findElement(By.xpath("//img[@alt='Save']"));
	        wbE_btnSave.click();
        	
        	if(obj_Fl.ComCompareDivLinkText(wb_driver, "successMsgDiv",arrS_userDetails[11] ))
			{
				return true;
			}
	        return false;
		}
		catch(Exception e)
		{
			obj_Fl.ComPrintException("Unable to create user",  e.toString());
			return false;
		}
	}

	//*********************************************************************************************************************
	//* Function Name    : Pine360UnblockAndResetPassword
	//*
	//* Description      : Unblock and Reset Password for an existing user [More -> UnblockAndResetPassword]
	//*
	//* Input            : wb_driver			[in] Required Object
	//*					 : UserList 		[in] Required Object
	//*                   
	//* Output           : None
	//*
	//* Example          : Pine360UnblockAndResetPassword(wb_driver,{"saurabh","password"});
	//*                    
	//*********************************************************************************************************************
	//* Creation By      : Saurabh Agrawal
	//* Date of Creation : 14 April, 2014 
	//*********************************************************************************************************************
	public boolean Pine360UnblockAndResetPassword(WebDriver wb_driver,String[] arrS_userDetails)
	{
		FrameworkLibrary obj_Fl=new FrameworkLibrary();
		Pine360Common obj_Pine=new Pine360Common();
		try
		{
			String[] arrS_TabList=new String[]{"More","Unblock and Reset Password"};
			if(!obj_Pine.Pine360ClickTab(wb_driver,arrS_TabList))
			{
				return false;
			}
			
			WebElement wbE_userName = wb_driver.findElement(By.id("username"));
			obj_Fl.ComEnterText(wbE_userName,arrS_userDetails[0]);
			
        	WebElement wbE_password = wb_driver.findElement(By.id("Password"));
        	obj_Fl.ComEnterText(wbE_password,arrS_userDetails[2]);
        	
        	WebElement wbE_confirmPassword = wb_driver.findElement(By.id("C_Password"));
        	obj_Fl.ComEnterText(wbE_confirmPassword,arrS_userDetails[3]);
        	
        	WebElement wbE_chkBoxUnblock = wb_driver.findElement(By.id("unblockCustomer"));
        	if(Boolean.valueOf(arrS_userDetails[1]))
        	{
        		wbE_chkBoxUnblock.click();	
        	}
        	
        	WebElement wbE_btnSubmit=wb_driver.findElement(By.xpath("//img[@alt='Submit']"));
        	wbE_btnSubmit.click();
        	Thread.sleep(1000);
        	if(obj_Fl.ComCompareDivLinkText(wb_driver, "responseTxt",arrS_userDetails[4] ))
			{
				return true;
			}
	       return false;
		}
		catch(Exception e)
		{
			obj_Fl.ComPrintException("Unable to reset user password", e.toString());
			return false;
		}
	}
	
	//*********************************************************************************************************************
	//* Function Name    : Pine360ClickMenu
	//*
	//* Description      : Click the top link of Pine360 Page
	//*
	//* Input            : wb_driver			[in] Required Object
	//*					 : linkText 		[in] Required Object
	//*                   
	//* Output           : None
	//*
	//* Example          : Pine360ClickMenu(wb_driver,"Change Merchant");
	//*                    
	//*********************************************************************************************************************
	//* Creation By      : Saurabh Agrawal
	//* Date of Creation : 12 April, 2014 
	//*********************************************************************************************************************
	public boolean Pine360ClickMenu(WebDriver wb_driver, String str_linkText)
	{
		FrameworkLibrary obj_Fl=new FrameworkLibrary();
		try
		{
			WebElement wbE_Link=wb_driver.findElement(By.linkText(str_linkText));
			wbE_Link.click();
			return true;
		}
		catch(Exception e)
		{
			String msg="Unable to find:" + str_linkText;
			obj_Fl.ComPrintException(msg, e.toString());
			return false;
		}
	}
	
	//*********************************************************************************************************************
	//* Function Name    : Pine360SelectMerchant
	//*
	//* Description      : Select any merchant
	//*
	//* Input            : wb_driver			[in] Required Object
	//*					 : MerchantName 	[in] Required Object
	//*                   
	//* Output           : None
	//*
	//* Example          : Pine360SelectMerchant(wb_driver,"Religare");
	//*                    
	//*********************************************************************************************************************
	//* Creation By      : Saurabh Agrawal
	//* Date of Creation : 11 April, 2014 
	//*********************************************************************************************************************
	public boolean Pine360SelectMerchant(WebDriver wb_driver, String str_MerchantName)
	{
		FrameworkLibrary objFL=new FrameworkLibrary();
		try 
		{
			Pine360ClickMenu(wb_driver, "Change Merchant");
			WebElement wbE_MDropDown=wb_driver.findElement(By.id("merchant_id"));
			if(objFL.ComSelectDropDownValue(wb_driver,wbE_MDropDown, str_MerchantName))
			{
				WebElement wbE_btnSave=wb_driver.findElement(By.xpath("//input[@value='Save']"));
				wbE_btnSave.click();
				return true;
			}
			return false;
		}
		catch(Exception e)
		{
			String str_failMsg="Unable to Select Merchant:"+ str_MerchantName;
			objFL.ComPrintException(str_failMsg, e.toString());
			return false;
		}
	}
	
	//*********************************************************************************************************************
	//* Function Name    : Pine360CreateMembership
	//*
	//* Description      : Create Membership
	//*
	//* Input            : wb_driver						[in] Required Object
	//*					 : arrS_membershipDetails         	[in] Required Object
	//*
	//* Output           : None
	//*
	//* Example          : Pine360CreateMembership(wb_driver,{"Loyalty Membership","description","Loyalty","Card Membership created successfully"});
	//*                    
	//*********************************************************************************************************************
	//* Creation By      : Saurabh Agrawal
	//* Date of Creation : 11 April, 2014 
	//*********************************************************************************************************************
	public boolean Pine360CreateMembership(WebDriver wb_driver, String[] arrS_membershipDetails)
	{
		FrameworkLibrary obj_Fl=new FrameworkLibrary();
		Pine360Common obj_Pine=new Pine360Common();
		try
		{
			String[] arrS_TabList=new String[]{"More","Manage Memberships"};
			if(!obj_Pine.Pine360ClickTab(wb_driver,arrS_TabList))
			{
				return false;
			}
			WebElement wbE_MembershipName=wb_driver.findElement(By.id("cardMembership.name"));
			obj_Fl.ComEnterText(wbE_MembershipName,arrS_membershipDetails[0]);
			
			WebElement wbE_Description=wb_driver.findElement(By.id("cardMembership.description"));
			obj_Fl.ComEnterText(wbE_Description,arrS_membershipDetails[1]);
			
			WebElement wbE_Program=wb_driver.findElement(By.id("programTypeId"));
			obj_Fl.ComSelectDropDownValue(wb_driver, wbE_Program, arrS_membershipDetails[2]);
			
			WebElement wbE_btnCreate=wb_driver.findElement(By.xpath("//img[@alt='Create']"));
			wbE_btnCreate.click();
			if(obj_Fl.ComIsAlertPresent(wb_driver,arrS_membershipDetails[3]) || obj_Fl.ComCompareDivLinkText(wb_driver, "successMsgDiv", arrS_membershipDetails[3]) )
			{
				return true;
			}
			return false;
		}
		catch(Exception e)
		{
			String str_failMmsg="Unable to create membership:" + arrS_membershipDetails[0];
			obj_Fl.ComPrintException(str_failMmsg, e.toString());
			return false;
		}
	}

	//*********************************************************************************************************************
	//* Function Name    : Pine360CreateCardTier
	//*
	//* Description      : Create Card Tier
	//*
	//* Input            : wb_driver			[in] Required Object
	//*					 : arrS_tierDetails   	[in] Required Object
	//*
	//* Output           : None
	//*
	//* Example          : Pine360CreateCardTier(wb_driver,{"Loyalty Membership","Gold","Gold Tier","Tier created successfully")};
	//*                    
	//*********************************************************************************************************************
	//* Creation By      : Saurabh Agrawal
	//* Date of Creation : 14 April, 2014 
	//*********************************************************************************************************************
	public boolean Pine360CreateCardTier(WebDriver wb_driver,String[] arrS_tierDetails)
	{
		FrameworkLibrary obj_Fl=new FrameworkLibrary();
		Pine360Common obj_Pine=new Pine360Common();
		try
		{
			String[] arrS_TabList=new String[]{"More","Manage Tiers"};
			if(!obj_Pine.Pine360ClickTab(wb_driver,arrS_TabList))
			{
				return false;
			}
			WebElement wbE_Membership=wb_driver.findElement(By.id("membershipId"));
			obj_Fl.ComSelectDropDownValue(wb_driver, wbE_Membership, arrS_tierDetails[0]);
			
			WebElement wbE_TierName=wb_driver.findElement(By.id("membershipTier.name"));
			obj_Fl.ComEnterText(wbE_TierName,arrS_tierDetails[1]);
			
			WebElement wbE_Description=wb_driver.findElement(By.id("membershipTier.description"));
			obj_Fl.ComEnterText(wbE_Description,arrS_tierDetails[2]);
			
			WebElement wbE_btnCreate=wb_driver.findElement(By.xpath("//img[@alt='Create']"));
			wbE_btnCreate.click();
			if(obj_Fl.ComIsAlertPresent(wb_driver,arrS_tierDetails[3]) || obj_Fl.ComCompareDivLinkText(wb_driver, "successMsgDiv",arrS_tierDetails[3] ) )
			{
				return true;
			}
			return false;
		}
		catch(Exception e)
		{
			String str_failMsg="Unable to create card Tier:" + arrS_tierDetails[1];
			obj_Fl.ComPrintException(str_failMsg, e.toString());
			return false;
		}
	}
	
	//*********************************************************************************************************************
	//* Function Name    : Pine360CreateBinRangeProduct
	//*
	//* Description      : Create Card Tier
	//*
	//* Input            : wb_driver						[in] Required Object
	//*					 : arrS_binproductDetails         	[in] Required Object
	//*
	//* Output           : None
	//*
	//* Example          : Pine360CreateBinRangeProduct(wb_driver,{"10","Religare","AdobeProduct","BinRange Product created successfully")};
	//*                    
	//*********************************************************************************************************************
	//* Creation By      : Saurabh Agrawal
	//* Date of Creation : 14 April, 2014 
	//*********************************************************************************************************************
	public boolean Pine360CreateBinRangeProduct(WebDriver wb_driver,String[] arrS_binproductDetails)
	{
		FrameworkLibrary obj_Fl=new FrameworkLibrary();
		Pine360Common obj_Pine=new Pine360Common();
		try
		{
			String[] arrS_TabList=new String[]{"More","Card Ordering"};
			if(!obj_Pine.Pine360ClickTab(wb_driver,arrS_TabList))
			{
				return false;
			}
			
			WebElement wbE_productLink=wb_driver.findElement(By.id("binRangeProductId"));
			wbE_productLink.click();
			
			WebElement wbE_Code=wb_driver.findElement(By.id("productCode"));
			obj_Fl.ComEnterText(wbE_Code,arrS_binproductDetails[0]);
			
			WebElement wbE_Merchant=wb_driver.findElement(By.id("merchantList"));
			obj_Fl.ComSelectDropDownValue(wb_driver, wbE_Merchant, arrS_binproductDetails[1]);
			
			WebElement wbE_ProdName=wb_driver.findElement(By.id("productName"));
			obj_Fl.ComEnterText(wbE_ProdName,arrS_binproductDetails[2]);
			
			WebElement wbE_btnSave=wb_driver.findElement(By.xpath("//img[@alt='Save']"));
			wbE_btnSave.click();
			if(obj_Fl.ComIsAlertPresent(wb_driver,arrS_binproductDetails[3]) || obj_Fl.ComCompareDivLinkText(wb_driver, "successMsgDiv",arrS_binproductDetails[3] ))
			{
				return true;
			}
			return false;
		}
		catch(Exception e)
		{
			obj_Fl.ComPrintException("Unable to create Bin Range Product", e.toString());
			return false;
		}
	}
	
	//*********************************************************************************************************************
	//* Function Name    : Pine360CreateBinRangeCorporate
	//*
	//* Description      : Create denomination
	//*
	//* Input            : wb_driver							[in] Required Object
	//*					 : arrS_bincorporateDetails   			[in] Required Object
	//*
	//* Output           : None
	//*
	//* Example          : Pine360CreateBinRangeCorporate(wb_driver,{"10","Religare","OPHC","BinRange corporate created successfully")};
	//*                    
	//*********************************************************************************************************************
	//* Creation By      : Saurabh Agrawal
	//* Date of Creation : 14 April, 2014 
	//*********************************************************************************************************************
	public boolean Pine360CreateBinRangeCorporate(WebDriver wb_driver,String[] arrS_bincorporateDetails)
	{
		FrameworkLibrary obj_Fl=new FrameworkLibrary();
		Pine360Common obj_Pine=new Pine360Common();
		try
		{
			String[] arrS_TabList=new String[]{"More","Card Ordering"};
			if(!obj_Pine.Pine360ClickTab(wb_driver,arrS_TabList))
			{
				return false;
			}
			
			WebElement wbE_corpotateLink=wb_driver.findElement(By.id("binRangeCorporateId"));
			wbE_corpotateLink.click();
			
			WebElement wbE_Code=wb_driver.findElement(By.id("corporateCode"));
			obj_Fl.ComEnterText(wbE_Code,arrS_bincorporateDetails[0]);
			
			WebElement wbE_Merchant=wb_driver.findElement(By.id("merchantList"));
			obj_Fl.ComSelectDropDownValue(wb_driver, wbE_Merchant, arrS_bincorporateDetails[1]);
			
			WebElement wbE_ProdName=wb_driver.findElement(By.id("corporateName"));
			obj_Fl.ComEnterText(wbE_ProdName,arrS_bincorporateDetails[2]);
			
			WebElement wbE_btnSave=wb_driver.findElement(By.xpath("//img[@alt='Save']"));
			wbE_btnSave.click();
			if(obj_Fl.ComIsAlertPresent(wb_driver,arrS_bincorporateDetails[3]) || obj_Fl.ComCompareDivLinkText(wb_driver, "successMsgDiv", arrS_bincorporateDetails[3]))
			{
				return true;
			}
			return false;
		}
		catch(Exception e)
		{
			obj_Fl.ComPrintException("Unable to create Bin Range Corporate",e.toString());
			return false;
		}
	}
	
	//*********************************************************************************************************************
	//* Function Name    : Pine360CreateBinRange
	//*
	//* Description      : Create denomination
	//*
	//* Input            : wb_driver			[in] Required Object
	//*					 : arrS_binDetails		[in] Required Object
	//*
	//* Output           : None
	//*
	//* Example          : Pine360CreateBinRange(wb_driver,{"TestBin","Test Bin range","Religare","Adobe","OPHC", "Bin Range created successfully")};
	//*                    
	//*********************************************************************************************************************
	//* Creation By      : Saurabh Agrawal
	//* Date of Creation : 14 April, 2014 
	//*********************************************************************************************************************
	public boolean Pine360CreateBinRange(WebDriver wb_driver,String[] arrS_binDetails)
	{
		FrameworkLibrary obj_Fl=new FrameworkLibrary();
		Pine360Common obj_Pine=new Pine360Common();
		try
		{
			String[] arrS_TabList=new String[]{"More","Card Ordering"};
			if(!obj_Pine.Pine360ClickTab(wb_driver,arrS_TabList))
			{
				return false;
			}
			
			WebElement wbE_binLink=wb_driver.findElement(By.id("binRangeAdminId"));
			wbE_binLink.click();
			
			WebElement wbE_BinName=wb_driver.findElement(By.id("binRangeName"));
			obj_Fl.ComEnterText(wbE_BinName,arrS_binDetails[0]);
			
			WebElement wbE_Description=wb_driver.findElement(By.id("binRangeDesc"));
			obj_Fl.ComEnterText(wbE_Description,arrS_binDetails[1]);
			
			WebElement wbE_Merchant=wb_driver.findElement(By.id("merchantList"));
			obj_Fl.ComSelectDropDownValue(wb_driver, wbE_Merchant, arrS_binDetails[2]);
			
			WebElement wbE_Product=wb_driver.findElement(By.id("productList"));
			obj_Fl.ComSelectDropDownValue(wb_driver, wbE_Product, arrS_binDetails[3]);
			
			WebElement wbE_Corporate=wb_driver.findElement(By.id("corporateList"));
			obj_Fl.ComSelectDropDownValue(wb_driver, wbE_Corporate, arrS_binDetails[4]);
			
			WebElement wbE_CardHolder=wb_driver.findElement(By.id("cardHolderType"));
			obj_Fl.ComSelectDropDownValue(wb_driver, wbE_CardHolder, arrS_binDetails[5]);
			
			WebElement wbE_btnSave=wb_driver.findElement(By.xpath("//img[@alt='Save']"));
			wbE_btnSave.click();
			if(obj_Fl.ComIsAlertPresent(wb_driver,arrS_binDetails[6]) || obj_Fl.ComCompareDivLinkText(wb_driver, "successMsgDiv",arrS_binDetails[6]))
			{
				return true;
			}
			return false;
		}
		catch(Exception e)
		{
			obj_Fl.ComPrintException("Unable to create Bin Range",e.toString());
			return true;
		}
	}
	
	//*********************************************************************************************************************
	//* Function Name    : Pine360CreateDenomination
	//*
	//* Description      : Create denomination
	//*
	//* Input            : wb_driver					[in] Required Object
	//*					 : arrS_denominationDetails   	[in] Required Object
	//*
	//* Output           : None
	//*
	//* Example          : Pine360CreateDenomination(wb_driver,{"10","Loyalty","Denomination created successfully")};
	//*                    
	//*********************************************************************************************************************
	//* Creation By      : Saurabh Agrawal
	//* Date of Creation : 14 April, 2014 
	//*********************************************************************************************************************
	public boolean Pine360CreateDenomination(WebDriver wb_driver,String[] arrS_denominationDetails)
	{
		FrameworkLibrary obj_Fl=new FrameworkLibrary();
		Pine360Common obj_Pine=new Pine360Common();
		try
		{
			String[] arrS_TabList=new String[]{"More","Card Ordering"};
			if(!obj_Pine.Pine360ClickTab(wb_driver,arrS_TabList))
			{
				return false;
			}
			
			WebElement wbE_denominationLink=wb_driver.findElement(By.id("denominationId"));
			wbE_denominationLink.click();
			
			WebElement wbE_denomination=wb_driver.findElement(By.id("denomination"));
			obj_Fl.ComEnterText(wbE_denomination,arrS_denominationDetails[0]);
			
			WebElement wbE_programType=wb_driver.findElement(By.id("programType"));
			obj_Fl.ComSelectDropDownValue(wb_driver, wbE_programType, arrS_denominationDetails[1]);
			
			WebElement wbE_btnSave=wb_driver.findElement(By.xpath("//img[@alt='Save']"));
			wbE_btnSave.click();
			if(obj_Fl.ComIsAlertPresent(wb_driver,arrS_denominationDetails[2]) || obj_Fl.ComCompareDivLinkText(wb_driver, "successMsgDiv", arrS_denominationDetails[2]))
			{
				return true;
			}
			return false;
		}
		catch(Exception e)
		{
			String str_failMsg="Unable to create Denomination:" + arrS_denominationDetails[0];
			obj_Fl.ComPrintException(str_failMsg, e.toString());
			return false;
		}
	}
	
	//*********************************************************************************************************************
	//* Function Name    : Pine360CreateCustomer
	//*
	//* Description      : Create customer
	//*
	//* Input            : wb_driver				[in] Required Object
	//*					 : arrS_customerDetails   	[in] Required Object
	//*
	//* Output           : None
	//*
	//* Example          : Pine360CreateCustomer(wb_driver,{Customer List Array});
	//*                    
	//*********************************************************************************************************************
	//* Creation By      : Saurabh Agrawal
	//* Date of Creation : 16 April, 2014 
	//*********************************************************************************************************************
	public boolean Pine360CreateCustomer(WebDriver wb_driver,String[] arrS_customerDetails)
	{
		FrameworkLibrary obj_Fl=new FrameworkLibrary();
		Pine360Common obj_Pine=new Pine360Common();
		try
		{
			String[] arrS_TabList=new String[]{"Customer"};
			if(!obj_Pine.Pine360ClickTab(wb_driver,arrS_TabList))
			{
				return false;
			}
				
			WebElement  wbE_Title=wb_driver.findElement(By.id("customerTitle"));
			obj_Fl.ComSelectDropDownValue(wb_driver, wbE_Title, arrS_customerDetails[0]);
			
			WebElement  wbE_FirstName=wb_driver.findElement(By.id("customer.firstName"));
			obj_Fl.ComEnterText(wbE_FirstName,arrS_customerDetails[1]);
			
			WebElement  wbE_LastName=wb_driver.findElement(By.id("customer.lastName"));
			obj_Fl.ComEnterText(wbE_LastName,arrS_customerDetails[2]);
			
			WebElement  wbE_Gender=wb_driver.findElement(By.id("genderID"));
			obj_Fl.ComSelectDropDownValue(wb_driver, wbE_Gender, arrS_customerDetails[3]);
			
			WebElement  wbE_Email=wb_driver.findElement(By.id("contact.email"));
			obj_Fl.ComEnterText(wbE_Email,arrS_customerDetails[5]);
			
			String[] Date=new String[]{};
			WebElement  wbE_DobDay=wb_driver.findElement(By.id("dob_day"));
			WebElement  wbE_DobMonth=wb_driver.findElement(By.id("dob_month"));
			WebElement  wbE_DobYear=wb_driver.findElement(By.id("dob_year"));
			Date=arrS_customerDetails[4].split("/");
			obj_Fl.ComSelectDropDownValue(wb_driver, wbE_DobDay, Date[0]);
			wbE_DobMonth.sendKeys(Date[1]);
			obj_Fl.ComSelectDropDownValue(wb_driver, wbE_DobYear, Date[2]);
			
			WebElement  wbE_AnniDay=wb_driver.findElement(By.id("mad_day"));
			WebElement  wbE_AnniMonth=wb_driver.findElement(By.id("mad_month"));
			WebElement  wbE_AnniYear=wb_driver.findElement(By.id("mad_year"));
			Date=arrS_customerDetails[6].split("/");
			obj_Fl.ComSelectDropDownValue(wb_driver, wbE_AnniDay, Date[0]);
			wbE_AnniMonth.sendKeys(Date[1]);
			obj_Fl.ComSelectDropDownValue(wb_driver, wbE_AnniYear, Date[2]);
			
			WebElement  wbE_Address=wb_driver.findElement(By.id("contact.address1"));
			obj_Fl.ComEnterText(wbE_Address,arrS_customerDetails[7]);
			
			WebElement  wbE_State=wb_driver.findElement(By.id("contact.state"));
			obj_Fl.ComSelectDropDownValue(wb_driver, wbE_State, arrS_customerDetails[8]);
			Thread.sleep(2000);
			wbE_State.click();
			
			WebElement  wbE_City=wb_driver.findElement(By.id("city"));
			obj_Fl.ComSelectDropDownValue(wb_driver, wbE_City, arrS_customerDetails[9]);
			//wbE_City.click();
			//wbE_City.sendKeys(arrS_customerDetails[9]);
			
			WebElement  wbE_Pincode=wb_driver.findElement(By.id("pincode"));
			obj_Fl.ComEnterText(wbE_Pincode,arrS_customerDetails[10]);
			
			WebElement  wbE_Landline=wb_driver.findElement(By.id("contact.homePhone"));
			obj_Fl.ComEnterText(wbE_Landline,arrS_customerDetails[11]);
			
			WebElement  wbE_Mobile=wb_driver.findElement(By.id("contact.mobileNumber"));
			obj_Fl.ComEnterText(wbE_Mobile,arrS_customerDetails[12]);
			
			if(!obj_Fl.ComCheckEmptyString(arrS_customerDetails[13]))
			{
				WebElement  wbE_LychkBox=wb_driver.findElement(By.cssSelector("input[id='programIds'][value='1']"));
				WebElement  wbE_LyMembership=wb_driver.findElement(By.id("cardMembershipId_1"));
				WebElement  wbE_LyTier=wb_driver.findElement(By.id("cardMembershipTierId_1"));
				wbE_LychkBox.click();
				obj_Fl.ComSelectDropDownValue(wb_driver, wbE_LyMembership,arrS_customerDetails[13]);
				Thread.sleep(2000);
				wbE_LyMembership.click();
				obj_Fl.ComSelectDropDownValue(wb_driver, wbE_LyTier,arrS_customerDetails[14]);
			}
			
			if(!obj_Fl.ComCheckEmptyString(arrS_customerDetails[15]))
			{
				WebElement  wbE_GiftchkBox=wb_driver.findElement(By.cssSelector("input[id='programIds'][value='2']"));
				WebElement  wbE_GiftMembership=wb_driver.findElement(By.id("cardMembershipId_2"));
				WebElement  wbE_GiftTier=wb_driver.findElement(By.id("cardMembershipTierId_2"));
				wbE_GiftchkBox.click();
				obj_Fl.ComSelectDropDownValue(wb_driver, wbE_GiftMembership,arrS_customerDetails[15]);
				Thread.sleep(2000);
				wbE_GiftMembership.click();
				obj_Fl.ComSelectDropDownValue(wb_driver, wbE_GiftTier,arrS_customerDetails[16]);
			}
			
			if(!obj_Fl.ComCheckEmptyString(arrS_customerDetails[17]))
			{
				WebElement  wbE_PrepaidchkBox=wb_driver.findElement(By.cssSelector("input[id='programIds'][value='3']"));
				WebElement  wbE_PrepaidMembership=wb_driver.findElement(By.id("cardMembershipId_3"));
				WebElement  wbE_PrepaidTier=wb_driver.findElement(By.id("cardMembershipTierId_3"));
				wbE_PrepaidchkBox.click();
				obj_Fl.ComSelectDropDownValue(wb_driver, wbE_PrepaidMembership,arrS_customerDetails[17]);
				Thread.sleep(2000);
				wbE_PrepaidMembership.click();
				obj_Fl.ComSelectDropDownValue(wb_driver, wbE_PrepaidTier,arrS_customerDetails[18]);
			}
			
			wb_driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
			if(Boolean.valueOf(arrS_customerDetails[19]))
			{
				try
				{
					WebElement wbE_MobileCard=wb_driver.findElement(By.cssSelector("input[id='cardTypeId'][value='1']"));
					if(wbE_MobileCard.isDisplayed())
					{
						wbE_MobileCard.click();
					}
				}
				catch(NoSuchElementException Ex)
				{
				}
				
			}
			else if(Boolean.valueOf(arrS_customerDetails[20]))
			{
				try
				{
					WebElement  wbE_PhysicalCard=wb_driver.findElement(By.cssSelector("input[id='cardTypeId'][value='2']"));
					if(wbE_PhysicalCard.isDisplayed())
					{
						wbE_PhysicalCard.click();
					}
				}
				catch(NoSuchElementException Ex)
				{
					
				}
			}
			wb_driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
			WebElement  wbE_CardNumber=wb_driver.findElement(By.id("cardID"));
			obj_Fl.ComEnterText(wbE_CardNumber,arrS_customerDetails[21]);
			
			WebElement  wbE_Store=wb_driver.findElement(By.id("enrolStoreId"));
			obj_Fl.ComSelectDropDownValue(wb_driver, wbE_Store,arrS_customerDetails[22]);
			
			WebElement  wbE_btnSave=wb_driver.findElement(By.xpath("//img[@alt='Save']"));
			wbE_btnSave.click();
			
			if(obj_Fl.ComIsAlertPresent(wb_driver, arrS_customerDetails[23]) || obj_Fl.ComCompareDivLinkText(wb_driver, "successMsgDiv", arrS_customerDetails[23]))
			{
				return true;
			} 
			return false;
		}
		catch(Exception e)
		{
			obj_Fl.ComPrintException("Unable to create Customer",e.toString());
			return false;
		}
	}
	
	//*********************************************************************************************************************
	//* Function Name    : Pine360CreateRole
	//*
	//* Description      : Create Role
	//*
	//* Input            : wb_driver			[in] Required Object
	//*					 : RoleList   	[in] Required Object
	//*
	//* Output           : None
	//*
	//* Example          : Pine360CreateRole(wb_driver,{Role List Array});
	//*                    
	//*********************************************************************************************************************
	//* Creation By      : Saurabh Agrawal
	//* Date of Creation : 16 April, 2014 
	//*********************************************************************************************************************
	public boolean Pine360CreateRole(WebDriver wb_driver,String[] arrS_roleDetails)
	{
		FrameworkLibrary obj_Fl=new FrameworkLibrary();
		Pine360Common obj_Pine=new Pine360Common();
		try
		{
			String[] arrS_TabList=new String[]{"More","Create Role"};
			if(!obj_Pine.Pine360ClickTab(wb_driver,arrS_TabList))
			{
				return false;
			}
								
			WebElement  wbE_ParentRole=wb_driver.findElement(By.id("role"));
			if(!obj_Fl.ComSelectDropDownValue(wb_driver, wbE_ParentRole,arrS_roleDetails[0]))
			{
				return false;
			}
			WebElement  wbE_RoleName=wb_driver.findElement(By.id("roleName"));
			obj_Fl.ComEnterText(wbE_RoleName,arrS_roleDetails[1]);
			
			WebElement  wbE_RoleDesc=wb_driver.findElement(By.id("roleDescription"));
			obj_Fl.ComEnterText(wbE_RoleDesc,arrS_roleDetails[2]);
			
			WebElement  wbE_btnCreate=wb_driver.findElement(By.xpath("//img[@alt='Create']"));
			wbE_btnCreate.click();
				
			for(int i_count=3;i_count < arrS_roleDetails.length;i_count++)
			{
				try
				{
					wb_driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
					WebElement wbE_permission = wb_driver.findElement(By.xpath("//*[@value='"+ arrS_roleDetails[i_count] +"']"));
					wbE_permission.click();
				} 
				catch(NoSuchElementException Ex)
				{
					obj_Fl.ComPrintException("Unable to find Element",Ex.toString());
					continue;
				}
				catch(Exception Ex)
				{
					obj_Fl.ComPrintException("Unable to Create Role",Ex.toString());
					continue;
				}
				finally
				{
					wb_driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				}
				
			}
			wbE_btnCreate=wb_driver.findElement(By.xpath("//img[@alt='Create']"));
			wbE_btnCreate.click();
			if(obj_Fl.ComIsAlertPresent(wb_driver,arrS_roleDetails[184]) || obj_Fl.ComCompareDivLinkText(wb_driver, "successMsgDiv",arrS_roleDetails[184]))
			{
				return true;
			}
			return false;
		}
		catch(Exception e)
		{
			obj_Fl.ComPrintException("Unable to create role",e.toString());
			return false;
		}
	}
	
	//*********************************************************************************************************************
	//* Function Name    : Pine360CreateOrder
	//*
	//* Description      : Create Order
	//*
	//* Input            : wb_driver			[in] Required Object
	//*					 : arrS_orderDetails   	[in] Required Object
	//*
	//* Output           : None
	//*
	//* Example          : Pine360CreateRole(wb_driver,{Order List Array});
	//*                    
	//*********************************************************************************************************************
	//* Creation By      : Saurabh Agrawal
	//* Date of Creation : 16 April, 2014 
	//*********************************************************************************************************************
	public boolean Pine360CreateOrder(WebDriver wb_driver,String[] arrS_orderDetails)
	{
		FrameworkLibrary obj_Fl=new FrameworkLibrary();
		Pine360Common obj_Pine=new Pine360Common();
		try
		{
			String[] arrS_TabList=new String[]{"More","Card Ordering"};
			if(!obj_Pine.Pine360ClickTab(wb_driver,arrS_TabList))
			{
				return false;
			}
			WebElement wbE_StockOrder=wb_driver.findElement(By.id("stockOrderId"));;
			wbE_StockOrder.click();
			
			if(arrS_orderDetails[2].equalsIgnoreCase("Normal"))
			{
				return Pine360CreateNormalOrder(wb_driver, arrS_orderDetails);
			}
			else if (arrS_orderDetails[2].equalsIgnoreCase("Primary"))
			{
				return Pine360CreatePrimaryOrder(wb_driver, arrS_orderDetails);
			}
			else if (arrS_orderDetails[2].equalsIgnoreCase("Secondary"))
			{
				return Pine360CreateSecondaryOrder(wb_driver, arrS_orderDetails);
			}
			else if (arrS_orderDetails[2].equalsIgnoreCase("Reissue"))
			{}
			return false;
		}
		catch(Exception e)
		{
			obj_Fl.ComPrintException("Unable to create stock order",e.toString());
			return false;
		}
	}
	
	public boolean Pine360CreateNormalOrder(WebDriver wb_driver,String[] arrS_orderDetails)
	{
		FrameworkLibrary obj_Fl=new FrameworkLibrary();
		try
		{
			WebElement  wbE_OrderName=wb_driver.findElement(By.id("orderName"));
			obj_Fl.ComEnterText(wbE_OrderName,arrS_orderDetails[0]);
			
			WebElement  wbE_OrderDes=wb_driver.findElement(By.id("orderDescription"));
			obj_Fl.ComEnterText(wbE_OrderDes,arrS_orderDetails[1]);
			
			WebElement  wbE_CardType=wb_driver.findElement(By.id("cardHolderType"));
			obj_Fl.ComSelectDropDownValue(wb_driver, wbE_CardType,arrS_orderDetails[2]);
			
			WebElement  wbE_CardScheme=wb_driver.findElement(By.id("schemeGroupId"));
			obj_Fl.ComSelectDropDownValue(wb_driver, wbE_CardScheme,arrS_orderDetails[4]);
			
			WebElement  wbE_Quantity=wb_driver.findElement(By.id("stockOrderQuantity"));
			obj_Fl.ComEnterText(wbE_Quantity,arrS_orderDetails[3]);
				
			WebElement  wbE_btnSave=wb_driver.findElement(By.xpath("//img[@alt='Save']"));
			wbE_btnSave.click();
			if(obj_Fl.ComIsAlertPresent(wb_driver,arrS_orderDetails[11]) || obj_Fl.ComCompareDivLinkText(wb_driver, "successMsgDiv", arrS_orderDetails[11]))
			{
				return true;
			}
			return false;
		}
		catch(Exception e)
		{
			obj_Fl.ComPrintException("Unable to create stock order",e.toString());
			return false;
		}
	}
	
	public boolean Pine360CreatePrimaryOrder(WebDriver wb_driver,String[] arrS_orderDetails)
	{
		FrameworkLibrary obj_Fl=new FrameworkLibrary();
		try
		{
			WebElement  wbE_OrderName=wb_driver.findElement(By.id("orderName"));
			obj_Fl.ComEnterText(wbE_OrderName,arrS_orderDetails[0]);
			
			WebElement  wbE_OrderDes=wb_driver.findElement(By.id("orderDescription"));
			obj_Fl.ComEnterText(wbE_OrderDes,arrS_orderDetails[1]);
			
			WebElement  wbE_CardType=wb_driver.findElement(By.id("cardHolderType"));
			obj_Fl.ComSelectDropDownValue(wb_driver, wbE_CardType,arrS_orderDetails[2]);
			
			WebElement  wbE_CardScheme=wb_driver.findElement(By.id("schemeGroupId"));
			obj_Fl.ComSelectDropDownValue(wb_driver, wbE_CardScheme,arrS_orderDetails[4]);
			
			WebElement wbE_fileName=wb_driver.findElement(By.id("uploadFile"));
			wbE_fileName.sendKeys(arrS_orderDetails[3]);
				
			WebElement  wbE_btnSave=wb_driver.findElement(By.xpath("//img[@alt='Save']"));
			wbE_btnSave.click();
			if(obj_Fl.ComIsAlertPresent(wb_driver,arrS_orderDetails[11]) || obj_Fl.ComCompareDivLinkText(wb_driver, "successMsgDiv", arrS_orderDetails[11]))
			{
				return true;
			}
			return false;
		}
		catch(Exception e)
		{
			obj_Fl.ComPrintException("Unable to create stock order",e.toString());
			return false;
		}
	}
	
	public boolean Pine360CreateSecondaryOrder(WebDriver wb_driver,String[] arrS_orderDetails)
	{
		FrameworkLibrary obj_Fl=new FrameworkLibrary();
		try
		{
			WebElement  wbE_OrderName=wb_driver.findElement(By.id("orderName"));
			obj_Fl.ComEnterText(wbE_OrderName,arrS_orderDetails[0]);
			
			WebElement  wbE_OrderDes=wb_driver.findElement(By.id("orderDescription"));
			obj_Fl.ComEnterText(wbE_OrderDes,arrS_orderDetails[1]);
			
			WebElement  wbE_CardType=wb_driver.findElement(By.id("cardHolderType"));
			obj_Fl.ComSelectDropDownValue(wb_driver, wbE_CardType,arrS_orderDetails[2]);
			
			WebElement wbE_fileName=wb_driver.findElement(By.id("uploadFile"));
			wbE_fileName.sendKeys(arrS_orderDetails[3]);
				
			WebElement  wbE_btnSave=wb_driver.findElement(By.xpath("//img[@alt='Save']"));
			wbE_btnSave.click();
			
			WebElement wbE_newCardScheme=wb_driver.findElement(By.id("selectedId1"));
			obj_Fl.ComSelectDropDownValue(wb_driver,wbE_newCardScheme, arrS_orderDetails[4]);
			
			wbE_btnSave=wb_driver.findElement(By.xpath("//img[@alt='Save']"));
			wbE_btnSave.click();
			
			if(obj_Fl.ComIsAlertPresent(wb_driver,arrS_orderDetails[11]) || obj_Fl.ComCompareDivLinkText(wb_driver, "successMsgDiv", arrS_orderDetails[11]))
			{
				return true;
			}
			return false;
		}
		catch(Exception e)
		{
			obj_Fl.ComPrintException("Unable to create stock order",e.toString());
			return false;
		}
	}
	//*********************************************************************************************************************
	//* Function Name    : Pine360ClickStockOrder
	//*
	//* Description      : Click any order i.e. view, approve,cancel or edit order
	//*
	//* Input            : wb_driver			[in] Required Object
	//*					 : StockOrderName  	[in] Required Object
	//*					 : Action       	[in] Required Object
	//*
	//* Output           : None
	//*
	//* Example          : Pine360ClickStockOrder(wb_driver,"tatastarbucksorder","Edit");
	//*                    
	//*********************************************************************************************************************
	//* Creation By      : Saurabh Agrawal
	//* Date of Creation : 17 April, 2014 
	//*********************************************************************************************************************
	public boolean Pine360ClickStockOrder(WebDriver wb_driver, String str_StockOrderName, String str_Action)
	{
		FrameworkLibrary obj_Fl=new FrameworkLibrary();
		Pine360Common obj_Pine=new Pine360Common();
		try
		{
			String[] arrS_TabList=new String[]{"More","Card Ordering"};
			if(!obj_Pine.Pine360ClickTab(wb_driver,arrS_TabList))
			{
				return false;
			}
			
			WebElement wbE_StockOrder=wb_driver.findElement(By.id("stockOrderId"));
			wbE_StockOrder.click();
			obj_Pine.Pine360Search(wb_driver, new String[] {"searchOrderName"}, new String[] {str_StockOrderName});
			Pine360ClickSearchItem(wb_driver,str_StockOrderName,str_Action,"");
			
			if(str_Action.equalsIgnoreCase("cancel"))
			{
				return obj_Fl.ComIsAlertPresent(wb_driver, "Are you sure want to cancel order ?");
			}
			return true;
		}
		catch(Exception e)
		{
			obj_Fl.ComPrintException("Unable to find/click stock order",e.toString());
			return false;
		}
	}
	
	//*********************************************************************************************************************
	//* Function Name    : Pine360AllocateOrCancelorReceiveStockOrder
	//*
	//* Description      : Allocate/Cancel/Receive StockOrder
	//*
	//* Input            : wb_driver			[in] Required Object
	//*					 : Action       	[in] Required Object
	//*
	//* Output           : None
	//*
	//* Example          : Pine360AllocateOrCancelorReceiveStockOrder(wb_driver,"allocate","Stock order updated successfully");
	//*                    
	//*********************************************************************************************************************
	//* Creation By      : Saurabh Agrawal
	//* Date of Creation : 17 April, 2014 
	//*********************************************************************************************************************
	public boolean Pine360AllocateOrCancelorReceiveStockOrder(WebDriver wb_driver,String str_Action,String str_SuccessMsg)
	{
		FrameworkLibrary obj_Fl=new FrameworkLibrary();
		try
		{
			WebElement wbE_Action=wb_driver.findElement(By.id("actionType"));
			WebElement  wbE_btnSave=wb_driver.findElement(By.xpath("//img[@alt='Save']"));
			
			wbE_Action.sendKeys(str_Action);
			wbE_btnSave.click();
			Thread.sleep(10000);
			if(obj_Fl.ComIsAlertPresent(wb_driver,str_SuccessMsg) || obj_Fl.ComCompareDivLinkText(wb_driver, "successMsgDiv", str_SuccessMsg))
			{
				return true;
			}
			return false;
		}
		catch(Exception e)
		{
			obj_Fl.ComPrintException("Unable to Allocate/Cancel/Receive stock order",e.toString());
			return false;
		}
	}
	
	//*********************************************************************************************************************
	//* Function Name    : Pine360DispatchStockOrder
	//*
	//* Description      : Dispatch StockOrder
	//*
	//* Input            : wb_driver			[in] Required Object
	//*					 : Action       	[in] Required Object
	//*					 : DispatchTo      	[in] Required Object
	//*
	//* Output           : None
	//*
	//* Example          : Pine360AllocateOrCancelorReceiveStockOrder(wb_driver,"email","saurabh@pine.com","Stock order updated successfully");
	//*                    
	//*********************************************************************************************************************
	//* Creation By      : Saurabh Agrawal
	//* Date of Creation : 17 April, 2014 
	//*********************************************************************************************************************
	public boolean Pine360DispatchStockOrder(WebDriver wb_driver,String str_sendoption,String str_DispatchTo,String str_SuccessMsg)
	{
		FrameworkLibrary obj_Fl=new FrameworkLibrary();
		try
		{
			WebElement wbE_Option=null;
			WebElement wbE_DispatchTo=null;
			if(str_sendoption.equalsIgnoreCase("sftp"))
			{
				wbE_Option=wb_driver.findElement(By.cssSelector("input[id='commType'][value='1']"));
				wbE_DispatchTo=wb_driver.findElement(By.id("sftpAddress"));
			}
			else if(str_sendoption.equalsIgnoreCase("ftp"))
			{
				wbE_Option=wb_driver.findElement(By.cssSelector("input[id='commType'][value='2']"));
				wbE_DispatchTo=wb_driver.findElement(By.id("ftpAddress"));
			}
			else if (str_sendoption.equalsIgnoreCase("email"))
			{
				wbE_Option=wb_driver.findElement(By.cssSelector("input[id='commType'][value='3']"));
				wbE_DispatchTo=wb_driver.findElement(By.id("emailId"));
			}
			
			wbE_Option.click(); 
			if(!obj_Fl.ComSelectDropDownValue(wb_driver, wbE_DispatchTo,str_DispatchTo))
			{
				return false;
			}
			
			WebElement  wbbtnSave=wb_driver.findElement(By.xpath("//img[@alt='Save']"));
			wbbtnSave.click();
			Thread.sleep(10000);
			if(obj_Fl.ComIsAlertPresent(wb_driver,str_SuccessMsg) || obj_Fl.ComCompareDivLinkText(wb_driver, "successMsgDiv", str_SuccessMsg))
			{
				return true;
			}
			return false;
		}
		catch(Exception e)
		{
			obj_Fl.ComPrintException("Unable to dispatch stock order",e.toString());
			return false;
		}
	}
	
	//*********************************************************************************************************************
	//* Function Name    : Pine360CreateCardScheme
	//*
	//* Description      : Create Card Scheme
	//*
	//* Input            : wb_driver						[in] Required Object
	//*					 : arrS_cardschemeDetails       	[in] Required Object
	//*
	//* Output           : None
	//*
	//* Example          : Pine360CreateCardScheme(wb_driver,{("Card Scheme details array)});
	//*                    
	//*********************************************************************************************************************
	//* Creation By      : Saurabh Agrawal
	//* Date of Creation : 30 April, 2014 
	//*********************************************************************************************************************
	public boolean Pine360CreateCardScheme(WebDriver wb_driver,String[] arrS_cardschemeDetails )
	{
		FrameworkLibrary obj_Fl=new FrameworkLibrary();
		Pine360Common obj_Pine=new Pine360Common();
		int i_btnCount=0;
		try
		{
			String[] arrS_TabList=new String[]{"More","Card Ordering"};
			if(!obj_Pine.Pine360ClickTab(wb_driver,arrS_TabList))
			{
				return false;
			}
			
			WebElement wbE_cardScheme=wb_driver.findElement(By.id("cardSchemeId"));
			wbE_cardScheme.click();
			
			WebElement wbE_schemeName=wb_driver.findElement(By.id("schemeName"));
			obj_Fl.ComEnterText(wbE_schemeName, arrS_cardschemeDetails[0]);
			
			WebElement wbE_schemeDescription=wb_driver.findElement(By.id("schemeDescription"));
			obj_Fl.ComEnterText(wbE_schemeDescription, arrS_cardschemeDetails[1]);
			
			WebElement wbE_schemeStatus=wb_driver.findElement(By.id("schemeStatus"));
			obj_Fl.ComSelectDropDownValue(wb_driver, wbE_schemeStatus, arrS_cardschemeDetails[2]);
			
			WebElement wbE_cardType=wb_driver.findElement(By.id("cardType"));
			obj_Fl.ComSelectDropDownValue(wb_driver, wbE_cardType, arrS_cardschemeDetails[3]);
			
			WebElement wbE_product=wb_driver.findElement(By.id("binrangeProduct"));
			obj_Fl.ComSelectDropDownValue(wb_driver, wbE_product, arrS_cardschemeDetails[7]);
			
			WebElement wbE_corporate=wb_driver.findElement(By.id("binrangeCorporate"));
			obj_Fl.ComSelectDropDownValue(wb_driver, wbE_corporate, arrS_cardschemeDetails[8]);
			
			WebElement wbE_cardholderType=wb_driver.findElement(By.id("cardHolderType"));
			obj_Fl.ComSelectDropDownValue(wb_driver, wbE_cardholderType, arrS_cardschemeDetails[9]);
			
			WebElement wbE_binRange=wb_driver.findElement(By.id("binRangeId"));
			wbE_binRange.sendKeys(arrS_cardschemeDetails[10]);
			
			// Loyalty Scheme Enabled
			if(Boolean.valueOf(arrS_cardschemeDetails[5]))
			{
				WebElement wbE_isloyaltyType=wb_driver.findElement(By.id("loyaltySelected"));
				wbE_isloyaltyType.click();
				
				WebElement wbE_lyMembership=wb_driver.findElement(By.id("loyaltyMembershipId"));
				obj_Fl.ComSelectDropDownValue(wb_driver, wbE_lyMembership, arrS_cardschemeDetails[11]);
				
				WebElement wbE_lyTier=wb_driver.findElement(By.id("loyaltyTierId"));
				obj_Fl.ComSelectDropDownValue(wb_driver, wbE_lyTier, arrS_cardschemeDetails[12]);
				
				WebElement wbE_lyDenomination=wb_driver.findElement(By.id("loyaltyDenominationId"));
				obj_Fl.ComSelectDropDownValue(wb_driver, wbE_lyDenomination, arrS_cardschemeDetails[13]);
				
				WebElement wbE_lyfirsttxnAmount=wb_driver.findElement(By.id("loyaltyFirstAwardValue"));
				obj_Fl.ComEnterText(wbE_lyfirsttxnAmount, arrS_cardschemeDetails[14]);
				
				WebElement wbE_lyreplacementFees=wb_driver.findElement(By.id("loyaltyReplacementFee"));
				obj_Fl.ComEnterText(wbE_lyreplacementFees, arrS_cardschemeDetails[15]);
				
				if(Boolean.valueOf(arrS_cardschemeDetails[16]))
				{
					WebElement wbE_lychkboxismultipleRedeem=wb_driver.findElement(By.id("loyaltyMultipleRedeemAllowed"));
					wbE_lychkboxismultipleRedeem.click();
				}
				if(Boolean.valueOf(arrS_cardschemeDetails[17]))
				{
					WebElement wbE_lychkboxisReloadable=wb_driver.findElement(By.id("loyaltyReloadable"));
					wbE_lychkboxisReloadable.click();
				}
				if(Boolean.valueOf(arrS_cardschemeDetails[18]))
				{
					WebElement wbE_lychkboxismultipleLoadReload=wb_driver.findElement(By.id("loyaltyLoadInMultiples"));
					wbE_lychkboxismultipleLoadReload.click();
				}
				if(Boolean.valueOf(arrS_cardschemeDetails[19]))
				{
					WebElement wbE_lychkboxisPreloaded=wb_driver.findElement(By.id("loyaltyPreloaded"));
					wbE_lychkboxisPreloaded.click();
					WebElement wbE_lypreloadedPoints=wb_driver.findElement(By.id("loyaltyPreloadedValue"));
					obj_Fl.ComEnterText(wbE_lypreloadedPoints, arrS_cardschemeDetails[20]);
				}
				
				WebElement wbE_lyminBalance=wb_driver.findElement(By.id("loyaltyMinCardBalance"));
				obj_Fl.ComEnterText(wbE_lyminBalance, arrS_cardschemeDetails[21]);
				
				WebElement wbE_lymaxBalance=wb_driver.findElement(By.id("loyaltyMaxCardBalance"));
				obj_Fl.ComEnterText(wbE_lymaxBalance, arrS_cardschemeDetails[22]);
				
				WebElement wbE_lyactivateLoad=wb_driver.findElement(By.id("loyaltyActivationType"));
				obj_Fl.ComSelectDropDownValue(wb_driver, wbE_lyactivateLoad, arrS_cardschemeDetails[23]);
				//Verification for Activate Load
				if(arrS_cardschemeDetails[23].equalsIgnoreCase("Fixed date"))
				{
					WebElement wbE_lyactivateOn=wb_driver.findElement(By.id("loyaltyCalendar"));
					wbE_lyactivateOn.click();
					Pine360PickDate(wb_driver, arrS_cardschemeDetails[24],"dd/MM/yyyy");
				}
				else if(arrS_cardschemeDetails[23].equalsIgnoreCase("From order received"))
				{
					WebElement wbE_lyactivateAfter=wb_driver.findElement(By.id("loyaltyActivationAfter"));
					obj_Fl.ComEnterText(wbE_lyactivateAfter, arrS_cardschemeDetails[24].split("-")[0]);
					
					WebElement wbE_lyactivateDuration=wb_driver.findElement(By.id("loyaltyActivationDurationType"));
					obj_Fl.ComSelectDropDownValue(wb_driver, wbE_lyactivateDuration, arrS_cardschemeDetails[24].split("-")[1]);
				}
				
				WebElement wbE_lyactivateRedeem=wb_driver.findElement(By.id("loyaltyRedemptionType"));
				obj_Fl.ComSelectDropDownValue(wb_driver, wbE_lyactivateRedeem, arrS_cardschemeDetails[25]);
				//Verification for Activate Redeem
				if(arrS_cardschemeDetails[25].equalsIgnoreCase("Fixed date"))
				{
					WebElement wbE_lyactivateOn=wb_driver.findElement(By.id("loyaltyredeemDate"));
					wbE_lyactivateOn.click();
					Pine360PickDate(wb_driver, arrS_cardschemeDetails[26],"dd/MM/yyyy");
				}
				else if(arrS_cardschemeDetails[25].equalsIgnoreCase("From order received") || arrS_cardschemeDetails[25].equalsIgnoreCase("From Load Activation") || arrS_cardschemeDetails[25].equalsIgnoreCase("From First Load"))
				{
					WebElement wbE_lyactivateAfter=wb_driver.findElement(By.id("loyaltyRedemptionAfter"));
					obj_Fl.ComEnterText(wbE_lyactivateAfter, arrS_cardschemeDetails[26].split("-")[0]);
					
					WebElement wbE_lyactivateDuration=wb_driver.findElement(By.id("loyaltyRedemptionDurationType"));
					obj_Fl.ComSelectDropDownValue(wb_driver, wbE_lyactivateDuration, arrS_cardschemeDetails[26].split("-")[1]);
				}
				
				WebElement wbE_lyExpiry=wb_driver.findElement(By.id("loyaltyExpiryType"));
				obj_Fl.ComSelectDropDownValue(wb_driver, wbE_lyExpiry, arrS_cardschemeDetails[27]);
				//Verification for Expiry Date
				if(arrS_cardschemeDetails[27].equalsIgnoreCase("Fixed date"))
				{
					WebElement wbE_lyactivateOn=wb_driver.findElement(By.id("loyaltyexpDate"));
					wbE_lyactivateOn.click();
					Pine360PickDate(wb_driver, arrS_cardschemeDetails[28],"dd/MM/yyyy");
				}
				else if(arrS_cardschemeDetails[27].equalsIgnoreCase("From order received") || arrS_cardschemeDetails[27].equalsIgnoreCase("From Load Activation") || arrS_cardschemeDetails[27].equalsIgnoreCase("From First Load") || arrS_cardschemeDetails[27].equalsIgnoreCase("From Redeem Activation"))
				{
					WebElement wbE_lyactivateAfter=wb_driver.findElement(By.id("loyaltyExpiryAfter"));
					obj_Fl.ComEnterText(wbE_lyactivateAfter, arrS_cardschemeDetails[28].split("-")[0]);
					
					WebElement wbE_lyactivateDuration=wb_driver.findElement(By.id("loyaltyExpiryDurationType"));
					obj_Fl.ComSelectDropDownValue(wb_driver, wbE_lyactivateDuration, arrS_cardschemeDetails[28].split("-")[1]);
				}
				i_btnCount=1;
			}
			
			if(Boolean.valueOf(arrS_cardschemeDetails[6]))
			{
				WebElement wbE_isprepaidType=wb_driver.findElement(By.id("prepaidSelected"));
				wbE_isprepaidType.click();
				
				WebElement wbE_prPMembership=wb_driver.findElement(By.id("prepaidMembershipId"));
				obj_Fl.ComSelectDropDownValue(wb_driver, wbE_prPMembership, arrS_cardschemeDetails[29]);
				
				WebElement wbE_prPTier=wb_driver.findElement(By.id("prepaidTierId"));
				obj_Fl.ComSelectDropDownValue(wb_driver, wbE_prPTier, arrS_cardschemeDetails[30]);
				
				WebElement wbE_prPDenomination=wb_driver.findElement(By.id("prepaidDenominationId"));
				obj_Fl.ComSelectDropDownValue(wb_driver, wbE_prPDenomination, arrS_cardschemeDetails[31]);
				
				WebElement wbE_prPfirsttxnAmount=wb_driver.findElement(By.id("prepaidFirstAwardValue"));
				obj_Fl.ComEnterText(wbE_prPfirsttxnAmount, arrS_cardschemeDetails[32]);
				
				WebElement wbE_prPreplacementFees=wb_driver.findElement(By.id("prepaidReplacementFee"));
				obj_Fl.ComEnterText(wbE_prPreplacementFees, arrS_cardschemeDetails[33]);
				
				if(Boolean.valueOf(arrS_cardschemeDetails[34]))
				{
					WebElement wbE_prPchkboxismultipleRedeem=wb_driver.findElement(By.id("prepaidMultipleRedeemAllowed"));
					wbE_prPchkboxismultipleRedeem.click();
				}
				if(Boolean.valueOf(arrS_cardschemeDetails[35]))
				{
					WebElement wbE_prPchkboxisReloadable=wb_driver.findElement(By.id("prepaidReloadable"));
					wbE_prPchkboxisReloadable.click();
				}
				if(Boolean.valueOf(arrS_cardschemeDetails[36]))
				{
					WebElement wbE_prPchkboxismultipleLoadReload=wb_driver.findElement(By.id("prepaidLoadInMultiples"));
					wbE_prPchkboxismultipleLoadReload.click();
				}
				if(Boolean.valueOf(arrS_cardschemeDetails[37]))
				{
					WebElement wbE_prPchkboxisPreloaded=wb_driver.findElement(By.id("prepaidPreloaded"));
					wbE_prPchkboxisPreloaded.click();
					WebElement wbE_prPpreloadedPoints=wb_driver.findElement(By.id("prepaidPreloadedValue"));
					obj_Fl.ComEnterText(wbE_prPpreloadedPoints, arrS_cardschemeDetails[38]);
				}
				
				WebElement wbE_prPminBalance=wb_driver.findElement(By.id("prepaidMinCardBalance"));
				obj_Fl.ComEnterText(wbE_prPminBalance, arrS_cardschemeDetails[39]);
				
				WebElement wbE_prPmaxBalance=wb_driver.findElement(By.id("prepaidMaxCardBalance"));
				obj_Fl.ComEnterText(wbE_prPmaxBalance, arrS_cardschemeDetails[40]);
				
				WebElement wbE_prPactivateLoad=wb_driver.findElement(By.id("prepaidActivationType"));
				obj_Fl.ComSelectDropDownValue(wb_driver, wbE_prPactivateLoad, arrS_cardschemeDetails[41]);
				//Verification for Activate Load
				if(arrS_cardschemeDetails[41].equalsIgnoreCase("Fixed date"))
				{
					WebElement wbE_prPactivateOn=wb_driver.findElement(By.id("prepaidCalendar"));
					wbE_prPactivateOn.click();
					Pine360PickDate(wb_driver, arrS_cardschemeDetails[42],"dd/MM/yyyy");
				}
				else if(arrS_cardschemeDetails[41].equalsIgnoreCase("From order received"))
				{
					WebElement wbE_prPactivateAfter=wb_driver.findElement(By.id("prepaidActivationAfter"));
					obj_Fl.ComEnterText(wbE_prPactivateAfter, arrS_cardschemeDetails[42].split("-")[0]);
					
					WebElement wbE_prPactivateDuration=wb_driver.findElement(By.id("prepaidActivationDurationType"));
					obj_Fl.ComSelectDropDownValue(wb_driver, wbE_prPactivateDuration, arrS_cardschemeDetails[42].split("-")[1]);
				}
				
				WebElement wbE_prPactivateRedeem=wb_driver.findElement(By.id("prepaidRedemptionType"));
				obj_Fl.ComSelectDropDownValue(wb_driver, wbE_prPactivateRedeem, arrS_cardschemeDetails[43]);
				//Verification for Activate Redeem
				if(arrS_cardschemeDetails[43].equalsIgnoreCase("Fixed date"))
				{
					WebElement wbE_lyactivateOn=wb_driver.findElement(By.id("prepaidredeemDate"));
					wbE_lyactivateOn.click();
					Pine360PickDate(wb_driver, arrS_cardschemeDetails[44],"dd/MM/yyyy");
				}
				else if(arrS_cardschemeDetails[43].equalsIgnoreCase("From order received") || arrS_cardschemeDetails[43].equalsIgnoreCase("From Load Activation") || arrS_cardschemeDetails[43].equalsIgnoreCase("From First Load"))
				{
					WebElement wbE_lyactivateAfter=wb_driver.findElement(By.id("prepaidRedemptionAfter"));
					obj_Fl.ComEnterText(wbE_lyactivateAfter, arrS_cardschemeDetails[44].split("-")[0]);
					
					WebElement wbE_lyactivateDuration=wb_driver.findElement(By.id("prepaidRedemptionDurationType"));
					obj_Fl.ComSelectDropDownValue(wb_driver, wbE_lyactivateDuration, arrS_cardschemeDetails[44].split("-")[1]);
				}
				
				WebElement wbE_prPExpiry=wb_driver.findElement(By.id("prepaidExpiryType"));
				obj_Fl.ComSelectDropDownValue(wb_driver, wbE_prPExpiry, arrS_cardschemeDetails[45]);
				//Verification for Expiry Date
				if(arrS_cardschemeDetails[45].equalsIgnoreCase("Fixed date"))
				{
					WebElement wbE_lyactivateOn=wb_driver.findElement(By.id("prepaidexpDate"));
					wbE_lyactivateOn.click();
					Pine360PickDate(wb_driver, arrS_cardschemeDetails[46],"dd/MM/yyyy");
				}
				else if(arrS_cardschemeDetails[45].equalsIgnoreCase("From order received") || arrS_cardschemeDetails[45].equalsIgnoreCase("From Load Activation") || arrS_cardschemeDetails[45].equalsIgnoreCase("From First Load") || arrS_cardschemeDetails[45].equalsIgnoreCase("From Redeem Activation"))
				{
					WebElement wbE_lyactivateAfter=wb_driver.findElement(By.id("prepaidExpiryAfter"));
					obj_Fl.ComEnterText(wbE_lyactivateAfter, arrS_cardschemeDetails[46].split("-")[0]);
					
					WebElement wbE_lyactivateDuration=wb_driver.findElement(By.id("prepaidExpiryDurationType"));
					obj_Fl.ComSelectDropDownValue(wb_driver, wbE_lyactivateDuration, arrS_cardschemeDetails[46].split("-")[1]);
				}
				i_btnCount=2;
			}
			
			List<WebElement> wb_btnSave=wb_driver.findElements(By.xpath("//img[@alt='Save']"));
			wb_btnSave.get(i_btnCount).click();
			if(obj_Fl.ComIsAlertPresent(wb_driver,arrS_cardschemeDetails[47]) || obj_Fl.ComCompareDivLinkText(wb_driver, "successMsgDiv", arrS_cardschemeDetails[47]))
			{
				return true;
			}
			return false;
		}
		catch(Exception e)
		{
			obj_Fl.ComPrintException("Unable to create card scheme",e.toString());
			return false;
		}
	}
	
	//*********************************************************************************************************************
	//* Function Name    : Pine360PickDate
	//*
	//* Description      : Pick Date on card Card Scheme page
	//*
	//* Input            : wb_driver				[in] Required Object
	//*					 : str_date       			[in] Required Object
	//*					 : str_dateFormat       	[in] Required Object
	//*
	//* Output           : None
	//*
	//* Example          : Pine360PickDate(wb_driver,"23/04/2014","dd/MM/yyyy");
	//*                    
	//*********************************************************************************************************************
	//* Creation By      : Saurabh Agrawal
	//* Date of Creation : 30 April, 2014 
	//*********************************************************************************************************************
	public boolean Pine360PickDate(WebDriver wb_driver, String str_date,String str_dateFormat)
	{
		FrameworkLibrary obj_Fl=new FrameworkLibrary();
		try
		{
			DateFormat dF_dateFormatCurrent=new SimpleDateFormat(str_dateFormat);
			Date dt_current=dF_dateFormatCurrent.parse(str_date);
			
			DateFormat dF_dateformat=new SimpleDateFormat("d/MMMM/YYYY");
	        String[] arrS_date=dF_dateformat.format(dt_current).split("/");
			
	        WebElement wbE_calDiv=wb_driver.findElement(By.id("calendarDiv"));
			
			WebElement wbE_monthSelect=wbE_calDiv.findElement(By.id("monthSelect"));
			wbE_monthSelect.click();
			
			WebElement wbE_month=wbE_calDiv.findElement(By.xpath("//div[text()='" + arrS_date[1] + "']"));
			wbE_month.click();
			
			WebElement wbE_yearSelect=wb_driver.findElement(By.id("calendar_year_txt"));
			wbE_yearSelect.click();
			
			WebElement wbE_year=wbE_calDiv.findElement(By.xpath("//div[text()='" + arrS_date[2] + "']"));
			wbE_year.click();
			
			Thread.sleep(500);
			List<WebElement> wbE_date=wbE_calDiv.findElements(By.xpath("//td[text()='" + arrS_date[0] + "']"));
			if(wbE_date.size() > 1 )
			{
				wbE_date.get(1).click();
				return true;
			}
			wbE_date.get(0).click();
			return true;
		}
		catch(Exception e)
		{
			obj_Fl.ComPrintException("Unable to set date",e.toString());
			return false;
		}
	}
	
	public boolean Pine360ClickSearchItem(WebDriver wb_driver,String str_searchText,String str_Action,String str_nosearchMsg)
	{
		FrameworkLibrary obj_Fl=new FrameworkLibrary();
		try
		{
			WebElement wbE_ResultGrid=wb_driver.findElement(By.xpath("//table[@class='results']"));
			WebElement wbE_Order=wbE_ResultGrid.findElement(By.xpath("//td[text()='" + str_searchText + "']"));
			WebElement wbE_OrderParent=wbE_Order.findElement(By.xpath(".."));
			WebElement wbE_Action=wbE_OrderParent.findElement(By.linkText(str_Action));
			wbE_Action.click();
			return true;
		}
		catch(Exception Ex)
		{
			obj_Fl.ComPrintException("Unable to Search. ",Ex.toString());
			return false;
		}
	}
	
	public boolean Pine360EditUser(WebDriver wb_driver, String[] arrS_userDetails)
	{
		FrameworkLibrary obj_Fl=new FrameworkLibrary();
		Pine360Common obj_Pine=new Pine360Common();
		try
		{
			String[] arrS_TabList=new String[]{"More","Create User"};
			if(!obj_Pine.Pine360ClickTab(wb_driver,arrS_TabList))
			{
				return false;
			}
			
			String[] arrS_searchField=new String[]{"userNameSearch","customerNameSearch","lastNameSearch","profilePhoneSearch","citySearch"};
			String[] arrS_searchText=new String[]{arrS_userDetails[0],arrS_userDetails[1],arrS_userDetails[2],arrS_userDetails[3],arrS_userDetails[4]};
			Pine360Search(wb_driver, arrS_searchField, arrS_searchText);
			
			if(Pine360ClickSearchItem(wb_driver, arrS_userDetails[0], "Update",""))
			{
				WebElement wbE_firstName = wb_driver.findElement(By.id("customerName"));
				if(!obj_Fl.ComCheckEmptyString(arrS_userDetails[5]))
	        	{
	        		obj_Fl.ComEnterText(wbE_firstName,arrS_userDetails[5]);
	        	}
				
	        	WebElement wbE_lastName = wb_driver.findElement(By.id("lastName"));
	        	if(!obj_Fl.ComCheckEmptyString(arrS_userDetails[6]))
	        	{
	        		obj_Fl.ComEnterText(wbE_lastName,arrS_userDetails[6]);
	        	}
	        	
	        	WebElement wbE_userStatus = wb_driver.findElement(By.id("status"));
	        	if(!obj_Fl.ComCheckEmptyString(arrS_userDetails[7]))
	        	{
	        		wbE_userStatus.sendKeys(arrS_userDetails[7]);
	        	}
				
	        	WebElement wbE_dob=wb_driver.findElement(By.xpath("//img[@alt='Birth Date']"));
	        	if(!obj_Fl.ComCheckEmptyString(arrS_userDetails[8]))
	        	{
		        	wbE_dob.click();
		        	obj_Fl.ComSetDate(wb_driver,arrS_userDetails[8],"MM/dd/yyyy");
		        }
	        	
	        	WebElement wbE_state = wb_driver.findElement(By.id("state"));
	        	if(!obj_Fl.ComCheckEmptyString(arrS_userDetails[9]))
	        	{
		        	obj_Fl.ComSelectDropDownValue(wb_driver, wbE_state, arrS_userDetails[9]);
		        	Thread.sleep(2000);
	        	}
	        	
	        	WebElement wbE_city = wb_driver.findElement(By.id("city"));
	        	if(!obj_Fl.ComCheckEmptyString(arrS_userDetails[10]))
	        	{
	        		wbE_city.sendKeys(arrS_userDetails[10]);
	        	}
	        	
	        	WebElement wbE_email = wb_driver.findElement(By.id("customerEmail"));
	        	if(!obj_Fl.ComCheckEmptyString(arrS_userDetails[11]))
	        	{
	        		obj_Fl.ComEnterText(wbE_email,arrS_userDetails[11]);
	        	}
	        	
	        	WebElement wbE_phone=wb_driver.findElement(By.id("profilePhone"));
	        	if(!obj_Fl.ComCheckEmptyString(arrS_userDetails[12]))
	        	{
	        		obj_Fl.ComEnterText(wbE_phone,arrS_userDetails[12]);
	        	}
	        	
	        	WebElement wbE_role = wb_driver.findElement(By.id("role"));
	        	if(!obj_Fl.ComCheckEmptyString(arrS_userDetails[13]))
	        	{
	        		wbE_role.sendKeys(arrS_userDetails[13]);	   
	        	}
	        	
	        	WebElement wbE_btnSave=wb_driver.findElement(By.xpath("//img[@alt='Save']"));
		        wbE_btnSave.click();
	        	if(obj_Fl.ComCompareDivLinkText(wb_driver, "successMsgDiv",arrS_userDetails[14] ))
				{
					return true;
				}
			}
	        return false;
		}
		catch(Exception e)
		{
			obj_Fl.ComPrintException("Unable to update user",  e.toString());
			return false;
		}
	}
	
	public boolean Pine360EditCustomer(WebDriver wb_driver,String[] arrS_customerDetails)
	{
		FrameworkLibrary obj_Fl=new FrameworkLibrary();
		Pine360Common obj_Pine=new Pine360Common();
		try
		{
			String[] arrS_TabList=new String[]{"Customer"};
			if(!obj_Pine.Pine360ClickTab(wb_driver,arrS_TabList))
			{
				return false;
			}
			
			String[] arrS_searchField=new String[]{"firstName","lastName","mobileNumber","cardPan1","externalId"};
			String[] arrS_searchText=new String[]{arrS_customerDetails[0],arrS_customerDetails[1],arrS_customerDetails[2],arrS_customerDetails[3],arrS_customerDetails[4]};
			Pine360Search(wb_driver, arrS_searchField, arrS_searchText);
			
			if(Pine360ClickSearchItem(wb_driver, arrS_customerDetails[0], "Update",""))
			{
				WebElement  wbE_Title=wb_driver.findElement(By.id("customerTitle"));
				if(!obj_Fl.ComCheckEmptyString(arrS_customerDetails[5]))
				{
					obj_Fl.ComSelectDropDownValue(wb_driver, wbE_Title, arrS_customerDetails[5]);
				}
				
				WebElement  wbE_FirstName=wb_driver.findElement(By.id("customer.firstName"));
				if(!obj_Fl.ComCheckEmptyString(arrS_customerDetails[6]))
				{
					obj_Fl.ComEnterText(wbE_FirstName,arrS_customerDetails[6]);
				}
				
				WebElement  wbE_LastName=wb_driver.findElement(By.id("customer.lastName"));
				if(!obj_Fl.ComCheckEmptyString(arrS_customerDetails[7]))
				{
					obj_Fl.ComEnterText(wbE_LastName,arrS_customerDetails[7]);
				}
				
				WebElement  wbE_Gender=wb_driver.findElement(By.id("genderID"));
				if(!obj_Fl.ComCheckEmptyString(arrS_customerDetails[8]))
				{
					obj_Fl.ComSelectDropDownValue(wb_driver, wbE_Gender, arrS_customerDetails[8]);
				}
				
				WebElement  wbE_Email=wb_driver.findElement(By.id("contact.email"));
				if(!obj_Fl.ComCheckEmptyString(arrS_customerDetails[10]))
				{
					obj_Fl.ComEnterText(wbE_Email,arrS_customerDetails[10]);
				}
				
				String[] arrS_date=new String[]{};
				WebElement  wbE_DobMonth=wb_driver.findElement(By.id("dob_month"));
				WebElement  wbE_DobYear=wb_driver.findElement(By.id("dob_year"));
				WebElement  wbE_DobDay=wb_driver.findElement(By.id("dob_day"));
				if(!obj_Fl.ComCheckEmptyString(arrS_customerDetails[9]))
				{
					
					arrS_date=arrS_customerDetails[9].split("/");
					
					obj_Fl.ComSelectDropDownValue(wb_driver, wbE_DobDay, arrS_date[0]);
					wbE_DobMonth.sendKeys(arrS_date[1]);
					obj_Fl.ComSelectDropDownValue(wb_driver, wbE_DobYear, arrS_date[2]);
				}
				
				
				WebElement  wbE_AnniDay=wb_driver.findElement(By.id("mad_day"));
				WebElement  wbE_AnniMonth=wb_driver.findElement(By.id("mad_month"));
				WebElement  wbE_AnniYear=wb_driver.findElement(By.id("mad_year"));
				if(!obj_Fl.ComCheckEmptyString(arrS_customerDetails[11]))
				{
					arrS_date=arrS_customerDetails[11].split("/");
					obj_Fl.ComSelectDropDownValue(wb_driver, wbE_AnniDay, arrS_date[0]);
					wbE_AnniMonth.sendKeys(arrS_date[1]);
					obj_Fl.ComSelectDropDownValue(wb_driver, wbE_AnniYear, arrS_date[2]);
				}
				
				WebElement  wbE_Address=wb_driver.findElement(By.id("contact.address1"));
				if(!obj_Fl.ComCheckEmptyString(arrS_customerDetails[12]))
				{
					obj_Fl.ComEnterText(wbE_Address,arrS_customerDetails[12]);
				}
				
				WebElement  wbE_State=wb_driver.findElement(By.id("contact.state"));
				if(!obj_Fl.ComCheckEmptyString(arrS_customerDetails[13]))
				{
					obj_Fl.ComSelectDropDownValue(wb_driver, wbE_State, arrS_customerDetails[13]);
					Thread.sleep(2000);
				}
				
				WebElement  wbE_City=wb_driver.findElement(By.id("city"));
				if(!obj_Fl.ComCheckEmptyString(arrS_customerDetails[14]))
				{
					wbE_State.click();
					wbE_City.sendKeys(arrS_customerDetails[14]);
				}
				
				WebElement  wbE_Pincode=wb_driver.findElement(By.id("pincode"));
				if(!obj_Fl.ComCheckEmptyString(arrS_customerDetails[15]))
				{
					obj_Fl.ComEnterText(wbE_Pincode,arrS_customerDetails[15]);
				}
				
				WebElement  wbE_Landline=wb_driver.findElement(By.id("contact.homePhone"));
				if(!obj_Fl.ComCheckEmptyString(arrS_customerDetails[16]))
				{
					obj_Fl.ComEnterText(wbE_Landline,arrS_customerDetails[16]);
				}
				
				WebElement  wbE_Mobile=wb_driver.findElement(By.id("contact.mobileNumber"));
				if(!obj_Fl.ComCheckEmptyString(arrS_customerDetails[17]))
				{
					obj_Fl.ComEnterText(wbE_Mobile,arrS_customerDetails[17]);
				}
				
				WebElement  wbE_btnSave=wb_driver.findElement(By.xpath("//img[@alt='Save']"));
				wbE_btnSave.click();
				if(obj_Fl.ComIsAlertPresent(wb_driver, arrS_customerDetails[24]) || obj_Fl.ComCompareDivLinkText(wb_driver, "successMsgDiv", arrS_customerDetails[24]))
				{
					return true;
				} 
			}
			return false;
		}
		catch(Exception e)
		{
			obj_Fl.ComPrintException("Unable to edit Customer",e.toString());
			return false;
		}
	}
}
