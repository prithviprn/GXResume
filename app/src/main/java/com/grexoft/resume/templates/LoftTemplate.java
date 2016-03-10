package com.grexoft.resume.templates;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.grexoft.resume.R;
import com.grexoft.resume.helpers.Common_Utilty;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class LoftTemplate extends TemplateFragment {

	@Override
	protected int getLayoutId() {
		return R.layout.template_sample7;
	}

	@Override
	protected void afterResumeHead() {
		titles.add(name);
		headings.add(title);
	}

	@Override
	protected void addWorkExperience() {
		LinearLayout workExper = (LinearLayout) findViewById(R.id.txt_write_experience);
		if (resumeData.workExperience != null && !resumeData.workExperience.isEmpty()) {

			headings.add((TextView)findViewById(R.id.txt_heading_work_experience));

			LinearLayout tvExp;
			TextView compName, workDate, jobDesig, descrip;
			for (int i = 0; i < resumeData.workExperience.size(); i++) {

				tvExp = (LinearLayout) getLayoutInflater().inflate(
						R.layout.temp7_experience_item, null);

				compName = (TextView) tvExp.findViewById(R.id.txt_compName);
				workDate = (TextView) tvExp.findViewById(R.id.txt_working_date);
				jobDesig = (TextView) tvExp.findViewById(R.id.txt_designation);
				descrip = (TextView) tvExp.findViewById(R.id.txt_work_description);

				contents.add(compName);
				contents.add(workDate);
				contents.add(jobDesig);
				contents.add(descrip);

				compName.setText(resumeData.workExperience.get(i).compName);

				if (Common_Utilty
						.isNotEmptyString(resumeData.workExperience.get(i).timeStart)
						&& Common_Utilty.isNotEmptyString(resumeData.workExperience
						.get(i).timeEnd)) {
					workDate.setText(resumeData.workExperience.get(i).timeStart
							+ "-" + resumeData.workExperience.get(i).timeEnd);
				} else {
					workDate.setVisibility(View.GONE);
				}

				if (Common_Utilty
						.isNotEmptyString(resumeData.workExperience.get(i).jobDesig)) {
					jobDesig.setText("Designation : "
							+ resumeData.workExperience.get(i).jobDesig);
				} else {
					jobDesig.setVisibility(View.GONE);
				}

				if (Common_Utilty
						.isNotEmptyString(resumeData.workExperience.get(i).description)) {
					descrip.setText("Description : "
							+ resumeData.workExperience.get(i).description);
				} else {
					descrip.setVisibility(View.GONE);
				}
				workExper.addView(tvExp);

			}
		} else {
			LinearLayout expLayout = (LinearLayout) findViewById(R.id.work_experience);
			expLayout.setVisibility(View.GONE);
		}
	}

	@Override
	protected void addProjects() {
		LinearLayout projectDetail = (LinearLayout) findViewById(R.id.txt_write_projects);
		if (resumeData.project != null && !resumeData.project.isEmpty()) {

			headings.add((TextView)findViewById(R.id.txt_heading_projects));
			LinearLayout projDetail;
			TextView projTitle, projDate, projRole, projTeam, descrip;

			for (int i = 0; i < resumeData.project.size(); i++) {

				projDetail = (LinearLayout) getLayoutInflater().inflate(
						R.layout.temp7_project_item, null);

				projTitle = (TextView) projDetail
						.findViewById(R.id.txt_projects_title);
				projDate = (TextView) projDetail
						.findViewById(R.id.txt_time_period);
				projRole = (TextView) projDetail.findViewById(R.id.txt_role);
				projTeam = (TextView) projDetail
						.findViewById(R.id.txt_team_size);
				descrip = (TextView) projDetail
						.findViewById(R.id.txt_project_description);

				contents.add(projTitle);
				contents.add(projDate);
				contents.add(projRole);
				contents.add(projTeam);
				contents.add(descrip);


				projTitle.setText(resumeData.project.get(i).title);

				if (Common_Utilty
						.isNotEmptyString(resumeData.project.get(i).timebeg)
						&& Common_Utilty
						.isNotEmptyString(resumeData.project.get(i).timeend)) {
					projDate.setText(resumeData.project.get(i).timebeg + "-"
							+ resumeData.project.get(i).timeend);
				} else {
					projDate.setVisibility(View.GONE);
				}

				if (Common_Utilty.isNotEmptyString(resumeData.project.get(i).role)) {
					projRole.setText("Role : " + resumeData.project.get(i).role);
				} else {
					projRole.setVisibility(View.GONE);
				}

				if (Common_Utilty.isNotEmptyString(resumeData.project.get(i).size)) {
					projTeam.setText("Team Size : "
							+ resumeData.project.get(i).size);
				} else {
					projTeam.setVisibility(View.GONE);
				}

				if (Common_Utilty
						.isNotEmptyString(resumeData.project.get(i).description)) {
					descrip.setText("Description : "
							+ resumeData.project.get(i).description);
				} else {
					descrip.setVisibility(View.GONE);
				}
				projectDetail.addView(projDetail);
			}
		} else {
			LinearLayout projLayout = (LinearLayout) findViewById(R.id.projects);
			projLayout.setVisibility(View.GONE);
		}
	}

	@Override
	protected void addResearch() {
		LinearLayout researchPaper = (LinearLayout) findViewById(R.id.txt_write_papers);

		if (resumeData.research != null && !resumeData.research.isEmpty()) {

			headings.add((TextView)findViewById(R.id.txt_heading_research_paper));
			LinearLayout researchPapers;
			TextView paperTitle, journalName, volume, issue, descrip;

			for (int i = 0; i < resumeData.research.size(); i++) {

				researchPapers = (LinearLayout) getLayoutInflater().inflate(
						R.layout.temp7_research_item, null);

				paperTitle = (TextView) researchPapers
						.findViewById(R.id.txt_paper_title);
				journalName = (TextView) researchPapers
						.findViewById(R.id.txt_journal_name);
				volume = (TextView) researchPapers
						.findViewById(R.id.txt_volume);
				issue = (TextView) researchPapers.findViewById(R.id.txt_issue);
				descrip = (TextView) researchPapers
						.findViewById(R.id.txt_paper_description);

				contents.add(paperTitle);
				contents.add(journalName);
				contents.add(volume);
				contents.add(issue);
				contents.add(descrip);

				paperTitle.setText(resumeData.research.get(i).papertitle);

				if (Common_Utilty
						.isNotEmptyString(resumeData.research.get(i).journal)) {
					journalName.setText(resumeData.research.get(i).journal);
				} else {
					journalName.setVisibility(View.GONE);
				}

				if (Common_Utilty
						.isNotEmptyString(resumeData.research.get(i).volume)) {
					volume.setText("Volume : " + resumeData.research.get(i).volume);
				} else {
					volume.setVisibility(View.GONE);
				}

				if (Common_Utilty
						.isNotEmptyString(resumeData.research.get(i).paperIssue)) {
					issue.setText("Issue : "
							+ resumeData.research.get(i).paperIssue);
				} else {
					issue.setVisibility(View.GONE);
				}

				if (Common_Utilty
						.isNotEmptyString(resumeData.research.get(i).paperDescription)) {
					descrip.setText("Description : "
							+ resumeData.research.get(i).paperDescription);
				} else {
					descrip.setVisibility(View.GONE);
				}
				researchPaper.addView(researchPapers);
			}

		} else {
			LinearLayout paperLayout = (LinearLayout) findViewById(R.id.research_paper);
			paperLayout.setVisibility(View.GONE);
		}
	}

	@Override
	protected void addReference() {
		LinearLayout reference = (LinearLayout) findViewById(R.id.txt_write_reference);

		if (resumeData.reference != null && !resumeData.reference.isEmpty()) {

			headings.add((TextView)findViewById(R.id.txt_heading_reference));
			LinearLayout refer;

			TextView fullName, designation, workAddress, emailAddress, workPhone, personalPhone;

			for (int i = 0; i < resumeData.reference.size(); i++) {

				refer = (LinearLayout) getLayoutInflater().inflate(
						R.layout.temp7_reference_item, null);

				fullName = (TextView) refer.findViewById(R.id.txt_full_name);
				designation = (TextView) refer
						.findViewById(R.id.txt_title_designation);
				workPhone = (TextView) refer.findViewById(R.id.txt_work_phone);
				personalPhone = (TextView) refer
						.findViewById(R.id.txt_personal_phone);
				emailAddress = (TextView) refer
						.findViewById(R.id.txt_email_address);
				workAddress = (TextView) refer
						.findViewById(R.id.txt_work_address);

				contents.add(fullName);
				contents.add(designation);
				contents.add(workPhone);
				contents.add(emailAddress);
				contents.add(workAddress);

				fullName.setText(resumeData.reference.get(i).name);

				if (Common_Utilty
						.isNotEmptyString(resumeData.reference.get(i).title)) {
					designation.setText(resumeData.reference.get(i).title);
				} else {
					designation.setVisibility(View.GONE);
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
					Iterator<Entry<String, String>> itrator = phones.entrySet()
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

				if (Common_Utilty
						.isNotEmptyString(resumeData.reference.get(i).email)) {
					emailAddress.setText("E-mail : "
							+ resumeData.reference.get(i).email);
				} else {
					emailAddress.setVisibility(View.GONE);
				}

				if (Common_Utilty
						.isNotEmptyString(resumeData.reference.get(i).workAddress)) {
					workAddress.setText("Work Address : "
							+ resumeData.reference.get(i).workAddress);
				} else {
					workAddress.setVisibility(View.GONE);
				}

				reference.addView(refer);

			}
		} else {
			LinearLayout referenceHead = (LinearLayout) findViewById(R.id.reference);
			referenceHead.setVisibility(View.GONE);
		}
	}

}
