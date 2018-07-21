package com.skava.frameworkutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.framework.reporting.BaseClass;

public class ExcelReader
{
	static File fileName = new File(BaseClass.UserDir+ BaseClass.properties.getProperty("TestDataPath"));	
	
    public static String getData(String sheetName, String colName) 
    {	
    	final StackTraceElement[] ste = new Throwable().getStackTrace();
    	String strValue = "";
    	String testCaseName = Thread.currentThread().getStackTrace()[3].getClassName().substring(20);
    	int rowCount;
    	int colNum;
    	
    	//Read excel worksheet to hashmap
    	Map<String, LinkedHashMap<Integer, List>> map = new HashMap<String, LinkedHashMap<Integer, List>>();
    	map = loadExceldataToMap(sheetName);
    	
		//Get the specified sheet
    	Map<Integer, List> mapRows = getRowCount(sheetName, map);
		
		//GetColNumber
    	colNum = getColNum(mapRows, colName);
		
		//Get the value using column name and tc row number
    	strValue = getDataValue(mapRows, colNum, testCaseName);
		   	
    	return strValue;
    }

  	public static HashMap loadExceldataToMap(String sheetName)
  	{    	
    	//Read excel in a hashmap    	
		HashMap<String, LinkedHashMap<Integer, List>> map = new HashMap();		
		
		// Read specific worksheet to map
		map = loadExcelSheet(fileName,sheetName);		
		
		return map;	
    }

