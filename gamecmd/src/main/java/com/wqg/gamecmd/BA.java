package com.wqg.gamecmd;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.nfc.Tag;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.superrtc.sdk.ALog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class BA extends BaseAdapter {
    static public String TAG="BA";
    int Checked=-1;
    JSONArray jsonArray;
    Context context;
    HashMap<String,Bitmap>hashMap;
    public BA( Context context ){
        this.jsonArray=new JSONArray();
        this.hashMap=new HashMap<>();
        this.context=context;
    }
    public BA( Context context ,JSONArray jsonArray, HashMap<String,Bitmap>hashMap){
        this.jsonArray=jsonArray;
        this.hashMap=hashMap;
        this.context=context;
    }
    @Override
    public int getCount() {
        return jsonArray.length();
    }

    @Override
    public Object getItem(int position) {
        try {
            return jsonArray.get(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public void upData(JSONArray jsonArray, HashMap<String,Bitmap>hashMap){
        this.jsonArray=jsonArray;
    }
    public void add(Bitmap bitmap, Rect rect) throws JSONException {
        Log.d(TAG,"add");
        JSONObject jsonObject=new JSONObject();
        String name=null;

        if (jsonArray.length()>0){
            ArrayList<String>arrayListName=new ArrayList<>();
            for (int i=0;i<jsonArray.length();i++){
                arrayListName.add(jsonArray.getJSONObject(i).getString("name"));
            }
            int s=0;
            while (true){
                String Name="页面"+s++;
                if (!arrayListName.contains(Name)){
                    name=Name;
                    break;
                }
            }

        }else {
            name="页面"+0;
        }
        jsonObject.put("name",name);
        jsonObject.put("rect",RectToJSONObject(rect));
        hashMap.put(name,bitmap);
        jsonArray.put(jsonObject);
        notifyDataSetChanged();
    }
    public void addChild(int position ,Bitmap bitmap, Rect rect) throws JSONException {
        Log.d(TAG,"addChild");
        JSONObject jsonObject=jsonArray.getJSONObject(position);
        String name=jsonObject.getString("name");
        JSONArray childJSONArray=new JSONArray();
        try {
            childJSONArray=jsonObject.getJSONArray("child");
        }catch (JSONException e){
            e.printStackTrace();
        }
        String childName = null;
        ArrayList<String>arrayListName=new ArrayList<>();
        for (int i=0;i<childJSONArray.length();i++){
            arrayListName.add(childJSONArray.getJSONObject(i).getString("name"));
        }
        int s=0;
        while (true){
            String cName=name+"_child"+s++;
            if (!arrayListName.contains(cName)){
                childName=cName;
                break;
            }
        }
        JSONObject jsonObjectchild=new JSONObject();
        jsonObjectchild.put("name",childName);
        jsonObjectchild.put("rect",RectToJSONObject(rect));
        childJSONArray.put(jsonObjectchild);
        jsonObject.put("child",childJSONArray);
        hashMap.put(childName,bitmap);
        notifyDataSetChanged();
    }
    public void addChild(String name,Bitmap bitmap, Rect rect) throws JSONException {
        JSONObject jsonObject=null;
        for (int i=0;i<jsonArray.length();i++){
            if (name.equals(jsonArray.getJSONObject(i).getString("name"))){
                jsonObject=jsonArray.getJSONObject(i);
            }
        }
        JSONArray childJSONArray=new JSONArray();
        String childName = null;
        try {
            childJSONArray=jsonObject.getJSONArray("child");
        }catch (JSONException e){
            e.printStackTrace();
        }
        ArrayList<String>arrayListName=new ArrayList<>();
        for (int i=0;i<childJSONArray.length();i++){
            arrayListName.add(childJSONArray.getJSONObject(i).getString("name"));
        }
        int s=0;
        while (true){
            String cName=name+"_child"+s++;
            if (!arrayListName.contains(cName)){
                childName=cName;
                break;
            }
        }

        JSONObject jsonObjectchild=new JSONObject();
        jsonObjectchild.put("name",childName);
        jsonObjectchild.put("rect",RectToJSONObject(rect));
        childJSONArray.put(jsonObjectchild);
        jsonObject.put("child",childJSONArray);
        hashMap.put(childName,bitmap);
        notifyDataSetChanged();
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view=View.inflate(context,R.layout.item,null);
        if (position==Checked){
            view.setBackgroundResource(R.drawable.shape3);
        }
        try {
            JSONObject jsonObject=jsonArray.getJSONObject(position);
            String name=jsonObject.getString("name");
            TextView number=view.findViewById(R.id.itme_number);
            number.setText(position+"");
            TextView itme_name=view.findViewById(R.id.itme_name);
            itme_name.setText(name);
            ImageView itme_imageView=view.findViewById(R.id.itme_imageView);
            itme_imageView.setImageBitmap(hashMap.get(name));
            view.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
                int p=position;
                @Override
                public void onClick(View v) {
                   remove(p);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }
    public void load(String dir, String fileName) throws IOException, JSONException {
        this.hashMap=FileSaveAndLoad.FileLoad(dir);
        this.jsonArray=FileSaveAndLoad.loadJSONArray(dir+"/"+fileName);
        Checked=-1;
        new Handler(context.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                // 在这里执行你要想的操作 比如直接在这里更新ui或者调用回调在 在回调中更新ui
                notifyDataSetChanged();
            }
        });

    }
    public void save(String dir, String fileName){
        FileSaveAndLoad.FileSave(dir,hashMap);
        FileSaveAndLoad.saveJSONArray(dir,fileName,jsonArray);
    }
    public void Checked(int Checked){
        this.Checked=Checked;
        notifyDataSetChanged();
    }
    public boolean isChecked(){
        return Checked>=0;
    }
    public int getChecked(){
        return Checked;
    }
    public void clearChecked(){
        this.Checked=-1;
        notifyDataSetChanged();
    }
    public HashMap getHashMap(){
        return hashMap;
    }
    public void remove(int position){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            jsonArray.remove(position);
           /* try {

                JSONObject jsonObject= (JSONObject) jsonArray.remove(position);
                String name=jsonObject.getString("name");
                hashMap.remove(name);
                JSONArray jsonArray1=jsonObject.getJSONArray("child");
                if (jsonArray1!=null)
                    if (jsonArray1.length()>0){
                        for (int i=0;i<jsonArray1.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            String childName=jsonObject1.getString("name");
                            hashMap.remove(childName);
                        }
                    }
            } catch (JSONException e) {
                e.printStackTrace();
            }*/
            notifyDataSetChanged();
        }
    }
    JSONObject RectToJSONObject(Rect rect) throws JSONException {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("left",rect.left);
        jsonObject.put("top",rect.top);
        jsonObject.put("right",rect.right);
        jsonObject.put("bottom",rect.bottom);
        return jsonObject;
    }
}
