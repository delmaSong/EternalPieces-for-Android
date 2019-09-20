package com.example.myapplication.Activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.ImageView;

import com.example.myapplication.Adapters.FindStyleAdapter;
import com.example.myapplication.R;
import com.example.myapplication.SharedData.Design;
import com.example.myapplication.SharedData.Like;
import com.example.myapplication.SharedData.User;
import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class FindStyleActivity extends AppCompatActivity {

    //상단 이미지 변경 애니메이션
    static Handler handler;
    ImageView title_img;
    int[] imgArray = {R.drawable.top_image, R.drawable.top_img2, R.drawable.top_img3};
    int index;

    ImageButton backButton;

    DrawerLayout dl;
    View drawer;

    ImageButton btnOpenDrawer;
    ImageButton btnCloseDrawer;

    Button btn_waterColor, btn_lettering, btn_coverUp, btn_blackNgray, btn_crayon;

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
    String str_img;
    String title;
    String contents;
    String design_key;
    String style;

    //리사이클러뷰에서 도안 삭제시 쓸 변수
    String delete_key;      //삭제할 도안의 키값


    RecyclerView recyclerView;
    FindStyleAdapter adapter;
    List<Design> dataList;


    //shared preference
    SharedPreferences sp_design, sp_user, sp_login, sp_like_design;
    SharedPreferences.Editor editor, editor_login, editor_like;
    Gson gson = new Gson();
    String json, json_user, json_like;
    String designId;
    Design design;
    User user;
//    Like like = new Like();

    com.example.myapplication.SharedData.LikeDesign likeDesign = new com.example.myapplication.SharedData.LikeDesign();

    Intent intent;
//    //shared preference for 'like'
//    SharedPreferences sp_like, sp_login;
//    SharedPreferences.Editor like_editor;
//    Like like;
    String userId;


    List<String> setData = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_style_water_color);

        tedPermission();

        title_img = (ImageView)findViewById(R.id.title_img);
        backButton = (ImageButton) findViewById(R.id.buttonBack);

        //스타일 선택 버튼
        btn_waterColor = (Button)findViewById(R.id.btn_waterColor);
        btn_lettering = (Button)findViewById(R.id.btn_lettering);
        btn_coverUp = (Button)findViewById(R.id.btn_coverUp);
        btn_blackNgray = (Button)findViewById(R.id.btn_blackNgray);
        btn_crayon = (Button)findViewById(R.id.btn_crayon);

        //드로어 내부 버튼
        btnFindStyle = (Button)findViewById(R.id.btn_find_style);
        btnFindArtist = (Button)findViewById(R.id.btn_find_artist);
        btnMypage = (Button)findViewById(R.id.btn_mypage);
//        btnChatList = (Button)findViewById(R.id.btn_chat_list);
        btnBookingList = (Button)findViewById(R.id.btn_booking_list);
        btnUploadDesign = (Button)findViewById(R.id.btn_upload_design);
        btnUploadWork = (Button)findViewById(R.id.btn_upload_work);
        btnLike = (Button) findViewById(R.id.btn_like);
        set_time = (Button)findViewById(R.id.set_time);
        btn_logout = (Button)findViewById(R.id.btn_logout);

        dl = (DrawerLayout)findViewById(R.id.dl_find_style);
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

        json_user = sp_user.getString(userId,"");
        user = gson.fromJson(json_user, User.class);

//        sp_like = getSharedPreferences("like", MODE_PRIVATE);
//        editor_like = sp_like.edit();
//        json_like = gson.toJson(like);

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

