package com.example.mediaapprove.ui.Insure;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mediaapprove.R;

public class MBCFragment extends Fragment {

    private com.example.mediaapprove.ui.Insure.MBCViewModel MBCViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MBCViewModel =
                new ViewModelProvider(this).get(com.example.mediaapprove.ui.Insure.MBCViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mbc, container, false);
        MBCViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }
}