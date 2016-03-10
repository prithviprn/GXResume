package com.grexoft.resume.helpers;

import java.util.HashMap;

import org.json.JSONObject;

import android.os.AsyncTask;

public class ServerUtilities extends AsyncTask<String, String, String> {

	public static final String SERVER_ADDRESS = "http://www.grexoft.com/gxresume/api/";
	
	public static final String MEDIA_TYPE_JSON = "application/json; charset=utf-8";
	
	public static final String MEDIA_TYPE_FORM_X_WWW_URL_ENCODED = "application/x-www-form-urlencoded; charset=utf-8";

	public static final int REQUEST_TYPE_NEW_PURCHASE = 0;

	public static final int REQUEST_TYPE_VERIFY_PURCHASE = 1;

	private int requestType;

	private HashMap<String, String> parameters;

	private JSONObject response;

	public ServerUtilities(int requestType, HashMap<String, String> parameters) {
		this.requestType = requestType;
		this.parameters = parameters;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected String doInBackground(String... params) {
		String apiEndPoint = null;
		//MediaType mediaType = null;
		//FormEncoding formEncoding = null;
		switch (requestType) {
		case REQUEST_TYPE_NEW_PURCHASE:
			apiEndPoint = "new_order.php";
			//mediaType = MediaType.parse(MEDIA_TYPE_FORM_X_WWW_URL_ENCODED);
			break;
		case REQUEST_TYPE_VERIFY_PURCHASE:
			apiEndPoint = "verify_purchase.php";
			//mediaType = MediaType.parse(MEDIA_TYPE_JSON);
			//FormEncoding.Builder builder = new FormEncoding.Builder();
			
			break;
		default:
			return null;
		}
		
		
	    
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}

}
