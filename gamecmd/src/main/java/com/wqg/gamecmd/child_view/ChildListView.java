package com.wqg.gamecmd.child_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wqg.gamecmd.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ChildListView extends ConstraintLayout implements View.OnClickListener{
    String TAG="ChildListView";
    JSONObject jsonObject;
    ArrayList<View>arrayList=new ArrayList<>();
    public ChildListView(Context context, JSONObject jsonObject, HashMap<String, Bitmap> hashMap) throws JSONException {
        super(context);
        this.jsonObject=jsonObject;
        String name=jsonObject.getString("name");
         View.inflate(context, R.layout.child_list_view,this);
        LinearLayout linearLayout=findViewById(R.id.ScrollViewLL);
        TextView textView=findViewById(R.id.child_list_name);
        textView.setText(name);
        ImageView imageView= findViewById(R.id.child_imageView);
        imageView.setImageBitmap(hashMap.get(name));
        try {
            JSONArray jsonArray=jsonObject.getJSONArray("child");
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObjectChild=jsonArray.getJSONObject(i);
                String childName=jsonObjectChild.getString("name");
                View view=ChildListViewImte(context,hashMap.get(childName),childName,i);
                linearLayout.addView(view,LinearLayout.LayoutParams.MATCH_PARENT,70);
                arrayList.add(view);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

    }
      View ChildListViewImte(Context context, Bitmap bitmap, String name, final int i){
        View view= View.inflate(context, R.layout.child_item,null);
        TextView textView=view.findViewById(R.id.child_itme_name);
        textView.setText(name);
        ImageView imageView=view.findViewById(R.id.child_itme_imageView);
        imageView.setImageBitmap(bitmap);
        view.findViewById(R.id.child_itme_delete).setOnClickListener(new OnClickListener() {
            int p=i;
            @Override
            public void onClick(View v) {
                View view1=arrayList.get(p);
                Log.d(TAG,"view1.getParent()="+view1.getParent().getClass().getName());
                if (view1.getParent() instanceof LinearLayout){
                    Log.d(TAG,"instanceof LinearLayout");
                    ((LinearLayout) view1.getParent()).removeView(view1);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        try {
                            jsonObject.getJSONArray("child").remove(p);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
      /*  Log.d(TAG,"v.getRootView()="+v.getRootView().getClass().getName());
        v.getRootView().setBackgroundColor(Color.GRAY);
        if (v.getRootView() instanceof LinearLayout){
            ((LinearLayout) v.getParent()).removeView(v);

        }*/
    }
}
