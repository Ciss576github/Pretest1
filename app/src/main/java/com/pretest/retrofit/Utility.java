package com.pretest.retrofit;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.text.util.Linkify;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.pretest.R;
import com.pretest.UtilClasses.BadgeDrawable;
import com.pretest.UtilClasses.NetworkConnection;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.RequestBody;

public class Utility {
    String result = "";
    StringBuilder sb;
    String startdate = "";
    public static ProgressDialog progressDialog = null;
    public static AlertDialog.Builder alertbox;
    public static AlertDialog alertDialog;
    public static LayoutInflater _inflater;
    public static View progressdialogview;
    public static Dialog progress_dialog;
    public static RotateAnimation rAnim;
    //    GoogleMap googleMap;
    public static String _clientName = "";
    public static String _clientMessageCount = "";
    public static String _clientDisc = "";
    public static String _time = "";
    public static int _clientId = 0;
    public static int _clientFollowStatus = -1;
    public static String _clientImage = "";
    public Context activity;
    public static String _user_image_base64 = "";
    public static String USER_ID;
    public static String firs_char = "";
    public static String convTime;

    public static final int DISMISS_EVENT_SWIPE = 0;
    /** Indicates that the Snackbar was dismissed via an action click.*/
    public static final int DISMISS_EVENT_ACTION = 1;
    /** Indicates that the Snackbar was dismissed via a timeout.*/
    public static final int DISMISS_EVENT_TIMEOUT = 2;
//    /** Indicates that the Snackbar was dismissed via a call to {@link #dismiss()}.*/
    public static final int DISMISS_EVENT_MANUAL = 3;
    /** Indicates that the Snackbar was dismissed from a new Snackbar being shown.*/
    public static final int DISMISS_EVENT_CONSECUTIVE = 4;


    //in this class we will writing code which we need to use more often
    //for eg: fetching current date or showing toast

    //    //change status bar code on lollipop
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void changeStatusBarColor(Activity con) {
        Window window = con.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(con.getResources().getColor(R.color.Black));
    }

    public static boolean getAPIVerison() {

        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;

    }

    public static boolean isNullOrEmpty(String str) {
        if(str != null && !str.isEmpty())
            return false;
        return true;
    }

    public static int countWordsUsingSplit(String input) {
        if (input == null || input.isEmpty()) {
            return 0;
        }

        String[] words = input.split("\\s+");
        return words.length;
    }

    public static boolean checkURL(CharSequence input) {
        if (TextUtils.isEmpty(input)) {
            return false;
        }
        Pattern URL_PATTERN = Patterns.WEB_URL;
        boolean isURL = URL_PATTERN.matcher(input).matches();
        if (!isURL) {
            String urlString = input + "";
            if (URLUtil.isNetworkUrl(urlString)) {
                try {
                    new URL(urlString);
                    isURL = true;
                } catch (Exception e) {
                }
            }
        }
        return isURL;
    }

    public static void setUnderLineText(TextView tv, String textToUnderLine) {
        String tvt = tv.getText().toString();
        int ofe = tvt.indexOf(textToUnderLine, 0);

        UnderlineSpan underlineSpan = new UnderlineSpan();
        SpannableString wordToSpan = new SpannableString(tv.getText());
        for (int ofs = 0; ofs < tvt.length() && ofe != -1; ofs = ofe + 1) {
            ofe = tvt.indexOf(textToUnderLine, ofs);
            if (ofe == -1)
                break;
            else {
                wordToSpan.setSpan(underlineSpan, ofe, ofe + textToUnderLine.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tv.setText(wordToSpan, TextView.BufferType.SPANNABLE);
            }
        }
    }


//    public static Bitmap getCircleBitmap(Bitmap bitmap) {
//        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888);
//        final Canvas canvas = new Canvas(output);
//
//        final int color = Color.RED;
//        final Paint paint = new Paint();
//        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
//        final RectF rectF = new RectF(rect);
//
//        paint.setAntiAlias(true);
//        canvas.drawARGB(0, 0, 0, 0);
//        paint.setColor(color);
//        canvas.drawOval(rectF, paint);
//
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        canvas.drawBitmap(bitmap, rect, rect, paint);
//
//        bitmap.recycle();
//
//        return output;
//    }

    public static Bitmap getCircleBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output;
    }

