package com.grexoft.resume.templates;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.grexoft.resume.R;
import com.grexoft.resume.Resume;
import com.grexoft.resume.helpers.Common_Utilty;
import com.grexoft.resume.helpers.SdCardManager;

import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

public abstract class TemplateFragment extends Fragment {

	private static final String FONTS_DIRECTORY_PATH = Common_Utilty.FONT_DIRECTORY + "/new/";

	protected Resume resumeData;

	protected ArrayList<TextView> headings, contents, titles;

	protected View fragmentView;

	protected String presentAddressStr;

	protected boolean isPersonalInfoPresent;
	protected TextView name, title;


	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		fragmentView = inflater.inflate(getLayoutId(),container,false);

		resumeData = Resume.getInstance();

		renderLayout();

		setFormatting();

		return fragmentView;

	}

	protected abstract int getLayoutId();

	protected abstract void afterResumeHead();

	protected void renderLayout(){

		headings = new ArrayList<TextView>();
		contents = new ArrayList<TextView>();
		titles = new ArrayList<TextView>();

		addResumeHead();

		addObjective();

		addEducation();

		addProjects();

		addWorkExperience();

		addResearch();

		addSkills();

		addAchievements();

		addExtraCurricular();

		addStrengths();

		addHobbies();

		addReference();

		addPersonalDetails();

		addDeclaration();

	}

	protected void setFormatting(){

		Typeface headingTypeface = Typeface.createFromAsset(getActivity().getAssets(), FONTS_DIRECTORY_PATH + Common_Utilty.replacement(resumeData.getPreferences().getHeadingFont(), " ", "_") + ".ttf");
		for(TextView tv : headings){
			tv.setTypeface(headingTypeface,Typeface.BOLD);
			tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, resumeData.getPreferences().getHeadingTextSize());
		}

		Typeface contentTypeface = Typeface.createFromAsset(getActivity().getAssets(), FONTS_DIRECTORY_PATH + Common_Utilty.replacement(resumeData.getPreferences().getContentFont(), " ", "_") + ".ttf");
		System.out.println("content font in template fragment is : " + Common_Utilty.replacement(resumeData.getPreferences().getContentFont(), " ", "_") + ".ttf");
		for(TextView tv : contents){
			tv.setTypeface(contentTypeface);
			tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, resumeData.getPreferences().getContentTextSize());
		}

		Typeface titleTypeface = Typeface.createFromAsset(getActivity().getAssets(), FONTS_DIRECTORY_PATH + Common_Utilty.replacement(resumeData.getPreferences().getTitleFont(), " ", "_") + ".ttf");
		for(TextView tv : titles){
			tv.setTypeface(titleTypeface,Typeface.BOLD);
			tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, resumeData.getPreferences().getTitleTextSize());
		}
	}

	protected void addResumeHead(){

		name = (TextView) findViewById(R.id.txt_name);
		TextView signature = (TextView) findViewById(R.id.txt_signature);
		if (Common_Utilty.isNotEmptyString(resumeData.name)) {
			name.setText(resumeData.name.toUpperCase(Locale.US));
			signature.setText(resumeData.name.toUpperCase(Locale.US));
		} else {
			name.setText("Your Name");
			signature.setText("Your Signature");
		}

		title = (TextView)findViewById(R.id.txt_title);

		if(Common_Utilty.isNotEmptyString(resumeData.title) && resumeData.getPreferences().isShowTitle()){
			title.setText(WordUtils.capitalize(resumeData.title));
		}else{
			title.setVisibility(View.GONE);
		}
		TextView address = (TextView) findViewById(R.id.txt_address);
		if (address != null){
			presentAddressStr = "";

			if (Common_Utilty.isNotEmptyString(resumeData.city)) {
				if (Common_Utilty.isNotEmptyString(resumeData.street)) {
					presentAddressStr = presentAddressStr + resumeData.street + "\n";

				}
				presentAddressStr = presentAddressStr + resumeData.city;
				if (Common_Utilty.isNotEmptyString(resumeData.pincode)) {
					presentAddressStr = presentAddressStr + "-" + resumeData.pincode;
				}
			}

			if (Common_Utilty.isNotEmptyString(resumeData.city)
					&& Common_Utilty.isNotEmptyString(resumeData.country)) {
				presentAddressStr = presentAddressStr + ", " + "\n";
			}

			if (Common_Utilty.isNotEmptyString(resumeData.country)) {

				if (Common_Utilty.isNotEmptyString(resumeData.state)) {
					presentAddressStr = presentAddressStr + resumeData.state + ",";

				}
				presentAddressStr = presentAddressStr + resumeData.country;
			}

			if (!presentAddressStr.equals("")) {
				address.setText(presentAddressStr);
				contents.add(address);
			} else {
				address.setVisibility(View.GONE);
			}
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

		ImageView image = (ImageView) findViewById(R.id.image_png);

		if (image != null){
			if(resumeData.getPreferences().isShowImage() && Common_Utilty.isNotEmptyString(resumeData.image)){

				image.setVisibility(View.VISIBLE);
				image.setImageBitmap(SdCardManager.getImage(resumeData.image));

			}
			else {
				image.setVisibility(View.GONE);

			}
		}

		afterResumeHead();

	}

	protected void addObjective(){

		TextView objective = (TextView) findViewById(R.id.txt_write_objective);
		if (Common_Utilty.isNotEmptyString(resumeData.objectives)) {
			headings.add((TextView)findViewById(R.id.txt_heading_objective));
			objective.setText(resumeData.objectives);
			contents.add(objective);
		} else {
			LinearLayout objectiveLayout = (LinearLayout) findViewById(R.id.objective);
			objectiveLayout.setVisibility(View.GONE);
		}

	}

	protected void addEducation(){

		ViewGroup educationQualification = (ViewGroup) findViewById(R.id.txt_write_education);

		if (resumeData.educationDetail != null && !resumeData.educationDetail.isEmpty()) {

			headings.add((TextView)findViewById(R.id.txt_heading_education));
			TextView tvEdu;
			for (int i = 0; i < resumeData.educationDetail.size(); i++) {

				tvEdu = (TextView) getLayoutInflater().inflate(
						R.layout.education_view, null);
				SpannableString styledString = new SpannableString(
						(Common_Utilty.isNotEmptyString(resumeData.educationDetail
								.get(i).degree) ? resumeData.educationDetail.get(i).degree
								: "")

								+ (Common_Utilty
								.isNotEmptyString(resumeData.educationDetail
										.get(i).university) ? " from "
								+ resumeData.educationDetail.get(i).university
								: "")

								+ (Common_Utilty
								.isNotEmptyString(resumeData.educationDetail
										.get(i).passout) ? " in the year "
								+ resumeData.educationDetail.get(i).passout
								: "")

								+ (Common_Utilty
								.isNotEmptyString(resumeData.educationDetail
										.get(i).result) ? ". Percentage/CGPA : "
								+ resumeData.educationDetail.get(i).result
								: ""));

				styledString.setSpan(new StyleSpan(Typeface.BOLD), 0,
						resumeData.educationDetail.get(i).degree.length(), 0);
				//		tvEdu.setPadding(5, 0, 5, 0);
				tvEdu.setText(styledString);
				contents.add(tvEdu);
				educationQualification.addView(tvEdu);
			}
		} else {
			ViewGroup educationLayout = (ViewGroup) findViewById(R.id.education);

			educationLayout.setVisibility(View.GONE);
		}

	}

	protected void addProjects(){

		ViewGroup projectDetails = (ViewGroup) findViewById(R.id.txt_write_projects);

		if (resumeData.project != null && !resumeData.project.isEmpty()) {

			headings.add((TextView)findViewById(R.id.txt_heading_projects));

			TextView tvProjName, tvProjectdate, tvTeamSize, tvRole, tvDescrip;
			LinearLayout projectLayout;
			for (int i = 0; i < resumeData.project.size(); i++) {
				projectLayout = (LinearLayout) getLayoutInflater().inflate(
						R.layout.project_view, null);
				tvProjName = (TextView) projectLayout
						.findViewById(R.id.tv_project_name);

				tvProjectdate = (TextView) projectLayout
						.findViewById(R.id.tv_project_date);

				tvTeamSize = (TextView) projectLayout
						.findViewById(R.id.tv_team_size);

				tvRole = (TextView) projectLayout.findViewById(R.id.tv_role);

				tvDescrip = (TextView) projectLayout
						.findViewById(R.id.tv_description);

				contents.add(tvProjName);
				contents.add(tvProjectdate);
				contents.add(tvTeamSize);
				contents.add(tvRole);
				contents.add(tvDescrip);


				if (Common_Utilty.isNotEmptyString(resumeData.project.get(i).title)) {
					tvProjName.setText(resumeData.project.get(i).title);
				}

				if (Common_Utilty
						.isNotEmptyString(resumeData.project.get(i).timebeg)
						&& Common_Utilty
						.isNotEmptyString(resumeData.project.get(i).timeend)) {
					tvProjectdate.setText(resumeData.project.get(i).timebeg + "-"
							+ resumeData.project.get(i).timeend);
				} else {
					tvProjectdate.setVisibility(View.GONE);
				}

				if (Common_Utilty.isNotEmptyString(resumeData.project.get(i).size)) {
					tvTeamSize.setText("Team size : "
							+ resumeData.project.get(i).size);
				} else {
					tvTeamSize.setVisibility(View.GONE);
				}

				if (Common_Utilty.isNotEmptyString(resumeData.project.get(i).role)) {
					tvRole.setText("Role : " + resumeData.project.get(i).role);
				} else {
					tvRole.setVisibility(View.GONE);
				}

				if (Common_Utilty
						.isNotEmptyString(resumeData.project.get(i).description)) {
					tvDescrip.setText("Description : "
							+ resumeData.project.get(i).description);
				} else {
					tvDescrip.setVisibility(View.GONE);
				}

				projectDetails.addView(projectLayout);

			}
		}

		else {
			ViewGroup projectHeading = (ViewGroup) findViewById(R.id.projects);
			projectHeading.setVisibility(View.GONE);

		}

	}

	protected void addWorkExperience(){

		ViewGroup workExpDetails = (ViewGroup) findViewById(R.id.txt_write_work_experience);

		if (resumeData.workExperience != null && !resumeData.workExperience.isEmpty()) {

			headings.add((TextView)findViewById(R.id.txt_heading_work_experience));

			TextView tvComapny, tvJobDesig, tvWorkDate, tvWorkDescrip;
			LinearLayout workExp;
			for (int i = 0; i < resumeData.workExperience.size(); i++) {
				workExp = (LinearLayout) getLayoutInflater().inflate(
						R.layout.work_exeperience_view, null);
				tvComapny = (TextView) workExp
						.findViewById(R.id.tv_company_name);
				tvWorkDate = (TextView) workExp.findViewById(R.id.tv_work_date);
				tvJobDesig = (TextView) workExp
						.findViewById(R.id.tv_job_design);
				tvWorkDescrip = (TextView) workExp
						.findViewById(R.id.tv_job_description);

				contents.add(tvComapny);
				contents.add(tvWorkDate);
				contents.add(tvJobDesig);
				contents.add(tvWorkDescrip);

				tvComapny.setText(resumeData.workExperience.get(i).compName);

				if (Common_Utilty
						.isNotEmptyString(resumeData.workExperience.get(i).timeStart)
						&& Common_Utilty.isNotEmptyString(resumeData.workExperience
						.get(i).timeEnd)) {
					tvWorkDate.setText(resumeData.workExperience.get(i).timeStart
							+ "-" + resumeData.workExperience.get(i).timeEnd);
				} else {
					tvWorkDate.setVisibility(View.GONE);
				}

				if (Common_Utilty
						.isNotEmptyString(resumeData.workExperience.get(i).jobDesig)) {
					tvJobDesig.setText("Designation : "
							+ resumeData.workExperience.get(i).jobDesig);
				} else {
					tvJobDesig.setVisibility(View.GONE);
				}

				if (Common_Utilty
						.isNotEmptyString(resumeData.workExperience.get(i).description)) {
					tvWorkDescrip.setText("Description : "
							+ resumeData.workExperience.get(i).description);
				} else {
					tvWorkDescrip.setVisibility(View.GONE);
				}
				workExpDetails.addView(workExp);
			}
		}

		else {
			ViewGroup workExpHeading = (ViewGroup) findViewById(R.id.work_experience);
			workExpHeading.setVisibility(View.GONE);

		}

	}

	protected void addResearch(){

		ViewGroup researchPaper = (ViewGroup) findViewById(R.id.txt_write_research_paper);

		if (resumeData.research != null && !resumeData.research.isEmpty()) {

			headings.add((TextView)findViewById(R.id.txt_heading_research_paper));

			TextView tvPaperTitle, tvJournalName, tvVolume, tvIssue, tvDescrip;
			LinearLayout paperLayout;
			for (int i = 0; i < resumeData.research.size(); i++) {
				paperLayout = (LinearLayout) getLayoutInflater().inflate(
						R.layout.research_view, null);
				tvPaperTitle = (TextView) paperLayout
						.findViewById(R.id.tv_paper_title);

				tvJournalName = (TextView) paperLayout
						.findViewById(R.id.tv_journal_name);

				tvVolume = (TextView) paperLayout.findViewById(R.id.tv_volume);

				tvIssue = (TextView) paperLayout.findViewById(R.id.tv_issue);

				tvDescrip = (TextView) paperLayout
						.findViewById(R.id.tv_description);

				contents.add(tvPaperTitle);
				contents.add(tvJournalName);
				contents.add(tvVolume);
				contents.add(tvIssue);
				contents.add(tvDescrip);

				if (Common_Utilty
						.isNotEmptyString(resumeData.research.get(i).papertitle)) {
					tvPaperTitle.setText(resumeData.research.get(i).papertitle);
				}

				if (Common_Utilty
						.isNotEmptyString(resumeData.research.get(i).journal)) {
					tvJournalName.setText("Journal Name : "
							+ resumeData.research.get(i).journal);
				} else {
					tvJournalName.setVisibility(View.GONE);
				}

				if (Common_Utilty
						.isNotEmptyString(resumeData.research.get(i).volume)) {
					tvVolume.setText("Volume : "
							+ resumeData.research.get(i).volume);
				} else {
					tvVolume.setVisibility(View.GONE);
				}

				if (Common_Utilty
						.isNotEmptyString(resumeData.research.get(i).paperIssue)) {
					tvIssue.setText("Issue : "
							+ resumeData.research.get(i).paperIssue);
				} else {
					tvIssue.setVisibility(View.GONE);
				}

				if (Common_Utilty
						.isNotEmptyString(resumeData.research.get(i).paperDescription)) {
					tvDescrip.setText("Description : "
							+ resumeData.research.get(i).paperDescription);
				} else {
					tvDescrip.setVisibility(View.GONE);
				}

				researchPaper.addView(paperLayout);

			}
		}

		else {
			ViewGroup paperHeading = (ViewGroup) findViewById(R.id.research_paper);
			paperHeading.setVisibility(View.GONE);

		}

	}

	protected void addSkills(){

		if (resumeData.skills != null && !resumeData.skills.isEmpty()) {
			headings.add((TextView)findViewById(R.id.txt_heading_skills));
			TextView skills = (TextView) findViewById(R.id.txt_write_skills);
			contents.add(skills);
			String str = "";
			int i = 0;

			for (String skill : resumeData.skills) {
				i++;
				if (Common_Utilty.isNotEmptyString(skill)) {
					if (i < resumeData.skills.size())
						str = str + skill + '\n';
					else if (i == resumeData.skills.size())
						str = str + skill;
				}
			}
			skills.setText(str);
		} else {
			LinearLayout skillLayout = (LinearLayout) findViewById(R.id.skills);
			skillLayout.setVisibility(View.GONE);
		}

	}

	protected void addAchievements(){

		if (resumeData.achive != null && !resumeData.achive.isEmpty()) {
			headings.add((TextView)findViewById(R.id.txt_heading_achievements));
			TextView achievements = (TextView) findViewById(R.id.txt_write_achievements);
			contents.add(achievements);
			String str1 = "";
			int i = 0;
			for (String achievement : resumeData.achive) {
				i++;
				if (Common_Utilty.isNotEmptyString(achievement)) {
					if (i < resumeData.achive.size())
						str1 = str1 + achievement + '\n';
					else if (i == resumeData.achive.size())
						str1 = str1 + achievement;
				}
			}
			achievements.setText(str1);

		} else {
			LinearLayout achvLayout = (LinearLayout) findViewById(R.id.achievements);
			achvLayout.setVisibility(View.GONE);

		}

	}

	protected void addExtraCurricular(){

		if (resumeData.exCarr != null && !resumeData.exCarr.isEmpty()) {
			headings.add((TextView)findViewById(R.id.txt_heading_extra_curricular));
			TextView extraCurricular = (TextView) findViewById(R.id.txt_write_extra_curricular);
			contents.add(extraCurricular);
			String str2 = "";
			int i = 0;
			for (String extraClr : resumeData.exCarr) {
				i++;
				if (Common_Utilty.isNotEmptyString(extraClr)) {
					if (i < resumeData.exCarr.size())
						str2 = str2 + extraClr + '\n';
					else if (i == resumeData.exCarr.size())
						str2 = str2 + extraClr;
				}
			}
			extraCurricular.setText(str2);
		}

		else {
			LinearLayout extraLayout = (LinearLayout) findViewById(R.id.extra_curricular);
			extraLayout.setVisibility(View.GONE);

		}

	}

	protected void addStrengths(){

		if (resumeData.strength != null && !resumeData.strength.isEmpty()) {
			headings.add((TextView)findViewById(R.id.txt_heading_strengths));
			TextView strengths = (TextView) findViewById(R.id.txt_write_strengths);
			contents.add(strengths);
			String str4 = "";
			int i = 0;
			for (String strength : resumeData.strength) {
				i++;
				if (Common_Utilty.isNotEmptyString(strength)) {
					if (i < resumeData.strength.size())
						str4 = str4 + strength + '\n';
					else if (i == resumeData.strength.size())
						str4 = str4 + strength;
				}
			}
			strengths.setText(str4);
		}

		else {
			LinearLayout strengthLayout = (LinearLayout) findViewById(R.id.strength);
			strengthLayout.setVisibility(View.GONE);
		}

	}

	protected void addHobbies(){

		if (resumeData.hobbies != null && !resumeData.hobbies.isEmpty()) {
			headings.add((TextView)findViewById(R.id.txt_heading_hobbies));
			TextView hobbies = (TextView) findViewById(R.id.txt_write_hobbies);
			contents.add(hobbies);
			String str3 = "";
			int i = 0;
			for (String hobbie : resumeData.hobbies) {
				i++;
				if (Common_Utilty.isNotEmptyString(hobbie)) {
					if (i < resumeData.hobbies.size())
						str3 = str3 + hobbie + '\n';
					else if (i == resumeData.hobbies.size())
						str3 = str3 + hobbie;
				}
			}
			hobbies.setText(str3);
		}

		else {
			LinearLayout hobbieLayout = (LinearLayout) findViewById(R.id.hobbies);
			hobbieLayout.setVisibility(View.GONE);

		}

	}

	protected void addPersonalDetails(){

		ViewGroup personalLayout = (ViewGroup) findViewById(R.id.personal_details);

		LinearLayout personalItem = null;
		TextView tvAttr, tvValue;

		if (Common_Utilty.isNotEmptyString(resumeData.name)) {

			personalLayout.addView(getPersonalItem("name", resumeData.name));
		}

		if (resumeData.gender != null
				&& !resumeData.gender.getString().equals(
				Resume.Gender.NOT_SPECIFIED.getString())) {

			personalLayout.addView(getPersonalItem("Gender", resumeData.gender.getString()));

		}

		if (Common_Utilty.isNotEmptyString(resumeData.dob)) {
			personalItem = (LinearLayout) getLayoutInflater().inflate(
					R.layout.personal_view, null);

			personalLayout.addView(getPersonalItem("Date of birth", resumeData.dob));
		}

		if (Common_Utilty.isNotEmptyString(resumeData.nationality)) {
			personalItem = (LinearLayout) getLayoutInflater().inflate(
					R.layout.personal_view, null);

			personalLayout.addView(getPersonalItem("Nationality", resumeData.nationality));
		}

		if (Common_Utilty.isNotEmptyString(resumeData.language)) {
			personalItem = (LinearLayout) getLayoutInflater().inflate(
					R.layout.personal_view, null);

			personalLayout.addView(getPersonalItem("Languages known", resumeData.language));
		}
		if (Common_Utilty.isNotEmptyString(resumeData.fatherName)) {
			personalItem = (LinearLayout) getLayoutInflater().inflate(
					R.layout.personal_view, null);

			personalLayout.addView(getPersonalItem("Father's name", resumeData.fatherName));
		}
		if (Common_Utilty.isNotEmptyString(resumeData.motherName)) {
			personalItem = (LinearLayout) getLayoutInflater().inflate(
					R.layout.personal_view, null);

			personalLayout.addView(getPersonalItem("Mother's name", resumeData.motherName));
		}

		if (resumeData.addressesAreSame) {
			if (presentAddressStr != null && !presentAddressStr.equals("")) {
				personalItem = (LinearLayout) getLayoutInflater().inflate(
						R.layout.personal_view, null);

				personalLayout.addView(getPersonalItem("Address", Common_Utilty.replacement(presentAddressStr, "\n", " ")));

			}
		}

		if (!resumeData.addressesAreSame) {

			if (presentAddressStr == null){

				presentAddressStr = "";

				if (Common_Utilty.isNotEmptyString(resumeData.city)) {
					if (Common_Utilty.isNotEmptyString(resumeData.street)) {
						presentAddressStr = presentAddressStr + resumeData.street + "\n";

					}
					presentAddressStr = presentAddressStr + resumeData.city;
					if (Common_Utilty.isNotEmptyString(resumeData.pincode)) {
						presentAddressStr = presentAddressStr + "-" + resumeData.pincode;
					}
				}

				if (Common_Utilty.isNotEmptyString(resumeData.city)
						&& Common_Utilty.isNotEmptyString(resumeData.country)) {
					presentAddressStr = presentAddressStr + ", " + "\n";
				}

				if (Common_Utilty.isNotEmptyString(resumeData.country)) {

					if (Common_Utilty.isNotEmptyString(resumeData.state)) {
						presentAddressStr = presentAddressStr + resumeData.state + ",";

					}
					presentAddressStr = presentAddressStr + resumeData.country;
				}

				if (!presentAddressStr.equals("")) {
					personalLayout.addView(getPersonalItem("Present address", presentAddressStr));
				}

			}


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

				personalLayout.addView(getPersonalItem("Permanent address", permanentAddressStr));
			}

		}

		if (resumeData.customFields != null && !resumeData.customFields.isEmpty()) {

			for (HashMap.Entry entry : resumeData.customFields.entrySet()) {
				personalLayout.addView(getPersonalItem(entry.getKey().toString(), entry.getValue().toString()));
			}
		}

		if (personalItem == null) {
			personalLayout.setVisibility(View.GONE);
		} else {
			isPersonalInfoPresent = true;
			personalLayout.setVisibility(View.VISIBLE);
			headings.add((TextView) findViewById(R.id.txt_heading_personal_details));
		}

	}

	protected View getPersonalItem(String key, String value){

		ViewGroup personalItem = (ViewGroup)getLayoutInflater().inflate(
				R.layout.personal_view, null);

		TextView tvAttr = (TextView) personalItem.findViewById(R.id.tv_attribute);

		tvAttr.setText(key);

		TextView tvValue = (TextView) personalItem.findViewById(R.id.tv_value);
		tvValue.setText(value);

		contents.add(tvAttr);
		contents.add(tvValue);

		return personalItem;

	}

	protected void addReference(){

		ViewGroup referenceLayout = (ViewGroup) findViewById(R.id.txt_write_reference);

		if (resumeData.reference != null && !resumeData.reference.isEmpty()) {

			headings.add((TextView)findViewById(R.id.txt_heading_reference));

			TextView fullName, designation, workAddress, emailAddress, workPhone, personalPhone;
			LinearLayout refer;

			for (int i = 0; i < resumeData.reference.size(); i++) {

				refer = (LinearLayout) getLayoutInflater().inflate(
						R.layout.reference_view2, null);

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

				if (Common_Utilty
						.isNotEmptyString(resumeData.reference.get(i).name)) {
					fullName.setText(resumeData.reference.get(i).name);
				}

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

				HashMap<String, String> phones = new HashMap<String, String>();

				if (Common_Utilty
						.isNotEmptyString(resumeData.reference.get(i).workPhone)) {
					phones.put("Work Phone", resumeData.reference.get(i).workPhone);

				}

				if (Common_Utilty
						.isNotEmptyString(resumeData.reference.get(i).personalPhone)) {
					phones.put("Personal Phone",
							resumeData.reference.get(i).personalPhone);
				}

				if (phones.size() == 1) {
					String phone = "Contact : "
							+ phones.entrySet().iterator().next().getValue();
					workPhone.setText(phone);
					personalPhone.setVisibility(View.GONE);
				}

				else if (phones.size() == 2) {
					Iterator<Map.Entry<String, String>> itrator = phones.entrySet()
							.iterator();

					HashMap.Entry entry = itrator.next();
					String phone1 = entry.getKey() + " : " + entry.getValue();
					personalPhone.setText(phone1);

					HashMap.Entry entry2 = itrator.next();
					String phone2 = entry2.getKey() + " : " + entry2.getValue();
					workPhone.setText(phone2);
				} else {
					workPhone.setVisibility(View.GONE);
					personalPhone.setVisibility(View.GONE);
				}

				referenceLayout.addView(refer);
			}
		} else {
			ViewGroup referenceHead = (ViewGroup) findViewById(R.id.reference);
			referenceHead.setVisibility(View.GONE);
		}

	}

	protected void addDeclaration(){

		TextView declaration = (TextView) findViewById(R.id.txt_write_declaration);
		if (Common_Utilty.isNotEmptyString(resumeData.declaration)) {
			declaration.setText(resumeData.declaration);
			headings.add((TextView) findViewById(R.id.txt_heading_declaration));
			contents.add(declaration);
		} else {
			LinearLayout declarationLayout = (LinearLayout) findViewById(R.id.declaration);
			declarationLayout.setVisibility(View.GONE);

		}
		contents.add((TextView)findViewById(R.id.txt_date));
		contents.add((TextView)findViewById(R.id.txt_place));
		contents.add((TextView)findViewById(R.id.txt_signature));

	}

	protected View findViewById(int id) {
		return fragmentView.findViewById(id);
	}

	protected LayoutInflater getLayoutInflater() {
		return getActivity().getLayoutInflater();
	}

	
}
