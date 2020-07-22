package com.tcs.xpl.sqlev.dto;

import java.io.File;

public class TestCase {

	private int slNo;
	private File inputText;
	private File outputText;
	private String successMessage;
	private String failureMessage;
	private double score;
	
	
	public TestCase(int slNo, File inputText, File outputText, String successMessage, String failureMessage,
			double score) {
		super();
		this.slNo = slNo;
		this.inputText = inputText;
		this.outputText = outputText;
		this.successMessage = successMessage;
		this.failureMessage = failureMessage;
		this.score = score;
	}
	
	public TestCase() {
		// TODO Auto-generated constructor stub
	}

	public int getSlNo() {
		return slNo;
	}
	public void setSlNo(int slNo) {
		this.slNo = slNo;
	}
	public File getInputText() {
		return inputText;
	}
	public void setInputText(File inputText) {
		this.inputText = inputText;
	}
	public File getOutputText() {
		return outputText;
	}
	public void setOutputText(File outputText) {
		this.outputText = outputText;
	}
	public String getSuccessMessage() {
		return successMessage;
	}
	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}
	public String getFailureMessage() {
		return failureMessage;
	}
	public void setFailureMessage(String failureMessage) {
		this.failureMessage = failureMessage;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "TestCase [slNo=" + slNo + ", inputText=" + inputText + ", outputText=" + outputText
				+ ", successMessage=" + successMessage + ", failureMessage=" + failureMessage + ", score=" + score
				+ "]";
	}
	
	
}
