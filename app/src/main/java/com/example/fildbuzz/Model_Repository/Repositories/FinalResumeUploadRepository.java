package com.example.fildbuzz.Model_Repository.Repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.fildbuzz.Model_Repository.ApiClass.ApiInterface;
import com.example.fildbuzz.Model_Repository.ApiClass.RetrofitServerCall;
import com.example.fildbuzz.Model_Repository.ModelClass.ResumeModelClass;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONObject;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class FinalResumeUploadRepository {
    ApiInterface apiInterface;

    public FinalResumeUploadRepository() {
        apiInterface = RetrofitServerCall.getretrofitdata().create(ApiInterface.class);
    }

   //call server for data

    public LiveData<Object> postFinalResumeUpload(/*String token, String mName, String memail, String mPhone, String mFullAddress,
                                                  String mUniversity, String mGraduation, String mCgpa, String mExperience, String mCurrentWork,
                                                  String applying_in, String expected_salary, String field_buzz_reference,
                                                  String github_project_url, String cv_file, String on_spot_update_time, String on_spot_creation_time*/
            String token, ResumeModelClass resumeModelClass
    ) {
        final MutableLiveData<Object> responseData = new MutableLiveData<>();

        apiInterface.SendResumeDetails(token,"application/json", resumeModelClass).enqueue(new Callback<Object>() {


            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {

                    Gson gson = new Gson();
                    String json = gson.toJson(response.body());
                    JSONObject jsonObject=new JSONObject(json);
                    Log.e("second Api res:", response.body().toString());

                    String newToken_id=jsonObject.getJSONObject("cv_file").getString("id").split("\\.")[0];
                    Log.e("sdfghj",newToken_id);
                    //we only get token from here
                    if (response.body() != null) {
                        responseData.setValue(String.valueOf(newToken_id));
                    }
                } catch (Exception e) {
                    Log.e("exception ",e.toString());
                    responseData.setValue("badRequest");
                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                responseData.setValue("badRequest");
                Log.e("resume exception:", t.toString());
            }
        });
        return responseData;
    }
}