    public static Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }

//    public static void setOntoucheListner(ImageView imageToEdit, Activity mActivity, final ImageView imageMask) {
//
//        // Calcul pourcentage du min image
//        float ratio1 = imageToEdit.getWidth() / imageMask.getWidth();
//        float ratio2 = imageToEdit.getHeight() / imageMask.getHeight();
//        if (ratio1 < 1 && ratio2 < 1)
//            ZOOM_MIN = 1;
//        else
//            ZOOM_MIN = Math.min(ratio1, ratio2);
//
//        Display display = mActivity.getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);
//        screenWidth = size.x;
//        screenHeight = size.y;
//        matrix = new Matrix();
//        savedMatrix = new Matrix();
//        startPoint = new PointF();
//        midPoint = new PointF();
//
//        // imageToEdit.setImageMatrix(new Matrix());
//        /** * set on touch listner on image */
//        imageToEdit.setOnTouchListener(new View.OnTouchListener() {
//            private float savedTranslateX = 0;
//            private float savedTranslateY = 0;
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                ImageView view = (ImageView) v;
//                System.out.println("matrix=" + savedMatrix.toString());
//                switch (event.getAction() & MotionEvent.ACTION_MASK) {
//                    case MotionEvent.ACTION_DOWN:
//                        savedMatrix.set(matrix);
//                        startPoint.set(event.getX(), event.getY());
//                        mode = DRAG;
//                        break;
//                    case MotionEvent.ACTION_POINTER_DOWN:
//                        oldDist = spacing(event);
//                        if (oldDist > 10f) {
//                            savedMatrix.set(matrix);
//                            midPoint(midPoint, event);
//                            mode = ZOOM;
//                        }
//                        break;
//                    case MotionEvent.ACTION_UP:
//                    case MotionEvent.ACTION_POINTER_UP:
//                        mode = NONE;
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        if (mode == DRAG) {
//
//                            imageMask.getTop();
//                            imageMask.getTop();
//                            matrix.set(savedMatrix);
//                            matrix.postTranslate(event.getX() - startPoint.x, event.getY() - startPoint.y);
//                            mode = DRAG;
//                        } else if (mode == ZOOM) {
//                            float newDist = spacing(event);
//                            if (newDist > 10f) {
//                                matrix.set(savedMatrix);
//                                float scale = newDist / oldDist;
//                                matrix.postScale(scale, scale, midPoint.x, midPoint.y);
//                            }
//                        }
//                        break;
//                }
//
//                float[] f = new float[9];
//                matrix.getValues(f);
//                float scaleX = f[Matrix.MSCALE_X];
//                float scaleY = f[Matrix.MSCALE_Y];
//                if (mode == ZOOM) {
//                    if (scaleX <= ZOOM_MIN)
//                        matrix.postScale((ZOOM_MIN) / scaleX, (ZOOM_MIN) / scaleY, midPoint.x, midPoint.y);
//                    else if (scaleX >= ZOOM_MAX)
//                        matrix.postScale((ZOOM_MAX) / scaleX, (ZOOM_MAX) / scaleY, midPoint.x, midPoint.y);
//                    else {
//
//                        view.setImageMatrix(matrix);
//                    }
//                } else {
//                    view.setImageMatrix(matrix);
//                }
//
//                return true;
//            }
//
//            @SuppressLint("FloatMath")
//            private float spacing(MotionEvent event) {
//                float x = event.getX(0) - event.getX(1);
//                float y = event.getY(0) - event.getY(1);
//                return FloatMath.sqrt(x * x + y * y);
//            }
//
//            private void midPoint(PointF point, MotionEvent event) {
//                float x = event.getX(0) + event.getX(1);
//                float y = event.getY(0) + event.getY(1);
//                point.set(x / 2, y / 2);
//            }
//        });
//    }


    public static void setBadgeCount(Context context, LayerDrawable icon, String count) {

        BadgeDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }


    public static Bitmap scaleBitmap(Bitmap bitmap, int wantedWidth, int wantedHeight) {
        Bitmap output = Bitmap.createBitmap(wantedWidth, wantedHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Matrix m = new Matrix();
        m.setScale((float) wantedWidth / bitmap.getWidth(), (float) wantedHeight / bitmap.getHeight());
        canvas.drawBitmap(bitmap, m, new Paint());

        return output;
    }

    private boolean isValid(String urlString) {
        try {
            URL url = new URL(urlString);
            return URLUtil.isValidUrl(urlString) && Patterns.WEB_URL.matcher(urlString).matches();
        } catch (MalformedURLException e) {

        }

        return false;
    }

    public static void cancelDialog() {
        if (progress_dialog != null) {
            //System.out.println("Dialog is Canceling");
            progress_dialog.cancel();
            progress_dialog = null;
        }
    }

    public static void removeFragment(Activity context) {
        if (context.getFragmentManager().getBackStackEntryCount() > 0) {
            context.getFragmentManager().popBackStackImmediate();
        }
    }

    public static void showPhoto(Uri photoUri, Context context) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(photoUri, "image/*");
        context.startActivity(intent);
    }


    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            cursor.close();
            return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }


    @SuppressLint("RestrictedApi")
    public static void removeNavigationShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        menuView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        menuView.buildMenuView();
    }

    //    // ///////////////////show Progress dialog
    public static void showProgressDialog(final Context context) {
        progressDialog = new ProgressDialog(context,
                android.R.style.Theme_Panel);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        //		System.out.println("Dialog is Showing");
    }
    ///////////////////show Progress dialog
   /*
   public static void showProgressDialog(final Context context)
    {
        _inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        progressdialogview = _inflater.inflate(R.layout.progress_bar_layout,null);

        progress_dialog = new Dialog(context,android.R.style.Theme_Panel);
        progress_dialog.setContentView(progressdialogview);
        progress_dialog.setCancelable(false);
        progress_dialog.show();

        progressDialog = new ProgressDialog(context,R.layout.progress_bar_layout);
        View v = View.inflate(context,R.layout.progress_bar_layout, null);
        ImageView rotate = (ImageView) progressdialogview.findViewById(R.id.rotateImage);
    progressDialog.setMessage("Please wait...");
        rAnim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rAnim.setRepeatCount(Animation.INFINITE);
        rAnim.setInterpolator(new LinearInterpolator());
        rAnim.setDuration(700);
    *//* refreshIcon is object of an imageView *//*
        rotate.startAnimation(rAnim);
    		System.out.println("Dialog is Showing");
    }
    */

