package com.example.fildbuzz.ActivityView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fildbuzz.Model_Repository.ModelClass.CvFile_tsync_id;
import com.example.fildbuzz.Model_Repository.ModelClass.ResumeModelClass;
import com.example.fildbuzz.R;
import com.example.fildbuzz.databinding.FragmentCvUploadBinding;
import com.example.fildbuzz.utils.FilePath;
import com.example.fildbuzz.utils.SharedPreference;
import com.example.fildbuzz.viewModel.FileUploadViewModel;
import com.example.fildbuzz.viewModel.FinalResumeUploadViewModel;
import com.example.fildbuzz.viewModel.LoginViewModel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;


public class CvUploadFragment extends Fragment {
    SharedPreference sharedPreference;
    static String token;
    FragmentCvUploadBinding mbinding;
    MultipartBody.Part fileToUpload;
    private Uri filePath;
    FileUploadViewModel fileUploadViewModel;
    private String firstCreationTime, endOfUrlHit;
    String bytesd;
    private String tsync_id;
    private String name;
    private String email;
    private String phone;
    private String full_address;
    private String name_of_university;
    private String graduation_year;
    private String cgpa;
    private String experience_in_months;
    private String current_work_place_name;
    private String applying_in;
    private String expected_salary;
    private String field_buzz_reference;
    private String github_project_url;
    private CvFile_tsync_id cv_file;
    private String on_spot_update_time;
    private String on_spot_creation_time;

