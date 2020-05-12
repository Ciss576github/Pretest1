package com.pretest.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.hbb20.CountryCodePicker;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.pretest.R;
import com.pretest.UtilClasses.ImagePickerActivity;
import com.pretest.UtilClasses.MarshMallowPermission;
import com.pretest.UtilClasses.NetworkConnection;
import com.pretest.UtilClasses.SessionParam;
import com.pretest.retrofit.BaseRequest;
import com.pretest.retrofit.RequestReciever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.pretest.UtilClasses.Constant.BASE_URL;

//import android.provider.Settings;

public class Act_register extends AppCompatActivity {

    EditText et_name, et_email, et_mobile, et_password, et_cpassword;
    Button btn_signup;
    Context context;
    SessionParam sessionParam;
    MarshMallowPermission marshMallowPermission;
    Activity activity;
    String name = "", cat_id = "", email = "", mobile = "", time = "", category = "", pass = "", cnfm_pass = "", deviceId = "", to = "", from = "",
            designation = "", exper = "", fee = "", address = "", desc = "";
    ;
    RadioGroup radioGroup,radioGroup2;
    RadioButton typeradioButton;
    String type = "",gender = "";
    private BaseRequest baseRequest;
    TextView tv_signin, tv_cat;
    Toolbar toolbar, toolbar1;
    FrameLayout lyt_defult;
    MenuItem edit, delete, savet;
    CountryCodePicker ccp;
    AppBarLayout appbar;
    RelativeLayout lay_cat;
    LinearLayout l1, l2, l3, l4, l5, l6,lay_content2;
    RecyclerView rv_list;
    ImageView iv_profile_image;

    EditText et_degree, et_exp, et_fee, et_address, et_desc;
    TextView et_from,et_to,tv_edit;
    CardView card1;
    View v3, v4, v5, v6, v7, v8;

    RelativeLayout lay_fra;
    public static final int REQUEST_IMAGE = 100;
    MultipartBody.Part body = null;
    private final int REQ_CODE_Gallery = 1;
    private final int REQ_CODE_Camera = 1888;
    Uri selectedImage;
    File file;
    String user_id, contact = "",  degree = "",   profile_image = "", timing = "",
             insert_id = "", rating = "", b_website = "", b_alt_website = "";
    TextView tv_field, tv_rating, tv_exp, tv_city, tv_timing, tv_fee, tv_address, tv_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_register_test);


        context = this;
        activity = this;
        sessionParam = new SessionParam(getApplicationContext());
        marshMallowPermission = new MarshMallowPermission(activity);

        et_name = findViewById(R.id.et_name);
//        et_email = findViewById(R.id.et_email);
        et_mobile = findViewById(R.id.et_mobile);
        et_password = findViewById(R.id.et_password);
        et_cpassword = findViewById(R.id.et_cpassword);
        btn_signup = findViewById(R.id.btn_signup);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup2 = (RadioGroup) findViewById(R.id.radioGroup2);
        iv_profile_image = findViewById(R.id.iv_profile_image);
        tv_signin = findViewById(R.id.tv_signin);
        lay_cat = findViewById(R.id.lay_cat);
        tv_cat = findViewById(R.id.tv_cat);
        tv_edit = findViewById(R.id.tv_edit);
        card1 = findViewById(R.id.card1);
        lay_content2 = findViewById(R.id.lay_content2);
        l1 = findViewById(R.id.l1);
        l2 = findViewById(R.id.l2);
        l3 = findViewById(R.id.l3);
        l4 = findViewById(R.id.l4);
        l5 = findViewById(R.id.l5);
        l6 = findViewById(R.id.l6);

        v3 = findViewById(R.id.v3);
        v4 = findViewById(R.id.v4);
        v5 = findViewById(R.id.v5);
        v6 = findViewById(R.id.v6);
        v7 = findViewById(R.id.v7);
        v8 = findViewById(R.id.v8);


        et_from = findViewById(R.id.et_from);
        et_to = findViewById(R.id.et_to);
        et_degree = findViewById(R.id.et_degree);
        et_exp = findViewById(R.id.et_exp);
        et_fee = findViewById(R.id.et_fee);
        et_address = findViewById(R.id.et_address);
        et_desc = findViewById(R.id.et_desc);
