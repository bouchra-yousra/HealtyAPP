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
import com.example.healtyapp.database_item.Aliment;
import com.example.healtyapp.database_item.Aliment_historique;

import java.util.ArrayList;
import java.util.Objects;

public class HistoriqueNutritionAdapter extends ArrayAdapter<Aliment_historique> {
    private static final  String TAG ="DemandeList";
    private Context context;
    private int resource;

    public HistoriqueNutritionAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Aliment_historique> liste) {
        super(context, resource, liste);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(this.context);
        convertView = inflater.inflate(this.resource,parent,false);

        TextView name,quantite,calories,lipide,glucide,protein;

        int quantite_al = Objects.requireNonNull(getItem(position)).getAlimentData().getQuantite();
        String name_ = Objects.requireNonNull(getItem(position)).getAlimentData().getNom();

        Aliment aliment = getItem(position).getAliment();
        double calorie_al = (aliment.getCalorie().getTotal() * quantite_al)/100;
        double lipide_al = (aliment.getCalorie().getLipide() * quantite_al)/100;
        double glucide_al = (aliment.getCalorie().getGlicide() * quantite_al)/100;
        double protine_al = (aliment.getCalorie().getProtine() * quantite_al)/100;

        name = convertView.findViewById(R.id.name);
        quantite = convertView.findViewById(R.id.quantite);
        calories = convertView.findViewById(R.id.calorie);
        lipide = convertView.findViewById(R.id.lipide);
        glucide = convertView.findViewById(R.id.glucide);
        protein = convertView.findViewById(R.id.protine);

        name.setText(name_);
        calories.setText(String.valueOf(calorie_al)+" kcal");
        lipide.setText(String.valueOf(lipide_al)+" kcal");
        glucide.setText(String.valueOf(glucide_al)+" kcal");
        protein.setText(String.valueOf(protine_al)+" kcal");
        quantite.setText(String.valueOf(quantite_al)+" g");

        return convertView;
    }
}