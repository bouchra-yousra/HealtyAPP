package com.example.healtyapp.dialogue;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.healtyapp.R;
import com.example.healtyapp.adapter.FiveWeightTrakerAdapter;
import com.example.healtyapp.database_item.WeightTrakerItem;
import com.example.healtyapp.ui.home.HomeFragment;
import com.google.android.material.textfield.TextInputLayout;

public class ExampleDialog extends AppCompatDialogFragment {
    private ExampleDialogListener listener;
    private TextInputLayout weight,ideal;
    EditText a,b;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view =  inflater.inflate(R.layout.dialogue_item_weight_traker, null);
        builder.setView(view)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                      //  Fragment fragment = getActivity().getFragmentManager().findFragmentByTag("HOME_FRAGMENT");
                       // if (listener != null)
                         //   listener.onYesClicked();

                        if (verifier_number(a, 2,150,25) &&
                                verifier_number(b, 2,150,25)) {
                            WeightTrakerItem w = new WeightTrakerItem(Integer.parseInt(a.getText().toString()));
                            WeightTrakerItem.setIdeal_weight(Integer.parseInt(b.getText().toString()));

                            HomeFragment.addWeightInlist(w,HomeFragment.five_weight_list);
                            update_five_weight_list();

                        }

                    }
                });

        weight = view.findViewById(R.id.weight);
        ideal = view.findViewById(R.id.ideal_weight);

        a = weight.getEditText();
        b = ideal.getEditText();

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
    boolean verifier_number (EditText t, int max_lenght,int max,int min) {
        if (!t.getText().toString().isEmpty()
                && t.getText().toString().length() <= max_lenght
                && Integer.parseInt(t.getText().toString()) <= max
                && Integer.parseInt(t.getText().toString()) >= min)
            return true;
        return false;
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