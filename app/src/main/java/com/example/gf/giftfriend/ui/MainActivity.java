package com.example.gf.giftfriend.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gf.giftfriend.R;
import com.example.gf.giftfriend.util.IntentUtil;
import com.example.gf.giftfriend.util.PrefUtil;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;

import com.facebook.Profile;

import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


public class MainActivity extends AppCompatActivity {

    private CallbackManager mCallbackManager;
    private ImageView profileImgView;
    private LoginButton loginButton;
    private TextView info;
    Button mBtnSignIn , mBtnSignUp;
    EditText username , password;
    int counter = 3;
    private PrefUtil prefUtil;
    private IntentUtil intentUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_main);

        prefUtil = new PrefUtil(this);
        intentUtil = new IntentUtil(this);
        //宣告callback Manager

        //找到button
        loginButton = (LoginButton) findViewById(R.id.login_button);
        profileImgView = (ImageView) findViewById(R.id.profile_img);
        info = (TextView) findViewById(R.id.info);

        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Log.d("123",Profile.getCurrentProfile().getName());
                        Log.d("onSuccess",loginResult.getAccessToken().getToken().toString());

                        String userId = loginResult.getAccessToken().getUserId();
                        String accessToken = loginResult.getAccessToken().getToken();

                        prefUtil.saveAccessToken(accessToken);
                       //Intent it = new Intent(MainActivity.this,FinishLogin.class);
                       // startActivity(it);

                        String profileImgUrl = "https://graph.facebook.com/" + userId + "/picture?type=large";


                        Glide.with(MainActivity.this)
                                .load(profileImgUrl)
                                .into(profileImgView);
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Log.d("onCancel","onCancel");
                        info.setText("Login attempt cancelled.");
                    }

                    @Override
                    public void onError(FacebookException e) {
                        // App code
                        e.printStackTrace();
                        info.setText("Login attempt failed.");
                    }
                });


        // Text
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        //Button
        mBtnSignIn = (Button) findViewById(R.id.btnSignIn);
        mBtnSignUp  = (Button) findViewById(R.id.btnSignUp);


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
                if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
                    //correcct password
                    Toast.makeText(getApplicationContext(), "登入成功",Toast.LENGTH_SHORT).show();
                    Intent signin = new Intent(MainActivity.this, FinishLogin.class);
                    startActivity(signin);

                } else {
                    //wrong password
                    counter--;
                    Toast.makeText(getApplicationContext(), "帳號或密碼錯誤",Toast.LENGTH_SHORT).show();
                    if(counter==0){
                        Toast.makeText(getApplicationContext(), "超過三次登入失敗,請重新啟動App",Toast.LENGTH_SHORT).show();
                        mBtnSignIn.setEnabled(false);
                        mBtnSignIn.setBackgroundColor(Color.parseColor("#AAAAAA"));
//disble the button, close the application e.t.c
                    }
                }


            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_show_access_token:
                intentUtil.showAccessToken();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        deleteAccessToken();
        Profile profile = Profile.getCurrentProfile();
        info.setText(message(profile));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private String message(Profile profile) {
        StringBuilder stringBuffer = new StringBuilder();
        if (profile != null) {
            stringBuffer.append("Welcome ").append(profile.getName());
        }
        return stringBuffer.toString();
    }

    private void deleteAccessToken() {
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {

                if (currentAccessToken == null){
                    //User logged out
                    prefUtil.clearToken();
                    clearUserArea();
                }
            }
        };
    }

    private void clearUserArea() {
        info.setText("");
        profileImgView.setImageDrawable(null);
    }
}


