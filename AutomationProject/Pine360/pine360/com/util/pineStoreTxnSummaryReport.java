package pine360.com.util;

import framework.com.util.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class pineStoreTxnSummaryReport 
{
	public void pine360GetResultSet()
	{
		Connection connection=null;
		PreparedStatement pStatement = null;
		ResultSet rSet=null;
		try
		{
			connection=ComCreateDbConnection.comGetDBConnection(Pine360Constants.JDBC_URL, Pine360Constants.DB_USER, Pine360Constants.DB_PASSWORD);
			pStatement = connection.prepareStatement(Pine360Constants.StoreSummary);
			rSet = pStatement.executeQuery();
			ComCompareData.comCompare(rSet, "D:\\Test.xls");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				if(rSet != null)
				{
						rSet.close();
				}
				if(pStatement!=null)
				{
						pStatement.close();
				}
				connection.close();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
			
		}
	}
}
