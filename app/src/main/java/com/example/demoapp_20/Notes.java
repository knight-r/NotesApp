package com.example.demoapp_20;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class Notes extends AppCompatActivity {
   static ArrayList<String > notes = new ArrayList<>();
    static ArrayAdapter<String> arrayAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
     MenuInflater menuInflater= getMenuInflater();
     menuInflater.inflate(R.menu.new_menu_note,menu);
     return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
         super.onOptionsItemSelected(item);
         if(item.getGroupId() == R.id.add_note){
             Intent intent = new Intent(getApplicationContext(),EditNotes.class);
             startActivity(intent);
             return true;
         }
         return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        getSupportActionBar().setTitle("Your Notes List");
        ListView listView = (ListView)findViewById(R.id.listView);
        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("com.example.demoapp_20", Context.MODE_PRIVATE);
        HashSet<String> set= (HashSet<String>)sharedPreferences.getStringSet("notes", null);
        if(set == null){
            notes.add("New note");
        }else{
            notes= new ArrayList(set);

        }
        notes.add("New note");
        arrayAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1, notes);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int i, long id) {
                Intent intent = new Intent(getApplicationContext(), EditNotes.class);
                intent.putExtra("noteId", i);
                startActivity(intent);

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long id) {
               new AlertDialog.Builder(Notes.this)
                       .setIcon(android.R.drawable.ic_dialog_alert)
                       .setTitle("Are you sure?")
                       .setMessage("Do you want to delete this note?")
                       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               notes.remove(i);
                               arrayAdapter.notifyDataSetChanged();
                               SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("com.example.demoapp_20", Context.MODE_PRIVATE);
                               HashSet<String> set= new HashSet<String>(Notes.notes);
                               sharedPreferences.edit().putStringSet("notes", set).apply();
                           }
                       }).setNegativeButton("No",null).show();

                return true;
            }
        });

    }
}