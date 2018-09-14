package marmaris.lib.log;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import marmaris.lib.tools.MGFileManager;

public class MGLogger {

    public enum MGLoggerLevel{
        Info,
        Warning,
        Error
    }

    private static Logger mLogger;

    /**
     * Writes a message to the log file
     * @param c : Context
     * @param msg : the message
     * @param level : the Level of the log, see {@link MGLoggerLevel}
     */
    public static void appendLog(Context c, String msg, MGLoggerLevel level) {
        if(c == null || msg == null || msg.isEmpty())
            return;

        setUp(c);
        writeLog(c.getClass().toString(), msg, level);
    }

    /**
     * Write an exception to the log file
     * @param c : Context
     * @param e : the Exception
     */
    public static void appendLog(Context c,Exception e) {
        if(c == null || e == null)
            return;

        setUp(c);
        writeLog(c.getClass().toString(), getExceptionString(e), MGLoggerLevel.Error);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void setUp(Context c) {
        if (mLogger != null)
            return;

        try {
            mLogger = Logger.getLogger(c.getClass().toString());
            File mFile = new File(MGFileManager.getExternalFilesFolder(c, "marmaris.lib"));
            mFile.mkdirs();
            FileHandler mFileHandler = new FileHandler(mFile.getPath()+File.separator+"Logging.html");
            MGLoggerHtmlFormatter mFormatter = new MGLoggerHtmlFormatter();
            mFileHandler.setFormatter(mFormatter);
            mLogger.addHandler(mFileHandler);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(c.getClass().toString(), e.toString());
        }
    }

    private static void writeLog(String TAG, String msg, MGLoggerLevel level){
        if(mLogger == null)
            return;

        String log = "TAG: "+ TAG + ", LOG: "+msg;
        switch (level) {
            case Info:
                mLogger.info(log);
                break;
            case Warning:
                mLogger.warning(log);
                break;
            case Error:
                mLogger.severe(log);
                break;
        }
    }

    private static String getExceptionString(Exception e) {
        if (e == null)
            return null;

        StringBuilder mBuilder = new StringBuilder();
        mBuilder.append("Exception: ");
        mBuilder.append(e.toString());
        mBuilder.append("\n");
        mBuilder.append("Message: ");
        mBuilder.append(e.getMessage());
        mBuilder.append("\n");
        mBuilder.append("Cause: ");
        mBuilder.append(e.getCause());
        mBuilder.append("\n");
        if (e.getStackTrace().length > 0) {
            mBuilder.append("Stacktrace: ");
            for (StackTraceElement s: e.getStackTrace() ) {
                mBuilder.append("\n");
                mBuilder.append("FileName: ");
                mBuilder.append(s.getFileName());
                mBuilder.append(", ");
                mBuilder.append("MethodName: ");
                mBuilder.append(s.getMethodName());
                mBuilder.append(", ");
                mBuilder.append("LineNumber: ");
                mBuilder.append(s.getLineNumber());
            }
        }
        return mBuilder.toString();
    }

}
