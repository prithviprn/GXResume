package com.grexoft.resume;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.plus.PlusShare;
import com.grexoft.resume.helpers.Common_Utilty;

public class GXSharer {

	private CallbackManager callbackManager;

	private ShareDialog shareDialog;

	private String shareDescription;

	public Context context;
	public Activity activity;

	private Uri shareImagePath;

	public GXSharer(Activity activity) {

		this.activity = activity;
		this.context = activity;
		init();

	}

	public void init() {

		shareDescription = null;

		shareImagePath = Common_Utilty.ResourceToUri(context,
				R.drawable.banner_1024_500);

		FacebookSdk.sdkInitialize(context);

		callbackManager = CallbackManager.Factory.create();

		shareDialog = new ShareDialog(activity);

		shareDialog.registerCallback(callbackManager,
				new FacebookCallback<Sharer.Result>() {

					@Override
					public void onSuccess(Sharer.Result result) {

						Toast.makeText(context,
								"Thank you for sharing.",
								Toast.LENGTH_SHORT).show();

					}

					@Override
					public void onCancel() {

					}

					@Override
					public void onError(FacebookException error) {

						error.printStackTrace();

					}

				});

	}

	public void shareThroughFacebook() {

		Uri imageUri = Uri.parse("http://www.grexoft.com/gxresume/banner_1024_500_hd.jpg");

		Uri contentUri = Uri
				.parse("https://play.google.com/store/apps/details?id=com.grexoft.resume");

		if (ShareDialog.canShow(ShareLinkContent.class)) {

			shareDescription = "GX Resume allows you to create your resume instantly with ultra decent templates.";

			ShareLinkContent myContent = new ShareLinkContent.Builder()
					.setContentDescription(shareDescription)
					.setContentTitle("Create your resume instantly!")
					.setContentUrl(contentUri).setImageUrl(imageUri).build();

			ShareDialog.show(activity, myContent);
		}
		Toast.makeText(context, "cannot open facebook", Toast.LENGTH_SHORT).show();

	}

	public void shareThroughGoogle() {

		Intent shareIntent = new PlusShare.Builder(context)
				.setType("text/plain")
				.setText(shareDescription)
				.setContentUrl(
						Uri.parse("https://play.google.com/store/apps/details?id=com.grexoft.resume&hl=en"))
				.getIntent();

		context.startActivity(shareIntent);

	}

	public void shareThroughOthers() {

		Intent share = new Intent();

		share.setAction(Intent.ACTION_SEND);

		share.setType("image/jpeg");

		shareDescription = shareDescription
				+ "\n"
				+ "https://play.google.com/store/apps/details?id=" + Common_Utilty.APPLICATION_ID + "&hl=en";

		share.putExtra(Intent.EXTRA_TEXT, shareDescription);

		share.putExtra(Intent.EXTRA_STREAM, shareImagePath);

		context.startActivity(Intent.createChooser(share,
				"Select a platform to share."));

	}

	public void shareThroughWhatsApp() {

		if (appInstalledOrNot("com.whatsapp")) {

			Intent waIntent = new Intent(Intent.ACTION_SEND);

			waIntent.setType("image/*");

			waIntent.setPackage("com.whatsapp");

			waIntent.putExtra(Intent.EXTRA_STREAM, shareImagePath);

			waIntent.putExtra(Intent.EXTRA_TITLE, "GX Resume");

			shareDescription = shareDescription
					+ "\n"
					+ "https://play.google.com/store/apps/details?id=" + Common_Utilty.APPLICATION_ID + "&hl=en";

			waIntent.putExtra(Intent.EXTRA_TEXT, shareDescription);

			context.startActivity(Intent.createChooser(waIntent, "Share with"));
		}

		else {

			Toast.makeText(context, "Please install whatsapp",
					Toast.LENGTH_SHORT).show();
		}

	}

	private boolean appInstalledOrNot(String uri) {
		PackageManager pm = context.getPackageManager();
		boolean app_installed;
		try {
			pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
			app_installed = true;
		} catch (PackageManager.NameNotFoundException e) {
			app_installed = false;
		}
		return app_installed;
	}

}
