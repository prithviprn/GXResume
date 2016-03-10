package com.grexoft.resume.templates;

import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.grexoft.resume.R;
import com.grexoft.resume.helpers.Common_Utilty;

import java.util.ArrayList;
import java.util.Locale;



@SuppressWarnings({"deprecation"})
public class StandOutTemplate extends TemplateFragment {

    private static final String FONTS_DIRECTORY_PATH = Common_Utilty.FONT_DIRECTORY + "/new/";

    private ArrayList<TextView> contentHeadings;
    @Override
    protected int getLayoutId() {
        return R.layout.template_sample8;
    }

    @Override
    protected void afterResumeHead() {
        titles.add(name);
        titles.add(title);
    }

    @Override
    protected void setFormatting() {
        super.setFormatting();
        Typeface headingTypeface = Typeface.createFromAsset(getActivity().getAssets(), FONTS_DIRECTORY_PATH + Common_Utilty.replacement(resumeData.getPreferences().getHeadingFont(), " ", "_") + ".ttf");
        for(TextView tv : contentHeadings){
            tv.setTypeface(headingTypeface,Typeface.BOLD);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, resumeData.getPreferences().getContentTextSize());
        }
    }

    @Override
    protected void addEducation() {

        contentHeadings = new ArrayList<TextView>();

        LinearLayout eduQualification = (LinearLayout) findViewById(R.id.layout_wtite_edu);

        if (resumeData.educationDetail != null && !resumeData.educationDetail.isEmpty()) {

            headings.add((TextView) findViewById(R.id.txt_heading_education));

            TextView tvDegree, tvPassout, tvUniversity, tvPercentage;
            LinearLayout eduDtl;
            for (int i = 0; i < resumeData.educationDetail.size(); i++) {
                eduDtl = (LinearLayout) getLayoutInflater().inflate(
                        R.layout.temp8_education_item, null);
                tvDegree = (TextView) eduDtl.findViewById(R.id.txt_degree);
                tvPassout = (TextView) eduDtl.findViewById(R.id.txt_passout);
                tvUniversity = (TextView) eduDtl
                        .findViewById(R.id.txt_university);
                tvPercentage = (TextView) eduDtl
                        .findViewById(R.id.txt_percentage);

                contentHeadings.add(tvDegree);
                contents.add(tvPassout);
                contentHeadings.add(tvUniversity);
                contents.add(tvPercentage);


                if (Common_Utilty.isNotEmptyString(resumeData.educationDetail
                        .get(i).degree)) {

                    tvDegree.setText(resumeData.educationDetail.get(i).degree
                            .toUpperCase(Locale.US));

                } else {
                    tvDegree.setVisibility(View.GONE);
                }

                if (Common_Utilty.isNotEmptyString(resumeData.educationDetail
                        .get(i).passout)) {
                    tvPassout.setText(resumeData.educationDetail.get(i).passout);

                } else {
                    tvPassout.setVisibility(View.GONE);
                }

                if (Common_Utilty.isNotEmptyString(resumeData.educationDetail
                        .get(i).university)) {
                    tvUniversity
                            .setText(resumeData.educationDetail.get(i).university
                                    .toUpperCase(Locale.US));
                } else {
                    tvUniversity.setVisibility(View.GONE);
                }

                if (Common_Utilty.isNotEmptyString(resumeData.educationDetail
                        .get(i).result)) {
                    tvPercentage.setText("CGPA/percentage : "
                            + resumeData.educationDetail.get(i).result);
                } else {
                    tvPercentage.setVisibility(View.GONE);
                }

                eduQualification.addView(eduDtl);
            }
        } else {
            LinearLayout eduLayout = (LinearLayout) findViewById(R.id.layout_edu_parent);
            eduLayout.setVisibility(View.GONE);
        }

    }

    @Override
    protected void addWorkExperience() {

        LinearLayout workExpDetails = (LinearLayout) findViewById(R.id.layout_wtite_exp);

        if (resumeData.workExperience != null && !resumeData.workExperience.isEmpty()) {

            headings.add((TextView) findViewById(R.id.txt_heading_work_experience));

            TextView tvCompany, tvJobDesig, tvWorkDate, tvWorkDescrip;
            LinearLayout workExp;
            for (int i = 0; i < resumeData.workExperience.size(); i++) {
                workExp = (LinearLayout) getLayoutInflater().inflate(
                        R.layout.temp8_workexp_item, null);
                tvCompany = (TextView) workExp
                        .findViewById(R.id.txt_company_name);
                tvWorkDate = (TextView) workExp
                        .findViewById(R.id.txt_work_date);
                tvJobDesig = (TextView) workExp
                        .findViewById(R.id.txt_job_design);
                tvWorkDescrip = (TextView) workExp
                        .findViewById(R.id.txt_job_description);

                contentHeadings.add(tvCompany);
                contents.add(tvWorkDate);
                contentHeadings.add(tvJobDesig);
                contents.add(tvWorkDescrip);

                if (Common_Utilty
                        .isNotEmptyString(resumeData.workExperience.get(i).compName)) {

                    tvCompany.setText(resumeData.workExperience.get(i).compName
                            .toUpperCase(Locale.US));
                } else {
                    tvCompany.setVisibility(View.GONE);
                }

                if (Common_Utilty
                        .isNotEmptyString(resumeData.workExperience.get(i).timeStart)
                        && Common_Utilty.isNotEmptyString(resumeData.workExperience
                        .get(i).timeEnd)) {
                    tvWorkDate.setText(resumeData.workExperience.get(i).timeStart
                            + " - " + resumeData.workExperience.get(i).timeEnd);
                } else {
                    tvWorkDate.setVisibility(View.GONE);

                }

                if (Common_Utilty
                        .isNotEmptyString(resumeData.workExperience.get(i).jobDesig)) {
                    tvJobDesig.setText(resumeData.workExperience.get(i).jobDesig
                            .toUpperCase(Locale.US));

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
        } else {
            LinearLayout workLayout = (LinearLayout) findViewById(R.id.layout_exp_parent);
            workLayout.setVisibility(View.GONE);
        }

    }

    @Override
    protected void addProjects() {

        LinearLayout projectDetails = (LinearLayout) findViewById(R.id.layout_wtite_projects);

        if (resumeData.project != null && !resumeData.project.isEmpty()) {

            headings.add((TextView) findViewById(R.id.txt_heading_education));

            TextView tvProjName, tvProjectdate, tvTeamSize, tvRole, tvDescrip;
            LinearLayout projectLayout;
            for (int i = 0; i < resumeData.project.size(); i++) {
                projectLayout = (LinearLayout) getLayoutInflater().inflate(
                        R.layout.temp8_project_item, null);
                tvProjName = (TextView) projectLayout
                        .findViewById(R.id.txt_heading_projects);

                tvProjectdate = (TextView) projectLayout
                        .findViewById(R.id.txt_date);

                tvTeamSize = (TextView) projectLayout
                        .findViewById(R.id.txt_team);

                tvRole = (TextView) projectLayout.findViewById(R.id.txt_role);

                tvDescrip = (TextView) projectLayout
                        .findViewById(R.id.txt_description);

                contentHeadings.add(tvProjName);
                contents.add(tvProjectdate);
                contents.add(tvTeamSize);
                contentHeadings.add(tvRole);
                contents.add(tvDescrip);


                if (Common_Utilty.isNotEmptyString(resumeData.project.get(i).title)) {

                    tvProjName.setText(resumeData.project.get(i).title
                            .toUpperCase(Locale.US));

                } else {
                    tvProjName.setVisibility(View.GONE);
                }

                if (Common_Utilty
                        .isNotEmptyString(resumeData.project.get(i).timebeg)
                        && Common_Utilty
                        .isNotEmptyString(resumeData.project.get(i).timeend)) {
                    tvProjectdate.setText(resumeData.project.get(i).timebeg + " - "
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
                    tvRole.setText(resumeData.project.get(i).role
                            .toUpperCase(Locale.US));

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
        } else {
            LinearLayout projectHeading = (LinearLayout) findViewById(R.id.layout_projects_parent);
            projectHeading.setVisibility(View.GONE);

        }
    }

    @Override
    protected void addResearch() {
        LinearLayout researchPaper = (LinearLayout) findViewById(R.id.layout_write_research_paper);

        if (resumeData.research != null && !resumeData.research.isEmpty()) {

            headings.add((TextView) findViewById(R.id.txt_heading_education));

            TextView tvPaperTitle, tvJournalName, tvVolume, tvIssue, tvDescrip;
            LinearLayout paperLayout;
            for (int i = 0; i < resumeData.research.size(); i++) {
                paperLayout = (LinearLayout) getLayoutInflater().inflate(
                        R.layout.temp8_research_item, null);
                tvPaperTitle = (TextView) paperLayout
                        .findViewById(R.id.txt_paper_title);

                tvJournalName = (TextView) paperLayout
                        .findViewById(R.id.txt_journal_name);

                tvVolume = (TextView) paperLayout.findViewById(R.id.txt_volume);

                tvIssue = (TextView) paperLayout.findViewById(R.id.txt_issue);

                tvDescrip = (TextView) paperLayout
                        .findViewById(R.id.txt_description);

                contentHeadings.add(tvPaperTitle);
                contentHeadings.add(tvJournalName);
                contents.add(tvVolume);
                contents.add(tvIssue);
                contents.add(tvDescrip);

                if (Common_Utilty
                        .isNotEmptyString(resumeData.research.get(i).papertitle)) {

                    tvPaperTitle.setText(resumeData.research.get(i).papertitle
                            .toUpperCase(Locale.US));

                } else {
                    tvPaperTitle.setVisibility(View.GONE);
                }

                if (Common_Utilty
                        .isNotEmptyString(resumeData.research.get(i).journal)) {
                    tvJournalName.setText(resumeData.research.get(i).journal
                            .toUpperCase(Locale.US));

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
        } else {
            LinearLayout paperHeading = (LinearLayout) findViewById(R.id.layout_research_paper_parent);
            paperHeading.setVisibility(View.GONE);

        }
    }

    @Override
    protected void addReference() {

        LinearLayout referenceLayout = (LinearLayout) findViewById(R.id.layout_write_reference);

        if (resumeData.reference != null && !resumeData.reference.isEmpty()) {

            headings.add((TextView) findViewById(R.id.txt_heading_research_paper));

            TextView fullName, designation, workAddress, emailAddress, workPhone, personalPhone;
            LinearLayout refer;

            for (int i = 0; i < resumeData.reference.size(); i++) {

                refer = (LinearLayout) getLayoutInflater().inflate(
                        R.layout.temp8_reference_item, null);

                fullName = (TextView) refer.findViewById(R.id.txt_full_name);
                designation = (TextView) refer
                        .findViewById(R.id.txt_title_designation);
                workAddress = (TextView) refer
                        .findViewById(R.id.txt_work_address);
                emailAddress = (TextView) refer
                        .findViewById(R.id.txt_email_address);
                workPhone = (TextView) refer.findViewById(R.id.txt_work_phone);
                personalPhone = (TextView) refer
                        .findViewById(R.id.txt_personal_phone);

                contentHeadings.add(fullName);
                contentHeadings.add(designation);
                contents.add(workAddress);
                contents.add(emailAddress);
                contents.add(personalPhone);
                contents.add(workPhone);


                if (Common_Utilty
                        .isNotEmptyString(resumeData.reference.get(i).name)) {

                    fullName.setText(resumeData.reference.get(i).name
                            .toUpperCase(Locale.US));

                } else {
                    fullName.setVisibility(View.GONE);
                }

                if (Common_Utilty
                        .isNotEmptyString(resumeData.reference.get(i).title)) {
                    designation.setText(resumeData.reference.get(i).title
                            .toUpperCase(Locale.US));

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
            LinearLayout referenceHead = (LinearLayout) findViewById(R.id.layout_reference_parent);
            referenceHead.setVisibility(View.GONE);
        }
    }

    //	private View fragmentView;
//
//	TextView name, phone, email, resumeTitle, skills, strengths, hobbies,
//			achievements, extraCurricular, objective, workExp, education,
//			project, reserch, personal, declaration, signature;
//
//	ImageView image, myImage;
//
//	float lineSpace = 5;
//
//	Typeface oswald, droidSerifRegular;
//
//	@Override
//	@Nullable
//	public View onCreateView(LayoutInflater inflater,
//			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//
//		fragmentView = inflater.inflate(R.layout.template_sample8, container,
//				false);
//
//		renderLayout();
//		setFormatting();
//
//		return fragmentView;
//	}
//
////	@Override
////	protected int getLayoutId() {
////		return R.layout.template_sample8;
////	}
//
//
//	//	resumePdf = new TemplatePdf8(getApplicationContext());
//
//		public void renderLayout(){
//
//		Resume resume = Resume.getInstance();
//
//
//		name = (TextView) findViewById(R.id.txt_name);
//		signature = (TextView) findViewById(R.id.txt_signature);
//		if (Common_Utilty.isNotEmptyString(resume.name)) {
//			SpannableString namestring = new SpannableString(
//					resume.name.toUpperCase(Locale.US));
//			namestring.setSpan(new StyleSpan(Typeface.BOLD), 0,
//					resume.name.length(), 0);
//			name.setText(namestring);
//			((TextViewWithFont) name).setSpacing(lineSpace);
//
//			signature.setText(resume.name.toUpperCase(Locale.US));
//			signature.setTypeface(droidSerifRegular);
//		} else {
//			name.setText("Your Name");
//			signature.setText("Your Signature");
//
//		}
//
//
//		image = (ImageView) findViewById(R.id.image_png);
//
//
//		if(resume.getPreferences().isShowImage() && Common_Utilty.isNotEmptyString(resume.image)){
//
//			image.setVisibility(View.VISIBLE);
//			image.setImageBitmap(SdCardManager.getImage(resume.image));
//
//		}
//		else {
//			image.setVisibility(View.GONE);
//
//		}
//
//		phone = (TextView) findViewById(R.id.txt_contact);
//		if (Common_Utilty.isNotEmptyString(resume.primarycontact)) {
//			phone.setText(resume.primarycontact);
//			phone.setTypeface(droidSerifRegular);
//		} else {
//			phone.setText("Phone");
//
//		}
//
//			contents.add(phone);
//
//		email = (TextView) findViewById(R.id.txt_email);
//		if (Common_Utilty.isNotEmptyString(resume.email)) {
//			email.setText(resume.email);
//			email.setTypeface(droidSerifRegular);
//		} else {
//			email.setText("E-mail Id");
//
//		}
//
//			contents.add(email);
//
//		resumeTitle = (TextView) findViewById(R.id.txt_title);
//		if (Common_Utilty.isNotEmptyString(resume.title) && resume.getPreferences().isShowTitle()) {
//			resumeTitle.setText(resume.title.toUpperCase(Locale.US));
//			resumeTitle.setTypeface(oswald);
//			((TextViewWithFont) resumeTitle).setSpacing(lineSpace);
//			titles.add(resumeTitle);
//		} else {
//			resumeTitle.setVisibility(View.GONE);
//
//		}
//
//
//
//		objective = (TextView) findViewById(R.id.txt_write_objective);
//		if (Common_Utilty.isNotEmptyString(resume.objectives)) {
//			objective.setText(resume.objectives);
//			objective.setTypeface(droidSerifRegular);
//			contents.add(objective);
//			headings.add((TextView)findViewById(R.id.txt_heading_objective));
//		} else {
//			LinearLayout objectLayout = (LinearLayout) findViewById(R.id.layout_objective_parent);
//			objectLayout.setVisibility(View.GONE);
//		}
//
//		LinearLayout skillLayout = (LinearLayout) findViewById(R.id.layout_write_skills);
//
//		if (resume.skills != null && !resume.skills.isEmpty()) {
//			TextView myskill = null;
//
//			for (int i = 0; i < resume.skills.size(); i++) {
//
//				myskill = (TextView) getLayoutInflater().inflate(
//						R.layout.template8_generic_item, null);
//
//				myskill.setText(resume.skills.get(i));
//
//				myskill.setTypeface(droidSerifRegular);
//
//				skillLayout.addView(myskill);
//
//			}
//			contents.add(myskill);
//			headings.add((TextView)findViewById(R.id.txt_heading_skills));
//		} else {
//			LinearLayout sklLayout = (LinearLayout) findViewById(R.id.layout_skills_parent);
//			sklLayout.setVisibility(View.GONE);
//		}
//
//		LinearLayout strengthLayout = (LinearLayout) findViewById(R.id.layout_write_strengths);
//
//		if (resume.strength != null && !resume.strength.isEmpty()) {
//			TextView mystrength = null;
//
//			for (int i = 0; i < resume.strength.size(); i++) {
//
//				mystrength = (TextView) getLayoutInflater().inflate(
//						R.layout.template8_generic_item, null);
//
//				mystrength.setText(resume.strength.get(i));
//
//				mystrength.setTypeface(droidSerifRegular);
//
//				strengthLayout.addView(mystrength);
//
//			}
//			contents.add(mystrength);
//			headings.add((TextView)findViewById(R.id.txt_heading_strengths));
//		} else {
//			LinearLayout strnthLayout = (LinearLayout) findViewById(R.id.layout_strengths_parent);
//			strnthLayout.setVisibility(View.GONE);
//		}
//
//		LinearLayout workExpDetails = (LinearLayout) findViewById(R.id.layout_wtite_exp);
//
//		if (resume.workExperience != null && !resume.workExperience.isEmpty()) {
//
//			headings.add((TextView)findViewById(R.id.txt_heading_work_experience));
//
//			TextView tvCompany, tvJobDesig, tvWorkDate, tvWorkDescrip;
//			LinearLayout workExp;
//			for (int i = 0; i < resume.workExperience.size(); i++) {
//				workExp = (LinearLayout) getLayoutInflater().inflate(
//						R.layout.temp8_workexp_item, null);
//				tvCompany = (TextView) workExp
//						.findViewById(R.id.txt_company_name);
//				tvWorkDate = (TextView) workExp
//						.findViewById(R.id.txt_work_date);
//				tvJobDesig = (TextView) workExp
//						.findViewById(R.id.txt_job_design);
//				tvWorkDescrip = (TextView) workExp
//						.findViewById(R.id.txt_job_description);
//
//				contents.add(tvCompany);
//				contents.add(tvWorkDate);
//				contents.add(tvJobDesig);
//				contents.add(tvWorkDescrip);
//
//				if (Common_Utilty
//						.isNotEmptyString(resume.workExperience.get(i).compName)) {
//
//					tvCompany.setText(resume.workExperience.get(i).compName
//							.toUpperCase(Locale.US));
//				} else {
//					tvCompany.setVisibility(View.GONE);
//				}
//
//				if (Common_Utilty
//						.isNotEmptyString(resume.workExperience.get(i).timeStart)
//						&& Common_Utilty.isNotEmptyString(resume.workExperience
//								.get(i).timeEnd)) {
//					tvWorkDate.setText(resume.workExperience.get(i).timeStart
//							+ " - " + resume.workExperience.get(i).timeEnd);
//					tvWorkDate.setTypeface(droidSerifRegular);
//				} else {
//					tvWorkDate.setVisibility(View.GONE);
//
//				}
//
//				if (Common_Utilty
//						.isNotEmptyString(resume.workExperience.get(i).jobDesig)) {
//					tvJobDesig.setText(resume.workExperience.get(i).jobDesig
//							.toUpperCase(Locale.US));
//
//				} else {
//					tvJobDesig.setVisibility(View.GONE);
//				}
//
//				if (Common_Utilty
//						.isNotEmptyString(resume.workExperience.get(i).description)) {
//					tvWorkDescrip.setText("Description : "
//							+ resume.workExperience.get(i).description);
//					tvWorkDescrip.setTypeface(droidSerifRegular);
//				} else {
//					tvWorkDescrip.setVisibility(View.GONE);
//				}
//
//				workExpDetails.addView(workExp);
//			}
//		} else {
//			LinearLayout workLayout = (LinearLayout) findViewById(R.id.layout_exp_parent);
//			workLayout.setVisibility(View.GONE);
//		}
//
//		LinearLayout eduQualification = (LinearLayout) findViewById(R.id.layout_wtite_edu);
//
//		if (resume.educationDetail != null && !resume.educationDetail.isEmpty()) {
//
//			headings.add((TextView)findViewById(R.id.txt_heading_education));
//
//			TextView tvDegree, tvPassout, tvUniversity, tvPercentage;
//			LinearLayout eduDtl;
//			for (int i = 0; i < resume.educationDetail.size(); i++) {
//				eduDtl = (LinearLayout) getLayoutInflater().inflate(
//						R.layout.temp8_education_item, null);
//				tvDegree = (TextView) eduDtl.findViewById(R.id.txt_degree);
//				tvPassout = (TextView) eduDtl.findViewById(R.id.txt_passout);
//				tvUniversity = (TextView) eduDtl
//						.findViewById(R.id.txt_university);
//				tvPercentage = (TextView) eduDtl
//						.findViewById(R.id.txt_percentage);
//
//				contents.add(tvDegree);
//				contents.add(tvPassout);
//				contents.add(tvUniversity);
//				contents.add(tvPercentage);
//
//
//				if (Common_Utilty.isNotEmptyString(resume.educationDetail
//						.get(i).degree)) {
//
//					tvDegree.setText(resume.educationDetail.get(i).degree
//							.toUpperCase(Locale.US));
//
//				} else {
//					tvDegree.setVisibility(View.GONE);
//				}
//
//				if (Common_Utilty.isNotEmptyString(resume.educationDetail
//						.get(i).passout)) {
//					tvPassout.setText(resume.educationDetail.get(i).passout);
//					tvPassout.setTypeface(droidSerifRegular);
//				} else {
//					tvPassout.setVisibility(View.GONE);
//				}
//
//				if (Common_Utilty.isNotEmptyString(resume.educationDetail
//						.get(i).university)) {
//					tvUniversity
//							.setText(resume.educationDetail.get(i).university
//									.toUpperCase(Locale.US));
//				} else {
//					tvUniversity.setVisibility(View.GONE);
//				}
//
//				if (Common_Utilty.isNotEmptyString(resume.educationDetail
//						.get(i).result)) {
//					tvPercentage.setText("CGPA/percentage : "
//							+ resume.educationDetail.get(i).result);
//					tvPercentage.setTypeface(droidSerifRegular);
//				} else {
//					tvPercentage.setVisibility(View.GONE);
//				}
//
//				eduQualification.addView(eduDtl);
//			}
//		} else {
//			LinearLayout eduLayout = (LinearLayout) findViewById(R.id.layout_edu_parent);
//			eduLayout.setVisibility(View.GONE);
//		}
//
//		LinearLayout projectDetails = (LinearLayout) findViewById(R.id.layout_wtite_projects);
//
//		if (resume.project != null && !resume.project.isEmpty()) {
//
//			headings.add((TextView)findViewById(R.id.txt_heading_education));
//
//			TextView tvProjName, tvProjectdate, tvTeamSize, tvRole, tvDescrip;
//			LinearLayout projectLayout;
//			for (int i = 0; i < resume.project.size(); i++) {
//				projectLayout = (LinearLayout) getLayoutInflater().inflate(
//						R.layout.temp8_project_item, null);
//				tvProjName = (TextView) projectLayout
//						.findViewById(R.id.txt_heading_projects);
//
//				tvProjectdate = (TextView) projectLayout
//						.findViewById(R.id.txt_date);
//
//				tvTeamSize = (TextView) projectLayout
//						.findViewById(R.id.txt_team);
//
//				tvRole = (TextView) projectLayout.findViewById(R.id.txt_role);
//
//				tvDescrip = (TextView) projectLayout
//						.findViewById(R.id.txt_description);
//
//				contents.add(tvProjName);
//				contents.add(tvProjectdate);
//				contents.add(tvTeamSize);
//				contents.add(tvRole);
//				contents.add(tvDescrip);
//
//
//				if (Common_Utilty.isNotEmptyString(resume.project.get(i).title)) {
//
//					tvProjName.setText(resume.project.get(i).title
//							.toUpperCase(Locale.US));
//
//				} else {
//					tvProjName.setVisibility(View.GONE);
//				}
//
//				if (Common_Utilty
//						.isNotEmptyString(resume.project.get(i).timebeg)
//						&& Common_Utilty
//								.isNotEmptyString(resume.project.get(i).timeend)) {
//					tvProjectdate.setText(resume.project.get(i).timebeg + " - "
//							+ resume.project.get(i).timeend);
//					tvProjectdate.setTypeface(droidSerifRegular);
//				} else {
//					tvProjectdate.setVisibility(View.GONE);
//				}
//
//				if (Common_Utilty.isNotEmptyString(resume.project.get(i).size)) {
//					tvTeamSize.setText("Team size : "
//							+ resume.project.get(i).size);
//					tvTeamSize.setTypeface(droidSerifRegular);
//				} else {
//					tvTeamSize.setVisibility(View.GONE);
//				}
//
//				if (Common_Utilty.isNotEmptyString(resume.project.get(i).role)) {
//					tvRole.setText(resume.project.get(i).role
//							.toUpperCase(Locale.US));
//
//				} else {
//					tvRole.setVisibility(View.GONE);
//				}
//
//				if (Common_Utilty
//						.isNotEmptyString(resume.project.get(i).description)) {
//					tvDescrip.setText("Description : "
//							+ resume.project.get(i).description);
//					tvDescrip.setTypeface(droidSerifRegular);
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
//			LinearLayout projectHeading = (LinearLayout) findViewById(R.id.layout_projects_parent);
//			projectHeading.setVisibility(View.GONE);
//
//		}
//
//		LinearLayout researchPaper = (LinearLayout) findViewById(R.id.layout_write_research_paper);
//
//		if (resume.research != null && !resume.research.isEmpty()) {
//
//			headings.add((TextView)findViewById(R.id.txt_heading_education));
//
//			TextView tvPaperTitle, tvJournalName, tvVolume, tvIssue, tvDescrip;
//			LinearLayout paperLayout;
//			for (int i = 0; i < resume.research.size(); i++) {
//				paperLayout = (LinearLayout) getLayoutInflater().inflate(
//						R.layout.temp8_research_item, null);
//				tvPaperTitle = (TextView) paperLayout
//						.findViewById(R.id.txt_paper_title);
//
//				tvJournalName = (TextView) paperLayout
//						.findViewById(R.id.txt_journal_name);
//
//				tvVolume = (TextView) paperLayout.findViewById(R.id.txt_volume);
//
//				tvIssue = (TextView) paperLayout.findViewById(R.id.txt_issue);
//
//				tvDescrip = (TextView) paperLayout
//						.findViewById(R.id.txt_description);
//
//				contents.add(tvPaperTitle);
//				contents.add(tvJournalName);
//				contents.add(tvVolume);
//				contents.add(tvIssue);
//				contents.add(tvDescrip);
//
//				if (Common_Utilty
//						.isNotEmptyString(resume.research.get(i).papertitle)) {
//
//					tvPaperTitle.setText(resume.research.get(i).papertitle
//							.toUpperCase(Locale.US));
//
//				} else {
//					tvPaperTitle.setVisibility(View.GONE);
//				}
//
//				if (Common_Utilty
//						.isNotEmptyString(resume.research.get(i).journal)) {
//					tvJournalName.setText(resume.research.get(i).journal
//							.toUpperCase(Locale.US));
//
//				} else {
//					tvJournalName.setVisibility(View.GONE);
//				}
//
//				if (Common_Utilty
//						.isNotEmptyString(resume.research.get(i).volume)) {
//					tvVolume.setText("Volume : "
//							+ resume.research.get(i).volume);
//					tvVolume.setTypeface(droidSerifRegular);
//				} else {
//					tvVolume.setVisibility(View.GONE);
//				}
//
//				if (Common_Utilty
//						.isNotEmptyString(resume.research.get(i).paperIssue)) {
//					tvIssue.setText("Issue : "
//							+ resume.research.get(i).paperIssue);
//					tvIssue.setTypeface(droidSerifRegular);
//				} else {
//					tvIssue.setVisibility(View.GONE);
//				}
//
//				if (Common_Utilty
//						.isNotEmptyString(resume.research.get(i).paperDescription)) {
//					tvDescrip.setText("Description : "
//							+ resume.research.get(i).paperDescription);
//					tvDescrip.setTypeface(droidSerifRegular);
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
//			LinearLayout paperHeading = (LinearLayout) findViewById(R.id.layout_research_paper_parent);
//			paperHeading.setVisibility(View.GONE);
//
//		}
//
//		LinearLayout personalLayout = (LinearLayout) findViewById(R.id.layout_personal_details_parent);
//
//		LinearLayout personalItem = null;
//
//			TextView tvAttr, tvValue;
//
//		if (Common_Utilty.isNotEmptyString(resume.name)) {
//
//			personalItem = (LinearLayout) getLayoutInflater().inflate(
//					R.layout.temp8_personal_item, null);
//
//			tvAttr = (TextView) personalItem.findViewById(R.id.txt_attribute);
//			tvValue = (TextView) personalItem.findViewById(R.id.txt_value);
//
//			tvAttr.setText("Name");
//
//			tvValue.setText(resume.name);
//
//			tvAttr.setTypeface(droidSerifRegular);
//
//			tvValue.setTypeface(droidSerifRegular);
//
//			contents.add(tvAttr);
//			contents.add(tvValue);
//
//			personalLayout.addView(personalItem);
//		}
//
//		if (resume.gender != null
//				&& !resume.gender.getString().equals(
//						Resume.Gender.NOT_SPECIFIED.getString())) {
//
//			personalItem = (LinearLayout) getLayoutInflater().inflate(
//					R.layout.temp8_personal_item, null);
//
//			tvAttr = (TextView) personalItem.findViewById(R.id.txt_attribute);
//			tvValue = (TextView) personalItem.findViewById(R.id.txt_value);
//
//			tvAttr.setText("Gender");
//
//			tvValue.setText(resume.gender.getString());
//
//			tvAttr.setTypeface(droidSerifRegular);
//
//			tvValue.setTypeface(droidSerifRegular);
//
//			contents.add(tvAttr);
//			contents.add(tvValue);
//
//			personalLayout.addView(personalItem);
//
//		}
//
//		if (Common_Utilty.isNotEmptyString(resume.dob)) {
//			personalItem = (LinearLayout) getLayoutInflater().inflate(
//					R.layout.temp8_personal_item, null);
//
//			tvAttr = (TextView) personalItem.findViewById(R.id.txt_attribute);
//			tvValue = (TextView) personalItem.findViewById(R.id.txt_value);
//
//			tvAttr.setText("Date of birth");
//
//			tvValue.setText(resume.dob);
//
//			tvAttr.setTypeface(droidSerifRegular);
//
//			tvValue.setTypeface(droidSerifRegular);
//
//			contents.add(tvAttr);
//			contents.add(tvValue);
//
//			personalLayout.addView(personalItem);
//		}
//
//		if (Common_Utilty.isNotEmptyString(resume.nationality)) {
//			personalItem = (LinearLayout) getLayoutInflater().inflate(
//					R.layout.temp8_personal_item, null);
//
//			tvAttr = (TextView) personalItem.findViewById(R.id.txt_attribute);
//			tvValue = (TextView) personalItem.findViewById(R.id.txt_value);
//
//			tvAttr.setText("Nationality");
//
//			tvValue.setText(resume.nationality);
//
//			tvAttr.setTypeface(droidSerifRegular);
//
//			tvValue.setTypeface(droidSerifRegular);
//
//			contents.add(tvAttr);
//			contents.add(tvValue);
//
//			personalLayout.addView(personalItem);
//		}
//
//		if (Common_Utilty.isNotEmptyString(resume.language)) {
//			personalItem = (LinearLayout) getLayoutInflater().inflate(
//					R.layout.temp8_personal_item, null);
//			tvAttr = (TextView) personalItem.findViewById(R.id.txt_attribute);
//			tvValue = (TextView) personalItem.findViewById(R.id.txt_value);
//
//			tvAttr.setText("Languages known");
//
//			tvValue.setText(resume.language);
//
//			tvAttr.setTypeface(droidSerifRegular);
//
//			tvValue.setTypeface(droidSerifRegular);
//
//			contents.add(tvAttr);
//			contents.add(tvValue);
//
//			personalLayout.addView(personalItem);
//		}
//		if (Common_Utilty.isNotEmptyString(resume.fatherName)) {
//			personalItem = (LinearLayout) getLayoutInflater().inflate(
//					R.layout.temp8_personal_item, null);
//
//			tvAttr = (TextView) personalItem.findViewById(R.id.txt_attribute);
//			tvValue = (TextView) personalItem.findViewById(R.id.txt_value);
//
//			tvAttr.setText("Father's name");
//
//			tvValue.setText(resume.fatherName);
//
//			tvAttr.setTypeface(droidSerifRegular);
//
//			tvValue.setTypeface(droidSerifRegular);
//
//			contents.add(tvAttr);
//			contents.add(tvValue);
//
//			personalLayout.addView(personalItem);
//		}
//		if (Common_Utilty.isNotEmptyString(resume.motherName)) {
//			personalItem = (LinearLayout) getLayoutInflater().inflate(
//					R.layout.temp8_personal_item, null);
//
//			tvAttr = (TextView) personalItem.findViewById(R.id.txt_attribute);
//			tvValue = (TextView) personalItem.findViewById(R.id.txt_value);
//
//			tvAttr.setText("Mother's name");
//
//			tvValue.setText(resume.motherName);
//
//			tvAttr.setTypeface(droidSerifRegular);
//
//			tvValue.setTypeface(droidSerifRegular);
//
//			contents.add(tvAttr);
//			contents.add(tvValue);
//
//			personalLayout.addView(personalItem);
//		}
//
//		String presentAddressStr = "";
//
//		if (Common_Utilty.isNotEmptyString(resume.city)) {
//			if (Common_Utilty.isNotEmptyString(resume.street)) {
//
//				presentAddressStr = presentAddressStr + resume.street + ",";
//
//			}
//			presentAddressStr = presentAddressStr + resume.city;
//			if (Common_Utilty.isNotEmptyString(resume.pincode)) {
//				presentAddressStr = presentAddressStr + "-" + resume.pincode;
//			}
//		}
//
//		if (Common_Utilty.isNotEmptyString(resume.city)
//				&& Common_Utilty.isNotEmptyString(resume.country)) {
//			presentAddressStr = presentAddressStr + ", ";
//		}
//
//		if (Common_Utilty.isNotEmptyString(resume.country)) {
//
//			if (Common_Utilty.isNotEmptyString(resume.state)) {
//				presentAddressStr = presentAddressStr + resume.state + ",";
//
//			}
//			presentAddressStr = presentAddressStr + resume.country;
//		}
//
//		if (resume.addressesAreSame) {
//
//			if (!presentAddressStr.equals("")) {
//				personalItem = (LinearLayout) getLayoutInflater().inflate(
//						R.layout.temp8_personal_item, null);
//
//				tvAttr = (TextView) personalItem.findViewById(R.id.txt_attribute);
//				tvValue = (TextView) personalItem.findViewById(R.id.txt_value);
//
//				tvAttr.setText("Address");
//
//				tvValue.setText(presentAddressStr);
//
//				tvAttr.setTypeface(droidSerifRegular);
//
//				tvValue.setTypeface(droidSerifRegular);
//
//				contents.add(tvAttr);
//				contents.add(tvValue);
//
//				personalLayout.addView(personalItem);
//			}
//
//		} else {
//
//			if (!presentAddressStr.equals("")) {
//				personalItem = (LinearLayout) getLayoutInflater().inflate(
//						R.layout.temp8_personal_item, null);
//
//				tvAttr = (TextView) personalItem.findViewById(R.id.txt_attribute);
//				tvValue = (TextView) personalItem.findViewById(R.id.txt_value);
//
//				tvAttr.setText("Present address");
//
//				tvValue.setText(presentAddressStr);
//
//				tvAttr.setTypeface(droidSerifRegular);
//
//				tvValue.setTypeface(droidSerifRegular);
//
//				contents.add(tvAttr);
//				contents.add(tvValue);
//
//				personalLayout.addView(personalItem);
//			}
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
//						R.layout.temp8_personal_item, null);
//
//				tvAttr = (TextView) personalItem.findViewById(R.id.txt_attribute);
//				tvValue = (TextView) personalItem.findViewById(R.id.txt_value);
//
//				tvAttr.setText("Permanent address");
//
//				tvValue.setText(permanentAddressStr);
//
//				tvAttr.setTypeface(droidSerifRegular);
//
//				tvValue.setTypeface(droidSerifRegular);
//
//				contents.add(tvAttr);
//				contents.add(tvValue);
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
//						R.layout.temp8_personal_item, null);
//
//				tvAttr = (TextView) personalItem.findViewById(R.id.txt_attribute);
//				tvValue = (TextView) personalItem.findViewById(R.id.txt_value);
//
//				tvAttr.setText(entry.getKey().toString());
//
//				tvValue.setText(entry.getValue().toString());
//
//				tvAttr.setTypeface(droidSerifRegular);
//
//				tvValue.setTypeface(droidSerifRegular);
//
//				contents.add(tvAttr);
//				contents.add(tvValue);
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
//			headings.add((TextView)findViewById(R.id.txt_heading_personal_details));
//
//		LinearLayout referenceLayout = (LinearLayout) findViewById(R.id.layout_write_reference);
//
//		if (resume.reference != null && !resume.reference.isEmpty()) {
//
//			headings.add((TextView)findViewById(R.id.txt_heading_research_paper));
//
//			TextView fullName, designation, workAddress, emailAddress, workPhone, personalPhone;
//			LinearLayout refer;
//
//			for (int i = 0; i < resume.reference.size(); i++) {
//
//				refer = (LinearLayout) getLayoutInflater().inflate(
//						R.layout.temp8_reference_item, null);
//
//				fullName = (TextView) refer.findViewById(R.id.txt_full_name);
//				designation = (TextView) refer
//						.findViewById(R.id.txt_title_designation);
//				workAddress = (TextView) refer
//						.findViewById(R.id.txt_work_address);
//				emailAddress = (TextView) refer
//						.findViewById(R.id.txt_email_address);
//				workPhone = (TextView) refer.findViewById(R.id.txt_work_phone);
//				personalPhone = (TextView) refer
//						.findViewById(R.id.txt_personal_phone);
//
//				contents.add(fullName);
//				contents.add(designation);
//				contents.add(workAddress);
//				contents.add(emailAddress);
//				contents.add(personalPhone);
//				contents.add(workPhone);
//
//
//
//				if (Common_Utilty
//						.isNotEmptyString(resume.reference.get(i).name)) {
//
//					fullName.setText(resume.reference.get(i).name
//							.toUpperCase(Locale.US));
//
//				} else {
//					fullName.setVisibility(View.GONE);
//				}
//
//				if (Common_Utilty
//						.isNotEmptyString(resume.reference.get(i).title)) {
//					designation.setText(resume.reference.get(i).title
//							.toUpperCase(Locale.US));
//
//				} else {
//					designation.setVisibility(View.GONE);
//				}
//
//				if (Common_Utilty
//						.isNotEmptyString(resume.reference.get(i).workAddress)) {
//					workAddress.setText("Work Address : "
//							+ resume.reference.get(i).workAddress);
//					workAddress.setTypeface(droidSerifRegular);
//				} else {
//					workAddress.setVisibility(View.GONE);
//				}
//
//				if (Common_Utilty
//						.isNotEmptyString(resume.reference.get(i).email)) {
//					emailAddress.setText("E-mail : "
//							+ resume.reference.get(i).email);
//					emailAddress.setTypeface(droidSerifRegular);
//				} else {
//					emailAddress.setVisibility(View.GONE);
//				}
//
//				if (Common_Utilty
//						.isNotEmptyString(resume.reference.get(i).workPhone)) {
//					workPhone.setText("Work Phone : "
//							+ resume.reference.get(i).workPhone);
//					workPhone.setTypeface(droidSerifRegular);
//				} else {
//					workPhone.setVisibility(View.GONE);
//				}
//
//				if (Common_Utilty
//						.isNotEmptyString(resume.reference.get(i).personalPhone)) {
//					personalPhone.setText("Personal Phone : "
//							+ resume.reference.get(i).personalPhone);
//					personalPhone.setTypeface(droidSerifRegular);
//				} else {
//					personalPhone.setVisibility(View.GONE);
//				}
//
//				referenceLayout.addView(refer);
//			}
//		} else {
//			LinearLayout referenceHead = (LinearLayout) findViewById(R.id.layout_reference_parent);
//			referenceHead.setVisibility(View.GONE);
//		}
//
//		if (resume.hobbies != null && !resume.hobbies.isEmpty()) {
//
//			headings.add((TextView)findViewById(R.id.txt_heading_hobbies));
//
//			LinearLayout hobbieLayout = (LinearLayout) findViewById(R.id.layout_write_hobbies);
//
//			LinearLayout hobbie ;
//
//			for (int i = 0; i < resume.hobbies.size(); i++) {
//
//				hobbie = (LinearLayout) getLayoutInflater().inflate(
//						R.layout.template8_generic_item_right, null);
//
//				((TextView) hobbie.findViewById(R.id.txt_generic_right))
//						.setText(resume.hobbies.get(i));
//
//				((TextView) hobbie.findViewById(R.id.txt_generic_right))
//						.setTypeface(droidSerifRegular);
//
//				contents.add(((TextView) hobbie.findViewById(R.id.txt_generic_right)));
//
//				hobbieLayout.addView(hobbie);
//			}
//
//		} else {
//			LinearLayout layoutHobbie = (LinearLayout) findViewById(R.id.layout_hobbies_parent);
//			layoutHobbie.setVisibility(View.GONE);
//		}
//
//		if (resume.achive != null && !resume.achive.isEmpty()) {
//
//			headings.add((TextView)findViewById(R.id.txt_heading_achievements));
//
//			LinearLayout achiveLayout = (LinearLayout) findViewById(R.id.layout_write_achievements);
//
//			LinearLayout achiv;
//
//			for (int i = 0; i < resume.achive.size(); i++) {
//
//
//
//				achiv = (LinearLayout) getLayoutInflater().inflate(
//						R.layout.template8_generic_item_right, null);
//
//				((TextView) achiv.findViewById(R.id.txt_generic_right))
//						.setText(resume.achive.get(i));
//
//				((TextView) achiv.findViewById(R.id.txt_generic_right))
//						.setTypeface(droidSerifRegular);
//
//				contents.add(((TextView) achiv.findViewById(R.id.txt_generic_right)));
//
//				achiveLayout.addView(achiv);
//			}
//
//		} else {
//			LinearLayout layoutAchiv = (LinearLayout) findViewById(R.id.layout_achievements_parent);
//			layoutAchiv.setVisibility(View.GONE);
//		}
//
//		if (resume.exCarr != null && !resume.exCarr.isEmpty()) {
//
//			headings.add((TextView)findViewById(R.id.txt_heading_extra_curricular));
//
//			LinearLayout extraLayout = (LinearLayout) findViewById(R.id.layout_write_extra_curricular);
//
//			LinearLayout extraCurr;
//
//			for (int i = 0; i < resume.exCarr.size(); i++) {
//
//				extraCurr = (LinearLayout) getLayoutInflater().inflate(
//						R.layout.template8_generic_item_right, null);
//
//				((TextView) extraCurr.findViewById(R.id.txt_generic_right))
//						.setText(resume.exCarr.get(i));
//
//				((TextView) extraCurr.findViewById(R.id.txt_generic_right))
//						.setTypeface(droidSerifRegular);
//
//				contents.add(((TextView) extraCurr.findViewById(R.id.txt_generic_right)));
//
//				extraLayout.addView(extraCurr);
//			}
//
//		} else {
//			LinearLayout layoutExtra = (LinearLayout) findViewById(R.id.layout_extra_curricular_parent);
//			layoutExtra.setVisibility(View.GONE);
//		}
//
//			headings.add((TextView)findViewById(R.id.txt_heading_declaration));
//
//		declaration = (TextView) findViewById(R.id.txt_write_declaration);
//		if (Common_Utilty.isNotEmptyString(resume.declaration)) {
//			declaration.setText(resume.declaration);
//			declaration.setTypeface(droidSerifRegular);
//			contents.add(declaration);
//		} else {
//			LinearLayout declarationLayout = (LinearLayout) findViewById(R.id.layout_declaration_parent);
//			declarationLayout.setVisibility(View.GONE);
//		}
//
//			contents.add((TextView)findViewById(R.id.txt_date));
//			contents.add((TextView)findViewById(R.id.txt_place));
//			contents.add((TextView)findViewById(R.id.txt_signature));
//
//	}
//


}
