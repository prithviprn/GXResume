package com.grexoft.resume;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.grexoft.resume.helpers.DBHelper;
import com.grexoft.resume.model.EducationalDetail;
import com.grexoft.resume.model.Exp;
import com.grexoft.resume.model.Project;
import com.grexoft.resume.model.Reference;
import com.grexoft.resume.model.Research;

public class Resume {

	public Preferences preferences;

	public enum Gender {

		NOT_SPECIFIED(0, "Not specified"), MALE(1, "Male"), FEMALE(2, "Female");

		private int value;

		private String string;

		private Gender(int value, String string) {

			this.value = value;
			this.string = string;

		}

		public int getValue() {
			return value;
		}

		public String getString() {
			return string;
		}

	}

	public Gender gender;

	public int resumeId = -1;

	public String dob, nationality, language, fatherName, motherName,
			date_of_creation;

	public String image;
	
	public boolean showImage=true;

	public String name, primarycontact, email, street, city, pincode, state,
			country;

	public String street2, city2, pincode2, state2, country2;

	public String objectives;

	public String declaration;

	public Boolean addressesAreSame = true;

	// Boolean isMale = true;

	public ArrayList<EducationalDetail> educationDetail;

	public ArrayList<Exp> workExperience;

	public ArrayList<Project> project;

	public ArrayList<Research> research;

	public ArrayList<Reference> reference;

	public ArrayList<String> skills;

	public ArrayList<String> hobbies;

	public ArrayList<String> achive;

	public ArrayList<String> exCarr;

	public ArrayList<String> strength;

	public HashMap<String, String> customFields;

	private static Resume object;

	public DBHelper dbHelper;

	private boolean editedForms[] = new boolean[15];

	public String title,password;

	public boolean showTitle=true;

	public static final int CONTACT_FORM = 0;

	public static final int PERSONAL_FORM = 1;
	
	public static final int IMAGE_FORM = 2;

	public static final int OBJECTIVE_FORM = 3;

	public static final int EDUCATIONAL_FORM = 4;

	public static final int WORK_EXPERIENCE_FORM = 5;

	public static final int PROJECTS_FORM = 6;

	public static final int RESEARCH_FORM = 7;

	public static final int SKILLS_FORM = 8;

	public static final int EXTRA_CURRICULAR_FORM = 9;

	public static final int HOBBIES_FORM = 10;

	public static final int ACHEIVEMENTS_FORM = 11;

	public static final int STRENGTH_FORM = 12;
	
	public static final int REFERENCE_FORM = 13;

	public static final int DECLARATION_FORM = 14;

	public void initializePreferences(Context context){
		preferences = new Preferences(context);
	}

	public static Resume getInstance() {
		if (object != null) {
			return object;
		} else {
			object = new Resume();
			return object;
		}

	}

	public static Resume getNewInstance() {
		object = new Resume();
		return object;
	}

	public void checkForm(int formIndex) {

		if (formIndex < editedForms.length) {
			editedForms[formIndex] = true;
		}

	}

	public void synchDatabase() {

		for (int i = 0; i < editedForms.length; i++) {

			if (editedForms[i]) {
				saveForm(i);
				editedForms[i] = false;
			}

		}

	}

	public Preferences getPreferences() {
		return preferences;
	}

	public void getResumeInfo() {
		educationDetail = dbHelper.getEducationalDetails(resumeId);
		workExperience = dbHelper.getWorkExperiences(resumeId);
		project = dbHelper.getProjectDetails(resumeId);
		research = dbHelper.getResearchDetails(resumeId);
		skills = dbHelper.getRowsFromTable(DBHelper.SKILLS, resumeId);
		hobbies = dbHelper.getRowsFromTable(DBHelper.HOBBIES, resumeId);
		achive = dbHelper.getRowsFromTable(DBHelper.ACHEIVEMENT, resumeId);
		exCarr = dbHelper.getRowsFromTable(DBHelper.EXTRA_CURRICULAR, resumeId);
		strength = dbHelper.getRowsFromTable(DBHelper.STRENGTH, resumeId);
		customFields = dbHelper.getCustomField(resumeId);
		reference = dbHelper.getReference(resumeId);
		
		
		Bundle primaryInfo = dbHelper.getPrimaryInfo(resumeId);
		name = primaryInfo.getString("name");
		email = primaryInfo.getString("email");
		primarycontact = primaryInfo.getString("primary_contact");
		street = primaryInfo.getString("street");
		city = primaryInfo.getString("city");
		pincode = primaryInfo.getString("pincode");
		state = primaryInfo.getString("state");
		country = primaryInfo.getString("country");
		street2 = primaryInfo.getString("street1");
		city2 = primaryInfo.getString("city1");
		pincode2 = primaryInfo.getString("pincode1");
		state2 = primaryInfo.getString("state1");
		country2 = primaryInfo.getString("country1");
		dob = primaryInfo.getString("dob");
		image = primaryInfo.getString("image");

		System.out.println("image primary info: " + image);

		date_of_creation = primaryInfo.getString("date_of_creation");

		setGenderByString(primaryInfo.getString("gendar"));

		nationality = primaryInfo.getString("nationality");
		fatherName = primaryInfo.getString("father_name");
		motherName = primaryInfo.getString("mother_name");
		language = primaryInfo.getString("language");
		objectives = primaryInfo.getString("objectives");
		declaration = primaryInfo.getString("declaration");
		addressesAreSame = primaryInfo.getInt("addresses_are_same") == 1 ? true
				: false;
		title = primaryInfo.getString("title");
		password=primaryInfo.getString("password");

	}

