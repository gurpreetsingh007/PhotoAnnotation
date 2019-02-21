package com.example.photoannotation;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.util.Log;
import java.io.File;
import android.os.Environment;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.graphics.Canvas;
import android.graphics.Paint;


public class MainActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    private ImageView imageView;
    private VelocityTracker mVelocityTracker = null;
    private static final String DEBUG_TAG = "Velocity";


    String currentPhotoPath;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageview);
    }

    public void OpenCamera(View view){
        Intent takepic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // camera on
        if(takepic.resolveActivity(getPackageManager())!=null){
            startActivityForResult(takepic,REQUEST_IMAGE_CAPTURE);  // start camera to capture image
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) { //get the result of activity set it to your screen after taking a picture
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
        {
            Bitmap photo = (Bitmap)data.getExtras().get("data");
            imageView.setImageBitmap(photo);       // set the image





//            PdfDocument pdfDocument = new PdfDocument();  //convert image to pdf
//            PdfDocument.PageInfo pi = new PdfDocument.PageInfo.Builder(bitmap.getWidth(),bitmap.getHeight(),1).create(); // with corordinates create the page
//            PdfDocument.Page page = pdfDocument.startPage(pi); //link the page to pdf document
//
//            Canvas canvas = page.getCanvas();  // canvas to draw bitmap to pdf
//            Paint paint = new Paint();
//            paint.setColor(Color.parseColor("#FFFFFF"));  // get white color
//            canvas.drawPaint(paint);
//
//
//            bitmap = Bitmap.createScaledBitmap(bitmap,bitmap.getWidth(),bitmap.getHeight(),true);
//            paint.setColor(Color.BLUE);
//            canvas.drawBitmap(bitmap,0,0,null);
//
//            pdfDocument.finishPage(page);



        }
    }

//    private void dispatchTakePictureIntent(){
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // Ensure that there's a camera activity to handle the intent
//        if(takePictureIntent.resolveActivity(getPackageManager())!=null){
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (IOException ex){
//
//            }
//            if(photoFile != null){
//                Uri photoURI = FileProvider.getUriForFile(this,
//                        "com.example.android.fileprovider",photoFile);
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
//                startActivityForResult(takePictureIntent,REQUEST_TAKE_PHOTO);
//            }
//        }
//    }
//
//
//    private File createImageFile() throws IOException {
//        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile(
//                imageFileName, ".jpg",storageDir
//        );
//        // Save a file: path for use with ACTION_VIEW intents
//        currentPhotoPath = image.getAbsolutePath();
//        return image;
//    }






//    public boolean onTouchEvent(MotionEvent event) {
//        int index = event.getActionIndex();
//        int action = event.getActionMasked();
//        int pointerId = event.getPointerId(index);
//
//        switch(action) {
//            case MotionEvent.ACTION_DOWN:
//                if(mVelocityTracker == null) {
//                    // Retrieve a new VelocityTracker object to watch the
//                    // velocity of a motion.
//                    mVelocityTracker = VelocityTracker.obtain();
//                }
//                else {
//                    // Reset the velocity tracker back to its initial state.
//                    mVelocityTracker.clear();
//                }
//                // Add a user's movement to the tracker.
//                mVelocityTracker.addMovement(event);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                mVelocityTracker.addMovement(event);
//                // When you want to determine the velocity, call
//                // computeCurrentVelocity(). Then call getXVelocity()
//                // and getYVelocity() to retrieve the velocity for each pointer ID.
//                mVelocityTracker.computeCurrentVelocity(1000);
//                // Log velocity of pixels per second
//                // Best practice to use VelocityTrackerCompat where possible.
//                Log.d("", "X velocity: " + mVelocityTracker.getXVelocity(pointerId));
//                Log.d("", "Y velocity: " + mVelocityTracker.getYVelocity(pointerId));
//                break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//                // Return a VelocityTracker object back to be re-used by others.
//                mVelocityTracker.recycle();
//                break;
//        }
//        return true;
//    }
//    private File createImageFile() throws IOException{
//        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//        );
//
//        // Save a file: path for use with ACTION_VIEW intents
//        currentPhotoPath = image.getAbsolutePath();
//        return image;
//    }
}
