package com.grexoft.resume;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.grexoft.resume.helpers.Common_Utilty;
import com.grexoft.resume.helpers.EmailFetcher;
import com.grexoft.resume.helpers.ServerUtilities;
import com.grexoft.resume.inappbilling.IabHelper;
import com.grexoft.resume.inappbilling.IabResult;
import com.grexoft.resume.inappbilling.Purchase;
import com.grexoft.resume.model.ResumeTemplate;
import com.grexoft.resume.pdf.ClassicalResumePdf;
import com.grexoft.resume.pdf.ElegantResumePdf;
import com.grexoft.resume.pdf.ImpressiveResumePdf;
import com.grexoft.resume.pdf.LoftResumePdf;
import com.grexoft.resume.pdf.ProfessionalResumePdf;
import com.grexoft.resume.pdf.ResumePdf;
import com.grexoft.resume.pdf.SimpleResumePdf;
import com.grexoft.resume.pdf.StandOutResumePdf;
import com.grexoft.resume.pdf.StandardResumePdf;
import com.grexoft.resume.templates.ClassicalTemplate;
import com.grexoft.resume.templates.ElegantTemplate;
import com.grexoft.resume.templates.ImpressiveTemplate;
import com.grexoft.resume.templates.LoftTemplate;
import com.grexoft.resume.templates.NewTemplate;
import com.grexoft.resume.templates.ProfessionalTemplate;
import com.grexoft.resume.templates.SimpleTemplate;
import com.grexoft.resume.templates.StandOutTemplate;
import com.grexoft.resume.templates.StandardTemplate;
import com.grexoft.resume.templates.TemplateFragment;
import com.grexoft.resume.templates.TheTargetedTemplate;
import com.millennialmedia.InterstitialAd;
import com.millennialmedia.MMException;

public class TemplatePreviewActivity extends FragmentActivity implements
		OnTouchListener {
	
	private static final String TAG = "GXRESUME_TEMP_PREV";

	private boolean pdfWasOpened, adTurn;

	RelativeLayout purchaseTemplateLayout, getPdfLayout, viewPdfLayout,
			shareLayout, rateUsLayout, preferenceLayout;

	private View viewPdfAlfa, sharePdfAlfa;

	protected ResumePdf resumePdf;

	private String templateFileName;

	private GXSharer sharer;

	protected int templateId;
	private boolean isCreated = false;
	private boolean canShowImage;

	private TemplateFragment templateFragment;

	private TextView txtTemplateName;

	private String templateName;
	GXResumeApplication application;
	
	ResumeTemplate resumeTemplate;
	
	public static final int RC_PURCHASE_FLOW = 1001;
	private InterstitialAd interstitialAd;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.template_activity);

		adTurn = true;
		
		application = (GXResumeApplication)getApplication();
		
        Intent in = getIntent();
		
		templateId = in.getExtras().getInt("template_number");
		
		System.out.println("in template activity templateId" + templateId);
		
		
		resumeTemplate = application.getResumeTemplates().get(templateId);
		templateName = resumeTemplate.getTitle();
		System.out.println("in template activity name:" + templateName);
