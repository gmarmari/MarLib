package marmaris.lib.tools;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import marmaris.lib.log.MGLogger;
import marmaris.libs.R;

public abstract class MGAsyncTask<I, O> extends AsyncTask<Void, Integer, Void> {

    interface Listener {
        void onComplete(Object output, String errorMsg);
    }

    @NonNull
    private final Listener mListener;

    private I mInput;
    private O mOutput;
    private String mErrorMsg;

    private Context context;
    public Context getContext() {
        return context;
    }

    private MGProgressBar mgProgressBar;

    public MGProgressBar getProgressBar() {
        return mgProgressBar;
    }

    /**
     * Creates a new instance. Call execute to start the async execution and wait for the Listener to return the output.
     * @param context : Context
     * @param notify : if true, then a progress bar will be swohn
     * @param mListener : it is called onPostExecute and return the output and the errorMsg if any
     */
    public MGAsyncTask(@NonNull Context context, boolean notify, @NonNull Listener mListener) {
        this.context = context;
        this.mListener = mListener;
        if (notify) {
            mgProgressBar = new MGProgressBar(context, context.getString(R.string.loading_progress), true);
            mgProgressBar.setListener(new MGProgressBar.Listener() {
                @Override
                public void onCanceled() {
                    cancel(true);
                }
            });
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (isNotify())
            mgProgressBar.start();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            mOutput = getOutput();
        } catch (Exception e) {
            e.printStackTrace();
            MGLogger.appendLog(context, getClass().toString(), e);
            mErrorMsg = e.toString();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (isNotify())
            mgProgressBar.stop();
        mListener.onComplete(mOutput, mErrorMsg);
    }

    private boolean isNotify() {
        return mgProgressBar != null;
    }

    /**
     * Change the message of the progressbar
     * @param message : a custom message
     */
    public void setProgressMessage(@NonNull String message) {
        if (isNotify())
            mgProgressBar.setMessage(message);
    }

    /**
     * Append an error message to the mErrorMsg
     * @param errorMsg an error message
     */
    public void appendErrorMsg(@NonNull String errorMsg) {
        if (mErrorMsg == null)
            mErrorMsg = "";
        if (mErrorMsg.length() > 0)
            mErrorMsg += "/n";
        mErrorMsg += errorMsg;
    }

    /**
     * Create the output here.
     * @return the output
     * @throws Exception : an exception may occure but it is cought on doInBackground
     */
    protected abstract O getOutput() throws Exception;

}