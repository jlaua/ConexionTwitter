package com.imaginart.conexiontwitter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "nKpYTK5thtOO5pschZzIWNJSQ";
    private static final String TWITTER_SECRET = "C6U5Cwf8bSOBczJApnB0G9Dz0F2uDBpLswe3zAiDSfVgqhDQOL";
    public static final String CONSUMER_KEY = "nKpYTK5thtOO5pschZzIWNJSQ";
    public static final String CONSUMER_SECRET = "C6U5Cwf8bSOBczJApnB0G9Dz0F2uDBpLswe3zAiDSfVgqhDQOL";
    private static final String TAG = "MainActivity";
    private TwitterLoginButton loginButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API calls
                Log.d(TAG,"Login sucess .");
                Log.d(TAG,"User Id  " + result.data.getUserId());
                Log.d(TAG,"User name  " + result.data.getUserName());

                TwitterAuthClient authClient = new TwitterAuthClient();
                authClient.requestEmail(result.data, new Callback<String>() {
                    @Override
                    public void success(Result<String> result) {
                        Log.d(TAG, "User mail: " + result.data);
                    }

                    @Override
                    public void failure(TwitterException e) {
                        Log.e(TAG,"Request email error",e);

                    }
                });
            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
                Log.e(TAG, "Login Error ", exception);
            }
        });

        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
