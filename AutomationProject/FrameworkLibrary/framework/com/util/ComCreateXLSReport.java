package framework.com.util;

public class ComCreateXLSReport 
{
	public static void comCreateXLSReport(String colName)
	{
		try
		{
			boolean isColExist=false;
			for(int i_count=0;i_count<ComDriverScript.currentTestCaseXls.comGetColumnCount(ComConstants.TEST_STEPS_SHEET);i_count++)
			{
				if(ComDriverScript.currentTestCaseXls.comGetCellData(ComConstants.TEST_STEPS_SHEET,i_count , 1).equals(colName))
				{
					isColExist=true;
					break;
				}
			}
			
			if(!isColExist)
			{
				ComDriverScript.currentTestCaseXls.comAddColumn(ComConstants.TEST_STEPS_SHEET, colName);
			}
			
			int index=0;
			for(int i_count=2;i_count<=ComDriverScript.currentTestCaseXls.comGetRowCount(ComConstants.TEST_STEPS_SHEET);i_count++)
			{
				if(ComDriverScript.strCurrentTestCaseName.equals(ComDriverScript.currentTestCaseXls.comGetCellData(ComConstants.TEST_STEPS_SHEET, ComConstants.TCID, i_count)))
				{
					if(ComDriverScript.strlistResultSet.size()==0)
					{
						ComDriverScript.currentTestCaseXls.comSetCellData(ComConstants.TEST_STEPS_SHEET, colName, i_count, ComConstants.KEYWORD_SKIP);
					}
					else
					{
						ComDriverScript.currentTestCaseXls.comSetCellData(ComConstants.TEST_STEPS_SHEET, colName, i_count, ComDriverScript.strlistResultSet.get(index));
					}
					index++;
				}
			}
			
			if(ComDriverScript.strlistResultSet.size()==0)
			{
				// skip
				ComDriverScript.currentTestCaseXls.comSetCellData(ComDriverScript.strCurrentTestCaseName, ComConstants.RESULT, ComDriverScript.iCurrentTDSID, ComConstants.KEYWORD_SKIP);
				return;
			}
			else
			{
				for(int i_count=0;i_count<ComDriverScript.strlistResultSet.size();i_count++)
				{
					if(!ComDriverScript.strlistResultSet.get(i_count).equals(ComConstants.KEYWORD_PASS))
					{
						ComDriverScript.currentTestCaseXls.comSetCellData(ComDriverScript.strCurrentTestCaseName, ComConstants.RESULT, ComDriverScript.iCurrentTDSID, ComConstants.KEYWORD_FAIL);
						return;
					}
				}
			}
			ComDriverScript.currentTestCaseXls.comSetCellData(ComDriverScript.strCurrentTestCaseName, ComConstants.RESULT, ComDriverScript.iCurrentTDSID, ComConstants.KEYWORD_PASS);
		}
		catch(Exception e)
		{
			System.out.println("Error while executing comCreateXLSReport function");
			e.printStackTrace();
		}
	}
	
	public static void comFillxl(String colName,int rown)
	{
		try
		{
		boolean isColExist=false;
		for(int i_count=0;i_count<ComDriverScript.currentTestCaseXls.comGetColumnCount(ComConstants.TEST_STEPS_SHEET);i_count++)
		{
			if(ComDriverScript.currentTestCaseXls.comGetCellData(ComConstants.TEST_STEPS_SHEET,i_count , 1).equals(colName))
			{
				isColExist=true;
				break;
			}
		}
		
		if(!isColExist)
		{
			ComDriverScript.currentTestCaseXls.comAddColumn(ComConstants.TEST_STEPS_SHEET, colName);
		}
		ComDriverScript.currentTestCaseXls.comSetCellData(ComConstants.TEST_STEPS_SHEET, colName, rown, ComDriverScript.strKeywordExecutionResult);
		}
		catch(Exception e)
		{
			System.out.println("Error Here");
		}
	}
	
	public static void comMarkSkipXLSReport()
	{
		try
		{
			int iRowCount= ComDriverScript.currentTestCaseXls.comGetRowCount(ComConstants.TEST_STEPS_SHEET);
			int iColCount = ComDriverScript.currentTestCaseXls.comGetColumnCount(ComConstants.TEST_STEPS_SHEET);
			
			for ( int iCount = 0; iCount < iColCount;iCount++)
			{
				String colValue=ComDriverScript.currentTestCaseXls.comGetCellData(ComConstants.TEST_STEPS_SHEET, iCount, 1);
				if(colValue.toLowerCase().startsWith("result"))
				{
					for(int iRowNum = 2; iRowNum <= iRowCount; iRowNum++)
					{
						String cellValue=ComDriverScript.currentTestCaseXls.comGetCellData(ComConstants.TEST_STEPS_SHEET, iCount, iRowNum);
						if(cellValue.isEmpty())
						{
							ComDriverScript.currentTestCaseXls.comSetCellData(ComConstants.TEST_STEPS_SHEET, colValue, iRowNum, ComConstants.KEYWORD_SKIP);
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("Error while executing comMarkSkipXLSReport function");
			e.printStackTrace();
		}
	}
}
