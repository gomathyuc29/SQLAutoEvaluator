package com.tcs.xpl.sqlev.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.tcs.xpl.sqlev.exception.AppException;

public class ZipUtil {

	public static void unzip(String zipFilePath, String destDirectory) throws AppException 
	{
        try
        {
        	File destDir = new File(destDirectory);
        	if (!destDir.exists()) {
                destDir.mkdir();
            }
            ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
            ZipEntry entry = zipIn.getNextEntry();
            String filePath=null;
            // iterates over entries in the zip file
            while (entry != null) {
                 filePath = destDirectory + File.separator + entry.getName();
                if (!entry.isDirectory()) {
                    // if the entry is a file, extracts it
                    FileUtil.extractFile(zipIn, filePath);
                } else {
                    // if the entry is a directory, make the directory
                    File dir = new File(filePath);
                    dir.mkdir();
                }
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
            zipIn.close();
        }
        catch(IOException ie)
        {
        	throw new AppException(ie.getMessage());
        }
        catch(Exception e)
        {
        	throw new AppException(e.getMessage());
        }
    }
	
public static File printFilesOf(String maindirpath,String folderName,String filename,String fileExtension) throws AppException {
        try
        {
        	// File object 
            File maindir = new File(maindirpath); 
               
            if(maindir.exists() && maindir.isDirectory()) 
            { 
                // array for files and sub-directories  
                // of directory pointed by maindir 
                File arr[] = maindir.listFiles(); 
                  
                System.out.println("**********************************************"); 
                System.out.println("Files from main directory : " + maindir +" Searching for : "+ filename +" with extension: "+fileExtension); 
                System.out.println("**********************************************"); 
                  
                File f=RecursivePrint(arr,0,0,folderName,filename,fileExtension);  
                return f;
           }  
          
        }
        catch(Exception e)
        {
        	throw new AppException(e.getMessage());
        }
        return null;
	}

public static File RecursivePrint(File[] arr,int index,int level,String foldername,String fileLoc,String fileext) throws AppException  
{ 
 	try
 	{
 		File temp=null;
 	// terminate condition 
 	    if(index == arr.length) 
 	    	return null; 
 	          
 	    // tabs for internal levels 
 	    for (int i = 0; i < level; i++) 
 	        System.out.print("\t"); 
 	      
 	    // for files 
 	    if(arr[index].isFile()) 
 	    {
 	    	System.out.println(arr[index].getName()); 
 	    	if(arr[index].getName().equalsIgnoreCase(fileLoc+fileext))
 	    	{
 	    		temp= arr[index].getAbsoluteFile();
 	    		return temp;
 	    	}

 	    }
 	    // for sub-directories 
 	    else if(arr[index].isDirectory()) 
 	    { 
 	        System.out.println("[" + arr[index].getName() + "]"); 
 	        if(foldername !=null && arr[index].getName().equalsIgnoreCase(foldername))
 	        {
 	        	// recursion for sub-directories 
 	            temp=RecursivePrint(arr[index].listFiles(), 0, level + 1,foldername,fileLoc,fileext); 
 	            if(temp!=null)
 	            {
 	            	return temp;
 	            }
 	        }
 	        
 	    } 
 	           
 	   return RecursivePrint(arr,++index, level,foldername,fileLoc,fileext);
 	}
 	catch(Exception e)
    {
    	throw new AppException(e.getMessage());
    }
	
} 

	
}
