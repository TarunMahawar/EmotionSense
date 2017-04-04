package com.tmsnith.emotionsense.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tmsnith.emotionsense.R;
import com.tmsnith.emotionsense.RequestModels.DocumentModel;
import com.tmsnith.emotionsense.RequestModels.ImageUrlModel;
import com.tmsnith.emotionsense.RequestModels.SingleDocument;
import com.tmsnith.emotionsense.Resoponse.SingleFaceImageResponse;
import com.tmsnith.emotionsense.Resoponse.TextResponse;
import com.tmsnith.emotionsense.Service.UploadService;
import com.tmsnith.emotionsense.Utils.ApiInterface;
import com.tmsnith.emotionsense.Utils.SharedPref;
import com.tmsnith.emotionsense.Utils.Util;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.provider.ContactsContract.CommonDataKinds.Organization.TITLE;
import static android.provider.MediaStore.Video.VideoColumns.DESCRIPTION;
import static android.support.v7.appcompat.R.id.add;
import static android.support.v7.appcompat.R.id.contentPanel;

/**
 * A simple {@link Fragment} subclass.
 */
public class imageFragment extends Fragment {


    private Button upload_button;

    public imageFragment() {
        // Required empty public constructor
    }

    SharedPref sharedPref;
    private SharedPreferences.Editor editor;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final String TAG = "Upload News Feed";
    private Button select_image, upload_image;
    private ImageView image_to_upload;
    private static final String UPLOAD_SERVICE = "Upload";
    private static final String URL_IMAGE = "imageUrl";
    private static final String URL_TO_SEND = "UrlToSend";
    private static final String WORK = "work";
    private String imageUrl = "";
    public String UrlToSend = "";
    final String API = "587f02304e5442a78a054fb4e36ba173";
    final String Content_type = "application/json";
    private ArrayList<SingleFaceImageResponse> singleImageResponse;
    private SingleFaceImageResponse singleFaceImageResponse;
    private ProgressBar pb_anger, pb_contempt, pb_disgust, pb_fear, pb_happiness, pb_neutral, pb_sadness, pb_surprise;
    private double anger, contempt, disgust, fear, happiness, neutral, sadness, surprise;
    private TextView per_anger, per_contempt, per_disgust, per_fear, per_happiness, per_neutral, per_sadness, per_surprise;
    ProgressDialog progress;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_image, container, false);

        select_image = (Button) v.findViewById(R.id.select_image);
        image_to_upload = (ImageView) v.findViewById(R.id.image_to_upload);
        upload_image = (Button) v.findViewById(R.id.upload_image);
        pb_happiness = (ProgressBar) v.findViewById(R.id.HappiPercent);
        pb_neutral = (ProgressBar) v.findViewById(R.id.neutralPercent);
        pb_anger = (ProgressBar) v.findViewById(R.id.angerPercent);
        pb_contempt = (ProgressBar) v.findViewById(R.id.contemptPercent);
        pb_disgust = (ProgressBar) v.findViewById(R.id.disgustPercent);
        pb_fear = (ProgressBar) v.findViewById(R.id.fearPercent);
        pb_sadness = (ProgressBar) v.findViewById(R.id.sadPercent);
        pb_surprise = (ProgressBar) v.findViewById(R.id.surprisePercent);

        per_happiness = (TextView) v.findViewById(R.id.happi);
        per_neutral = (TextView) v.findViewById(R.id.neutral);
        per_anger = (TextView) v.findViewById(R.id.anger);
        per_contempt = (TextView) v.findViewById(R.id.contempt);
        per_disgust = (TextView) v.findViewById(R.id.disgust);
        per_fear = (TextView) v.findViewById(R.id.fear);
        per_sadness = (TextView) v.findViewById(R.id.sad);
        per_surprise = (TextView) v.findViewById(R.id.surprise);
        image_to_upload = (ImageView) v.findViewById(R.id.image_to_upload);
        select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createChooser();
            }
        });
        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), UploadService.class);
                i.putExtra(UPLOAD_SERVICE, true);
                if (!imageUrl.toString().equals(""))
                    i.putExtra(URL_IMAGE, imageUrl.toString());
                getActivity().startService(i);
                sharedPref = new SharedPref(getContext());
                UrlToSend = sharedPref.getUrlToSend();
                Log.d("UrlTOSend", " " + UrlToSend);
                if (UrlToSend.equals("")) {
                    return;
                }
                //Toast.makeText(getContext(), "retrofit is calling ", Toast.LENGTH_SHORT).show();
                retrofit();


            }
        });
        return v;
    }

    public static imageFragment get() {
        return new imageFragment();
    }


    private void createChooser() {
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 121);
            }

            return;

        }

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "CHOOSE PHOTO"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor c = getActivity().getContentResolver().query(filePath, filePathColumn, null, null, null);
            c.moveToFirst();
            String imgDecodableString = c.getString(c.getColumnIndex(filePathColumn[0]));
            c.close();
            image_to_upload.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
            imageUrl = imgDecodableString;
            Log.v("img url", " " + imgDecodableString);
        }
    }

    public void retrofit() {

        ApiInterface apiservice = com.tmsnith.emotionsense.Utils.Util.getRetrofitService();

        progress = new ProgressDialog(getActivity());
        progress.setTitle("wait ...");
        progress.setMessage("We are processing Your Image.");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        progress.show();

        singleFaceImageResponse = new SingleFaceImageResponse();
        singleImageResponse = new ArrayList<SingleFaceImageResponse>();
        ImageUrlModel request = new ImageUrlModel();
        request.setUrl(UrlToSend);
        Call<ArrayList<SingleFaceImageResponse>> call = apiservice.sendImageUrl(API, Content_type, request);

        call.enqueue(new Callback<ArrayList<SingleFaceImageResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<SingleFaceImageResponse>> call, Response<ArrayList<SingleFaceImageResponse>> response) {
                ArrayList<SingleFaceImageResponse> model = response.body();
                int status = response.code();
                Log.v("hack", model + "");
                //Toast.makeText(getContext(),""+model,Toast.LENGTH_SHORT).show();

                if (model != null && response.isSuccess()) {

                    //Toast.makeText(getContext(),"Success\n"+new Gson().toJson(response),Toast.LENGTH_LONG).show();
                    Log.v("hack", "Success\n" + model);
                    if (model.size() != 0) {
                        anger = model.get(0).getScores().getAnger() * 100;
                        contempt = model.get(0).getScores().getContempt() * 100;
                        disgust = model.get(0).getScores().getDisgust() * 100;
                        fear = model.get(0).getScores().getFear() * 100;
                        happiness = model.get(0).getScores().getHappiness() * 100;
                        neutral = model.get(0).getScores().getNeutral() * 100;
                        sadness = model.get(0).getScores().getSadness() * 100;
                        surprise = model.get(0).getScores().getSurprise() * 100;
                        progress.dismiss();

                        per_anger.setText(String.format("%.2f", anger) + "%");
                        per_contempt.setText(String.format("%.2f", contempt) + "%");
                        per_disgust.setText(String.format("%.2f", disgust) + "%");
                        per_fear.setText(String.format("%.2f", fear) + "%");
                        per_happiness.setText(String.format("%.2f", happiness) + "%");
                        per_neutral.setText(String.format("%.2f", neutral) + "%");
                        per_sadness.setText(String.format("%.2f", sadness) + "%");
                        per_surprise.setText(String.format("%.2f", surprise) + "%");

                        pb_anger.setProgress((int) anger);
                        pb_contempt.setProgress((int) contempt);
                        pb_disgust.setProgress((int) disgust);
                        pb_fear.setProgress((int) fear);
                        pb_happiness.setProgress((int) happiness);
                        pb_neutral.setProgress((int) neutral);
                        pb_sadness.setProgress((int) sadness);
                        pb_surprise.setProgress((int) surprise);
                    } else {
                        progress.dismiss();
                        Toast.makeText(getContext(), "Please Select the Image", Toast.LENGTH_LONG).show();
                        Log.v("hack", "Please Select the Image");
                    }

                } else {
                    progress.dismiss();
                    Toast.makeText(getContext(), "Please Select the Image", Toast.LENGTH_LONG).show();
                    Log.v("hack", "Please Select the Image");

                }
            }

            @Override
            public void onFailure(Call<ArrayList<SingleFaceImageResponse>> call, Throwable t) {
//                bar.setVisibility(View.GONE);
                progress.dismiss();
                Toast.makeText(getContext(), "Some error occurred!!2", Toast.LENGTH_SHORT).show();
                Log.v("hack", "Some error occurred!!2");
            }
        });

    }


}
