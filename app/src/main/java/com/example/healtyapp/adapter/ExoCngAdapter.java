package com.example.healtyapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.healtyapp.R;
import com.example.healtyapp.module.ExerciceCognitive;

import java.util.ArrayList;

public class ExoCngAdapter extends ArrayAdapter<ExerciceCognitive> {
    private static final  String TAG ="ExerciceCognitive";
    private Context context;
    private int resource;

    public ExoCngAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ExerciceCognitive> liste){
        super(context, resource, liste);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(this.context);
        convertView = inflater.inflate(this.resource,parent,false);

        TextView name,duration;

        name = convertView.findViewById(R.id.itemMname);
        duration = convertView.findViewById(R.id.itemMduration);

        name.setText(getItem(position).getTitle());
        duration.setText(String.valueOf(getItem(position).getDuree())+" min");
        return convertView;
    }
}
