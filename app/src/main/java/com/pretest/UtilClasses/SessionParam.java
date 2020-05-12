package com.pretest.UtilClasses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;

/*import com.facebook.AccessToken;
import com.facebook.login.LoginManager;*/


public class SessionParam {
    public String signupStage = "0", loginSession = "n";
    public String userId = "", unique_id = "", name = "", mobile = "", type = "", email = "", pImage = "", fcmtoken = "", gender = "", dob = "", country = "", profile = "", login = "", unqId = "", status = "", profileImg = "", noti_count = "0";
    String PREF_NAME = "MyPref";
    Context _context;
    public boolean notiState;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor prefsEditor;

    public static String USER_ID;


    public SessionParam(Context context, String signupStage) {
        this.signupStage = signupStage;
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString("signupStage", signupStage);
        prefsEditor.commit();
    }

    @SuppressLint("LongLogTag")
    public SessionParam(Context context, JSONObject jsonObject) {
        if (jsonObject != null) {
            userId = (jsonObject.optString("insert_id"));
            unique_id = (jsonObject.optString("unique_id"));
            name = (jsonObject.optString("name"));
            mobile = (jsonObject.optString("contact"));
            type = jsonObject.optString("type");
            email = jsonObject.optString("email");
            dob = jsonObject.optString("dob");
            country = jsonObject.optString("country");
            gender = jsonObject.optString("gender");
            profile = jsonObject.optString("Profile");

            USER_ID = userId;

            userId(context, userId);
            uniqueId(context, unique_id);
            type(context, type);
            userName(context, name);
            userEmail(context, email);
            userMobile(context, mobile);
            dateofbirth(context, dob);
            countryMeh(context, country);
            dateofbirth(context, gender);
            profileee(context, profile);
        }
    }


    public SessionParam(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.userId = sharedPreferences.getString("userId", "");
        this.gender = sharedPreferences.getString("gender", "");
        this.name = sharedPreferences.getString("name", "");
        this.mobile = sharedPreferences.getString("mob", "");
        this.email = sharedPreferences.getString("email", "");
        this.login = sharedPreferences.getString("login", "");
        this.country = sharedPreferences.getString("country", "");
        this.dob = sharedPreferences.getString("dob", "");
        this.noti_count = sharedPreferences.getString("COUNT", "");
        this.fcmtoken = sharedPreferences.getString("FCM_TOKEN", "");
        this.pImage = sharedPreferences.getString("P_IMAGE", "");
        this.type = sharedPreferences.getString("type", "");
        this.gender = sharedPreferences.getString("gender", "");
        this.profile = sharedPreferences.getString("profile", "");
        this.unique_id = sharedPreferences.getString("unique_id", "");
        this.notiState = sharedPreferences.getBoolean("state", false);

    }


    public void userId(Context context, String userId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString("userId", userId);
        prefsEditor.commit();
    }

    public void dateofbirth(Context context, String dob) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString("dob", dob);
        prefsEditor.commit();
    }

    public void profileee(Context context, String profile) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString("profile", profile);
        prefsEditor.commit();
    }

    public void type(Context context, String type) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString("type", type);
        prefsEditor.commit();
    }

    public void uniqueId(Context context, String unique_id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString("unique_id", unique_id);
        prefsEditor.commit();
    }

    public void userName(Context context, String name) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString("name", name);
        prefsEditor.commit();
    }

    public void userEmail(Context context, String email) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString("email", email);
        prefsEditor.commit();
    }

    public void userMobile(Context context, String mobile) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString("mob", mobile);
        prefsEditor.commit();
    }

    public void countryMeh(Context context, String country) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString("country", country);
        prefsEditor.commit();
    }

    public void imageProfile(Context context, String image) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString("P_IMAGE", image);
        prefsEditor.commit();
    }

    public void fcmToken(Context context, String token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString("FCM_TOKEN", token);
        prefsEditor.commit();
    }


    public void isSaveNotif(Context context, boolean state) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putBoolean("state", state);
        prefsEditor.commit();
    }


    public void clearPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        /*AccessToken.setCurrentAccessToken(null);
        LoginManager.getInstance().logOut();*/
        prefsEditor.clear();
        prefsEditor.commit();
    }


    @Override
    public String toString() {
        return "SessionParam [name=" + "]";
    }


    public void loginSession(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString("login", "yes");
        prefsEditor.commit();
    }

    public void editPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        /*AccessToken.setCurrentAccessToken(null);
        LoginManager.getInstance().logOut();*/
        prefsEditor.clear();
        prefsEditor.commit();
    }


}
