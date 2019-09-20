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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.myapplication.Adapters.TattooistMPWorkAdapter;
import com.example.myapplication.R;
import com.example.myapplication.SharedData.Like;
import com.example.myapplication.SharedData.Tattooist;
import com.example.myapplication.SharedData.User;
import com.example.myapplication.SharedData.Work;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Delma Song on 2019-05-29
 */
public class MypageWork extends AppCompatActivity {


    ImageButton buttonBack;

    Button btn_design, btn_work, btn_review;

    TextView title_id, tv_tattooist, tv_comment;
    ImageView img_profile, img_background;

    ImageButton btn_edit;



    //for shared
    SharedPreferences sp_work, sp_user, sp_login, sp_tattooist, sp_like;
    SharedPreferences.Editor editor, editor_login, like_editor;
    Gson gson;
    String json_work, json_tattooist, json_user;
    Work work;
    Tattooist tattooist;
    String tattooistId;

    User user;

    //for like
    JSONArray jsonArray = new JSONArray();

    Like like;
    String dataLike;


    //for recycler view
    List<Work> dataList;
    String str_img;
    Uri photoUri;
    String work_key;
    String userId;

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
        setContentView(R.layout.activity_mypage_work);


        buttonBack = (ImageButton)findViewById(R.id.buttonBack);

        title_id = (TextView)findViewById(R.id.title_id);
        tv_tattooist = (TextView)findViewById(R.id.tv_tattooist);
        tv_comment = (TextView)findViewById(R.id.tv_comment);
        img_profile = (ImageView)findViewById(R.id.img_profile);
        img_background = (ImageView)findViewById(R.id.img_background);

        btn_design = (Button)findViewById(R.id.btn_design);
        btn_work = (Button)findViewById(R.id.btn_work);
        btn_review = (Button)findViewById(R.id.btn_review);
        btn_edit = (ImageButton)findViewById(R.id.btn_edit);
        btn_edit.setVisibility(View.INVISIBLE);

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

        dl = (DrawerLayout)findViewById(R.id.dl_mypage_tattooist_work);
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




        Intent intent = getIntent();
        tattooistId = intent.getStringExtra("tattooist_key");
        Log.d("도안에서받은인텐트타투아이디", tattooistId);

        sp_work = getSharedPreferences("work", MODE_PRIVATE);
        editor = sp_work.edit();

        sp_tattooist = getSharedPreferences("tattooist", MODE_PRIVATE);
        json_tattooist = sp_tattooist.getString(tattooistId, "");
        tattooist = gson.fromJson(json_tattooist, Tattooist.class);

        sp_like = getSharedPreferences("like", MODE_PRIVATE);
        like_editor = sp_like.edit();

        //아티스트 찾기 액티비티의 어댑터에서 보내주는 타투이스트 아이디값을 기반으로 안의 내용 세팅해줌
        title_id.setText(tattooist.getId());
        tv_tattooist.setText(tattooist.getId());
        img_profile.setImageURI(tattooist.changeUri());
        tv_comment.setText(tattooist.getProfile());
        if(tattooist.getBackgroundPhoto() != null){

            img_background.setImageURI(tattooist.changeUriForBackground());
        }




        //login된 id랑 보고있는 마이페이지의 타투이스트 아이디값 일치시 편집버튼 생성
        userId = sp_login.getString("login_ok", "");
        if (userId.equals(tattooistId)){
            btn_edit.setVisibility(View.VISIBLE);
        }


        //좋아요 기능 관련
        like = new Like();

        dataLike = sp_like.getString(userId, "");
        Log.d("like쉐어드에 이미담긴값", dataLike);






        //settings for RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rv_work);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);

        dataList = new ArrayList<>();

        getAllEntry();

//        dataList.add(new Work(R.drawable.tattoo1));

        TattooistMPWorkAdapter adapter = new TattooistMPWorkAdapter(dataList);
        recyclerView.setAdapter(adapter);



    }


    protected void getAllEntry(){

        String owner = tattooist.getId();

        Map<String, ?> allEntries = sp_work.getAll();
        for(Map.Entry<String, ?> entry : allEntries.entrySet()){

            json_work = sp_work.getString(entry.getKey(), "");
            work = gson.fromJson(json_work, Work.class);

            photoUri = work.changeUri();
            str_img = photoUri.toString();
            work_key = work.getWorkId();

            if(work_key.contains(owner)){
                dataList.add(new Work(str_img, work_key));
            }
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

        btn_design.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MypageWork.this, TattooistMypageDesign.class);
                intent.putExtra("tattooist_key", tattooistId);
                startActivity(intent);
                finish();
            }
        });

        btn_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MypageWork.this, MypageWork.class);
                intent.putExtra("tattooist_key", tattooistId);
                startActivity(intent);
                finish();
            }
        });

        btn_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MypageWork.this, TattooistMypageReview.class);
                intent.putExtra("tattooist_key", tattooistId);
                startActivity(intent);
                finish();
            }
        });


        btnFindStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MypageWork.this, FindStyleActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnFindArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MypageWork.this, FindArtistActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnMypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MypageWork.this, TattooistMypageDesign.class);
                startActivity(intent);
                finish();
            }
        });

//        btnChatList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(TattooistMypageWork.this, TattooistChatList.class);
//                startActivity(intent);
//                finish();
//            }
//        });

        btnBookingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MypageWork.this, BookingList.class);
                startActivity(intent);
                finish();
            }
        });

        btnUploadDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MypageWork.this, UploadDesign.class);
                startActivity(intent);
                finish();
            }
        });

        btnUploadWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MypageWork.this, UploadWork.class);
                startActivity(intent);
                finish();
            }
        });

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MypageWork.this, LikeDesign.class);
                startActivity(intent);
                finish();
            }
        });
        set_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MypageWork.this, SetWorktime.class);
                startActivity(intent);
            }
        });


        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //로그아웃중이라면 로그인액티비티로 이동
                if(!sp_login.contains("login_ok")){
                    Intent intent = new Intent(MypageWork.this, LoginActivity.class);
                    startActivity(intent);
                }else{      //로그인 중이라면 sp_login 값 삭제
                    AlertDialog.Builder builder = new AlertDialog.Builder(MypageWork.this);
                    builder.setTitle("로그아웃 하시겠습니까?");
                    builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //로그아웃시 쉐어드 로그인 정보 삭제
                            editor_login.clear();
                            editor_login.apply();
                            Intent intent = new Intent(MypageWork.this, MainActivity.class);
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