    ResumeModelClass resumeModelClass;
    FinalResumeUploadViewModel finalResumeUploadViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreference = new SharedPreference(getContext());
        token = sharedPreference.getString("token");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mbinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_cv_upload, container, false);
        requestStoragePermission();
        fileOpenButton();
        //fileUploadViewModel=new ViewModelProvider(this).get(FileUploadViewModel.class);
        fileUploadViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(FileUploadViewModel.class);
        finalResumeUploadViewModel=new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(FinalResumeUploadViewModel.class);
        submitFinalData();
        cv_file = new CvFile_tsync_id(token);
        on_spot_creation_time = System.currentTimeMillis() + "";
        mbinding.inputPhone.addTextChangedListener(gettextchange);
        return mbinding.getRoot();
    }

    TextWatcher gettextchange = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String working = mbinding.inputPhone.getText().toString();

            int lenght = working.length();
            if (lenght == 3) {
                if (working.length() == 3 && before == 0) {
                    if (working.equals("013") || working.equals("014") || working.equals("015") || working.equals("016") ||
                            working.equals("017") || working.equals("018") || working.equals("019")) {
                        //Toast.makeText(getContext(), "Valid Operator digit", Toast.LENGTH_SHORT).show();
                    } else {
                        mbinding.inputPhone.setError("Enter valid phone number");
                        mbinding.inputPhone.setText("");
                    }
                }
            } else if (lenght <= 11 && lenght > 3) {
                String validity = working.substring(0, 3);
                if (validity.equals("013") || validity.equals("014") || validity.equals("015") || validity.equals("016") ||
                        validity.equals("017") || validity.equals("018") || validity.equals("019")) {

                } else {
                    mbinding.inputPhone.setError("Enter valid phone number");
                    mbinding.inputPhone.setText("");
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void submitFinalData() {
        mbinding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkMandatoryFields()) {
                    getAllDatas();

                    resumeModelClass = new ResumeModelClass(tsync_id, name, email, phone, full_address,
                            name_of_university, graduation_year, cgpa,
                            experience_in_months, current_work_place_name, applying_in,
                            expected_salary, field_buzz_reference, github_project_url,
                            cv_file, on_spot_update_time, on_spot_creation_time);

                    finalResumeUploadViewModel.setResumeData(token,resumeModelClass);
                    finalResumeUploadViewModel.getFinalUploadResponse().observe(getActivity(),resumeResult->{
                        String result1=resumeResult.toString();
                        if (!result1.equals("badRequest")){
                            fileUploadViewModel.setFileuploded(result1, fileToUpload);
                            fileUploadViewModel.getFileUploadResponse().observe(getActivity(), result -> {

                                String results = result.toString();
                                if (!results.equals("badRequest")){
                                    new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("Success")
                                            .setContentText("Your Profile creation Successfully")
                                            .setConfirmText("Ok")
                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sDialog) {
                                                    sDialog.dismissWithAnimation();
                                                }
                                            })
                                            .show();
                                }
                            });
                        }else {
                            new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Bad Request")
                                    .setContentText("Resume Upload Failed Please check Again")
                                    .setConfirmText("Ok")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismissWithAnimation();
                                        }
                                    })
                                    .show();
                        }

                    });
                }

            }
        });
    }

    private void getAllDatas() {
        tsync_id = token;
        name = mbinding.inputName.getText().toString();
        email = mbinding.inputEmail.getText().toString();
        phone = mbinding.inputPhone.getText().toString();
        full_address = mbinding.inputAddress.getText().toString();
        name_of_university = mbinding.inputUniversity.getText().toString();
        graduation_year = mbinding.inputGraduationEr.getText().toString();
        cgpa = mbinding.inputCgpa.getText().toString();
        experience_in_months = mbinding.inputExperience.getText().toString();
        current_work_place_name = mbinding.inputCurrentOrganisation.getText().toString();
        applying_in = mbinding.inputDeveloperType.getText().toString();
        expected_salary = mbinding.inputSalary.getText().toString();
        field_buzz_reference = mbinding.inputReference.getText().toString();
        github_project_url = mbinding.inputGithub.getText().toString();
        on_spot_update_time = System.currentTimeMillis() + "";

    }

    private boolean checkMandatoryFields() {
        boolean status = true;
        if (mbinding.inputName.getText().toString().length() < 1) {
            mbinding.inputName.setError("Enter Valid Name");
            status = false;
        }

        if (mbinding.inputEmail.getText().toString().length() < 1) {
            mbinding.inputEmail.setError("Enter Valid Email");
            status = false;
        }
        if (mbinding.inputPhone.getText().toString().length() < 11) {
            mbinding.inputPhone.setError("Enter Valid Phone number");
            status = false;
        }

        if (mbinding.inputUniversity.getText().toString().length() < 5) {
            mbinding.inputUniversity.setError("Enter Valid University Name");
            status = false;
        }

        if (mbinding.inputGraduationEr.getText().toString().length() < 2) {
            mbinding.inputGraduationEr.setError("Enter Valid Graduation Year");
            status = false;
        }
        if (mbinding.inputCgpa.getText().toString().length() < 2) {
            mbinding.inputCgpa.setError("Enter Valid CGPA");
            status = false;
        }
        if (mbinding.inputDeveloperType.getText().toString().length() < 5) {
            mbinding.inputDeveloperType.setError("Enter Application Type");
            status = false;
        }
        if (mbinding.inputSalary.getText().toString().length() < 4) {
            mbinding.inputSalary.setError("Enter Valid Salary");
            status = false;
        }
        if (mbinding.inputGithub.getText().toString().length() < 15) {
            mbinding.inputGithub.setError("Enter Your Resume");
            status = false;
        }
        if (mbinding.showFileName.getText().toString().length() < 2) {
            mbinding.showFileName.setError("Enter Your Resume");
            status = false;
        }
        return status;
    }


    private void fileOpenButton() {
        mbinding.fileOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("application/pdf");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Pdf"), 101);

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            Log.e("pdf path", filePath.getPath());
            //getting the actual path of the image
            String path = FilePath.getPathFromUri(getContext(), filePath);
            mbinding.showFileName.setText(path);
            Log.e("file path", path);
            File myFile = new File(path);
            // Read file to byte array

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                Path pdfPath = Paths.get(path);
                try {
                    byte[] pdfByteArray = Files.readAllBytes(pdfPath);
                    //bytesd= Base64.encodeToString(pdfByteArray,Base64.DEFAULT);
                    RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), pdfByteArray);
                    RequestBody Title = RequestBody.create(MediaType.parse("text/plain"), myFile.getName());
                    fileToUpload = MultipartBody.Part.createFormData("file", null, requestBody);
                    Log.e("my pdf binary file", pdfByteArray.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 102);
        }

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 103);
            }
        }


    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == 102) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                requestStoragePermission();
                Toast.makeText(getActivity(), "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(getActivity(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }

        if (requestCode == 103) {
            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                requestStoragePermission();
                Toast.makeText(getActivity(), "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(getActivity(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }


    }
}