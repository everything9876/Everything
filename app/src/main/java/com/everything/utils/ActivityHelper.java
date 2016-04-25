package com.everything.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.everything.R;

/**
 * Created by Mirek on 2016-03-21.
 */
public class ActivityHelper {
    public static void showErrorDialog(String message, Context context) {
        if (context == null) {
            return;
        }
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle(context.getString(R.string.error));
        dialogBuilder.setMessage(message);
        dialogBuilder.setPositiveButton(context.getString(android.R.string.ok), null);
        dialogBuilder.show();
    }

    public static void hideKeyboard(Activity activity) {
        if(activity!=null) {
            View focus = activity.getCurrentFocus();
            if (focus != null) {
                InputMethodManager keyboard = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (keyboard != null) {
                    keyboard.hideSoftInputFromWindow(focus.getWindowToken(), 0);
                }
            }
        }
    }
}
