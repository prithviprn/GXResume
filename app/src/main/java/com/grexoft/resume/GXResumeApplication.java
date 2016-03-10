package com.grexoft.resume;

import java.util.ArrayList;

import android.app.Application;
import android.content.Context;

import com.grexoft.resume.inappbilling.IabHelper;
import com.grexoft.resume.model.ResumeTemplate;

public class GXResumeApplication extends Application{
	
	public static final String MY_PREFS_NAME = "MyPreferences";
	
	private ArrayList<String> exemptions;
	
	private ArrayList<ResumeTemplate> resumeTemplates;
	
	private IabHelper iabHelper;
	
	@Override
	public void onCreate() {
		exemptions = new ArrayList<String>();
		exemptions.add("husain707@gmail.com");
		super.onCreate();
	}
	
	public ArrayList<ResumeTemplate> getResumeTemplates() {
		return resumeTemplates;
	}
	
	public void setResumeTemplates(ArrayList<ResumeTemplate> resumeTemplates) {
		this.resumeTemplates = resumeTemplates;
	}
	
	public IabHelper getIabHelper() {
		return iabHelper;
	}
	
	public void setIabHelper(IabHelper iabHelper) {
		this.iabHelper = iabHelper;
	}
	
	@Override
	protected void attachBaseContext(Context base) {
		// TODO Auto-generated method stub
		super.attachBaseContext(base);
		//MultiDex.install(this);
	}
	
	public ArrayList<String> getExemptions() {
		return exemptions;
	}

}
