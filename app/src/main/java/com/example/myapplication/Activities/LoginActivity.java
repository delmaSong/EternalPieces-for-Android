package com.example.myapplication.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.SharedData.User;
import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity {

    Button button_cancel;
    Button button_ok;
    EditText et_id;     //유저가 입력한 id 값
    EditText et_pw;     //유저가 입력한 pw 값
    String userid;      //회원가입 때 저장한 id 값
    String userpw;      //회원가입 때 저장한 pw 값
    CheckBox checkBox;

    String s_id, s_pw;

    SharedPreferences spUser, spLogin, sp_check;
    SharedPreferences.Editor editor, editor2, editor_check;
    Gson gson;
    String json;
    User user;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);





        button_cancel = (Button)findViewById(R.id.button_cancel);
        button_ok = (Button)findViewById(R.id.btn_ok);

        et_id = (EditText)findViewById(R.id.et_id);
        et_pw = (EditText)findViewById(R.id.et_pw);

        checkBox = (CheckBox)findViewById(R.id.checkBox);


        //저장된 데이터 있을 경우, 원래 있던 자리에 넣어줌.
        if(savedInstanceState != null) {
            String data_id = savedInstanceState.getString(s_id);
            String data_pw = savedInstanceState.getString(s_pw);

            //있던 자리에 넣어줌
            et_id.setText(data_id);
            et_pw.setText(data_pw);

        }

            spUser = getSharedPreferences("user", MODE_PRIVATE);
            editor= spUser.edit();

            sp_check = getSharedPreferences("login_check", MODE_PRIVATE);
            editor_check = sp_check.edit();
            if (checkBox.isChecked()){
                Log.d("checkBox is", " checked");
                editor_check.putString("login_check", "true");
                editor_check.apply();
            }

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    //로그인 유지 체크박스 해제되었다면, 쉐어드 로그인체크에 false > 어플 처음 메인액티 띄울 때 false이면 기존 login_ok 아이디 지우고 시작.
                    if(!checkBox.isChecked()){
                        Log.d("checkBox is", "not checked");
                        editor_check.putString("login_check", "false");
                        editor_check.apply();
                    }else{
                        Log.d("checkBox is", " checked");

                        editor_check.putString("login_check", "true");
                        editor_check.apply();
                    }
                }
            });

        //뒤로가기
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //일단 로그인 없이 메인 화면으로 보내줌. 타투이스트/타투어 분기에 따라 타투이스트는 추가 가입 절차 필요
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                spUser = getSharedPreferences("user", MODE_PRIVATE);
                spLogin = getSharedPreferences("login_ok", MODE_PRIVATE);
                editor= spUser.edit();
                editor2 = spLogin.edit();

                gson = new Gson();
                json = spUser.getString(et_id.getText().toString(), "");
                user = gson.fromJson(json, User.class);
                Log.d("TAG_user", spUser.getAll().toString());

                userid = user.getId().toString();
                userpw = user.getPw().toString();


                if(et_id.getText().toString().equals(userid) && et_pw.getText().toString().equals(userpw)){         //shared에 저장되어있는 데이터값과 유저 입력값 확인 후 로그인 승인
                    editor2.putString("login_ok", userid);
                    editor2.apply();
                    Log.d("logined", spLogin.getString("login_ok", "").toString());
                    Intent intent = new Intent(LoginActivity.this, MainAfterLoginActivity.class);
                    Toast.makeText(LoginActivity.this, userid+"님 로그인 되었습니다", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                }else if(et_id.getText().toString().equals("1") && et_pw.getText().toString().equals("1")){         //관리자 아이디,비번
                    Intent intent = new Intent(LoginActivity.this, MainAfterLoginActivity.class);
                    Toast.makeText(LoginActivity.this, "로그인 되었습니다", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                }else{      //sharerd에 저장되어있는 데이터와 유저의 입력값 불일치시 로그인 불허
                    Toast.makeText(LoginActivity.this, "아이디와 비밀번호를 확인해 주세요", Toast.LENGTH_SHORT).show();
                    et_id.requestFocus();

                }




            }
        });

    }




    //onPause()실행 되기 전에 여기 들려서 번들에 데이터 저장한다.
    @Override
    protected void onSaveInstanceState(Bundle savedBundle){
        super.onSaveInstanceState(savedBundle);

        String data_id = et_id.getText().toString();    //입력되던 id값 가져옴
        String data_pw = et_pw.getText().toString();

        savedBundle.putString("userid_key", data_id);
        savedBundle.putString("userpw1_key", data_pw);
    }





}
