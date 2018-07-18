package com.example.q.subway;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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
        titleView = findViewById(R.id.title);
        priceView = findViewById(R.id.price);
        calorieView = findViewById(R.id.calorie);
        likeCountView = findViewById(R.id.likeCount);
        ImageView lettuce ;
        ImageView tomato ;
        ImageView pepper;
        ImageView olive ;
        ImageView pickle ;
        ImageView jalapeno ;
        ImageView onion;
        ImageView cucumber;
        ImageView main ;
        ImageView main2 ;
        ImageView top ;
        ImageView bottom;
        ImageView cheese ;
        TextView breadDiscription = findViewById(R.id.breadDiscription);
        TextView mainDiscription = findViewById(R.id.mainDiscription);
        TextView vegeDiscription = findViewById(R.id.vegeDiscription);
        TextView cheeseDiscription = findViewById(R.id.cheeseDiscription);
        TextView extraDiscription = findViewById(R.id.extraDiscription);
        TextView sauceDiscription = findViewById(R.id.sauceDiscription);

        final String recipe;
        final String recipe_id;

        // Inflate the list_item.xml file if convertView is null

        lettuce = findViewById(R.id.lettuce);
        tomato = findViewById(R.id.tomato);
        pepper = findViewById(R.id.pepper);
        olive = findViewById(R.id.olive);
        pickle = findViewById(R.id.pickle);
        jalapeno = findViewById(R.id.jalapeno);
        onion = findViewById(R.id.onion);
        cucumber = findViewById(R.id.cucumber);
        main = findViewById(R.id.main);
        main2 = findViewById(R.id.main2);
        top = findViewById(R.id.top);
        bottom = findViewById(R.id.bottom);
        cheese = findViewById(R.id.cheese);

        Intent intent  = getIntent();
        recipe = intent.getExtras().getString("recipe");
        final int position = intent.getExtras().getInt("position");
        try {
            JSONArray jsonArray =  new JSONArray(recipe);
            JSONObject jsonObject = (JSONObject) jsonArray.get(position);
            titleView.setText(jsonObject.getString("title"));
            priceView.setText(jsonObject.getString("price")+"원");
            Log.d("shit", jsonObject.getString("title")+position);
            likeCountView.setText(jsonObject.getString("like"));
            calorieView.setText(jsonObject.getString("calorie")+"kcal");


            breadDiscription.setText("빵: "+jsonObject.getString("bread"));
            mainDiscription.setText("주재료: "+jsonObject.getString("main"));
            cheeseDiscription.setText("Cheese: "+jsonObject.getString("cheese"));
            vegeDiscription.setText("야채: "+weirdParser(jsonObject.getString("vege")));
            sauceDiscription.setText("Sauce: " +weirdParser(jsonObject.getString("sauce")));
            extraDiscription.setText("추가: " +weirdParser(jsonObject.getString("extra")));



            top.setVisibility(View.VISIBLE);
            bottom.setVisibility(View.VISIBLE);

            switch (jsonObject.getString("bread")){
                case "Honey Oat":
                    top.setImageResource(R.drawable.honey_oat_top);
                    bottom.setImageResource(R.drawable.honey_oat_bottom);
                    break;
                case"Hearty Italian":
                    top.setImageResource(R.drawable.hearty_top);
                    bottom.setImageResource(R.drawable.hearty_bottom);
                    break;
                case "Wheat":
                    top.setImageResource(R.drawable.wheat_top);
                    bottom.setImageResource(R.drawable.wheat_bottom);
                    break;
                case "Parmesan Oregano":
                    top.setImageResource(R.drawable.parmasean_oregano_top);
                    bottom.setImageResource(R.drawable.parmaesan_oregano_bottom);
                    break;
                case "White":
                    top.setImageResource(R.drawable.white_top);
                    bottom.setImageResource(R.drawable.white_bottom);
                    break;
                case "Flat Bread":
                    top.setImageResource(R.drawable.flat_bread_top);
                    bottom.setImageResource(R.drawable.flat_bread_bottom);
                    break;
                default:
                    break;
            }

            switch(jsonObject.getString("main")) {
                case"Italian B.M.T.":
                case "Spicy Italian Avocado":
                    main.setImageResource(R.drawable.pepperoni);
                    main.setVisibility(View.VISIBLE);
                    main2.setImageResource(R.drawable.ham);
                    main2.setVisibility(View.VISIBLE);
                    break;
                case "Turkey Bacon":
                case "Turkey Bacon Avocado":
                    main.setImageResource(R.drawable.turkey);
                    main.setVisibility(View.VISIBLE);
                    main2.setImageResource(R.drawable.bacon);
                    main2.setVisibility(View.VISIBLE);
                    break;

                case "Ham":
                    main.setImageResource(R.drawable.ham);
                    main.setVisibility(View.VISIBLE);
                    break;
                case "Roasted Beef":
                    main.setImageResource(R.drawable.roasted_beef);
                    main.setVisibility(View.VISIBLE);
                    break;
                case"B.L.T.":
                    main.setImageResource(R.drawable.bacon);
                    main.setVisibility(View.VISIBLE);
                    break;
                case "Turkey":
                    main.setImageResource(R.drawable.turkey);
                    main.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
            switch (jsonObject.getString("cheese")){
                case "American":
                    cheese.setImageResource(R.drawable.american_cheese);
                    cheese.setVisibility(View.VISIBLE);
                    break;
                case "Shredded":
                    cheese.setImageResource(R.drawable.american_cheese);
                    cheese.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
            for (String selectedItem: RecipeListAdapter.parseVege(jsonObject.getString("vege"))){
                Log.d("sauce", selectedItem);
                switch(selectedItem) {
                    case "Lettuce":
                        lettuce.setVisibility(View.VISIBLE);
                        break;
                    case "Tomatoes":
                        tomato.setVisibility(View.VISIBLE);
                        break;
                    case "Cucumbers":
                        cucumber.setVisibility(View.VISIBLE);
                        break;
                    case "Red Onions":
                        onion.setVisibility(View.VISIBLE);
                        break;
                    case "Peppers":
                        pepper.setVisibility(View.VISIBLE);
                        break;
                    case "Pickles":
                        pickle.setVisibility(View.VISIBLE);
                        break;
                    case "Olives":
                        olive.setVisibility(View.VISIBLE);
                        break;
                    case "Jalapeno":
                        jalapeno.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }
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
                networkTask.execute();

                String str="";
                try {
                    str = networkTask.get();
                    if(str.equals("0")){

                        NetworkTask networkTask2 = new NetworkTask("api/getlike", "post", null, jsonArray);
                        networkTask2.execute();
                        String str2="";
                        str2 = networkTask2.get();
                        Log.d("netTask2.get", str2);
                        JSONArray arraylike = new JSONArray(str2);
                        JSONObject objectlike = (JSONObject) arraylike.get(0);
                        String like="";
                        like = objectlike.getString("like");
                        Log.d("like", like);

                        int like2 = Integer.parseInt(like);
                        like2 = like2+1;
                        String like3 = String.valueOf(like2);
                        Log.d("like3", like3);

                        jsonObject.accumulate("newlike", like3);
                        jsonArray.put(jsonObject);
                        NetworkTask networkTask3 = new NetworkTask("api/postlike", "post", null, jsonArray);
                        networkTask3.execute();
                        Toast.makeText(getApplicationContext(),"@@@ LIKE !! @@@",Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(),"You already LIKE !",Toast.LENGTH_SHORT).show();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("netTask.get", str);

            }
        });

        Button replyButton = findViewById(R.id.replyButton);
        replyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        // Set text to each TextView of ListView item
    }
    public static String weirdParser(String input){
        String result ="";
        ArrayList<String> array = RecipeListAdapter.parseVege(input);
        if (input.equals("")){
            return "안먹";
        }
        else{
            result += array.get(0);
            for (int i=1; i<array.size(); i++){
                result += ", "+array.get(i);
            }
            return result;
        }
    }
}
