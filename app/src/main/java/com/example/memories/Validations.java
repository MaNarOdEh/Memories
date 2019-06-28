package com.example.memories;

import android.text.TextUtils;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Validations {
    private static final String DATE_FORMAT="yyyy-MM-dd";
    public  static boolean isEmailValid(String email){
        return email!=null &&android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidPass(String password) {
        return !TextUtils.isEmpty(password);
    }

    public static boolean isValidName(String name) {
        return !TextUtils.isEmpty(name);
    }

    public static boolean isValidDate(String date) {
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public static String getWrongMessage(String errorCode) {
        switch (errorCode) {

            case "ERROR_INVALID_CUSTOM_TOKEN":
                return "The custom token format is incorrect. Please check the documentation";

            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                return ("The custom token corresponds to a different audience");

            case "ERROR_INVALID_CREDENTIAL":
                return ("The supplied auth credential is malformed or has expired.");

            case "ERROR_INVALID_EMAIL":
                return ("The email address is badly formatted.");


            case "ERROR_WRONG_PASSWORD":
                return ("The password is invalid or the user does not have a password");

            case "ERROR_USER_MISMATCH":
                return ("The supplied credentials do not correspond to the previously signed in user");

            case "ERROR_REQUIRES_RECENT_LOGIN":
                return ("This operation is sensitive and requires recent authentication.");

            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                return (" account already exists with the same email address but different sign-in credentials\n. Sign in using a provider associated with this email address.");

            case "ERROR_EMAIL_ALREADY_IN_USE":
                return ("The email address is already in use by another account.  ");

            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                return("This credential is already \nassociated with a different user account.");

            case "ERROR_USER_DISABLED":
                return ("The user account has been disabled by an administrator.");

            case "ERROR_USER_TOKEN_EXPIRED":
                return ("The user\\'s credential is no longer valid.\n The user must sign in again.");

            case "ERROR_USER_NOT_FOUND":
                return ("There is no user record corresponding to \nthis identifier. The user may have been deleted.");

            case "ERROR_INVALID_USER_TOKEN":
                return ("The user\\'s credential is no longer valid.\n The user must sign in again.");

            case "ERROR_OPERATION_NOT_ALLOWED":
                return ("This operation is not allowed. \nYou must enable this service in the console.");

            case "ERROR_WEAK_PASSWORD":
                return ("The given password is invalid.");

        }
      return "";
    }
}
