package framework.com.util;
import java.util.ArrayList;
import java.util.List;

public class ComGetTestCasesList 
{
	static ComExcelReader testCaseXL;
	public static List<String> comGetTestCase(String str_testdir)
	{
		try
		{
			List<String> arr_testSteps=null;
			if(arr_testSteps == null) 
			{
				arr_testSteps=new ArrayList<String>();
				testCaseXL= new ComExcelReader(str_testdir);
				for(int i_StepID=2; i_StepID <= testCaseXL.comGetRowCount(ComConstants.TEST_CASES_SHEET); i_StepID++)
				{
					if(testCaseXL.comGetCellData(ComConstants.TEST_CASES_SHEET, ComConstants.RUNMODE, i_StepID).equals(ComConstants.RUNMODE_YES) )
					{
						String str_TCID=testCaseXL.comGetCellData(ComConstants.TEST_CASES_SHEET, ComConstants.TCID, i_StepID);
						arr_testSteps.add(str_TCID);
					}
				}
			}
			return arr_testSteps;
		}
		catch(Exception e)
		{
			return null;
		}
	}
}
