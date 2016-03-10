package com.grexoft.resume;

import java.util.ArrayList;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.grexoft.resume.forms.Fragment_activities;
import com.grexoft.resume.forms.Fragment_declaration;
import com.grexoft.resume.forms.Fragment_education;
import com.grexoft.resume.forms.Fragment_image;
import com.grexoft.resume.forms.Fragment_personnal;
import com.grexoft.resume.forms.Fragment_project;
import com.grexoft.resume.forms.Fragment_reference;
import com.grexoft.resume.forms.Fragment_research;
import com.grexoft.resume.forms.Fragment_skills;
import com.grexoft.resume.forms.Fragment_workExperience;
import com.grexoft.resume.forms.ResumeFragment;
import com.grexoft.resume.forms.fragment_contact;
import com.grexoft.resume.forms.fragment_objective;
import com.grexoft.resume.helpers.DBHelper;
import com.millennialmedia.InlineAd;
import com.millennialmedia.MMException;

@SuppressLint("NewApi")
@SuppressWarnings("deprecation")
public class InfoActivity extends ActionBarActivity implements OnTouchListener {

	private static final String TAG = "GXRESUME_INFO";

	Resume myResume;

	private RelativeLayout nextlLayout, prevLayout, plusLayout, templateLayout,
			synchLLayout;
	private ArrayList<RelativeLayout> overlay = new ArrayList<RelativeLayout>(3);

	private ResumeFragment fragmentsArray[];

	private int fragmentIcons[] = { R.drawable.ic_contact,
			R.drawable.ic_personal, R.drawable.ic_upload,
			R.drawable.ic_objective, R.drawable.ic_degree, R.drawable.ic_work,
			R.drawable.ic_project, R.drawable.ic_research,
			R.drawable.ic_skills, R.drawable.ic_extra_curricular,
			R.drawable.ic_declaration, R.drawable.ic_reference };

	private ImageView imgIcon;

	private TextView txtTitle;

