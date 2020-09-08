package com.example.healtyapp.ui.activitymorale;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.healtyapp.R;

import java.util.ArrayList;

public class MainReduceStress extends AppCompatActivity {

    //edit: added burnout attribute (textViews/string[]..ect)
    TextView click1,click2,aigu,chronic,burnOut,click3;
    String[] a;
    String[] c;
    String[] b;

    final int color1 = R.drawable.custom_button1;
    final int color2 = R.drawable.custom_button3;

    LinearLayout back;
    LinearLayout stress1,stress2,stress3;
    Button  type1,type2,type3;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_reduce_stress);

        click1 = findViewById(R.id.click1);
        click2= findViewById(R.id.click2);
        click3= findViewById(R.id.click3);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        aigu = findViewById(R.id.aigu);
        chronic = findViewById(R.id.chronic);
        burnOut = findViewById(R.id.burnOut);

        a = getResources().getStringArray(R.array.aigu);
        c = getResources().getStringArray(R.array.chronic);
        b = getResources().getStringArray(R.array.burnOut);

        stress1 = findViewById(R.id.stress1);
        stress2 = findViewById(R.id.stress2);
        stress3 = findViewById(R.id.stress3);

        type1 = findViewById(R.id.type1);
        type2 = findViewById(R.id.type2);
        type3 = findViewById(R.id.type3);

        prepare(type1,type2,type3);
        type1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gerer_color(type1,type2,type3);
                gerer_visibility(stress1,stress2,stress3);
            }
        });

        type2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gerer_color(type2,type1,type3);
                gerer_visibility(stress2,stress1,stress3);
            }
        });

        type3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gerer_color(type3,type2,type1);
                gerer_visibility(stress3,stress2,stress1);
            }
        });

        methode();
    }

    //remplir les 2 liste + edit
    void methode(){
        String one="-",two="-",three="-";
        int i; //pour garder l'incice du dernier element

        for(i = 0;i<a.length-1;i++){
            one += " "+a[i]+"\n- ";
        }
        one = " "+one+a[i];
        aigu.setText(one);

        for(i = 0;i<c.length-1;i++){
            two += " "+c[i]+"\n- ";
        }
        two = " "+two+c[i];
        chronic.setText(two);

        for(i = 0;i<b.length-1;i++){
            three += " "+b[i]+"\n- ";
        }
        three += " "+b[i];
        burnOut.setText(three);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void gerer_color(Button a, Button b, Button c) {
        a.setBackground(getDrawable(color1));
        b.setBackground(getDrawable(color2));
        c.setBackground(getDrawable(color2));
    }

    private void gerer_visibility(LinearLayout a, LinearLayout b, LinearLayout c) {
        a.setVisibility(View.VISIBLE);
        b.setVisibility(View.GONE);
        c.setVisibility(View.GONE);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void prepare(Button a, Button b, Button c) {
        a.setBackground(getDrawable(color2));
        b.setBackground(getDrawable(color2));
        c.setBackground(getDrawable(color2));
        gerer_color(type2,type1,type3);
        gerer_visibility(stress2,stress1,stress3);
    }
}