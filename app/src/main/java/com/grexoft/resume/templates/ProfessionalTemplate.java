package com.grexoft.resume.templates;

import com.grexoft.resume.R;

public class ProfessionalTemplate extends TemplateFragment {

	@Override
	protected int getLayoutId() {
		return R.layout.template_sample5;
	}

	@Override
	protected void afterResumeHead() {
		titles.add(name);
		headings.add(title);
	}
}
