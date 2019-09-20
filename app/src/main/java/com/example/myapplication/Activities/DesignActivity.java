package com.example.myapplication.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.myapplication.R;
import com.example.myapplication.SharedData.Design;
import com.example.myapplication.SharedData.Like;
import com.example.myapplication.SharedData.Tattooist;
import com.example.myapplication.SharedData.User;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class DesignActivity extends AppCompatActivity {

//    Button buttonChat;
    Button buttonBooking;
    ImageButton buttonBack;
    ImageButton btn_edit;

    DrawerLayout dl;
    View drawer;

    ImageButton btnOpenDrawer;
    ImageButton btnCloseDrawer;

    TextView ct_design_name, ct_tattooist, ct_size, ct_price, ct_time, tv_comment;
    ImageView img_design, img_profile;
    TextView tv_title;

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



    //sharedPreference
    SharedPreferences sp_design, sp_login, sp_like_design, sp_tattooist, sp_user;
    SharedPreferences.Editor like_editor, editor_login;
    Gson gson = new Gson();
    String design_json, json_user, tattooist_json, json;
    Design design;
    Tattooist tattooist;
    User user;
    String design_key;      //어댑터에서 보내준 디자인 키값 받아서 이걸 기반으로 뿌려줌

    JSONArray jsonArray = new JSONArray();

    String userId;
//    Like like;
    String dataLike;
    com.example.myapplication.SharedData.LikeDesign likeDesign;

    List<String> likeList = new ArrayList<>();
    List<String> alreadyList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design);

//        buttonChat = (Button)findViewById(R.id.buttonChat);
        buttonBooking = (Button)findViewById(R.id.buttonBooking);
        buttonBack = (ImageButton) findViewById(R.id.buttonBack);
        btn_edit = (ImageButton)findViewById(R.id.btn_edit);
        btn_edit.setVisibility(View.INVISIBLE);


        ct_design_name = (TextView)findViewById(R.id.ct_design_name);
        ct_tattooist = (TextView)findViewById(R.id.ct_tattooist);
        ct_size = (TextView)findViewById(R.id.ct_size);
        ct_price = (TextView)findViewById(R.id.ct_price);
        ct_time = (TextView)findViewById(R.id.ct_time);
        img_design = (ImageView)findViewById(R.id.img_design0);
        img_profile = (ImageView)findViewById(R.id.img_profile);
        tv_title =(TextView)findViewById(R.id.tv_title);
        tv_comment = (TextView)findViewById(R.id.tv_comment);


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


        dl = (DrawerLayout)findViewById(R.id.dl_design);
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




        //어댑터에서 보내준 design key값 받음
        Intent intent = getIntent();
        design_key = intent.getStringExtra("design_key");


        //settings for SharedPreference
        sp_login = getSharedPreferences("login_ok", Context.MODE_PRIVATE);
        editor_login = sp_login.edit();

//        if(sp_login.contains("login_ok")){
//            userId = sp_login.getString("login_ok", "");
//        }
        userId = sp_login.getString("login_ok", "");



        sp_like_design = getSharedPreferences("like_design", Context.MODE_PRIVATE);
        like_editor = sp_like_design.edit();

        dataLike = sp_like_design.getString(userId, "");
        likeDesign = gson.fromJson(dataLike, com.example.myapplication.SharedData.LikeDesign.class);
        Log.d("like쉐어드에 이미담긴값", dataLike);

