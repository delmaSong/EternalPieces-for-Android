package com.example.myapplication.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class MainActivity extends AppCompatActivity {

    Button buttonStyle;
    Button buttonArtist;
    Button buttonJoin;
    Button buttonLogin;

    SharedPreferences sp_check, sp_login;
    SharedPreferences.Editor editor_login;

    String stillLogin;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        buttonStyle = (Button)findViewById(R.id.button);
        buttonArtist = (Button)findViewById(R.id.button2);
        buttonJoin = (Button)findViewById(R.id.button3);
        buttonLogin = (Button)findViewById(R.id.button4);

        sp_check = getSharedPreferences("login_check", MODE_PRIVATE);
        sp_login = getSharedPreferences("login_ok", MODE_PRIVATE);
        userId = sp_login.getString("login_ok", "");
        editor_login = sp_login.edit();



       stillLogin = sp_check.getString("login_check", "");
        Log.d("in main", stillLogin);

        //이전에 자동로그인 체크 안하고 어플 시작시
       if(stillLogin.equals("false")){
           editor_login.clear();
           editor_login.apply();
       }else if(userId == null){    //자동로그인 체크 안하고 로그아웃시 원래 메인액티비티 띄워줌

       }else if(stillLogin.equals("true")){  //자동로그인 체크 하고 어플 시작시 로그인된 메인액티비티 띄워줌
           Intent intent = new Intent(MainActivity.this, MainAfterLoginActivity.class);
           startActivity(intent);
           finish();
       }else{       //자동로그인 체크하고 로그아웃시
           editor_login.clear();
           editor_login.apply();
       }



    }

    protected void onResume(){
        super.onResume();

        buttonStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,FindStyleActivity.class);
       //         intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                startActivity(intent);

            }
        });

        buttonArtist.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FindArtistActivity.class);
                startActivity(intent);
            }
        });

        buttonJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, JoinActivity.class);
                startActivity(intent);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    }

//    @Override
//    protected void onPause(){
//        super.onPause();
//
//    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
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
