package com.pretest.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.pretest.R;
import com.pretest.UtilClasses.MarshMallowPermission;
import com.pretest.UtilClasses.NetworkConnection;
import com.pretest.UtilClasses.SessionParam;
import com.pretest.UtilClasses.ToastMessage;
import com.pretest.retrofit.BaseRequest;
import com.pretest.retrofit.RequestReciever;
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.pretest.UtilClasses.Constant.BASE_URL;

public class Act_login extends AppCompatActivity {

    private static final int RC_SIGN_IN = 234;
    private static final String TAG = "simplifiedcoding";
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    public boolean datafinish = false;
    Button btn_login;
    TextView tv_register;
    EditText et_username;
    RadioGroup radioGroup;
    ShowHidePasswordEditText et_password;
    Context context;
    SessionParam sessionParam;
    MarshMallowPermission marshMallowPermission;
    Activity activity;
    private String username = "", password = "", device_id = "";
    private BaseRequest baseRequest;
    private Dialog dialog;
    private AsyncTask mMyTask;
    String name, email, mobile;
    String Imagepath_downloads = Environment.getExternalStorageDirectory() + "/Konnectin/Downloads/";
    ProgressDialog progressDialog;
    ToastMessage toastMessage;
    String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_act2);

        context = this;
        activity = this;
        sessionParam = new SessionParam(getApplicationContext());
        marshMallowPermission = new MarshMallowPermission(activity);
        toastMessage = new ToastMessage(context);

        btn_login = findViewById(R.id.btn_login);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        tv_register = findViewById(R.id.tv_register);
        tv_register = findViewById(R.id.tv_register);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);


        if (marshMallowPermission.checkPermissionForPhoneState()){
            TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(Act_login.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            device_id = TelephonyMgr.getDeviceId();
        }else {
            device_id = "";
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String validNumber = "^[+]?[0-9]{8,15}$";

                if (et_username.getText().toString().isEmpty()) {
                    toastMessage.showLongCustomToast("Please enter your Mobile");
//                    Toast.makeText(getApplicationContext(), "Please enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (et_password.getText().toString().isEmpty()) {
                    toastMessage.showLongCustomToast("Please enter Password");
//                    Toast.makeText(getApplicationContext(), "Please enter Password", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (type.isEmpty()) {
                    toastMessage.showLongCustomToast("Please Select Type");
//                    Toast.makeText(getApplicationContext(), "Please enter Password", Toast.LENGTH_SHORT).show();
                    return;

                }
                else {
                    username = et_username.getText().toString();
                    password = et_password.getText().toString();

                    if (!username.equals("")) {
                        if (!username.matches(validNumber)) {
                            Toast.makeText(getApplicationContext(), "Check mobile mo", Toast.LENGTH_SHORT).show();
                        } else {
                            callApi();
                            et_username.setText("");
                            et_password.setText("");
                        }
                    }






                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.READ_PHONE_STATE},100);
        }

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i = new Intent(Act_login.this, Act_register.class);
                startActivity(i);
//                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if (checkedId == R.id.radioUser) {
                    type = "Patient";

                } else {
                    type = "Doctor";
                }
            }

        });


    }

    private void permission() {
        datafinish = true;
        List<String> permissionsNeeded = new ArrayList<String>();
        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION))
            permissionsNeeded.add("GPS");
        if (!addPermission(permissionsList, Manifest.permission.ACCESS_COARSE_LOCATION))
            permissionsNeeded.add("location");
        if (!addPermission(permissionsList, Manifest.permission.ACCESS_NETWORK_STATE))
            permissionsNeeded.add("Network state");
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            permissionsNeeded.add("Write storage");
        if (!addPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE))
            permissionsNeeded.add("Read storage");
        if (permissionsList.size() > 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            }
            return;
        }

        init();
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setCancelable(false)
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, final String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_COARSE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_NETWORK_STATE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                if (perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                    init();
                }

            }
            break;

//            case FINE_LOCATION_PERMISSION_REQUEST_CODE: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // permission was granted, yay! Do the
//                    // contacts-related task you need to do.
//                    Toast.makeText(context, "Permission granted fine", Toast.LENGTH_SHORT).show();
//                    iv_loc.setVisibility(View.VISIBLE);
//                } else {
//                    // permission denied, boo! Disable the
//                    // functionality that depends on this permission.
//                    Toast.makeText(context, "Permission denied fine", Toast.LENGTH_SHORT).show();
//                }
//                return;
//            }
//            case COARSE_LOCATION_PERMISSION_REQUEST_CODE: {
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // permission was granted, yay! Do the
//                    // contacts-related task you need to do.
//                    Toast.makeText(context, "Permission granted coras", Toast.LENGTH_SHORT).show();
//                    iv_loc.setVisibility(View.VISIBLE);
//
//                } else {
//                    // permission denied, boo! Disable the
//                    // functionality that depends on this permission.
//                    Toast.makeText(context, "Permission denied coras", Toast.LENGTH_SHORT).show();
//                }
//                return;
//            }

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    private boolean addPermission(List<String> permissionsList, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission);
                // Check for Rationale Option
                if (!shouldShowRequestPermissionRationale(permission))
                    return false;
            }
        }
        return true;
    }

    private void init() {
//        getLocation();
    }

    private void callApi() {

        if (NetworkConnection.checkNetworkStatus(getApplicationContext()) == true) {

            api_login();
        } else {
            sucessDialog(getResources().getString(R.string.Internet_connection), context);
        }
    }

    private void api_login() {
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {


                try {
                    JSONObject jsonObject = new JSONObject(Json);

                    String result = jsonObject.getString("result");

                    if (result.equals("success")) {
                        sessionParam.loginSession(context);
                        sessionParam = new SessionParam(context, jsonObject);


                        finish();
                        Intent i = new Intent(Act_login.this, Act_home.class);
                        startActivity(i);
//                    overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    }else if (result.equals("Contact Not Registered")){
                        Toast.makeText(context, "User Not Exist", Toast.LENGTH_SHORT).show();

                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                toastMessage.showLongCustomToast(message);
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                toastMessage.showLongCustomToast(message);
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }
        });
        RequestBody type_ = RequestBody.create(MediaType.parse("text/plain"), type);
        RequestBody email_ = RequestBody.create(MediaType.parse("text/plain"), username);
        RequestBody password_ = RequestBody.create(MediaType.parse("text/plain"), password);

        baseRequest.callAPILogin(1, BASE_URL, type_, email_, password_);

    }

    public void sucessDialog(String message, Context context) {

        final Dialog mDialog = new Dialog(context);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.notification_dailog2);
        mDialog.setCanceledOnTouchOutside(true);

        Button btn_ok;
        TextView tv_retry;
        TextView tv_notification;
        btn_ok = mDialog.findViewById(R.id.btn_ok);
        tv_retry = mDialog.findViewById(R.id.tv_retry);
        tv_notification = mDialog.findViewById(R.id.tv_notification);
        tv_notification.setText(message);
        tv_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.cancel();
                callApi();
            }
        });
        mDialog.show();
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }


    @Override
    protected void onStart() {
        super.onStart();

        permission();
    }
}
