package com.example.fildbuzz.Model_Repository.ApiClass;

import com.example.fildbuzz.Model_Repository.ModelClass.ResumeModelClass;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiInterface {
    //for login module
    @FormUrlEncoded
    @POST("login/")
    Call<Object> LogingFildBuzz(@Field("username") String mUserName, @Field("password") String mPassword);


    //for file upload
    @Multipart
    @POST("file-object/{token}/")
    Call<Object> UploadCvFile(@Path("token") String tokens,@Header("Authorization") String authorization, @Part MultipartBody.Part file);


    //final hit for resume
    //@FormUrlEncoded

    @POST("v0/recruiting-entities/")
    Call<Object> SendResumeDetails(/*@Field("tsync_id") String tsync_id, @Field("name") String name, @Field("email") String email,
                                   @Field("phone") String phone, @Field("full_address") String full_address,
                                   @Field("name_of_university") String name_of_university,
                                   @Field("graduation_year") String graduation_year, @Field("cgpa") String cgpa,
                                   @Field("experience_in_months") String experience_in_months,
                                   @Field("current_work_place_name") String current_work_place_name,
                                   @Field("applying_in") String applying_in,
                                   @Field("expected_salary") String expected_salary,
                                   @Field("field_buzz_reference") String field_buzz_reference, @Field("github_project_url") String github_project_url,
                                   @Field("cv_file") String cv_file, @Field("on_spot_update_time") String on_spot_update_time,
                                   @Field("on_spot_creation_time") String on_spot_creation_time*/
            @Header("Authorization") String authorization,@Header("Content-Type") String contentType, @Body ResumeModelClass resumeModelClass);
}
