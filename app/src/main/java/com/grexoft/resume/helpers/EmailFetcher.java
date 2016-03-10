package com.grexoft.resume.helpers;

import java.util.regex.Pattern;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.util.Patterns;

/**
 * This class uses the AccountManager to get the primary email address of the
 * current user.
 */
public class EmailFetcher {

  public static String getEmail(Context context) {
    AccountManager accountManager = AccountManager.get(context); 
    Account account = getAccount(accountManager);

    if (account == null) {
      return null;
    } else {
      return account.name;
    }
  }

  private static Account getAccount(AccountManager accountManager) {
	Pattern emailPattern = Patterns.EMAIL_ADDRESS; 
    Account[] accounts = accountManager.getAccountsByType("com.google");
    Account account = null;
    for (Account ac : accounts) {
        if (emailPattern.matcher(ac.name).matches()) {
            account = ac;
            return account;
        }
        else{
        	account = null;
        }
    }
    return account;
  }
}