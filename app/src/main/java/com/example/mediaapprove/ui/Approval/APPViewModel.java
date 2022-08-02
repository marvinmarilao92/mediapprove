package com.example.mediaapprove.ui.Approval;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class APPViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public APPViewModel() {
        mText = new MutableLiveData<>();

    }

    public LiveData<String> getText() {
        return mText;
    }
}