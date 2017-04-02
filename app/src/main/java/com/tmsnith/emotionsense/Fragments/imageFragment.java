package com.tmsnith.emotionsense.Fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
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
import com.tmsnith.emotionsense.Utils.Util;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.provider.ContactsContract.CommonDataKinds.Organization.TITLE;
import static android.provider.MediaStore.Video.VideoColumns.DESCRIPTION;
import static android.support.v7.appcompat.R.id.add;

/**
 * A simple {@link Fragment} subclass.
 */
public class imageFragment extends Fragment {


    private Button upload_button;
    public imageFragment() {
        // Required empty public constructor
    }

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final String TAG = "Upload News Feed";
    private Button select_image, upload_image;
    private ImageView image_to_upload;
    private static final String UPLOAD_SERVICE="Upload";
    private static final String  URL_IMAGE="imageUrl";
    private static final  String WORK="work";
    private String imageUrl="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_image, container, false);

        select_image = (Button) v.findViewById(R.id.select_image);
        image_to_upload = (ImageView) v.findViewById(R.id.image_to_upload);
        upload_image = (Button) v.findViewById(R.id.upload_image);
        select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createChooser();
            }
        });

        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*StringBuilder imageUrl = new StringBuilder();
                            for(int i=0;i<add.imageUrl.size();i++)
                                imageUrl.append(add.imageUrl.get(i)+" ");
                            Log.d("image",imageUrl.toString());*/
                            Intent i=new Intent(getContext(), UploadService.class);
                            i.putExtra(UPLOAD_SERVICE,true);
                            if(!imageUrl.toString().isEmpty())
                                i.putExtra(URL_IMAGE,imageUrl.toString());
                            getActivity().startService(i);

                            Log.d(TAG," "+imageUrl);

            }
        });
        return v;
    }

    public static imageFragment get()
    {
        return  new imageFragment();
    }


    private void createChooser() {
        if(ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){

            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
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
            imageUrl = imgDecodableString;
            Log.v("img url"," "+ imgDecodableString);
        }
    }


}
