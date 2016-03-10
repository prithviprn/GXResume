package com.grexoft.resume.forms;

import java.util.ArrayList;


import org.apache.commons.lang3.text.WordUtils;

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

import com.grexoft.resume.R;
import com.grexoft.resume.Resume;
import com.grexoft.resume.helpers.Common_Utilty;
import com.grexoft.resume.model.Reference;

public class Fragment_reference extends ResumeFragment {

	

	private LinearLayout referenceContainer;

	private EditText etName;
	private EditText etJobdesig;
	private EditText etAddress;
	private EditText etWorkPhone, etPersonalPhone;
	private EditText etEmail;
	private int editID;

	@Override
	public boolean savedata() {
		if (etName.getText().toString() != null
				&& !etName.getText().toString().equals("")) {
			addRecord();
		}
		myResume.checkForm(Resume.REFERENCE_FORM);
		return true;
	}

	public class ReferenceAdapter extends BaseAdapter {
		LayoutInflater inflater;

		public ReferenceAdapter() {
			inflater = getActivity().getLayoutInflater();
		}

		@Override
		public int getCount() {

			return myResume.reference.size();
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

		public View getView(final int position, View convertView,
				ViewGroup parent) {
			LinearLayout referenceLayout;
			if (convertView != null) {
				referenceLayout = (LinearLayout) convertView;
			} else {
				referenceLayout = (LinearLayout) inflater.inflate(
						R.layout.generic_tuple_item, parent, false);
			}

			TextView tv_name = (TextView) referenceLayout
					.findViewById(R.id.tvitem);
			tv_name.setText(myResume.reference.get(position).name);

			Button edit = (Button) referenceLayout.findViewById(R.id.btn_edit);
			Button delete = (Button) referenceLayout.findViewById(R.id.btn_dlt);

			edit.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {

					etName.setText(myResume.reference.get(position).name);
					etJobdesig.setText(myResume.reference.get(position).title);
					etAddress.setText(myResume.reference.get(position).workAddress);
					etWorkPhone.setText(myResume.reference.get(position).workPhone);
					etPersonalPhone.setText(myResume.reference.get(position).personalPhone);
					etEmail.setText(myResume.reference.get(position).email);
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
									myResume.reference.remove(position);

									notifyDataSetChanged();

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

			return referenceLayout;
		}

	}

	@Override
	public void addRecord() {

		String Name = WordUtils.capitalizeFully(etName.getText().toString());

		if (Name != null && !Name.equals("")) {

			String jobDesign = Common_Utilty.firstUpper(etJobdesig.getText().toString());
			String address = Common_Utilty.firstUpper(etAddress.getText().toString());
			String email = etEmail.getText().toString();
			String workPhone = etWorkPhone.getText().toString();
			String personalPhone = etPersonalPhone.getText().toString();
			

			
			if (editID == -1) {
				Reference reference = new Reference(Name, jobDesign, 
						address ,workPhone,personalPhone, email);
				myResume.reference.add(reference);

			}

			else {
				Reference editReference = myResume.reference.get(editID);

				editReference.name = Name;
				editReference.title = jobDesign;
				editReference.email = email;
				editReference.workAddress = address;
				
				editReference.workPhone = workPhone;
				editReference.personalPhone = personalPhone;
				

				editID = -1;

			}
			etName.setText("");
			etJobdesig.setText("");
			etAddress.setText("");
			etEmail.setText("");
			etWorkPhone.setText("");
			etPersonalPhone.setText("");
			
			
			
			invalidateReferenceList();

		}
	}

	@Override
	protected void setLayoutId() {

		layoutId = R.layout.fragment_reference;

	}

	@Override
	protected void init() {

		editID = -1;

		referenceContainer = (LinearLayout) fragmentView
				.findViewById(R.id.reference_container);

		
		etName = (EditText) fragmentView.findViewById(R.id.et_name);
		etJobdesig = (EditText) fragmentView.findViewById(R.id.et_job_title);
		etAddress = (EditText) fragmentView.findViewById(R.id.et_address);
		etEmail = (EditText) fragmentView.findViewById(R.id.et_email);
		etWorkPhone = (EditText) fragmentView.findViewById(R.id.et_work_phone);
		etPersonalPhone = (EditText) fragmentView.findViewById(R.id.et_personal_phone);
		

		if (myResume.reference == null) {
			myResume.reference = new ArrayList<Reference>();
		}

		invalidateReferenceList();

	}

	private void invalidateReferenceList() {

		if (((ViewGroup) referenceContainer).getChildCount() > 0) {

			referenceContainer.removeAllViews();
		}

		if (myResume.reference != null
				&& !myResume.reference.isEmpty()) {

			for (int i = 0; i < myResume.reference.size(); i++) {
				addReferenceToList(i);
			}

		}

	}

	@SuppressLint("InflateParams")
	private void addReferenceToList(final int position) {

		final LinearLayout referenceLayout;

		referenceLayout = (LinearLayout) getActivity().getLayoutInflater()
				.inflate(R.layout.generic_tuple_item, null);

		TextView tvRefererName = (TextView) referenceLayout.findViewById(R.id.tvitem);

		tvRefererName.setText(myResume.reference.get(position).name);

		ImageView edit = (ImageView) referenceLayout.findViewById(R.id.btn_edit);

		ImageView delete = (ImageView) referenceLayout.findViewById(R.id.btn_dlt);

		edit.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				etName.setText(myResume.reference.get(position).name);
				etJobdesig.setText(myResume.reference.get(position).title);
				etAddress.setText(myResume.reference.get(position).workAddress);
				etEmail.setText(myResume.reference.get(position).email);
				etWorkPhone.setText(myResume.reference.get(position).workPhone);
				etPersonalPhone.setText(myResume.reference.get(position).personalPhone);
				
				

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

								myResume.reference.remove(position);

								invalidateReferenceList();

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

		referenceContainer.addView(referenceLayout);

	}

}
