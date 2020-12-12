package com.example.fildbuzz.Model_Repository.Repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.fildbuzz.Model_Repository.ApiClass.ApiInterface;
import com.example.fildbuzz.Model_Repository.ApiClass.RetrofitServerCall;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

public class FileUploadRepository {
    ApiInterface apiInterface;

    public FileUploadRepository(){
        apiInterface= RetrofitServerCall.getretrofitdata().create(ApiInterface.class);
    }

//call server for data

    public LiveData<Object> postUploadeFile(String tokenValue, MultipartBody.Part fileToUpload){
        final MutableLiveData<Object> responseData=new MutableLiveData<>();
        apiInterface.UploadCvFile(tokenValue,fileToUpload).enqueue(new Callback<Object>() {


            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    Log.e("login response:",response.body().toString());

                Gson gson = new Gson();
                String json = gson.toJson(response.body());
                    //we only get token from here
                    if (response.body()!=null){
                        responseData.setValue(((LinkedTreeMap) response.body()).get("token"));
                    }
                }catch (Exception e){
                    responseData.setValue("badRequest");
                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                responseData.setValue("badRequest");
                Log.e("upload exception:",t.toString());
            }
        });
        return  responseData;
    }
}
