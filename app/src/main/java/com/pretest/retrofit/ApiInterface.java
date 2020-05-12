package com.pretest.retrofit;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

import static com.pretest.UtilClasses.Constant.Book_appointment;
import static com.pretest.UtilClasses.Constant.Registration;
import static com.pretest.UtilClasses.Constant.Send_msg;
import static com.pretest.UtilClasses.Constant.appointment_accept_reject;
import static com.pretest.UtilClasses.Constant.insert_rating;
import static com.pretest.UtilClasses.Constant.login;
import static com.pretest.UtilClasses.Constant.send_prescription;
import static com.pretest.UtilClasses.Constant.update_chat_status;
import static com.pretest.UtilClasses.Constant.update_device_id;
import static com.pretest.UtilClasses.Constant.update_doctor_profile;

/**
 * Created by Himanshu choudhary on 15/10/2018
 */

public interface ApiInterface {

//    @GET("Kampus/Api2.php?apicall=student_list")
//    Call<ModelResponse> getUserList(
//            @Query("&organization_id=") int organisation_id, @Query("&currentpage=") int page
//
//    );

    @GET()
    @Streaming
    Call<ResponseBody> downloadImage(@Url String fileUrl);


    @POST
    Call<JsonElement> postData(@Url String remainingURL, @Body JsonObject jsonObject, @Header("Authorization") String token);
    //Map<String, String> params

    @GET
    Call<JsonElement> postDataGET(@Url String remainingURL, @QueryMap Map<String, String> map, @Header("Authorization") String token);

    @GET
    Call<JsonElement> postDataTellSid(@Url String remainingURL, @QueryMap Map<String, String> map);

    @GET
    Call<JsonElement> getDataWithoutMap(@Url String remainingURL);

    @GET
    Call<JsonElement> getImageUrl(@Url String remainingURL);


    @DELETE
    Call<JsonElement> callAPIDELETE(@Url String remainingURL, @QueryMap Map<String, String> map, @Header("Authorization") String token);


    @POST
    Call<JsonElement> postDataCustomURL(@Url String remainingURL, @Body JsonObject jsonObject);

    @POST
    Call<JsonElement> postDataCustomURL1(@Url String remainingURL, @Body JsonObject jsonObject);

    @GET
    Call<JsonElement> postDataGET(@Url String remainingURL, @QueryMap Map<String, String> map);


//    name_,action_,mobile_no_,title_,body,story_desc_,user_id_

    @Multipart
    @POST(login)
    Call<JsonElement> postLogin(
            @Part("user_type") RequestBody type_,
            @Part("contact") RequestBody email_,
            @Part("password") RequestBody password_);

  @Multipart
    @POST(update_chat_status)//userId_, sender_id_
    Call<JsonElement> postupdatesttt(
            @Part("user_id") RequestBody userId_,
            @Part("sender_id") RequestBody sender_id_);

    @Multipart
    @POST(Send_msg)//userId_, type_, rec_id_,message_,msg_type_
    Call<JsonElement> postMsg(
            @Part("user_id") RequestBody userId_,
            @Part("user_type") RequestBody type_,
            @Part("reciver_id") RequestBody rec_id_,
            @Part("Message") RequestBody message_,
            @Part("msg_type") RequestBody msg_type_,
            @Part MultipartBody.Part file);

    @Multipart//
    @POST(appointment_accept_reject)
//status_,pat_id_,userId_,timing_
    Call<JsonElement> postAcceptAppoint(
            @Part("status") RequestBody status_,
            @Part("patient_id") RequestBody pat_id_,
            @Part("doctor_id") RequestBody userId_,
            @Part("time") RequestBody timing_,
            @Part("appointment_id") RequestBody a_id_);

