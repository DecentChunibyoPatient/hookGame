package com.wqg.gamecmd.child_view;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.wqg.gamecmd.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class LogView extends LinearLayout {
    ListView listView;
    public MBaseAdapter mBaseAdapter;
    public LogView(Context context) {
        super(context);
        inflate(context, R.layout.log_view,this);
        listView=findViewById(R.id.listView);
        listView.setAdapter(mBaseAdapter=new MBaseAdapter());
    }
    public MBaseAdapter getMBaseAdapter(){
        return mBaseAdapter;
    }
    Handler handler=new Handler(){
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                mBaseAdapter.notifyDataSetChanged();
            }
        };
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                {
                    removeCallbacks(runnable);
                    postAtTime(runnable,500);
                }break;
            }
        }
    };
    public class MBaseAdapter extends BaseAdapter{
        JSONArray jsonArray=new JSONArray();
        public MBaseAdapter(JSONArray jsonArray){
            this.jsonArray=jsonArray;
        }
        public MBaseAdapter(){
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

        @Override
        public long getItemId(int position) {
            return 0;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=inflate(getContext(),R.layout.log_view_item,null);
            try {
                JSONObject jsonObject=jsonArray.getJSONObject(position);
                String name=jsonObject.getString("name");
                TextView textView=view.findViewById(R.id.textView);
                textView.setText(name);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return view;
        }
        public void upData(JSONArray jsonArray){
            this.jsonArray= jsonArray;
            handler.sendEmptyMessage(0);
        }
        public void addData(JSONArray jsonArray) throws JSONException {
            for (int i=0;i<jsonArray.length();i++){
                this.jsonArray.put(jsonArray.get(i));
            }
            handler.sendEmptyMessage(0);
        }
        public void removeData(String name) throws JSONException {
            for (int i=0;i<jsonArray.length();i++){
              JSONObject jsonObject=jsonArray.getJSONObject(i);

              if (name.equals(jsonObject.getString("name"))){
                  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                      jsonArray.remove(i);
                  }
              }
            }
            handler.sendEmptyMessage(0);
        }

    }
}