//        likeDesign = new com.example.myapplication.SharedData.LikeDesign();

        //쉐어드에 도안 좋아요 이미 누른 게 있다면
        if(likeDesign != null){
            //alreadyList에 하나씩 넣어라
            if(likeDesign.getDesignId() != null){
                for(int i=0; i<likeDesign.getDesignId().size(); i++){
                    alreadyList.add(likeDesign.getDesignId().get(i));
                }
                Log.d("디자인액티의 alreadyList", alreadyList.toString());
            }
        }


        sp_user = getSharedPreferences("user", MODE_PRIVATE);
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




        //좋아요 버튼
        final ToggleButton tb = (ToggleButton)findViewById(R.id.tb1);
        tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userId == null || userId == ""){
                }else if(userId != null || userId.length() > 0){

                    //이미 좋아요 눌렸던 놈이라면
                    if(alreadyList.contains(design_key)){
                        alreadyList.remove(design_key);
                        tb.setBackgroundDrawable(getResources().getDrawable(R.drawable.no_like));
                        Log.d("안좋아요_디자인액티에서", design_key);
                    }else if(!alreadyList.contains(design_key)){
                        alreadyList.add(design_key);
                        tb.setBackgroundDrawable(getResources().getDrawable(R.drawable.like));
                        Log.d("좋아요_디자인액티에서", design_key);
                    }

                    likeDesign.setDesignId(alreadyList);
                    json = gson.toJson(likeDesign);
                    like_editor.putString(userId, json);
                    like_editor.apply();
                }

            }
        });

        //쉐어드에 담겨진 디자인키값과 뿌려지는 디자인 키값 중 같은 것은 하트 색칠
        if(dataLike.contains(design_key)){
            Log.d("하트표시", design_key);
                tb.setBackgroundDrawable(
                    getResources().getDrawable(R.drawable.like)
                );
        }





        //쉐어드 뭐 쓸건지 설정
        sp_design = getSharedPreferences("design", MODE_PRIVATE);
        sp_tattooist = getSharedPreferences("tattooist", MODE_PRIVATE);


        //design 쉐어드에 저장된 거 gson으로 design객체에 넣어준다
        design_json = sp_design.getString(design_key, "");
        Log.d("design_json", design_json+"");
        design = gson.fromJson(design_json, Design.class);

        tattooist_json = sp_tattooist.getString(design.getTattooist(), "");
        tattooist = gson.fromJson(tattooist_json, Tattooist.class);

        //login_ok 쉐어드에 저장된 현재 로그인한 아이디값 gson으로 userId에 세팅
