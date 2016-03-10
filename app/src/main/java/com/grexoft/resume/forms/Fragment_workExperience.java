package com.grexoft.resume.forms;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.grexoft.resume.R;
import com.grexoft.resume.Resume;
import com.grexoft.resume.helpers.Common_Utilty;
import com.grexoft.resume.model.Exp;

public class Fragment_workExperience extends ResumeFragment {

	

	private LinearLayout workExperienceContainer;

	private EditText etCompany;
	private EditText etJobdesig;
	private EditText etStartDate;
	private EditText etEnddate;
	private EditText etWorkDescrip;
	private int editID;

	@Override
	public boolean savedata() {
		if (etCompany.getText().toString() != null
				&& !etCompany.getText().toString().equals("")) {
			addRecord();
		}

		myResume.checkForm(Resume.WORK_EXPERIENCE_FORM);
		return true;

	}

	public class WorkExpAdapter extends BaseAdapter {
		LayoutInflater inflater;

		public WorkExpAdapter() {
			inflater = getActivity().getLayoutInflater();
		}

		@Override
		public int getCount() {

			return myResume.workExperience.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			LinearLayout workExpLayout;
			if (convertView != null) {
				workExpLayout = (LinearLayout) convertView;
			} else {
				workExpLayout = (LinearLayout) inflater.inflate(
						R.layout.generic_tuple_item, parent, false);
			}

			TextView tv_company = (TextView) workExpLayout
					.findViewById(R.id.tvitem);
			tv_company.setText(myResume.workExperience.get(position).compName);

			Button edit = (Button) workExpLayout.findViewById(R.id.btn_edit);
			Button delete = (Button) workExpLayout.findViewById(R.id.btn_dlt);

			edit.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {

					etCompany.setText(myResume.workExperience.get(position).compName);
					etJobdesig.setText(myResume.workExperience.get(position).jobDesig);
					etStartDate.setText(myResume.workExperience.get(position).timeStart);
					etEnddate.setText(myResume.workExperience.get(position).timeEnd);
					etWorkDescrip.setText(myResume.workExperience.get(position).description);

					editID = position;

				}
			});

			delete.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							getActivity());
					alertDialogBuilder.setTitle("Delete");
					alertDialogBuilder
							.setMessage("Are you sure, you want to delete this entry?");
					alertDialogBuilder.setPositiveButton(android.R.string.yes,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									myResume.workExperience.remove(position);

									adapter.notifyDataSetChanged();

									if (editID == position) {
										editID = -1;

									}
								}
							});
					alertDialogBuilder.setNegativeButton(android.R.string.no,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.cancel();
								}
							});
					alertDialogBuilder
							.setIcon(android.R.drawable.ic_dialog_alert);
					alertDialogBuilder.show();

				}
			});

			return workExpLayout;
		}

	}

	@Override
	public void addRecord() {

		String compName = Common_Utilty.firstUpper(etCompany.getText()
				.toString());

		if (compName != null && !compName.equals("")) {

			String jobDesign = Common_Utilty.firstUpper(etJobdesig.getText()
					.toString());
			String startDate = etStartDate.getText().toString();
			String endDate = etEnddate.getText().toString();
			String workDescrip = Common_Utilty.firstUpper(etWorkDescrip
					.getText().toString());

			if (editID == -1) {
				Exp workExperience = new Exp(compName, jobDesign, startDate,
						endDate, workDescrip);

				myResume.workExperience.add(workExperience);

			}

			else {				
				Exp editWorkExp = myResume.workExperience.get(editID);

				editWorkExp.compName = compName;
				editWorkExp.jobDesig = jobDesign;
				editWorkExp.timeStart = startDate;
				editWorkExp.timeEnd = endDate;
				editWorkExp.description = workDescrip;

				editID = -1;

			}
			etCompany.setText("");
			etStartDate.setText("");
			etJobdesig.setText("");
			etEnddate.setText("");
			etWorkDescrip.setText("");

			invalidateWorkList();

		} else {
			Toast.makeText(getActivity(), "Please enter company",
					Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	protected void setLayoutId() {

		layoutId = R.layout.fragment_workexperience;

	}

	@Override
	protected void init() {

		

		workExperienceContainer = (LinearLayout) fragmentView
				.findViewById(R.id.work_experience_container);

		etCompany = (EditText) fragmentView.findViewById(R.id.etCompany);
		etJobdesig = (EditText) fragmentView
				.findViewById(R.id.etJobDesignation);
		etStartDate = (EditText) fragmentView.findViewById(R.id.etJoiningDate);
		etEnddate = (EditText) fragmentView.findViewById(R.id.etLeavingDate);
		etWorkDescrip = (EditText) fragmentView
				.findViewById(R.id.etWorkDescription);

		if (myResume.workExperience == null) {
			myResume.workExperience = new ArrayList<Exp>();
		}
		
		editID = -1;

		invalidateWorkList();

	}

	private void invalidateWorkList() {

		if (((ViewGroup) workExperienceContainer).getChildCount() > 0) {

			 workExperienceContainer.removeAllViews();
		}

		if (myResume.workExperience != null
				&& !myResume.workExperience.isEmpty()) {

			for (int i = 0; i < myResume.workExperience.size(); i++) {
				addWorkToList(i);
			}

		}

	}

	@SuppressLint("InflateParams")
	private void addWorkToList(final int position) {

		final LinearLayout degreeLayout;

		degreeLayout = (LinearLayout) getActivity().getLayoutInflater()
				.inflate(R.layout.generic_tuple_item, null);

		TextView tvComp = (TextView) degreeLayout.findViewById(R.id.tvitem);

		tvComp.setText(myResume.workExperience.get(position).compName);

		ImageView edit = (ImageView) degreeLayout.findViewById(R.id.btn_edit);

		ImageView delete = (ImageView) degreeLayout.findViewById(R.id.btn_dlt);

		edit.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {				

				etCompany.setText(myResume.workExperience.get(position).compName);
				etWorkDescrip.setText(myResume.workExperience.get(position).description);
				etJobdesig.setText(myResume.workExperience.get(position).jobDesig);
				etEnddate.setText(myResume.workExperience.get(position).timeEnd);
				etStartDate.setText(myResume.workExperience.get(position).timeStart);

				
				editID = position;

			}
		});

		delete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						getActivity());
				alertDialogBuilder.setTitle("Delete");
				alertDialogBuilder
						.setMessage("Are you sure, you want to delete this entry?");
				alertDialogBuilder.setPositiveButton(android.R.string.yes,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

								myResume.workExperience.remove(position);

								
								invalidateWorkList();

								if (editID == position) {

									editID = -1;

								}
							}
						});
				alertDialogBuilder.setNegativeButton(android.R.string.no,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
							}
						});
				alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
				alertDialogBuilder.show();

			}
		});

		workExperienceContainer.addView(degreeLayout);

	}

}
