package com.grexoft.resume.forms;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.grexoft.resume.R;
import com.grexoft.resume.Resume;
import com.grexoft.resume.helpers.Common_Utilty;

public class Fragment_activities extends ResumeFragment {

	private int itemId = R.id.et_field;

	private LinearLayout activitiesContainer, hobbiesContainer,
			strengthContainer, achievementContainer;

	private ImageView bExtCarr, bHobbies, bAchivement, bStrength;

	public static final int EXTRA_CURRICULAR_ACTIVITIES = 1;

	public static final int HOBBIES = 2;

	public static final int ACHIEVEMENTS = 3;

	public static final int STRENGTHS = 4;

	@Override
	public boolean savedata() {

		saveAchivements();
		saveExtra();
		saveHobbies();
		saveStrenght();
return true;

	}

	public void saveHobbies() {
		String str;
		EditText ethobby;

		myResume.hobbies = new ArrayList<String>();

		for (int i = 0; i < hobbiesContainer.getChildCount(); i++) {
			ethobby = (EditText) hobbiesContainer.getChildAt(i).findViewById(
					itemId);
			str = ethobby.getText().toString();
			if (str != null && !str.equals("")) {
				myResume.hobbies.add(Common_Utilty.firstUpper(str));
			}

		}
		myResume.checkForm(Resume.HOBBIES_FORM);

	}

	public void saveAchivements() {
		String str;
		EditText etachive;
		myResume.achive = new ArrayList<String>();

		for (int i = 0; i < achievementContainer.getChildCount(); i++) {
			etachive = (EditText) achievementContainer.getChildAt(i)
					.findViewById(itemId);
			str = etachive.getText().toString();
			if (str != null && !str.equals("")) {
				myResume.achive.add(Common_Utilty.firstUpper(str));
			}
		}
		myResume.checkForm(Resume.ACHEIVEMENTS_FORM);
	}

	public void saveExtra() {
		String str;
		EditText etExCarr;
		myResume.exCarr = new ArrayList<String>();

		for (int i = 0; i < activitiesContainer.getChildCount(); i++) {
			etExCarr = (EditText) activitiesContainer.getChildAt(i)
					.findViewById(R.id.et_field);
			str = etExCarr.getText().toString();
			if (str != null && !str.equals("")) {
				myResume.exCarr.add(Common_Utilty.firstUpper(str));
			}

		}
		myResume.checkForm(Resume.EXTRA_CURRICULAR_FORM);

	}

	public void saveStrenght() {
		String str;
		EditText etStrenght;
		myResume.strength = new ArrayList<String>();

		for (int i = 0; i < strengthContainer.getChildCount(); i++) {
			etStrenght = (EditText) strengthContainer.getChildAt(i)
					.findViewById(itemId);
			str = etStrenght.getText().toString();
			if (str != null && !str.equals("")) {
				myResume.strength.add(Common_Utilty.firstUpper(str));
			}

		}
		myResume.checkForm(Resume.STRENGTH_FORM);

	}

	@Override
	public void addRecord() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setLayoutId() {

		layoutId = R.layout.fragment_activities;

	}

