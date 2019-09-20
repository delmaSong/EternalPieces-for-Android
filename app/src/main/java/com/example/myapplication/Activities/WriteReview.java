package com.example.myapplication.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.SharedData.Like;
import com.example.myapplication.SharedData.Review;
import com.example.myapplication.SharedData.Tattooist;
import com.example.myapplication.SharedData.User;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WriteReview extends AppCompatActivity {


    ImageButton buttonBack;
    Button btn_ok;

    EditText review_title, review_contents;
    RatingBar review_star;
    ImageButton btn_upload_img;
    ImageView set_img;


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
    SharedPreferences sp_user, sp_login, sp_review;
    SharedPreferences.Editor editor_login, editor_review;
    Gson gson;
    String json_user, json_review;
    String userId;
    String tattooistId;

    User user;
    Review review = new Review();

    //값 받을 변수
    float stars;

    String str_img;    //이미지 string값 저장 변수
    String path;       //uri를 filePath로 변환한 값
    Uri photoUri;
    private File tempFile;

    //현재시간 입력
    SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmSS");
    SimpleDateFormat reviewDate = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm");
    Date date = new Date();
    String nowTime = format.format(date);
    String setTime = reviewDate.format(date);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);


        buttonBack = (ImageButton)findViewById(R.id.buttonBack);
        btn_ok = (Button)findViewById(R.id.btn_ok);

        review_title = (EditText)findViewById(R.id.review_title);
        review_contents = (EditText)findViewById(R.id.review_contents);
        review_star = (RatingBar)findViewById(R.id.review_star);
        btn_upload_img = (ImageButton)findViewById(R.id.btn_upload_img);
        set_img = (ImageView)findViewById(R.id.set_img);

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

        dl = (DrawerLayout)findViewById(R.id.dl_write_review);
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

        Intent intent = getIntent();
        tattooistId = intent.getStringExtra("tattooistId");


        //settings for shared and login-out
        sp_login = getSharedPreferences("login_ok", MODE_PRIVATE);
        editor_login = sp_login.edit();
        userId = sp_login.getString("login_ok", "");

        sp_user = getSharedPreferences("user", MODE_PRIVATE);
        sp_review = getSharedPreferences("review", MODE_PRIVATE);
        editor_review = sp_review.edit();

        gson = new Gson();
        json_user = sp_user.getString(userId,"");
        user = gson.fromJson(json_user, User.class);

//        json_review = gson.toJson(review);


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


        //rating bar 별점 주기
        review_star.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                stars = rating;
            }
        });


        btn_upload_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAlbum();
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


        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //리뷰 제목 미작성시
                if(review_title.getText().length() == 0){
                    Toast.makeText(getApplicationContext(), "리뷰 제목을 입력해주세요", Toast.LENGTH_SHORT).show();
                    review_title.requestFocus();
                    return;
                }

                //리뷰 내용 미작성시
                if(review_contents.getText().length() == 0){
                    Toast.makeText(getApplicationContext(), "리뷰 내용을 입력해주세요", Toast.LENGTH_SHORT).show();
                    review_contents.requestFocus();
                    return;
                }

                //사진 미업로드시
                if(path == null){
                    Toast.makeText(getApplicationContext(), "리뷰 사진을 업로드 해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(WriteReview.this);
                builder.setTitle("리뷰를 업로드 하시겠습니까? 리뷰는 업로드 후 수정이 불가능합니다");
                builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //쉐어드에 데이터 저장
                        //리뷰 제목 저장
                        review.setTitle(review_title.getText().toString());
                        //리뷰 내용 저장
                        review.setContents(review_contents.getText().toString());
                        //리뷰 별점 저장
                        if(stars == 0){
                            review.setStar(0);
                        }else {
                            review.setStar(stars);
                        }
                        //리뷰 사진 저장
                        if(path != null){
                            review.setPhoto(path);
                        }
                        //리뷰 작성 시간 저장
                        review.setDate(setTime);
                        //리뷰 작성자 저장
                        review.setWriter(userId);
                        //시술해준 타투이스트 아이디 저장
                        if(tattooistId != null){

                            review.setTattooistId(tattooistId);
                        }

                        json_review = gson.toJson(review);
                        editor_review.putString(tattooistId+nowTime, json_review);
                        editor_review.apply();

                        Intent intent = new Intent(WriteReview.this, MainAfterLoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                builder.show();


            }
        });










        //for drawers
        btnFindStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WriteReview.this, FindStyleActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnFindArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WriteReview.this, FindArtistActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnMypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WriteReview.this, MypageDesign.class);
                startActivity(intent);
                finish();
            }
        });

