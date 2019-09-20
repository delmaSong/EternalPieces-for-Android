package com.example.myapplication.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.SharedData.Design;
import com.example.myapplication.SharedData.User;
import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class UploadDesign extends AppCompatActivity{

    public Context context;

    ImageButton buttonBack;
    Button btn_ok;
    ImageView imageView;
    Button btn_upload_img;
    EditText tv_design_name;
    EditText et_price;
    EditText et_time;
    EditText et_size;
    Spinner sp_style;
    String selectedSpinner = "";     //선택된 스피너 값 저장
    EditText et_comment;

    ArrayList styleList;
    ArrayAdapter arrayAdapter;

    Design design;
    SharedPreferences sp_design, sp_user, sp_login;
    SharedPreferences.Editor editor, editor_login;
    Gson gson = new Gson();
    String json, json_user;
    String userId;
    String designId;        //도안 키값
    String noEditId;        //인텐트로 받아온 디자인값 덮어씌워줄 변수
    String style;

    User user;

    String str_img;    //이미지 string값 저장 변수
    String path;       //uri를 filePath로 변환한 값
    Uri photoUri;
    private File tempFile;

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



    //현재시간 입력
    SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmSS");
    Date date = new Date();
    String nowTime = format.format(date);

    Intent intent;

    //입력값 숫자인지 확인하기 위해
    String intResult="";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_design);

        tedPermission();


        buttonBack = (ImageButton)findViewById(R.id.buttonBack);
        imageView = (ImageView)findViewById(R.id.imageView);
        btn_upload_img = (Button)findViewById(R.id.btn_upload_img);
        tv_design_name = (EditText)findViewById(R.id.tv_design_name);
        et_price = (EditText)findViewById(R.id.et_price);
        et_time = (EditText)findViewById(R.id.et_time);
        et_size = (EditText)findViewById(R.id.et_size);
        btn_ok = (Button)findViewById(R.id.btn_ok);
        et_comment = (EditText)findViewById(R.id.et_comment);

        //spinner
        styleList = new ArrayList();

        styleList.add("선택해주세요");
        styleList.add("레터링");
        styleList.add("블랙앤그레이");
        styleList.add("수채화");
        styleList.add("커버업");
        styleList.add("크레용");

        arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, styleList);
        sp_style = (Spinner)findViewById(R.id.sp_style);
        sp_style.setAdapter(arrayAdapter);
        sp_style.setSelection(0, false);
        sp_style.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), styleList.get(position)+"가 선택되었습니다", Toast.LENGTH_SHORT).show();
                selectedSpinner = styleList.get(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        //이미지 업로드 버튼 클릭
        btn_upload_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAlbum();
            }
        });



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


        dl = (DrawerLayout)findViewById(R.id.dl_upload_design);
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



        //수정하러 들어왔을때 인텐트로 보내준거 받음
        intent = getIntent();
        if(intent.hasExtra("title")){

            tv_design_name.setText(intent.getStringExtra("title"));
            et_size.setText(intent.getStringExtra("size"));
            et_comment.setText(intent.getStringExtra("comment"));
            et_time.setText(intent.getStringExtra("time"));
            et_price.setText(intent.getStringExtra("price"));
            noEditId = intent.getStringExtra("designId");
            style = intent.getStringExtra("style");
//        Log.d("noEditId", noEditId);

            str_img = intent.getStringExtra("photo");
            Uri uri = Uri.parse(str_img);
            imageView.setImageURI(uri);
        }



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

            imageView.setImageBitmap(originalBm);           //imageView에 이미지 삽입

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

















    protected void onResume(){
        super.onResume();



        //sharedPreference 초기화
        sp_design = getSharedPreferences("design", MODE_PRIVATE);
        sp_user = getSharedPreferences("login_ok", MODE_PRIVATE);

        //shared editor 초기화
        editor = sp_design.edit();

        design = new Design();

        //login_ok로 저장된 밸류값(userid) 가져옴
        userId = sp_user.getString("login_ok", "");



        designId = userId + nowTime;




        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //사진 미업로드시
                if(str_img == null){
                    Toast.makeText(getApplicationContext(), "사진을 업로드해 주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                //도안 이름 미설정시
                if(tv_design_name.getText().length() == 0){
                    Toast.makeText(getApplicationContext(), "도안 이름을 입력해 주세요", Toast.LENGTH_SHORT).show();
                    tv_design_name.requestFocus();
                    return;
                }
                //사이즈 미입력시
                if(et_size.getText().length() == 0){
                    Toast.makeText(getApplicationContext(), "사이즈를 입력해 주세요", Toast.LENGTH_SHORT).show();
                    et_size.requestFocus();
                    return;
                }else if(!isNumber(et_size.getText().toString())){
                    Toast.makeText(getApplicationContext(), "사이즈란에 숫자만 입력해 주세요", Toast.LENGTH_SHORT).show();
                    et_size.requestFocus();
                    return;
                }
                //가격 미입력시
                if(et_price.getText().length() == 0){
                    Toast.makeText(getApplicationContext(), "가격을 입력해 주세요", Toast.LENGTH_SHORT).show();
                    et_price.requestFocus();
                    return;
                }else if(!isNumber(et_price.getText().toString())){
                    Toast.makeText(getApplicationContext(), "가격란에 숫자만 입력해 주세요", Toast.LENGTH_SHORT).show();
                    et_price.requestFocus();
                    return;
                }
                //예상 소요시간 미입력시
                if(et_time.getText().length() == 0){
                    Toast.makeText(getApplicationContext(), "예상 소요시간을 입력해 주세요", Toast.LENGTH_SHORT).show();
                    et_time.requestFocus();
                    return;
                }else if(!isNumber(et_time.getText().toString())){
                    Toast.makeText(getApplicationContext(), "소요시간란에 숫자만 입력해 주세요", Toast.LENGTH_SHORT).show();
                    et_time.requestFocus();
                    return;
                }
                //간략한 설명 미입력시
                if(et_comment.getText().length() == 0){
                    Toast.makeText(getApplicationContext(), "간략한 설명을 입력해 주세요", Toast.LENGTH_SHORT).show();
                    et_comment.requestFocus();
                    return;
                }
                if(selectedSpinner.equals("")){
                    Toast.makeText(getApplicationContext(), "스타일을 선택해 주세요", Toast.LENGTH_SHORT).show();
                    return;
                }else if(selectedSpinner.equals("선택해주세요")){
                    Toast.makeText(getApplicationContext(), "스타일을 선택해 주세요", Toast.LENGTH_SHORT).show();
                    return;
                }






                //쉐어드에 데이터 저장
                design.setStyle(selectedSpinner);
                design.setName(tv_design_name.getText().toString());
                design.setSize(et_size.getText().toString());
                design.setSpendTime(et_time.getText().toString());
                design.setPrice(et_price.getText().toString());
                design.setComment(et_comment.getText().toString());
                design.setPhoto(path);
                design.setTattooist(userId);


                //intent로 들어온 값 있을 때만 수행.. 아니면 exception 난다
                if(intent.hasExtra("title")){
                    //수정하러 들어온거면 기존 쓰던 designId값 쓴다. 아닐때만 새로운 디자인아이디값
                    if(noEditId.length() > 0){
                        Log.d("원래 디자인 아이디 쓴다", noEditId);
                        designId = noEditId;
                    }else{
                        Log.d("새로운 디자인 아이디 쓴다", designId);
                    }
                }

                design.setDesignId(designId);



                //프로그레스 다이얼로그
                CheckTypeTask task = new CheckTypeTask();
                task.execute();



                //gson 초기화 및 shared에 데이터 save
//                gson = new Gson();
                json = gson.toJson(design);     //design(java Object) gson을 통해 json 형식으로 바꿔
                editor.putString(designId, json);
                editor.apply();

                Log.d("set upload Design", sp_design.getAll().toString());



                Intent intent = new Intent(UploadDesign.this, FindStyleActivity.class);
                startActivity(intent);
                finish();



            }
        });


        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });









        //for drawer
        btnFindStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UploadDesign.this, FindStyleActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnFindArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UploadDesign.this, FindArtistActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnMypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UploadDesign.this, MypageDesign.class);
                startActivity(intent);
                finish();
            }
        });

