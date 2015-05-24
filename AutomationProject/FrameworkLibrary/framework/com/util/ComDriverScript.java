package framework.com.util;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pine360.com.util.*;

public class ComDriverScript 
{
	public static ComExcelReader suiteXLS;
	public static ComExcelReader currentTestCaseXls;
	
	public static int iCurrentTCID;
	public static int iCurrentTSID;
	public static int iCurrentTDSID;
	
	public static WebDriver driver=null;
	public static String strObjectId;
	public static String strObjectName;
	public static String strTestData;
	public static String strKeyword;
	public static String strUserDir;
	public static String strTestdir;
	public static String strCurrentTestCaseName;
	public static String strCurrentTestSuite;
	public static String strKeywordExecutionResult;
	
	public static ArrayList<String> strlistResultSet;
	public static List<String> listTestSteps= null;
	public static Logger APP_LOGS;
	
	public ComDriverScript() throws IOException
	{
		strUserDir=System.getProperty("user.dir");
		strlistResultSet=new ArrayList<String>();
	}
	
	public void comStartTest() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		APP_LOGS= Logger.getLogger("devpinoyLogger");
		APP_LOGS.debug("Intialize Suite xlsx");
		suiteXLS = new ComExcelReader(strUserDir + "\\TestData\\Suite.xls"); //Read suite Excel to find out Project to be run
		
		for(int i_currentSuiteID=2; i_currentSuiteID <= suiteXLS.comGetRowCount(ComConstants.TEST_SUITE_SHEET); i_currentSuiteID++)
		{
			// Get TestSuiteName
			strCurrentTestSuite = suiteXLS.comGetCellData(ComConstants.TEST_SUITE_SHEET, ComConstants.Test_Suite_ID, i_currentSuiteID); 
			//Verify if Suite test to be executed. Execute only if Runmode is "Y"
			if(suiteXLS.comGetCellData(ComConstants.TEST_SUITE_SHEET, ComConstants.RUNMODE, i_currentSuiteID).equals(ComConstants.RUNMODE_YES)) 
			{
				// Get Test Data excel
				strTestdir= strUserDir + suiteXLS.comGetCellData(ComConstants.TEST_SUITE_SHEET, ComConstants.TestDirPath, i_currentSuiteID);
				//Read all test cases to be executed with Runmode "Y"
				List<String> listcurrentTestCase=ComGetTestCasesList.comGetTestCase(strTestdir);
				// Read all Test Steps from Excel into memory (string Map)
				Map<String, List<String>> mapTestSteps =ComGetTestStepsList.getMap(strTestdir); // Read all Test Steps
				
				for(int i=0;i< listcurrentTestCase.size();i++) //Loop for each test case
				{
					strCurrentTestCaseName=listcurrentTestCase.get(i);
					listTestSteps = mapTestSteps.get(strCurrentTestCaseName); //get relevant test steps
					currentTestCaseXls =new ComExcelReader(strTestdir);
					if(currentTestCaseXls.comIsSheetExist(strCurrentTestCaseName))
					{
						// Get count of Test Data and execute Test Cases for each of them
						iCurrentTCID=currentTestCaseXls.comGetRowCount(strCurrentTestCaseName);
						for( iCurrentTDSID=2; iCurrentTDSID <= iCurrentTCID;iCurrentTDSID++)
						{
							String colName=ComConstants.RESULT +(ComDriverScript.iCurrentTDSID-1);
							comExecuteFramework();
							ComCreateXLSReport.comCreateXLSReport(colName);
							strlistResultSet.clear();
						}
					}
					else
					{ 
						iCurrentTDSID=2;
						String colName=ComConstants.RESULT +(ComDriverScript.iCurrentTDSID-1);
						comExecuteFramework();
						ComCreateXLSReport.comCreateXLSReport(colName);
						strlistResultSet.clear();
					}
				}
			}
		}	
		
		ComCreateXLSReport.comMarkSkipXLSReport();
		try 
		{
			ComReportUtil.comCreateReport();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	public void comExecuteFramework()
	{
		try
		{
			for ( int iStepcount=0; iStepcount < listTestSteps.size(); iStepcount++)
			{
				String strarraySteps[] = listTestSteps.get(iStepcount).split("\\;",-1);
				strKeyword= strarraySteps[0];
				strObjectId= strarraySteps[1];
				strObjectName= strarraySteps[2];
				String strdataCol= "";
				if(strarraySteps.length  > 3)
				{
					strdataCol=strarraySteps[3];
				}
				
				if(strdataCol.toLowerCase().startsWith(ComConstants.COL))
				{
					String[] temp = strdataCol.split(ComConstants.dataSeparator);
					String colName=temp[1];
					strTestData= currentTestCaseXls.comGetCellData(strCurrentTestCaseName, colName, iCurrentTDSID);
				}
				else if(strdataCol.toLowerCase().startsWith(ComConstants.MasterData.toLowerCase()))
				{
					String[] temp = strdataCol.split(ComConstants.dataSeparator);
					String colName=temp[1];
					strTestData= currentTestCaseXls.comGetCellData(ComConstants.MasterData, colName, 2);
				}
				else 
				{
					strTestData=strdataCol;
				}
				try
				{
					if(strKeyword.toLowerCase().startsWith("com"))
					{
						Class<?> frameworkClass = Class.forName(ComFrameworkLibrary.class.getName());
						Method frameworkMethod = frameworkClass.getMethod(strKeyword, new Class[] {});
						strKeywordExecutionResult = (String)frameworkMethod.invoke(new ComFrameworkLibrary());
					}
					else if(strKeyword.toLowerCase().startsWith("pine360"))
					{
						Class<?> pineClass = Class.forName(Pine360Common.class.getName());
						Method pineMethod = pineClass.getMethod(strKeyword, new Class[] {});
						strKeywordExecutionResult = (String)pineMethod.invoke(new Pine360Common());
					}
					else if(strKeyword.toLowerCase().startsWith("pinegift"))
					{
						Class<?> pineClass = Class.forName(PineGiftCommon.class.getName());
						Method pineGiftMethod = pineClass.getMethod(strKeyword, new Class[] {});
						strKeywordExecutionResult = (String)pineGiftMethod.invoke(new PineGiftCommon());
					}
					if(strKeywordExecutionResult.equals(ComConstants.KEYWORD_FAIL))
					{
						strKeywordExecutionResult=strKeywordExecutionResult + "- " + strTestData;
					}
					strlistResultSet.add(strKeywordExecutionResult);
				}
				catch(NoSuchMethodException e) 
				{
					APP_LOGS.debug("No Such Method found Exception. Method Name: " + strKeyword);
					strlistResultSet.add(strKeywordExecutionResult);
				}
			}
		}
		catch(Exception e)
		{
			APP_LOGS.debug("Error while Executing Function: " + strKeyword);
		}
	}
}
		

