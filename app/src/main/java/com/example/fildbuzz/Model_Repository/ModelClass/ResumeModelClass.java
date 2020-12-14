package com.example.fildbuzz.Model_Repository.ModelClass;

import retrofit2.http.Field;

public class ResumeModelClass {
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

    public ResumeModelClass(String tsync_id, String name, String email, String phone, String full_address,
                            String name_of_university, String graduation_year, String cgpa,
                            String experience_in_months, String current_work_place_name, String applying_in,
                            String expected_salary, String field_buzz_reference, String github_project_url,
                            CvFile_tsync_id cv_file, String on_spot_update_time, String on_spot_creation_time)
    {
        this.tsync_id = tsync_id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.full_address = full_address;
        this.name_of_university = name_of_university;
        this.graduation_year = graduation_year;
        this.cgpa = cgpa;
        this.experience_in_months = experience_in_months;
        this.current_work_place_name = current_work_place_name;
        this.applying_in = applying_in;
        this.expected_salary = expected_salary;
        this.field_buzz_reference = field_buzz_reference;
        this.github_project_url = github_project_url;
        this.cv_file = cv_file;
        this.on_spot_update_time = on_spot_update_time;
        this.on_spot_creation_time = on_spot_creation_time;
    }
}
