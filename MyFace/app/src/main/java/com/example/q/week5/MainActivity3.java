package com.example.q.week5;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

public class MainActivity3 extends AppCompatActivity implements View.OnClickListener {

    BottomNavigationView navigationView;
    private Button btn1, btn2;
    private TextView tv, tv2;
    private ImageView iv, iv2;
    private static final int CAMERA_CODE = 1111;
    private static final int GALLERY_CODE = 1112;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        btn1 = (Button) findViewById(R.id.btn_camera);
        btn2 = (Button) findViewById(R.id.btn_gallery);
        tv = (TextView) findViewById(R.id.tv_age);
        tv2 = (TextView) findViewById(R.id.tv_detail);
        iv = (ImageView) findViewById(R.id.iv);
        iv2 = (ImageView) findViewById(R.id.iv_first);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_camera:
                Intent iCam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(iCam, CAMERA_CODE);
                break;
            case R.id.btn_gallery:
                Intent iGal = new Intent(Intent.ACTION_PICK);
                iGal.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                iGal.setType("image/*");
                startActivityForResult(iGal, GALLERY_CODE);
                break;
        }
    }

    // onActivityResult로 intent의 결과값을 처리하고, data.getData로 사진URI를 가져온다
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAMERA_CODE :
                    tv.setText(null);
                    tv2.setText("앨범에서 사진을 선택하세요.");
                    iv.setVisibility(View.GONE);
                    iv2.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "사진을 선택하세요 !", Toast.LENGTH_SHORT).show();
                    //촬영한 사진 저장코드 추가
                    break;
                case GALLERY_CODE :
                    // Image URI check @@@@@@@@@@
                    String uriStr = data.getData().toString();
                    String path = data.getData().getPath();
                    String realPath = getRealPathFromUri(this, data.getData());
                    Log.d("@@@@ img URI: ", uriStr);
                    Log.d("@@@@ img URI path: ", path);
                    Log.d("@@@@ img URI realPath: ", realPath);

                    iv.setVisibility(View.VISIBLE);
                    iv2.setVisibility(View.GONE);
                    try {
                        JSONObject CFRJSON = new CFR2apiTask().execute(realPath).get();
                        if(CFRJSON == null) {
                            tv.setText("얼굴나이 측정실패");
                            tv2.setText("사람 얼굴이 아닙니다.");
                            new MainActivity3.LoadImgTask().execute("https://pbs.twimg.com/profile_images/476169263305457665/I8QlqUVh.jpeg");
                        } else {
                            //iv.setImageURI(data.getData());

                            /*String age = getAgeFromJson(CFRJSON);
                            String imgLink = getImgLinkFromAge(age);*/
                            String testImgLink = "https://pinkfrogpetproducts.com/wp-content/uploads/2014/03/img.gif";

                            tv.setText("test age"); //age 들어갈것
                            tv2.setText("당신의 얼굴 나이 !!");
                            new MainActivity3.LoadImgTask().execute(testImgLink);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    break;
                default :
                    break;
            }
        }
    }

    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private class LoadImgTask extends AsyncTask<String, Void, Bitmap> {
        Bitmap mBitmap;
        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                mBitmap = BitmapFactory.decodeStream(
                        (InputStream) new URL(strings[0]).getContent()
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
            return mBitmap;
        }
        @Override
        protected void onPostExecute(Bitmap image) {
            if(image != null) {
                iv.setImageBitmap(image);
            } else {
                Toast.makeText(MainActivity3.this, "Image Load Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    static class CFR2apiTask extends AsyncTask<String, Void, JSONObject> {
        StringBuffer reqStr = new StringBuffer();
        String clientId = "bCE1PD3K8ivrZXs1Ky1j";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "IjxlgqjqNG";//애플리케이션 클라이언트 시크릿값";
        @Override
        protected JSONObject doInBackground(String... strings) {
            try {
                Log.d("@@@@@ CFR API ", "START !!! @@@@@");
                String paramName = "image"; // 파라미터명은 image로 지정
                String imgFile = strings[0];
                File uploadFile = new File(imgFile);
                //String apiURL = "https://openapi.naver.com/v1/vision/celebrity"; // 유명인 얼굴 인식
                String apiURL = "https://openapi.naver.com/v1/vision/face"; // 얼굴 감지
                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setUseCaches(false);
                con.setDoOutput(true);
                con.setDoInput(true);
                // multipart request
                String boundary = "---" + System.currentTimeMillis() + "---";
                con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
                con.setRequestProperty("X-Naver-Client-Id", clientId);
                con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
                OutputStream outputStream = con.getOutputStream();
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);
                String LINE_FEED = "\r\n";
                // file 추가
                String fileName = uploadFile.getName();
                writer.append("--" + boundary).append(LINE_FEED);
                writer.append("Content-Disposition: form-data; name=\"" + paramName + "\"; filename=\"" + fileName + "\"").append(LINE_FEED);
                writer.append("Content-Type: "  + URLConnection.guessContentTypeFromName(fileName)).append(LINE_FEED);
                writer.append(LINE_FEED);
                writer.flush();
                FileInputStream inputStream = new FileInputStream(uploadFile);
                byte[] buffer = new byte[4096];
                int bytesRead = -1;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.flush();
                inputStream.close();
                writer.append(LINE_FEED).flush();
                writer.append("--" + boundary + "--").append(LINE_FEED);
                writer.close();
                BufferedReader br = null;
                int responseCode = con.getResponseCode();
                if(responseCode==200) { // 정상 호출
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                } else {  // 에러 발생
                    Log.d("@@@@@ error, respCode: ", String.valueOf(responseCode));
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                }
                String inputLine;
                if(br != null) {
                    StringBuffer response = new StringBuffer();
                    while ((inputLine = br.readLine()) != null) {
                        response.append(inputLine);
                    }
                    br.close();
                    Log.d("@@@@@ Result: ", response.toString());
                    return new JSONObject(response.toString());
                } else {
                    Log.d("@@@@@ error: ", "BufferedReader is null");
                }
            } catch (Exception e) {
                Log.d("@@@@@ error: ", String.valueOf(e));
            }
            return null;
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_cele1:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(R.anim.hold, R.anim.fade_in);
                    return true;
                case R.id.navigation_cele2:
                    startActivity(new Intent(getApplicationContext(), MainActivity2.class));
                    overridePendingTransition(R.anim.hold, R.anim.fade_in);
                    return true;
                case R.id.navigation_age:
                    startActivity(new Intent(getApplicationContext(), MainActivity3.class));
                    overridePendingTransition(R.anim.hold, R.anim.fade_in);
                    return true;
            }
            return false;
        }
    };
    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
    }
    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
    private void updateNavigationBarState(){
        int actionId = getNavigationMenuItemId();
        selectBottomNavigationBarItem(actionId);
    }
    void selectBottomNavigationBarItem(int itemId) {
        MenuItem item = navigationView.getMenu().findItem(itemId);
        item.setChecked(true);
    }
    int getNavigationMenuItemId() {
        return R.id.navigation_age;
    }

}
