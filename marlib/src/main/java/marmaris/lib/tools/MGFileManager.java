package marmaris.lib.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.NonNull;

import java.io.File;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import marmaris.lib.log.MGLogger;
import marmaris.libs.R;

public class MGFileManager {

    private static final String GB = "GB";
    private static final String MB = "MB";
    private static final String KB = "KB";
    private static final String B = "B";

    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        return Environment.getExternalStorageState().equals( Environment.MEDIA_MOUNTED);
    }

    /* Checks if external storage is available for at least read */
    public static boolean isExternalStorageReadable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) || Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED_READ_ONLY);
    }

    /**
     * If the ExternalStorageWritable returns the ExternalStorageDirectory. If the given folder name is not null adds it to the path.
     * @param context : Context
     * @param folderName : the folder name under the ExternalStorageDirectory
     * @return the path of the external storage directorz + the given folder name
     */
    @NonNull public static String getExternalFilesFolder(Context context, String folderName) {
        if (!isExternalStorageWritable())
            return "";
        if (folderName != null && !folderName.isEmpty())
            return Environment.getExternalStorageDirectory().getPath() + File.separator + folderName;
        else
            return Environment.getExternalStorageDirectory().getPath();
    }

    /** Returns the free space in Bites */
    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    public static long getFreeSpace(Context c, String path){
        long availableSize = 0;
        try {
            StatFs stat = new StatFs(path);
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2)
                availableSize = (long) stat.getAvailableBlocks() * (long) stat.getBlockSize();
            else
                availableSize = stat.getAvailableBlocksLong() * stat.getBlockSizeLong();
        } catch (Exception e) {
            e.printStackTrace();
            MGLogger.appendLog(c, MGFileManager.class.toString(), e);
        }
        return availableSize;
    }

    /**
     * From the given bytes creates a string for the file size with the best unit
     * @param c : Context
     * @param bytes : the file size in bytes
     * @return the file size as string
     */
    public static String getFileSize(Context c, Double bytes){
        if (bytes == null)
            return "";

        DecimalFormat formatter = new DecimalFormat(c.getString(R.string.demical_format));
        formatter.setRoundingMode(RoundingMode.CEILING);

        String size;
        if (bytes == 0)
            size = "";
        else if (bytes >= (1024*1024*1024)) // GB
            size = formatter.format(bytes/(1024*1024*1024) ) + " " + GB;
        else if (bytes >= (1024*1024)) // MB
            size = formatter.format(bytes/(1024*1024) ) + " " + MB;
        else if (bytes >= (1024)) // KB
            size = formatter.format(bytes/1024 ) + " " + KB;
        else // Bytes
            size = formatter.format(bytes) + " " + B;
        return size;
    }

}
