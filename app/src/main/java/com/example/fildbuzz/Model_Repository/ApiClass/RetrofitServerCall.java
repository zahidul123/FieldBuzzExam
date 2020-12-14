package com.example.fildbuzz.Model_Repository.ApiClass;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
                                    .connectTimeout(30, TimeUnit.SECONDS)
                                    .readTimeout(20, TimeUnit.SECONDS)
                                    .writeTimeout(20, TimeUnit.SECONDS)
                                    .build())
                            .build();
                }
            }

        }
        return retrofit;
    }

    public static Retrofit getRetrofitFullresume(String token){
        OkHttpClient defaultHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Interceptor.Chain chain) throws IOException {
                        Request original = chain.request();
                        Request request = original.newBuilder()
                                .header("Authorization", token)
                                .header("Content-Type", "application/json")
                                .method(original.method(),original.body())
                                .build();
                        return chain.proceed(request);

                    }}).build();
        if (retrofit==null){
            synchronized (RetrofitServerCall.class){
                if (retrofit==null){
                    retrofit=new Retrofit.Builder()
                            .baseUrl(base_url)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .client(defaultHttpClient)
                            .build();
                }
            }

        }
        return retrofit;

    }
}
