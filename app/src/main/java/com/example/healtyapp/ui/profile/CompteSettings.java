package com.example.healtyapp.ui.profile;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.healtyapp.R;
import com.example.healtyapp.Enumeration.gender;
import com.example.healtyapp.dialogue.DatePickerFragment;
import com.example.healtyapp.dialogue.DialogueChangePassword;
import com.example.healtyapp.module.User;
import com.example.healtyapp.vue.center_activities.MainActivity;
import com.example.healtyapp.vue.center_activities.MainMyMenu;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;

@RequiresApi(api = Build.VERSION_CODES.M)
public class CompteSettings extends AppCompatActivity implements DialogueChangePassword.ExampleDialogListener, DatePickerDialog.OnDateSetListener {


    EditText firstname, lastname, username,taille, poid;
    Button modifier, change_password;
    LinearLayout birthday;
    LinearLayout homme,femme;
    boolean h,f;

    //data
    DatabaseReference path;
    User user = MainMyMenu.user;

    int color1 = R.drawable.custom_button1;
    int color2 = R.drawable.custom_button3;

    String password;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sous_activity_compte_settings);

        firstname = findViewById(R.id.editTextFirstName);
        lastname = findViewById(R.id.editTextLastName);
        username = findViewById(R.id.editTextUsername);
        taille = findViewById(R.id.editTextTaille);
        poid = findViewById(R.id.editTextPoid);

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
        change_password = findViewById(R.id.change_password);
        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        gerer_gender_color ();

        homme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setSexe(gender.HOMME.toString());
                gerer_gender_color();
            }
        });

        femme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setSexe(gender.FEMME.toString());
                gerer_gender_color();
            }
        });

        prepare();

        modifier = findViewById(R.id.modifier);
        modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_user ();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void gerer_gender_color() {


        if (user.getSexe().equals(gender.FEMME.toString()) ) {
            femme.setBackground(getDrawable(color1));
            homme.setBackground(getDrawable(color2));
            //Toast.makeText(getApplicationContext(),gender.FEMME.toString()+"|"+user.getSexe(),Toast.LENGTH_SHORT).show();

        } else  {
            homme.setBackground(getDrawable(color1));
            femme.setBackground(getDrawable(color2));
            //Toast.makeText(getApplicationContext(),gender.HOMME.toString() + "|"+user.getSexe(),Toast.LENGTH_SHORT).show();
        }

    }

    private void update_user() {
        change_user();
        path = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getIdUser());
        path.setValue(user);

        MainMyMenu.user = user;
    }

    boolean verifier_text (EditText t, int max) {
        if (!t.getText().toString().isEmpty() && t.getText().toString().length() <= max)
            return true;
        return false;
    }

    boolean verifier_number (EditText t, int max_lenght,int max,int min) {
        if (!t.getText().toString().isEmpty()
                && t.getText().toString().length() <= max_lenght
                && Integer.parseInt(t.getText().toString()) <= max
                && Integer.parseInt(t.getText().toString()) >= min)
            return true;
        return false;
    }

    void change_user () {
        if (verifier_text(firstname, 10))
            user.setFirst_name(firstname.getText().toString());

        if (verifier_text(lastname, 10))
            user.setLast_name(lastname.getText().toString());

        if (verifier_text(username, 10))
            user.setUsername(username.getText().toString());

        if (verifier_number(poid, 2,150,25))
            user.setPoids(poid.getText().toString());

        if (verifier_number(taille, 3,250,125))
            user.setTaille(taille.getText().toString());

        if(!(verifier_text(firstname, 10)
                && verifier_text(lastname, 10)
                && verifier_text(username, 10)
                && verifier_number(poid, 2,150,25)
                && verifier_number(taille, 3,250,125)))
            Toast.makeText(getApplicationContext(),"Check again please",Toast.LENGTH_SHORT).show();
    }

    void prepare () {
        firstname.setText(user.getFirst_name());
        lastname.setText(user.getLast_name());
        username.setText(user.getUsername());
        poid.setText(user.getPoids());
        taille.setText(user.getTaille());
    }

    public void openDialog() {
        DialogueChangePassword exampleDialog = new DialogueChangePassword();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }

    @Override
    public void applyTexts(String old_password, String password) {
        if (user.getPassword().equals(old_password)){
            user.setPassword(password);
            path = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getIdUser()).child("password");
            path.setValue(password);
        }
        else
            Toast.makeText(getApplicationContext(),"Try again",Toast.LENGTH_SHORT).show();
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
    }
}