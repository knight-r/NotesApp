package com.example.demoapp_20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class EditNotes extends AppCompatActivity {
    int noteId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_notes);
        getSupportActionBar().setTitle("Edit Your Note");
        EditText editText =(EditText)findViewById(R.id.editText);
        Intent intent = getIntent();
         noteId=intent.getIntExtra("noteId",-1);
        if(noteId != -1){
            editText.setText(Notes.notes.get(noteId));
        }else{
            Notes.notes.add("");
            noteId=Notes.notes.size()-1;
           Notes.arrayAdapter.notifyDataSetChanged();
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                 Notes.notes.set(noteId,String.valueOf(s));
                 Notes.arrayAdapter.notifyDataSetChanged();
                SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("com.example.demoapp_20", Context.MODE_PRIVATE);
                HashSet<String> set= new HashSet<String>(Notes.notes);
                sharedPreferences.edit().putStringSet("notes", set).apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}