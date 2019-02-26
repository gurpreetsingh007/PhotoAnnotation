package com.example.photoannotation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;

public class Editmode extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView imageView;
    PaintView paintView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        imageView = findViewById(R.id.imageView5);
        Bundle extras = getIntent().getExtras();
        byte[] bytes = extras.getByteArray("picture");
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        imageView.setImageBitmap(bmp);
        setContentView(R.layout.activity_second);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