//    public static void sucessDialog(String message, String title, String btn, Context context) {
//
//        final Dialog mDialog = new Dialog(context);
//        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);  //without extar space of title
//        mDialog.setContentView(R.layout.notification_dailog);
//        mDialog.setCanceledOnTouchOutside(true);
//
//        Button btn_ok;
//        TextView tv_notification, tv_title;
//        btn_ok = mDialog.findViewById(R.id.btn_ok);
//        tv_title = mDialog.findViewById(R.id.tv_title);
//        tv_notification = mDialog.findViewById(R.id.tv_notification);
//        tv_notification.setText(message);
//        tv_title.setText(title);
//        btn_ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDialog.cancel();
//
//            }
//        });
//        mDialog.show();
//
//
//    }

    public static boolean checkEmail(String email) {
        String expression = "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*"
                + "+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
        Pattern emailPattern = Pattern.compile(expression);
        return emailPattern.matcher(email).matches();
    }

    public static boolean checkUpi(String upi) {
        String expression = "[a-zA-Z0-9_]{5,}@[a-zA-Z]{3,}";
        Pattern upiPattern = Pattern.compile(expression);
        return upiPattern.matcher(upi).matches();
    }

    //                        //hide key board
    public static void hideKeyBoard(View view, Activity context) {
        View focusedView = context.getCurrentFocus();
        if (focusedView != null) {
            ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void showKeyboard(View view, Activity context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        View v = context.getCurrentFocus();
        if (v != null)
            imm.showSoftInput(v, 0);
    }

    public static void requestFocus(View editText, Activity activity)
    {
        try {
            editText.requestFocus();
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

  public static void hideFocus(View editText, Activity activity)
    {
        try {
            editText.requestFocus();
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public static void showAlertDialog(final String title, String message, final Context context, final boolean redirectToPreviousScreen) {
        if (alertDialog != null && alertDialog.isShowing()) {

        } else {
            alertbox = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
            alertbox.setMessage(message);
            alertbox.setTitle(title);
            /*alertbox.setTitle(Gravity.CENTER);*/
            alertbox.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    alertDialog.dismiss();
                }
            });
            alertDialog = alertbox.create();
            alertDialog.show();
        }
    }
    ///////// key board hide

    public static String covertTimeToText(String dataDate) {
        try {

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date past = format.parse(dataDate);
            Date now = new Date();
            long seconds = TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime());
            long minutes = TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime());
            long hours = TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime());
            long days = TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime());
//
//          System.out.println(TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime()) + " milliseconds ago");
//          System.out.println(TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime()) + " minutes ago");
//          System.out.println(TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime()) + " hours ago");
//          System.out.println(TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime()) + " days ago");

            if (seconds < 60) {
                convTime = seconds + " seconds ago";
                System.out.println(seconds + " seconds ago");
            } else if (minutes < 60) {
                convTime = minutes + " minutes ago";
                System.out.println(minutes + " minutes ago");
            } else if (hours < 24) {
                convTime = hours + " hours ago";
                System.out.println(hours + " hours ago");
            } else {
                convTime = days + " days ago";
                System.out.println(days + " days ago");
            }
        } catch (Exception j) {
            j.printStackTrace();
        }

        return convTime;
    }


    public static Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
                    encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

