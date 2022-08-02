package com.example.mediaapprove.ui.MNP;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MNPViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MNPViewModel() {
        mText = new MutableLiveData<>();

    }

    public LiveData<String> getText() {
        return mText;
    }
}