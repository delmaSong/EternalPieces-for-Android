package com.example.myapplication.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.myapplication.Adapters.LikeArtistAdapter;
import com.example.myapplication.Adapters.LikeDesignAdapter;
import com.example.myapplication.Items.ItemLikeArtist;
import com.example.myapplication.R;
import com.example.myapplication.SharedData.Like;
import com.example.myapplication.SharedData.Tattooist;
import com.example.myapplication.SharedData.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LikeArtist extends AppCompatActivity {


    ImageButton buttonBack;
    Button btnLikeDesign;

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

    //shared preference
    SharedPreferences sp_tattooist,sp_login, sp_like, sp_user;
    SharedPreferences.Editor editor, editor_login;
    Gson gson;
    String json_tattooist, json_like, json_user;

    Tattooist tattooist;
    String userId;
    List<Tattooist> dataList;
    com.example.myapplication.SharedData.LikeArtist likeArtist;
    User user;

    //값 임시저장 변수
    Uri photoUri;
    String str_img;     //리사이클러뷰에 넣어줄 이미지
    String name;        //리사이클러뷰에 넣어줄 타투이스트 아이디
    String contents;    //간단 소개
    String address;
    List<String> likeId;

//    String likeId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_artist);


        buttonBack = (ImageButton)findViewById(R.id.buttonBack);
        btnLikeDesign = (Button)findViewById(R.id.btn_like_design);


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

        dl = (DrawerLayout)findViewById(R.id.dl_like_tattooist);
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



        sp_like = getSharedPreferences("like_artist", MODE_PRIVATE);
        json_like = sp_like.getString(userId, "");
        likeArtist = gson.fromJson(json_like, com.example.myapplication.SharedData.LikeArtist.class);
        likeId = new ArrayList<>();



        if(sp_like.contains(userId)){
            if(likeArtist.getArtistId() !=null){

                for(int i=0; i<likeArtist.getArtistId().size(); i++){
                    likeId.add(likeArtist.getArtistId().get(i));
                    Log.d("like객체에 담겼는지", likeId+"");
                }
            }
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(LikeArtist.this);
            builder.setTitle("좋아한 아티스트가 없습니다");
            builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            builder.show();
        }



        sp_tattooist = getSharedPreferences("tattooist", MODE_PRIVATE);

        RecyclerView recyclerView = findViewById(R.id.rv_like_artist);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        dataList = new ArrayList<>();

        getAllEntry();

        LikeArtistAdapter adapter = new LikeArtistAdapter(dataList);
        recyclerView.setAdapter(adapter);



    }

    protected void getAllEntry(){

        Map<String, ?> allEntries = sp_tattooist.getAll();


        for(Map.Entry<String, ?> entry : allEntries.entrySet()){

            json_tattooist = sp_tattooist.getString(entry.getKey(), "");
            tattooist = gson.fromJson(json_tattooist, Tattooist.class);

            //변수에 쉐어드에서 꺼낸 데이터 저장해주기
            name = tattooist.getId();
            contents = tattooist.getProfile();
            photoUri = tattooist.changeUri();
            str_img = photoUri.toString();
            address = tattooist.getAddress();


            Log.d("shared에 있는 아티스트 이름", name);
            Log.d("shared에 있는 아티스트 소개", contents);
            Log.d("shared에 있는 아티스트 주소", address);
            Log.d("shared에 있는 아티스트 사진uri", str_img);

//            if(likeId.indexOf(name) > -1){
//                dataList.add(new Tattooist(str_img, name, contents));
//            }else {
//                return;
//            }
            for(int i=0; i<likeId.size(); i++){
                String aa = likeId.get(i);
                Log.d("aa", aa);
                if(aa.equals(name)){
                    dataList.add(new Tattooist(str_img, name, contents));
                }
            }
            Log.d("좋아요아티스트들어갈놈들", dataList.toString());


            Log.d("entry keys", entry.getKey());


        }
    }


    protected void onResume(){
        super.onResume();
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnLikeDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LikeArtist.this, LikeDesign.class);
                startActivity(intent);
                finish();
            }
        });


        btnFindStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LikeArtist.this, FindStyleActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnFindArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LikeArtist.this, FindArtistActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnMypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LikeArtist.this, MypageDesign.class);
                startActivity(intent);
                finish();
            }
        });

//        btnChatList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LikeArtist.this, TattooistChatList.class);
//                startActivity(intent);
//                finish();
//            }
//        });

        btnBookingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LikeArtist.this, BookingList.class);
                startActivity(intent);
                finish();
            }
        });

        btnUploadDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LikeArtist.this, UploadDesign.class);
                startActivity(intent);
                finish();
            }
        });

        btnUploadWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LikeArtist.this, UploadWork.class);
                startActivity(intent);
                finish();
            }
        });

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LikeArtist.this, LikeDesign.class);
                startActivity(intent);
            }
        });

        set_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LikeArtist.this, SetWorktime.class);
                startActivity(intent);
            }
        });


        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //로그아웃중이라면 로그인액티비티로 이동
                if(!sp_login.contains("login_ok")){
                    Intent intent = new Intent(LikeArtist.this, LoginActivity.class);
                    startActivity(intent);
                }else{      //로그인 중이라면 sp_login 값 삭제
                    AlertDialog.Builder builder = new AlertDialog.Builder(LikeArtist.this);
                    builder.setTitle("로그아웃 하시겠습니까?");
                    builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //로그아웃시 쉐어드 로그인 정보 삭제
                            editor_login.clear();
                            editor_login.apply();
                            Intent intent = new Intent(LikeArtist.this, MainActivity.class);
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
