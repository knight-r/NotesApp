package com.example.demoapp_20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class FullView extends AppCompatActivity {
  ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        getSupportActionBar().setTitle("Full View");
        getSupportActionBar().hide();
        getSupportActionBar().setTitle("Full view of image");
        Intent i=getIntent();
        int itemId=i.getExtras().getInt("id");
        ImageAdapter imageAdapter= new ImageAdapter(this);
        imageView = (ImageView) findViewById(R.id.imageview);
        imageView.setImageResource(imageAdapter.imageArray[itemId]);

    }

}