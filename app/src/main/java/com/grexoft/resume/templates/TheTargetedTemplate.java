package com.grexoft.resume.templates;

import com.grexoft.resume.R;

/**
 * Created by Rehan on 01-03-2016.
 */
public class TheTargetedTemplate extends TemplateFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.template_sample9;
    }

    @Override
    protected void afterResumeHead() {
        titles.add(name);
        headings.add(title);
    }
}
