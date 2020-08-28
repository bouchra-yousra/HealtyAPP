package com.example.healtyapp.ui.activitymorale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.healtyapp.R;
import com.example.healtyapp.module.User;
import com.example.healtyapp.vue.center_activities.MainMyMenu;

import java.util.Locale;

public class MainTomate extends AppCompatActivity {

    LinearLayout start;
    TextView time,s,name;
    CountDownTimer mCountDownTimer;
    private long mTimeLeftInMillis;
    boolean mTimerRunning;
    double x;
    User user;
    CardView cardView;
    LinearLayout linearLayout;
    public static int compt = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tomate);
        user = MainMyMenu.user;
        //0.5 = 30s
        x = 0.01 * 25 * 60000;

        time = findViewById(R.id.chrono);
        start = findViewById(R.id.chrono_start);
        s=findViewById(R.id.starttext);

        name = findViewById(R.id.chrono_name_exo);
        name.setText("Exo");

        cardView = findViewById(R.id.card_break);
        linearLayout = findViewById(R.id.take_break);

        cardView.setVisibility(View.INVISIBLE);
        s.setText("Start");

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

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainTomate.this,MainPause.class));
                compt --;
                s.setText("Start");
            }
        });


    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;

    }

    private void startTimer() {

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                //if (mTimeLeftInMillis == 0)
                    s.setText("Start Again");
                mTimerRunning = false;
                cardView.setVisibility(View.VISIBLE);
            }
        }.start();
        mTimerRunning = true;
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        time.setText(timeLeftFormatted);
        if (mTimeLeftInMillis == 0)
            s.setText("Start Again");
    }

    private void resetTimer() {
        mTimeLeftInMillis = 0;
        mCountDownTimer.cancel();
        updateCountDownText();
        mTimerRunning=false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        cardView.setVisibility(View.INVISIBLE);
    }
}