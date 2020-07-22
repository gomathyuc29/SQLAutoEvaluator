package com.tcs.xpl.sqlev.dto;

import java.io.File;
import java.util.List;

public class AssociateData {

	private int slNo;
	private String ctdtId;
	private long empId;
	private String name;
	private File sqlScript;
	private List<TestCaseFeedback> feedbackList;
	public List<TestCaseFeedback> getFeedbackList() {
		return feedbackList;
	}
	public void setFeedbackList(List<TestCaseFeedback> feedbackList) {
		this.feedbackList = feedbackList;
	}
	public long getEmpId() {
		return empId;
	}
	public void setEmpId(long i) {
		this.empId = i;
	}
	public int getSlNo() {
		return slNo;
	}
	public void setSlNo(int slNo) {
		this.slNo = slNo;
	}
	public String getCtdtId() {
		return ctdtId;
	}
	public void setCtdtId(String ctdtId) {
		this.ctdtId = ctdtId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public File getSqlScript() {
		return sqlScript;
	}
	public void setSqlScript(File sqlScript) {
		this.sqlScript = sqlScript;
	}
	@Override
	public String toString() {
		return "AssociateData [slNo=" + slNo + ", ctdtId=" + ctdtId + ", empId=" + empId + ", name=" + name
				+ ", sqlScript=" + sqlScript + ", feedbackList=" + feedbackList + "]";
	}
	
	
	
}
