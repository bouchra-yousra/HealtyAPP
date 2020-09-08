package com.example.healtyapp.dialogue;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.healtyapp.R;
import com.example.healtyapp.adapter.FiveWeightTrakerAdapter;
import com.example.healtyapp.database_item.MoodTrakerItem;
import com.example.healtyapp.database_item.WeightTrakerItem;
import com.example.healtyapp.ui.home.HomeFragment;
import com.example.healtyapp.vue.center_activities.MainMyMenu;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class MoodDialog extends AppCompatDialogFragment {
    private ExampleDialogListener listener;
    private LinearLayout a,b,c,d,e,f,g,h,l;
    int mood = -1;
    boolean selected = false;
    //color
    final int color2 = R.drawable.custum_layout;
    final int color1 = R.drawable.custom_layout4;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view =  inflater.inflate(R.layout.dialogue_item_mood, null);
        builder.setView(view)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (mood != (-1)) {
                            MoodTrakerItem m = new MoodTrakerItem(mood);
                            Toast.makeText(view.getContext(),""+mood ,Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(view.getContext(),"No mood selectes",Toast.LENGTH_SHORT).show();

                    }
                });

        a = view.findViewById(R.id.happy);
        b = view.findViewById(R.id.energitic);
        c = view.findViewById(R.id.normal);
        d = view.findViewById(R.id.sad);
        e = view.findViewById(R.id.tired);
        f = view.findViewById(R.id.sick);
        g = view.findViewById(R.id.nervous);
        h = view.findViewById(R.id.angry);
        l = view.findViewById(R.id.grumpy);

        prepare(a,b,c,d,e,f,g,h,l);

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gerer_color(a,b,c,d,e,f,g,h,l);
                mood = 0;
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gerer_color(b,a,c,d,e,f,g,h,l);
                mood = 1;
            }
        });
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gerer_color(c,b,a,d,e,f,g,h,l);
                mood = 2;
            }
        });
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gerer_color(d,b,c,a,e,f,g,h,l);
                mood = 3;
            }
        });
        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gerer_color(e,b,c,d,a,f,g,h,l);
                mood = 5;
            }
        });
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gerer_color(f,b,c,d,e,a,g,h,l);
                mood = 4;
            }
        });
        g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gerer_color(g,b,c,d,e,f,a,h,l);
                mood = 6;
            }
        });
        h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gerer_color(h,b,c,d,e,f,g,a,l);
                mood = 8;
            }
        });
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gerer_color(l,b,c,d,e,f,g,h,a);
                mood = 7;
            }
        });
        return builder.create();
    }
    public interface ExampleDialogListener {
        void onYesClicked();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ExampleDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + "must implement ExampleDialogListener");
        }
    }

    //METHODES
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void gerer_color (LinearLayout l,
                                   LinearLayout l1,
                                   LinearLayout l2,
                                   LinearLayout l3,
                                   LinearLayout l4,
                                   LinearLayout l5,
                                   LinearLayout l6,
                                   LinearLayout l7,
                                   LinearLayout l8) {

        selected = true;
        l1.setBackground(getActivity().getDrawable(color2));
        l2.setBackground(getActivity().getDrawable(color2));
        l3.setBackground(getActivity().getDrawable(color2));
        l4.setBackground(getActivity().getDrawable(color2));
        l5.setBackground(getActivity().getDrawable(color2));
        l6.setBackground(getActivity().getDrawable(color2));
        l7.setBackground(getActivity().getDrawable(color2));
        l8.setBackground(getActivity().getDrawable(color2));

        l.setBackground(getActivity().getDrawable(color1));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void prepare (LinearLayout l,
                             LinearLayout l1,
                             LinearLayout l2,
                             LinearLayout l3,
                             LinearLayout l4,
                             LinearLayout l5,
                             LinearLayout l6,
                             LinearLayout l7,
                             LinearLayout l8) {

        l1.setBackground(getActivity().getDrawable(color2));
        l2.setBackground(getActivity().getDrawable(color2));
        l3.setBackground(getActivity().getDrawable(color2));
        l4.setBackground(getActivity().getDrawable(color2));
        l5.setBackground(getActivity().getDrawable(color2));
        l6.setBackground(getActivity().getDrawable(color2));
        l7.setBackground(getActivity().getDrawable(color2));
        l8.setBackground(getActivity().getDrawable(color2));

        l.setBackground(getActivity().getDrawable(color2));
    }

    void update_five_weight_list () {
        ListView listView = HomeFragment.listView;

        listView.setAdapter(null);
        listView.setHorizontalScrollBarEnabled(true);
        listView.canScrollHorizontally(50);
        FiveWeightTrakerAdapter adapter = new FiveWeightTrakerAdapter(getActivity().getApplicationContext(),R.layout.item_five_weight_traker,HomeFragment.five_weight_list);
        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
        layoutParams.height = methode((HomeFragment.five_weight_list.size()*100)+5,getActivity()); //this is in pixels
        listView.setLayoutParams(layoutParams);
        adapter.notifyDataSetChanged();
        listView.setAdapter(null);

        listView.setAdapter(adapter);
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
}