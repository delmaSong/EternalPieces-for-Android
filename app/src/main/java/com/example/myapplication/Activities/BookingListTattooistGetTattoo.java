package com.example.myapplication.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ToggleButton;

import com.example.myapplication.Adapters.BookingListAdapter;
import com.example.myapplication.Adapters.TattooerBookingAdapter;
import com.example.myapplication.R;
import com.example.myapplication.SharedData.TattooerBooking;
import com.example.myapplication.SharedData.TattooistBooking;
import com.example.myapplication.SharedData.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Delma Song on 2019-06-01
 */
public class BookingListTattooistGetTattoo extends AppCompatActivity {

    ToggleButton tb_booking;
    ImageButton buttonBack;
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

    List<TattooistBooking> tattooistDataList;
    List<TattooerBooking> tattooerDataList;

    SharedPreferences sp_booking_tattooist, sp_login, sp_user, sp_booking_tattooer;
    SharedPreferences.Editor editor_login;

    Gson gson= new Gson();
    String json, json_user;

    User user;

    TattooistBooking tattooistBooking;
    TattooerBooking tattooerBooking;
    String bk_date, bk_time, bk_price, bk_bodyPart, bk_size, bk_comment, bk_bookerId,bk_bookingId, bk_designId, bk_tattooistId, bk_address, bk_design;

    String userId;
    List<String> userList, tattList;

    public static String reviewUserId;


    RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_list_tattooist_get_tattoo);

        tb_booking = (ToggleButton)findViewById(R.id.tb_booking);
        buttonBack = (ImageButton)findViewById(R.id.buttonBack);

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

        dl = (DrawerLayout)findViewById(R.id.dl_booking_list_tattooer);
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


        //shared preference
        sp_booking_tattooist = getSharedPreferences("tattooist_booking", MODE_PRIVATE);
        sp_booking_tattooer = getSharedPreferences("tattooer_booking", MODE_PRIVATE);
        sp_login = getSharedPreferences("login_ok", MODE_PRIVATE);
        editor_login = sp_login.edit();
        userId = sp_login.getString("login_ok", "");
        reviewUserId = userId;

        sp_user = getSharedPreferences("user",MODE_PRIVATE);

        json_user = sp_user.getString(userId,"");
        user = gson.fromJson(json_user, User.class);


        if(sp_user.contains(userId)){

        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(BookingListTattooistGetTattoo.this);
            builder.setTitle("예약 내역이 없습니다");
            builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            builder.show();
        }


        //for drawers button setting
        //로그인 되어있다면..
        if(!userId.isEmpty()){
            //타투이스트로 로그인한거라면
            if(user.isTon()){
                tb_booking.setTextOff("타투 시술 하는 예약정보");
                tb_booking.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(BookingListTattooistGetTattoo.this, BookingList.class);
                        startActivity(intent);
                        finish();
                        Log.d("intent from GetTattoo", "to BookingList");
                    }
                });

                tb_booking.setTextOn("타투 시술 받는 예약정보");


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



        //settings for recyclerView
        recyclerView = findViewById(R.id.rv_booking_tattooer);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        tattooistDataList = new ArrayList<>();
        tattooerDataList = new ArrayList<>();
        userList = new ArrayList<>();
        tattList = new ArrayList<>();



//        //로그인 유저가 타투이스트라면
//        if(user.isTon()){
//            tattooistBook();
//            Log.d("userIsTattooist", "userIsTattooist");
//        }else{  //타투어라면
//            tattooerBook();
//            Log.d("userIsTattooer", "userIsTattooer");
//        }
        tattooerBook();




    }







    protected void onResume(){
        super.onResume();
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btnFindStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookingListTattooistGetTattoo.this, FindStyleActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnFindArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookingListTattooistGetTattoo.this, FindArtistActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnMypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookingListTattooistGetTattoo.this, MypageDesign.class);
                startActivity(intent);
                finish();
            }
        });

