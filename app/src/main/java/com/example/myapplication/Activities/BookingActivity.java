package com.example.myapplication.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.myapplication.Adapters.SetTimeAdapter;
import com.example.myapplication.R;
import com.example.myapplication.SharedData.Design;
import com.example.myapplication.SharedData.TattooerBooking;
import com.example.myapplication.SharedData.Tattooist;
import com.example.myapplication.SharedData.TattooistBooking;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class BookingActivity extends AppCompatActivity {

    ImageButton btn_back;
    Button buttonToPay, btn_prev, btn_next;
    ToggleButton tb_1, tb_2, tb_3, tb_4, tb_5, tb_6, tb_7;

    TextView tv_name, tv_size, tv_money, tv_spend_time;
    ImageView img_target;
    TextView selected_time;

    Spinner sp_body_part;
    Spinner size;

    ArrayList partList;
    ArrayList sizeList;
    ArrayAdapter partAdapter;
    ArrayAdapter sizeAdapter;

    //intent값 받는 변수
    String i_img, i_name, i_size, i_price, i_time, i_design_id;

    //tb값 선택시 날짜값 담을 변수
    String str_tb1, str_tb2, str_tb3, str_tb4, str_tb5, str_tb6, str_tb7;

    public static String doItDate;

    Calendar calendar = Calendar.getInstance();
    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("dd");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 ");
    SimpleDateFormat timeFormat = new SimpleDateFormat("yyyyMMddHHmmSS");
    String nowTime = timeFormat.format(date);

    String tattooistId, userId, tattooerBookingId, tattooistBookingId;
    public static String nowTattooist;

    //recyclerView
    RecyclerView recyclerView;
    SetTimeAdapter adapter;
    List<String> dataList = new ArrayList<>();

//ㅇㅖ약하기 액티에서 필요한 쉐어드 정보
    //디자인-이미지,이름,사이즈,가격,예상시간       >> 인텐트로 받아왔음 쉐어드 노필요
    //워크타임 - 타투이스트가 설정한 요일, 시간값 끌어오기      >> ㅇ
    //타투이스트 - 타투이스트 누구껀지
    //유저 - 로그인한 유저의 이름으로 예약정보에 저장
    //타투어예약현황- 타투 받는 사람으로서 예약정보 저장. 시술부위, 사이즈 등
    //타투이스트 예약현황 - 타투 해주는 사람으로서 예약정보 저장



    //for sharedPreference
    SharedPreferences sp_tattooist, sp_login, sp_date, sp_tattooer_booking, sp_tattooist_booking;
    Gson gson = new Gson();
    String tattooist_json, tattooer_booking_json, tattooist_booking_json;
    SharedPreferences.Editor tattooer_booking_editor, tattooist_booking_editor;
    Tattooist tattooist;
    TattooerBooking tattooerBooking;
    TattooistBooking tattooistBooking;

    List<String> dateList, timeList;


    //쉐어드 저장정보 담을 변수
    String tmp_bodyPart, tmp_size, tmp_time;

    //기존 예약된 날짜정보 담을 변수
    public static List<String> occupiedDateList = new ArrayList<>();

    //tattooist_booking.xml에서 키값 담을 리스트
    List<String> bookingKeys = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        btn_back = (ImageButton)findViewById(R.id.btn_back);
        buttonToPay = (Button)findViewById(R.id.buttonToPay);

        sp_body_part = (Spinner)findViewById(R.id.sp_body_part);
        size = (Spinner)findViewById(R.id.size);

        btn_prev = (Button)findViewById(R.id.btn_prev);
        btn_next = (Button)findViewById(R.id.btn_next);
        selected_time = (TextView)findViewById(R.id.selected_time);

        img_target = (ImageView)findViewById(R.id.img_target);
        tv_name = (TextView)findViewById(R.id.tv_name);
        tv_size = (TextView)findViewById(R.id.tv_size);
        tv_money = (TextView)findViewById(R.id.tv_money);
        tv_spend_time = (TextView)findViewById(R.id.tv_spend_time);


        tb_1 = (ToggleButton)findViewById(R.id.tb_1);
        tb_2 = (ToggleButton)findViewById(R.id.tb_2);
        tb_3 = (ToggleButton)findViewById(R.id.tb_3);
        tb_4 = (ToggleButton)findViewById(R.id.tb_4);
        tb_5 = (ToggleButton)findViewById(R.id.tb_5);
        tb_6 = (ToggleButton)findViewById(R.id.tb_6);
        tb_7 = (ToggleButton)findViewById(R.id.tb_7);

        tb_1.setEnabled(false);
        tb_2.setEnabled(false);
        tb_3.setEnabled(false);
        tb_4.setEnabled(false);
        tb_5.setEnabled(false);
        tb_6.setEnabled(false);
        tb_7.setEnabled(false);


        //인텐트로 보내준 도안 정보 받기
        Intent intent = getIntent();
        i_img = intent.getStringExtra("selected_img");
        i_name = intent.getStringExtra("selected_name");
        i_size = intent.getStringExtra("selected_size");
        i_price = intent.getStringExtra("selected_price");
        i_time = intent.getStringExtra("selected_time");
        tattooistId = intent.getStringExtra("tattooist_id");
        i_design_id = intent.getStringExtra("design_id");

        nowTattooist = tattooistId;


        //인텐트 받은 값 기반으로 세팅
        Uri uri = Uri.parse(i_img);
        img_target.setImageURI(uri);
        tv_name.setText(i_name);
        tv_size.setText(i_size);
        tv_money.setText(i_price);
        tv_spend_time.setText(i_time);


        tattooerBooking = new TattooerBooking();
        tattooistBooking = new TattooistBooking();


        //쉐어드 초기화
        sp_tattooist = getSharedPreferences("tattooist", MODE_PRIVATE);
        sp_login = getSharedPreferences("login_ok", MODE_PRIVATE);
        sp_date = getSharedPreferences("workDate", MODE_PRIVATE);
        sp_tattooer_booking = getSharedPreferences("tattooer_booking", MODE_PRIVATE);
        sp_tattooist_booking = getSharedPreferences("tattooist_booking", MODE_PRIVATE);

        //타투이스트의 이미 예약된 요일, 시간 정보 가져오기
        getAllEntry();



        //로그인된 유저 아이디 세팅
        userId = sp_login.getString("login_ok", "");


        //shared editor
        tattooer_booking_editor = sp_tattooer_booking.edit();
        tattooist_booking_editor = sp_tattooist_booking.edit();



        dateList = new ArrayList<>();
        timeList = new ArrayList<>();


        //타투이스트의 시술 가능한 요일, 시간 값 쉐어드 불러오기
        tattooist_json = sp_date.getString(tattooistId, "");
        tattooist = gson.fromJson(tattooist_json, Tattooist.class);


//        //타투어 예약정보 저장하기(키값 - 예약자 id + 현재시간)
//        tattooer_booking_json = gson.toJson(tattooerBooking);




        Log.d("시술가능요일", dateList.toString());
        Log.d("시술가능시간", timeList.toString());


        //쉐어드에 설정된 시술가능한 요일에만 토글버튼 클릭 가능.
        if(tattooist.getWorkDate().contains("월")){
            tb_2.setEnabled(true);
        }if(tattooist.getWorkDate().contains("화")){
            tb_3.setEnabled(true);
        }if(tattooist.getWorkDate().contains("수")){
            tb_4.setEnabled(true);
        }if(tattooist.getWorkDate().contains("목")){
            tb_5.setEnabled(true);
        }if(tattooist.getWorkDate().contains("금")){
            tb_6.setEnabled(true);
        }if(tattooist.getWorkDate().contains("토")){
            tb_7.setEnabled(true);
        }if(tattooist.getWorkDate().contains("일")){
            tb_1.setEnabled(true);
        }


        //이번주 월요일 날짜값 기준으로 토글 버튼에 날짜값 세팅
        getCurMonday();

        //날짜 다음 버튼 클릭. 7일씩 이동
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNextMonday();
            }
        });

        //날짜 이전 버튼 클릭. 7일씩 이동
        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPrevMonday();
            }
        });


        //토글버튼 눌러서 날짜 선택하면 텍스트뷰 담아주기
        tb_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_time.setText(str_tb1);
                Toast.makeText(getApplicationContext(), str_tb1+"을(를) 선택하셨습니다", Toast.LENGTH_SHORT).show();
                doItDate = str_tb1;
                adapter.notifyDataSetChanged();
            }
        });
        tb_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_time.setText(str_tb2);
                Toast.makeText(getApplicationContext(), str_tb2+"을(를) 선택하셨습니다", Toast.LENGTH_SHORT).show();
                doItDate = str_tb2;
                adapter.notifyDataSetChanged();

            }
        });
        tb_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_time.setText(str_tb3);
                Toast.makeText(getApplicationContext(), str_tb3+"을(를) 선택하셨습니다", Toast.LENGTH_SHORT).show();
                doItDate = str_tb3;
                adapter.notifyDataSetChanged();

            }
        });
        tb_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_time.setText(str_tb4);
                Toast.makeText(getApplicationContext(), str_tb4+"을(를) 선택하셨습니다", Toast.LENGTH_SHORT).show();
                doItDate = str_tb4;
                adapter.notifyDataSetChanged();

            }
        });
        tb_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_time.setText(str_tb5);
                Toast.makeText(getApplicationContext(), str_tb5+"을(를) 선택하셨습니다", Toast.LENGTH_SHORT).show();
                doItDate = str_tb5;
                adapter.notifyDataSetChanged();

            }
        });
        tb_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_time.setText(str_tb6);
                Toast.makeText(getApplicationContext(), str_tb6+"을(를) 선택하셨습니다", Toast.LENGTH_SHORT).show();
                doItDate = str_tb6;
                adapter.notifyDataSetChanged();

            }
        });
        tb_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_time.setText(str_tb7);
                Toast.makeText(getApplicationContext(), str_tb7+"을(를) 선택하셨습니다", Toast.LENGTH_SHORT).show();
                doItDate = str_tb7;
                adapter.notifyDataSetChanged();

            }
        });




        //리사이클러뷰 적용
        recyclerView = findViewById(R.id.rv_set_time);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        recyclerView.setLayoutManager(layoutManager);

        for(int i=0; i<tattooist.getWorkTime().size(); i++){
            timeList.add(tattooist.getWorkTime().get(i));
            Log.d("타투이스트리스트", tattooist.getWorkTime().get(i)+"");
            Log.d("타임리스트", timeList.get(i)+"");
            dataList.add(tattooist.getWorkTime().get(i));
        }

        adapter = new SetTimeAdapter(this, dataList);
        recyclerView.setAdapter(adapter);
