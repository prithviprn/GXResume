package com.grexoft.resume.templates;

import java.util.HashMap;
import java.util.Locale;

import org.apache.commons.lang3.text.WordUtils;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.grexoft.resume.R;
import com.grexoft.resume.Resume;
import com.grexoft.resume.helpers.Common_Utilty;

public class ElegantTemplate extends TemplateFragment {


	@Override
	protected int getLayoutId() {
		return R.layout.template_sample2;
	}

	@Override
	protected void afterResumeHead() {

	}

	@Override
	protected void addResumeHead() {

		TextView address = (TextView) findViewById(R.id.txt_address);

		presentAddressStr = "";

		if (Common_Utilty.isNotEmptyString(resumeData.city)) {
			if (Common_Utilty.isNotEmptyString(resumeData.street)) {
				presentAddressStr = presentAddressStr + (resumeData.street) + "\n";

			}
			presentAddressStr = presentAddressStr + (resumeData.city);
			if (Common_Utilty.isNotEmptyString(resumeData.pincode)) {
				presentAddressStr = presentAddressStr + "-" + resumeData.pincode;
			}
		}

		if (Common_Utilty.isNotEmptyString(resumeData.city)
				&& Common_Utilty.isNotEmptyString(resumeData.country)) {
			presentAddressStr = presentAddressStr + "," + "\n";
		}

		if (Common_Utilty.isNotEmptyString(resumeData.country)) {

			if (Common_Utilty.isNotEmptyString(resumeData.state)) {
				presentAddressStr = presentAddressStr + (resumeData.state) + ",";

			}
			presentAddressStr = presentAddressStr + (resumeData.country);
		}

		if (!presentAddressStr.equals("")) {
			address.setText(presentAddressStr);
			contents.add(address);
		} else {
			address.setVisibility(View.GONE);
		}

		TextView contact = (TextView) findViewById(R.id.txt_contact);
		if (Common_Utilty.isNotEmptyString(resumeData.primarycontact)) {
			contact.setText(resumeData.primarycontact);

		} else {
			contact.setText("Contact");
		}

		contents.add(contact);

		TextView email = (TextView) findViewById(R.id.txt_email);
		if (Common_Utilty.isNotEmptyString(resumeData.email)) {
			email.setText(resumeData.email);

		} else {
			email.setText("Email");
		}

		contents.add(email);

		TextView name = (TextView) findViewById(R.id.txt_name);
		TextView signature = (TextView) findViewById(R.id.txt_signature);

		String name_title = "";

		if(Common_Utilty.isNotEmptyString(resumeData.name) ){

			name_title = resumeData.name.toUpperCase(Locale.US);
		}
		if(Common_Utilty.isNotEmptyString(resumeData.title) && resumeData.showTitle){

			name_title = name_title + " [ " + WordUtils.capitalize(resumeData.title) + " ] ";
		}


		name.setText(name_title);
		titles.add(name);
		signature.setText(resumeData.name.toUpperCase(Locale.US));

	}

	@Override
	protected void addObjective() {
		super.addObjective();
		if (!Common_Utilty.isNotEmptyString(resumeData.objectives)) {
			View v1 = (View) findViewById(R.id.view_1);
			v1.setVisibility(View.GONE);
		}
	}

	@Override
	protected void addEducation() {
		super.addEducation();
		if (resumeData.educationDetail == null || resumeData.educationDetail.isEmpty()) {
			View v2 = (View) findViewById(R.id.view_2);
			v2.setVisibility(View.GONE);
		}
	}

	@Override
	protected void addWorkExperience() {
		super.addWorkExperience();
		if (resumeData.workExperience == null || resumeData.workExperience.isEmpty()) {
			View v3 = (View) findViewById(R.id.view_3);
			v3.setVisibility(View.GONE);
		}
	}

	@Override
	protected void addProjects() {
		super.addProjects();
		if (resumeData.project == null || resumeData.project.isEmpty()) {
			View v4 = (View) findViewById(R.id.view_4);
			v4.setVisibility(View.GONE);
		}
	}

	@Override
	protected void addResearch() {
		super.addResearch();
		if (resumeData.research == null || resumeData.research.isEmpty()) {
			View v5 = (View) findViewById(R.id.view_5);
			v5.setVisibility(View.GONE);
		}
	}

	@Override
	protected void addSkills() {
		super.addSkills();
		if (resumeData.skills == null || resumeData.skills.isEmpty()) {
			View v6 = (View) findViewById(R.id.view_6);
			v6.setVisibility(View.GONE);
		}
	}

	@Override
	protected void addAchievements() {
		super.addAchievements();
		if (resumeData.achive == null || resumeData.achive.isEmpty()) {
			View v7 = (View) findViewById(R.id.view_7);
			v7.setVisibility(View.GONE);
		}
	}

