package com.example.myapplication.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.myapplication.R;
import com.example.myapplication.SharedData.Tattooist;
import com.example.myapplication.SharedData.User;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SetWorktime extends AppCompatActivity {


    ImageButton btn_back;

    ToggleButton btn_mon, btn_tue, btn_wed, btn_thu, btn_fri, btn_sat, btn_sun;
    ToggleButton btn_9, btn_10, btn_11, btn_12, btn_13, btn_14, btn_15, btn_16, btn_17, btn_18, btn_19, btn_20, btn_21, btn_22, btn_23;

    Button btn_ok, btn_cancel;

    //sharedPreference에 저장할 때 쓸 변수
    List<String> dateList, timeList;

    //sharedPreference에 저장된 데이터 가져올 때(retrieve) 쓸 변수
    List<String> rt_dateList, rt_timeList;

    Gson gson;
    Tattooist tattooist;
    String json, json_user;



    
    String date = "";
    String time = "";
    public Context context;

    SharedPreferences sp_date, sp_user, sp_login;
    SharedPreferences.Editor editor_login;

    String userId;

    User user;

    //for Drawer
    DrawerLayout dl;
    View drawer;

    ImageButton btnOpenDrawer;
    ImageButton btnCloseDrawer;

    //드로어 내부 버튼
    Button btnFindStyle;
    Button btnFindArtist;
    Button btnMypage;
//    Button btnChatList;
    Button btnBookingList;
    Button btnUploadDesign;
    Button btnUploadWork;
    Button btnLike;
    Button set_time, btn_logout;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_worktime);


        btn_back = (ImageButton)findViewById(R.id.btn_back);

        btn_mon = (ToggleButton)findViewById(R.id.btn_mon);
        btn_tue = (ToggleButton)findViewById(R.id.btn_tue);
        btn_wed = (ToggleButton)findViewById(R.id.btn_wed);
        btn_thu = (ToggleButton)findViewById(R.id.btn_thu);
        btn_fri = (ToggleButton)findViewById(R.id.btn_fri);
        btn_sat = (ToggleButton)findViewById(R.id.btn_sat);
        btn_sun = (ToggleButton)findViewById(R.id.btn_sun);


        btn_9 = (ToggleButton)findViewById(R.id.btn_9);
        btn_10 = (ToggleButton)findViewById(R.id.btn_10);
        btn_11 = (ToggleButton)findViewById(R.id.btn_11);
        btn_12 = (ToggleButton)findViewById(R.id.btn_12);
        btn_13 = (ToggleButton)findViewById(R.id.btn_13);
        btn_14 = (ToggleButton)findViewById(R.id.btn_14);
        btn_15 = (ToggleButton)findViewById(R.id.btn_15);
        btn_16 = (ToggleButton)findViewById(R.id.btn_16);
        btn_17 = (ToggleButton)findViewById(R.id.btn_17);
        btn_18 = (ToggleButton)findViewById(R.id.btn_18);
        btn_19 = (ToggleButton)findViewById(R.id.btn_19);
        btn_20 = (ToggleButton)findViewById(R.id.btn_20);
        btn_21 = (ToggleButton)findViewById(R.id.btn_21);
        btn_22 = (ToggleButton)findViewById(R.id.btn_22);
        btn_23 = (ToggleButton)findViewById(R.id.btn_23);


        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_ok = (Button)findViewById(R.id.btn_ok);




        //드로어 내부 버튼
        btnFindStyle = (Button)findViewById(R.id.btn_find_style);
        btnFindArtist = (Button)findViewById(R.id.btn_find_artist);
        btnMypage = (Button)findViewById(R.id.btn_mypage);