//    public static void clickify(TextView view, final String clickableText,
//                                String new_s2, String from, final ClickSpan.OnClickListener listener) {
//
//        CharSequence text = view.getText();
//        String string = text.toString();
//        ClickSpan span = new ClickSpan(listener, from);
////        ClickableSpan clickSpan = new ClickableSpan(listener)
//
//        new StyleSpan(Typeface.BOLD);
//
////        Pattern pattern = Pattern.compile("@"+arrayList.get(l));
////        String scheme = "www.android.com";
////        Linkify.addLinks(tv_title, pattern, scheme);
//
//
//        int start = string.indexOf(clickableText);
//        int end = start + clickableText.length();
//        if (start == -1) return;
//
//        SpannableString s = SpannableString.valueOf(text);
//
//        if (text instanceof Spannable) {
//            ((Spannable) text).setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        } else {
//
//            s.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            view.setText(s);
//        }
//
//        MovementMethod m = view.getMovementMethod();
//        if ((m == null) || !(m instanceof LinkMovementMethod)) {
//            view.setMovementMethod(LinkMovementMethod.getInstance());
//        }
//    }

//    public static void clickify1(EditText view, final String clickableText,String new_s2, String from, final ClickSpan.OnClickListener listener) {
//
//        CharSequence text = view.getText();
//        String string = text.toString();
//        ClickSpan span = new ClickSpan(listener, from);
////        ClickableSpan clickSpan = new ClickableSpan(listener)
//
//        new StyleSpan(Typeface.BOLD);
//
////        Pattern pattern = Pattern.compile("@"+arrayList.get(l));
////        String scheme = "www.android.com";
////        Linkify.addLinks(tv_title, pattern, scheme);
//
//
//        int start = string.indexOf(clickableText);
//        int end = start + clickableText.length();
//        if (start == -1) return;
//
//        SpannableString s = SpannableString.valueOf(text);
//
//        if (text instanceof Spannable) {
//            ((Spannable) text).setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        } else {
//
//            s.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            view.setText(s);
//        }
//
//        MovementMethod m = view.getMovementMethod();
//        if ((m == null) || !(m instanceof LinkMovementMethod)) {
//            view.setMovementMethod(LinkMovementMethod.getInstance());
//        }
//    }

    public static void addLink(TextView textView, String patternToMatch,
                               final String link) {
        Linkify.TransformFilter filter = new Linkify.TransformFilter() {
            @Override
            public String transformUrl(Matcher match, String url) {
                return link;
            }
        };
        Linkify.addLinks(textView, Pattern.compile(patternToMatch), null, null,
                filter);
    }

    public static String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public static String capitalizeWord(String name) {
        StringBuilder sb = new StringBuilder(name);
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        return sb.toString();
    }

    public static String capitalize(String capString){
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()){
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
        }

        return capMatcher.appendTail(capBuffer).toString();
    }

    public void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

  /*
    public static boolean showDialog(final Context context, String heading, String message) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Change_pswd Dialog Title
        alertDialog.setTitle(heading);

        // Change_pswd Dialog Message
        alertDialog.setMessage(message);

        // Change_pswd Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // Change_pswd Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //return true;

                result = "yes";

                // Write your code here to invoke YES event
                //Toast.makeText(context, "You clicked on YES", Toast.LENGTH_SHORT).show();
            }
        });

        // Change_pswd Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                result = "no";
                // Write your code here to invoke NO event
                //Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
        if (result.equals("yes")) {
            return true;
        } else {
            return false;
        }

    }

*/
    //----------- hide KeyBoard------------

    public static void hideKeyBoard(Activity context) {
        View focusedView = context.getCurrentFocus();
        //	 Toast.makeText(context,"not hide", 1).show();
        if (focusedView != null) {
            //       Toast.makeText(context,"hide", 1).show();
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(context.getWindow().getCurrentFocus().getWindowToken(), 0);
        }
    }


    //----------- convert Bitmap To Base64------------

