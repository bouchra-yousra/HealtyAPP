package com.example.healtyapp.ui.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.healtyapp.vue.center_activities.MainMyMenu;
import com.example.healtyapp.R;
import com.example.healtyapp.module.User;
import com.google.firebase.database.DatabaseReference;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    static User user = new User();
    TextView text;
    //int maxKca;
    DatabaseReference myDataBase;
    public static ProgressBar progressBar;
    public static int max_progress = 0;

    //manipulate progress
    SharedPreferences share;
    final String SHARE = MainMyMenu.Share;
    static final String PROGRESS= MainMyMenu.PROGRESS_NUTRITION;
    static final String PROGRESS_ALIMENT= "currentKca";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.activity_home, container, false);

        share = getActivity().getSharedPreferences(SHARE,getActivity().MODE_PRIVATE);

        text = root.findViewById(R.id.textHome);
        text.setText(String.valueOf((int) MainMyMenu.kca)+" Kca");


        progressBar = root.findViewById(R.id.progressBarNutrition);
        progressBar.setMax((int) MainMyMenu.kca);
        progressBar.setProgress(share.getInt(PROGRESS_ALIMENT,0));


        return root;
    }
}