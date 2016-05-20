package com.example.gf.giftfriend;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;

import com.facebook.Profile;

import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    private CallbackManager mCallbackManager;
    private AccessToken accessToken;
    private TextView mTextDetail;
    private LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //宣告callback Manager
        mCallbackManager = CallbackManager.Factory.create();
        //找到button
        loginButton = (LoginButton) findViewById(R.id.login_button);
        mTextDetail = (TextView) findViewById(R.id.mTextDetail);


        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {



                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Log.d("123",Profile.getCurrentProfile().getName());
                        mTextDetail.setText(Profile.getCurrentProfile().getName());
                        Log.d("onSuccess",loginResult.getAccessToken().getToken().toString());
                        Intent it = new Intent(MainActivity.this,FinishLogin.class);
                        startActivity(it);
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Log.d("onCancel","onCancel");
                    }

                    @Override
                    public void onError(FacebookException e) {
                        // App code
                        e.printStackTrace();
                    }
                });


        //Button
        Button mBtnSignIn = (Button) findViewById(R.id.btnSignIn);
        Button mBtnSignUp = (Button) findViewById(R.id.btnSignUp);

        mBtnSignUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(MainActivity.this, MemberSignUp.class);
                startActivity(signup);
            }
        });
        mBtnSignIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signin = new Intent(MainActivity.this, FinishLogin.class);
                startActivity(signin);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

}


