package com.everything.utils;

/**
 * Created by Mirek on 2016-03-21.
 */
public final class TextUtils {
    private TextUtils(){
    }

    public static boolean isTextNullOrEmpty(String text){
        return text == null || text.isEmpty() || text.equals("");
    }
}
