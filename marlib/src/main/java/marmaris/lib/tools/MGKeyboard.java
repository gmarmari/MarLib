package marmaris.lib.tools;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import marmaris.lib.log.MGLogger;

public class MGKeyboard {

    public static void close(Context context){
        if (context == null) return;
        View view = ((Activity) context).getCurrentFocus();
        if (view == null) return;
        try {
            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if(inputManager != null) {
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        } catch (Exception e) {
            e.printStackTrace();
            MGLogger.appendLog(context, MGKeyboard.class.toString(), e);
        }

    }

}
