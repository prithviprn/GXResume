package com.grexoft.resume.forms;


import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.grexoft.resume.R;
import com.grexoft.resume.Resume;

public class Fragment_declaration extends ResumeFragment implements
		OnClickListener {
	
	
	EditText etDeclartion;

	
	@Override
	public boolean savedata() {

		myResume.declaration = etDeclartion.getText().toString();

		myResume.checkForm(Resume.DECLARATION_FORM);
		return true;

	}

	@Override
	public void addRecord() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		String str = "";

		switch (v.getId()) {
		case R.id.tv_declar_suggestion1:
		case R.id.tv_declar_suggestion2:
		case R.id.tv_declar_suggestion3:
		case R.id.tv_declar_suggestion4:
			TextView tv = (TextView) v;
			str = tv.getText().toString();
			break;

		}

		etDeclartion.setText(str);
	}

	@Override
	protected void setLayoutId() {

		layoutId = R.layout.fragment_declaration;

	}

	@Override
	protected void init() {

		etDeclartion = (EditText) fragmentView.findViewById(R.id.et_declartion);

		((TextView) fragmentView.findViewById(R.id.tv_declar_suggestion1))
				.setOnClickListener(this);

		((TextView) fragmentView.findViewById(R.id.tv_declar_suggestion2))
				.setOnClickListener(this);
		((TextView) fragmentView.findViewById(R.id.tv_declar_suggestion3))
				.setOnClickListener(this);
		((TextView) fragmentView.findViewById(R.id.tv_declar_suggestion4))
				.setOnClickListener(this);

		etDeclartion.setText(myResume.declaration);

	}

}
