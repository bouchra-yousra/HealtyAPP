package com.example.healtyapp.dialogue;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.healtyapp.R;
import com.example.healtyapp.database_item.WeightTrakerItem;
import com.example.healtyapp.ui.activitymorale.MainTomate;
import com.example.healtyapp.ui.home.HomeFragment;
import com.google.android.material.textfield.TextInputLayout;

public class ExplainPmodoroDialog extends AppCompatDialogFragment {
    private ExampleDialogListener listener;
    private TextInputLayout task;
    public static String task_title;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view =  inflater.inflate(R.layout.dialogue_item_pomodoro, null);
        builder .setView(view)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("start", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (valideTask (task)) {
                            //MainTomate.name.setText();
                            Intent intent = new Intent(getActivity(),MainTomate.class);
                            intent.putExtra("pomodoro_title",task.getEditText().getText().toString());
                            //MainTomate.UpdateTaskName (task.getEditText().getText().toString());

                            task_title = task.getEditText().getText().toString();

                            if(task_title.endsWith("bouchra"))
                                Toast.makeText(getContext(),"cvrai",Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(getActivity(), MainTomate.class));
                            Toast.makeText(getContext(),task.getEditText().getText().toString(),Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(),"Try again",Toast.LENGTH_SHORT).show();

                        }
                    }
                });

        task = view.findViewById(R.id.task);

        return builder.create();
    }

    private boolean valideTask(TextInputLayout task) {
        if (!task.getEditText().getText().toString().isEmpty())
            return true;
        return false;
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
}