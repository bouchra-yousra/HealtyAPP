package com.example.healtyapp.database_item;

import com.example.healtyapp.R;

import java.util.Calendar;

public class MoodTrakerItem {
    private static final String mood0 = "HAPPY",
            mood1 = "ENERGEITIC",
            mood8 = "ANGRY",
            mood3 = "SAD",
            mood4 = "SICK",
            mood5 = "TIRED",
            mood6 = "NERVOUS",
            mood7 = "GRUMPY",
            mood2 = "NORMAL";

    private static int  happy = 0,
                        energetic = 1,
                        normal = 2,
                        sad = 3,
                        sick = 4,
                        tired = 5,
                        nervous = 6,
                        grumpy = 7,
                        angry = 8;

    private int mood; //en kg
    private String date;

    public MoodTrakerItem(int mood) {
        this.mood = mood;
        this.date = initDate();
    }

    private String initDate() {
        //today
        Calendar calSelected = Calendar.getInstance();
        String today = "" + calSelected.get(Calendar.DAY_OF_MONTH)
                + (calSelected.get(Calendar.MONTH) + 1)
                + calSelected.get(Calendar.YEAR);
        return today;
    }

    //METHODES
    public int Moodicon (int mood) {
        int s = 0;
        //drawble selon mood
        switch (mood){
            case 0: s = R.drawable.ic_happy;break;
            case 1: s = R.drawable.ic_smile;break;
            case 2: s = R.drawable.ic_normal;break;
            case 3: s = R.drawable.ic_sad;break;
            case 4: s = R.drawable.ic_sick;break;
            case 5: s = R.drawable.ic_tired;break;
            case 6: s = R.drawable.ic_nervous;break;
            case 7: s = R.drawable.ic_grumpy;break;
            case 8: s = R.drawable.ic_angry;break;
        }
        return s;
    }

    public int Moodicon () {
        int s = 0;
        //drawble selon mood
        switch (this.mood){
            case 0: s = R.drawable.ic_happy;break;
            case 1: s = R.drawable.ic_smile;break;
            case 2: s = R.drawable.ic_normal;break;
            case 3: s = R.drawable.ic_sad;break;
            case 4: s = R.drawable.ic_sick;break;
            case 5: s = R.drawable.ic_tired;break;
            case 6: s = R.drawable.ic_nervous;break;
            case 7: s = R.drawable.ic_grumpy;break;
            case 8: s = R.drawable.ic_angry;break;
        }
        return s;
    }

    public static String moodByNumber(int mood) {
        String s = mood0;

        switch (mood){
            case 0: s = mood0;break;
            case 1: s = mood1;break;
            case 2: s = mood2;break;
            case 3: s = mood3;break;
            case 4: s = mood4;break;
            case 5: s = mood5;break;
            case 6: s = mood6;break;
            case 7: s = mood7;break;
            case 8: s = mood8;break;
        }
        return s;
    }

    public static int moodByString(String mood) {
        int s = 0;

        switch (mood){
            case mood0 : s = happy;break;
            case mood1 : s = energetic;break;
            case mood2 : s = normal;break;
            case mood3 : s = sad;break;
            case mood4 : s = sick;break;
            case mood5 : s = tired;break;
            case mood6 : s = nervous;break;
            case mood7 : s = grumpy;break;
            case mood8 : s = angry;break;
        }
        return s;
    }
    //GETTERS


    public String getDate() {
        return date;
    }

    public int getMood() {
        return mood;
    }
}
