package com.tcs.xpl.sqlev.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.tcs.xpl.sqlev.util.FileUtil;
import com.tcs.xpl.sqlev.util.XlUtil;
import com.tcs.xpl.sqlev.constants.ApplicationConstants;
import com.tcs.xpl.sqlev.dto.AssociateData;
import com.tcs.xpl.sqlev.dto.TestCase;
import com.tcs.xpl.sqlev.dto.TestCaseFeedback;
import com.tcs.xpl.sqlev.exception.AppException;
import com.tcs.xpl.sqlev.service.AppService;

/**
 * Servlet implementation class SQLEvalServlet
 */
@WebServlet("/SQLEvalServlet")
public class SQLEvalServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SQLEvalServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try
		{
			System.out.println("In Main Servlet");
			List<AssociateData> associateList = null;
			List<TestCase> testCaseList = null;
			if (request.getAttribute("associateDataList") != null) {
				associateList = (List<AssociateData>) request.getAttribute("associateDataList");
			}

			if (request.getAttribute("testcaseList") != null) {
				testCaseList = (List<TestCase>) request.getAttribute("testcaseList");
			}
			System.out.println("*************************************************");
			for(AssociateData a:associateList)
			{
				System.out.println(a.toString());
			}
			System.out.println("*************************************************");
			for(TestCase testcase:testCaseList){
				System.out.println(testcase);
			}
			
			
			
			AppService service = new AppService();
			RequestDispatcher rd = null;
			FileUtil util = new FileUtil();
			List<TestCaseFeedback> listTestcaseFeedback = null;
			
			double ZERO = 0.0;
			if (associateList != null && !associateList.isEmpty()) {
				for(AssociateData associateData:associateList){
				if (testCaseList != null && !testCaseList.isEmpty()) {
					listTestcaseFeedback = new ArrayList<TestCaseFeedback>();
					int count = 1;
					for (TestCase testcase : testCaseList) {
						
						TestCaseFeedback testcaseFeedback = null;
						boolean rtValue = service.excuteInputFileDynamically(testcase.getInputText());
						System.out.println("In Servlet Boolean Value--" + rtValue);
						if (rtValue) {
							System.out.println(" DB SetUp is done ");
							String outputTestCaseFileName = service.excuteSQLFileDynamically(associateData.getSqlScript());
							boolean compareContents = util.compareTwoFileContents(outputTestCaseFileName,testcase.getOutputText());
							if (compareContents) {
								testcaseFeedback = new TestCaseFeedback(count, testcase.getScore(),
										testcase.getSuccessMessage());
							} else {
								testcaseFeedback = new TestCaseFeedback(count, ZERO, testcase.getFailureMessage());
							}
							

						}
						count++;
						listTestcaseFeedback.add(testcaseFeedback);
						service.clearTestCaseTables();
					}
				}
				associateData.setFeedbackList(listTestcaseFeedback);
				}
				
			}
			System.out.println(listTestcaseFeedback);
			System.out.println(associateList);
			
			FileUtil.deleteFolder(new File(ApplicationConstants.FILE_SAVE_LOCATION));
			
			byte[] data=XlUtil.exportDataToXls(associateList,testCaseList);
			response.setContentType("application/ms-excel");
			response.setContentLength(data.length);
			response.setHeader("Expires:", "0");
			response.setHeader("Content-Disposition", "attachment; filename=Evaluation_Summary_"+LocalDateTime.now().toString()+ApplicationConstants.XLS_EXTENSTION);
			OutputStream outStream = response.getOutputStream();
			outStream.write(data);
			outStream.flush();
			
		}
		catch(AppException ae)
		{
			throw new ServletException(ae);
		}
		catch (Exception e) 
		{
			throw new ServletException(e);
		}
		
		
	}
	
	

	
}
