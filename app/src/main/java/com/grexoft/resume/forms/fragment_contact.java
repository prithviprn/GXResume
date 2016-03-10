package com.grexoft.resume.forms;

import org.apache.commons.lang3.text.WordUtils;

import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.grexoft.resume.R;
import com.grexoft.resume.Resume;

public class fragment_contact extends ResumeFragment {
	
	private EditText etPrimaryContact, etEmail;
	private EditText etStreet, etCity, etPincode, etState, etCountry;
	private EditText etStreet2, etCity2, etPincode2, etState2, etCountry2;
	private EditText etName;
	private SwitchCompat switchAddressPermanent;

	

	@Override
	public boolean savedata() {

		
		String name = WordUtils.capitalizeFully(etName.getText().toString());
		
		myResume.name = name;

		
		String primaryContact = etPrimaryContact.getText().toString();
		myResume.primarycontact = primaryContact;

		
		String email = etEmail.getText().toString();
		myResume.email = email;

		
		String Street = etStreet.getText().toString();
		myResume.street = Street;

		
		String City = etCity.getText().toString();
		myResume.city = City;

		
		String pincode = etPincode.getText().toString();
		myResume.pincode = pincode;

		
		String state = etState.getText().toString();
		myResume.state = state;

		
		String country = etCountry.getText().toString();
		myResume.country = country;

		
		

		if (!myResume.addressesAreSame) {
			
			String Street2 = etStreet2.getText().toString();
			myResume.street2 = Street2;

			
			String City2 = etCity2.getText().toString();
			myResume.city2 = City2;

			
			String pincode2 = etPincode2.getText().toString();
			myResume.pincode2 = pincode2;

			
			String state2 = etState2.getText().toString();
			myResume.state2 = state2;

			
			String country2 = etCountry2.getText().toString();
			myResume.country2 = country2;
			
		} else if (myResume.addressesAreSame) {
			
			myResume.street2 = Street;
			myResume.city2 = City;
			myResume.pincode2 = pincode;
			myResume.state2 = state;
			myResume.country2 = country;
		}

		myResume.checkForm(Resume.CONTACT_FORM);
		return true;

	}

	@Override
	public void addRecord() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setLayoutId() {
		
		layoutId = R.layout.fragment_contact;
		
	}

	@Override
	protected void init() {
		
		etName = (EditText) fragmentView.findViewById(R.id.etname);
		etPrimaryContact = (EditText) fragmentView
				.findViewById(R.id.etprimarycontact);
		etEmail = (EditText) fragmentView.findViewById(R.id.etemail);

		etStreet = (EditText) fragmentView.findViewById(R.id.et_street);
		etCity = (EditText) fragmentView.findViewById(R.id.et_city);
		etPincode = (EditText) fragmentView.findViewById(R.id.et_pincode);
		etState = (EditText) fragmentView.findViewById(R.id.et_state);
		etCountry = (EditText) fragmentView.findViewById(R.id.et_country);

		etStreet2 = (EditText) fragmentView.findViewById(R.id.et_street2);
		etCity2 = (EditText) fragmentView.findViewById(R.id.et_city2);
		etPincode2 = (EditText) fragmentView.findViewById(R.id.et_pincode2);
		etState2 = (EditText) fragmentView.findViewById(R.id.et_state2);
		etCountry2 = (EditText) fragmentView.findViewById(R.id.et_country2);

		switchAddressPermanent = (SwitchCompat) fragmentView.findViewById(R.id.switch_address_permanent);

		if (myResume.addressesAreSame) {

			switchAddressPermanent.setChecked(false);
			
			etStreet2.setVisibility(View.GONE);
			etCity2.setVisibility(View.GONE);
			etPincode2.setVisibility(View.GONE);
			etState2.setVisibility(View.GONE);
			etCountry2.setVisibility(View.GONE);
			
		} else if (!myResume.addressesAreSame) {
			
			switchAddressPermanent.setChecked(true);
			
			etStreet2.setVisibility(View.VISIBLE);
			etCity2.setVisibility(View.VISIBLE);
			etPincode2.setVisibility(View.VISIBLE);
			etState2.setVisibility(View.VISIBLE);
			etCountry2.setVisibility(View.VISIBLE);
			
		}
		
		switchAddressPermanent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
				
				
				if (isChecked){
					etStreet2.setVisibility(View.VISIBLE);
					etCity2.setVisibility(View.VISIBLE);
					etPincode2.setVisibility(View.VISIBLE);
					etState2.setVisibility(View.VISIBLE);
					etCountry2.setVisibility(View.VISIBLE);

					myResume.addressesAreSame = false;
				}
				
				else{
					
					etStreet2.setVisibility(View.GONE);
					etCity2.setVisibility(View.GONE);
					etPincode2.setVisibility(View.GONE);
					etState2.setVisibility(View.GONE);
					etCountry2.setVisibility(View.GONE);
					
					myResume.addressesAreSame = true;
				}
				
			}
		});

		

		etName.setText(myResume.name);
		etPrimaryContact.setText(myResume.primarycontact);
		etEmail.setText(myResume.email);

		etStreet.setText(myResume.street);
		etCity.setText(myResume.city);
		etPincode.setText(myResume.pincode);
		etState.setText(myResume.state);
		etCountry.setText(myResume.country);

		etStreet2.setText(myResume.street2);
		etCity2.setText(myResume.city2);
		etPincode2.setText(myResume.pincode2);
		etState2.setText(myResume.state2);
		etCountry2.setText(myResume.country2);
		
	}

}
