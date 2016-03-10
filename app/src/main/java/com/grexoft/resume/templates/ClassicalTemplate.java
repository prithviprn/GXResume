package com.grexoft.resume.templates;

import java.util.HashMap;
import java.util.Locale;

import org.apache.commons.lang3.text.WordUtils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.grexoft.resume.R;
import com.grexoft.resume.Resume;
import com.grexoft.resume.helpers.Common_Utilty;

public class ClassicalTemplate extends TemplateFragment {


	@Override
	protected int getLayoutId() {
		return R.layout.template_sample4;
	}

	@Override
	protected void afterResumeHead() {

	}

	@Override
	protected void addResumeHead() {
		TextView name = (TextView) findViewById(R.id.txt_name);
		TextView signature = (TextView) findViewById(R.id.txt_signature);
		if (Common_Utilty.isNotEmptyString(resumeData.name)) {
			name.setText(resumeData.name.toUpperCase(Locale.US));
			signature.setText(resumeData.name.toUpperCase(Locale.US));
		} else {
			name.setText("Your Name");
			signature.setText("Your Signature");
		}

		titles.add(name);

		TextView title = (TextView)findViewById(R.id.txt_title);

		if(Common_Utilty.isNotEmptyString(resumeData.title) && resumeData.getPreferences().isShowTitle()){
			headings.add(title);
			title.setText(WordUtils.capitalize(resumeData.title));
		}else{
			title.setVisibility(View.GONE);
		}


		TextView address = (TextView) findViewById(R.id.txt_address_email_mobile);
		presentAddressStr = "";
		String emailStr = "";
		String mobileStr = "";

		if (Common_Utilty.isNotEmptyString(resumeData.city)) {
			if (Common_Utilty.isNotEmptyString(resumeData.street)) {
				presentAddressStr = presentAddressStr + resumeData.street + ", ";

			}
			presentAddressStr = presentAddressStr + resumeData.city;
			if (Common_Utilty.isNotEmptyString(resumeData.pincode)) {
				presentAddressStr = presentAddressStr + "-" + resumeData.pincode;
			}
		}

		if (Common_Utilty.isNotEmptyString(resumeData.city)
				&& Common_Utilty.isNotEmptyString(resumeData.country)) {
			presentAddressStr = presentAddressStr + ", ";
		}

		if (Common_Utilty.isNotEmptyString(resumeData.country)) {

			if (Common_Utilty.isNotEmptyString(resumeData.state)) {
				presentAddressStr = presentAddressStr + resumeData.state + ", ";

			}
			presentAddressStr = presentAddressStr + resumeData.country;
		}

		if (Common_Utilty.isNotEmptyString(resumeData.email)) {
			emailStr = emailStr + " - " + resumeData.email;
		}

		if (Common_Utilty.isNotEmptyString(resumeData.primarycontact)) {
			mobileStr = mobileStr + " - " + resumeData.primarycontact;
		}

		if (!presentAddressStr.equals("") || !emailStr.equals("")
				|| !mobileStr.equals("")) {
			address.setText(presentAddressStr + emailStr + mobileStr);
		} else {
			address.setVisibility(View.GONE);
		}

		contents.add(address);

	}

