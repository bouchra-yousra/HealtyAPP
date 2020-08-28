package com.example.healtyapp.systeme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.healtyapp.R;
import com.example.healtyapp.module.ExercicePhysique;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainAddActPh extends AppCompatActivity {

    EditText title,level,etapes,calories,objectif;
    Button send;

    DatabaseReference myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.systeme_add_activity_physique);

        title=findViewById(R.id.exo_title);
        level=findViewById(R.id.exo_level);

        etapes=findViewById(R.id.exo_etapes);
        calories=findViewById(R.id.exo_cal);
        objectif = findViewById(R.id.exo_obj);

        send = findViewById(R.id.send);



        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExercicePhysique exercicePhysique = new ExercicePhysique();

                exercicePhysique.setTitle(title.getText().toString());

                exercicePhysique.setLevel(level.getText().toString());
                if(etapes.getText().toString().isEmpty()){
                    ArrayList<String> f = new ArrayList<>();
                    f.add(title.getText().toString());
                    exercicePhysique.setEtapes(f);
                }else{
                    exercicePhysique.setEtapes(changeIt(etapes.getText().toString()));
                }

                exercicePhysique.setMET(Double.parseDouble(calories.getText().toString()));
                exercicePhysique.setObjectif(objectif.getText().toString());
                UploadIT(exercicePhysique);
            }
        });


    }

    public void UploadIT(ExercicePhysique exercicePhysique){
        myDatabase = FirebaseDatabase.getInstance().getReference();
        myDatabase.child("ActivityPh").child(exercicePhysique.getTitle()).setValue(exercicePhysique);

    }



    public ArrayList<String> changeIt(String text){
        String x = "";
        text = "-"+text;
        ArrayList<String> arrayList = new ArrayList<>();
        for(int i =0;i<text.length();i++){
            if(text.charAt(i) == ';'){
                x="-"+x;
                arrayList.add(x);
                x="";
            }else{
                x= x+text.charAt(i);
            }
        }
        return arrayList;
    }
}