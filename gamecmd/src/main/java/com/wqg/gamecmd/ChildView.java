package com.wqg.gamecmd;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ChildView {
    static public String TAG="ChildView";
    static public View set(final Context context,final SetInterface setInterface){
        final View view1=View.inflate(context,R.layout.set,null);
        RadioGroup sexRg = view1.findViewById(R.id.sex_rg);
        final RadioButton manRb = view1.findViewById(R.id.man_rb);
        final RadioButton womanRb = view1.findViewById(R.id.woman_rb);
        if (CmdWindow.Room.equals("页面9_child1")){
            manRb.setChecked(true);
        }else  if (CmdWindow.Room.equals("页面9_child2")){
            womanRb.setChecked(true);
        }
        sexRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(RadioGroup rg, int checkedId) {
                // TODO Auto-generated method stub
                if(checkedId == manRb.getId()){
                    CmdWindow.Room="页面9_child1";
                }else if(checkedId == womanRb.getId()){
                    CmdWindow.Room="页面9_child2";
                }
            }
        });
        ImageButton EyeThreadTimeAdd=view1.findViewById(R.id.EyeThreadTimeAdd);
        final EditText EyeThreadTimeEditText=view1.findViewById(R.id.EyeThreadTimeEditText);
        ImageButton EyeThreadTimeRemove=view1.findViewById(R.id.EyeThreadTimeRemove);

        final SeekBar EyeThreadTimeSeekBar=view1.findViewById(R.id.EyeThreadTimeSeekBar);
        EyeThreadTimeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                EyeThreadTimeEditText.setText(""+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        EyeThreadTimeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EyeThreadTimeSeekBar.getProgress()<=EyeThreadTimeSeekBar.getMax()) {
                    EyeThreadTimeSeekBar.setProgress(EyeThreadTimeSeekBar.getProgress() + 1);
                    EyeThreadTimeEditText.setText(""+EyeThreadTimeSeekBar.getProgress());
                }
            }
        });
        EyeThreadTimeRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EyeThreadTimeSeekBar.getProgress()>0){
                    EyeThreadTimeSeekBar.setProgress(EyeThreadTimeSeekBar.getProgress()-1);
                    EyeThreadTimeEditText.setText(""+EyeThreadTimeSeekBar.getProgress());
                }
            }
        });



        ImageButton pageTimeAdd=view1.findViewById(R.id.pageTimeAdd);
        final EditText pageTimeEditText=view1.findViewById(R.id.pageTimeEditText);
        ImageButton pageTimeRemove=view1.findViewById(R.id.pageTimeRemove);

        final SeekBar pageTimeSeekBar=view1.findViewById(R.id.pageTimeSeekBar);
        pageTimeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pageTimeEditText.setText(""+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        pageTimeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (pageTimeSeekBar.getProgress()<=pageTimeSeekBar.getMax()){
                   pageTimeSeekBar.setProgress(pageTimeSeekBar.getProgress()+1);
                   pageTimeEditText.setText(""+pageTimeSeekBar.getProgress());
               }
            }
        });
        pageTimeRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pageTimeSeekBar.getProgress()>0){
                    pageTimeSeekBar.setProgress(pageTimeSeekBar.getProgress()-1);
                    pageTimeEditText.setText(""+pageTimeSeekBar.getProgress());
                }
            }
        });

        if (Connect.sharedPreferences!=null){
            int EyeThreadTime=Connect.sharedPreferences.getInt("EyeThreadTime",0);
            int pageTime=Connect.sharedPreferences.getInt("pageTime",0);
            EyeThreadTimeEditText.setText(""+EyeThreadTime);
            EyeThreadTimeSeekBar.setProgress(EyeThreadTime);
            pageTimeEditText.setText(""+pageTime);
            pageTimeSeekBar.setProgress(pageTime);
        }else {
            EyeThreadTimeEditText.setText(""+0);
            EyeThreadTimeSeekBar.setProgress(0);
            pageTimeEditText.setText(""+0);
            pageTimeSeekBar.setProgress(0);
        }
        final EditText GoldCoin=view1.findViewById(R.id.GoldCoin);
        GoldCoin.setText("");
        final EditText collaborators=view1.findViewById(R.id.Collaborators);
        if (Connect.sharedPreferences!=null){
            if (!Connect.sharedPreferences.getString("toChatUsername","").equals("")){
                collaborators.setText(Connect.sharedPreferences.getString("toChatUsername",""));
            }else {
                collaborators.setText("");
            }
        }else {
            collaborators.setText("");
        }
        view1.findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        view1.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setInterface.cancel();
            }
        });
        view1.findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setInterface.save();
                if (Connect.sharedPreferences!=null){
                    Connect.toChatUsername=collaborators.getText().toString();
                    SharedPreferences.Editor editor=Connect.sharedPreferences.edit();
                    editor.putString("toChatUsername",collaborators.getText().toString());
                    editor.putInt("EyeThreadTime",EyeThreadTimeSeekBar.getProgress());
                    editor.putInt("pageTime",pageTimeSeekBar.getProgress());
                    editor.commit();
                    Toast.makeText(context,"保存成功",Toast.LENGTH_SHORT).show();
                    setInterface.setConfigure(EyeThreadTimeSeekBar.getProgress(),pageTimeSeekBar.getProgress());
                }


            }
        });

        ImageButton xTimeAdd=view1.findViewById(R.id.xTimeAdd);
        final EditText xTimeEditText=view1.findViewById(R.id.xTimeEditText);
        xTimeEditText.setText(CmdWindow.x+"");
        ImageButton xTimeRemove=view1.findViewById(R.id.xTimeRemove);
        View.OnClickListener onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.xTimeAdd:
                    {
                        if (CmdWindow.x<10){
                            CmdWindow.x++;
                            xTimeEditText.setText(CmdWindow.x+"");
                        }

                    }break;
                    case R.id.xTimeRemove:
                    {
                        if (CmdWindow.x>0){
                            CmdWindow.x--;
                            xTimeEditText.setText(CmdWindow.x+"");
                        }
                    }break;
                }
            }
        };
        xTimeAdd.setOnClickListener(onClickListener);
        xTimeRemove.setOnClickListener(onClickListener);
        Spinner spinner = view1.findViewById(R.id.spinner);
        //数据
        ArrayList data_list = new ArrayList<String>();
        data_list.add("500000");
        data_list.add("100000");
        data_list.add("10000");
        data_list.add("1000");
        data_list.add("100");

        //适配器
        ArrayAdapter  arr_adapter= new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner.setAdapter(arr_adapter);
        setSpinnerItemSelectedByValue(spinner,CmdWindow.BS+"");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(parent.getItemAtPosition(position));
                int bs=Integer.parseInt(parent.getItemAtPosition(position).toString());
                CmdWindow.BS=bs;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view1;
    }
    /**
     * 根据值, 设置spinner默认选中:
     * @param spinner
     * @param value
     */
    public static void setSpinnerItemSelectedByValue(Spinner spinner,String value){
        SpinnerAdapter apsAdapter= spinner.getAdapter(); //得到SpinnerAdapter对象
        int k= apsAdapter.getCount();
        for(int i=0;i<k;i++){
            if(value.equals(apsAdapter.getItem(i).toString())){
                spinner.setSelection(i,true);// 默认选中项
                break;
            }
        }
    }
    static View buttonbarinit( Context context,final CmdInterface cmdInterface){
        View buttonbar= View.inflate(context, R.layout.button_bar,null);
        buttonbar.findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cmdInterface.sendAction();
            }
        });

        buttonbar.findViewById(R.id.close).setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                cmdInterface.close();
            }
        });
        buttonbar.findViewById(R.id.logview).setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                cmdInterface.log();
            }
        });
        buttonbar.findViewById(R.id.set).setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                cmdInterface.set();
            }
        });
        buttonbar.findViewById(R.id.screenshot).setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
              cmdInterface.screenshot(view.getContext());
            }
        });
      return buttonbar;
    }

    static public View ChildListView(Context context, JSONObject jsonObject,HashMap<String, Bitmap>hashMap) throws JSONException {
        String name=jsonObject.getString("name");
        View view= View.inflate(context, R.layout.child_list_view,null);
        LinearLayout linearLayout=view.findViewById(R.id.ScrollViewLL);
        TextView textView=view.findViewById(R.id.child_list_name);
        textView.setText(name);
        ImageView imageView=view.findViewById(R.id.child_imageView);
        imageView.setImageBitmap(hashMap.get(name));
        try {
            JSONArray jsonArray=jsonObject.getJSONArray("child");
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObjectChild=jsonArray.getJSONObject(i);
                String childName=jsonObjectChild.getString("name");
                linearLayout.addView(ChildListViewImte(context,hashMap.get(childName),childName),LinearLayout.LayoutParams.WRAP_CONTENT,70);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return view;
    }
    static public View ChildListViewImte(Context context, Bitmap bitmap, String name){
        View view= View.inflate(context, R.layout.child_item,null);
        TextView textView=view.findViewById(R.id.child_itme_name);
        textView.setText(name);
        ImageView imageView=view.findViewById(R.id.child_itme_imageView);
        imageView.setImageBitmap(bitmap);
        return view;
    }
}
