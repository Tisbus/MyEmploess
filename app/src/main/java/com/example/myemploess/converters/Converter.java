package com.example.myemploess.converters;

import androidx.room.TypeConverter;

import com.example.myemploess.pojo.Speciality;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Converter {
    @TypeConverter
    public String getConverterToString(List<Speciality> speciality){
        return new Gson().toJson(speciality);
    }
    public List<Speciality> stringToListSpeciality(String line){
        Gson gson = new Gson();
        ArrayList object = gson.fromJson(line, ArrayList.class);
        ArrayList<Speciality> specialities = new ArrayList<>();
        for (Object o : object) {
            specialities.add(gson.fromJson(o.toString(), Speciality.class));
        }
        return specialities;
    }
}
