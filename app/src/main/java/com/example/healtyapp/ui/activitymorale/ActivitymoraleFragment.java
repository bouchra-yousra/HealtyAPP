package com.example.healtyapp.ui.activitymorale;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.healtyapp.dialogue.ExampleDialog;
import com.example.healtyapp.dialogue.ExplainPmodoroDialog;
import com.example.healtyapp.module.ExerciceCognitive;
import com.example.healtyapp.adapter.ExoCngAdapter;
import com.example.healtyapp.systeme.MainAddActM;
import com.example.healtyapp.vue.center_activities.MainMyMenu;
import com.example.healtyapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ActivitymoraleFragment extends Fragment {
    public static TextView time_progress;
    public static ProgressBar progressBar_cognitive;

    private ActivitymoraleViewModel activitymoraleViewModel;
    LinearLayout bt,bt_yoga,bt_breath,bt_tips,layout_historique;
    Button button;

    Button yes,no;
    TextView nervous;

    ListView list_rec;
    ExoCngAdapter exoCngAdapter;
    ArrayList<ExerciceCognitive> arrayList = new ArrayList<>();

    //manipulate progress
    private SharedPreferences share;
    private final String SHARE = MainMyMenu.Share;
    private static final String PROGRESS_COGNITIVE = MainMyMenu.PROGRESS_COGNITIVE;
    private static final String PROGRESS_TIME = MainMyMenu.PROGRESS_COGNITIVE_TIME;

    //database data
    DatabaseReference path;

    //today
    Calendar calSelected = Calendar.getInstance();
    String today = "" + calSelected.get(Calendar.DAY_OF_MONTH)
            + (calSelected.get(Calendar.MONTH) + 1)
            + calSelected.get(Calendar.YEAR);


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        activitymoraleViewModel =
                ViewModelProviders.of(this).get(ActivitymoraleViewModel.class);
        final View root = inflater.inflate(R.layout.activity_actmorale, container, false);

        share = getActivity().getSharedPreferences(SHARE,getActivity().MODE_PRIVATE);
        list_rec = root.findViewById(R.id.list_actmrl_rec);
        button = root.findViewById(R.id.click);

       bt = root.findViewById(R.id.bt_tmt);
       bt_yoga = root.findViewById(R.id.bt_yoga);
       bt_breath= root.findViewById(R.id.bt_breath);
       bt_tips= root.findViewById(R.id.bt_tips);
       layout_historique = root.findViewById(R.id.historyph_cgn);

       layout_historique.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(getActivity(), Historique_cognitive.class));
           }
       });

       yes = root.findViewById(R.id.yes_nervous);
       no = root.findViewById(R.id.no_nevous);
       nervous = root.findViewById(R.id.are_you_nervous);
       time_progress = root.findViewById(R.id.act_cognitive_duration);

       progressBar_cognitive = root.findViewById(R.id.progressbar_cognitive);
       progressBar_cognitive.setMax(60);
       update_progress_cognitive ();

        //if (!share.contains(PROGRESS_COGNITIVE)) {
            // getProgressCognitive ();
        //}

       //if (!share.contains(PROGRESS))
           //getHistory_all_Progress_cognitive(progressBar_cognitive);

       animate_progressbar(progressBar_cognitive);
       nervous.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(yes.getVisibility()==View.GONE){
                   nervous.setGravity(Gravity.CENTER);
                   yes.setVisibility(View.VISIBLE);
                   no.setVisibility(View.VISIBLE);
               }else{
                   nervous.setGravity(Gravity.START);
                   yes.setVisibility(View.GONE);
                   no.setVisibility(View.GONE);
               }
           }
       });

       bt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               openDialog ();
               //0startActivity(new Intent(getActivity(), MainTomate.class));
           }
       });

       //edit: button clicks
       bt_yoga.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(getActivity(), MainYoga.class));
           }
       });


       bt_breath.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(getActivity(), MainBreathing.class));
           }
       });
       bt_tips.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(getActivity(), MainRelaxTips.class));
           }
       });

       yes.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(getActivity(), MainReduceStress.class));

           }
       });

       no.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               nervous.setGravity(Gravity.START);
               yes.setVisibility(View.GONE);
               no.setVisibility(View.GONE);

           }
       });
       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(getActivity(), MainAddActM.class));
           }
       });

        generaterec(5);
        exoCngAdapter = new ExoCngAdapter(root.getContext(),R.layout.itemactivitymoral,arrayList);

        //make listeView's height = list of activites size * 60(item height)
        ViewGroup.LayoutParams layoutParams = list_rec.getLayoutParams();
        layoutParams.height = methode((arrayList.size()*60)+5,root.getContext()); //this is in pixels
        list_rec.setLayoutParams(layoutParams);
        exoCngAdapter.notifyDataSetChanged();
        list_rec.setAdapter(null);
        list_rec.setAdapter(exoCngAdapter);
