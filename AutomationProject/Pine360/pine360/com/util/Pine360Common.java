package pine360.com.util;

import java.text.*;
import java.util.*;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import framework.com.util.*;

public class Pine360Common 
{
	public static int iRoleCount=0;
	//*********************************************************************************************************************
	//* Function Name    : pine360ClickMore
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
	public String pine360ClickMore()
	{
		try
		{
			ComFrameworkLibrary.comLogging(ComConstants.LOGGING_START);
			WebElement wbTabList=ComDriverScript.driver.findElement(By.className("navigationBlueTabs"));
			WebElement wbMenu=wbTabList.findElement(By.id("menu"));
			WebElement wbMenuList=wbMenu.findElement(By.className("menulink"));
			Actions builder=new Actions(ComDriverScript.driver);
			builder.moveToElement(wbMenuList).build().perform();
			Thread.sleep(2000);
			WebElement wbSubMenu=ComDriverScript.driver.findElement(By.linkText(ComDriverScript.strTestData));
			wbSubMenu.click();
			return ComConstants.KEYWORD_PASS;
		}
		catch(Exception e)
		{
			ComFrameworkLibrary.comLogging(e.toString());
			return ComConstants.KEYWORD_FAIL;
		}
		finally
		{
			ComFrameworkLibrary.comLogging(ComConstants.LOGGING_END);
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
	public static boolean Pine360Search(WebDriver wb_driver,String[] arrS_searchField,String[] arrS_searchText)
	{
		try
		{
			ComFrameworkLibrary.comLogging(ComConstants.LOGGING_START);
			WebElement wbE_Search,wbE_SchButton;
			int i_lensearchField=arrS_searchField.length;
			int i_lensearchText=arrS_searchText.length;
			if(i_lensearchField==i_lensearchText)
			{
				for(int i_count=0;i_count<i_lensearchField;i_count++)
				{
					wbE_Search=wb_driver.findElement(By.id(arrS_searchField[i_count]));
					ComFrameworkLibrary.comEnterText(wbE_Search, arrS_searchText[i_count]);
				}
			}
			wbE_SchButton=wb_driver.findElement(By.xpath("//img[@alt='Search']"));
			wbE_SchButton.click();
			return true;
		}
		catch(Exception e)
		{
			ComFrameworkLibrary.comLogging(e.toString());
			return false;
		}
		finally
		{
			ComFrameworkLibrary.comLogging(ComConstants.LOGGING_END);
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
		try
		{
			//String[] arrS_TabList=new String[]{"Customer"};
			WebElement  wbE_Title=wb_driver.findElement(By.id("customerTitle"));
			ComFrameworkLibrary.comSelectDropdown();
			
			WebElement  wbE_FirstName=wb_driver.findElement(By.id("customer.firstName"));
			ComFrameworkLibrary.comEnterText(wbE_FirstName,arrS_customerDetails[1]);
			
			WebElement  wbE_LastName=wb_driver.findElement(By.id("customer.lastName"));
			ComFrameworkLibrary.comEnterText(wbE_LastName,arrS_customerDetails[2]);
			
			WebElement  wbE_Gender=wb_driver.findElement(By.id("genderID"));
			ComFrameworkLibrary.comSelectDropdown();
			
			WebElement  wbE_Email=wb_driver.findElement(By.id("contact.email"));
			ComFrameworkLibrary.comEnterText(wbE_Email,arrS_customerDetails[5]);
			
			String[] Date=new String[]{};
			WebElement  wbE_DobDay=wb_driver.findElement(By.id("dob_day"));
			WebElement  wbE_DobMonth=wb_driver.findElement(By.id("dob_month"));
			WebElement  wbE_DobYear=wb_driver.findElement(By.id("dob_year"));
			Date=arrS_customerDetails[4].split("/");
			ComFrameworkLibrary.comSelectDropdown();
			wbE_DobMonth.sendKeys(Date[1]);
			ComFrameworkLibrary.comSelectDropdown();
			
			WebElement  wbE_AnniDay=wb_driver.findElement(By.id("mad_day"));
			WebElement  wbE_AnniMonth=wb_driver.findElement(By.id("mad_month"));
			WebElement  wbE_AnniYear=wb_driver.findElement(By.id("mad_year"));
			Date=arrS_customerDetails[6].split("/");
			ComFrameworkLibrary.comSelectDropdown();
			wbE_AnniMonth.sendKeys(Date[1]);
			ComFrameworkLibrary.comSelectDropdown();
			
			WebElement  wbE_Address=wb_driver.findElement(By.id("contact.address1"));
			ComFrameworkLibrary.comEnterText(wbE_Address,arrS_customerDetails[7]);
			
			WebElement  wbE_State=wb_driver.findElement(By.id("contact.state"));
			ComFrameworkLibrary.comSelectDropdown();
			Thread.sleep(2000);
			wbE_State.click();
			
			WebElement  wbE_City=wb_driver.findElement(By.id("city"));
			ComFrameworkLibrary.comSelectDropdown();
			//wbE_City.click();
			//wbE_City.sendKeys(arrS_customerDetails[9]);
			
			WebElement  wbE_Pincode=wb_driver.findElement(By.id("pincode"));
			ComFrameworkLibrary.comEnterText(wbE_Pincode,arrS_customerDetails[10]);
			
			WebElement  wbE_Landline=wb_driver.findElement(By.id("contact.homePhone"));
			ComFrameworkLibrary.comEnterText(wbE_Landline,arrS_customerDetails[11]);
			
			WebElement  wbE_Mobile=wb_driver.findElement(By.id("contact.mobileNumber"));
			ComFrameworkLibrary.comEnterText(wbE_Mobile,arrS_customerDetails[12]);
			
			if(!ComFrameworkLibrary.comCheckEmptyString(arrS_customerDetails[13]))
			{
				WebElement  wbE_LychkBox=wb_driver.findElement(By.cssSelector("input[id='programIds'][value='1']"));
				WebElement  wbE_LyMembership=wb_driver.findElement(By.id("cardMembershipId_1"));
				WebElement  wbE_LyTier=wb_driver.findElement(By.id("cardMembershipTierId_1"));
				wbE_LychkBox.click();
				ComFrameworkLibrary.comSelectDropdown();
				Thread.sleep(2000);
				wbE_LyMembership.click();
				ComFrameworkLibrary.comSelectDropdown();
			}
			
			if(!ComFrameworkLibrary.comCheckEmptyString(arrS_customerDetails[15]))
			{
				WebElement  wbE_GiftchkBox=wb_driver.findElement(By.cssSelector("input[id='programIds'][value='2']"));
				WebElement  wbE_GiftMembership=wb_driver.findElement(By.id("cardMembershipId_2"));
				WebElement  wbE_GiftTier=wb_driver.findElement(By.id("cardMembershipTierId_2"));
				wbE_GiftchkBox.click();
				ComFrameworkLibrary.comSelectDropdown();
				Thread.sleep(2000);
				wbE_GiftMembership.click();
				ComFrameworkLibrary.comSelectDropdown();
			}
			
			if(!ComFrameworkLibrary.comCheckEmptyString(arrS_customerDetails[17]))
			{
				WebElement  wbE_PrepaidchkBox=wb_driver.findElement(By.cssSelector("input[id='programIds'][value='3']"));
				WebElement  wbE_PrepaidMembership=wb_driver.findElement(By.id("cardMembershipId_3"));
				WebElement  wbE_PrepaidTier=wb_driver.findElement(By.id("cardMembershipTierId_3"));
				wbE_PrepaidchkBox.click();
				ComFrameworkLibrary.comSelectDropdown();
				Thread.sleep(2000);
				wbE_PrepaidMembership.click();
				ComFrameworkLibrary.comSelectDropdown();
			}
			
			//wb_driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
			if(Boolean.valueOf(arrS_customerDetails[19]))
			{
				try
				{
					WebElement wbE_MobileCard=wb_driver.findElement(By.cssSelector("input[id='cardTypeId'][value='1']"));
					//if(wbE_MobileCard.isDisplayed()) -- To Check
					if(wbE_MobileCard.isEnabled())
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
					//if(wbE_PhysicalCard.isDisplayed())  -- To Check
					if(wbE_PhysicalCard.isEnabled())
					{
						wbE_PhysicalCard.click();
					}
				}
				catch(NoSuchElementException Ex)
				{
					
				}
			}
			//wb_driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
			WebElement  wbE_CardNumber=wb_driver.findElement(By.id("cardID"));
			ComFrameworkLibrary.comEnterText(wbE_CardNumber,arrS_customerDetails[21]);
			
			WebElement  wbE_Store=wb_driver.findElement(By.id("enrolStoreId"));
			ComFrameworkLibrary.comSelectDropdown();
			
			WebElement  wbE_btnSave=wb_driver.findElement(By.xpath("//img[@alt='Save']"));
			wbE_btnSave.click();
			
			if(ComFrameworkLibrary.comIsAlertPresent() || ComFrameworkLibrary.comCompareDivLinkText())
			{
				return true;
			} 
			return false;
		}
		catch(Exception e)
		{
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
	public String pine360SelectRolePermissions()
	{
		try
		{
			ComFrameworkLibrary.comLogging(ComConstants.LOGGING_START);
			ArrayList<String> roleList = new ArrayList<String>();
			roleList=ComFrameworkLibrary.ComReadExcel(ComDriverScript.strTestdir, ComDriverScript.strTestData);
			
			String[] roleDetails = roleList.get(iRoleCount).split("\\;",-1);
			for(int loop=4;loop < roleDetails.length;loop++)
			{
				if(!roleDetails[loop].isEmpty())
				{
					ComDriverScript.driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
					ComDriverScript.strTestData= "Row:" + (iRoleCount+1) + ", Col:" + loop;
					WebElement wbPermission = ComDriverScript.driver.findElement(By.xpath("//*[@value='"+ roleDetails[loop] +"']"));
					wbPermission.click();
				}
			}
			return ComConstants.KEYWORD_PASS;
		}
		catch(Exception e)
		{
			ComFrameworkLibrary.comLogging(e.toString());
			return ComConstants.KEYWORD_FAIL;
		}
		finally
		{
			iRoleCount++;
			ComFrameworkLibrary.comLogging(ComConstants.LOGGING_END);
			ComDriverScript.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
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
		try
		{
			//String[] arrS_TabList=new String[]{"More","Card Ordering"};
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
			return false;
		}
	}
	
	public static boolean Pine360CreateNormalOrder(WebDriver wb_driver,String[] arrS_orderDetails)
	{
		try
		{
			WebElement  wbE_OrderName=wb_driver.findElement(By.id("orderName"));
			ComFrameworkLibrary.comEnterText(wbE_OrderName,arrS_orderDetails[0]);
			
			WebElement  wbE_OrderDes=wb_driver.findElement(By.id("orderDescription"));
			ComFrameworkLibrary.comEnterText(wbE_OrderDes,arrS_orderDetails[1]);
			
			WebElement  wbE_CardType=wb_driver.findElement(By.id("cardHolderType"));
			ComFrameworkLibrary.comSelectDropdown();
			
			WebElement  wbE_CardScheme=wb_driver.findElement(By.id("schemeGroupId"));
			ComFrameworkLibrary.comSelectDropdown();
			
			WebElement  wbE_Quantity=wb_driver.findElement(By.id("stockOrderQuantity"));
			ComFrameworkLibrary.comEnterText(wbE_Quantity,arrS_orderDetails[3]);
				
			WebElement  wbE_btnSave=wb_driver.findElement(By.xpath("//img[@alt='Save']"));
			wbE_btnSave.click();
			if(ComFrameworkLibrary.comIsAlertPresent() || ComFrameworkLibrary.comCompareDivLinkText())
			{
				return true;
			}
			return false;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	public boolean Pine360CreatePrimaryOrder(WebDriver wb_driver,String[] arrS_orderDetails)
	{
		try
		{
			WebElement  wbE_OrderName=wb_driver.findElement(By.id("orderName"));
			ComFrameworkLibrary.comEnterText(wbE_OrderName,arrS_orderDetails[0]);
			
			WebElement  wbE_OrderDes=wb_driver.findElement(By.id("orderDescription"));
			ComFrameworkLibrary.comEnterText(wbE_OrderDes,arrS_orderDetails[1]);
			
			WebElement  wbE_CardType=wb_driver.findElement(By.id("cardHolderType"));
			ComFrameworkLibrary.comSelectDropdown();
			
			WebElement  wbE_CardScheme=wb_driver.findElement(By.id("schemeGroupId"));
			ComFrameworkLibrary.comSelectDropdown();
			
			WebElement wbE_fileName=wb_driver.findElement(By.id("uploadFile"));
			wbE_fileName.sendKeys(arrS_orderDetails[3]);
				
			WebElement  wbE_btnSave=wb_driver.findElement(By.xpath("//img[@alt='Save']"));
			wbE_btnSave.click();
			if(ComFrameworkLibrary.comIsAlertPresent() || ComFrameworkLibrary.comCompareDivLinkText())
			{
				return true;
			}
			return false;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	public boolean Pine360CreateSecondaryOrder(WebDriver wb_driver,String[] arrS_orderDetails)
	{
		try
		{
			WebElement  wbE_OrderName=wb_driver.findElement(By.id("orderName"));
			ComFrameworkLibrary.comEnterText(wbE_OrderName,arrS_orderDetails[0]);
			
			WebElement  wbE_OrderDes=wb_driver.findElement(By.id("orderDescription"));
			ComFrameworkLibrary.comEnterText(wbE_OrderDes,arrS_orderDetails[1]);
			
			WebElement  wbE_CardType=wb_driver.findElement(By.id("cardHolderType"));
			ComFrameworkLibrary.comSelectDropdown();
			
			WebElement wbE_fileName=wb_driver.findElement(By.id("uploadFile"));
			wbE_fileName.sendKeys(arrS_orderDetails[3]);
				
			WebElement  wbE_btnSave=wb_driver.findElement(By.xpath("//img[@alt='Save']"));
			wbE_btnSave.click();
			
			WebElement wbE_newCardScheme=wb_driver.findElement(By.id("selectedId1"));
			ComFrameworkLibrary.comSelectDropdown();
			
			wbE_btnSave=wb_driver.findElement(By.xpath("//img[@alt='Save']"));
			wbE_btnSave.click();
			
			if(ComFrameworkLibrary.comIsAlertPresent() || ComFrameworkLibrary.comCompareDivLinkText())
			{
				return true;
			}
			return false;
		}
		catch(Exception e)
		{
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
		try
		{
			//String[] arrS_TabList=new String[]{"More","Card Ordering"};
			WebElement wbE_StockOrder=wb_driver.findElement(By.id("stockOrderId"));
			wbE_StockOrder.click();
			Pine360Search(wb_driver, new String[] {"searchOrderName"}, new String[] {str_StockOrderName});
			Pine360ClickSearchItem(wb_driver,str_StockOrderName,str_Action,"");
			
			if(str_Action.equalsIgnoreCase("cancel"))
			{
				return ComFrameworkLibrary.comIsAlertPresent();
			}
			return true;
		}
		catch(Exception e)
		{
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
		try
		{
			WebElement wbE_Action=wb_driver.findElement(By.id("actionType"));
			WebElement  wbE_btnSave=wb_driver.findElement(By.xpath("//img[@alt='Save']"));
			
			wbE_Action.sendKeys(str_Action);
			wbE_btnSave.click();
			Thread.sleep(10000);
			if(ComFrameworkLibrary.comIsAlertPresent() || ComFrameworkLibrary.comCompareDivLinkText())
			{
				return true;
			}
			return false;
		}
		catch(Exception e)
		{
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
			ComFrameworkLibrary.comSelectDropdown();
			
			WebElement  wbbtnSave=wb_driver.findElement(By.xpath("//img[@alt='Save']"));
			wbbtnSave.click();
			Thread.sleep(10000);
			if(ComFrameworkLibrary.comIsAlertPresent() || ComFrameworkLibrary.comCompareDivLinkText())
			{
				return true;
			}
			return false;
		}
		catch(Exception e)
		{
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
		int i_btnCount=0;
		try
		{
			//String[] arrS_TabList=new String[]{"More","Card Ordering"};
			WebElement wbE_cardScheme=wb_driver.findElement(By.id("cardSchemeId"));
			wbE_cardScheme.click();
			
			WebElement wbE_schemeName=wb_driver.findElement(By.id("schemeName"));
			ComFrameworkLibrary.comEnterText(wbE_schemeName, arrS_cardschemeDetails[0]);
			
			WebElement wbE_schemeDescription=wb_driver.findElement(By.id("schemeDescription"));
			ComFrameworkLibrary.comEnterText(wbE_schemeDescription, arrS_cardschemeDetails[1]);
			
			WebElement wbE_schemeStatus=wb_driver.findElement(By.id("schemeStatus"));
			ComFrameworkLibrary.comSelectDropdown();
			
			WebElement wbE_cardType=wb_driver.findElement(By.id("cardType"));
			ComFrameworkLibrary.comSelectDropdown();
			
			WebElement wbE_product=wb_driver.findElement(By.id("binrangeProduct"));
			ComFrameworkLibrary.comSelectDropdown();
			
			WebElement wbE_corporate=wb_driver.findElement(By.id("binrangeCorporate"));
			ComFrameworkLibrary.comSelectDropdown();
			
			WebElement wbE_cardholderType=wb_driver.findElement(By.id("cardHolderType"));
			ComFrameworkLibrary.comSelectDropdown();
			
			WebElement wbE_binRange=wb_driver.findElement(By.id("binRangeId"));
			wbE_binRange.sendKeys(arrS_cardschemeDetails[10]);
			
			// Loyalty Scheme Enabled
			if(Boolean.valueOf(arrS_cardschemeDetails[5]))
			{
				WebElement wbE_isloyaltyType=wb_driver.findElement(By.id("loyaltySelected"));
				wbE_isloyaltyType.click();
				
				WebElement wbE_lyMembership=wb_driver.findElement(By.id("loyaltyMembershipId"));
				ComFrameworkLibrary.comSelectDropdown();
				
				WebElement wbE_lyTier=wb_driver.findElement(By.id("loyaltyTierId"));
				ComFrameworkLibrary.comSelectDropdown();
				
				WebElement wbE_lyDenomination=wb_driver.findElement(By.id("loyaltyDenominationId"));
				ComFrameworkLibrary.comSelectDropdown();
				
				WebElement wbE_lyfirsttxnAmount=wb_driver.findElement(By.id("loyaltyFirstAwardValue"));
				ComFrameworkLibrary.comEnterText(wbE_lyfirsttxnAmount, arrS_cardschemeDetails[14]);
				
				WebElement wbE_lyreplacementFees=wb_driver.findElement(By.id("loyaltyReplacementFee"));
				ComFrameworkLibrary.comEnterText(wbE_lyreplacementFees, arrS_cardschemeDetails[15]);
				
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
					ComFrameworkLibrary.comEnterText(wbE_lypreloadedPoints, arrS_cardschemeDetails[20]);
				}
				
				WebElement wbE_lyminBalance=wb_driver.findElement(By.id("loyaltyMinCardBalance"));
				ComFrameworkLibrary.comEnterText(wbE_lyminBalance, arrS_cardschemeDetails[21]);
				
				WebElement wbE_lymaxBalance=wb_driver.findElement(By.id("loyaltyMaxCardBalance"));
				ComFrameworkLibrary.comEnterText(wbE_lymaxBalance, arrS_cardschemeDetails[22]);
				
				WebElement wbE_lyactivateLoad=wb_driver.findElement(By.id("loyaltyActivationType"));
				ComFrameworkLibrary.comSelectDropdown();
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
					ComFrameworkLibrary.comEnterText(wbE_lyactivateAfter, arrS_cardschemeDetails[24].split("-")[0]);
					
					WebElement wbE_lyactivateDuration=wb_driver.findElement(By.id("loyaltyActivationDurationType"));
					ComFrameworkLibrary.comSelectDropdown();
				}
				
				WebElement wbE_lyactivateRedeem=wb_driver.findElement(By.id("loyaltyRedemptionType"));
				ComFrameworkLibrary.comSelectDropdown();
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
					ComFrameworkLibrary.comEnterText(wbE_lyactivateAfter, arrS_cardschemeDetails[26].split("-")[0]);
					
					WebElement wbE_lyactivateDuration=wb_driver.findElement(By.id("loyaltyRedemptionDurationType"));
					ComFrameworkLibrary.comSelectDropdown();
				}
				
				WebElement wbE_lyExpiry=wb_driver.findElement(By.id("loyaltyExpiryType"));
				ComFrameworkLibrary.comSelectDropdown();
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
					ComFrameworkLibrary.comEnterText(wbE_lyactivateAfter, arrS_cardschemeDetails[28].split("-")[0]);
					
					WebElement wbE_lyactivateDuration=wb_driver.findElement(By.id("loyaltyExpiryDurationType"));
					ComFrameworkLibrary.comSelectDropdown();
				}
				i_btnCount=1;
			}
			
			if(Boolean.valueOf(arrS_cardschemeDetails[6]))
			{
				WebElement wbE_isprepaidType=wb_driver.findElement(By.id("prepaidSelected"));
				wbE_isprepaidType.click();
				
				WebElement wbE_prPMembership=wb_driver.findElement(By.id("prepaidMembershipId"));
				ComFrameworkLibrary.comSelectDropdown();
				
				WebElement wbE_prPTier=wb_driver.findElement(By.id("prepaidTierId"));
				ComFrameworkLibrary.comSelectDropdown();
				
				WebElement wbE_prPDenomination=wb_driver.findElement(By.id("prepaidDenominationId"));
				ComFrameworkLibrary.comSelectDropdown();
				
				WebElement wbE_prPfirsttxnAmount=wb_driver.findElement(By.id("prepaidFirstAwardValue"));
				ComFrameworkLibrary.comEnterText(wbE_prPfirsttxnAmount, arrS_cardschemeDetails[32]);
				
				WebElement wbE_prPreplacementFees=wb_driver.findElement(By.id("prepaidReplacementFee"));
				ComFrameworkLibrary.comEnterText(wbE_prPreplacementFees, arrS_cardschemeDetails[33]);
				
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
					ComFrameworkLibrary.comEnterText(wbE_prPpreloadedPoints, arrS_cardschemeDetails[38]);
				}
				
				WebElement wbE_prPminBalance=wb_driver.findElement(By.id("prepaidMinCardBalance"));
				ComFrameworkLibrary.comEnterText(wbE_prPminBalance, arrS_cardschemeDetails[39]);
				
				WebElement wbE_prPmaxBalance=wb_driver.findElement(By.id("prepaidMaxCardBalance"));
				ComFrameworkLibrary.comEnterText(wbE_prPmaxBalance, arrS_cardschemeDetails[40]);
				
				WebElement wbE_prPactivateLoad=wb_driver.findElement(By.id("prepaidActivationType"));
				ComFrameworkLibrary.comSelectDropdown();
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
					ComFrameworkLibrary.comEnterText(wbE_prPactivateAfter, arrS_cardschemeDetails[42].split("-")[0]);
					
					WebElement wbE_prPactivateDuration=wb_driver.findElement(By.id("prepaidActivationDurationType"));
					ComFrameworkLibrary.comSelectDropdown();
				}
				
				WebElement wbE_prPactivateRedeem=wb_driver.findElement(By.id("prepaidRedemptionType"));
				ComFrameworkLibrary.comSelectDropdown();
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
					ComFrameworkLibrary.comEnterText(wbE_lyactivateAfter, arrS_cardschemeDetails[44].split("-")[0]);
					
					WebElement wbE_lyactivateDuration=wb_driver.findElement(By.id("prepaidRedemptionDurationType"));
					ComFrameworkLibrary.comSelectDropdown();
				}
				
				WebElement wbE_prPExpiry=wb_driver.findElement(By.id("prepaidExpiryType"));
				ComFrameworkLibrary.comSelectDropdown();
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
					ComFrameworkLibrary.comEnterText(wbE_lyactivateAfter, arrS_cardschemeDetails[46].split("-")[0]);
					
					WebElement wbE_lyactivateDuration=wb_driver.findElement(By.id("prepaidExpiryDurationType"));
					ComFrameworkLibrary.comSelectDropdown();
				}
				i_btnCount=2;
			}
			
			List<WebElement> wb_btnSave=wb_driver.findElements(By.xpath("//img[@alt='Save']"));
			wb_btnSave.get(i_btnCount).click();
			if(ComFrameworkLibrary.comIsAlertPresent() || ComFrameworkLibrary.comCompareDivLinkText())
			{
				return true;
			}
			return false;
		}
		catch(Exception e)
		{
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
			return false;
		}
	}
	
	public boolean Pine360ClickSearchItem(WebDriver wb_driver,String str_searchText,String str_Action,String str_nosearchMsg)
	{
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
			return false;
		}
	}
	
	public boolean Pine360EditUser(WebDriver wb_driver, String[] arrS_userDetails)
	{
		try
		{
			//String[] arrS_TabList=new String[]{"More","Create User"};
			String[] arrS_searchField=new String[]{"userNameSearch","customerNameSearch","lastNameSearch","profilePhoneSearch","citySearch"};
			String[] arrS_searchText=new String[]{arrS_userDetails[0],arrS_userDetails[1],arrS_userDetails[2],arrS_userDetails[3],arrS_userDetails[4]};
			Pine360Search(wb_driver, arrS_searchField, arrS_searchText);
			
			if(Pine360ClickSearchItem(wb_driver, arrS_userDetails[0], "Update",""))
			{
				WebElement wbE_firstName = wb_driver.findElement(By.id("customerName"));
				if(!ComFrameworkLibrary.comCheckEmptyString(arrS_userDetails[5]))
	        	{
	        		ComFrameworkLibrary.comEnterText(wbE_firstName,arrS_userDetails[5]);
	        	}
				
	        	WebElement wbE_lastName = wb_driver.findElement(By.id("lastName"));
	        	if(!ComFrameworkLibrary.comCheckEmptyString(arrS_userDetails[6]))
	        	{
	        		ComFrameworkLibrary.comEnterText(wbE_lastName,arrS_userDetails[6]);
	        	}
	        	
	        	WebElement wbE_userStatus = wb_driver.findElement(By.id("status"));
	        	if(!ComFrameworkLibrary.comCheckEmptyString(arrS_userDetails[7]))
	        	{
	        		wbE_userStatus.sendKeys(arrS_userDetails[7]);
	        	}
				
	        	WebElement wbE_dob=wb_driver.findElement(By.xpath("//img[@alt='Birth Date']"));
	        	if(!ComFrameworkLibrary.comCheckEmptyString(arrS_userDetails[8]))
	        	{
		        	wbE_dob.click();
		        	ComFrameworkLibrary.comSetDate();
		        }
	        	
	        	WebElement wbE_state = wb_driver.findElement(By.id("state"));
	        	if(!ComFrameworkLibrary.comCheckEmptyString(arrS_userDetails[9]))
	        	{
		        	ComFrameworkLibrary.comSelectDropdown();
		        	Thread.sleep(2000);
	        	}
	        	
	        	WebElement wbE_city = wb_driver.findElement(By.id("city"));
	        	if(!ComFrameworkLibrary.comCheckEmptyString(arrS_userDetails[10]))
	        	{
	        		wbE_city.sendKeys(arrS_userDetails[10]);
	        	}
	        	
	        	WebElement wbE_email = wb_driver.findElement(By.id("customerEmail"));
	        	if(!ComFrameworkLibrary.comCheckEmptyString(arrS_userDetails[11]))
	        	{
	        		ComFrameworkLibrary.comEnterText(wbE_email,arrS_userDetails[11]);
	        	}
	        	
	        	WebElement wbE_phone=wb_driver.findElement(By.id("profilePhone"));
	        	if(!ComFrameworkLibrary.comCheckEmptyString(arrS_userDetails[12]))
	        	{
	        		ComFrameworkLibrary.comEnterText(wbE_phone,arrS_userDetails[12]);
	        	}
	        	
	        	WebElement wbE_role = wb_driver.findElement(By.id("role"));
	        	if(!ComFrameworkLibrary.comCheckEmptyString(arrS_userDetails[13]))
	        	{
	        		wbE_role.sendKeys(arrS_userDetails[13]);	   
	        	}
	        	
	        	WebElement wbE_btnSave=wb_driver.findElement(By.xpath("//img[@alt='Save']"));
		        wbE_btnSave.click();
	        	if(ComFrameworkLibrary.comCompareDivLinkText())
				{
					return true;
				}
			}
	        return false;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	public boolean Pine360EditCustomer(WebDriver wb_driver,String[] arrS_customerDetails)
	{
		try
		{
			//String[] arrS_TabList=new String[]{"Customer"};
			String[] arrS_searchField=new String[]{"firstName","lastName","mobileNumber","cardPan1","externalId"};
			String[] arrS_searchText=new String[]{arrS_customerDetails[0],arrS_customerDetails[1],arrS_customerDetails[2],arrS_customerDetails[3],arrS_customerDetails[4]};
			Pine360Search(wb_driver, arrS_searchField, arrS_searchText);
			
			if(Pine360ClickSearchItem(wb_driver, arrS_customerDetails[0], "Update",""))
			{
				WebElement  wbE_Title=wb_driver.findElement(By.id("customerTitle"));
				if(!ComFrameworkLibrary.comCheckEmptyString(arrS_customerDetails[5]))
				{
					ComFrameworkLibrary.comSelectDropdown();
				}
				
				WebElement  wbE_FirstName=wb_driver.findElement(By.id("customer.firstName"));
				if(!ComFrameworkLibrary.comCheckEmptyString(arrS_customerDetails[6]))
				{
					ComFrameworkLibrary.comEnterText(wbE_FirstName,arrS_customerDetails[6]);
				}
				
				WebElement  wbE_LastName=wb_driver.findElement(By.id("customer.lastName"));
				if(!ComFrameworkLibrary.comCheckEmptyString(arrS_customerDetails[7]))
				{
					ComFrameworkLibrary.comEnterText(wbE_LastName,arrS_customerDetails[7]);
				}
				
				WebElement  wbE_Gender=wb_driver.findElement(By.id("genderID"));
				if(!ComFrameworkLibrary.comCheckEmptyString(arrS_customerDetails[8]))
				{
					ComFrameworkLibrary.comSelectDropdown();
				}
				
				WebElement  wbE_Email=wb_driver.findElement(By.id("contact.email"));
				if(!ComFrameworkLibrary.comCheckEmptyString(arrS_customerDetails[10]))
				{
					ComFrameworkLibrary.comEnterText(wbE_Email,arrS_customerDetails[10]);
				}
				
				String[] arrS_date=new String[]{};
				WebElement  wbE_DobMonth=wb_driver.findElement(By.id("dob_month"));
				WebElement  wbE_DobYear=wb_driver.findElement(By.id("dob_year"));
				WebElement  wbE_DobDay=wb_driver.findElement(By.id("dob_day"));
				if(!ComFrameworkLibrary.comCheckEmptyString(arrS_customerDetails[9]))
				{
					
					arrS_date=arrS_customerDetails[9].split("/");
					
					ComFrameworkLibrary.comSelectDropdown();
					wbE_DobMonth.sendKeys(arrS_date[1]);
					ComFrameworkLibrary.comSelectDropdown();
				}
				
				
				WebElement  wbE_AnniDay=wb_driver.findElement(By.id("mad_day"));
				WebElement  wbE_AnniMonth=wb_driver.findElement(By.id("mad_month"));
				WebElement  wbE_AnniYear=wb_driver.findElement(By.id("mad_year"));
				if(!ComFrameworkLibrary.comCheckEmptyString(arrS_customerDetails[11]))
				{
					arrS_date=arrS_customerDetails[11].split("/");
					ComFrameworkLibrary.comSelectDropdown();
					wbE_AnniMonth.sendKeys(arrS_date[1]);
					ComFrameworkLibrary.comSelectDropdown();
				}
				
				WebElement  wbE_Address=wb_driver.findElement(By.id("contact.address1"));
				if(!ComFrameworkLibrary.comCheckEmptyString(arrS_customerDetails[12]))
				{
					ComFrameworkLibrary.comEnterText(wbE_Address,arrS_customerDetails[12]);
				}
				
				WebElement  wbE_State=wb_driver.findElement(By.id("contact.state"));
				if(!ComFrameworkLibrary.comCheckEmptyString(arrS_customerDetails[13]))
				{
					ComFrameworkLibrary.comSelectDropdown();
					Thread.sleep(2000);
				}
				
				WebElement  wbE_City=wb_driver.findElement(By.id("city"));
				if(!ComFrameworkLibrary.comCheckEmptyString(arrS_customerDetails[14]))
				{
					wbE_State.click();
					wbE_City.sendKeys(arrS_customerDetails[14]);
				}
				
				WebElement  wbE_Pincode=wb_driver.findElement(By.id("pincode"));
				if(!ComFrameworkLibrary.comCheckEmptyString(arrS_customerDetails[15]))
				{
					ComFrameworkLibrary.comEnterText(wbE_Pincode,arrS_customerDetails[15]);
				}
				
				WebElement  wbE_Landline=wb_driver.findElement(By.id("contact.homePhone"));
				if(!ComFrameworkLibrary.comCheckEmptyString(arrS_customerDetails[16]))
				{
					ComFrameworkLibrary.comEnterText(wbE_Landline,arrS_customerDetails[16]);
				}
				
				WebElement  wbE_Mobile=wb_driver.findElement(By.id("contact.mobileNumber"));
				if(!ComFrameworkLibrary.comCheckEmptyString(arrS_customerDetails[17]))
				{
					ComFrameworkLibrary.comEnterText(wbE_Mobile,arrS_customerDetails[17]);
				}
				
				WebElement  wbE_btnSave=wb_driver.findElement(By.xpath("//img[@alt='Save']"));
				wbE_btnSave.click();
				if(ComFrameworkLibrary.comIsAlertPresent() || ComFrameworkLibrary.comCompareDivLinkText())
				{
					return true;
				} 
			}
			return false;
		}
		catch(Exception e)
		{
			return false;
		}
	}
}
