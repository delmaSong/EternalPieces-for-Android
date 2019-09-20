package com.example.myapplication.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class SetWorktimeWhenJoin extends AppCompatActivity {

    ImageButton btn_back;

    ToggleButton btn_mon, btn_tue, btn_wed, btn_thu, btn_fri, btn_sat, btn_sun;
    ToggleButton btn_9, btn_10, btn_11, btn_12, btn_13, btn_14, btn_15, btn_16, btn_17, btn_18, btn_19, btn_20, btn_21, btn_22, btn_23;

    Button btn_ok, btn_cancel;

    List<String> dateList, timeList;

    String date = "";
    String time = "";
    public Context context;

    SharedPreferences sp_time, sp_date;
    User user;
    Gson gson;
    String json_tattooist;
    Tattooist tattooist;
    String userId;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_worktime_when_join);

        Intent intent =getIntent();
        userId = intent.getStringExtra("user_key");

//        userId = user.getId();

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


        sp_date = getSharedPreferences("workDate", MODE_PRIVATE);






    }




    protected void onResume(){

        super.onResume();


        dateList = new ArrayList<>();
        timeList = new ArrayList<>();


        //shared preference에 가능 요일, 시간 저장
        final SharedPreferences.Editor date_edit = sp_date.edit();


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
                if (btn_mon.isChecked()){
                    btn_mon.setBackgroundColor(Color.parseColor("#F8D362"));
                    dateList.add("월");

                }else {
                    btn_mon.setBackgroundColor(Color.parseColor("#DDDDDD"));
                    dateList.remove("월");
                }
            }
        });

        btn_tue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_tue.isChecked()){
                    btn_tue.setBackgroundColor(Color.parseColor("#F8D362"));
                    dateList.add("화");
                }else {
                    btn_tue.setBackgroundColor(Color.parseColor("#DDDDDD"));
                    dateList.remove("화");
                }
                }
        });

        btn_wed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_wed.isChecked()){
                    btn_wed.setBackgroundColor(Color.parseColor("#F8D362"));
                    dateList.add("수");
                }else {
                    btn_wed.setBackgroundColor(Color.parseColor("#DDDDDD"));
                    dateList.remove("수");
                }
            }
        });

        btn_thu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_thu.isChecked()){
                    btn_thu.setBackgroundColor(Color.parseColor("#F8D362"));
                    dateList.add("목");
                }else {
                    btn_thu.setBackgroundColor(Color.parseColor("#DDDDDD"));
                    dateList.remove("목");
                }
            }
        });

        btn_fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_fri.isChecked()){
                    btn_fri.setBackgroundColor(Color.parseColor("#F8D362"));
                    dateList.add("금");
                }else {
                    btn_fri.setBackgroundColor(Color.parseColor("#DDDDDD"));
                    dateList.remove("금");
                }
            }
        });

        btn_sat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_sat.isChecked()){
                    btn_sat.setBackgroundColor(Color.parseColor("#F8D362"));
                    dateList.add("토");
                }else {
                    btn_sat.setBackgroundColor(Color.parseColor("#DDDDDD"));
                    dateList.remove("토");
                }
            }
        });

        btn_sun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_sun.isChecked()){
                    btn_sun.setBackgroundColor(Color.parseColor("#F8D362"));
                    dateList.add("일");
                }else {
                    btn_sun.setBackgroundColor(Color.parseColor("#DDDDDD"));
                    dateList.remove("일");
                }
            }
        });





        //set workTime with Toggle

        btn_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_9.isChecked()){
                    btn_9.setBackgroundColor(Color.parseColor("#F8D362"));
                    timeList.add("9시");
                }else {
                    btn_9.setBackgroundColor(Color.parseColor("#DDDDDD"));
                    timeList.remove("9시");
                }
            }
        });

        btn_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_10.isChecked()){
                    btn_10.setBackgroundColor(Color.parseColor("#F8D362"));
                    timeList.add("10시");
                }else {
                    btn_10.setBackgroundColor(Color.parseColor("#DDDDDD"));
                    timeList.remove("10시");
                }
            }
        });

        btn_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_11.isChecked()){
                    btn_11.setBackgroundColor(Color.parseColor("#F8D362"));
                    timeList.add("11시");
                }else {
                    btn_11.setBackgroundColor(Color.parseColor("#DDDDDD"));
                    timeList.remove("11시");
                }
            }
        });

        btn_12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_12.isChecked()){
                    btn_12.setBackgroundColor(Color.parseColor("#F8D362"));
                    timeList.add("12시");
                }else {
                    btn_12.setBackgroundColor(Color.parseColor("#DDDDDD"));
                    timeList.remove("12시");
                }
            }
        });

        btn_13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_13.isChecked()){
                    btn_13.setBackgroundColor(Color.parseColor("#F8D362"));
                    timeList.add("13시");
                }else {
                    btn_13.setBackgroundColor(Color.parseColor("#DDDDDD"));
                    timeList.remove("13시");
                }
            }
        });

        btn_14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_14.isChecked()){
                    btn_14.setBackgroundColor(Color.parseColor("#F8D362"));
                    timeList.add("14시");
                }else {
                    btn_14.setBackgroundColor(Color.parseColor("#DDDDDD"));
                    timeList.remove("14시");
                }
            }
        });

        btn_15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_15.isChecked()){
                    btn_15.setBackgroundColor(Color.parseColor("#F8D362"));
                    timeList.add("15시");
                }else {
                    btn_15.setBackgroundColor(Color.parseColor("#DDDDDD"));
                    timeList.remove("15시");
                }
            }
        });

        btn_16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_16.isChecked()){
                    btn_16.setBackgroundColor(Color.parseColor("#F8D362"));
                    timeList.add("16시");
                }else {
                    btn_16.setBackgroundColor(Color.parseColor("#DDDDDD"));
                    timeList.remove("16시");
                }
            }
        });

        btn_17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_17.isChecked()){
                    btn_17.setBackgroundColor(Color.parseColor("#F8D362"));
                    timeList.add("17시");
                }else {
                    btn_17.setBackgroundColor(Color.parseColor("#DDDDDD"));
                    timeList.remove("17시");
                }
            }
        });

        btn_18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_18.isChecked()){
                    btn_18.setBackgroundColor(Color.parseColor("#F8D362"));
                    timeList.add("18시");
                }else {
                    btn_18.setBackgroundColor(Color.parseColor("#DDDDDD"));
                    timeList.remove("18시");
                }
            }
        });

        btn_19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_19.isChecked()){
                    btn_19.setBackgroundColor(Color.parseColor("#F8D362"));
                    timeList.add("19시");
                }else {
                    btn_19.setBackgroundColor(Color.parseColor("#DDDDDD"));
                    timeList.remove("19시");
                }
            }
        });

        btn_20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_20.isChecked()){
                    btn_20.setBackgroundColor(Color.parseColor("#F8D362"));
                    timeList.add("20시");
                }else {
                    btn_20.setBackgroundColor(Color.parseColor("#DDDDDD"));
                    timeList.remove("20시");
                }
            }
        });

        btn_21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_21.isChecked()){
                    btn_21.setBackgroundColor(Color.parseColor("#F8D362"));
                    timeList.add("21시");
                }else {
                    btn_21.setBackgroundColor(Color.parseColor("#DDDDDD"));
                    timeList.remove("21시");
                }
            }
        });

        btn_22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_22.isChecked()){
                    btn_22.setBackgroundColor(Color.parseColor("#F8D362"));
                    timeList.add("22시");
                }else {
                    btn_22.setBackgroundColor(Color.parseColor("#DDDDDD"));
                    timeList.remove("22시");
                }
            }
        });

        btn_23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_23.isChecked()){
                    btn_23.setBackgroundColor(Color.parseColor("#F8D362"));
                    timeList.add("23시");
                }else {
                    btn_23.setBackgroundColor(Color.parseColor("#DDDDDD"));
                    timeList.remove("23시");
                }
            }
        });






        btn_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //요일 선택하지 않았다면
                if(dateList.size() == 0 || dateList == null){
                    Toast.makeText(getApplicationContext(), "시술 가능한 요일을 설정해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                //시간 선택하지 않았다면
                if(timeList.size() == 0 || timeList == null){
                    Toast.makeText(getApplicationContext(), "시술 가능한 시간을 설정해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                tattooist = new Tattooist();
//                tattooist.setWorkDate(dateList);
//                tattooist.setWorkTime(timeList);

                gson = new Gson();

                for(int i=0; i<dateList.size(); i++){
                    date += dateList.get(i).toString();

                }
                for(int j=0; j<timeList.size(); j++){
                    time += timeList.get(j).toString();
                }


                //선택한 시간 확인하는 dialog 생성.
                AlertDialog.Builder builder = new AlertDialog.Builder(SetWorktimeWhenJoin.this);
                builder.setTitle("선택하신 날짜와 시간이 맞으신가요?");
                builder.setMessage(date + time);
                builder.setPositiveButton("네",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tattooist.setWorkDate(dateList);
                                tattooist.setWorkTime(timeList);
                                json_tattooist = gson.toJson(tattooist);
                                date_edit.putString(userId, json_tattooist);
                                date_edit.apply();

                                Log.d("put workdate at sharedd", sp_date.getAll().toString());

                                Intent intent = new Intent(SetWorktimeWhenJoin.this, LoginActivity.class);
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











    }






}
