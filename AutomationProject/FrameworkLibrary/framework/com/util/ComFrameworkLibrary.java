package framework.com.util;

import static framework.com.util.ComDriverScript.APP_LOGS;

import javax.xml.parsers.*;

import org.w3c.dom.*;

import java.io.*;
import java.lang.reflect.Method;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.*;

import java.util.*;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import jxl.*;

import java.text.*;

public class ComFrameworkLibrary
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

	public static ArrayList<String> comReadConfigFile(String strConfigFilePath)
	{
		try
		{
			comLogging(ComConstants.LOGGING_START);
			ArrayList<String> arrL_LoginData = new ArrayList<String>();
			DocumentBuilderFactory dbF_Factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder db_Builder = dbF_Factory.newDocumentBuilder();
			Document dM_doc = db_Builder.parse(new File(strConfigFilePath));
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
			comLogging(e.toString());
			return null;
		}
		finally
		{
			comLogging(ComConstants.LOGGING_END);
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

	public String comOpenBrowser()
	{
		String strBrowserdriverPath=System.getProperty("user.dir") + "\\BrowserDriverServer";
		try
		{
			comLogging(ComConstants.LOGGING_START);
			if (ComDriverScript.strTestData.equalsIgnoreCase("firefox"))
	        {
				ComDriverScript.driver = new FirefoxDriver();
			}
	        else if (ComDriverScript.strTestData.equalsIgnoreCase("chrome"))
	        {
	        	// Set Path for the executable file
	        	System.setProperty("webdriver.chrome.driver",strBrowserdriverPath + "\\chromedriver.exe");
	        	ComDriverScript.driver = new ChromeDriver();
	        } 
	        else if (ComDriverScript.strTestData.equalsIgnoreCase("ie")) 
	        {
	        	// Set Path for the executable file
	        	System.setProperty("webdriver.ie.driver", strBrowserdriverPath + "\\IEDriverServer.exe");
	        	ComDriverScript.driver = new InternetExplorerDriver();
	        }
	        else
	        {
	        	throw new IllegalArgumentException("The Browser Type is Undefined");
	        }
			ComDriverScript.driver.manage().window().maximize();
			ComDriverScript.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			return ComConstants.KEYWORD_PASS;
		}
		catch(Exception e)
		{
			comLogging(e.toString());
			comScreenshot();
			return ComConstants.KEYWORD_FAIL;
		}
		finally
		{
			comLogging(ComConstants.LOGGING_END);
		}
	}
	
	public String comNavigate()
	{	
		comLogging(ComConstants.LOGGING_START);
		try
		{
			ComDriverScript.driver.get(ComDriverScript.strTestData);
			return ComConstants.KEYWORD_PASS;
		}
		catch(Exception e)
		{
			comScreenshot();
			comLogging(e.toString());
			return ComConstants.KEYWORD_FAIL;
		}
		finally
		{
			comLogging(ComConstants.LOGGING_END);
		}
	 }
	
	public static String comClick()
	{
		try
		{
			comLogging(ComConstants.LOGGING_START);
			WebElement wbElement=comFindElement();
			wbElement.click();
			return ComConstants.KEYWORD_PASS;
		}
		catch(Exception e)
		{
			comLogging(e.toString());
			comScreenshot();
			return ComConstants.KEYWORD_FAIL;
		}
		finally
		{
			comLogging(ComConstants.LOGGING_END);
		}
	}
	
	public static String comSelectDropdown()
	{
		try
		{
			comLogging(ComConstants.LOGGING_START);
			if(!comCheckEmptyString(ComDriverScript.strTestData))
			{
				WebElement wbElement = comFindElement();
				Select selDropDown=new Select(wbElement);
				ComDriverScript.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
				selDropDown.selectByVisibleText(ComDriverScript.strTestData);
				return ComConstants.KEYWORD_PASS;
			}
			return ComConstants.KEYWORD_FAIL;
		}
		catch(Exception e)
		{
			comLogging(e.toString());
			comScreenshot();
			return ComConstants.KEYWORD_FAIL;
		}
		finally
		{
			comLogging(ComConstants.LOGGING_END);
			ComDriverScript.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}
	}
	
	public String comSelectList()
	{
		comLogging(ComConstants.LOGGING_START);
		try
		{
			ComDriverScript.driver.findElement(By.xpath(ComDriverScript.strObjectId)).sendKeys(ComDriverScript.strTestData);
			return ComConstants.KEYWORD_PASS;
		}
		catch(Exception e)
		{
			comScreenshot();
			comLogging(e.toString());
			return ComConstants.KEYWORD_FAIL;
		}
		finally
		{
			comLogging(ComConstants.LOGGING_END);
		}
	 }
	
	public String comVerifySuccessMsg() throws InterruptedException
	{
		comLogging(ComConstants.LOGGING_START);
		try
		{
			if(comIsAlertPresent() || comCompareDivLinkText())
			{
				return ComConstants.KEYWORD_PASS;
			}
			return ComConstants.KEYWORD_FAIL;
		}
		catch(Exception e)
		{
			comScreenshot();
			comLogging(e.toString());
			return ComConstants.KEYWORD_FAIL;
   		}
		finally
		{
			
			Thread.sleep(5000);
			comLogging(ComConstants.LOGGING_END);
		}
		
		}
	
	public static String comWriteInInput()
	{
		comLogging(ComConstants.LOGGING_START);
		try
        {
			WebElement wbElement= comFindElement();
        	wbElement.sendKeys(ComDriverScript.strTestData);
        	return ComConstants.KEYWORD_PASS;
    	}
        catch(Exception e)
        {
        	comScreenshot();
        	comLogging(e.toString());
        	return ComConstants.KEYWORD_FAIL;
   		}
		finally
		{
			comLogging(ComConstants.LOGGING_END);
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

	public static ArrayList<String> ComReadExcel(String str_excelFileName,String str_sheetName)
	{
		comLogging(ComConstants.LOGGING_START);
		ArrayList<String> strlistTestData=new ArrayList<String>();
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
					str_RowValue=str_RowValue + wb_sheet.getCell(i_count,i_rowC).getContents().toString() + ";";
					
				}
				strlistTestData.add(str_RowValue);
			}
			return strlistTestData;
		}
		catch(Exception e)
		{
			comLogging(e.toString());
			return null;
		}
		finally
		{
			wb_book.close();
			comLogging(ComConstants.LOGGING_END);
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
	public static String comSetDate()
    {
		try
		{
			comLogging(ComConstants.LOGGING_START);
			String strDateFormat="MM/dd/yyyy";
			Thread.sleep(2000);
			
	        Set<String> strsetWindows = ComDriverScript.driver.getWindowHandles();
	        Iterator<String> stritrIterator=strsetWindows.iterator();
	        
	        String strParentWindow=stritrIterator.next();
	        String strPopupWindows=stritrIterator.next();
	        
	        
	        ComDriverScript.driver.switchTo().window(strPopupWindows);    
	        DateFormat dF_dateformat=new SimpleDateFormat(strDateFormat);
	        
	        Calendar calender=Calendar.getInstance();
	        calender.setTime(dF_dateformat.parse(ComDriverScript.strTestData));
	        
	        String strYear=Integer.toString(calender.get(Calendar.YEAR));
	        int iMonth = calender.get(Calendar.MONTH);
	        String strDay =Integer.toString(calender.get(Calendar.DAY_OF_MONTH));
	        Select selDate = new Select(ComDriverScript.driver.findElement(By.id("cboYear")));
	        selDate.selectByVisibleText(strYear);
	        selDate = new Select(ComDriverScript.driver.findElement(By.id("cboMonth")));  
	        selDate.selectByIndex(iMonth);
	        ComDriverScript.driver.findElement(By.linkText(strDay)).click();      
	        ComDriverScript.driver.switchTo().window(strParentWindow); 
	        return ComConstants.KEYWORD_PASS;
	    }
		catch (Exception e)
		{
			comLogging(e.toString());
			return ComConstants.KEYWORD_FAIL;
		}
		finally
		{
			comLogging(ComConstants.LOGGING_END);
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
	public static Boolean comCheckEmptyString(String str_inputStr)
	{
		if(str_inputStr.equals(null) || str_inputStr.equals(""))
		{
			return true;
		}
		return false;
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
	public static boolean comIsAlertPresent()
	{
		try
		{
			comLogging(ComConstants.LOGGING_START);
			WebDriverWait wb_wait = new WebDriverWait(ComDriverScript.driver, 2 /*timeout in seconds*/);
			if(wb_wait.until(ExpectedConditions.alertIsPresent())==null)
			{
			       return false;
			}
			else
			{
			       Alert alert = ComDriverScript.driver.switchTo().alert();
			       System.out.println("Alert:" + alert.getText());
			       String str_alertText=alert.getText();
			       Thread.sleep(2000);
			       if(!str_alertText.equalsIgnoreCase(ComDriverScript.strTestData))
			       {
			    	   return false;
			       }
			       alert.accept();
			       return true;
			}
		}
		catch(NoAlertPresentException  ex)
		{
			comLogging(ex.toString());
			return false;
		}
		catch(Exception e)
		{
			comLogging(e.toString());
			return false;
		}
		finally
		{
			comLogging(ComConstants.LOGGING_END);
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
	public static boolean comCompareDivLinkText()
	{
		try
		{
			comLogging(ComConstants.LOGGING_START);
			WebElement wbSuccessMsg= comFindElement();
			String strDivText=wbSuccessMsg.getText();
			APP_LOGS.debug("Actual Text  : " + strDivText);
			APP_LOGS.debug("Expected Text: " + strDivText);
			if(strDivText.equalsIgnoreCase(ComDriverScript.strTestData))
			{
				return true;
			}
			comScreenshot();
			return false;
		}
		catch(Exception e)
		{
			comLogging(e.toString());
			return false;
		}
		finally
		{
			comLogging(ComConstants.LOGGING_END);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static WebElement comFindElement()
	{
		try
		{
			APP_LOGS.debug("Start executing Method comFindElement");
			String strarrayObjectId[] = ComDriverScript.strObjectId.split(",");
			String strarrayObjectName[] = ComDriverScript.strObjectName.split(",");
			if(strarrayObjectId == null || strarrayObjectId.length != strarrayObjectName.length )
			{
				APP_LOGS.debug(" Object Id length is null or length Mistmatch");
				return null;
			}
			List<WebElement> wblistElement=null;
			WebElement wb=null;
			Class<?> driverClass = Class.forName(ComDriverScript.driver.getClass().getName());
			Method getMethod = driverClass.getMethod("findElements", new Class[] {By.class });
			getMethod.setAccessible(true);
			By objid=null;
			for(int i_count=0; i_count < strarrayObjectId.length;i_count++)
			{
				switch(strarrayObjectId[i_count].toLowerCase())
				{
					case "name":
						objid=By.name(strarrayObjectName[i_count]);
						break;
					case "id":
						objid=By.id(strarrayObjectName[i_count]);
						break;
					case "xpath":
						String path=String.format(strarrayObjectName[i_count],ComDriverScript.strTestData);
						objid=By.xpath(path);
						break;
					case "linktext":
						objid=By.linkText(strarrayObjectName[i_count]);
						break;
					case "classname":
						objid=By.className(strarrayObjectName[i_count]);
						break;
					case "cssSelector":
						objid=By.cssSelector(strarrayObjectName[i_count]);
						break;
					default:
						break;
				}
				if(i_count==0)
				{
					ComDriverScript.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					wblistElement = (List<WebElement>)getMethod.invoke(ComDriverScript.driver, objid);
					for (WebElement element: wblistElement)
					{
						if(element.isDisplayed())
						{
							wb=element;
							break;
						}
					}
				}
				/*else
				{
					wbElement = (WebElement)getMethod.invoke(wbElement, objid);
					Actions builder=new Actions(ComDriverScript.driver);
					builder.moveToElement(wbElement).build().perform();
					Thread.sleep(1000);
				}*/
			}
			//return wbElement;
			return wb;
		}
		catch(Exception e)
		{
			APP_LOGS.debug("Error while executing Method comFindElement");
			ComDriverScript.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			return null;
		}
		finally
		{
			APP_LOGS.debug("Finished executing Method comFindElement");
			ComDriverScript.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}
	} 
	
	public static void comScreenshot()
	{
		try 
		{
			comLogging(ComConstants.LOGGING_START);
			File scrFile = ((TakesScreenshot)ComDriverScript.driver).getScreenshotAs(OutputType.FILE);
			String fileName=ComDriverScript.strKeyword+".jpg";
			FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+"//screenshots//"+fileName));
		} 
		catch (IOException e1) 
		{
			comLogging(e1.toString());
		}
		finally
		{
			comLogging(ComConstants.LOGGING_END);
		}
	}
	
	public  String comSelectDate()
	{
		try
        {
			comLogging(ComConstants.LOGGING_START);
        	DateFormat dateFormat2 = new SimpleDateFormat("dd"); 
            Date date2 = new Date();

            String today = dateFormat2.format(date2); 
    		WebElement dateWidget =ComDriverScript.driver.findElement(By.xpath(ComDriverScript.strObjectId));
    		List<WebElement> columns=dateWidget.findElements(By.tagName("td"));

    		for (WebElement cell: columns)
    		{
    			if (cell.getText().equals(today))
    			{
    				cell.click();
    				break;
    			}
    		}
    		return ComConstants.KEYWORD_PASS;
        }
        catch(Exception e)
        {
        	comScreenshot();
        	comLogging(e.toString());
        	return ComConstants.KEYWORD_FAIL;
   		}
		finally
		{
			comLogging(ComConstants.LOGGING_END);
		}
	}
	
	public static String comCloseBrowser()
	{
		try
		{
			comLogging(ComConstants.LOGGING_START);
			ComDriverScript.driver.close();
			return ComConstants.KEYWORD_PASS;
		}
		catch(Exception e)
		{
			comScreenshot();
        	comLogging(e.toString());
        	return ComConstants.KEYWORD_FAIL;
		}
		finally
		{
			comLogging(ComConstants.LOGGING_END);
		}
	}
	public static void comEnterText(WebElement wbElement,String Text)
	{
		wbElement.clear();
		wbElement.click();
		wbElement.sendKeys(Text);
	}
	
	public String comSleep()
	{
		comLogging(ComConstants.LOGGING_START);
		
		try 
		{
			int i_Time=Integer.parseInt(ComDriverScript.strTestData);
			Thread.sleep(i_Time);
			return ComConstants.KEYWORD_PASS;
		} 
		catch (InterruptedException e) 
		{
			comLogging(e.toString());
			return ComConstants.KEYWORD_FAIL;
		}
		finally
		{
			comLogging(ComConstants.LOGGING_END);
		}
	}
	
	public static void comLogging(String strPrint)
	{
		if(strPrint.equalsIgnoreCase(ComConstants.LOGGING_START))
		{
			APP_LOGS.debug("*************************************************************************************************");
			APP_LOGS.debug("Starting Executing Method:		" + ComDriverScript.strKeyword);
			APP_LOGS.debug("ObjectId:    					" + ComDriverScript.strObjectId);
			APP_LOGS.debug("Object Name: 					" + ComDriverScript.strObjectName);
			APP_LOGS.debug("Test Data:   					" + ComDriverScript.strTestData);
			APP_LOGS.debug("################################################################################################");
		}
		else if(strPrint.equalsIgnoreCase(ComConstants.LOGGING_END))
		{
			APP_LOGS.debug("################################################################################################");
			APP_LOGS.debug("Finished Executing Method:		" + ComDriverScript.strKeyword);
			APP_LOGS.debug("*************************************************************************************************");
		}
		else
		{
			APP_LOGS.debug("Error while executing Method:   " + ComDriverScript.strKeyword);
			APP_LOGS.debug(strPrint);
			comScreenshot();
		}
	}
}

