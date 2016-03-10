package com.grexoft.resume.helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;



public class SdCardManager {
	
	
	 public static void CopyAssetsFontsToSdCard(Context context) {
	        AssetManager assetManager = context.getAssets();
	        String[] files = null;
	        try {
	            files = assetManager.list("fonts");
	            
	        } catch (IOException e) {
	            Log.e("tag", e.getMessage());
	        }
	        
	        String root = Environment.getExternalStorageDirectory().toString() + "/";
	        
	        File applicationDirectory = new File(root + Common_Utilty.APPLICATION_DIRECTORY );
	        
	        applicationDirectory.mkdirs();
	        
	        System.out.println(applicationDirectory.getPath());
	        
	        
	        File fontsDirectory = new File(applicationDirectory.getPath() + "/" + Common_Utilty.FONT_DIRECTORY);
	        
	        fontsDirectory.mkdirs();
	        
	 	           
	        
	        
	        System.out.println(fontsDirectory.getPath());
	        
	        File fontFile;

	        for(String filename : files) {
	            System.out.println("File name => "+filename);
	            InputStream in = null;
	            FileOutputStream out = null;
	            try {
	            	fontFile = new File(fontsDirectory, filename);
	            	if (fontFile.exists())
	    	        fontFile.delete();
	              in = (InputStream) assetManager.open("fonts/"+filename);   // if files resides inside the "Files" directory itself
	              out = new FileOutputStream(fontFile);
	              copyFile(in, out);
	              in.close();
	              in = null;
	              out.flush();
	              out.close();
	              out = null;
	            } catch(Exception e) {
	                //Log.e("tag", e.getMessage());
	            	e.printStackTrace();
	            }
	        }
	    }
	 public static void CopyAssetsImageToSdCard(Context context) {
	        AssetManager assetManager = context.getAssets();
	       String image[]=null;
	        try {
	            	image=assetManager.list("Png");
	           
	            
	        } catch (IOException e) {
	            Log.e("tag", e.getMessage());
	        }
	        
	        
	        
	        String root = Environment.getExternalStorageDirectory().toString() + "/"+Common_Utilty.APPLICATION_DIRECTORY;
	        
	        
	        
	        File imageDirectory = new File( root+ "/" + Common_Utilty.IMAGE_DIRECTORY +"/"+Common_Utilty.PNG_DIRECTORY);
	        
	        imageDirectory.mkdirs();
	        
	        File imageFile;

	        for(String filename : image) {
	            System.out.println("File name => "+filename);
	            InputStream in = null;
	            FileOutputStream out = null;
	            try {
	            	imageFile = new File(imageDirectory, filename);
	            	if (imageFile.exists())
	    	       imageFile.delete();
	              in = (InputStream) assetManager.open("Png/"+filename);   // if files resides inside the "Files" directory itself
	              out = new FileOutputStream(imageFile);
	              copyFile(in, out);
	              in.close();
	              in = null;
	              out.flush();
	              out.close();
	              out = null;
	            } catch(Exception e) {
	                //Log.e("tag", e.getMessage());
	            	e.printStackTrace();
	            }
	        }
	    }
	    private static void copyFile(InputStream in, FileOutputStream out) throws IOException {
	        byte[] buffer = new byte[1024];
	        int read;
	        while((read = in.read(buffer)) != -1){
	          out.write(buffer, 0, read);
	        }
	    }
	    
	    public static void deleteDirectory( File dir )
		{

		    if ( dir.isDirectory() )
		    {
		        String [] children = dir.list();
		        for ( int i = 0 ; i < children.length ; i ++ )
		        {
		         File child =    new File( dir , children[i] );
		         if(child.isDirectory()){
		             deleteDirectory( child );
		             child.delete();
		         }else{
		             child.delete();

		         }
		        }
		        dir.delete();
		    }
		}
	    
	    public static void saveBitmap(String name, Bitmap bitmap) {

			String root = Environment.getExternalStorageDirectory().toString()
					+ "/";

			File applicationDirectory = new File(root
					+ Common_Utilty.APPLICATION_DIRECTORY);
			
			System.out.println("name :" + name);

			applicationDirectory.mkdirs();

			System.out.println(applicationDirectory.getPath());

			File imagesDirectory = new File(applicationDirectory.getPath() + "/"
					+ Common_Utilty.IMAGE_DIRECTORY);

			imagesDirectory.mkdirs();
			
			File file = new File(imagesDirectory, name);
			
			if (file.exists())
				file.delete();
			
			OutputStream out = null;
			
			try {
				out = new FileOutputStream(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			bitmap.compress(CompressFormat.JPEG, 100, out);
			
			try {
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		}
		public static Bitmap getImage(String name){
			String root = Environment.getExternalStorageDirectory().toString()
					+ "/"+Common_Utilty.APPLICATION_DIRECTORY+"/"+Common_Utilty.IMAGE_DIRECTORY;
			File f = new File(root+"/"+name);
			
			
			
			Bitmap bmp = BitmapFactory.decodeFile(f.getAbsolutePath());
		
			
			return bmp;
		}
	}


