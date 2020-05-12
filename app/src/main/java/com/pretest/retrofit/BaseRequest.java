package com.pretest.retrofit;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pretest.R;
import com.pretest.UtilClasses.SessionParam;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by himanshu on 15/10/2018.
 */
public class BaseRequest<T> extends BaseRequestParser {
    public int TYPE_NOT_CONNECTED = 0;
    public int TYPE_WIFI = 1;
    public int TYPE_MOBILE = 2;
    String token = "";
    SessionParam sessionParam;
    String network_error_message = "Check internet connection";
    private Context mContext;
    private ApiInterface apiInterface;
    private RequestReciever requestReciever;
    private boolean runInBackground = false;
    private Dialog dialog;
    //    ProgressDialog progressDialog;
    private View loaderView = null;
    private int APINumber_ = 1;
    public Callback<JsonElement> responseCallback = new Callback<JsonElement>() {
        @Override
        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
            String responseServer = "";
            hideLoader();
            if (null != response.body()) {
                JsonElement jsonElement = response.body();
                if (null != jsonElement) {
                    responseServer = jsonElement.toString();
                    requestReciever.onSuccess(APINumber_, responseServer, null);
                }

            } else if (response.errorBody() != null) {
                responseServer = readStreamFully(response.errorBody().contentLength(),
                        response.errorBody().byteStream());
            }

            logFullResponse(responseServer, "OUTPUT");

//            if (parseJson(responseServer)) {
//                if (null != requestReciever) {
//                    if (null != getDataArray()) {
//                        requestReciever.onSuccess(APINumber_, responseServer, getDataArray());
//                    } else if (null != getDataObject()) {
//                        requestReciever.onSuccess(APINumber_, responseServer, getDataObject());
//                    } else {
//                        requestReciever.onSuccess(APINumber_, responseServer, message);
//                    }
//                }
//            } else {
//                if (null != requestReciever) {
//                    requestReciever.onFailure(1, "" + mResponseCode, message);
//                }
//            }
        }

        @Override
        public void onFailure(Call<JsonElement> call, Throwable t) {
//            handler.removeCallbacksAndMessages(null);
//            handler.postDelayed(r, 1000);
           /* if (t.getMessage().startsWith("Unable to resolve")) {
               r.run();
            }*/
        }
    };

    public Callback<JsonElement> responseCallbackCustom = new Callback<JsonElement>() {
        @Override
        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
            String responseServer = "";
            hideLoader();

            if (response.raw().networkResponse() != null) {
                Log.d("DATATAAA", "onResponse: response is from NETWORK...");
            } else if (response.raw().cacheResponse() != null
                    && response.raw().networkResponse() == null) {
                Log.d("DATATAAA", "onResponse: response is from CACHE...");
            }

            if (null != response.body()) {
                JsonElement jsonElement = response.body();
                if (null != jsonElement) {
                    responseServer = jsonElement.toString();
                }

            } else if (response.errorBody() != null) {
                responseServer = readStreamFully(response.errorBody().contentLength(),
                        response.errorBody().byteStream());
            }
            logFullResponse(responseServer, "OUTPUT");
            requestReciever.onSuccess(APINumber_, responseServer, null);
        }

        @Override
        public void onFailure(Call<JsonElement> call, Throwable t) {
//            handler.removeCallbacksAndMessages(null);
//            handler.postDelayed(r, 1000);
            /*if (t.getMessage().startsWith("Unable to resolve")) {
               r.run();
            }*/
        }
    };
    public Callback<JsonElement> responseCallbackCustomchat = new Callback<JsonElement>() {
        @Override
        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
            String responseServer = "";

            if (null != response.body()) {
                JsonElement jsonElement = response.body();
                if (null != jsonElement) {
                    responseServer = jsonElement.toString();
                }

            } else if (response.errorBody() != null) {
                responseServer = readStreamFully(response.errorBody().contentLength(),
                        response.errorBody().byteStream());
            }

            logFullResponse(responseServer, "OUTPUT");
            requestReciever.onSuccess(APINumber_, responseServer, null);
        }

        @Override
        public void onFailure(Call<JsonElement> call, Throwable t) {
//            handler.removeCallbacksAndMessages(null);
//            handler.postDelayed(r, 1000);
            /*if (t.getMessage().startsWith("Unable to resolve")) {
               r.run();
            }*/
        }
    };
    private boolean showErrorDialog = true;
    private RequestType requestType = null;

    public BaseRequest(Context context) {
        mContext = context;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        dialog = getProgressesDialog(context);
        sessionParam = new SessionParam(mContext);
//        token = sessionParam.token;

        //dialog.setTitle("Fetching details...");
    }

    public BaseRequest() {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

//        dialog.setTitle("Fetching details...");
    }


    public BaseRequest(Context context, Fragment fm) {
        mContext = context;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        dialog = getProgressesDialog(context);
    }

    public boolean isRunInBackground() {
        return runInBackground;
    }

    public void setRunInBackground(boolean runInBackground) {
        this.runInBackground = runInBackground;
    }

    public void setLoaderView(View loaderView_) {
        this.loaderView = loaderView_;
    }

    public void setBaseRequestListner(RequestReciever requestListner) {
        this.requestReciever = requestListner;

    }

    public void callAPIPostCustomURL(final int APINumber, JsonObject jsonObject, String remainingURL) {
        requestType = RequestType.Post;
        APINumber_ = APINumber;
        showLoader();

//        if (jsonObject == null) {
//            jsonObject = new JsonObject();
//        }

        //  String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;

        Log.i("BaseReq",
                "Url" + " : " + remainingURL);
        logFullResponse(jsonObject.toString(), "INPUT");

        Call<JsonElement> call = apiInterface.postDataCustomURL(remainingURL, jsonObject);

        call.enqueue(responseCallback);
    }


