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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.myapplication.Adapters.FindArtistAdapter;
import com.example.myapplication.R;
import com.example.myapplication.SharedData.Design;
import com.example.myapplication.SharedData.Like;
import com.example.myapplication.SharedData.Tattooist;
import com.example.myapplication.SharedData.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class FindArtistActivity extends AppCompatActivity {

    Spinner spinnerBig;
    Spinner spinnerSmall;


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


    //값 임시저장 변수
    Uri photoUri;
    String str_img;     //리사이클러뷰에 넣어줄 이미지
    String name;        //리사이클러뷰에 넣어줄 타투이스트 아이디
    String contents;    //간단 소개
    String address;

    //리사이클러뷰에서 도안 삭제시 쓸 변수
    String delete_key;      //삭제할 도안의 키값


    RecyclerView recyclerView;
    FindArtistAdapter adapter;
    List<Tattooist> dataList;


    //shared preference
    SharedPreferences sp_tattooist, sp_user, sp_login;
    SharedPreferences.Editor editor_login;
    Gson gson;
    String json_tattooist, json_user;
    String userId;

    Tattooist tattooist;
    Like like;
    User user;

    //for spinner
    ArrayAdapter<CharSequence> adspin1, adspin2;
    String choice_do="";
    String choice_seo="";






    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_artist);

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

        dl = (DrawerLayout)findViewById(R.id.dl_find_artist);
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
        sp_tattooist = getSharedPreferences("tattooist", MODE_PRIVATE);

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





        //setting for spinner
        spinnerBig = (Spinner)findViewById(R.id.spinnerBig);
        spinnerSmall = (Spinner)findViewById(R.id.spinnerSmall);

        adspin1 = ArrayAdapter.createFromResource(this, R.array.spinner_do, android.R.layout.simple_spinner_dropdown_item);
        adspin1.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinnerBig.setAdapter(adspin1);
        spinnerBig.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(adspin1.getItem(position).equals("지역을 선택해주세요")){
                    choice_do="";
                    adspin2 = ArrayAdapter.createFromResource(FindArtistActivity.this, R.array.spinner_select, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerSmall.setAdapter(adspin2);
                    spinnerSmall.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_seo = "";
                            dataList.clear();
                            adapter.notifyDataSetChanged();
                            getAllEntry();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }else if(adspin1.getItem(position).equals("서울")){
                    choice_do = "서울";
                    adspin2 = ArrayAdapter.createFromResource(FindArtistActivity.this, R.array.spinner_do_seoul, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerSmall.setAdapter(adspin2);
//                    spinnerSmall.setSelection(0, false);
                    spinnerSmall.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(position ==0){
                                dataList.clear();
                                adapter.notifyDataSetChanged();
                                getAllEntry();
                            }else if(position !=0){
                                choice_seo = adspin2.getItem(position).toString();
                                dataList.clear();
                                adapter.notifyDataSetChanged();
                                getAllEntry();
                            }else{
                                return;
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }else if(adspin1.getItem(position).equals("인천")){
                    choice_do = "인천";
                    adspin2 = ArrayAdapter.createFromResource(FindArtistActivity.this, R.array.spinner_do_incheon, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerSmall.setAdapter(adspin2);
                    spinnerSmall.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(position ==0){
                                dataList.clear();
                                adapter.notifyDataSetChanged();
                                getAllEntry();
                            }else if(position !=0){
                                choice_seo = adspin2.getItem(position).toString();
                                dataList.clear();
                                adapter.notifyDataSetChanged();
                                getAllEntry();
                            }else{
                                return;
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }else if(adspin1.getItem(position).equals("부산")){
                    choice_do = "부산";
                    adspin2 = ArrayAdapter.createFromResource(FindArtistActivity.this, R.array.spinner_do_busan, android.R.layout.simple_spinner_dropdown_item);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerSmall.setAdapter(adspin2);
                    spinnerSmall.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(position ==0){
                                dataList.clear();
                                adapter.notifyDataSetChanged();
                                getAllEntry();
                            }else if(position !=0){
                                choice_seo = adspin2.getItem(position).toString();
                                dataList.clear();
                                adapter.notifyDataSetChanged();
                                getAllEntry();
                            }else{
                                return;
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        //리사이클러뷰 적용
        recyclerView = findViewById(R.id.recycler_find_artist);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);


        dataList = new ArrayList<>();
        tattooist = new Tattooist();
        like = new Like();

//        sp_tattooist = getSharedPreferences("tattooist", MODE_PRIVATE);
//        gson = new Gson();

        getAllEntry();

        adapter = new FindArtistAdapter(this, dataList);
        recyclerView.setAdapter(adapter);




    }








    //design.xml에 있는 놈 전부 꺼내서 쉐어드에 넣어줄 메소드
    protected void getAllEntry(){
        userId = tattooist.getId();

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

            if(address.contains(choice_do) && address.contains(choice_seo)){
                dataList.add(new Tattooist(str_img, name, contents));
            }


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


        btnFindStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindArtistActivity.this, FindStyleActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnFindArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindArtistActivity.this, FindArtistActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnMypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindArtistActivity.this, MypageDesign.class);
                startActivity(intent);
                finish();
            }
        });

//        btnChatList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(FindArtistActivity.this, TattooistChatList.class);
//                startActivity(intent);
//                finish();
//            }
//        });

        btnBookingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindArtistActivity.this, BookingList.class);
                startActivity(intent);
                finish();
            }
        });

        btnUploadDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindArtistActivity.this, UploadDesign.class);
                startActivity(intent);
                finish();
            }
        });

        btnUploadWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindArtistActivity.this, UploadWork.class);
                startActivity(intent);
                finish();
            }
        });

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindArtistActivity.this, LikeDesign.class);
                startActivity(intent);
            }
        });

        set_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindArtistActivity.this, SetWorktime.class);
                startActivity(intent);
            }
        });


        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //로그아웃중이라면 로그인액티비티로 이동
                if(!sp_login.contains("login_ok")){
                    Intent intent = new Intent(FindArtistActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else{      //로그인 중이라면 sp_login 값 삭제
                    AlertDialog.Builder builder = new AlertDialog.Builder(FindArtistActivity.this);
                    builder.setTitle("로그아웃 하시겠습니까?");
                    builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //로그아웃시 쉐어드 로그인 정보 삭제
                            editor_login.clear();
                            editor_login.apply();
                            Intent intent = new Intent(FindArtistActivity.this, MainActivity.class);
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
