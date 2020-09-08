package com.example.healtyapp.vue.app_initialisation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healtyapp.Enumeration.gender;
import com.example.healtyapp.R;
import com.example.healtyapp.dialogue.DatePickerFragment;
import com.example.healtyapp.dialogue.DialogueChangePassword;
import com.example.healtyapp.module.Birthday;
import com.example.healtyapp.module.User;
import com.example.healtyapp.vue.sous_activities.MainUserChooseActivityLevel;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener , DatePickerDialog.OnDateSetListener  {

    TextInputLayout a,b;
    EditText height,weihgt;
    LinearLayout homme,femme;
    Spinner maladie;
    CheckBox healthy,sportif,stress;
    LinearLayout next;
    String maladie_name;
    String user_gender = "";
    LinearLayout birthday,back;
    Boolean bday_valide = false;
    Birthday user_birthday = null;
    TextView textbday;

    int color1 = R.drawable.custom_button1;
    int color2 = R.drawable.custom_button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_activity_sign_up);
        textbday = findViewById(R.id.textbday);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                finish();
            }
        });

        a = findViewById(R.id.edit_signup_height);
        height = a.getEditText();

        b = findViewById(R.id.edit_signup_weight);
        weihgt = b.getEditText();

        birthday = findViewById(R.id.birthday);
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        homme = findViewById(R.id.homme);
        femme = findViewById(R.id.femme);

        homme.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                user_gender = gender.HOMME.toString();
                gerer_gender_color();
            }
        });

        femme.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                user_gender = gender.FEMME.toString();
                gerer_gender_color();
            }
        });

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
                    intent.putExtra("Sexe",user_gender);

                    if (user_birthday != null) {
                        intent.putExtra("birthday_day",user_birthday.getDay());
                        intent.putExtra("birthday_month",user_birthday.getMonth());
                        intent.putExtra("birthday_year",user_birthday.getYear());
                    } else {
                        finish();
                    }
                    /*
                    if (verifier_gender())
                        intent.putExtra("Sexe",gender.HOMME.toString());
                    else
                        intent.putExtra("Sexe",gender.FEMME.toString());*/

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
            if(verifier_gender()){
                if(bday_valide)
                    return true;
                else
                    Toast.makeText(getApplicationContext(),"Check your birthday",Toast.LENGTH_SHORT).show();
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void gerer_gender_color() {


        if (user_gender.equals(gender.FEMME.toString()) ) {
            femme.setBackground(getDrawable(color1));
            homme.setBackground(getDrawable(color2));
            //Toast.makeText(getApplicationContext(),gender.FEMME.toString()+"|"+user.getSexe(),Toast.LENGTH_SHORT).show();

        } else  {
            homme.setBackground(getDrawable(color1));
            femme.setBackground(getDrawable(color2));
            //Toast.makeText(getApplicationContext(),gender.HOMME.toString() + "|"+user.getSexe(),Toast.LENGTH_SHORT).show();
        }

    }

    boolean verifier_gender () {
        if (user_gender.equals(gender.FEMME.toString()) || user_gender.equals(gender.HOMME.toString()))
            return true;
        return false;
    }

    boolean verifier_text (EditText t, int max) {
        if (!t.getText().toString().isEmpty() && t.getText().toString().length() <= max)
            return true;
        return false;
    }

    boolean verifier_date (int minage, Calendar bday) {
        int age = 0;
        Calendar c = Calendar.getInstance();
        if ( c.get(Calendar.YEAR) - bday.get(Calendar.YEAR) >= minage){
            if ( c.get(Calendar.MONTH) - bday.get(Calendar.MONTH) >= 0){
                if ( c.get(Calendar.DAY_OF_MONTH) - bday.get(Calendar.DAY_OF_MONTH) >= 0)
                    age = c.get(Calendar.YEAR) - bday.get(Calendar.YEAR);
                else
                    age = c.get(Calendar.YEAR) - bday.get(Calendar.YEAR) -1;
            }else
                age = c.get(Calendar.YEAR) - bday.get(Calendar.YEAR);
        }
        else{
            Toast.makeText(getApplicationContext(),"Try again", Toast.LENGTH_SHORT).show();
            return false;}

        if (c.get(Calendar.MONTH) - bday.get(Calendar.MONTH) == 0 && c.get(Calendar.DAY_OF_MONTH) - bday.get(Calendar.DAY_OF_MONTH) == 0)
            Toast.makeText(getApplicationContext(),"Happy birthday", Toast.LENGTH_SHORT).show();
        return true;
    }

    public void openDialog() {
        DialogueChangePassword exampleDialog = new DialogueChangePassword();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        //TextView textView = (TextView) findViewById(R.id.textView);
        //textView.setText(currentDateString);

        bday_valide = verifier_date(7,c);
        if(bday_valide) {
            user_birthday =new Birthday(c);
            textbday.setText(String.valueOf(user_birthday.calculate_age(c)));
        }
    }
}