/*
    public static String convertBitmapToBase64(Bitmap image)
    {
        String imageEncoded = "";
        try{
            Bitmap immagex=image;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            immagex.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] b = baos.toByteArray();
            imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
            //						Log.e("LOOK", imageEncoded);
        }catch(OutOfMemoryError e)
        {
            e.printStackTrace();
            System.out.println("Error 1 : " + e);
        }catch(Exception e1){
            e1.printStackTrace();
            System.out.println("Error 2 : " + e1);
        }

        return imageEncoded;
    }
*/

//    public static boolean isValidMail(String email) {
//
//        String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
//                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
//
//        return Pattern.compile(EMAIL_STRING).matcher(email).matches();
//
//    }

    public static  boolean isValidMobile(String phone) {
        return Patterns.PHONE.matcher(phone).matches();
    }

//    public static boolean isValidMobile(String phone) {
//        if (!Pattern.matches("[a-zA-Z]+", phone)) {
//            return phone.length() > 6 && phone.length() <= 13;
//        }
//        return false;
//    }

    public static  boolean isValidMail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    //----------- bitmap To Base64------------

    public static String bitmapToBase64(Bitmap bitmap) {
        String base64 = null;
        try {
            if (bitmap != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);
                byte[] _byteArray = baos.toByteArray();
                base64 = Base64.encodeToString(_byteArray, Base64.DEFAULT);
//				base64 = Base64.encodeToString(_byteArray,Base64.NO_WRAP);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        return base64;
    }

    //----------- image_to_Base64------------

/*
    public static String image_to_Base64(Context context, String filePath) {
        Bitmap bmp = null;
        ByteArrayOutputStream bos = null;
        byte[] bt = null;
        String encodeString = null;
        File sourceFile;
        Compressor compressedImageBitmap;

        try {
            sourceFile = new File(filePath);

            bmp = BitmapFactory.decodeFile(filePath);
            bmp = new Compressor(context).compressToBitmap(sourceFile);
            bos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bt = bos.toByteArray();
            encodeString = Base64.encodeToString(bt, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (OutOfMemoryError ome) {

        }
        return encodeString;
    }
*/

    //----------- show Calender ------------

    //public TextView showCalender(Context context,TextView tv) {
  /*
    public static String showCalender(Context context) {
        //here we are calling calendar
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        //tv.setText("");


        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        //tv_startdate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        startdate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
        //tv.setText(startdate);
        //return tv;
        return startdate;
    }
*/

    public static void expand(final View v, int duration, int targetHeight) {

        int prevHeight  = v.getHeight();

        v.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    public static void collapse(final View v, int duration, int targetHeight) {
        int prevHeight  = v.getHeight();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    public String getAddressFromLatlong(Context context, double lat, double longi) {

        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        if (NetworkConnection.checkNetworkStatus(context) == true) {
            //locationPresenter.runGeocodeAPI(latLng.latitude + "," + latLng.longitude, AppConstants.GEOCODE_API_KEY);
            List<Address> addresses = new ArrayList<>();
            try {
                addresses = geocoder.getFromLocation(lat, longi, 1);

                //latitude= latLng.latitude;
                //longitude = latLng.longitude;
            } catch (IOException e) {
                e.printStackTrace();
            }

            Address address = addresses.get(0);


            if (address != null) {
                sb = new StringBuilder();
                if (address.getMaxAddressLineIndex() > 0) {
                    for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                        sb.append(address.getAddressLine(i) + "\n");
                    }
                } else {
                    try {
                        sb.append(address.getAddressLine(0));
                    } catch (Exception ignored) {
                    }
                }
            }
        }
        return sb.toString();

    }


    public static String getRealPathFromURI(Context context, Uri uri) {
        String path = "";
        if (context.getContentResolver() != null) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }


    Bitmap ShrinkBitmap(String file, int width, int height) {
        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
        bmpFactoryOptions.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);

        int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight / (float) height);
        int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth / (float) width);

        if (heightRatio > 1 || widthRatio > 1) {
            if (heightRatio > widthRatio) {
                bmpFactoryOptions.inSampleSize = heightRatio;
            } else {
                bmpFactoryOptions.inSampleSize = widthRatio;
            }
        }

        bmpFactoryOptions.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);
        return bitmap;
    }


    //-----------decode Sampled Bitmap From Resource------------

    public static Bitmap decodeSampledBitmapFromResource(String path, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }
    //-----------calculate In Sample Size------------

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    //-----------get image uri------------

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    //-----------Get image path------------

    public static String getPath(Uri uri, Context context) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s = cursor.getString(column_index);
        cursor.close();
        return s;
    }

    //-----------change Date YMD to DMY------------

    public static String changeDateYMDtoDMY(String date) {
        Date myDate;
        String outputDateStr = "";
        try {
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
            myDate = inputFormat.parse(date);
            outputDateStr = outputFormat.format(myDate);
            System.out.println(outputDateStr);
        } catch (Exception e) {
            //java.text.ParseException: Unparseable date: Geting error
            System.out.println("Excep" + e);
        }
        return outputDateStr;
    }

    //-----------change Date month Name------------

    public String changeDatemonthName(String date) {
        Date myDate;
        String outputDateStr = "";
        try {
            DateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
            DateFormat outputFormat = new SimpleDateFormat("MMM-dd, yyyy HH:mm");
            myDate = inputFormat.parse(date);
            outputDateStr = outputFormat.format(myDate);
            System.out.println(outputDateStr);
        } catch (Exception e) {
            //java.text.ParseException: Unparseable date: Geting error
            System.out.println("Excep" + e);
        }
        return outputDateStr;
    }
    //-----------changeDate DMY to YMD-----------

    public static String changeDateDMYtoYMD(String date) {

        Date myDate;
        String outputDateStr = "";
        try {
            DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
            DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            myDate = inputFormat.parse(date);
            outputDateStr = outputFormat.format(myDate);
            System.out.println(outputDateStr);

        } catch (Exception e) {
            //java.text.ParseException: Unparseable date: Geting error
            System.out.println("Excep" + e);
        }
        return outputDateStr;
    }
    //-----------Email validation------------

