package com.grexoft.resume;



import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentSplash1 extends Fragment {

	Typeface bankGothicFont;
	TextView appName, poweredBy;
	protected View fragmentView;

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		fragmentView = inflater.inflate(R.layout.menu_splash1, container, false);
		
		bankGothicFont = Typeface.createFromAsset(getActivity().getAssets(),
				"fonts/BankGothic.ttf");
		appName = (TextView)fragmentView. findViewById(R.id.tv_app_name);
		appName.setTypeface(bankGothicFont);
		

      
//		poweredBy = (TextView) fragmentView.findViewById(R.id.tv_powerdby);
//		poweredBy.setTypeface(bankGothicFont);


		return fragmentView;
	}

}