package com.example.healtyapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.healtyapp.adapter_item.Objectifitem;
import com.example.healtyapp.R;
import com.example.healtyapp.ui.profile.ProfileFragment;

import java.util.List;

public class ObjectifAdapter extends ArrayAdapter <Objectifitem> {

    private static final  String TAG ="Objectif";
    private Context context;
    private int resource;

    public ObjectifAdapter(@NonNull Context context, int resource, @NonNull List<Objectifitem> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(this.context);
        convertView = inflater.inflate(this.resource,parent,false);

        TextView objname;
        ProgressBar physique,cognitive,nutrition;
        final RelativeLayout main_layout;

        objname = convertView.findViewById(R.id.objname);
        physique = convertView.findViewById(R.id.progress_physique);
        cognitive = convertView.findViewById(R.id.progress_cognitive);
        nutrition = convertView.findViewById(R.id.progress_nutrition);
        main_layout = convertView.findViewById(R.id.layout_objectif_item);

        objname.setText(getItem(position).name+" id ="+getItem(position).id);
        physique.setProgress(30);
        cognitive.setProgress(40);
        nutrition.setProgress(50);

        objname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (main_layout.getVisibility() == View.GONE){
                    main_layout.setVisibility(View.VISIBLE);
                    ProfileFragment.one_all++;
                }

                else {
                    main_layout.setVisibility(View.GONE);
                    if(ProfileFragment.one_all > 0)
                        ProfileFragment.one_all--;
                }

            }
        });


        return convertView;
    }
}