	@Override
	protected void init() {

		activitiesContainer = (LinearLayout) fragmentView
				.findViewById(R.id.activities_container);

		hobbiesContainer = (LinearLayout) fragmentView
				.findViewById(R.id.hobbies_container);

		strengthContainer = (LinearLayout) fragmentView
				.findViewById(R.id.strength_container);

		achievementContainer = (LinearLayout) fragmentView
				.findViewById(R.id.achievement_container);

		if (myResume.hobbies == null) {
			myResume.hobbies = new ArrayList<String>();
			myResume.hobbies.add(new String(""));
		}

		invalidateList(HOBBIES);

		if (myResume.exCarr == null) {
			myResume.exCarr = new ArrayList<String>();
			myResume.exCarr.add(new String(""));
		}

		invalidateList(EXTRA_CURRICULAR_ACTIVITIES);

		if (myResume.achive == null) {
			myResume.achive = new ArrayList<String>();
			myResume.achive.add(new String(""));
		}

		invalidateList(ACHIEVEMENTS);

		if (myResume.strength == null) {
			myResume.strength = new ArrayList<String>();
			myResume.strength.add(new String(""));
		}

		invalidateList(STRENGTHS);

		bHobbies = (ImageView) fragmentView.findViewById(R.id.btn_hobbies);
		bHobbies.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				saveHobbies();
				String str = new String("");
				myResume.hobbies.add(str);

				// Toast.makeText(getActivity(),
				// "hobbies.add " + myResume.hobbies.size(),
				// Toast.LENGTH_SHORT).show();

				invalidateList(HOBBIES);

			}
		});

		bAchivement = (ImageView) fragmentView
				.findViewById(R.id.btn_achievement);
		bAchivement.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// aListV.setAdapter(null);

				saveAchivements();
				String str = new String("");
				myResume.achive.add(str);

				invalidateList(ACHIEVEMENTS);

			}
		});

		bExtCarr = (ImageView) fragmentView.findViewById(R.id.btn_exCarriculam);
		bExtCarr.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				saveExtra();
				String str = new String("");
				myResume.exCarr.add(str);

				invalidateList(EXTRA_CURRICULAR_ACTIVITIES);

			}
		});

		bStrength = (ImageView) fragmentView.findViewById(R.id.btn_strength);
		bStrength.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				saveStrenght();
				String str = new String("");
				myResume.strength.add(str);

				invalidateList(STRENGTHS);

			}
		});

	}

	private void invalidateList(int listNumber) {

		ViewGroup container = null;

		ArrayList<String> myList = null;

		switch (listNumber) {

		case EXTRA_CURRICULAR_ACTIVITIES:
			container = (ViewGroup) activitiesContainer;
			myList = myResume.exCarr;
			break;

		case STRENGTHS:
			container = (ViewGroup) strengthContainer;
			myList = myResume.strength;
			break;

		case HOBBIES:
			container = (ViewGroup) hobbiesContainer;
			myList = myResume.hobbies;
			break;

		case ACHIEVEMENTS:
			container = (ViewGroup) achievementContainer;
			myList = myResume.achive;
			break;

		}

		System.out
				.println("inside invalidate, list size is : " + myList.size());

		if (container.getChildCount() > 0) {

			container.removeAllViews();
		}

		if (myList != null && !myList.isEmpty()) {

			for (int i = 0; i < myList.size(); i++) {
				container.addView(getView(i, listNumber, myList));
			}

		}

	}

	@SuppressLint("InflateParams")
	private View getView(final int position, final int listNumber,
			ArrayList<String> mylist) {

		LinearLayout layoutItem = (LinearLayout) getActivity()
				.getLayoutInflater().inflate(R.layout.addmore_item, null);

		final EditText etItemFeild = (EditText) layoutItem
				.findViewById(R.id.et_field);

		if (!mylist.get(position).equals("")) {
			etItemFeild.setText(mylist.get(position));
		}

		else {

			String hint = "";

			switch (listNumber) {

			case HOBBIES:
				hint = "enter hobby";
				break;

			case ACHIEVEMENTS:
				hint = "enter acheivement";
				break;

			case EXTRA_CURRICULAR_ACTIVITIES:
				hint = "enter extra curricular activities";
				break;

			case STRENGTHS:
				hint = "enter strength";
				break;
			}

			etItemFeild.setHint(hint);
		}

		ImageView delete = (ImageView) layoutItem.findViewById(R.id.btn_delete);

		delete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(
						getActivity());
				alertdialogbuilder.setTitle("Delete");
				alertdialogbuilder
						.setMessage("Are you sure, you want to delete this entry?");
				alertdialogbuilder.setPositiveButton(android.R.string.yes,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								
													
								boolean flag = Common_Utilty.isNotEmptyString(etItemFeild.getText().toString()) ? true : false;
								
								deleteItem(position, listNumber, flag);

							}
						});
				alertdialogbuilder.setNegativeButton(android.R.string.no,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();

							}
						});

				alertdialogbuilder.setIcon(android.R.drawable.ic_dialog_alert);
				alertdialogbuilder.show();

			}

		});

		return layoutItem;

	}

	private void deleteItem(int position, int listNumber, boolean saveFirst) {		

		if (saveFirst) savedata();
		
		ArrayList<String> myList = null;

		switch (listNumber) {

		case EXTRA_CURRICULAR_ACTIVITIES:
			
			myList = myResume.exCarr;
			break;

		case STRENGTHS:
			
			myList = myResume.strength;
			break;

		case HOBBIES:
			
			myList = myResume.hobbies;
			break;

		case ACHIEVEMENTS:
			
			myList = myResume.achive;
			break;

		}

		System.out.println("before remove");

		System.out.println("positio is : " + position);

		System.out.println("myList size : " + myList.size());
		
			
		
		myList.remove(position);
		
		

		System.out.println("after remove");

		System.out.println("positio is : " + position);

		System.out.println("myList size : " + myList.size());

		invalidateList(listNumber);
	}

}
