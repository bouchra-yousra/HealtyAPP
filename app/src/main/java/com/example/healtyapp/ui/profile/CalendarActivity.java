package com.example.healtyapp.ui.profile;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healtyapp.R;
import com.example.healtyapp.adapter.HistoriqueCognitiveAdaoter;
import com.example.healtyapp.adapter.HistoriqueNutritionAdapter;
import com.example.healtyapp.adapter.HistoriquePhysiqueAdapter;
import com.example.healtyapp.database_item.Aliment;
import com.example.healtyapp.database_item.AlimentData;
import com.example.healtyapp.database_item.Aliment_historique;
import com.example.healtyapp.database_item.CognitieActivite;
import com.example.healtyapp.database_item.PhysicalActivity;
import com.example.healtyapp.ui.activitymorale.Historique_cognitive;
import com.example.healtyapp.ui.activityphysic.HistoryUserPh;
import com.example.healtyapp.vue.center_activities.MainActivity;
import com.example.healtyapp.vue.center_activities.MainMyMenu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.example.healtyapp.ui.activityphysic.ActivityphysicFragment.methode;

public class CalendarActivity extends AppCompatActivity {

    //calendar
    CalendarView calendar;
    Calendar nextYear = Calendar.getInstance();

    //element
    ListView listView;
    ImageView a,b,c;
    public static ArrayList<PhysicalActivity> activityPhysique;
    public static ArrayList<CognitieActivite> activityCognitive;
    public static ArrayList<Aliment_historique> activityNutrition;
    int progress_physique,progress_cognitive,progress_nutrition;

    //no activity
    TextView txtnoact;
    LinearLayout laynoact;
    final String n0 = "Select an activity type";
    final String n1 = "No activities";
    final String n2 = "Stay tuned";

    //drawble
    final int color1 = R.drawable.custom_button1;
    final int color2 = R.drawable.custom_button3;

    //adapters
    HistoriquePhysiqueAdapter historyPhAdapter;
    HistoriqueCognitiveAdaoter historyCngAdapter;
    HistoriqueNutritionAdapter historiqueNutritionAdapter;

    //data
    DatabaseReference databaseReference;
    SharedPreferences share;

    //affichage
    String choice = c1;
    static final String c1 = "physique",c2 = "cognitive",c3 = "nutrition";

    String dateselected ;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        txtnoact = findViewById(R.id.text_noact);
        txtnoact.setText(n0);
        laynoact = findViewById(R.id.layout_noact);

        Calendar k = Calendar.getInstance();
        dateselected = "" + k.get(Calendar.DAY_OF_MONTH)
                + (k.get(Calendar.MONTH) + 1)
                + k.get(Calendar.YEAR);

        a = findViewById(R.id.btnph);
        b = findViewById(R.id.btncng);
        c = findViewById(R.id.btnnut);