	@Override
	protected void addExtraCurricular() {
		super.addExtraCurricular();
		if (resumeData.exCarr == null || resumeData.exCarr.isEmpty()) {
			View v9 = (View) findViewById(R.id.view_9);
			v9.setVisibility(View.GONE);
		}
	}

	@Override
	protected void addHobbies() {
		super.addHobbies();
		if (resumeData.hobbies == null || resumeData.hobbies.isEmpty()) {
			View v10 = (View) findViewById(R.id.view_10);
			v10.setVisibility(View.GONE);
		}
	}

	@Override
	protected void addStrengths() {
		super.addStrengths();
		if (resumeData.strength == null || resumeData.strength.isEmpty()) {
			View v8 = (View) findViewById(R.id.view_8);
			v8.setVisibility(View.GONE);
		}
	}

	@Override
	protected void addPersonalDetails() {
		String personalStr = "";

		if (Common_Utilty.isNotEmptyString(resumeData.name)) {
			personalStr += "Name : " + resumeData.name;
		}
		if (resumeData.gender != null
				&& !resumeData.gender.getString().equals(
				Resume.Gender.NOT_SPECIFIED.getString())) {
			personalStr += '\n' + "Gender : " + resumeData.gender.getString();
		}
		if (Common_Utilty.isNotEmptyString(resumeData.dob)) {
			personalStr += '\n' + "Date of birth : " + resumeData.dob;
		}
		if (Common_Utilty.isNotEmptyString(resumeData.nationality)) {
			personalStr += '\n' + "Nationality : " + resumeData.nationality;
		}
		if (Common_Utilty.isNotEmptyString(resumeData.language)) {
			personalStr += '\n' + "Languages known : " + resumeData.language;
		}
		if (Common_Utilty.isNotEmptyString(resumeData.fatherName)) {
			personalStr += '\n' + "Father's Name : " + resumeData.fatherName;
		}
		if (Common_Utilty.isNotEmptyString(resumeData.motherName)) {
			personalStr += '\n' + "Mother's Name : " + resumeData.motherName;
		}

		if (resumeData.addressesAreSame) {
			if (presentAddressStr != null && !presentAddressStr.equals("")) {
				personalStr = personalStr + '\n' + "Address : "	+ Common_Utilty.replacement(presentAddressStr, "\n", " ");
			}

		}
		if (!resumeData.addressesAreSame) {

			String permanentAddressStr = "";

			if (Common_Utilty.isNotEmptyString(resumeData.city2)) {
				if (Common_Utilty.isNotEmptyString(resumeData.street2)) {
					permanentAddressStr = permanentAddressStr + resumeData.street2
							+ ",";

				}
				permanentAddressStr = permanentAddressStr + resumeData.city2;
				if (Common_Utilty.isNotEmptyString(resumeData.pincode2)) {
					permanentAddressStr = permanentAddressStr + "-"
							+ resumeData.pincode2;
				}
			}

			if (Common_Utilty.isNotEmptyString(resumeData.city2)
					&& Common_Utilty.isNotEmptyString(resumeData.country2)) {
				permanentAddressStr = permanentAddressStr + ", ";
			}

			if (Common_Utilty.isNotEmptyString(resumeData.country2)) {

				if (Common_Utilty.isNotEmptyString(resumeData.state2)) {
					permanentAddressStr = permanentAddressStr + resumeData.state2
							+ ",";

				}
				permanentAddressStr = permanentAddressStr + resumeData.country2;
			}

			if (!permanentAddressStr.equals("")) {

				personalStr = personalStr + '\n' + "Permanent Address : "
						+ permanentAddressStr;
			}

		}

		if (resumeData.customFields != null && !resumeData.customFields.isEmpty()) {

			for (HashMap.Entry entry : resumeData.customFields.entrySet()) {
				personalStr = personalStr + "\n" +entry.getKey() + " : "
						+ entry.getValue();
			}
		}

		if (personalStr.equals("")) {
			LinearLayout customLayout = (LinearLayout) findViewById(R.id.personal_details);
			customLayout.setVisibility(View.GONE);
		}

		else {
			((TextView)findViewById(R.id.txt_write_personal)).setText(personalStr);
			contents.add(((TextView) findViewById(R.id.txt_write_personal)));
			headings.add((TextView)findViewById(R.id.txt_heading_personal_details));
		}

		if (personalStr == null || personalStr.equals("")) {
			View v11 = (View) findViewById(R.id.view_11);
			v11.setVisibility(View.GONE);
		}
	}

	@Override
	protected void addReference() {
		super.addReference();
		if (resumeData.reference == null || resumeData.reference.isEmpty()) {
			View v12 = (View) findViewById(R.id.view_12);
			v12.setVisibility(View.GONE);
		}
	}

}
