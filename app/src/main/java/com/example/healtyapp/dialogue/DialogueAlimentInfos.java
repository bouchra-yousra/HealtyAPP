package com.example.healtyapp.dialogue;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.healtyapp.R;
import com.example.healtyapp.database_item.Aliment;
import com.example.healtyapp.ui.activitymorale.MainTomate;
import com.example.healtyapp.ui.nutrition.NutritionFragment;

import java.util.Objects;

public class DialogueAlimentInfos extends AppCompatDialogFragment {
    private ExplainPmodoroDialog.ExampleDialogListener listener;
    //private TextInputLayout weight,ideal;
    //EditText a,b;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view =  inflater.inflate(R.layout.dialogue_item_aliment_infos, null);
        builder .setView(view)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

        TextView name,calories,lipide,glucide,protein;

        Aliment aliment = NutritionFragment.getCurrentAliment();
        double calorie_al = aliment.getCalorie().getTotal();
        double lipide_al = aliment.getCalorie().getLipide();
        double glucide_al = aliment.getCalorie().getGlicide();
        double protine_al = aliment.getCalorie().getProtine();

        name = view.findViewById(R.id.name);
        calories = view.findViewById(R.id.calorie);
        lipide = view.findViewById(R.id.lipide);
        glucide = view.findViewById(R.id.glucide);
        protein = view.findViewById(R.id.protine);

        name.setText(String.valueOf(aliment.getNom()));
        calories.setText(String.valueOf(calorie_al)+" kcal");
        lipide.setText(String.valueOf(lipide_al)+" kcal");
        glucide.setText(String.valueOf(glucide_al)+" kcal");
        protein.setText(String.valueOf(protine_al)+" kcal");

        return builder.create();
    }
    public interface ExampleDialogListener {
        void onYesClicked();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ExplainPmodoroDialog.ExampleDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + "must implement ExampleDialogListener");
        }
    }
}