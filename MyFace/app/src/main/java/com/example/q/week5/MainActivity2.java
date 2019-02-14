package com.example.q.week5;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
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
import android.widget.Toast;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {

    BottomNavigationView navigationView;
    private Button btn1, btn2;
    private ImageView iv;
    private static final int CAMERA_CODE = 1111;
    private static final int GALLERY_CODE = 1112;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        btn1 = (Button) findViewById(R.id.btn_camera);
        btn2 = (Button) findViewById(R.id.btn_gallery);
        iv = (ImageView) findViewById(R.id.iv);

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

                    iv.setImageURI(data.getData());
                    Toast.makeText(this, uriStr, Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, realPath, Toast.LENGTH_SHORT).show();
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
        return R.id.navigation_cele2;
    }

}
