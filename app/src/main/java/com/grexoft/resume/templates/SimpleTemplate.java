package com.grexoft.resume.templates;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map.Entry;

import org.apache.commons.lang3.text.WordUtils;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.grexoft.resume.R;
import com.grexoft.resume.Resume;
import com.grexoft.resume.helpers.Common_Utilty;
import com.grexoft.resume.helpers.SdCardManager;

public class SimpleTemplate extends TemplateFragment {

	@Override
	protected int getLayoutId() {
		return R.layout.template_sample3;
	}

	@Override
	protected void afterResumeHead() {
		titles.add(name);
		headings.add(title);
	}
}
