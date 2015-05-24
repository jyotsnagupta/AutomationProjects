package framework.com.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.*;
import org.apache.poi.ss.usermodel.*;
import java.io.*;
import java.util.Calendar;

public class ComExcelReader 
{
	public static String filename;
	public  String path;
	public  FileInputStream fis = null;
	public  FileOutputStream fileOut =null;
	private HSSFWorkbook workbook = null;
	private HSSFSheet sheet = null;
	private HSSFRow row   =null;
	private HSSFCell cell = null;
	
	public ComExcelReader(String path) 
	{
		this.path=path;
		try 
		{
			fis = new FileInputStream(path);
			workbook = new HSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			fis.close();
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	// returns the row count in a sheet
	public int comGetRowCount(String sheetName)
	{
		int index = workbook.getSheetIndex(sheetName);
		if(index==-1)
		{
				return 0;
		}
		else
		{
			sheet = workbook.getSheetAt(index);
			int number=sheet.getLastRowNum()+1;
			return number;
		}
		
	}
	
	// returns the data from a cell
	public String comGetCellData(String sheetName,String colName,int rowNum)
	{
		try
		{
			if(rowNum <=0)
			{
				return "";
			}
			int index = workbook.getSheetIndex(sheetName);
			int col_Num=-1;
			if(index==-1)
			{
				return "";
			}
		
			sheet = workbook.getSheetAt(index);
			row=sheet.getRow(0);
			for(int i=0;i<row.getLastCellNum();i++)
			{
				//System.out.println(row.getCell(i).getStringCellValue().trim());
				if(row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
				{
					col_Num=i;
				}
			}
			if(col_Num==-1)
			{
				return "";
			}
		
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum-1);  
			if(row==null)
			{
				return "";
			}
			cell = row.getCell(col_Num);
		
			if(cell==null)
			{
				return "";
			}
			cell.setCellType(Cell.CELL_TYPE_STRING);
			if(cell.getCellType()==Cell.CELL_TYPE_STRING)
			{
				return cell.getStringCellValue();
			}
			else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC || cell.getCellType()==Cell.CELL_TYPE_FORMULA )
			{
				cell.setCellType(Cell.CELL_TYPE_STRING);
				String cellText  = String.valueOf(cell.getNumericCellValue());
				//Date cellText  = cell.getDateCellValue();
				if(cellText.split("\\.").length > 1 && cellText.split("\\.")[1].equals("0"))
				{
					cellText=cellText.split("\\.")[0];
				}
				if (HSSFDateUtil.isCellDateFormatted(cell)) 
				{
					// format in form of M/D/YY
					double d = cell.getNumericCellValue();
					Calendar cal =Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(d));
		            cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
		            cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH)+1 + "/" + cellText;
		        }
				return cellText;
			}
			else if(cell.getCellType()==Cell.CELL_TYPE_BLANK)
			{
				return ""; 
			}
			else 
			{
				return String.valueOf(cell.getBooleanCellValue());
			}
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "row "+rowNum+" or column "+colName +" does not exist in xls";
		}
	}
	
	// returns the data from a cell
	public String comGetCellData(String sheetName,int colNum,int rowNum)
	{
		try
		{
			if(rowNum <=0)
			{
				return "";
			}
		
			int index = workbook.getSheetIndex(sheetName);
			if(index==-1)
			{
				return "";
			}
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum-1);
			if(row==null)
			{
				return "";
			}
			cell = row.getCell(colNum);
			if(cell==null)
			{
				return "";
			}
		
			if(cell.getCellType()==Cell.CELL_TYPE_STRING)
			{
				return cell.getStringCellValue();
			}
			else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC || cell.getCellType()==Cell.CELL_TYPE_FORMULA )
			{
				String cellText  = String.valueOf(cell.getNumericCellValue());
				if (HSSFDateUtil.isCellDateFormatted(cell)) 
				{
					// format in form of M/D/YY
					double d = cell.getNumericCellValue();
					Calendar cal =Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.MONTH)+1 + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cellText;
				}
				return cellText;
			}
			else if(cell.getCellType()==Cell.CELL_TYPE_BLANK)
			{
				return "";
			}
			else 
			{
				return String.valueOf(cell.getBooleanCellValue());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "row "+rowNum+" or column "+colNum +" does not exist  in xls";
		}
	}
	
	// returns true if data is set successfully else false
	public boolean comSetCellData(String sheetName,String colName,int rowNum, String data)
	{
		try
		{
			fis = new FileInputStream(path); 
			workbook = new HSSFWorkbook(fis);

			if(rowNum<=0)
			{
				return false;
			}
		
			int index = workbook.getSheetIndex(sheetName);
			int colNum=-1;
			if(index==-1)
			{
				return false;
			}
			sheet = workbook.getSheetAt(index);
			row=sheet.getRow(0);
			for(int i=0;i<row.getLastCellNum();i++)
			{
				if(row.getCell(i).getStringCellValue().trim().equals(colName))
				{
					colNum=i;
				}
			}
			
			if(colNum==-1)
			{
				return false;
			}

			sheet.autoSizeColumn(colNum); 
			row = sheet.getRow(rowNum-1);
			if (row == null)
			{
				row = sheet.createRow(rowNum-1);
			}
		
			cell = row.getCell(colNum);	
			if (cell == null)
	        {
				cell = row.createCell(colNum);
	        }

			cell.setCellValue(data);
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();	
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	// returns true if data is set successfully else false
	public boolean comSetCellData(String sheetName,String colName,int rowNum, String data,String url)
	{
		try
		{
			fis = new FileInputStream(path); 
			workbook = new HSSFWorkbook(fis);

			if(rowNum<=0)
			{
				return false;
			}
		
			int index = workbook.getSheetIndex(sheetName);
			int colNum=-1;
			if(index==-1)
			{
				return false;
			}
		
			sheet = workbook.getSheetAt(index);
			row=sheet.getRow(0);
			for(int i=0;i<row.getLastCellNum();i++)
			{
				if(row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(colName))
				{
					colNum=i;
				}
			}
		
			if(colNum==-1)
			{
				return false;
			}
			sheet.autoSizeColumn(colNum); 
			row = sheet.getRow(rowNum-1);
			if (row == null)
			{
				row = sheet.createRow(rowNum-1);
			}
		
			cell = row.getCell(colNum);	
			if (cell == null)
	        {
				cell = row.createCell(colNum);
	        }
			
			cell.setCellValue(data);
			HSSFCreationHelper createHelper = (HSSFCreationHelper) workbook.getCreationHelper();

			//cell style for hyperlinks
			//by default hypelrinks are blue and underlined
			CellStyle hlink_style = workbook.createCellStyle();
			HSSFFont hlink_font = workbook.createFont();
			hlink_font.setUnderline(HSSFFont.U_SINGLE);
			hlink_font.setColor(IndexedColors.BLUE.getIndex());
			hlink_style.setFont(hlink_font);
			//hlink_style.setWrapText(true);

			HSSFHyperlink link = createHelper.createHyperlink(HSSFHyperlink.LINK_FILE);
			link.setAddress(url);
			cell.setHyperlink(link);
			cell.setCellStyle(hlink_style);
	      
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();	

		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	// returns true if sheet is created successfully else false
	public boolean comAddSheet(String  sheetname)
	{		
		FileOutputStream fileOut;
		try 
		{
			workbook.createSheet(sheetname);	
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();		    
		} 
		catch (Exception e) 
		{			
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	// returns true if sheet is removed successfully else false if sheet does not exist
	public boolean comRemoveSheet(String sheetName)
	{		
		int index = workbook.getSheetIndex(sheetName);
		if(index==-1)
		{
			return false;
		}
		
		FileOutputStream fileOut;
		try 
		{
			workbook.removeSheetAt(index);
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
		    fileOut.close();		    
		} 
		catch (Exception e) 
		{			
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	// returns true if column is created successfully
	public boolean comAddColumn(String sheetName,String colName)
	{
		try
		{				
			fis = new FileInputStream(path); 
			workbook = new HSSFWorkbook(fis);
			int index = workbook.getSheetIndex(sheetName);
			if(index==-1)
			{
				return false;
			}
			
			HSSFCellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			
			sheet=workbook.getSheetAt(index);
			row = sheet.getRow(0);
			
			if (row == null)
			{
				row = sheet.createRow(0);
			}
			if(row.getLastCellNum() == -1)
			{
				cell = row.createCell(0);
			}
			else
			{
				int i_lastRow=row.getLastCellNum();
				cell = row.createCell(i_lastRow); 
			}
	        
	        cell.setCellValue(colName);
	        cell.setCellStyle(style);
	        
	        fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
		    fileOut.close();		    

		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// removes a column and all the contents
	public boolean comRemoveColumn(String sheetName, int colNum) 
	{
		try
		{
			if(!comIsSheetExist(sheetName))
			{
				return false;
			}
			fis = new FileInputStream(path); 
			workbook = new HSSFWorkbook(fis);
			sheet=workbook.getSheet(sheetName);
			HSSFCellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
			style.setFillPattern(HSSFCellStyle.NO_FILL);
		
			for(int i =0;i< comGetRowCount(sheetName);i++)
			{
				row=sheet.getRow(i);	
				if(row!=null)
				{
					cell=row.getCell(colNum);
					if(cell!=null)
					{
						cell.setCellStyle(style);
						row.removeCell(cell);
					}
				}
			}
			
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
		    fileOut.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// find whether sheets exists	
	public boolean comIsSheetExist(String sheetName)
	{
		int index = workbook.getSheetIndex(sheetName);
		if(index==-1)
		{
			index=workbook.getSheetIndex(sheetName.toUpperCase());
			if(index==-1)
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		else
		{
			return true;
		}
	}
	
	// returns number of columns in a sheet	
	public int comGetColumnCount(String sheetName)
	{
		// check if sheet exists
		if(!comIsSheetExist(sheetName))
		{
			return -1;
		}
		
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(0);
		
		if(row==null)
		{
			return -1;
		}
		return row.getLastCellNum();
		
	}
	
	//String sheetName, String testCaseName,String keyword ,String URL,String message
	public boolean comAddHyperLink(String sheetName,String screenShotColName,String testCaseName,int index,String url,String message)
	{
		url=url.replace('\\', '/');
		if(!comIsSheetExist(sheetName))
		{
			return false;
		}
		
	    sheet = workbook.getSheet(sheetName);
	    for(int i=2;i<= comGetRowCount(sheetName);i++)
	    {
	    	if(comGetCellData(sheetName, 0, i).equalsIgnoreCase(testCaseName))
	    	{
	    		comSetCellData(sheetName, screenShotColName, i+index, message,url);
	    		break;
	    	}
	    }
		return true; 
	}

	public int comGetCellRowNum(String sheetName,String colName,String cellValue)
	{
		for(int i=2;i<= comGetRowCount(sheetName);i++)
		{
			if(comGetCellData(sheetName,colName , i).equalsIgnoreCase(cellValue))
			{
	    		return i;
	    	}
	    }
		return -1;
	}
	
}
