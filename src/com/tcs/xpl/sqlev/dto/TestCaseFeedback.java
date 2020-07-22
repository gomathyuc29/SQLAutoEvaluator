package com.tcs.xpl.sqlev.dto;

public class TestCaseFeedback {

	private int testCaseNo;
	private double score;
	private String feedback;
	
	public TestCaseFeedback(int testCaseNo, double score, String feedback) {
		super();
		this.testCaseNo = testCaseNo;
		this.score = score;
		this.feedback = feedback;
	}
	public int getTestCaseNo() {
		return testCaseNo;
	}
	public void setTestCaseNo(int testCaseNo) {
		this.testCaseNo = testCaseNo;
	}
	public double getScore() {
		return score;
	}
	@Override
	public String toString() {
		return "TestCaseFeedback [testCaseNo=" + testCaseNo + ", score=" + score + ", feedback=" + feedback + "]";
	}
	public void setScore(double score) {
		this.score = score;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	
	
}