//        final String userId = sp_login.getString("login_ok", "");
        Log.d("design액티 현재 로그인한 놈", userId+"");
        Log.d("도안 올린놈 아이디", design.getTattooist()+"");
        String writer = design.getTattooist();

        //현재 로그인한 아이디값과 도안의 타투이스트 값 같으면 edit 버튼 보임
        if(userId.equals(writer)){
            Log.d("도안올린놈=로그인한놈", "");
            btn_edit.setVisibility(View.VISIBLE);
        }




        String str_img = design.getPhoto();
        Uri uri = Uri.parse(str_img);
        if(tattooist.getProfilePhoto() != null){
            String str_profile = tattooist.getProfilePhoto();
            Uri uri2 = Uri.parse(str_profile);
            img_profile.setImageURI(uri2);
        }
        Log.d("uri뭐 들어오냐", uri.toString());

        ct_design_name.setText(design.getName());
        ct_time.setText(design.getSpendTime()+" 시간 내외");
        ct_price.setText(design.getPrice()+" 만원");
        ct_tattooist.setText(design.getTattooist());
        ct_size.setText(design.getSize()+" cm");
        tv_title.setText(design.getTattooist());
        img_design.setImageURI(uri);
        tv_comment.setText(design.getComment());


    }

    @Override
    protected void onResume() {
        super.onResume();

        ct_tattooist.setClickable(true);
        ct_tattooist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DesignActivity.this, TattooistMypageDesign.class);
                intent.putExtra("tattooist_key", design.getTattooist().toString());
                startActivity(intent);
            }
        });

        //isSoldOut true면 팔린거 false면 안팔린거
        if(design.isSoldOut()){
            buttonBooking.setEnabled(false);
            buttonBooking.setText("SOLDOUT");
        }else { //도안 안팔렸다면
            if(userId == "" || userId == null){     //로그인 안한 유저는 예약 못해
                buttonBooking.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(DesignActivity.this);
                        builder.setTitle("로그인 하셔야 이용이 가능합니다");
                        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                        builder.show();
                    }
                });
            }else if(design.getTattooist().equals(userId)){ //자기가 올린 도안이면 예약 못해
                buttonBooking.setActivated(false);
                buttonBooking.setBackgroundColor(Color.parseColor("#CDCDCD"));
            }else{
                buttonBooking.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DesignActivity.this, BookingActivity.class);
                        intent.putExtra("selected_img", design.getPhoto() + "");
                        intent.putExtra("selected_name", design.getName() + "");
                        intent.putExtra("selected_size", design.getSize());
                        intent.putExtra("selected_price", design.getPrice());
                        intent.putExtra("selected_time", design.getSpendTime());
                        intent.putExtra("tattooist_id", design.getTattooist());
                        intent.putExtra("design_id", design.getDesignId());
                        startActivity(intent);
                    }
                });
            }
        }
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("click on editBtn", "수정버튼 눌렀음");

                AlertDialog.Builder builder = new AlertDialog.Builder(DesignActivity.this);
                builder.setTitle("원하는 항목을 선택해 주세요");
                builder.setPositiveButton("수정", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //인텐트로 업로드 디자인 액티비티로 이동. 써있던 내용 가지고..
                        Intent intent = new Intent(DesignActivity.this, UploadDesign.class);
                        intent.putExtra("title", design.getName());
                        intent.putExtra("size", design.getSize());
                        intent.putExtra("price", design.getPrice());
                        intent.putExtra("time", design.getSpendTime());
                        intent.putExtra("comment", design.getComment());
                        intent.putExtra("photo", design.getPhoto());
                        intent.putExtra("designId", design.getDesignId());
                        intent.putExtra("style", design.getStyle());
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setNeutralButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //이거 삭제하고,, 파인드 스타일 액티비티로 이동..
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(DesignActivity.this);
                        builder1.setTitle("정말로 삭제하시겠습니까?");
                        builder1.setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("ㄹㅇ삭제버튼 누름", "delete at dialog");
                                //ㄹㅇ 삭제 기능 구현
                                Intent intent = new Intent(DesignActivity.this, FindStyleActivity.class);
                                intent.putExtra("designId", design.getDesignId());
                                Log.d("intent로 보내는 designId", design.getDesignId()+"");
                                startActivity(intent);
                                finish();
                            }
                        });
                        builder1.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                        builder1.show();
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
        });


        //for drawer
        btnFindStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DesignActivity.this, FindStyleActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnFindArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DesignActivity.this, FindArtistActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnMypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DesignActivity.this, MypageDesign.class);
                startActivity(intent);
                finish();
            }
        });

//        btnChatList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(DesignActivity.this, TattooistChatList.class);
//                startActivity(intent);
//                finish();
//            }
//        });

        btnBookingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DesignActivity.this, BookingList.class);
                startActivity(intent);
                finish();
            }
        });

        btnUploadDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DesignActivity.this, UploadDesign.class);
                startActivity(intent);
                finish();
            }
        });

        btnUploadWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DesignActivity.this, UploadWork.class);
                startActivity(intent);
                finish();
            }
        });

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DesignActivity.this, LikeDesign.class);
                startActivity(intent);
                finish();
            }
        });

        set_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DesignActivity.this, SetWorktime.class);
                startActivity(intent);
            }
        });


        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //로그아웃중이라면 로그인액티비티로 이동
                if(!sp_login.contains("login_ok")){
                    Intent intent = new Intent(DesignActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else{      //로그인 중이라면 sp_login 값 삭제
                    AlertDialog.Builder builder = new AlertDialog.Builder(DesignActivity.this);
                    builder.setTitle("로그아웃 하시겠습니까?");
                    builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //로그아웃시 쉐어드 로그인 정보 삭제
                            editor_login.clear();
                            editor_login.apply();
                            Intent intent = new Intent(DesignActivity.this, MainActivity.class);
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
