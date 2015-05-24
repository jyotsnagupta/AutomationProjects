package framework.com.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComGetTestStepsList 
{
	static ComExcelReader testdataXL;
	public static Map<String,List<String>> getMap(String str_testdir)
	{
		try
		{
			Map<String, List<String>> map = null;
			if(map == null) 
			{
				map = new HashMap<String, List<String>>();
				testdataXL= new ComExcelReader(str_testdir);
				for(int i_StepID=2; i_StepID <= testdataXL.comGetRowCount(ComConstants.TEST_STEPS_SHEET); i_StepID++)
				{
					String str_TCID=testdataXL.comGetCellData(ComConstants.TEST_STEPS_SHEET, ComConstants.TCID, i_StepID);
					List<String> list_hashKey = map.get(str_TCID);
					if(list_hashKey== null)
					{
						list_hashKey = new ArrayList<String>();
						map.put(str_TCID, list_hashKey);
					}
					String str_keyword=testdataXL.comGetCellData(ComConstants.TEST_STEPS_SHEET, ComConstants.KEYWORD, i_StepID);
					String str_objectId=testdataXL.comGetCellData(ComConstants.TEST_STEPS_SHEET, ComConstants.OBJECTId, i_StepID);
					String str_objectName=testdataXL.comGetCellData(ComConstants.TEST_STEPS_SHEET, ComConstants.OBJECT, i_StepID);
					String str_testData=testdataXL.comGetCellData(ComConstants.TEST_STEPS_SHEET, ComConstants.DATA, i_StepID);
					list_hashKey.add(str_keyword + ";" + str_objectId + ";" + str_objectName + ";" + str_testData);
					
				}
			}
			return map;
		}
		catch(Exception e)
		{
			return null;
		}
	}

}
