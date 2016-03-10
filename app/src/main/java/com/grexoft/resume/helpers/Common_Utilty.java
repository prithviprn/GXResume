package com.grexoft.resume.helpers;

import java.util.Locale;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;

public class Common_Utilty {

	public static final String APPLICATION_DIRECTORY = "My Resume";

	public static final String PNG_DIRECTORY = "Png";

	public final static String FONT_DIRECTORY = "fonts";

	public static final String RESUME_DIRECTORY = "resumes";

	public static final String IMAGE_DIRECTORY = "images";

	public static final String APPLICATION_ID = "com.grexoft.resume";

	public static Boolean isNotEmptyString(String s) {
		if (s != null && !s.equals("")) {
			return true;
		} else {
			return false;
		}

	}

	public static String firstUpper(String s) {

		if (!isNotEmptyString(s))
			return s;

		String str;
		str = s.substring(0, 1).toUpperCase(Locale.US) + s.substring(1);

		return str;

	}

	public static Uri ResourceToUri(Context context, int resID) {
		return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
				+ context.getResources().getResourcePackageName(resID) + '/'
				+ context.getResources().getResourceTypeName(resID) + '/'
				+ context.getResources().getResourceEntryName(resID));
	}

	public static String replacement(String str, String find, String replace) {

		while (str.contains(find)) {

			int index = str.indexOf(find);

			str = str.substring(0, index) + replace + str.substring(index + 1);

		}

		return str;

	}

	static String getEmail(Context context) {
		AccountManager accountManager = AccountManager.get(context);
		Account account = getAccount(accountManager);

		if (account == null) {
			return null;
		} else {
			return account.name;
		}
	}

	private static Account getAccount(AccountManager accountManager) {
		Account[] accounts = accountManager.getAccountsByType("com.google");
		Account account;
		if (accounts.length > 0) {
			account = accounts[0];
		} else {
			account = null;
		}
		return account;
	}

}
