package com.example.myemploess.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myemploess.R;
import com.example.myemploess.pojo.Employees;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeHolder> {

    public List<Employees> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employees> employees) {
        this.employees = employees;
        notifyDataSetChanged();
    }

    private List<Employees> employees;

    @Override
    public EmployeeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_item, parent, false);
        return new EmployeeHolder(view);
    }

    @Override
    public void onBindViewHolder(EmployeeAdapter.EmployeeHolder holder, int position) {
        Employees employee = employees.get(position);
        holder.textViewNames.setText(employee.getFirstName());
        holder.textViewLastNames.setText(employee.getLastName());
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    class EmployeeHolder extends RecyclerView.ViewHolder{

        private TextView textViewNames;
        private TextView textViewLastNames;
        public EmployeeHolder(View itemView) {
            super(itemView);
            textViewNames = itemView.findViewById(R.id.textViewName);
            textViewLastNames = itemView.findViewById(R.id.textViewLastName);
        }
    }
}
