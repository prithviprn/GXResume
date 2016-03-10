package com.grexoft.resume.helpers;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import com.grexoft.resume.Resume;
import com.grexoft.resume.model.EducationalDetail;
import com.grexoft.resume.model.Exp;
import com.grexoft.resume.model.Project;
import com.grexoft.resume.model.Reference;
import com.grexoft.resume.model.Research;
import com.grexoft.resume.model.SavedResumes;

public class DBHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "ResumeDatabase.db";

	public static final String RESUMES = "resumes";
	public static final String EDUCATIONAL_QUALIFICATION = "educational_qualification";
	public static final String SKILLS = "skills";
	public static final String WORK_EXPERIENCE = "work_experience";
	public static final String REFERENCE = "reference";
	public static final String PROJECT_DETAIL = "project_detail";
	public static final String RESEARCH_DETAIL = "research_detail";
	public static final String STRENGTH = "strength";
	public static final String HOBBIES = "hobbies";
	public static final String ACHEIVEMENT = "acheivement";
	public static final String EXTRA_CURRICULAR = "extra_curricular";
	public static final String CUSTOM_FIELDS = "custom_fields";
	

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}
	
	
	
	

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table if not exists "
				+ RESUMES
				+ "("
				+ "resume_id integer primary key autoincrement not null,"
				+ "date_of_creation date default (datetime('now', 'localtime')),"
				+ "image varchar(50),"
				+ "title varchar(50)," + "password varchar(64),"
				+ "name varchar(50)," + "email_id varchar(50),"
				+ "contact_number varchar(15)," + "addresses_are_same integer,"
				+ "permanent_address_street varchar(255),"
				+ "permanent_address_city varchar(25),"
				+ "permanent_address_pincode varchar(15),"
				+ "permanent_address_state varchar(25),"
				+ "permanent_address_country varchar(25),"
				+ "correspondence_address_street varchar(255),"
				+ "correspondence_address_city varchar(25),"
				+ "correspondence_address_pincode varchar(15),"
				+ "correspondence_address_state varchar(25),"
				+ "correspondence_address_country varchar(25),"
				+ "date_of_birth varchar(10)," + "gender varchar(10),"
				+ "nationality varchar(25)," + "languages_known varchar(255),"
				+ "father_name varchar(50)," + "mother_name varchar(50),"
				+ "objective varchar(255)," + "declaration varchar(255),"
				+ "check( addresses_are_same in (0, 1)),"
				+ "check( gender in ('" + Resume.Gender.MALE.getString() + "', '" + Resume.Gender.FEMALE.getString() + "', '" + Resume.Gender.NOT_SPECIFIED.getString() + "'))" + ")");

		db.execSQL("create table if not exists "
				+ EDUCATIONAL_QUALIFICATION
				+ "("
				+ "id integer primary key autoincrement not null,"
				+ "resume_id integer,"
				+ "degree varchar(255),"
				+ "university varchar(255),"
				+ "year_of_passing varchar(25),"
				+ "result varchar(25),"
				+ "foreign key (resume_id) references resumes(resume_id) on delete cascade on update cascade"
				+ ")");

		db.execSQL("create table if not exists "
				+ SKILLS
				+ "("
				+ "id integer primary key autoincrement not null,"
				+ "resume_id integer,"
				+ "skills varchar(255),"
				+ "foreign key (resume_id) references resumes(resume_id) on delete cascade on update cascade"
				+ ")");

		db.execSQL("create table if not exists "
				+ WORK_EXPERIENCE
				+ "("
				+ "id integer primary key autoincrement not null,"
				+ "resume_id integer,"
				+ "company_name varchar(25),"
				+ "job_dsignation varchar(25),"
				+ "start_year varchar(25),"
				+ "end_year varchar(25),"
				+ "job_description varchar(25),"
				+ "foreign key (resume_id) references resumes(resume_id) on delete cascade on update cascade"
				+ ")");

		db.execSQL("create table if not exists "
				+ STRENGTH
				+ "("
				+ "id integer primary key autoincrement not null,"
				+ "resume_id integer,"
				+ "strength varchar(255),"
				+ "foreign key (resume_id) references resumes(resume_id) on delete cascade on update cascade"
				+ ")");

		db.execSQL("create table if not exists "
				+ HOBBIES
				+ "("
				+ "id integer primary key autoincrement not null,"
				+ "resume_id integer,"
				+ "hobbies varchar(255),"
				+ "foreign key (resume_id) references resumes(resume_id) on delete cascade on update cascade"
				+ ")");

		db.execSQL("create table if not exists "
				+ CUSTOM_FIELDS
				+ "("
				+ "id integer primary key autoincrement not null,"
				+ "resume_id integer,"
				+ "field_name varchar(25),"
				+ "field_value varchar(25),"
				+ "foreign key (resume_id) references resumes(resume_id) on delete cascade on update cascade"
				+ ")");

		db.execSQL("create table if not exists "
				+ ACHEIVEMENT
				+ "("
				+ "id integer primary key autoincrement not null, "
				+ "resume_id integer, "
				+ "acheivements varchar(255),"
				+ "foreign key (resume_id) references resumes(resume_id) on delete cascade on update cascade"
				+ ")");

		db.execSQL("create table if not exists "
				+ EXTRA_CURRICULAR
				+ "("
				+ "id integer primary key autoincrement not null, "
				+ "resume_id integer, "
				+ "extra_curricular varchar(255),"
				+ "foreign key (resume_id) references resumes(resume_id) on delete cascade on update cascade"
				+ ")");

		db.execSQL("create table if not exists "
				+ PROJECT_DETAIL
				+ "("
				+ "id integer primary key autoincrement not null,"
				+ "resume_id integer,"
				+ "project_title varchar(45),"
				+ "team_size varchar(15),"
				+ "role varchar(15),"
				+ "project_description varchar(255),"
				+ "start_date varchar(25),"
				+ "end_date varchar(25),"
				+ "foreign key (resume_id) references resumes(resume_id) on delete cascade on update cascade"
				+ ")");

		db.execSQL("create table if not exists "
				+ RESEARCH_DETAIL
				+ "("
				+ "id integer primary key autoincrement not null,"
				+ "resume_id integer,"
				+ "paper_title varchar(255),"
				+ "journal varchar(255),"
				+ "volume varchar(10),"
				+ "issue varchar(10),"
				+ "paper_description varchar(255),"
				+ "foreign key (resume_id) references resumes(resume_id) on delete cascade on update cascade"
				+ ")");

		db.execSQL("create table if not exists "
				+ REFERENCE
				+ "("
				+ "id integer primary key autoincrement not null,"
				+ "resume_id integer,"
				+ "name varchar(25),"
				+ "job_dsignation varchar(25),"
				+ "address varchar(255),"
				+ "work_phone varchar(25),"
				+ "personal_phone varchar(25),"
				+ "email varchar(25),"
				+ "foreign key (resume_id) references resumes(resume_id) on delete cascade on update cascade"
				+ ")");
	

	}

	public void deleteRowsFromTable(String tableName, int resumeId) {

		String query = "delete from " + tableName + " where resume_id = "
				+ resumeId;

		SQLiteDatabase db = this.getWritableDatabase();

		db.execSQL(query);

		System.out.println("entry " + resumeId + " from table " + tableName
				+ " deleted : ");

	}

	public void insertRowIntoTable(String tableName,
			HashMap<String, String> hashMap, int resumeId) {

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();

		if (resumeId >= 0)
			contentValues.put("resume_id", resumeId);

		for (HashMap.Entry<String, String> entry : hashMap.entrySet()) {

			contentValues.put(entry.getKey().toString(), entry.getValue() != null ?  entry.getValue()
					.toString() : "");
		}

		System.out.println("row inserted in table " + tableName + " : "
				+ db.insert(tableName, null, contentValues));

	}

	public int getLastInsertResumeId() {

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor resultSet = db.rawQuery("select max(resume_id) from Resumes",
				null);

		resultSet.moveToFirst();

		return resultSet.getInt(0);
	}

	public void modifyTableRow(String tableName,
			HashMap<String, String> modifactions, int resumeId) {
		
		System.out.println("modify table row for table : " + tableName);

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues contentValues = new ContentValues();

		for (HashMap.Entry<String, String> entry : modifactions.entrySet()) {

			contentValues.put(entry.getKey().toString(), entry.getValue() != null ?  entry.getValue()
					.toString() : "");
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
		System.out.println("resume_id" + resumeId);
		System.out.println("table "
				+ tableName
				+ "updated : "
				+ db.update(tableName, contentValues, "resume_id=" + resumeId,
						null));

	}
	
	


	public ArrayList<EducationalDetail> getEducationalDetails(int resumeId) {

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor resultSet = db.rawQuery("select * from "
				+ EDUCATIONAL_QUALIFICATION + " where resume_id = " + resumeId,
				null);

		resultSet.moveToFirst();

		ArrayList<EducationalDetail> educationalDetails = new ArrayList<EducationalDetail>();

		if (resultSet.moveToFirst()) {
			do {
				educationalDetails
						.add(new EducationalDetail(resultSet
								.getString(resultSet.getColumnIndex("degree")),
								resultSet.getString(resultSet
										.getColumnIndex("university")),
								resultSet.getString(resultSet
										.getColumnIndex("year_of_passing")),
								resultSet.getString(resultSet
										.getColumnIndex("result"))));
			} while (resultSet.moveToNext());
		}

		return educationalDetails;
	}

	public ArrayList<Exp> getWorkExperiences(int resumeId) {

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor resultSet = db.rawQuery("select * from " + WORK_EXPERIENCE
				+ " where resume_id =" + resumeId, null);
		resultSet.moveToFirst();

		ArrayList<Exp> workEeperiences = new ArrayList<Exp>();

		if (resultSet.moveToFirst()) {
			do {
				workEeperiences.add(new Exp(resultSet.getString(resultSet
						.getColumnIndex("company_name")), resultSet
						.getString(resultSet.getColumnIndex("job_dsignation")),
						resultSet.getString(resultSet
								.getColumnIndex("start_year")),
						resultSet.getString(resultSet
								.getColumnIndex("end_year")), resultSet
								.getString(resultSet
										.getColumnIndex("job_description"))));
			} while (resultSet.moveToNext());
		}

		return workEeperiences;
	}

	public ArrayList<Reference> getReference(int resumeId) {

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor resultSet = db.rawQuery("select * from " + REFERENCE
				+ " where resume_id =" + resumeId, null);
		resultSet.moveToFirst();

		ArrayList<Reference> reference = new ArrayList<Reference>();

		if (resultSet.moveToFirst()) {
			do {
				reference
						.add(new Reference(resultSet.getString(resultSet
								.getColumnIndex("name")), resultSet
								.getString(resultSet
										.getColumnIndex("job_dsignation")),
								resultSet.getString(resultSet
										.getColumnIndex("address")),

								resultSet.getString(resultSet
										.getColumnIndex("work_phone")),
								resultSet.getString(resultSet
										.getColumnIndex("personal_phone")),
								resultSet.getString(resultSet
										.getColumnIndex("email"))));
			} while (resultSet.moveToNext());
		}

		return reference;
	}

	public ArrayList<Project> getProjectDetails(int resumeId) {

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor resultSet = db.rawQuery(" select * from " + PROJECT_DETAIL
				+ " where resume_id =" + resumeId, null);
		resultSet.moveToFirst();

		ArrayList<Project> ProjectDetails = new ArrayList<Project>();

		if (resultSet.moveToFirst()) {
			do {
				ProjectDetails.add(new Project(resultSet.getString(resultSet
						.getColumnIndex("project_title")), resultSet
						.getString(resultSet
								.getColumnIndex("project_description")),
						resultSet.getString(resultSet
								.getColumnIndex("start_date")),
						resultSet.getString(resultSet
								.getColumnIndex("end_date")), resultSet
								.getString(resultSet.getColumnIndex("role")),
						resultSet.getString(resultSet
								.getColumnIndex("team_size"))));
			} while (resultSet.moveToNext());
		}

		return ProjectDetails;
	}

	public ArrayList<Research> getResearchDetails(int resumeId) {

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor resultSet = db.rawQuery(" select * from " + RESEARCH_DETAIL
				+ " where resume_id =" + resumeId, null);
		resultSet.moveToFirst();

		ArrayList<Research> ResearchDetails = new ArrayList<Research>();

		if (resultSet.moveToFirst()) {
			do {
				ResearchDetails
						.add(new Research(resultSet.getString(resultSet
								.getColumnIndex("paper_title")), resultSet
								.getString(resultSet
										.getColumnIndex("paper_description")),

								resultSet.getString(resultSet
										.getColumnIndex("volume")), resultSet
										.getString(resultSet
												.getColumnIndex("issue")),
								resultSet.getString(resultSet
										.getColumnIndex("journal"))));
			} while (resultSet.moveToNext());
		}

		return ResearchDetails;
	}

	public ArrayList<String> getRowsFromTable(String tableName, int resumeId) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor resultSet = db.rawQuery(" select * from " + tableName
				+ " where resume_id = " + resumeId, null);
		resultSet.moveToFirst();

		String columnName = "";

		switch (tableName) {

		case SKILLS:
			columnName = "skills";
			break;
		case ACHEIVEMENT:
			columnName = "acheivements";
			break;
		case HOBBIES:
			columnName = "hobbies";
			break;
		case STRENGTH:
			columnName = "strength";
			break;
		case EXTRA_CURRICULAR:
			columnName = "extra_curricular";
			break;

		}

		ArrayList<String> rowsFromTable = new ArrayList<String>();

		if (resultSet.moveToFirst()) {
			do {
				rowsFromTable.add(resultSet.getString(resultSet
						.getColumnIndex(columnName)));
			} while (resultSet.moveToNext());
		}
		return rowsFromTable;
	}

	public HashMap<String, String> getCustomField(int resumeId) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor resultSet = db.rawQuery("select * from " + CUSTOM_FIELDS
				+ " where resume_id =" + resumeId, null);

		HashMap<String, String> customFields = new HashMap<String, String>();
		if (resultSet.moveToFirst()) {
			do {
				customFields.put(resultSet.getString(resultSet
						.getColumnIndex("field_name")), resultSet
						.getString(resultSet.getColumnIndex("field_value")));
			} while (resultSet.moveToNext());
		}

		return customFields;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		

		db.execSQL("PRAGMA writable_schema = 1");
		db.execSQL("delete from sqlite_master where type in ('table', 'index', 'trigger')");
		db.execSQL("PRAGMA writable_schema = 0");
		// db.execSQL("vacuum");
		// db.execSQL("PRAGMA INTEGRITY_CHECK");
		onCreate(db);

	}

	public ArrayList<SavedResumes> getSavedResumes() {

		String query = "select resume_id, title, date_of_creation, password from "
				+ RESUMES;

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor resultSet = db.rawQuery(query, null);

		System.out.println("total resumes : " + resultSet.getCount());

		if (resultSet.moveToFirst()) {

			ArrayList<SavedResumes> savedResumes = new ArrayList<SavedResumes>();

			do {

				savedResumes.add(new SavedResumes(resultSet.getInt(0),
						resultSet.getString(1), resultSet.getString(2),
						resultSet.getString(3)));

			} while (resultSet.moveToNext());

			return savedResumes;

		}

		return null;
	}

	public Bundle getPrimaryInfo(int resumeId) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor resultSet = db.rawQuery("select * from " + RESUMES
				+ " where resume_id =" + resumeId, null);

		Bundle primaryInfo = new Bundle();
		if (resultSet.moveToFirst()) {
			do {

				primaryInfo.putString("password", resultSet.getString(resultSet
						.getColumnIndex("password")));
				primaryInfo.putString("name",
						resultSet.getString(resultSet.getColumnIndex("name")));
				primaryInfo.putString("title",
						resultSet.getString(resultSet.getColumnIndex("title")));
				primaryInfo.putString("email", resultSet.getString(resultSet
						.getColumnIndex("email_id")));
				primaryInfo.putString("primary_contact", resultSet
						.getString(resultSet.getColumnIndex("contact_number")));
				primaryInfo
						.putInt("addresses_are_same", resultSet
								.getInt(resultSet
										.getColumnIndex("addresses_are_same")));
				primaryInfo.putString("street", resultSet.getString(resultSet
						.getColumnIndex("correspondence_address_street")));
				primaryInfo.putString("city", resultSet.getString(resultSet
						.getColumnIndex("correspondence_address_city")));
				primaryInfo.putString("pincode", resultSet.getString(resultSet
						.getColumnIndex("correspondence_address_pincode")));
				primaryInfo.putString("state", resultSet.getString(resultSet
						.getColumnIndex("correspondence_address_state")));
				primaryInfo.putString("country", resultSet.getString(resultSet
						.getColumnIndex("correspondence_address_country")));
				primaryInfo.putString("street1", resultSet.getString(resultSet
						.getColumnIndex("permanent_address_street")));
				primaryInfo.putString("city1", resultSet.getString(resultSet
						.getColumnIndex("permanent_address_city")));
				primaryInfo.putString("pincode1", resultSet.getString(resultSet
						.getColumnIndex("permanent_address_pincode")));
				primaryInfo.putString("state1", resultSet.getString(resultSet
						.getColumnIndex("permanent_address_state")));
				primaryInfo.putString("country1", resultSet.getString(resultSet
						.getColumnIndex("permanent_address_country")));
				primaryInfo.putString("dob", resultSet.getString(resultSet
						.getColumnIndex("date_of_birth")));
				primaryInfo
						.putString("gendar", resultSet.getString(resultSet
								.getColumnIndex("gender")));
				primaryInfo.putString("nationality", resultSet
						.getString(resultSet.getColumnIndex("nationality")));
				primaryInfo.putString("language", resultSet.getString(resultSet
						.getColumnIndex("languages_known")));

				primaryInfo.putString("father_name", resultSet
						.getString(resultSet.getColumnIndex("father_name")));

				primaryInfo.putString("mother_name", resultSet
						.getString(resultSet.getColumnIndex("mother_name")));

				primaryInfo.putString("objectives", resultSet
						.getString(resultSet.getColumnIndex("objective")));
				primaryInfo.putString("declaration", resultSet
						.getString(resultSet.getColumnIndex("declaration")));
				
				primaryInfo.putString("date_of_creation", resultSet
						.getString(resultSet.getColumnIndex("date_of_creation")));
				
				primaryInfo.putString("image", resultSet
						.getString(resultSet.getColumnIndex("image")));

			} while (resultSet.moveToNext());
		}

		return primaryInfo;
	}

}
