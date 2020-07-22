package com.tcs.xpl.sqlev.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.tcs.xpl.sqlev.constants.ApplicationConstants;
import com.tcs.xpl.sqlev.dto.AssociateData;
import com.tcs.xpl.sqlev.dto.TestCase;
import com.tcs.xpl.sqlev.dto.TestCaseFeedback;
import com.tcs.xpl.sqlev.exception.AppException;

public class XlUtil {

	public static List<AssociateData> processAssociateData(String loc)throws AppException
	{
		List<AssociateData> result=new ArrayList<>();
		NPOIFSFileSystem npoifs = null;
	     
		try
		{
			
		    npoifs = new NPOIFSFileSystem(new File(loc));
			Workbook wb = WorkbookFactory.create(npoifs); 
			Sheet mySheet =wb.getSheetAt(0); 
			Iterator<Row> rowIter = mySheet.rowIterator();
		 
			 Row headerRow=rowIter.next();
			  boolean columnsOk=false;
			  columnsOk=FileUtil.validateColumns(headerRow, ApplicationConstants.ASSOCIATE_XL_COLUMNS);
			  if(!columnsOk) {
				  System.out.println("Columns in uploaded associate file not matching");
				  throw new AppException("Please check the uploaded associate data file");
			  }
			  else 
			  {		System.out.println("Columns in uploaded associate file matching template");
				  while (rowIter.hasNext())
				  {
						Row row = rowIter.next(); // For each row, iterate through all the columns
						Iterator<Cell> cellIterator = row.cellIterator();
						AssociateData assocData=new AssociateData();
						
						int counter=0;
					
						while (cellIterator.hasNext()) 
						{
							Cell cell = cellIterator.next();
						
							// Check the cell type and format accordingly
							switch (cell.getCellType()) {
							case Cell.CELL_TYPE_NUMERIC:
								if(counter==0)
								{
									assocData.setSlNo((int)cell.getNumericCellValue());
									counter++;
									break;
								}
								assocData.setEmpId((long)cell.getNumericCellValue());
								counter++;
								break;
							case Cell.CELL_TYPE_STRING:
								if(counter==2)
								{
									assocData.setName(cell.getStringCellValue());
									counter++;
									break;
								}
								assocData.setCtdtId(cell.getStringCellValue());
								counter++;
								break;
							}
						}
						result.add(assocData);
						System.out.println("");
					}
			  }
			  if (npoifs != null) { npoifs.close(); }
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			throw new AppException("Error while processing associate data from inputs");
		}
		
		return result;
	}
	
	public static List<TestCase> processTestCase(String input3ZipFileLocation,String input3UnZipFileLocation) throws AppException
	{
		int rowCounter=0;
		List<TestCase> testCaseList=new ArrayList<TestCase>();
		NPOIFSFileSystem npoifs = null;
		//search for the score_distribution.xls from the extracted folder
		try 
		{
			ZipUtil.unzip(input3ZipFileLocation, ApplicationConstants.FILE_SAVE_LOCATION);
			File scoreSheet=ZipUtil.printFilesOf(input3UnZipFileLocation,null,ApplicationConstants.SCORE_DISTRIBUTION_FILE_NAME,ApplicationConstants.XLS_EXTENSTION);
			//InputStream inputStream= new ByteArrayInputStream(IOUtils.toByteArray(new
					 // FileInputStream(scoreSheet)));
				
				npoifs=new NPOIFSFileSystem(scoreSheet);
			  Workbook wb = WorkbookFactory.create(npoifs); 
			  Sheet mySheet =wb.getSheetAt(0); 
			  Iterator<Row> rowIter = mySheet.rowIterator();
			  Row headerRow=rowIter.next();
			  boolean columnsOk=false;
			  columnsOk=FileUtil.validateColumns(headerRow, ApplicationConstants.SCOREDIST_XL_COLUMNS);
			  if(!columnsOk) {
				  System.out.println("Columns in uploaded test case file not matching");
				  throw new AppException("Please check the uploaded test case file");
			  }
			  else 
			  {
				  System.out.println("Columns in uploaded test case file matching template");
				  while (rowIter.hasNext()) 
				  {
				  	TestCase c=new TestCase();
					Row row = rowIter.next(); // For each row, iterate through all the columns
					Iterator<Cell> cellIterator = row.cellIterator();
					int cellCounter=0;
					while (cellIterator.hasNext()) 
					{
						Cell cell = cellIterator.next();
						
						// Check the cell type and format accordingly
						switch (cell.getCellType()) {
								case Cell.CELL_TYPE_NUMERIC:
									System.out.print(cell.getNumericCellValue() + "\t");
									break;
								case Cell.CELL_TYPE_STRING:
									System.out.print(cell.getStringCellValue() + "\t");
									break;
						}
						
						switch(cellCounter)
						{
								case 0:c.setSlNo(cellCounter+1);break;
								case 1:String[] fileNames= cell.getStringCellValue().split("\\.");
										File x1=ZipUtil.printFilesOf(input3UnZipFileLocation,null,fileNames[0],"."+fileNames[1]);
										c.setInputText(x1);
										break;
								case 2: fileNames= cell.getStringCellValue().split("\\.");
										File x2=ZipUtil.printFilesOf(input3UnZipFileLocation,null,fileNames[0],"."+fileNames[1]);
										c.setOutputText(x2);
										break;
								case 3: c.setSuccessMessage(cell.getStringCellValue());
										break;
								case 4:c.setFailureMessage(cell.getStringCellValue());
										break;
								case 5:c.setScore(cell.getNumericCellValue());
										break;
										
								default:break;
						}
						
						cellCounter++;
					}
					System.out.println("");
					rowCounter++;
					testCaseList.add(c);
				}
			  
			  System.out.println("Total number of test cases : "+rowCounter);	
			  } 
			  if (npoifs != null) { npoifs.close(); }
			  
		}
		catch(Exception e)
		{
			throw new AppException("Error while processing testcase data inputs");
		}
		return testCaseList;
		
	}
	
