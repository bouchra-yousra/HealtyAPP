package com.example.healtyapp.ui.nutrition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.example.healtyapp.adapter.HistoriqueNutritionAdapter;
import com.example.healtyapp.adapter.ItemAdapter;
import com.example.healtyapp.adapter_item.ItemHistory;
import com.example.healtyapp.R;
import com.example.healtyapp.database_item.Aliment;
import com.example.healtyapp.database_item.AlimentData;
import com.example.healtyapp.database_item.Aliment_historique;
import com.example.healtyapp.vue.center_activities.MainActivity;
import com.example.healtyapp.vue.center_activities.MainMyMenu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import static com.example.healtyapp.ui.activityphysic.ActivityphysicFragment.methode;

public class NutritionHistoryActivity extends AppCompatActivity {
    HistoriqueNutritionAdapter historiqueNutritionAdapter;
    public static  ArrayList<Aliment_historique> activityNutrition = new ArrayList<>();

    ItemAdapter itemAdapter;
    public static  ArrayList<ItemHistory> myArray = new ArrayList<>();
    TextView all_cal,all_time;

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


    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historique_nutrition);
        all_cal = findViewById(R.id.all_cal);
        all_time = findViewById(R.id.all_time);
        methodexX();

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        laynoact = findViewById(R.id.layout_noact);

        listView = findViewById(R.id.liste_v);

        listView.setAdapter(null);
        getHistory();

        /*
        itemAdapter = new ItemAdapter(getApplicationContext(),R.layout.listeitem,myArray);
        itemAdapter.notifyDataSetChanged();
        listView.setAdapter(null);
        listView.setAdapter(itemAdapter);*/
    }


    public void updatelist () {
        listView.setAdapter(null);

        if (activityNutrition.isEmpty())
            return;

        historiqueNutritionAdapter = new HistoriqueNutritionAdapter(getApplicationContext(),R.layout.item_historique_nutrition,activityNutrition);
        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
        layoutParams.height = methode((activityNutrition.size()*100)+5,this); //this is in pixels
        listView.setLayoutParams(layoutParams);
        historiqueNutritionAdapter.notifyDataSetChanged();
        listView.setAdapter(null);

        listView.setAdapter(historiqueNutritionAdapter);
        methodexX();
    }

    //DATA
    public void getHistory(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("UserActivitys").child(Objects.requireNonNull(getSharedPreferences(MainActivity.MyUser, MODE_PRIVATE).getString("UserId", MainMyMenu.user.getIdUser()))).child(today).child("Nutrition");
        activityNutrition = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    int quantite = 0;
                    if (snapshot.exists()) {
                        if (snapshot.child("nom").exists()) {
                            String nom = snapshot.child("nom").getValue(String.class);
                            if (snapshot.child("quantite").exists())
                                 quantite = (int) snapshot.child("quantite").getValue(Integer.class);
                            AlimentData a = new AlimentData (nom,quantite);
                            if (getAliment(a) != null)
                            activityNutrition.add(new Aliment_historique(a,getAliment(a)));
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

    public Aliment getAliment (AlimentData a) {
        ArrayList <Aliment> arrayList = MainMyMenu.arrayList;
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i) != null && arrayList.get(i).getNom().trim().equals(a.getNom()))
                return arrayList.get(i);
        }
        return null;
    }

    public  void methodexX(){
        int x=0,y=0;

        for(int i = 0 ; i<activityNutrition.size();i++){
            x+=activityNutrition.get(i).getAliment().getCalorie().getTotal();
            y+=activityNutrition.get(i).getAlimentData().getQuantite();
        }

        all_cal.setText(String.valueOf(x)+ "kCal");
        all_time.setText(String.valueOf(y)+" g");
    }
}