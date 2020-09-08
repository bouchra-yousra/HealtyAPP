package com.example.healtyapp.ui.activityphysic;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healtyapp.R;
import com.example.healtyapp.database_item.ActivityPhUser;
import com.example.healtyapp.database_item.PhysicalActivity;
import com.example.healtyapp.module.ExercicePhysique;
import com.example.healtyapp.module.User;
import com.example.healtyapp.ui.activitymorale.MainActivityM;
import com.example.healtyapp.vue.center_activities.MainMyMenu;
import com.example.healtyapp.vue.sous_activities.AfterExo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Locale;

public class MainChrono extends AppCompatActivity {

    //chrono
    LinearLayout start,pause,back;
    TextView time,s,p,name;
    CountDownTimer mCountDownTimer;
    private long mTimeLeftInMillis;
    boolean mTimerRunning;
    double x;
    User user;
    public static double homex = 0;

    //show Activity
    public static ExercicePhysique exercicePhysique;
    ActivityPhUser activityPhUser1 = new ActivityPhUser();
    public static  TextView caloories;
    DatabaseReference mDatabase,mDatabase1;

    Button show;
    //LinearLayout layout_etaps;
    TextView text_etaps;

    //manipulate progress
    private SharedPreferences share;
    private final String SHARE = MainMyMenu.Share;
    private static final String PROGRESS_PHYSIQUE = MainMyMenu.PROGRESS_PHYSIQUE;

    //today
    Calendar calSelected = Calendar.getInstance();
    String today = "" + calSelected.get(Calendar.DAY_OF_MONTH)
            + (calSelected.get(Calendar.MONTH) + 1)
            + calSelected.get(Calendar.YEAR);

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chrono);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        share  = getSharedPreferences(SHARE,MODE_PRIVATE);

        user = MainMyMenu.user;
        if (homex > 0)
            x = homex * 60000;
        else
            x = getIntent().getDoubleExtra("Time", 0) * 60000;

        caloories = findViewById(R.id.chrono_burned);
        time = findViewById(R.id.chrono);
        start = findViewById(R.id.chrono_start);
        pause = findViewById(R.id.chrono_pause);
        name = findViewById(R.id.chrono_name_exo);

        name .setText(exercicePhysique.getTitle());

        s=findViewById(R.id.starttext);
        p= findViewById(R.id.pausetext);
        mTimeLeftInMillis = (int)x;
        mTimerRunning = false;

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mTimerRunning){
                    s.setText("Stop");

                    mTimeLeftInMillis = (int)x;
                    startTimer();
                }else{
                    s.setText("Start");
                    resetTimer();
                } }});

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTimerRunning){
                    p.setText("Resume");
                    pauseTimer();
                }else{
                    p.setText("Pause");
                    startTimer();
                }
            }
        });
        updateCountDownText();


        text_etaps = findViewById(R.id.exo_etapes);
        methode_steps();
        text_etaps.setVisibility(View.GONE);

        show = findViewById(R.id.show_etaps);
        show.setText("show steps");
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visible();
            }
        });
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;

    }

    private void startTimer() {
        final String first,last;
        first = "Your Burned ";
        last = " Calories";

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                caloories.setText(first +String.valueOf((int)HowMuchCaloriesBurnExo(exercicePhysique,(x-millisUntilFinished)/60000))+last);
                updateCountDownText();
            }
            @Override
            public void onFinish() {

                mTimerRunning = false;
                activityPhUser1.setPoids(Integer.parseInt(user.getPoids()));
                activityPhUser1.setCalories_burned((int)HowMuchCaloriesBurnExo(exercicePhysique,x/60000));
                activityPhUser1.setSexe(user.getSexe());
                activityPhUser1.setId_user(user.getIdUser());
                activityPhUser1.setDuree((int)x/60000);
                activityPhUser1.setExercicePhysique(exercicePhysique);
                addatabase(activityPhUser1);

                update_progress_physique(activityPhUser1.getCalories_burned());

                //make changes at  user progress
                //ActivityphysicFragment.progressBar.setProgress(ActivityphysicFragment.progressBar.getProgress()+activityPhUser1.getCalories_burned());
                ActivityphysicFragment.whatburned.setText(String.valueOf(Integer.parseInt(ActivityphysicFragment.whatburned.getText().toString())+activityPhUser1.getCalories_burned()));
                add_database_progress();
                startActivity(new Intent(MainChrono.this, AfterExo.class));
                finish();
            }
        }.start();
        mTimerRunning = true;
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        time.setText(timeLeftFormatted);
    }

    private void resetTimer() {
        mTimeLeftInMillis = 0;
        mCountDownTimer.cancel();
        updateCountDownText();
        mTimerRunning=false;
    }

    //DATA
    private  void addatabase (ActivityPhUser a){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("UserActivitys").child(user.getIdUser()).child(today).child("Physique").child(String.valueOf(System.currentTimeMillis())+a.getExercicePhysique().getTitle());
        mDatabase.setValue(new PhysicalActivity(a.getExercicePhysique().getTitle(),a.getDuree(),a.getCalories_burned()));
    }

    private void add_database_progress() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("UserActivitys").child(user.getIdUser()).child(today).child("Progressions").child("Physique");
        mDatabase.setValue( share.getInt(PROGRESS_PHYSIQUE,0));
    }

    private void update_progress_physique(int progress) {

        //edit share
        SharedPreferences.Editor editor = share.edit();
        editor.putInt(PROGRESS_PHYSIQUE,  (share.getInt(PROGRESS_PHYSIQUE,0) + progress));
        editor.apply();

        ActivityphysicFragment.progressBar_physique.setProgress(share.getInt(PROGRESS_PHYSIQUE,0));
        //progress.setprogress(min + sec/60)
    }

    //CALCUL
    public double HowMuchCaloriesBurnExo(ExercicePhysique exercicePhysique, Double timeInMinute){
        double MET = exercicePhysique.getMET();
        double poids = MainMyMenu.poids;

        double KcalPerMin = (MET * 3.5 * poids)/200;
        double KcalTotal = KcalPerMin*timeInMinute;

        return KcalTotal;
    }

    //VUE
    //__hide/show text steps
    private void visible () {
        if (text_etaps.getVisibility() == View.VISIBLE){
            text_etaps.setVisibility(View.GONE);
            show.setText("show steps");
            Toast.makeText(this, "gone", Toast.LENGTH_LONG).show();
        }
        else {
            text_etaps.setVisibility(View.VISIBLE);
            show.setText("Hide steps");
            Toast.makeText(this, "visible", Toast.LENGTH_LONG).show();
        }
    }

    void methode_steps () {
        text_etaps.setText("");
        for(int i = 0; i< exercicePhysique.getEtapes().size(); i++){
            text_etaps.setText(text_etaps.getText().toString()+"\n"+String.valueOf(i+1)+". "+ exercicePhysique.getEtapes().get(i)+"\n");
        }
    }
}

