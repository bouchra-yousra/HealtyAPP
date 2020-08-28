package com.example.healtyapp.ui.profile;

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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.healtyapp.module.BienEtre;
import com.example.healtyapp.module.PreventionBurnOut;
import com.example.healtyapp.module.Sport;
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

    private ProfileViewModel profileViewModel;
    static User user = new User();
    private TextView txt_poids, txt_taille, txt_bday, txt_fname, txt_lname, txt_sexe;
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

    public static int one_all = 0; //changes when the Objectif item;changes

    //manipulate progress
    SharedPreferences share,share2;
    final String SHARE = MainMyMenu.Share;
    final String SHARE2 = MainActivity.MyUser;
    static final String PROGRESS= MainMyMenu.PROGRESS_COGNITIVE;

    //data
    private FirebaseAuth myAuth;
    DatabaseReference databaseReference;

    //week progress
    TextView t1,t2,t3,t4,t5,t6,t7;
    ProgressBar p1,p2,p3,p4,p5,p6,p7;
    ProgressBar a1,a2,a3,
                b1,b2,b3,
                c1,c2,c3;

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
        txt_bday.setText(user.getAge());
        txt_poids = root.findViewById(R.id.poid);
        txt_poids.setText(user.getPoids());
        txt_taille = root.findViewById(R.id.taille);
        txt_taille.setText(user.getTaille());
        txt_sexe = root.findViewById(R.id.sexe);
        txt_sexe.setText(user.getSexe());
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

        a1 = root.findViewById(R.id.progress_physique1);
        a2 = root.findViewById(R.id.progress_physique2);
        a3 = root.findViewById(R.id.progress_physique3);
        b1 = root.findViewById(R.id.progress_cognitive1);
        b2 = root.findViewById(R.id.progress_cognitive2);
        b3 = root.findViewById(R.id.progress_cognitive3);
        c1 = root.findViewById(R.id.progress_nutrition1);
        c2 = root.findViewById(R.id.progress_nutrition2);
        c3 = root.findViewById(R.id.progress_nutrition3);

        a1.setProgress(0);
        a2.setProgress(0);
        a3.setProgress(0);

        change_progress (a1,0);
        change_progress (a2,0);
        change_progress (a3,0);
        change_progress (b1,0);
        change_progress (b2,0);
        change_progress (b3,0);
        change_progress (c1,0);
        change_progress (c2,0);
        change_progress (c3,0);
        /*
        objectif = root.findViewById(R.id.list_objectif);
        adapt_obj = new ObjectifAdapter(root.getContext(),R.layout.item_objectif,arrayList);

        //layout de la liste des objectif (listeView)
        layoutParams = objectif.getLayoutParams();
        get_hight_layout ();
        adapt_obj.notifyDataSetChanged();
        objectif.setAdapter(null);
        objectif.setAdapter(adapt_obj);
        */

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
                    c = R.id.progress_cognitive2;
                    d = R.id.progress_nutrition2;
                    e = R.id.layoutobj2;
                    obj1 = true;
                    break;
                }
                case 2: {
                    a = R.id.obj3;
                    b = R.id.progress_physique3;
                    c = R.id.progress_cognitive3;
                    d = R.id.progress_nutrition3;
                    e = R.id.layoutobj3;
                    obj2 = true;
                    break;
                }
                default: {
                    a = R.id.obj1;
                    b = R.id.progress_physique1;
                    c = R.id.progress_cognitive1;
                    d = R.id.progress_nutrition1;
                    e = R.id.layoutobj1;
                }
            }
            TextView textView = root.findViewById(a) ;
            LinearLayout l = root.findViewById(e);
            textView.setText(String.valueOf(i));

            ProgressBar p1 = root.findViewById(b);
            ProgressBar p2 = root.findViewById(c);
            ProgressBar p3 = root.findViewById(d);
            display_item(arrayList.get(i),textView,p1,p2,p3,l);
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
        t7.setText(arrayList.get(6));

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

    //list objectif
    void put_list_obj(){
        for (int i = 0;i < user.getObjectifs().size(); i++){
            obj = new Objectifitem(user.getObjectifs().get(i),30,30,30);
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

    void display_item(Objectifitem object, TextView name, ProgressBar physique, ProgressBar cognitive, ProgressBar nutrition, LinearLayout l) {
        name.setText(object.getName());
        l.setVisibility(View.VISIBLE);
        physique.setProgress(object.getProgress_physique());
        cognitive.setProgress(object.getProgress_cognitive());
        nutrition.setProgress(object.getProgress_nutrition());
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

        edit1.clear();
        edit1.apply();

        edit2.clear();
        edit2.apply();
        FirebaseAuth.getInstance().signOut();
        getActivity().finish();
    }

    //DATA : week progress
    public void getHistory_Progress (String today, final int key){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("UserActivitys").child(MainMyMenu.user.getIdUser()).child(today).child("Progressions");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int proph = 0,progn = 0,pronut = 0,prowt = 0;

                    if (dataSnapshot.child("Cognitive").exists())
                        progn = dataSnapshot.child("Cognitive").getValue(Integer.class);

                    if (dataSnapshot.child("Nutrition").exists())
                        pronut = dataSnapshot.child("Nutrition").getValue(Integer.class);

                    if (dataSnapshot.child("Physique").exists())
                        proph = dataSnapshot.child("Physique").getValue(Integer.class);

                    if (dataSnapshot.child("Water").exists())
                        prowt = dataSnapshot.child("Water").getValue(Integer.class);

                    updateProgress (key, calcul_progress(proph,progn,pronut));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        //
    }

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
            case 1: p1.setProgress (taux) ;break;
            case 2: p2.setProgress (taux) ;break;
            case 3: p3.setProgress (taux) ;break;
            case 4: p4.setProgress (taux) ;break;
            case 5: p5.setProgress (taux) ;break;
            case 6: p6.setProgress (taux) ;break;
            case 7: p7.setProgress (taux) ;break;
        }
    }
}