//        ccp = findViewById(R.id.ccp);




        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String validNumber = "^[+]?[0-9]{8,15}$";

                name = et_name.getText().toString();
                pass = et_password.getText().toString();
                cnfm_pass = et_cpassword.getText().toString();
                mobile = et_mobile.getText().toString();
                category = tv_cat.getText().toString();

                from = et_from.getText().toString();
                to = et_to.getText().toString();

                time = from + " - " + to;
                designation = et_degree.getText().toString();
                exper = et_exp.getText().toString();
                fee = et_fee.getText().toString();
                address = et_address.getText().toString();
                desc = et_desc.getText().toString();


                if (type.equals("")) {
                    Toast.makeText(getApplicationContext(), "Select Creating type", Toast.LENGTH_SHORT).show();
                } else {

                    if (type.equals("Doctor")) {
                        if (cat_id.equals("")) {
                            Toast.makeText(getApplicationContext(), "Select Category", Toast.LENGTH_SHORT).show();
                        } else if (name.equals("")) {
                            Toast.makeText(getApplicationContext(), "Fill name", Toast.LENGTH_SHORT).show();
                        } else if (mobile.equals("")) {
                            Toast.makeText(getApplicationContext(), "Fill Mobile no", Toast.LENGTH_SHORT).show();
                        } else if (time.equals("")) {
                            Toast.makeText(getApplicationContext(), "Fill visiting timing", Toast.LENGTH_SHORT).show();
                        } else if (designation.equals("")) {
                            Toast.makeText(getApplicationContext(), "Fill designation no", Toast.LENGTH_SHORT).show();
                        } else if (exper.equals("")) {
                            Toast.makeText(getApplicationContext(), "Fill Experiance", Toast.LENGTH_SHORT).show();
                        } else if (fee.equals("")) {
                            Toast.makeText(getApplicationContext(), "Fill Fee per/patient", Toast.LENGTH_SHORT).show();
                        } else if (address.equals("")) {
                            Toast.makeText(getApplicationContext(), "Fill Address", Toast.LENGTH_SHORT).show();
                        } else if (desc.equals("")) {
                            Toast.makeText(getApplicationContext(), "Fill Description", Toast.LENGTH_SHORT).show();
                        } else if (pass.equals("")) {
                            Toast.makeText(getApplicationContext(), "Fill password", Toast.LENGTH_SHORT).show();
                        } else if (cnfm_pass.equals("")) {
                            Toast.makeText(getApplicationContext(), "Fill Confirm password", Toast.LENGTH_SHORT).show();
                        } else {
                            if (pass.equals(cnfm_pass)){
                                mobile = et_mobile.getText().toString();
                                if (!mobile.equals("")) {
                                    if (!mobile.matches(validNumber)) {
                                        Toast.makeText(getApplicationContext(), "Check mobile mo", Toast.LENGTH_SHORT).show();
                                    } else {
                                        TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                                        if (ActivityCompat.checkSelfPermission(Act_register.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                                            return;
                                        }
                                        deviceId = TelephonyMgr.getDeviceId();

                                        if (file!=null) {
                                            callApi();
                                        }else {
                                            Toast.makeText(getApplicationContext(), "Add profile image", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }else {
                                Toast.makeText(getApplicationContext(), "Password and Confirm password doesn't match", Toast.LENGTH_SHORT).show();
                            }

                        }
                    } else {
                        if (name.equals("")) {
                            Toast.makeText(getApplicationContext(), "Enter name", Toast.LENGTH_SHORT).show();
                        } else if (mobile.equals("")) {
                            Toast.makeText(getApplicationContext(), "Enter Mobile no", Toast.LENGTH_SHORT).show();
                        } else if (pass.equals("")) {
                            Toast.makeText(getApplicationContext(), "Enter password", Toast.LENGTH_SHORT).show();
                        } else if (cnfm_pass.equals("")) {
                            Toast.makeText(getApplicationContext(), "Enter Confirm password", Toast.LENGTH_SHORT).show();
                        } else {
                            if (pass.equals(cnfm_pass)){
                                mobile = et_mobile.getText().toString();
                                if (!mobile.equals("")) {
                                    if (!mobile.matches(validNumber)) {
                                        Toast.makeText(getApplicationContext(), "Check mobile mo", Toast.LENGTH_SHORT).show();
                                    } else {
                                        TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                                        if (ActivityCompat.checkSelfPermission(Act_register.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                                            return;
                                        }
                                        deviceId = TelephonyMgr.getDeviceId();

                                        if (file!=null) {
                                            time = "";
                                            callApi();
                                        }else {
                                            Toast.makeText(getApplicationContext(), "Add profile image", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                }
                            }else {
                                Toast.makeText(getApplicationContext(), "Password and Confirm password doesn't match", Toast.LENGTH_SHORT).show();

                            }

                        }

                    }

                }

            }
        });

        iv_profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!marshMallowPermission.checkPermissionForCamera() && !marshMallowPermission.checkPermissionForExternalStorage()) {
                    marshMallowPermission.requestPermissionForCamera();
                    marshMallowPermission.requestPermissionForExternalStorage();
                } else if (!marshMallowPermission.checkPermissionForCamera()) {
                    marshMallowPermission.requestPermissionForCamera();
                } else if (!marshMallowPermission.checkPermissionForExternalStorage()) {
                    marshMallowPermission.requestPermissionForExternalStorage();
                } else {
//                    selectImage();

//                    Intent i = new Intent(getContext(), MainActivity_crop.class);
//                    startActivity(i);
//
//                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);

                    iv_profile_image.setImageDrawable(null);

                    Dexter.withActivity(activity)
                            .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .withListener(new MultiplePermissionsListener() {
                                @Override
                                public void onPermissionsChecked(MultiplePermissionsReport report) {
                                    if (report.areAllPermissionsGranted()) {
                                        showImagePickerOptions();
                                    }

                                    if (report.isAnyPermissionPermanentlyDenied()) {
                                        showSettingsDialog();
                                    }
                                }

                                @Override
                                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                    token.continuePermissionRequest();
                                }
                            }).check();
                }
            }
        });

        tv_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i = new Intent(Act_register.this, Act_login.class);
                startActivity(i);
//                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });


        et_from.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String format;

                        if (selectedHour == 0) {

                            selectedHour += 12;
                            format = "AM";
                        }
                        else if (selectedHour == 12) {

                            format = "PM";

                        }
                        else if (selectedHour > 12) {

                            selectedHour -= 12;

                            format = "PM";

                        }
                        else {

                            format = "AM";
                        }


                        et_from.setText(selectedHour + ":" + selectedMinute +" "+ format);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        et_to.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String format;

                        if (selectedHour == 0) {

                            selectedHour += 12;
                            format = "AM";
                        }
                        else if (selectedHour == 12) {

                            format = "PM";

                        }
                        else if (selectedHour > 12) {

                            selectedHour -= 12;

                            format = "PM";

                        }
                        else {

                            format = "AM";
                        }


                        et_to.setText(selectedHour + ":" + selectedMinute +" "+ format);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });


        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if (checkedId == R.id.radioUser) {
                    type = "Patient";
                    lay_cat.setVisibility(View.GONE);
                    l1.setVisibility(View.GONE);
                    l2.setVisibility(View.GONE);
                    l3.setVisibility(View.GONE);
                    l4.setVisibility(View.GONE);
                    l5.setVisibility(View.GONE);
                    l6.setVisibility(View.GONE);

                    v3.setVisibility(View.GONE);
                    v4.setVisibility(View.GONE);
                    v5.setVisibility(View.GONE);
                    v6.setVisibility(View.GONE);
                    v7.setVisibility(View.GONE);
                    v8.setVisibility(View.GONE);

                    tv_edit.setVisibility(View.VISIBLE);
                    card1.setVisibility(View.VISIBLE);

                    lay_content2.setVisibility(View.GONE);

                    tv_cat.setText("");

                    et_address.setText("");
                    et_degree.setText("");
                    et_desc.setText("");
                    et_exp.setText("");
                    et_fee.setText("");
                    et_from.setText("");
                    et_to.setText("");

                    category = "";
                    cat_id = "";
                    from = "";
                    to = "";
                    designation = "";
                    exper = "";
                    fee = "";
                    address = "";
                    desc = "";

                } else {
                    type = "Doctor";
                    lay_cat.setVisibility(View.VISIBLE);
                    l1.setVisibility(View.VISIBLE);
                    l2.setVisibility(View.VISIBLE);
                    l3.setVisibility(View.VISIBLE);
                    l4.setVisibility(View.VISIBLE);
                    l5.setVisibility(View.VISIBLE);
                    l6.setVisibility(View.VISIBLE);

                    v3.setVisibility(View.VISIBLE);
                    v4.setVisibility(View.VISIBLE);
                    v5.setVisibility(View.VISIBLE);
                    v6.setVisibility(View.VISIBLE);
                    v7.setVisibility(View.VISIBLE);
                    v8.setVisibility(View.VISIBLE);

                    tv_edit.setVisibility(View.VISIBLE);
                    card1.setVisibility(View.VISIBLE);
                    lay_content2.setVisibility(View.GONE);

                }
            }

        });

        radioGroup2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if (checkedId == R.id.radiomale) {
                    gender = "Male";

                } else {
                    gender = "Female";

                }
            }

        });

    }


    private void callApi() {

        if (NetworkConnection.checkNetworkStatus(context) == true) {
            api_register();
        } else {
            sucessDialog(getResources().getString(R.string.Internet_connection), context);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(context, Act_login.class);
        startActivity(i);
    }

    private void api_register() {
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
                        Toast.makeText(context, "Register Sucessfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(context, Act_home.class);
                        startActivity(i);
                    } else if (result.equals("Contact already Exist")) {
                        Toast.makeText(context, "User already Exist", Toast.LENGTH_SHORT).show();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }
        });

        if (file != null) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        } else {
            RequestBody requestFile = RequestBody.create(MultipartBody.FORM, "");
            body = MultipartBody.Part.createFormData("file", "", requestFile);
        }


        RequestBody name_ = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody mobile_ = RequestBody.create(MediaType.parse("text/plain"), mobile);
        RequestBody pass_ = RequestBody.create(MediaType.parse("text/plain"), pass);
        RequestBody cat_ = RequestBody.create(MediaType.parse("text/plain"), cat_id);
        RequestBody type_ = RequestBody.create(MediaType.parse("text/plain"), type);

        RequestBody time_ = RequestBody.create(MediaType.parse("text/plain"), time);
        RequestBody designation_ = RequestBody.create(MediaType.parse("text/plain"), designation);
        RequestBody exper_ = RequestBody.create(MediaType.parse("text/plain"), exper);
        RequestBody fee_ = RequestBody.create(MediaType.parse("text/plain"), fee);
        RequestBody address_ = RequestBody.create(MediaType.parse("text/plain"), address);
        RequestBody desc_ = RequestBody.create(MediaType.parse("text/plain"), desc);
        RequestBody gender_ = RequestBody.create(MediaType.parse("text/plain"), gender);


        baseRequest.callAPIRegister(1, BASE_URL, name_, mobile_, pass_, cat_, type_, time_, designation_, exper_, fee_, address_, desc_, body,gender_);

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
            }
        });
        mDialog.show();


    }




