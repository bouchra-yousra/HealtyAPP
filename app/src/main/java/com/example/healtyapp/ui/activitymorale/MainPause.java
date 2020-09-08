package com.example.healtyapp.ui.activitymorale;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healtyapp.R;
import com.example.healtyapp.dialogue.ExplainPmodoroDialog;
import com.example.healtyapp.module.User;
import com.example.healtyapp.vue.center_activities.MainMyMenu;

import java.util.Locale;

public class MainPause extends AppCompatActivity {

    LinearLayout start;
    TextView time,s,name,break_txt;
    CountDownTimer mCountDownTimer;
    private long mTimeLeftInMillis;
    boolean mTimerRunning;
    double x;
    int compt;
    User user;
    LinearLayout back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pause);
        user = MainMyMenu.user;
        compt = MainTomate.compt;
        break_txt = findViewById(R.id.break_txt);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        name = findViewById(R.id.chrono_name_exo);
        name.setText(MainTomate.name.getText().toString());

        if (name.getText().toString().isEmpty())
            name.setText(ExplainPmodoroDialog.task_title);

        //x =0.25 * 60000;
        if(compt == 0){
            //big break
            x =0.01 * 15 * 60000;
            break_txt.setText("Big Break");
            MainTomate.compt = 4;
        }
        else{
            //small break
            x = 0.01 * 5 * 60000;
            break_txt.setText("Small Break");
        }


        time = findViewById(R.id.chrono);
        start = findViewById(R.id.chrono_start);

        name = findViewById(R.id.chrono_name_exo);

        s=findViewById(R.id.starttext);

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
                }
            }
        });
        updateCountDownText();
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
    }

    private void startTimer() {
        final String first,last;
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                mTimerRunning = false;
                //startActivity(new Intent(MainTomate.this,MainPause.class));
                //Toast.makeText(this, " ", Toast.LENGTH_SHORT).show();
                finish();
            }
        }.start();
        //Toast.makeText(this, "x = "+mCountDownTimer, Toast.LENGTH_SHORT).show();
        mTimerRunning = true;
    }

    private void updateCountDownText() {
       // Toast.makeText(this, "update"+mTimeLeftInMillis, Toast.LENGTH_SHORT).show();
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        time.setText(timeLeftFormatted);
    }

    private void resetTimer() {
        //Toast.makeText(this, "reset"+x, Toast.LENGTH_SHORT).show();
        mTimeLeftInMillis = 0;
        mCountDownTimer.cancel();
        updateCountDownText();
        mTimerRunning=false;

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "back"+x, Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }
}