package com.example.fildbuzz.Model_Repository.ApiClass;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiInterface {
    //for login module
    @FormUrlEncoded
    @POST("login/")
    Call<Object>LogingFildBuzz(@Field("username") String mUserName, @Field("password") String mPassword);


    //for file upload
    @Multipart
    @POST( "file-object/{token}/")
    Call<Object>UploadCvFile(@Path("token")String tokens, @Part MultipartBody.Part file);


    //final hit for resume
    @FormUrlEncoded
    @POST("v0/recruiting-entities/")
    Call<Object>SendResumeDetails(@Field("tsync_id") String token, @Field("name") String mName,@Field("email") String memail,
                                  @Field("phone") String mPhone,@Field("full_address") String mFullAddress,
                                  @Field("name_of_university") String mUniversity,
                                  @Field("graduation_year") String mGraduation, @Field("cgpa") String mCgpa,@Field("experience_in_months") String mExperience,
                                  @Field("current_work_place_name") String mCurrentWork,@Field("applying_in") String applying_in,
                                  @Field("expected_salary") String expected_salary,
                                  @Field("field_buzz_reference") String field_buzz_reference, @Field("github_project_url") String github_project_url,
                                  @Field("cv_file") String cv_file, @Field("on_spot_update_time") String on_spot_update_time,
                                  @Field("on_spot_update_time") String on_spot_creation_time);
}
