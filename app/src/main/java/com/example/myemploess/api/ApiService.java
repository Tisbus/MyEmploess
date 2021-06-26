package com.example.myemploess.api;

import com.example.myemploess.pojo.EmployeeResponse;


import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {
    @GET("testTask.json")
    Observable<EmployeeResponse> getEmployee();
}
