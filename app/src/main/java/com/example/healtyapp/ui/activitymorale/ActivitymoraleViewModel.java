package com.example.healtyapp.ui.activitymorale;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ActivitymoraleViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ActivitymoraleViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}