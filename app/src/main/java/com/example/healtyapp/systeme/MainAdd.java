package com.example.healtyapp.systeme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.healtyapp.R;
import com.example.healtyapp.database_item.Aliment;
import com.example.healtyapp.module.Calorie;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainAdd extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText nom,lipide,protine,glicide,eau;
    Spinner type,categorie;
    Button send;
    DatabaseReference myDatabase;
    String cat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.systeme_add_aliment);

        nom = findViewById(R.id.nom_aliment);

        categorie = findViewById(R.id.spinner_categorie);
        lipide = findViewById(R.id.lipid_aliment);
        protine = findViewById(R.id.protine_aliment);
        glicide = findViewById(R.id.glicide_aliment);
        send = findViewById(R.id.send);
        eau = findViewById(R.id.eau_aliment);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categorie_aliment, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorie.setAdapter(adapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Aliment aliment = new Aliment();
                aliment.setNom(nom.getText().toString());

                aliment.setCategorie(cat);
                aliment.setPourcentage_eau(Integer.parseInt(eau.getText().toString()));
                double l,g,p;
                l=Double.parseDouble(lipide.getText().toString());
                p = Double.parseDouble(protine.getText().toString());
                g = Double.parseDouble(glicide.getText().toString());
                aliment.setCalorie(new Calorie(l,p,g));
                Send(aliment);
            }
        });
    }

    public void Send(Aliment aliment){
        myDatabase = FirebaseDatabase.getInstance().getReference();
        myDatabase.child("Aliments").child(aliment.getNom()).setValue(aliment);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        cat = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}