package com.example.demoapp_20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {
    Boolean signInMode =true;
    TextView login;
    EditText password,username;
    RelativeLayout relativeLayout;
    ImageView logo;
    GoogleSignInClient mGoogleSignInClient;
    private static int RC_SIGN_IN=100;
    public void showFolders(){
        Intent intent= new Intent(getApplicationContext(), FoldersList.class);
        startActivity(intent);
    }
    // Perform the corresponding event when enter key is pressed
    @Override
    public boolean onKey(View v, int i, KeyEvent event) {
        if(i== KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
            SignIn(v);
        }
        return false;
    }
    // check for which mode is active
    @Override
    public void onClick(View v){
        if(v.getId()==R.id.login){
            Button signUp =(Button)findViewById(R.id.signin);
            if(signInMode){
                signInMode = false;
                signUp.setText("LOG IN");
                login.setText(" SIGN UP");
            }else{
                signInMode=true;
                signUp.setText("SIGN UP");
                login.setText("LOG IN");
            }
        }else if(v.getId() == R.id.relative_layout || v.getId() == R.id.logo){
            InputMethodManager inputMethod=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            inputMethod.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }
    }
    public void SignIn(View v){
         username = (EditText)findViewById(R.id.username);
         if(username.getText().toString().matches("") || password.getText().toString().matches("")){
            TextView warning = (TextView)findViewById(R.id.warning);

                warning.setText("x: A username and password are required!");
                warning.setTextColor(Color.parseColor("#FF0000"));
                //warning.animate().alpha(0.0f).setDuration(5000);

          }
         else {
             if(signInMode) {
//                 Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
//                         .applicationId("myappID")
//                         .clientKey("kT6jmINOhOaW")
//                         .server("http://13.233.100.141/parse/")
//                         .build()
//                 );
                 ParseUser user = new ParseUser();
                 user.setUsername(username.getText().toString());
                 user.setPassword(password.getText().toString());
                 user.signUpInBackground(new SignUpCallback() {
                     @Override
                     public void done(ParseException e) {
                         if (e == null) {
                             Log.i("Signup", "successful");
                             showFolders();
                         } else {
                             Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                         }
                     }
                 });
             }else{
                 ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
                     @Override
                     public void done(ParseUser user, ParseException e) {
                         if(user != null){
                             Log.i("LogIn","Login successful");
                             showFolders();

                         }else{
                             Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                         }
                     }
                 });
                 ParseAnalytics.trackAppOpenedInBackground(getIntent());
             }

         }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Welcome to NotesApp");
        password = (EditText)findViewById(R.id.password);
         relativeLayout =(RelativeLayout)findViewById(R.id.relative_layout);
         logo = (ImageView)findViewById(R.id.logo);
        relativeLayout.setOnClickListener(this);
        logo.setOnClickListener(this);
         password.setOnKeyListener( this);
        login =(TextView)findViewById(R.id.login);
        login.setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
         mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("myappID")
                .clientKey("kT6jmINOhOaW")
                .server("http://13.233.100.141/parse/")
                .build()
        );
        if(ParseUser.getCurrentUser() != null || account != null){
            showFolders();
        }
            //ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            if (acct != null) {
                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();
                Toast.makeText(MainActivity.this, "A user has already logged in", Toast.LENGTH_SHORT).show();
            }
            // Signed in successfully, show authenticated UI.

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.

            Log.d("SignIn failed!", e.toString());

        }
    }

}