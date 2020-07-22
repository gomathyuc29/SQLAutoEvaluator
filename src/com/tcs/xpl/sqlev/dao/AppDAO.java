package com.tcs.xpl.sqlev.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.jdbc.ScriptRunner;

import com.tcs.xpl.sqlev.constants.ApplicationConstants;
import com.tcs.xpl.sqlev.util.DatabaseUtil;
import com.tcs.xpl.sqlev.util.FileUtil;

public class AppDAO {


	

	public boolean excuteInputFileDynamically(File fileName) {
		boolean rtrValue = false;
		String result = null;
		Connection connection = null;

		try {
			connection = DatabaseUtil.createConnection();
			// Initialize the script runner
			result = exceuteScriptRunner(connection, fileName);
			if (result != null && !result.isEmpty()) {
				rtrValue = true;
			}
			executeDropTables(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return rtrValue;
		}
		return rtrValue;
	}

	private void executeDropTables(String result) {
		String tableQuery="CREATETABLE";
		String tableSeperator="(";
		String dropList="";
		if (result != null && !result.isEmpty()) {
			result=result.replaceAll("\\s+", "");
			System.out.println(result);
			String[] tableQueryArray=result.split(tableQuery);
			for(String strValue:tableQueryArray){
				if(!strValue.isEmpty()){
				int beginIndex= Integer.parseInt(ApplicationConstants.ZERO);
				int endIndex=strValue.indexOf(tableSeperator);
				String reultVal=strValue.substring(beginIndex,endIndex);
				System.out.println(reultVal);
				String dropQueryValue=ApplicationConstants.DROP_QUERY+ApplicationConstants.SPACE+reultVal+ApplicationConstants.SPACE+ApplicationConstants.SEMICOLON;
				dropList+=dropQueryValue+"\n";
			}
			}
			

			
		}
		
		if(dropList!=null && !dropList.isEmpty()){
			String fullPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
			String[] rootPath = fullPath.split(ApplicationConstants.WEBINF);
			System.out.println("rootPath" + rootPath);
			String dropFilePath = rootPath[0] + ApplicationConstants.FILE_SEPERATOR
					+ ApplicationConstants.OUTPUT_TESTCASE_FOLDER + ApplicationConstants.FILE_SEPERATOR
					+ ApplicationConstants.DROPQUERYFILE +ApplicationConstants.FILEEXTENSION;
			FileWriter fw;
			try {
				fw = new FileWriter(dropFilePath);
				fw.write(dropList);
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		}
		
	}

	
	public void clearTestCaseTables(){
		boolean rtrValue = false;
		String result = null;
		Connection connection = null;

		try {
			connection = DatabaseUtil.createConnection();
			String fullPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
			String[] rootPath = fullPath.split(ApplicationConstants.WEBINF);
			System.out.println("rootPath" + rootPath);
			String dropFilePath = rootPath[0] + ApplicationConstants.FILE_SEPERATOR
					+ ApplicationConstants.OUTPUT_TESTCASE_FOLDER + ApplicationConstants.FILE_SEPERATOR
					+ ApplicationConstants.DROPQUERYFILE +ApplicationConstants.FILEEXTENSION;
	
			 File fileName=new File(dropFilePath);
			// Initialize the script runner
			result = exceuteScriptRunner(connection, fileName);
			if (result != null && !result.isEmpty()) {
				rtrValue = true;
			}
			System.out.println(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		
	}
	public String excuteSQLFileDynamically(File fileName) {
		String result = null;
		Connection connection = null;
		String outputFilePath = "";
		int i = 0;
		try {
			connection = DatabaseUtil.createConnection();
			// Initialize the script runner
			result = exceuteScriptRunner(connection, fileName);
			String content = FileUtil.readFile(fileName);
			System.out.println("content--" + content);
			content = content.substring(0, content.length() - 1);
			System.out.println("content--" + content);
			if (result != null && !result.isEmpty()) {
				String fullPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
				String[] rootPath = fullPath.split(ApplicationConstants.WEBINF);
				System.out.println("rootPath" + rootPath);
				outputFilePath = rootPath[0] + ApplicationConstants.FILE_SEPERATOR
						+ ApplicationConstants.OUTPUT_TESTCASE_FOLDER + ApplicationConstants.FILE_SEPERATOR
						+ ApplicationConstants.TESTCASE + i + ApplicationConstants.FILEEXTENSION;
				System.out.println("outputFilePath" + outputFilePath);
				String resultSplit = result.toString().replace(content, "");
				System.out.println("resultSplit" + resultSplit.trim());
				FileWriter fw = new FileWriter(outputFilePath);
				fw.write(resultSplit.trim());
				fw.close();

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return outputFilePath;
		}
		return outputFilePath;

	}

	public String exceuteScriptRunner(Connection connection, File fileName) {
		StringWriter stringWriter = new StringWriter();
		try {
			ScriptRunner sr = new ScriptRunner(connection);
			// Creating a reader object
			Reader reader = new BufferedReader(new FileReader(fileName));
			// Running the script
			sr.setLogWriter(new PrintWriter(stringWriter));
			sr.setSendFullScript(false);
			sr.setAutoCommit(true);
			sr.setEscapeProcessing(false);
			sr.runScript(reader);
			reader.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return stringWriter.toString();
		}
		
		System.out.println(stringWriter.toString());
		return stringWriter.toString();
	}

}
