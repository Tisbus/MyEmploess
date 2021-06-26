package com.example.myemploess.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EmployeeResponse {
    @SerializedName("response")
    @Expose
    private List<Employees> response = null;

    public List<Employees> getEmployees() {
        return response;
    }

    public void setResponse(List<Employees> response) {
        this.response = response;
    }
}