    @Multipart
    @POST(Registration)
//name_, mobile_, time_, designation_, exper_, fee_, address_, desc_, body,gender_,doctor_id_
    Call<JsonElement> postRegister(@Part("name") RequestBody name_,
                                   @Part("contact") RequestBody mobile_,
                                   @Part("password") RequestBody pass_,
                                   @Part("category") RequestBody cat_,
                                   @Part("user_type") RequestBody type_,
                                   @Part("Timing") RequestBody time_,
                                   @Part("degree") RequestBody designation_,
                                   @Part("experience") RequestBody exper_,
                                   @Part("fees") RequestBody fee_,
                                   @Part("Address") RequestBody address_,
                                   @Part("Description") RequestBody desc_,
                                   @Part MultipartBody.Part file,
                                   @Part("gender") RequestBody gender);


  @Multipart
    @POST(update_doctor_profile)
//name_, mobile_, time_, designation_, exper_, fee_, address_, desc_, body,gender_,doctor_id_
    Call<JsonElement> postUpdateprof(@Part("name") RequestBody name_,
                                     @Part("contact") RequestBody mobile_,
                                     @Part("Timing") RequestBody time_,
                                     @Part("degree") RequestBody designation_,
                                     @Part("experience") RequestBody exper_,
                                     @Part("fees") RequestBody fee_,
                                     @Part("Address") RequestBody address_,
                                     @Part("Description") RequestBody desc_,
                                     @Part MultipartBody.Part file,
                                     @Part("gender") RequestBody gender,
                                     @Part("doctor_id") RequestBody doctor_id_);

    @Multipart
    @POST(Book_appointment)
//userId_, desc_, time_, dom_, status_,doctor_id_
    Call<JsonElement> postAppointment(@Part("patinet_id") RequestBody userId_,
                                      @Part("Description") RequestBody desc_,
                                      @Part("time_preference") RequestBody time_,
                                      @Part("appointment_date") RequestBody dom_,
                                      @Part("First_meeting_status") RequestBody status_,
                                      @Part("doctor_id") RequestBody doctor_id_);

    @Multipart
    @POST(insert_rating)
//userId_,rest_id_,rating__
    Call<JsonElement> postRatigg(
            @Part("user_id") RequestBody userId_,
            @Part(" doctor_id") RequestBody rest_id_,
            @Part("rating") RequestBody rating__);



    @Multipart
    @POST(send_prescription)//userId_, desc_, patient_id_, appoint_id_
    Call<JsonElement> formData(@Part("doctor_id") RequestBody userId_,
                               @Part("prescription") RequestBody desc_,
                               @Part("patient_id") RequestBody patient_id_,
                               @Part("appointment_id") RequestBody appoint_id_,
                               @Part MultipartBody.Part file);

    @Multipart//
    @POST(update_device_id)
    Call<JsonElement> token(@Part("user_id") RequestBody userId_,
                            @Part("device_token") RequestBody recent_token_,
                            @Part("user_type") RequestBody type_);


    @Multipart
    @POST("getuseranser/")
//@Part("items[]") List<String> items)
    Call<JsonElement> getUserAnswer(@Part("email_id") RequestBody email_id,
                                    @Part("ques_id") RequestBody ques_id,
                                    @Part("reply") RequestBody reply,
                                    @Part("app_name") RequestBody app_name);

    @Multipart
    @POST("SOH/PHP/Api2.php?apicall=book_item")
    Call<JsonElement> pOSTbOOK(@Part("mob_no") RequestBody mobile_,
                               @Part("action") RequestBody action_,
                               @Part("item_id") RequestBody item_id_,
                               @Part("user_id") RequestBody user_id_,
                               @Part("description") RequestBody desc_,
                               @Part("full_name") RequestBody name_);


    @Multipart
    @POST("Kampus/Api2.php?apicall=add_course")
    Call<JsonElement> postCourse(@Part("course_name") RequestBody courseName_,
                                 @Part("organization_id") RequestBody org_id_);

    @Multipart
    @POST("Kampus/Api2.php?apicall=add_department")
    Call<JsonElement> postDept(@Part("department_name") RequestBody dept_name_,
                               @Part("organization_id") RequestBody org_id_,//dept_name_,org_id_,cour_id_
                               @Part("course_id") RequestBody cour_id_);

