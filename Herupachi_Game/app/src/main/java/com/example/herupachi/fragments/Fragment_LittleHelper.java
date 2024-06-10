package com.example.herupachi.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.herupachi.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_LittleHelper#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_LittleHelper extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_little_helper_, container, false);
    }
}