package com.example.healtyapp.ui.activityphysic;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ActivityphysicViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ActivityphysicViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}