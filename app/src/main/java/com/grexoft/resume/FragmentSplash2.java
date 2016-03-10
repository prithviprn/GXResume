package com.grexoft.resume;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentSplash2 extends Fragment {
	
	protected View fragmentView;
	
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		fragmentView=inflater.inflate(R.layout.menu_splash2, container, false);
		
		return fragmentView;
	}

}