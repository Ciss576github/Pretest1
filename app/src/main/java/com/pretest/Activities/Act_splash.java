package com.pretest.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import com.pretest.R;
import com.pretest.UtilClasses.SessionParam;

public class Act_splash extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;
    SessionParam sessionParam;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeStatusBarColor("#ABE0EB");
        setContentView(R.layout.act_splash);

        context = this;
        sessionParam=new SessionParam(context);

    }

    private void callApi() {

        startTimer();
    }

    private void startTimer() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sessionParam.login.equals("yes")){
                    Intent intent = new Intent(Act_splash.this, Act_home.class);
                    startActivity(intent);

                }else {
                    Intent intent = new Intent(Act_splash.this, Act_login.class);
                    startActivity(intent);
                }


            }

        }, SPLASH_TIME_OUT);
    }



    @Override
    protected void onStart() {
        super.onStart();
        callApi();
    }

    private void changeStatusBarColor(String color){
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color));
        }
    }
}