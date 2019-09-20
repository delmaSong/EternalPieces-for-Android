package com.example.myapplication.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.SharedData.User;
import com.google.gson.Gson;

import java.util.Map;
import java.util.regex.Pattern;


public class JoinActivity extends AppCompatActivity {

    final static String USER_KEY = "";

    Button buttonCancel;
    Button buttonOk, btn_check;
    RadioGroup rg;
    EditText et_id;
    EditText et_pw;
    EditText et_pw2;
    RadioButton rb;

    User user;

    boolean ttt;        //타투이스트 여부. 참이 타투이스트. 반대는 타투어.
    String userid, userpw;

    //아이디 중복체크 여부
    boolean doubleCheck = false;

    SharedPreferences sp_user;
    Gson gson = new Gson();
    String json;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        et_id = (EditText)findViewById(R.id.et_id);
        et_pw = (EditText)findViewById(R.id.et_pw);
        et_pw2 = (EditText)findViewById(R.id.et_pw2);

        et_id.setPrivateImeOptions("defaultInputmode=english");
        et_pw.setPrivateImeOptions("defaultInputmode=english");
        et_pw2.setPrivateImeOptions("defaultInputmode=english");

        et_id.setFilters(new InputFilter[]{new CustomInputFilter()});
        et_pw.setFilters(new InputFilter[]{new CustomInputFilter()});


        //shared preference 이용한 데이터 저장 구현
        sp_user = getSharedPreferences("user", MODE_PRIVATE);       //user라는 xml에 저장. 내 어플에서만 데이터 이용 가능.

        //User 객체 초기화
        user = new User();

        //JoinTattooistActivity에서 취소 버튼 누르며 데이터 넘겨줌.
        Intent intent = getIntent();
        userid = intent.getStringExtra("userid_key");
        userpw = intent.getStringExtra("userpw_key");

        //그거 받아서 자리에 넣어줌
        et_id.setText(userid);
        et_pw.setText(userpw);
        et_pw2.requestFocus();




        buttonCancel = (Button)findViewById(R.id.button_cancel);
        buttonOk = (Button)findViewById(R.id.btn_ok);
        btn_check = (Button)findViewById(R.id.btn_check);

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isIdOverlap();
            }
        });

        rg = (RadioGroup)findViewById(R.id.radioGroup);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rb = (RadioButton)findViewById(checkedId);

                if(checkedId == R.id.radioTattoist){
                    Toast.makeText(JoinActivity.this, "타투이스트를 선택하셨습니다", Toast.LENGTH_SHORT).show();
                    ttt = true;
                }else if(checkedId == R.id.radioTattooer){
                    Toast.makeText(JoinActivity.this,"타투어를 선택하셨습니다", Toast.LENGTH_SHORT).show();
                    ttt = false;
                }else{
                    Toast.makeText(JoinActivity.this,"올바르게 선택해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });



        //비밀번호 일치 검사
        et_pw2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = et_pw.getText().toString();
                String confirm = et_pw2.getText().toString();

                //비밀번호 확인 때 일치시 검정색
                if(password.equals(confirm)){
                    et_pw.setTextColor(Color.BLACK);
                    et_pw2.setTextColor(Color.BLACK);
                }else{      //불일치시 적색
                    et_pw.setTextColor(Color.RED);
                    et_pw2.setTextColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        //확인버튼 선택시
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //id입력 확인
                if(et_id.getText().toString().length() == 0){
                    Toast.makeText(JoinActivity.this, "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
                    et_id.requestFocus();
                    return;
                }else if(!doubleCheck){
                    Toast.makeText(getApplicationContext(), "아이디 중복확인을 해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }else if(et_id.getText().toString().length() < 7){
                    Toast.makeText(getApplicationContext(), "아이디는 6자리 이상 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                //비밀번호 입력 확인
                if(et_pw.getText().toString().length() == 0){
                    Toast.makeText(JoinActivity.this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                    et_pw.requestFocus();
                    return;
                }else if(et_pw.getText().toString().length() < 7){
                    Toast.makeText(getApplicationContext(), "비밀번호는 6자리 이상 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                //비밀번호 확인 입력 확인
                if(et_pw2.getText().toString().length() == 0){
                    Toast.makeText(JoinActivity.this, "비밀번호를 다시 입력해주세요", Toast.LENGTH_SHORT).show();
                    et_pw2.requestFocus();
                    return;
                }

                //비밀번호 일치 확인
                if(!et_pw.getText().toString().equals(et_pw2.getText().toString())){
                    Toast.makeText(JoinActivity.this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                    et_pw.setText("");
                    et_pw2.setText("");
                    et_pw.requestFocus();
                    return;
                }




                user.setId(et_id.getText().toString());
                user.setPw(et_pw.getText().toString());
//                user.setLikeDesign(null);
//                user.setLikeTattooist(null);
                user.setTon(ttt);

                if(et_id.length() >0 && et_pw.length() > 0 && et_pw2.length() > 0){
                    SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);       //user라는 이름의 xml파일 생성

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(user);        //user 객체 통째로 json으로 변환
                    editor.putString(et_id.getText().toString(), json);         //변환된 json형태의 user객체를 입력받은 아이디 키로 sharedPreference에 저장.

                    editor.apply();

                    Log.d("TAG_user", user.toString());
                    Log.d("TAG_user", sharedPreferences.getAll().toString());

                }




                //라디오버튼 타투이스트 선택시 화면 이동
                if(ttt){
                    Intent intent = new Intent(JoinActivity.this, JoinTattooistActivity.class);
                    intent.putExtra("user_key", user);
                    startActivity(intent);
                    finish();

                }else {  //타투어 선택시 이동 화면

                    Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                    intent.putExtra("user_key", user);
                    startActivity(intent);
                    finish();
                }


            }
        });



    }




    @Override
    protected void onResume(){
        super.onResume();

        //뒤로 이동
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(JoinActivity.this, "취소하셨습니다", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }


    protected void isIdOverlap() {
        Map<String, ?> allEntries = sp_user.getAll();

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {

            json = sp_user.getString(entry.getKey(), "");
            user = gson.fromJson(json, User.class);

            if(entry.getKey().equals(et_id.getText().toString())){
                Toast.makeText(getApplicationContext(), "해당 아이디가 이미 존재합니다.", Toast.LENGTH_SHORT).show();
                et_id.requestFocus();
                return;
            }else{
                Toast.makeText(getApplicationContext(), "아이디 사용이 가능합니다.", Toast.LENGTH_SHORT).show();
            }
         }
        doubleCheck = true;
    }


    protected class CustomInputFilter implements InputFilter {
        @Override
        public CharSequence filter(CharSequence source, int start,
                                   int end, Spanned dest, int dstart, int dend) {
            Pattern ps = Pattern.compile("^[-_a-zA-Z0-9]+$");

            if(source.equals("") || ps.matcher(source).matches()){
                return source;
            }

            Toast.makeText(getApplicationContext(),"영문, 숫자, _ , -만 입력 가능합니다.", Toast.LENGTH_SHORT).show();

            return "";
        }
    }





}