	public void saveForm(int formIndex) {

		switch (formIndex) {

		case CONTACT_FORM:

			HashMap<String, String> contactMap = new HashMap<String, String>();

			contactMap.put("title", title);
			contactMap.put("password", password);
			contactMap.put("name", name);
			contactMap.put("email_id", email);
			contactMap.put("contact_number", primarycontact);
			contactMap.put("correspondence_address_street", street);
			contactMap.put("correspondence_address_city", city);
			contactMap.put("correspondence_address_pincode", pincode);
			contactMap.put("correspondence_address_state", state);
			contactMap.put("correspondence_address_country", country);

			if (addressesAreSame) {
				contactMap.put("addresses_are_same", String.valueOf(1));
				contactMap.put("permanent_address_street", street);
				contactMap.put("permanent_address_city", city);
				contactMap.put("permanent_address_pincode", pincode);
				contactMap.put("permanent_address_state", state);
				contactMap.put("permanent_address_country", country);

			}

			else {
				contactMap.put("addresses_are_same", String.valueOf(0));
				contactMap.put("permanent_address_street", street2);
				contactMap.put("permanent_address_city", city2);
				contactMap.put("permanent_address_pincode", pincode2);
				contactMap.put("permanent_address_state", state2);
				contactMap.put("permanent_address_country", country2);
			}

			if (resumeId < 0) {

				dbHelper.insertRowIntoTable(DBHelper.RESUMES, contactMap,
						resumeId);

				resumeId = dbHelper.getLastInsertResumeId();

			}

			else {

				dbHelper.modifyTableRow(DBHelper.RESUMES, contactMap, resumeId);
			}

			break;

		case PERSONAL_FORM:

			HashMap<String, String> personalMap = new HashMap<String, String>();
			personalMap.put("date_of_birth", dob);
			personalMap.put("gender", gender.getString());
			System.out.println("gender is " + gender.getString());
			personalMap.put("nationality", nationality);
			personalMap.put("languages_known", language);
			personalMap.put("father_name", fatherName);
			personalMap.put("mother_name", motherName);

			if (resumeId < 0) {

				dbHelper.insertRowIntoTable(DBHelper.RESUMES, personalMap,
						resumeId);

				resumeId = dbHelper.getLastInsertResumeId();

			} else {
				dbHelper.modifyTableRow(DBHelper.RESUMES, personalMap, resumeId);
			}

			dbHelper.deleteRowsFromTable(DBHelper.CUSTOM_FIELDS, resumeId);

			if (customFields != null && !customFields.isEmpty()) {

				HashMap<String, String> customFieldsMap;

				for (HashMap.Entry<String, String> entry : customFields
						.entrySet()) {

					customFieldsMap = new HashMap<String, String>();

					customFieldsMap
							.put("field_name", entry.getKey().toString());

					customFieldsMap.put("field_value", entry.getValue()
							.toString());

					dbHelper.insertRowIntoTable(DBHelper.CUSTOM_FIELDS,
							customFieldsMap, resumeId);
				}
			}

			break;

		case IMAGE_FORM:

			HashMap<String, String> imageMap = new HashMap<String, String>();

			imageMap.put("image", image);

			if (resumeId < 0) {

				dbHelper.insertRowIntoTable(DBHelper.RESUMES, imageMap,
						resumeId);

				resumeId = dbHelper.getLastInsertResumeId();

			}

			else {

				dbHelper.modifyTableRow(DBHelper.RESUMES, imageMap, resumeId);
			}
			break;

		case OBJECTIVE_FORM:

			HashMap<String, String> objectiveMap = new HashMap<String, String>();

			objectiveMap.put("objective", objectives);

			if (resumeId < 0) {

				dbHelper.insertRowIntoTable(DBHelper.RESUMES, objectiveMap,
						resumeId);

				resumeId = dbHelper.getLastInsertResumeId();
			} else {
				dbHelper.modifyTableRow(DBHelper.RESUMES, objectiveMap,
						resumeId);
			}
			break;

		case EDUCATIONAL_FORM:

			dbHelper.deleteRowsFromTable(DBHelper.EDUCATIONAL_QUALIFICATION,
					resumeId);

			HashMap<String, String> educationMap;

			for (EducationalDetail educationalDetailItem : educationDetail) {

				educationMap = new HashMap<String, String>();

				educationMap.put("degree", educationalDetailItem.degree);
				educationMap
						.put("university", educationalDetailItem.university);
				educationMap.put("year_of_passing",
						educationalDetailItem.passout);
				educationMap.put("result", educationalDetailItem.result);

				dbHelper.insertRowIntoTable(DBHelper.EDUCATIONAL_QUALIFICATION,
						educationMap, resumeId);
			}

			break;

		case PROJECTS_FORM:

			dbHelper.deleteRowsFromTable(DBHelper.PROJECT_DETAIL, resumeId);

			HashMap<String, String> projectMap;

			for (Project projectDetailItem : project) {

				projectMap = new HashMap<String, String>();

				projectMap.put("project_title", projectDetailItem.title);
				projectMap.put("start_date", projectDetailItem.timebeg);
				projectMap.put("end_date", projectDetailItem.timeend);
				projectMap.put("role", projectDetailItem.role);
				projectMap.put("team_size", projectDetailItem.size);
				projectMap.put("project_description",
						projectDetailItem.description);

				dbHelper.insertRowIntoTable(DBHelper.PROJECT_DETAIL,
						projectMap, resumeId);
			}
			break;

		case RESEARCH_FORM:

			dbHelper.deleteRowsFromTable(DBHelper.RESEARCH_DETAIL, resumeId);

			HashMap<String, String> researchMap;

			for (Research researchDetailItem : research) {

				researchMap = new HashMap<String, String>();

				researchMap.put("paper_title", researchDetailItem.papertitle);
				researchMap.put("paper_description",
						researchDetailItem.paperDescription);
				researchMap.put("volume", researchDetailItem.volume);
				researchMap.put("issue", researchDetailItem.paperIssue);
				researchMap.put("journal", researchDetailItem.journal);

				dbHelper.insertRowIntoTable(DBHelper.RESEARCH_DETAIL,
						researchMap, resumeId);
			}
			break;

		case WORK_EXPERIENCE_FORM:

			dbHelper.deleteRowsFromTable(DBHelper.WORK_EXPERIENCE, resumeId);

			HashMap<String, String> experienceMap;

			for (Exp workExperienceItem : workExperience) {

				experienceMap = new HashMap<String, String>();

				experienceMap.put("company_name", workExperienceItem.compName);
				experienceMap
						.put("job_dsignation", workExperienceItem.jobDesig);
				experienceMap.put("start_year", workExperienceItem.timeStart);
				experienceMap.put("end_year", workExperienceItem.timeEnd);
				experienceMap.put("job_description",
						workExperienceItem.description);

				dbHelper.insertRowIntoTable(DBHelper.WORK_EXPERIENCE,
						experienceMap, resumeId);

			}
			break;

		case SKILLS_FORM:

			dbHelper.deleteRowsFromTable(DBHelper.SKILLS, resumeId);

			HashMap<String, String> skillsMap;
			for (String skillsItem : skills) {

				skillsMap = new HashMap<String, String>();

				skillsMap.put("skills", skillsItem);

				dbHelper.insertRowIntoTable(DBHelper.SKILLS, skillsMap,
						resumeId);
			}

			break;

		case HOBBIES_FORM:

			dbHelper.deleteRowsFromTable(DBHelper.HOBBIES, resumeId);

			HashMap<String, String> hobbiesMap;

			for (String hobbiesItem : hobbies) {

				hobbiesMap = new HashMap<String, String>();

				hobbiesMap.put("hobbies", hobbiesItem);

				dbHelper.insertRowIntoTable(DBHelper.HOBBIES, hobbiesMap,
						resumeId);
			}
			break;

		case ACHEIVEMENTS_FORM:

			dbHelper.deleteRowsFromTable(DBHelper.ACHEIVEMENT, resumeId);

			HashMap<String, String> acheivementMap;

			for (String acheivementItem : achive) {

				acheivementMap = new HashMap<String, String>();

				acheivementMap.put("acheivements", acheivementItem);

				dbHelper.insertRowIntoTable(DBHelper.ACHEIVEMENT,
						acheivementMap, resumeId);
			}
			break;

		case EXTRA_CURRICULAR_FORM:

			dbHelper.deleteRowsFromTable(DBHelper.EXTRA_CURRICULAR, resumeId);

			HashMap<String, String> extraCurricularMap;

			for (String extraCurricularItem : exCarr) {

				extraCurricularMap = new HashMap<String, String>();

				extraCurricularMap.put("extra_curricular", extraCurricularItem);

				dbHelper.insertRowIntoTable(DBHelper.EXTRA_CURRICULAR,
						extraCurricularMap, resumeId);
			}
			break;

		case STRENGTH_FORM:

			dbHelper.deleteRowsFromTable(DBHelper.STRENGTH, resumeId);

			HashMap<String, String> strengthMap;

			for (String strengthItem : strength) {

				strengthMap = new HashMap<String, String>();

				strengthMap.put("strength", strengthItem);

				dbHelper.insertRowIntoTable(DBHelper.STRENGTH, strengthMap,
						resumeId);
			}
			break;
			
		case REFERENCE_FORM:

			dbHelper.deleteRowsFromTable(DBHelper.REFERENCE, resumeId);

			HashMap<String, String> referenceMap;

			for (Reference refItem : reference) {

				referenceMap = new HashMap<String, String>();

				referenceMap.put("name", refItem.name);
				referenceMap.put("job_dsignation", refItem.title);
				referenceMap.put("address", refItem.workAddress);
				referenceMap.put("email", refItem.email);
				referenceMap.put("work_phone", refItem.workPhone);
				referenceMap.put("personal_phone", refItem.personalPhone);

				dbHelper.insertRowIntoTable(DBHelper.REFERENCE, referenceMap,
						resumeId);

			}
			break;
			
		case DECLARATION_FORM:

			HashMap<String, String> declarationMap = new HashMap<String, String>();

			declarationMap.put("declaration", declaration);

			if (resumeId < 0) {

				dbHelper.insertRowIntoTable(DBHelper.RESUMES, declarationMap,
						resumeId);

				resumeId = dbHelper.getLastInsertResumeId();
			} else {
				dbHelper.modifyTableRow(DBHelper.RESUMES, declarationMap,
						resumeId);
			}
			break;

		

		}

	}