//        btnChatList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(BookingList.this, TattooistChatList.class);
//                startActivity(intent);
//                finish();
//            }
//        });

        btnBookingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookingListTattooistGetTattoo.this, BookingList.class);
                startActivity(intent);
                finish();
            }
        });

        btnUploadDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookingListTattooistGetTattoo.this, UploadDesign.class);
                startActivity(intent);
                finish();
            }
        });

        btnUploadWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookingListTattooistGetTattoo.this, UploadWork.class);
                startActivity(intent);
                finish();
            }
        });

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookingListTattooistGetTattoo.this, LikeDesign.class);
                startActivity(intent);
                finish();
            }
        });

        set_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookingListTattooistGetTattoo.this, SetWorktime.class);
                startActivity(intent);
            }
        });


        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //로그아웃중이라면 로그인액티비티로 이동
                if(!sp_login.contains("login_ok")){
                    Intent intent = new Intent(BookingListTattooistGetTattoo.this, LoginActivity.class);
                    startActivity(intent);
                }else{      //로그인 중이라면 sp_login 값 삭제
                    AlertDialog.Builder builder = new AlertDialog.Builder(BookingListTattooistGetTattoo.this);
                    builder.setTitle("로그아웃 하시겠습니까?");
                    builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //로그아웃시 쉐어드 로그인 정보 삭제
                            editor_login.clear();
                            editor_login.apply();
                            Intent intent = new Intent(BookingListTattooistGetTattoo.this, MainActivity.class);
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

    protected void tattooistBook() {
        Map<String, ?> allEntries = sp_booking_tattooist.getAll();

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {

            json = sp_booking_tattooist.getString(entry.getKey(), "");
            tattooistBooking = gson.fromJson(json, TattooistBooking.class);


            //변수에 쉐어드에서 꺼낸 데이터 저장해주기
            bk_date = tattooistBooking.getDate();
            bk_time = tattooistBooking.getTime();
            bk_price = tattooistBooking.getPrice();
            bk_bodyPart = tattooistBooking.getBodyPart();
            bk_size = tattooistBooking.getSize();
            bk_comment = tattooistBooking.getBookerComment();
            bk_bookerId = tattooistBooking.getBookerId();
//            bk_bookingId = tattooistBooking.getBookingId();
            bk_designId = tattooistBooking.getDesignId();
            bk_tattooistId = tattooistBooking.getTattooistId();
            bk_address = tattooistBooking.getAddress();
            bk_design = tattooistBooking.getDesign();

            if(entry.getKey().contains(userId)){
                tattooistDataList.add(new TattooistBooking(bk_date, bk_time, bk_price, bk_bodyPart, bk_size, bk_comment, bk_bookerId, bk_designId, bk_tattooistId, bk_address, bk_design));
                BookingListAdapter adapter = new BookingListAdapter(tattooistDataList);
                recyclerView.setAdapter(adapter);
            }

            Log.d("userId is", userId);
            Log.d("entry keys", entry.getKey());

        }
    }

    protected void tattooerBook() {
        Map<String, ?> allEntries = sp_booking_tattooer.getAll();

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {

            json = sp_booking_tattooer.getString(entry.getKey(), "");
            tattooerBooking = gson.fromJson(json, TattooerBooking.class);


            //변수에 쉐어드에서 꺼낸 데이터 저장해주기
            bk_date = tattooerBooking.getDate();
            bk_time = tattooerBooking.getTime();
            bk_price = tattooerBooking.getPrice();
            bk_bodyPart = tattooerBooking.getBodyPart();
            bk_size = tattooerBooking.getSize();
            bk_comment = tattooerBooking.getBookerComment();
            bk_bookerId = tattooerBooking.getUserId();
//            bk_bookingId = tattooerBooking.getBookingNumber();
            bk_designId = tattooerBooking.getDesignId();
            bk_tattooistId = tattooerBooking.getTattooistId();
            bk_address = tattooerBooking.getAddress();
            bk_design = tattooerBooking.getDesign();

            if(entry.getKey().contains(userId)){
//                userList.add(entry.getKey());
                tattooerDataList.add(new TattooerBooking(bk_date, bk_time, bk_price, bk_bodyPart, bk_size, bk_comment, bk_bookerId, bk_designId, bk_tattooistId, bk_address, bk_design));
                TattooerBookingAdapter adapter = new TattooerBookingAdapter(tattooerDataList);
                recyclerView.setAdapter(adapter);
            }

            Log.d("userId is", userId);
            Log.d("entry keys", entry.getKey());

        }
    }
}
