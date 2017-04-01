package com.tmsnith.emotionsense.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tmsnith.emotionsense.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class textFragment extends Fragment {


    public textFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_text, container, false);
    }

    public static textFragment get()
    {
        return  new textFragment();
    }
}
