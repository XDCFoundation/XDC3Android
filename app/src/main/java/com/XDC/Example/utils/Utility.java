package com.XDC.Example.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Divyesh on 21-Jul-18.
 */

public class Utility
{


    static Context context;
    public static ProgressDialog progress;

    public static String accesstoken = null;
    public static int SELECTED_TAB = 0;
    static ProgressBar progressBar ;
    public static boolean is_commentactivity_active = false;



    public Utility(Context context) {
        this.context = context;

    }

    public static void showProcess(Context context) {

        progress = ProgressDialog.show(context, "",
                "fetching Information", true);
    }

    public static void showProcess(Context context, String data) {

        progress = ProgressDialog.show(context, "",
                data, true);
    }

    public static void dismissProcess() {

        if (progress != null)
            progress.dismiss();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }





    public static void closeKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }













    //for email pattern
    public static boolean emailValidator(String email) {

        Pattern pattern;
        Matcher matcher;

        final String EMAIL_PATTERN = "^[_A-Za-z0-9ء-ي٠-٩-]+(\\.[_A-Za-z0-9ء-ي٠-٩-]+)*@[A-Za-z0-9ء-ي٠-٩]+(\\.[A-Za-z0-9ء-ي٠-٩]+)*(\\.[A-Zء-يa-z ]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);

        Log.e("return", matcher.matches() + "");
        return matcher.matches();
    }







    public static void logE(Class tag, String message) {
        Log.e(tag.getSimpleName(), message);
    }

    public static void printUrl(Class tag, retrofit2.Call call) {
        logE(tag, "URL : " + call.request().url().toString());
    }

    public static void printUrlAndError(Class tag, retrofit2.Call call, Throwable throwable) {
        logE(tag, "Fail URL : " + call.request().url().toString());
        logE(tag, "Error : " + throwable.getMessage());
    }











    public static long getCurrantTimeStamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp.getTime();
    }


    public static Boolean isActivityRunning(Class activityClass, Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(Integer.MAX_VALUE);

        for (ActivityManager.RunningTaskInfo task : tasks) {
            if (activityClass.getCanonicalName().equalsIgnoreCase(task.baseActivity.getClassName()))
                return true;
        }

        return false;
    }

    public static Bitmap getVideoThumb(String Url) {
        return ThumbnailUtils.createVideoThumbnail(Url, MediaStore.Video.Thumbnails.MINI_KIND);
    }

    public static boolean isValidUrl(String url) {
        return URLUtil.isValidUrl(url);
    }



    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }



    public static String getDateForProfile(long timeStamp)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeStamp);
        String date = DateFormat.format("dd/MM/yyyy HH:mm", cal).toString();
        return date;
    }


    public static void setProgressDialog(Context context) {

        int llPadding = 30;
        LinearLayout ll = new LinearLayout(context);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        ll.setPadding(llPadding, llPadding, llPadding, llPadding);
        ll.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams llParam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        llParam.gravity = Gravity.CENTER;
        ll.setLayoutParams(llParam);

        progressBar = new ProgressBar(context);
        progressBar.setIndeterminate(true);
        progressBar.setPadding(0, 0, llPadding, 0);
        progressBar.setLayoutParams(llParam);

        llParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        llParam.gravity = Gravity.CENTER;
        TextView tvText = new TextView(context);
        tvText.setText("Loading ...");
        tvText.setTextColor(Color.parseColor("#000000"));
        tvText.setTextSize(20);
        tvText.setLayoutParams(llParam);

        ll.addView(progressBar);
        ll.addView(tvText);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setView(ll);

        AlertDialog dialog = builder.create();
        dialog.show();
        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(dialog.getWindow().getAttributes());
            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setAttributes(layoutParams);
        }
    }




    public static void hideProgressDialog(Context context) {
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);
    }





}