	public void setGenderByPosition(int position) {
		if (position >= 0 && position <= 2) {
			if (position == Resume.Gender.MALE.getValue()) {
				gender = Resume.Gender.MALE;
			} else if (position == Resume.Gender.FEMALE.getValue()) {
				gender = Resume.Gender.FEMALE;
			} else if (position == Resume.Gender.NOT_SPECIFIED.getValue()) {
				gender = Resume.Gender.NOT_SPECIFIED;
			}else{
				gender = Resume.Gender.NOT_SPECIFIED;
			}
		}

	}

	public void setGenderByString(String stringValue) {
		if (stringValue != null) {
			if (stringValue.equals(Resume.Gender.MALE.getString())) {
				gender = Resume.Gender.MALE;
			} else if (stringValue.equals(Resume.Gender.FEMALE.getString())) {
				gender = Resume.Gender.FEMALE;
			} else if (stringValue.equals(Resume.Gender.NOT_SPECIFIED
					.getString())) {
				gender = Resume.Gender.NOT_SPECIFIED;
			}
		} else {
			gender = Resume.Gender.NOT_SPECIFIED;
		}
	}

	public class Preferences{

		private static final String SHOW_IMAGE_KEY = "show_image";

		private static final String SHOW_TITLE_KEY = "show_title";

