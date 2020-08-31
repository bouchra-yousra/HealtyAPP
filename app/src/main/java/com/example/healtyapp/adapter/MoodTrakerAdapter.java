package com.example.healtyapp.adapter;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healtyapp.R;
import com.example.healtyapp.database_item.MoodTrakerItem;
import com.example.healtyapp.database_item.WeightTrakerItem;

import java.util.ArrayList;

public class MoodTrakerAdapter extends ArrayAdapter<MoodTrakerItem> {
    private static final  String TAG ="Mood Traker";
    private Context context;
    private int resource;

    public MoodTrakerAdapter (@NonNull Context context, int resource, @NonNull ArrayList<MoodTrakerItem> liste){
        super(context, resource, liste);
        this.context = context;
        this.resource = resource;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(this.context);
        convertView = inflater.inflate(this.resource,parent,false);

        TextView date,mood;
        ImageView icon_mood;

        date = convertView.findViewById(R.id.date);
        mood = convertView.findViewById(R.id.mood);
        icon_mood = convertView.findViewById(R.id.icom_mood);

        icon_mood.setImageResource(getItem(position).Moodicon());

        mood.setText(String.valueOf(MoodTrakerItem.moodByNumber(getItem(position).getMood())));
        date.setText(String.valueOf(getItem(position).getDate()));
        return convertView;
    }
}
