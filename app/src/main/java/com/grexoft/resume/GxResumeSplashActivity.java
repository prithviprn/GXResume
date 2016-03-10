package com.grexoft.resume;



import java.util.ArrayList;
import java.util.HashMap;

import android.R.color;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.grexoft.resume.helpers.Common_Utilty;
import com.grexoft.resume.helpers.DBHelper;
import com.grexoft.resume.helpers.EmailFetcher;
import com.grexoft.resume.inappbilling.IabHelper;
import com.grexoft.resume.inappbilling.IabResult;
import com.grexoft.resume.inappbilling.Inventory;
import com.grexoft.resume.inappbilling.Purchase;
import com.grexoft.resume.inappbilling.IabHelper.OnIabSetupFinishedListener;
import com.grexoft.resume.model.ResumeTemplate;
import com.millennialmedia.MMSDK;
import com.viewpagerindicator.CirclePageIndicator;



@SuppressWarnings("deprecation")
public class GxResumeSplashActivity extends FragmentActivity {
	public LinearLayout create;
	Button  saved;
	private MyAdapter mAdapter;
	private ViewPager mPager;
	public LinearLayout buttons;	
	
	Resume myResume;	

	public final static String TAG = "GX RESUME";

	private boolean setupStarted;
	
	GXResumeApplication application;
	
	private boolean canShowImage[] = {true,true,false,true,false,false,true,false};
	
	private boolean inventoryCheckSuccess;
	

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		MMSDK.initialize(this);
        
        application = (GXResumeApplication)getApplication();
    	sharedPreferences();
        	 
        create=(LinearLayout)findViewById(R.id.create);
        saved = (Button)findViewById(R.id.saved);
        