		private static final String TITLE_FONT_KEY = "title_font";

		private static final String CONTENT_FONT_KEY = "content_font";

		private static final String HEADING_FONT_KEY = "heading_font";

		private static final String TITLE_TEXT_SIZE_KEY = "title_text_size";

		private static final String HEADING_TEXT_SIZE_KEY = "heading_text_size";

		private static final String CONTENT_TEXT_SIZE_KEY = "content_text_size";

		private boolean showImage, showTitle;

		private int contentTextSize, headingTextSize, titleTextSize;

		private String contentFont, headingFont,titleFont;

		private SharedPreferences sharedPreferences;

		private SharedPreferences.Editor editor;

		private Preferences(Context context){

			sharedPreferences = context.getSharedPreferences(GXResumeApplication.MY_PREFS_NAME,context.MODE_PRIVATE);

			showImage = sharedPreferences.getBoolean(getKeyString(SHOW_IMAGE_KEY), true);
			showTitle = sharedPreferences.getBoolean(getKeyString(SHOW_TITLE_KEY), true);

			contentTextSize = sharedPreferences.getInt(getKeyString(CONTENT_TEXT_SIZE_KEY), 10);
			headingTextSize = sharedPreferences.getInt(getKeyString(HEADING_TEXT_SIZE_KEY), 10);
			titleTextSize = sharedPreferences.getInt(getKeyString(TITLE_TEXT_SIZE_KEY), 14);

			contentFont = sharedPreferences.getString(getKeyString(CONTENT_FONT_KEY), "calibri_light");
			headingFont = sharedPreferences.getString(getKeyString(HEADING_FONT_KEY), "calibri_light");
			titleFont = sharedPreferences.getString(getKeyString(TITLE_FONT_KEY), "calibri_light");

		}



