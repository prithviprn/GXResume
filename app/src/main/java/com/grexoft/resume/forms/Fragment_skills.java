package com.grexoft.resume.forms;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.grexoft.resume.R;
import com.grexoft.resume.Resume;
import com.grexoft.resume.helpers.Common_Utilty;

public class Fragment_skills extends ResumeFragment {

	private int itemId = R.id.et_field;

	private LinearLayout skillsContainer;

	private ImageView ivSkills;

	public static final int SKILLS = 1;

	@Override
	public boolean savedata() {

		saveSkills();
		return true;

	}

	private void saveSkills() {
		String str;
		EditText etSkills;

		myResume.skills = new ArrayList<String>();

		for (int i = 0; i < skillsContainer.getChildCount(); i++) {
			etSkills = (EditText) skillsContainer.getChildAt(i).findViewById(
					itemId);
			str = etSkills.getText().toString();
			if (str != null && !str.equals("")) {
				myResume.skills.add(Common_Utilty.firstUpper(str));
			}

		}
		myResume.checkForm(Resume.SKILLS_FORM);

	}

	@Override
	public void addRecord() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setLayoutId() {

		layoutId = R.layout.fragment_skills;

	}

	@Override
	protected void init() {

		skillsContainer = (LinearLayout) fragmentView
				.findViewById(R.id.skills_container);

		if (myResume.skills == null) {
			myResume.skills = new ArrayList<String>();
			myResume.skills.add(new String(""));
		}

		invalidateList();

		ivSkills = (ImageView) fragmentView.findViewById(R.id.btn_addSkills);
		ivSkills.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				saveSkills();
				String str = new String("");
				myResume.skills.add(str);
				invalidateList();

			}
		});

	}

	private void invalidateList() {

		
		System.out.println("inside invalidate, list size is : " + myResume.skills.size());

		if (skillsContainer.getChildCount() > 0) {

			skillsContainer.removeAllViews();
		}

		if (myResume.skills != null && !myResume.skills.isEmpty()) {

			for (int i = 0; i < myResume.skills.size(); i++) {
				skillsContainer.addView(getView(i));
			}

		}

	}

	@SuppressLint("InflateParams")
	private View getView(final int position) {

		LinearLayout layoutItem = (LinearLayout) getActivity()
				.getLayoutInflater().inflate(R.layout.addmore_item, null);

		final EditText etItemFeild = (EditText) layoutItem
				.findViewById(R.id.et_field);

		if (!myResume.skills.get(position).equals("")) {
			etItemFeild.setText(myResume.skills.get(position));
		}

		else {

			String hint = "";

			hint = "enter skill";

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

								boolean flag = Common_Utilty
										.isNotEmptyString(etItemFeild.getText()
												.toString()) ? true : false;

								deleteItem(position, flag);

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

	private void deleteItem(int position, boolean saveFirst) {

		if (saveFirst)
			savedata();

		ArrayList<String> myList = null;

		myList = myResume.skills;

		myList.remove(position);

		invalidateList();

	}
}
