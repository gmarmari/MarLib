package marmaris.lib.tools;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.ContextThemeWrapper;

import java.util.ArrayList;

import marmaris.libs.R;

public class MGAlertDialog {

    public enum MGAlertDialogType{
        MESSAGE,
        LIST
    }

    private Context context;

    private MGAlertDialogType type;

    private String title;
    private Integer icon;
    private String message;

    public MGAlertDialog(Context context, String title, Integer icon, String message, MGAlertDialogType type) {
        this.context = context;
        this.title = title;
        this.icon = icon;
        this.message = message;
        this.type = type != null ? type : MGAlertDialogType.MESSAGE;
    }

    private String negativeButtonText;
    private DialogInterface.OnClickListener negativeButtonListener;
    public void setNegativeButton(String text, DialogInterface.OnClickListener listener){
        if (text == null || listener == null)
            return;
        negativeButtonText = text;
        negativeButtonListener = listener;
    }

    private String positiveButtonText;
    private DialogInterface.OnClickListener positiveButtonListener;
    public void setPositiveButton(String text, DialogInterface.OnClickListener listener){
        if (text == null || listener == null)
            return;
        positiveButtonText = text;
        positiveButtonListener = listener;
    }

    private ArrayList<String> items;
    private DialogInterface.OnClickListener itemSelectedListener;
    public void setItems(ArrayList<String> items, DialogInterface.OnClickListener listener){
        if (items == null || listener == null)
            return;
        this.items = items;
        itemSelectedListener = listener;
    }

    public void alertMessage(){
        if (!type.equals(MGAlertDialogType.MESSAGE))
            return;
        if (title == null && message == null)
            return;

        @SuppressWarnings("deprecation")
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(new ContextThemeWrapper(context, android.R.style.Theme_Material_Dialog));
        }else{
            builder = new AlertDialog.Builder(context);
        }

        if (title != null) builder.setTitle(title);
        if (message != null)  builder.setMessage(message);
        if (icon != null) builder.setIcon(icon);
        if (negativeButtonListener != null){
            builder.setNegativeButton(negativeButtonText, negativeButtonListener);
        }
        if (positiveButtonListener != null){
            builder.setPositiveButton(positiveButtonText, positiveButtonListener);
        }

        if (negativeButtonListener == null && positiveButtonListener == null){
            builder.setNegativeButton(R.string.ok,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
        }

        builder.show();
    }

    public void alertListItems(){
        if (!type.equals(MGAlertDialogType.LIST))
            return;
        if (items == null || items.isEmpty())
            return;

        @SuppressWarnings("deprecation")
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(new ContextThemeWrapper(context, android.R.style.Theme_Material_Dialog));
        }else{
            builder = new AlertDialog.Builder(context);
        }

        if (title != null) builder.setTitle(title);
        if (icon != null) builder.setIcon(icon);

        String[] itemsToArray = items.toArray(new String[items.size()]);
        builder.setItems(itemsToArray, itemSelectedListener);

        builder.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

}