    @Multipart
    @POST("SOH/PHP/Api2.php?apicall=booked_items")
    Call<JsonElement> bookedItems(@Part("user_id") RequestBody user_id_);

    //body,name_,action_,mobile_no_,title_,story_desc_,user_id_

    @Multipart
    @POST("Kampus/Api2.php?apicall=add_director")
//body,name_,email_,mobile_no_,address_,dob_,doj_,dir_id_,dept_id_,organization_id_,gender_
    Call<JsonElement> addDirector(@Part MultipartBody.Part file,
                                  @Part("director_name") RequestBody name_,
                                  @Part("director_email_id") RequestBody email_,
                                  @Part("director_mobile_no") RequestBody mobile_no_,
                                  @Part("director_address") RequestBody address_,
                                  @Part("diretor_dob") RequestBody dob_,
                                  @Part("director_date_of_joining") RequestBody doj_,
                                  @Part("director_clg_id") RequestBody dir_id_,
                                  @Part("course_id") RequestBody course_id,
                                  @Part("organization_id") RequestBody organization_id_,
                                  @Part("director_gender") RequestBody gender_);

    @Multipart
    @POST("Kampus/Api2.php?apicall=add_assignment")
    Call<JsonElement> addAssignment(@Part List<MultipartBody.Part> files,
                                    @Part("title") RequestBody title_,
                                    @Part("description") RequestBody description_,
                                    @Part("start_time") RequestBody starttime_,
                                    @Part("end_time") RequestBody endtime_,
                                    @Part("start_date") RequestBody startdate_,
                                    @Part("end_date") RequestBody enddate_,
                                    @Part("department_id") RequestBody dept_id_,
                                    @Part("course_id") RequestBody course_id_,
                                    @Part("faculty_id") RequestBody userId_,
                                    @Part("hod_id") RequestBody hod_id_,
                                    @Part("director_id") RequestBody director_id_,
                                    @Part("organization_id") RequestBody org_id_,
                                    @Part("sem_id") RequestBody semId_,
                                    @Part("subject_id") RequestBody subId_,
                                    @Part("section_id") RequestBody sectionId_);

    @Multipart
    @POST("Kampus/Api2.php?apicall=add_studymaterial")
//parts,title_,description_,dept_id_,course_id_,
        // userId_,hod_id_,director_id_,org_id_, semId_,sectionId_,subId_
    Call<JsonElement> addStudymat(@Part List<MultipartBody.Part> files,
                                  @Part("title") RequestBody title_,
                                  @Part("description") RequestBody description_,
                                  @Part("department_id") RequestBody dept_id_,
                                  @Part("course_id") RequestBody course_id_,
                                  @Part("faculty_id") RequestBody userId_,
                                  @Part("hod_id") RequestBody hod_id_,
                                  @Part("director_id") RequestBody director_id_,
                                  @Part("organization_id") RequestBody org_id_,
                                  @Part("sem_id") RequestBody semId_,
                                  @Part("subject_id") RequestBody subId_,
                                  @Part("section_id") RequestBody sectionId_);

    @Multipart
    @POST("Kampus/Api2.php?apicall=add_timetable")
//body,name_,email_,mobile_no_,address_,dob_,doj_,dir_id_,dept_id_,organization_id_,gender_
    Call<JsonElement> addTimeTable(
            @Part("sem_id") RequestBody semId_,
            @Part("section_id") RequestBody sectionId_,
            @Part("day") RequestBody day_,
            @Part("organization_id") RequestBody org_id_,
            @Part("course_id") RequestBody course_id_,
            @Part("department_id") RequestBody dept_id_,
            @Part("hod_id") RequestBody userId_,
            @Part("director_id") RequestBody hod_director_id_,
            @Part("starting_hour[]") ArrayList<String> starthrs,
            @Part("ending_hour[]") ArrayList<String> endhrs,
            @Part("starting_min[]") ArrayList<String> startmin,
            @Part("ending_min[]") ArrayList<String> endmin,
            @Part("faculty_id[]") ArrayList<String> facId,
            @Part("subject_id[]") ArrayList<String> subId);


