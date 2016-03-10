package com.grexoft.resume.forms;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.grexoft.resume.Resume;

public abstract class ResumeFragment extends Fragment {
	
	protected int layoutId;
	
	protected Resume myResume;
	
	protected View fragmentView;
	
	protected BaseAdapter adapter;
	
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		setLayoutId();
		
		fragmentView = inflater.inflate(layoutId, container, false);
		
		myResume = Resume.getInstance();
		
		init();
		
		return fragmentView;
	}
	
	protected abstract void setLayoutId();
	
	protected abstract void init();
	
	public abstract boolean savedata();
	
	public abstract void addRecord();

}
