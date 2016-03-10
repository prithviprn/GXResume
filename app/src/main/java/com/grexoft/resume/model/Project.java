package com.grexoft.resume.model;

public class Project {

	public String title, description, timebeg, timeend, role, size;

	public Project(String title2, String description2, String timebeg, String timeend,
			String role2, String size2) {
		title = title2;
		description = description2;
		this.timebeg = timebeg;
		this.timeend = timeend;
		role = role2;
		size = size2;
	
	}

}
