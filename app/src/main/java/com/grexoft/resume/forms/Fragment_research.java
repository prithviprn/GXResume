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
import com.grexoft.resume.model.Research;

public class Fragment_research extends ResumeFragment {

	

	private LinearLayout researchContainer;

	private EditText etPaperTitle;
	private EditText etJournal;
	private EditText etVolume;
	private EditText etIssue;
	private EditText etDescription;
	private int editID;

	@Override
	public boolean savedata() {
		if (etPaperTitle.getText().toString() != null
				&& !etPaperTitle.getText().toString().equals("")) {
			addRecord();
		}
		if (myResume.research == null || myResume.research.isEmpty()) {
			Toast.makeText(getActivity(), "you have not entered any research",
					Toast.LENGTH_SHORT).show();
		}
		myResume.checkForm(Resume.RESEARCH_FORM);
		return true;
	}

	public class ResearchAdapter extends BaseAdapter {
		LayoutInflater inflater;

		public ResearchAdapter() {
			inflater = getActivity().getLayoutInflater();
		}

		@Override
		public int getCount() {

			return myResume.research.size();
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
			LinearLayout paperLayout;
			if (convertView != null) {
				paperLayout = (LinearLayout) convertView;
			} else {
				paperLayout = (LinearLayout) inflater.inflate(
						R.layout.generic_tuple_item, parent, false);
			}

			TextView tv_paperName = (TextView) paperLayout
					.findViewById(R.id.tvitem);
			tv_paperName.setText(myResume.research.get(position).papertitle);

			Button edit = (Button) paperLayout.findViewById(R.id.btn_edit);
			Button delete = (Button) paperLayout.findViewById(R.id.btn_dlt);

			edit.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {

					etPaperTitle.setText(myResume.research.get(position).papertitle);
					etVolume.setText(myResume.research.get(position).volume);
					etJournal.setText(myResume.research.get(position).journal);
					etIssue.setText(myResume.research.get(position).paperIssue);

					etDescription.setText(myResume.research.get(position).paperDescription);

					editID = position;

				}
			});

			delete.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					AlertDialog.Builder alertdialodbuilder = new AlertDialog.Builder(
							getActivity());
					alertdialodbuilder.setTitle("Delete");
					alertdialodbuilder
							.setMessage("Are sure, you want to delete this entry?");
					alertdialodbuilder.setPositiveButton(android.R.string.yes,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									myResume.research.remove(position);

									notifyDataSetChanged();

									if (editID == position) {
										editID = -1;

									}

								}
							});
					alertdialodbuilder.setNegativeButton(android.R.string.no,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.cancel();
								}
							});
					alertdialodbuilder
							.setIcon(android.R.drawable.ic_dialog_alert);
					alertdialodbuilder.show();

				}
			});

			return paperLayout;
		}

	}

	@Override
	public void addRecord() {

		String paperName = Common_Utilty.firstUpper(etPaperTitle.getText()
				.toString());

		if (paperName != null && !paperName.equals("")) {

			String volume = etVolume.getText().toString();
			String issue = etIssue.getText().toString();
			String journal = Common_Utilty.firstUpper(etJournal.getText()
					.toString());
			String Description = Common_Utilty.firstUpper(etDescription
					.getText().toString());

			if (editID == -1) {
				Research research = new Research(paperName, Description,
						volume, issue, journal);
				myResume.research.add(research);
			}

			else {
				Research editResearch = myResume.research.get(editID);

				editResearch.papertitle = paperName;
				editResearch.journal = journal;
				editResearch.volume = volume;
				editResearch.paperIssue = issue;

				editResearch.paperDescription = Description;

				editID = -1;
			}
			etPaperTitle.setText("");
			etJournal.setText("");
			etIssue.setText("");
			etVolume.setText("");
			etDescription.setText("");

			invalidateResearchList();

		} else {
			Toast.makeText(getActivity(), "Please enter research",
					Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	protected void setLayoutId() {

		layoutId = R.layout.fragment_research;

	}

	@Override
	protected void init() {

		editID = -1;

		

		researchContainer = (LinearLayout) fragmentView
				.findViewById(R.id.research_container);

		etPaperTitle = (EditText) fragmentView.findViewById(R.id.etpaperTitle);
		etJournal = (EditText) fragmentView.findViewById(R.id.etjournel);
		etVolume = (EditText) fragmentView.findViewById(R.id.etvolume);
		etIssue = (EditText) fragmentView.findViewById(R.id.etIssue);
		etDescription = (EditText) fragmentView
				.findViewById(R.id.etdescription);

		if (myResume.research == null) {
			myResume.research = new ArrayList<Research>();
		}

		
		invalidateResearchList();

	}

	private void invalidateResearchList() {

		if (((ViewGroup) researchContainer).getChildCount() > 0) {

			researchContainer.removeAllViews();
		}

		if (myResume.research != null
				&& !myResume.research.isEmpty()) {

			for (int i = 0; i < myResume.research.size(); i++) {
				addResearchToList(i);
			}

		}

	}

	@SuppressLint("InflateParams")
	private void addResearchToList(final int position) {

		final LinearLayout researchLayout;

		researchLayout = (LinearLayout) getActivity().getLayoutInflater()
				.inflate(R.layout.generic_tuple_item, null);

		TextView tvPaperTitle = (TextView) researchLayout.findViewById(R.id.tvitem);

		tvPaperTitle.setText(myResume.research.get(position).papertitle);

		ImageView edit = (ImageView) researchLayout.findViewById(R.id.btn_edit);

		ImageView delete = (ImageView) researchLayout.findViewById(R.id.btn_dlt);

		edit.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				etPaperTitle.setText(myResume.research.get(position).papertitle);
				etJournal.setText(myResume.research.get(position).journal);
				etVolume.setText(myResume.research.get(position).volume);
				etIssue.setText(myResume.research.get(position).paperIssue);
				etDescription.setText(myResume.research.get(position).paperDescription);

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

								myResume.research.remove(position);

								
								invalidateResearchList();

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

		researchContainer.addView(researchLayout);

	}

}
