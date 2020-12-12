package com.example.fildbuzz.Model_Repository.ApiClass;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServerCall {
    private static final String base_url="https://recruitment.fisdev.com/api/";
    private static Retrofit retrofit=null;
    private static Gson gson=new GsonBuilder().setLenient().create();

    public static Retrofit getretrofitdata(){
        if (retrofit==null){
            synchronized (RetrofitServerCall.class){
                if (retrofit==null){
                    retrofit=new Retrofit.Builder()
                            .baseUrl(base_url)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .client(new OkHttpClient().newBuilder()
                                    .connectTimeout(20, TimeUnit.SECONDS)
                                    .readTimeout(10, TimeUnit.SECONDS)
                                    .writeTimeout(10, TimeUnit.SECONDS)
                                    .build())
                            .build();
                }
            }

        }
        return retrofit;
    }
}
