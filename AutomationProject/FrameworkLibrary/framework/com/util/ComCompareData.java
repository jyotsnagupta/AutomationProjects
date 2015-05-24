package framework.com.util;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ComCompareData 
{
	public static void comCompare(ResultSet rSet,String strExcel) throws SQLException
	{
		ComExcelReader xl=new ComExcelReader(strExcel);
		xl.comAddColumn("Sheet 1", "Result");
		int colcount=rSet.getMetaData().getColumnCount();
		
		while (rSet.next())
		{
			for ( int count=1;count<colcount;count++)
			{
				int rowN=rSet.getRow();
				String xlData=xl.comGetCellData("Sheet 1", count-1, rowN+1);
				String rowData=rSet.getString(count);
				
				System.out.println("Excel:" + xlData);
				System.out.println("RowData" + rowData);
				if(xlData.toLowerCase().equals(rowData.toLowerCase()))
				{
					System.out.println("Row:" + rowN + " ,Column:" + count + " --> Matches");
				}
				else
				{
					System.out.println("Row:" + rowN + " ,Column:" + count + " --> Does not Match");
					break;
				}
			}
		}
	}
}
