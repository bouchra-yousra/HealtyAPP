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
import android.widget.TextView;
import android.widget.Toast;

import com.example.healtyapp.R;
import com.example.healtyapp.Tests.Test;
import com.example.healtyapp.vue.center_activities.MainActivity;
import com.example.healtyapp.vue.sous_activities.MainForgetpw;
import com.example.healtyapp.vue.sous_activities.SelectObjective;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class LoginActivity extends AppCompatActivity {

    //BDD
    private FirebaseAuth myAuth;
    private DatabaseReference mDatabase;
    //SharedPreferces
    SharedPreferences userShare;
    TextInputLayout a,b,c;
    EditText username,mail,password;
    LinearLayout login;
    TextView create_account, forget_password;
    TextView forget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_activity_login);
        userShare = getSharedPreferences("MyUser", MODE_PRIVATE);

        a = findViewById(R.id.login_edittext_email);
        mail = a.getEditText();

        b = findViewById(R.id.login_edittext_password);
        password = b.getEditText();

        login = findViewById(R.id.login_button_login);
        create_account = findViewById(R.id.login_creat_account);
        forget_password = findViewById(R.id.login_forgetpswd);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValide()){
                    Login(mail.getText().toString(),password.getText().toString());
                }
            }
        });
        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            }
        });

        forget = findViewById(R.id.login_forgetpswd);
        forget.setClickable(true);
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SelectObjective.class));
            }
        });
    }

    public boolean isValide(){
        if(/*valideMail(mail) &&*/!mail.getText().toString().isEmpty()){
            if(/*validePassword(password) &&**/!password.getText().toString().isEmpty()){
                return  true;
            }else{
                Toast.makeText(getApplicationContext(),"Verfier password",Toast.LENGTH_SHORT).show();

            }
        }else{
            Toast.makeText(getApplicationContext(),"Verfier Mail",Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    public void Login(final String mail, final String password){
        final String TAG = "SIGN IN";
        myAuth = FirebaseAuth.getInstance();

        myAuth.signInWithEmailAndPassword(mail, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user1 = myAuth.getCurrentUser();
                            SharedPreferences.Editor editor= userShare.edit();
                            editor.putBoolean("Logged",true);
                            editor.putString("Email",mail);
                            editor.putString("Password",password);
                            editor.commit();
                            editor.apply();
                            MainActivity.getCurrnentUser(mail);
                            startActivity(new Intent(LoginActivity.this,MainWelcome.class));
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    //todo
    public boolean validePassword(String password) {
        if(password.length() == 8)
            return true;
        return false;
    }

}
