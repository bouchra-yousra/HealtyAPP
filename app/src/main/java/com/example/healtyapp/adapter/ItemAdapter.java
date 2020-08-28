package com.example.healtyapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.healtyapp.adapter_item.ItemHistory;
import com.example.healtyapp.R;

import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<ItemHistory> {

    private static final  String TAG ="DemandeList";
    private Context context;
    private int resource;

    public ItemAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ItemHistory> liste) {
        super(context, resource, liste);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(this.context);
        convertView = inflater.inflate(this.resource,parent,false);

        TextView name,size;

        name = convertView.findViewById(R.id.name);
        size = convertView.findViewById(R.id.size);

        name.setText(getItem(position).getName());
        size.setText(String.valueOf(getItem(position).getSize()));

        return convertView;
    }
}
