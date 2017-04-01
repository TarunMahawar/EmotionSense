package com.tmsnith.emotionsense.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tmsnith.emotionsense.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class textFragment extends Fragment {


    EditText text;
    Button submit;

    public textFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v =  inflater.inflate(R.layout.fragment_text, container, false);
        text = (EditText) v.findViewById(R.id.text);
        submit = (Button) v.findViewById(R.id.submit1);
        submit.setText("ssss");
        Log.v("successful1", "sucessful1");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Successful", Toast.LENGTH_SHORT).show();
                Log.v("successful", "sucessful");
            }
        });

//        return null;
        return inflater.inflate(R.layout.fragment_text, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }



    public static textFragment get()
    {
        return  new textFragment();
    }
}
