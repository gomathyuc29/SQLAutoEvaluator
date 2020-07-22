package com.tcs.xpl.sqlev.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.FileUtils;

import com.tcs.xpl.sqlev.constants.ApplicationConstants;
import com.tcs.xpl.sqlev.dto.AssociateData;
import com.tcs.xpl.sqlev.dto.TestCase;
import com.tcs.xpl.sqlev.exception.AppException;
import com.tcs.xpl.sqlev.service.AppService;
import com.tcs.xpl.sqlev.util.FileUtil;


/**
 * Servlet implementation class UploadFileServlet
 */
@WebServlet("/UploadFileServlet")
@MultipartConfig
public class UploadFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadFileServlet() {
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
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		processRequest(request, response);
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		List<AssociateData> assocList=null;
		List<TestCase> testCaseList=null;
		
		AppService appService=new AppService();
		
		try 
		{
			//Retreive inputs
			Part filePart1 = request.getPart(ApplicationConstants.FILE_PARAM1); // Retrieves <input type="file" name="file">
			String fileName1 = Paths.get(filePart1.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
			Part filePart2 = request.getPart(ApplicationConstants.FILE_PARAM2); // Retrieves <input type="file" name="file">
			String fileName2 = Paths.get(filePart2.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
			Part filePart3 = request.getPart(ApplicationConstants.FILE_PARAM3); // Retrieves <input type="file" name="file">
			String fileName3 = Paths.get(filePart3.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
			
			System.out.println(fileName1+","+fileName2+","+fileName3);
			
			if(!FileUtil.validateInputs(fileName1,fileName2,fileName3))
				throw new AppException("Invalid inputs.Please check again");
				
			
			//Delete temp directory and create new
			//FileUtils.deleteDirectory(new File(ApplicationConstants.FILE_SAVE_LOCATION));
			File file = new File(ApplicationConstants.FILE_SAVE_LOCATION);
			file.mkdir();
			
			System.out.println("Directory creation passed");
			
			//Save uploaded inputs in temp directory
			InputStream fileContent1 = filePart1.getInputStream();
			String SOLUTION_ZIP_LOCATION=ApplicationConstants.FILE_SAVE_LOCATION+fileName1;
			String SOLUTION_UNZIP_LOCATION=ApplicationConstants.FILE_SAVE_LOCATION+fileName1.substring(0,fileName1.length()-4);
			FileUtil.copyInputStreamToFile(fileContent1,new File(SOLUTION_ZIP_LOCATION));
			fileContent1.close();
			InputStream fileContent2 = filePart2.getInputStream();
			String ASSOCIATE_DETAILS=ApplicationConstants.FILE_SAVE_LOCATION+fileName2;
			FileUtil.copyInputStreamToFile(fileContent2,new File(ASSOCIATE_DETAILS));
			fileContent2.close();
			String TESTCASE_ZIP_LOCATION=ApplicationConstants.FILE_SAVE_LOCATION+fileName3;
			String TESTCASE_UNZIP_LOCATION=ApplicationConstants.FILE_SAVE_LOCATION+fileName3.substring(0,fileName3.length()-4);
			InputStream fileContent3 = filePart3.getInputStream();
			FileUtil.copyInputStreamToFile(fileContent3,new File(TESTCASE_ZIP_LOCATION));
			fileContent3.close();
			//Gather associate and testcase details
			assocList=appService.processAssociateDetails(ASSOCIATE_DETAILS);
			testCaseList=appService.processTestCase(TESTCASE_ZIP_LOCATION,TESTCASE_UNZIP_LOCATION);
			assocList=appService.processAssociateSolution(assocList,SOLUTION_ZIP_LOCATION,SOLUTION_UNZIP_LOCATION);
			
			//Calling SQLEvalServlet to process solution
			
			  RequestDispatcher rd=request.getRequestDispatcher("/SQLEvalServlet");
			  request.setAttribute("associateDataList", assocList);
			  request.setAttribute("testcaseList", testCaseList);
			  
			  rd.forward(request,response);
			 
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
