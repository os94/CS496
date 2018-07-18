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
    Context context;
    JSONArray replies;

    public ReplyListAdapter(Context context, int vg, JSONArray replies){
        this.context=context;
        groupid=vg;
        this.replies = replies;
        //this.recipeIdList = recipeIdListList;
    }
    // Hold views of the ListView to improve its scrolling performance
/*    static class ViewHolder {
        public TextView titleView;
        public TextView priceView;
        public TextView likeCountView;
        public TextView calorieView;
        public String recipe;
        public int position;
    }*/
    public Object getItem(int position) {
        try {
            return  replies.get(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public long getItemId(int position) {
        return position;
    }
    public int getCount() {
        if (this.replies != null) {
            return this.replies.length();
        }
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // Inflate the list_item.xml file if convertView is null
        //if(rowView==null){

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(groupid, parent, false);

/*        ViewHolder viewHolder = new ViewHolder();
        viewHolder.titleView = rowView.findViewById(R.id.title);
        viewHolder.calorieView = rowView.findViewById(R.id.calorie);
        viewHolder.likeCountView =  rowView.findViewById(R.id.likeCount);
        viewHolder.priceView =  rowView.findViewById(R.id.price);
        viewHolder.recipe = this.recipe;
        rowView.setTag(viewHolder);*/

        //}
        // Set text to each TextView of ListView item
        try {

            JSONObject reply = (JSONObject) replies.get(position);
            TextView user = rowView.findViewById(R.id.writer);
            TextView comment = rowView.findViewById(R.id.comment);
            user.setText(reply.getString("creator"));
            comment.setText(reply.getString("comment"));
          /*  viewHolder.titleView.setText(jsonObject.getString("title"));
            viewHolder.calorieView.setText(jsonObject.getString("calorie")+"kcal");
            viewHolder.likeCountView.setText(jsonObject.getString("like"));
            viewHolder.priceView.setText(jsonObject.getString("price")+"원");
            viewHolder.position = position;*/
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rowView;
    }
}