        prepare(a,b,c);

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice = c1;
                gerer_color (a,b,c);
                gere_affichage(dateselected);
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice = c2;
                gerer_color (b,c,a);
                gere_affichage(dateselected);
                Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();
            }
        });
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice = c3;
                gerer_color (c,a,b);
                gere_affichage(dateselected);
            }
        });

        nextYear.add(Calendar.YEAR, 1);
        calendar = findViewById(R.id.calendarView);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

                //String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(date);
                Calendar calSelected = Calendar.getInstance();
                calSelected.set(i,i1,i2);
                String selectedDate = "" + calSelected.get(Calendar.DAY_OF_MONTH)
                        + (calSelected.get(Calendar.MONTH) + 1)
                        + calSelected.get(Calendar.YEAR);

                //garder la date selectionner
                dateselected = selectedDate;

                //future
                if (calSelected.compareTo(Calendar.getInstance()) > 0){

                    activityPhysique = new ArrayList<>();
                    activityCognitive = new ArrayList<>();
                    activityNutrition = new ArrayList<>();

                    updatelist_cognitive();
                    updatelist_nutrition();
                    updatelist_physique();

                    txtnoact.setText(n2);
                    laynoact.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "try again", Toast.LENGTH_SHORT).show();
                }
                //today/past
                else {
                    txtnoact.setText(n1);
                    laynoact.setVisibility(View.VISIBLE);
                    gere_affichage(selectedDate);
                }

            }
        });

        listView = findViewById(R.id.list);
        listView.setAdapter(null);


    }


    //DATA
    //PHYSIQUE
    public void getHistory_physique(String today){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("UserActivitys").child(MainMyMenu.user.getIdUser()).child(today).child("Physique");
        activityPhysique = new ArrayList<>();

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
                            activityPhysique.add(a);
                            updatelist_physique();
                            laynoact.setVisibility(View.GONE);
                        }
                    } else
                        laynoact.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void updatelist_physique () {
        listView.setAdapter(null);

        if (activityPhysique.isEmpty()) {
            laynoact.setVisibility(View.VISIBLE);
            return;
        }

        historyPhAdapter = new HistoriquePhysiqueAdapter(getApplicationContext(),R.layout.item_historyactph,activityPhysique);
        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
        layoutParams.height = methode((activityPhysique.size()*100)+5,this); //this is in pixels
        listView.setLayoutParams(layoutParams);
        historyPhAdapter.notifyDataSetChanged();
        listView.setAdapter(null);

        listView.setAdapter(historyPhAdapter);
        //methodexX();
    }

    //COGNITIVE
    public void getHistory_cognitive(String today){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("UserActivitys").child(MainMyMenu.user.getIdUser()).child(today).child("Cognitive");
        activityCognitive = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if (snapshot.exists()) {
                        if (snapshot.child("title").exists() && snapshot.child("duree").exists()) {
                            String titre = snapshot.child("title").getValue(String.class);
                            int duree = snapshot.child("duree").getValue(Integer.class);
                            CognitieActivite a = new CognitieActivite (titre,duree);
                            activityCognitive.add(a);

                            updatelist_cognitive();
                            updatelist_nutrition();
                            updatelist_physique();

                            Toast.makeText(getApplicationContext(), choice, Toast.LENGTH_SHORT).show();

                            laynoact.setVisibility(View.GONE);
                           //
                        }
                    }else
                        laynoact.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        Toast.makeText(getApplicationContext(), "data", Toast.LENGTH_SHORT).show();
    }

    public void updatelist_cognitive () {
        listView.setAdapter(null);

        if (activityCognitive.isEmpty()) {
            laynoact.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "update return", Toast.LENGTH_SHORT).show();
            return;
        }


        historyCngAdapter = new HistoriqueCognitiveAdaoter(getApplicationContext(),R.layout.item_historyactph,activityCognitive);
        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
        layoutParams.height = methode((activityCognitive.size()*100)+5,this); //this is in pixels
        listView.setLayoutParams(layoutParams);
        historyCngAdapter.notifyDataSetChanged();
        //listView.setAdapter(null);

        listView.setAdapter(historyCngAdapter);
        Toast.makeText(getApplicationContext(), "update finished cng", Toast.LENGTH_SHORT).show();
    }

    //NUTRITION
    public void getHistory_nutrition(String today){
        if (MainMyMenu.user.getIdUser() == null)
            databaseReference = FirebaseDatabase.getInstance().getReference().child("UserActivitys").child(share.getString("UserId", MainMyMenu.user.getIdUser())).child(today).child("Nutrition");
        else
            databaseReference = FirebaseDatabase.getInstance().getReference().child("UserActivitys").child(MainMyMenu.user.getIdUser()).child(today).child("Nutrition");
        activityNutrition = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if (snapshot.exists()) {
                        if (snapshot.child("nom").exists() && snapshot.child("quantite").exists()) {
                            String nom = snapshot.child("nom").getValue(String.class);
                            int quantite= snapshot.child("quantite").getValue(Integer.class);
                            AlimentData a = new AlimentData (nom,quantite);
                            if (getAliment(a) != null)
                                activityNutrition.add(new Aliment_historique(a,getAliment(a)));
                            updatelist_nutrition();
                            laynoact.setVisibility(View.GONE);
                        }
                    } else
                        laynoact.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        //
    }

    public void updatelist_nutrition () {
        listView.setAdapter(null);

        if (activityNutrition.isEmpty()){
            laynoact.setVisibility(View.VISIBLE);
            return;}

        historiqueNutritionAdapter = new HistoriqueNutritionAdapter(getApplicationContext(),R.layout.item_historique_nutrition,activityNutrition);
        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
        layoutParams.height = methode((activityNutrition.size()*100)+5,this); //this is in pixels
        listView.setLayoutParams(layoutParams);
        historiqueNutritionAdapter.notifyDataSetChanged();
        listView.setAdapter(null);

        listView.setAdapter(historiqueNutritionAdapter);
    }


    //Vue
    void gere_affichage (String key) {

        if (txtnoact.getText().equals(n0)) {
            txtnoact.setText(n1);
        }

        //choice = c1;
        switch (choice) {
            case c1:
                Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();

                activityPhysique = new ArrayList<>();
                activityCognitive = new ArrayList<>();
                activityNutrition = new ArrayList<>();
                updatelist_physique();

                getHistory_physique(key);
                break;
            case c2:
                Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();

                activityPhysique = new ArrayList<>();
                activityCognitive = new ArrayList<>();
                activityNutrition = new ArrayList<>();
                updatelist_cognitive();

                getHistory_cognitive(key);
                break;
            case c3:
                Toast.makeText(getApplicationContext(), "3", Toast.LENGTH_SHORT).show();

                activityPhysique = new ArrayList<>();
                activityCognitive = new ArrayList<>();
                activityNutrition = new ArrayList<>();
                updatelist_nutrition();

                getHistory_nutrition(key);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void gerer_color(ImageView a, ImageView b, ImageView c) {
        a.setBackground(getDrawable(color1));
        b.setBackground(getDrawable(color2));
        c.setBackground(getDrawable(color2));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void prepare(ImageView a, ImageView b, ImageView c) {
        a.setBackground(getDrawable(color2));
        b.setBackground(getDrawable(color2));
        c.setBackground(getDrawable(color2));
    }

    //get element
    public Aliment getAliment (AlimentData a) {
        ArrayList <Aliment> arrayList = MainMyMenu.arrayList;
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i) != null && arrayList.get(i).getNom().trim().equals(a.getNom()))
                return arrayList.get(i);
        }
        return null;
    }
}
