package com.example.gf.giftfriend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MemberSignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sigup);


        Button mBtnSignUp = (Button) findViewById(R.id.btn_SignUp);
        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent checksignUp = new Intent( MemberSignUp.this,FinishLogin.class);
                startActivity(checksignUp);
            }
        });

    }
}


