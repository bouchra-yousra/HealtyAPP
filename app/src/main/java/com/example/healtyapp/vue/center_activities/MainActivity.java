package com.example.healtyapp.vue.center_activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.healtyapp.R;
import com.example.healtyapp.module.User;
import com.example.healtyapp.vue.app_initialisation.LoginActivity;
import com.example.healtyapp.vue.app_initialisation.MainWelcome;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Button button;
    Timer timer;
    SharedPreferences share;
    private FirebaseAuth myAuth;
    static DatabaseReference myDataBase;

    public static final String MyUser = "MyUser";
    private static String userid;

    Boolean systeme_pane = false;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_activity_main);
        share = getSharedPreferences("MyUser",MODE_PRIVATE);

        if (systeme_pane) {
            logout();
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                if(share.contains("Logged")){

                    AutoLogin(share.getString("Email",null),share.getString("Password",null));
                    getCurrnentUser(share.getString("Email",null));

                    share.edit().putString("UserId",userid);
                    share.edit().apply();

                    startActivity(new Intent(MainActivity.this, MainWelcome.class));
                    finish();

                }else{
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        },3500);
    }
    public void AutoLogin(String mail,String password){
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

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...

                    }
                });

        //hna ndir login b mail w password fel dik te3 authefication!!

    }

    public static void getCurrnentUser(final String mail){

        myDataBase = FirebaseDatabase.getInstance().getReference().child("Users");
        myDataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user1 = snapshot.getValue(User.class);

                    if(user1.getEmail().equals(mail)){
                        userid = user1.getIdUser();
                        MainMyMenu.user = user1;
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //logout
    void logout () {
        SharedPreferences.Editor edit1 = share.edit();
        edit1.clear();
        edit1.apply();

        FirebaseAuth.getInstance().signOut();
        finish();
    }

}
