package com.example.myemploess.screens.employees;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myemploess.api.ApiFactory;
import com.example.myemploess.api.ApiService;
import com.example.myemploess.data.EmployeeData;
import com.example.myemploess.pojo.EmployeeResponse;
import com.example.myemploess.pojo.Employees;

import java.util.List;
import java.util.concurrent.ExecutionException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class EmployeeViewModel extends AndroidViewModel{

    private static EmployeeData data;
    private LiveData<List<Employees>> employees;
    private MutableLiveData<Throwable> errors;

    public LiveData<Throwable> getErrors() {
        return errors;
    }

    private CompositeDisposable compositeDisposable;


    public EmployeeViewModel(Application application) {
        super(application);
        data = EmployeeData.getInstance(application);
        employees = data.employeeDao().getAllEmployee();
        errors = new MutableLiveData<>();
    }

    public LiveData<List<Employees>> getEmployees() {
        return employees;
    }

    public void clearError(){
        errors.setValue(null);
    }

    private void insertTask(List<Employees> employee){
        try {
            new InsertTask().execute(employee).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class InsertTask extends AsyncTask<List<Employees>, Void, Void>{
        @Override
        protected Void doInBackground(List<Employees>... lists) {
            if(lists != null && lists.length > 0){
                data.employeeDao().insert(lists[0]);
            }
            return null;
        }
    }

    private void deleteAll(){
        new DeleteAllTask().execute();
    }

    private static class DeleteAllTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            data.employeeDao().deleteAll();
            return null;
        }
    }

    public void loadData(){
        ApiFactory apiFactory = ApiFactory.getInstance();
        ApiService apiService = apiFactory.getApiService();
        compositeDisposable = new CompositeDisposable();
        Disposable disposable = apiService.getEmployee()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<EmployeeResponse>() {
                    @Override
                    public void accept(EmployeeResponse employee) throws Exception {
                    deleteAll();
                    insertTask(employee.getEmployees());
                }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        errors.setValue(throwable);
                    }
                });
        compositeDisposable.add(disposable);
    }


    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }
}
