package com.everything;

import android.content.Context;
import android.content.res.Resources;
import android.text.Editable;
import android.util.DisplayMetrics;
import android.widget.EditText;

import com.everything.utils.TextUtils;

/**
 * Created by Mirek on 2016-04-19.
 */
public class Utils {
    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    public static int getIntValueFromEdittext(EditText editText){
        if(editText!=null){
            Editable editable = editText.getText();
            if(editable!=null) {
                String text = editText.getText().toString();
                int value = Integer.parseInt(text);
                return value;
            }else{
                return 0;
            }
        }else{
            return 0;
        }
    }

    public static boolean validateEdittext(EditText editText, String errorMessage) {
        if (editText != null && editText.getText() != null) {
            String text = editText.getText().toString();
            if (TextUtils.isTextNullOrEmpty(text)) {
                editText.setError(errorMessage);
                editText.requestFocus();
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }


}
