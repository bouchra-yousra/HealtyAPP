package com.example.healtyapp.vue.app_initialisation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.healtyapp.Enumeration.gender;
import com.example.healtyapp.R;
import com.example.healtyapp.vue.sous_activities.MainUserChooseActivityLevel;
import com.google.android.material.textfield.TextInputLayout;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextInputLayout a,b;
    EditText height,weihgt;
    RadioButton homme,femme;
    Spinner maladie;
    CheckBox healthy,sportif,stress;
    LinearLayout next;
    String maladie_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_activity_sign_up);

        a = findViewById(R.id.edit_signup_height);
        height = a.getEditText();

        b = findViewById(R.id.edit_signup_weight);
        weihgt = b.getEditText();

        homme = findViewById(R.id.homme);
        femme = findViewById(R.id.femme);

        maladie= findViewById(R.id.spinner);

        healthy = findViewById(R.id.obj1);
        sportif = findViewById(R.id.obj2);
        stress = findViewById(R.id.obj3);

        next = findViewById(R.id.btnNxt);
        ArrayAdapter<CharSequence> adapter_maladie = ArrayAdapter.createFromResource(this,R.array.maladie,android.R.layout.simple_spinner_dropdown_item);

        adapter_maladie.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        maladie.setAdapter(adapter_maladie);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number =0;
                if(isValidate()){
                    Intent intent= new Intent(SignUpActivity.this, MainUserChooseActivityLevel.class);
                    intent.putExtra("Height",height.getText().toString());
                    intent.putExtra("Weight",weihgt.getText().toString());
                    intent.putExtra("Maladie",maladie_name);

                    if (homme.isChecked())
                        intent.putExtra("Sexe",gender.HOMME.toString());
                    else
                        intent.putExtra("Sexe",gender.FEMME.toString());

               /*
                    if(healthy.isChecked()){
                       number =(number*10)+1;
                    }

                    if(sportif.isChecked()){
                        number= (number*10)+2;
                    }

                    if(stress.isChecked()){
                       number=(number*10)+3;
                    }

                    if(homme.isChecked()){
                        intent.putExtra("Sexe", gender.HOMME);
                    }else{ intent.putExtra("Sexe",gender.FEMME); }

                    intent.putExtra("Number",number);*/
                    startActivity(intent);
                }
            }
        });

    }

    public boolean isValidate(){

        if(!height.getText().toString().isEmpty()
                && !weihgt.getText().toString().isEmpty()
                && verifier_number(weihgt, 2,150,25)
                && verifier_number(height, 3,250,125)){

            if(homme.isChecked() || femme.isChecked()){
               // if(healthy.isChecked()|| sportif.isChecked() || stress.isChecked()){
                    return true;
              /*  }else{
                    Toast.makeText(getApplicationContext(),"Verfier Les Objectifs",Toast.LENGTH_SHORT).show();
                }*/
            }else{
                Toast.makeText(getApplicationContext(),"Select your gender",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getApplicationContext(),"Check your informations",Toast.LENGTH_SHORT).show();
        }

        return  false;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        maladie_name = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    boolean verifier_number (EditText t, int max_lenght,int max,int min) {
        if (!t.getText().toString().isEmpty()
                && t.getText().toString().length() <= max_lenght
                && Integer.parseInt(t.getText().toString()) <= max
                && Integer.parseInt(t.getText().toString()) >= min)
            return true;
        return false;
    }
}
