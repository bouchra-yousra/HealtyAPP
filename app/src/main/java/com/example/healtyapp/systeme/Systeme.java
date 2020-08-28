package com.example.healtyapp.systeme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.healtyapp.R;

public class Systeme extends AppCompatActivity {

    Button button1,button2,button3,button4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_systeme);


        //systeme buttons
        button1 = findViewById(R.id.systemph);
        button2 = findViewById(R.id.systemecng);
        button3 = findViewById(R.id.systemenut);
        button3 = findViewById(R.id.systemeobj);

         button1.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 moveToAddPh();
             }
         });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToAddGn();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToAddAliment();
            }
        });
    }

    //Add
    void moveToAddAliment() {
        startActivity(new Intent(getApplicationContext(), MainAdd.class));
    }
    void moveToAddPh() {
        startActivity(new Intent(getApplicationContext(), MainAddActPh.class));
    }
    void moveToAddGn() {
        startActivity(new Intent(getApplicationContext(), MainAddActM.class));
    }

}