package com.grexoft.resume;


import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.grexoft.resume.inappbilling.IabHelper;
import com.grexoft.resume.inappbilling.IabHelper.OnIabSetupFinishedListener;
import com.grexoft.resume.inappbilling.IabResult;
import com.grexoft.resume.inappbilling.Inventory;
import com.grexoft.resume.model.ResumeTemplate;

public class TemplateChooserActivity extends AppCompatActivity implements OnTouchListener {
	
	public static final int TEMPLATE_CLASSIC = 0;
	public static final int TEMPLATE_SIMPLE = 1;
	public static final int TEMPLATE_STANDARD = 2;
	public static final int TEMPLATE_THE_TARGETED = 3;
	public static final int TEMPLATE_NEW = 4;
	public static final int TEMPLATE_ELEGANT = 5;
	public static final int TEMPLATE_PROFESSIONAL = 6;
	public static final int TEMPLATE_IMPRESSIVE = 7;
	public static final int TEMPLATE_LOFT = 8;
	public static final int TEMPLATE_STAND_OUT = 9;


	ImageView ImgPrev, ImgNext;

	TextView Preview;

	private int totalTemplates;

	private int currentTemplateItem;

	private String currentViewTemplate;

	private ViewPager viewPager;
	
	private String  templateName;
	
	private ArrayList<String> skus;
	
	public final static String TAG = "GX RESUME";

