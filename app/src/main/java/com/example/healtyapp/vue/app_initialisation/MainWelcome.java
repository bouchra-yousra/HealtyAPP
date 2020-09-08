package com.example.healtyapp.vue.app_initialisation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.healtyapp.R;
import com.example.healtyapp.Tests.Test;
import com.example.healtyapp.module.Birthday;
import com.example.healtyapp.module.User;
import com.example.healtyapp.vue.center_activities.MainMyMenu;
import com.example.healtyapp.vue.sous_activities.MainForgetpw;
import com.example.healtyapp.vue.sous_activities.NoInternet;
import com.google.android.gms.actions.NoteIntents;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainWelcome extends AppCompatActivity {

    TextView text;
    SharedPreferences share;
    Timer timer;
    static User user = new User();
    DatabaseReference myDataBase;
    ProgressBar p;
    private WifiManager wifiManager;
    boolean wifistate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        share = getSharedPreferences("MyUser", MODE_PRIVATE);
        setContentView(R.layout.init_activity_main_welcome);
        text = findViewById(R.id.welcome);
        text.setText("Please wait...");
        text.setVisibility(View.GONE);
        p = findViewById(R.id.progressBar);
        p.setVisibility(View.GONE);

        timer = new Timer();
        //text.setText("Welcome "+MainMyMenu.user.getFirst_name());


        getCurrnentUser(FirebaseAuth.getInstance().getCurrentUser().getUid());
        //text.setText("Welcome "+user.getFirst_name());

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(MainWelcome.this, MainMyMenu.class);
                wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                if (wifiManager.isWifiEnabled()) {
                    if (user == null){
                        startActivity(new Intent(MainWelcome.this, Test.class));
                        finish();
                    }

                    intent.putExtra("Kca",user.getBesoin_energy());
                    intent.putExtra("ml",user.getBesoin_eau());
                    intent.putExtra("Sexe",user.getSexe());
                    intent.putExtra("Poids",user.getPoids());

                    startActivity(intent);
                    finish();
                } else {
                    startActivity(new Intent(MainWelcome.this, Test.class));
                    finish();
                }
            }
        },3500);

    }



    public void getCurrnentUser(final String id){

        myDataBase = FirebaseDatabase.getInstance().getReference().child("Users").child(id);
        myDataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                p.setVisibility(View.VISIBLE);
                User user1 = new User();
                if (dataSnapshot.exists())
                     user1 = dataSnapshot.getValue(User.class);
                else {
                     String first_name = dataSnapshot.child("first_name").getValue(String.class);
                     String last_name= dataSnapshot.child("last_name").getValue(String.class);
                     String age = dataSnapshot.child("age").getValue(String.class);
                     String username = dataSnapshot.child("username").getValue(String.class);
                     String email = dataSnapshot.child("email").getValue(String.class);
                     String password = dataSnapshot.child("password").getValue(String.class);
                     //ArrayList<String> objectifs = (ArrayList<String>) dataSnapshot.child("objectifs").getValue(ArrayList.class);
                     String taille = dataSnapshot.child("taille").getValue(String.class);
                     String poids = dataSnapshot.child("poids").getValue(String.class);
                     String sexe = dataSnapshot.child("sexe").getValue(String.class);
                     String maladie = dataSnapshot.child("maladie").getValue(String.class);
                     String level = dataSnapshot.child("level").getValue(String.class);
                     double metabolisme = dataSnapshot.child("metabolisme").getValue(Double.class);
                     double besoin_energy = dataSnapshot.child("besoin_energy").getValue(Double.class);
                     double besoin_eau = dataSnapshot.child("besoin_eau").getValue(Double.class);
                    // ArrayList<Integer> birthday = (ArrayList<Integer>) dataSnapshot.child("birthday").getValue(ArrayList.class);

                     user1.setIdUser(id);
                    user1.setFirst_name(first_name);
                    user1.setLast_name(last_name);
                    user1.setAge(age);
                    user1.setTaille(taille);
                    user1.setPoids(poids);
                    user1.setSexe(sexe);
                    user1.setPassword(password);
                    user1.setEmail(email);
                    user1.setUsername(username);
                    user1.setMaladie(maladie);
                    user1.setLevel(level);
                    user1.setMetabolisme(metabolisme);
                    user1.setBesoin_eau();
                    user1.setBesoin_energy();
                    //user1.setBirthday(new Birthday(birthday.get(0),birthday.get(1),birthday.get(2)));
                  //  user1.setObjectifs(objectifs);
                }
                user = user1;

                p.setVisibility(View.GONE);
                text.setText("Welcome "+user.getFirst_name());
                text.setVisibility(View.VISIBLE);
                text_animation(text);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(wifiStateReceiver, intentFilter);
    }
    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(wifiStateReceiver);
    }
    private BroadcastReceiver wifiStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int wifiStateExtra = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                    WifiManager.WIFI_STATE_UNKNOWN);
            switch (wifiStateExtra) {
                case WifiManager.WIFI_STATE_ENABLED:
                    wifistate = true;
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                    Toast.makeText(getApplicationContext(), "wifi off.",
                            Toast.LENGTH_SHORT).show();
                   // wifiSwitch.setChecked(false);
                   // wifiSwitch.setText("WiFi is OFF");
                    break;
            }
        }
    };

    private void text_animation (TextView textView) {
        YoYo.with(Techniques.SlideInLeft)
                .duration(700)
                .repeat(0)
                .playOn(textView);
    }
}
