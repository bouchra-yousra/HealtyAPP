package com.example.healtyapp.systeme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.healtyapp.R;
import com.example.healtyapp.module.ExerciceCognitive;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainAddActM extends AppCompatActivity {

    EditText title,type,etapes,duree;
    Button send;
    ExerciceCognitive exerciceCognitive;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.systeme_add_activity_moral);

        exerciceCognitive = new ExerciceCognitive();

        title = findViewById(R.id.actmtitle);
        type = findViewById(R.id.actmtype);
        etapes = findViewById(R.id.actmetapes);
        duree = findViewById(R.id.actmduree);

        send = findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInfo();
                sendTobDD(exerciceCognitive);
            }
        });
    }


    private  void getInfo(){
        String et;
        et = etapes.getText().toString();
        ArrayList<String> et1 = new ArrayList<>();
        et1 = divise(et);

        exerciceCognitive = new ExerciceCognitive(title.getText().toString(),type.getText().toString(),et1);
        exerciceCognitive.setDuree(Integer.parseInt(duree.getText().toString()));
    }

    public void sendTobDD(ExerciceCognitive exerciceCognitive){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("ActivityM").child(exerciceCognitive.getTitle()).setValue(exerciceCognitive);

    }

    private  ArrayList<String> divise(String word){
        ArrayList arrayList = new ArrayList();
        String a="";

        for(int i=0;i<word.length();i++){
            if(String.valueOf(word.charAt(i)).equals(";")){
                arrayList.add(a);
                a="";
            }else{
                a=a+word.charAt(i);
            }
        }
        return  arrayList;
    }
}