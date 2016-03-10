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
import com.grexoft.resume.model.EducationalDetail;

public class Fragment_education extends ResumeFragment {
	
	
	
	private LinearLayout degreeContainer;

	private EditText etdegree;
	private EditText etuniversity;
	private EditText etyear;
	private EditText etcgpi;

	private int editId;

	

	@Override
	public boolean savedata() {

		if (etdegree.getText().toString() != null
				&& !etdegree.getText().toString().equals("")) {

			addRecord();
		}

		if (myResume.educationDetail == null
				|| myResume.educationDetail.isEmpty()) {
			Toast.makeText(getActivity(), "you have not entered any degree",
					Toast.LENGTH_SHORT).show();
		}
		myResume.checkForm(Resume.EDUCATIONAL_FORM);
		return true;

	}

	public class DegreeAdapter extends BaseAdapter {

		LayoutInflater inflater;

		public DegreeAdapter() {
			inflater = getActivity().getLayoutInflater();
		}

		@Override
		public int getCount() {

			return myResume.educationDetail.size();
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

			LinearLayout degreeLayout;

			if (convertView != null) {
				degreeLayout = (LinearLayout) convertView;
			} else {
				degreeLayout = (LinearLayout) inflater.inflate(
						R.layout.generic_tuple_item, parent, false);
			}

			TextView tv_degree = (TextView) degreeLayout
					.findViewById(R.id.tvitem);
			tv_degree.setText(myResume.educationDetail.get(position).degree);

			Button edit = (Button) degreeLayout.findViewById(R.id.btn_edit);
			Button delete = (Button) degreeLayout.findViewById(R.id.btn_dlt);

			edit.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {

					etdegree.setText(myResume.educationDetail.get(position).degree);
					etuniversity.setText(myResume.educationDetail.get(position).university);
					etyear.setText(myResume.educationDetail.get(position).result);
					etcgpi.setText(myResume.educationDetail.get(position).passout);

					editId = position;

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
									myResume.educationDetail.remove(position);
									
									notifyDataSetChanged();
									
									if (editId == position) {
										editId = -1;

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

			return degreeLayout;
		}

	}

	@Override
	public void addRecord() {

		String degree = Common_Utilty.firstUpper(etdegree.getText().toString());

		if (degree != null && !degree.equals("")) {

			String university = Common_Utilty.firstUpper(etuniversity.getText()
					.toString());
			String year = etyear.getText().toString();
			String grade = etcgpi.getText().toString();
			if (editId == -1) {
				EducationalDetail educationalDetail = new EducationalDetail(
						degree, university, year, grade);

				myResume.educationDetail.add(educationalDetail);
			}

			else {
				EducationalDetail edu = myResume.educationDetail.get(editId);

				edu.degree = degree;
				edu.university = university;
				edu.passout = year;
				edu.result = grade;

				editId = -1;
			}

			etdegree.setText("");
			etuniversity.setText("");
			etyear.setText("");
			etcgpi.setText("");

			invalidateDegreesList();

		} else
			// if(degreeList.getChildCount()<1 || !degree.equals("")){
			Toast.makeText(getActivity(), "please enter degree",
					Toast.LENGTH_SHORT).show();
		// }

	}

	@Override
	protected void setLayoutId() {

		layoutId = R.layout.fragment_education;

	}

	@Override
	protected void init() {

		editId = -1;

		etdegree = (EditText) fragmentView.findViewById(R.id.etDedree);
		etuniversity = (EditText) fragmentView.findViewById(R.id.etcollege);
		etyear = (EditText) fragmentView.findViewById(R.id.etyear);
		etcgpi = (EditText) fragmentView.findViewById(R.id.etcgpi);

		if (myResume.educationDetail == null) {
			myResume.educationDetail = new ArrayList<EducationalDetail>();

		}

		//adapter = new DegreeAdapter();
		//degreeList.setAdapter(adapter);
		
		degreeContainer = (LinearLayout)fragmentView.findViewById(R.id.degree_container);
		
		invalidateDegreesList();
		
//		Toast.makeText(getActivity(), "education fragment", Toast.LENGTH_SHORT).show();

	}

	private void invalidateDegreesList() {
		
		if (((ViewGroup)degreeContainer).getChildCount() > 0){
			
			degreeContainer.removeAllViews();
		}
		
		if (myResume.educationDetail != null && !myResume.educationDetail.isEmpty()){
			
			
			
			for( int i = 0 ; i < myResume.educationDetail.size() ; i++){
				addDegreeToList(i);
			}
			
		}
		
	}
	
	@SuppressLint("InflateParams") private void addDegreeToList(final int position){
		
		final LinearLayout degreeLayout;

		degreeLayout = (LinearLayout) getActivity().getLayoutInflater().inflate(
				R.layout.generic_tuple_item, null);
		
		TextView tv_degree = (TextView) degreeLayout
				.findViewById(R.id.tvitem);
		
		tv_degree.setText(myResume.educationDetail.get(position).degree);

		ImageView edit = (ImageView) degreeLayout.findViewById(R.id.btn_edit);
		
		ImageView delete = (ImageView) degreeLayout.findViewById(R.id.btn_dlt);

		edit.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				etdegree.setText(myResume.educationDetail.get(position).degree);
				etuniversity.setText(myResume.educationDetail.get(position).university);
				etyear.setText(myResume.educationDetail.get(position).passout);
				etcgpi.setText(myResume.educationDetail.get(position).result);

				editId = position;

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
								
								myResume.educationDetail.remove(position);
								
								//Toast.makeText(getActivity(), "degrees : " + myResume.educationDetail.size(), Toast.LENGTH_SHORT).show();
								
								invalidateDegreesList();
								
								if (editId == position) {
									
									editId = -1;

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
		
		degreeContainer.addView(degreeLayout);
		
	}

}
