package com.example.fildbuzz.viewModel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.fildbuzz.Model_Repository.Repositories.LoginRepository;

public class LoginViewModel extends AndroidViewModel {

    LoginRepository loginRepository;
    LiveData<Object>loginResponse;
    public LoginViewModel(@NonNull Application application) {
        super(application);
        loginRepository=new LoginRepository();

    }
    public void setloginData(String userName,String password){

       if (loginRepository!=null){
           loginResponse=loginRepository.postLoginData(userName,password);
       }
    }
    public LiveData<Object>getLogintSuccessRecponse(){
        return loginResponse;
    }
}
