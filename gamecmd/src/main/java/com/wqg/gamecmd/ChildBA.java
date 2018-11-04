package com.wqg.gamecmd;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ChildBA extends BaseAdapter {
    JSONArray jsonArray;
    Context context;
    HashMap<String,Bitmap>hashMap;
    public ChildBA( Context context, JSONArray jsonArray,HashMap<String,Bitmap>hashMap){
        this.context=context;
        this.jsonArray=jsonArray;
        this.hashMap=hashMap;
    }
    @Override
    public int getCount() {
        return jsonArray.length();
    }

    @Override
    public Object getItem(int position) {
        return jsonArray.put(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=View.inflate(context,R.layout.child_item,null);
        try {
            JSONObject jsonObject=jsonArray.getJSONObject(position);
            ImageView imageView=view.findViewById(R.id.child_itme_imageView);
            String name=jsonObject.getString("name");
            imageView.setImageBitmap(hashMap.get(name));
            TextView textView=view.findViewById(R.id.child_itme_name);
            textView.setText(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }
}
