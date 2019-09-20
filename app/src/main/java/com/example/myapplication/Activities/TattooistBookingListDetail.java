package com.example.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.myapplication.R;

public class TattooistBookingListDetail extends AppCompatActivity {

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
    Button btnChatList;
    Button btnBookingList;
    Button btnUploadDesign;
    Button btnUploadWork;
    Button btnLike;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_list_tattooist_detail);


        buttonBack = (ImageButton)findViewById(R.id.buttonBack);

        //드로어 내부 버튼
        btnFindStyle = (Button)findViewById(R.id.btn_find_style);
        btnFindArtist = (Button)findViewById(R.id.btn_find_artist);
        btnMypage = (Button)findViewById(R.id.btn_mypage);
        btnChatList = (Button)findViewById(R.id.btn_chat_list);
        btnBookingList = (Button)findViewById(R.id.btn_booking_list);
        btnUploadDesign = (Button)findViewById(R.id.btn_upload_design);
        btnUploadWork = (Button)findViewById(R.id.btn_upload_work);
        btnLike = (Button)findViewById(R.id.btn_like);

        dl = (DrawerLayout)findViewById(R.id.dl_booking_list_tattooist_detail);
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
                Intent intent = new Intent(TattooistBookingListDetail.this, FindStyleActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnFindArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TattooistBookingListDetail.this, FindArtistActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnMypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TattooistBookingListDetail.this, TattooistMypageDesign.class);
                startActivity(intent);
                finish();
            }
        });

        btnChatList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TattooistBookingListDetail.this, TattooistChatList.class);
                startActivity(intent);
                finish();
            }
        });

        btnBookingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TattooistBookingListDetail.this, BookingList.class);
                startActivity(intent);
                finish();
            }
        });

        btnUploadDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TattooistBookingListDetail.this, UploadDesign.class);
                startActivity(intent);
                finish();
            }
        });

        btnUploadWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TattooistBookingListDetail.this, UploadWork.class);
                startActivity(intent);
                finish();
            }
        });

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TattooistBookingListDetail.this, LikeDesign.class);
                startActivity(intent);
                finish();
            }
        });

    }


}
