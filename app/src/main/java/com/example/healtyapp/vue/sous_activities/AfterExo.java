package com.example.healtyapp.vue.sous_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.healtyapp.R;
import com.example.healtyapp.vue.app_initialisation.MainWelcome;
import com.example.healtyapp.vue.center_activities.MainMyMenu;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;

public class AfterExo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_exo);

        Timer timer = new Timer();
        //text.setText("Welcome "+MainMyMenu.user.getFirst_name());

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                finish();
            }
        },3500);

    }
}