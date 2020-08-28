package com.example.healtyapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.healtyapp.database_item.ActivityPhUser;
import com.example.healtyapp.R;
import com.example.healtyapp.database_item.PhysicalActivity;

import java.util.ArrayList;

public class HistoriquePhysiqueAdapter extends ArrayAdapter<PhysicalActivity> {

    private static final  String TAG ="DemandeList";
    private Context context;
    private int resource;

    public HistoriquePhysiqueAdapter(@NonNull Context context, int resource, @NonNull ArrayList<PhysicalActivity> liste) {
        super(context, resource, liste);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        convertView = inflater.inflate(this.resource,parent,false);

        TextView name,duration,calories;

        name = convertView.findViewById(R.id.historyph_name);
        duration = convertView.findViewById(R.id.historyph_duration);
        calories = convertView.findViewById(R.id.historyph_cal);

        name.setText(getItem(position).getTitle());
        calories.setText(String.valueOf(getItem(position).getCalories_burned()));
        duration.setText(String.valueOf(getItem(position).getDuree()));

        return convertView;
    }
}
