package com.example.myemploess.data;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myemploess.pojo.Employees;

@Database(entities = {Employees.class}, version = 2, exportSchema = false)
public abstract class EmployeeData extends RoomDatabase {
    private static EmployeeData data;
    private static final String BASE_NAME = "employee.db";

    private static final Object LOCK = new Object();
    public static EmployeeData getInstance(Context context){
        synchronized (LOCK) {
            if (data == null) {
                data = Room.databaseBuilder(context, EmployeeData.class, BASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
            }
            return data;
        }
    }

    public abstract EmployeeDao employeeDao();
}