	GXResumeApplication application;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.template);
		
		application = (GXResumeApplication)getApplication();
		
		System.out.println("resume template list : " + ( application.getResumeTemplates() != null ? application.getResumeTemplates().toString() : null));
		
		if(application.getResumeTemplates() == null || application.getResumeTemplates().isEmpty()){
			
			final IabHelper billingHelper = new IabHelper(this,
					getString(R.string.base64_public_key));
			
			application.setIabHelper(billingHelper);

			billingHelper.enableDebugLogging(true, TAG);
			
			final ProgressDialog progressDialog = new ProgressDialog(this);
			progressDialog.setCancelable(false);
			progressDialog.setMessage("Loading templates. Please wait...");
			progressDialog.show();			

			billingHelper.startSetup(new OnIabSetupFinishedListener() {

				@Override
				public void onIabSetupFinished(IabResult result) {

					if (result.isSuccess()) {
						
						skus = new ArrayList<String>();

						//skus.add("resume_template_006");
						skus.add("resume_template_005");
						skus.add("resume_template_004");
						skus.add("resume_template_003");
						skus.add("resume_template_002");
						skus.add("resume_template_001");
						billingHelper.queryInventoryAsync(true, skus, new IabHelper.QueryInventoryFinishedListener() {
							
							@Override
							public void onQueryInventoryFinished(IabResult result, Inventory inv) {
								
								if (result.isFailure()){
									addFreeTemplates();
									progressDialog.cancel();
									initViewPager();
								}
								
								else {
																		
									application.setResumeTemplates(new ArrayList<ResumeTemplate>());
									
									addFreeTemplates();
									
									application.getResumeTemplates().add(
											new ResumeTemplate(
													getString(R.string.elegant_resume),
													false,
													false,
													R.drawable.il_elegant,
													(inv.getPurchase(skus.get(0)) == null ? false : true),
													inv.getSkuDetails(skus.get(0))
											)
									);
									
									
									application.getResumeTemplates().add(
											new ResumeTemplate(
													getString(R.string.professional_resume),
													false,
													true,
													R.drawable.il_professional,
													(inv.getPurchase(skus.get(1)) == null ? false : true),
													inv.getSkuDetails(skus.get(1))
													)
											);
									
									application.getResumeTemplates().add(
											new ResumeTemplate(
													getString(R.string.impressive_resume),
													false,
													false,
													R.drawable.il_impressive,
													(inv.getPurchase(skus.get(2)) == null ? false : true),
													inv.getSkuDetails(skus.get(2))
													)
											);
									
									application.getResumeTemplates().add(
											new ResumeTemplate(
													getString(R.string.loft_resume),
													false,
													true,
													R.drawable.il_loft,
													(inv.getPurchase(skus.get(3)) == null ? false : true),
													inv.getSkuDetails(skus.get(3))
															)
											);
									
									application.getResumeTemplates().add(
											new ResumeTemplate(
													getString(R.string.stand_out_resume),
													false,
													true,
													R.drawable.il_stand_out,
													(inv.getPurchase(skus.get(4)) == null ? false : true),
													inv.getSkuDetails(skus.get(4))
													)
											);
									

									
									
									
									
									
									
									
									
									progressDialog.cancel();
									initViewPager();
								}
								
								
								
							}
						});
					}
					else {
						Log.v(TAG, "Iab Helper startup failed");
						addFreeTemplates();
						progressDialog.cancel();
						initViewPager();
						
					}

				}
			});
			
		}
		else{
			initViewPager();
		}
		

	}
	
	private void addFreeTemplates(){		
		if(application.getResumeTemplates() == null){
			application.setResumeTemplates(new ArrayList<ResumeTemplate>());
		}
		application.getResumeTemplates().add(new ResumeTemplate(getString(R.string.classical_resume), true, false, R.drawable.il_classical, true, null));
		application.getResumeTemplates().add(new ResumeTemplate(getString(R.string.simple_resume), true, true, R.drawable.il_simple, true, null));
		application.getResumeTemplates().add(new ResumeTemplate(getString(R.string.standard_resume), true, false, R.drawable.il_standard, true, null));
		application.getResumeTemplates().add(new ResumeTemplate(getString(R.string.targeted_resume),true,true,R.drawable.il_stand_out,true,null));
		application.getResumeTemplates().add(new ResumeTemplate(getString(R.string.new_resume),true,true,R.drawable.il_elegant,true,null));
	}
	
	private void initViewPager(){
		
		totalTemplates = application.getResumeTemplates().size();
		
		for(ResumeTemplate resumeTemplate : application.getResumeTemplates()){
			System.out.println("title : " + resumeTemplate.getTitle());
			System.out.println("price : " + ( resumeTemplate.isFree() ? "free" : resumeTemplate.skuDetails.getPrice()));
		}
		
		viewPager = (ViewPager) findViewById(R.id.templates_container);
		viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));

		currentTemplateItem = 0;
		
		templateName = application.getResumeTemplates().get(0).getTitle();
		viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				currentTemplateItem = arg0;
				System.out.println("onPageSelected :" + currentTemplateItem);
				templateName = application.getResumeTemplates().get(arg0).getTitle();
				
				System.out.println("template number : " + currentTemplateItem + " template name : " + templateName);
				

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

		

		ImgPrev = (ImageView) findViewById(R.id.btn_prev);
		// ImgPrev.setOnClickListener(this);
		ImgNext = (ImageView) findViewById(R.id.btn_next);
		// ImgNext.setOnClickListener(this);
		Preview = (TextView) findViewById(R.id.tv_template_select);
		// Preview.setOnClickListener(this);

		ImgPrev.setOnTouchListener(this);
		ImgNext.setOnTouchListener(this);
		Preview.setOnTouchListener(this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.template, menu);
	    return super.onCreateOptionsMenu(menu);
	}

	public class MyAdapter extends FragmentPagerAdapter {

		

		public MyAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return application.getResumeTemplates().size();
		}

		@Override
		public Fragment getItem(int position) {
			System.out.println("get item : " + position);

			ResumeTemplate template = application.getResumeTemplates().get(position);
			
			int imageId = template.getTemplateImageId();
			currentViewTemplate = template.getTitle();
			String price = template.isFree() ? "Free" : template.skuDetails.getPrice();
			return new TemplateInformationFragment(imageId,currentViewTemplate, price);
			
		}
	}

	@SuppressWarnings({ "deprecation", "deprecation" })
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == event.ACTION_DOWN) {
			if (v.getId() == R.id.btn_next)
				((ImageView) v).setImageDrawable(getResources().getDrawable(
						R.drawable.temp_next_click));
			else if (v.getId() == R.id.btn_prev)
				((ImageView) v).setImageDrawable(getResources().getDrawable(
						R.drawable.temp_prev_click));
			return true;
		} else if (event.getAction() == event.ACTION_UP) {
			
			switch (v.getId()) {

			case R.id.btn_next:
				((ImageView) v).setImageDrawable(getResources().getDrawable(
						R.drawable.temp_next));
				if (currentTemplateItem == totalTemplates - 1)
					break;
				viewPager.setCurrentItem(currentTemplateItem + 1, true);
				break;

			case R.id.btn_prev:
				((ImageView) v).setImageDrawable(getResources().getDrawable(
						R.drawable.temp_prev));
				if (currentTemplateItem == 0)
					break;
				viewPager.setCurrentItem(currentTemplateItem - 1, true);
				break;

			case R.id.tv_template_select:
				Intent inten;

				inten = new Intent(getApplicationContext(),
						TemplatePreviewActivity.class);

				inten.putExtra("template_number", currentTemplateItem);
				
				inten.putExtra("template_name", templateName);
				
				System.out.println("in preview current temp item:" + currentTemplateItem);
				System.out.println("in preview temp name:" + templateName);

				startActivity(inten);

			}

			return true;
		}
		return false;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int responseCode,
			Intent intent) {
		if (application.getIabHelper() == null) return;

	    // Pass on the activity result to the helper for handling
	    if (!application.getIabHelper().handleActivityResult(requestCode, responseCode, intent)) {
	        // not handled, so handle it ourselves (here's where you'd
	        // perform any handling of activity results not related to in-app
	        // billing...
	    	if (responseCode == RESULT_FIRST_USER) {
				if (requestCode == 2) {
					finish();
				}
			}
	        super.onActivityResult(requestCode, responseCode, intent);
	    }
		
		super.onActivityResult(requestCode, responseCode, intent);
	}

}