		private String getKeyString(String key){
			return title + "_" + date_of_creation + "_" + key;
		}

		public boolean isShowImage() {
			return showImage;
		}

		public boolean isShowTitle() {
			return showTitle;
		}

		public int getHeadingTextSize() {
			return headingTextSize;
		}

		public int getContentTextSize() {
			return contentTextSize;
		}

		public String getContentFont() {
			return contentFont;
		}

		public String getHeadingFont() {
			return headingFont;
		}

		public void setShowImage(boolean showImage) {
			this.showImage = showImage;
		}

		public int getTitleTextSize() {
			return titleTextSize;
		}

		public void setTitleTextSize(int titleTextSize) {
			this.titleTextSize = titleTextSize;
		}

		public String getTitleFont() {
			return titleFont;
		}

		public void setTitleFont(String titleFont) {
			this.titleFont = titleFont;
		}

		public void setShowTitle(boolean showTitle) {
			this.showTitle = showTitle;
		}

		public void setContentTextSize(int contentTextSize) {
			this.contentTextSize = contentTextSize;
		}

		public void setHeadingTextSize(int headingTextSize) {
			this.headingTextSize = headingTextSize;
		}

		public void setContentFont(String contentFont) {
			this.contentFont = contentFont;
		}

		public void setHeadingFont(String headingFont) {
			this.headingFont = headingFont;
		}

		public void savePreferences(){

//			contentFont = preferences.getString(CONTENT_FONT_KEY);
//			headingFont = preferences.getString(HEADING_FONT_KEY);
//
//			headingTextSize = preferences.getInt(HEADING_TEXT_SIZE_KEY);
//			contentTextSize = preferences.getInt(CONTENT_TEXT_SIZE_KEY);
//
//			showTitle = preferences.getBoolean(SHOW_TITLE_KEY);
//			showImage = preferences.getBoolean(SHOW_IMAGE_KEY);

			editor = sharedPreferences.edit();

			editor.putString(getKeyString(CONTENT_FONT_KEY),contentFont);
			editor.putString(getKeyString(HEADING_FONT_KEY),headingFont);
			editor.putString(getKeyString(TITLE_FONT_KEY), titleFont);

			editor.putInt(getKeyString(CONTENT_TEXT_SIZE_KEY), contentTextSize);
			editor.putInt(getKeyString(HEADING_TEXT_SIZE_KEY), headingTextSize);
			editor.putInt(getKeyString(TITLE_TEXT_SIZE_KEY), titleTextSize);

			editor.putBoolean(getKeyString(SHOW_TITLE_KEY), showTitle);
			editor.putBoolean(getKeyString(SHOW_IMAGE_KEY), showImage);

			editor.commit();

		}

		@Override
		public String toString() {
			String str = "";
			str += "show image : " + showImage + " ; ";
			str += "show title : " + showTitle + " ; ";
			str += "content :  size => " + contentTextSize + " , font => " + contentFont + " ; ";
			str += "heading :  size => " + headingTextSize + " , font => " + headingFont + " ; ";
			str += "title :  size => " + titleTextSize + " , font => " + titleFont + " ; ";
			return str;
		}
	}

}
