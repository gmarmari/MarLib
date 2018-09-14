package marmaris.lib.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

public class MGToast {

    public enum MGToastDuration{
        Short,
        Long
    }

    private Toast toast;
    public Toast getToast() {
        return toast;
    }

    /**
     * Creates a new Toast
     * @param c : Context
     * @param message : the message of the toast
     * @return a new instace of MGToast
     */
    @SuppressLint("ShowToast")
    public static MGToast newInstance(Context c, String message){
        MGToast mgToast = new MGToast();
        if (c != null && message != null)
            mgToast.toast = Toast.makeText(c, message, Toast.LENGTH_SHORT);
        return mgToast;
    }

    /**
     * Creates a new Toast
     * @param c : Context
     * @param message : the message of the toast
     * @param duration : the duration of the toast
     * @return a new instace of MGToast
     */
    @SuppressLint("ShowToast")
    public static MGToast newInstance(Context c, String message, MGToastDuration duration){
        MGToast mgToast = new MGToast();
        if (c != null && message != null) {
            if (duration == MGToastDuration.Long)
                mgToast.toast = Toast.makeText(c, message, Toast.LENGTH_LONG);
            else
                mgToast.toast = Toast.makeText(c, message, Toast.LENGTH_SHORT);
        }
        return mgToast;
    }

    public void show(){
        if (toast != null)
            toast.show();
    }

}
