package com.example.q.maptest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

public class ImageAdapterGridView extends BaseAdapter {

    private Context context;
    private ArrayList<String> dataList;

    public ImageAdapterGridView(Context mContext, ArrayList<String> result) {
        context = mContext;
        dataList = result;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }
    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ImageView imageView;
        if(view == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(350, 350));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(2,2,2,2);
            imageView.setAdjustViewBounds(false);
        } else {
            imageView = (ImageView) view;
        }

        //Bitmap bmp = BitmapFactory.decodeFile(dataList.get(position));
        //Bitmap resized = Bitmap.createScaledBitmap(bmp, 350, 350, true);
        imageView.setImageBitmap(StringToBitmap(dataList.get(position)));


        return imageView;
    }

    public Bitmap StringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

}
