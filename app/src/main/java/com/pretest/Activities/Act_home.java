package com.pretest.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.pretest.R;
import com.pretest.UtilClasses.MarshMallowPermission;
import com.pretest.UtilClasses.SessionParam;
import com.pretest.retrofit.BaseRequest;
import com.squareup.picasso.Picasso;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;

import okhttp3.MediaType;
import okhttp3.RequestBody;


public class Act_home extends AppCompatActivity {


    Context context;
    SessionParam sessionParam;
    MarshMallowPermission marshMallowPermission;
    Activity activity;
    private BaseRequest baseRequest;

    Toolbar toolbar;
    TextView tv_name;
    ImageView iv_profile_image, profile;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;

    FrameLayout lyt_defult;
    RecyclerView rv_category_list, rv_stores_list, rv_offers_list;

    String name = "", profile_image = "", mobile = "", image = "", gender = "", deviceId = "", dob = "",
            country = "", state = "", address = "", city = "", postalCode = "";
    String type = "";
    String from = "";

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 5000; // time in milliseconds between successive task executions.
    ViewPager mViewPager;
    NestedScrollView scroll_layout;
    LinearLayout lay1;
    CardView card2, card3;
    ProgressBar progress, progressbar3;
    TextView tv_cat;
    public String recent_token;
    MenuItem action_search, notification, save, edit, send;
    Menu menu;
    DotsIndicator dotsIndicator;
    SwipeRefreshLayout mSwipeRefreshLayout;

    RadioButton radioAll, radioCountry;
    RadioGroup radioGroup;
    String countrry_name = "";
    Dialog pDialog;
    BottomNavigationView bottomNavigationView;
    RelativeLayout lay_tool;
    TextView tool_name;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home);


        toolbar = findViewById(R.id.toolbar);
        lay_tool = findViewById(R.id.lay_tool);
        tool_name = findViewById(R.id.tool_name);
        iv_profile_image = findViewById(R.id.iv_profile_image);
        setSupportActionBar(toolbar);


        context = this;
        activity = this;
        sessionParam = new SessionParam(getApplicationContext());
        marshMallowPermission = new MarshMallowPermission(activity);



//        lay_tool.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (sessionParam.type.equals("Doctor")) {
//                    Intent intent1 = new Intent(Act_home.this, Act_doc_profile.class);//ACTIVITY_NUM = 0
//                    intent1.putExtra("DOC_ID",sessionParam.userId);
//                    startActivity(intent1);
//                }
//            }
//        });

//
//        recent_token = FirebaseInstanceId.getInstance().getToken();
//        Log.e("TOKEN", recent_token);
//        callApi();



//        checkLogin();


        tool_name.setText(sessionParam.name);
        Picasso.get().load(sessionParam.profile).into(iv_profile_image);

        lyt_defult = (FrameLayout) findViewById(R.id.lyt_defult);




    }




    @Override
    public void onBackPressed() {

        finish();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);


//        Frag_home myFragment = (Frag_home)getSupportFragmentManager().findFragmentByTag("HOME_FRAG");
//
//        if (myFragment != null && myFragment.isVisible()) {
//            finish();
//            Intent a = new Intent(Intent.ACTION_MAIN);
//            a.addCategory(Intent.CATEGORY_HOME);
//            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(a);
//        }else {
//            bottomNavigationView.setSelectedItemId(R.id.navigation_home);
//
//        }

    }






}
