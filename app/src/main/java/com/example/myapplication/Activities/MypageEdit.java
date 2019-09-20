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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.SharedData.Tattooist;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by Delma Song on 2019-05-29
 */
public class MypageEdit extends AppCompatActivity {

    TextView title_id, tv_tattooist;

    ImageButton buttonBack;     //edit cancel
    Button btn_edit;       //edit ok

    TextView tv_comment;        //셀프 소개
    Button btn_edit_profile, btn_edit_background;       //프로필사진 수정, 배경사진 수정

    ImageView img_profile, img_background;              //프로필사진, 백그라운드사진 들어갈 자리



    //for intent date temp val
    String contents, profilePhoto, backgroundPhoto, address;
    List<String> style;


    //for shared Preference
    SharedPreferences sp_tattooist;
    SharedPreferences.Editor editor;
    Gson gson;
    String json_tattooist;
    Tattooist tattooist;
    String tattooistId;


    String profile_str_img, back_str_img;    //이미지 string값 저장 변수
    String profile_path, backimg_path;       //uri를 filePath로 변환한 값
    Uri profile_photoUri, back_photoUri;
    private File profile_tempFile, back_tempFile;

    Uri profileUri, backgroundUri;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_edit);


        title_id = (TextView)findViewById(R.id.title_id);
        tv_tattooist = (TextView)findViewById(R.id.tv_tattooist);

        buttonBack = (ImageButton)findViewById(R.id.buttonBack);
        btn_edit = (Button)findViewById(R.id.btn_edit);
        tv_comment = (TextView)findViewById(R.id.tv_comment);
        btn_edit_profile = (Button)findViewById(R.id.btn_edit_profile);
        btn_edit_background=(Button)findViewById(R.id.btn_edit_background);


        img_profile = (ImageView)findViewById(R.id.img_profile);
        img_background = (ImageView)findViewById(R.id.img_background);


        //뒤로가기 버튼 클릭시
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MypageEdit.this);
                builder.setTitle("그냥 나가시겠습니까? 수정하신 내용은 반영되지 않습니다");
                builder.setPositiveButton("나가요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MypageEdit.this, TattooistMypageDesign.class);
                        intent.putExtra("tattooist_key", tattooistId);
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





        //인텐트로 값 받기
        Intent intent = getIntent();
        tattooistId = intent.getStringExtra("tattooist_key");
        contents = intent.getStringExtra("contents");
        profilePhoto = intent.getStringExtra("profile_photo");
        address = intent.getStringExtra("address");
        style = intent.getStringArrayListExtra("style");
        if(intent.hasExtra("back_photo")){
            backgroundPhoto = intent.getStringExtra("back_photo");
            backgroundUri = Uri.parse(backgroundPhoto);
            img_background.setImageURI(backgroundUri);
        }
        Log.d("intent로 받은 값 ID", tattooistId);
        Log.d("intent로 받은 값 간단소개", contents);
        Log.d("intent로 받은 값 프로필포토", profilePhoto);
        Log.d("intent로 받은 값 주소", address);


        //맨 위에 찍힐 작성자 아이디 및 먼저 저장된 나머지 값 끌어와서 세팅
        title_id.setText(tattooistId);
        tv_tattooist.setText(tattooistId);
        profileUri = Uri.parse(profilePhoto);
        img_profile.setImageURI(profileUri);
        tv_comment.setText(contents);




        //프로필 사진 업로드 버튼 클릭시
        btn_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAlbumForProfile();
            }
        });

        //배경사진 업로드 버튼 클릭시
        btn_edit_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAlbumForBackground();
            }
        });




        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //수정 입력한 값 쉐어드에 넣어줌
                Log.d("수정페이지의 간단소개 내용", tv_comment.getText().toString());
                tattooist = new Tattooist();
                tattooist.setId(tattooistId);
                tattooist.setProfile(tv_comment.getText().toString());
                tattooist.setAddress(address);
                tattooist.setStyle(style);

                //수정시 사진 업로드 안하면 기존에 저장되어있던 놈으로 다시 넣어줌
                if(profile_path != null){
                    tattooist.setProfilePhoto(profile_path);
                }else {
                    tattooist.setProfilePhoto(profilePhoto);
                }
                if(backimg_path != null){
                    tattooist.setBackgroundPhoto(backimg_path);
                }else{
                    tattooist.setBackgroundPhoto(backgroundPhoto);
                }



                //쉐어드에 값 저장하기
                sp_tattooist = getSharedPreferences("tattooist", MODE_PRIVATE);
                editor = sp_tattooist.edit();

                gson = new Gson();
                json_tattooist = gson.toJson(tattooist);
                editor.putString(tattooistId, json_tattooist);
                editor.apply();

                Intent intent1 = new Intent(MypageEdit.this, TattooistMypageDesign.class);
                intent1.putExtra("tattooist_key", tattooistId);
                startActivity(intent1);
                finish();

            }
        });


    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK) {

            if (requestCode == 1) {
                Log.d("카메라 OnActivityResult", "리퀘스트 코드 1");

                profile_photoUri = data.getData();
                profile_str_img = profile_photoUri.toString();
                Log.d("URIIIII", profile_str_img);

                profile_path= getImagePathFromInputStreamUri(profile_photoUri, "profile");
                Log.d("uri의 패스 찾기 ", profile_path);
                Cursor cursor = null;

                try {

                    String[] proj = {MediaStore.Images.Media.DATA};

                    assert profile_photoUri != null;
                    cursor = getContentResolver().query(profile_photoUri, proj, null, null, null);

                    assert cursor != null;
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    Log.d("카메라 OnActivityResult22", String.valueOf(column_index));


                    cursor.moveToFirst();
                    profile_tempFile = new File(cursor.getString(column_index));
                    Log.d("where is CURSOR", profile_tempFile.toString());



                } finally {
                    if (cursor != null)
                        cursor.close();
                }
                setProfileImage();
            }

            if (requestCode == 2) {
                Log.d("카메라 OnActivityResult", "리퀘스트 코드 1");

                back_photoUri = data.getData();
                back_str_img = back_photoUri.toString();
                Log.d("URIIIII", back_str_img);

                backimg_path= getImagePathFromInputStreamUri(back_photoUri, "background");
                Log.d("uri의 패스 찾기 ", backimg_path);
                Cursor cursor = null;

                try {

                    String[] proj = {MediaStore.Images.Media.DATA};

                    assert back_photoUri != null;
                    cursor = getContentResolver().query(back_photoUri, proj, null, null, null);

                    assert cursor != null;
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    Log.d("카메라 OnActivityResult22", String.valueOf(column_index));


                    cursor.moveToFirst();
                    back_tempFile = new File(cursor.getString(column_index));
                    Log.d("where is CURSOR", back_tempFile.toString());



                } finally {
                    if (cursor != null)
                        cursor.close();
                }
                setBackgroundImage();
            }
        }

    }







    private void setProfileImage(){

        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap originalBm = BitmapFactory.decodeFile(profile_tempFile.getAbsolutePath(), options);

        img_profile.setImageBitmap(originalBm);           //imageView에 이미지 삽입

    }

    private void setBackgroundImage(){

        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap originalBm = BitmapFactory.decodeFile(back_tempFile.getAbsolutePath(), options);

        img_background.setImageBitmap(originalBm);           //imageView에 이미지 삽입

    }




    private void goToAlbumForProfile(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    private void goToAlbumForBackground(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, 2);
    }







    public String getImagePathFromInputStreamUri(Uri uri, String role) {
        InputStream inputStream = null;
        String filePath = null;

        if(role == "profile") {

            if (uri.getAuthority() != null) {
                try {
                    inputStream = getContentResolver().openInputStream(uri); // context needed
                    File photoFile = createTemporalFileFrom(inputStream, "profile");

                    filePath = photoFile.getPath();

                } catch (FileNotFoundException e) {
                    // log
                } catch (IOException e) {
                    // log
                } finally {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return filePath;
        }else {
            if (uri.getAuthority() != null) {
                try {
                    inputStream = getContentResolver().openInputStream(uri); // context needed
                    File photoFile = createTemporalFileFrom(inputStream, "background");

                    filePath = photoFile.getPath();

                } catch (FileNotFoundException e) {
                    // log
                } catch (IOException e) {
                    // log
                } finally {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return filePath;
        }
    }

    private File createTemporalFileFrom(InputStream inputStream, String role) throws IOException {
        File targetFile = null;

        if(role == "profile") {


            if (inputStream != null) {
                int read;
                byte[] buffer = new byte[8 * 1024];

                targetFile = createTemporalProfileFile();
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
        }else{
            if (inputStream != null) {
                int read;
                byte[] buffer = new byte[8 * 1024];

                targetFile = createTemporalBackgroundFile();
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

    }

    private File createTemporalProfileFile() {
        String img_name = "profile_tempFile" + tattooistId;
        return new File(getExternalCacheDir(), img_name+".jpg"); // context needed
    }
    private File createTemporalBackgroundFile() {
        String img_name = "background_tempFile" + tattooistId;
        return new File(getExternalCacheDir(), img_name+".jpg"); // context needed
    }


}
