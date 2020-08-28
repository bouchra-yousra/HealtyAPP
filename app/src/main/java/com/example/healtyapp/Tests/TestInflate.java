package com.example.healtyapp.Tests;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.healtyapp.R;
import com.example.healtyapp.module.ExercicePhysique;
import com.example.healtyapp.vue.center_activities.MainMyMenu;

/*
*cet inflater consider the dialogue layout only, right?
*  */
public class TestInflate extends AppCompatDialogFragment {

    //dialogue elements  (application of an physical exercise: title, duration)
    private EditText editTextDuration;
    public static ExercicePhysique exercicePhysique = new ExercicePhysique();
    private TextView textViewName,textViewBurned;
    private TestInflateLisnner listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view =  inflater.inflate(R.layout.dialogue_item_test_inflate, null);
        builder.setView(view)
                .setTitle("Exercice a faire")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int calories = 0;
                        if(!editTextDuration.getText().toString().trim().isEmpty() && Integer.parseInt(editTextDuration.getText().toString())>0) {
                            //calcul calorie burned
                            calories = (int)HowMuchCaloriesBurnExo(exercicePhysique,Integer.parseInt(editTextDuration.getText().toString()));

                            //transfere les donnees vers le listner
                            listener.applyTexts(calories,Double.parseDouble(editTextDuration.getText().toString()), exercicePhysique);
                        } else {
                            Toast.makeText(getContext(),"Try again",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        editTextDuration = view.findViewById(R.id.bd_exo_duration);
        textViewName = view.findViewById(R.id.bd_exo_name);
        textViewBurned = view.findViewById(R.id.bd_exo_burn);
        textViewName.setText(exercicePhysique.getTitle());

        editTextDuration.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            //show how much calories this exercise will burn in the given duration
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //int i = Integer.parseInt(editTextDuration.getText().toString());
                if(!editTextDuration.getText().toString().trim().isEmpty() && Integer.parseInt(editTextDuration.getText().toString())>0){
                    String phrase = "Burned Calories :  "+String.valueOf((int)HowMuchCaloriesBurnExo(exercicePhysique,Integer.parseInt(editTextDuration.getText().toString())));
                    phrase+=" kcal in "+editTextDuration.getText().toString()+" Minutes";
                    textViewBurned.setText(phrase);
                }else{
                    textViewBurned.setText("please check the duration again!");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener =(TestInflateLisnner) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement TestInflateLisnner");
        }
    }

    public interface TestInflateLisnner {
        void applyTexts(int calories, double time, ExercicePhysique exercicePhysique);
    }

    public double HowMuchCaloriesBurnExo(ExercicePhysique exercicePhysique, int min){

        double MET = exercicePhysique.getMET();
        double poids = MainMyMenu.poids;

        //calcul des calorie brulee
        double KcalPerMin = (MET * 3.5 * poids)/200;
        double KcalTotal = KcalPerMin*min;

        return KcalTotal;
    }
}