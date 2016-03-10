package com.grexoft.resume;



import java.util.Locale;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class TemplateInformationFragment extends Fragment{
	

	private int imageId;
	private String templateName;
	private String templatePrice;
	
	public TemplateInformationFragment(int imageId, String templateName, String templatePrice) {
		this.imageId = imageId;
		this.templateName = templateName;
		this.templatePrice = templatePrice;
	}

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		
		View fragmentView = inflater.inflate(R.layout.item_templete, container, false);
		
		((ImageView)fragmentView.findViewById(R.id.img_container)).setImageDrawable(getActivity().getResources().getDrawable(imageId));
		
		((TextView)fragmentView.findViewById(R.id.txt_name)).setText(templateName.toUpperCase(Locale.US));
		((TextView)fragmentView.findViewById(R.id.txt_name)).setShadowLayer(1.0f, 1.0f, 1.0f, Color.DKGRAY);
		((TextView)fragmentView.findViewById(R.id.txt_name)).setTypeface(((TextView)fragmentView.findViewById(R.id.txt_name)).getTypeface(), Typeface.BOLD);
		
		
		((TextView)fragmentView.findViewById(R.id.txt_price)).setText(String.valueOf(templatePrice));
		
		
		
		
		return fragmentView;
	}
}