	private int topOfFragment;
	private View prev_overlay, add_overlay, next_overlay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_info);

		imgIcon = (ImageView) findViewById(R.id.icon);

		myResume = Resume.getNewInstance();
		myResume.initializePreferences(getApplicationContext());

		myResume.dbHelper = new DBHelper(this);

		Bundle extras = getIntent().getExtras();

		boolean isInEdit = extras.getBoolean("is_in_edit");

		if (isInEdit) {
			myResume.resumeId = extras.getInt("resume_id");
			myResume.getResumeInfo();

		}

		templateLayout = (RelativeLayout) findViewById(R.id.template_layout);

		synchLLayout = (RelativeLayout) findViewById(R.id.synch_layout);

		nextlLayout = (RelativeLayout) findViewById(R.id.next_layout);

		prevLayout = (RelativeLayout) findViewById(R.id.prev_layout);

		plusLayout = (RelativeLayout) findViewById(R.id.add_layout);

		add_overlay = findViewById(R.id.add_overlay_view);
		prev_overlay = findViewById(R.id.prev_overlay_view);
		next_overlay = findViewById(R.id.next_overlay_view);

		fragmentsArray = new ResumeFragment[12];

		topOfFragment = -1;

		switchToFragment(0);

		synchLLayout.setOnTouchListener(this);
		nextlLayout.setOnTouchListener(this);
		templateLayout.setOnTouchListener(this);
		plusLayout.setOnTouchListener(this);
		prevLayout.setOnTouchListener(this);

		ImageView imgHelp = (ImageView) findViewById(R.id.img_help);

		imgHelp.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				ImageView iv = (ImageView) v;
				if (event.getAction() == event.ACTION_DOWN) {
					iv.setImageResource(R.drawable.ic_help_on_touch);
					// return true;
				} else if (event.getAction() == event.ACTION_UP) {
					iv.setImageResource(R.drawable.ic_help);

					AlertDialog.Builder alertDialog = new AlertDialog.Builder(
							InfoActivity.this);
					LinearLayout view = (LinearLayout) getLayoutInflater()
							.inflate(R.layout.fragment_help, null);
					alertDialog.setView(view);
					alertDialog.setCancelable(true);

					alertDialog.setNegativeButton("Close",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();

								}
							});

					alertDialog.show();

					// return true;
				}
				return true;
			}
		});

		addMMAd();

		// @Override
		// public void onClick(View v) {
		//
		// AlertDialog.Builder alertDialog = new
		// AlertDialog.Builder(InfoActivity.this);
		// LinearLayout view = (LinearLayout) getLayoutInflater().inflate(
		// R.layout.fragment_help, null);
		// alertDialog.setView(view);
		// alertDialog.setCancelable(true);
		//
		// alertDialog.setNegativeButton("Close", new
		// DialogInterface.OnClickListener() {
		//
		// @Override
		// public void onClick(DialogInterface dialog, int which) {
		// dialog.dismiss();
		//
		// }
		// });
		//
		//
		// alertDialog.show();
		//
		// }
		// });

		// next.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		//
		//
		// // fragmentTransaction.replace(R.id.fragmentcontainer,
		// fragment).addToBackStack(null).commit();
		// if(topOfFragment< fragmentsArray.length-1){
		//
		// fragmentsArray[topOfFragment].savedata();
		//
		// switchToFragment(topOfFragment+1);
		// }
		//
		// }
		// });
		//

		// template.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		//
		// fragmentsArray[topOfFragment].savedata();
		//
		// myResume.synchDatabase();
		//
		// Intent intent=new Intent(getApplicationContext(),
		// View_templates.class);
		//
		// startActivity(intent);
		// }
		// });

		// plus.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// fragmentsArray[topOfFragment].addRecord();
		//
		// }
		// });
		//
		// prev.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		//
		// if(topOfFragment >0){
		// switchToFragment(topOfFragment-1);
		// }
		// }
		// });
		//
	}

	private void addMMAd(){

		try {
			InlineAd inlineAd = InlineAd.createInstance ("221283" , (ViewGroup) findViewById(R.id.ad_container_inline));
			inlineAd.setListener(new InlineAd.InlineListener() {
				@Override
				public void onRequestSucceeded(InlineAd inlineAd) {
					Log.i(TAG, "Inline Ad loaded.");
				}
				@Override
				public void onRequestFailed(InlineAd inlineAd, InlineAd.InlineErrorStatus errorStatus) {
					Log. i ( TAG , errorStatus.toString());
				}
				@Override
				public void onClicked(InlineAd inlineAd) {
					Log. i ( TAG , "Inline Ad clicked.");
				}
				@Override
				public void onResize(InlineAd inlineAd, int width, int height) {
					Log. i ( TAG , "Inline Ad starting resize.");
				}
				@Override
				public void onResized(InlineAd inlineAd, int width, int height, boolean toOriginalSize) {
					Log. i ( TAG , "Inline Ad resized.");
				}
				@Override
				public void onExpanded(InlineAd inlineAd) {
					Log. i ( TAG , "Inline Ad expanded.");
				}
				@Override
				public void onCollapsed(InlineAd inlineAd) {
					Log. i ( TAG , "Inline Ad collapsed.");
				}
				@Override
				public void onAdLeftApplication(InlineAd inlineAd) {
					Log. i ( TAG , "Inline Ad left application.");
				}
			});

			// set a refresh rate of 30 seconds that will be applied after the first request
			if (inlineAd != null) {
				inlineAd.setRefreshInterval(30000);
				// The InlineAdMetadata instance is used to pass additional metadata to the server to
				// improve ad selection
				final InlineAd.InlineAdMetadata inlineAdMetadata = new InlineAd.InlineAdMetadata().
						setAdSize(InlineAd.AdSize.BANNER);
				inlineAd.request(inlineAdMetadata);
			}
		} catch (MMException e) {
			Log. e ( TAG , "Error creating inline ad", e);
		}
	}

	private void switchToFragment(int fragmentid) {
		System.out.println("inside switch to fragment overlay.size = "
				+ overlay.size());
		ResumeFragment fragment = null;
		txtTitle = (TextView) findViewById(R.id.title);
		resetDisabledList();
		switch (fragmentid) {
		case 0:
			overlay.add(prevLayout);
			overlay.add(plusLayout);

			prev_overlay.setVisibility(View.VISIBLE);
			add_overlay.setVisibility(View.VISIBLE);

			fragment = new fragment_contact();
			txtTitle.setText(getString(R.string.contact_heading).toUpperCase(Locale.US));

			break;
		case 1:
			overlay.add(plusLayout);
			add_overlay.setVisibility(View.VISIBLE);
			fragment = new Fragment_personnal();
			txtTitle.setText(getString(R.string.personal_heading).toUpperCase(Locale.US));

			break;

		case 2:
			overlay.add(plusLayout);
			add_overlay.setVisibility(View.VISIBLE);
			fragment = new Fragment_image();
			txtTitle.setText(getString(R.string.upload_image_heading).toUpperCase(Locale.US));
			break;

		case 3:
			overlay.add(plusLayout);
			add_overlay.setVisibility(View.VISIBLE);
			fragment = new fragment_objective();
			txtTitle.setText(getString(R.string.objective_heading).toUpperCase(Locale.US));
			break;
		case 4:

			fragment = new Fragment_education();
			txtTitle.setText(getString(R.string.education_heading).toUpperCase(Locale.US));
			break;
		case 5:
			fragment = new Fragment_workExperience();
			txtTitle.setText(getString(R.string.work_exp_heading)
					.toUpperCase(Locale.US));
			break;
		case 6:
			fragment = new Fragment_project();
			txtTitle.setText(getString(R.string.projects_heading).toUpperCase(Locale.US));
			break;
		case 7:
			fragment = new Fragment_research();
			txtTitle.setText(getString(R.string.research_heading).toUpperCase(Locale.US));
			break;
		case 8:
			overlay.add(plusLayout);
			add_overlay.setVisibility(View.VISIBLE);
			fragment = new Fragment_skills();
			txtTitle.setText(getString(R.string.skills_heading).toUpperCase(Locale.US));
			break;
		case 9:
			overlay.add(plusLayout);
			add_overlay.setVisibility(View.VISIBLE);
			fragment = new Fragment_activities();
			txtTitle.setText(getString(R.string.activities_heading)
					.toUpperCase(Locale.US));
			break;
		case 10:

			fragment = new Fragment_reference();
			txtTitle.setText(getString(R.string.references_heading).toUpperCase(Locale.US));
			break;
		case 11:
			overlay.add(plusLayout);
			overlay.add(nextlLayout);
			add_overlay.setVisibility(View.VISIBLE);
			next_overlay.setVisibility(View.VISIBLE);
			fragment = new Fragment_declaration();
			txtTitle.setText(getString(R.string.declaration_heading)
					.toUpperCase(Locale.US));
			break;

		}

		if (fragment != null) {

			fragmentsArray[fragmentid] = fragment;

			FragmentManager fManager = getSupportFragmentManager();

			FragmentTransaction fTransaction = fManager.beginTransaction();

			fTransaction.replace(R.id.fragmentcontainer, fragment).commit();

			topOfFragment = fragmentid;

			imgIcon.setImageResource(fragmentIcons[fragmentid]);

			// Toast.makeText(getApplicationContext(), topOfFragment + "",
			// Toast.LENGTH_SHORT).show();

		}
	}

	private void resetDisabledList() {

		overlay.clear();
		add_overlay.setVisibility(View.INVISIBLE);
		prev_overlay.setVisibility(View.INVISIBLE);
		next_overlay.setVisibility(View.INVISIBLE);

		add_overlay.setAlpha(0.4f);
		prev_overlay.setAlpha(0.4f);
		next_overlay.setAlpha(0.4f);
		System.out.println("inside resetList overlay.size = " + overlay.size());

	}

