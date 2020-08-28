package com.example.healtyapp.vue.app_initialisation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.healtyapp.R;
import com.example.healtyapp.module.User;
import com.example.healtyapp.vue.center_activities.MainMyMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class MainWelcome extends AppCompatActivity {

    TextView text;
    SharedPreferences share;
    Timer timer;
    static User user = new User();
    DatabaseReference myDataBase;
    ProgressBar p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        share = getSharedPreferences("MyUser", MODE_PRIVATE);
        setContentView(R.layout.init_activity_main_welcome);
        text = findViewById(R.id.welcome);
        text.setText("Please wait...");
        p = findViewById(R.id.progressBar);

        timer = new Timer();
        //text.setText("Welcome "+MainMyMenu.user.getFirst_name());


        getCurrnentUser(FirebaseAuth.getInstance().getCurrentUser().getUid());
        //text.setText("Welcome "+user.getFirst_name());

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(MainWelcome.this, MainMyMenu.class);
                intent.putExtra("Kca",user.getBesoin_energy());
                intent.putExtra("ml",user.getBesoin_eau());
                intent.putExtra("Sexe",user.getSexe());
                intent.putExtra("Poids",user.getPoids());

                startActivity(intent);
                finish();
            }
        },3500);

    }



    public void getCurrnentUser(String id){

        myDataBase = FirebaseDatabase.getInstance().getReference().child("Users").child(id);
        myDataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user1 = dataSnapshot.getValue(User.class);

            user = user1;

                p.setVisibility(View.GONE);
                text.setVisibility(View.VISIBLE);
                text.setText("Welcome "+user.getFirst_name());

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