//        btnChatList = (Button)findViewById(R.id.btn_chat_list);
        btnBookingList = (Button)findViewById(R.id.btn_booking_list);
        btnUploadDesign = (Button)findViewById(R.id.btn_upload_design);
        btnUploadWork = (Button)findViewById(R.id.btn_upload_work);
        btnLike = (Button)findViewById(R.id.btn_like);
        set_time = (Button)findViewById(R.id.set_time);
        btn_logout = (Button)findViewById(R.id.btn_logout);



        dl = (DrawerLayout)findViewById(R.id.dl_set_worktime);
        drawer = (View)findViewById(R.id.dl_drawer);

        btnOpenDrawer = (ImageButton) findViewById(R.id.btnOpenDrawer);
        btnCloseDrawer = (ImageButton) findViewById(R.id.btnCloseDrawer);

        //드로어 오픈 버튼
        btnOpenDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dl.openDrawer(drawer);
            }
        });

        //드로어 클로즈 버튼
        btnCloseDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dl.closeDrawer(drawer);
            }
        });


        //settings for shared and login-out
        sp_login = getSharedPreferences("login_ok", MODE_PRIVATE);
        editor_login = sp_login.edit();
        userId = sp_login.getString("login_ok", "");

        sp_user = getSharedPreferences("user", MODE_PRIVATE);

        gson = new Gson();
        json_user = sp_user.getString(userId,"");
        user = gson.fromJson(json_user, User.class);

        //setting for drawer buttons
        //로그인 되어있다면..
        if(!userId.isEmpty()){
            //타투이스트로 로그인한거라면
            if(user.isTon()){

            }else{  //타투이스트로 로그인한게 아니라면
                //마이페이지 버튼 숨김
                btnMypage.setEnabled(false);
                //도안업로드 버튼 숨김
                btnUploadDesign.setEnabled(false);
                //시술사진 업로드 버튼 숨김
                btnUploadWork.setEnabled(false);
                //시술 가능 시간 설정 버튼 숨김
                set_time.setEnabled(false);
            }
        }else{  //로그인 되어있지 않다면
            btn_logout.setText("로그인");
            //좋아요 버튼 숨김
            btnLike.setEnabled(false);
            //나의예약 버튼 숨김
            btnBookingList.setEnabled(false);
            //마이페이지 버튼 숨김
            btnMypage.setEnabled(false);
            //도안업로드 버튼 숨김
            btnUploadDesign.setEnabled(false);
            //시술사진 업로드 버튼 숨김
            btnUploadWork.setEnabled(false);
            //시술 가능 시간 설정 버튼 숨김
            set_time.setEnabled(false);
        }





    }




    protected void onResume(){

        super.onResume();

        //shared preference 초기화
        sp_date = getSharedPreferences("workDate", MODE_PRIVATE);
        sp_user = getSharedPreferences("login_ok", MODE_PRIVATE);


        final String userId = sp_user.getString("login_ok", "");
//        gson = new Gson();
        json = sp_date.getString(userId, "");   //쉐어드에 userId를 키값으로 저장된 놈들을 json 변수에 담아줌
        tattooist = gson.fromJson(json, Tattooist.class);       //gson을 통해 json으로 저장되어있는놈 javaObject로 변환해 담음.


        dateList = new ArrayList<>();
        timeList = new ArrayList<>();

        rt_dateList = new ArrayList<>();
        rt_timeList = new ArrayList<>();

        //shared preference에 가능 요일, 시간 저장
        final SharedPreferences.Editor date_edit = sp_date.edit();

        Log.d("get DATA", tattooist.getWorkDate().toString());

        //shared에 저장되어있던 요일 rt_dateList에 다시 저장
        for(int i=0; i<tattooist.getWorkDate().size(); i++){
            rt_dateList.add(tattooist.getWorkDate().get(i).toString());
            Log.d("set DATA", rt_dateList.get(i).toString());
        }

        //shared에 저장되어있던 시간 rt_timeList에 다시 저장
        for(int i=0; i<tattooist.getWorkTime().size(); i++){
            rt_timeList.add(tattooist.getWorkTime().get(i).toString());
        }




        //저장되어 있는 값 있다면 배경 색깔 변경
        if (rt_dateList.contains("월")){
            btn_mon.setBackgroundColor(Color.parseColor("#F8D362"));

        }else{
            btn_mon.setBackgroundColor(Color.parseColor("#DDDDDD"));
        }
        if (rt_dateList.contains("화")){
            btn_tue.setBackgroundColor(Color.parseColor("#F8D362"));
        }else{
            btn_tue.setBackgroundColor(Color.parseColor("#DDDDDD"));
        }
        if (rt_dateList.contains("수")){
            btn_wed.setBackgroundColor(Color.parseColor("#F8D362"));
        }else{
            btn_wed.setBackgroundColor(Color.parseColor("#DDDDDD"));
        }
        if (rt_dateList.contains("목")){
            btn_thu.setBackgroundColor(Color.parseColor("#F8D362"));
        }else{
            btn_thu.setBackgroundColor(Color.parseColor("#DDDDDD"));
        }
        if (rt_dateList.contains("금")){
            btn_fri.setBackgroundColor(Color.parseColor("#F8D362"));
        }else{
            btn_fri.setBackgroundColor(Color.parseColor("#DDDDDD"));
        }
        if (rt_dateList.contains("토")){
            btn_sat.setBackgroundColor(Color.parseColor("#F8D362"));
        }else{
            btn_sat.setBackgroundColor(Color.parseColor("#DDDDDD"));
        }
        if (rt_dateList.contains("일")){
            btn_sun.setBackgroundColor(Color.parseColor("#F8D362"));
        }else{
            btn_sun.setBackgroundColor(Color.parseColor("#DDDDDD"));
        }





        //저장되어있는 값 있다면 색깔 변경. 시간
        if (rt_timeList.contains("9시")){
            btn_9.setBackgroundColor(Color.parseColor("#F8D362"));
        }else{
            btn_9.setBackgroundColor(Color.parseColor("#DDDDDD"));
        }
        if (rt_timeList.contains("10시")){
            btn_10.setBackgroundColor(Color.parseColor("#F8D362"));
        }else{
            btn_10.setBackgroundColor(Color.parseColor("#DDDDDD"));
        }
        if (rt_timeList.contains("11시")){
            btn_11.setBackgroundColor(Color.parseColor("#F8D362"));
        }else{
            btn_11.setBackgroundColor(Color.parseColor("#DDDDDD"));
        }
        if (rt_timeList.contains("12시")){
            btn_12.setBackgroundColor(Color.parseColor("#F8D362"));
        }else{
            btn_12.setBackgroundColor(Color.parseColor("#DDDDDD"));
        }
        if (rt_timeList.contains("13시")){
            btn_13.setBackgroundColor(Color.parseColor("#F8D362"));
        }else{
            btn_13.setBackgroundColor(Color.parseColor("#DDDDDD"));
        }
        if (rt_timeList.contains("14시")){
            btn_14.setBackgroundColor(Color.parseColor("#F8D362"));
        }else{
            btn_14.setBackgroundColor(Color.parseColor("#DDDDDD"));
        }
        if (rt_timeList.contains("15시")){
            btn_15.setBackgroundColor(Color.parseColor("#F8D362"));
        }else{
            btn_15.setBackgroundColor(Color.parseColor("#DDDDDD"));
        }
        if (rt_timeList.contains("16시")){
            btn_16.setBackgroundColor(Color.parseColor("#F8D362"));
        }else{
            btn_16.setBackgroundColor(Color.parseColor("#DDDDDD"));
        }
        if (rt_timeList.contains("17시")){
            btn_17.setBackgroundColor(Color.parseColor("#F8D362"));
        }else{
            btn_17.setBackgroundColor(Color.parseColor("#DDDDDD"));
        }
        if (rt_timeList.contains("18시")){
            btn_18.setBackgroundColor(Color.parseColor("#F8D362"));
        }else{
            btn_18.setBackgroundColor(Color.parseColor("#DDDDDD"));
        }
        if (rt_timeList.contains("19시")){
            btn_19.setBackgroundColor(Color.parseColor("#F8D362"));
        }else{
            btn_19.setBackgroundColor(Color.parseColor("#DDDDDD"));
        }
        if (rt_timeList.contains("20시")){
            btn_20.setBackgroundColor(Color.parseColor("#F8D362"));
        }else{
            btn_20.setBackgroundColor(Color.parseColor("#DDDDDD"));
        }
        if (rt_timeList.contains("21시")){
            btn_21.setBackgroundColor(Color.parseColor("#F8D362"));
        }else{
            btn_21.setBackgroundColor(Color.parseColor("#DDDDDD"));
        }
        if (rt_timeList.contains("22시")){
            btn_22.setBackgroundColor(Color.parseColor("#F8D362"));
        }else{
            btn_22.setBackgroundColor(Color.parseColor("#DDDDDD"));
        }
        if (rt_timeList.contains("23시")){
            btn_23.setBackgroundColor(Color.parseColor("#F8D362"));
        }else{
            btn_23.setBackgroundColor(Color.parseColor("#DDDDDD"));
        }




        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });





        //set workDate with toggle
        btn_mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rt_dateList.contains("월")){      //기존에 포함되어 있다면
                    if(btn_mon.isChecked()){        //다시 체크했을 때 배경색 변경
                        btn_mon.setBackgroundColor(Color.parseColor("#DDDDDD"));
                        dateList.remove("월");       //리스트에서도 삭제
                        rt_dateList.remove("월");
                    }
                    else {
                        btn_mon.setBackgroundColor(Color.parseColor("#F8D362"));
                        dateList.add("월");
                    }
                }else {//기존에 포함되어 있지 않다면
                    if (btn_mon.isChecked()){       //체크했을 때 배경색 변경 및 리스트에 추가
                        btn_mon.setBackgroundColor(Color.parseColor("#F8D362"));
                        dateList.add("월");
                    }else {
                        btn_mon.setBackgroundColor(Color.parseColor("#DDDDDD"));
                        dateList.remove("월");
                    }
                }
            }
        });

        btn_tue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rt_dateList.contains("화")){      //기존에 포함되어 있다면
                    if(btn_tue.isChecked()){        //다시 체크했을 때 배경색 변경
                        btn_tue.setBackgroundColor(Color.parseColor("#DDDDDD"));
                        dateList.remove("화");       //리스트에서도 삭제
                        rt_dateList.remove("화");
                    }
                    else {
                        btn_tue.setBackgroundColor(Color.parseColor("#F8D362"));
                        dateList.add("화");
                    }
                }else {//기존에 포함되어 있지 않다면
                    if (btn_tue.isChecked()){       //체크했을 때 배경색 변경 및 리스트에 추가
                        btn_tue.setBackgroundColor(Color.parseColor("#F8D362"));
                        dateList.add("화");
                    }else {
                        btn_tue.setBackgroundColor(Color.parseColor("#DDDDDD"));
                    dateList.remove("화");
                }
                }
            }
        });

        btn_wed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(rt_dateList.contains("수")){      //기존에 포함되어 있다면
                    if(btn_wed.isChecked()){        //다시 체크했을 때 배경색 변경
                        btn_wed.setBackgroundColor(Color.parseColor("#DDDDDD"));
                        dateList.remove("수");       //리스트에서도 삭제
                        rt_dateList.remove("수");
                    }
                    else {
                        btn_wed.setBackgroundColor(Color.parseColor("#F8D362"));
                        dateList.add("수");
                    }
                }else {//기존에 포함되어 있지 않다면
                    if (btn_wed.isChecked()){       //체크했을 때 배경색 변경 및 리스트에 추가
                        btn_wed.setBackgroundColor(Color.parseColor("#F8D362"));
                        dateList.add("수");
                    }else {
                        btn_wed.setBackgroundColor(Color.parseColor("#DDDDDD"));
                        dateList.remove("수");
                    }
                }
            }
        });

        btn_thu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rt_dateList.contains("목")){      //기존에 포함되어 있다면
                    if(btn_thu.isChecked()){        //다시 체크했을 때 배경색 변경
                        btn_thu.setBackgroundColor(Color.parseColor("#DDDDDD"));
                        dateList.remove("목");       //리스트에서도 삭제
                        rt_dateList.remove("목");
                    }
                    else {
                        btn_thu.setBackgroundColor(Color.parseColor("#F8D362"));
                        dateList.add("목");
                    }
                }else {//기존에 포함되어 있지 않다면
                    if (btn_thu.isChecked()){       //체크했을 때 배경색 변경 및 리스트에 추가
                        btn_thu.setBackgroundColor(Color.parseColor("#F8D362"));
                        dateList.add("목");
                    }else {
                        btn_thu.setBackgroundColor(Color.parseColor("#DDDDDD"));
                        dateList.remove("목");
                    }
                }
            }
        });

        btn_fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rt_dateList.contains("금")){      //기존에 포함되어 있다면
                    if(btn_fri.isChecked()){        //다시 체크했을 때 배경색 변경
                        btn_fri.setBackgroundColor(Color.parseColor("#DDDDDD"));
                        dateList.remove("금");       //리스트에서도 삭제
                        rt_dateList.remove("금");
                    }
                    else {
                        btn_fri.setBackgroundColor(Color.parseColor("#F8D362"));
                        dateList.add("금");
                    }
                }else {//기존에 포함되어 있지 않다면
                    if (btn_fri.isChecked()){       //체크했을 때 배경색 변경 및 리스트에 추가
                        btn_fri.setBackgroundColor(Color.parseColor("#F8D362"));
                        dateList.add("금");
                    }else {
                        btn_fri.setBackgroundColor(Color.parseColor("#DDDDDD"));
                        dateList.remove("금");
                    }
                }
            }
        });

        btn_sat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rt_dateList.contains("토")){      //기존에 포함되어 있다면
                    if(btn_sat.isChecked()){        //다시 체크했을 때 배경색 변경
                        btn_sat.setBackgroundColor(Color.parseColor("#DDDDDD"));
                        dateList.remove("토");       //리스트에서도 삭제
                        rt_dateList.remove("토");
                    }
                    else {
                        btn_sat.setBackgroundColor(Color.parseColor("#F8D362"));
                        dateList.add("토");
                    }
                }else {//기존에 포함되어 있지 않다면
                    if (btn_sat.isChecked()){       //체크했을 때 배경색 변경 및 리스트에 추가
                        btn_sat.setBackgroundColor(Color.parseColor("#F8D362"));
                        dateList.add("토");
                    }else {
                        btn_sat.setBackgroundColor(Color.parseColor("#DDDDDD"));
                        dateList.remove("토");
                    }
                }
            }
        });

        btn_sun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rt_dateList.contains("일")){      //기존에 포함되어 있다면
                    if(btn_sun.isChecked()){        //다시 체크했을 때 배경색 변경
                        btn_sun.setBackgroundColor(Color.parseColor("#DDDDDD"));
                        dateList.remove("일");       //리스트에서도 삭제
                        rt_dateList.remove("일");
                    }
                    else {
                        btn_sun.setBackgroundColor(Color.parseColor("#F8D362"));
                        dateList.add("일");
                    }
                }else {//기존에 포함되어 있지 않다면
                    if (btn_sun.isChecked()){       //체크했을 때 배경색 변경 및 리스트에 추가
                        btn_sun.setBackgroundColor(Color.parseColor("#F8D362"));
                        dateList.add("일");
                    }else {
                        btn_sun.setBackgroundColor(Color.parseColor("#DDDDDD"));
                        dateList.remove("일");
                    }
                }
            }
        });





        //set workTime with Toggle

        btn_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rt_timeList.contains("9시")){      //기존에 포함되어 있다면
                    if(btn_9.isChecked()){        //다시 체크했을 때 배경색 변경
                        btn_9.setBackgroundColor(Color.parseColor("#DDDDDD"));
                        timeList.remove("9시");       //리스트에서도 삭제
                        rt_timeList.remove("9시");
                    }
                    else {
                        btn_9.setBackgroundColor(Color.parseColor("#F8D362"));
                        timeList.add("9시");
                    }
                }else {//기존에 포함되어 있지 않다면
                    if (btn_9.isChecked()){       //체크했을 때 배경색 변경 및 리스트에 추가
                        btn_9.setBackgroundColor(Color.parseColor("#F8D362"));
                        timeList.add("9시");
                    }else {
                        btn_9.setBackgroundColor(Color.parseColor("#DDDDDD"));
                        timeList.remove("9시");
                    }
                }
            }
        });

        btn_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rt_timeList.contains("10시")){      //기존에 포함되어 있다면
                    if(btn_10.isChecked()){        //다시 체크했을 때 배경색 변경
                        btn_10.setBackgroundColor(Color.parseColor("#DDDDDD"));
                        timeList.remove("10시");       //리스트에서도 삭제
                        rt_timeList.remove("10시");
                    }
                    else {
                        btn_10.setBackgroundColor(Color.parseColor("#F8D362"));
                        timeList.add("10시");
                    }
                }else {//기존에 포함되어 있지 않다면
                    if (btn_10.isChecked()){       //체크했을 때 배경색 변경 및 리스트에 추가
                        btn_10.setBackgroundColor(Color.parseColor("#F8D362"));
                        timeList.add("10시");
                    }else {
                        btn_10.setBackgroundColor(Color.parseColor("#DDDDDD"));
                        timeList.remove("10시");
                    }
                }
            }
        });

        btn_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rt_timeList.contains("11시")){      //기존에 포함되어 있다면
                    if(btn_11.isChecked()){        //다시 체크했을 때 배경색 변경
                        btn_11.setBackgroundColor(Color.parseColor("#DDDDDD"));
                        timeList.remove("11시");       //리스트에서도 삭제
                        rt_timeList.remove("11시");
                    }
                    else {
                        btn_11.setBackgroundColor(Color.parseColor("#F8D362"));
                        timeList.add("11시");
                    }
                }else {//기존에 포함되어 있지 않다면
                    if (btn_11.isChecked()){       //체크했을 때 배경색 변경 및 리스트에 추가
                        btn_11.setBackgroundColor(Color.parseColor("#F8D362"));
                        timeList.add("11시");
                    }else {
                        btn_11.setBackgroundColor(Color.parseColor("#DDDDDD"));
                        timeList.remove("11시");
                    }
                }
            }
        });

        btn_12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rt_timeList.contains("12시")){      //기존에 포함되어 있다면
                    if(btn_12.isChecked()){        //다시 체크했을 때 배경색 변경
                        btn_12.setBackgroundColor(Color.parseColor("#DDDDDD"));
                        timeList.remove("12시");       //리스트에서도 삭제
                        rt_timeList.remove("12시");
                    }
                    else {
                        btn_12.setBackgroundColor(Color.parseColor("#F8D362"));
                        timeList.add("12시");
                    }
                }else {//기존에 포함되어 있지 않다면
                    if (btn_12.isChecked()){       //체크했을 때 배경색 변경 및 리스트에 추가
                        btn_12.setBackgroundColor(Color.parseColor("#F8D362"));
                        timeList.add("12시");
                    }else {
                        btn_12.setBackgroundColor(Color.parseColor("#DDDDDD"));
                        timeList.remove("12시");
                    }
                }
            }
        });

        btn_13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rt_timeList.contains("13시")){      //기존에 포함되어 있다면
                    if(btn_13.isChecked()){        //다시 체크했을 때 배경색 변경
                        btn_13.setBackgroundColor(Color.parseColor("#DDDDDD"));
                        timeList.remove("13시");       //리스트에서도 삭제
                        rt_timeList.remove("13시");
                    }
                    else {
                        btn_13.setBackgroundColor(Color.parseColor("#F8D362"));
                        timeList.add("13시");
                    }
                }else {//기존에 포함되어 있지 않다면
                    if (btn_13.isChecked()){       //체크했을 때 배경색 변경 및 리스트에 추가
                        btn_13.setBackgroundColor(Color.parseColor("#F8D362"));
                        timeList.add("13시");
                    }else {
                        btn_13.setBackgroundColor(Color.parseColor("#DDDDDD"));
                        timeList.remove("13시");
                    }
                }
            }
        });

        btn_14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rt_timeList.contains("14시")){      //기존에 포함되어 있다면
                    if(btn_14.isChecked()){        //다시 체크했을 때 배경색 변경
                        btn_14.setBackgroundColor(Color.parseColor("#DDDDDD"));
                        timeList.remove("14시");       //리스트에서도 삭제
                        rt_timeList.remove("14시");
                    }
                    else {
                        btn_14.setBackgroundColor(Color.parseColor("#F8D362"));
                        timeList.add("14시");
                    }
                }else {//기존에 포함되어 있지 않다면
                    if (btn_14.isChecked()){       //체크했을 때 배경색 변경 및 리스트에 추가
                        btn_14.setBackgroundColor(Color.parseColor("#F8D362"));
                        timeList.add("14시");
                    }else {
                        btn_14.setBackgroundColor(Color.parseColor("#DDDDDD"));
                        timeList.remove("14시");
                    }
                }
            }
        });

        btn_15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rt_timeList.contains("15시")){      //기존에 포함되어 있다면
                    if(btn_15.isChecked()){        //다시 체크했을 때 배경색 변경
                        btn_15.setBackgroundColor(Color.parseColor("#DDDDDD"));
                        timeList.remove("15시");       //리스트에서도 삭제
                        rt_timeList.remove("15시");
                    }
                    else {
                        btn_15.setBackgroundColor(Color.parseColor("#F8D362"));
                        timeList.add("15시");
                    }
                }else {//기존에 포함되어 있지 않다면
                    if (btn_15.isChecked()){       //체크했을 때 배경색 변경 및 리스트에 추가
                        btn_15.setBackgroundColor(Color.parseColor("#F8D362"));
                        timeList.add("15시");
                    }else {
                        btn_15.setBackgroundColor(Color.parseColor("#DDDDDD"));
                        timeList.remove("15시");
                    }
                }
            }
        });

        btn_16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rt_timeList.contains("16시")){      //기존에 포함되어 있다면
                    if(btn_16.isChecked()){        //다시 체크했을 때 배경색 변경
                        btn_16.setBackgroundColor(Color.parseColor("#DDDDDD"));
                        timeList.remove("16시");       //리스트에서도 삭제
                        rt_timeList.remove("16시");
                    }
                    else {
                        btn_16.setBackgroundColor(Color.parseColor("#F8D362"));
                        timeList.add("16시");
                    }
                }else {//기존에 포함되어 있지 않다면
                    if (btn_16.isChecked()){       //체크했을 때 배경색 변경 및 리스트에 추가
                        btn_16.setBackgroundColor(Color.parseColor("#F8D362"));
                        timeList.add("16시");
                    }else {
                        btn_16.setBackgroundColor(Color.parseColor("#DDDDDD"));
                        timeList.remove("16시");
                    }
                }
            }
        });

        btn_17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rt_timeList.contains("17시")){      //기존에 포함되어 있다면
                    if(btn_17.isChecked()){        //다시 체크했을 때 배경색 변경
                        btn_17.setBackgroundColor(Color.parseColor("#DDDDDD"));
                        timeList.remove("17시");       //리스트에서도 삭제
                        rt_timeList.remove("17시");
                    }
                    else {
                        btn_17.setBackgroundColor(Color.parseColor("#F8D362"));
                        timeList.add("17시");
                    }
                }else {//기존에 포함되어 있지 않다면
                    if (btn_17.isChecked()){       //체크했을 때 배경색 변경 및 리스트에 추가
                        btn_17.setBackgroundColor(Color.parseColor("#F8D362"));
                        timeList.add("17시");
                    }else {
                        btn_17.setBackgroundColor(Color.parseColor("#DDDDDD"));
                        timeList.remove("17시");
                    }
                }
            }
        });

        btn_18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rt_timeList.contains("18시")){      //기존에 포함되어 있다면
                    if(btn_18.isChecked()){        //다시 체크했을 때 배경색 변경
                        btn_18.setBackgroundColor(Color.parseColor("#DDDDDD"));
                        timeList.remove("18시");       //리스트에서도 삭제
                        rt_timeList.remove("18시");
                    }
                    else {
                        btn_18.setBackgroundColor(Color.parseColor("#F8D362"));
                        timeList.add("18시");
                    }
                }else {//기존에 포함되어 있지 않다면
                    if (btn_18.isChecked()){       //체크했을 때 배경색 변경 및 리스트에 추가
                        btn_18.setBackgroundColor(Color.parseColor("#F8D362"));
                        timeList.add("18시");
                    }else {
                        btn_18.setBackgroundColor(Color.parseColor("#DDDDDD"));
                        timeList.remove("18시");
                    }
                }
            }
        });

        btn_19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rt_timeList.contains("19시")){      //기존에 포함되어 있다면
                    if(btn_19.isChecked()){        //다시 체크했을 때 배경색 변경
                        btn_19.setBackgroundColor(Color.parseColor("#DDDDDD"));
                        timeList.remove("19시");       //리스트에서도 삭제
                        rt_timeList.remove("19시");
                    }
                    else {
                        btn_19.setBackgroundColor(Color.parseColor("#F8D362"));
                        timeList.add("19시");
                    }
                }else {//기존에 포함되어 있지 않다면
                    if (btn_19.isChecked()){       //체크했을 때 배경색 변경 및 리스트에 추가
                        btn_19.setBackgroundColor(Color.parseColor("#F8D362"));
                        timeList.add("19시");
                    }else {
                        btn_19.setBackgroundColor(Color.parseColor("#DDDDDD"));
                        timeList.remove("19시");
                    }
                }
            }
        });

        btn_20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rt_timeList.contains("20시")){      //기존에 포함되어 있다면
                    if(btn_20.isChecked()){        //다시 체크했을 때 배경색 변경
                        btn_20.setBackgroundColor(Color.parseColor("#DDDDDD"));
                        timeList.remove("20시");       //리스트에서도 삭제
                        rt_timeList.remove("20시");
                    }
                    else {
                        btn_20.setBackgroundColor(Color.parseColor("#F8D362"));
                        timeList.add("20시");
                    }
                }else {//기존에 포함되어 있지 않다면
                    if (btn_20.isChecked()){       //체크했을 때 배경색 변경 및 리스트에 추가
                        btn_20.setBackgroundColor(Color.parseColor("#F8D362"));
                        timeList.add("20시");
                    }else {
                        btn_20.setBackgroundColor(Color.parseColor("#DDDDDD"));
                        timeList.remove("20시");
                    }
                }
            }
        });

        btn_21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rt_timeList.contains("21시")){      //기존에 포함되어 있다면
                    if(btn_21.isChecked()){        //다시 체크했을 때 배경색 변경
                        btn_21.setBackgroundColor(Color.parseColor("#DDDDDD"));
                        timeList.remove("21시");       //리스트에서도 삭제
                        rt_timeList.remove("21시");
                    }
                    else {
                        btn_21.setBackgroundColor(Color.parseColor("#F8D362"));
                        timeList.add("21시");
                    }
                }else {//기존에 포함되어 있지 않다면
                    if (btn_21.isChecked()){       //체크했을 때 배경색 변경 및 리스트에 추가
                        btn_21.setBackgroundColor(Color.parseColor("#F8D362"));
                        timeList.add("21시");
                    }else {
                        btn_21.setBackgroundColor(Color.parseColor("#DDDDDD"));
                        timeList.remove("21시");
                    }
                }
            }
        });

        btn_22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rt_timeList.contains("22시")){      //기존에 포함되어 있다면
                    if(btn_22.isChecked()){        //다시 체크했을 때 배경색 변경
                        btn_22.setBackgroundColor(Color.parseColor("#DDDDDD"));
                        timeList.remove("22시");       //리스트에서도 삭제
                        rt_timeList.remove("22시");
                    }
                    else {
                        btn_22.setBackgroundColor(Color.parseColor("#F8D362"));
                        timeList.add("22시");
                    }
                }else {//기존에 포함되어 있지 않다면
                    if (btn_22.isChecked()){       //체크했을 때 배경색 변경 및 리스트에 추가
                        btn_22.setBackgroundColor(Color.parseColor("#F8D362"));
                        timeList.add("22시");
                    }else {
                        btn_22.setBackgroundColor(Color.parseColor("#DDDDDD"));
                        timeList.remove("22시");
                    }
                }
            }
        });

        btn_23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rt_timeList.contains("23시")){      //기존에 포함되어 있다면
                    if(btn_23.isChecked()){        //다시 체크했을 때 배경색 변경
                        btn_23.setBackgroundColor(Color.parseColor("#DDDDDD"));
                        timeList.remove("23시");       //리스트에서도 삭제
                        rt_timeList.remove("23시");
                    }
                    else {
                        btn_23.setBackgroundColor(Color.parseColor("#F8D362"));
                        timeList.add("23시");
                    }
                }else {//기존에 포함되어 있지 않다면
                    if (btn_23.isChecked()){       //체크했을 때 배경색 변경 및 리스트에 추가
                        btn_23.setBackgroundColor(Color.parseColor("#F8D362"));
                        timeList.add("23시");
                    }else {
                        btn_23.setBackgroundColor(Color.parseColor("#DDDDDD"));
                        timeList.remove("23시");
                    }
                }
            }
        });






        btn_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                //기존에 shared 에 저장되어있던 항목들도 다시 dateList에 담음. 이걸 다시 shared에 저장할거임.
                for(int i=0; i<rt_dateList.size(); i++){
                    dateList.add(rt_dateList.get(i).toString());
                }
                for(int i=0; i<rt_timeList.size(); i++){
                    timeList.add(rt_timeList.get(i).toString());
                }

                //유저에게 띄워줘서 확인하기 위한 변수
                for(int i=0; i<dateList.size(); i++){
                    date += dateList.get(i).toString();

                }
                for(int j=0; j<timeList.size(); j++){
                    time += timeList.get(j).toString();
                }



                //선택한 시간 확인하는 dialog 생성.
                AlertDialog.Builder builder = new AlertDialog.Builder(SetWorktime.this);
                builder.setTitle("선택하신 날짜와 시간이 맞으신가요?");
                builder.setMessage(date + time);
                builder.setPositiveButton("네",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tattooist.setWorkDate(dateList);
                                tattooist.setWorkTime(timeList);
                                json = gson.toJson(tattooist);
                                date_edit.putString(userId, json);
                                date_edit.apply();

                                Log.d("Date Edit Right", sp_date.getAll().toString());

                                Toast.makeText(getApplicationContext(), "변경이 완료되었습니다", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SetWorktime.this, SetWorktime.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                builder.setNegativeButton("아니오",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                builder.show();


            }
        });












        //for drawer
        btnFindStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetWorktime.this, FindStyleActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnFindArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetWorktime.this, FindArtistActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnMypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetWorktime.this, MypageDesign.class);
                startActivity(intent);
                finish();
            }
        });

