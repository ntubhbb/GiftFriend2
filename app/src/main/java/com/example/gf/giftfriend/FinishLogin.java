package com.example.gf.giftfriend;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FinishLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finishlogin);

        Button mBtnAdd = (Button) findViewById(R.id.btn_add);
        Button mBtnScan = (Button) findViewById(R.id.btn_scan);
        Button mBtnUpload = (Button) findViewById(R.id.btn_upload);
        Button mBtnRecord = (Button) findViewById(R.id.btn_record);
        Button mBtnpage = (Button) findViewById(R.id.btn_page);


        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addFriend = new Intent(FinishLogin.this, AddFriend.class);
                startActivity(addFriend);
            }
        });

        mBtnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scan = new Intent(FinishLogin.this, Scan.class);
                startActivity(scan);
            }
        });

        mBtnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent upload = new Intent(FinishLogin.this, Upload.class);
                startActivity(upload);
            }
        });

        mBtnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rc = new Intent(FinishLogin.this, GiftRecord.class);
                startActivity(rc);
            }
        });

        mBtnpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://140.131.7.132/TEST/GiftFriend/index.html");
                Intent page = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(page);
            }
        });
    }

}
