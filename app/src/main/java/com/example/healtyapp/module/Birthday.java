package com.example.healtyapp.module;

import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;

public class Birthday {
    int day;
    int month;
    int year;

    public Birthday(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Birthday(Calendar c) {
        this.day = c.get(Calendar.DAY_OF_MONTH);
        this.month = c.get(Calendar.MONTH);
        this.year = c.get(Calendar.YEAR);
    }

    //METHODES
    int calculate_age (Calendar bday) {
        int age = 0;
        Calendar c = Calendar.getInstance();

        //today's year > year => bday correct
        if ( c.get(Calendar.YEAR) - bday.get(Calendar.YEAR) >= 0){

            //if today's month > month => means user bday has passed
            if ( c.get(Calendar.MONTH) - bday.get(Calendar.MONTH) >= 0){

                //if today's day > day => user bday passed
                if ( c.get(Calendar.DAY_OF_MONTH) - bday.get(Calendar.DAY_OF_MONTH) >= 0)
                    age = c.get(Calendar.YEAR) - bday.get(Calendar.YEAR);

                    //user bday is still comming
                else
                    age = c.get(Calendar.YEAR) - bday.get(Calendar.YEAR) -1;

                //user bday is still comming
            }else
                age = c.get(Calendar.YEAR) - bday.get(Calendar.YEAR);

            //user bday is false
        }else
            age = 0;
        return age;
    }

    boolean isBirthday (Calendar bday) {
        Calendar c = Calendar.getInstance();
        if (c.get(Calendar.MONTH) - bday.get(Calendar.MONTH) == 0 && c.get(Calendar.DAY_OF_MONTH) - bday.get(Calendar.DAY_OF_MONTH) == 0)
            return true;
        return false;
    }

    //METHODES Birthday
    int calculate_age () {
        int age = 0;
        Calendar c = Calendar.getInstance();

        //today's year > year => bday correct
        if ( c.get(Calendar.YEAR) - this.year >= 0){

            //if today's month > month => means user bday has passed
            if ( c.get(Calendar.MONTH) - this.month >= 0){

                //if today's day > day => user bday passed
                if ( c.get(Calendar.DAY_OF_MONTH) - this.day >= 0)
                    age = c.get(Calendar.YEAR) - this.year;

                    //user bday is still comming
                else
                    age = c.get(Calendar.YEAR) - this.year -1;

                //user bday is still comming
            }else
                age = c.get(Calendar.YEAR) - this.year;

            //user bday is false
        }else
            age = 0;
        return age;
    }

    boolean isBirthday () {
        Calendar c = Calendar.getInstance();
        if (c.get(Calendar.MONTH) - this.month == 0 && c.get(Calendar.DAY_OF_MONTH) - this.day == 0)
            return true;
        return false;
    }

    //SETTERS/GETTERS
    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}
