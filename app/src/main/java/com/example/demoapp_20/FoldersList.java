package com.example.demoapp_20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.parse.Parse;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class FoldersList extends AppCompatActivity {
    GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folders_list);
        getSupportActionBar().setTitle("FoldersList");

        String s= ParseUser.getCurrentUser().getUsername();

        TextView tv = findViewById(R.id.textView);
        tv.setText("Hi ! " + s);
        tv.setTypeface(tv.getTypeface(), Typeface.ITALIC);
        tv.setTypeface(tv.getTypeface(), Typeface.BOLD);
        TextView images,documents,written_notes;
        Button logout=(Button)findViewById(R.id.logout);
        Button google_logout=(Button)findViewById(R.id.google_logout);
        images=(TextView)findViewById(R.id.images);
       documents=(TextView)findViewById(R.id.documents);
        written_notes=(TextView)findViewById(R.id.written_notes);
        written_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Notes.class);
                startActivity(i);
            }
        });

        images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),ImagesList.class);
                startActivity(i);
            }
        });
        documents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                int PICKFILE_RESULT_CODE =1;
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("file/*");
                startActivity(intent);
                startActivityForResult(intent,PICKFILE_RESULT_CODE);
                //intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            tv.setText("Hi ! " + personName);
            tv.setTypeface(tv.getTypeface(), Typeface.ITALIC);
            tv.setTypeface(tv.getTypeface(), Typeface.BOLD);
        }
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int y=0;
                Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                    ParseUser.logOut();
                startActivity(intent);
            }
        });
        google_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),MainActivity.class);
              signOut();
                startActivity(intent);
            }
        });

    }
    private void signOut() {

        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    //Toast.makeText(FoldersList.this, "", Toast.LENGTH_SHORT).show();
                       finish();
                    }
                });
    }


}