/*
        list_rec.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivityM.exerciceCognitive = (ExerciceCognitive) parent.getItemAtPosition(position);
               // startActivity(new Intent(ActivitymoraleFragment.this,MainActivityM.class));
                //here
            }
        });*/

        //step 2/2
        list_rec.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivityM.exerciceCognitive = (ExerciceCognitive) parent.getItemAtPosition(position);
                startActivity(new Intent(getActivity(),MainActivityM.class));
            }
        });
        return root;
    }

    private void generaterec(int size) {
        if( MainMyMenu.exerciceCognitives.size() < size)
            size = MainMyMenu.exerciceCognitives.size();

        ArrayList<ExerciceCognitive> yoga_list =  new ArrayList<>();
        ArrayList<ExerciceCognitive> relax_list =  new ArrayList<>();
        ArrayList<ExerciceCognitive> breath_list =  new ArrayList<>();
        arrayList = new ArrayList<>();

        for(int i = 0;i<MainMyMenu.exerciceCognitives.size();i++){
            if(MainMyMenu.exerciceCognitives.get(i).getType().equals("Yoga")){
                //here
                yoga_list.add(MainMyMenu.exerciceCognitives.get(i));
            }
        }
;
        //get Only activities with type equal Breathing technique
        for(int i = 0;i<MainMyMenu.exerciceCognitives.size();i++){
            if(MainMyMenu.exerciceCognitives.get(i).getType().equals("Breathing technique")){
                //here
                breath_list.add(MainMyMenu.exerciceCognitives.get(i));
            }
        }

        //get Only activities with type equal Breathing technique
        for(int i = 0;i<MainMyMenu.exerciceCognitives.size();i++){
            if(MainMyMenu.exerciceCognitives.get(i).getType().equals("Relaxing")){
                //here
                relax_list.add(MainMyMenu.exerciceCognitives.get(i));
            }
        }

        size = 3;
        int i;
        for(i = 0;arrayList.size() < size && i < MainMyMenu.exerciceCognitives.size();i++){
            switch (i%3){
                case 0:
                    if(i < yoga_list.size()) {
                        arrayList.add(yoga_list.get(i));
                        //Toast.makeText(this.getContext(), "case 0  "+i, Toast.LENGTH_LONG).show();
                        break;
                    } /*else
                        continue;*/
                case 1:
                    if(i < breath_list.size()){
                        //Toast.makeText(this.getContext(), "case 1  "+i, Toast.LENGTH_LONG).show();
                        arrayList.add(breath_list.get(i));
                        break;
                    } /*else
                        continue;*/
                case 2:
                    if(i < relax_list.size()) {
                        //Toast.makeText(this.getContext(), "case 2  "+i, Toast.LENGTH_LONG).show();
                        arrayList.add(relax_list.get(i));
                        break;
                    } /*else
                        continue;*/
               /* default:
                    if(i < yoga_list.size()){
                        arrayList.add(yoga_list.get(i));
                        break;
                    } else
                        continue;*/
            }
        }

        Toast.makeText(this.getContext(), ""+i, Toast.LENGTH_SHORT).show();
    }

    //convert dp into pixel
    private static int methode(float dp, Context context){
        Resources resources = context.getResources();
        int i = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                resources.getDisplayMetrics()
        );
        return i;
    }

    private ArrayList<ExerciceCognitive> breathing() {
        ArrayList<ExerciceCognitive> arrayList_ = new ArrayList<>();
        //get Only activities with type equal Breathing technique
        for(int i = 0;i<MainMyMenu.exerciceCognitives.size();i++){
            if(MainMyMenu.exerciceCognitives.get(i).getType().equals("Breathing technique")){
                //here
                arrayList_.add(MainMyMenu.exerciceCognitives.get(i));
            }
        }
        return arrayList_;
    }

    private ArrayList<ExerciceCognitive> yoga() {
        ArrayList<ExerciceCognitive> arrayList = new ArrayList<>();
        //get Only activities with type equal Breathing technique
        for(int i = 0;i<MainMyMenu.exerciceCognitives.size();i++){
            if(MainMyMenu.exerciceCognitives.get(i).getType().equals("Yoga")){
                //here
                arrayList.add(MainMyMenu.exerciceCognitives.get(i));
            }
        }
        return arrayList;
    }

    private ArrayList<ExerciceCognitive> relaxing() {
        ArrayList<ExerciceCognitive> arrayList = new ArrayList<>();
        //get Only activities with type equal Breathing technique
        for(int i = 0;i<MainMyMenu.exerciceCognitives.size();i++){
            if(MainMyMenu.exerciceCognitives.get(i).getType().equals("Relaxing")){
                //here
                arrayList.add(MainMyMenu.exerciceCognitives.get(i));
            }
        }
        return arrayList;
    }

    private void update_progress_cognitive() {
        Toast.makeText(getActivity(),"progression1: "+share.getInt(PROGRESS_COGNITIVE,0),Toast.LENGTH_SHORT).show();

        //long result_time = share.getLong(PROGRESS_TIME,0);
        int duree = share.getInt(PROGRESS_COGNITIVE,0);
        long result_time = duree*60000;
        int minutes = (int) (result_time / 1000) / 60;
        int seconds = (int) (result_time / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        ActivitymoraleFragment.time_progress.setText(timeLeftFormatted);
        progressBar_cognitive.setProgress(duree);
        Toast.makeText(getActivity(),"progression2: "+share.getInt(PROGRESS_COGNITIVE,0),Toast.LENGTH_SHORT).show();
    }

    private void update_progress_cognitive_prepar(int i) {
        //long result_time = share.getLong(PROGRESS_TIME,0);

        int duree = i;
        long result_time = duree*60000;
        int minutes = (int) (result_time / 1000) / 60;
        int seconds = (int) (result_time / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        ActivitymoraleFragment.time_progress.setText(timeLeftFormatted);
        progressBar_cognitive.setProgress(duree);

        SharedPreferences.Editor editor = share.edit();
        editor.putInt(PROGRESS_COGNITIVE, share.getInt(PROGRESS_COGNITIVE, 0) + duree);
        editor.putLong(PROGRESS_TIME,result_time);
        editor.apply();
    }

    //DATA
    private void getHistory_all_Progress_cognitive (final ProgressBar pro2){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("UserActivitys").child(MainMyMenu.user.getIdUser()).child(today);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                        int progn = 0;
                        if (dataSnapshot.child("Progressions").exists()) {
                            if (dataSnapshot.child("Progressions").child("Cognitive").exists())
                                progn = dataSnapshot.child("Progressions").child("Cognitive").getValue(Integer.class);
                        }
                        //pro2.setProgress(pro2.getProgress()+progn);
                        update_progress_cognitive_prepar(progn);
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        //
    }

    //DIALOG
    private void openDialog() {
        ExplainPmodoroDialog dialog= new ExplainPmodoroDialog();
        // dialog.setTargetFragment(HomeFragment.this,1);
        dialog.show(requireActivity().getSupportFragmentManager(), "example dialog");
    }

    //ANIMATION
    private void animate_progressbar (final ProgressBar p) {
        final ObjectAnimator objectAnimator = ObjectAnimator.ofInt(p,"progress",0,p.getProgress());
        objectAnimator.setDuration(1500);

        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                p.setVisibility(View.GONE);
            }
        });

        objectAnimator.start();
    }
}