/*
    private void loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.lyt_defult, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }
*/


//    public void sucessDialog(String message, Context context) {
//
//        final Dialog mDialog = new Dialog(context);
//        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        mDialog.setContentView(R.layout.notification_dailog2);
//        mDialog.setCanceledOnTouchOutside(true);
//
//
//        Button btn_ok;
//        TextView tv_retry;
//        TextView tv_notification;
//        btn_ok = mDialog.findViewById(R.id.btn_ok);
//        tv_retry = mDialog.findViewById(R.id.tv_retry);
//        tv_notification = mDialog.findViewById(R.id.tv_notification);
//        tv_notification.setText(message);
//        tv_retry.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDialog.cancel();
//                callApi();
//
//
//            }
//        });
//        mDialog.show();
//
//
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                selectedImage = data.getParcelableExtra("path");
                if (selectedImage != null) {
                    iv_profile_image.setImageURI(selectedImage);

                    File actualImageFile = new File(selectedImage.getPath());
                    file = saveBitmapToFile(actualImageFile);

                    long fileSizeInBytes = file.length();
                    long fileSizeInKB = fileSizeInBytes / 1024;
                    long fileSizeInMB = fileSizeInKB / 1024;

                    Log.e("SIZE>>>", String.valueOf(fileSizeInKB));
                }
            }
        }

    }


    public File saveBitmapToFile(File file) {
        try {

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE = 75;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            return file;
        } catch (Exception e) {
            return null;
        }
    }

    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(context, new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        });
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(context, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Act_register.this.openSettings();
            }
        });
        builder.setNegativeButton(getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(context, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }


    // navigating user to app settings
    private void openSettings() {

        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

}
