package com.example.healtyapp.ui.activityphysic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.healtyapp.R;
import com.example.healtyapp.adapter.HistoriquePhysiqueAdapter;
import com.example.healtyapp.database_item.PhysicalActivity;
import com.example.healtyapp.vue.center_activities.MainMyMenu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

import static com.example.healtyapp.ui.activityphysic.ActivityphysicFragment.methode;


public class HistoryUserPh extends AppCompatActivity {

    //public static ArrayList<ActivityPhUser> activityPhUsers;
    public static ArrayList<PhysicalActivity> activityPhUsers;
    ListView listView;
    TextView all_cal,all_time;
    HistoriquePhysiqueAdapter historyPhAdapter;

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
        setContentView(R.layout.historique_physique);
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
        listView = findViewById(R.id.recent_history);

        methodexX();
        getHistory();

    }

    public  void methodexX(){
        int x=0,y=0;

        for(int i = 0 ; i<activityPhUsers.size();i++){
            x+=activityPhUsers.get(i).getCalories_burned();
            y+=activityPhUsers.get(i).getDuree();
        }

        all_cal.setText(String.valueOf(x)+ "kCal");
        all_time.setText(String.valueOf(y)+" Min");
    }

    //DATA
    public void getHistory(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("UserActivitys").child(MainMyMenu.user.getIdUser()).child(today).child("Physique");
        HistoryUserPh.activityPhUsers = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if (snapshot.exists()) {
                        if (snapshot.child("calories_burned").exists() && snapshot.child("duree").exists() && snapshot.child("title").exists())
                         {   int duree = snapshot.child("duree").getValue(Integer.class);
                            int calorie = snapshot.child("calories_burned").getValue(Integer.class);
                            String title = snapshot.child("title").getValue(String.class);
                            PhysicalActivity a = new PhysicalActivity(title,duree,calorie);
                            HistoryUserPh.activityPhUsers.add(a);
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
    }

    private void updatelist () {
        listView.setAdapter(null);

        historyPhAdapter = new HistoriquePhysiqueAdapter(getApplicationContext(),R.layout.item_historyactph,activityPhUsers);
        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
        layoutParams.height = methode((activityPhUsers.size()*100)+5,this); //this is in pixels
        listView.setLayoutParams(layoutParams);
        historyPhAdapter.notifyDataSetChanged();
        listView.setAdapter(null);

        listView.setAdapter(historyPhAdapter);
        methodexX();
    }
}