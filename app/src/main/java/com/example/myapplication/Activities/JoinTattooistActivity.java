package com.example.myapplication.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.SharedData.Tattooist;
import com.example.myapplication.SharedData.User;
import com.google.gson.Gson;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class JoinTattooistActivity extends AppCompatActivity {


    Button button_cancel;
    Button button_ok;


    //스트링 형태로 값 받을 변수들
    String userid;      //회원가입 때 저장한 id 값
    String userpw;      //회원가입 때 저장한 pw 값

    User user;

    String address = "";

    // 우체국 오픈api 인증키
    private String _key = "ba16af29cb7427feb1559456714737";

    private EditText addressEdit;
    private Button btnSearch;
    private ListView addressList;
    TextView tv_adrs;

    private ArrayAdapter<String> _addressListAdapter;

    // 사용자가 입력한 주소
    private String _putAddress;
    // 우체국으로부터 반환 받은 우편주소 리스트
    private ArrayList<String> _addressSearchResultArr = new ArrayList<String>();



    @Override
    protected void onCreate(@Nullable Bundle savedInstancedState){
        super.onCreate(savedInstancedState);
        setContentView(R.layout.activity_join_tattooist);


        button_cancel = (Button)findViewById(R.id.button_cancel);
        button_ok = (Button)findViewById(R.id.btn_ok);

        addressEdit = (EditText)findViewById(R.id.addressEdit);
        btnSearch = (Button)findViewById(R.id.btnSearch);
        addressList = (ListView)findViewById(R.id.addressList);
        tv_adrs = (TextView)findViewById(R.id.tv_adrs);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAddress(addressEdit.getText().toString());
            }
        });

        //쓰던 데이터 있으면 받아오기
        Intent intent = getIntent();
        userid = intent.getStringExtra("userid_key");
        userpw = intent.getStringExtra("userpw_key");













        //취소 버튼 선택시
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(JoinTattooistActivity.this, JoinActivity.class);
                startActivity(intent);
                finish();

            }
        });


        //다음 버튼 선택시
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(address.equals("")){
                    Toast.makeText(getApplicationContext(), "주소를 선택해 주세요", Toast.LENGTH_SHORT).show();
                    return;
                }




                Intent intent =getIntent();
                user = intent.getParcelableExtra("user_key");

                String userId = user.getId();


                Intent intent2 = new Intent(JoinTattooistActivity.this, JoinTattooistNext.class);
                intent2.putExtra("user_key", userId);
                intent2.putExtra("address", address);
                startActivity(intent2);
                finish();
            }
        });






    }


    public void getAddress(String kAddress)
    {
        _putAddress = kAddress;
        new GetAddressDataTask().execute();
    }

    private class GetAddressDataTask extends AsyncTask<String, Void, HttpResponseCache>
    {
        @Override
        protected HttpResponseCache doInBackground(String... urls)
        {
            HttpResponseCache response = null;
            final String apiurl = "http://biz.epost.go.kr/KpostPortal/openapi";

            ArrayList<String> addressInfo = new ArrayList<String>();

            HttpURLConnection conn = null;
            try
            {
                StringBuffer sb = new StringBuffer(3);
                sb.append(apiurl);
                sb.append("?regkey=" + _key + "&target=postNew&query=");
                sb.append(URLEncoder.encode(_putAddress, "EUC-KR"));
                String query = sb.toString();

                URL url = new URL(query);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("accept-language", "ko");

                DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                byte[] bytes = new byte[4096];
                InputStream in = conn.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                while (true)
                {
                    int red = in.read(bytes);
                    if (red < 0)
                        break;
                    baos.write(bytes, 0, red);
                }
                String xmlData = baos.toString("utf-8");
                baos.close();
                in.close();
                conn.disconnect();

                Document doc = docBuilder.parse(new InputSource(new StringReader(xmlData)));
                Element el = (Element) doc.getElementsByTagName("itemlist").item(0);
                for (int i = 0; i < ((Node) el).getChildNodes().getLength(); i++)
                {
                    Node node = ((Node) el).getChildNodes().item(i);
                    if (!node.getNodeName().equals("item"))
                    {
                        continue;
                    }
                    String address = node.getChildNodes().item(1).getFirstChild().getNodeValue();
                    String post = node.getChildNodes().item(3).getFirstChild().getNodeValue();
                    Log.w("tag", "address = " + address);
                    addressInfo.add(address + "\n우편번호:" + post.substring(0, 3) + "-" + post.substring(3));
                }

                _addressSearchResultArr = addressInfo;
                publishProgress();


            } catch (Exception e)
            {
                e.printStackTrace();
            } finally
            {
                try
                {
                    if (conn != null)
                        conn.disconnect();
                } catch (Exception e)
                {
                }
            }

            return response;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

            String[] addressStrArray = new String[_addressSearchResultArr.size()];
            addressStrArray = _addressSearchResultArr.toArray(addressStrArray);

            _addressListAdapter = new ArrayAdapter<String>(JoinTattooistActivity.this, android.R.layout.simple_list_item_1, addressStrArray);
            addressList.setAdapter(_addressListAdapter);

            addressList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    address = _addressListAdapter.getItem(position).toString();
                            tv_adrs.setText(address);
                            Toast.makeText(getApplicationContext(), "아래 입력된 주소가 맞으시면 다음 버튼을 눌러주세요", Toast.LENGTH_LONG).show();
                }
            });
        }
    }





}
