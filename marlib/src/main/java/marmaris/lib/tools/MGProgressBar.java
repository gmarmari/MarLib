package marmaris.lib.tools;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.util.Timer;
import java.util.TimerTask;

import marmaris.libs.R;

public class MGProgressBar {

    private static final int MAX_TIME = 120000;

    public interface Listener{
        void onCanceled();
    }
    private Listener mListener;
    public void setListener(Listener mListener){
        this.mListener = mListener;
    }

    private Context context;
    private ProgressDialog dialog;
    private Timer timer;

    private boolean isCanceled;
    public boolean isCanceled() {
        return isCanceled;
    }

    /**
     * A ProgressDialog with a message and (if allowCancel) a cancel button
     * @param context : Context
     * @param message : the progress dialog message
     * @param allowCancel : boolean to indicate if the cancel button will be shown
     */
    public MGProgressBar(Context context, String message, boolean allowCancel) {
        this.context = context;
        dialog = new ProgressDialog(context);
        dialog.setMessage(message);
        dialog.setCancelable(false);
        isCanceled = false;
        if (allowCancel)
            setCancelButton();
    }

    private void setCancelButton(){
        if (dialog == null)
            return;

        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, context.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                isCanceled = true;
                if (mListener != null)
                    mListener.onCanceled();
            }
        });
    }

    /**
     * Set the message of the progress dialog
     * @param message : a string message
     */
    public void setMessage(String message) {
        if (dialog != null && message != null)
            dialog.setMessage(message);
    }

    /**
     * If the progress dialog is not showing shows the progress dialog.
     * Also starts a timer that closes the progress bar after the maximum time
     */
    public void start(){
        if (dialog.isShowing())
            return;

        dialog.show();
        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                stop();
                isCanceled = true;
                if (mListener != null)
                    mListener.onCanceled();
            }
        }, MAX_TIME);
    }

    /**  Closes the progress dialog */
    public void stop(){
        if(dialog != null && dialog.isShowing())
            dialog.dismiss();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /**  @return true if the dialog is showing or else false */
    public boolean isShowing(){
        return dialog != null && dialog.isShowing();
    }

}
