package FrameworkLibrary;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import jxl.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class FrameworkLibrary
{
	//*********************************************************************************************************************
	//* Function Name    : ComReadConfigFile
	//*
	//* Description      : Get data from Config.XML file 
	//*
	//* Input            : str_configFilePath      [in] Required Object
	//*                   
	//* Output           : ArrayList [out] (Return the Node values for URL, UserName, Password)
	//*
	//* Example          : ComReadConfigFile("C:\Config.xml");
	//*                    
	//*********************************************************************************************************************
	//* Creation By      : Saurabh Agrawal 
	//* Date of Creation : 4 April, 2013 
	//*********************************************************************************************************************

	public ArrayList<String> ComReadConfigFile(String str_configFilePath)
	{
		try
		{
			ArrayList<String> arrL_LoginData = new ArrayList<String>();
			DocumentBuilderFactory dbF_Factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder db_Builder = dbF_Factory.newDocumentBuilder();
			Document dM_doc = db_Builder.parse(new File(str_configFilePath));
			NodeList nL_List = dM_doc.getElementsByTagName("Test");
			Node nNode=nL_List.item(0);
			if (nNode.getNodeType() == Node.ELEMENT_NODE)
			{
				Element eElement = (Element) nNode;
				arrL_LoginData.add(eElement.getElementsByTagName("URL").item(0).getTextContent());
				arrL_LoginData.add(eElement.getElementsByTagName("UserName").item(0).getTextContent());
				arrL_LoginData.add(eElement.getElementsByTagName("Password").item(0).getTextContent());
				arrL_LoginData.add(eElement.getElementsByTagName("LoginPage").item(0).getTextContent());
				arrL_LoginData.add(eElement.getElementsByTagName("Merchant").item(0).getTextContent());
			}
			return arrL_LoginData;
		}
		catch(Exception e)
		{
			ComPrintException("Unable to read config file",e.toString());
			return null;
		}
	}

	//*********************************************************************************************************************
	//* Function Name    : ComOpenURL
	//*
	//* Description      : Open the URL in FireFox WebBrowser   
	//*
	//* Input            : str_baseUrl      [in] Required Object
	//*                   
	//* Output           : wbE_driver [out] Return the Firefox WebwbE_driver object
	//*
	//* Example          : ComOpenURL("http://192.168.100.80:8080");
	//*                    
	//*********************************************************************************************************************
	//* Creation By      : Saurabh Agrawal 
	//* Date of Creation : 4 April, 2013 
	//*********************************************************************************************************************

	public WebDriver ComOpenURL(String str_baseUrl)
	{
		try
		{
			WebDriver wb_driver;
			wb_driver = new FirefoxDriver();
			wb_driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			wb_driver.manage().window().maximize();
			wb_driver.get(str_baseUrl);
			return wb_driver;
		}
		catch(Exception e)
		{
			ComPrintException("Unable to start firefox wbE_driver",e.toString());
			return null;
		}
	}
	
	//*********************************************************************************************************************
	//* Function Name    : ComReadExcel
	//*
	//* Description      : Read Test Data From Excel file   
	//*
	//* Input            : str_excelFileName      [in] Required Object
	//*					 : str_sheetName	      [in] Required Object
	//*
	//* Output           : arrL_TestData [out] Return the column values 
	//*
	//* Example          : ComReadConfigFile("C:\TestData.xls","CreateRole");
	//*                    
	//*********************************************************************************************************************
	//* Creation By      : Saurabh Agrawal 
	//* Date of Creation : 5 April, 2013 
	//*********************************************************************************************************************

	public ArrayList<String> ComReadExcel(String str_excelFileName,String str_sheetName)
	{
		ArrayList<String> arrL_TestData=new ArrayList<String>();
		File fL_xlsFile=new File(str_excelFileName);
		Workbook wb_book = null;
		try
		{
			wb_book=Workbook.getWorkbook(fL_xlsFile);
			Sheet wb_sheet=wb_book.getSheet(str_sheetName); 
			int colCount=wb_sheet.getColumns();
			int i_rowCount=wb_sheet.getRows();
			String str_RowValue;
			for(int i_rowC=1;i_rowC < i_rowCount;i_rowC++)
			{
				str_RowValue="";
				for(int i_count=0;i_count<colCount;i_count++)
				{
					str_RowValue=str_RowValue + wb_sheet.getCell(i_count,i_rowC).getContents() + ";";
				}
				arrL_TestData.add(str_RowValue);
			}
			return arrL_TestData;
		}
		catch(Exception e)
		{
			ComPrintException("Unable to read excel",e.toString());
			return null;
		}
		finally
		{
			wb_book.close();
		}
	}
	
	//*********************************************************************************************************************
	//* Function Name    : ComSetDate
	//*
	//* Description      : Set Date from Date Picker 
	//*
	//* Input            : wbE_driver      [in] Required Object
	//*					 : str_date		   [in] Required Object
	//*					 : str_dateFormat  [in] Required Object
	//*
	//* Output           : wbE_wbE_driver [out] 
	//*
	//* Example          : ComSetDate(wbE_driver,"14\03\2013","dd\MM\YYYY");
	//*                    
	//*********************************************************************************************************************
	//* Creation By      : Prakher Agarwal 
	//* Date of Creation : 9 April, 2013 
	//*********************************************************************************************************************
	public WebDriver ComSetDate(WebDriver wb_driver, String str_date, String str_dateFormat)
    {
		try
		{
	        Set<String> setS_windows = wb_driver.getWindowHandles();
	        Iterator<String> itrS_iterator=setS_windows.iterator();
	        String str_parentWindow=itrS_iterator.next();
	        String str_popupWindows=itrS_iterator.next();
	        wb_driver.switchTo().window(str_popupWindows);    
	        DateFormat dF_dateformat=new SimpleDateFormat(str_dateFormat);
	        Calendar cal_Calender=Calendar.getInstance();
	        cal_Calender.setTime(dF_dateformat.parse(str_date));
	        String str_Year=Integer.toString(cal_Calender.get(Calendar.YEAR));
	        int i_Month = cal_Calender.get(Calendar.MONTH);
	        String str_Day =Integer.toString(cal_Calender.get(Calendar.DAY_OF_MONTH));
	        Select sel_cbSelect = new Select(wb_driver.findElement(By.id("cboYear")));
	        sel_cbSelect.selectByVisibleText(str_Year);
	        sel_cbSelect = new Select(wb_driver.findElement(By.id("cboMonth")));  
	        sel_cbSelect.selectByIndex(i_Month);
	        wb_driver.findElement(By.linkText(str_Day)).click();      
	        wb_driver.switchTo().window(str_parentWindow); 
	        return wb_driver;
		}
		catch (Exception e)
		{
			ComPrintException("Unable to set date", e.toString());
			return null;
		}
    }
	
	//*********************************************************************************************************************
	//* Function Name    : ComCheckEmptyString
	//*
	//* Description      : Check string is empty or null
	//*
	//* Input            : str_inputStr      [in] Required Object
	//*   
	//* Output           : None 
	//*
	//* Example          : ComCheckEmptyString("nonemptystring");ComCheckEmptyString("")
	//*                    
	//*********************************************************************************************************************
	//* Creation By      : Saurabh Agarwal 
	//* Date of Creation : 21 April, 2014 
	//*********************************************************************************************************************
	public Boolean ComCheckEmptyString(String str_inputStr)
	{
		if(str_inputStr.equals(null) || str_inputStr.equals(""))
		{
			return true;
		}
		return false;
	}
	
	//*********************************************************************************************************************
	//* Function Name    : ComSelectDrpDownValue
	//*
	//* Description      : Select value from dropdown
	//*
	//* Input            : wbE_Element     				 [in] Required Object
	//* Input            : str_dropDownValue		     [in] Required Object
	//*   
	//* Output           : None 
	//*
	//* Example          : ComSelectDrpDownValue(wbstate,"Bihar");
	//*                    
	//*********************************************************************************************************************
	//* Creation By      : Saurabh Agarwal 
	//* Date of Creation : 21 April, 2014 
	//*********************************************************************************************************************
	public boolean ComSelectDropDownValue(WebDriver wb_driver,WebElement wbE_Element,String str_dropDownValue)
	{
		try
		{
			if(!ComCheckEmptyString(str_dropDownValue))
			{
				wb_driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
				Select sel_DropDown=new Select(wbE_Element);
				sel_DropDown.selectByVisibleText(str_dropDownValue);
				wb_driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				return true;
			}
			return false;
		}
		catch(Exception e)
		{
			String str_msg="Unable to find " +  str_dropDownValue + " in Dropdown";
			ComPrintException(str_msg,e.toString());
			return false;
		}
	}
	
	//*********************************************************************************************************************
	//* Function Name    : ComPrintException
	//*
	//* Description      : Print exception
	//*
	//* Input            : str_inputMsg      [in] Required Object
	//* Input            : str_ErrorMsg		 [in] Required Object
	//*   
	//* Output           : None 
	//*
	//* Example          : ComPrintException("This is exception1","This is exception2");
	//*                    
	//*********************************************************************************************************************
	//* Creation By      : Saurabh Agarwal 
	//* Date of Creation : 22 April, 2014 
	//*********************************************************************************************************************
	public void ComPrintException(String str_inputMsg, String str_ErrorMsg)
	{
		System.out.println(str_inputMsg);
		System.out.println("Error:" + str_ErrorMsg);
	}
	
	//*********************************************************************************************************************
	//* Function Name    : ComIsAlertPresent
	//*
	//* Description      : Check Alert window is present or not
	//*
	//* Input            : wbE_driver	     [in] Required Object
	//* Input            : str_AlertMsg      [in] Required Object
	//*   
	//* Output           : None 
	//*
	//* Example          : ComIsAlertPresent(wbE_driver);
	//*                    
	//*********************************************************************************************************************
	//* Creation By      : Saurabh Agarwal 
	//* Date of Creation : 22 April, 2014 
	//*********************************************************************************************************************
	public boolean ComIsAlertPresent(WebDriver wb_driver,String str_AlertMsg)
	{
		try
		{
			WebDriverWait wb_wait = new WebDriverWait(wb_driver, 2 /*timeout in seconds*/);
			if(wb_wait.until(ExpectedConditions.alertIsPresent())==null)
			{
			       return false;
			}
			else
			{
			       Alert alert = wb_driver.switchTo().alert();
			       System.out.println("Alert:" + alert.getText());
			       String str_alertText=alert.getText();
			       Thread.sleep(2000);
			       if(!str_alertText.equalsIgnoreCase(str_AlertMsg))
			       {
			    	   return false;
			       }
			       alert.accept();
			       return true;
			}
		}
		catch(NoAlertPresentException  ex)
		{
			ComPrintException("NoAlert",ex.toString());
			return false;
		}
		catch(Exception e)
		{
			ComPrintException("TimeOutException Info: Not an error",e.toString());
			return false;
		}
	}
	
	//*********************************************************************************************************************
	//* Function Name    : ComCompareDivLinkText
	//*
	//* Description      : Verify that attached text to any div matches the passed string
	//*
	//* Input            : wbE_driver     		[in] Required Object
	//* Input            : wbE_Idname		    [in] Required Object
	//* Input            : str_successText	    [in] Required Object
	//*   
	//* Output           : None 
	//*
	//* Example          : ComCompareDivLinkText(wbE_driver,"successDivMsg","Role Created successfully");
	//*                    
	//*********************************************************************************************************************
	//* Creation By      : Saurabh Agarwal 
	//* Date of Creation : 23 April, 2014 
	//*********************************************************************************************************************
	public boolean ComCompareDivLinkText(WebDriver wb_driver, String wbE_Idname,String str_successText)
	{
		WebElement wbE_SuccessMsg=wb_driver.findElement(By.id(wbE_Idname));
		String str_DivText=wbE_SuccessMsg.getText();
		if(str_DivText.equalsIgnoreCase(str_successText))
		{
			return true;
		}
		System.out.println(str_DivText);
		return false;
	}
	
	public String ComStringConcatenate(ArrayList<String> arrL_List)
	{
		String str_FailMsg="";
		if(arrL_List.size() > 0)
		{
			for(int i_count=0;i_count < arrL_List.size();i_count++ )
			{
				str_FailMsg=str_FailMsg + arrL_List.get(i_count);
			}
		}
		return str_FailMsg;
	}
	
	public void ComEnterText(WebElement wbElement,String Text)
	{
		wbElement.clear();
		wbElement.click();
		wbElement.sendKeys(Text);
	}
}

