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

import com.example.myapplication.Adapters.TattooistMPDesignAdapter;
import com.example.myapplication.R;
import com.example.myapplication.SharedData.Design;
import com.example.myapplication.SharedData.Like;
import com.example.myapplication.SharedData.Tattooist;
import com.example.myapplication.SharedData.User;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Delma Song on 2019-05-29
 */
public class MypageDesign extends AppCompatActivity {
    ImageButton buttonBack;

    Button btn_design, btn_work, btn_review;

    TextView title_id, tv_tattooist, tv_comment;
    ImageView img_profile, img_background;

    ImageButton btn_edit;


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



    //sharedPreference
    SharedPreferences sp_tattooist, sp_login, sp_design, sp_user, sp_like;
    SharedPreferences.Editor editor, like_editor, editor_login;
    Gson gson;
    String json_tattooist, json_design, json_user;
    Tattooist tattooist;
    Design design;
    String tattooistId;


    List<Design> dataList;
    String str_img;
    Uri photoUri;
    String design_key;
    String userId;

    JSONArray jsonArray = new JSONArray();

    Like like;
    String dataLike;
    User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_design);

        buttonBack = (ImageButton)findViewById(R.id.buttonBack);

        title_id = (TextView)findViewById(R.id.title_id);
        tv_tattooist = (TextView)findViewById(R.id.tv_tattooist);
        tv_comment = (TextView)findViewById(R.id.tv_comment);
        img_profile = (ImageView)findViewById(R.id.img_profile);
        img_background = (ImageView)findViewById(R.id.img_background);
        btn_edit = (ImageButton)findViewById(R.id.btn_edit);
        btn_edit.setVisibility(View.INVISIBLE);

        btn_design = (Button)findViewById(R.id.btn_design);
        btn_work = (Button)findViewById(R.id.btn_work);
        btn_review = (Button)findViewById(R.id.btn_review);

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


        dl = (DrawerLayout)findViewById(R.id.dl_mypage_tattooist_design);
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





        //settings for SharedPreference
        sp_tattooist = getSharedPreferences("tattooist", MODE_PRIVATE);
        sp_design = getSharedPreferences("design", MODE_PRIVATE);
        sp_like = getSharedPreferences("like", MODE_PRIVATE);
        like_editor = sp_like.edit();


        Intent intent = getIntent();
        tattooistId = intent.getStringExtra("tattooist_key");
//        Log.d("tattooistId", tattooistId);

        json_tattooist = sp_tattooist.getString(userId, "");
        tattooist = gson.fromJson(json_tattooist, Tattooist.class);
        tattooistId = tattooist.getId();



//        //login된 id랑 보고있는 마이페이지의 타투이스트 아이디값 일치시 편집버튼 생성
//        if (userId.equals(tattooistId)){
            btn_edit.setVisibility(View.VISIBLE);
//        }

        json_design = sp_design.getString(tattooistId, "");
        design = gson.fromJson(json_design, Design.class);

        //아티스트 찾기 액티비티의 어댑터에서 보내주는 타투이스트 아이디값을 기반으로 안의 내용 세팅해줌
        title_id.setText(tattooist.getId());
        tv_tattooist.setText(tattooist.getId());
        img_profile.setImageURI(tattooist.changeUri());
        tv_comment.setText(tattooist.getProfile());
        if(tattooist.getBackgroundPhoto() != null){

            img_background.setImageURI(tattooist.changeUriForBackground());
        }



        //좋아요 기능 관련
        like = new Like();

        if(sp_like.contains(userId)){
            dataLike = sp_like.getString(userId, "");
            Log.d("like쉐어드에 이미담긴값", dataLike);
        }Log.d("좋아요 안눌렀어요", "안좋아요");




        //edit버튼 클릭시 다이얼로그.
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("click btn_edit","마이페이지에서 수정버튼");

                AlertDialog.Builder builder = new AlertDialog.Builder(MypageDesign.this);
                builder.setTitle("수정하시겠습니까?");
                builder.setPositiveButton("수정", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //수정시 인텐트로 들고가야 할 내용들 세팅
                        Intent intent = new Intent(MypageDesign.this, TattooistMypageEdit.class);
                        intent.putExtra("tattooist_key", tattooistId);
                        intent.putExtra("address", tattooist.getAddress());
                        intent.putExtra("profile_photo", tattooist.getProfilePhoto() );
                        intent.putExtra("contents", tattooist.getProfile());
                        intent.putStringArrayListExtra("style", (ArrayList<String>) tattooist.getStyle());
                        if(tattooist.getBackgroundPhoto() != null){
                            intent.putExtra("back_photo", tattooist.getBackgroundPhoto());
                        }
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
        });



        str_img = tattooist.changeUri().toString();



        //settings for RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rv_design);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);

        dataList = new ArrayList<>();

