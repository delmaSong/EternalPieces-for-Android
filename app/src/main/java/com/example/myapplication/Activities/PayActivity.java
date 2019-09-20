package com.example.myapplication.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.SharedData.Design;
import com.example.myapplication.SharedData.TattooerBooking;
import com.example.myapplication.SharedData.Tattooist;
import com.example.myapplication.SharedData.TattooistBooking;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PayActivity extends AppCompatActivity {

    ImageButton btn_back;
    Button btn_pay;
    ImageView img_target;

    TextView tv_user_name, tv_booking_time, tv_address, tv_price, tv_part, tv_size, tv_tattooist;
    EditText et_rq;

    RadioGroup radioGroup;
    RadioButton rb_cash, rb_card;

    //for shared preference
    SharedPreferences sp_tattooer_booking, sp_tattooist_booking, sp_tattooist, sp_design;
    SharedPreferences.Editor tattooer_booking_editor, tattooist_booking_editor, tattooist_editor, design_editor;

    TattooerBooking tattooerBooking;
    TattooistBooking tattooistBooking;
    Tattooist tattooist;
    Design design;

    Gson gson;
    String tattooer_booking_json, tattooist_booking_json, tattooist_json, design_json;

    String i_design_id, i_img, userId, tattooistId, tattooerBookingId, tattooistBookingId, doTattooDate, doTattooTime;
    String tmp_bodyPart, tmp_size, tmp_time, i_price;


    Date date = new Date();
    SimpleDateFormat timeFormat = new SimpleDateFormat("yyyyMMddHHmmSS");
    String nowTime = timeFormat.format(date);

    //design.xml담을 변수..
    String de_comment, de_designId, de_liked, de_name, de_photo, de_price, de_size, de_spendTime, de_style, de_tattooist;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        btn_back = (ImageButton)findViewById(R.id.btn_back);
        btn_pay = (Button)findViewById(R.id.btn_pay);

        img_target = (ImageView)findViewById(R.id.img_target);
        et_rq = (EditText)findViewById(R.id.et_rq);

        tv_user_name = (TextView)findViewById(R.id.tv_user_name);
        tv_booking_time = (TextView)findViewById(R.id.tv_booking_time);
        tv_address = (TextView)findViewById(R.id.tv_address);
        tv_price = (TextView)findViewById(R.id.tv_price);
        tv_part = (TextView)findViewById(R.id.tv_part);
        tv_size = (TextView)findViewById(R.id.tv_size);
        tv_tattooist = (TextView)findViewById(R.id.tv_tattooist);

        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        rb_cash = (RadioButton)findViewById(R.id.rb_cash);
        rb_card = (RadioButton)findViewById(R.id.rb_card);

        Intent intent = getIntent();
        //예약자
        userId = intent.getStringExtra("tattooer_id");
        tv_user_name.setText(userId);
        //날짜/시간
        doTattooDate = intent.getStringExtra("booking_date");
        doTattooTime = intent.getStringExtra("booking_time");
        tv_booking_time.setText(doTattooDate + doTattooTime);
        //총 결제금액
        i_price = intent.getStringExtra("booking_price");
        tv_price.setText(i_price);
        //타투이스트
        tattooistId = intent.getStringExtra("tattooist_id");
        tv_tattooist.setText(tattooistId);
        //부위
        tmp_bodyPart = intent.getStringExtra("booking_body_part");
        tv_part.setText(tmp_bodyPart);
        //크기
        tmp_size = intent.getStringExtra("booking_size");
        tv_size.setText(tmp_size);
        //이미지
        i_img = intent.getStringExtra("booking_img");
        Uri uri = Uri.parse(i_img);
        img_target.setImageURI(uri);
        //도안 아이디
        i_design_id = intent.getStringExtra("booing_img_id");


    }

    @Override
    protected void onResume(){
        super.onResume();

        tattooistBooking = new TattooistBooking();
        tattooerBooking = new TattooerBooking();
        design = new Design();
        gson = new Gson();

        //쉐어드 초기화
        sp_tattooist = getSharedPreferences("tattooist", MODE_PRIVATE);
        sp_tattooer_booking = getSharedPreferences("tattooer_booking", MODE_PRIVATE);
        sp_tattooist_booking = getSharedPreferences("tattooist_booking", MODE_PRIVATE);
        sp_design = getSharedPreferences("design", MODE_PRIVATE);


        //shared editor
        tattooer_booking_editor = sp_tattooer_booking.edit();
        tattooist_booking_editor = sp_tattooist_booking.edit();
        tattooist_editor = sp_tattooist.edit();
        design_editor = sp_design.edit();

        tattooist_json = sp_tattooist.getString(tattooistId, "");
        tattooist = gson.fromJson(tattooist_json, Tattooist.class);
        tv_address.setText(tattooist.getAddress());


        //design.xml에 저장된 정보 가져오기 ( soldout값 저장해주기 위해)
        design_json = sp_design.getString(i_design_id, "");
        design = gson.fromJson(design_json, Design.class);

        de_comment = design.getComment();
        de_designId = design.getDesignId();
        de_name = design.getName();
        de_photo = design.getPhoto();
        de_price = design.getPrice();
        de_size = design.getSize();
        de_spendTime = design.getSpendTime();
        de_style = design.getStyle();
        de_tattooist = design.getTattooist();


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //타투어 예약정보 키값 (타투어 아이디 + 현재시간)
                tattooerBookingId = userId + nowTime;
                //타투이스트 예약정보 키값(타투이스트 아이디 + 현재시간)
                tattooistBookingId = tattooistId + nowTime;

                if(et_rq.length() == 0){
                    Toast.makeText(getApplicationContext(), "요청사항을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }




                final AlertDialog.Builder builder = new AlertDialog.Builder(PayActivity.this);
                builder.setTitle("예약을 완료하시겠습니까?");
                builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
//                    AlertDialog.Builder builder2 = new AlertDialog.Builder(PayActivity.this);
//                    builder2.
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //FOR TATTOOER BOOKING DATA
                        //쉐어드에 예약 유저 정보 저장
                        tattooerBooking.setUserId(userId);
                        //쉐어드에 도안 사진 정보 저장
                        tattooerBooking.setDesign(i_img);
                        //쉐어드에 도안 아이디값 저장
                        tattooerBooking.setDesignId(i_design_id);
                        //쉐어드에 부위정보 저장
                        tattooerBooking.setBodyPart(tmp_bodyPart);
                        //쉐어드에 사이즈 정보 저장
                        tattooerBooking.setSize(tmp_size);
                        //쉐어드에 가격정보 저장
                        tattooerBooking.setPrice(i_price);
                        //쉐어드에 날짜정보 저장
                        tattooerBooking.setDate(doTattooDate);
                        //쉐어드에 시간정보 저장
                        tattooerBooking.setTime(doTattooTime);
                        //쉐어드에 타투이스트 주소 저장
                        tattooerBooking.setAddress(tv_address.getText().toString());
                        //쉐어드에 요청사항 저장
                        tattooerBooking.setBookerComment(et_rq.getText().toString());
                        //쉐어드에 부킹넘버 저장
                        tattooerBooking.setBookingNumber(tattooerBookingId);
                        //쉐어드에 타투이스트 아이디 저장
                        tattooerBooking.setTattooistId(tattooistId);


                        //타투어 예약정보 저장하기(키값 - 예약자 id + 현재시간)
                        tattooer_booking_json = gson.toJson(tattooerBooking);

                        tattooer_booking_editor.putString(tattooerBookingId, tattooer_booking_json);
//                        tattooer_booking_editor.clear();
                        tattooer_booking_editor.apply();


                        //FOR TATTOOIST BOOKING DATA
                        //쉐어드에 예약 유저 정보 저장
                        tattooistBooking.setBookerId(userId);
                        //쉐어드에 도안 사진 정보 저장
                        tattooistBooking.setDesign(i_img);
                        //쉐어드에 도안 아이디값 저장
                        tattooistBooking.setDesignId(i_design_id);
                        //쉐어드에 부위정보 저장
                        tattooistBooking.setBodyPart(tmp_bodyPart);
                        //쉐어드에 사이즈 정보 저장
                        tattooistBooking.setSize(tmp_size);
                        //쉐어드에 가격정보 저장
                        tattooistBooking.setPrice(i_price);
                        //쉐어드에 날짜정보 저장
                        tattooistBooking.setDate(doTattooDate);
                        //쉐어드에 시간정보 저장
                        tattooistBooking.setTime(doTattooTime);
                        //쉐어드에 예약정보 저장
                        tattooistBooking.setBookingId(tattooerBookingId);
                        //쉐어드에 요청사항 저장
                        tattooistBooking.setBookerComment(et_rq.getText().toString());
                        //쉐어드에 타투이스트 저장
                        tattooistBooking.setTattooistId(tattooistId);
                        //쉐어드에 타투이스트 주소 저장
                        tattooistBooking.setAddress(tv_address.getText().toString());

                        //타투이스트 예약정보 저장하기
                        tattooist_booking_json = gson.toJson(tattooistBooking);

                        tattooist_booking_editor.putString(tattooistBookingId, tattooist_booking_json);
//                        tattooist_booking_editor.clear();
                        tattooist_booking_editor.apply();


                        //도안에 솔드아웃 값 매겨서 다시 저장.
                        design.setComment(de_comment);
                        design.setDesignId(de_designId);
                        design.setName(de_name);
                        design.setPhoto(de_photo);
                        design.setPrice(de_price);
                        design.setSize(de_size);
                        design.setSpendTime(de_spendTime);
                        design.setStyle(de_style);
                        design.setTattooist(de_tattooist);
                        design.setSoldOut(true);

                        design_json = gson.toJson(design);

                        design_editor.putString(i_design_id, design_json);
//                        design_editor.clear();
                        design_editor.apply();

                        AlertDialog.Builder builder2 = new AlertDialog.Builder(PayActivity.this);
                        builder2.setTitle("예약이 완료되었습니다. 자세한 사항은 나의 예약현황에서 확인 가능합니다");
                        builder2.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(PayActivity.this, FindStyleActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        builder2.show();
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
    }

}
