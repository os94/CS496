package com.example.q.subway;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class LoadingActivity extends AppCompatActivity {

    TextView tv_signup;
    EditText et_id, et_pw;
    Button btn_signin;
    public static String global_uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);


        tv_signup = (TextView) findViewById(R.id.tv_signup);
        et_id = (EditText) findViewById(R.id.et_id);
        et_pw = (EditText) findViewById(R.id.et_pw);
        btn_signin = (Button) findViewById(R.id.btn_signin);

        // debugging Login route @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        ImageView iv_title;
        iv_title = (ImageView) findViewById(R.id.iv_title);
        iv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Signup.class);
                startActivity(intent);
            }
        });

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = et_id.getText().toString();
                String upw = et_pw.getText().toString();
                JSONObject jsonObject = new JSONObject();
                JSONArray jsonArray = new JSONArray();
                try {
                    jsonObject.put("u_id", uid);
                    jsonObject.put("u_pw", upw);
                    jsonArray.put(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                NetworkTask networkTask = new NetworkTask("api/account_login", "post", null, jsonArray);
                networkTask.execute();

                String str = "";
                try {
                    str = networkTask.get();
                    if(str.equals("{\"result\":1}")){
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        //intent.putExtra("userID", uid);
                        global_uid = uid;
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                Log.d("netTask.get", str);


            }
        });
    }

}
