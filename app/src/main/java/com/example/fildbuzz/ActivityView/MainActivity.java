package com.example.fildbuzz.ActivityView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.fildbuzz.R;
import com.example.fildbuzz.databinding.ActivityMainBinding;
import com.example.fildbuzz.utils.SharedPreference;
import com.example.fildbuzz.viewModel.LoginViewModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mainBinding;
    LoginViewModel loginViewModel;
    SweetAlertDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       mainBinding= DataBindingUtil.setContentView(this, R.layout.activity_main);
      // loginViewModel=new ViewModelProvider(this).get(LoginViewModel.class);
       loginViewModel=new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(LoginViewModel.class);

         pDialog= new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);

       mainBinding.submitBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {


               if (checkMandatoryField()&&checkEmailPattern()){

                       InputMethodManager imm = (InputMethodManager)MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                       imm.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(), 0);
                   pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                   pDialog.setTitleText("Loading");
                   pDialog.setCancelable(false);
                   pDialog.show();
                       loginViewModel.setloginData(mainBinding.email.getText().toString(),mainBinding.password.getText().toString());
                       loginViewModel.getLogintSuccessRecponse().observe(MainActivity.this,allResponse->{
                           if (allResponse!=null){
                               pDialog.dismiss();
                               String response=allResponse.toString();
                               if (response.equals("badRequest")){
                                   new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                                           .setTitleText("Bad Request")
                                           .setContentText("Login Failed Please check Again")
                                           .setConfirmText("Ok")
                                           .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                               @Override
                                               public void onClick(SweetAlertDialog sDialog) {
                                                   sDialog.dismissWithAnimation();
                                               }
                                           })
                                           .show();
                               }else {
                                   SharedPreference preference=new SharedPreference(getApplicationContext());
                                   preference.putString("token",response);
                                   Log.e("the token is ",response);
                                   mainBinding.cardSubmitInterface.setVisibility(View.GONE);
                                   CvUploadFragment cvUploadFragment=new CvUploadFragment();
                                   FragmentManager fragmentManager = getSupportFragmentManager();
                                   fragmentManager.beginTransaction().replace(R.id.container, cvUploadFragment).commit();
                               }

                           }
                       });
               }
           }
       });
    }

    private boolean checkMandatoryField() {
        boolean status=true;
        if (mainBinding.password.getText().toString().length()<4){
            mainBinding.password.setError("Enter Valid Password");
            return false;
        }
        if (mainBinding.email.getText().toString().length()<11){
            mainBinding.email.setError("Enter Valid Email");
            return false;
        }

        return status;
    }

    private boolean checkEmailPattern() {
        String emailPtrn=mainBinding.email.getText().toString();
        Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailPtrn);
        if (matcher.find()==false){
            mainBinding.email.setError("Enter Valid Email");
        }
        return !matcher.find();
    }
}