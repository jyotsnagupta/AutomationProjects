package framework.com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ComCreateDbConnection 
{
	private static Connection connection = null;
	
	public synchronized static Connection comGetDBConnection(String JDBC_URL,String DB_USER, String DB_PASSWORD) throws SQLException 
	{
		if(connection == null)
		{
			try 
			{
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				connection= DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
			} 
			catch (ClassNotFoundException e) 
			{
				e.printStackTrace();

			}
		}
		
		return connection;
		
	}
	
	public synchronized static void comCloseConnection() throws SQLException 
	{
		if(connection != null)
		{
			connection.close();
		}
	}
}
