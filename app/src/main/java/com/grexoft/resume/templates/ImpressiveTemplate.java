package com.grexoft.resume.templates;



import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.grexoft.resume.R;
import com.grexoft.resume.helpers.Common_Utilty;

public class ImpressiveTemplate extends TemplateFragment {

	@Override
	protected int getLayoutId() {
		return R.layout.template_sample6;
	}

	@Override
	protected void afterResumeHead() {
		titles.add(title);
		headings.add(name);
	}

	@Override
	protected void addResumeHead() {
		super.addResumeHead();
		if (!Common_Utilty.isNotEmptyString(resumeData.name)
				&& !Common_Utilty.isNotEmptyString(presentAddressStr)
				&& !Common_Utilty.isNotEmptyString(resumeData.primarycontact)
				&& !Common_Utilty.isNotEmptyString(resumeData.email)){
			LinearLayout layoutBox1 = (LinearLayout) findViewById(R.id.layout_box1);
			layoutBox1.setVisibility(View.GONE);
		}
	}

	@Override
	protected void addSkills() {
		super.addSkills();
		if (!Common_Utilty.isNotEmptyString(resumeData.objectives)
				&& resumeData.educationDetail.isEmpty() && resumeData.skills.isEmpty()) {
			LinearLayout layoutbox2 = (LinearLayout) findViewById(R.id.layout_box2);
			layoutbox2.setVisibility(View.GONE);
		}
	}

	@Override
	protected void addProjects() {
		super.addProjects();
		if (resumeData.workExperience.isEmpty() && resumeData.project.isEmpty()) {
			LinearLayout layoutbox3 = (LinearLayout) findViewById(R.id.layout_box3);
			layoutbox3.setVisibility(View.GONE);
		}
	}

	@Override
	protected void addStrengths() {
		super.addStrengths();
		if (resumeData.research.isEmpty() && resumeData.strength.isEmpty()) {
			LinearLayout layoutbox4 = (LinearLayout) findViewById(R.id.layout_box4);
			layoutbox4.setVisibility(View.GONE);
		}
	}

	@Override
	protected void addHobbies() {
		super.addHobbies();
		if (resumeData.achive.isEmpty() && resumeData.exCarr.isEmpty()
				&& resumeData.hobbies.isEmpty()) {
			LinearLayout layoutbox5 = (LinearLayout) findViewById(R.id.layout_box5);
			layoutbox5.setVisibility(View.GONE);
		}
	}

	@Override
	protected void addReference() {

		LinearLayout referenceLayout = (LinearLayout) findViewById(R.id.txt_write_reference);


		if (resumeData.reference != null && !resumeData.reference.isEmpty()) {

			headings.add((TextView)findViewById(R.id.txt_heading_reference));

			TextView fullName, designation, workAddress, emailAddress, workPhone, personalPhone;
			LinearLayout refer;

			for (int i = 0; i < resumeData.reference.size(); i++) {

				refer = (LinearLayout) getLayoutInflater().inflate(
						R.layout.reference_view, null);

				fullName = (TextView) refer.findViewById(R.id.tv_full_name);
				designation = (TextView) refer
						.findViewById(R.id.tv_title_designation);
				workAddress = (TextView) refer
						.findViewById(R.id.tv_work_address);
				emailAddress = (TextView) refer
						.findViewById(R.id.tv_email_address);
				workPhone = (TextView) refer.findViewById(R.id.tv_work_phone);
				personalPhone = (TextView) refer
						.findViewById(R.id.tv_personal_phone);

				contents.add(fullName);
				contents.add(designation);
				contents.add(workAddress);
				contents.add(emailAddress);
				contents.add(workPhone);
				contents.add(personalPhone);

				SpannableString nameString = new SpannableString(resumeData.reference.get(i).name);
				nameString.setSpan(new StyleSpan(Typeface.BOLD), 0,	nameString.length(), 0);

				fullName.setText(nameString);


				if (Common_Utilty
						.isNotEmptyString(resumeData.reference.get(i).title)) {
					designation.setText("Designation : "
							+ resumeData.reference.get(i).title);
				} else {
					designation.setVisibility(View.GONE);
				}

				if (Common_Utilty
						.isNotEmptyString(resumeData.reference.get(i).workAddress)) {
					workAddress.setText("Work Address : "
							+ resumeData.reference.get(i).workAddress);
				} else {
					workAddress.setVisibility(View.GONE);
				}

				if (Common_Utilty
						.isNotEmptyString(resumeData.reference.get(i).email)) {
					emailAddress.setText("E-mail : "
							+ resumeData.reference.get(i).email);
				} else {
					emailAddress.setVisibility(View.GONE);
				}

				if (Common_Utilty
						.isNotEmptyString(resumeData.reference.get(i).workPhone)) {
					workPhone.setText("Work Phone : "
							+ resumeData.reference.get(i).workPhone);
				} else {
					workPhone.setVisibility(View.GONE);
				}

				if (Common_Utilty
						.isNotEmptyString(resumeData.reference.get(i).personalPhone)) {
					personalPhone.setText("Personal Phone : "
							+ resumeData.reference.get(i).personalPhone);
				} else {
					personalPhone.setVisibility(View.GONE);
				}
				referenceLayout.addView(refer);
			}
		} else {
			LinearLayout referenceHead = (LinearLayout) findViewById(R.id.reference);
			referenceHead.setVisibility(View.GONE);
		}

		if (!isPersonalInfoPresent && resumeData.reference.isEmpty()) {

			LinearLayout layoutbox6 = (LinearLayout) findViewById(R.id.layout_box6);
			layoutbox6.setVisibility(View.GONE);

		}
	}

}
