package com.example.fildbuzz.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.fildbuzz.Model_Repository.ModelClass.ResumeModelClass;
import com.example.fildbuzz.Model_Repository.Repositories.FileUploadRepository;
import com.example.fildbuzz.Model_Repository.Repositories.FinalResumeUploadRepository;

public class FinalResumeUploadViewModel extends AndroidViewModel {
    FinalResumeUploadRepository finalResumeUploadRepository;
    LiveData<Object> finalUploadResponse;
    public FinalResumeUploadViewModel(@NonNull Application application) {
        super(application);
        finalResumeUploadRepository=new FinalResumeUploadRepository();
    }
  /*  public void setResumeData(String token, String mName, String memail, String mPhone, String mFullAddress,
                              String mUniversity, String mGraduation, String mCgpa, String mExperience, String mCurrentWork,
                              String applying_in, String expected_salary, String field_buzz_reference,
                              String github_project_url, String cv_file, String on_spot_update_time, String on_spot_creation_time){
        if (finalResumeUploadRepository!=null){
            finalUploadResponse=finalResumeUploadRepository.postFinalResumeUpload(token, mName, memail, mPhone, mFullAddress,
                    mUniversity, mGraduation, mCgpa, mExperience, mCurrentWork,
                    applying_in, expected_salary, field_buzz_reference,
                    github_project_url, cv_file, on_spot_update_time, on_spot_creation_time);
        }

    }*/
  public void setResumeData(String token,ResumeModelClass resumeModelClass){
      if (finalResumeUploadRepository!=null){
          finalUploadResponse=finalResumeUploadRepository.postFinalResumeUpload("token "+token,resumeModelClass);
      }
  }
    public LiveData<Object>getFinalUploadResponse(){
        return finalUploadResponse;
    }
}