        String email = EmailFetcher.getEmail(this);
        
        
        buttons=(LinearLayout)findViewById(R.id.button_layout);
       
        
        create.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				if(event.getAction()==event.ACTION_DOWN){
					v.setBackgroundColor(getResources().getColor(R.color.on_touch));
					
				}
				else if(event.getAction()==event.ACTION_UP){
					v.setBackgroundColor(getResources().getColor(R.color.new_resume_color));
					createNewResumeDialog();

				}
				return true;
			}
		});       
       
       
        saved.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==event.ACTION_DOWN){
				
					v.setBackgroundColor(getResources().getColor(R.color.new_resume_color));
					saved.setTextColor(getResources().getColor(color.white));
					
				
				}
				else if(event.getAction()==event.ACTION_UP){
					v.setBackgroundDrawable(getResources().getDrawable(R.drawable.main_screen_btn_border));
					saved.setTextColor(getResources().getColor(R.color.new_resume_color));
					Intent intent = new Intent(getApplicationContext(), SavedActivity.class);
				startActivity(intent);


				}
				return true;
			}
		});
        
		
        
        
        
    }
	
	
    
	@SuppressWarnings("deprecation")
	private void sharedPreferences() {
      
		SharedPreferences SPrefs = getSharedPreferences(GXResumeApplication.MY_PREFS_NAME, MODE_PRIVATE);
		setContentView(R.layout.activity_main);
		final Animation animTranslate = AnimationUtils.loadAnimation(this,
				R.anim.anim_translate);
    	
    	final Animation animTranslateLeft=AnimationUtils.loadAnimation(this, R.anim.anim_translate_left);
    	
    	final Animation animAlpha = AnimationUtils.loadAnimation(this,
				R.anim.anim_alpha);
		 mPager = (ViewPager) findViewById(R.id.pager);
		final CirclePageIndicator titleIndicator = (CirclePageIndicator) findViewById(R.id.titles);
		if (SPrefs.getBoolean("first_time", true)) {
			Log.d("Comments", "First time");
			
			
			mAdapter = new MyAdapter(getSupportFragmentManager());
		
			
			mPager.setAdapter(mAdapter);
			
			
			mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
				
			
				
				@Override
				public void onPageSelected(int arg0) {
					System.out.println("on page selected : " + arg0);
					
				}
				
				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					switch (arg0) {
					case 0:
						if(arg1==0.0){
							titleIndicator.setStrokeColor(getResources().getColor(R.color.violet_screen));
							titleIndicator.setFillColor(getResources().getColor(R.color.violet_screen));	
						}
						break;
						
							
						
						
					case 1:
						if(arg1==0.0){
							buttons.setVisibility(View.INVISIBLE);
							titleIndicator.setStrokeColor(getResources().getColor(R.color.dark_gray_screen));
							titleIndicator.setFillColor(getResources().getColor(R.color.dark_gray_screen));
						}
						else{
							buttons.setVisibility(View.VISIBLE);
							buttons.setAlpha(arg1);
						}
						break;
					case 2:
						if(arg1==0.0){
							titleIndicator.setStrokeColor(getResources().getColor(R.color.green_screen));
							titleIndicator.setFillColor(getResources().getColor(R.color.green_screen));
						}
						buttons.setAlpha(1.0f);

					default:
						break;
					}
					
				}
				
				@Override
				public void onPageScrollStateChanged(int arg0) {
				
					System.out.println("on page scrolled changed" +arg0);
					
				}
			});
			
			titleIndicator.setViewPager(mPager);
			
			
			SPrefs.edit().putBoolean("first_time", false).commit();
		} else {
			mPager.setVisibility(View.GONE);
			titleIndicator.setVisibility(View.GONE);
			FragmentManager fragmentManager = getSupportFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.add(R.id.homeview, new FragmentSplash1()).commit();
			 new Handler().postDelayed(new Runnable()
		        {
		           @Override
		           public void run()
		           {
		        	   buttons.setVisibility(LinearLayout.VISIBLE);
		        	   buttons.setAlpha(1.0f);
		        	   animTranslateLeft.setDuration(1000);
		        	   create.startAnimation(animTranslateLeft);
		        	   animTranslate.setDuration(1000);
		        	   saved.startAnimation(animTranslate);
		           }
		        }, 3000);
		}
	}

	
	public class MyAdapter extends FragmentPagerAdapter {
		
		public MyAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public Fragment getItem(int position) {
			System.out.println("get item : " + position);
		
			switch (position) {
			case 0:		return new FragmentSplash1();
			case 1: 	return new FragmentSplash2();			
			case 2:		return new FragmentSplash3();
			default:	return null;
			}
		}
	}
	
	protected void createNewResumeDialog() {

		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(
				GxResumeSplashActivity.this);

		View view = getLayoutInflater().inflate(R.layout.new_resume, null);

		final EditText etResumeTitle = (EditText) view
				.findViewById(R.id.edit_resume_title);

		final EditText etResumePassword = (EditText) view
				.findViewById(R.id.edit_resume_password);

		dialogBuilder.setView(view);

		dialogBuilder.setCancelable(false);
		

		dialogBuilder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						dialog.dismiss();

					}
				});

		dialogBuilder.setPositiveButton("Create",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						String resumeTitle = etResumeTitle.getText().toString();

						String password = etResumePassword.getText().toString();

						if (!Common_Utilty.isNotEmptyString(resumeTitle)) {
							Toast.makeText(getApplicationContext(),
									"please enter title atleast",
									Toast.LENGTH_SHORT).show();
						}

						else {

							DBHelper dbHelper = new DBHelper(
									getApplicationContext());

							HashMap<String, String> resumeCredential = new HashMap<String, String>();

							resumeCredential.put("title", resumeTitle);

							resumeCredential.put("password", password);

							dbHelper.insertRowIntoTable(DBHelper.RESUMES,
									resumeCredential, -1);

							dialog.dismiss();

							Intent inten = new Intent(getApplicationContext(),
									InfoActivity.class);

							inten.putExtra("is_in_edit", true);

							inten.putExtra("resume_id",
									dbHelper.getLastInsertResumeId());

							startActivity(inten);

						}

					}
				});

		dialogBuilder.show();

	}
	
	

  
}