//    public final static boolean isValidEmail(CharSequence target) {
//        if (target == null) {
//            return false;
//        } else {
//            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
//        }
//    }

    //-----------convert To String------------

    public static String convertToString(ArrayList<String> list) {

        StringBuilder sb = new StringBuilder();
        String delim = "";
        for (String s : list) {
            sb.append(delim);
            sb.append(s);
            delim = ",";
        }
        return sb.toString();
    }
    //-----------convert To Array------------

    private ArrayList<String> convertToArray(String string) {
        ArrayList<String> list = new ArrayList<String>(Arrays.asList(string.split(",")));
        return list;
    }

    //-----------CONVERT meter To KM--------------

    public String meterToKM(float meters) {
        float km;
        km = meters / 1000;
        return format(km);
    }

    //-----------CONVERT Km To meter--------------

    public String kmToMeter(float km) {
        float meter;
        meter = km * 1000;
        return format(meter);
    }

    //-----------FORMAT------------

    public static String format(Number n) {
        NumberFormat format = DecimalFormat.getInstance();
        format.setRoundingMode(RoundingMode.FLOOR);
        format.setMinimumFractionDigits(0);
        format.setMaximumFractionDigits(2);
        return format.format(n);
    }


    //-----------Dist in km------------

    public static double distanceInKM(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    //    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
//        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
//        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
//        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        vectorDrawable.draw(canvas);
//        return BitmapDescriptorFactory.fromBitmap(bitmap);
//    }
    public float distanceFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 3958.75;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = earthRadius * c;
        Log.e("DISTANCE RADIUS: ", String.valueOf(dist));

        int meterConversion = 1609;
        return new Float(dist * meterConversion).floatValue();
    }

    //    protected Marker createMarker(double latitude, double longitude, String title, String snippet, int iconResID) {
