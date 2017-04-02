package com.tmsnith.emotionsense.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tmsnith.emotionsense.R;
import com.tmsnith.emotionsense.RequestModels.DocumentModel;
import com.tmsnith.emotionsense.RequestModels.SingleDocument;
import com.tmsnith.emotionsense.Resoponse.TextResponse;
import com.tmsnith.emotionsense.Utils.ApiInterface;
import com.tmsnith.emotionsense.Utils.Util;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class textFragment extends Fragment {


    EditText text;
    Button submit;

    String inputText;

    final String API = "b868ca80b2184f46a3ed464503ea4f2c";
    final String Content_type = "application/json";

    public textFragment() {
        // Required empty public constructor
    }

        DocumentModel request;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v =  inflater.inflate(R.layout.fragment_text, container, false);
        text = (EditText) v.findViewById(R.id.text);
        submit = (Button) v.findViewById(R.id.submit1);
        Log.v("hack", "sucessful1");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             inputText = "" + text.getText().toString();
                if(inputText.equals(""))
                {
                    return;
                }
                retrofit();
            }
        });

//        return null;
        return v;
    }


    public void retrofit(){

        ApiInterface apiservice= Util.getRetrofitService();

        request = new DocumentModel();

        SingleDocument d= new SingleDocument("id","string");

        ArrayList<SingleDocument> list=request.getDocument();
        list.add(d);

        request.setDocument(list);


        Toast.makeText(getActivity(), "" , Toast.LENGTH_SHORT).show();
//        Call<DocumentModel> t =
        Call<TextResponse> call=apiservice.sendText(API, Content_type, request );


        Toast.makeText(getActivity(), "" , Toast.LENGTH_SHORT).show();

        call.enqueue(new Callback<TextResponse>() {
            @Override
            public void onResponse(Call<TextResponse> call, Response<TextResponse> response) {
//                bar.setVisibility(View.GONE);

                TextResponse model=response.body();
                int status=response.code();
                Log.v("hack",model +"");
                Toast.makeText(getActivity(),""+model,Toast.LENGTH_SHORT).show();

                if(model!=null && response.isSuccess()){
//                    recyclerView.setVisibility(View.VISIBLE);

//                    list=model.getRestaurants();
//                    adapter.refresh(list);

                    Toast.makeText(getActivity(),"Success\n"+new Gson().toJson(response),Toast.LENGTH_LONG).show();
                    Log.v("hack","Success\n"+model);

                }else{
                    Toast.makeText(getActivity(),"Some error occurred!!1",Toast.LENGTH_SHORT).show();
                    Log.v("hack","Some error occurred!!1");
                }
            }

            @Override
            public void onFailure(Call<TextResponse> call, Throwable t) {
//                bar.setVisibility(View.GONE);
                Toast.makeText(getActivity(),"Some error occurred!!2",Toast.LENGTH_SHORT).show();
                Log.v("hack","Some error occurred!!2");
            }
        });

}

    public static textFragment get()
    {
        return  new textFragment();
    }
}
