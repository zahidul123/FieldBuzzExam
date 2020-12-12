package com.example.fildbuzz.Model_Repository.Repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.fildbuzz.Model_Repository.ApiClass.ApiInterface;
import com.example.fildbuzz.Model_Repository.ApiClass.RetrofitServerCall;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {
    ApiInterface apiInterface;
    public LoginRepository(){
        apiInterface= RetrofitServerCall.getretrofitdata().create(ApiInterface.class);
    }

    //call server for data

    public LiveData<Object> postLoginData(String mUserName,String mPassword){
        final MutableLiveData<Object> responseData=new MutableLiveData<>();
        apiInterface.LogingFildBuzz(mUserName,mPassword).enqueue(new Callback<Object>() {


            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    Log.e("login response:",response.body().toString());
               /* ObjectMapper objectMapper = new ObjectMapper();
                try {
                    String json = objectMapper.writeValueAsString(response);

                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                Gson gson = new Gson();
                String json = gson.toJson(response.body());*/
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
                Log.e("login exception:",t.toString());
            }
        });
        return  responseData;
    }
}
