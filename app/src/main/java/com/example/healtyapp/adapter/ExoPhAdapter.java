package com.example.healtyapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.healtyapp.module.ExercicePhysique;
import com.example.healtyapp.vue.center_activities.MainMyMenu;
import com.example.healtyapp.R;

import java.util.ArrayList;

public class ExoPhAdapter extends ArrayAdapter<ExercicePhysique> {

    private static final  String TAG ="ActivyPh";
    private Context context;
    private int resource;

    public ExoPhAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ExercicePhysique> liste){
        super(context, resource, liste);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        convertView = inflater.inflate(this.resource,parent,false);

        TextView name,burned;

        name = convertView.findViewById(R.id.actphname);
        burned = convertView.findViewById(R.id.burned);
        name.setText(getItem(position).getTitle());
        //size.setText(String.valueOf(getItem(position).getSize()));
        burned.setText("This exercise burn : "+String.valueOf((int)HowMuchCaloriesBurnExo(getItem(position)))+
                "kcal in 1 minute");

        return convertView;
    }

    public double HowMuchCaloriesBurnExo(ExercicePhysique exercicePhysique){
        double MET = exercicePhysique.getMET();
        double poids = MainMyMenu.poids;
        double KcalPerMin = (MET * 3.5 * poids)/200;
        double KcalTotal = KcalPerMin*1;        //est t'il necessaire???

        return KcalTotal;
    }
}
