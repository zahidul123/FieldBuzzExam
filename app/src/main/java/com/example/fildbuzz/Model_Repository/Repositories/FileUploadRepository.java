package com.example.fildbuzz.Model_Repository.Repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.fildbuzz.Model_Repository.ApiClass.ApiInterface;
import com.example.fildbuzz.Model_Repository.ApiClass.RetrofitServerCall;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONObject;

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

    public LiveData<Object> postUploadeFile(String token_id,String tokenValue, MultipartBody.Part fileToUpload){
        final MutableLiveData<Object> responseData=new MutableLiveData<>();
        apiInterface.UploadCvFile(token_id,"close","token "+tokenValue,fileToUpload).enqueue(new Callback<Object>() {


            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {

                    if (response.body()!=null){
                        Gson gson = new Gson();
                        String json = gson.toJson(response.body());
                        Log.e("third api response :",json);
                        //we only get token from here
                        JSONObject jsonObject=new JSONObject(json);
                        String successMessage=jsonObject.getString("message");
                        responseData.setValue(successMessage);
                    }
                }catch (Exception e){
                    Log.e(" exception",e.toString());
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
