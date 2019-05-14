package com.example.santi.tercerparcial;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    String imageFilePath;
    Context context;
    private static final int REQUEST_CAPTURE_IMAGE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button)findViewById(R.id.button1);
        imageView = (ImageView)findViewById(R.id.ImageView1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////
////                startActivityForResult(intent,0);
                Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(pictureIntent.resolveActivity(getPackageManager()) != null){
                    //Create a file to store the image
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File

                    }
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile (getApplicationContext(),"com.example.santi.tercerparcial.provider", photoFile);
                        pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(pictureIntent, REQUEST_CAPTURE_IMAGE);
                    }
                }

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
//
//        imageView.setImageBitmap(bitmap);
//        Save save = new Save();
//        save.SaveImage(this,bitmap);
        if (requestCode == REQUEST_CAPTURE_IMAGE &&
                resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(imageBitmap);
            }
        }



    }
    private void openCameraIntent() {
        Intent pictureIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        if(pictureIntent.resolveActivity(getPackageManager()) != null){
            //Create a file to store the image
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,                                                                                                    "com.example.android.provider", photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        photoURI);
                startActivityForResult(pictureIntent,
                        REQUEST_CAPTURE_IMAGE);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        imageFilePath = image.getAbsolutePath();
        return image;
    }


}
