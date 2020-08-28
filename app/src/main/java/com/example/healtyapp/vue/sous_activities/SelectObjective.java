package com.example.healtyapp.vue.sous_activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.example.healtyapp.R;
import com.example.healtyapp.module.BienEtre;
import com.example.healtyapp.module.PreventionBurnOut;
import com.example.healtyapp.module.Sport;
import com.example.healtyapp.module.User;
import com.example.healtyapp.vue.app_initialisation.SignUpActivity2;
import com.example.healtyapp.vue.center_activities.MainMyMenu;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SelectObjective extends AppCompatActivity {
    LinearLayout bienetre, prevention, sport;
    Button next;

    Intent intent;
    String height,weight,sexe,maladie;
    int level,number;

    public static boolean created = false;
    boolean obj1 = false,
            obj2 = false,
            obj3 = false;

    final int color2 = R.drawable.custom_button7;
    final int color1 = R.drawable.custom_button1;

    User user;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_objective);

        intent = getIntent();

        bienetre = findViewById(R.id.bienetre);
        prevention = findViewById(R.id.prevenu);
        sport = findViewById(R.id.sport);
        next = findViewById(R.id.setgoal);

        //created means the user exist (false: means we're in authentification | true: means the user want to change his objs)

        if (created) {
            next.setText("Set your goals");
            obj1 = prepare(BienEtre.titre,bienetre);
            obj2 = prepare(PreventionBurnOut.titre,prevention);
            obj3 = prepare(Sport.titre,sport);
        }

        else
            next.setText("next");

        bienetre.setBackground(getDrawable(color2));
        sport.setBackground(getDrawable(color2));
        prevention.setBackground(getDrawable(color2));

        bienetre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obj1 = selected_obj(bienetre,obj1);
            }
        });

        prevention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obj2 = selected_obj(prevention,obj2);
            }
        });

        sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obj3 = selected_obj(sport,obj3);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (created) {
                    if (obj1 || obj2 || obj3){
                        if(obj1){
                            update_list_obj (BienEtre.titre);
                        }else
                            deletefrom_list_obj (BienEtre.titre);
                        if(obj3){
                            update_list_obj (Sport.titre);
                        }else
                            deletefrom_list_obj (Sport.titre);
                        if(obj2){
                            update_list_obj (PreventionBurnOut.titre);
                        }else
                            deletefrom_list_obj (PreventionBurnOut.titre);

                        set_database_obj ();}
                    else
                        Toast.makeText(getApplicationContext(),"Chooose a goal please",Toast.LENGTH_SHORT).show();
                    finish();
                    //
                }else {
                    if (obj1 || obj2 || obj3)
                    {   if(obj1){
                        number =(number*10)+1;
                        }
                        if(obj3){
                            number= (number*10)+2;
                        }
                        if(obj2){
                            number=(number*10)+3;
                        }

                        height = intent.getStringExtra("Height");
                        weight = intent.getStringExtra("Weight");
                        maladie = intent.getStringExtra("Maladie");
                        sexe = intent.getStringExtra("Sexe");
                        intent.getIntExtra("Level",0);

                        passe_info();}
                    else
                        Toast.makeText(getApplicationContext(),"select your goals?",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void deletefrom_list_obj(String titre) {
        for (int i = 0; i < MainMyMenu.user.getObjectifs().size(); i++) {
            if (MainMyMenu.user.getObjectifs().get(i).equals(titre)){
                MainMyMenu.user.getObjectifs().remove(titre);
                return;
            }
        }
    }

    private void set_database_obj() {
        DatabaseReference data = FirebaseDatabase.getInstance().getReference().child("Users").child(MainMyMenu.user.getIdUser()).child("objectifs");
        data.setValue(MainMyMenu.user.getObjectifs());
    }


    private void update_list_obj(String titre) {
        for (int i = 0; i < MainMyMenu.user.getObjectifs().size(); i++) {
            if (MainMyMenu.user.getObjectifs().get(i).equals(titre)) {
                return;
            }
        }

        MainMyMenu.user.getObjectifs().add(titre);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private boolean prepare (String titre, LinearLayout l) {
        for (int i = 0; i < MainMyMenu.user.getObjectifs().size(); i++) {
            if (MainMyMenu.user.getObjectifs().get(i).equals(titre)){

                return selected_obj(l,false);
            }
        }
        return selected_obj(l,true);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    boolean selected_obj (LinearLayout l, boolean obj) {
        //obj is true
        if (obj) {
            //deselectionner
             l.setBackground(getDrawable(color2));
             return false;
        } else {
            //selectionner
            l.setBackground(getDrawable(color1));
            return true;
        }
    }

    void set_user_objective () {
        user.getObjectifs().clear();
        user.setObjectifs(new ArrayList<String>());

        if (obj1)
            user.getObjectifs().add(BienEtre.titre);
        if (obj2)
            user.getObjectifs().add(PreventionBurnOut.titre);
        if (obj3)
            user.getObjectifs().add(Sport.titre);
    }

    void passe_info () {
        Intent intent1 = new Intent(SelectObjective.this, SignUpActivity2.class);
        intent1.putExtra("Height",height);
        intent1.putExtra("Weight",weight);
        intent1.putExtra("Maladie",maladie);
        intent1.putExtra("Sexe",sexe);
        intent1.putExtra("Number",number);
        intent1.putExtra("Level",level);
        startActivityForResult(intent1,0);
    }
}