package com.example.healtyapp.vue.sous_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.healtyapp.R;
import com.example.healtyapp.module.Birthday;
import com.example.healtyapp.vue.app_initialisation.SignUpActivity;
import com.example.healtyapp.vue.app_initialisation.SignUpActivity2;

public class MainUserChooseActivityLevel extends AppCompatActivity {
    Intent intent ;
    LinearLayout level0,level1,level2,level3,back;
    String height,weight,sexe,maladie;
    int number;
    Birthday bday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sous_activity_user_choose_level);

        intent = getIntent();
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainUserChooseActivityLevel.this, SignUpActivity.class));
                finish();
            }
        });

        level0 = findViewById(R.id.level0);
        level1 = findViewById(R.id.level1);
        level3 = findViewById(R.id.level3);
        level2 = findViewById(R.id.level2);

        height = intent.getStringExtra("Height");
        weight = intent.getStringExtra("Weight");
        maladie = intent.getStringExtra("Maladie");
        sexe = intent.getStringExtra("Sexe");
        number = intent.getIntExtra("Number",0);

        bday = new Birthday(intent.getIntExtra("birthday_day",1),intent.getIntExtra("birthday_month",1),intent.getIntExtra("birthday_year",2000));

        level0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passe_info(0);

            }
        });
        level1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passe_info(1);
            }
        });
        level2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passe_info(2);
            }
        });
        level3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passe_info(3);
            }
        });

        SelectObjective.created = false;
    }

    void passe_info (int n) {
        Intent intent1 = new Intent(MainUserChooseActivityLevel.this,SelectObjective.class);
        intent1.putExtra("Height",height);
        intent1.putExtra("Weight",weight);
        intent1.putExtra("Maladie",maladie);
        intent1.putExtra("Sexe",sexe);
        intent1.putExtra("Number",number);
        intent1.putExtra("Level",n);

        intent1.putExtra("birthday_day",bday.getDay());
        intent1.putExtra("birthday_month",bday.getMonth());
        intent1.putExtra("birthday_year",bday.getYear());

        startActivityForResult(intent1,0);
        finish();
    }
}