//	@Override
//	public void onBackPressed() {
//		// Toast.makeText(getApplicationContext(), "save changes before exit",
//		// Toast.LENGTH_SHORT).show();
//		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//				InfoActivity.this);
//		alertDialogBuilder.setTitle(getString(R.string.on_back_press));
//
//		alertDialogBuilder.setCancelable(false);
//
//		alertDialogBuilder.setPositiveButton(android.R.string.yes,
//				new DialogInterface.OnClickListener() {
//
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						if (fragmentsArray[topOfFragment].savedata()) {
//							myResume.synchDatabase();
//							InfoActivity.super.onBackPressed();
//						}
//
//					}
//				});
//		alertDialogBuilder.setNegativeButton(android.R.string.no,
//				new DialogInterface.OnClickListener() {
//
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//
//						InfoActivity.super.onBackPressed();
//					}
//				});
//		AlertDialog alertDialog = alertDialogBuilder.create();
//
//		alertDialog.show();
//
//		// super.onBackPressed();
//	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		if (overlay.contains(v)) {
			return false;
		}

		if (event.getAction() == event.ACTION_DOWN) {

			v.setBackgroundColor(getResources().getColor(R.color.on_touch));
		} else if (event.getAction() == event.ACTION_UP) {
			v.setBackgroundColor(getResources().getColor(
					R.color.progress_bar_color));
			switch (v.getId()) {
			case R.id.template_layout:
				if (fragmentsArray[topOfFragment].savedata()) {

					myResume.synchDatabase();

					Intent intent = new Intent(getApplicationContext(),TemplateChooserActivity.class);

					startActivity(intent);
				}
				break;
			case R.id.synch_layout:
				if (fragmentsArray[topOfFragment].savedata()) {
					myResume.synchDatabase();
				}

				break;
			case R.id.add_layout:
				fragmentsArray[topOfFragment].addRecord();
				break;
			case R.id.next_layout:
				if (topOfFragment < fragmentsArray.length - 1) {
					if (fragmentsArray[topOfFragment].savedata()) {
						switchToFragment(topOfFragment + 1);
					}

				}
				break;
			case R.id.prev_layout:
				if (topOfFragment > 0) {
					switchToFragment(topOfFragment - 1);
				}
				break;

			}

		}

		return true;
	}

}
