package com.example.healtyapp.ui.activitymorale;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.healtyapp.adapter.ExoCngAdapter;
import com.example.healtyapp.R;
import com.example.healtyapp.module.ExerciceCognitive;
import com.example.healtyapp.vue.center_activities.MainMyMenu;

import java.util.ArrayList;

public class MainBreathing extends AppCompatActivity {
    ListView listExercices;

    ArrayList<ExerciceCognitive> arrayList = new ArrayList<>();
    ExoCngAdapter exoCngAdapter;

    LinearLayout back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_breathing);
        setTitle("Breathing technique");
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        //step 1/2
        listExercices = findViewById(R.id.list_breathing);

        exoCngAdapter = new ExoCngAdapter(this,R.layout.itemactivitymoral,arrayList);

        //get Only activities with type equal Breathing technique
        for(int i = 0; i< MainMyMenu.exerciceCognitives.size(); i++){
            if(MainMyMenu.exerciceCognitives.get(i).getType().equals("Breathing technique")){
                //here
                arrayList.add(MainMyMenu.exerciceCognitives.get(i));
            }
        }

        //make listeView's height = list of activites size * 60(item height)
        ViewGroup.LayoutParams layoutParams = listExercices.getLayoutParams();
        layoutParams.height = methode((arrayList.size()*60)+5,this); //this is in pixels
        listExercices.setLayoutParams(layoutParams);
        exoCngAdapter.notifyDataSetChanged();
        listExercices.setAdapter(null);
        listExercices.setAdapter(exoCngAdapter);

        //step 2/2
        listExercices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivityM.exerciceCognitive = (ExerciceCognitive) parent.getItemAtPosition(position);
                startActivity(new Intent(MainBreathing.this,MainActivityM.class));
            }
        });
    }

    //convert dp into pixel
    public static int methode(float dp, Context context){
        Resources resources = context.getResources();
        int i = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                resources.getDisplayMetrics()
        );
        return i;
    }


}