package com.grexoft.resume.forms;

import java.io.File;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.grexoft.resume.R;
import com.grexoft.resume.Resume;
import com.grexoft.resume.helpers.Common_Utilty;
import com.grexoft.resume.helpers.SdCardManager;

public class Fragment_image extends ResumeFragment {

	protected static final int RESULT_LOAD_IMAGE = 1;

	private ImageView myImage;
	
	private Button upload_pic;
	
	private ImageView delete_pic;
	
	private boolean isImageSet;

	@Override
	protected void setLayoutId() {
		layoutId = R.layout.fragment_image;

	}

	@Override
	protected void init() {
		
		isImageSet = false;
		
		
		myImage = (ImageView) fragmentView.findViewById(R.id.imgView);

		upload_pic = (Button) fragmentView.findViewById(R.id.btn_upload_image);

		upload_pic.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				startActivityForResult(i, RESULT_LOAD_IMAGE);

			}
		});
		
		//myImage.setImageBitmap(AssetsToSdcard.getImage(myResume.image));
		String root = Environment.getExternalStorageDirectory().toString()
				+ "/"+Common_Utilty.APPLICATION_DIRECTORY+"/"+Common_Utilty.IMAGE_DIRECTORY;
		File file = new File(root+"/"+myResume.image);
		
		if(Common_Utilty.isNotEmptyString(myResume.image) &&file.exists()){
			
			
			isImageSet = true;
			
			myImage.setImageBitmap(SdCardManager.getImage(myResume.image));
			
		}else{
			myResume.image="";
			myImage.setImageResource(R.drawable.ic_default_image);
			isImageSet=false;
			
		}

		delete_pic = (ImageView) fragmentView.findViewById(R.id.btn_delete_image);

		delete_pic.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(
						getActivity());
				alertdialogbuilder.setTitle("Delete");
				alertdialogbuilder
						.setMessage("Are you sure, you want to delete this entry?");
				alertdialogbuilder.setPositiveButton(android.R.string.yes,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								

								myResume.image = "";
								
								
								myImage.setImageResource(R.drawable.ic_default_image);
								
								isImageSet = false;
				
							}
						});
				alertdialogbuilder.setNegativeButton(android.R.string.no,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();

							}
						});

				alertdialogbuilder.setIcon(android.R.drawable.ic_dialog_alert);
				alertdialogbuilder.show();

			}

		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_IMAGE
				&& resultCode == getActivity().RESULT_OK && data != null) {

			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getActivity().getContentResolver().query(
					selectedImage, filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);

			myImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
			
			isImageSet = true;
			
			cursor.close();

		}
	}

	@Override
	public boolean savedata() {
		
		if (isImageSet){
			
			BitmapDrawable profilePicBitmap = (BitmapDrawable) myImage
					.getDrawable();

			

			System.out.println("date of creation :" + myResume.date_of_creation);

			int index = myResume.date_of_creation.indexOf(" ");

			myResume.date_of_creation = Common_Utilty.replacement(myResume.date_of_creation, ":", "_");
			
			myResume.date_of_creation = Common_Utilty.replacement(myResume.date_of_creation, " ", "_");
			
			myResume.date_of_creation = Common_Utilty.replacement(myResume.date_of_creation, "-", "_");

			System.out.println("date of creation after replacement:"
					+ myResume.date_of_creation);

			String fileName = myResume.title + "_" + myResume.date_of_creation + ".jpg";

			
			myResume.image = fileName;

			System.out.println("image name: " + myResume.image);

			SdCardManager.saveBitmap(fileName, profilePicBitmap.getBitmap());
			
			
		}

		
			myResume.checkForm(Resume.IMAGE_FORM);
	
		
			
		System.out.println("insert save data my image: " + myResume.image);
		return true;

	}

	@Override
	public void addRecord() {
		// TODO Auto-generated method stub

	}

	

}
