package com.example.photoannotation;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import java.io.File;
import android.os.Environment;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.SeekBar;


public class MainActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    DrawingView drawingView;
    SeekBar seekBar;
    ImageView imageView;
    Bitmap bitmap;
    Canvas canvas;
    Paint paint;
    Button camera;
    Uri uriimage;
    String currentPhotoPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        imageView = findViewById(R.id.imageView);
        drawingView = findViewById(R.id.canvas);
        camera = findViewById(R.id.camera);

        drawingView.setOnTouchListener(new TouchCanvas());

        seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                drawingView.setCurrentWidth(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    public void setColor(View view) {
        ColorDrawable buttonColor = (ColorDrawable) view.getBackground();
        drawingView.setCurrentColor(buttonColor.getColor());
        if (view.getTag() != null && view.getTag().equals("eraser")) {
            drawingView.setCurrentWidth(seekBar.getProgress() * 4);
        } else {
            drawingView.setCurrentWidth(seekBar.getProgress());
        }
    }

    public void deleteDrawing(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to erase everything?")
                .setTitle("Delete Drawing")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        drawingView.erase();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void undo(View view){
        drawingView.undo();
    }
    public void eraser(View view) {
        drawingView.erasermode();
    }

    protected void onActivityResult(int requestCode, int resultCode,Intent data) { //get the result of activity set it to your screen after taking a picture
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_IMAGE_CAPTURE) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uriimage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bitmap tmp = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(),Bitmap.Config.ARGB_8888);
            canvas = new Canvas(tmp);
            canvas.drawBitmap(bitmap,0,0,null);

            imageView.setImageDrawable(new BitmapDrawable(getResources(),tmp));


        }
    }

    public void dispatchTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                uriimage = photoURI;
            }
        }

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }




}