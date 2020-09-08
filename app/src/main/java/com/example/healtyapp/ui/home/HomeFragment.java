package com.example.healtyapp.ui.home;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.healtyapp.adapter.FiveMoodTrakerAdapter;
import com.example.healtyapp.adapter.FiveWeightTrakerAdapter;
import com.example.healtyapp.adapter.MoodTrakerAdapter;
import com.example.healtyapp.adapter.WeightTrakerAdapter;
import com.example.healtyapp.database_item.Aliment;
import com.example.healtyapp.database_item.AlimentData;
import com.example.healtyapp.database_item.MoodTrakerItem;
import com.example.healtyapp.database_item.WeightTrakerItem;
import com.example.healtyapp.dialogue.ExampleDialog;
import com.example.healtyapp.dialogue.ExplainPmodoroDialog;
import com.example.healtyapp.dialogue.MoodDialog;
import com.example.healtyapp.dialogue.TestInflate;
import com.example.healtyapp.module.ExercicePhysique;
import com.example.healtyapp.module.Sport;
import com.example.healtyapp.ui.activityphysic.ActivityphysicFragment;
import com.example.healtyapp.ui.activityphysic.MainChrono;
import com.example.healtyapp.ui.profile.ProfileFragment;
import com.example.healtyapp.vue.center_activities.MainActivity;
import com.example.healtyapp.vue.center_activities.MainMyMenu;
import com.example.healtyapp.R;
import com.example.healtyapp.module.User;
import com.example.healtyapp.vue.sous_activities.MainForgetpw;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment implements ExampleDialog.ExampleDialogListener {

    private static final String TAG = "HOME_FRAGMENT";
    private static final String PROGRESS_HOME_PHYSIQUE = MainMyMenu.PROGRESS_PHYSIQUE;
    private static final String PROGRESS_HOME_COGNITIVE = MainMyMenu.PROGRESS_COGNITIVE;
    private HomeViewModel homeViewModel;
    public static User user = MainMyMenu.user;

    //progress texts
    TextView text,calconsoumed,
             home_username,
             protine, glucide, lipide,
             center1, center2,
             water_old, water_pro,
             center3, center4, cngpro,phpro;

    LinearLayout pomodoro,rec_home;

    //int maxKca;
    DatabaseReference myDataBase;
    public static ProgressBar progressBar_home,progressBar_home2,progressBar_home3,progressBar_home4,proprotine,proglucide,prolipide;
    public static int max_progress = 0;

    //today's workout
    public static ArrayList<ExercicePhysique> RecList_home;
    public static ArrayList<ExercicePhysique> arrayListhome= new ArrayList<>();
    ArrayList<LinearLayout> linearLayouts = new ArrayList<>();
    ArrayList<TextView> rec_eco_titre = new ArrayList<>();
    ArrayList<TextView> rec_eco_duree= new ArrayList<>();
    ArrayList<TextView> rec_eco_cal= new ArrayList<>();

    //manipulate progress
    SharedPreferences share;
    final String SHARE = MainMyMenu.Share;
    private static final String PROGRESS= MainMyMenu.PROGRESS_NUTRITION;
    private static final String PROGRESS_ALIMENT= "currentKca";

    Button addmood,addweight;

    //pman a
    //weight traker
    ArrayList<WeightTrakerItem> weight_list = new ArrayList<>();
    ArrayList<MoodTrakerItem> mood_list = new ArrayList<>();

    //plan b
    public static ArrayList<MoodTrakerItem[]> five_mood_list = new ArrayList<>();
    public static ArrayList<WeightTrakerItem[]> five_weight_list = new ArrayList<>();

    public static ListView listView, listViewMood;

    //layouts init
    public static LinearLayout lay1,lay2,lay3;
    TextView txtlip,txtpro,txtglu,txtcal,txtwater;


    //today
    Calendar calSelected = Calendar.getInstance();
    String today = "" + calSelected.get(Calendar.DAY_OF_MONTH)
            + (calSelected.get(Calendar.MONTH) + 1)
            + calSelected.get(Calendar.YEAR);


    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.activity_home, container, false);
        Log.d(TAG, "onCreateView: ");
        root.setTag(TAG);

        share = getActivity().getSharedPreferences(SHARE,getActivity().MODE_PRIVATE);

        rec_home = root.findViewById(R.id.layout_rec_home);

        home_username = root.findViewById(R.id.home_username);
        if (user != null && user.getLast_name() != null)
            home_username.setText("Welcome "+String.valueOf(MainMyMenu.user.getLast_name()));

        txtcal = root.findViewById(R.id.txtcal);
        txtpro = root.findViewById(R.id.txtprotine);
        txtlip = root.findViewById(R.id.txtlipide);
        txtglu = root.findViewById(R.id.txtglucide);
        txtwater = root.findViewById(R.id.txtwater);

        txtcal.setText(String.valueOf((int) user.getBesoin_energy())+" Calories");
        txtpro.setText(String.valueOf((int) user.calcul_besoin_protine())+" Cal");
        txtlip.setText(String.valueOf((int) user.calcul_besoin_lipide())+" Cal");
        txtglu.setText(String.valueOf((int) user.calcul_besoin_glucide())+" Cal");
        txtwater.setText(String.valueOf((int) user.getBesoin_eau())+" MiliLitres");

        lay1 = root.findViewById(R.id.layout_home1);
        lay2 = root.findViewById(R.id.layout_init1_home);
        lay2 = root.findViewById(R.id.layout_home2);

        proprotine = root.findViewById(R.id.proprotine);
        proglucide = root.findViewById(R.id.proglucide);
        prolipide = root.findViewById(R.id.prolipide);

        proprotine.setMax((int) MainMyMenu.user.calcul_besoin_protine());
        proglucide.setMax((int) MainMyMenu.user.calcul_besoin_glucide());
        prolipide.setMax((int) MainMyMenu.user.calcul_besoin_lipide());

        proprotine.setProgress(share.getInt("protine",0));
        proglucide.setProgress(share.getInt("glucide",0));
        prolipide.setProgress(share.getInt("lipide",0));

        protine = root.findViewById(R.id.protine);
        protine.setText(String.valueOf((int) user.calcul_besoin_protine())+" Cal");
        glucide = root.findViewById(R.id.glucide);
        glucide.setText(String.valueOf((int) user.calcul_besoin_glucide())+" Cal");
        lipide = root.findViewById(R.id.lipide);
        lipide.setText(String.valueOf((int) user.calcul_besoin_lipide())+" Cal");

        getRecList_home(5);
        listView = root.findViewById(R.id.list_weight_traker);
        listViewMood = root.findViewById(R.id.list_mood_traker);

        text = root.findViewById(R.id.textHome);
        calconsoumed = root.findViewById(R.id.newcl);
        addweight = root.findViewById(R.id.weight);
        addmood = root.findViewById(R.id.mood);
        center1 = root.findViewById(R.id.center_food);
        center2 = root.findViewById(R.id.center_water);
        center3 = root.findViewById(R.id.physique);
        center4 = root.findViewById(R.id.cognitive);
        water_old = root.findViewById(R.id.besoin_eau);
        water_pro = root.findViewById(R.id.consoeau);
        phpro = root.findViewById(R.id.phpro);
        cngpro = root.findViewById(R.id.cngpro);

        //progression
        progressBar_home = root.findViewById(R.id.progressBarNutrition);
        progressBar_home2 = root.findViewById(R.id.progresseau);
        progressBar_home3 = root.findViewById(R.id.progressBar2);
        progressBar_home4 = root.findViewById(R.id.progressBar3);

        progressBar_home.setMax((int) MainMyMenu.kca);
        progressBar_home2.setMax((int) MainMyMenu.bes_eau);
        progressBar_home3.setMax((int) MainMyMenu.bes_eau / 3);
        progressBar_home4.setMax((int) 60);

        if(progressBar_home.getMax() == 0 && user != null)
            progressBar_home.setMax((int)user.getBesoin_energy());

        progressBar_home.setProgress(share.getInt(PROGRESS_ALIMENT,0));
        progressBar_home2.setProgress(share.getInt("currentWater",0));
        progressBar_home3.setProgress(share.getInt(PROGRESS_HOME_PHYSIQUE,0));
        progressBar_home4.setProgress(share.getInt(PROGRESS_HOME_COGNITIVE,0));

        //gererlayout (lay1,lay2);
        /*
         if (progressBar_home.getProgress() == 0) {
             lay2.setVisibility(View.VISIBLE);
             lay1.setVisibility(View.GONE);
         } else {
             lay1.setVisibility(View.VISIBLE);
             lay2.setVisibility(View.GONE);
         }

         if (progressBar_home3.getProgress() == 0 && progressBar_home4.getProgress() == 0)
             lay3.setVisibility(View.GONE);
         else
             lay3.setVisibility(View.VISIBLE);*/

        calconsoumed.setText(String.valueOf(progressBar_home.getProgress())+" ");
        text.setText(String.valueOf(progressBar_home.getMax())+" Calories");
        water_old.setText(String.valueOf(progressBar_home.getMax())+" MiliLitres");
        water_pro.setText(String.valueOf(share.getInt("currentWater",0))+" ");
        phpro.setText(String.valueOf(share.getInt(PROGRESS_HOME_PHYSIQUE,0))+" Calories");
        cngpro.setText(String.valueOf(share.getInt(PROGRESS_HOME_COGNITIVE,0))+" Minutes");

        center1.setText(String.valueOf((share.getInt(PROGRESS_ALIMENT,0) * 100 )/ progressBar_home.getMax())+"%");
        center2.setText(String.valueOf((share.getInt("currentWater",0) * 100 )/ progressBar_home2.getMax())+"%");
        center3.setText(String.valueOf((share.getInt(PROGRESS_HOME_PHYSIQUE,0) * 100 )/ progressBar_home3.getMax())+"%");
        center4.setText(String.valueOf((share.getInt(PROGRESS_HOME_COGNITIVE,0) * 100 )/ progressBar_home4.getMax())+"%");

        //animation
        animate_progressbar_(progressBar_home,progressBar_home.getProgress());
        animate_progressbar_(progressBar_home2,progressBar_home2.getProgress());
        animate_progressbar_(progressBar_home3,progressBar_home3.getProgress());
        animate_progressbar_(progressBar_home4,progressBar_home4.getProgress());
        animate_progressbar_(proprotine,proprotine.getProgress());
        animate_progressbar_(proglucide,proglucide.getProgress());
        animate_progressbar_(prolipide,prolipide.getProgress());

        pomodoro = root.findViewById(R.id.pomodoro);
        pomodoro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPomodoroDialog();
            }
        });

        //getHistory_nutpro(progressBar_home,calconsoumed,proprotine,prolipide,proglucide);

        //weight traker
        five_mood_list = new ArrayList<>();
        /*
        weight_list.add(new WeightTrakerItem(55));
        weight_list.add(new WeightTrakerItem(54));
        weight_list.add(new WeightTrakerItem(56));
        weight_list.add(new WeightTrakerItem(57));

        five_weight_list.add(set_five_weight_traker(
                new WeightTrakerItem(55),
                new WeightTrakerItem(56),
                new WeightTrakerItem(55),
                null
        ));
*/

        update_five_weight_list();
        /* five_weight_list = new ArrayList<>();
        five_weight_list.add(set_five_weight_traker(
                new WeightTrakerItem(55),
                new WeightTrakerItem(56),
                new WeightTrakerItem(55),
                null
        ));*/
        addweight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
                update_five_weight_list();
            }
        });
        //update_weight_list();

        //mood traker
        five_mood_list = new ArrayList<>();

        addmood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(),"moooooooood",Toast.LENGTH_SHORT).show();
                openDialogMood();
            }
        });
        mood_list.add(new MoodTrakerItem(0));
        mood_list.add(new MoodTrakerItem(1));
        mood_list.add(new MoodTrakerItem(2));
        mood_list.add(new MoodTrakerItem(3));

        five_mood_list.add(set_five_mood_traker(
                new MoodTrakerItem(0),
                new MoodTrakerItem(1),
                new MoodTrakerItem(2),
                null
                ));

        update_five_mood_list();
        //update_mood_list();

        //TODAY'S WORKOUT
        //test
        int size = 4;
        if (MainMyMenu.exercicePhysiqueArrayList.size() <size)
            size = MainMyMenu.exercicePhysiqueArrayList.size();
        for(int i = 0; i<MainMyMenu.exercicePhysiqueArrayList.size(); i++){
            for(int j=0;j<MainMyMenu.user.getObjectifs().size();j++){
                if(MainMyMenu.exercicePhysiqueArrayList.get(i).getObjectif().equals(MainMyMenu.user.getObjectifs().get(j))){
                    arrayListhome.add(MainMyMenu.exercicePhysiqueArrayList.get(i));
                }
            }
        }

        linearLayouts.add((LinearLayout) root.findViewById(R.id.layout_home_exo1));
        linearLayouts.add((LinearLayout) root.findViewById(R.id.layout_home_exo2));
        linearLayouts.add((LinearLayout) root.findViewById(R.id.layout_home_exo3));
        linearLayouts.add((LinearLayout) root.findViewById(R.id.layout_home_exo4));

        rec_eco_titre.add((TextView) root.findViewById(R.id.rec_exo1));
        rec_eco_titre.add((TextView) root.findViewById(R.id.rec_exo2));
        rec_eco_titre.add((TextView) root.findViewById(R.id.rec_exo3));
        rec_eco_titre.add((TextView) root.findViewById(R.id.rec_exo3));

        rec_eco_cal.add((TextView) root.findViewById(R.id.rec_exo1_kcl));
        rec_eco_cal.add((TextView) root.findViewById(R.id.rec_exo2_kcl));
        rec_eco_cal.add((TextView) root.findViewById(R.id.rec_exo3_kcl));
        rec_eco_cal.add((TextView) root.findViewById(R.id.rec_exo4_kcl));

        rec_eco_duree.add((TextView) root.findViewById(R.id.rec_exo1_time));
        rec_eco_duree.add((TextView) root.findViewById(R.id.rec_exo2_time));
        rec_eco_duree.add((TextView) root.findViewById(R.id.rec_exo3_time));
        rec_eco_duree.add((TextView) root.findViewById(R.id.rec_exo4_time));

        displayRecExo(linearLayouts.size());
        //gererlayout(lay1,lay2);
        //getRecList_home(5);
        //displayRecExo(linearLayouts.size());

        return root;
    }

    private void gererlayout(LinearLayout lay1, LinearLayout lay2) {
        if (share.getInt(PROGRESS_ALIMENT,0) == 0) {
            lay2.setVisibility(View.VISIBLE);
            lay1.setVisibility(View.GONE);
        } else {
            lay1.setVisibility(View.VISIBLE);
            lay2.setVisibility(View.GONE);
        }
    }

    void update_weight_list () {
        listView.setAdapter(null);
        listView.setHorizontalScrollBarEnabled(true);
        listView.canScrollHorizontally(50);
        WeightTrakerAdapter adapter = new WeightTrakerAdapter(getActivity().getApplicationContext(),R.layout.item_weight_traker,weight_list);
        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
        layoutParams.height = methode((weight_list.size()*100)+5,getActivity()); //this is in pixels
        listView.setLayoutParams(layoutParams);
        adapter.notifyDataSetChanged();
        listView.setAdapter(null);

        listView.setAdapter(adapter);
    }

    void update_five_weight_list () {
        listView.setAdapter(null);
        listView.setHorizontalScrollBarEnabled(true);
        listView.canScrollHorizontally(50);
        FiveWeightTrakerAdapter adapter = new FiveWeightTrakerAdapter(getActivity().getApplicationContext(),R.layout.item_five_weight_traker,five_weight_list);
        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
        layoutParams.height = methode((five_weight_list.size()*100)+5,getActivity()); //this is in pixels
        listView.setLayoutParams(layoutParams);
        adapter.notifyDataSetChanged();
        listView.setAdapter(null);

        listView.setAdapter(adapter);
    }

    //VUE
    void update_mood_list () {
        listViewMood.setAdapter(null);

        MoodTrakerAdapter adapter = new MoodTrakerAdapter(getActivity().getApplicationContext(),R.layout.item_mood_traker,mood_list);
        ViewGroup.LayoutParams layoutParams = listViewMood.getLayoutParams();
        layoutParams.height = methode((mood_list.size()*100)+5,getActivity()); //this is in pixels
        listViewMood.setLayoutParams(layoutParams);
        adapter.notifyDataSetChanged();
        listViewMood.setAdapter(null);

        listViewMood.setAdapter(adapter);
    }

    //convert dp into pixel
    public static int methode(float dp, Context context){
        Resources resources = context.getResources();
        int i = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                resources.getDisplayMetrics()
        );
        return i;
    }

    void update_five_mood_list () {
        listViewMood.setAdapter(null);


        FiveMoodTrakerAdapter adapter = new FiveMoodTrakerAdapter(getActivity().getApplicationContext(),R.layout.item_five_mood_traker,five_mood_list);
        ViewGroup.LayoutParams layoutParams = listViewMood.getLayoutParams();
        layoutParams.height = methode((five_mood_list.size()*100)+5,getActivity()); //this is in pixels
        listViewMood.setLayoutParams(layoutParams);
        adapter.notifyDataSetChanged();
        listViewMood.setAdapter(null);

        listViewMood.setAdapter(adapter);
    }

    //SET LISTES METHODES
    public MoodTrakerItem[] set_five_mood_traker (MoodTrakerItem a,MoodTrakerItem b,MoodTrakerItem c,MoodTrakerItem d){
        MoodTrakerItem[] list = new MoodTrakerItem[4];
        list[0] = a;
        list[1] = b;
        list[2] = c;
        list[3] = d;

        return list;
    }

    public WeightTrakerItem[] set_five_weight_traker (WeightTrakerItem a,WeightTrakerItem b,WeightTrakerItem c,WeightTrakerItem d){
        WeightTrakerItem[] list = new WeightTrakerItem[4];
        list[0] = a;
        list[1] = b;
        list[2] = c;
        list[3] = d;

        return list;
    }

    public static boolean addWeightInTab(WeightTrakerItem w, WeightTrakerItem[] tab) {
        for (int i = 0;i < tab.length; i++) {
            if(tab[i] == null) {
                tab[i] = w;
                return true;
            }
        }
        return false;
    }

    public static void addWeightInlist (WeightTrakerItem w,ArrayList<WeightTrakerItem[]> tab) {
        //parcourir le tab
        for (int i = 0;i < tab.size(); i++) {

            //si on a un tab
            if (tab.get(i) != null){
                //si on a put ajouter dans le tab c bn
                if(addWeightInTab(w,tab.get(i)))
                    return;
                //else on continue tanque on a des element
            }

            //si non on cree un nouveau tab
            else {
                WeightTrakerItem[] weighttab = new WeightTrakerItem[4];
                weighttab [0] = w;
                weighttab [1] = null;
                weighttab [2] = null;
                weighttab [3] = null;
                tab.add(weighttab);
                return;
            }

            HomeFragment.five_weight_list = tab;
        }
    }

    //DIALOG
    public void openDialog() {
        ExampleDialog dialog = new ExampleDialog();
       // dialog.setTargetFragment(HomeFragment.this,1);
        dialog.show(requireActivity().getSupportFragmentManager(), "example dialog");
    }

    private void openPomodoroDialog() {
        ExplainPmodoroDialog dialog= new ExplainPmodoroDialog();
        // dialog.setTargetFragment(HomeFragment.this,1);
        dialog.show(requireActivity().getSupportFragmentManager(), "example dialog");
    }

    public void openDialogMood() {
        MoodDialog dialog = new MoodDialog();
        // dialog.setTargetFragment(HomeFragment.this,1);
        dialog.show(requireActivity().getSupportFragmentManager(), "example dialog");
    }

    @Override
    public void onYesClicked() {
        Toast.makeText(getActivity(),"moooooooood",Toast.LENGTH_SHORT).show();
    }


    //user null
    void findUser () {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MainActivity.MyUser,getActivity().MODE_PRIVATE);
        final String id = sharedPreferences.getString("UserId","");

        if (id.equals("")) {
            Toast.makeText(getActivity(),"Systeme didn't find user",Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }

        DatabaseReference myDataBase = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getIdUser());
        myDataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user1 = new User();
                if (dataSnapshot.exists())
                    user1 = dataSnapshot.getValue(User.class);
                user = user1;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void getRecList_home (int size){
        if (RecList_home == null)
            RecList_home = MainMyMenu.exercicePhysiqueArrayList;

        //getlist ();

        if (RecList_home.size() <size)
            size = RecList_home.size();
        for(int i = 0; i<RecList_home.size(); i++){
            for(int j=0;j<MainMyMenu.user.getObjectifs().size();j++){
                if(RecList_home.get(i).getObjectif().equals(MainMyMenu.user.getObjectifs().get(j))){
                    arrayListhome.add(RecList_home.get(i));
                }
            }
        }
    }

    private void getlist() {
        DatabaseReference myDatabase = FirebaseDatabase.getInstance().getReference().child("ActivityPh");
        RecList_home = new ArrayList<>();
        myDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ExercicePhysique exercicePhysique = snapshot.getValue(ExercicePhysique.class);

                    //Add ActPh in static arraylist
                    RecList_home.add(exercicePhysique);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public static void getRecList_home_static (int size){
        if (RecList_home == null)
            RecList_home = MainMyMenu.exercicePhysiqueArrayList;
        if (RecList_home.size() <size)
            size = RecList_home.size();
        for(int i = 0; i<RecList_home.size(); i++){
            for(int j=0;j<MainMyMenu.user.getObjectifs().size();j++){
                if(RecList_home.get(i).getObjectif().equals(MainMyMenu.user.getObjectifs().get(j))){
                    arrayListhome.add(RecList_home.get(i));
                }
            }
        }
    }

    void displayRecExo (int size) {
        getRecList_home(size);
        for (int i = 0; i < size ; i++) {
            if (arrayListhome != null && i < arrayListhome.size()){
                if (arrayListhome.get(i) != null ) {
                    rec_eco_titre.get(i).setText(arrayListhome.get(i).getTitle());
                    rec_eco_cal.get(i).setText(String.valueOf((int) HowMuchCaloriesBurnExo(arrayListhome.get(i), (double) CalculTimeExoRec (arrayListhome.get(i)))) + " cal");
                    rec_eco_duree.get(i).setText(String.valueOf((double) CalculTimeExoRec (arrayListhome.get(i)))+" min");
                    final int finalI = i;
                    linearLayouts.get(i).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // TestInflate testInflate = new TestInflate();
                            // TestInflate.exercicePhysique = (ExercicePhysique) arrayList.get(finalI);
                            // testInflate.show(getActivity().getSupportFragmentManager(), "Test dialog");
                            MainChrono.exercicePhysique = arrayListhome.get(finalI);
                            Intent intent = new Intent(getActivity(),MainChrono.class);
                            intent.putExtra("Time",(double) CalculTimeExoRec (arrayListhome.get(finalI)));
                            startActivity(intent);
                        }
                    });

                    if (rec_eco_titre.get(i).getText().toString().equals("Jamping jaks")){
                        linearLayouts.get(i).setVisibility(View.VISIBLE);
                        rec_home.setVisibility(View.VISIBLE);
                    } else {
                        linearLayouts.get(i).setVisibility(View.GONE);
                        rec_home.setVisibility(View.GONE);
                    }
                }else   {
                    linearLayouts.get(i).setVisibility(View.GONE);
                    rec_home.setVisibility(View.GONE);
                }

            }
            else{
                rec_home.setVisibility(View.GONE);
                linearLayouts.get(i).setVisibility(View.GONE);}
        }
    }

    public void displayRecExo2 (int size) {
        if (RecList_home == null)
            RecList_home = MainMyMenu.exercicePhysiqueArrayList;
        if (RecList_home.size() <size)
            size = RecList_home.size();
        for(int i = 0; i<RecList_home.size(); i++){
            for(int j=0;j<MainMyMenu.user.getObjectifs().size();j++){
                if(RecList_home.get(i).getObjectif().equals(MainMyMenu.user.getObjectifs().get(j))){
                    arrayListhome.add(RecList_home.get(i));
                }
            }
        };

        for (int i = 0; i < size ; i++) {
            if (arrayListhome != null && i < arrayListhome.size()){ ;
                rec_eco_titre.get(i).setText(arrayListhome.get(i).getTitle());
                rec_eco_cal.get(i).setText(String.valueOf((int) HowMuchCaloriesBurnExo(arrayListhome.get(i), (double) CalculTimeExoRec (arrayListhome.get(i)))) + " cal");
                rec_eco_duree.get(i).setText(String.valueOf((double) CalculTimeExoRec (arrayListhome.get(i)))+" min");
                final int finalI = i;
                linearLayouts.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // TestInflate testInflate = new TestInflate();
                        // TestInflate.exercicePhysique = (ExercicePhysique) arrayList.get(finalI);
                        // testInflate.show(getActivity().getSupportFragmentManager(), "Test dialog");
                        MainChrono.exercicePhysique = arrayListhome.get(finalI);
                        Intent intent = new Intent(getActivity(),MainChrono.class);
                        intent.putExtra("Time",(double) CalculTimeExoRec (arrayListhome.get(finalI)));
                        startActivity(intent);
                    }
                });
            }
            else
                linearLayouts.get(i).setVisibility(View.GONE);
        }
    }

    private int CalculTimeExoRec(ExercicePhysique exercicePhysique) {
        int time = 1;

        //selon level de diffeculter + objectif
        switch (Integer.parseInt(exercicePhysique.getLevel())){
            case 1:
                 if (objExist(Sport.titre))
                     time = 10;
                 else
                     time = 5;
                break;
            case 2:
                if (objExist(Sport.titre))
                    time = 15;
                else
                    time = 7;
                break;
            case 3:
                if (objExist(Sport.titre))
                    time = 20;
                else
                    time = 10;
                break;
        }
        return time;
    }

    public double HowMuchCaloriesBurnExo(ExercicePhysique exercicePhysique, Double timeInMinute){

        /*Consommation en Kcal par minute = (MET*3,5*Poids en kilos)/200
Ce qui donne pour une personne de 60 kilos faisant de la marche calme pendant 30 minutes :
Consommation en Kcal par minute = (3*3,5*60)/200 = 3,15 Kcal/mn
Donc pour 30 minutes = 3,15*30 = 94 kcal pour 30 minutes*/

        double MET = exercicePhysique.getMET();
        double poids = MainMyMenu.poids;

        double KcalPerMin = (MET * 3.5 * poids)/200;
        double KcalTotal = KcalPerMin*timeInMinute;

        return KcalTotal;
    }

    boolean objExist (String titre) {
        for (int i = 0; i < MainMyMenu.user.getObjectifs().size(); i++) {
            if (MainMyMenu.user.getObjectifs().get(i).equals(titre)) {
             //   Toast.makeText(getActivity(),titre+" true",Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        //Toast.makeText(getActivity(),titre+" false",Toast.LENGTH_SHORT).show();
        return  false;
    }

    //DATA
    public void getHistory_all_Progress_parobj (final ProgressBar pro1){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("UserActivitys").child(MainMyMenu.user.getIdUser());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        int pronut = 0,prowt = 0;

                        if (snapshot.child("Progressions").exists()) {
                            if (snapshot.child("Progressions").child("Nutrition").exists())
                            pronut = snapshot.child("Progressions").child("Nutrition").getValue(Integer.class);

                            if (snapshot.child("Progressions").child("Water").exists())
                                prowt = snapshot.child("Progressions").child("Water").getValue(Integer.class);
                        }
                        pro1.setProgress(pro1.getProgress()+pronut);
                        //pro2.setProgress(pro2.getProgress()+prowt);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        //
    }

    //DATA
    private void getHistory_nutpro(final ProgressBar pro1, final TextView text_center, final ProgressBar protine, final ProgressBar lipide, final ProgressBar glucide){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("UserActivitys").child(requireActivity().getSharedPreferences(MainActivity.MyUser, MODE_PRIVATE).getString("UserId", MainMyMenu.user.getIdUser())).child(today).child("Nutrition");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int resultat_progression = 0;
                int p = 0,l = 0,g = 0;
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    int quantite = 0;

                    if (snapshot.exists()) {
                        if (snapshot.child("nom").exists()) {
                            String nom = snapshot.child("nom").getValue(String.class);
                            if (snapshot.child("quantite").exists())
                                quantite = (int) snapshot.child("quantite").getValue(Integer.class);
                            AlimentData a = new AlimentData (nom,quantite);
                            if (getAliment(a) != null){
                                resultat_progression += convertQttoClAliment(quantite, getAliment(a));
                                p += (int) convertQttoClAliment(quantite,getAliment(a).getCalorie().getProtine());
                                g += (int) convertQttoClAliment(quantite,getAliment(a).getCalorie().getGlicide());
                                l += (int) convertQttoClAliment(quantite,getAliment(a).getCalorie().getLipide());
                                animate_progressbar_(pro1,pro1.getProgress());
                                animate_progressbar_(protine,protine.getProgress());
                                animate_progressbar_(glucide,glucide.getProgress());
                                animate_progressbar_(lipide,lipide.getProgress());
                            }
                        }
                    }
                }
                pro1.setProgress(resultat_progression);
                text_center.setText(String.valueOf((resultat_progression)));
                protine.setProgress(p);
                lipide.setProgress(l);
                glucide.setProgress(g);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public Aliment getAliment (AlimentData a) {
        ArrayList <Aliment> arrayList = MainMyMenu.arrayList;
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i) != null && arrayList.get(i).getNom().trim().equals(a.getNom()))
                return arrayList.get(i);
        }
        return null;
    }

    public double convertQttoClAliment(int quantite,Aliment aliment){
        return (double)(quantite*aliment.getCalorie().getTotal())/100;
    }

    public double convertQttoClAliment(int quantite,Double kcal){
        return (double)(quantite*kcal)/100;
    }

    //ANIMATION
    private void animate_progressbar (final ProgressBar p) {
        final ObjectAnimator objectAnimator = ObjectAnimator.ofInt(p,"progress",0,p.getProgress());
        objectAnimator.setDuration(1500);

        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
               // p.setVisibility(View.GONE);
            }
        });

        objectAnimator.start();
    }

    private void animate_progressbar_ ( ProgressBar p,int max) {
        final ObjectAnimator objectAnimator = ObjectAnimator.ofInt(p,"progress",0,max);
        objectAnimator.setDuration(1500);

        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                //p.setVisibility(View.GONE);
            }
        });
        objectAnimator.start();
    }

}