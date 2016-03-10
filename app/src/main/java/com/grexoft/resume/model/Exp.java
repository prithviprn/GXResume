package com.grexoft.resume.model;
public class Exp {
	public String compName;
	public String jobDesig;
	public String timeStart;
	public String timeEnd;
	public String description;

	public Exp(String compName, String jobDesig, String timeStart, String timeEnd, String description) {
		this.compName = compName;
		this.jobDesig = jobDesig;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.description = description;
	}

}