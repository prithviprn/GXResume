package com.grexoft.resume.model;

public class SavedResumes {
	
	private int resumeId;
	
	private String resumeTitle;
	
	private String dateOfCreation;
	
	private String password;
	
	
	

	public SavedResumes(int resumeId, String candidateName, String timestamp, String password) {
		super();
		this.resumeId = resumeId;
		this.resumeTitle = candidateName;
		this.dateOfCreation = timestamp;
		this.password = password;
	}

	public int getResumeId() {
		return resumeId;
	}

	public String getresumeTitle() {
		return resumeTitle;
	}

	public String getTimestamp() {
		return dateOfCreation;
	}
	
	public boolean isPasswordCorrect(String password) {
		return this.password.equals(password);
	}
	
	public boolean isPasswordSet(){
		
		return !password.equals(null) && !password.equals("");
		
	}

}
