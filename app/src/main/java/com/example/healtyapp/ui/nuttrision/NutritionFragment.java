package com.example.healtyapp.ui.nuttrision;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.healtyapp.database_item.Aliment;
import com.example.healtyapp.database_item.AlimentData;
import com.example.healtyapp.module.User;
import com.example.healtyapp.systeme.MainAddActPh;
import com.example.healtyapp.vue.center_activities.MainMyMenu;
import com.example.healtyapp.R;
import com.example.healtyapp.ui.home.HomeFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class NutritionFragment extends Fragment {

  RadioButton radio_bottle,radio_glass,radio_other;
  ImageView img_bottle,img_glass;
  Button show;
  EditText other_qt;
  LinearLayout linear,go
          ,leguems,fresh_fruits,dried_fruit
          ,burgers,meats,other;

  Button add_to_circle,water_traker_btn;
  Button clear;

  Button click;
  public static ProgressBar progressBar,progressBareau;
  EditText quantite;
  Spinner spinner;
  Button addt;

  TextView total,current,besoin_eau,conso_eau,center_water,center_food;

  static ArrayList<String> alimentsnames = new ArrayList<>();
  static Aliment currentAliment;
  // static int progress = 0;

  int maxKca;
  int maxEau;
  public static int home_pro = 0;

  DatabaseReference path;
  User user = MainMyMenu.user;

  //manipulate progress
  SharedPreferences share;
  final String SHARE = MainMyMenu.Share;
  static final String PROGRESS= MainMyMenu.PROGRESS_COGNITIVE;

  //today
  Calendar calSelected = Calendar.getInstance();
  String today = "" + calSelected.get(Calendar.DAY_OF_MONTH)
          + (calSelected.get(Calendar.MONTH) + 1)
          + calSelected.get(Calendar.YEAR);

  //color
  final int color2 = R.drawable.custum_layout;
  final int color1 = R.drawable.custom_layout4;

   final int color3 = R.drawable.custom_button3;
   final int color4 = R.drawable.custom_button1;

  private LinearLayout glass,bottle,water;
  TextView txt;
  EditText other_water;
  int quantite_water = 0;
  boolean cliked_water = false;

  private NutritionViewModel nutritionViewModel;

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    nutritionViewModel =
            ViewModelProviders.of(this).get(NutritionViewModel.class);
    View root = inflater.inflate(R.layout.activity_nuttrision, container, false);

    share = getActivity().getSharedPreferences(SHARE,MODE_PRIVATE);

    center_food = root.findViewById(R.id.center_food);
    center_water = root.findViewById(R.id.center_water);

    leguems = root.findViewById(R.id.leguems);
    fresh_fruits = root.findViewById(R.id.fresh_fruits);
    dried_fruit = root.findViewById(R.id.dried_fruit);
    meats = root.findViewById(R.id.meats);
    burgers = root.findViewById(R.id.burgers);
    other = root.findViewById(R.id.other);

    radio_bottle=root.findViewById(R.id.radio_bottle);
    radio_glass=root.findViewById(R.id.radio_verre);
    radio_other=root.findViewById(R.id.radio_autreqt);
    img_bottle=root.findViewById(R.id.image_boutielle);
    img_glass=root.findViewById(R.id.image_verre);

    show =root.findViewById(R.id.add_water);

    other_qt = root.findViewById(R.id.autre_qt);
    linear =root.findViewById(R.id.linear);
    addt = root.findViewById(R.id.addt);
    add_to_circle = root.findViewById(R.id.add_water_tocircle);
    clear = root.findViewById(R.id.clear);
    radio_glass.setChecked(true);

    go = root.findViewById(R.id.go);
    maxKca = (int) MainMyMenu.kca;
    maxEau = (int) MainMyMenu.bes_eau;
    conso_eau = root.findViewById(R.id.consoeau);
    current = root.findViewById(R.id.newcl);
    total = root.findViewById(R.id.maxcl);
    besoin_eau = root.findViewById(R.id.besoin_eau);
    besoin_eau.setText(String.valueOf(maxEau)+" MiliLitres");
    total.setText(String.valueOf(maxKca)+" Calories");
    spinner = root.findViewById(R.id.spinner_t);


    txt = root.findViewById(R.id.the_other_txt);
    glass = root.findViewById(R.id.the_glass);
    water = root.findViewById(R.id.the_other);
    bottle = root.findViewById(R.id.the_bottle);
    water_traker_btn = root.findViewById(R.id.add_water_nut);
    other_water = root.findViewById(R.id.the_other_edit);
    other_water.setVisibility(View.GONE);

    spinner_ (true);
    prepare (leguems,fresh_fruits,dried_fruit
            ,burgers,meats,other);

    leguems.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        categorie_aliment(leguems,"Leguems",fresh_fruits,dried_fruit
                ,burgers,meats,other);
        spinner_(false);
      }
    });
    fresh_fruits.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        categorie_aliment(fresh_fruits,"Fruits Frais",leguems,dried_fruit
                ,burgers,meats,other);
        spinner_(false);
      }
    });
    dried_fruit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        categorie_aliment(dried_fruit,"Fruits Sec",fresh_fruits,leguems
                ,burgers,meats,other);
        spinner_(false);
      }
    });
    burgers.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        categorie_aliment(burgers,"Burgers",fresh_fruits,dried_fruit
                ,leguems,meats,other);
        spinner_(false);
      }
    });
    meats.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        categorie_aliment(meats,"Viandes",fresh_fruits,dried_fruit
                ,burgers,leguems,other);
        spinner_(false);
      }
    });
    other.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        getAll2();
        categorie_aliment(other,"other",fresh_fruits,dried_fruit
                ,burgers,meats,leguems);
        spinner_(false);
      }
    });

    progressBar = root.findViewById(R.id.progressBarNutrition);
    quantite = root.findViewById(R.id.quantite);
    progressBareau = root.findViewById(R.id.progresseau);

    progressBareau.setMax(maxEau);
    progressBar.setMax(maxKca);

    HomeFragment.progressBar.setMax(maxKca);

    conso_eau.setText(String.valueOf(share.getInt("currentWater",0)));
    current.setText(String.valueOf(share.getInt("currentKca",0)));

    progressBar.setProgress(share.getInt("currentKca",0));
    HomeFragment.progressBar.setProgress(share.getInt("currentKca",0));

    progressBareau.setProgress(share.getInt("currentWater",0));
    click = root.findViewById(R.id.click);

    center_food.setText(String.valueOf((progressBar.getProgress()*100) / progressBar.getMax()));
    center_water.setText(String.valueOf((progressBareau.getProgress()*100) / progressBareau.getMax()));

    addt.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        activiy_nutrition_aliment ();
      }
    });

    click.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(getActivity(), MainAddActPh.class));
      }
    });


    show.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(linear.getVisibility() == View.VISIBLE){
          linear.setVisibility(View.GONE);
          show.setText("Click");
        }else{
          linear.setVisibility(View.VISIBLE);
          show.setText("Annuler");
        }

        cliked_water = true;
      }
    });


    prepare_watertraker(glass,water,bottle);
    glass.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        txt.setVisibility(View.VISIBLE);
        other_water.setVisibility(View.GONE);
        gerer_watertraker(glass,bottle,water,25);
      }
    });

    water.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        gerer_watertraker(water,bottle,glass,100);
        txt.setVisibility(View.GONE);
        other_water.setVisibility(View.VISIBLE);
        if (!other_water.getText().toString().isEmpty() && Integer.parseInt(other_water.getText().toString()) > 0 && Integer.parseInt(other_water.getText().toString()) <= 1000)
          quantite_water = Integer.parseInt(other_water.getText().toString()) * 10;
        else{
          quantite_water = 0;
        Toast.makeText(getActivity(),"The quantite must be under 1000cl, try again",Toast.LENGTH_SHORT).show();}

        cliked_water = true;
      }
    });

    bottle.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View view) {
        txt.setVisibility(View.VISIBLE);
        other_water.setVisibility(View.GONE);
        gerer_watertraker(bottle,water,glass,50);
        cliked_water = true;
      }
    });

    water_traker_btn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (cliked_water){
        int x = Integer.parseInt(conso_eau.getText().toString());
        conso_eau.setText(String.valueOf(x+quantite_water));
        //Updates_water(0);

        Updates_water(share.getInt("currentWater",0)+quantite_water);
        progressBareau.setProgress(share.getInt("currentWater",0));
        center_water.setText(String.valueOf((progressBareau.getProgress()*100) / progressBareau.getMax()));
        addprogress_water((progressBareau.getProgress()*100) / progressBareau.getMax());

        //Toast.makeText(getActivity(),"water" + quantite_water + "|" + share.getInt("currentWater",0) + "|",Toast.LENGTH_SHORT).show();
        }
      }
    });


    img_bottle.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        radio_bottle.setChecked(true);
        other_qt.setEnabled(false);
        radio_glass.setChecked(false);
        radio_other.setChecked(false);
      }
    });

    img_glass.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        radio_glass.setChecked(true);
        other_qt.setEnabled(false);
        radio_bottle.setChecked(false);
        radio_other.setChecked(false);
      }
    });


    radio_other.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        radio_other.setChecked(true);
        radio_bottle.setChecked(false);
        radio_glass.setChecked(false);
        other_qt.setEnabled(true);
      }
    });

    radio_glass.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        radio_glass.setChecked(true);

        radio_other.setChecked(false);
        radio_bottle.setChecked(false);
        other_qt.setEnabled(false);
      }
    });

    radio_bottle.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        radio_bottle.setChecked(true);

        radio_other.setChecked(false);
        radio_glass.setChecked(false);
        other_qt.setEnabled(false);
      }
    });


    add_to_circle.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        int x = Integer.parseInt(conso_eau.getText().toString());
        if(radio_glass.isChecked()){
          progressBareau.setProgress(progressBareau.getProgress()+250);
          //todo add progress water
          conso_eau.setText(String.valueOf(x+250));
          Toast.makeText(getActivity(),"Ajouter Avec Succes",Toast.LENGTH_SHORT);
          linear.setVisibility(View.GONE);
          show.setText("Click");
          Updates(Integer.parseInt(current.getText().toString()),Integer.parseInt(conso_eau.getText().toString()));
        }

        if(radio_bottle.isChecked()){
          progressBareau.setProgress(progressBareau.getProgress()+500);
          conso_eau.setText(String.valueOf(x+500));
          Toast.makeText(getActivity(),"Ajouter Avec Succes",Toast.LENGTH_SHORT);
          linear.setVisibility(View.GONE);
          show.setText("Click");
          Updates(Integer.parseInt(current.getText().toString()),Integer.parseInt(conso_eau.getText().toString()));
        }

        if(radio_other.isChecked()){
          if(!other_qt.getText().toString().isEmpty()){
            progressBareau.setProgress(progressBareau.getProgress()+(Integer.parseInt(other_qt.getText().toString())*10));
            conso_eau.setText(String.valueOf(x+Integer.parseInt(other_qt.getText().toString())*10));
            Toast.makeText(getActivity(),"Ajouter Avec Succes",Toast.LENGTH_SHORT);
            linear.setVisibility(View.GONE);
            show.setText("Click");
            Updates(Integer.parseInt(current.getText().toString()),Integer.parseInt(conso_eau.getText().toString()));
          }else{
            Toast.makeText(getActivity(),"Verifer la Quantite",Toast.LENGTH_SHORT);
          }
        }
      }
    });

    clear.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        SharedPreferences.Editor editor = share.edit();
        editor.clear();
        editor.commit();
        editor.apply();
        progressBareau.setProgress(0);
        progressBar.setProgress(0);
        current.setText(String.valueOf(0));
        conso_eau.setText(String.valueOf(0));
      }
    });

    go.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(getActivity(), NutritionHistoryActivity.class));
      }
    });

    home_pro = progressBar.getProgress();
    return root;
  }


  //METHODES
  private void activiy_nutrition_aliment() {
    if(!quantite.getText().toString().trim().isEmpty()){
      if(progressBar.getProgress()<maxKca){

        //calorie d'aliment
        int x = (int)convertQttoCl(Integer.parseInt(quantite.getText().toString()));
        current.setText(String.valueOf(Integer.parseInt(current.getText().toString())+x));

        //incrementer progress
        progressBar.setProgress(progressBar.getProgress()+ x);


        progressBareau.setProgress(progressBareau.getProgress() + convertQttoGr(Integer.parseInt(quantite.getText().toString())));
        conso_eau.setText(String.valueOf(Integer.parseInt(conso_eau.getText().toString())+convertQttoGr(Integer.parseInt(quantite.getText().toString()))));

        //HomeFragment.max_progress = progressBar.getProgress();

        //NutritionHistoryActivity.myArray.add(new ItemHistory(currentAliment.getNom(),x));
        Updates(share.getInt("currentKca",0) + x, share.getInt("currentWater",0) + convertQttoGr(Integer.parseInt(quantite.getText().toString())));
        addatabase(currentAliment,Integer.parseInt(quantite.getText().toString()));
        addprogress_water((progressBareau.getProgress()*100) / progressBareau.getMax());

        center_food.setText(String.valueOf((progressBar.getProgress()*100) / progressBar.getMax()));
        center_water.setText(String.valueOf((progressBareau.getProgress()*100) / progressBareau.getMax()));
        Toast.makeText(getActivity(),"kcal: "+String.valueOf(share.getInt("currentKca",0)) +"w "+ String.valueOf(share.getInt("currentKca",0)),Toast.LENGTH_SHORT).show();
      }
    }
  }

  public void getAll2(){
    alimentsnames = new ArrayList<>();
    for(int i =0;i<MainMyMenu.arrayList.size();i++){
      alimentsnames.add(MainMyMenu.arrayList.get(i).getNom());
    }
  }

  private void spinner_(boolean b) {
    if (b)
      getAll2();

    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, alimentsnames);
    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(arrayAdapter);
    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(getContext(),parent.getItemAtPosition(position).toString(),Toast.LENGTH_SHORT).show();
        //currentAliment = MainMyMenu.arrayList.get(position);
        currentAliment = getAliment(alimentsnames.get(position));
        Toast.makeText(getContext(),currentAliment.getNom(),Toast.LENGTH_SHORT).show();
      }
      @Override
      public void onNothingSelected(AdapterView<?> parent) {
      }
    });

  }

  //get element
  public Aliment getAliment (String a) {
    ArrayList <Aliment> arrayList = MainMyMenu.arrayList;
    for (int i = 0; i < arrayList.size(); i++) {
      if (arrayList.get(i) != null && arrayList.get(i).getNom().trim().equals(a))
        return arrayList.get(i);
    }
    return null;
  }

  //CALCUL
  public double convertQttoCl(int quantite){
    return (double)(quantite*currentAliment.getCalorie().getTotal())/100;
  }

  public int convertCltoPercent(double cal,int max){
    return (int)(((cal*100)/max)*100);

  }

  public int convertQttoGr(int qt){
    return (qt*currentAliment.getPourcentage_eau())/100;
  }

  //DATA
  //__share data
  public void Updates(int kca,int w){
    SharedPreferences.Editor editor = share.edit();
    editor.putInt("currentKca",kca);
    editor.putInt("currentWater",w);
    editor.commit();
    editor.apply();
  }

  public void Updates_water(int w){
    SharedPreferences.Editor editor = share.edit();
    editor.putInt("currentWater",w);
    editor.apply();
  }


  //__ database data
  private  void addatabase (Aliment a, int q){
    path = FirebaseDatabase.getInstance().getReference().child("UserActivitys").child(user.getIdUser()).child(today).child("Nutrition").child(AlimentData.nb+a.getNom());
    path.setValue(new AlimentData(a.getNom(),q));
    addprogress((progressBar.getProgress() *100) / progressBar.getMax());
  }

  private void addprogress (int p) {
    path = FirebaseDatabase.getInstance().getReference().child("UserActivitys").child(user.getIdUser()).child(today).child("Progressions").child("Nutrition");
    path.setValue(p);
  }

  private void addprogress_water (int p) {
    path = FirebaseDatabase.getInstance().getReference().child("UserActivitys").child(user.getIdUser()).child(today).child("Progressions").child("Water");
    path.setValue(p);
  }


  //VUE
  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public void categorie_aliment (LinearLayout l, String categorie,
                                              LinearLayout l1,
                                              LinearLayout l2,
                                              LinearLayout l3,
                                              LinearLayout l4,
                                              LinearLayout l5) {

    l1.setBackground(getActivity().getDrawable(color2));
    l2.setBackground(getActivity().getDrawable(color2));
    l3.setBackground(getActivity().getDrawable(color2));
    l4.setBackground(getActivity().getDrawable(color2));
    l5.setBackground(getActivity().getDrawable(color2));

    l.setBackground(getActivity().getDrawable(color1));

    if (categorie.equals("other"))
      return;

    alimentsnames = new ArrayList<String>();
    for (int i = 0;i < MainMyMenu.arrayList.size(); i++) {
      if (MainMyMenu.arrayList.get(i).getCategorie() != null)
        if (MainMyMenu.arrayList.get(i).getCategorie().equals(categorie))
          alimentsnames.add(MainMyMenu.arrayList.get(i).getNom());
    }
  }


  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  private void prepare(LinearLayout l1,
                       LinearLayout l2,
                       LinearLayout l3,
                       LinearLayout l4,
                       LinearLayout l5,
                       LinearLayout l6) {
    l1.setBackground(getActivity().getDrawable(color2));
    l2.setBackground(getActivity().getDrawable(color2));
    l3.setBackground(getActivity().getDrawable(color2));
    l4.setBackground(getActivity().getDrawable(color2));
    l5.setBackground(getActivity().getDrawable(color2));
    l6.setBackground(getActivity().getDrawable(color2));
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  void prepare_watertraker (LinearLayout l3, LinearLayout l1, LinearLayout l2){
    l1.setBackground(getActivity().getDrawable(color2));
    l2.setBackground(getActivity().getDrawable(color2));
    l3.setBackground(getActivity().getDrawable(color2));
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  void gerer_watertraker (LinearLayout l1, LinearLayout l2, LinearLayout l3,int q) {
    l1.setBackground(getActivity().getDrawable(color1));
    l2.setBackground(getActivity().getDrawable(color2));
    l3.setBackground(getActivity().getDrawable(color2));

    //Toast.makeText(getActivity(),"bottle",Toast.LENGTH_SHORT).show();

    quantite_water = q * 10;
  }
}
