package pine360.com.util;

public class Pine360Constants 
{
	public static final String JDBC_URL = "jdbc:sqlserver://192.168.101.223:6135;DatabaseName=NovaPlus360_DBSchema";
	//public static final String JDBC_URL = "jdbc:sqlserver://192.168.101.159:6135;DatabaseName=TRM_TEST";
	public static final String DB_USER = "sa";
	public static final String DB_PASSWORD = "password@1234";
	//public static final String DB_PASSWORD = "password123";
	public static final String StoreSummary="Select Loyalty_Transactionid,TerminalID,CardPAN,TransactionAmount from Loyalty_Transaction(nolock) where OwnerID=54 and TransactionTime >='2014/12/01' and TerminalID=19767";
}
