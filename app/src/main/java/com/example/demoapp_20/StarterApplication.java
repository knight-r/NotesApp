package com.example.demoapp_20;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class StarterApplication extends Application {
    public void OnCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        // Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId( "myappID")
                .clientKey("kT6jmINOhOaW")
                .server("http://13.233.100.141/parse/")
                .build()
        );
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL,true);
    }
}