//		txtTemplateName.setText(templateName);

		txtTemplateName = (TextView) findViewById(R.id.title);

		purchaseTemplateLayout = (RelativeLayout)findViewById(R.id.purchase_template_layout);
		getPdfLayout = (RelativeLayout) findViewById(R.id.get_pdf_layout);
		viewPdfLayout = (RelativeLayout) findViewById(R.id.view_pdf_layout);
		rateUsLayout = (RelativeLayout) findViewById(R.id.rate_us_layout);
		shareLayout = (RelativeLayout) findViewById(R.id.share_layout);
		preferenceLayout = (RelativeLayout) findViewById(R.id.preference_layout);
		
		String email = EmailFetcher.getEmail(getApplicationContext());
		
		if(resumeTemplate.isFree() || !resumeTemplate.isFree() && resumeTemplate.isPurchased() || application.getExemptions().contains(email)){
			purchaseTemplateLayout.setVisibility(View.GONE);
			getPdfLayout.setVisibility(View.VISIBLE);
		}else{
			purchaseTemplateLayout.setVisibility(View.VISIBLE);
			getPdfLayout.setVisibility(View.GONE);
		}

		viewPdfAlfa = (View) findViewById(R.id.viewpdf_overlay_view);
		// viewPdfAlfa.setVisibility(View.GONE);
		viewPdfAlfa.setAlpha(0.5f);

		sharePdfAlfa = (View) findViewById(R.id.sharepdf_overlay_view);

		sharePdfAlfa.setAlpha(0.5f);

		sharer = new GXSharer(TemplatePreviewActivity.this);

		getPdfLayout.setOnTouchListener(this);

		viewPdfLayout.setOnTouchListener(this);

		rateUsLayout.setOnTouchListener(this);
		shareLayout.setOnTouchListener(this);
		preferenceLayout.setOnTouchListener(this);
		purchaseTemplateLayout.setOnTouchListener(this);

		loadTemplate();

		initAd();



	}

	@Override
	protected void onStart() {
		super.onStart();
		if (pdfWasOpened){
			pdfWasOpened = false;

			if (adTurn){
				adTurn = false;

				if (interstitialAd != null) {
					Toast.makeText(getApplicationContext(),"instertitial ad not null",Toast.LENGTH_SHORT).show();
					interstitialAd.load(this,null);
				}
			}
		}
	}

	private void initAd(){

		try{

		final InterstitialAd interstitialAd = InterstitialAd.createInstance("221284");
			this.interstitialAd = interstitialAd;
		interstitialAd.setListener(new InterstitialAd.InterstitialListener() {
			@Override
			public void onLoaded( final InterstitialAd interstitialAd) {
				Log. i(TAG, "Interstitial Ad loaded.");
				// Show the Ad using the display options you configured.
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							try {
								interstitialAd.show(getApplicationContext());
							} catch (MMException e) {
								e.printStackTrace();
							}
						}
					});

			}
			@Override
			public void onLoadFailed(InterstitialAd interstitialAd,
									 InterstitialAd.InterstitialErrorStatus errorStatus) {
				Log. i ( TAG , "Interstitial Ad load failed.");
			}
			@Override
			public void onShown(InterstitialAd interstitialAd) {
				Log. i ( TAG , "Interstitial Ad shown.");
			}
			@Override
			public void onShowFailed(InterstitialAd interstitialAd,
									 InterstitialAd.InterstitialErrorStatus errorStatus) {
				Log. i ( TAG , "Interstitial Ad show failed.");
			}
			@Override
			public void onClosed(InterstitialAd interstitialAd) {
				Log. i ( TAG , "Interstitial Ad closed.");
			}
			@Override
			public void onClicked(InterstitialAd interstitialAd) {
				Log. i ( TAG , "Interstitial Ad clicked.");
			}
			@Override
			public void onAdLeftApplication(InterstitialAd interstitialAd) {
				Log. i ( TAG , "Interstitial Ad left application.");
			}
			@Override
			public void onExpired(InterstitialAd interstitialAd) {
				Log. i ( TAG , "Interstitial Ad expired.");
			}
		});
	} catch (MMException e) {
		Log. e ( TAG , "Error creating interstitial ad", e);
// abort loading ad
	}

	}

	private void loadTemplate() {

		FragmentManager fManager = getSupportFragmentManager();

		FragmentTransaction fTransaction = fManager.beginTransaction();

		templateFragment = null;

		switch (templateId) {
		case TemplateChooserActivity.TEMPLATE_STAND_OUT:
			templateFragment = new StandOutTemplate();
			resumePdf = new StandOutResumePdf(getApplicationContext(),
					templateName);			
			break;
		case TemplateChooserActivity.TEMPLATE_LOFT:
			templateFragment = new LoftTemplate();
			resumePdf = new LoftResumePdf(getApplicationContext(), templateName);
			break;
		case TemplateChooserActivity.TEMPLATE_IMPRESSIVE:
			templateFragment = new ImpressiveTemplate();
			resumePdf = new ImpressiveResumePdf(getApplicationContext(),
					templateName);
			break;
		case TemplateChooserActivity.TEMPLATE_PROFESSIONAL:
			templateFragment = new ProfessionalTemplate();
			resumePdf = new ProfessionalResumePdf(getApplicationContext(),
					templateName);
			break;
		case TemplateChooserActivity.TEMPLATE_ELEGANT:
			templateFragment = new ElegantTemplate();
			resumePdf = new ElegantResumePdf(getApplicationContext(),
					templateName);
			break;
		case TemplateChooserActivity.TEMPLATE_CLASSIC:
			templateFragment = new ClassicalTemplate();
			resumePdf = new ClassicalResumePdf(getApplicationContext(),
					templateName);
			break;
		case TemplateChooserActivity.TEMPLATE_SIMPLE:
			templateFragment = new SimpleTemplate();
			resumePdf = new SimpleResumePdf(getApplicationContext(),
					templateName);
			break;
		case TemplateChooserActivity.TEMPLATE_STANDARD:
			templateFragment = new StandardTemplate();
			resumePdf = new StandardResumePdf(getApplicationContext(),
					templateName);
			break;

		case TemplateChooserActivity.TEMPLATE_THE_TARGETED:
			templateFragment = new TheTargetedTemplate();
			//resumePdf = new TheTargetedTemplate(getApplicationContext(), templateName);
				break;

			case TemplateChooserActivity.TEMPLATE_NEW:
				templateFragment = new NewTemplate();
				break;

		default:
			break;
		}

		canShowImage = application.getResumeTemplates().get(templateId)
				.isCanShowImage();
		txtTemplateName.setText(application.getResumeTemplates()
				.get(templateId).getTitle().toUpperCase(Locale.US));

		if (templateFragment != null) {

			fTransaction.replace(R.id.fragment_container, templateFragment)
					.commit();

		}

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		if (event.getAction() == event.ACTION_DOWN) {

			if ((v.getId() == R.id.view_pdf_layout && !isCreated)
					|| (v.getId() == R.id.share_layout && !isCreated))
				return false;

			v.setBackgroundColor(getResources().getColor(
					R.color.new_resume_color));
			return true;
		} else if (event.getAction() == event.ACTION_UP) {
			v.setBackgroundColor(getResources().getColor(R.color.green_screen));

			switch (v.getId()) {
			case R.id.purchase_template_layout:
				//Toast.makeText(getApplicationContext(), "sku is : " + resumeTemplate.skuDetails.getSku(), Toast.LENGTH_SHORT).show();
				application.getIabHelper().launchPurchaseFlow(this, resumeTemplate.skuDetails.getSku(), RC_PURCHASE_FLOW, new IabHelper.OnIabPurchaseFinishedListener() {
					
					@Override
					public void onIabPurchaseFinished(IabResult result, Purchase info) {
						
						if (result.isFailure()) {
							//Toast.makeText(getApplicationContext(),"some error occured",Toast.LENGTH_SHORT).show();
							System.out.println("purchase error : " + result.getMessage());
							return;
						}
//						Toast.makeText(getApplicationContext(),"Thanks for purchasing",Toast.LENGTH_SHORT).show();
//						System.out.println("purchase info : " + info);
//						
//						//String email = info.
//						HashMap<String, String> requestData = new HashMap<String, String>();
//						requestData.put("sku", info.getSku());
//						requestData.put("developer_payload", info.getDeveloperPayload());
//						requestData.put("gp_order_id", info.getOrderId());
//						//requestData.put(, value);
//						ServerUtilities newPurchaseRequest = new ServerUtilities(ServerUtilities.REQUEST_TYPE_NEW_PURCHASE, null);
						getPdfLayout.setVisibility(View.VISIBLE);
						purchaseTemplateLayout.setVisibility(View.GONE);
						resumeTemplate.setPurchased(true);
					}
				});
				break;
			case R.id.get_pdf_layout:

				new PdfGenerator().execute();

				break;

			case R.id.view_pdf_layout:
				if (isCreated) {

					String root = Environment.getExternalStorageDirectory()
							.toString()
							+ "/"
							+ Common_Utilty.APPLICATION_DIRECTORY
							+ "/"
							+ Common_Utilty.RESUME_DIRECTORY;

					File file = new File(root + "/" + templateFileName);
					if (file.exists()) {
						Intent target = new Intent(Intent.ACTION_VIEW);
						target.setDataAndType(Uri.fromFile(file),
								"application/pdf");
						target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
						Intent intent = Intent.createChooser(target,
								"Open File");
						try {
							startActivity(intent);
							pdfWasOpened = true;
						} catch (ActivityNotFoundException e) {
							Toast.makeText(getApplicationContext(),
									"Please install pdf reader",
									Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(getApplicationContext(),
								"No file found : " + templateFileName,
								Toast.LENGTH_SHORT).show();
					}
				}

				break;

			case R.id.rate_us_layout:

				Intent intent = new Intent(Intent.ACTION_VIEW);
				// Try Google play
				intent.setData(Uri.parse("market://details?id="
						+ Common_Utilty.APPLICATION_ID));
				if (!MyStartActivity(intent)) {
					// Market (Google play) app seems not installed, let's try
					// to open a webbrowser
					intent.setData(Uri
							.parse("https://play.google.com/store/apps/details?id="
									+ Common_Utilty.APPLICATION_ID));
					if (!MyStartActivity(intent)) {
						// Well if this also fails, we have run out of options,
						// inform the user.
						Toast.makeText(
								getApplicationContext(),
								"Could not open Android market, please install the market app.",
								Toast.LENGTH_SHORT).show();
					}
				}

				break;

			case R.id.share_layout:
				if (isCreated) {

					String root = Environment.getExternalStorageDirectory()
							.toString()
							+ "/"
							+ Common_Utilty.APPLICATION_DIRECTORY
							+ "/"
							+ Common_Utilty.RESUME_DIRECTORY;

					String[] mailto = { "example@mail.com" };
					String str = "file://" + root + "/" + templateFileName;
					Uri uri = Uri.parse(str);
					Intent emailIntent = new Intent(Intent.ACTION_SEND);
					emailIntent.putExtra(Intent.EXTRA_EMAIL, mailto);
					emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
					emailIntent.putExtra(Intent.EXTRA_TEXT, "");
					emailIntent.setType("application/pdf");
					emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
					startActivity(Intent.createChooser(emailIntent,
							"Send email using:"));

				}
				break;

			case R.id.preference_layout:
				final Resume resumeData = Resume.getInstance();

				AlertDialog.Builder alertdialog = new AlertDialog.Builder(
						TemplatePreviewActivity.this);

				final LinearLayout prefrence = (LinearLayout) getLayoutInflater()
						.inflate(R.layout.prefrence_dialogue, null);

				System.out.println("preferences : " + resumeData.getPreferences().toString());

				final Switch showTitle = (Switch) prefrence
						.findViewById(R.id.switch_title);

				final Switch showImage = (Switch) prefrence
						.findViewById(R.id.switch_image);
				LinearLayout showImageLayout = (LinearLayout) prefrence
						.findViewById(R.id.show_image_in_resume);



				final Spinner spinnerContentFont = (Spinner)prefrence.findViewById(R.id.spinner_content_font);

				for (int j = 0 ; j < spinnerContentFont.getAdapter().getCount() ; j++){
					System.out.println("content font at : " + j + " is : " + spinnerContentFont.getAdapter().getItem(j).toString());
					if (resumeData.getPreferences().getContentFont().equals(spinnerContentFont.getAdapter().getItem(j).toString())) spinnerContentFont.setSelection(j);
				}


				final Spinner spinnerHeadingFont = (Spinner)prefrence.findViewById(R.id.spinner_heading_font);
				for (int j = 0 ; j < spinnerHeadingFont.getAdapter().getCount() ; j++){
					System.out.println("content font at : " + j + " is : " + spinnerHeadingFont.getAdapter().getItem(j).toString());
					if (resumeData.getPreferences().getHeadingFont().equals(spinnerHeadingFont.getAdapter().getItem(j).toString())) spinnerHeadingFont.setSelection(j);
				}

				final Spinner spinnerTitleFont = (Spinner)prefrence.findViewById(R.id.spinner_title_font);
				for (int j = 0 ; j < spinnerContentFont.getAdapter().getCount() ; j++){
					System.out.println("content font at : " + j + " is : " + spinnerTitleFont.getAdapter().getItem(j).toString());
					if (resumeData.getPreferences().getTitleFont().equals(spinnerTitleFont.getAdapter().getItem(j).toString())) spinnerTitleFont.setSelection(j);
				}

				final SeekBar seekContentTextSize = (SeekBar)prefrence.findViewById(R.id.seek_text_size_content);
				final SeekBar seekHeadingTextSize = (SeekBar)prefrence.findViewById(R.id.seek_text_size_heading);
				final SeekBar seekTitleTextSize = (SeekBar)prefrence.findViewById(R.id.seek_text_size_title);

				seekContentTextSize.setOnSeekBarChangeListener(new ProgressChangeListener());
				seekHeadingTextSize.setOnSeekBarChangeListener(new ProgressChangeListener());
				seekTitleTextSize.setOnSeekBarChangeListener(new ProgressChangeListener());

				int progress = 20;
				switch (resumeData.preferences.getContentTextSize()){
					case 10 : progress = 20; break;
					case 11 : progress = 40; break;
					case 12 : progress = 60; break;
					case 13 : progress = 80; break;
					case 14 : progress = 100; break;
				}
				seekContentTextSize.setProgress(progress);

				switch (resumeData.preferences.getHeadingTextSize()){
					case 10 : progress = 20; break;
					case 11 : progress = 40; break;
					case 12 : progress = 60; break;
					case 13 : progress = 80; break;
					case 14 : progress = 100; break;
				}
				seekHeadingTextSize.setProgress(progress);

				switch (resumeData.preferences.getTitleTextSize()){
					case 14 : progress = 20; break;
					case 15 : progress = 40; break;
					case 16 : progress = 60; break;
					case 17 : progress = 80; break;
					case 18 : progress = 100; break;
				}
				seekTitleTextSize.setProgress(progress);

				showImage.setChecked(resumeData.getPreferences().isShowImage() && canShowImage
						&& Common_Utilty.isNotEmptyString(resumeData.image));

				showTitle.setChecked(resumeData.getPreferences().isShowTitle());

				alertdialog.setView(prefrence);

				if (!canShowImage)
					showImageLayout.setVisibility(View.GONE);

				alertdialog.setNegativeButton(android.R.string.cancel,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();

							}
						});
				alertdialog.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								// Resume resumeData = Resume.getInstance();
								resumeData.getPreferences().setShowImage(showImage.isChecked());
								Toast.makeText(TemplatePreviewActivity.this, "set show image : " + showImage.isChecked(), Toast.LENGTH_SHORT).show();
								resumeData.getPreferences().setShowTitle(showTitle.isChecked());

								resumeData.getPreferences().setContentFont(spinnerContentFont.getSelectedItem().toString());
								System.out.println("content font is : " + spinnerContentFont.getSelectedItem().toString());
								resumeData.getPreferences().setTitleFont(spinnerTitleFont.getSelectedItem().toString());
								System.out.println("title font is : " + spinnerTitleFont.getSelectedItem().toString());
								resumeData.getPreferences().setHeadingFont(spinnerHeadingFont.getSelectedItem().toString());
								System.out.println("heading font is : " + spinnerHeadingFont.getSelectedItem().toString());

								resumeData.getPreferences().setContentTextSize((seekContentTextSize.getProgress() / 20) + 9);
								resumeData.getPreferences().setHeadingTextSize((seekHeadingTextSize.getProgress() / 20) + 9);
								resumeData.getPreferences().setTitleTextSize((seekTitleTextSize.getProgress() / 20) + 13);

								resumeData.getPreferences().savePreferences();

								System.out.println("preferences after save : " + resumeData.getPreferences().toString());

								loadTemplate();

								viewPdfAlfa.setVisibility(View.VISIBLE);
								isCreated = false;

							}
						});
				alertdialog.create();
				alertdialog.show();

				break;

			default:
				break;
			}
			return true;
		}

		return false;
	}

	private class ProgressChangeListener implements SeekBar.OnSeekBarChangeListener {
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			if (fromUser){
				if (progress <= 20) seekBar.setProgress(20);
				else if (progress <= 40) seekBar.setProgress(40);
				else if (progress <= 60) seekBar.setProgress(60);
				else if (progress <= 80) seekBar.setProgress(80);
				else if (progress <= 100) seekBar.setProgress(100);
			}
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {

		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {

		}
	}

	private boolean MyStartActivity(Intent aIntent) {
		try {
			startActivity(aIntent);
			return true;
		} catch (ActivityNotFoundException e) {
			return false;
		}
	}

	private void ShowShareDialog(String tName) {
		templateFileName = tName;
		AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(
				TemplatePreviewActivity.this);

		LinearLayout view = (LinearLayout) getLayoutInflater().inflate(
				R.layout.facebook_share_screen, null);

		TextView txtMsg = (TextView) view.findViewById(R.id.txt_message);
		txtMsg.setText("Your pdf file saved in the directory  Sdcard/My Resume/resumes/"
				+ templateFileName);

		ImageView shareBtn = (ImageView) view.findViewById(R.id.img_share);

		shareBtn.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				ImageView iv = (ImageView) v;
				if (event.getAction() == event.ACTION_DOWN) {

					iv.setImageResource(R.drawable.ic_share_ontouch);
					return true;
				} else if (event.getAction() == event.ACTION_UP) {
					iv.setImageResource(R.drawable.ic_share);
					sharer.shareThroughOthers();
					return true;
				}
				return false;
			}
		});

		ImageView fb = (ImageView) view.findViewById(R.id.btn_facebook);
		fb.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				ImageView iv = (ImageView) v;
				if (event.getAction() == event.ACTION_DOWN) {

					iv.setImageResource(R.drawable.ic_fb_ontouch);
					return true;
				} else if (event.getAction() == event.ACTION_UP) {
					iv.setImageResource(R.drawable.ic_fb);
					sharer.shareThroughFacebook();
					return true;
				}
				return false;
			}
		});

		ImageView google = (ImageView) view.findViewById(R.id.btn_google);
		google.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				ImageView iv = (ImageView) v;
				if (event.getAction() == event.ACTION_DOWN) {

					iv.setImageResource(R.drawable.ic_google_plus_ontouch);
					return true;
				} else if (event.getAction() == event.ACTION_UP) {
					iv.setImageResource(R.drawable.ic_google_plus);
					sharer.shareThroughGoogle();
					return true;
				}
				return false;
			}
		});

		ImageView whatsapp = (ImageView) view.findViewById(R.id.btn_whatsapp);

		whatsapp.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				ImageView iv = (ImageView) v;
				if (event.getAction() == event.ACTION_DOWN) {

					iv.setImageResource(R.drawable.ic_whatsapp_on_touch);
					return true;
				} else if (event.getAction() == event.ACTION_UP) {
					iv.setImageResource(R.drawable.ic_whatsapp);
					sharer.shareThroughWhatsApp();
					return true;
				}
				return false;
			}
		});

		alertdialogbuilder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						dialog.dismiss();

					}
				});

		alertdialogbuilder.setView(view);
		alertdialogbuilder.setCancelable(false);

		alertdialogbuilder.show();

	}

	class PdfGenerator extends AsyncTask<Void, Void, Void> {
		ProgressDialog ring;
		String templateName = null;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			ring = new ProgressDialog(TemplatePreviewActivity.this);
			ring.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			// ring.setTitle("Please wait.....");
			ring.setMessage("Generating PDF....");
			ring.setCancelable(false);

			ring.show();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			templateFileName = resumePdf.createPdf();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			ring.dismiss();
			ShowShareDialog(templateFileName);
			isCreated = true;

			viewPdfAlfa.setVisibility(View.GONE);

			sharePdfAlfa.setVisibility(View.GONE);
		}
	}
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		if (application.getIabHelper() == null) return;		
		

	    // Pass on the activity result to the helper for handling
	    if (!application.getIabHelper().handleActivityResult(arg0, arg1, arg2)) {
	        // not handled, so handle it ourselves (here's where you'd
	        // perform any handling of activity results not related to in-app
	        // billing...
	        super.onActivityResult(arg0, arg1, arg2);
	    }
	}

}
