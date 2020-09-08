package com.example.healtyapp.vue.app_initialisation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.healtyapp.R;
import com.example.healtyapp.module.BienEtre;
import com.example.healtyapp.module.Birthday;
import com.example.healtyapp.module.ObjectiveUser;
import com.example.healtyapp.module.PreventionBurnOut;
import com.example.healtyapp.module.Sport;
import com.example.healtyapp.module.User;
import com.example.healtyapp.vue.center_activities.MainMyMenu;
import com.example.healtyapp.vue.sous_activities.SelectObjective;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class SignUpActivity2 extends AppCompatActivity {

    //BDD
    private FirebaseAuth myAuth;
    private DatabaseReference mDatabase;

    //
    TextInputLayout a,b,c,d,e,f;
    EditText first_name,last_name,username,email,password;
    LinearLayout create,back;
    Intent intent;
    User user;
    SharedPreferences userShare;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_activity_sign_up2);
        intent = getIntent();
        userShare = getSharedPreferences("MyUser", MODE_PRIVATE);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectObjective.created = false;
                startActivity(new Intent(SignUpActivity2.this,SelectObjective.class));
                finish();
            }
        });


        a = findViewById(R.id.signup_first_name);
        first_name = a.getEditText();

        b  = findViewById(R.id.signup_last_name);
        last_name = b.getEditText();

        c  = findViewById(R.id.signup_username);
        username = c.getEditText();

       // d  = findViewById(R.id.signup_year_name);
       // year = d.getEditText();

        e  = findViewById(R.id.signup_email);
        email = e.getEditText();

        f = findViewById(R.id.signup_password);
        password = f.getEditText();

        create=findViewById(R.id.btnSignup);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValidate()){
                    CreateUser();
                }
            }
        });

    }

    public boolean isValidate(){
        if(validename(first_name) && !first_name.getText().toString().isEmpty()){
            if(validename(last_name)&&!last_name.getText().toString().isEmpty() ){
                if(valideusername(username) && !username.getText().toString().isEmpty()){
                    if(validemail(email) && !email.getText().toString().isEmpty()){
                        if(validepassword(password) && !password.getText().toString().isEmpty() && password.getText().toString().length()>=8){
                                        return  true;
                        }else{
                                Toast.makeText(getApplicationContext(),"Verfier Password",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"Verfier Mail",Toast.LENGTH_SHORT).show();
                    }
                }else{
                        Toast.makeText(getApplicationContext(),"Verfier UserName",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getApplicationContext(),"Verfier Laste Name",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getApplicationContext(),"Verfier First Name",Toast.LENGTH_SHORT).show();
        }
        return false;
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

    private boolean validename(EditText last_name) {
        return verifier_text(last_name, 10);
    }

    private boolean valideusername(EditText username) {
        return verifier_text(username, 10);
    }

    private boolean validemail(EditText email) {
        /*String p = email.getText().toString();
        String condition = "@gmail.com";
        if (p.length()> (5 + condition.length()) && p.contains(condition))
            return true;
        return false;*/
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches();
    }

    private boolean validepassword(EditText password) {
        String p = password.getText().toString();
        if (p.length()>8 &&
                (p.contains("0") ||
                p.contains("1") ||
                p.contains("2") ||
                p.contains("3") ||
                p.contains("4") ||
                p.contains("5") ||
                p.contains("6") ||
                p.contains("7") ||
                p.contains("8") ||
                p.contains("9")    )
        )
            return true;
        return false;
    }

    public void getAllUserInformation(){
        int number;
        int currentyear = Calendar.getInstance().get(Calendar.YEAR);
        ArrayList<String> x = new ArrayList<>();
        user = new User();
        user.setFirst_name(first_name.getText().toString());
        user.setLast_name(last_name.getText().toString());
        //hadi mghdich ndirha drwk dok nfahmek 3lah user.setIdUser();
        user.setUsername(username.getText().toString());
        user.setEmail(email.getText().toString());
        user.setPassword(password.getText().toString());

        //user.setAge(String.valueOf(currentyear-Integer.parseInt(year.getText().toString())));

        Birthday bday = new Birthday(intent.getIntExtra("birthday_day",1),intent.getIntExtra("birthday_month",1),intent.getIntExtra("birthday_year",2000));
        //user.setBirthday_(bday);
        user.setAge(String.valueOf(user.getAgeFromBirthday(bday)));

        user.setPoids(intent.getStringExtra("Weight"));
        user.setMaladie(intent.getStringExtra("Maladie"));
        user.setTaille(intent.getStringExtra("Height"));
        user.setLevel(String.valueOf(intent.getIntExtra("Level",0)));
        number = intent.getIntExtra("Number",0);

        String obj1,obj2,obj3;
        obj1 = BienEtre.titre;
        obj2= Sport.titre;
        obj3 = PreventionBurnOut.titre;

        switch (number){
            case 0:
            case 1:
                x.add(obj1);
                break;
            case 2:
                x.add(obj2);
                break;
            case 3:
                x.add(obj3);
                break;
            case 12:
                x.add(obj1);
                x.add(obj2);
                break;
            case 13:
                x.add(obj1);
                x.add(obj3);
                break;
            case 23:
                x.add(obj2);
                x.add(obj3);

                break;

            case 123:
                x.add(obj1);
                x.add(obj2);
                x.add(obj3);
                break;

        }

        user.setObjectifs(x);
        user.setSexe(intent.getStringExtra("Sexe"));
        user.setMetabolisme();
        user.setBesoin_energy();
        user.setBesoin_eau();

    }

    public void CreateUser(){
        getAllUserInformation();
        CreatOnAuthSystem(user.getEmail(),user.getPassword());
    }

    public  void CreateUserOnRealTimeBDD(FirebaseUser user1,User user){
        user.setIdUser(user1.getUid());//
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Users").child(user.getIdUser()).setValue(user);
    }

    public void CreatOnAuthSystem(String mail,String password){
        myAuth = FirebaseAuth.getInstance();
        final String TAG = "Sign IN";
        myAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");

                            FirebaseUser user1= myAuth.getCurrentUser();
                            CreateUserOnRealTimeBDD(user1,user);
                            SharedPreferences.Editor editor= userShare.edit();
                            editor.putBoolean("Logged",true);
                            editor.putString("Email",user.getEmail());
                            editor.putString("Password",user.getPassword());
                            editor.commit();
                            editor.apply();
                            startActivity(new Intent(SignUpActivity2.this,MainWelcome.class));
                            finish();
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity2.this, "Authentication échouer ",
                                    Toast.LENGTH_SHORT).show();

                        }
                        // ...
                    }
                });
    }



    private void put_obj(User user) {
        for (int i = 0; i < user.getObjectifs().size(); i++) {
            add_database_obj_detail(user.getObjectifs().get(i));
        }
    }

    //add objective
    private void add_database_obj_detail(String titre) {
        ObjectiveUser objective = new ObjectiveUser(titre);
        DatabaseReference data = FirebaseDatabase.getInstance().getReference().child("Objectif").child("ObjectiveUser").child(MainMyMenu.user.getIdUser()).child(titre);
        data.setValue(objective);
    }

}
