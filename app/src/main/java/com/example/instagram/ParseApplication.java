package com.example.instagram;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // set applicationId, and server server based on the values in the Heroku settings.
        // clientKey is not needed unless explicitly configured
        // any network interceptors must be added with the Configuration Builder given this syntax
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.application_id)) // should correspond to APP_ID env variable
                .clientKey(getString(R.string.master_key))  // set explicitly unless clientKey is explicitly configured on Parse server
                .server(getString(R.string.server)).build());

    }
}
