package com.example.mediaapprove.ui.CCI;

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

public class CCIFragment extends Fragment {

    private com.example.mediaapprove.ui.CCI.CCIViewModel MBCViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MBCViewModel =
                new ViewModelProvider(this).get(com.example.mediaapprove.ui.CCI.CCIViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cci, container, false);
        MBCViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }
}