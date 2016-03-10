package com.grexoft.resume.forms;

import java.util.HashMap;

import org.apache.commons.lang3.text.WordUtils;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.grexoft.resume.R;
import com.grexoft.resume.Resume;
import com.grexoft.resume.helpers.Common_Utilty;

@SuppressLint("InflateParams") public class Fragment_personnal extends ResumeFragment {
	
	private Spinner gendar;
	
	private EditText etdob, etnationality, etlanguage,etFatherName,etMotherName,etTitle;
	
	private ImageView btn_addmore;
	private Button btn_changePassword;

	
	private void addCustomfield(String fieldName, String fieldValue){
		
		final LinearLayout moreFieldsContainer = (LinearLayout) fragmentView
				.findViewById(R.id.more_fields_layout);
		
		
		
		final LinearLayout customFieldLayout = (LinearLayout) getLayoutInflater(
				null).inflate(R.layout.addmore_item, null);

		

		EditText customField = (EditText) customFieldLayout
				.findViewById(R.id.et_field);

		customField.setTag(fieldName);
		
		customField.setHint("enter "
				+ fieldName);
		
		if (fieldValue != null) customField.setText(fieldValue);

		

		

		ImageView btnDelete = (ImageView) customFieldLayout
				.findViewById(R.id.btn_delete);

		btnDelete
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {

						moreFieldsContainer
								.removeView(customFieldLayout);

					}
				});
		
		
		moreFieldsContainer.addView(customFieldLayout);
		
		
		
	}

	@Override
	public boolean savedata() {		

		String nationality = Common_Utilty.firstUpper(etnationality.getText().toString());

		myResume.nationality = Common_Utilty.firstUpper(nationality);
		
		String title=WordUtils.capitalizeFully(etTitle.getText().toString());
		if(Common_Utilty.isNotEmptyString(title)){
		
				myResume.title=title;
			
		}else{
			
			Toast.makeText(getActivity(), "Please enter title", Toast.LENGTH_SHORT).show();
			return false;
		}
		

		String language = Common_Utilty.firstUpper(etlanguage.getText().toString());
		
		myResume.language = Common_Utilty.firstUpper(language);
		
		String dateOfBirth = etdob.getText().toString();
	
		myResume.dob = dateOfBirth;
		
		myResume.setGenderByPosition(gendar.getSelectedItemPosition());
		
		String FatherName=WordUtils.capitalizeFully(etFatherName.getText().toString());
		myResume.fatherName= WordUtils.capitalizeFully(FatherName);
		
		String MotherName=WordUtils.capitalizeFully(etMotherName.getText().toString());
		
		myResume.motherName=WordUtils.capitalizeFully(MotherName);
		
		myResume.customFields = new HashMap<String, String>();

		ViewGroup moreFieldsContainer = (ViewGroup) fragmentView
				.findViewById(R.id.more_fields_layout);

		EditText customTextField;

		for (int i = 0; i < moreFieldsContainer.getChildCount(); i++) {
			customTextField = (EditText) moreFieldsContainer.getChildAt(i)
					.findViewById(R.id.et_field);
			String fieldName = WordUtils.capitalize(customTextField.getTag().toString());
			String fieldvalue = customTextField.getText().toString();
			myResume.customFields.put(fieldName,(fieldvalue));
		}
		myResume.checkForm(Resume.PERSONAL_FORM);
		return true;
	}

	@Override
	public void addRecord() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setLayoutId() {
		
		layoutId = R.layout.fragment_personal;
		
	}

	@Override
	protected void init() {
		
		etdob = (EditText) fragmentView.findViewById(R.id.etdob);
		
		etTitle=(EditText)fragmentView.findViewById(R.id.et_title);
		
		btn_changePassword=(Button)fragmentView.findViewById(R.id.btn_password);
		
		if(Common_Utilty.isNotEmptyString(myResume.password)){
			btn_changePassword.setVisibility(View.VISIBLE);
		}
		
		
		etnationality = (EditText) fragmentView
				.findViewById(R.id.etnationality);
		etlanguage = (EditText) fragmentView.findViewById(R.id.etlanguage);
		etFatherName = (EditText) fragmentView.findViewById(R.id.et_father_name);
		etMotherName = (EditText) fragmentView.findViewById(R.id.et_mother_name);
		btn_addmore = (ImageView) fragmentView.findViewById(R.id.btn_addmore);
		
		gendar = (Spinner) fragmentView.findViewById(R.id.gendar);
		
		
		if (myResume.gender == null){
			gendar.setSelection(0);
		}
		
		else {
			gendar.setSelection(myResume.gender.getValue());
		}		
		
		gendar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				
				myResume.setGenderByPosition(position);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		

		btn_addmore.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				View dialogView = getActivity().getLayoutInflater().inflate(R.layout.custom_dialogue, null);

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						getActivity());
				alertDialogBuilder.setView(dialogView);
				final EditText userInput = (EditText) dialogView
						.findViewById(R.id.et_feildName);
				alertDialogBuilder.setCancelable(false);
				alertDialogBuilder.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

								addCustomfield(userInput.getText().toString(), null);

							}
						});
				alertDialogBuilder.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();

			}
		});
		btn_changePassword.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				View dialogView = getActivity().getLayoutInflater().inflate(R.layout.password_dialogue, null);

				 AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						getActivity());
				alertDialogBuilder.setView(dialogView);
			    final EditText et_oldPasword = (EditText) dialogView
						.findViewById(R.id.et_old_password);	    
			   
			    final EditText et_newPasword = (EditText) dialogView
						.findViewById(R.id.et_new_passsword);
			    
			    
			    System.out.println(myResume.password);
				
				alertDialogBuilder.setCancelable(false);
				
				alertDialogBuilder.setPositiveButton("Change",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
						

							}
						});
				alertDialogBuilder.setNegativeButton("Remove",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
						}
						});
				
				alertDialogBuilder.setNeutralButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
				
 			final AlertDialog alertDialog = alertDialogBuilder.create();

				
				alertDialog.show();
				
				alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
	              {            
	                  @Override
	                  public void onClick(View v)
	                  {
	                      Boolean wantToCloseDialog = false;
	                      String oldPassword=et_oldPasword.getText().toString();
							String newPassword=et_newPasword.getText().toString();
							if(Common_Utilty.isNotEmptyString(oldPassword)){
								if(oldPassword.equals(myResume.password)){
									if(Common_Utilty.isNotEmptyString(newPassword)){
										myResume.password=newPassword;
										myResume.checkForm(Resume.CONTACT_FORM);
										wantToCloseDialog=true;
										Toast.makeText(getActivity(), "Your password is changed", Toast.LENGTH_SHORT).show();
										System.out.println("after changed password is :"+myResume.password);
									}else{
										Toast.makeText(getActivity(), "please enter new password", Toast.LENGTH_SHORT).show();
										wantToCloseDialog=false;
									}
									
								
								}else{
									Toast.makeText(getActivity(), "Incorrect old password", Toast.LENGTH_SHORT).show();
									wantToCloseDialog=false;
								}
								
							}else{
								Toast.makeText(getActivity(), "please enter password", Toast.LENGTH_SHORT).show();
								wantToCloseDialog=false;
								
							}
	                      if(wantToCloseDialog)
	                          alertDialog.dismiss();
	                     
	                  }
	              });

	        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener()
	          {            
	              @Override
	              public void onClick(View v)
	              {
	                  Boolean wantToCloseDialog = false;
	              	String oldPassword=et_oldPasword.getText().toString();
					if(Common_Utilty.isNotEmptyString(oldPassword)){
						if(oldPassword.equals(myResume.password)){
							myResume.password=null;
							btn_changePassword.setVisibility(View.GONE);
							myResume.checkForm(Resume.CONTACT_FORM);
							wantToCloseDialog=true;
							Toast.makeText(getActivity(), "Your password is removed", Toast.LENGTH_SHORT).show();
							System.out.println("after removed password is :"+myResume.password);
						}else{
							Toast.makeText(getActivity(), "Incorrect old password", Toast.LENGTH_SHORT).show();
							wantToCloseDialog=false;
							
						}
						
					}else{
						Toast.makeText(getActivity(), "Please enter old password", Toast.LENGTH_SHORT).show();
						wantToCloseDialog=false;
					}
	                  if(wantToCloseDialog)
	                      alertDialog.dismiss();
	              
	              }
	          });
				
				
				
			}
		});

		etlanguage.setText(myResume.language);
		etnationality.setText(myResume.nationality);
		etdob.setText(myResume.dob);
		etFatherName.setText(myResume.fatherName);
		etMotherName.setText(myResume.motherName);
		etTitle.setText(myResume.title);
		
		if (myResume.customFields != null && !myResume.customFields.isEmpty()){
			
			for (HashMap.Entry<String, String> entry : myResume.customFields.entrySet()){
				addCustomfield(entry.getKey().toString(), entry.getValue().toString());
			}
		}
		
	}
	
}