    public static HashMap loadExcelSheet(File fileName, String strSheetName)
    {
        // Used the LinkedHashMap and LikedList to maintain the order
        HashMap<String, LinkedHashMap<Integer, List>> outerMap = new LinkedHashMap<String, LinkedHashMap<Integer, List>>();
        LinkedHashMap<Integer, List> hashMap = new LinkedHashMap<Integer, List>();
        String sheetName = null;
        
        // Create an ArrayList to store the data read from excel sheet.
        // List sheetData = new ArrayList();
        FileInputStream fis = null;
        try
        {
            fis = new FileInputStream(fileName);
            // Create an excel workbook from the file system
            HSSFWorkbook workBook = new HSSFWorkbook(fis);
            // Get the first sheet on the workbook.
            for (int i = 0; i < workBook.getNumberOfSheets(); i++)
            {
                HSSFSheet sheet = workBook.getSheetAt(i);
                // XSSFSheet sheet = workBook.getSheetAt(0);
                sheetName = workBook.getSheetName(i);
                
                if(sheetName.equals(strSheetName)) {
	                Iterator rows = sheet.rowIterator();
	                while (rows.hasNext())
	                {
	                    HSSFRow row = (HSSFRow) rows.next();
	                    Iterator cells = row.cellIterator();
	
	                    List data = new LinkedList();
	                    while (cells.hasNext())
	                    {
	                        HSSFCell cell = (HSSFCell) cells.next();
	                        cell.setCellType(Cell.CELL_TYPE_STRING);
	                        data.add(cell);
	                    }
	                    hashMap.put(row.getRowNum(), data);
	
	                    // sheetData.add(data);
	                }
                outerMap.put(sheetName, hashMap);
                hashMap = new LinkedHashMap<Integer, List>();
                }
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (fis != null)
            {
                try
                {
                    fis.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

        return outerMap;
    }
    
    public static Map getRowCount(String sheetName, Map<String, LinkedHashMap<Integer, List>> map) 
    {	
    	Map<Integer, List> mapRows = new LinkedHashMap();		
    	mapRows = map.get(sheetName);	
    	
		return mapRows;		
    }
    
   
    private static int getColNum(Map<Integer, List> mapRows, String colName) 
    {
    	String[] colList;
		int colNum = 0;
		colList = mapRows.get(0).toString().replace("[","").replaceAll("]", "").split(",");		
		int i=1;
		
		for (String listItem : colList) 
		{
		    if(listItem.toString().trim().equals(colName)) 
		    {		    	
		    	colNum = i-1;
		    }
		    i = i+1;
		}
		
		return colNum;
    }
    
    public static String getDataValue(Map<Integer, List> mapRows, int colNum, String testCaseName)
    {
    	String[] rowList;
		boolean testCaseID;
		int rowNum = 1;
		String strVal = null;
    	
		for(int Cnt=0; Cnt<=mapRows.size(); Cnt++) 
		{    		
    		rowList=mapRows.get(Cnt).toString().replace("[","").replaceAll("]", "").split(",");
    		testCaseID=true;
    		if(rowList[0].equals(testCaseName)) 
    		{
    			strVal = rowList[colNum].trim();   
    			break;
    		}
    	}

    	return strVal;
    }
    	
    public static HashMap loadExcelLines(File fileName)
    {
        // Used the LinkedHashMap and LikedList to maintain the order
        HashMap<String, LinkedHashMap<Integer, List>> outerMap = new LinkedHashMap<String, LinkedHashMap<Integer, List>>();
        LinkedHashMap<Integer, List> hashMap = new LinkedHashMap<Integer, List>();
        String sheetName = null;
        
        // Create an ArrayList to store the data read from excel sheet.
        // List sheetData = new ArrayList();
        FileInputStream fis = null;
        try
        {
            fis = new FileInputStream(fileName);
            // Create an excel workbook from the file system
            HSSFWorkbook workBook = new HSSFWorkbook(fis);
            // Get the first sheet on the workbook.
            for (int i = 0; i < workBook.getNumberOfSheets(); i++)
            {
                HSSFSheet sheet = workBook.getSheetAt(i);
                // XSSFSheet sheet = workBook.getSheetAt(0);
                sheetName = workBook.getSheetName(i);

                Iterator rows = sheet.rowIterator();
                while (rows.hasNext())
                {
                    HSSFRow row = (HSSFRow) rows.next();
                    Iterator cells = row.cellIterator();

                    List data = new LinkedList();
                    while (cells.hasNext())
                    {
                        HSSFCell cell = (HSSFCell) cells.next();
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        data.add(cell);
                    }
                    hashMap.put(row.getRowNum(), data);

                    // sheetData.add(data);
                }
        
                outerMap.put(sheetName, hashMap);
                hashMap = new LinkedHashMap<Integer, List>();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (fis != null)
            {
                try
                {
                    fis.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

        return outerMap;
    }
    
    public static Map getBatchExecInfo(String fileName,String sheetName) throws IOException 
    {
		 boolean blnExecNo=false;
		 Map<String, List> hashMap = new LinkedHashMap<String, List>();
		 Map<String, List> mapBatch = new LinkedHashMap<String, List>();
		 FileInputStream file = new FileInputStream(new File(fileName));
		 HSSFWorkbook workBook = new HSSFWorkbook(file);
		 HSSFSheet sheet=workBook.getSheet(sheetName);
		 //Iterate through each rows from first sheet
		 Iterator<Row> rowIterator = sheet.iterator();
		 while(rowIterator.hasNext()) 
		 {
				Row row = rowIterator.next();
				List data = new LinkedList();
				String strKey="";
				if(row.getRowNum()!=0) 
				{					
					//For each row, iterate through each columns
					Iterator<Cell> cellIterator = row.cellIterator();
					while(cellIterator.hasNext()) 
					{					
						Cell cell = cellIterator.next();
						////System.out.println(cell.getColumnIndex());						
						if(cell.getColumnIndex()==0) 
						{
							strKey=cell.getStringCellValue();
						}
						else 
						{							
							data.add(cell.getStringCellValue());
						}
					}
					////System.out.println(data.toString());
					hashMap.put(strKey, data);
				}				
		 }
			
			/*for(String key: hashMap.keySet()) {					
				blnExecNo=hashMap.get(key).get(0).toString().trim().equals("No");
				if(blnExecNo) {
					//hashMap.remove(key);					
				}else {
					mapBatch.put(key, hashMap.get(key));
				}
			}
			return mapBatch;*/
			return hashMap;
	}
}