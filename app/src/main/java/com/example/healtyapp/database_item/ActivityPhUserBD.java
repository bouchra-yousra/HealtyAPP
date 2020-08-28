package com.example.healtyapp.database_item;

import java.util.ArrayList;

public class ActivityPhUserBD {
    private  String id_user;
    private ArrayList<ActivityPhUser> activityPhUsers;
    private  int duree_total;
    private  int calories_total;

    ActivityPhUserBD(){
        this.activityPhUsers = new ArrayList<>();
        this.duree_total = 0;
        this.calories_total = 0;
    }

    public ArrayList<ActivityPhUser> getActivityPhUsers() {
        return activityPhUsers;
    }

    public void setActivityPhUsers(ArrayList<ActivityPhUser> activityPhUsers) {
        this.activityPhUsers = activityPhUsers;
    }

    public int getDuree_total() {
        return duree_total;
    }

    public void setDuree_total(int duree_total) {
        this.duree_total = duree_total;
    }

    public int getCalories_total() {
        return calories_total;
    }

    public void setCalories_total(int calories_total) {
        this.calories_total = calories_total;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }
}
