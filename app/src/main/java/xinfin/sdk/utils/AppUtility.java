package xinfin.sdk.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.gson.Gson;

import xinfin.sdk.constants.AppConstants;


public class AppUtility {

    private static ProgressDialog progress;

    Gson gson = new Gson();
    public static boolean isEmpty(@Nullable String str) {
        return str == null || str.trim().length() == 0;
    }

    public static void hideDialog() {
        try {
            if (progress != null && progress.isShowing())
                progress.dismiss();
        } catch (Exception e) {
            Log.d(AppConstants.TAG, "hideDialog " + e.getLocalizedMessage());
        }
    }

    public static void showAlert(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Alert");
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setPositiveButton(context.getString(android.R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public static void showDialog(Context context) {
        if (context == null)
            return;
        if (progress != null && progress.isShowing())
            return;
       progress = new ProgressDialog(context, com.google.android.material.R.style.Theme_MaterialComponents_DayNight_Dialog);
      //  progress.setMessage("Loading...");
        progress.setCancelable(false);
        progress.show();
        progress.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        getDialog().setContentView(R.layout.dialog_spinner);

       /* Drawable draw=context.getResources().getDrawable(R.drawable.progressbar);
       progress = new ProgressDialog(context);
        progress.setIndeterminate(true);
        progress.setCancelable(false);

        progress.setIcon(context.getResources().getDrawable(R.drawable.progressbar));
        progress.setIndeterminateDrawable(context.getResources().getDrawable(R.drawable.progressbar));
        //progress.setProgressDrawable(draw);
        //progress.setMessage("Some Text");
        progress.show();*/

    }

  }
