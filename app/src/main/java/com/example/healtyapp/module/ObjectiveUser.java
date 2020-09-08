package com.example.healtyapp.module;

import android.content.Intent;

import java.util.ArrayList;
import java.util.Calendar;

public class ObjectiveUser {
    boolean active;
    String date_debut;
    String date_fin;
    String id;
    int taux_progress;
    //int track_days;

    public ObjectiveUser(String id) {
        Calendar calSelected = Calendar.getInstance();
        String today = "" + calSelected.get(Calendar.DAY_OF_MONTH)
                + (calSelected.get(Calendar.MONTH) + 1)
                + calSelected.get(Calendar.YEAR);

        this.active = true;
        this.date_debut = today;
        this.id = id;
        this.taux_progress = 0;
/*
        String  d = ""+calSelected.get(Calendar.DAY_OF_MONTH);
        String m =  ""+(calSelected.get(Calendar.MONTH) + 1);

        if (d.length() == 1)
            d = "0" + d;

        if (m.length() == 1)
            m = "0" + m;

        String today = "" +d
                +m
                + calSelected.get(Calendar.YEAR);*/
        //this.track_days = 0;
    }

    static int calcul_progression(int activite_physique, int activivte_cognitive, int activite_nutrition) {
        return 0;
    }

    //METHODES
    public void quiter_objective () {
        //today
        Calendar calSelected = Calendar.getInstance();
        String today = "" + calSelected.get(Calendar.DAY_OF_MONTH)
                +  (calSelected.get(Calendar.MONTH) + 1)
                +  calSelected.get(Calendar.YEAR);

        this.active = false;
        this.date_fin = today;
        //this.track_days = 0;
    }

    public int addToTauxProgress (int taux_progress) {
        this.taux_progress += taux_progress;
        return this.taux_progress;
    }

    //GETTERS
    public boolean isActive() {
        return active;
    }

    public String getDate_debut() {
        return date_debut;
    }

    public String getDate_fin() {
        return date_fin;
    }


    public String getId() {
        return id;
    }

    public int getTaux_progress() {
        return taux_progress;
    }



/*
    public static Birthday calcul (String word){
        int a= Integer.parseInt(word);
        int lengh = word.length();
        Calendar calSelected = Calendar.getInstance();
        int     day= calSelected.get(Calendar.DAY_OF_MONTH),
                month = (calSelected.get(Calendar.MONTH) + 1),
                year = calSelected.get(Calendar.YEAR);
        String y,m,d;

        y = ""+ ((a % 10000))+(a % 1000)+(a % 100)+(a % 10);
        switch (lengh) {
            case 8:
                m = ""+((a % 1000000))+(a % 100000);
                d = ""+((a % 100000000))+(a % 10000000);
                break;
            case 7:
                m = ""+((a % 1000000))+(a % 100000);
                d = ""+(a % 10000000);
                break;
            case 6:
                m = ""+(a % 100000);
                d = ""+(a % 1000000);
                break;
        }


        ArrayList<Integer> arrayList = new ArrayList();
        String a="";

        for(int i=0;i<word.length();i++){
            if(String.valueOf(word.charAt(i)).equals()){
                arrayList.add(Integer.parseInt(a));
                a="";
            }else{
                a=a+word.charAt(i);
            }
        }
        return  new Birthday(arrayList.get(0),arrayList.get(1),arrayList.get(2));
    }*/
}
