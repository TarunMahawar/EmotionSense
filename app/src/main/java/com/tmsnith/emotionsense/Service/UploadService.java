package com.tmsnith.emotionsense.Service;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.View;
import android.widget.Toast;


import com.cloudinary.Cloudinary;
import com.cloudinary.Util;
import com.cloudinary.android.Utils;
import com.cloudinary.utils.ObjectUtils;
import com.google.gson.Gson;
import com.tmsnith.emotionsense.RequestModels.ImageUrlModel;
import com.tmsnith.emotionsense.Resoponse.SingleFaceImageResponse;
import com.tmsnith.emotionsense.Utils.ApiInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;


public class UploadService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    private static final String UPLOAD_SERVICE = "Upload";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String URL_IMAGE = "imageUrl";
    private static final String UPLOADING_START="start";
    private static final  String UPLOADING_FINISH="finish";
    private static final String UPLOADING_ERROR="error";
    private static final String REGISTER_ROLL_NO="rollNoRegister";
    private static  final String ROLL_NO="rollNo";
    private static final  String WORK="work";
    public String UrlToSend = "";
    final String API = "587f02304e5442a78a054fb4e36ba173";
    final String Content_type = "application/json";
    private ArrayList<SingleFaceImageResponse> singleImageResponse;
    private SingleFaceImageResponse singleFaceImageResponse;


public UploadService(){
    super("UploadService");
}
    public UploadService(String name) {
        super(name);

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            if (intent.hasExtra(UPLOAD_SERVICE)) {
                String imageUrl = "";
                if (intent.hasExtra(URL_IMAGE)) {
                    imageUrl = intent.getStringExtra(URL_IMAGE);
                    Cloudinary cloudinary = new Cloudinary(Utils.cloudinaryUrlFromContext(getApplicationContext()));
                    try {
                        Intent i = new Intent(UPLOADING_START);
                        i.putExtra(WORK,"Image");
                        sendBroadcast(i);
                        Map map = cloudinary.uploader().upload(imageUrl.trim(),ObjectUtils.emptyMap());
                        Log.d("image", (String) map.get("url"));
                        Toast.makeText(getApplicationContext(), "final url", Toast.LENGTH_SHORT).show();
                        UrlToSend = (String) map.get("url");

                        if(UrlToSend.equals(""))
                        {
                            return;
                        }
                        Toast.makeText(getApplicationContext(), "retrofit is calling ", Toast.LENGTH_SHORT).show();
                        retrofit();

                    } catch (Exception e) {
                        e.printStackTrace();
                        Intent i = new Intent();
                        i.setAction(UPLOADING_ERROR);
                        i.putExtra(WORK, "Image");
                        sendBroadcast(i);
                    }
                }
                else {
                    Intent i = new Intent(UPLOADING_START);
                    i.putExtra(WORK,"Image");
                    sendBroadcast(i);
                }

            }
        }
    }

    public void retrofit(){

        ApiInterface apiservice= com.tmsnith.emotionsense.Utils.Util.getRetrofitService();

        singleFaceImageResponse = new SingleFaceImageResponse();
        singleImageResponse = new ArrayList<SingleFaceImageResponse>();
        ImageUrlModel request = new ImageUrlModel();
        request.setUrl(UrlToSend);
        Toast.makeText(getApplicationContext(), "" , Toast.LENGTH_SHORT).show();
//        Call<DocumentModel> t =
        Call<ArrayList<SingleFaceImageResponse>> call=apiservice.sendImageUrl(API, Content_type, request );


        Toast.makeText(getApplicationContext(), "" , Toast.LENGTH_SHORT).show();

        call.enqueue(new Callback<ArrayList<SingleFaceImageResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<SingleFaceImageResponse>> call, Response<ArrayList<SingleFaceImageResponse>> response) {
//                bar.setVisibility(View.GONE);

                ArrayList<SingleFaceImageResponse> model=response.body();
                int status=response.code();
                Log.v("hack",model +"");
                Toast.makeText(getApplicationContext(),""+model,Toast.LENGTH_SHORT).show();

                if(model!=null && response.isSuccess()){
//                    recyclerView.setVisibility(View.VISIBLE);

//                    list=model.getRestaurants();
//                    adapter.refresh(list);

                    Toast.makeText(getApplicationContext(),"Success\n"+new Gson().toJson(response),Toast.LENGTH_LONG).show();
                    Log.v("hack","Success\n"+model);

                }else{
                    Toast.makeText(getApplicationContext(),"Some error occurred!!1",Toast.LENGTH_SHORT).show();
                    Log.v("hack","Some error occurred!!1");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<SingleFaceImageResponse>> call, Throwable t) {
//                bar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),"Some error occurred!!2",Toast.LENGTH_SHORT).show();
                Log.v("hack","Some error occurred!!2");
            }
        });

    }

}
