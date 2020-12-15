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
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiInterface {
    //for login module
    @FormUrlEncoded
    @POST("login/")
    Call<Object> LogingFildBuzz(@Field("username") String mUserName, @Field("password") String mPassword);


    //for file upload
    @Multipart
    //@FormUrlEncoded
    @PUT("file-object/{token}/")
    Call<Object> UploadCvFile(@Path("token") String tokens,@Header("Connection")String conn,@Header("Authorization") String authorization, @Part MultipartBody.Part file);


    //final hit for resume
    //@FormUrlEncoded

    @POST("v1/recruiting-entities/")
    Call<Object> SendResumeDetails(
            @Header("Authorization") String authorization,@Header("Content-Type") String contentType, @Body ResumeModelClass resumeModelClass);
}
