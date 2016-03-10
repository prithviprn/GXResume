package com.grexoft.resume;

import java.util.ArrayList;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.grexoft.resume.helpers.DBHelper;
import com.grexoft.resume.model.SavedResumes;

@SuppressWarnings("deprecation")
public class SavedActivity extends ActionBarActivity {
	private ListView savedList;
	Resume myResume;
	ArrayList<SavedResumes> savedResumes = new ArrayList<SavedResumes>();
	ResumeAdapter adapter;
	TextView txtNoResume;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		myResume = Resume.getInstance();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_saved);
		savedList = (ListView) findViewById(R.id.resumes_container);

		myResume.dbHelper = new DBHelper(this);
		txtNoResume = (TextView) findViewById(R.id.txt_no_resume);

		savedResumes = myResume.dbHelper.getSavedResumes();
		if (savedResumes != null && !savedResumes.isEmpty()) {
			System.out.print("saved resumes : " + savedResumes.size());
			savedList.setVisibility(View.VISIBLE);
			txtNoResume.setVisibility(View.GONE);
			adapter = new ResumeAdapter();
			savedList.setAdapter(adapter);
		} else {
			savedList.setVisibility(View.GONE);
			txtNoResume.setVisibility(View.VISIBLE);
			
		}

	}

	public class ResumeAdapter extends BaseAdapter {

		LayoutInflater inflater;

		public ResumeAdapter() {
			inflater = getLayoutInflater();
		}

		@Override
		public int getCount() {

			return savedResumes.size();
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
			LinearLayout savedResumeLayout;

			if (convertView != null) {
				savedResumeLayout = (LinearLayout) convertView;
			} else {
				savedResumeLayout = (LinearLayout) inflater.inflate(
						R.layout.generic_saved_resume_item, parent, false);
			}

			TextView tv_name_of_resume = (TextView) savedResumeLayout
					.findViewById(R.id.txt_title);
			TextView tv_date_of_creation = (TextView) savedResumeLayout
					.findViewById(R.id.txt_resume_date);
			tv_name_of_resume.setText(savedResumes.get(position)
					.getresumeTitle());
			tv_date_of_creation.setText(savedResumes.get(position)
					.getTimestamp());

			ImageView edit = (ImageView) savedResumeLayout
					.findViewById(R.id.btn_edit);
			ImageView delete = (ImageView) savedResumeLayout
					.findViewById(R.id.btn_dlt);
			delete.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

					AlertDialog.Builder alertdialodbuilder = new AlertDialog.Builder(
							SavedActivity.this);

					if (savedResumes.get(position).isPasswordSet()) {

						alertdialodbuilder
								.setTitle("Authenticate before delete");

						final EditText etPassword = new EditText(
								getApplicationContext());

						etPassword.setHint(getResources().getString(
								R.string.resume_password));

						etPassword.setInputType(InputType.TYPE_CLASS_TEXT
								| InputType.TYPE_TEXT_VARIATION_PASSWORD);

						etPassword
								.setLayoutParams(new LinearLayout.LayoutParams(
										LayoutParams.MATCH_PARENT,
										LayoutParams.WRAP_CONTENT));

						etPassword.setTextColor(Color.BLACK);
						etPassword.setBackgroundColor(Color.WHITE);

						alertdialodbuilder.setView(etPassword);

						alertdialodbuilder.setPositiveButton("delete",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {

										if (savedResumes.get(position)
												.isPasswordCorrect(
														etPassword.getText()
																.toString())) {

											myResume.dbHelper
													.deleteRowsFromTable(
															DBHelper.RESUMES,
															savedResumes
																	.get(position)
																	.getResumeId());

											savedResumes.remove(position);
											notifyDataSetChanged();

											if (savedResumes.size() == 0) {

												savedList
														.setVisibility(View.GONE);
												txtNoResume
														.setVisibility(View.VISIBLE);
											}

										}

										else {
											Toast.makeText(
													getApplicationContext(),
													"incorrect password",
													Toast.LENGTH_SHORT).show();
										}

									}
								});
					}

					else {

						alertdialodbuilder.setTitle("Delete");

						alertdialodbuilder
								.setMessage("Are sure, you want to delete this entry?");

						alertdialodbuilder.setPositiveButton(
								android.R.string.yes,
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {

										myResume.dbHelper.deleteRowsFromTable(
												DBHelper.RESUMES, savedResumes
														.get(position)
														.getResumeId());

										savedResumes.remove(position);

										adapter.notifyDataSetChanged();
										if (savedResumes.size() == 0) {

											savedList.setVisibility(View.GONE);
											txtNoResume
													.setVisibility(View.VISIBLE);
										}

									}
								});
					}

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

			edit.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

					if (savedResumes.get(position).isPasswordSet()) {

						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
								SavedActivity.this);

						View view = getLayoutInflater().inflate(
								R.layout.authentication_dialogue, null);

						final EditText etPassword = (EditText) view
								.findViewById(R.id.edit_authentication_password);

						alertDialogBuilder.setView(view);

						alertDialogBuilder.setPositiveButton("Authenticate",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {

										if (savedResumes.get(position)
												.isPasswordCorrect(
														etPassword.getText()
																.toString())) {

											Intent intent = new Intent(
													getApplicationContext(),
													InfoActivity.class);
											intent.putExtra("is_in_edit", true);
											intent.putExtra("resume_id",
													savedResumes.get(position)
															.getResumeId());
											startActivity(intent);
										}

										else {
											Toast.makeText(
													getApplicationContext(),
													"incorrect password",
													Toast.LENGTH_SHORT).show();
										}

									}
								});

						alertDialogBuilder.setNegativeButton(
								android.R.string.no,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.cancel();
									}
								});
						
						alertDialogBuilder.show();

					}

					else {

						Intent intent = new Intent(getApplicationContext(),
								InfoActivity.class);
						intent.putExtra("is_in_edit", true);
						intent.putExtra("resume_id", savedResumes.get(position)
								.getResumeId());
						startActivity(intent);
					}

				}
			});

			return savedResumeLayout;
		}

	}

}