    @Multipart//course_id_, org_id_,sem_list_string
    @POST("Kampus/Api2.php?apicall=add_sem")
//body,name_,email_,mobile_no_,address_,dob_,doj_,dir_id_,dept_id_,organization_id_,gender_
    Call<JsonElement> addSemister(
            @Part("course_id") RequestBody course_id_,
            @Part("organization_id") RequestBody org_id_,
            @Query("semester_id[]") ArrayList<String> sem_list_string);

    @Multipart
    @POST("View_dsr")
    Call<JsonElement> addTimeTable1(
            @Part("last_date") RequestBody userId_,
            @Part("v_code") RequestBody type_,
            @Part("ti[]") int[] arr1);


//    @Multipart
//    @POST("Kampus/Api2.php?apicall=add_timetable")//body,name_,email_,mobile_no_,address_,dob_,doj_,dir_id_,dept_id_,organization_id_,gender_
//    Call<JsonElement> addTimeTable(
//            @Part("sem_id") RequestBody semId_,
//            @Part("section_id") RequestBody sectionId_,
//            @Part("day") RequestBody day_,
//            @Part("organization_id") RequestBody org_id_,
//            @Part("course_id") RequestBody course_id_,
//            @Part("department_id") RequestBody dept_id_,
//            @Part("hod_id") RequestBody userId_,
//            @Part("director_id") RequestBody hod_director_id_,
//            @Part List<MultipartBody.Part> starthrs,
//            @Part List<MultipartBody.Part> endhrs,
//            @Part List<MultipartBody.Part> startmin,
//            @Part List<MultipartBody.Part> endmin,
//            @Part List<MultipartBody.Part> facId,
//            @Part List<MultipartBody.Part> subId);


//    @Part("starting_hour[]") ArrayList<String> starthrs,
//    @Part("ending_hour[]") ArrayList<String> endhrs,
//    @Part("starting_min[]") ArrayList<String> startmin,
//    @Part("ending_min[]") ArrayList<String> endmin,
//    @Part("faculty_id[]") ArrayList<String> facId,
//    @Part("subject_id[]") ArrayList<String> subId);

    @Multipart
    @POST("./")
    Call<JsonElement> addSubEvent(@Part("") RequestBody useCase,
                                  @Query("event_id[]") ArrayList<String> event_id,
                                  @Query("user_id[]") ArrayList<String> user_id,
                                  @Query("name[]") ArrayList<String> name,
                                  @Query("date_time[]") ArrayList<String> date_time,
                                  @Part("token") RequestBody token,
                                  @Part MultipartBody.Part... profilePic);

    //semId_,sectionId_,day_,org_id_,course_id_,dept_id_,userId_,hod_director_id_,starthrs,endhrs,startmin,endmin,facId,subId
    @Multipart
    @POST("Kampus/Api2.php?apicall=add_hod")
    Call<JsonElement> addhOD(@Part MultipartBody.Part file,
                             @Part("hod_name") RequestBody name_,
                             @Part("hod_email_id") RequestBody email_,
                             @Part("hod_mobile_no") RequestBody mobile_no_,
                             @Part("hod_address") RequestBody address_,
                             @Part("hod_dob") RequestBody dob_,
                             @Part("hod_date_of_joining") RequestBody doj_,
                             @Part("hod_clg_id") RequestBody hod_clg_id_,
                             @Part("department_id") RequestBody dept_id_,
                             @Part("organization_id") RequestBody organization_id_,
                             @Part("hod_gender") RequestBody gender_,
                             @Part("course_id") RequestBody course_id_);

