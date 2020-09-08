package com.example.healtyapp.ui.activitymorale;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healtyapp.R;
import com.example.healtyapp.database_item.CognitieActivite;
import com.example.healtyapp.module.ExerciceCognitive;
import com.example.healtyapp.module.User;
import com.example.healtyapp.vue.center_activities.MainActivity;
import com.example.healtyapp.vue.center_activities.MainMyMenu;
import com.example.healtyapp.vue.sous_activities.AfterExo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Locale;

public class MainActivityM extends AppCompatActivity {

    //etaps
    public static ExerciceCognitive exerciceCognitive;
    private TextView TextViewExtEtape;
    private TextView titre;
    Button show;

    //chrono
    LinearLayout start,pause,back;
    TextView time,s,p,name;
    CountDownTimer mCountDownTimer;
    private long mTimeLeftInMillis;
    boolean mTimerRunning;
    double x;

    //manipulate progress
    SharedPreferences share;
    final String SHARE = MainMyMenu.Share;
    private static final String PROGRESS = MainMyMenu.PROGRESS_COGNITIVE;
    private static final String PROGRESS_TIME = MainMyMenu.PROGRESS_COGNITIVE_TIME;

    //private final String userid = getSharedPreferences(MainActivity.MyUser, MODE_PRIVATE).getString("UserId",MainMyMenu.user.getIdUser());

    //database data
    DatabaseReference path;

    User user;

    //today
    Calendar calSelected = Calendar.getInstance();
    String today = "" + calSelected.get(Calendar.DAY_OF_MONTH)
            + (calSelected.get(Calendar.MONTH) + 1)
            + calSelected.get(Calendar.YEAR);

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_m);
        setTitle("Cognitive Exercise");

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        share = getSharedPreferences(SHARE,MODE_PRIVATE);

        TextViewExtEtape = findViewById(R.id.exo_etapes);
        titre = findViewById(R.id.titre_exo);
        titre.setText(exerciceCognitive.getTitle());

        //order the steps in the textView
        methode_steps();

        ViewGroup.LayoutParams layoutParams = TextViewExtEtape.getLayoutParams();
        layoutParams.height = methode((exerciceCognitive.getEtapes().size()*60)+5,this); //this is in pixels
        TextViewExtEtape.setLayoutParams(layoutParams);

        user = MainMyMenu.user;
        x = exerciceCognitive.getDuree()* 60000;//getIntent().getDoubleExtra("Time", 0) * 60000;
        time = findViewById(R.id.chrono);
        start = findViewById(R.id.chrono_start);
        pause = findViewById(R.id.chrono_pause);
        name = findViewById(R.id.chrono_name_exo);


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
                }
            }
        });

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

        show = findViewById(R.id.show_etaps2);
        show.setText("show steps");
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visible(TextViewExtEtape);
            }
        });
    }

    //VUE
    void methode_steps () {
        TextViewExtEtape.setText("");
        for(int i =0;i<exerciceCognitive.getEtapes().size();i++){
            TextViewExtEtape.setText(TextViewExtEtape.getText().toString()+"\n"+String.valueOf(i+1)+". "+exerciceCognitive.getEtapes().get(i)+"\n");
        }
    }

    //__convert dp into pixel
    public static int methode(float dp, Context context){
        Resources resources = context.getResources();
        int i = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                resources.getDisplayMetrics()
        );
        return i;
    }

    public int etap_taille () {
        int max = 1,i,imax = 0;
        int x = 18;

        for(i = 0;i<exerciceCognitive.getEtapes().size();i++) {
            if (exerciceCognitive.getEtapes().get(i).length()/ x / 2> max) {
                max = (int) (( exerciceCognitive.getEtapes().get(i).length()) / x /2);
                imax = i;
            }
        }

        System.out.print("\n\n______________________________________SYSTEM____________________________________________________________\n\n"
                +"\nexerciceCognitive.getEtapes().size() = "+exerciceCognitive.getEtapes().size()
                +"\nexerciceCognitive.getEtapes().get("+imax+").length() = "+exerciceCognitive.getEtapes().get(imax).length()
               // +"\ntextSize = "+x
                +"\nmax = "+max
                +"\nmax*370 = "+max*370
                +"\nmax*18 = "+max*x
                +"\nmax*23 = "+max*23);

        if(max > 60)
            return max*x;
        else
            return 60;
    }

    //VUE
    //__hide/show text steps
    private void visible (TextView text_etaps) {
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

    //CHRONO
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
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                mTimerRunning = false;
                //duration par minutes

                exerciceCognitive.setDuree ((int) (x/60000) - (int) (mTimeLeftInMillis/ 60000));
                update_progress_cognitive (((long) x  - mTimeLeftInMillis));
                add_database (exerciceCognitive);
                Toast.makeText(getApplicationContext(), "x = "+x+"| mtime = "+mTimeLeftInMillis, Toast.LENGTH_LONG).show();
                startActivity(new Intent(MainActivityM.this, AfterExo.class));
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
    //__database data
    private void add_database(ExerciceCognitive e) {
        //Toast.makeText(this, "user: "+user.getIdUser()+" today: "+today, Toast.LENGTH_LONG).show();
        path = FirebaseDatabase.getInstance().getReference().child("UserActivitys").child(user.getIdUser()).child(today).child("Cognitive").child(String.valueOf(System.currentTimeMillis()+exerciceCognitive.getTitle()));
        CognitieActivite a = new CognitieActivite(e.getTitle(),e.getDuree());
        path.setValue(a);
    }

    private void add_database_progress() {
        //Toast.makeText(this, "user: "+user.getIdUser()+" today: "+today, Toast.LENGTH_LONG).show();
        path = FirebaseDatabase.getInstance().getReference().child("UserActivitys").child(user.getIdUser()).child(today).child("Progressions").child("Cognitive");
        path.setValue((share.getInt(PROGRESS,0))); //on ajout la duree
    }

    //__share data
    private void update_progress_cognitive(long result_time) {
        //todo progress cognitive : set max = 60min + add min/sec
        //progress.setprogress(min + sec/60)

        int duree = exerciceCognitive.getDuree();
        int minutes = (int) (((share.getInt(PROGRESS, 0) + duree)*60000 )/ 1000) / 60;
        int seconds = (int) (((share.getInt(PROGRESS, 0) + duree)*60000 ) / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        ActivitymoraleFragment.time_progress.setText(timeLeftFormatted);


        //edit share
        SharedPreferences.Editor editor = share.edit();
        editor.putInt(PROGRESS, share.getInt(PROGRESS, 0) + duree);
        editor.putLong(PROGRESS_TIME,result_time);
        editor.apply();
        Toast.makeText(this, "duree tot" + share.getInt(PROGRESS, 0), Toast.LENGTH_LONG).show();
        
        ActivitymoraleFragment.progressBar_cognitive.setProgress(share.getInt(PROGRESS,0));
        add_database_progress();
    }
}