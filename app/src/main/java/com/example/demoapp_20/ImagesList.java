package com.example.demoapp_20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;

import android.widget.AdapterView;

import android.widget.GridView;
import android.widget.Toast;

public class ImagesList extends AppCompatActivity {
    GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_list);
    gridView=(GridView)findViewById(R.id.gridView);
    gridView.setAdapter(new ImageAdapter(this));
    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
           Intent intent = new Intent(getApplicationContext(), FullView.class);
           intent.putExtra("id",position);
           startActivity(intent);
        }
    });
       gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
               Toast.makeText(ImagesList.this,"Sorry! No Further Operation ):",Toast.LENGTH_SHORT).show();
               return true;
           }
       });
    }


}