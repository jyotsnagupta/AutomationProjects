package pine360.com.util;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

import framework.com.util.*;

public class PineGiftCommon 
{
	public static int iQty=0;
	public static int iAmount=0;
	public static int iTotalQty=0;
	public static int iDenomination=0;
	
	public static String strIndex;
	
	public String pineGiftSelectCorporate()
	{
		try
		{
			ComFrameworkLibrary.comLogging(ComConstants.LOGGING_START);
			//Find Element by Corporate Name
			WebElement wbName=ComFrameworkLibrary.comFindElement();
			//Get Element Id
			strIndex= wbName.getAttribute("id").toString().split("_")[1];
			return ComConstants.KEYWORD_PASS;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			ComFrameworkLibrary.comLogging(e.toString());
			return ComConstants.KEYWORD_FAIL;
		}
		finally
		{
			ComFrameworkLibrary.comLogging(ComConstants.LOGGING_END);
		}
	}
	
	public String pineGiftSelectDenomination()
	{
		try
		{
			ComFrameworkLibrary.comLogging(ComConstants.LOGGING_START);
			//Find Denomination Element
			String strDenoId="denoId_" + strIndex;
			WebElement wbDenomination=ComDriverScript.driver.findElement(By.id(strDenoId));
			WebElement wbDeenominationid= wbDenomination.findElement(By.name("denomination_id"));
			
			// Select Denomination
			Select sel_DropDown=new Select(wbDeenominationid);
			sel_DropDown.selectByVisibleText(ComDriverScript.strTestData);
			iDenomination = Integer.parseInt(ComDriverScript.strTestData);
			return ComConstants.KEYWORD_PASS;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			ComFrameworkLibrary.comLogging(e.toString());
			return ComConstants.KEYWORD_FAIL;
		}
		finally
		{
			ComFrameworkLibrary.comLogging(ComConstants.LOGGING_END);
		}
	}
	
	public String pineGiftEnterQuantity()
	{
		try
		{
			ComFrameworkLibrary.comLogging(ComConstants.LOGGING_START);
			String strCount= "voucherCount_" + strIndex;
			WebElement wbVoucher=ComDriverScript.driver.findElement(By.id(strCount));
			WebElement wbQuantity=wbVoucher.findElement(By.name("voucherCount"));
			wbQuantity.clear();
			wbQuantity.sendKeys(ComDriverScript.strTestData);
			iQty= Integer.parseInt(ComDriverScript.strTestData);
			iTotalQty=iTotalQty+ iQty;
			return ComConstants.KEYWORD_PASS;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			ComFrameworkLibrary.comLogging(e.toString());
			return ComConstants.KEYWORD_FAIL;
		}
		finally
		{
			ComFrameworkLibrary.comLogging(ComConstants.LOGGING_END);
		}
	}
	
	public String pineGiftSelectVoucher()
	{
		try
		{
			ComFrameworkLibrary.comLogging(ComConstants.LOGGING_START);
			String strSelect= "voucherSelect_" + strIndex;
			WebElement wbSelect=ComDriverScript.driver.findElement(By.id(strSelect));
			wbSelect.click();
			
			if(!ComDriverScript.strTestData.isEmpty())
			{
				try
				{
					if(ComFrameworkLibrary.comCompareDivLinkText())
					{
						return ComConstants.KEYWORD_PASS; 
					}
					else
					{
						return ComConstants.KEYWORD_FAIL;
					}
				}
				catch(Exception e)
				{
					return ComConstants.KEYWORD_FAIL;
				}
			}
			iAmount = iAmount + iDenomination*iQty;
			return ComConstants.KEYWORD_PASS;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			ComFrameworkLibrary.comLogging(e.toString());
			return ComConstants.KEYWORD_FAIL;
		}
		finally
		{
			ComFrameworkLibrary.comLogging(ComConstants.LOGGING_END);
		}
	}
	
	public String pineGiftVerifyQty()
	{
		try
		{
			ComFrameworkLibrary.comLogging(ComConstants.LOGGING_START);
			ComDriverScript.strTestData = "Total(Qty:" + iTotalQty + ") Rs. " + iAmount;
			if(ComFrameworkLibrary.comCompareDivLinkText())
			{
				return ComConstants.KEYWORD_PASS;
			}
			return ComConstants.KEYWORD_FAIL;
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
	
	public String pineGiftVerifyVoucherCount()
	{
		try
		{
			ComFrameworkLibrary.comLogging(ComConstants.LOGGING_START);
			WebElement wbTable= ComFrameworkLibrary.comFindElement();
			int iVoucherCount=  wbTable.findElements(By.className("table_inner_big_td")).size();
			
			if(iVoucherCount == iTotalQty)
			{
				return ComConstants.KEYWORD_PASS;
			}
			return ComConstants.KEYWORD_FAIL;
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
	
	public static String pineGiftClick()
	{
		try
		{
			ComFrameworkLibrary.comLogging(ComConstants.LOGGING_START);
			if(ComDriverScript.strTestData.isEmpty())
			{
				return ComConstants.KEYWORD_SKIP;
			}
			else
			{
				WebElement wbElement=ComFrameworkLibrary.comFindElement();
				wbElement.click();
				return ComConstants.KEYWORD_PASS;
			}
		}
		catch(Exception e)
		{
			ComFrameworkLibrary.comLogging(e.toString());
			ComFrameworkLibrary.comScreenshot();
			return ComConstants.KEYWORD_FAIL;
		}
		finally
		{
			ComFrameworkLibrary.comLogging(ComConstants.LOGGING_END);
		}
	}

	public static String pineGiftVerifySuccessMsg()
	{
		try
		{
			ComFrameworkLibrary.comLogging(ComConstants.LOGGING_START);
			if(ComDriverScript.strTestData.isEmpty())
			{
				return ComConstants.KEYWORD_SKIP;
			}
			else
			{
				WebElement wbSuccessMsg= ComFrameworkLibrary.comFindElement();
				String strDivText=wbSuccessMsg.getText();
				if(ComFrameworkLibrary.comIsAlertPresent() || strDivText.toLowerCase().contains(ComDriverScript.strTestData.toLowerCase()))
				{
					return ComConstants.KEYWORD_PASS;
				}
				return ComConstants.KEYWORD_FAIL;
			}
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

	public String pineGiftWriteInInput()
	{
		try
        {
			ComFrameworkLibrary.comLogging(ComConstants.LOGGING_START);
			if(ComDriverScript.strTestData.isEmpty())
			{
				return ComConstants.KEYWORD_SKIP;
			}
			else
			{
				return ComFrameworkLibrary.comWriteInInput();
			}
        }
        catch(Exception e)
        {
        	ComFrameworkLibrary.comScreenshot();
        	ComFrameworkLibrary.comLogging(e.toString());
        	return ComConstants.KEYWORD_FAIL;
   		}
		finally
		{
			ComFrameworkLibrary.comLogging(ComConstants.LOGGING_END);
		}
	}
}
