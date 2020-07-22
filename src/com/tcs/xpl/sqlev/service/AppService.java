package com.tcs.xpl.sqlev.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.tcs.xpl.sqlev.constants.ApplicationConstants;
import com.tcs.xpl.sqlev.dao.AppDAO;
import com.tcs.xpl.sqlev.dto.AssociateData;
import com.tcs.xpl.sqlev.dto.TestCase;
import com.tcs.xpl.sqlev.exception.AppException;
import com.tcs.xpl.sqlev.util.XlUtil;
import com.tcs.xpl.sqlev.util.ZipUtil;

public class AppService {
	
	AppDAO dao=new AppDAO();
	public boolean excuteInputFileDynamically(File inputFile){
		boolean rtrValue=dao.excuteInputFileDynamically(inputFile);
		return rtrValue;
	}
	public String excuteSQLFileDynamically(File outputFile){
		String rtrValue=dao.excuteSQLFileDynamically(outputFile);
		return rtrValue;
	}
	public void clearTestCaseTables(){
		dao.clearTestCaseTables();
		
	}
	
	public List<AssociateData> processAssociateDetails(String loc) throws AppException 
	{
		List<AssociateData> assocList=XlUtil.processAssociateData(loc);
			return assocList;
	}
	
	public List<TestCase> processTestCase(String ziplocation,String unzipLocation) throws AppException
	{
		List<TestCase> assocList=XlUtil.processTestCase(ziplocation, unzipLocation);
			return assocList;
	}
	
	public List<AssociateData> processAssociateSolution(List<AssociateData> assocList,String SolZipFileLocation,String SolUnZipFileLocation) throws AppException 
	{
		ZipUtil.unzip(SolZipFileLocation, ApplicationConstants.FILE_SAVE_LOCATION);
	
			for(AssociateData a:assocList)
			{
				if(a.getCtdtId()!=null)
				{
					a.setSqlScript(ZipUtil.printFilesOf(SolUnZipFileLocation,a.getCtdtId(),ApplicationConstants.ASSOCIATE_SCRIPT_FILE_NAME,ApplicationConstants.SQL_EXTENSTION));
				}
				else if(a.getEmpId()>0)
				{
					a.setSqlScript(ZipUtil.printFilesOf(SolUnZipFileLocation,String.valueOf(a.getEmpId()),ApplicationConstants.ASSOCIATE_SCRIPT_FILE_NAME,ApplicationConstants.SQL_EXTENSTION));
				}
			}
			return assocList;
	}
}