//        btnChatList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(UploadDesign.this, TattooistChatList.class);
//                startActivity(intent);
//                finish();
//            }
//        });

        btnBookingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UploadDesign.this, BookingList.class);
                startActivity(intent);
                finish();
            }
        });

        btnUploadDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UploadDesign.this, UploadDesign.class);
                startActivity(intent);
                finish();
            }
        });

        btnUploadWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UploadDesign.this, UploadWork.class);
                startActivity(intent);
                finish();
            }
        });

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UploadDesign.this, LikeDesign.class);
                startActivity(intent);
                finish();
            }
        });

        set_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UploadDesign.this, SetWorktime.class);
                startActivity(intent);
            }
        });


        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //로그아웃중이라면 로그인액티비티로 이동
                if(!sp_login.contains("login_ok")){
                    Intent intent = new Intent(UploadDesign.this, LoginActivity.class);
                    startActivity(intent);
                }else{      //로그인 중이라면 sp_login 값 삭제
                    AlertDialog.Builder builder = new AlertDialog.Builder(UploadDesign.this);
                    builder.setTitle("로그아웃 하시겠습니까?");
                    builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //로그아웃시 쉐어드 로그인 정보 삭제
                            editor_login.clear();
                            editor_login.apply();
                            Intent intent = new Intent(UploadDesign.this, MainActivity.class);
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
                Toast.makeText(getApplicationContext(), "권한 허가", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                //권한 요청 실패
                Toast.makeText(getApplicationContext(), "권한 거부", Toast.LENGTH_SHORT).show();
           }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage("사진을 업로드하기 위해서는 사진첩 접근 권한이 필요합니다.")
                .setDeniedMessage("[설정] > [권한]에서 권한을 허용할 수 있습니다.")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)     //확인할 권한을 다중 인자로 넣어줌.
                .check();
        Log.d("Permission_TAG", TedPermission.TAG.toString());
    }






    //프로그레스 다이얼로그
    private class CheckTypeTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog asyncDialog = new ProgressDialog(UploadDesign.this);

        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("업로드중입니다..");

            // show dialog
            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                for (int i = 0; i < 5; i++) {
                    //asyncDialog.setProgress(i * 30);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            asyncDialog.dismiss();
            super.onPostExecute(result);
        }

    }


    //숫자인지 판별 메소드
    public static boolean isNumber(String str){
        boolean result = false;


        try{
            Double.parseDouble(str) ;
            result = true ;
        }catch(Exception e){}


        return result ;
    }



}
