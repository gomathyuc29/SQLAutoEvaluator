package com.tcs.xpl.sqlev.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.tcs.xpl.sqlev.constants.ApplicationConstants;
import com.tcs.xpl.sqlev.exception.AppException;

public class FileUtil {

	public static String readFile(File  file) throws IOException {
        String content = FileUtils.readFileToString(file);
        System.out.println(content);
		return content;
	}

	public boolean compareTwoFileContents(String Filename, File outputFile) throws IOException {

		BufferedReader reader1 = null;
		BufferedReader reader2=null;
		boolean areEqual = true;
		try {
			reader1 = new BufferedReader(new FileReader(Filename));

			reader2 = new BufferedReader(new FileReader(outputFile));
			String line0 = reader1.readLine().trim();
			String line1 = reader1.readLine().trim();
			
			String line2 = reader2.readLine().trim();
			line1=line1.replaceAll("\\s+", "");
			System.out.println("line1--"+line1);
			line2=line2.replaceAll("\\s+", "");
			System.out.println("line2--"+line2);
			
			int lineNum = 1;

			while (line1 != null || line2 != null) {
				if (line1 == null || line2 == null) {
					areEqual = false;
					break;
				} else if (!line1.equalsIgnoreCase(line2)) {
					areEqual = false;
					break;
				}
				line1 = reader1.readLine();
				line2 = reader2.readLine();
				lineNum++;
			}

			reader1.close();
			reader2.close();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return areEqual = false;
		}
		finally
		{
			if(reader1!=null && reader2!=null)
			{
				reader1.close();
				reader2.close();
			}
		}
		return areEqual;
	}
	
	
	public static void copyInputStreamToFile(InputStream inputStream, File file)
    		throws AppException, IOException {
		FileOutputStream outputStream=null;
            try {
            	outputStream = new FileOutputStream(file);
                int read;
                byte[] bytes = new byte[1024];

                while ((read = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
                outputStream.close();
            }
            catch(IOException ie)
            {
            	throw new AppException(ie.getMessage());
            }
            finally
            {
            	if(outputStream!=null)outputStream.close();
            }
            
        }

    public static boolean validateColumns(Row headerRow, String requiredCols) throws AppException{
    	try
    	{
    		boolean isOk=false;
    		String[] cols=requiredCols.split("###");
        	
        	Iterator<Cell> cellIterator = headerRow.cellIterator();
    		int columnCounter=0;
    		while (cellIterator.hasNext()) 
    		{
    			Cell cell = cellIterator.next();
    			String columnName=cell.getStringCellValue();
    			if(!columnName.equalsIgnoreCase(cols[columnCounter])) {
    				isOk=false;
    				return isOk;
    			}
    			else {
    				isOk=true;
    			}
    			columnCounter++;
    		}
    		if(columnCounter!=cols.length) {
    			isOk=false;
    		}
    		
        	return isOk;
    	}
    	catch(Exception e)
    	{
    		throw new AppException("Column header validation failed "+e.getMessage());
    	}
    	
    }

    /**
     * Extracts a zip entry (file entry)
     * @param zipIn
     * @param filePath
     * @throws IOException
     */
    public static void extractFile(ZipInputStream zipIn, String filePath) throws AppException {
        try
        {
        	BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        	byte[] bytesIn = new byte[4096];
            int read = 0;
            while ((read = zipIn.read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }
            bos.close();
        }
        catch(Exception e)
        {
        	throw new AppException(e.getMessage());
        }
    }
    
    public static  boolean validateInputs(String fileName1, String fileName2, String fileName3) {
		if(fileName1==null || fileName2==null ||fileName3==null)
		{
			return false;
		}
		
		if(fileName1.contains(ApplicationConstants.ZIP_EXTENSTION)
				&&fileName2.contains(ApplicationConstants.XLS_EXTENSTION)&&
				fileName3.contains(ApplicationConstants.ZIP_EXTENSTION) )
		{
			return true;
		}
		return false;
	}
    
    public static void deleteFolder(File file){
	      for (File subFile : file.listFiles())
	      {
	    	  System.out.println("Name : "+subFile.getName());
	         if(subFile.isDirectory()) {
	            deleteFolder(subFile);
	         } else {
	        	 System.out.println("Deleting "+subFile.getName());
	            subFile.delete();
	         }
	      }
	      System.out.println("Deleting file: "+file.getName());
	      file.delete();
	   }
}
