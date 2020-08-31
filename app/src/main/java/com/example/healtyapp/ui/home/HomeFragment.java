package com.example.healtyapp.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.healtyapp.database_item.MoodTrakerItem;
import com.example.healtyapp.database_item.WeightTrakerItem;
import com.example.healtyapp.dialogue.ExampleDialog;
import com.example.healtyapp.vue.center_activities.MainMyMenu;
import com.example.healtyapp.R;
import com.example.healtyapp.module.User;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment implements ExampleDialog.ExampleDialogListener {

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

    Button addmood,addweight;

    //pman a
    //weight traker
    ArrayList<WeightTrakerItem> weight_list = new ArrayList<>();
    ArrayList<MoodTrakerItem> mood_list = new ArrayList<>();

    //plan b
    ArrayList<MoodTrakerItem[]> five_mood_list = new ArrayList<>();
    ArrayList<WeightTrakerItem[]> five_weight_list = new ArrayList<>();

    ListView listView, listViewMood;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.activity_home, container, false);

        share = getActivity().getSharedPreferences(SHARE,getActivity().MODE_PRIVATE);

        listView = root.findViewById(R.id.list_weight_traker);
        listViewMood = root.findViewById(R.id.list_mood_traker);

        text = root.findViewById(R.id.textHome);
        text.setText(String.valueOf((int) MainMyMenu.kca)+" Kca");

        addweight = root.findViewById(R.id.weight);
        addmood = root.findViewById(R.id.mood);

        //progression
        progressBar = root.findViewById(R.id.progressBarNutrition);
        progressBar.setMax((int) MainMyMenu.kca);
        progressBar.setProgress(share.getInt(PROGRESS_ALIMENT,0));

        //weight traker
        addweight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

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

        update_five_weight_list();
        //update_weight_list();

        //mood traker
        addmood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"moooooooood",Toast.LENGTH_SHORT).show();
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
        return root;
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
        layoutParams.height = methode((weight_list.size()*100)+5,getActivity()); //this is in pixels
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
        layoutParams.height = methode((mood_list.size()*100)+5,getActivity()); //this is in pixels
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

    //DIALOG
    public void openDialog() {
        ExampleDialog dialog = new ExampleDialog();
        dialog.show(requireActivity().getSupportFragmentManager(), "example dialog");
    }
    @Override
    public void onYesClicked() {
    }

}