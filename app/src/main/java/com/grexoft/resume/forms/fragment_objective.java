package com.grexoft.resume.forms;



import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.grexoft.resume.R;
import com.grexoft.resume.Resume;
import com.grexoft.resume.helpers.Common_Utilty;

public class fragment_objective extends ResumeFragment implements
		OnClickListener {
	
	private EditText etObjective;
	

	

	@Override
	public boolean savedata() {
		
		myResume.objectives = Common_Utilty.firstUpper(etObjective.getText().toString());

		myResume.checkForm(Resume.OBJECTIVE_FORM);
		return true;

	}

	@Override
	public void onClick(View v) {
		String str = "";

		switch (v.getId()) {
		case R.id.objectivesuggestion1:
		case R.id.objectivesuggestion2:
		case R.id.objectivesuggestion3:
		case R.id.objectivesuggestion4:
		case R.id.objectivesuggestion5:
			TextView tv = (TextView) v;
			str = tv.getText().toString();
			break;

		}

		etObjective.setText(str);
	}

	@Override
	public void addRecord() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setLayoutId() {


		layoutId = R.layout.fragment_objective;
		
	}

	@Override
	protected void init() {
		
		etObjective = (EditText) fragmentView.findViewById(R.id.etcarrerobj);

		((TextView) fragmentView.findViewById(R.id.objectivesuggestion1))
				.setOnClickListener(this);

		((TextView) fragmentView.findViewById(R.id.objectivesuggestion2))
				.setOnClickListener(this);
		((TextView) fragmentView.findViewById(R.id.objectivesuggestion3))
				.setOnClickListener(this);
		((TextView) fragmentView.findViewById(R.id.objectivesuggestion4))
				.setOnClickListener(this);
		((TextView) fragmentView.findViewById(R.id.objectivesuggestion5))
		.setOnClickListener(this);

		etObjective.setText(myResume.objectives);
		
	}

}