//        btnChatList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SetWorktime.this, TattooistChatList.class);
//                startActivity(intent);
//                finish();
//            }
//        });

        btnBookingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetWorktime.this, BookingList.class);
                startActivity(intent);
                finish();
            }
        });

        btnUploadDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetWorktime.this, UploadDesign.class);
                startActivity(intent);
                finish();
            }
        });

        btnUploadWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetWorktime.this, UploadWork.class);
                startActivity(intent);
                finish();
            }
        });

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetWorktime.this, LikeDesign.class);
                startActivity(intent);
                finish();
            }
        });

        set_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetWorktime.this, SetWorktime.class);
                startActivity(intent);
            }
        });


        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //로그아웃중이라면 로그인액티비티로 이동
                if(!sp_login.contains("login_ok")){
                    Intent intent = new Intent(SetWorktime.this, LoginActivity.class);
                    startActivity(intent);
                }else{      //로그인 중이라면 sp_login 값 삭제
                    AlertDialog.Builder builder = new AlertDialog.Builder(SetWorktime.this);
                    builder.setTitle("로그아웃 하시겠습니까?");
                    builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //로그아웃시 쉐어드 로그인 정보 삭제
                            editor_login.clear();
                            editor_login.apply();
                            Intent intent = new Intent(SetWorktime.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });
                    builder.show();
                }
            }
        });





    }






}
