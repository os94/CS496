package com.example.q.subway;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReplyListAdapter  extends BaseAdapter {
    int groupid;
    JSONArray recipeIdList;
    Context context;
    String recipe;

    public ReplyListAdapter(Context context, int vg, String recipe){
        this.context=context;
        groupid=vg;
        this.recipe = recipe;
        //this.recipeIdList = recipeIdListList;
    }
    // Hold views of the ListView to improve its scrolling performance
    static class ViewHolder {
        public TextView titleView;
        public TextView priceView;
        public TextView likeCountView;
        public TextView calorieView;
        public String recipe;
        public int position;
    }
    public Object getItem(int position) {
        try {
            return  recipeIdList.get(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public long getItemId(int position) {
        return position;
    }
    public int getCount() {
        if (this.recipe != null) {
            try {
                JSONArray jsonArray = new JSONArray(this.recipe);
                return jsonArray.length();
            }
            catch(JSONException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
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

        // Inflate the list_item.xml file if convertView is null
        //if(rowView==null){


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView= inflater.inflate(groupid, parent, false);

        ViewHolder viewHolder = new ViewHolder();
        viewHolder.titleView = rowView.findViewById(R.id.title);
        viewHolder.calorieView = rowView.findViewById(R.id.calorie);
        viewHolder.likeCountView =  rowView.findViewById(R.id.likeCount);
        viewHolder.priceView =  rowView.findViewById(R.id.price);
        viewHolder.recipe = this.recipe;
        rowView.setTag(viewHolder);

        lettuce = rowView.findViewById(R.id.lettuce);
        tomato = rowView.findViewById(R.id.tomato);
        pepper = rowView.findViewById(R.id.pepper);
        olive = rowView.findViewById(R.id.olive);
        pickle =rowView. findViewById(R.id.pickle);
        jalapeno = rowView.findViewById(R.id.jalapeno);
        onion = rowView.findViewById(R.id.onion);
        cucumber = rowView.findViewById(R.id.cucumber);
        main = rowView.findViewById(R.id.main);
        main2 = rowView.findViewById(R.id.main2);
        top = rowView.findViewById(R.id.top);
        bottom = rowView.findViewById(R.id.bottom);
        cheese = rowView.findViewById(R.id.cheese);
        //}
        // Set text to each TextView of ListView item
        try {
            JSONArray jsonArray = new JSONArray(this.recipe);
            JSONObject jsonObject = (JSONObject) jsonArray.get(position);


            viewHolder.titleView.setText(jsonObject.getString("title"));
            viewHolder.calorieView.setText(jsonObject.getString("calorie")+"kcal");
            viewHolder.likeCountView.setText(jsonObject.getString("like"));
            viewHolder.priceView.setText(jsonObject.getString("price")+"원");
            viewHolder.position = position;

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

            Log.d("shit", jsonObject.getString("title")+position);
            Log.d("parse",jsonObject.getString("vege"));
            for (String item: parseVege(jsonObject.getString("vege"))){
                Log.d("sauce", item);
                switch(item) {
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

        return rowView;
    }
    public static ArrayList<String> parseVege(String vegeString){
        ArrayList<String> vegeList = new ArrayList<>();
        String[] vegeArray;
        if(vegeString.equals("")){

        }
        else{
            vegeArray = vegeString.split(",");
            for(int i=1; i < vegeArray.length; i++){
                vegeList.add(vegeArray[i]);
            }
        }
        return vegeList;
    }
}