	public static byte[] exportDataToXls(List<AssociateData> associateList, List<TestCase> testCaseList) {
		try {
			//Create workbook in .xlsx format
			Workbook workbook = new HSSFWorkbook();

			//Create Sheet
			Sheet sh = workbook.createSheet("Evaluation_Summary");
			
			if(testCaseList==null || testCaseList.size()==0)
			{
				System.out.println("No testcases to compare results");
			}
			
			System.out.println(testCaseList.size());
			
			ArrayList<String> dynamicColHeaders=new ArrayList<String>();
			//Based on testcase list size frame columns
			for(int i=1;i<=testCaseList.size();i++)
			{
				dynamicColHeaders.add(ApplicationConstants.TC+i+ApplicationConstants.UNDERSCORE+ApplicationConstants.SCORE);
				dynamicColHeaders.add(ApplicationConstants.TC+i+ApplicationConstants.UNDERSCORE+ApplicationConstants.MESSAGE);
			}
			dynamicColHeaders.add(ApplicationConstants.TOTAL_SCORE);
			
			//Create top row with column headings
			ArrayList<String> excelHeaders=new ArrayList<String>();
			excelHeaders.add(ApplicationConstants.SL_NO);
			excelHeaders.add(ApplicationConstants.ID);
			excelHeaders.add(ApplicationConstants.NAME);
			
			excelHeaders.addAll(dynamicColHeaders);
			
			//We want to make it bold with a foreground color.
			Font headerFont = workbook.createFont();
			headerFont.setFontHeightInPoints((short)12);
			headerFont.setColor(IndexedColors.BLACK.index);
			//Create a CellStyle with the font
			CellStyle headerStyle = workbook.createCellStyle();
			headerStyle.setFont(headerFont);
			headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
			//Create the header row
			Row headerRow = sh.createRow(0);
			//Iterate over the column headings to create columns
			for(int i=0;i<excelHeaders.size();i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(excelHeaders.get(i));
				cell.setCellStyle(headerStyle);
			}
			//Freeze Header Row
			sh.createFreezePane(0, 1);
			
			//Fill data
			int rownum =1;
			for(AssociateData a:associateList) {
				Row row = sh.createRow(rownum++);
				
				row.createCell(0).setCellValue(a.getSlNo());
				if(a.getCtdtId()!=null)
				{
					row.createCell(1).setCellValue(a.getCtdtId());
				}
				else
				{
					row.createCell(1).setCellValue(a.getEmpId());
				}
				row.createCell(2).setCellValue(a.getName());
				int counter=3;
				int scoreCounter=0;
				for(TestCaseFeedback t:a.getFeedbackList())
				{
					row.createCell(counter++).setCellValue(t.getScore());
					row.createCell(counter++).setCellValue(t.getFeedback());
					scoreCounter+=t.getScore();
				}
				row.createCell(counter).setCellValue(scoreCounter);
			}
			//Autosize columns
			for(int i=0;i<excelHeaders.size();i++) {
				sh.autoSizeColumn(i);
			}
			
			System.out.println("Completed");
			
			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			workbook.write(outByteStream);
			byte [] outArray = outByteStream.toByteArray();
			return outArray;
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			throw new AppException(e.getMessage());
		}
		
	}	

}
