package framework.com.util;
// reads the xls files and generates corresponding html reports
// Calls sendmail - mail
import java.io.*;
import java.util.Date;
import  framework.com.util.ComConstants;
import  framework.com.util.ComExcelReader;

public class ComReportUtil 
{
	public static String result_FolderName=null;
	static String currentTestName=null;
	static String currentTestRunmode=null;
	static String currentTestDescription=null;
	public static void comCreateReport() throws Exception 
	{
		// read suite.xls
		System.out.println("executing");
		Date d = new Date();
		String date=d.toString().replaceAll(" ", "_");
		date=date.replaceAll(":", "_");
		date=date.replaceAll("\\+", "_");
		System.out.println(date);
		result_FolderName=ComDriverScript.strUserDir + "\\Reports\\Report"+"_"+date;
		
		
		new File(result_FolderName).mkdirs();
		ComExcelReader suiteXLS = new ComExcelReader(ComDriverScript.strUserDir + "\\TestData\\Suite.xls");

		// create index.html
		String indexHtmlPath=result_FolderName+"\\index.html";
		new File(indexHtmlPath).createNewFile();
		
		try
		{
			FileWriter fstream = new FileWriter(indexHtmlPath);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write("<html><HEAD> <TITLE>Automation Test Results</TITLE></HEAD><body><h4 align=center><FONT COLOR=660066 FACE=AriaL SIZE=6><b><u> Automation Test Results</u></b></h4><table  border=1 cellspacing=1 cellpadding=1 ><tr><h4> <FONT COLOR=660000 FACE=Arial SIZE=4.5> <u>Test Details :</u></h4><td width=150 align=left bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2.75><b>Run Date</b></td><td width=150 align=left><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>");
			out.write(d.toString());
			out.write("</b></td></tr><tr><td width=150 align=left bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2.75><b>Run Environment</b></td><td width=150 align=left><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>");
			out.write("UAT");
			out.write("</b></td></tr><tr><td width=150 align= left  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2.75><b>Release</b></td><td width=150 align= left ><FONT COLOR=#153E7E FACE= Arial  SIZE=2.75><b>");
			out.write("2.0.0");
			out.write("</b></td></tr></table><h4> <FONT COLOR=660000 FACE= Arial  SIZE=4.5> <u>Report :</u></h4><table  border=1 cellspacing=1 cellpadding=1 width=100%><tr><td width=20% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>SUITE NAME</b></td><td width=40% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>DESCRIPTION</b></td><td width=10% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>EXECUTION RESULT</b></td></tr>");

			int totalTestSuites=suiteXLS.comGetRowCount(ComConstants.TEST_SUITE_SHEET);
			String currentTestSuite=null;
			ComExcelReader current_suite_xls=null;
			String suite_result="";
			for(int currentSuiteID =2;currentSuiteID<= totalTestSuites;currentSuiteID++)
			{
				suite_result="";
				currentTestSuite=null;
				current_suite_xls=null;
				currentTestSuite = suiteXLS.comGetCellData(ComConstants.TEST_SUITE_SHEET, ComConstants.SUITE_ID,currentSuiteID);
				String str_testdir= System.getProperty("user.dir") + suiteXLS.comGetCellData(ComConstants.TEST_SUITE_SHEET, ComConstants.TestDirPath, currentSuiteID);
				current_suite_xls=new ComExcelReader(str_testdir);
				for(int currentTestCaseID=2;currentTestCaseID<=current_suite_xls.comGetRowCount(ComConstants.TEST_CASES_SHEET);currentTestCaseID++)
				{
					
				    	 
					currentTestName = current_suite_xls.comGetCellData(ComConstants.TEST_CASES_SHEET, ComConstants.TCID, currentTestCaseID);
					currentTestDescription = current_suite_xls.comGetCellData(ComConstants.TEST_CASES_SHEET, ComConstants.DESCRIPTION, currentTestCaseID);
					currentTestRunmode = current_suite_xls.comGetCellData(ComConstants.TEST_CASES_SHEET, ComConstants.RUNMODE, currentTestCaseID);
					System.out.println(currentTestSuite + " -- "+currentTestName );
				    	 
					// make the file corresponding to test Steps
					String testSteps_file=result_FolderName+"\\"+currentTestSuite+"_steps.html";
					new File(testSteps_file).createNewFile();
						  
					int rows= current_suite_xls.comGetRowCount(ComConstants.TEST_STEPS_SHEET);
					int cols = current_suite_xls.comGetColumnCount(ComConstants.TEST_STEPS_SHEET);
					FileWriter fstream_test_steps= new FileWriter(testSteps_file);
					BufferedWriter out_test_steps= new BufferedWriter(fstream_test_steps);
					out_test_steps.write("<html><HEAD> <TITLE>"+currentTestSuite+" Test Results</TITLE></HEAD><body><h4 align=center><FONT COLOR=660066 FACE=AriaL SIZE=6><b><u> "+currentTestSuite+" Detailed Test Results</u></b></h4><table width=100% border=1 cellspacing=1 cellpadding=1 >");
					out_test_steps.write("<tr>");
					for(int colNum=0;colNum<cols;colNum++)
					{
						out_test_steps.write("<td align= center bgcolor=#153E7E><FONT COLOR=#ffffff FACE= Arial  SIZE=2><b>");
						out_test_steps.write(current_suite_xls.comGetCellData(ComConstants.TEST_STEPS_SHEET, colNum, 1));
					}
					out_test_steps.write("</b></tr>");

					// fill the whole sheet
					boolean result_col=false;
					for(int rowNum=2;rowNum<=rows;rowNum++)
					{
						out_test_steps.write("<tr>");  
						for(int colNum=0;colNum<cols;colNum++)
						{
							String data=current_suite_xls.comGetCellData(ComConstants.TEST_STEPS_SHEET, colNum, rowNum);
							result_col=current_suite_xls.comGetCellData(ComConstants.TEST_STEPS_SHEET, colNum, 1).startsWith(ComConstants.RESULT);
							if(data.isEmpty())
							{
								if(result_col)
								{
									data="SKIP";  
								}
								else
								{	  
									data=" ";
								}
							}
								  
							if((data.startsWith("Pass") || data.startsWith("PASS")) && result_col)
							{
								out_test_steps.write("<td align=center bgcolor=green><FONT COLOR=#000000 FACE= Arial  SIZE=1>");
							}
							else if((data.startsWith("Fail") || data.startsWith("FAIL")) && result_col)
							{
								out_test_steps.write("<td align=center bgcolor=red><FONT COLOR=#000000 FACE= Arial  SIZE=1>");
								if(suite_result.equals(""))
								{
									suite_result="FAIL";
								}
							}
							else if((data.startsWith("Skip") || data.startsWith("SKIP")) && result_col)
							{
								out_test_steps.write("<td align=center bgcolor=yellow><FONT COLOR=#000000 FACE= Arial  SIZE=1>");
							}
							else 
							{
								out_test_steps.write("<td align= center bgcolor=#ffffff><FONT COLOR=#000000 FACE= Arial  SIZE=1>");
							
							}
							out_test_steps.write(data);
								  
						}
						out_test_steps.write("</tr>");
					}
					out_test_steps.write("</tr>");
					out_test_steps.write("</table>");  
					out_test_steps.close();
				 }
				   
				  out.write("<tr><td width=20% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");
				  out.write("<a href="+currentTestSuite.replace(" ", "%20")+"_steps.html>"+currentTestSuite+"</a>");
				  out.write("</b></td><td width=40% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");
				  out.write(suiteXLS.comGetCellData(ComConstants.TEST_SUITE_SHEET, ComConstants.DESCRIPTION,currentSuiteID));
				  out.write("</b></td><td width=10% align=center  bgcolor=");
				  
				  if(suiteXLS.comGetCellData(ComConstants.TEST_SUITE_SHEET, ComConstants.RUNMODE,currentSuiteID).equalsIgnoreCase(ComConstants.RUNMODE_YES))
				  {
					  if(suite_result.equals("FAIL"))
					  {
						  out.write("red><FONT COLOR=153E7E FACE=Arial SIZE=2><b>FAIL</b></td></tr>");
					  }
					  else
					  {
							  out.write("green><FONT COLOR=153E7E FACE=Arial SIZE=2><b>PASS</b></td></tr>");
					  }
				  }
				  else
				  {
					  out.write("yellow><FONT COLOR=153E7E FACE=Arial SIZE=2><b>SKIP</b></td></tr>");
				  }
			  }
			  //Close the output stream
			  out.write("</table>");
			  out.close();
		}
		catch (Exception e)
		{
			//Catch exception if any
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
		ComSendMail.Comexecute("Automation_Report");
	}

}