//        btnChatList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(WriteReview.this, TattooistChatList.class);
//                startActivity(intent);
//                finish();
//            }
//        });

        btnBookingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WriteReview.this, BookingList.class);
                startActivity(intent);
                finish();
            }
        });

        btnUploadDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WriteReview.this, UploadDesign.class);
                startActivity(intent);
                finish();
            }
        });

        btnUploadWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WriteReview.this, UploadWork.class);
                startActivity(intent);
                finish();
            }
        });

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WriteReview.this, LikeDesign.class);
                startActivity(intent);
                finish();
            }
        });

        set_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WriteReview.this, SetWorktime.class);
                startActivity(intent);
            }
        });


        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //로그아웃중이라면 로그인액티비티로 이동
                if(!sp_login.contains("login_ok")){
                    Intent intent = new Intent(WriteReview.this, LoginActivity.class);
                    startActivity(intent);
                }else{      //로그인 중이라면 sp_login 값 삭제
                    AlertDialog.Builder builder = new AlertDialog.Builder(WriteReview.this);
                    builder.setTitle("로그아웃 하시겠습니까?");
                    builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //로그아웃시 쉐어드 로그인 정보 삭제
                            editor_login.clear();
                            editor_login.apply();
                            Intent intent = new Intent(WriteReview.this, MainActivity.class);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK) {

            if (requestCode == 1) {
                Log.d("카메라 OnActivityResult", "리퀘스트 코드 1");

                photoUri = data.getData();
                str_img = photoUri.toString();
                Log.d("URIIIII", str_img);

                path= getImagePathFromInputStreamUri(photoUri);
                Log.d("uri의 패스 찾기 ", path);
                Cursor cursor = null;

                try {

                    String[] proj = {MediaStore.Images.Media.DATA};

                    assert photoUri != null;
                    cursor = getContentResolver().query(photoUri, proj, null, null, null);

                    assert cursor != null;
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    Log.d("카메라 OnActivityResult22", String.valueOf(column_index));


                    cursor.moveToFirst();
                    tempFile = new File(cursor.getString(column_index));
                    Log.d("where is CURSOR", tempFile.toString());



                } finally {
                    if (cursor != null)
                        cursor.close();
                }
                setImagePath();
            }
        }

    }







    private void setImagePath(){

        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);

        set_img.setImageBitmap(originalBm);           //imageView에 이미지 삽입
//        review_img.setText(str_img);
    }





    private void goToAlbum(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");

//        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivityForResult(intent, 1);
    }







    public String getImagePathFromInputStreamUri(Uri uri) {
        InputStream inputStream = null;
        String filePath = null;

        if (uri.getAuthority() != null) {
            try {
                inputStream = getContentResolver().openInputStream(uri); // context needed
                File photoFile = createTemporalFileFrom(inputStream);

                filePath = photoFile.getPath();

            } catch (FileNotFoundException e) {
                // log
            } catch (IOException e) {
                // log
            }finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return filePath;
    }

    private File createTemporalFileFrom(InputStream inputStream) throws IOException {
        File targetFile = null;

        if (inputStream != null) {
            int read;
            byte[] buffer = new byte[8 * 1024];

            targetFile = createTemporalFile();
            OutputStream outputStream = new FileOutputStream(targetFile);

            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            outputStream.flush();

            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return targetFile;
    }

    private File createTemporalFile() {
        String img_name = "tempFile"+nowTime;
        return new File(getExternalCacheDir(), img_name+".jpg"); // context needed
    }





}
