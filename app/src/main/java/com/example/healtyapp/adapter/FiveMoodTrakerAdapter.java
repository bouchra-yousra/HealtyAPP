package com.example.healtyapp.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.healtyapp.R;
import com.example.healtyapp.database_item.MoodTrakerItem;

import java.util.ArrayList;

public class FiveMoodTrakerAdapter extends ArrayAdapter<MoodTrakerItem[]> {
    private static final  String TAG ="Mood Traker";
    private Context context;
    private int resource;

    public FiveMoodTrakerAdapter(@NonNull Context context, int resource, @NonNull ArrayList<MoodTrakerItem[]> liste){
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

        TextView[] date = new TextView[4], mood = new TextView[4];
        ImageView[] icon_mood = new ImageView[4];
        LinearLayout[] linearLayouts = new LinearLayout[4];

        date[0] = convertView.findViewById(R.id.date1);
        mood[0] = convertView.findViewById(R.id.mood1);
        icon_mood[0] = convertView.findViewById(R.id.icom_mood1);
        linearLayouts[0] = convertView.findViewById(R.id.layout1);

        date[1] = convertView.findViewById(R.id.date2);
        mood[1] = convertView.findViewById(R.id.mood2);
        icon_mood[1] = convertView.findViewById(R.id.icom_mood2);
        linearLayouts[1] = convertView.findViewById(R.id.layout2);

        date[2] = convertView.findViewById(R.id.date3);
        mood[2] = convertView.findViewById(R.id.mood3);
        icon_mood[2] = convertView.findViewById(R.id.icom_mood3);
        linearLayouts[2] = convertView.findViewById(R.id.layout3);

        date[3] = convertView.findViewById(R.id.date4);
        mood[3] = convertView.findViewById(R.id.mood4);
        icon_mood[3] = convertView.findViewById(R.id.icom_mood4);
        linearLayouts[3] = convertView.findViewById(R.id.layout4);

        for (int i = 0; i < 4; i++) {
            if (getItem(position)[i] == null)
                linearLayouts[i].setVisibility(View.GONE);
            else
            {
                icon_mood[i].setImageResource(getItem(position)[i].Moodicon());
                mood[i].setText(String.valueOf(MoodTrakerItem.moodByNumber(getItem(position)[i].getMood())));
                date[i].setText(String.valueOf(getItem(position)[i].getDate()));
            }
        }


        return convertView;
    }
}