//        dataList.add(new Design(str_img));
        getAllDesignEntry();

        TattooistMPDesignAdapter adapter = new TattooistMPDesignAdapter(dataList);
        recyclerView.setAdapter(adapter);


    }



    //design.xml에 있는 놈 전부 꺼내서 쉐어드에 넣어줄 메소드
    protected void getAllDesignEntry(){

        String owner = tattooist.getId();       //현재 타투이스트 마이페이지 주인

        Map<String, ?> allEntries = sp_design.getAll();

        for(Map.Entry<String, ?> entry : allEntries.entrySet()){

            json_design = sp_design.getString(entry.getKey(), "");
            design = gson.fromJson(json_design, Design.class);


            //변수에 쉐어드에서 꺼낸 데이터 저장해주기
            photoUri = design.changeUri();
            str_img = photoUri.toString();
            design_key = design.getDesignId();

            Log.d("put Shared_photo", str_img);

            //타투이스트 작성자와 현재 타투이스트 마이페이지 주인이 같을 때 리사이클러뷰 뿌려줌
            if(design_key.contains(owner)){
                dataList.add(new Design(str_img, design_key));
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

        btn_design.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MypageDesign.this, MypageDesign.class);
                intent.putExtra("tattooist_key", tattooistId);
                startActivity(intent);
                finish();
            }
        });

        btn_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MypageDesign.this, TattooistMypageWork.class);
                intent.putExtra("tattooist_key", tattooistId);
                Log.d("시술사진가는인텐트tattooistId", tattooistId);
                startActivity(intent);
                finish();
            }
        });

        btn_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MypageDesign.this, TattooistMypageReview.class);
                intent.putExtra("tattooist_key", tattooistId);
                startActivity(intent);
                finish();
            }
        });


        btnFindStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MypageDesign.this, FindStyleActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnFindArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MypageDesign.this, FindArtistActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnMypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MypageDesign.this, MypageDesign.class);
                startActivity(intent);
                finish();
            }
        });

//        btnChatList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(TattooistMypageDesign.this, TattooistChatList.class);
//                startActivity(intent);
//                finish();
//            }
//        });

        btnBookingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MypageDesign.this, BookingList.class);
                startActivity(intent);
                finish();
            }
        });

        btnUploadDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MypageDesign.this, UploadDesign.class);
                startActivity(intent);
                finish();
            }
        });

        btnUploadWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MypageDesign.this, UploadWork.class);
                startActivity(intent);
                finish();
            }
        });

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MypageDesign.this, LikeDesign.class);
                startActivity(intent);
                finish();
            }
        });

        set_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MypageDesign.this, SetWorktime.class);
                startActivity(intent);
            }
        });


        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //로그아웃중이라면 로그인액티비티로 이동
                if(!sp_login.contains("login_ok")){
                    Intent intent = new Intent(MypageDesign.this, LoginActivity.class);
                    startActivity(intent);
                }else{      //로그인 중이라면 sp_login 값 삭제
                    AlertDialog.Builder builder = new AlertDialog.Builder(MypageDesign.this);
                    builder.setTitle("로그아웃 하시겠습니까?");
                    builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //로그아웃시 쉐어드 로그인 정보 삭제
                            editor_login.clear();
                            editor_login.apply();
                            Intent intent = new Intent(MypageDesign.this, MainActivity.class);
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
