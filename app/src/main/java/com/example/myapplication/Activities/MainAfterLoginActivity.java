package com.example.myapplication.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class MainAfterLoginActivity extends AppCompatActivity {

    Button buttonStyle;
    Button buttonArtist;

    String s_id, s_pw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_after_login);

        buttonStyle = (Button)findViewById(R.id.buttonStyle);
        buttonArtist = (Button)findViewById(R.id.buttonArtist);

        Intent intent = getIntent();
        s_id = intent.getStringExtra("userid_key");
        s_pw = intent.getStringExtra("userpw_key");

    }

    protected void onResume(){
        super.onResume();

        buttonStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainAfterLoginActivity.this, FindStyleActivity.class);
                startActivity(intent);
            }
        });

        buttonArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainAfterLoginActivity.this, FindArtistActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainAfterLoginActivity.this);
        dialog.setTitle("프로그램 종료");
        dialog.setMessage("종료하시겠습니까?");
        dialog.setPositiveButton("종료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();
    }



}
