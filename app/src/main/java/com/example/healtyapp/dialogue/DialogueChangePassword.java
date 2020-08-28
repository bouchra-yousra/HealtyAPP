package com.example.healtyapp.dialogue;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.healtyapp.R;
import com.google.android.material.textfield.TextInputLayout;

public class DialogueChangePassword extends AppCompatDialogFragment {

    private TextInputLayout a,b;
    private EditText editTextold,editTextPassword;
    private ExampleDialogListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogue_change_password, null);
        builder.setView(view)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String old_password = editTextold.getText().toString();
                        String password = editTextPassword.getText().toString();
                        listener.applyTexts(old_password, password);
                    }
                });

        a  = view.findViewById(R.id.old_password);
        editTextold= a.getEditText();
        b = view.findViewById(R.id.new_password);
        editTextPassword = b.getEditText();
        return builder.create();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }
    public interface ExampleDialogListener {
        void applyTexts(String username, String password);
    }
}