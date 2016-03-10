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
import com.grexoft.resume.model.Project;

public class Fragment_project extends ResumeFragment {	

	private LinearLayout projectContainer;

	private EditText etProjTitle;
	private EditText etFrom;
	private EditText etTo;
	private EditText etRole;
	private EditText etTeamSize;
	private EditText etDescription;
	private int editID;

	public boolean savedata() {

		if (etProjTitle.getText().toString() != null
				&& !etProjTitle.getText().toString().equals("")) {
			addRecord();
		}
		if (myResume.project == null || myResume.project.isEmpty()) {
			Toast.makeText(getActivity(), "you have not entered any project",
					Toast.LENGTH_SHORT).show();
		}

		myResume.checkForm(Resume.PROJECTS_FORM);
		return true;

	}

	public class ProjectAdapter extends BaseAdapter {
		LayoutInflater inflater;

		public ProjectAdapter() {
			inflater = getActivity().getLayoutInflater();
		}

		@Override
		public int getCount() {

			return myResume.project.size();
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
			LinearLayout projectLayout;
			if (convertView != null) {
				projectLayout = (LinearLayout) convertView;
			} else {
				projectLayout = (LinearLayout) inflater.inflate(
						R.layout.generic_tuple_item, parent, false);
			}

			TextView tv_projectName = (TextView) projectLayout
					.findViewById(R.id.tvitem);
			tv_projectName.setText(myResume.project.get(position).title);

			Button edit = (Button) projectLayout.findViewById(R.id.btn_edit);
			Button delete = (Button) projectLayout.findViewById(R.id.btn_dlt);

			edit.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {

					etProjTitle.setText(myResume.project.get(position).title);
					etFrom.setText(myResume.project.get(position).timebeg);
					etTo.setText(myResume.project.get(position).timeend);
					etTeamSize.setText(myResume.project.get(position).size);
					etRole.setText(myResume.project.get(position).role);
					etDescription.setText(myResume.project.get(position).description);

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
									myResume.project.remove(position);

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

			return projectLayout;
		}

	}

	@Override
	public void addRecord() {

		String projectName = Common_Utilty.firstUpper(etProjTitle.getText()
				.toString());

		if (projectName != null && !projectName.equals("")) {

			System.out.println(projectName);
			String From = etFrom.getText().toString();
			System.out.println(From);

			String To = etTo.getText().toString();
			System.out.println(To);

			String Role = Common_Utilty.firstUpper(etRole.getText().toString());
			System.out.println(Role);

			String Teamsize = etTeamSize.getText().toString();
			System.out.println(Teamsize);

			String Description = Common_Utilty.firstUpper(etDescription
					.getText().toString());
			System.out.println(Description);

			if (editID == -1) {
				Project projects = new Project(projectName, Description, From,
						To, Role, Teamsize);

				myResume.project.add(projects);
			}

			else {
				Project editProject = myResume.project.get(editID);

				editProject.title = projectName;
				editProject.timebeg = From;
				editProject.timeend = To;
				editProject.role = Role;
				editProject.size = Teamsize;
				editProject.description = Description;

				editID = -1;
			}
			etProjTitle.setText("");
			etFrom.setText("");
			etTo.setText("");
			etRole.setText("");
			etTeamSize.setText("");
			etDescription.setText("");

			invalidateProjectList();

		} else {
			Toast.makeText(getActivity(), "Please enter project",
					Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	protected void setLayoutId() {

		layoutId = R.layout.fragment_project;

	}

	@Override
	protected void init() {

		editID = -1;	

		projectContainer = (LinearLayout) fragmentView
				.findViewById(R.id.txt_write_projects);

		etProjTitle = (EditText) fragmentView.findViewById(R.id.etprojectTitle);
		etFrom = (EditText) fragmentView.findViewById(R.id.etFrom);
		etTo = (EditText) fragmentView.findViewById(R.id.etTo);
		etRole = (EditText) fragmentView.findViewById(R.id.etRole);
		etTeamSize = (EditText) fragmentView.findViewById(R.id.etTeamsize);
		etDescription = (EditText) fragmentView
				.findViewById(R.id.etdescription);

		if (myResume.project == null) {
			myResume.project = new ArrayList<Project>();
		}
		
		invalidateProjectList();

	}

	private void invalidateProjectList() {

		if (((ViewGroup) projectContainer).getChildCount() > 0) {

			projectContainer.removeAllViews();
		}

		if (myResume.project != null
				&& !myResume.project.isEmpty()) {

			for (int i = 0; i < myResume.project.size(); i++) {
				addProjectToList(i);
			}

		}

	}

	@SuppressLint("InflateParams")
	private void addProjectToList(final int position) {

		final LinearLayout degreeLayout;

		degreeLayout = (LinearLayout) getActivity().getLayoutInflater()
				.inflate(R.layout.generic_tuple_item, null);

		TextView tvTitle = (TextView) degreeLayout.findViewById(R.id.tvitem);

		tvTitle.setText(myResume.project.get(position).title);

		ImageView edit = (ImageView) degreeLayout.findViewById(R.id.btn_edit);

		ImageView delete = (ImageView) degreeLayout.findViewById(R.id.btn_dlt);

		edit.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				etProjTitle.setText(myResume.project.get(position).title);
				etRole.setText(myResume.project.get(position).role);
				etFrom.setText(myResume.project.get(position).timebeg);
				etTo.setText(myResume.project.get(position).timeend);
				etDescription.setText(myResume.project.get(position).description);
				etTeamSize.setText(myResume.project.get(position).size);

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

								myResume.project.remove(position);

//								Toast.makeText(
//										getActivity(),
//										"degrees : "
//												+ myResume.educationDetail
//														.size(),
//										Toast.LENGTH_SHORT).show();

								invalidateProjectList();

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

		projectContainer.addView(degreeLayout);

	}

}
