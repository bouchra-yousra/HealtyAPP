package com.example.healtyapp.ui.activitymorale;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.healtyapp.R;

import java.util.ArrayList;

public class MainReduceStress extends AppCompatActivity {

    //edit: added burnout attribute (textViews/string[]..ect)
    TextView click1,click2,aigu,chronic,burnOut,click3;
    String[] a;
    String[] c;
    String[] b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_reduce_stress);

        click1 = findViewById(R.id.click1);
        click2= findViewById(R.id.click2);
        click3= findViewById(R.id.click3);

        aigu = findViewById(R.id.aigu);
        chronic = findViewById(R.id.chronic);
        burnOut = findViewById(R.id.burnOut);

        a = getResources().getStringArray(R.array.aigu);
        c = getResources().getStringArray(R.array.chronic);
        b = getResources().getStringArray(R.array.burnOut);

        methode();
    }

    //remplir les 2 liste + edit
    void methode(){
        String one="-",two="-",three="-";
        int i; //pour garder l'incice du dernier element
        for(i = 0;i<a.length-1;i++){
            one = " "+one+a[i]+"\n-";
        }
        one = " "+one+a[i];
        aigu.setText(one);

        for(i = 0;i<c.length-1;i++){
            two = " "+two+c[i]+"\n-";
        }
        two = " "+two+c[i];
        chronic.setText(two);

        for(i = 0;i<b.length-1;i++){
            three += " "+b[i]+"\n- ";
        }
        three += " "+b[i];
        burnOut.setText(three);
    }
}