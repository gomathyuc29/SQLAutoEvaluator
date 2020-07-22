package com.tcs.xpl.sqlev.constants;

public interface ApplicationConstants 
{


	public static final String FILE_PARAM1="file1";
	public static final String FILE_PARAM2="file2";
	public static final String FILE_PARAM3="file3";
	public static final String FILE_SAVE_LOCATION="C://Users//gomat//Desktop//SQLCodeEv//";
	
	public static final String XLS_EXTENSTION=".xls";
	public static final String TXT_EXTENSTION=".txt";
	public static final String SQL_EXTENSTION=".sql";
	public static final String ZIP_EXTENSTION=".zip";
	
	
	public static final String SCORE_DISTRIBUTION_FILE_NAME="Score_Distribution";
	public static final String ASSOCIATE_SCRIPT_FILE_NAME="script";
	
	//SQL constants
	public static final String SQLLITE_JAR = "org.sqlite.JDBC";
	//public static final String SQLLITE_URL = "jdbc:sqlite:C://Users//485083//MySQLiteDB";
	public static final String SQLLITE_URL = "jdbc:sqlite:C:\\Users\\gomat\\Desktop\\MySQLiteDB";
	public static final String OUTPUT_TESTCASE_FOLDER = "OUTPUT_TESTCASES";
	public static final String WEBINF = "/WEB-INF/classes/";
	public static final String FILE_SEPERATOR = "/";
	public static final String FILEEXTENSION = ".txt";
	public static final String TESTCASE = "TestCase";
	public static final String ENCODING = "UTF-8";
	public static final String DROP_QUERY = "DROP TABLE";
	public static final String SEMICOLON=";";
	public static final String ZERO="0";
	public static final String SPACE=" ";
	public static final String DROPQUERYFILE = "DROPQUERYFILE";
	//Excel Headers
	public static final String ASSOCIATE_XL_COLUMNS = "SL No###Id###Name";
	public static final String SCOREDIST_XL_COLUMNS = "SL No###Input File###Output File###Success Message###Failure Message###Score";
	
	//Export data headers
	public static final String SL_NO="SL No";
	public static final String ID="ID";
	public static final String NAME="NAME";
	public static final String TC="TC";
	public static final String MESSAGE="MESSAGE";
	public static final String SCORE="SCORE";
	public static final String UNDERSCORE="_";
	public static final String TOTAL_SCORE="TOTAL_SCORE";
	
}
