package com.example.myemploess.screens.employees;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.myemploess.R;
import com.example.myemploess.adapters.EmployeeAdapter;
import com.example.myemploess.api.ApiFactory;
import com.example.myemploess.api.ApiService;
import com.example.myemploess.pojo.EmployeeResponse;
import com.example.myemploess.pojo.Employees;
import com.example.myemploess.pojo.Speciality;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class EmployeeListActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private EmployeeAdapter adapter;
    private EmployeeViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerViewEmployee);
        adapter = new EmployeeAdapter();
        adapter.setEmployees(new ArrayList<Employees>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        model = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(EmployeeViewModel.class);
        model.getEmployees().observe(this, new Observer<List<Employees>>() {
            @Override
            public void onChanged(List<Employees> employees) {
                adapter.setEmployees(employees);
                for(Employees e : employees){
                    List<Speciality> specialities = e.getSpecialty();
                    for (Speciality s: specialities
                         ) {
                        Log.i("Result", s.getName());
                    }
                }
            }
        });
        model.getErrors().observe(this, new Observer<Throwable>() {
            @Override
            public void onChanged(Throwable throwable) {
                if(throwable != null){
                    Toast.makeText(EmployeeListActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    model.clearError();
                }
            }
        });
        model.loadData();
    }
}