//
//        return googleMap.addMarker(new MarkerOptions()
//                .position(new LatLng(latitude, longitude))
//                .anchor(0.5f, 0.5f)
//                .title(title)
//                .snippet(snippet)
//                .icon(BitmapDescriptorFactory.fromResource(iconResID)));
//    }
//    private void drawMarker(LatLng latLng,String address) {
//        // Creating an instance of MarkerOptions
//        MarkerOptions markerOptions = new MarkerOptions();
//
//        // Change_pswd latitude and longitude for the marker
//        markerOptions.position(latLng);
//        markerOptions.title(address);
//
//        // Adding marker on the Google Map
//        googleMap.addMarker(markerOptions);
//    }
    public String removeFirstChar(String s) {
        return s.substring(1);
    }

    public static String getYouTubeId(String youTubeUrl) {
        String pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(youTubeUrl);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return "error";
        }
    }


    public int compareVersionNames(String oldVersionName, String newVersionName) {
        int res = 0;

        String[] oldNumbers = oldVersionName.split("\\.");
        String[] newNumbers = newVersionName.split("\\.");

        // To avoid IndexOutOfBounds
        int maxIndex = Math.min(oldNumbers.length, newNumbers.length);

        for (int i = 0; i < maxIndex; i++) {
            int oldVersionPart = Integer.valueOf(oldNumbers[i]);
            int newVersionPart = Integer.valueOf(newNumbers[i]);

            if (oldVersionPart < newVersionPart) {
                res = -1;
                break;
            } else if (oldVersionPart > newVersionPart) {
                res = 1;
                break;
            }
        }

        // If versions are the same so far, but they have different length...
        if (res == 0 && oldNumbers.length != newNumbers.length) {
            res = (oldNumbers.length > newNumbers.length) ? 1 : -1;
        }

        return res;
    }

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, descriptionString);
    }
   /* @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri, Context context) {
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        File file = FileUtils.getFile(context, fileUri);
        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(Objects.requireNonNull(context.getContentResolver().getType(fileUri))),
                        file
                );
        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }*/
}

