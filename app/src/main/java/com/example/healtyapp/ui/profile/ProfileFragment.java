package com.example.healtyapp.ui.profile;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.healtyapp.Enumeration.gender;
import com.example.healtyapp.database_item.AlimentData;
import com.example.healtyapp.module.BienEtre;
import com.example.healtyapp.module.ExercicePhysique;
import com.example.healtyapp.module.PreventionBurnOut;
import com.example.healtyapp.module.Sport;
import com.example.healtyapp.ui.activitymorale.ActivitymoraleFragment;
import com.example.healtyapp.ui.activityphysic.ActivityphysicFragment;
import com.example.healtyapp.ui.home.HomeFragment;
import com.example.healtyapp.ui.nutrition.NutritionFragment;
import com.example.healtyapp.vue.center_activities.MainActivity;
import com.example.healtyapp.vue.center_activities.MainMyMenu;
import com.example.healtyapp.adapter.ObjectifAdapter;
import com.example.healtyapp.adapter_item.Objectifitem;
import com.example.healtyapp.R;
import com.example.healtyapp.module.User;
import com.example.healtyapp.vue.sous_activities.SelectObjective;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class ProfileFragment extends Fragment {

//    private FragmentAListener listener;
    private static final String  TAG = "Profil";
    private ProfileViewModel profileViewModel;
    public static User user = new User();
    private TextView txt_poids, txt_taille, txt_bday, txt_fname, txt_lname, txt_sexe,progress_txt;

    private Objectifitem obj;
    private ObjectifAdapter adapt_obj;
    private ListView objectif;
    private ArrayList<Objectifitem> arrayList = new ArrayList<>();
    private ViewGroup.LayoutParams layoutParams;
    private boolean obj1,obj2,obj0;

    private Button logout;
    private LinearLayout l1,l2,l3,settings,gotocalendar,set_obj;
    LinearLayout layout_objectif; //element par element
    LinearLayout getLayout_objectif_item;

    LinearLayout ishomme,isfemmme;

    public static int one_all = 0; //changes when the Objectif item;changes

    //manipulate progress
    SharedPreferences share,share2;
    final String SHARE = MainMyMenu.Share;
    final String SHARE2 = MainActivity.MyUser;
    static final String PROGRESS= MainMyMenu.PROGRESS_COGNITIVE;

    int taux1 = 0,taux2 = 0,taux3 = 0;

    //data
    private FirebaseAuth myAuth;
    DatabaseReference databaseReference;

    //week progress
    TextView t1,t2,t3,t4,t5,t6,t7;
    ProgressBar p1,p2,p3,p4,p5,p6,p7;

    //today
    Calendar calSelected = Calendar.getInstance();
    String today = "" + calSelected.get(Calendar.DAY_OF_MONTH)
            + (calSelected.get(Calendar.MONTH) + 1)
            + calSelected.get(Calendar.YEAR);


    @SuppressLint("ResourceType")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.activity_profile, container, false);
        root.setTag(TAG);

        profileViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

         share = getActivity().getSharedPreferences(SHARE,getActivity().MODE_PRIVATE);
         share2 = getActivity().getSharedPreferences(SHARE2,getActivity().MODE_PRIVATE);

        layout_objectif = root.findViewById (R.id.layout_profil_obj);
        getLayout_objectif_item = root.findViewById(R.layout.item_objectif);

        user = MainMyMenu.user;

        if (user.getFirst_name() == null && user == null)
            startActivity(new Intent(getActivity(), CalendarActivity.class));


        txt_fname = root.findViewById(R.id.first_name);
        txt_fname.setText(user.getFirst_name());
        txt_lname = root.findViewById(R.id.last_name);
        txt_lname.setText(user.getLast_name());
        txt_bday = root.findViewById(R.id.date_naiss);
        txt_bday.setText(user.getAge()+" YO");
        txt_poids = root.findViewById(R.id.poid);
        txt_poids.setText(user.getPoids());
        txt_taille = root.findViewById(R.id.taille);
        txt_taille.setText(user.getTaille());
        txt_sexe = root.findViewById(R.id.sexe);
        txt_sexe.setText(user.getSexe());
        isfemmme = root.findViewById(R.id.isfemme);
        ishomme = root.findViewById(R.id.ishomme);
        gerer_img_gender();

        findUser();

        set_obj = root.findViewById(R.id.set_goal);
        set_obj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectObjective.created = true;
                startActivity(new Intent(getActivity(), SelectObjective.class));
            }
        });


        settings = root.findViewById(R.id.profil_settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CompteSettings.class));
            }
        });

        gotocalendar = root.findViewById(R.id.calendar);
        gotocalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CalendarActivity.class));
            }
        });

        logout = root.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        //generate liste de type objectif item
        put_list_obj();

        //Toast.makeText(getContext(),user.getFirst_name(),Toast.LENGTH_SHORT).show();
        int a,b,c,d,e;

        //ajout des infos dans le layout
        for (int i = 0;i < arrayList.size(); i++){
            switch (i){
                case 0: {
                    a = R.id.obj1;
                    b = R.id.progress_physique1;
                    c = R.id.progress_cognitive1;
                    d = R.id.progress_nutrition1;
                    e = R.id.layoutobj1;
                    obj0 = true;
                    break;
                }
                case 1: {
                    a = R.id.obj2;
                    b = R.id.progress_physique2;
                    e = R.id.layoutobj2;
                    obj1 = true;
                    break;
                }
                case 2: {
                    a = R.id.obj3;
                    b = R.id.progress_physique3;
                    e = R.id.layoutobj3;
                    obj2 = true;
                    break;
                }
                default: {
                    a = R.id.obj1;
                    b = R.id.progress_physique1;
                    e = R.id.layoutobj1;
                }
            }
            TextView textView = root.findViewById(a) ;
            LinearLayout l = root.findViewById(e);
            textView.setText(String.valueOf(i));

            ProgressBar p1 = root.findViewById(b);
            //ProgressBar p2 = root.findViewById(c);
            //ProgressBar p3 = root.findViewById(d);
            display_item(arrayList.get(i),textView,p1,l);

            progress_txt = root.findViewById(R.id.progress_txt);

        }

        l1 = root.findViewById(R.id.layout_profil_obj1);
        l2 = root.findViewById(R.id.layout_profil_obj2);
        l3 = root.findViewById(R.id.layout_profil_obj3);

        click_obj(root.findViewById(R.id.layoutobj1),root.findViewById(R.id.objectifiem1),obj0,l2,l3);
        click_obj(root.findViewById(R.id.layoutobj2),root.findViewById(R.id.objectifiem2),obj1,l1,l3);
        click_obj(root.findViewById(R.id.layoutobj3),root.findViewById(R.id.objectifitem3),obj2,l1,l2);

        p1 = root.findViewById(R.id.p1);
        p2 = root.findViewById(R.id.p2);
        p3 = root.findViewById(R.id.p3);
        p4 = root.findViewById(R.id.p4);
        p5 = root.findViewById(R.id.p5);
        p6 = root.findViewById(R.id.p6);
        p7 = root.findViewById(R.id.p7);

        t1 = root.findViewById(R.id.txt1);
        t2 = root.findViewById(R.id.txt2);
        t3 = root.findViewById(R.id.txt3);
        t4 = root.findViewById(R.id.txt4);
        t5 = root.findViewById(R.id.txt5);
        t6 = root.findViewById(R.id.txt6);
        t7 = root.findViewById(R.id.txt7);
        gerer_affichage_txt_week ();

        return root;
    }

    private void change_progress(ProgressBar a, int i) {
        a.setProgress(i);
    }

    private void gerer_affichage_txt_week() {
        Calendar x = Calendar.getInstance();
        int dayOfWeek = x.get(Calendar.DAY_OF_WEEK);

        String a = "Tus",
                b = "Mon",
                c = "Sun",
                d = "Sat",
                e = "Fri",
                f = "Thu",
                g = "Wed";

        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(a, b, c, d, e, f, g));

        if (Calendar.MONDAY == dayOfWeek)
            arrayList = new ArrayList<>(Arrays.asList   (c, d, e, f, g, a, b));
        else if (Calendar.TUESDAY == dayOfWeek)
            arrayList = new ArrayList<>(Arrays.asList   (b, c, d, e, f, g, a));
        else if (Calendar.WEDNESDAY == dayOfWeek)
            arrayList = new ArrayList<>(Arrays.asList   (a, b, c, d, e, f, g));
        else if (Calendar.THURSDAY == dayOfWeek)
            arrayList = new ArrayList<>(Arrays.asList( g, a, b, c, d, e, f));
        else if (Calendar.FRIDAY == dayOfWeek)
            arrayList = new ArrayList<>(Arrays.asList   (f, g, a, b, c, d, e));
        else if (Calendar.SATURDAY == dayOfWeek)
            arrayList = new ArrayList<>(Arrays.asList   (e, f, g, a, b, c, d));
        else if (Calendar.SUNDAY == dayOfWeek)
            arrayList = new ArrayList<>(Arrays.asList   (d, e, f, g, a, b, c));
       // arrayList = new ArrayList<>(Arrays.asList   ("1","2","3","4","5","6","7"));

        t1.setText(arrayList.get(0));
        t2.setText(arrayList.get(1));
        t3.setText(arrayList.get(2));
        t4.setText(arrayList.get(3));
        t5.setText(arrayList.get(4));
        t6.setText(arrayList.get(5));
        //t7.setText(arrayList.get(6));
        t7.setText("Today");

        int key1 = calSelected.get(Calendar.DAY_OF_MONTH);
        String key2 = "" + (calSelected.get(Calendar.MONTH) + 1)
                + calSelected.get(Calendar.YEAR);

        getHistory_Progress(today,7);
        getHistory_Progress((key1-1) + key2,6);
        getHistory_Progress((key1-2) + key2,5);
        getHistory_Progress((key1-3) + key2,4);
        getHistory_Progress((key1-4) + key2,3);
        getHistory_Progress((key1-5) + key2,2);
        getHistory_Progress((key1-6) + key2,1);

        /*
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        dateFormat.format(cal.getTime());*/
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

    //gender image
    void gerer_img_gender () {
        if (user.getSexe().equals(gender.FEMME.toString())) {
            isfemmme.setVisibility(View.VISIBLE);
            ishomme.setVisibility(View.GONE);
        }else {
            isfemmme.setVisibility(View.GONE);
            ishomme.setVisibility(View.VISIBLE);

        }
    }

    //list objectif
    void put_list_obj(){
        for (int i = 0;i < user.getObjectifs().size(); i++){
            obj = new Objectifitem(user.getObjectifs().get(i),0,0,0);
            arrayList.add(obj);
        }
    }

    void get_hight_layout (){
        if(one_all == 0)
            layoutParams.height = methode((arrayList.size()*70)+5,this.getContext()); //this is in pixels
        else
            layoutParams.height = methode((arrayList.size()*one_all*70)+5,this.getContext()); //this is in pixels
        objectif.setLayoutParams(layoutParams);
    }

    void display_item(Objectifitem object, TextView name, ProgressBar physique, LinearLayout l) {
        name.setText(object.getName());
        l.setVisibility(View.VISIBLE);
        getHistory_Taux_Progress (object.name,physique);

        //getHistory_Taux_Progress_parobj(object.name,physique);
        object.setProgress_physique(physique.getProgress());
        physique.setProgress(object.getProgress_physique());

    }

    void click_obj (View textView, final View layout, Boolean obj,final View layout1,final View layout2) {
        if (obj) {
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (layout.getVisibility() == View.GONE) {
                        layout.setVisibility(View.VISIBLE);
                        layout1.setVisibility(View.GONE);
                        layout2.setVisibility(View.GONE);
                    }

                    else {
                        layout.setVisibility(View.GONE);
                        layout1.setVisibility(View.VISIBLE);
                        layout2.setVisibility(View.VISIBLE);
                    }

                }
            });
        }else
            textView.setVisibility(View.GONE);
    }

    //logout
    void logout () {
        SharedPreferences.Editor edit1 = share.edit();
        SharedPreferences.Editor edit2 = share2.edit();

        edit1.remove(MainMyMenu.PROGRESS_COGNITIVE_TIME);
        edit1.remove(MainMyMenu.PROGRESS_COGNITIVE);
        edit1.remove(MainMyMenu.PROGRESS_WATER);
        edit1.remove(MainMyMenu.PROGRESS_NUTRITION);
        edit1.remove(MainMyMenu.PROGRESS_PHYSIQUE);
        edit1.clear();
        edit1.apply();

        edit2.clear();
        edit2.apply();
        //clean_progress();

        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getActivity(),MainActivity.class));
        getActivity().finish();
    }

    void clean_progress () {

        ActivityphysicFragment.progressBar_physique.setProgress(0);
        ActivitymoraleFragment.progressBar_cognitive.setProgress(0);
        HomeFragment.progressBar_home.setProgress(0);
        NutritionFragment.progressBar.setProgress(0);
        NutritionFragment.progressBareau.setProgress(0);
    }

    //DATA : week progress
    public void getHistory_Progress (String today, final int key){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("UserActivitys").child(MainMyMenu.user.getIdUser()).child(today).child("Progressions");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int proph = 0,progn = 0,pronut = 0,prowt = 0;;

                    if (dataSnapshot.child("Cognitive").exists())
                        progn = dataSnapshot.child("Cognitive").getValue(Integer.class);

                    if (dataSnapshot.child("Nutrition").exists())
                        pronut = dataSnapshot.child("Nutrition").getValue(Integer.class);

                    if (dataSnapshot.child("Physique").exists())
                        proph = dataSnapshot.child("Physique").getValue(Integer.class);

                    if (dataSnapshot.child("Water").exists())
                        prowt = dataSnapshot.child("Water").getValue(Integer.class);


                    /* updateProgress (key, calcul_progress(convertCalorieToProgressPhysique(proph),
                            convertDurationToProgress (progn),
                            convertCalorieToProgressNutrition(pronut)));*/

                    int a = convertCalorieToProgressPhysique(proph);
                    int b = convertDurationToProgress(progn);
                    int c = convertCalorieToProgressNutrition(pronut);
                    updateProgress (key, calcul_progress(a,b,c));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private int convertDurationToProgress(int progn) {
        return (progn * 100) / 60;
    }

    private int convertCalorieToProgressPhysique(int pro) { return (pro * 100) / share.getInt("MAX1",1); }

    private int convertCalorieToProgressNutrition(int pro) { return (pro * 100) / share.getInt("MAX2",1); }

    public void getHistory_Taux_Progress_parobj (String titre, final ProgressBar progressBar){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Objectif").child("ObjectiveUser").child(MainMyMenu.user.getIdUser()).child(titre);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int taux = 0;
                    if (dataSnapshot.child("active").exists())
                        if (dataSnapshot.child("active").getValue(Boolean.class))
                            if (dataSnapshot.child("taux_progression").exists())
                                taux = dataSnapshot.child("taux_progression").getValue(Integer.class);
                    progressBar.setProgress(taux);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        //
    }

    public void getHistory_Taux_Progress (final String obj, final ProgressBar p){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("UserActivitys").child(MainMyMenu.user.getIdUser());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int taux = 0;
                int nb = 0;
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if (dataSnapshot.exists()) {
                        int proph = 0,progn = 0,pronut = 0,prowt = 0;

                        //get all progression
                        if (snapshot.child("Progressions").child("Cognitive").exists())
                            progn = snapshot.child("Progressions").child("Cognitive").getValue(Integer.class);

                        if (snapshot.child("Progressions").child("Nutrition").exists())
                            pronut = snapshot.child("Progressions").child("Nutrition").getValue(Integer.class);

                        if (snapshot.child("Progressions").child("Physique").exists())
                            proph = snapshot.child("Progressions").child("Physique").getValue(Integer.class);

                        if (snapshot.child("Progressions").child("Water").exists())
                            prowt = snapshot.child("Progressions").child("Water").getValue(Integer.class);


                        //calcul taux per day selon obj
                        switch (obj) {
                            case Sport.titre:
                                nb++;
                                taux += Sport.calcul_progression(convertCalorieToProgressPhysique(proph),
                                        convertDurationToProgress (progn),
                                        convertCalorieToProgressNutrition(pronut));
                                break;
                            case BienEtre.titre:
                                nb++;
                                taux += BienEtre.calcul_progression(convertCalorieToProgressPhysique(proph),
                                        convertDurationToProgress (progn),
                                        convertCalorieToProgressNutrition(pronut));
                                break;
                            case PreventionBurnOut.titre:
                                nb++;
                                taux += PreventionBurnOut.calcul_progression(convertCalorieToProgressPhysique(proph),
                                        convertDurationToProgress (progn),
                                        convertCalorieToProgressNutrition(pronut));
                                break;
                        }
                    }
                }
                if(nb == 0)
                    nb = 1;

                p.setProgress(taux / nb);
                animate_progressbar(p);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

/*    public void getHistory_all_Progress_parobj (final ProgressBar pro1,String objectif){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Objectif").child("ObjectiveUser").child(MainMyMenu.user.getIdUser()).child(objectif);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        int pro = 0;
                        String date;

                        if (snapshot.child("taux_progression").exists()) {
                            pro = snapshot.child("Progressions").child("Cognitive").getValue(Integer.class);
                        }
                        pro1.setProgress(pro);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        //
    }
*/

    //progression (jour)
    private int calcul_progress(int proph, int progn, int pronut) {
        int total = 0, nb = 0;

        for (int i = 0; i < user.getObjectifs().size(); i++) {
            switch (user.getObjectifs().get(i)){
                case Sport.titre:
                    total += Sport.calcul_progression(proph,progn,pronut) ;
                    nb ++; break;
                case BienEtre.titre:
                    total += BienEtre.calcul_progression(proph,progn,pronut) ;
                    nb ++; break;
                case PreventionBurnOut.titre:
                    total += PreventionBurnOut.calcul_progression(proph,progn,pronut) ;
                    nb ++; break;
            }
        }

        //Toast.makeText(getActivity(),"******pro = "+proph+ " |phnut = "+pronut+" |total"+total, Toast.LENGTH_SHORT).show();
        if (nb == 0) nb = 1;
        if ((total / nb) <= 100)
             return (int) (total / nb);
         else
             return 50;
    }

    private  void updateProgress (int key, int taux) {
        switch (key) {
            case 1: p1.setProgress (taux) ;animate_progressbar(p1);break;
            case 2: p2.setProgress (taux) ;animate_progressbar(p2);break;
            case 3: p3.setProgress (taux) ;animate_progressbar(p3);break;
            case 4: p4.setProgress (taux) ;animate_progressbar(p4);break;
            case 5: p5.setProgress (taux) ;animate_progressbar(p5);break;
            case 6: p6.setProgress (taux) ;animate_progressbar(p6);break;
            case 7: p7.setProgress (taux) ;animate_progressbar(p7);progress_txt.setText(String.valueOf(taux));break;
        }
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

    public void updateuser() {

        txt_fname.setText(user.getFirst_name());
        txt_lname.setText(user.getLast_name());
        txt_bday.setText(user.getAge()+" YO");
        txt_poids.setText(user.getPoids());
        txt_taille.setText(user.getTaille());
        txt_sexe.setText(user.getSexe());
        gerer_img_gender();
        put_list_obj();
    }

    void findUser () {
        DatabaseReference myDataBase = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getIdUser());
        myDataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user1 = new User();
                if (dataSnapshot.exists())
                    user1 = dataSnapshot.getValue(User.class);
                user = user1;
                updateuser();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