//        //어댑터에 세팅된 데이터리스트 받아
//            setData = adapter.sendList();


        //상단 이미지 애니메이션
        handler = new Handler(){
          @Override
          public void handleMessage(Message message){
              super.handleMessage(message);
              title_img.setImageResource(imgArray[index]);
          }
        };

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    Message message = handler.obtainMessage();
                    handler.sendMessage(message);
                    index++;
                    if(index >2){
                        index = 0;
                    }
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        th.start();




        //도안 삭제시 인텐트로 디자인 아이디 받음
        intent = getIntent();
        delete_key = intent.getStringExtra("designId");
        Log.d("intent로 받은 designId", delete_key+"");


        //리사이클러뷰 적용
        recyclerView = findViewById(R.id.recycler_find_style);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);


        dataList = new ArrayList<>();

        design = new Design();
        sp_design = getSharedPreferences("design", MODE_PRIVATE);


        if(intent.hasExtra("designId")){
            Log.d("intent에 담긴게 있다", "FindStyle액티");
            deleteDesign();
        }

        getAllEntry();

        adapter = new FindStyleAdapter(this, dataList);
        recyclerView.setAdapter(adapter);




    }





    protected void deleteDesign(){

        Log.d("deleteDesign()", "도안 지우는 메소드 들어왔는가");
        editor = sp_design.edit();
        editor.remove(delete_key);
        Log.d("what is deleteKey", delete_key+"");
        editor.apply();

//            dataList.remove(new Design(delete_title, delete_photo, delete_comment, delete_key));
    }





    //design.xml에 있는 놈 전부 꺼내서 쉐어드에 넣어줄 메소드
    protected void getAllEntry(){
        designId = design.getDesignId();

        Map<String, ?> allEntries = sp_design.getAll();



        for(Map.Entry<String, ?> entry : allEntries.entrySet()){

            json = sp_design.getString(entry.getKey(), "");
            design = gson.fromJson(json, Design.class);

            //변수에 쉐어드에서 꺼낸 데이터 저장해주기
            title = design.getName();
            contents = design.getComment();
            photoUri = design.changeUri();
            str_img = photoUri.toString();
            design_key = design.getDesignId();
            style = design.getStyle();

            Log.d("put Shared_title", title);
            Log.d("put Shared_contents", contents);
            Log.d("put Shared_photo", str_img);
            Log.d("put Shared_style", style);

            if(style.equals("수채화")){

                dataList.add(new Design(title, str_img, contents, design_key));
            }




            Log.d("entry keys", entry.getKey());


        }
    }






    protected void onResume(){
        super.onResume();




        //뒤로가기 버튼
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //for choice style
        btn_waterColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindStyleActivity.this, FindStyleActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_blackNgray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindStyleActivity.this, FindStyleBGActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_coverUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindStyleActivity.this, FindStyleCoverUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_crayon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindStyleActivity.this, FindStyleCrayonActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_lettering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindStyleActivity.this, FindStyleLetteringActivity.class);
                startActivity(intent);
                finish();
            }
        });









        //for drawer
        btnFindStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindStyleActivity.this, FindStyleActivity.class);
                startActivity(intent);
                finish();

            }
        });

        btnFindArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindStyleActivity.this, FindArtistActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnMypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindStyleActivity.this, MypageDesign.class);
                startActivity(intent);
                finish();
            }
        });

//        btnChatList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(FindStyleActivity.this, TattooistChatList.class);
//                startActivityForResult(intent, 1109);
//                dl.closeDrawer(drawer);
//            }
//        });

        btnBookingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindStyleActivity.this, BookingList.class);
                startActivity(intent);
                finish();
            }
        });

        btnUploadDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindStyleActivity.this, UploadDesign.class);
                startActivityForResult(intent, 1111);
                dl.closeDrawer(drawer);
            }
        });

        btnUploadWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindStyleActivity.this, UploadWork.class);
                startActivity(intent);
                finish();
            }
        });

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindStyleActivity.this, LikeDesign.class);
                startActivity(intent);
                finish();
            }
        });

        set_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindStyleActivity.this, SetWorktime.class);
                startActivity(intent);
                finish();
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //로그아웃중이라면 로그인액티비티로 이동
                if(!sp_login.contains("login_ok")){
                    Intent intent = new Intent(FindStyleActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else{      //로그인 중이라면 sp_login 값 삭제
                    AlertDialog.Builder builder = new AlertDialog.Builder(FindStyleActivity.this);
                    builder.setTitle("로그아웃 하시겠습니까?");
                    builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //로그아웃시 쉐어드 로그인 정보 삭제
                            editor_login.clear();
                            editor_login.apply();
                            Intent intent = new Intent(FindStyleActivity.this, MainActivity.class);
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


    public void tedPermission(){

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                //권한 요청 성공
//                Toast.makeText(getApplicationContext(), "권한 허가", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                //권한 요청 실패
//                Toast.makeText(getApplicationContext(), "권한 거부", Toast.LENGTH_SHORT).show();
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage("사진을 업로드하기 위해서는 사진첩 접근 권한이 필요합니다.")
                .setDeniedMessage("[설정] > [권한]에서 권한을 허용할 수 있습니다.")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)     //확인할 권한을 다중 인자로 넣어줌.
                .check();
        Log.d("Permission_Style", TedPermission.TAG.toString());
    }


    @Override
    protected void onPause(){
        super.onPause();

//        //어댑터에 세팅된 데이터리스트 받아
//        setData = adapter.sendList();
//
//        sp_like_design = getSharedPreferences("like_design", MODE_PRIVATE);
//        editor_like = sp_like_design.edit();
//        json_like = gson.toJson(likeDesign);
////        like = gson.fromJson(json_like, Like.class);
//
//        likeDesign.setDesignId(setData);
//        editor_like.putString(userId, json_like);
//        editor_like.apply();
//        Log.d("shared에 잘 들어갈랑가", setData.toString());


    }



}