//        tmp_time = adapter.settingTime();



        //스피너 선택
        partList = new ArrayList();
        partList.add("부위를 선택해주세요");
        partList.add("팔 상단");
        partList.add("팔 하단");
        partList.add("다리 상단");
        partList.add("다리 하단");
        partList.add("손등");
        partList.add("발등");
        partList.add("등");
        partList.add("기타 부위");

        partAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, partList);

        sp_body_part.setAdapter(partAdapter);
        sp_body_part.setSelection(0, false);
        sp_body_part.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(BookingActivity.this, partList.get(position)+"(이)가 선택되었습니다", Toast.LENGTH_SHORT).show();
                tmp_bodyPart = partList.get(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        sizeList = new ArrayList();
        sizeList.add("사이즈를 선택해주세요");
        sizeList.add("5cm 미만(미니)");
        sizeList.add("5cm-7cm");
        sizeList.add("8cm-10cm");
        sizeList.add("11cm-15cm");
        sizeList.add("15cm 이상");

        sizeAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, sizeList);

        size.setAdapter(sizeAdapter);
        size.setSelection(0, false);
        size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(BookingActivity.this, sizeList.get(position)+"가 선택되었습니다", Toast.LENGTH_SHORT).show();
                tmp_size = sizeList.get(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();

//        tattooerBooking = new TattooerBooking();
//        tattooistBooking = new TattooistBooking();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //예약하기 버튼 클릭
        buttonToPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //타투어 예약정보 키값 (타투어 아이디 + 현재시간)
                tattooerBookingId = userId + nowTime;
                //타투이스트 예약정보 키값(타투이스트 아이디 + 현재시간)
                tattooistBookingId = tattooistId + nowTime;

                tmp_time = adapter.settingTime();



                //시술 가능 날짜 미선택시
                if(doItDate == null){
                    Toast.makeText(getApplicationContext(), "시술 가능한 날짜를 선택해 주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                //시술 부위 미선택시
                if(tmp_bodyPart == null){
                    Toast.makeText(getApplicationContext(), "시술 부위를 선택해 주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                //시술 사이즈 미선택시
                if(tmp_size == null){
                    Toast.makeText(getApplicationContext(), "시술 사이즈를 선택해 주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                //시술 시간 미선택시
                if(tmp_time == null){
                    Toast.makeText(getApplicationContext(), "시술 시간을 선택해 주세요", Toast.LENGTH_SHORT).show();
                    return;
                }


                Intent intent = new Intent(BookingActivity.this, PayActivity.class);
                intent.putExtra("booking_img", i_img);
                intent.putExtra("booing_img_id", i_design_id);
                intent.putExtra("booking_body_part", tmp_bodyPart);
                intent.putExtra("booking_size", tmp_size);
                intent.putExtra("booking_price", i_price);
                intent.putExtra("booking_date", doItDate);
                intent.putExtra("booking_time", tmp_time);
                intent.putExtra("tattooer_id", userId);
                intent.putExtra("tattooer_booking_id", tattooerBookingId);
                intent.putExtra("tattooist_booking_id", tattooistBookingId);
                intent.putExtra("tattooist_id", tattooistId);

                startActivity(intent);
            }
        });

        //도안 화면에서 정보 받아야 함. 사이즈, 부위, 센치, 아티스트 등등

    }


    //오늘날짜 기준으로 월요일 날짜값 얻기
    protected void getCurMonday(){

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);


        tb_2.setText(formatter.format(calendar.getTime()));
        tb_2.setTextOn(formatter.format(calendar.getTime()));
        tb_2.setTextOff(formatter.format(calendar.getTime()));
        str_tb2 = sdf.format(calendar.getTime());
        str_tb2 = str_tb2 + "월";
        if(calendar.getTime().compareTo(date) == -1 || calendar.getTime().compareTo(date) == 0) {
            tb_2.setEnabled(false);
        }

        calendar.add(Calendar.DATE, 1);
        tb_3.setText(formatter.format(calendar.getTime()));
        tb_3.setTextOn(formatter.format(calendar.getTime()));
        tb_3.setTextOff(formatter.format(calendar.getTime()));
        str_tb3 = sdf.format(calendar.getTime());
        str_tb3 = str_tb3 + "화";
//        if(calendar.getTime().compareTo(date) == -1|| calendar.getTime().compareTo(date) == 0) {
//            tb_3.setEnabled(false);
//        }

        calendar.add(Calendar.DATE, 1);
        tb_4.setText(formatter.format(calendar.getTime()));
        tb_4.setTextOn(formatter.format(calendar.getTime()));
        tb_4.setTextOff(formatter.format(calendar.getTime()));
        str_tb4 = sdf.format(calendar.getTime());
        str_tb4 = str_tb4 + "수";
        if(calendar.getTime().compareTo(date) == -1|| calendar.getTime().compareTo(date) == 0) {
            tb_4.setEnabled(false);
        }

        calendar.add(Calendar.DATE, 1);
        tb_5.setText(formatter.format(calendar.getTime()));
        tb_5.setTextOn(formatter.format(calendar.getTime()));
        tb_5.setTextOff(formatter.format(calendar.getTime()));
        str_tb5 = sdf.format(calendar.getTime());
        str_tb5 = str_tb5 + "목";
        if(calendar.getTime().compareTo(date) == -1|| calendar.getTime().compareTo(date) == 0) {
            tb_5.setEnabled(false);
        }

        calendar.add(Calendar.DATE, 1);
        tb_6.setText(formatter.format(calendar.getTime()));
        tb_6.setTextOn(formatter.format(calendar.getTime()));
        tb_6.setTextOff(formatter.format(calendar.getTime()));
        str_tb6 = sdf.format(calendar.getTime());
        str_tb6 = str_tb6 + "금";
        if(calendar.getTime().compareTo(date) == -1|| calendar.getTime().compareTo(date) == 0) {
            tb_6.setEnabled(false);
        }

        calendar.add(Calendar.DATE, 1);
        tb_7.setText(formatter.format(calendar.getTime()));
        tb_7.setTextOn(formatter.format(calendar.getTime()));
        tb_7.setTextOff(formatter.format(calendar.getTime()));
        str_tb7 = sdf.format(calendar.getTime());
        str_tb7 = str_tb7 + "토";
        if(calendar.getTime().compareTo(date) == -1|| calendar.getTime().compareTo(date) == 0) {
            tb_7.setEnabled(false);
        }

        calendar.add(Calendar.DATE, 1);
        tb_1.setText(formatter.format(calendar.getTime()));
        tb_1.setTextOn(formatter.format(calendar.getTime()));
        tb_1.setTextOff(formatter.format(calendar.getTime()));
        str_tb1 = sdf.format(calendar.getTime());
        str_tb1 = str_tb1 + "일";
        if(calendar.getTime().compareTo(date) == -1|| calendar.getTime().compareTo(date) == 0) {
            tb_1.setEnabled(false);
        }
    }

    //오늘날짜 기준으로 다음주 월요일 날짜값 얻기
    protected void getNextMonday(){

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);



        tb_2.setText(formatter.format(calendar.getTime()));
        tb_2.setTextOn(formatter.format(calendar.getTime()));
        tb_2.setTextOff(formatter.format(calendar.getTime()));
        str_tb2 = sdf.format(calendar.getTime());
        str_tb2 = str_tb2 + "월";
        if((calendar.getTime().compareTo(date) == 1 && tattooist.getWorkDate().contains("월")) || calendar.getTime().compareTo(date) == 0) {
            tb_2.setEnabled(true);
        }

        calendar.add(Calendar.DATE, 1);
        tb_3.setText(formatter.format(calendar.getTime()));
        tb_3.setTextOn(formatter.format(calendar.getTime()));
        tb_3.setTextOff(formatter.format(calendar.getTime()));
        str_tb3 = sdf.format(calendar.getTime());
        str_tb3 = str_tb3 + "화";
        if((calendar.getTime().compareTo(date) == 1 && tattooist.getWorkDate().contains("화")) || calendar.getTime().compareTo(date) == 0) {
            tb_3.setEnabled(true);
        }

        calendar.add(Calendar.DATE, 1);
        tb_4.setText(formatter.format(calendar.getTime()));
        tb_4.setTextOn(formatter.format(calendar.getTime()));
        tb_4.setTextOff(formatter.format(calendar.getTime()));
        str_tb4 = sdf.format(calendar.getTime());
        str_tb4 = str_tb4 + "수";
        if((calendar.getTime().compareTo(date) == 1 && tattooist.getWorkDate().contains("수")) || calendar.getTime().compareTo(date) == 0) {
            tb_4.setEnabled(true);
        }

        calendar.add(Calendar.DATE, 1);
        tb_5.setText(formatter.format(calendar.getTime()));
        tb_5.setTextOn(formatter.format(calendar.getTime()));
        tb_5.setTextOff(formatter.format(calendar.getTime()));
        str_tb5 = sdf.format(calendar.getTime());
        str_tb5 = str_tb5 + "목";
        if((calendar.getTime().compareTo(date) == 1 && tattooist.getWorkDate().contains("목")) || calendar.getTime().compareTo(date) == 0) {
            tb_5.setEnabled(true);
        }

        calendar.add(Calendar.DATE, 1);
        tb_6.setText(formatter.format(calendar.getTime()));
        tb_6.setTextOn(formatter.format(calendar.getTime()));
        tb_6.setTextOff(formatter.format(calendar.getTime()));
        str_tb6 = sdf.format(calendar.getTime());
        str_tb6 = str_tb6 + "금";
        if((calendar.getTime().compareTo(date) == 1 && tattooist.getWorkDate().contains("금")) || calendar.getTime().compareTo(date) == 0) {
            tb_6.setEnabled(true);
        }

        calendar.add(Calendar.DATE, 1);
        tb_7.setText(formatter.format(calendar.getTime()));
        tb_7.setTextOn(formatter.format(calendar.getTime()));
        tb_7.setTextOff(formatter.format(calendar.getTime()));
        str_tb7 = sdf.format(calendar.getTime());
        str_tb7 = str_tb7 + "토";
        if((calendar.getTime().compareTo(date) == 1 && tattooist.getWorkDate().contains("토")) || calendar.getTime().compareTo(date) == 0) {
            tb_7.setEnabled(true);
        }

        calendar.add(Calendar.DATE, 1);
        tb_1.setText(formatter.format(calendar.getTime()));
        tb_1.setTextOn(formatter.format(calendar.getTime()));
        tb_1.setTextOff(formatter.format(calendar.getTime()));
        str_tb1 = sdf.format(calendar.getTime());
        str_tb1 = str_tb1 + "일";
        if((calendar.getTime().compareTo(date) == 1 && tattooist.getWorkDate().contains("일")) || calendar.getTime().compareTo(date) == 0) {
            tb_1.setEnabled(true);
        }
    }

    //오늘 기준으로 지난주 월요일 날짜값 얻기
    protected void getPrevMonday(){

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);


        calendar.add(Calendar.DATE, -14);
        tb_2.setText(formatter.format(calendar.getTime()));
        tb_2.setTextOn(formatter.format(calendar.getTime()));
        tb_2.setTextOff(formatter.format(calendar.getTime()));
        str_tb2 = sdf.format(calendar.getTime());
        str_tb2 = str_tb2 + "월";
        if(calendar.getTime().compareTo(date) == -1 || calendar.getTime().compareTo(date) == 0) {
            tb_2.setEnabled(false);
        }

        calendar.add(Calendar.DATE, 1);
        tb_3.setText(formatter.format(calendar.getTime()));
        tb_3.setTextOn(formatter.format(calendar.getTime()));
        tb_3.setTextOff(formatter.format(calendar.getTime()));
        str_tb3 = sdf.format(calendar.getTime());
        str_tb3 = str_tb3 + "화";
        if(calendar.getTime().compareTo(date) == -1 || calendar.getTime().compareTo(date) == 0) {
            tb_3.setEnabled(false);
        }

        calendar.add(Calendar.DATE, 1);
        tb_4.setText(formatter.format(calendar.getTime()));
        tb_4.setTextOn(formatter.format(calendar.getTime()));
        tb_4.setTextOff(formatter.format(calendar.getTime()));
        str_tb4 = sdf.format(calendar.getTime());
        str_tb4 = str_tb4 + "수";
        if(calendar.getTime().compareTo(date) == -1 || calendar.getTime().compareTo(date) == 0) {
            tb_4.setEnabled(false);
        }

        calendar.add(Calendar.DATE, 1);
        tb_5.setText(formatter.format(calendar.getTime()));
        tb_5.setTextOn(formatter.format(calendar.getTime()));
        tb_5.setTextOff(formatter.format(calendar.getTime()));
        str_tb5 = sdf.format(calendar.getTime());
        str_tb5 = str_tb5 + "목";
        if(calendar.getTime().compareTo(date) == -1 || calendar.getTime().compareTo(date) == 0) {
            tb_5.setEnabled(false);
        }

        calendar.add(Calendar.DATE, 1);
        tb_6.setText(formatter.format(calendar.getTime()));
        tb_6.setTextOn(formatter.format(calendar.getTime()));
        tb_6.setTextOff(formatter.format(calendar.getTime()));
        str_tb6 = sdf.format(calendar.getTime());
        str_tb6 = str_tb6 + "금";
        if(calendar.getTime().compareTo(date) == -1 || calendar.getTime().compareTo(date) == 0) {
            tb_6.setEnabled(false);
        }

        calendar.add(Calendar.DATE, 1);
        tb_7.setText(formatter.format(calendar.getTime()));
        tb_7.setTextOn(formatter.format(calendar.getTime()));
        tb_7.setTextOff(formatter.format(calendar.getTime()));
        str_tb7 = sdf.format(calendar.getTime());
        str_tb7 = str_tb7 + "토";
        if(calendar.getTime().compareTo(date) == -1 || calendar.getTime().compareTo(date) == 0) {
            tb_7.setEnabled(false);
        }

        calendar.add(Calendar.DATE, 1);
        tb_1.setText(formatter.format(calendar.getTime()));
        tb_1.setTextOn(formatter.format(calendar.getTime()));
        tb_1.setTextOff(formatter.format(calendar.getTime()));
        str_tb1 = sdf.format(calendar.getTime());
        str_tb1 = str_tb1 + "일";
        if(calendar.getTime().compareTo(date) == -1 || calendar.getTime().compareTo(date) == 0) {
            tb_1.setEnabled(false);
        }

    }


    protected void getAllEntry(){

        Map<String, ?> allEntries = sp_tattooist_booking.getAll();

        for(Map.Entry<String, ?> entry : allEntries.entrySet()){

            tattooist_booking_json = sp_tattooist_booking.getString(entry.getKey(), "");
            tattooistBooking = gson.fromJson(tattooist_booking_json, TattooistBooking.class);


            //tattooist_booking.xml의 모든 키를 bookingKeys에 담음
            bookingKeys.add(entry.getKey());
            //해당 타투이스트의 예약 요일정보만 담음
            if(entry.getKey().contains(tattooistId)){
                occupiedDateList.add(tattooistBooking.getDate());
            }
            Log.d("해당도안타투이스트누구", tattooistId);
            Log.d("occupiedDateList", occupiedDateList.toString());


        }
    }
}
