package com.subayu.comarudin.lbb_gis;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

public class Upload extends AppCompatActivity implements View.OnClickListener{
    ImageView im;
    Button bt1,bt2;
    public static final String READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE";
    public static final int PERMISSION_CODE = 42042;
    private final static int REQUEST_CODE = 42;
    public static final String UPLOAD_URL = "http://smkmuhlmg.000webhostapp.com/upload/upload.php";
    String alamatfile,nama;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        im = (ImageView)findViewById(R.id.img);
        bt1 = (Button)findViewById(R.id.button1);
        bt2 = (Button)findViewById(R.id.button2);

        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);

        nama = getIntent().getStringExtra("nama");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button1:
                pickFile();
                break;
            case R.id.button2:
                uploadMultipart();
                break;
        }
    }

    void launchPicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        try {
            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            //alert user that file manager not working
            Toast.makeText(this, "Error Ambil File", Toast.LENGTH_SHORT).show();
        }
    }
    public void pickFile() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE}, PERMISSION_CODE);
        } else {
            launchPicker();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launchPicker();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        switch (requestCode) {
            case REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    String PathHolder = data.getData().getPath();
                    Uri uri = data.getData();
                    alamatfile = PathHolder;
                    Glide.with(getApplicationContext()).load(uri).placeholder(R.drawable.upload).error(R.drawable.error).into(im);

                    Toast.makeText(Upload.this, uri.toString() + PathHolder, Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(Upload.this, "Error Ambil File", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
    public void uploadMultipart() {
        //getting name for the pdf


        //getting the actual path of the pdf
        String path = alamatfile;

        if (path == null) {

            Toast.makeText(this, "Mohon Cari BErkas Anda Dulu", Toast.LENGTH_LONG).show();
        } else {
            //Uploading code
            try {


                //Creating a multi part request
                new MultipartUploadRequest(this, UPLOAD_URL)
                        .addFileToUpload(path, "file") //Adding file
                        .setMethod("POST")
                        .addParameter("nama", nama)
                        .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(2)
                        .startUpload(); //Starting the upload

            } catch (Exception exc) {
                Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
