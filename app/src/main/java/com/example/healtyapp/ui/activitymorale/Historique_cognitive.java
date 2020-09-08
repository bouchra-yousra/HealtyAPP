package com.example.healtyapp.ui.activitymorale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.healtyapp.R;
import com.example.healtyapp.adapter.HistoriqueCognitiveAdaoter;
import com.example.healtyapp.database_item.CognitieActivite;
import com.example.healtyapp.vue.center_activities.MainActivity;
import com.example.healtyapp.vue.center_activities.MainMyMenu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

import static com.example.healtyapp.ui.activityphysic.ActivityphysicFragment.methode;

public class Historique_cognitive extends AppCompatActivity {

    //elements
    public static ArrayList<CognitieActivite> activityCognitive;
    ListView listView;
    TextView all_cal,all_time;
    HistoriqueCognitiveAdaoter historyCngAdapter;

    //today
    Calendar calSelected = Calendar.getInstance();
    String today = "" + calSelected.get(Calendar.DAY_OF_MONTH)
            + (calSelected.get(Calendar.MONTH) + 1)
            + calSelected.get(Calendar.YEAR);

    //data
    DatabaseReference databaseReference;

    //no activity
    LinearLayout laynoact,back;

    //drawble
    final int color1 = R.drawable.custom_button1;
    final int color2 = R.drawable.custom_button3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historique_cognitive_);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        laynoact = findViewById(R.id.layout_noact);

        all_cal = findViewById(R.id.all_cal);
        all_time = findViewById(R.id.all_time);
        all_time.setText(0+" Min");

        listView = findViewById(R.id.liste_exo_cognitive);
        getHistory();
        //updatelist();
    }

    //vue methodes
    public  void methodexX(){
        int x=0,y=0;
        for(int i = 0 ; i<activityCognitive.size();i++){
            //x += activityCognitive.get(i).getCalories_burned();
            y += activityCognitive.get(i).getDuree();
        }

        //all_cal.setText(String.valueOf(x)+ "Cal");
        all_time.setText(String.valueOf(y)+" Min");
    }

    public void updatelist () {
        listView.setAdapter(null);

        if (activityCognitive.isEmpty())
            return;

        historyCngAdapter = new HistoriqueCognitiveAdaoter(getApplicationContext(),R.layout.item_historyactph,activityCognitive);
        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
        layoutParams.height = methode((activityCognitive.size()*100)+5,this); //this is in pixels
        listView.setLayoutParams(layoutParams);
        historyCngAdapter.notifyDataSetChanged();
        listView.setAdapter(null);

        listView.setAdapter(historyCngAdapter);
        methodexX();
    }

    //DATA
    public void getHistory(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("UserActivitys").child(getSharedPreferences(MainActivity.MyUser,MODE_PRIVATE).getString("UserId",MainMyMenu.user.getIdUser())).child(today).child("Cognitive");
        Historique_cognitive.activityCognitive = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if (snapshot.exists()) {
                        if (snapshot.child("title").exists() && snapshot.child("duree").exists()) {
                            String titre = snapshot.child("title").getValue(String.class);
                            int duree = snapshot.child("duree").getValue(Integer.class);
                            CognitieActivite a = new CognitieActivite (titre,duree);
                            Historique_cognitive.activityCognitive.add(a);
                            updatelist();
                            laynoact.setVisibility(View.GONE);
                        }
                     }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        //
    }

}