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
import com.example.healtyapp.database_item.WeightTrakerItem;

import java.util.ArrayList;

public class WeightTrakerAdapter extends ArrayAdapter<WeightTrakerItem> {
    private static final  String TAG ="ExerciceCognitive";
    private Context context;
    private int resource;

    public WeightTrakerAdapter (@NonNull Context context, int resource, @NonNull ArrayList<WeightTrakerItem> liste){
        super(context, resource, liste);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(this.context);
        convertView = inflater.inflate(this.resource,parent,false);

        TextView date,weight;

        date = convertView.findViewById(R.id.date);
        weight = convertView.findViewById(R.id.weight);

        date.setText(String.valueOf(getItem(position).getWeight()));
        weight.setText(String.valueOf(getItem(position).getDate()));
        return convertView;
    }
}
