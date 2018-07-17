package com.example.q.subway;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import static com.example.q.subway.LoadingActivity.global_uid;

public class RecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        TextView titleView;
        TextView priceView;
        TextView likeCountView;
        TextView calorieView;
        final String recipe;

        titleView = findViewById(R.id.title);
        priceView = findViewById(R.id.price);
        calorieView = findViewById(R.id.calorie);
        likeCountView = findViewById(R.id.likeCount);


        Intent intent  = getIntent();
        recipe = intent.getExtras().getString("recipe");
        final int position = intent.getExtras().getInt("position");
        try {
            JSONArray jsonArray =  new JSONArray(recipe);
            JSONObject jsonObject = (JSONObject) jsonArray.get(position);
            titleView.setText(jsonObject.getString("title"));
            priceView.setText(jsonObject.getString("price"));

            likeCountView.setText(jsonObject.getString("like"));
            calorieView.setText(jsonObject.getString("calorie"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Button likeButton = findViewById(R.id.likeButton);
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();
                JSONArray jsonArray = new JSONArray();
                try {
                    JSONArray recipeArray =  new JSONArray(recipe);
                    JSONObject recipeObject = (JSONObject) recipeArray.get(position);

                    jsonObject.accumulate("u_id", global_uid);
                    jsonObject.accumulate("good_id", recipeObject.getString("_id"));
                    jsonArray.put(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                NetworkTask networkTask = new NetworkTask("api/good", "post", null, jsonArray);
                networkTask.execute();            }
        });
    }
}
