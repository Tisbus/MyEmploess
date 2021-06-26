package com.example.myemploess.data;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.myemploess.pojo.Employees;

import java.util.List;

@Dao
public interface EmployeeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Employees> employeesList);
    @Query("DELETE FROM employee")
    void deleteAll();
    @Query("SELECT * FROM employee")
    LiveData<List<Employees>> getAllEmployee();
}