	@Override
	protected void addEducation() {
		LinearLayout educationQualification = (LinearLayout) findViewById(R.id.layout_table_container);

		if (resumeData.educationDetail != null && !resumeData.educationDetail.isEmpty()) {

			headings.add((TextView)findViewById(R.id.txt_heading_education));

			headings.add((TextView)findViewById(R.id.txt_table_heading_degree));
			headings.add((TextView)findViewById(R.id.txt_table_heading_passout));
			headings.add((TextView)findViewById(R.id.txt_table_heading_percentage));
			headings.add((TextView)findViewById(R.id.txt_table_heading_university));



			TextView tvDegree, tvUniversity, tvPassout, tvPercentage;

			LinearLayout eduDetail;
			for (int i = 0; i < resumeData.educationDetail.size(); i++) {

				eduDetail = (LinearLayout) getLayoutInflater().inflate(
						R.layout.temp4_education_item, null);

				tvDegree = (TextView) eduDetail
						.findViewById(R.id.txt_table_heading_degree);

				tvUniversity = (TextView) eduDetail
						.findViewById(R.id.txt_table_heading_university);

				tvPassout = (TextView) eduDetail
						.findViewById(R.id.txt_table_heading_passout);

				tvPercentage = (TextView) eduDetail
						.findViewById(R.id.txt_table_heading_percentage);

				contents.add(tvDegree);
				contents.add(tvUniversity);
				contents.add(tvPassout);
				contents.add(tvPercentage);

				if (Common_Utilty.isNotEmptyString(resumeData.educationDetail
						.get(i).degree)) {
					tvDegree.setText(resumeData.educationDetail.get(i).degree);
				} else {
					tvDegree.setVisibility(View.GONE);
				}

				if (Common_Utilty.isNotEmptyString(resumeData.educationDetail
						.get(i).university)) {
					tvUniversity
							.setText(resumeData.educationDetail.get(i).university);
				} else {
					tvUniversity.setVisibility(View.GONE);
				}

				if (Common_Utilty.isNotEmptyString(resumeData.educationDetail
						.get(i).passout)) {
					tvPassout.setText(resumeData.educationDetail.get(i).passout);
				} else {
					tvPassout.setVisibility(View.GONE);
				}

				if (Common_Utilty.isNotEmptyString(resumeData.educationDetail
						.get(i).result)) {
					tvPercentage.setText(resumeData.educationDetail.get(i).result);
				} else {
					tvPercentage.setVisibility(View.GONE);
				}

				educationQualification.addView(eduDetail);
			}
		} else {
			LinearLayout layoutEdu = (LinearLayout) findViewById(R.id.education);
			layoutEdu.setVisibility(View.GONE);
		}
	}

//	public void renderLayout(){
//
//		Resume resume = Resume.getInstance();
//
//
//
//
//
//
//
//		LinearLayout workExpDetails = (LinearLayout) findViewById(R.id.txt_write_work_experience);
//
//		if (resume.workExperience != null && !resume.workExperience.isEmpty()) {
//
//			TextView tvComapny, tvJobDesig, tvWorkDate, tvWorkDescrip;
//			LinearLayout workExp;
//			for (int i = 0; i < resume.workExperience.size(); i++) {
//				workExp = (LinearLayout) getLayoutInflater().inflate(
//						R.layout.work_exeperience_view, null);
//				tvComapny = (TextView) workExp
//						.findViewById(R.id.tv_company_name);
//				tvWorkDate = (TextView) workExp.findViewById(R.id.tv_work_date);
//				tvJobDesig = (TextView) workExp
//						.findViewById(R.id.tv_job_design);
//				tvWorkDescrip = (TextView) workExp
//						.findViewById(R.id.tv_job_description);
//
//				tvComapny.setText(resume.workExperience.get(i).compName);
//
//				if (Common_Utilty
//						.isNotEmptyString(resume.workExperience.get(i).timeStart)
//						&& Common_Utilty.isNotEmptyString(resume.workExperience
//								.get(i).timeEnd)) {
//					tvWorkDate.setText(resume.workExperience.get(i).timeStart
//							+ "-" + resume.workExperience.get(i).timeEnd);
//				} else {
//					tvWorkDate.setVisibility(View.GONE);
//				}
//
//				if (Common_Utilty
//						.isNotEmptyString(resume.workExperience.get(i).jobDesig)) {
//					tvJobDesig.setText("Designation : "
//							+ resume.workExperience.get(i).jobDesig);
//				} else {
//					tvJobDesig.setVisibility(View.GONE);
//				}
//
//				if (Common_Utilty
//						.isNotEmptyString(resume.workExperience.get(i).description)) {
//					tvWorkDescrip.setText("Description : "
//							+ resume.workExperience.get(i).description);
//				} else {
//					tvWorkDescrip.setVisibility(View.GONE);
//				}
//				workExpDetails.addView(workExp);
//
//			}
//		}
//
//		else {
//			LinearLayout workExpHeading = (LinearLayout) findViewById(R.id.work_experience);
//			workExpHeading.setVisibility(View.GONE);
//
//		}
//
//		LinearLayout projectDetails = (LinearLayout) findViewById(R.id.txt_write_projects);
//
//		if (resume.project != null && !resume.project.isEmpty()) {
//
//			TextView tvProjName, tvProjectdate, tvTeamSize, tvRole, tvDescrip;
//			LinearLayout projectLayout;
//			for (int i = 0; i < resume.project.size(); i++) {
//				projectLayout = (LinearLayout) getLayoutInflater().inflate(
//						R.layout.project_view, null);
//				tvProjName = (TextView) projectLayout
//						.findViewById(R.id.tv_project_name);
//
//				tvProjectdate = (TextView) projectLayout
//						.findViewById(R.id.tv_project_date);
//
//				tvTeamSize = (TextView) projectLayout
//						.findViewById(R.id.tv_team_size);
//
//				tvRole = (TextView) projectLayout.findViewById(R.id.tv_role);
//
//				tvDescrip = (TextView) projectLayout
//						.findViewById(R.id.tv_description);
//
//				if (Common_Utilty.isNotEmptyString(resume.project.get(i).title)) {
//					tvProjName.setText(resume.project.get(i).title);
//				}
//
//				if (Common_Utilty
//						.isNotEmptyString(resume.project.get(i).timebeg)
//						&& Common_Utilty
//								.isNotEmptyString(resume.project.get(i).timeend)) {
//					tvProjectdate.setText(resume.project.get(i).timebeg + "-"
//							+ resume.project.get(i).timeend);
//				} else {
//					tvProjectdate.setVisibility(View.GONE);
//				}
//
//				if (Common_Utilty.isNotEmptyString(resume.project.get(i).size)) {
//					tvTeamSize.setText("Team size : "
//							+ resume.project.get(i).size);
//				} else {
//					tvTeamSize.setVisibility(View.GONE);
//				}
//
//				if (Common_Utilty.isNotEmptyString(resume.project.get(i).role)) {
//					tvRole.setText("Role : " + resume.project.get(i).role);
//				} else {
//					tvRole.setVisibility(View.GONE);
//				}
//
//				if (Common_Utilty
//						.isNotEmptyString(resume.project.get(i).description)) {
//					tvDescrip.setText("Description : "
//							+ resume.project.get(i).description);
//				} else {
//					tvDescrip.setVisibility(View.GONE);
//				}
//
//				projectDetails.addView(projectLayout);
//
//			}
//		}
//
//		else {
//			LinearLayout projectHeading = (LinearLayout) findViewById(R.id.projects);
//			projectHeading.setVisibility(View.GONE);
//
//		}
//
//		LinearLayout researchPaper = (LinearLayout) findViewById(R.id.txt_write_research_paper);
//
//		if (resume.research != null && !resume.research.isEmpty()) {
//
//			TextView tvPaperTitle, tvJournalName, tvVolume, tvIssue, tvDescrip;
//			LinearLayout paperLayout;
//			for (int i = 0; i < resume.research.size(); i++) {
//				paperLayout = (LinearLayout) getLayoutInflater().inflate(
//						R.layout.research_view, null);
//				tvPaperTitle = (TextView) paperLayout
//						.findViewById(R.id.tv_paper_title);
//
//				tvJournalName = (TextView) paperLayout
//						.findViewById(R.id.tv_journal_name);
//
//				tvVolume = (TextView) paperLayout.findViewById(R.id.tv_volume);
//
//				tvIssue = (TextView) paperLayout.findViewById(R.id.tv_issue);
//
//				tvDescrip = (TextView) paperLayout
//						.findViewById(R.id.tv_description);
//
//				if (Common_Utilty
//						.isNotEmptyString(resume.research.get(i).papertitle)) {
//					tvPaperTitle.setText(resume.research.get(i).papertitle);
//				}
//
//				if (Common_Utilty
//						.isNotEmptyString(resume.research.get(i).journal)) {
//					tvJournalName.setText("Journal Name : "
//							+ resume.research.get(i).journal);
//				} else {
//					tvJournalName.setVisibility(View.GONE);
//				}
//
//				if (Common_Utilty
//						.isNotEmptyString(resume.research.get(i).volume)) {
//					tvVolume.setText("Volume : "
//							+ resume.research.get(i).volume);
//				} else {
//					tvVolume.setVisibility(View.GONE);
//				}
//
//				if (Common_Utilty
//						.isNotEmptyString(resume.research.get(i).paperIssue)) {
//					tvIssue.setText("Issue : "
//							+ resume.research.get(i).paperIssue);
//				} else {
//					tvIssue.setVisibility(View.GONE);
//				}
//
//				if (Common_Utilty
//						.isNotEmptyString(resume.research.get(i).paperDescription)) {
//					tvDescrip.setText("Description : "
//							+ resume.research.get(i).paperDescription);
//				} else {
//					tvDescrip.setVisibility(View.GONE);
//				}
//
//				researchPaper.addView(paperLayout);
//
//			}
//		}
//
//		else {
//			LinearLayout paperHeading = (LinearLayout) findViewById(R.id.research_paper);
//			paperHeading.setVisibility(View.GONE);
//
//		}
//
//		if (resume.skills != null && !resume.skills.isEmpty()) {
//			skills = (TextView) findViewById(R.id.txt_write_skills);
//			String str = "";
//			int i = 0;
//
//			for (String skill : resume.skills) {
//				i++;
//				if (Common_Utilty.isNotEmptyString(skill)) {
//					if (i < resume.skills.size())
//						str = str + skill + '\n';
//					else if (i == resume.skills.size())
//						str = str + skill;
//				}
//			}
//			skills.setText(str);
//		} else {
//			LinearLayout skillLayout = (LinearLayout) findViewById(R.id.skills);
//			skillLayout.setVisibility(View.GONE);
//		}
//
//		if (resume.achive != null && !resume.achive.isEmpty()) {
//			achievements = (TextView) findViewById(R.id.txt_write_achievements);
//			String str1 = "";
//			int i = 0;
//			for (String achievement : resume.achive) {
//				i++;
//				if (Common_Utilty.isNotEmptyString(achievement)) {
//					if (i < resume.achive.size())
//						str1 = str1 + achievement + '\n';
//					else if (i == resume.achive.size())
//						str1 = str1 + achievement;
//				}
//			}
//			achievements.setText(str1);
//
//		} else {
//			LinearLayout achvLayout = (LinearLayout) findViewById(R.id.achievements);
//			achvLayout.setVisibility(View.GONE);
//
//		}
//
//		if (resume.exCarr != null && !resume.exCarr.isEmpty()) {
//			extraCurricular = (TextView) findViewById(R.id.txt_write_extra_curricular);
//			String str2 = "";
//			int i = 0;
//			for (String extraClr : resume.exCarr) {
//				i++;
//				if (Common_Utilty.isNotEmptyString(extraClr)) {
//					if (i < resume.exCarr.size())
//						str2 = str2 + extraClr + '\n';
//					else if (i == resume.exCarr.size())
//						str2 = str2 + extraClr;
//				}
//			}
//			extraCurricular.setText(str2);
//		}
//
//		else {
//			LinearLayout extraLayout = (LinearLayout) findViewById(R.id.extra_curricular);
//			extraLayout.setVisibility(View.GONE);
//
//		}
//
//		if (resume.hobbies != null && !resume.hobbies.isEmpty()) {
//			hobbies = (TextView) findViewById(R.id.txt_write_hobbies);
//
//			String str3 = "";
//			int i = 0;
//			for (String hobbie : resume.hobbies) {
//				i++;
//				if (Common_Utilty.isNotEmptyString(hobbie)) {
//					if (i < resume.hobbies.size())
//						str3 = str3 + hobbie + '\n';
//					else if (i == resume.hobbies.size())
//						str3 = str3 + hobbie;
//				}
//			}
//			hobbies.setText(str3);
//		}
//
//		else {
//			LinearLayout hobbieLayout = (LinearLayout) findViewById(R.id.hobbies);
//			hobbieLayout.setVisibility(View.GONE);
//
//		}
//
//		if (resume.strength != null && !resume.strength.isEmpty()) {
//			strengths = (TextView) findViewById(R.id.txt_write_strengths);
//			String str4 = "";
//			int i = 0;
//			for (String strength : resume.strength) {
//				i++;
//				if (Common_Utilty.isNotEmptyString(strength)) {
//					if (i < resume.strength.size())
//						str4 = str4 + strength + '\n';
//					else if (i == resume.strength.size())
//						str4 = str4 + strength;
//				}
//			}
//			strengths.setText(str4);
//		}
//
//		else {
//			LinearLayout strengthLayout = (LinearLayout) findViewById(R.id.strength);
//			strengthLayout.setVisibility(View.GONE);
//		}
//
//		LinearLayout personalLayout = (LinearLayout) findViewById(R.id.personal_details);
//
//		LinearLayout personalItem = null;
//
//		if (Common_Utilty.isNotEmptyString(resume.name)) {
//
//			personalItem = (LinearLayout) getLayoutInflater().inflate(
//					R.layout.personal_view, null);
//
//			((TextView) personalItem.findViewById(R.id.tv_attribute))
//					.setText("Name");
//
//			((TextView) personalItem.findViewById(R.id.tv_value))
//					.setText(resume.name);
//
//			personalLayout.addView(personalItem);
//		}
//
//		if (resume.gender != null
//				&& !resume.gender.getString().equals(
//						Resume.Gender.NOT_SPECIFIED.getString())) {
//
//			personalItem = (LinearLayout) getLayoutInflater().inflate(
//					R.layout.personal_view, null);
//
//			((TextView) personalItem.findViewById(R.id.tv_attribute))
//					.setText("Gender");
//
//			((TextView) personalItem.findViewById(R.id.tv_value))
//					.setText(resume.gender.getString());
//
//			personalLayout.addView(personalItem);
//
//		}
//
//		if (Common_Utilty.isNotEmptyString(resume.dob)) {
//			personalItem = (LinearLayout) getLayoutInflater().inflate(
//					R.layout.personal_view, null);
//
//			((TextView) personalItem.findViewById(R.id.tv_attribute))
//					.setText("Date of birth");
//
//			((TextView) personalItem.findViewById(R.id.tv_value))
//					.setText(resume.dob);
//
//			personalLayout.addView(personalItem);
//		}
//
//		if (Common_Utilty.isNotEmptyString(resume.nationality)) {
//			personalItem = (LinearLayout) getLayoutInflater().inflate(
//					R.layout.personal_view, null);
//
//			((TextView) personalItem.findViewById(R.id.tv_attribute))
//					.setText("Nationality");
//
//			((TextView) personalItem.findViewById(R.id.tv_value))
//					.setText(resume.nationality);
//
//			personalLayout.addView(personalItem);
//		}
//
//		if (Common_Utilty.isNotEmptyString(resume.language)) {
//			personalItem = (LinearLayout) getLayoutInflater().inflate(
//					R.layout.personal_view, null);
//
//			((TextView) personalItem.findViewById(R.id.tv_attribute))
//					.setText("Language known");
//
//			((TextView) personalItem.findViewById(R.id.tv_value))
//					.setText(resume.language);
//
//			personalLayout.addView(personalItem);
//		}
//		if (Common_Utilty.isNotEmptyString(resume.fatherName)) {
//			personalItem = (LinearLayout) getLayoutInflater().inflate(
//					R.layout.personal_view, null);
//
//			((TextView) personalItem.findViewById(R.id.tv_attribute))
//					.setText("Father's name");
//
//			((TextView) personalItem.findViewById(R.id.tv_value))
//					.setText(resume.fatherName);
//
//			personalLayout.addView(personalItem);
//		}
//		if (Common_Utilty.isNotEmptyString(resume.motherName)) {
//			personalItem = (LinearLayout) getLayoutInflater().inflate(
//					R.layout.personal_view, null);
//
//			((TextView) personalItem.findViewById(R.id.tv_attribute))
//					.setText("Mother's name");
//
//			((TextView) personalItem.findViewById(R.id.tv_value))
//					.setText(resume.motherName);
//
//			personalLayout.addView(personalItem);
//		}
//
//		if (resume.addressesAreSame) {
//			if (!presentAddressStr.equals("")) {
//				personalItem = (LinearLayout) getLayoutInflater().inflate(
//						R.layout.personal_view, null);
//
//				((TextView) personalItem.findViewById(R.id.tv_attribute))
//						.setText("Address");
//
//				((TextView) personalItem.findViewById(R.id.tv_value))
//						.setText(presentAddressStr);
//
//				personalLayout.addView(personalItem);
//			}
//		}
//
//		if (!resume.addressesAreSame) {
//
//			String permanentAddressStr = "";
//
//			if (Common_Utilty.isNotEmptyString(resume.city2)) {
//				if (Common_Utilty.isNotEmptyString(resume.street2)) {
//					permanentAddressStr = permanentAddressStr + resume.street2
//							+ ",";
//
//				}
//				permanentAddressStr = permanentAddressStr + resume.city2;
//				if (Common_Utilty.isNotEmptyString(resume.pincode2)) {
//					permanentAddressStr = permanentAddressStr + "-"
//							+ resume.pincode2;
//				}
//			}
//
//			if (Common_Utilty.isNotEmptyString(resume.city2)
//					&& Common_Utilty.isNotEmptyString(resume.country2)) {
//				permanentAddressStr = permanentAddressStr + ", ";
//			}
//
//			if (Common_Utilty.isNotEmptyString(resume.country2)) {
//
//				if (Common_Utilty.isNotEmptyString(resume.state2)) {
//					permanentAddressStr = permanentAddressStr + resume.state2
//							+ ",";
//
//				}
//				permanentAddressStr = permanentAddressStr + resume.country2;
//			}
//
//			if (!permanentAddressStr.equals("")) {
//				personalItem = (LinearLayout) getLayoutInflater().inflate(
//						R.layout.personal_view, null);
//
//				((TextView) personalItem.findViewById(R.id.tv_attribute))
//						.setText("Permanent Address");
//
//				((TextView) personalItem.findViewById(R.id.tv_value))
//						.setText(permanentAddressStr);
//
//				personalLayout.addView(personalItem);
//			}
//
//		}
//
//		if (resume.customFields != null && !resume.customFields.isEmpty()) {
//
//			for (HashMap.Entry entry : resume.customFields.entrySet()) {
//				personalItem = (LinearLayout) getLayoutInflater().inflate(
//						R.layout.personal_view, null);
//
//				((TextView) personalItem.findViewById(R.id.tv_attribute))
//						.setText(entry.getKey().toString());
//
//				((TextView) personalItem.findViewById(R.id.tv_value))
//						.setText(entry.getValue().toString());
//
//				personalLayout.addView(personalItem);
//			}
//		}
//
//		if (personalItem == null) {
//			personalLayout.setVisibility(View.GONE);
//		}
//
//		else {
//			personalLayout.setVisibility(View.VISIBLE);
//		}
//
//		LinearLayout referenceLayout = (LinearLayout) findViewById(R.id.txt_write_reference);
//
//		if (resume.reference != null && !resume.reference.isEmpty()) {
//
//			TextView fullName, designation, workAddress, emailAddress, workPhone, personalPhone;
//			LinearLayout refer;
//
//			for (int i = 0; i < resume.reference.size(); i++) {
//
//				refer = (LinearLayout) getLayoutInflater().inflate(
//						R.layout.reference_view, null);
//
//				fullName = (TextView) refer.findViewById(R.id.tv_full_name);
//				designation = (TextView) refer
//						.findViewById(R.id.tv_title_designation);
//				workAddress = (TextView) refer
//						.findViewById(R.id.tv_work_address);
//				emailAddress = (TextView) refer
//						.findViewById(R.id.tv_email_address);
//				workPhone = (TextView) refer.findViewById(R.id.tv_work_phone);
//				personalPhone = (TextView) refer
//						.findViewById(R.id.tv_personal_phone);
//
//				if (Common_Utilty
//						.isNotEmptyString(resume.reference.get(i).name)) {
//					fullName.setText(resume.reference.get(i).name);
//				}
//
//				if (Common_Utilty
//						.isNotEmptyString(resume.reference.get(i).title)) {
//					designation.setText("Designation : "
//							+ resume.reference.get(i).title);
//				} else {
//					designation.setVisibility(View.GONE);
//				}
//
//				if (Common_Utilty
//						.isNotEmptyString(resume.reference.get(i).workAddress)) {
//					workAddress.setText("Work Address : "
//							+ resume.reference.get(i).workAddress);
//				} else {
//					workAddress.setVisibility(View.GONE);
//				}
//
//				if (Common_Utilty
//						.isNotEmptyString(resume.reference.get(i).email)) {
//					emailAddress.setText("E-mail : "
//							+ resume.reference.get(i).email);
//				} else {
//					emailAddress.setVisibility(View.GONE);
//				}
//
//				if (Common_Utilty
//						.isNotEmptyString(resume.reference.get(i).workPhone)) {
//					workPhone.setText("Work Phone : "
//							+ resume.reference.get(i).workPhone);
//				} else {
//					workPhone.setVisibility(View.GONE);
//				}
//
//				if (Common_Utilty
//						.isNotEmptyString(resume.reference.get(i).personalPhone)) {
//					personalPhone.setText("Personal Phone : "
//							+ resume.reference.get(i).personalPhone);
//				} else {
//					personalPhone.setVisibility(View.GONE);
//				}
//				referenceLayout.addView(refer);
//			}
//		} else {
//			LinearLayout referenceHead = (LinearLayout) findViewById(R.id.reference);
//			referenceHead.setVisibility(View.GONE);
//		}
//
//		declaration = (TextView) findViewById(R.id.txt_write_declaration);
//		if (Common_Utilty.isNotEmptyString(resume.declaration)) {
//			declaration.setText(resume.declaration);
//		} else {
//			LinearLayout declarationLayout = (LinearLayout) findViewById(R.id.declaration);
//			declarationLayout.setVisibility(View.GONE);
//		}
//
//		if (!Common_Utilty.isNotEmptyString(resume.declaration)) {
//			View vdec = (View) findViewById(R.id.view_dec);
//			vdec.setVisibility(View.GONE);
//		}
//	}

	}