    @Multipart
    @POST("Kampus/Api2.php?apicall=add_faculty")
//faculty_clg_id_,dept_id_,organization_id_,gender_,directorId__,hodId__,prefix_,experience_,designation_
    Call<JsonElement> addFaculty(@Part MultipartBody.Part file,
                                 @Part("faculty_name") RequestBody name_,
                                 @Part("f_email_id") RequestBody email_,
                                 @Part("f_mobile_no") RequestBody mobile_no_,
                                 @Part("f_address") RequestBody address_,
                                 @Part("f_dob") RequestBody dob_,
                                 @Part("f_date_of_joining") RequestBody doj_,
                                 @Part("faculty_clg_id") RequestBody faculty_clg_id_,
                                 @Part("department_id") RequestBody dept_id_,
                                 @Part("organization_id") RequestBody organization_id_,
                                 @Part("f_gender") RequestBody gender_,
                                 @Part("course_id") RequestBody courseId_,
                                 @Part("hod_id") RequestBody hodId__,
                                 @Part("experience") RequestBody experience_,
                                 @Part("designation") RequestBody designation_);


    @Multipart
    @POST("Kampus/Api2.php?apicall=add_student")
//body,fname_,mname_,lname_,email_,mobile_no_,emer_mobbile_,paddress_,taddress_,dob_,admission_date_,
    Call<JsonElement> addStudent(@Part MultipartBody.Part file,
                                 @Part("first_name") RequestBody fname_,
                                 @Part("middle_name") RequestBody mname_,
                                 @Part("last_name") RequestBody lname_,
                                 @Part("s_email_id") RequestBody email_,
                                 @Part("s_mobile_no") RequestBody mobile_no_,
                                 @Part("s_emergency_contact_no") RequestBody emer_mobbile_,
                                 @Part("permanent_address") RequestBody paddress_,
                                 @Part("temporary_address") RequestBody taddress_,
                                 @Part("s_dob") RequestBody dob_,
                                 @Part("admission_date") RequestBody admission_date_,
                                 @Part("fathers_name") RequestBody fat_name_,
                                 @Part("mothers_name") RequestBody mot_name_,
                                 @Part("enrollment_no") RequestBody enrol_no_,
                                 @Part("session_start") RequestBody session_start_,
                                 @Part("session_end") RequestBody session_end_,
                                 @Part("scholarship") RequestBody scholarship_,
                                 @Part("s_caste") RequestBody caste_,
                                 @Part("s_blood_group") RequestBody blood_,
                                 @Part("s_gender") RequestBody gender_,
                                 @Part("department_id") RequestBody dept_id_,
                                 @Part("organization_id") RequestBody organization_id_,
                                 @Part("course_id") RequestBody courseId_,
                                 @Part("hod_id") RequestBody hodId__,
                                 @Part("sem_id") RequestBody semId_,
                                 @Part("section_id") RequestBody sectionId_,
                                 @Part("s_city") RequestBody city_,
                                 @Part("s_state") RequestBody state_,
                                 @Part("hsc_result") RequestBody hsc_,
                                 @Part("ssc_result") RequestBody ssc_,
                                 @Part("diploma") RequestBody diploma_);

    @Multipart
    @POST("signup/")
    Call<JsonElement> tellSidSignup(@Part("email_id_to") RequestBody email_id,
                                    @Part("device_id") RequestBody device_id,
                                    @Part("fcm_token") RequestBody fcm_token,
                                    @Part("app_name") RequestBody app_name,
                                    @Part("ssecrete") RequestBody ssecrete);

    @Multipart
    @POST("logout/")
    Call<JsonElement> tellSidLogout(@Part("emp_email") RequestBody email_id);


    @Multipart
    @POST("updateDotEvidence")
    Call<JsonElement> updateEvidence(@Part MultipartBody.Part file,
                                     @Part("id") RequestBody dotId,
                                     @Part("description") RequestBody description,
                                     @Header("Authorization") String token);

    @Multipart
    @POST("addUserDirectiveFile")
    Call<JsonElement> testBlueData(@Part MultipartBody.Part answer,
                                   @Header("Authorization") String token);
/*

    @Multipart
    @POST("addDotEvidence")
    Call<MultipartAddEvidence> addEvidence(@Header("Authorization") String token, @Part("file\"; filename=\"pp.png\" ") RequestBody file, @Part("dotId") RequestBody dotId, @Part("description") RequestBody description);
*/

}

