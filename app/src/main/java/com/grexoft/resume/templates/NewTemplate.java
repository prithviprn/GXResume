package com.grexoft.resume.templates;


import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grexoft.resume.R;
import com.grexoft.resume.helpers.Common_Utilty;

import org.apache.commons.lang3.text.WordUtils;

import java.util.Locale;


/**
 * Created by Rehan on 02-03-2016.
 */
public class NewTemplate extends TemplateFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.template_sample10;
    }

    @Override
    protected void afterResumeHead() {
        titles.add(name);
        headings.add(title);
    }

    @Override
    protected void addResumeHead() {

        TextView name = (TextView) findViewById(R.id.txt_name);
        TextView signature = (TextView) findViewById(R.id.txt_signature);
        if (Common_Utilty.isNotEmptyString(resumeData.name)) {
            name.setText(resumeData.name.toUpperCase(Locale.US));
            signature.setText(resumeData.name.toUpperCase(Locale.US));
        } else {
            name.setText("Your Name");
            signature.setText("Your Signature");
        }

        titles.add(name);

        TextView title = (TextView) findViewById(R.id.txt_title);

        if (Common_Utilty.isNotEmptyString(resumeData.title) && resumeData.getPreferences().isShowTitle()) {
            headings.add(title);
            title.setText(WordUtils.capitalize(resumeData.title));
        } else {
            title.setVisibility(View.GONE);
        }


        TextView address = (TextView) findViewById(R.id.txt_address_email_mobile);
        String presentAddressStr = "";
        String emailStr = "";
        String mobileStr = "";

        if (Common_Utilty.isNotEmptyString(resumeData.city)) {
            if (Common_Utilty.isNotEmptyString(resumeData.street)) {
                presentAddressStr = presentAddressStr + resumeData.street + ", ";

            }
            presentAddressStr = presentAddressStr + resumeData.city;

            if (Common_Utilty.isNotEmptyString(resumeData.pincode)) {
                presentAddressStr = presentAddressStr + "-" + resumeData.pincode;
            }
        }

        if (Common_Utilty.isNotEmptyString(resumeData.city)
                && Common_Utilty.isNotEmptyString(resumeData.country)) {
            presentAddressStr = presentAddressStr + ", ";
        }

        if (Common_Utilty.isNotEmptyString(resumeData.country)) {

            if (Common_Utilty.isNotEmptyString(resumeData.state)) {
                presentAddressStr = presentAddressStr + resumeData.state + ", ";

            }
            presentAddressStr = presentAddressStr + resumeData.country;
        }

        if (Common_Utilty.isNotEmptyString(resumeData.email)) {
            emailStr = emailStr + "\n" + resumeData.email;
        }

        if (Common_Utilty.isNotEmptyString(resumeData.primarycontact)) {
            mobileStr = mobileStr + "  " + resumeData.primarycontact;
        }

        if (!presentAddressStr.equals("") || !emailStr.equals("")
                || !mobileStr.equals("")) {
            address.setText(presentAddressStr + emailStr + mobileStr);
        } else {
            address.setVisibility(View.GONE);
        }

        contents.add(address);

    }

    @Override
    protected View getPersonalItem(String key, String value) {
        ViewGroup personalItem = (ViewGroup)getLayoutInflater().inflate(
                R.layout.personal_view, null);

        TextView tvAttr = (TextView) personalItem.findViewById(R.id.tv_attribute);

        tvAttr.setText(key);
        tvAttr.setGravity(Gravity.RIGHT);

        TextView tvValue = (TextView) personalItem.findViewById(R.id.tv_value);
        tvValue.setText(value);

        contents.add(tvAttr);
        contents.add(tvValue);

        return personalItem;
    }
}
