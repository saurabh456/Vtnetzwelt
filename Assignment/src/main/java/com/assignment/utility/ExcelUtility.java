package com.assignment.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtility {
	
	static String excelFileName = "RestaurantsData.xlsx";
	static FileInputStream fis = null;
	static FileOutputStream fos = null;
	static Workbook wb = null;
	static Sheet sheet = null;
	static Row row = null;
	static Cell cell = null;
	
	private static void initilize() 
	{
		
		try {
			fis = new FileInputStream("./Output/"+excelFileName);
			
			  wb = WorkbookFactory.create(fis);
			  System.out.println("Workbook initilises successfully");
		}
		catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void writeDataIntoExcel(List<Map<String,String>> restaurantsFinalList, String sheetName)
	{
		
		initilize();
		
		int rowCount = 0;
		
		Sheet sheet = wb.createSheet(sheetName);
		
		row = sheet.createRow(rowCount++);
		
		row.createCell(0).setCellValue("Delivery Location");
		
		row.createCell(1).setCellValue("Restaurant Name");
		
		row.createCell(2).setCellValue("Restaurant Url");
		
		System.out.println("Successfully Created the Headers in Excel");
		
		for(Map<String, String> map : restaurantsFinalList)
		{
			
			 Set<Map.Entry<String,String>> set = map.entrySet();
			 
			 for (Map.Entry<String, String> entry: set) 
		        {
			
				 	row = sheet.createRow(rowCount++);
			 
				 	int columnCount=0;
	            
	                //System.out.println("Restaurant's details are: "+entry.getValue());
	            
	                String lists[] = entry.getValue().split("@");
	            
	            
		            for(String list : lists)
		            {
		            	System.out.println("Hashmpa values are in Excel Utility:"+list);
		            	
		            	row.createCell(columnCount++, CellType.STRING).setCellValue(list);
		            }
			
	      }
	}
		 
		 	try {
				
		 		fos = new FileOutputStream("./Output/"+excelFileName);
		 		
		 		wb.write(fos);
		        
		        System.out.println("Data successfully written in Excel file");
		        
			} 
		 	catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 	
		 	catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 	
		 	finally
		 	{
		 		try {
					fis.close();
					  fos.close();

				      wb.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		      
		 	}
		 	
	}
	
}
