package marmaris.lib.tools;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;

public class MGColor {

    @SuppressWarnings("deprecation")
    public static int getColorAccordingToAndroidVersion(Context context, int colorId){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ContextCompat.getColor(context, colorId);
        }else{
            return context.getResources().getColor(colorId);
        }
    }

}