//    Handler handler = new Handler();
//    Runnable r = new Runnable() {
//        @Override
//        public void run() {
//            hideLoader();
//            if (null != requestReciever) {
//                requestReciever.onNetworkFailure(APINumber_, network_error_message);
//            }
//            if (showErrorDialog) {
//            }
//        }
//    };


    public ArrayList<Object> getDataList(JSONArray mainArray, Class<T> t) {
        Gson gsm = null;
        ArrayList<Object> list = null;
        list = new ArrayList<>();
        if (null != mainArray) {

            for (int i = 0; i < mainArray.length(); i++) {
                gsm = new Gson();
                Object object = gsm.fromJson(mainArray.optJSONObject(i).toString(), t);
                list.add(object);
            }
        }
        return list;
    }

    public ArrayList<Object> getDataListreverse(JSONArray mainArray, Class<T> t) {
        Gson gsm = null;
        ArrayList<Object> list = null;
        list = new ArrayList<>();
        if (null != mainArray) {

            for (int i = mainArray.length() - 1; i >= 0; i--) {
                gsm = new Gson();
                Object object = gsm.fromJson(mainArray.optJSONObject(i).toString(), t);
                list.add(object);
            }
        }
        return list;
    }

//    public void callAPIPostIMAGE(final int APINumber, JsonObject jsonObject, String remainingURL, MultipartBody.Part body, RequestBody dotId, RequestBody description, RequestBody section, RequestBody sectionId) {
//
//        APINumber_ = APINumber;
//        showLoader();
//
//        if (jsonObject == null) {
//            jsonObject = new JsonObject();
//        }
//
//        String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
//        Log.i("BaseReq",
//                "Url" + " : "
//                        + baseURL);
//        logFullResponse(jsonObject.toString(), "INPUT");
//        //Call<JsonElement> call = apiInterface.uploadImage(body, "Bearer " + token);
//        Call<JsonElement> call = apiInterface.uploadImage(body, dotId, description, section, sectionId, "Bearer " + token);
//        Log.d("Token", token);
//        call.enqueue(responseCallback);
//    }

    public void callAPIPost(final int APINumber, JsonObject jsonObject, String remainingURL) {
        requestType = RequestType.Post;
        APINumber_ = APINumber;
        showLoader();
        if (jsonObject == null) {
            jsonObject = new JsonObject();
        }
        String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
        Log.i("BaseReq",
                "Url" + " : "
                        + baseURL);
        logFullResponse(jsonObject.toString(), "INPUT");
        Call<JsonElement> call = apiInterface.postData(remainingURL, jsonObject, "Bearer " + token);

        Log.d("Token", token);

        call.enqueue(responseCallback);
    }

    public void callAPIPostWOLoader(final int APINumber, JsonObject jsonObject, String remainingURL) {
        requestType = RequestType.Post;
        APINumber_ = APINumber;
        //showLoader();
        if (jsonObject == null) {
            jsonObject = new JsonObject();
        }
        String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
        Log.i("BaseReq",
                "Url" + " : "
                        + baseURL);
        logFullResponse(jsonObject.toString(), "INPUT");
        Call<JsonElement> call = apiInterface.postData(remainingURL, jsonObject, "Bearer " + token);

        Log.d("Token", token);

        call.enqueue(responseCallback);
    }

    public void callAPIPostUpdateEvidence(final int APINumber, JsonObject jsonObject, String remainingURL, MultipartBody.Part body, RequestBody dotId, RequestBody description) {

        APINumber_ = APINumber;
        showLoader();

        if (jsonObject == null) {
            jsonObject = new JsonObject();
        }

        String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
        Log.i("BaseReq",
                "Url" + " : "
                        + baseURL);
        logFullResponse(jsonObject.toString(), "INPUT");
        //Call<JsonElement> call = apiInterface.uploadImage(body, "Bearer " + token);
        Call<JsonElement> call = apiInterface.updateEvidence(body, dotId, description, "Bearer " + token);
        Log.d("Token", token);
        call.enqueue(responseCallback);
    }

    //user_type_,device_id_,user_id_,organization_id_

    public void callAPIGET(final int APINumber, Map<String, String> map, String remainingURL) {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        showLoader();
        String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
        if (!baseURL.endsWith("?")) {
            baseURL = baseURL + "?";
        }

        for (Map.Entry<String, String> entry : map.entrySet()) {
            baseURL = baseURL + entry.getKey() + "=" + entry.getValue() + "&";
        }
        System.out.println("BaseReq INPUT URL : " + baseURL);
        //token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImVjYmE4Y2YyZjQyYzQxZWZmMTUwZTA0NWM5YmFmZDM3MTE2ODU0MDczMzQ4NTc4Y2ZlNGU1ZmEyZjQyZWMxNzBjYzM0NWMzM2NmZjIyYzY5In0";
        Call<JsonElement> call = apiInterface.postDataGET(remainingURL, map, "Bearer " + token);
        call.enqueue(responseCallback);
        Log.d("Token", token);
    }

    //                                                                               //name_,email_,mobile_,password_

    public void callAPILogin(final int APINumber, String remainingURL, RequestBody type_, RequestBody email_, RequestBody password_) {//user_type_,device_id_,email_,password_,org_id_
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        showLoader();
        //String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.postLogin(type_, email_, password_);
        call.enqueue(responseCallback);
    }

    public void callAPupdatestt(final int APINumber, String remainingURL, RequestBody userId_, RequestBody sender_id_) {//user_type_,device_id_,email_,password_,org_id_
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        showLoader();
        //String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.postupdatesttt(userId_, sender_id_);
        call.enqueue(responseCallback);
    }

    public void callAPIssendmsg(final int APINumber, String remainingURL, RequestBody userId_, RequestBody type_, RequestBody rec_id_, RequestBody message_, RequestBody msg_type_, MultipartBody.Part body) {//user_type_,device_id_,email_,password_,org_id_
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        showLoader();
        //String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.postMsg(userId_, type_, rec_id_, message_, msg_type_, body);
        call.enqueue(responseCallback);
    }

    public void callAPIAcceptAppoint(final int APINumber, String remainingURL, RequestBody status_, RequestBody pat_id_, RequestBody userId_, RequestBody timing_, RequestBody a_id_) {//user_type_,device_id_,email_,password_,org_id_
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        showLoader();
        //String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.postAcceptAppoint(status_, pat_id_, userId_, timing_, a_id_);
        call.enqueue(responseCallback);
    }

    public void callAPIRegister(final int APINumber, String remainingURL, RequestBody name_, RequestBody mobile_, RequestBody pass_, RequestBody cat_, RequestBody type_, RequestBody time_, RequestBody designation_, RequestBody exper_, RequestBody fee_, RequestBody address_, RequestBody desc_, MultipartBody.Part body, RequestBody gender) {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        showLoader();
        //String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        //Call<JsonElement> call = apiInterface_.formData(images,latitude,fcm_token,msg_detail,app_name,email_id_to,ssecrete,device_id,longitude,location_detail);
        Call<JsonElement> call = apiInterface_.postRegister(name_, mobile_, pass_, cat_, type_, time_, designation_, exper_, fee_, address_, desc_, body, gender);
        call.enqueue(responseCallback);
    }

    public void callAPIUpdateProfile(final int APINumber, String remainingURL, RequestBody name_, RequestBody mobile_, RequestBody time_, RequestBody designation_, RequestBody exper_, RequestBody fee_, RequestBody address_, RequestBody desc_, MultipartBody.Part body, RequestBody gender_, RequestBody doctor_id_) {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        showLoader();
        //String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        //Call<JsonElement> call = apiInterface_.formData(images,latitude,fcm_token,msg_detail,app_name,email_id_to,ssecrete,device_id,longitude,location_detail);
        Call<JsonElement> call = apiInterface_.postUpdateprof(name_, mobile_, time_, designation_, exper_, fee_, address_, desc_, body,gender_,doctor_id_);
        call.enqueue(responseCallback);
    }

    public void callAPIAppointment(final int APINumber, String remainingURL, RequestBody userId_, RequestBody desc_, RequestBody time_, RequestBody dom_, RequestBody status_, RequestBody doctor_id_) {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        showLoader();
        //String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        //Call<JsonElement> call = apiInterface_.formData(images,latitude,fcm_token,msg_detail,app_name,email_id_to,ssecrete,device_id,longitude,location_detail);
        Call<JsonElement> call = apiInterface_.postAppointment(userId_, desc_, time_, dom_, status_, doctor_id_);
        call.enqueue(responseCallback);
    }


    public void callAPIADDPRESCR(final int APINumber, String remainingURL, RequestBody userId_, RequestBody desc_, RequestBody patient_id_, RequestBody appoint_id_, MultipartBody.Part body) {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        showLoader();
        //String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        //Call<JsonElement> call = apiInterface_.formData(images,latitude,fcm_token,msg_detail,app_name,email_id_to,ssecrete,device_id,longitude,location_detail);
        Call<JsonElement> call = apiInterface_.formData(userId_, desc_, patient_id_, appoint_id_,body);
        call.enqueue(responseCallback);
    }

    public void callAPIRecientToken(final int APINumber, String remainingURL, RequestBody userId_, RequestBody recent_token_, RequestBody type_) {//user_type_,device_id_,email_,password_,org_id_
        APINumber_ = APINumber;
        requestType = BaseRequest.RequestType.Post;
        showLoader();
        //String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        //Call<JsonElement> call = apiInterface_.formData(images,latitude,fcm_token,msg_detail,app_name,email_id_to,ssecrete,device_id,longitude,location_detail);
        Call<JsonElement> call = apiInterface_.token(userId_, recent_token_, type_);
        call.enqueue(responseCallback);
    }


    public void callAPIBookedItems(final int APINumber, String remainingURL, RequestBody user_id_) {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        showLoader();
        //String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
//        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        //Call<JsonElement> call = apiInterface_.formData(images,latitude,fcm_token,msg_detail,app_name,email_id_to,ssecrete,device_id,longitude,location_detail);
        Call<JsonElement> call = apiInterface_.bookedItems(user_id_);
        call.enqueue(responseCallback);
    }

    //
    public void callAPIpOSTbOOK(final int APINumber, String remainingURL, RequestBody mobile_, RequestBody action_, RequestBody item_id_, RequestBody user_id_, RequestBody desc_, RequestBody name_) {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        showLoader();
        //String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        //Call<JsonElement> call = apiInterface_.formData(images,latitude,fcm_token,msg_detail,app_name,email_id_to,ssecrete,device_id,longitude,location_detail);
        Call<JsonElement> call = apiInterface_.pOSTbOOK(mobile_, action_, item_id_, user_id_, desc_, name_);
        call.enqueue(responseCallback);
    }

    //,
    public void callAPIAddDirector(final int APINumber, String remainingUrl, MultipartBody.Part body, RequestBody name_, RequestBody email_, RequestBody mobile_no_, RequestBody address_, RequestBody dob_, RequestBody doj_, RequestBody dir_id_, RequestBody course_id, RequestBody organization_id_, RequestBody gender_) {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        showLoader();
        //String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
//        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingUrl).create(ApiInterface.class);
        //Call<JsonElement> call = apiInterface_.formData(images,latitude,fcm_token,msg_detail,app_name,email_id_to,ssecrete,device_id,longitude,location_detail);
        Call<JsonElement> call = apiInterface_.addDirector(body, name_, email_, mobile_no_, address_, dob_, doj_, dir_id_, course_id, organization_id_, gender_);
        call.enqueue(responseCallback);
    }

    public void callAPIAddAssignment(final int APINumber, String remainingUrl, List<MultipartBody.Part> parts, RequestBody title_, RequestBody description_, RequestBody starttime_, RequestBody endtime_, RequestBody startdate_, RequestBody enddate_, RequestBody dept_id_, RequestBody course_id_, RequestBody userId_, RequestBody hod_id_, RequestBody director_id_, RequestBody org_id_, RequestBody semId_, RequestBody sectionId_, RequestBody subId_) {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        showLoader();
        //String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
//        System.out.println("BaseReq INPUT URL : " + remainingURL);
        logFullResponse(parts.toString(), "INPUT");
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingUrl).create(ApiInterface.class);
        //Call<JsonElement> call = apiInterface_.formData(images,latitude,fcm_token,msg_detail,app_name,email_id_to,ssecrete,device_id,longitude,location_detail);
        Call<JsonElement> call = apiInterface_.addAssignment(parts, title_, description_, starttime_, endtime_, startdate_, enddate_, dept_id_, course_id_, userId_, hod_id_, director_id_, org_id_, semId_, sectionId_, subId_);
        call.enqueue(responseCallback);
    }

    public void callAPIAddStudymate(final int APINumber, String remainingUrl, List<MultipartBody.Part> parts, RequestBody title_, RequestBody description_, RequestBody dept_id_, RequestBody course_id_, RequestBody userId_, RequestBody hod_id_, RequestBody director_id_, RequestBody org_id_, RequestBody semId_, RequestBody sectionId_, RequestBody subId_) {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        showLoader();
        //String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
//        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingUrl).create(ApiInterface.class);
        //Call<JsonElement> call = apiInterface_.formData(images,latitude,fcm_token,msg_detail,app_name,email_id_to,ssecrete,device_id,longitude,location_detail);
        Call<JsonElement> call = apiInterface_.addStudymat(parts, title_, description_, dept_id_, course_id_, userId_, hod_id_, director_id_, org_id_, semId_, sectionId_, subId_);
        call.enqueue(responseCallback);
    }

    public void callAPIAddsemister(final int APINumber, String remainingUrl, RequestBody course_id_, RequestBody org_id_, ArrayList<String> sem_list_string) {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        showLoader();
        //String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
//        System.out.println("BaseReq INPUT URL : " + endhrs);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingUrl).create(ApiInterface.class);
        //Call<JsonElement> call = apiInterface_.formData(images,latitude,fcm_token,msg_detail,app_name,email_id_to,ssecrete,device_id,longitude,location_detail);
        Call<JsonElement> call = apiInterface_.addSemister(course_id_, org_id_, sem_list_string);
        call.enqueue(responseCallback);
    }

    public void callAPIAddTimeTable(final int APINumber, String remainingUrl, RequestBody userId_, RequestBody type_, int[] arr1) {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        showLoader();
        //String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
//        System.out.println("BaseReq INPUT URL : " + endhrs);
//        logFullResponse(starthrs.toString(), "INPUT");
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingUrl).create(ApiInterface.class);
        //Call<JsonElement> call = apiInterface_.formData(images,latitude,fcm_token,msg_detail,app_name,email_id_to,ssecrete,device_id,longitude,location_detail);
        Call<JsonElement> call = apiInterface_.addTimeTable1(userId_, type_, arr1);
        call.enqueue(responseCallback);
    }


    public void callAPIAddhod(final int APINumber, String remainingUrl, MultipartBody.Part body, RequestBody name_, RequestBody email_, RequestBody mobile_no_, RequestBody address_, RequestBody dob_, RequestBody doj_, RequestBody hod_clg_id_, RequestBody dept_id_, RequestBody organization_id_, RequestBody gender_, RequestBody course_id_) {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        showLoader();
        //String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
//        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingUrl).create(ApiInterface.class);
        //Call<JsonElement> call = apiInterface_.formData(images,latitude,fcm_token,msg_detail,app_name,email_id_to,ssecrete,device_id,longitude,location_detail);
        Call<JsonElement> call = apiInterface_.addhOD(body, name_, email_, mobile_no_, address_, dob_, doj_, hod_clg_id_, dept_id_, organization_id_, gender_, course_id_);
        call.enqueue(responseCallback);
    }

    //body,name_,email_,mobile_no_
    //                ,address_,dob_,doj_,faculty_clg_id_,dept_id_,organization_id_,gender_,                                                                                                                                                                      directorId__,hodId__,prefix_,experience_,designation_
    public void callAPIAddfaculty(final int APINumber, String remainingUrl, MultipartBody.Part body, RequestBody name_, RequestBody email_, RequestBody mobile_no_, RequestBody address_, RequestBody dob_, RequestBody doj_, RequestBody faculty_clg_id_, RequestBody dept_id_, RequestBody organization_id_, RequestBody gender_, RequestBody courseId_, RequestBody hodId__, RequestBody experience_, RequestBody designation_) {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        showLoader();
        //String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
//        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingUrl).create(ApiInterface.class);
        //Call<JsonElement> call = apiInterface_.formData(images,latitude,fcm_token,msg_detail,app_name,email_id_to,ssecrete,device_id,longitude,location_detail);
        Call<JsonElement> call = apiInterface_.addFaculty(body, name_, email_, mobile_no_, address_, dob_, doj_, faculty_clg_id_, dept_id_, organization_id_, gender_, courseId_, hodId__, experience_, designation_);
        call.enqueue(responseCallback);
    }


    public void callAPIRating(final int APINumber, String remainingURL, RequestBody userId_, RequestBody  doctor_id_, RequestBody rating__) {//user_type_,device_id_,email_,password_,org_id_
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        showLoader();
        //String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.postRatigg( userId_, doctor_id_,rating__);
        call.enqueue(responseCallback);
    }


    public void callAPIAddstudent(final int APINumber, String remainingUrl, MultipartBody.Part body, RequestBody fname_, RequestBody mname_, RequestBody lname_, RequestBody email_, RequestBody mobile_no_, RequestBody emer_mobbile_, RequestBody paddress_, RequestBody taddress_, RequestBody dob_, RequestBody admission_date_, RequestBody fat_name_, RequestBody mot_name_, RequestBody enrol_no_, RequestBody session_start_, RequestBody session_end_, RequestBody scholarship_, RequestBody caste_, RequestBody blood_, RequestBody gender_, RequestBody dept_id_, RequestBody organization_id_, RequestBody courseId_, RequestBody hodId__, RequestBody semId_, RequestBody sectionId_, RequestBody city_, RequestBody state_, RequestBody hsc_, RequestBody ssc_, RequestBody diploma_) {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        showLoader();
        //String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
//        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingUrl).create(ApiInterface.class);
        //Call<JsonElement> call = apiInterface_.formData(images,latitude,fcm_token,msg_detail,app_name,email_id_to,ssecrete,device_id,longitude,location_detail);
        Call<JsonElement> call = apiInterface_.addStudent(body, fname_, mname_, lname_, email_, mobile_no_, emer_mobbile_, paddress_, taddress_, dob_, admission_date_, fat_name_, mot_name_, enrol_no_, session_start_, session_end_, scholarship_, caste_, blood_, gender_, dept_id_, organization_id_, courseId_, hodId__, semId_, sectionId_, city_, state_, hsc_, ssc_, diploma_);
        call.enqueue(responseCallback);


//                       ,mobile_no_,emer_mobbile_,paddress_,taddress_,dob_,admission_date_,fat_name_,mot_name_,enrol_no_,session_start_,session_end_,scholarship_,caste_,blood_,gender_,dept_id_,organization_id_,courseId_,hodId__,semId_,sectionId_
    }

    public void callAPIgetUserAnswer(final int APINumber, String remainingURL, RequestBody email_id, RequestBody ques_id, RequestBody reply, RequestBody app_name) {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        //showLoader();
        //String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        //Call<JsonElement> call = apiInterface_.formData(images,latitude,fcm_token,msg_detail,app_name,email_id_to,ssecrete,device_id,longitude,location_detail);
        Call<JsonElement> call = apiInterface_.getUserAnswer(email_id, ques_id, reply, app_name);
        call.enqueue(responseCallback);
    }

    public void callAPITellSidSignup(final int APINumber, String remainingURL, RequestBody email_id, RequestBody device_id, RequestBody fcm_token, RequestBody app_name, RequestBody ssecrete) {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        //showLoader();
        //String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        //Call<JsonElement> call = apiInterface_.formData(images,latitude,fcm_token,msg_detail,app_name,email_id_to,ssecrete,device_id,longitude,location_detail);
        Call<JsonElement> call = apiInterface_.tellSidSignup(email_id, device_id, fcm_token, app_name, ssecrete);
        call.enqueue(responseCallback);
    }

    public void callAPITellSid_Logout(final int APINumber, String remainingURL, RequestBody emp_email) {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        //showLoader();
        //String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
        System.out.println("BaseReq INPUT URL : " + remainingURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(remainingURL).create(ApiInterface.class);
        //Call<JsonElement> call = apiInterface_.formData(images,latitude,fcm_token,msg_detail,app_name,email_id_to,ssecrete,device_id,longitude,location_detail);
        Call<JsonElement> call = apiInterface_.tellSidLogout(emp_email);
        call.enqueue(responseCallback);
    }

    public void callAPIDELETE(final int APINumber, Map<String, String> map, String remainingURL, String id) {
        APINumber_ = APINumber;
        requestType = RequestType.Post;
        showLoader();
        String baseURL = ApiClient.getClient().baseUrl().toString() + remainingURL;
        if (!baseURL.endsWith("?")) {
            baseURL = baseURL + "?";
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            baseURL = baseURL;
        }
        System.out.println("BaseReq INPUT URL : " + baseURL);
        //token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImVjYmE4Y2YyZjQyYzQxZWZmMTUwZTA0NWM5YmFmZDM3MTE2ODU0MDczMzQ4NTc4Y2ZlNGU1ZmEyZjQyZWMxNzBjYzM0NWMzM2NmZjIyYzY5In0";
        Call<JsonElement> call = apiInterface.callAPIDELETE(remainingURL + id, map, "Bearer " + token);
        call.enqueue(responseCallback);
        Log.d("Token", token);
    }

/*

    public void callAPIGETData2(final int APINumber, String baseURL_) {
        APINumber_ = APINumber;

        String baseURL = baseURL_;
        if (!baseURL.endsWith("?")) {
            baseURL = baseURL + "?";
        }

        System.out.println("BaseReq INPUT URL : " + baseURL);
        ApiInterface apiInterface_ = ApiClient.getClientChatimage().create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.getImageUrl(baseURL_);
        call.enqueue(responseCallbackCustom);
    }
*/

    public void callAPIGETData(final int APINumber, String baseURL_) {
        APINumber_ = APINumber;
        showLoader();
        String baseURL = baseURL_;
        if (!baseURL.endsWith("?")) {
            baseURL = baseURL + "?";
        }
        System.out.println("BaseReq INPUT URL : " + baseURL);
        ApiInterface apiInterface_ = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.getDataWithoutMap(baseURL_);
        call.enqueue(responseCallbackCustom);
    }

    public void callAPIGETDatawdwd(final int APINumber, String baseURL_, Class<T> t) {
        APINumber_ = APINumber;

        showLoader();
        String baseURL = baseURL_;
        if (!baseURL.endsWith("?")) {
            baseURL = baseURL + "?";
        }
        System.out.println("BaseReq INPUT URL : " + baseURL);
        ApiInterface apiInterface_ = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.getDataWithoutMap(baseURL_);
        call.enqueue(responseCallbackCustom);
    }


    public void upiValid(final int APINumber, String baseURL_) {
        APINumber_ = APINumber;
        showLoader();
        String baseURL = baseURL_;
        if (!baseURL.endsWith("?")) {
            baseURL = baseURL + "?";
        }
        System.out.println("BaseReq INPUT URL : " + baseURL);
        ApiInterface apiInterface_ = ApiClient.getClient2().create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.getDataWithoutMap(baseURL_);
        call.enqueue(responseCallbackCustom);
    }

    public void callAPIGETChat(final int APINumber, String baseURL_) {
        APINumber_ = APINumber;
        String baseURL = baseURL_;
        if (!baseURL.endsWith("?")) {
            baseURL = baseURL + "?";
        }
        System.out.println("BaseReq INPUT URL : " + baseURL);
        ApiInterface apiInterface_ = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.getDataWithoutMap(baseURL_);
        call.enqueue(responseCallbackCustomchat);
    }

    public void callAPIGETFeedback(final int APINumber, String baseURL_) {
        APINumber_ = APINumber;
        showLoader();
        String baseURL = baseURL_;
        if (!baseURL.endsWith("/")) {
            baseURL = baseURL + "/";
        }
        System.out.println("BaseReq INPUT URL : " + baseURL);
        ApiInterface apiInterface_ = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.getDataWithoutMap(baseURL);
        call.enqueue(responseCallbackCustom);
    }

    public void callAPIGETCustomURL(final int APINumber, Map<String, String> map, String baseURL_) {
        APINumber_ = APINumber;
        showLoader();
        String baseURL = baseURL_;
        if (!baseURL.endsWith("?")) {
            baseURL = baseURL + "?";
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            baseURL = baseURL + entry.getKey() + "=" + entry.getValue() + "&";
        }
        System.out.println("BaseReq INPUT URL : " + baseURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(baseURL_).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.postDataGET("", map, "Bearer " + token);
        call.enqueue(responseCallbackCustom);
    }

    public void callAPIGETCustomURLTellSid(final int APINumber, Map<String, String> map, String baseURL_) {
        APINumber_ = APINumber;
        showLoader();
        String baseURL = baseURL_;
        if (!baseURL.endsWith("?")) {
            baseURL = baseURL + "?";
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            baseURL = baseURL + entry.getKey() + "=" + entry.getValue() + "&";
        }
        System.out.println("BaseReq INPUT URL : " + baseURL);
        ApiInterface apiInterface_ = ApiClient.getCustomClient(baseURL_).create(ApiInterface.class);
        Call<JsonElement> call = apiInterface_.postDataGET(baseURL_, map);
        call.enqueue(responseCallback);
    }
/*

    progressDialog = new ProgressDialog(ChatActivity.this, R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            progressDialog.setCancelable(false);
*/
//   public ProgressDialog getProgressesDialog(Context ct) {
//       ProgressDialog progressDialog = new ProgressDialog(mContext, R.style.AppTheme_Dark_Dialog);
//
//       progressDialog.setIndeterminate(true);
//       progressDialog.setMessage("Please Wait...");
//       progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//       progressDialog.show();
//       progressDialog.setCancelable(false);
//       return progressDialog;
//}

    public void logFullResponse(String response, String inout) {
        final int chunkSize = 3000;

        if (null != response && response.length() > chunkSize) {
            int chunks = (int) Math.ceil((double) response.length()
                    / (double) chunkSize);


            for (int i = 1; i <= chunks; i++) {
                if (i != chunks) {
                    Log.i("BaseReq",
                            inout + " : "
                                    + response.substring((i - 1) * chunkSize, i
                                    * chunkSize));
                } else {
                    Log.i("BaseReq",
                            inout + " : "
                                    + response.substring((i - 1) * chunkSize,
                                    response.length()));
                }
            }
        } else {

            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.d("BaseReq", inout + " : " + jsonObject.toString(jsonObject.length()));

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("BaseReq", " logFullResponse=>  " + response);
            }

        }
    }

    private String readStreamFully(long len, InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public Dialog getProgressesDialog(Context ct) {

        Dialog dialog = new Dialog(ct);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.progressdialog);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;


//        Dialog dialog = new Dialog(ct);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        //dialog.setTitle("Fetching details...");
//        dialog.setContentView(R.layout.progress_dialog_loader);
//        dialog.setCanceledOnTouchOutside(false);
//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        Window window = dialog.getWindow();
//        lp.copyFrom(window.getAttributes());
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        window.setAttributes(lp);
//        return dialog;
    }

    public void showErrorDialog(Context ct, String msg, final int APINumber, final JsonObject jsonObject, String remainingURL) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ct);
        alertDialog.setMessage(msg);
        alertDialog.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }

    public void showLoader() {
        if (mContext != null && !((Activity) mContext).isDestroyed()) {
            if (!runInBackground) {
                if (null != loaderView) {
                    loaderView.setVisibility(View.VISIBLE);
                } else if (null != dialog) {
                    dialog.show();
                }
            }
        }
    }

    public void hideLoader() {
        if (mContext != null && !((Activity) mContext).isDestroyed()) {
            if (!runInBackground) {
                if (null != loaderView) {
                    loaderView.setVisibility(View.GONE);
                } else if (null != dialog) {
                    dialog.dismiss();
                }
            }
        }
    }

    public int getConnectivityStatus(Context context) {
        if (null == context) {
            return TYPE_NOT_CONNECTED;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        if (null != activeNetwork && activeNetwork.isConnected()) {

            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                return TYPE_WIFI;
            }
            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return TYPE_MOBILE;
            }
        }
        return TYPE_NOT_CONNECTED;
    }

    public enum RequestType {
        Post, Get
    }


}
