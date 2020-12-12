package com.example.fildbuzz.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.fildbuzz.Model_Repository.Repositories.FileUploadRepository;

import okhttp3.MultipartBody;

public class FileUploadViewModel extends AndroidViewModel {
    FileUploadRepository fileUploadRepository;
    LiveData<Object> fileUploadResponse;
    public FileUploadViewModel(@NonNull Application application) {
        super(application);
        fileUploadRepository=new FileUploadRepository();

    }
    public void setFileuploded(String token, MultipartBody.Part uplodedFile){
        if (fileUploadRepository!=null){
            fileUploadResponse=fileUploadRepository.postUploadeFile(token,uplodedFile);
        }
    }
    public LiveData<Object>getFileUploadResponse(){
        return fileUploadResponse;
    }
}
