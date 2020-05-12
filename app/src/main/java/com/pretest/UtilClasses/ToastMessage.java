package com.pretest.UtilClasses;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.pretest.R;

public class ToastMessage {
    private Context context;
    private static ToastMessage instance;

    /**
     * @param context
     */
    public ToastMessage(Context context) {
        this.context = context;
    }

    /**
     * @param context
     * @return
     */
    public synchronized static ToastMessage getInstance(Context context) {
        if (instance == null) {
            instance = new ToastMessage(context);
        }
        return instance;
    }

    /**
     * @param message
     */
    public void showLongMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param message
     */
    public void showSmallMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * The Toast displayed via this method will display it for short period of time
     *
     * @param message
     */
    public void showLongCustomToast(String message) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View layout = inflater.inflate(R.layout.customtoast, (ViewGroup) ((Activity) context).findViewById(R.id.custom_toast_layout));
        TextView msgTv = (TextView) layout.findViewById(R.id.custom_toast_message);
        msgTv.setText(message);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.BOTTOM, 40, 40);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();


    }

    /**
     * The toast displayed by this class will display it for long period of time
     *
     * @param message
     */
    public void showSmallCustomToast(String message) {

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View layout = inflater.inflate(R.layout.customtoast, (ViewGroup) ((Activity) context).findViewById(R.id.custom_toast_layout));
        TextView msgTv = (TextView) layout.findViewById(R.id.custom_toast_message);
        msgTv.setText(message);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

}