package com.example.myapplication.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.SharedData.Tattooist;
import com.example.myapplication.SharedData.User;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;


/**
 * Created by Delma Song on 2019-05-18
 */
public class JoinTattooistNext extends AppCompatActivity {


    //profile 사진 업로드
    Button btn_upload_img;
    ImageView setImage;

    EditText et_introduce;

    Button button_cancel;
    Button button_ok;



    //스트링 형태로 값 받을 변수들
    String s_introduce;
    String address;
    List<String> myStyle;
    String userId;

    String userid;      //회원가입 때 저장한 id 값
    String userpw;      //회원가입 때 저장한 pw 값

    Gson gson;
    String json_tattooist;
    Tattooist tattooist;
    User user;

    //for image upload
    String str_img;    //이미지 string값 저장 변수
    String path;       //uri를 filePath로 변환한 값
    Uri photoUri;
    private File tempFile;


    @Override
    protected void onCreate(@Nullable Bundle savedInstancedState){
        super.onCreate(savedInstancedState);
        setContentView(R.layout.activity_join_tattooist_next);

        btn_upload_img = (Button)findViewById(R.id.btn_upload_img);
        setImage = (ImageView)findViewById(R.id.setImage);

        et_introduce = (EditText)findViewById(R.id.et_introduce);

        button_cancel = (Button)findViewById(R.id.button_cancel);
        button_ok = (Button)findViewById(R.id.btn_ok);




//        //쓰던 데이터 있으면 받아오기
//        Intent intent = getIntent();
//        userid = intent.getStringExtra("userid_key");
//        userpw = intent.getStringExtra("userpw_key");


        //저장된 데이터 있을시 복원
        if(savedInstancedState != null){
            String data_introduce = savedInstancedState.getString(s_introduce);

            et_introduce.setText(data_introduce);
        }




        btn_upload_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAlbum();
            }
        });








        //취소 버튼 선택시
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(JoinTattooistNext.this, JoinActivity.class);
                startActivity(intent);
                finish();

            }
        });







        Intent intent2 = getIntent();
        address = intent2.getStringExtra("address");
//        myStyle = intent2.getStringArrayListExtra("style");
        userId = intent2.getStringExtra("user_key");




        //확인 버튼 선택시
        button_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //프로필 사진 미업로드시
                if(str_img == null){
                    Toast.makeText(JoinTattooistNext.this, "프로필 사진을 업로드해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                //간단한 소개 미입력시
                if(et_introduce.getText().toString().length() == 0){
                    Toast.makeText(JoinTattooistNext.this, "간단한 소개를 입력해주세요", Toast.LENGTH_SHORT).show();
                    et_introduce.requestFocus();
                    return;
                }



                tattooist = new Tattooist();
                tattooist.setId(userId);
                tattooist.setProfile(et_introduce.getText().toString());
                tattooist.setProfilePhoto(path);
//                tattooist.setStyle(myStyle);
                tattooist.setAddress(address);






                //shared preference에 데이터 삽입
                SharedPreferences sp_tattooist = getSharedPreferences("tattooist", MODE_PRIVATE);       //tattooist라는 이름의 xml 파일 선언
                SharedPreferences.Editor editor = sp_tattooist.edit();      //쉐어드 tattooist에서 쓰는 에디터 선언

                gson = new Gson();

                //to save
                json_tattooist = gson.toJson(tattooist);
                editor.putString(userId, json_tattooist);     //앞 액티비티에서 저장한 유저의 아이디를 키값으로 설정해 타투이스트의 정보를 저장,,,,,,
                editor.apply();
                Log.d("Tatt_check", sp_tattooist.getAll().toString());




                Intent intent3 = new Intent(JoinTattooistNext.this, SetWorktimeWhenJoin.class);
                intent3.putExtra("user_key", userId);
                startActivity(intent3);
                finish();
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
                setImage();
            }
        }

    }


    private void setImage(){

        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);

        setImage.setImageBitmap(originalBm);           //imageView에 이미지 삽입

    }





    private void goToAlbum(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
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
        String img_name = userId;
        return new File(getExternalCacheDir(), img_name+".jpg"); // context needed
    }





    //onPause()실행 되기 전에 여기 들려서 번들에 데이터 저장한다.
    @Override
    protected void onSaveInstanceState(Bundle savedBundle){
        super.onSaveInstanceState(savedBundle);

        String data_introduce = et_introduce.getText().toString();

        savedBundle.putString("userintroduce_key", data_introduce);
    }


}
